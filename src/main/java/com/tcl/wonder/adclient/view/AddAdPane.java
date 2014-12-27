package com.tcl.wonder.adclient.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.utlis.UIUtils;

public class AddAdPane extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(AddAdPane.class);
	
	
	private JTextField videopathFlied;
	
	private JTextField metapathFlied;
	
	private JTextField idFlied;
	
	private JTextField nameFlied;
	
	private JTextField logoFlied;
	
	private JTextField durationFlied;
	
	private JTextArea infoFlied;
	
	private JButton videopathButton;
	
	private JButton metapathButton;
	
	private JButton addButton;
	
	private JButton resetButton;
	
	
	public AddAdPane()
	{
		logger.debug("add completely");
		layoutUI();
	}


	private void layoutUI()
	{
		
		System.out.println(this.getLayout());
		JPanel adPanel = new JPanel();
//		adPanel.setSize(800, 600);
//		this.setBounds(0, 0, 800, 600);
		
		setLayout(new BorderLayout(5,5)); 
//		this.add(new JButton("test"),BorderLayout.SOUTH);
//		this.add(adPanel,BorderLayout.CENTER);
		this.setOpaque(true);
		
		
		GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints cLable = UIUtils.getGridBagConstraints( 1,  0, 0);
        GridBagConstraints cText = UIUtils.getGridBagConstraints(4,   1, 0);
        GridBagConstraints cButton =  UIUtils.getGridBagConstraints(0,0 , 0);
        GridBagConstraints cArea =  UIUtils.getGridBagConstraints(0, 0, 1);
        adPanel.setLayout(gridbag);
		
		JLabel videpathLabel = new JLabel("广告视频路径:");
		JLabel metapathLabel = new JLabel("mata文件路径:");
		JLabel idLabel = new JLabel("广告ID:");
		JLabel nameLabel = new JLabel("广告名称:");
		JLabel logoLabel = new JLabel("广告LOGO:");
		JLabel durationLabel = new JLabel("广告时长:");
		JLabel infoLabel = new JLabel("广告信息:");
		
		videopathFlied = new JTextField();
		metapathFlied = new JTextField();
		idFlied = new JTextField();
		nameFlied = new JTextField();
		logoFlied = new JTextField();
		durationFlied = new JTextField();
		infoFlied = new JTextArea();
		
		videopathButton = new JButton("选择...");
		metapathButton = new JButton("选择...");
		addButton = new JButton("增加");
		resetButton = new JButton("重置");
		
        gridbag.setConstraints(videpathLabel, cLable);
        adPanel.add(videpathLabel);
		gridbag.setConstraints(videopathFlied, cText);
		adPanel.add(videopathFlied);
		gridbag.setConstraints(videopathButton, cButton);
		adPanel.add(videopathButton);
		
		gridbag.setConstraints(metapathLabel, cLable);
		adPanel.add(metapathLabel);
		gridbag.setConstraints(metapathFlied, cText);
		adPanel.add(metapathFlied);
		gridbag.setConstraints(metapathButton, cButton);
		adPanel.add(metapathButton);
		
		gridbag.setConstraints(idLabel, cLable);
		adPanel.add(idLabel);
		gridbag.setConstraints(idFlied, UIUtils.getGridBagConstraints(0, 0, 0));
		adPanel.add(idFlied);
		
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
		
		gridbag.setConstraints(infoLabel, cLable);
		adPanel.add(infoLabel);
		gridbag.setConstraints(infoFlied, cArea);
		adPanel.add(infoFlied);
		
//		this.add(addButton,new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,0,0, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 5, 5, 10), 0, 0));
		
		JPanel operationPanel = new JPanel();
		
		this.add("North", new JButton("test"));
		this.add("Center",adPanel);
		this.add(operationPanel,BorderLayout.SOUTH);
		operationPanel.add(addButton);
		operationPanel.add(resetButton);
		
		logger.debug("add completely");
		
		
	}

}
