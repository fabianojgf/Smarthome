package org.smart.services;

import java.io.Serializable;

public class MessageDeliveryOptions implements Serializable {
	String brokerUri;
	String clientId;
	String topicDomain;
	String topicTargetId;
	
	public MessageDeliveryOptions() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MessageDeliveryOptions(String brokerUri, String clientId,
                                  String topicDomain, String topicTargetId, String username,
                                  String password) {
		super();
		this.brokerUri = brokerUri;
		this.clientId = clientId;
		this.topicDomain = topicDomain;
		this.topicTargetId = topicTargetId;
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

	public String getTopicFullName() {
		return topicDomain + "/" + topicTargetId;
	}

	@Override
	public String toString() {
		return "MessageDeliveryOptions [brokerUri=" + brokerUri + ", clientId="
				+ clientId + ", topicDomain=" + topicDomain
				+ ", topicTargetId=" + topicTargetId + "]";
	}
}
