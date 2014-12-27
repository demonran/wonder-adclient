package com.tcl.wonder.adclient.service;

import java.util.List;

import com.tcl.wonder.adclient.dao.AdDAO;
import com.tcl.wonder.adclient.dao.ChannelAdDAO;
import com.tcl.wonder.adclient.dao.impl.DAOFactory;
import com.tcl.wonder.adclient.entity.Ad;

public class AdService
{
	private AdDAO adDAO = DAOFactory.getAdDAO();
	
	private ChannelAdDAO channelAdDAO = DAOFactory.getChannelAdDAO();
	
	public List<Ad> listAd()
	{
		return adDAO.findAll();
	}
	
	public Ad getAdByChannelId(String channelId)
	{
		String adId = channelAdDAO.findAdIdByChannelId(channelId);
		
		return adDAO.findById(adId);
	}
}
