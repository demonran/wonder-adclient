package com.tcl.wonder.adclient.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * AD实体类
 * @author liuran
 * 2015年1月23日
 *
 */
public class Ad implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String logo;

	private int duration;

	private String info;

	private Date updatetime;

	public Ad()
	{
	}
	
	

	public Ad(String id, String name, String logo, int duration, String info)
	{
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.duration = duration;
		this.info = info;
	}



	public Ad(String id, String name, String logo, int duration, String info, Date updatetime)
	{
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.duration = duration;
		this.info = info;
		this.updatetime = updatetime;
	}

	public Ad(Map<String, String> adMap)
	{
		this(adMap.get("id"), adMap.get("name"), adMap.get("logo"),adMap
				.get("duration") ==null?0:Integer.valueOf(adMap
				.get("duration")), adMap.get("info"), new Date(Long.valueOf(adMap.get("updatetime"))));
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLogo()
	{
		return logo;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}



	public Date getUpdatetime()
	{
		return updatetime;
	}

	public void setUpdatetime(Date updatetime)
	{
		this.updatetime = updatetime;
	}

	@Override
	public String toString()
	{
		return "Ad [id=" + id + ", name=" + name + ", logo=" + logo + ", duration=" + duration
				+ ", info=" + info + ", updatetime=" + updatetime + "]";
	}



	
	
}
