package com.tcl.wonder.adclient.view.tabpanel;

import javax.swing.JPanel;

/**
 * 所有Tabbed面板基类
 * @author liuran
 * 2015年1月23日
 *
 */
public abstract class TabPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public abstract void refresh();
}
