package org.smart.equipament.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.smart.equipament.input.delegate.DoorInputDelegate;
import org.smart.equipament.input.delegate.EquipamentInputDelegate;
import org.smart.equipament.model.Door;

public class DoorInput extends EquipamentInput {

	public DoorInput(EquipamentInputDelegate delegate) {
		super(delegate);
		
		JButton buttonOnOff = new JButton("On/Off");
		
		getPanel().add(buttonOnOff);
		
		buttonOnOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DoorInputDelegate tDelegate = (DoorInputDelegate) getDelegate();
				Door tEquipament = (Door) tDelegate.getObjectStatus();
				
				if(tEquipament.isOpen())
					tDelegate.close();
				else
					tDelegate.open();
			}
		});
		
		getPanel().setVisible(true);
	}
	
	@Override
	public void close() {
		
	}
}
