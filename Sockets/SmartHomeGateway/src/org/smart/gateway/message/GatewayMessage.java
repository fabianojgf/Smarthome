package org.smart.gateway.message;

import org.smart.gateway.message.enums.GatewayMessageType;
import org.smart.gateway.model.GatewayClientInfo;

public class GatewayMessage {
	GatewayMessageType type;
	GatewayClientInfo clientInfo;
	String target = "";
	String content = "";
	
	public GatewayMessage() {
		super();
	}
	
	public GatewayMessage(GatewayMessageType type,
			GatewayClientInfo clientInfo, String target, String contect) {
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

	@Override
	public String toString() {
		return "GatewayMessage [type=" + type + ", clientInfo=" + clientInfo
				+ ", target=" + target + ", content=" + content + "]";
	}
}
