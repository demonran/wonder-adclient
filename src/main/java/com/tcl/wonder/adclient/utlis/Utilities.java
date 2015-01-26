package com.tcl.wonder.adclient.utlis;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公共工具类
 * @author liuran
 * 2015年1月23日
 *
 */
public class Utilities
{
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
	
	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void sleepSecond(long second)
	{
		try
		{
			Thread.sleep(second * 1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static Date parse(String timeStr)
	{
		try
		{
			return df.parse(timeStr);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String format(Date date)
	{
		return df.format(date);
	}

	public static boolean isStringEmpty(String str)
	{
		return str == null || str.isEmpty();
	}

	public static boolean isFileExist(String filename)
	{
		return new File(filename).exists();
	}

	public static boolean isDirectoryExist(String filename)
	{
		File file = new File(filename);
		if(!file.exists())
		{
			return false;
		}
		return file.isDirectory();
	}
	
	public static String getAdIdFromMetafile(String metafile)
	{
		String id = null;
		File file = new File(metafile);
		if(file.exists())
		{
			BufferedReader reader = null;
			try
			{
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String str = null;
				while((str = reader.readLine())!=null)
				{
					if(str.contains("adsName="))
					{
						id =  str.split("=")[1].trim();
					}
				}
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}finally
			{
				closeStream(reader);
			}
		}
		return id;
	}
	
	public static void closeStream(Closeable closeable)
	{
		try
		{
			closeable.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
