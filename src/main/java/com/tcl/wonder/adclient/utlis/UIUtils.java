package com.tcl.wonder.adclient.utlis;

import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/**
 * UI工具类
 * @author liuran
 * 2015年1月23日
 *
 */
public class UIUtils
{
	
	
    //上文
    private static final String strHtmlBegin = 
           "<html>"
              + "<bgcolor color=#000080>"
                  +"<font size='5' color='red' style='font-family:楷体'>"
                  +"<b>";
    //下文
    private static final String strHtmlEnd = 
                  "</b>"
                 +"</font>"
             +"</bgcolor>"
          +"</html>";
    

	public static GridBagConstraints getGridBagConstraints(int gridx,int gridy,int gridwidth,int gridheight,double weightx,double weighty)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 5, 5, 10);
		return c;
	}
	
	public static GridBagConstraints getGridBagConstraints(int gridwidth,int gridheight,double weightx,double weighty)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 5, 5, 10);
		return c;
	}
	
	public static GridBagConstraints getGridBagConstraints(int gridwidth,double weightx,double weighty)
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = gridwidth;
		c.weightx = weightx;
		c.weighty = weighty;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 5, 5, 10);
		return c;
	}
	
	public static String convertHtml(String str)
	{
		return strHtmlBegin + str + strHtmlEnd;
	}
	
	/**
	 * 
	 * @param imageName
	 * @return
	 */
	public static Image getImage(String imageName){
		Image image = Toolkit.getDefaultToolkit().getImage("icon/"+imageName);
		return image;
		
	}
	
	public static ImageIcon getImageIcon(String imageName){
		ImageIcon imageIcon = new ImageIcon("icon/"+imageName);
		return imageIcon;
	}
	
	/**
	 * 
	 * @param imageName
	 * @return
	 */
	public static ImageIcon getImageIcon(String imageName,int x,int y){
		ImageIcon imageIcon=getImageIcon(imageName);
		int width=imageIcon.getIconWidth();
		int height=imageIcon.getIconHeight();
		float xbili=(float) ((x*1.0)/width);
		float ybili=(float) ((y*1.0)/height);
		float bili=xbili>ybili?ybili:xbili;
		int newwidth=(int) (width*bili);
		int newheight=(int) (height*bili);
		Image image=getImage(imageName,newwidth,newheight);
		imageIcon.setImage(image);
		return imageIcon;
	}
	
	/**
	 * 
	 * @param imageName 
	 * @return
	 */
	public static Image getImage(String imageName,int x,int y){
		Image image = Toolkit.getDefaultToolkit().getImage("icon/"+imageName);
		image = image.getScaledInstance(x,y, Image.SCALE_SMOOTH); 
		return image;
	}
	

}
