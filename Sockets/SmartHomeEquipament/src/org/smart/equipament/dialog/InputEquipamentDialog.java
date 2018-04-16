package org.smart.equipament.dialog;

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
import org.smart.equipament.model.enums.EquipamentType;

@SuppressWarnings("serial")
public class InputEquipamentDialog extends JDialog {
	JPanel panel;
	
	JLabel labelHost;
	JLabel labelHostPort;
	JLabel labelLocalPort;
	JLabel labelClientName;
	JLabel labelTargetName;
	JLabel labelEquipamentName;
	JLabel labelEquipamentType;
	
	JTextField textFieldHost;
	JTextField textFieldHostPort;
	JTextField textFieldLocalPort;
	JTextField textFieldClientName;
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
		
		labelHost = new JLabel("Host Address");
		labelHostPort = new JLabel("Host Port");
		labelLocalPort = new JLabel("Local Port");
		labelClientName = new JLabel("Client Name");
		labelTargetName = new JLabel("Target Name");
		labelEquipamentType = new JLabel("Equipament Type");
		labelEquipamentName = new JLabel("Equipament Name");
		
		textFieldHost = new JTextField();
		textFieldHostPort = new JTextField();
		textFieldLocalPort = new JTextField();
		textFieldClientName = new JTextField();
		textFieldTargetName = new JTextField();
		comboEquipamentType = new JComboBox<>(EquipamentType.values());
		textFieldEquipamentName = new JTextField();
		
		buttonOk = new JButton("OK");
		buttonCancel = new JButton("Cancel");
		
		panel.add(labelHost); panel.add(textFieldHost);
		panel.add(labelHostPort); panel.add(textFieldHostPort);
		panel.add(labelLocalPort); panel.add(textFieldLocalPort);
		panel.add(labelClientName); panel.add(textFieldClientName);
		panel.add(labelTargetName); panel.add(textFieldTargetName);
		panel.add(labelEquipamentType); panel.add(comboEquipamentType);
		panel.add(labelEquipamentName); panel.add(textFieldEquipamentName);
		
		panel.add(buttonOk);
		panel.add(buttonCancel);
		
		buttonOk.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				SocketClientOptions options = new SocketClientOptions();
				options.setHost(textFieldHost.getText());
				options.setHostPort(Integer.valueOf(textFieldHostPort.getText()));
				options.setLocalPort(Integer.valueOf(textFieldLocalPort.getText()));
				options.setClientName(textFieldClientName.getText());
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
		textFieldHost.setText("localhost");
		textFieldHostPort.setText("8000");
		textFieldLocalPort.setText("8001");
		textFieldClientName.setText("");
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
