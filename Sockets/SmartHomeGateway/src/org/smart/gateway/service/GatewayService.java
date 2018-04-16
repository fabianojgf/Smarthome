package org.smart.gateway.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.smart.gateway.message.GatewayMessage;
import org.smart.gateway.message.enums.GatewayMessageType;
import org.smart.gateway.model.GatewayClientInfo;
import org.smart.gateway.service.thread.SocketClientThread;
import org.smart.gateway.service.thread.SocketClientThreadDelegate;
import org.smart.gateway.service.thread.SocketListeningThread;
import org.smart.gateway.service.thread.SocketListeningThreadDelegate;
import org.smart.json.JsonConverter;

public class GatewayService implements SocketListeningThreadDelegate, SocketClientThreadDelegate {
	ServerSocket server;
	
	List<GatewayClientInfo> clients;
	Hashtable<String, SocketListeningThread> socketListeningThreads;
	Hashtable<String, SocketClientThread> socketClientThreads;
	
	GatewayServiceDelegate delegate;
	
	public GatewayService() {
		super();
		clients = new ArrayList<GatewayClientInfo>();
		socketListeningThreads = new Hashtable<String, SocketListeningThread>();
		socketClientThreads = new Hashtable<String, SocketClientThread>();
	}
	
	public void start() {
		try {
			server = new ServerSocket(8000);
			delegate.postInformation("Connected");
			
			while(true) {
				//Waiting for connection
				Socket clientSocket = server.accept();
				
				//Connection stablished
				SocketListeningThread thread = new SocketListeningThread(clientSocket);
				thread.setDelegate(this);
				thread.start();
				
				//Adding the new client threat in thread collection
				//socketListeningThreads.add(thread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void stop() {
		//Stop the server socket
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		removeClients();	
	}
	
	public void insertClient(GatewayClientInfo client) {
		clients.add(client);
	}
	
	public void removeClient(GatewayClientInfo client) {
		clients.remove(client);
	}
	
	public void removeClients() {
		Iterator<GatewayClientInfo> it = clients.iterator();
		while(it.hasNext()) {
			removeClient(it.next());
		}
		
		Iterator<String> itThread = socketListeningThreads.keySet().iterator();
		while(itThread.hasNext()) {
			//Get the key
			String key = itThread.next();
			
			//Stopping thread
			SocketListeningThread t = socketListeningThreads.get(key);
			t.interrupt();
			
			//Removing thread from thread collection
			itThread.remove();
		}
		
		itThread = socketClientThreads.keySet().iterator();
		while(itThread.hasNext()) {
			//Get the key
			String key = itThread.next();
			
			//Stopping thread
			SocketClientThread t = socketClientThreads.get(key);
			t.interrupt();
			
			//Removing thread from thread collection
			itThread.remove();
		}
	}

	public GatewayServiceDelegate getDelagate() {
		return delegate;
	}

	public void setDelagate(GatewayServiceDelegate delagate) {
		this.delegate = delagate;
	}

	public List<GatewayClientInfo> getClients() {
		return clients;
	}

	public void setClients(List<GatewayClientInfo> clients) {
		this.clients = clients;
	}

	@Override
	public void insertSocketListening(SocketListeningThread thread) {
		socketListeningThreads.put(thread.getClientInfo().getKey(), thread);
		clients.add(thread.getClientInfo());
		delegate.updatePresentation();
	}
	
	@Override
	public void insertSocketClient(SocketClientThread thread) {
		socketClientThreads.put(thread.getClientInfo().getKey(), thread);
	}

	@Override
	public void postInformation(String message) {
		delegate.postInformation(message);
	}

	@Override
	public void redirectMessage(GatewayMessage message) {
		try {
			System.out.println(message.toString());
			
			//Se já existe socket client para esse host, usa-o
    		SocketClientThread socketClientThread = socketClientThreads.get(message.getTarget());
    		if(socketClientThread != null) {
				socketClientThread.sendMessage(JsonConverter.objectToJson(message));
    		}
    		//Se não existe socket client, mas há server, para esse host, cria-se um novo e usa-o
    		else {
    			SocketListeningThread socketListeningThread = socketListeningThreads.get(message.getTarget());
    			if(socketListeningThread != null) {
    	    		GatewayClientInfo clientRedirect = socketListeningThread.getClientInfo();
    	    		
    	    		Socket client = new Socket(clientRedirect.getHost(), clientRedirect.getPort());
    				
    				System.out.println("O cliente [Gateway] se conectou ao servidor [Final]!");
    				
    				socketClientThread = new SocketClientThread(client);
    				socketClientThread.setClientInfo(clientRedirect);
    				
    				insertSocketClient(socketClientThread);
    				
    				socketClientThread.start();
    				
    				socketClientThread.sendMessage(JsonConverter.objectToJson(message));
    			}
    			//Se não existe socket client nem server, retorna-se mensagem para o origin
    			else {
    				//Para quebrar um possível loop
    				if(!message.getTarget().equals(message.getClientInfo().getKey())) {
    					message.setContent("Destinatário [" + message.getTarget() + "] não encontrado. Mensagem: [" + message.getContent() + "]");
    					message.setType(GatewayMessageType.ERROR);
    					message.setTarget(message.getClientInfo().getKey());
    					redirectMessage(message);
    				}
    			}
    		}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
