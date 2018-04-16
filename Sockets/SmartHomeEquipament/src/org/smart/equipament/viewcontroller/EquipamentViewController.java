package org.smart.equipament.viewcontroller;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.smart.equipament.input.EquipamentInput;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.output.EquipamentOutput;
import org.smart.gateway.service.SocketService;
import org.smart.gateway.service.SocketServiceDelegate;
import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.gateway.util.NetworkUtil;
import org.smart.json.JsonConverter;

public abstract class EquipamentViewController<T> implements SocketServiceDelegate {
	private T equipament;
	private EquipamentInput input;
	private EquipamentOutput output;
	private JFrame frame;
	
	private SocketService service;
	
	public EquipamentViewController(String name) {
		super();
		
		frame = new JFrame(name);
        frame.setSize(480, 320);
        frame.setLayout(new GridLayout(2, 1));
        frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
				close();
			}
			
			@Override
			public void windowActivated(WindowEvent e) { }
		});
	}

	public T getEquipament() {
		return equipament;
	}

	public void setEquipament(T equipament) {
		this.equipament = equipament;
	}

	public EquipamentInput getInput() {
		return input;
	}

	public void setInput(EquipamentInput input) {
		this.input = input;
	}

	public EquipamentOutput getOutput() {
		return output;
	}

	public void setOutput(EquipamentOutput output) {
		this.output = output;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void initiliazeConnection(SocketClientOptions options) {
		service = new SocketService(options);
		service.setDelegate(this);
		service.start();
		
		service.connect();
		sendInformation();
	}

	public void sendInformation() {
		//updateUri((Equipament) getEquipament());
		if(service != null)
			service.sendMessage(JsonConverter.objectToJson((Equipament) getEquipament()));
	}
	
	public void updateUri(Equipament equipament) {
		equipament.setUri(NetworkUtil.getHostAddress());
	}
	
	public void close() {
		service.interrupt();
		service.stop();
		input.close();
		output.close();
	}
}
