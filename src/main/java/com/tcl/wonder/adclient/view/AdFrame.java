package com.tcl.wonder.adclient.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.view.component.InfiniteProgressPanel;
import com.tcl.wonder.adclient.view.tabpanel.BatchInsertPane;
import com.tcl.wonder.adclient.view.tabpanel.InsertAdPane;
import com.tcl.wonder.adclient.view.tabpanel.ListAdPane;
import com.tcl.wonder.adclient.view.tabpanel.TabPanel;
import com.tcl.wonder.adclient.view.tabpanel.UpdateAdPane;

public class AdFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UpdateAdPane updateAdPane;

	private JTabbedPane tab;

	private InfiniteProgressPanel glassPane;
	
	private List<TabPanel> tabs = new ArrayList<TabPanel>(4);
	
	private static Logger logger = LoggerFactory.getLogger(AdFrame.class);

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
		setPreferredSize(new Dimension(800, 600));
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		 pack();
		layoutUI();
		this.setFont(new Font("楷体", Font.PLAIN, 12));

	}

	private void layoutUI()
	{
		glassPane = new InfiniteProgressPanel("数据更新中......");
		setGlassPane(glassPane);
		// 对象实例化
		tab = new JTabbedPane(JTabbedPane.TOP);
		// 对象化面板
		JPanel combop = new JPanel();
		TabPanel p1 = new ListAdPane(this);
		TabPanel p2 = new InsertAdPane(this);
		TabPanel p3 = new BatchInsertPane();
		updateAdPane = new UpdateAdPane(this);

		tab.add(p1, "Select");
		tab.add(p2, "Insert");
		tab.add(p3, "BatchInsert");
		tab.add(updateAdPane, "Updata");
		
		tabs.add(p1);
		tabs.add(p2);
		tabs.add(p3);
		tabs.add(updateAdPane);

		JLabel title = new JLabel("广告信息系统");
		title.setFont(new Font("楷体", Font.BOLD, 24));
		combop.add(title);

		this.setLayout(new BorderLayout());
		this.add(combop, BorderLayout.NORTH);
		this.add(tab, BorderLayout.CENTER);
		
		tab.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				logger.debug("---setSelectTab---");
				tabs.get(tab.getSelectedIndex()).refresh();
			}
		});
	}

	public void setSelectTab(int index)
	{
		tab.setSelectedIndex(index);
		
	}

	public UpdateAdPane getUpdateAdPane()
	{
		return updateAdPane;
	}

	
	public void startInfiniteProgress()
	{
		glassPane.start();
	}
	
	public void stopInfiniteProgress()
	{
		glassPane.stop();
	}
	
	//初始化刷新数据
	public void init()
	{
		tabs.get(0).refresh();
	}

	public static void main(String[] args)
	{
		AdFrame f = new AdFrame();
		f.setTitle("Wonder Advertisement Client");
		f.setVisible(true);
		f.init();
	}
}
