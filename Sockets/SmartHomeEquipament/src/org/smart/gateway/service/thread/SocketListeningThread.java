package org.smart.gateway.service.thread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.smart.gateway.message.GatewayMessage;
import org.smart.gateway.message.enums.GatewayMessageType;
import org.smart.gateway.model.GatewayClientInfo;
import org.smart.json.JsonConverter;

public class SocketListeningThread extends Thread {
    protected Socket socketListening;
    protected SocketListeningThreadDelegate delegate;
    protected SocketClientThread socketClientThread;
    
    private InputStream inp = null;
    private BufferedReader brinp = null;
    private DataOutputStream out = null;
    
    private GatewayClientInfo clientInfo;
    
    public SocketListeningThread(Socket clientListening) {
        this.socketListening = clientListening;
    }

    public void run() {
        try {
            inp = socketListening.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socketListening.getOutputStream());
        } catch (IOException e) {
            return;
        }
        
        String line;
        while (true) {
            try {
                line = brinp.readLine();
                if (line == null) {
                	closeSocket();
                    return;
                } else {
                	System.out.println("IP Client: " + socketListening.getRemoteSocketAddress().toString());
                	System.out.println("Content: "+ line);
                	
                	GatewayMessage message = (GatewayMessage) JsonConverter.objectFromJson(line, GatewayMessage.class);
                	
                	if(message.getType() == GatewayMessageType.MESSAGE) {
	                	delegate.postInformation(message.getContent().toString());
	                	
	                    out.writeBytes(message.getContent().toString() + "\n\r");
	                    out.flush();
                	}
                	else {
                		if(message.getType() == GatewayMessageType.ERROR) {
                			JOptionPane.showMessageDialog(null, message.getContent().toString());
                		}
                	}
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
	    	
	    	socketListening.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public SocketListeningThreadDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(SocketListeningThreadDelegate delegate) {
		this.delegate = delegate;
	}

	public Socket getSocketListening() {
		return socketListening;
	}

	public void setSocketListening(Socket socketListening) {
		this.socketListening = socketListening;
	}

	public GatewayClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(GatewayClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
}
