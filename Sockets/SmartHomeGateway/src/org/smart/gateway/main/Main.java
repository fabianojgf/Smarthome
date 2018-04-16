package org.smart.gateway.main;

import org.smart.gateway.viewcontroller.GatewayViewController;

public class Main {

	public static void main(String[] args) {
		GatewayViewController viewController = new GatewayViewController("Socket Gateway");
		viewController.start();
	}

}
