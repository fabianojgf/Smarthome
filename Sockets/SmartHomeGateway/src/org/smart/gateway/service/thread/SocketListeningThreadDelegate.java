package org.smart.gateway.service.thread;

import org.smart.gateway.message.GatewayMessage;

public interface SocketListeningThreadDelegate {
	public void insertSocketListening(SocketListeningThread thread);
	public void postInformation(String message);
	public void redirectMessage(GatewayMessage message);
}
