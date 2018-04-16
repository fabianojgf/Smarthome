package org.smart.gateway.message;

import org.smart.gateway.message.enums.GatewayMessageType;
import org.smart.gateway.model.GatewayClientInfo;

public class GatewayMessage {
	GatewayMessageType type;
	GatewayClientInfo clientInfo;
	String target;
	String content;
	
	public GatewayMessage() {
		super();
	}
	
	public GatewayMessage(GatewayMessageType type,
			GatewayClientInfo clientInfo, String target, String content) {
		super();
		this.type = type;
		this.clientInfo = clientInfo;
		this.target = target;
		this.content = content;
	}
	
	public GatewayMessageType getType() {
		return type;
	}
	
	public void setType(GatewayMessageType type) {
		this.type = type;
	}
	
	public GatewayClientInfo getClientInfo() {
		return clientInfo;
	}
	
	public void setClientInfo(GatewayClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
