package com.tcl.wonder.adclient.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.tcl.wonder.adclient.dao.AdDAO;
import com.tcl.wonder.adclient.dao.WonderCache;
import com.tcl.wonder.adclient.dao.connection.DBConnectionManager;
import com.tcl.wonder.adclient.dao.connection.NConnection;
import com.tcl.wonder.adclient.entity.Ad;

public class RedisAdDAOImpl implements AdDAO
{
	
	private NConnection sharedJedis;
	
	private static Logger logger = LoggerFactory.getLogger(RedisAdDAOImpl.class);
	
	private static String AD_PREFIX = "ADINFO_*";
	
	public RedisAdDAOImpl(String host)
	{
		sharedJedis = DBConnectionManager.getInstance().getConnection("ads");
	}
	
	@Override
	public boolean update(Ad ad)
	{
		return add(ad);
	}
	
	@Override
	public boolean add(Ad ad)
	{
		Map<String,String> hash = new HashMap<String,String>();
		hash.put("id", ad.getId());
		hash.put("name", ad.getName());
		hash.put("logo", ad.getLogo());
		hash.put("info", ad.getInfo());
		hash.put("duration", String.valueOf(ad.getDuration()));
		long updateTime = ad.getUpdatetime()==null?System.currentTimeMillis():ad.getUpdatetime().getTime();
		hash.put("updatetime", String.valueOf(updateTime));
		sharedJedis.hmset(AD_PREFIX+ad.getId(), hash);
		WonderCache.setDatabaseHasUpdate(true);
		return true;
	}
	
	@Override
	public boolean delete(Ad ad)
	{
		return delete(ad.getId());
	}
	
	@Override
	public boolean delete(String id)
	{
		sharedJedis.del(AD_PREFIX+id);
		WonderCache.setDatabaseHasUpdate(true);
		return true;
	}
	
	@Override
	public List<Ad> findAll()
	{
		List<Ad> ads = WonderCache.getAds();
		if(ads == null)
		{
			long tt = System.currentTimeMillis();
			ads = new ArrayList<Ad>();
			Collection<Jedis> allShards = sharedJedis.getAllShards();
			Set<String> keys = new HashSet<String>();
			for(Jedis jedis : allShards)
			{
				keys.addAll(jedis.keys(AD_PREFIX));
			}
			for(String key : keys)
			{
				Map<String,String> adMap = sharedJedis.hgetAll(key);
				ads.add(new Ad(adMap));
			}
			logger.debug("ad info size:{} ,get all info cost time :{}ms" ,keys.size(),System.currentTimeMillis() -tt);
			WonderCache.cacheAd(ads);
		}
		
		return ads;
	}
	
	@Override
	public Map<String,Ad> findAlltoMap()
	{
		Map<String,Ad> adsMap = WonderCache.getAdsMap();
		if(adsMap == null)
		{
			adsMap = new HashMap<String,Ad>();
			Collection<Jedis> allShards = sharedJedis.getAllShards();
			Set<String> keys = new HashSet<String>();
			for(Jedis jedis : allShards)
			{
				keys.addAll(jedis.keys(AD_PREFIX));
			}
			for(String key : keys)
			{
				Map<String,String> adMap = sharedJedis.hgetAll(key);
				Ad ad = new Ad(adMap);
				adsMap.put(ad.getId(),new Ad(adMap));
			}
			logger.debug("ad info size: " +keys.size());
			WonderCache.cacheAdMap(adsMap);
		}
		return adsMap;
	}
	
	@Override
	public Ad findById(String id)
	{
		Map<String,String> adMap = sharedJedis.hgetAll(AD_PREFIX+id);
		if(adMap == null || adMap.size() == 0)
		{
			return null;
		}
		return new Ad(adMap);
	}

	@Override
	public boolean addAll(List<Ad> ads)
	{	
		for(Ad ad : ads)
		{
			add(ad);
		}
		return true;
	}
	

}
