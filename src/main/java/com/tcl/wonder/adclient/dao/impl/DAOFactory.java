package com.tcl.wonder.adclient.dao.impl;

import com.tcl.wonder.adclient.dao.AdDAO;
import com.tcl.wonder.adclient.dao.ChannelAdDAO;

public class DAOFactory
{
	public static AdDAO redisAdDaoImpl = new RedisAdDAOImpl(); 
	
	public static ChannelAdDAO redisChannelAdDaoImpl = new RedisChannelAdDAOImpl();
	
	public static AdDAO getAdDAO()
	{
		return redisAdDaoImpl;
	}
	
	public static ChannelAdDAO getChannelAdDAO()
	{
		return redisChannelAdDaoImpl;
	}
}
