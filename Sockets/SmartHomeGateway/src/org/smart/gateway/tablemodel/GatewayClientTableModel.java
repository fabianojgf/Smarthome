package org.smart.gateway.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.smart.gateway.model.GatewayClientInfo;

public class GatewayClientTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int COL_HOST = 0;
	private static final int COL_PORT = 1;
	private static final int COL_KEY = 2;
	
	private List<GatewayClientInfo> linhas;
	private String[] colunas = new String[]{"Host", "Port", "Key"};
	
	public GatewayClientTableModel(List<GatewayClientInfo> clients) {
		super();
		this.linhas = clients;
	}
	
	@Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return linhas.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colunas.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		GatewayClientInfo client = linhas.get(rowIndex);
		if (columnIndex == COL_HOST) {
			return client.getHost();
		} else if (columnIndex == COL_PORT) {
			return client.getPort();
		} else if (columnIndex == COL_KEY) {
			return client.getKey();
		} 
		return "";
	}
	
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        GatewayClientInfo gatewayClient = this.linhas.get(rowIndex);

        switch (columnIndex) {
            case COL_HOST:
            	gatewayClient.setHost(String.valueOf(aValue));
                break;
            case COL_PORT:
            	gatewayClient.setPort((Integer) aValue);
                break;
            case COL_KEY:
            	gatewayClient.setKey((String) aValue);
                break;
        }
        fireTableDataChanged();
    }
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return colunas[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
        case COL_HOST:
            return String.class;
        case COL_PORT:
            return Integer.class;
        case COL_KEY:
            return String.class;
        default:
            return String.class;
		}
	}

	public List<GatewayClientInfo> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<GatewayClientInfo> linhas) {
		this.linhas = linhas;
	}
}
