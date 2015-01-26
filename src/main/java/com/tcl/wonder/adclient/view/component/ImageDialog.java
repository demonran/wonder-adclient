package com.tcl.wonder.adclient.view.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.utlis.UIUtils;

public class ImageDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(ImageDialog.class);
	
	private String imageUrl;
	
	private JLabel imageLabel;
	
	private JPanel imagePane;
	
	private Frame parentFrame;
	

	public ImageDialog(Frame frame)
	{
		super(frame,true);
		this.parentFrame = frame;
		
		updateUI(frame);
	}
	
	

	public ImageDialog(String imageUrl)
	{
		this.imageUrl = imageUrl;
		updateUI(null);
	}



	private void updateUI(Frame frame)
	{
		this.setLocationRelativeTo(frame);
//		setSize(new Dimension(500,500));
		this.setUndecorated(true);
		
		imagePane = new JPanel();
		imagePane.setLayout(new BorderLayout());
		imageLabel = new JLabel();
		imagePane.add(imageLabel,BorderLayout.CENTER);
		
		
		this.add(imagePane);
//		this.pack();
		
		addEvent();
	}



	private void addEvent()
	{
		this.addMouseListener(new MouseAdapter()
		{
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				setVisible(false);
				
			}
		});
	}



	public String getImageUrl()
	{
		return imageUrl;
	}



	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}
	
	public void refreshImage(Icon icon)
	{
		setSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
		UIUtils.centerWindow(this);
		imageLabel.setIcon(icon);
		imagePane.repaint();
	}
	
	public void showImage(boolean visible)
	{
		refreshImage(UIUtils.getImageIcon("started1.gif"));
		
		
		
		
//		imageLabel.setIcon(UIUtils.getImageIcon("started1.gif"));
//		imageLabel.setSize(new Dimension(icon.getIconWidth(),icon.getIconHeight()));
//		setPreferredSize(new Dimension(300,300));
//		imageLabel.setIcon(UIUtils.getImageIcon("failed.jpg"));
//		repaint();
		
		
			
		new SwingWorker<ImageIcon, Void>()
		{
			@Override
			protected ImageIcon doInBackground() 
			{
				ImageIcon icon = null;
				try
				{
					icon = UIUtils.getImageIcon(new URL(imageUrl),300,300);
					if(icon.getIconHeight() < 0 )
					{
						logger.warn("logo is not found : {} ", icon);
						icon = UIUtils.getImageIcon("default.jpg", 300, 300);
					}
				} catch (MalformedURLException e)
				{
					icon = UIUtils.getImageIcon("default.jpg", 300, 300);
					e.printStackTrace();
				}

				return icon;
			}

			@Override
			protected void done()
			{
				ImageIcon icon;
				try
				{
					icon = get();
				} catch (InterruptedException | ExecutionException e)
				{
					e.printStackTrace();
					icon = UIUtils.getImageIcon("default.jpg", 300, 300);
				}
				refreshImage(icon);
				
			}
			
		}.execute();
		
		this.setVisible(true);
//		ImageIcon icon = UIUtils.getImageIcon(imageUrl,100,100);
//		imageLabel.setIcon(icon);
		
	}
	
}
