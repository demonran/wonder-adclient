package com.tcl.wonder.adclient.view.table;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import javax.swing.JTable;

public class StatusImageObserver implements ImageObserver {

	JTable table;
	int row;
	int col;
	public StatusImageObserver(){}
	
	public StatusImageObserver(JTable table,int row, int col){
		this.table = table;
		this.row = row;
		this.col = col;
		System.out.println("ImageObserver initializated");
	}
	
	public void setProperties(JTable table,int row, int col)
	{
		this.table = table;
		this.row = row;
		this.col = col;
	}


	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// TODO Auto-generated method stub
		if ((infoflags & (FRAMEBITS | ALLBITS)) != 0) {
	        Rectangle rect = table.getCellRect(row, col, false);
	        table.repaint(rect);
	      }
		return (infoflags & (ALLBITS | ABORT)) == 0;

	}

}