package org.smart.gateway.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.smart.gateway.model.GatewayClientInfo;
import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.gateway.service.thread.SocketClientThread;
import org.smart.gateway.service.thread.SocketClientThreadDelegate;
import org.smart.gateway.service.thread.SocketListeningThread;
import org.smart.gateway.service.thread.SocketListeningThreadDelegate;
import org.smart.gateway.util.NetworkUtil;

public class SocketService extends Thread implements SocketListeningThreadDelegate, SocketClientThreadDelegate {
	ServerSocket server;
	
	SocketListeningThread listeningThread;
	SocketClientThread clientThread;
	
	SocketServiceDelegate delegate;
	SocketClientOptions options;
	GatewayClientInfo clientInfo;
	
	public SocketService(SocketClientOptions options) {
		super();
		this.options = options;
		
		clientInfo = new GatewayClientInfo();
		clientInfo.setHost(NetworkUtil.getHostAddress());
		clientInfo.setPort(options.getLocalPort());
		clientInfo.setKey(options.getClientName());
	}

	public void run() {
		try {
			server = new ServerSocket(options.getLocalPort());
			
			while(true) {
				//Waiting for connection
				Socket clientSocket = server.accept();
				
				//Connection stablished
				listeningThread = new SocketListeningThread(clientSocket);
				listeningThread.setDelegate(SocketService.this);
				listeningThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void interrupt() {
		//Stop the server socket
		try {
			server.close();
			listeningThread.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.interrupt();
	}

	@Override
	public void postInformation(String message) {
		delegate.postReceivedMessage(message);
	}
	
	public void connect() {
		if(clientThread == null) {
			Socket client;
			try {
				client = new Socket(options.getHost(), options.getHostPort());
				
				clientThread = new SocketClientThread(client);
				clientThread.setDelegate(this);
				clientThread.setClientInfo(clientInfo);				
				clientThread.start();
				
				clientThread.connect();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String message) {
		if(clientThread != null) {
			clientThread.sendMessage(message);
		}
	}

	public SocketServiceDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(SocketServiceDelegate delegate) {
		this.delegate = delegate;
	}

	public SocketClientOptions getOptions() {
		return options;
	}

	public void setOptions(SocketClientOptions options) {
		this.options = options;
	}
}
