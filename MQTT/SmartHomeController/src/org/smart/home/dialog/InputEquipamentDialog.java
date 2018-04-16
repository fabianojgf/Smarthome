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

import org.smart.home.equipament.model.enums.EquipamentType;
import org.smart.home.util.NetworkUtil;
import org.smart.mqtt.client.SmartMqttClientOptions;

public class InputEquipamentDialog extends JDialog {
	JPanel panel;
	
	JLabel labelBrokerHost;
	JLabel labelBrokerPort;
	JLabel labelClientId;
	JLabel labelTopicDomain;
	JLabel labelTopicTargetId;
	JLabel labelUsername;
	JLabel labelPassword;
	JLabel labelEquipamentName;
	JLabel labelEquipamentType;
	
	JTextField textFieldBrokerHost;
	JTextField textFieldBrokerPort;
	JTextField textFieldClientId;
	JTextField textFieldTopicDomain;
	JTextField textFieldTopicTargetId;
	JTextField textFieldUsername;
	JTextField textFieldPassword;
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
		
		labelBrokerHost = new JLabel("Broker Host");
		labelBrokerPort = new JLabel("Broker Port");
		labelClientId = new JLabel("Client ID");
		labelTopicDomain = new JLabel("Topic Domain");
		labelTopicTargetId = new JLabel("Topic Target ID");
		labelUsername = new JLabel("Username");
		labelPassword = new JLabel("Password");
		labelEquipamentType = new JLabel("Equipament Type");
		labelEquipamentName = new JLabel("Equipament Name");
		
		textFieldBrokerHost = new JTextField();
		textFieldBrokerPort = new JTextField();
		textFieldClientId = new JTextField();
		textFieldTopicDomain = new JTextField();
		textFieldTopicTargetId = new JTextField();
		textFieldUsername = new JTextField();
		textFieldPassword = new JTextField();
		comboEquipamentType = new JComboBox<>(EquipamentType.values());
		textFieldEquipamentName = new JTextField();
		
		buttonOk = new JButton("OK");
		buttonCancel = new JButton("Cancel");
		
		panel.add(labelBrokerHost); panel.add(textFieldBrokerHost);
		panel.add(labelBrokerPort); panel.add(textFieldBrokerPort);
		panel.add(labelClientId); panel.add(textFieldClientId);
		panel.add(labelTopicDomain); panel.add(textFieldTopicDomain);
		panel.add(labelTopicTargetId); panel.add(textFieldTopicTargetId);
		panel.add(labelUsername); panel.add(textFieldUsername);
		panel.add(labelPassword); panel.add(textFieldPassword);
		panel.add(labelEquipamentType); panel.add(comboEquipamentType);
		panel.add(labelEquipamentName); panel.add(textFieldEquipamentName);
		
		panel.add(buttonOk);
		panel.add(buttonCancel);
		
		buttonOk.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				SmartMqttClientOptions options = new SmartMqttClientOptions();
				options.setBrokerUri("tcp://" + textFieldBrokerHost.getText() + ":" + textFieldBrokerPort.getText());
				options.setClientId(textFieldClientId.getText());
				options.setTopicDomain(textFieldTopicDomain.getText());
				options.setTopicTargetId(textFieldTopicTargetId.getText());
				options.setUsername(textFieldUsername.getText());
				options.setPassword(textFieldPassword.getText());
				
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
		textFieldBrokerHost.setText(NetworkUtil.getHostAddress());
		textFieldBrokerPort.setText("1883");
		textFieldClientId.setText("");
		textFieldTopicDomain.setText("smarthome");
		textFieldTopicTargetId.setText("");
		textFieldEquipamentName.setText("");
	}

	public InputEquipamentDialogDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(InputEquipamentDialogDelegate delegate) {
		this.delegate = delegate;
	}
}
