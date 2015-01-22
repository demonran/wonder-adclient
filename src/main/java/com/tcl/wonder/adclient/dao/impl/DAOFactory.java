package com.tcl.wonder.adclient.dao.impl;

import com.tcl.wonder.adclient.dao.AdDAO;
import com.tcl.wonder.adclient.dao.ChannelAdDAO;

public class DAOFactory
{
	//114.215.148.2
	public static AdDAO redisAdDaoImpl = new RedisAdDAOImpl("114.215.148.2"); 
	
	public static AdDAO localAdDao = new RedisAdDAOImpl("localhost"); 
	
	public static ChannelAdDAO redisChannelAdDaoImpl = new RedisChannelAdDAOImpl();
	
	public static AdDAO getAdDAO()
	{
		return redisAdDaoImpl;
	}
	
	public static AdDAO getLocalAdDAO()
	{
		return localAdDao;
	}
	
	public static ChannelAdDAO getChannelAdDAO()
	{
		return redisChannelAdDaoImpl;
	}
}
