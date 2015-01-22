package com.tcl.wonder.adclient.view.worker;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.entity.Video;
import com.tcl.wonder.adclient.entity.Video.Status;
import com.tcl.wonder.adclient.service.AdUploadService;
import com.tcl.wonder.adclient.view.worker.callback.Callback;

public class UploadSwingWorker extends SwingWorker<Boolean, Integer>
{
	private Video video;
	
	private Ad ad;
	
	private Callback callback;
	
	private AdUploadService service = AdUploadService.getInstance();
	
	

	public UploadSwingWorker(Video video, Ad ad,Callback callback)
	{
		this.video = video;
		this.ad = ad;
		this.callback = callback;
	}

	@Override
	protected Boolean doInBackground() throws Exception
	{
		return service.upload(video,ad);
	}


	@Override
	protected void done()
	{
		try
		{
			boolean success = get();
			
			if(success)
			{
				video.setStatus(Status.SUCCESS);
			}
			else
			{
				video.setStatus(Status.FAILED);
			}
			
			callback.call(video);
			
		} catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
	
	

}
