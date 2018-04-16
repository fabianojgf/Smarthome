package org.smart.home.viewcontroller;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import org.smart.gateway.message.GatewayMessage;
import org.smart.gateway.message.enums.GatewayMessageType;
import org.smart.gateway.service.SocketService;
import org.smart.gateway.service.SocketServiceDelegate;
import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.home.dialog.ConfigControllerDialog;
import org.smart.home.dialog.ConfigControllerDialogDelegate;
import org.smart.home.dialog.InputEquipamentDialog;
import org.smart.home.dialog.InputEquipamentDialogDelegate;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.model.enums.EquipamentType;
import org.smart.home.equipament.viewcontroller.EquipamentViewController;
import org.smart.home.equipament.viewcontroller.EquipamentViewControllerDelegate;
import org.smart.home.equipament.viewcontroller.util.EquipamentViewControllerUtil;
import org.smart.json.JsonConverter;

public class HomeController implements InputEquipamentDialogDelegate, ConfigControllerDialogDelegate, SocketServiceDelegate, EquipamentViewControllerDelegate {
	private List<RoomController> rooms;
	private JFrame frame;
	private JTabbedPane tabbedPane;
	
	private SocketService service;
	private SocketClientOptions options;
	private Hashtable<String, EquipamentViewController> equipaments;
	
	public HomeController(String name) {
		super();
		
		rooms = new ArrayList<RoomController>();
		equipaments = new Hashtable<String, EquipamentViewController>();
		
		frame = new JFrame(name);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(3, 1));
        frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) { }
			
			@Override
			public void windowIconified(WindowEvent e) { }
			
			@Override
			public void windowDeiconified(WindowEvent e) { }
			
			@Override
			public void windowDeactivated(WindowEvent e) { }
			
			@Override
			public void windowClosing(WindowEvent e) { }
			
			@Override
			public void windowClosed(WindowEvent e) { }
			
			@Override
			public void windowActivated(WindowEvent e) { }
		});
		
		loadMenus();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setSize(800, 600);
		
		frame.setContentPane(tabbedPane);
		
		frame.setVisible(true);
	}
	
	public void loadMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_M);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);
		
		JMenuItem menuItem1 = new JMenuItem("Connection", KeyEvent.VK_N);
		menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem1.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem1);
		
		JMenuItem menuItem = new JMenuItem("New Room", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem);
		
		JMenuItem menuItem2 = new JMenuItem("New Equipament", KeyEvent.VK_N);
		menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		menuItem2.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem2);
		
		menuItem1.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigControllerDialog dialog = new ConfigControllerDialog(frame, "New Equipament");
				dialog.setDelegate(HomeController.this);
				dialog.useDefaultValues();
				dialog.show();
			}
		});
		
		menuItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				insertRoom();
			}
		});
		
		menuItem2.addActionListener(new ActionListener() {	
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				InputEquipamentDialog dialog = new InputEquipamentDialog(frame, "New Equipament");
				dialog.setDelegate(HomeController.this);
				dialog.useDefaultValues();
				dialog.show();
			}
		});

		frame.setJMenuBar(menuBar);
	}
	
	public void insertRoom() {
		String name = JOptionPane.showInputDialog("Room Name");
	    if (name != null && !name.equals("")) {
	    	RoomController room = new RoomController(name);
	    	rooms.add(room);
			tabbedPane.addTab(name, room.getPanel());
	    }
	}
	
	public void initiliazeConnection(SocketClientOptions options) {
		service = new SocketService(options);
		service.setDelegate(this);
		service.start();
		
		service.connect();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void postInputEquipamentDialog(SocketClientOptions options,
			EquipamentType type, String name) {
		if(rooms.size() > 0) {
    		RoomController room = rooms.get(tabbedPane.getSelectedIndex());
    		
    		EquipamentViewController<? extends Equipament> controller = 
    				EquipamentViewControllerUtil.getControllerByEquipamentType(type, name);
    		controller.setDelegate(this);
    		
    		equipaments.put(options.getTargetName(), controller);
    		
    		room.getEquipaments().add(controller);
    		
    		room.getPanel().setSize(800, 600);
    		room.getPanel().add(controller.getFrame());
    		
    		room.getPanel().repaint();
    		
    		controller.initiliazeConnection(options);
    	}
	}

	@Override
	public void postConfigControllerDialog(SocketClientOptions options) {
		this.options = options;
		initiliazeConnection(options);
	}

	@Override
	public void postReceivedMessage(String message) {
		System.out.println("Control: " + message);
		GatewayMessage msg = (GatewayMessage) JsonConverter.objectFromJson(message, GatewayMessage.class);
		if(msg.getType() == GatewayMessageType.MESSAGE) {
			EquipamentViewController<? extends Equipament> controller = equipaments.get(msg.getClientInfo().getKey());
			if(controller != null) {
				//Equipament equipament = (Equipament) JsonConverter.objectFromJson(msg.getContent(), Equipament.class);
				//controller.getOutput().updateOutput(equipament);
				controller.postReceivedMessage(msg.getContent());
			}
		}
	}

	@Override
	public void sendMessage(String target, String message) {
		if(service != null)
			service.sendMessage(target, message);
	}
}
