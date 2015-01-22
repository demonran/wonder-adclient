package com.tcl.wonder.adclient.view.table;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Video.Status;
import com.tcl.wonder.adclient.utlis.UIUtils;

public class ButtonCellEidtor extends AbstractCellEditor  implements TableCellEditor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(ButtonCellEidtor.class);
	
	private JLabel label;
	
	public ButtonCellEidtor()
	{
		label = new JLabel();
	}
	


	@Override
	public Object getCellEditorValue()
	{
		return Status.STARTED;
	}


	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
			int row, int column)
	{
		
		ImageIcon icon = null;
		Status status = (Status)value;
		logger.debug(status.toString());
//		if(status == Status.NO_START)
//		{
//			button.addMouseListener(new MouseAdapter()
//			{
//				public void mouseClicked(MouseEvent e) {
//					logger.debug("clicked...");
//				}
//			});
//		}
		switch (status)
		{
			case NO_START:
				icon = UIUtils.getImageIcon("play.png",20,20);
//				label.addMouseListener(new MouseAdapter()
//				{
//					public void mouseClicked(MouseEvent e) {
//						logger.debug("clicked...");
//					}
//				});
				break;
	
			default:
				break;
		}
		label.setIcon(icon);
//		return this;
//		button.setText(value.toString());
//		MyTableModel tableModel = (MyTableModel) table.getModel();
//		logger.info("delete table row :{}" , row);
//		tableModel.remove(row);
		return label;
	}


}
