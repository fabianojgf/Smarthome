package org.smart.equipament.model;

public class TV extends Equipament {
	private boolean on;
	private int channel;
	private int soundVolume;
	
	public TV() {
		super();
		on = false;
		channel = 10;
		soundVolume = 8;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getSoundVolume() {
		return soundVolume;
	}

	public void setSoundVolume(int soundVolume) {
		this.soundVolume = soundVolume;
	}
	
}
