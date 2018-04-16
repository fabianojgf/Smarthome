package org.smart.home.equipament.viewcontroller;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.smart.home.equipament.model.Equipament;
import org.smart.gateway.message.GatewayMessage;
import org.smart.gateway.service.SocketService;
import org.smart.gateway.service.SocketServiceDelegate;
import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.gateway.util.NetworkUtil;
import org.smart.home.equipament.input.EquipamentInput;
import org.smart.home.equipament.output.EquipamentOutput;
import org.smart.json.JsonConverter;

public abstract class EquipamentViewController<T> implements SocketServiceDelegate {
	private T equipament;
	private EquipamentInput input;
	private EquipamentOutput output;
	private JPanel frame;
	
	private SocketClientOptions options;
	private EquipamentViewControllerDelegate delegate;
	
	String name;
	String uri;
	
	public EquipamentViewController(String name) {
		super();
		
		this.frame = new JPanel();
		this.frame.setSize(480, 320);
        this.frame.setLayout(new GridLayout(2, 1));
        this.frame.setBackground(Color.BLUE);
        
        this.name = name;
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

	public JPanel getFrame() {
		return frame;
	}

	public void setFrame(JPanel frame) {
		this.frame = frame;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public EquipamentViewControllerDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(EquipamentViewControllerDelegate delegate) {
		this.delegate = delegate;
	}

	public void initiliazeConnection(SocketClientOptions options) {
		this.options = options;
	}

	public void sendInformation() {
		if(delegate != null)
		delegate.sendMessage(options.getTargetName(), JsonConverter.objectToJson(equipament));
	}
	
	public void updateUri(Equipament equipament) {
		equipament.setUri(NetworkUtil.getHostAddress());
	}
	
	public void close() {
		input.close();
		output.close();
	}
}
