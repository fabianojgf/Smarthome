package org.smart.home.equipament.viewcontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.smart.home.equipament.input.DoorInput;
import org.smart.home.equipament.input.delegate.DoorInputDelegate;
import org.smart.home.equipament.model.AirConditioning;
import org.smart.home.equipament.model.Door;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.output.DoorOutput;
import org.smart.home.equipament.output.delegate.DoorOutputDelegate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DoorViewController extends EquipamentViewController<Door> implements DoorInputDelegate, DoorOutputDelegate {

	public DoorViewController(String name) {
		super(name);
		setEquipament(new Door());
		getEquipament().setName(name);	
		
		setInput(new DoorInput(this));
		setOutput(new DoorOutput(this));
		
		getFrame().add(getOutput().getPanel());
		getFrame().add(getInput().getPanel());
		getFrame().setVisible(true);
		
		updateDisplay();
	}

	@Override
	public void open() {
		getEquipament().setOpen(true);
		updateDisplay();
	}

	@Override
	public void close() {
		getEquipament().setOpen(false);
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
		Door object;
		try {
			object = mapper.readValue(message, Door.class);
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
