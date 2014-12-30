package com.tcl.wonder.adclient.view.tabpanel;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonCellEidtor extends AbstractCellEditor  implements TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(ButtonCellEidtor.class);
	
	private JButton button = new JButton();;
	


	@Override
	public Object getCellEditorValue()
	{
		return button;
	}


	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			int row, int column)
	{
		button.setText(value.toString());
		MyTableModel tableModel = (MyTableModel) table.getModel();
		logger.info("delete table row :{}" , row);
		tableModel.remove(row);
		return button;
	}


}
