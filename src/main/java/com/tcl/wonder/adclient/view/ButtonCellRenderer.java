package com.tcl.wonder.adclient.view;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonCellRenderer implements TableCellRenderer
{
	private static Logger logger = LoggerFactory.getLogger(ButtonCellRenderer.class);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column)
	{

		logger.debug("isSelected ={} ,hasFocus={}",isSelected,hasFocus);
		logger.debug(row + "");


		return new JButton(value.toString());
	}
	
	

}
