package com.tcl.wonder.adclient.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tcl.wonder.adclient.entity.Ad;
import com.tcl.wonder.adclient.entity.Video;
import com.tcl.wonder.adclient.utlis.HttpUtils;
import com.tcl.wonder.adclient.utlis.Utilities;



public class AdUploadService
{
	String ipPort="localhost:8080";
	String urlPath = "http://" + ipPort + "/WonderAdMatchingServer/api/";
	
	private static Logger logger = LoggerFactory.getLogger(AdUploadService.class);
	
	private static  AdUploadService instance;
	
	private AdService adService = AdService.getInstance();
	
	public static AdUploadService getInstance()
	{
		if(instance == null)
		{
			logger.info("AdUploadService initialized...");
			instance = new AdUploadService();
		}
		return instance;
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
			String adsName =  new File(videopath).getName();
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

		        
		      
}
