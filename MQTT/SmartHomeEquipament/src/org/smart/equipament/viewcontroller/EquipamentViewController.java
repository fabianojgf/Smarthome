package org.smart.equipament.viewcontroller;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import org.smart.equipament.input.EquipamentInput;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.output.EquipamentOutput;
import org.smart.home.util.NetworkUtil;
import org.smart.mqtt.client.SmartMqttClient;
import org.smart.mqtt.client.SmartMqttClientDelegate;
import org.smart.mqtt.client.SmartMqttClientOptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class EquipamentViewController<T> implements SmartMqttClientDelegate {
	private T equipament;
	private EquipamentInput input;
	private EquipamentOutput output;
	private JFrame frame;
	
	private SmartMqttClient connector;
	
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
				connector.stopClient();
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
	
	public SmartMqttClient getConnector() {
		return connector;
	}

	public void setConnector(SmartMqttClient connector) {
		this.connector = connector;
	}
	
	public void initiliazeConnection(String brokerUri, String clientId, String topicDomain, String topicTargetId, String user, String password) {
		connector = new SmartMqttClient(brokerUri, clientId, topicDomain, topicTargetId, user, password);
		connector.setDelegate(this);
		connector.runClient();
		sendInformation();
	}
	
	public void initiliazeConnection(SmartMqttClientOptions options) {
		connector = new SmartMqttClient(options);
		connector.setDelegate(this);
		connector.runClient();
		sendInformation();
	}

	public void sendInformation() {
		updateUri((Equipament) getEquipament());
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "---";
		try {
			jsonInString = mapper.writeValueAsString(equipament);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		if(connector != null && connector.isConnected())
			connector.publishMessage(jsonInString);
	}
	
	public void updateUri(Equipament equipament) {
		equipament.setUri(NetworkUtil.getHostAddress());
	}
}
