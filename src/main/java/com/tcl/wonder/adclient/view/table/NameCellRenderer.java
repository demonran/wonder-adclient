package com.tcl.wonder.adclient.view.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.utlis.Utilities;

public class NameCellRenderer extends DefaultTableCellRenderer 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(NameCellRenderer.class);
	
	
	
	public NameCellRenderer()
	{
		logger.info("NameCellRenderer initialized...");
	}
	
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column)
	{
//		logger.debug("status: {} " ,value);
		String name = (String)value;
		if(Utilities.isStringEmpty(name))
		{
			setBackground(Color.yellow);
		}
		return this;
	}
	
//    public void updateUI() {
//        super.updateUI();
//        setForeground(null);
//        setBackground(null);
//    }


}
