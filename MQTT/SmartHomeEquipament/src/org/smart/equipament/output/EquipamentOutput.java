package org.smart.equipament.output;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.smart.equipament.model.Equipament;
import org.smart.equipament.output.delegate.EquipamentOutputDelegate;

public abstract class EquipamentOutput {
	private EquipamentOutputDelegate delegate;
	private JPanel panel;
	
	JLabel labelTitleType, labelType, labelTitleName, labelName, labelTitleUri, labelUri;
	
	public EquipamentOutput() {
		super();
		initPanel();
	}

	public EquipamentOutput(EquipamentOutputDelegate delegate) {
		super();
		this.delegate = delegate;
		initPanel();
	}
	
	public void initPanel() {
		panel = new JPanel(new GridLayout(2, 2));
		panel.setSize(480, 150);
		panel.setBackground(Color.WHITE);
		
		labelTitleType = new JLabel("Equipament Type:");
		labelType = new JLabel("");
		labelTitleName = new JLabel("Equipament Name:");
		labelName = new JLabel("");
		labelTitleUri = new JLabel("Address:");
		labelUri = new JLabel("");
		
		getPanel().add(labelTitleType);
		getPanel().add(labelType);
		getPanel().add(labelTitleName);
		getPanel().add(labelName);
		getPanel().add(labelTitleUri);
		getPanel().add(labelUri);
	}

	public EquipamentOutputDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(EquipamentOutputDelegate delegate) {
		this.delegate = delegate;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	public void updateOutput(Equipament equipament) {
		labelType.setText(equipament.getClass().getSimpleName());
		labelName.setText(equipament.getName());
		labelUri.setText(equipament.getUri());
		getPanel().repaint();
	}
	
	public abstract void close();
}
