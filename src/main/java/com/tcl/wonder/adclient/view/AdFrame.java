package com.tcl.wonder.adclient.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.tcl.wonder.adclient.view.tabpanel.BatchInsertPane;
import com.tcl.wonder.adclient.view.tabpanel.InsertAdPane;
import com.tcl.wonder.adclient.view.tabpanel.ListAdPane;
import com.tcl.wonder.adclient.view.tabpanel.UpdateAdPane;

public class AdFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UpdateAdPane updateAdPane;
	
	private JTabbedPane tab;
	
	public AdFrame()
	{
		 try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}  
         JFrame.setDefaultLookAndFeelDecorated(true);  
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		
		layoutUI();
		
		setVisible(true);
		setGlassPane(new InfiniteProgressPanel());
	}
	
	 private void layoutUI(){

		  //对象实例化
		  tab = new JTabbedPane(JTabbedPane.TOP); 
		  //容器
		  Container container = this.getLayeredPane();
		  //对象化面板
		  JPanel combop = new JPanel();
		  JPanel p1 = new ListAdPane(this);
		  JPanel p2 = new InsertAdPane();
		  JPanel p3 = new BatchInsertPane();
		  updateAdPane = new UpdateAdPane();


		  tab.add(p1,"Select");
		  tab.add(p2,"Insert");
		  tab.add(p3,"BatchInsert");
		  tab.add(updateAdPane,"Updata");
		  
		  combop.add(new JLabel("广告信息系统"));
		  
		  container.setLayout(new BorderLayout());
		  container.add(combop,BorderLayout.NORTH);
		  container.add(tab,BorderLayout.CENTER);
	 }
	 
	 public void setSelectTab(int index)
	 {
		 tab.setSelectedIndex(index);
	 }
	 
	 public UpdateAdPane getUpdateAdPane()
	 {
		 return updateAdPane;
	 }
	 
	 public static void main(String[] args)
	{
		new AdFrame();
	}
}
