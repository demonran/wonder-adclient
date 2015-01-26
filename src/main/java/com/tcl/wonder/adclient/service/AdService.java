package com.tcl.wonder.adclient.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tcl.wonder.adclient.dao.AdDAO;
import com.tcl.wonder.adclient.dao.WonderCache;
import com.tcl.wonder.adclient.dao.ChannelAdDAO;
import com.tcl.wonder.adclient.dao.impl.DAOFactory;
import com.tcl.wonder.adclient.entity.Ad;

/**
 * AD数据库操作封装服务类
 * @author liuran
 * 2015年1月23日
 *
 */
public class AdService
{
	private AdDAO adDAO = DAOFactory.getLocalAdDAO();
	
	private ChannelAdDAO channelAdDAO = DAOFactory.getChannelAdDAO();
	
	AdDAO localAdDao  = DAOFactory.getLocalAdDAO();
	
	private static AdService instance;
	
	public static AdService getInstance()
	{
		if(instance == null)
		{
			instance = new AdService();
		}
		return instance;
	}
	
	
	
	public List<Ad> listAd()
	{
		return adDAO.findAll();
	}
	
	public Map<String,Ad> mapAd()
	{
		return adDAO.findAlltoMap();
	}
	
	public Ad getAdByChannelId(String channelId)
	{
		String adId = channelAdDAO.findAdIdByChannelId(channelId);
		
		return adDAO.findById(adId);
	}
	
	public Ad getAdByAdId(String adId)
	{
		Ad ad = WonderCache.getAdById(adId);
		if(ad == null)
		{
			ad = adDAO.findById(adId);
		}
		return ad;
	}
	
	public boolean insertAd(Ad ad)
	{
		return adDAO.add(ad);
	}
	
	public boolean updateAd(Ad ad)
	{
		return adDAO.update(ad);
	}
	
	public boolean deleteAd(Ad ad)
	{
		return adDAO.delete(ad);
	}
	
	public boolean deleteAdById(String id)
	{
		return adDAO.delete(id);
	}
	
	public List<String> getAllAdId()
	{
		List<String> adIds = WonderCache.getAllAdId();
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
	
	public static void main(String[] args)
	{
		AdService adService = AdService.getInstance();
		List<Ad> ads = adService.listAd();
		adService.localAdDao.addAll(ads);
	}
}
