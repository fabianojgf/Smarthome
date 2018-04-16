package org.smart.home.viewcontroller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.smart.home.equipament.viewcontroller.EquipamentViewController;

@SuppressWarnings("rawtypes")
public class RoomController {
	private String name;
	private List<EquipamentViewController> equipaments;
	private JPanel panel;
	
	public RoomController(String name) {
		super();
		this.name = name;
		this.equipaments = new ArrayList<EquipamentViewController>();
		
		panel = new JPanel();
		panel.setBackground(Color.RED);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EquipamentViewController> getEquipaments() {
		return equipaments;
	}

	public void setEquipaments(List<EquipamentViewController> equipaments) {
		this.equipaments = equipaments;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
}
