package org.smart.equipament.viewcontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.smart.equipament.input.TVInput;
import org.smart.equipament.input.delegate.TVInputDelegate;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.model.TV;
import org.smart.equipament.output.TVOutput;
import org.smart.equipament.output.delegate.TVOutputDelegate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TVViewController extends EquipamentViewController<TV> implements TVInputDelegate, TVOutputDelegate {

	public TVViewController(String name) {
		super(name);
		setEquipament(new TV());
		
		getEquipament().setName(name);	
		updateUri(getEquipament());
		
		setInput(new TVInput(this));
		setOutput(new TVOutput(this));
		
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
	public void changeChannel(int channel) {
		getEquipament().setChannel(channel);
		updateDisplay();
	}
	
	@Override
	public void changeSoundVolume(int soundVolume) {
		getEquipament().setSoundVolume(soundVolume);
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
		TV object;
		try {
			object = mapper.readValue(message, TV.class);
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
