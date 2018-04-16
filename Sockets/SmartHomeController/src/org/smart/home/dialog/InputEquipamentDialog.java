package org.smart.home.dialog;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.home.equipament.model.enums.EquipamentType;

@SuppressWarnings("serial")
public class InputEquipamentDialog extends JDialog {
	JPanel panel;
	
	JLabel labelTargetName;
	JLabel labelEquipamentName;
	JLabel labelEquipamentType;
	
	JTextField textFieldTargetName;
	JComboBox<EquipamentType> comboEquipamentType;
	JTextField textFieldEquipamentName;
	
	JButton buttonOk;
	JButton buttonCancel;
	
	InputEquipamentDialogDelegate delegate;

	public InputEquipamentDialog(Frame owner, String title) {
		super(owner, title);
		panel = new JPanel();
		panel.setLayout(new GridLayout(10, 1));
		panel.setSize(400, 200);
		
		labelTargetName = new JLabel("Target Name");
		labelEquipamentType = new JLabel("Equipament Type");
		labelEquipamentName = new JLabel("Equipament Name");
		
		textFieldTargetName = new JTextField();
		comboEquipamentType = new JComboBox<>(EquipamentType.values());
		textFieldEquipamentName = new JTextField();
		
		buttonOk = new JButton("OK");
		buttonCancel = new JButton("Cancel");
		
		panel.add(labelTargetName); panel.add(textFieldTargetName);
		panel.add(labelEquipamentType); panel.add(comboEquipamentType);
		panel.add(labelEquipamentName); panel.add(textFieldEquipamentName);
		
		panel.add(buttonOk);
		panel.add(buttonCancel);
		
		buttonOk.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				SocketClientOptions options = new SocketClientOptions();
				options.setTargetName(textFieldTargetName.getText());
				
				delegate.postInputEquipamentDialog(options, (EquipamentType) comboEquipamentType.getSelectedItem(), textFieldEquipamentName.getText());
			
				dispose();
			}
		});
		
		buttonCancel.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		setSize(400, 200);
		setContentPane(panel);
	}
	
	public void useDefaultValues() {
		textFieldTargetName.setText("smarthome");
		textFieldEquipamentName.setText("");
	}

	public InputEquipamentDialogDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(InputEquipamentDialogDelegate delegate) {
		this.delegate = delegate;
	}
}
