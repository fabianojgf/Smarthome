package org.smart.equipament.output;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.smart.equipament.model.Door;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.output.delegate.EquipamentOutputDelegate;

public class DoorOutput extends EquipamentOutput {
	JLabel labelTitleOpenClose, labelOpenClose;

	public DoorOutput(EquipamentOutputDelegate delegate) {
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
		
		Door object = (Door) equipament;
		labelOpenClose.setText(object.isOpen() ? "OPEN" : "CLOSED");
		getPanel().repaint();
	}
	
	@Override
	public void close() {
		
	}
}
