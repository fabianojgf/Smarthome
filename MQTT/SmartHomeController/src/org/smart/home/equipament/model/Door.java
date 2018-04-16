package org.smart.home.equipament.model;

public class Door extends Equipament {
	private boolean open;

	public Door() {
		super();
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
