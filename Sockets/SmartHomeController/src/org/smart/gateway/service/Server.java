package org.smart.gateway.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			int port = 8002;
			
			ServerSocket server = new ServerSocket(port);
			System.out.println("Servidor Conectado - Port [" + port + "]");
			while(true) {
				Socket clientSocket = server.accept();
				
				SocketListeningThread thread = new SocketListeningThread(clientSocket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
