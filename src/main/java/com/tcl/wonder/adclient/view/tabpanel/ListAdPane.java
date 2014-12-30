package com.tcl.wonder.adclient.view.tabpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.service.AdService;
import com.tcl.wonder.adclient.view.AdFrame;
import com.tcl.wonder.adclient.view.jfiled.AutoCompleteTextFiled;

public class ListAdPane extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final String[] columnNames = {"编号", "id", "name", "logo",// 列名最好用final修饰
			"duration", "info", "videoname"};

	private JTable table;
	
	PopupMenu popupMenu;
	MenuItem menuItem1;
	MenuItem menuItem2 ;
	
	private JButton refreshButton;

	private static Logger logger = LoggerFactory.getLogger(ListAdPane.class);

	private AdService adServer = new AdService();

	private int selectedRow;
	
	private AutoCompleteTextFiled selectFiled;
	
	private AdFrame adFrame;
	
	public ListAdPane(AdFrame adFrame)
	{
		this.adFrame = adFrame;
		layoutUI();
	}

	private void layoutUI()
	{
		MyTableModel model = new MyTableModel(columnNames);
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(600, 100));// 设置表格的大小
		table.setRowHeight(30);// 设置每行的高度为20
		table.setRowMargin(5);// 设置相邻两行单元格的距离
		table.setRowSelectionAllowed(true);// 设置可否被选择.默认为false
		table.setGridColor(Color.black);// 设置网格线的颜色
		table.setBackground(Color.lightGray);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		TableColumnModel columnModel = table.getColumnModel();
		
		TableColumn firsetColumn = columnModel.getColumn(0);
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(100);
		firsetColumn.setMinWidth(10);
		
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	    render.setHorizontalAlignment(SwingConstants.CENTER);
	    firsetColumn.setCellRenderer(render);
		
		popupMenu = new PopupMenu();
		menuItem1 = new MenuItem("刪除");
		menuItem2 = new MenuItem("更新");
		popupMenu.add(menuItem1);
		popupMenu.add(menuItem2);
		table.add(popupMenu);
		
		JPanel operationPanel = new JPanel();
		selectFiled = new AutoCompleteTextFiled();
		selectFiled.setColumns(100);
		
		JButton selelctButton = new JButton("查询");
		refreshButton = new JButton("刷新");
		
		operationPanel.add(selectFiled);
		operationPanel.add(selelctButton);
		operationPanel.add(refreshButton);
//		operationPanel.setLayout(null);
		
		
		
		this.setLayout(new BorderLayout()); 
		this.setOpaque(true);
		this.add(operationPanel,BorderLayout.NORTH);
//		operationPanel.add(addButton);
//		operationPanel.add(resetButton);

		JScrollPane pane = new JScrollPane(table);

//		this.setPreferredSize(null);
//		this.setBackground(Color.black);
		this.add(pane,BorderLayout.CENTER);

		initData();
		addEvent();
	}
	
	private void initData()
	{
		uploadTable();
		
		selectFiled.setupAutoCompleteItem(adServer.getAllAdId());
	}
	
	private void uploadTable()
	{
		List<Ad> ads = adServer.listAd();
		fillTable(ads);
	}
	
	private void addEvent()
	{
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				selectedRow = table.rowAtPoint(e.getPoint());
				logger.debug("selectedRow = {}", selectedRow);
				
				table.setSelectionBackground(Color.white);// 设置所选择行的背景色
				table.setSelectionForeground(Color.red);// 设置所选择行的前景色

				int bindex = e.getButton();
				if (bindex == MouseEvent.BUTTON3)
				{
					popupMenu.show(table, e.getX(), e.getY());
				}
				super.mousePressed(e);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				super.mouseEntered(e);
			}

		});

		menuItem1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				MyTableModel tableModel = (MyTableModel) table.getModel();
				Object adName = table.getValueAt(selectedRow, 2);
				int option = JOptionPane.showConfirmDialog(table, String.format("确认删除广告(%s)信息吗？",adName), "删除", JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION)
				{
					tableModel.remove(selectedRow);
				}

			}

		});
		menuItem2.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Ad ad =  getAdByTablerow(selectedRow);
				adFrame.getUpdateAdPane().update(ad);
				adFrame.setSelectTab(3);
			}
		});
		
		refreshButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				uploadTable();
			}
		});
	}

	public void fillTable(List<Ad> ads)
	{
		MyTableModel tableModel = (MyTableModel) table.getModel();
		tableModel.setRowCount(0);// 清除原有行

		// 填充数据
		for (int i =0; i < ads.size();i++)
		{
			List<Object> rowList = new ArrayList<Object>();
			Ad ad = ads.get(i);
			rowList.add(i+1);
			rowList.add(ad.getId());
			rowList.add(ad.getName());
			rowList.add(ad.getLogo());
			rowList.add(ad.getDuration());
			rowList.add(ad.getVideoname());
			rowList.add(ad.getInfo());
			// 添加数据到表格
			tableModel.addRow(rowList);
		}

		// 更新表格
		table.invalidate();
	}
	
	private Ad getAdByTablerow(int row)
	{
		String id = (String)table.getValueAt(selectedRow, 1);
		if(id == null || id.isEmpty())
		{
			return null;
		}
		String name = (String)table.getValueAt(selectedRow, 2);
		String logo = (String)table.getValueAt(selectedRow, 3);
		int duration = (int)table.getValueAt(selectedRow, 4);
		String info = (String)table.getValueAt(selectedRow, 5);
		String videoname = (String)table.getValueAt(selectedRow, 6);
		
		Ad ad = new Ad(id, name, logo, duration, info, videoname);
		return ad;
	}

}
