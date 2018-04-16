package org.smart.equipament.input;

import java.util.Random;

import org.smart.equipament.input.delegate.EquipamentInputDelegate;
import org.smart.equipament.input.delegate.LightSensorInputDelegate;

public class LightSensorInput extends EquipamentInput {
	Thread thread;
	boolean condition = true;
	
	public LightSensorInput(EquipamentInputDelegate delegate) {
		super(delegate);
		
		getPanel().setVisible(true);
		
		changeState();
	}
	
	private void changeState() {
		thread = new Thread() {
			@Override
			public void run() {
				while(condition) {
					((LightSensorInputDelegate) getDelegate()).changeValue(new Random().nextFloat() % 100);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}
	
	@Override
	public void close() {
		condition = false;
		thread.interrupt();
	}
}
