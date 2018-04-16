package org.smart.home.equipament.input;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.smart.home.equipament.input.delegate.EquipamentInputDelegate;

public abstract class EquipamentInput {
	private EquipamentInputDelegate delegate;
	private JPanel panel;

	public EquipamentInput(EquipamentInputDelegate delegate) {
		super();
		this.delegate = delegate;
		
		panel = new JPanel(new GridLayout(1, 3));
		panel.setSize(480, 150);
		panel.setBackground(Color.BLACK);
	}

	public EquipamentInputDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(EquipamentInputDelegate delegate) {
		this.delegate = delegate;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	public abstract void close();
}
