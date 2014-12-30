package com.tcl.wonder.adclient.view.tabpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.service.AdServerClient;
import com.tcl.wonder.adclient.service.AdService;
import com.tcl.wonder.adclient.utlis.UIUtils;

public class BatchInsertPane extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static Logger logger = LoggerFactory.getLogger(InsertAdPane.class);
	
	
	private JTextField videopathFlied;
	
	private JTextField xmlpathFlied;
	
	private JButton videopathButton;
	
	private JButton xmlpathButton;
	
	private JButton addButton;
	
	private JButton resetButton;
	
	private JLabel errorLabel;
	
	
	private AdService  adService = new AdService();
	
	private AdServerClient client = new AdServerClient();
	
	public BatchInsertPane()
	{
		layoutUI();
	}


	private void layoutUI()
	{
		
		JPanel adPanel = new JPanel();
		
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints cLable = UIUtils.getGridBagConstraints( 1,  0, 0);
        GridBagConstraints cText = UIUtils.getGridBagConstraints(4,   1, 0);
        GridBagConstraints cButton =  UIUtils.getGridBagConstraints(0,0 , 0);
        adPanel.setLayout(gridbag);
		
		JLabel videpathLabel = new JLabel("广告视频文件夹:");
		JLabel xmlpathLabel = new JLabel("广告信息xml路径:");
		errorLabel = new JLabel();
		errorLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
		errorLabel.setForeground(Color.RED);
		
		videopathFlied = new JTextField();
		xmlpathFlied = new JTextField();
		
		videopathButton = new JButton("选择...");
		xmlpathButton = new JButton("选择...");
		addButton = new JButton("增加");
		resetButton = new JButton("重置");
		
        gridbag.setConstraints(videpathLabel, cLable);
        adPanel.add(videpathLabel);
		gridbag.setConstraints(videopathFlied, cText);
		adPanel.add(videopathFlied);
		gridbag.setConstraints(videopathButton, cButton);
		adPanel.add(videopathButton);
		
		gridbag.setConstraints(xmlpathLabel, cLable);
		adPanel.add(xmlpathLabel);
		gridbag.setConstraints(xmlpathFlied, cText);
		adPanel.add(xmlpathFlied);
		gridbag.setConstraints(xmlpathButton, cButton);
		adPanel.add(xmlpathButton);
		
		JPanel operationPanel = new JPanel();
		
		this.setLayout(new BorderLayout()); 
		this.setOpaque(true);
		this.add("North", errorLabel);
		this.add("Center",adPanel);
		this.add(operationPanel,BorderLayout.SOUTH);
		operationPanel.add(addButton);
		operationPanel.add(resetButton);
		
		logger.debug("add completely");
		
		addEvent();
		
	}


	private void addEvent()
	{
		videopathButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();  
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(null);  
				File dir = fc.getSelectedFile();  
				if(dir != null)
				{
					String videopath = dir.getAbsolutePath();
					logger.info("select a video,video path:{} ",videopath);
					videopathFlied.setText(videopath);
				}  
				
			}
			
		});
		
		xmlpathButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fd = new JFileChooser();  
				fd.setFileFilter(new FileNameExtensionFilter("XML file","xml"));
				fd.showOpenDialog(null);  
				File file = fd.getSelectedFile();  
				if(file != null)
				{
					String xmlpath = file.getAbsolutePath();
					xmlpathFlied.setText(xmlpath);
					logger.info("select a meta file ,metafile path : {}",xmlpath);
				}  
				
			}
			
		});
		
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				videopathFlied.setText("");
				xmlpathFlied.setText("");
			}
		});
		
		addButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addButton.setEnabled(false);
				if(validateFiled())
				{
					new Thread()
					{
						public void run()
						{
							Ad ad = getAdfromForm();
							boolean success = adService.insertAd(ad);
							if(success)
							{
								//上传指纹到adServer
								String video = videopathFlied.getText().trim();
								String metafile = xmlpathFlied.getText().trim();
								success = client.upload(video, metafile);
								//上传失败则回退数据库信息
								if(!success)
								{
									logger.info("upload to ad server unsuccessfully,begin rollbacking the database");
									adService.deleteAd(ad);
								}
								
							}else
							{
								logger.info("add ad to database unsuccessfully");
							}
							addButton.setEnabled(true);
						}
					}.start();
					
				}
					
					
				
				
			}
		});
		
	}
	
	private boolean validateFiled()
	{
		if(videopathFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请选择广告视频文件！");
			return false;
		}
		if(xmlpathFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请选择广告meta文件！");
			return false;
		}
		return true;
	}
	
	private Ad getAdfromForm()
	{
		Ad ad = new Ad();
//		ad.setId(idFlied.getText().trim());
//		ad.setName(nameFlied.getText().trim());
//		ad.setLogo(logoFlied.getText().trim());
//		ad.setDuration(Integer.valueOf(durationFlied.getText().trim()));
//		ad.setInfo(infoFlied.getText().trim());
//		ad.setVideoname(videopathFlied.getText().trim().substring(videopathFlied.getText().trim().lastIndexOf("\\")+1));
		return ad;
	}


}
