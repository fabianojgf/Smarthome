package org.smart.home.equipament.viewcontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.smart.home.equipament.input.GateInput;
import org.smart.home.equipament.input.delegate.GateInputDelegate;
import org.smart.home.equipament.model.AirConditioning;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.model.Gate;
import org.smart.home.equipament.output.GateOutput;
import org.smart.home.equipament.output.delegate.GateOutputDelegate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GateViewController extends EquipamentViewController<Gate> implements GateInputDelegate, GateOutputDelegate {

	public GateViewController(String name) {
		super(name);
		setEquipament(new Gate());
		getEquipament().setName(name);	
		
		setInput(new GateInput(this));
		setOutput(new GateOutput(this));
		
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
		Gate object;
		try {
			object = mapper.readValue(message, Gate.class);
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
