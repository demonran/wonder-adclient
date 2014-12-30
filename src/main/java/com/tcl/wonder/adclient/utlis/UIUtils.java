package com.tcl.wonder.adclient.utlis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UIUtils
{
	
	
    //上文
    private final String strHtmlBegin = 
           "<html>"
              + "<bgcolor color=#000080>"
                  +"<font color=#0000cd >"
                  +"<b>";
    //下文
    private final String strHtmlEnd = 
                  "</b>"
                 +"</font>"
             +"</bgcolor>"
          +"</html>";
    

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
