package org.smart.home.viewcontroller;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.smart.home.dialog.InputEquipamentDialog;
import org.smart.home.dialog.InputEquipamentDialogDelegate;
import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.model.enums.EquipamentType;
import org.smart.home.equipament.viewcontroller.AirConditioningViewController;
import org.smart.home.equipament.viewcontroller.EquipamentViewController;
import org.smart.home.equipament.viewcontroller.util.EquipamentViewControllerUtil;
import org.smart.mqtt.client.SmartMqttClientOptions;

public class HomeController implements InputEquipamentDialogDelegate {
	private List<RoomController> rooms;
	private JFrame frame;
	private JTabbedPane tabbedPane;
	private int selectedTab = -1;
	
	public HomeController(String name) {
		super();
		
		rooms = new ArrayList<RoomController>();
		
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
		
		JMenuItem menuItem = new JMenuItem("New Room", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem);
		
		JMenuItem menuItem2 = new JMenuItem("New Equipament", KeyEvent.VK_N);
		menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem2.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem2);
		
		menuItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				insertRoom();
			}
		});
		
		menuItem2.addActionListener(new ActionListener() {	
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

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void postInputEquipamentDialog(SmartMqttClientOptions options,
			EquipamentType type, String name) {
		if(rooms.size() > 0) {
    		RoomController room = rooms.get(tabbedPane.getSelectedIndex());
    		
    		EquipamentViewController<? extends Equipament> controller = 
    				EquipamentViewControllerUtil.getControllerByEquipamentType(type, name);
    		room.getEquipaments().add(controller);
    		
    		room.getPanel().setSize(800, 600);
    		room.getPanel().add(controller.getFrame());
    		
    		room.getPanel().repaint();
    		
    		controller.initiliazeConnection(options.getBrokerUri(), options.getClientId(), 
    				options.getTopicDomain(), options.getTopicTargetId(), null, null);
    	}
	}
}
