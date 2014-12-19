package com.tcl.wonder.adclient.entity;

public class Ad
{
	private String id;
	
	private String name;
	
	private String logo;
	
	private int duration;
	
	private String info;
	
	private String videoname;

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
