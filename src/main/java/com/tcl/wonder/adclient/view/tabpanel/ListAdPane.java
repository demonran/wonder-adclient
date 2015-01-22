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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.tcl.wonder.adclient.utlis.UIUtils;
import com.tcl.wonder.adclient.utlis.Utilities;
import com.tcl.wonder.adclient.view.AdFrame;
import com.tcl.wonder.adclient.view.Contents;
import com.tcl.wonder.adclient.view.component.AutoCompleteTextFiled;
import com.tcl.wonder.adclient.view.table.MyTableModel;
import com.tcl.wonder.adclient.view.worker.AdSwingWorker;
import com.tcl.wonder.adclient.view.worker.callback.CallbackAdapter;

public class ListAdPane extends TabPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final String[] columnNames = { "编号", "广告ID", "广告名称", "广告LOGO",// 列名最好用final修饰
			"广告时长", "广告信息", "更新时间" };

	private JTable table;

	PopupMenu popupMenu;
	MenuItem menuItem1;
	MenuItem menuItem2;

	private JButton refreshButton;
	
	private JButton queryButton;

	private static Logger logger = LoggerFactory.getLogger(ListAdPane.class);

	private int selectedRow;

	private AutoCompleteTextFiled idFiled;

	private AdFrame adFrame;

	Map<String, Ad> allAdsMap ;
	
	private AdService adServer = AdService.getInstance();
	
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
		table.setSelectionBackground(Color.GREEN);// 设置所选择行的背景色
		table.setSelectionForeground(Color.BLUE);// 设置所选择行的前景色

		TableColumnModel columnModel = table.getColumnModel();

		TableColumn firsetColumn = columnModel.getColumn(0);
		firsetColumn.setPreferredWidth(Contents.INT_WIGHT);
		firsetColumn.setMaxWidth(100);
		firsetColumn.setMinWidth(10);

		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		firsetColumn.setCellRenderer(render);

		TableColumn durationColumn = columnModel.getColumn(4);
		durationColumn.setPreferredWidth(Contents.INT_WIGHT);
		durationColumn.setMinWidth(10);
		durationColumn.setMaxWidth(300);
		durationColumn.setCellRenderer(render);

		popupMenu = new PopupMenu();
		menuItem1 = new MenuItem("刪除");
		menuItem2 = new MenuItem("更新");
		popupMenu.add(menuItem1);
		popupMenu.add(menuItem2);
		table.add(popupMenu);

		JPanel operationPanel = new JPanel();
		idFiled = new AutoCompleteTextFiled();
		idFiled.setColumns(100);
		idFiled.setBounds(0, 0, 50, 20);

		queryButton = new JButton("查找");
		queryButton.setIcon(UIUtils.getImageIcon("search.gif"));
		queryButton.setContentAreaFilled(false);
		queryButton.setBounds(50, 0, 30, 10);
		refreshButton = new JButton("刷新");
		refreshButton.setIcon(UIUtils.getImageIcon("refresh.gif"));
//		refreshButton.setBorderPainted(false);
//		refreshButton.setForeground(new Color(51, 154, 47));
//		refreshButton.setFocusPainted(false);
		refreshButton.setContentAreaFilled(false);
		queryButton.setBounds(80, 0, 30, 10);

		operationPanel.add(idFiled);
		operationPanel.add(queryButton);
		operationPanel.add(refreshButton);

		this.setLayout(new BorderLayout());
		this.setOpaque(true);
		this.add(operationPanel, BorderLayout.NORTH);

		JScrollPane pane = new JScrollPane(table);

		this.add(pane, BorderLayout.CENTER);

		addEvent();
	}

	private void initData()
	{
		adFrame.startInfiniteProgress();
		AdSwingWorker worker = new AdSwingWorker(new CallbackAdapter()
		{

			@Override
			public void call(Map<String, Ad> adsMap)
			{
				allAdsMap = adsMap;
				fillTable(allAdsMap);
				idFiled.setupAutoCompleteItem(adsMap.keySet());
				adFrame.stopInfiniteProgress();
			}
		});
		worker.execute();
	}

	private void addEvent()
	{

		// 悬浮提示单元格的值
		table.addMouseMotionListener(new MouseAdapter()
		{
			public void mouseMoved(MouseEvent e)
			{
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (row > -1 && col > -1)
				{
					table.setRowSelectionInterval(row, row);
					Object value = table.getValueAt(row, col);
					if (null != value && !"".equals(value))
						table.setToolTipText(value.toString());// 悬浮显示单元格内容
					else
						table.setToolTipText(null);// 关闭提示
				}
			}
		});

		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				selectedRow = table.rowAtPoint(e.getPoint());
				logger.debug("selectedRow = {}", selectedRow);

				int bindex = e.getButton();
				if (bindex == MouseEvent.BUTTON3)
				{
					popupMenu.show(table, e.getX(), e.getY());
				}
				super.mousePressed(e);
			}

		});

		menuItem1.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				MyTableModel tableModel = (MyTableModel) table.getModel();
				Object adName = table.getValueAt(selectedRow, 2);
				int option = JOptionPane.showConfirmDialog(adFrame,
						UIUtils.convertHtml(String.format("确认删除广告(%s)信息吗？", adName)), "删除",
						JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION)
				{
					String id = (String)table.getValueAt(selectedRow, 1);
					adServer.deleteAdById(id);
					allAdsMap.remove(id);
					tableModel.remove(selectedRow);
					
				}

			}

		});
		menuItem2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				Ad ad = getAdByTablerow(selectedRow);
				adFrame.getUpdateAdPane().setAd(ad);
				adFrame.setSelectTab(3);
			}
		});

		refreshButton.addMouseListener(new MouseAdapter()
		{	
			@Override
			public void mouseExited(MouseEvent e)
			{
//				refreshButton.setContentAreaFilled(false);
//				refreshButton.setBackground(null);
				refreshButton.setIcon(UIUtils.getImageIcon("refresh.gif"));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
//				refreshButton.setBackground(Color.BLUE);
				refreshButton.setIcon(UIUtils.getImageIcon("refresh.gif",15,15));
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				initData();
			}
		});
		
		queryButton.addMouseListener(new MouseAdapter()
		{
			
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				queryButton.setIcon(UIUtils.getImageIcon("search.gif"));
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				queryButton.setIcon(UIUtils.getImageIcon("search.gif",15,15));
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(allAdsMap == null)
				{
					return;
				}
				queryButton.setIcon(UIUtils.getImageIcon("searchcluster.gif"));
				List<Ad> queryList = new ArrayList<Ad>();
				queryList.clear();
				
				String queryId = idFiled.getText();
				
				for(String id:allAdsMap.keySet())
				{
					if(id.startsWith(queryId))
					{
						if(id.equals(queryId))
						{
							queryList.add(0, allAdsMap.get(id));
						}else
						{
							queryList.add(allAdsMap.get(id));
						}
					}
				}
				logger.info("query list size : {}, allAdMap size :{} " ,queryList.size(),allAdsMap.size());
				fillTable(queryList);
				queryButton.setIcon(UIUtils.getImageIcon("search.gif"));
			}
		});
	}

	public void fillTable(List<Ad> ads)
	{
		MyTableModel tableModel = (MyTableModel) table.getModel();
		tableModel.setRowCount(0);// 清除原有行

		// 填充数据
		for (int i = 0; i < ads.size(); i++)
		{
			List<Object> rowList = new ArrayList<Object>();
			Ad ad = ads.get(i);
			rowList.add(i + 1);
			rowList.add(ad.getId().trim());
			rowList.add(ad.getName().trim());
			rowList.add(ad.getLogo().trim());
			rowList.add(ad.getDuration());
			rowList.add(ad.getInfo().trim());
			rowList.add(ad.getUpdatetime());
			// 添加数据到表格
			tableModel.addRow(rowList);
		}

		// 更新表格
		table.updateUI();
	}

	private void fillTable(Map<String, Ad> adsMap)
	{
		if(adsMap == null)
		{
			return;
		}
		MyTableModel tableModel = (MyTableModel) table.getModel();
		tableModel.setRowCount(0);// 清除原有行

		// 填充数据
		int i = 1;// 计数
		for (Entry<String, Ad> entry : adsMap.entrySet())
		{
			List<Object> rowList = new ArrayList<Object>();
			Ad ad = entry.getValue();
			rowList.add(i++);
			rowList.add(ad.getId().trim());
			rowList.add(ad.getName().trim());
			rowList.add(ad.getLogo().trim());
			rowList.add(ad.getDuration());
			rowList.add(ad.getInfo().trim());
			rowList.add(Utilities.format(ad.getUpdatetime()));
			// 添加数据到表格
			tableModel.addRow(rowList);
		}

		// 更新表格
		table.updateUI();
	}

	private Ad getAdByTablerow(int row)
	{
		String id = (String) table.getValueAt(selectedRow, 1);
		if (id == null || id.isEmpty())
		{
			return null;
		}
		String name = (String) table.getValueAt(selectedRow, 2);
		String logo = (String) table.getValueAt(selectedRow, 3);
		int duration = (int) table.getValueAt(selectedRow, 4);
		String info = (String) table.getValueAt(selectedRow, 5);
		Date updatetime = Utilities.parse((String) table.getValueAt(selectedRow, 6));

		Ad ad = new Ad(id, name, logo, duration, info, updatetime);
		return ad;
	}

	@Override
	public void refresh()
	{
//		initData();
	}

}
