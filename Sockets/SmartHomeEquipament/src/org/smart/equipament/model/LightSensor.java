package org.smart.equipament.model;

public class LightSensor extends Equipament {
	private float value;
	
	public LightSensor() {
		super();
		value = 0.0f;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
