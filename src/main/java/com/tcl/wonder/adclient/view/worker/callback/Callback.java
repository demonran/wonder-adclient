package com.tcl.wonder.adclient.view.worker.callback;

import java.util.Map;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.entity.Video;


public interface Callback
{
	public void call(Map<String,Ad> map);
	
	public void call(Ad ad);

	void call(Video video);
	
}
