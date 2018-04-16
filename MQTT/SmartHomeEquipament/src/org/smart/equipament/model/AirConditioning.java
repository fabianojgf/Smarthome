package org.smart.equipament.model;

public class AirConditioning extends Equipament {
	private boolean on;
	private int temperature;
	private int min = 16, max = 26;
	
	public AirConditioning() {
		super();
		on = false;
		temperature = (min + max) / 2;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		if(temperature >= min && temperature <= max)
			this.temperature = temperature;
	}
}
