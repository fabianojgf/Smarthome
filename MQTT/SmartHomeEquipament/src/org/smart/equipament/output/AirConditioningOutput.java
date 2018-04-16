package org.smart.equipament.output;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.smart.equipament.model.AirConditioning;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.output.delegate.EquipamentOutputDelegate;

public class AirConditioningOutput extends EquipamentOutput {
	
	JLabel labelTitleOnOff, labelOnOff, labelTitleTemp, labelTemp;

	public AirConditioningOutput(EquipamentOutputDelegate delegate) {
		super(delegate);
		
		labelTitleOnOff = new JLabel("Status:");
		labelOnOff = new JLabel("");
		labelTitleTemp = new JLabel("Temperature:");
		labelTemp = new JLabel("");
		
		getPanel().setLayout(new GridLayout(5, 1));
		getPanel().add(labelTitleOnOff);
		getPanel().add(labelOnOff);
		getPanel().add(labelTitleTemp);
		getPanel().add(labelTemp);
		
		getPanel().setVisible(true);
	}
	
	@Override
	public void updateOutput(Equipament equipament) {
		super.updateOutput(equipament);
		
		AirConditioning object = (AirConditioning) equipament;
		labelOnOff.setText(object.isOn() ? "ON" : "OFF");
		labelTemp.setText(object.isOn() ? (object.getTemperature() + " ºC") : "---");
		getPanel().repaint();
	}
	
	@Override
	public void close() {
		
	}
}
