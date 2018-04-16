package org.smart.mqtt.printer;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessagePrinter implements Runnable {
	ConcurrentLinkedQueue<MqttMessage> myQ;

	public MessagePrinter(ConcurrentLinkedQueue<MqttMessage> q) {
		myQ = q;
	}

	@Override
	public void run() {
		MqttMessage m = null;
		while (true) {
			m = myQ.poll();
			if (m != null) {
				try {
					System.out.println("-------------------------------------------------");
					System.out.println("| Message: " + new String(m.getPayload()));
					System.out.println("| Time: " + System.currentTimeMillis());
					System.out.println("-------------------------------------------------");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
