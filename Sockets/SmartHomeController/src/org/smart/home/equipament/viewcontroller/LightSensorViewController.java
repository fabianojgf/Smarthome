package org.smart.home.equipament.viewcontroller;

import java.io.IOException;

import org.smart.home.equipament.input.LightSensorInput;
import org.smart.home.equipament.input.delegate.LightSensorInputDelegate;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.model.LightSensor;
import org.smart.home.equipament.output.LightSensorOutput;
import org.smart.home.equipament.output.delegate.LightSensorOutputDelegate;
import org.smart.gateway.service.SocketServiceDelegate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class LightSensorViewController extends EquipamentViewController<LightSensor> implements LightSensorInputDelegate, LightSensorOutputDelegate, SocketServiceDelegate {

	public LightSensorViewController(String name) {
		super(name);
		setEquipament(new LightSensor());
		
		getEquipament().setName(name);	
		updateUri(getEquipament());
		
		setInput(new LightSensorInput(this));
		setOutput(new LightSensorOutput(this));
		
		getFrame().add(getOutput().getPanel());
		getFrame().add(getInput().getPanel());
		getFrame().setVisible(true);
		
		updateDisplay();
	}

	@Override
	public void changeValue(float value) {
		getEquipament().setValue(value);
		updateDisplay();
	}

	@Override
	public void updateDisplay() {
		getOutput().updateOutput(getEquipament());
		//sendInformation();
	}

	@Override
	public Equipament getObjectStatus() {
		return getEquipament();
	}

	@Override
	public void postReceivedMessage(String message) {
		ObjectMapper mapper = new ObjectMapper();
		LightSensor object;
		try {
			object = mapper.readValue(message, LightSensor.class);
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
