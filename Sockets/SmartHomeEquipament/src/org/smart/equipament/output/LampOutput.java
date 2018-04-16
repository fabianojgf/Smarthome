package org.smart.equipament.output;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.smart.equipament.model.Lamp;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.output.delegate.EquipamentOutputDelegate;

public class LampOutput extends EquipamentOutput {
	JLabel labelTitleOnOff, labelOnOff;

	public LampOutput(EquipamentOutputDelegate delegate) {
		super(delegate);
		getPanel().setLayout(new GridLayout(4, 1));
		
		labelTitleOnOff = new JLabel("Status:");
		labelOnOff = new JLabel("");
		
		getPanel().add(labelTitleOnOff);
		getPanel().add(labelOnOff);
		
		getPanel().setVisible(true);
	}

	@Override
	public void updateOutput(Equipament equipament) {
		super.updateOutput(equipament);
		
		Lamp object = (Lamp) equipament;
		labelOnOff.setText(object.isOn() ? "ON" : "OFF");
		getPanel().repaint();
	}
	
	@Override
	public void close() {
		
	}
}
