package org.smart.gateway.model;

public class GatewayClientInfo {
	private String host;
	private Integer port;
	private String key;
	
	public GatewayClientInfo() {
		super();
	}

	public GatewayClientInfo(String host, int port, String key) {
		super();
		this.host = host;
		this.port = port;
		this.key = key;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "GatewayClientInfo [host=" + host + ", port=" + port + ", key="
				+ key + "]";
	}
}
