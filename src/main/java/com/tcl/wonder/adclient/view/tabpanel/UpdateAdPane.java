package com.tcl.wonder.adclient.view.tabpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.service.AdService;
import com.tcl.wonder.adclient.utlis.UIUtils;
import com.tcl.wonder.adclient.view.AdFrame;
import com.tcl.wonder.adclient.view.Contents;
import com.tcl.wonder.adclient.view.worker.AdSwingWorker;
import com.tcl.wonder.adclient.view.worker.callback.CallbackAdapter;

/**
 * 广告信息更新类
 * @author liuran
 * 2015年1月23日
 *
 */
public class UpdateAdPane extends TabPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(InsertAdPane.class);
	
	private JComboBox<String> idFlied;
	
	private JTextField nameFlied;
	
	private JTextField logoFlied;
	
	private JTextField durationFlied;
	
	private JTextArea infoFlied;
	
	private JButton updateButton;
	
	private JButton resetButton;
	
	private JLabel errorLabel;
	
	private AdFrame adFrame;
	
	private Ad ad;
	
	private AdService  adService = new AdService();
	
	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
	
	public UpdateAdPane(AdFrame adFrame)
	{
		this.adFrame = adFrame;
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
		JLabel infoLabel = new JLabel("广告信息:");
		errorLabel = new JLabel();
		errorLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
		errorLabel.setForeground(Color.RED);
		
		idFlied = new JComboBox<String>(model);
		idFlied.setEditable(false);
		nameFlied = new JTextField();
		
		logoFlied = new JTextField();
		durationFlied = new JTextField();
		infoFlied = new JTextArea();
		
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
		operationPanel.add(updateButton);
		operationPanel.add(resetButton);
		
		addEvent();
	}
	
	public void setAd(Ad ad)
	{
		this.ad = ad;
	}
	
	private void initData()
	{
		nameFlied.setText(ad.getName());
		logoFlied.setText(ad.getLogo());
		durationFlied.setText(String.valueOf(ad.getDuration()));
		infoFlied.setText(ad.getInfo());
	}


	private void addEvent()
	{
		idFlied.addItemListener(new ItemListener()
		{
			
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getItem() == null)
				{
					return;
				}
				String id = e.getItem().toString();
				logger.info("update ad id :{}" ,id);
				if(id.equals(Contents.ID_SELECT))
				{
					nameFlied.setText("");
					logoFlied.setText("");
					durationFlied.setText("");
					infoFlied.setText("");
				}
				else
				{
					 new AdSwingWorker(new CallbackAdapter(){

						@Override
						public void call(Ad ad)
						{
							setAd(ad);
							initData();
						}
					},id).execute();;
				}
				
				
			}
		});
		idFlied.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		
		
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
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
							boolean success = adService.updateAd(ad);
							if(success)
							{
								logger.info("add ad to database successfully");
							}else
							{
								logger.error("add ad to database failed");
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
		if(idFlied.getSelectedItem().toString().isEmpty())
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
		ad.setId(idFlied.getSelectedItem().toString());
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
		logger.debug(" Updata Panel selected");
		adFrame.startInfiniteProgress();
		AdSwingWorker worker = new AdSwingWorker(new CallbackAdapter(){
			public void call(Map<String,Ad> adsMap)
			{
				model.removeAllElements();
				model.addElement(ad==null? Contents.ID_SELECT : ad.getId());
				for(String id : adsMap.keySet())
				{
					model.addElement(id);
				}
				adFrame.stopInfiniteProgress();
			}
		});
		worker.execute();
	}
}
