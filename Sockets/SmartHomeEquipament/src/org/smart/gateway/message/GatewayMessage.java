package org.smart.gateway.message;

import org.smart.gateway.message.enums.GatewayMessageType;
import org.smart.gateway.model.GatewayClientInfo;

public class GatewayMessage {
	GatewayMessageType type;
	GatewayClientInfo clientInfo;
	Object target;
	Object content;
	
	public GatewayMessage() {
		super();
	}
	
	public GatewayMessage(GatewayMessageType type,
			GatewayClientInfo clientInfo, Object target, Object contect) {
		super();
		this.type = type;
		this.clientInfo = clientInfo;
		this.target = target;
		this.content = contect;
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
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Object getContent() {
		return content;
	}
	
	public void setContent(Object content) {
		this.content = content;
	}
}
