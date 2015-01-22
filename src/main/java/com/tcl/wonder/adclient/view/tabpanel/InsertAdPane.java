package com.tcl.wonder.adclient.view.tabpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.entity.Video;
import com.tcl.wonder.adclient.service.AdUploadService;
import com.tcl.wonder.adclient.utlis.UIUtils;
import com.tcl.wonder.adclient.view.AdFrame;
/**
 * 插入面板类
 * @author liuran
 *
 */
public class InsertAdPane extends TabPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(InsertAdPane.class);
	
	
	private JTextField updatetimeFlied;
	
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
	
	private AdUploadService adUploadService = AdUploadService.getInstance();
	
	private AdFrame adFrame;
	
	public InsertAdPane(AdFrame adFrame)
	{
		this.adFrame = adFrame;
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
		
		updatetimeFlied = new JTextField();
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
		gridbag.setConstraints(updatetimeFlied, cText);
		adPanel.add(updatetimeFlied);
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
		
		infoFlied.setLineWrap(true);
		JScrollPane infoPane = new JScrollPane(infoFlied);
		infoPane.setBorder(durationFlied.getBorder());
		gridbag.setConstraints(infoPane, cArea);
		adPanel.add(infoPane);
		
		
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
					updatetimeFlied.setText(videopath);
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
				updatetimeFlied.setText("");
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
				adFrame.startInfiniteProgress();
				if(validateFiled())
				{
					new Thread()
					{
						public void run()
						{
							Ad ad = getAdfromForm();
							String videopath = updatetimeFlied.getText().trim();
							String metafile = metapathFlied.getText().trim();
							Video video = new Video();
							video.setId(ad.getId());
							video.setPath(videopath);
							video.setMetaPath(metafile);
							video.setName(ad.getName());
							boolean success = adUploadService.upload(video, ad);
							if(success)
							{
								logger.info("add ad successfully");
								JOptionPane.showMessageDialog(adFrame, UIUtils.convertHtml(String.format("上传广告(%s)成功？", ad.getName())),"上传广告",JOptionPane.YES_OPTION);
							}else
							{
								logger.info("add ad  failed");
								JOptionPane.showMessageDialog(adFrame, UIUtils.convertHtml(String.format("上传广告(%s)失败？", ad.getName())),"上传广告",JOptionPane.YES_OPTION);
							}
							adFrame.stopInfiniteProgress();
						}
					}.start();
					
				}
					
					
				
				
			}
		});
		
	}
	
	private boolean validateFiled()
	{
		if(updatetimeFlied.getText().trim().isEmpty())
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
		ad.setUpdatetime(new Date());
		return ad;
	}


	@Override
	public void refresh()
	{
		// TODO Auto-generated method stub
		
	}
}
