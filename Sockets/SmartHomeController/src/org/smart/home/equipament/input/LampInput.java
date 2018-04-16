package org.smart.home.equipament.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.smart.home.equipament.input.delegate.EquipamentInputDelegate;
import org.smart.home.equipament.input.delegate.LampInputDelegate;
import org.smart.home.equipament.model.Lamp;

public class LampInput extends EquipamentInput {

	public LampInput(EquipamentInputDelegate delegate) {
		super(delegate);
		
		JButton buttonOnOff = new JButton("On/Off");
		
		getPanel().add(buttonOnOff);
		
		buttonOnOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LampInputDelegate tDelegate = (LampInputDelegate) getDelegate();
				Lamp tEquipament = (Lamp) tDelegate.getObjectStatus();
				
				if(tEquipament.isOn())
					tDelegate.turnOff();
				else
					tDelegate.turnOn();
			}
		});
		
		getPanel().setVisible(true);
	}
	
	@Override
	public void close() {
		
	}
}
