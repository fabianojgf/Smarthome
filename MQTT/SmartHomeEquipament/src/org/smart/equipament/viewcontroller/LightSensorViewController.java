package org.smart.equipament.viewcontroller;

import org.smart.equipament.input.LightSensorInput;
import org.smart.equipament.input.delegate.LightSensorInputDelegate;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.model.LightSensor;
import org.smart.equipament.output.LightSensorOutput;
import org.smart.equipament.output.delegate.LightSensorOutputDelegate;

public class LightSensorViewController extends EquipamentViewController<LightSensor> implements LightSensorInputDelegate, LightSensorOutputDelegate {

	public LightSensorViewController(String name) {
		super(name);
		setEquipament(new LightSensor());
		
		getEquipament().setName(name);	
		updateUri(getEquipament());
		
		setOutput(new LightSensorOutput(this));
		setInput(new LightSensorInput(this));
		
		getFrame().add(getOutput().getPanel());
		getFrame().add(getInput().getPanel());
		getFrame().setVisible(true);
		
		//updateDisplay();
	}

	@Override
	public void changeValue(float value) {
		getEquipament().setValue(value);
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
		//Do nothing
	}
}
