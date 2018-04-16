package org.smart.mqtt.client;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.smart.mqtt.printer.MessagePrinter;

public class SmartMqttClient implements MqttCallback {
	MqttClient myClient;
	MqttConnectOptions connOpt;
	ConcurrentLinkedQueue<MqttMessage> msgQ;
	SmartMqttClientDelegate delegate;
	Thread threadSubcribe;
	
	String brokerUri;
	String clientId;
	String topicDomain;
	String topicTargetId;
	String username;
	String password;

	public SmartMqttClient() {
		super();
	}
	
	public SmartMqttClient(SmartMqttClientOptions options) {
		super();
		this.brokerUri = options.getBrokerUri();
		this.clientId = options.getClientId();
		this.topicDomain = options.getTopicDomain();
		this.topicTargetId = options.getTopicTargetId();
		this.username = options.getUsername();
		this.password = options.getPassword();
	}

	public SmartMqttClient(String brokerUri, String clientId, String topicDomain, String topicTargetId, String username,
			String password) {
		super();
		this.brokerUri = brokerUri;
		this.clientId = clientId;
		this.topicDomain = topicDomain;
		this.topicTargetId = topicTargetId;
		this.username = username;
		this.password = password;
	}

	/**
	 * connectionLost
	 * This callback is invoked upon losing the MQTT connection.
	 */
	@Override
	public void connectionLost(Throwable t) {
		System.out.println("Connection lost!");
		// code to reconnect to the broker would go here if desired
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		try {
			System.out.println("Pub complete" + new String(arg0.getMessage().getPayload()));
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		msgQ.offer(arg1);
		delegate.postReceivedMessage(new String(arg1.getPayload()));
	}
	
	public void startSubscribeService() {
		threadSubcribe = new Thread() {
			public void run() {
				try {
					while(true) {
						int subQoS = 0;
						myClient.subscribe(fullTopicName(), subQoS);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		};
		threadSubcribe.start();
	}
	
	public void publishMessage(String textMessage) {
		int pubQoS = 0;
		MqttMessage message = new MqttMessage(textMessage.getBytes());
    	message.setQos(pubQoS);
    	message.setRetained(false);
    	
		MqttTopic topic = myClient.getTopic(fullTopicName());

		System.out.println("Message [" + textMessage + "] sent by [" + clientId + "] to topic [" + fullTopicName() + "]");

    	MqttDeliveryToken token = null;
    	try {
    		// publish message to broker
			token = topic.publish(message);
	    	// Wait until the message has been delivered to the broker
			token.waitForCompletion();
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * runClient
	 * The main functionality of this simple example.
	 * Create a MQTT client, connect to broker, pub/sub, disconnect.
	 */
	public void runClient() {
		msgQ = new ConcurrentLinkedQueue<MqttMessage>();
		MessagePrinter mp = new MessagePrinter(msgQ);
		Thread t = new Thread(mp);
		t.start();
		
		connOpt = new MqttConnectOptions();
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
//		if(username != null)
//			connOpt.setUserName(username);
//		if(password != null)
//			connOpt.setPassword(password.toCharArray());
		
		try {
			myClient = new MqttClient(brokerUri, clientId);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		System.out.println("Connected to " + brokerUri);

		startSubscribeService();
	}
	
	public void stopClient() {
		try {
			System.out.println("Disconnecting the client from broker.");
			threadSubcribe.stop();
			myClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String fullTopicName() {
		return topicDomain + "/" + topicTargetId;
	}
	
	public boolean isConnected() {
		return myClient.isConnected();
	}

	public SmartMqttClientDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(SmartMqttClientDelegate delegate) {
		this.delegate = delegate;
	}

	public String getBrokerUri() {
		return brokerUri;
	}

	public void setBrokerUri(String brokerUri) {
		this.brokerUri = brokerUri;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTopicDomain() {
		return topicDomain;
	}

	public void setTopicDomain(String topicDomain) {
		this.topicDomain = topicDomain;
	}

	public String getTopicTargetId() {
		return topicTargetId;
	}

	public void setTopicTargetId(String topicTargetId) {
		this.topicTargetId = topicTargetId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
