package org.smart.mqtt.client;

public interface SmartMqttClientDelegate {
	public void postReceivedMessage(String message);
}
