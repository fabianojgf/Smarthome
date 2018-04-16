package org.smart.equipament.input.delegate;

public interface TVInputDelegate extends EquipamentInputDelegate {
	public void turnOn();
	public void turnOff();
	public void changeChannel(int channel);
	public void changeSoundVolume(int soundVolume);
}
