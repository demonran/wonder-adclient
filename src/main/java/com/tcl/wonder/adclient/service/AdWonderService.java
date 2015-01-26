package com.tcl.wonder.adclient.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.config.Config;
import com.tcl.wonder.adclient.config.ConfigManager;
import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.entity.Video;
import com.tcl.wonder.adclient.utlis.HttpUtils;
import com.tcl.wonder.adclient.utlis.Utilities;


/**
 * AD服务类，实现事务性上传删除。
 * @author liuran
 * 2015年1月23日
 *
 */
public class AdWonderService
{
	
	
	private static Logger logger = LoggerFactory.getLogger(AdWonderService.class);
	
	private static  AdWonderService instance;
	
	private AdService adService;
	
	String urlPath ;
	
	public AdWonderService()
	{
		urlPath = Config.getString("adServerApi");
		adService = AdService.getInstance();
	}
	
	public static AdWonderService getInstance()
	{
		if(instance == null){
			synchronized (ConfigManager.class) {
				logger.info("AdUploadService initialized...");
				instance = new AdWonderService();
			}
		}
		return instance;
	}
	
	public boolean isAdExist(Ad ad)
	{
		return isAdExitst(ad.getId());
	}
	
	public boolean isAdExitst(String adId)
	{
		return adService.getAdByAdId(adId)!= null;
		
	}
	
	public synchronized boolean upload(Video video,Ad ad)
	{
		boolean success = adService.insertAd(ad);
		logger.info("insert {} into database successed",video.getId());
		if(success)
		{
			//上传指纹到adServer
			String videopath = video.getPath();
			File videoFile = new File(videopath);
			String filename =  new File(videopath).getName();
			String adsName =filename.substring(0, filename.indexOf("."));
			String meta = adsName+".txt";
		
			File metafile = new File(videoFile.getParent() +"/"+meta);
			
			
			if(!metafile.exists())
			{
				PrintWriter pw = null;
				try {
					metafile.createNewFile();
					FileOutputStream fos = new FileOutputStream(metafile);
					pw = new PrintWriter(fos);
					pw.println("adsName="+adsName);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					pw.close();
				}
			 }
			String metaPath = metafile.getAbsolutePath();
			success = upload(videopath, metaPath);
			//上传失败则回退数据库信息
			if(!success)
			{
				logger.info("upload to ad server failed,begin rollbacking the database");
				adService.deleteAd(ad);
			}else
			{
				logger.info("upload {} to ad server successed",video.getId());
			}
			
		}else
		{
			logger.info("add ad to database unsuccessfully");
		}
		Utilities.sleep(1000);
		return success;
	}
	
	public boolean upload(String video,String metafile)
	{
		try
		{
			HttpUtils.postFile(urlPath+"match/upload", video, metafile);
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
//	 public static void main(String[] args) throws IOException {
//		   if ((args.length < 1) || (args[0] == "-help")) {
//		      System.out.println("Usage: java -jar RestfulClient.jar <list>");
//		       System.out.println("                                   <delete> <ID>");
//		      System.out.println("                                   <upload> <video filepath> <metafilepath>");
//		    }
//	    else
//		    {
//		      String ipPort = args[0];
//	      String tempUrlString = "http://" + ipPort + "/WonderAdMatchingServer/api/";
//		       urlString = tempUrlString;
//	       if (args[1].equals("list")) {
//	        listAll();
//	       }
//		     else if (args[1].equals("delete")) {
//		       if (args.length < 3) {
//		          System.out.println("Usage: java -jar RestfulClient.jar delete <ID>");
//		       } else {
//		           String ID = args[2];
//	          deleteRequest(ID);
//		        }
//		     } else if (args[1].equals("upload")) {
//		         if (args.length < 4) {
//		         System.out.println("Usage: java -jar RestfulClient.jar upload <videofilepath> <metafilepath>");
//		        } else {
//		          String video = args[2];
//	          String metafile = args[3];
//	         postUploadRequest(video, metafile);
//		         }
//		     }
//		          }
//		        }

	public boolean delete(Ad ad)
	{
		boolean success = adService.deleteAd(ad);
		if(success)
		{
			//do someting to delete the ad wonder server
		}
		return success;
		
	}

		        
		      
}
