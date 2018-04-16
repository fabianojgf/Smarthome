package org.smart.home.equipament.viewcontroller;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.smart.home.equipament.input.EquipamentInput;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.output.EquipamentOutput;
import org.smart.home.util.NetworkUtil;
import org.smart.mqtt.client.SmartMqttClient;
import org.smart.mqtt.client.SmartMqttClientDelegate;
import org.smart.mqtt.client.SmartMqttClientOptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class EquipamentViewController<T> implements SmartMqttClientDelegate {
	private T equipament;
	private EquipamentInput input;
	private EquipamentOutput output;
	private JPanel frame;
	
	private SmartMqttClient connector;
	
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
	
	public SmartMqttClient getConnector() {
		return connector;
	}

	public void setConnector(SmartMqttClient connector) {
		this.connector = connector;
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
	
	public void close() {
		input.close();
		output.close();
	}
}
