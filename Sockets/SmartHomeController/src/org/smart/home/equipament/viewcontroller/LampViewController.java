package org.smart.home.equipament.viewcontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.smart.home.equipament.input.LampInput;
import org.smart.home.equipament.input.delegate.LampInputDelegate;
import org.smart.home.equipament.model.AirConditioning;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.model.Lamp;
import org.smart.home.equipament.output.LampOutput;
import org.smart.home.equipament.output.delegate.LampOutputDelegate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LampViewController extends EquipamentViewController<Lamp> implements LampInputDelegate, LampOutputDelegate {

	public LampViewController(String name) {
		super(name);
		setEquipament(new Lamp());
		getEquipament().setName(name);	
		
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
