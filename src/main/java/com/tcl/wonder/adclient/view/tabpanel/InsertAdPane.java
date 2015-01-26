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
import com.tcl.wonder.adclient.service.AdWonderService;
import com.tcl.wonder.adclient.utlis.UIUtils;
import com.tcl.wonder.adclient.view.AdFrame;
/**
 * ���������
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
	
	private AdWonderService adUploadService = AdWonderService.getInstance();
	
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
		
		JLabel videpathLabel = new JLabel("�����Ƶ·��:");
		JLabel metapathLabel = new JLabel("mata�ļ�·��:");
		JLabel idLabel = new JLabel("���ID:");
		JLabel nameLabel = new JLabel("�������:");
		JLabel logoLabel = new JLabel("���LOGO:");
		JLabel durationLabel = new JLabel("���ʱ��:");
		JLabel infoLabel = new JLabel("�����Ϣ:");
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
		
		videopathButton = new JButton("ѡ��...");
		metapathButton = new JButton("ѡ��...");
		addButton = new JButton("����");
		resetButton = new JButton("����");
		
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
							
							if(adUploadService.isAdExist(ad))
							{
								logger.info("Ad {} already exist",ad.getId());
								JOptionPane.showMessageDialog(adFrame, UIUtils.convertHtml(String.format("���(%s)�Ѿ�����!", ad.getName())),"�ϴ����",JOptionPane.YES_OPTION);
								
							}else
							{
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
									JOptionPane.showMessageDialog(adFrame, UIUtils.convertHtml(String.format("�ϴ����(%s)�ɹ�!", ad.getName())),"�ϴ����",JOptionPane.YES_OPTION);
								}else
								{
									logger.info("add ad  failed");
									JOptionPane.showMessageDialog(adFrame, UIUtils.convertHtml(String.format("�ϴ����(%s)ʧ��!", ad.getName())),"�ϴ����",JOptionPane.YES_OPTION);
								}
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
			errorLabel.setText("��ѡ������Ƶ�ļ���");
			return false;
		}
		if(metapathFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("��ѡ����meta�ļ���");
			return false;
		}
		if(idFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("��������ID��");
			return false;
		}
		if(nameFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("�����������ƣ�");
			return false;
		}
		if(logoFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("��������LOGO·����");
			return false;
		}
		if(durationFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("��������ʱ������λ��s����");
			return false;
		}
		if(infoFlied.getText().trim().isEmpty())
		{
			errorLabel.setText("����������ϸ��Ϣ��");
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
