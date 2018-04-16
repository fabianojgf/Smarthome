package org.smart.home.equipament.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.smart.home.equipament.input.delegate.EquipamentInputDelegate;
import org.smart.home.equipament.input.delegate.GateInputDelegate;
import org.smart.home.equipament.model.Gate;

public class GateInput extends EquipamentInput {

	public GateInput(EquipamentInputDelegate delegate) {
		super(delegate);
		
		JButton buttonOnOff = new JButton("On/Off");
		
		getPanel().add(buttonOnOff);
		
		buttonOnOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GateInputDelegate tDelegate = (GateInputDelegate) getDelegate();
				Gate tEquipament = (Gate) tDelegate.getObjectStatus();
				
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
