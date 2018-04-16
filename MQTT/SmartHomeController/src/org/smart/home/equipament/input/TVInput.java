package org.smart.home.equipament.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.smart.home.equipament.input.delegate.EquipamentInputDelegate;
import org.smart.home.equipament.input.delegate.TVInputDelegate;
import org.smart.home.equipament.model.TV;

public class TVInput extends EquipamentInput {

	public TVInput(EquipamentInputDelegate delegate) {
		super(delegate);
		
		JButton buttonOnOff = new JButton("On/Off");
		JButton buttonUpChannel = new JButton("Ch+");
		JButton buttonDownChannel = new JButton("Ch-");
		JButton buttonUpSound = new JButton("Vol+");
		JButton buttonDownSound = new JButton("Vol-");
		
		getPanel().add(buttonOnOff);
		getPanel().add(buttonUpChannel);
		getPanel().add(buttonDownChannel);
		getPanel().add(buttonUpSound);
		getPanel().add(buttonDownSound);
		
		buttonOnOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TVInputDelegate tDelegate = (TVInputDelegate) getDelegate();
				TV tEquipament = (TV) tDelegate.getObjectStatus();
				
				if(tEquipament.isOn())
					tDelegate.turnOff();
				else
					tDelegate.turnOn();
			}
		});
		buttonUpChannel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TVInputDelegate tDelegate = (TVInputDelegate) getDelegate();
				TV tEquipament = (TV) tDelegate.getObjectStatus();
				tDelegate.changeChannel(tEquipament.getChannel() + 1);
			}
		});
		buttonDownChannel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TVInputDelegate tDelegate = (TVInputDelegate) getDelegate();
				TV tEquipament = (TV) tDelegate.getObjectStatus();
				tDelegate.changeChannel(tEquipament.getChannel() - 1);
			}
		});
		buttonUpSound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TVInputDelegate tDelegate = (TVInputDelegate) getDelegate();
				TV tEquipament = (TV) tDelegate.getObjectStatus();
				tDelegate.changeSoundVolume(tEquipament.getSoundVolume() + 1);
			}
		});
		buttonDownSound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TVInputDelegate tDelegate = (TVInputDelegate) getDelegate();
				TV tEquipament = (TV) tDelegate.getObjectStatus();
				tDelegate.changeSoundVolume(tEquipament.getSoundVolume() - 1);
			}
		});
		
		getPanel().setVisible(true);
	}
	
	@Override
	public void close() {
		
	}
}
