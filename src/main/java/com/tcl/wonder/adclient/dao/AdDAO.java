package com.tcl.wonder.adclient.dao;

import java.util.List;

import com.tcl.wonder.adclient.entity.Ad;

public interface AdDAO
{
	boolean update(Ad ad);
	
	boolean add(Ad ad);
	
	boolean delete(Ad ad);
	
	List<Ad> findAll();
	
	Ad findById(String id);
}
