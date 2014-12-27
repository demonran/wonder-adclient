package com.tcl.wonder.adclient.utlis;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UIUtils
{
	public static GridBagConstraints getGridBagConstraints(int gridx,int gridy,int gridwidth,int gridheight,double weightx,double weighty)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 5, 5, 10);
		return c;
	}
	
	public static GridBagConstraints getGridBagConstraints(int gridwidth,int gridheight,double weightx,double weighty)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 5, 5, 10);
		return c;
	}
	
	public static GridBagConstraints getGridBagConstraints(int gridwidth,double weightx,double weighty)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = gridwidth;
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 5, 5, 10);
		return c;
	}
}
