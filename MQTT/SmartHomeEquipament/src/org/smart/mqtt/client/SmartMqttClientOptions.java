package org.smart.mqtt.client;

public class SmartMqttClientOptions {
	String brokerUri;
	String clientId;
	String topicDomain;
	String topicTargetId;
	String username;
	String password;
	
	public SmartMqttClientOptions() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SmartMqttClientOptions(String brokerUri, String clientId,
			String topicDomain, String topicTargetId, String username,
			String password) {
		super();
		this.brokerUri = brokerUri;
		this.clientId = clientId;
		this.topicDomain = topicDomain;
		this.topicTargetId = topicTargetId;
		this.username = username;
		this.password = password;
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

	@Override
	public String toString() {
		return "SmartMqttClientOptions [brokerUri=" + brokerUri + ", clientId="
				+ clientId + ", topicDomain=" + topicDomain
				+ ", topicTargetId=" + topicTargetId + ", username=" + username
				+ ", password=" + password + "]";
	}
}
