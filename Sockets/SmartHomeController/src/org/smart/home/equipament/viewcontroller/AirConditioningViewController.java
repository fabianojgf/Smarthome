package org.smart.home.equipament.viewcontroller;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.smart.gateway.service.SocketServiceDelegate;
import org.smart.home.equipament.input.AirConditioningInput;
import org.smart.home.equipament.input.delegate.AirConditioningInputDelegate;
import org.smart.home.equipament.model.AirConditioning;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.output.AirConditioningOutput;
import org.smart.home.equipament.output.delegate.AirConditioningOutputDelegate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AirConditioningViewController extends EquipamentViewController<AirConditioning> implements AirConditioningInputDelegate, AirConditioningOutputDelegate, SocketServiceDelegate {

	public AirConditioningViewController(String name) {
		super(name);
		setEquipament(new AirConditioning());
		getEquipament().setName(name);	
		
		setInput(new AirConditioningInput(this));
		setOutput(new AirConditioningOutput(this));
		
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
	public void changeTemperature(int temperature) {
		getEquipament().setTemperature(temperature);
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
		AirConditioning object;
		try {
			object = mapper.readValue(message, AirConditioning.class);
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
