package org.smart.equipament.output;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.smart.equipament.model.TV;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.output.delegate.EquipamentOutputDelegate;

public class TVOutput extends EquipamentOutput {
	JLabel labelTitleOnOff, labelOnOff, labelTitleChannel, labelChannel, labelTitleSoundVolume, labelSoundVolume;

	public TVOutput(EquipamentOutputDelegate delegate) {
		super(delegate);
		
		getPanel().setLayout(new GridLayout(6, 1));
		
		labelTitleOnOff = new JLabel("Status:");
		labelOnOff = new JLabel("");
		labelTitleChannel = new JLabel("Channel:");
		labelChannel = new JLabel("");
		labelTitleSoundVolume = new JLabel("Sound Volume:");
		labelSoundVolume = new JLabel("");
		
		getPanel().add(labelTitleOnOff);
		getPanel().add(labelOnOff);
		getPanel().add(labelTitleChannel);
		getPanel().add(labelChannel);
		getPanel().add(labelTitleSoundVolume);
		getPanel().add(labelSoundVolume);
		
		getPanel().setVisible(true);
	}

	@Override
	public void updateOutput(Equipament equipament) {
		super.updateOutput(equipament);
		
		TV object = (TV) equipament;
		labelOnOff.setText(object.isOn() ? "ON" : "OFF");
		labelChannel.setText(object.isOn() ? (object.getChannel() + "") : "---");
		labelSoundVolume.setText(object.isOn() ? (object.getSoundVolume() + "") : "---");
		getPanel().repaint();
	}
	
	@Override
	public void close() {
		
	}
}
