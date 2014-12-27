package com.tcl.wonder.adclient.entity;

import java.util.Map;

public class Ad
{
	private String id;

	private String name;

	private String logo;

	private int duration;

	private String info;

	private String videoname;

	public Ad()
	{
	}

	public Ad(String id, String name, String logo, int duration, String info, String videoname)
	{
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.duration = duration;
		this.info = info;
		this.videoname = videoname;
	}

	public Ad(Map<String, String> adMap)
	{
		this(adMap.get("id"), adMap.get("name"), adMap.get("logo"),adMap
				.get("duration") ==null?0:Integer.valueOf(adMap
				.get("duration")), adMap.get("info"), adMap.get("videoname"));
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

	public String getVideoname()
	{
		return videoname;
	}

	public void setVideoname(String videoname)
	{
		this.videoname = videoname;
	}

}
