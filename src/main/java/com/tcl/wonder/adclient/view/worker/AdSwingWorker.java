package com.tcl.wonder.adclient.view.worker;

import java.util.Map;

import javax.swing.SwingWorker;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.service.AdService;
import com.tcl.wonder.adclient.view.worker.callback.Callback;

public class AdSwingWorker extends SwingWorker<Map<String,Ad>, Void>
{
	private AdService adServer = AdService.getInstance();
	
	private Callback callback;
	
	private String[] params;
	
	public AdSwingWorker(Callback callback,String... params)
	{
		this.callback = callback;
		this.params = params;
	}
	
	@Override
	protected Map<String, Ad> doInBackground() throws Exception
	{
		Map<String,Ad> ads = adServer.mapAd();
		
		return ads;
	}

	@Override
	protected void done()
	{
		try
		{
			Map<String,Ad> adsMap = get();
			callback.call(adsMap);
			if(params != null && params.length == 1)
			{
				callback.call(adsMap.get(params[0]));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	

}
