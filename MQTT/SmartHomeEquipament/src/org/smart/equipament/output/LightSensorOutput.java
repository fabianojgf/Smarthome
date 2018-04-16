package org.smart.equipament.output;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.smart.equipament.model.Lamp;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.model.LightSensor;
import org.smart.equipament.output.delegate.EquipamentOutputDelegate;

public class LightSensorOutput extends EquipamentOutput {
	JLabel labelTitleLightValue, labelLightValue;

	public LightSensorOutput(EquipamentOutputDelegate delegate) {
		super(delegate);
		getPanel().setLayout(new GridLayout(4, 1));
		
		labelTitleLightValue = new JLabel("Light Value:");
		labelLightValue = new JLabel("");
		
		getPanel().add(labelTitleLightValue);
		getPanel().add(labelLightValue);
		
		getPanel().setVisible(true);
	}

	@Override
	public void updateOutput(Equipament equipament) {
		super.updateOutput(equipament);
		
		LightSensor object = (LightSensor) equipament;
		labelLightValue.setText("" + object.getValue());
		getPanel().repaint();
	}
	
	@Override
	public void close() {
		
	}
}
