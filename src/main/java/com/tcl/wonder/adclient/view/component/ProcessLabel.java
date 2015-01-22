package com.tcl.wonder.adclient.view.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JLabel;

public class ProcessLabel extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image image;

	public ProcessLabel(Image image)
	{
		this.image = image;
		new Thread()
		{
			public void run()
			{
				while (true)
				{
					ProcessLabel.this.repaint();// ���������Paint
					try
					{
						Thread.sleep(100);// ����100����
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
    /**
     * ����paint����
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graph = (Graphics2D) g;
        if (image != null) {
            // ȫ�����ͼƬ
            graph.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image
                    .getWidth(null), image.getHeight(null), null);
        }
    }
}
