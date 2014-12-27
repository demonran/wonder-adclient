package com.tcl.wonder.adclient.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.service.AdService;

public class ListAdPane extends JPanel
{
	 final String[] columnNames = {"id", "name", "logo",//���������final����
			   "duration", "info", "videoname",""};

	private JTable table;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(ListAdPane.class);
	
	private AdService adServer = new AdService();
	
	private int selectedRow;
	
	public ListAdPane()
	{
		layoutUI();
	}

	private void layoutUI()
	{
		  Object[][] rowData = new Object[6][];
		  
		  MyTableModel model = new MyTableModel(rowData, columnNames);
		  table = new JTable(model);  
		  
		  table.setPreferredScrollableViewportSize(new Dimension(600, 100));//���ñ��Ĵ�С
		  table.setRowHeight (30);//����ÿ�еĸ߶�Ϊ20
		  table.setRowMargin (5);//�����������е�Ԫ��ľ���
		  table.setRowSelectionAllowed (true);//���ÿɷ�ѡ��.Ĭ��Ϊfalse
		  table.setSelectionBackground (Color.white);//������ѡ���еı���ɫ
		  table.setSelectionForeground (Color.red);//������ѡ���е�ǰ��ɫ
		  table.setGridColor (Color.black);//���������ߵ���ɫ
		  table.setBackground (Color.lightGray);
		  table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		  
		  
		  final PopupMenu popupMenu1 = new PopupMenu();
		  MenuItem menuItem1 = new MenuItem("�h��");
		  MenuItem menuItem2 = new MenuItem("����");
		  MenuItem menuItem3 = new MenuItem();
		  popupMenu1.add(menuItem1);
		  popupMenu1.add(menuItem2);
		  popupMenu1.add(menuItem3);
		  table.add(popupMenu1);
		  
		  
		  table.addMouseListener(new MouseAdapter(){

			@Override
			public void mousePressed(MouseEvent e)
			{
				selectedRow = table.rowAtPoint(e.getPoint());
				logger.debug("selectedRow = {}",selectedRow);
				 
				
				int bindex = e.getButton();
				if(bindex ==  MouseEvent.BUTTON3)
				{
					popupMenu1.show(table, e.getX(), e.getY());
				}
				super.mousePressed(e);
			}

		  });
		  
		  
		  menuItem1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				MyTableModel tableModel = (MyTableModel) table.getModel();
				tableModel.remove(selectedRow);
				
			}

			  
		  });
		  
		  TableColumnModel columnModel = table.getColumnModel();  
	        columnModel.getColumn(6).setCellRenderer( new ButtonCellRenderer() );  
//	        columnModel.getColumn(6).setCellEditor( new ButtonCellEidtor() );  
		  
		  JScrollPane pane = new JScrollPane (table);

		  this.setLayout(new GridLayout (0, 1));
		  this.setPreferredSize (null);
		  this.setBackground (Color.black);
		  this.add (pane);
		  
		  List<Ad> ads = adServer.listAd();
		  fillTable(ads);
	}
	
	public void fillTable(List<Ad> ads){
		MyTableModel tableModel = (MyTableModel) table.getModel();
		tableModel.setRowCount(0);// ���ԭ����
	  
		// �������
		for(Ad ad:ads){
		    List<Object> rowList = new ArrayList<Object>();
		    rowList.add(ad.getId());
		    rowList.add(ad.getName());
		    rowList.add(ad.getLogo());
		    rowList.add(ad.getLogo());
		    rowList.add(ad.getLogo());
		    rowList.add(ad.getLogo());
		    rowList.add("ɾ��");
		    // ������ݵ����
		    tableModel.addRow(rowList);
		}
	  
		// ���±��
		table.invalidate();
	}
	
	
}
