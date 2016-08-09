package com.kingshine.layout.table;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CheckTableModle extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;

	public CheckTableModle(){
		
	}
	public CheckTableModle(Object[][] data, Object[] columnNames) {
		super(data,columnNames);
	}
    public CheckTableModle(Vector<Vector<Object>> data, Vector<String> columnNames) {
        super(data, columnNames);
    }

    // /**
    // * 根据类型返回显示空间
    // * 布尔类型返回显示checkbox
    // */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public void selectAllOrNull(boolean value) {
        for (int i = 0; i < getRowCount(); i++) {
            this.setValueAt(value, i, 0);
        }
    }
	@Override
	public boolean isCellEditable(int row, int column) { 
		if(column==0){
			return true;
		}
	    return false;
	}

}