package org.smart.gateway.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketListeningThread extends Thread {
    protected Socket socket;
    
    private InputStream inp = null;
    private BufferedReader brinp = null;
    private DataOutputStream out = null;
    
    public SocketListeningThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                	closeSocket();
                    return;
                } else {
                	System.out.println("Target: " + line);
                    out.writeBytes(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    
    @Override
    public void interrupt() {
    	closeSocket();
    	super.interrupt();
    }
    
    public void closeSocket() {
    	try {
			inp.close();
			brinp.close();
	    	out.close();
	    	socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
