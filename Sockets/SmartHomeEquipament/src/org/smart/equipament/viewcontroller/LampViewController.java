package org.smart.equipament.viewcontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.smart.equipament.input.LampInput;
import org.smart.equipament.input.delegate.LampInputDelegate;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.model.Lamp;
import org.smart.equipament.output.LampOutput;
import org.smart.equipament.output.delegate.LampOutputDelegate;
import org.smart.gateway.service.SocketServiceDelegate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LampViewController extends EquipamentViewController<Lamp> implements LampInputDelegate, LampOutputDelegate, SocketServiceDelegate {

	public LampViewController(String name) {
		super(name);
		setEquipament(new Lamp());
		
		getEquipament().setName(name);	
		updateUri(getEquipament());
		
		setInput(new LampInput(this));
		setOutput(new LampOutput(this));
		
		getFrame().add(getOutput().getPanel());
		getFrame().add(getInput().getPanel());
		getFrame().setVisible(true);
		
		updateDisplay();
	}

	@Override
	public void turnOn() {
		getEquipament().setOn(true);
		updateDisplay();
	}

	@Override
	public void turnOff() {
		getEquipament().setOn(false);
		updateDisplay();
	}

	@Override
	public void updateDisplay() {
		getOutput().updateOutput(getEquipament());
		sendInformation();
	}

	@Override
	public Equipament getObjectStatus() {
		return getEquipament();
	}

	@Override
	public void postReceivedMessage(String message) {
		ObjectMapper mapper = new ObjectMapper();
		Lamp object;
		try {
			object = mapper.readValue(message, Lamp.class);
			updateUri(object);
			
			setEquipament(object);
			getOutput().updateOutput(getEquipament());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
