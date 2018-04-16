package org.smart.gateway.viewcontroller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;

import org.smart.gateway.frame.GatewayFrame;
import org.smart.gateway.frame.GatewayFrameDelegate;
import org.smart.gateway.model.GatewayClientInfo;
import org.smart.gateway.service.GatewayService;
import org.smart.gateway.service.GatewayServiceDelegate;

public class GatewayViewController implements GatewayFrameDelegate, GatewayServiceDelegate {
	private GatewayFrame frame;
	private GatewayService service;
	
	public GatewayViewController(String name) {
		super();
		service = new GatewayService();
		
		frame = new GatewayFrame(name);
		frame.setDelegate(this);
        frame.setSize(640, 480);
        frame.setLayout(null);
        frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.loadViews();
		
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) { }
			
			@Override
			public void windowIconified(WindowEvent e) { }
			
			@Override
			public void windowDeiconified(WindowEvent e) { }
			
			@Override
			public void windowDeactivated(WindowEvent e) { }
			
			@Override
			public void windowClosing(WindowEvent e) { }
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnectClients();
				stop();
			}
			
			@Override
			public void windowActivated(WindowEvent e) { }
		});
		
		frame.setVisible(true);
	}
	
	public void start() {
		service.setDelagate(this);
		service.start();
	}
	
	public void stop() {
		if(service != null) {
			service.setDelagate(null);
			service.stop();
		}
	}
	
	public void disconnectClients() {
		service.removeClients();
	}

	@Override
	public List<GatewayClientInfo> getGatewayClients() {
		return service.getClients();
	}

	@Override
	public void postInformation(String message) {
		frame.update(message);
	}
	
	@Override
	public void updatePresentation() {
		frame.updatePresentation();
	}
}
