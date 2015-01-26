package com.tcl.wonder.adclient.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.tcl.wonder.adclient.entity.Ad;

/**
 * 广告的缓存类 
 * use ehcache
 * @author liuran
 *
 */
public class WonderCacheUseEhcache
{
	private Cache cache;
	
	public WonderCacheUseEhcache(){
		CacheManager manager = CacheManager.create("conf/ehcache.xml");
		cache = manager.getCache("wonderCache");
		
	}
	
	public void cacheAd(List<Ad> newAds)
	{
		Element element = new Element("adsList",newAds);
		cache.put(element);
		
		List<String>  adIds = new ArrayList<String>();
		for(Ad ad : newAds)
		{
			adIds.add(ad.getId());
		}
		element = new Element("adIds",adIds);
		cache.put(element);
	}
	
	public void cacheAdMap(Map<String,Ad> newAds)
	{
		Element element = new Element("adsMap",newAds);
		cache.put(element);
	}
	
	public List<Ad> getAds()
	{
		Element element = cache.get("adsList");
		element.getObjectValue();
		List<?> objectValue = (List<?>)element.getObjectValue();
		return (List<Ad>)objectValue;
	}
	
	public Map<String,Ad> getAdsMap()
	{
		Element element = cache.get("adsMap");
		List<?> objectValue = (List<?>)element.getObjectValue();
		return (Map<String,Ad>)objectValue;
	}
	
	public List<String> getAllAdId()
	{
		Element element = cache.get("adIds");
		List<?> objectValue = (List<?>)element.getObjectValue();
		return (List<String>)objectValue;
	}

	public Ad getAdById(String adId)
	{
		Element element = cache.get(adId);
		List<?> objectValue = (List<?>)element.getObjectValue();
		return (Ad)objectValue;
	}
	
	public void cacheAdById(String adId,Ad ad)
	{
		Element element = new Element(adId,ad);
		cache.put(element);
	}
	
}
