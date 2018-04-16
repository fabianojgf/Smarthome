package org.smart.mqtt.equipament.model;

public class Lamp extends Equipament {
	private boolean on;
	
	public Lamp() {
		super();
		on = false;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}
}
