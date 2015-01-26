package com.tcl.wonder.adclient.view.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.tcl.wonder.adclient.entity.Video.Status;
import com.tcl.wonder.adclient.utlis.Utilities;
import com.tcl.wonder.adclient.view.table.MyTableModel;

/**
 * JTable��װ�࣬ʵ��ָ������ɫ�仯
 * @author liuran
 * 2015��1��23��
 *
 */
public class NTable extends JTable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NTable(MyTableModel model)
	{
		super(model);
	}

	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		// TODO Auto-generated method stub
		Component component= super.prepareRenderer(renderer, row, column);
		
		String name = (String)this.getValueAt(row, 2);
		
		Status status = (Status)this.getValueAt(row, 4);
		
		if(Utilities.isStringEmpty(name))
		{
			component.setBackground(Color.yellow);
		}else if(status == Status.FAILED)
		{
			component.setBackground(Color.RED);
		}else if(status == Status.SUCCESS)
		{
			component.setBackground(Color.GREEN);
		}else
		{
			component.setBackground(Color.white);
		}
		return component;
	}

}
