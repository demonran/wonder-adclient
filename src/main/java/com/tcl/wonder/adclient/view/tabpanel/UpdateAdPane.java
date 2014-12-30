package com.tcl.wonder.adclient.view.tabpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.service.AdService;
import com.tcl.wonder.adclient.utlis.UIUtils;

public class UpdateAdPane extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(InsertAdPane.class);
	
	
	private JTextField idFlied;
	
	private JTextField nameFlied;
	
	private JTextField logoFlied;
	
	private JTextField durationFlied;
	
	private JTextArea infoFlied;
	
	private JTextField videonameFlied;
	
	private JButton updateButton;
	
	private JButton resetButton;
	
	private JLabel errorLabel;
	
	
	private AdService  adService = new AdService();
	
	public UpdateAdPane()
	{
		logger.debug("add completely");
		layoutUI();
	}


	private void layoutUI()
	{
		
		JPanel adPanel = new JPanel();
		
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints cLable = UIUtils.getGridBagConstraints( 1,  0, 0);
        GridBagConstraints cText = UIUtils.getGridBagConstraints(1, 0.5, 0);
        GridBagConstraints cArea =  UIUtils.getGridBagConstraints(0, 0, 1);
        adPanel.setLayout(gridbag);
		
		JLabel idLabel = new JLabel("广告ID:");
		JLabel nameLabel = new JLabel("广告名称:");
		JLabel logoLabel = new JLabel("广告LOGO:");
		JLabel durationLabel = new JLabel("广告时长:");
		JLabel videonameLabel = new JLabel("广告视频名称:");
		JLabel infoLabel = new JLabel("广告信息:");
		errorLabel = new JLabel();
		errorLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
		errorLabel.setForeground(Color.RED);
		
		idFlied = new JTextField();
		idFlied.setEnabled(false);
		nameFlied = new JTextField();
		
		logoFlied = new JTextField();
		durationFlied = new JTextField();
		infoFlied = new JTextArea();
		videonameFlied = new JTextField();
		
		updateButton = new JButton("更新");
		resetButton = new JButton("重置");
		
		
		gridbag.setConstraints(idLabel, cLable);
		adPanel.add(idLabel);
		gridbag.setConstraints(idFlied,cText);
		adPanel.add(idFlied);
		JLabel label = new JLabel();
		gridbag.setConstraints(label, UIUtils.getGridBagConstraints(0, 1, 0));
		adPanel.add(label);
		
		gridbag.setConstraints(nameLabel, cLable);
		adPanel.add(nameLabel);
		gridbag.setConstraints(nameFlied, UIUtils.getGridBagConstraints(0, 0, 0));
		adPanel.add(nameFlied);
		
		gridbag.setConstraints(logoLabel, cLable);
		adPanel.add(logoLabel);
		gridbag.setConstraints(logoFlied, UIUtils.getGridBagConstraints(0, 0, 0));
		adPanel.add(logoFlied);
		
		gridbag.setConstraints(durationLabel, cLable);
		adPanel.add(durationLabel);
		gridbag.setConstraints(durationFlied, UIUtils.getGridBagConstraints(0, 0, 0));
		adPanel.add(durationFlied);
		
		gridbag.setConstraints(videonameLabel, cLable);
		adPanel.add(videonameLabel);
		gridbag.setConstraints(videonameFlied,  UIUtils.getGridBagConstraints(0, 0, 0));
		adPanel.add(videonameFlied);
		
		gridbag.setConstraints(infoLabel, cLable);
		adPanel.add(infoLabel);
		gridbag.setConstraints(infoFlied, cArea);
		adPanel.add(infoFlied);
		
		
		JPanel operationPanel = new JPanel();
		
		this.setLayout(new BorderLayout()); 
		this.setOpaque(true);
		this.add("North", errorLabel);
		this.add("Center",adPanel);
		this.add(operationPanel,BorderLayout.SOUTH);
		operationPanel.add(updateButton);
		operationPanel.add(resetButton);
		
		logger.debug("add completely");
		
		addEvent();
	}
	
	public void update(Ad ad)
	{
		idFlied.setText(ad.getId());
		nameFlied.setText(ad.getName());
		logoFlied.setText(ad.getLogo());
		durationFlied.setText(String.valueOf(ad.getDuration()));
		infoFlied.setText(ad.getInfo());
		videonameFlied.setText(ad.getVideoname());
	}


	private void addEvent()
	{
		
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				videonameFlied.setText("");
				nameFlied.setText("");
				logoFlied.setText("");
				durationFlied.setText("");
				infoFlied.setText("");
			}
		});
		
		updateButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateButton.setEnabled(false);
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
								logger.info("add ad to database successfully");
							}else
							{
								logger.info("add ad to database unsuccessfully");
							}
							updateButton.setEnabled(true);
						}
					}.start();
					
				}
					
					
				
				
			}
		});
		
	}
	
	private boolean validateFiled()
	{
		if(idFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请输入广告ID！");
			return false;
		}
		if(nameFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请输入广告名称！");
			return false;
		}
		if(logoFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请输入广告LOGO路径！");
			return false;
		}
		if(durationFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请输入广告时长（单位：s）！");
			return false;
		}
		if(infoFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请输入广告详细信息！");
			return false;
		}
		if(videonameFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请输入广告文件名称！");
			return false;
		}
		return true;
	}
	
	private Ad getAdfromForm()
	{
		Ad ad = new Ad();
		ad.setId(idFlied.getText().trim());
		ad.setName(nameFlied.getText().trim());
		ad.setLogo(logoFlied.getText().trim());
		ad.setDuration(Integer.valueOf(durationFlied.getText().trim()));
		ad.setInfo(infoFlied.getText().trim());
		ad.setVideoname(videonameFlied.getText().trim());
		return ad;
	}
}
