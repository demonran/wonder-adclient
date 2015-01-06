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
	
	final String[] columnNames = {"���", "id", "name", "logo",// ���������final����
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

		table.setPreferredScrollableViewportSize(new Dimension(600, 100));// ���ñ��Ĵ�С
		table.setRowHeight(30);// ����ÿ�еĸ߶�Ϊ20
		table.setRowMargin(5);// �����������е�Ԫ��ľ���
		table.setRowSelectionAllowed(true);// ���ÿɷ�ѡ��.Ĭ��Ϊfalse
		table.setGridColor(Color.black);// ���������ߵ���ɫ
		table.setBackground(Color.lightGray);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(Color.GREEN);// ������ѡ���еı���ɫ
		table.setSelectionForeground(Color.BLUE);// ������ѡ���е�ǰ��ɫ
		
		TableColumnModel columnModel = table.getColumnModel();
		
		TableColumn firsetColumn = columnModel.getColumn(0);
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(100);
		firsetColumn.setMinWidth(10);
		
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	    render.setHorizontalAlignment(SwingConstants.CENTER);
	    firsetColumn.setCellRenderer(render);
		
		popupMenu = new PopupMenu();
		menuItem1 = new MenuItem("�h��");
		menuItem2 = new MenuItem("����");
		popupMenu.add(menuItem1);
		popupMenu.add(menuItem2);
		table.add(popupMenu);
		
		JPanel operationPanel = new JPanel();
		selectFiled = new AutoCompleteTextFiled();
		selectFiled.setColumns(100);
		
		JButton selelctButton = new JButton("��ѯ");
		refreshButton = new JButton("ˢ��");
		
		operationPanel.add(selectFiled);
		operationPanel.add(selelctButton);
		operationPanel.add(refreshButton);
		
		
		
		this.setLayout(new BorderLayout()); 
		this.setOpaque(true);
		this.add(operationPanel,BorderLayout.NORTH);

		JScrollPane pane = new JScrollPane(table);

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
		
		//������ʾ��Ԫ���ֵ 
		table.addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent e) {
				int row=table.rowAtPoint(e.getPoint());
				int col=table.columnAtPoint(e.getPoint());
				if(row>-1 && col>-1){
					table.setRowSelectionInterval(row, row);
					Object value=table.getValueAt(row, col);
					if(null!=value && !"".equals(value))
						table.setToolTipText(value.toString());//������ʾ��Ԫ������
					else
						table.setToolTipText(null);//�ر���ʾ
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
				int option = JOptionPane.showConfirmDialog(table, String.format("ȷ��ɾ�����(%s)��Ϣ��",adName), "ɾ��", JOptionPane.OK_CANCEL_OPTION);
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
		tableModel.setRowCount(0);// ���ԭ����

		// �������
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
			// ������ݵ����
			tableModel.addRow(rowList);
		}

		// ���±��
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
