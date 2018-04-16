package org.smart.gateway.frame;

import java.util.List;

import org.smart.gateway.model.GatewayClientInfo;

public interface GatewayFrameDelegate {
	public List<GatewayClientInfo> getGatewayClients();
}
