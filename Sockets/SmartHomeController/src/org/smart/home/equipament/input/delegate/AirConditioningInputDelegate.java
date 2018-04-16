package org.smart.home.equipament.input.delegate;

public interface AirConditioningInputDelegate extends EquipamentInputDelegate {
	public void turnOn();
	public void turnOff();
	public void changeTemperature(int temperature);
}
