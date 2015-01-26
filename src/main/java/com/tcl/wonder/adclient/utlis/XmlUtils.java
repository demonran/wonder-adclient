package com.tcl.wonder.adclient.utlis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.tcl.wonder.adclient.entity.Ad;

/**
 * xml工具类
 * @author liuran
 * 2015年1月23日
 *
 */
public class XmlUtils
{
	public static List<String> getIdFromXml(File file)
	{
		List<String> list = new ArrayList<String>();
		SAXBuilder builder = new SAXBuilder();
		try
		{
			Document doc = builder.build(file);
			Element rootElement = doc.getRootElement();
			for(Element element : rootElement.getChildren())
			{
				String id = element.getAttributeValue("id");
				list.add(id);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Ad> getAdFromXml(File file)
	{
		List<Ad> list = new ArrayList<Ad>();
		SAXBuilder builder = new SAXBuilder();
		try
		{
			Document doc = builder.build(file);
			Element rootElement = doc.getRootElement();
			for(Element element : rootElement.getChildren())
			{
				String id = element.getAttributeValue("id");
				String name = element.getChildText("name");
				 String logo = element.getChildText("logo");
				 int duration = Integer.valueOf(element.getChildText("duration"));
				 String info = element.getChildText("info");
				 list.add(new Ad(id, name, logo, duration, info));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public static Map<String,Ad> getAdMapFromXml(File file)
	{
		Map<String,Ad> map = new HashMap<String,Ad>();
		SAXBuilder builder = new SAXBuilder();
		try
		{
			Document doc = builder.build(file);
			Element rootElement = doc.getRootElement();
			for(Element element : rootElement.getChildren())
			{
				String id = element.getAttributeValue("adID");
				String name = element.getChildText("adName");
				 String logo = element.getChildText("adLogo");
				 int duration = Integer.valueOf(element.getChildText("duration")==null?"0":element.getChildText("duration"));
				 String info = element.getChildText("adInfo");
				 map.put(id,new Ad(id, name, logo, duration, info));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}
}
