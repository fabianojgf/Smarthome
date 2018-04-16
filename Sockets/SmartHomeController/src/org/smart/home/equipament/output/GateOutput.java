package org.smart.home.equipament.output;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.smart.home.equipament.model.Gate;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.output.delegate.EquipamentOutputDelegate;

public class GateOutput extends EquipamentOutput {
	JLabel labelTitleOpenClose, labelOpenClose;

	public GateOutput(EquipamentOutputDelegate delegate) {
		super(delegate);
		getPanel().setLayout(new GridLayout(4, 1));
		
		labelTitleOpenClose = new JLabel("Status:");
		labelOpenClose = new JLabel("");
		
		getPanel().add(labelTitleOpenClose);
		getPanel().add(labelOpenClose);
		
		getPanel().setVisible(true);
	}

	@Override
	public void updateOutput(Equipament equipament) {
		super.updateOutput(equipament);
		
		Gate object = (Gate) equipament;
		labelOpenClose.setText(object.isOpen() ? "OPEN" : "CLOSED");
		getPanel().repaint();
	}
	
	@Override
	public void close() {
		
	}
}
