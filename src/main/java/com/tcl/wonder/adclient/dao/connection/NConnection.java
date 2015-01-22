package com.tcl.wonder.adclient.dao.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

public class NConnection
{
	private Connection connection;
	
	private ShardedJedis shardedJedis;
	
	public NConnection(ShardedJedis shardedJedis)
	{
		this.shardedJedis = shardedJedis;
	}

	public NConnection(Connection con)
	{
		this.connection = con;
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	public void close() throws SQLException
	{
		connection.close();
		
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return connection.prepareStatement(sql);
	}

	public void hmset(String key, Map<String, String> hash)
	{
		shardedJedis.hmset(key, hash);
		
	}

	public void del(String key)
	{
		shardedJedis.del(key);
		
	}

	public Collection<Jedis> getAllShards()
	{
		return shardedJedis.getAllShards();
	}

	public Map<String, String> hgetAll(String key)
	{
		return shardedJedis.hgetAll(key);
	}
	
	

}
