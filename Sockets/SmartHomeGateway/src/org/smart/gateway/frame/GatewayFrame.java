package org.smart.gateway.frame;

import java.awt.Color;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.smart.gateway.tablemodel.GatewayClientTableModel;
import org.smart.gateway.util.NetworkUtil;

public class GatewayFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel labelHostAddress, labelStatus;
	private JButton buttonStart, buttonStop;
	private JTable tableClients;
	private JScrollPane scrollPainel;
	
	private GatewayFrameDelegate delegate;
	
	public GatewayFrame(String title) throws HeadlessException {
		super(title);
	}
	
	public void loadViews() {
		loadViewTop();
		loadViewLeft();
		//loadViewRight();
		loadViewBottom();
	}
	
	private void loadViewTop() {
		JPanel panelTop = new JPanel();
		panelTop.setBounds(0, 0, 640, 70);
		panelTop.setLayout(null);
		panelTop.setBackground(Color.LIGHT_GRAY);
		add(panelTop);
		
		JLabel labelTitleHostAddress = new JLabel();
		labelTitleHostAddress.setBounds(10, 10, 100, 20);
		labelTitleHostAddress.setText("Host Address:");
		panelTop.add(labelTitleHostAddress);
		
		JLabel labelTitleStatus = new JLabel();
		labelTitleStatus.setBounds(10, 40, 60, 20);
		labelTitleStatus.setText("Status:");
		panelTop.add(labelTitleStatus);
		
		labelHostAddress = new JLabel();
		labelHostAddress.setBounds(120, 10, 300, 20);
		labelHostAddress.setText(NetworkUtil.getHostAddress());
		panelTop.add(labelHostAddress);
		
		labelStatus = new JLabel();
		labelStatus.setBounds(80, 40, 100, 20);
		labelStatus.setText("---");
		panelTop.add(labelStatus);
	}
	
	private void loadViewLeft() {
		JPanel panelLeft = new JPanel();
		panelLeft.setBounds(0, 70, 640, 380);
		//panelLeft.setBounds(0, 70, 500, 380);
		panelLeft.setLayout(null);
		panelLeft.setBackground(Color.GRAY);
		add(panelLeft);
		
		GatewayClientTableModel tableModel = new GatewayClientTableModel(delegate.getGatewayClients());
		
		JLabel labelTitleClients = new JLabel();
		labelTitleClients.setBounds(10, 10, 150, 20);
		labelTitleClients.setText("Connected Clients");
		panelLeft.add(labelTitleClients);
		
		tableClients = new JTable(tableModel);
		tableClients.setBounds(10, 40, 620, 330);
		//tableClients.setBounds(10, 40, 480, 330);
		tableClients.setGridColor(Color.BLACK);
		panelLeft.add(tableClients);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tableClients.setDefaultRenderer(String.class, centerRenderer);
		tableClients.setDefaultRenderer(Integer.class, centerRenderer);
		
		tableClients.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				tableClients.repaint();
			}
		});
		
		scrollPainel = new JScrollPane(tableClients);
		scrollPainel.setBounds(10, 40, 620, 330);
		//scrollPainel.setBounds(10, 40, 480, 330);
		panelLeft.add(scrollPainel);
	}
	
	private void loadViewRight() {
		JPanel panelRight = new JPanel();
		panelRight.setBounds(500, 70, 140, 380);
		panelRight.setLayout(null);
		//panelRight.setBackground(Color.RED);
		add(panelRight);
		
		buttonStart = new JButton("Start");
		buttonStart.setBounds(20, 10, 100, 20);
		panelRight.add(buttonStart);
		
		buttonStop = new JButton("Stop");
		buttonStop.setBounds(20, 40, 100, 20);
		panelRight.add(buttonStop);
	}
	
	private void loadViewBottom() {
		JPanel panelBottom = new JPanel();
		panelBottom.setBounds(0, 450, 640, 30);
		panelBottom.setLayout(null);
		panelBottom.setBackground(Color.LIGHT_GRAY);
		add(panelBottom);
	}

	public GatewayFrameDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(GatewayFrameDelegate delegate) {
		this.delegate = delegate;
	}
	
	public void update(String message) {
		labelStatus.setText(message);
		labelStatus.repaint();
		
		tableClients.repaint();
	}
	
	public void updatePresentation() {
		tableClients.setModel(new GatewayClientTableModel(delegate.getGatewayClients()));
		tableClients.repaint();
	}
}
