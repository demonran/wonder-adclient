package com.tcl.wonder.adclient.service;

import java.io.IOException;

import com.tcl.wonder.adclient.utlis.HttpUtils;



public class AdServerClient
{
	String ipPort="localhost:8080";
	String urlPath = "http://" + ipPort + "/WonderAdMatchingServer/api/";
	
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
