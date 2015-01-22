package com.tcl.wonder.adclient.dao.impl;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.tcl.wonder.adclient.dao.ChannelAdDAO;

public class RedisChannelAdDAOImpl implements ChannelAdDAO
{
	
	private ShardedJedis sharedJedis;
	
	public RedisChannelAdDAOImpl()
	{
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(1000);
		jedisPoolConfig.setMinIdle(100);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setMaxWaitMillis(300000);
		
		JedisShardInfo shardInfo = new JedisShardInfo("114.215.148.2", 6379, 300000,"instance:01");
		
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(shardInfo);
		
		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,shards);
		sharedJedis = shardedJedisPool.getResource();
	}

	@Override
	public String findAdIdByChannelId(String channelId)
	{
		return sharedJedis.get(channelId);
	}
	
	
}
