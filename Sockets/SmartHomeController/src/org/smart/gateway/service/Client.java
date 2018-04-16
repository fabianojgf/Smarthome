package org.smart.gateway.service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import org.smart.gateway.message.GatewayMessage;
import org.smart.gateway.message.enums.GatewayMessageType;
import org.smart.gateway.model.GatewayClientInfo;
import org.smart.gateway.util.NetworkUtil;
import org.smart.json.JsonConverter;

public class Client {
	public static void main(String[] args) {
		int port = 8002;
		String origin = "client2";
		String target = "client1";
		
		GatewayClientInfo info = new GatewayClientInfo();
		info.setHost(NetworkUtil.getHostAddress());
		info.setPort(port);
		info.setKey(origin);
		
		GatewayMessage message = new GatewayMessage();
		message.setType(GatewayMessageType.OPEN_CONNECTION);
		message.setClientInfo(info);
		
		try {
			Socket client = new Socket("192.168.15.6", 8000);
			//Socket client = new Socket("10.41.101.32", 8000);
			System.out.println("O cliente se conectou ao servidor! - [" + origin + "] - " + "[" + port + "]");
			
			Scanner teclado = new Scanner(System.in);
			PrintStream saida = new PrintStream(client.getOutputStream());
			
			//send information to return
			saida.println(JsonConverter.objectToJson(message));
			
			//sending messages
			while (teclado.hasNextLine() && client.isConnected()) {
				message.setType(GatewayMessageType.MESSAGE);
				message.setContent(teclado.nextLine());
				message.setTarget(target);
				saida.println(JsonConverter.objectToJson(message));
            }

            saida.close();
            teclado.close();
            client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
