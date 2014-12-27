package com.tcl.wonder.adclient.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AdFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AdFrame()
	{
		 try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         JFrame.setDefaultLookAndFeelDecorated(true);  
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		
		layoutUI();
		
		setVisible(true);
	}
	
	 private void layoutUI(){

		  //对象实例化
		  JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP); 
		  //容器
		  Container container = this.getLayeredPane();
		  //对象化面板
		  JPanel combop = new JPanel();
		  JPanel p1 = new ListAdPane();
		  JPanel p2 = new AddAdPane();
		  JPanel p3 = new JPanel();
		  JPanel p4 = new JPanel();


		  tab.add(p1,"Select");
		  tab.add(p2,"Updata");
		  tab.add(p3,"Inserte");
		  tab.add(p4,"Delete");
		  
		  combop.add(new JLabel("广告信息系统"));
		  
		  container.setLayout(new BorderLayout());
		  container.add(combop,BorderLayout.NORTH);
		  container.add(tab,BorderLayout.CENTER);
	 }
	 
	 public static void main(String[] args)
	{
		new AdFrame();
	}
}
