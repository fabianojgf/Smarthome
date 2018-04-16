package org.smart.home.equipament.input;

import org.smart.home.equipament.input.delegate.EquipamentInputDelegate;

public class LightSensorInput extends EquipamentInput {	
	public LightSensorInput(EquipamentInputDelegate delegate) {
		super(delegate);
		
		getPanel().setVisible(true);
	}
	
	@Override
	public void close() {
		
	}
}
