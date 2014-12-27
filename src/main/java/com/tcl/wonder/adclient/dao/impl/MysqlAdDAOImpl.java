package com.tcl.wonder.adclient.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.dao.AdDAO;
import com.tcl.wonder.adclient.dao.connection.DBConnectionManager;
import com.tcl.wonder.adclient.entity.Ad;

public class MysqlAdDAOImpl implements AdDAO
{
	
	private final static Logger logger = LoggerFactory.getLogger(MysqlAdDAOImpl.class);
	
	
	private String database;
	
	private String table;
	
	private PreparedStatement save;
	
	private Connection conn = null;
	

	static
	{
		try
		{
			Class.forName("");
			
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void init()
	{
		long time = System.currentTimeMillis();
		try
		{
			conn = DBConnectionManager.getInstance().getConnection("user");
			createSchema();
			save = conn.prepareStatement("update ad values(?,?,?,?,?,?)  ");
		} catch (SQLException e)
		{
			logger.error("Preparing statements error: ",e);
			e.printStackTrace();
		}
		
		logger.info("Preparing statements takes {} milliseconds.", System.currentTimeMillis() - time);
		
	}
	
	public void createSchema() throws SQLException{
		String createKeyspace = "CREATE DATABASE IF NOT EXISTS " + database + ";";


		String createTable = 
				"CREATE TABLE IF NOT EXISTS " + database + "." + table + " (" +
						"id varchar PRIMARY KEY," + 
						"name varchar," +
						"logo varchar," + 
						"duration int," + 
						"info varchar," +
						"videoname varchar," +
						");";

		conn.prepareStatement(createKeyspace).execute();
		conn.prepareStatement(createTable).execute();
	}
	
	public MysqlAdDAOImpl()
	{
	}
	
	@Override
	public boolean update(Ad ad)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(Ad ad)
	{
		try
		{
			save.setString(1, ad.getId());
			save.setString(2, ad.getName());
			save.setString(3, ad.getLogo());
			save.setString(4, ad.getInfo());
			save.setInt(5, ad.getDuration());
			save.setString(6, ad.getVideoname());
			save.executeUpdate();
		} catch (SQLException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Ad ad)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Ad> findAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ad findById(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
