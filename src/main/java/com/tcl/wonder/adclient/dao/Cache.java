package com.tcl.wonder.adclient.dao;

import java.util.ArrayList;
import java.util.List;

import com.tcl.wonder.adclient.entity.Ad;

/**
 * 广告的缓存类
 * @author liuran
 *
 */
public class Cache
{
	private static List<Ad> ads = new ArrayList<Ad>();
	
	private static long updateTime;
	
	private static boolean databaseHasUpdate = true;
	
	public static void cacheAd(List<Ad> newAds)
	{
		ads = newAds;
		databaseHasUpdate = false;
		updateTime = System.currentTimeMillis();
	}
	
	public static List<Ad> getAds()
	{
		if(databaseHasUpdate)
		{
			ads = null;
		}
		if(System.currentTimeMillis() - updateTime >1000*60)
		{
			ads = null;
		}
		return ads;
	}
	
	public static List<String> getAllAdId()
	{
		if(databaseHasUpdate)
		{
			ads = null;
		}
		if(System.currentTimeMillis() - updateTime >1000*60)
		{
			return null;
		}
		List<String>  adIds = new ArrayList<String>();
		for(Ad ad : ads)
		{
			adIds.add(ad.getId());
		}
		
		return adIds;		
	}

	public static void setDatabaseHasUpdate(boolean databaseHasUpdate)
	{
		Cache.databaseHasUpdate = databaseHasUpdate;
	}
	
	
}
