package com.tcl.wonder.adclient.utlis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.tcl.wonder.adclient.entity.Ad;


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
		} catch (JDOMException | IOException e)
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
		} catch (JDOMException | IOException e)
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
				String id = element.getAttributeValue("id");
				String name = element.getChildText("name");
				 String logo = element.getChildText("logo");
				 int duration = Integer.valueOf(element.getChildText("duration")==null?"0":element.getChildText("duration"));
				 String info = element.getChildText("info");
				 map.put(id,new Ad(id, name, logo, duration, info));
			}
		} catch (JDOMException | IOException e)
		{
			e.printStackTrace();
		}
		return map;
	}
}
