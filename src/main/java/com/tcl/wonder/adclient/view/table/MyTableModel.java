package com.tcl.wonder.adclient.view.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyTableModel extends  AbstractTableModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static Logger logger = LoggerFactory.getLogger(MyTableModel.class);
	
	 //��Ԫ��Ԫ������  
    private Class<?> []cellType={JLabel.class,String.class,String.class,String.class,Integer.class,String.class,String.class};  
    //��ͷ  
    private String[] columnNames;  
    //ģ������  
    private List<List<Object>> data;  
    
    public MyTableModel(String[] columnNames)
	{
		data = new ArrayList<List<Object>>();
		this.columnNames = columnNames;
	}

    public MyTableModel(String[] columnNames, List<List<Object>> data)
	{
		super();
		this.columnNames = columnNames;
		this.data = data;
	}
	public MyTableModel(Object[][] data,String[] columnNames){  
		this(columnNames, convertToList(data));
    }  
    @Override  
    public Class<?> getColumnClass(int columnIndex) {  
        return cellType[columnIndex];  
    }  
    @Override  
    public String getColumnName(int columnIndex) {  
        return columnNames[columnIndex];  
    }  
    @Override  
    public int getColumnCount() {  
        return columnNames.length;  
    }  
    @Override  
    public int getRowCount() {  
        return data.size();  
    }  
    @Override  
    public Object getValueAt(int row, int column) {  
    	 List<Object> rowList = data.get(row);
         return rowList.get(column);
    }  
      //��дisCellEditable����  
    public boolean isCellEditable(int r,int c)  
    {  
        return true;  
    }  
    //��дsetValueAt����  
    public void setValueAt(Object value,int row,int column)  
    {  
    	List<Object> rowList = data.get(row);
    	rowList.set(column,value );
        fireTableCellUpdated(row, column);
    }
	public List<List<Object>> getData()
	{
		return data;
	}
	public void setData(List<List<Object>> data)
	{
		this.data = data;
	}
	public void setRowCount(int i)
	{
		data.clear();
	} 
	
	public void addRow(List<Object> list)
	{
		data.add(list);
		fireTableRowsInserted(data.size(),data.size());
	}
	
	public void remove(int row)
	{
		data.remove(row);
		logger.debug("remove row  {}",row);
		fireTableRowsInserted(data.size(),data.size());
	}
	
	
	private static List<Object> convertToList(Object[] anArray)
	{
		 if (anArray == null) {
	            return null;
	        }
		 List<Object> list = new ArrayList<Object>(anArray.length);
	        for (Object o : anArray) {
	        	list.add(o);
	        }
	        return list;
	}
	
	 private static List<List<Object>> convertToList(Object[][] anArray) {
        if (anArray == null) {
            return null;
        }
        List<List<Object>> list = new ArrayList<List<Object>>(anArray.length);
        for (Object[] o : anArray) {
        	list.add(convertToList(o));
        }
        return list;
    }
	

}
