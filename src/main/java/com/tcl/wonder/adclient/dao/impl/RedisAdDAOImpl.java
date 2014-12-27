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
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.tcl.wonder.adclient.dao.AdDAO;
import com.tcl.wonder.adclient.entity.Ad;

public class RedisAdDAOImpl implements AdDAO
{
	
	private ShardedJedis sharedJedis;
	
	private static Logger logger = LoggerFactory.getLogger(RedisAdDAOImpl.class);
	
	public RedisAdDAOImpl()
	{
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(1000);
		jedisPoolConfig.setMinIdle(100);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setMaxWaitMillis(300000);
		
		JedisShardInfo shardInfo = new JedisShardInfo("127.0.0.1", 6379, 300000,"instance:01");
		
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(shardInfo);
		
		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,shards);
		sharedJedis = shardedJedisPool.getResource();
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
		sharedJedis.hmset("ad_"+ad.getId(), hash);
		return true;
	}
	
	@Override
	public boolean delete(Ad ad)
	{
		sharedJedis.del(ad.getId());
		return true;
	}
	
	@Override
	public List<Ad> findAll()
	{
		List<Ad> ads = new ArrayList<Ad>();
		Collection<Jedis> allShards = sharedJedis.getAllShards();
		Set<String> keys = new HashSet<String>();
		for(Jedis jedis : allShards)
		{
			keys.addAll(jedis.keys("ad_*"));
		}
		for(String key : keys)
		{
			Map<String,String> adMap = sharedJedis.hgetAll(key);
			logger.debug(adMap.toString());
			ads.add(new Ad(adMap));
		}
		
		return ads;
	}
	
	@Override
	public Ad findById(String id)
	{
		Map<String,String> adMap = sharedJedis.hgetAll(id);
		return new Ad(adMap);
	}

}
