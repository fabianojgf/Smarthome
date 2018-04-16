package org.smart.gateway.service.thread;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import org.smart.gateway.model.GatewayClientInfo;

public class SocketClientThread extends Thread {
	protected Socket socket;
	protected PrintStream saida;
	
	private GatewayClientInfo clientInfo;
	
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
	
	public void sendMessage(String message) {
		saida.println(message);
		//System.out.println("O cliente [Gateway] se conectou ao servidor [Final]!");
		saida.flush();
	}

	public GatewayClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(GatewayClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
}
