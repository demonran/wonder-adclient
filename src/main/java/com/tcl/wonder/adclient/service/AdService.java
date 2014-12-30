package com.tcl.wonder.adclient.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tcl.wonder.adclient.dao.AdDAO;
import com.tcl.wonder.adclient.dao.Cache;
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
	
	public boolean insertAd(Ad ad)
	{
		return adDAO.add(ad);
	}
	
	public boolean deleteAd(Ad ad)
	{
		return adDAO.delete(ad);
	}
	
	public List<String> getAllAdId()
	{
		List<String> adIds = Cache.getAllAdId();
		if(adIds == null)
		{
			List<Ad> ads = adDAO.findAll();
			if(ads == null)
			{
				return Collections.emptyList();
			}else
			{
				adIds = new ArrayList<String>();
				for(Ad ad : ads)
				{
					adIds.add(ad.getId());
				}
			}
		}
		return adIds;
		
	}
}
