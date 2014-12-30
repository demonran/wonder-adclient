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
/**
 * 插入面板类
 * @author liuran
 *
 */
public class InsertAdPane extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(InsertAdPane.class);
	
	
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
	
	private JLabel errorLabel;
	
	
	private AdService  adService = new AdService();
	
	private AdServerClient client = new AdServerClient();
	
	public InsertAdPane()
	{
		logger.debug("add completely");
		layoutUI();
	}


	private void layoutUI()
	{
		
		JPanel adPanel = new JPanel();
		
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
		errorLabel = new JLabel();
		errorLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
		errorLabel.setForeground(Color.RED);
		
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
				JFileChooser fd = new JFileChooser();  
				fd.setFileFilter(new FileNameExtensionFilter("VIDEO file","mp4","avi"));
				fd.showOpenDialog(null);  
				File file = fd.getSelectedFile();  
				if(file != null)
				{
					String videopath = file.getAbsolutePath();
					String defaultMetapath = videopath.substring(0, videopath.indexOf("."))+".txt";
					logger.info("select a video,video path:{} ,defaultMetapath:{}",videopath,defaultMetapath);
					videopathFlied.setText(videopath);
					metapathFlied.setText(defaultMetapath);
					metapathFlied.setForeground(Color.GRAY);
					
					String filename = file.getName();
					idFlied.setText(filename.substring(0,filename.indexOf(".")));
					idFlied.setForeground(Color.GRAY);
				}  
				
			}
			
		});
		
		metapathButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fd = new JFileChooser();  
				fd.setFileFilter(new FileNameExtensionFilter("TXT file","txt"));
				fd.showOpenDialog(null);  
				File file = fd.getSelectedFile();  
				if(file != null)
				{
					String metapath = file.getAbsolutePath();
					metapathFlied.setText(metapath);
					metapathFlied.setForeground(Color.BLACK);
					logger.info("select a meta file ,metafile path : {}",metapath);
				}  
			}
		});
		
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				videopathFlied.setText("");
				metapathFlied.setText("");
				idFlied.setText("");
				nameFlied.setText("");
				logoFlied.setText("");
				durationFlied.setText("");
				infoFlied.setText("");
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
								String metafile = metapathFlied.getText().trim();
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
		if(metapathFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("请选择广告meta文件！");
			return false;
		}
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
		ad.setVideoname(videopathFlied.getText().trim().substring(videopathFlied.getText().trim().lastIndexOf("\\")+1));
		return ad;
	}
}
