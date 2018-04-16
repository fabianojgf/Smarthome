package org.smart.equipament.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.smart.equipament.input.delegate.AirConditioningInputDelegate;
import org.smart.equipament.input.delegate.EquipamentInputDelegate;
import org.smart.equipament.model.AirConditioning;

public class AirConditioningInput extends EquipamentInput {
	
	public AirConditioningInput(EquipamentInputDelegate delegate) {
		super(delegate);

		JButton buttonOnOff = new JButton("On/Off");
		JButton buttonUpTemp = new JButton("+");
		JButton buttonDownTemp = new JButton("-");
		
		getPanel().add(buttonOnOff);
		getPanel().add(buttonUpTemp);
		getPanel().add(buttonDownTemp);
		
		buttonOnOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AirConditioningInputDelegate tDelegate = (AirConditioningInputDelegate) getDelegate();
				AirConditioning tEquipament = (AirConditioning) tDelegate.getObjectStatus();
				
				if(tEquipament.isOn())
					tDelegate.turnOff();
				else
					tDelegate.turnOn();
			}
		});
		buttonUpTemp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AirConditioningInputDelegate tDelegate = (AirConditioningInputDelegate) getDelegate();
				AirConditioning tEquipament = (AirConditioning) tDelegate.getObjectStatus();
				tDelegate.changeTemperature(tEquipament.getTemperature() + 1);
			}
		});
		buttonDownTemp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AirConditioningInputDelegate tDelegate = (AirConditioningInputDelegate) getDelegate();
				AirConditioning tEquipament = (AirConditioning) tDelegate.getObjectStatus();
				tDelegate.changeTemperature(tEquipament.getTemperature() - 1);
			}
		});

		getPanel().setVisible(true);
	}
	
	@Override
	public void close() {
		
	}
}
