package com.tcl.wonder.adclient.utlis;

public class Utilities
{
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
}
