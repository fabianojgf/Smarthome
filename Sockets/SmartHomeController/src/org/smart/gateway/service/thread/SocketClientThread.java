package org.smart.gateway.service.thread;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import org.smart.gateway.message.GatewayMessage;
import org.smart.gateway.message.enums.GatewayMessageType;
import org.smart.gateway.model.GatewayClientInfo;
import org.smart.json.JsonConverter;

public class SocketClientThread extends Thread {
	protected Socket socket;
	protected PrintStream saida;
	
	private GatewayClientInfo clientInfo;
	private SocketClientThreadDelegate delegate;
	
	public SocketClientThread(Socket clientSocket) {
        socket = clientSocket;
        
        try {
			saida = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public void run() {
		
	}
	
	@Override
	public void interrupt() {
		try {
			saida.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		super.interrupt();
	}
	
	public void connect() {
		GatewayMessage completeMessage = new GatewayMessage();
		completeMessage.setClientInfo(clientInfo);
		completeMessage.setType(GatewayMessageType.OPEN_CONNECTION);
		
		saida.println(JsonConverter.objectToJson(completeMessage));
	}
	
	public void sendMessage(String target, String message) {
		GatewayMessage completeMessage = new GatewayMessage();
		completeMessage.setClientInfo(clientInfo);
		completeMessage.setType(GatewayMessageType.MESSAGE);
		
		completeMessage.setContent(message);
		completeMessage.setTarget(target);
		
		saida.println(JsonConverter.objectToJson(completeMessage));
	}

	public GatewayClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(GatewayClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public SocketClientThreadDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(SocketClientThreadDelegate delegate) {
		this.delegate = delegate;
	}
}
