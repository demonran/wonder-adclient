package com.tcl.wonder.adclient.view.table;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Video.Status;
import com.tcl.wonder.adclient.utlis.UIUtils;

public class StatusCellRenderer extends DefaultTableCellRenderer 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(StatusCellRenderer.class);
	
	
	
	public StatusCellRenderer()
	{
		logger.info("StatusCellRenderer initialized...");
	}
	
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column)
	{
//		logger.debug("status: {} " ,value);
		ImageIcon icon = null;
		Status status = (Status)value;
		switch (status)
		{
			case NO_START:
				icon = UIUtils.getImageIcon("play.png",20,20);
				this.addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e) {
						logger.debug("clicked...");
					}
				});
				break;
			case STARTED:
				icon =UIUtils.getImageIcon("started.gif");
				StatusImageObserver imageObserver = new StatusImageObserver();
				imageObserver.setProperties(table, row, column);
				icon.setImageObserver(imageObserver);
				break;
			case SUCCESS:
				icon = UIUtils.getImageIcon("success.jpg",20,20);
				break;
			case FAILED:
				icon = UIUtils.getImageIcon("failed.jpg",20,20);
				break;
	
			default:
				break;
		}
		this.setIcon(icon);
		return this;
	}
	
//    public void updateUI() {
//        super.updateUI();
//        setForeground(null);
//        setBackground(null);
//    }


}
