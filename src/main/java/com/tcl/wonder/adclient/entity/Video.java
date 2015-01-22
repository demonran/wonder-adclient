package com.tcl.wonder.adclient.entity;

public class Video
{
	
	public enum Status
	{
		NO_START,STARTED,SUCCESS,FAILED
	}
	
	private String id;
	
	private String name;
	
	private String path;
	
	private Status status;
	
	private String metaPath;
	
	public Video()
	{
		status = Status.NO_START;
	}

	public Video(String id, String name, String path, Status status)
	{
		this.id = id;
		this.name = name;
		this.path = path;
		this.status = status;
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

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public String getMetaPath()
	{
		return metaPath;
	}

	public void setMetaPath(String metaPath)
	{
		this.metaPath = metaPath;
	}
	
}
