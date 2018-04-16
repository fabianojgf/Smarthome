package org.smart.equipament.model;

public class Gate extends Equipament {
	private boolean open;	
	
	public Gate() {
		super();
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
