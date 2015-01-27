package com.tcl.wonder.adclient.utlis;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

/**
 * HTTP请求工具类
 * @author liuran
 * 2015年1月23日
 *
 */
public class HttpUtils
{

 
  public static String urlString = "http://localhost:8080/WonderAdMatchingServer/api/";
  
	      
	      
	    public static String get(String urlString) throws IOException
	      {
	      URL url = new URL(urlString);
	      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	      InputStream inStream = null;
	      ByteArrayOutputStream out = null;
	     String result = null;
	          
	     conn.setDoOutput(true);
	     conn.setDoInput(true);
	     conn.setRequestMethod("GET");
	     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     conn.setRequestProperty("charset", "utf-8");
	     conn.setUseCaches(false);
	          
	      
	     int responseCode = conn.getResponseCode();
	     if (responseCode == 200) {
	       inStream = conn.getInputStream();
	       out = new ByteArrayOutputStream();
	            
	       int len = -1;
	       byte[] buffer = new byte['?'];
	       while ((len = inStream.read(buffer)) != -1) {
	         out.write(buffer, 0, len);
	            }
	            
	       out.flush();
	       result = out.toString();
	       
//	       convertToObject(result);
	          }
	     return result;
	        }
	        
	      
//	    public static File getFile(String urlString) throws IOException
//	      {
//	      URL url = new URL(urlString);
//	      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//	      InputStream inStream = null;
//	      OutputStream out = null;
//	     String result = null;
//	          
//	     conn.setDoOutput(true);
//	     conn.setDoInput(true);
//	     conn.setRequestMethod("GET");
//	     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//	     conn.setRequestProperty("charset", "utf-8");
//	     conn.setUseCaches(false);
//	          
//	      
//	     int responseCode = conn.getResponseCode();
//	     if (responseCode == 200) {
//	       inStream = conn.getInputStream();
//	       out = new FileOutputStream();
//	            
//	       int len = -1;
//	       byte[] buffer = new byte['?'];
//	       while ((len = inStream.read(buffer)) != -1) {
//	         out.write(buffer, 0, len);
//	            }
//	            
//	       out.flush();
//	       result = out.toString();
//	       
//	          }
//	     return result;
//	        }
	        
	        
	      
    public static boolean postFile(String urlPath,String videoFile, String metaFile)throws IOException
	{
	     URL url = new URL(urlPath);
	     HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	     conn.setConnectTimeout(10000);
	     conn.setDoOutput(true);
	     conn.setRequestMethod("POST");
	          
	     FileBody fileBody = new FileBody(new File(videoFile));
	     FileBody mfileBody = new FileBody(new File(metaFile));
	     MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT);
	     multipartEntity.addPart("video", fileBody);
	     multipartEntity.addPart("metadata", mfileBody);
	     conn.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());
	          
	     OutputStream out = conn.getOutputStream();
	     multipartEntity.writeTo(out);
	          
	     int responseCode = conn.getResponseCode();
	     System.out.println(responseCode);
	     if (responseCode == 200) {
	       System.out.println("Upload Successful!");
	       return true;
	     }
	     else {
	       System.out.println("Upload Fail!");
	       return false;
	     }
    }
	        
	      
	        public static void post(String urlPath,NameValuePair[] paras)
	          throws IOException
	        {
	     URL url = new URL(urlPath);
	     HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	     conn.setDoOutput(true);
	     conn.setDoInput(true);
	     String urlParameters = "";
	     for(NameValuePair pair :paras )
	     {
	    	 if(!urlParameters.isEmpty())
	    	 {
	    		 urlParameters += "&";
	    	 }
	    	 urlParameters+= pair.getName()+"="+pair.getValue();
	     }
	     
	     conn.setRequestMethod("POST");
	     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     conn.setRequestProperty("charset", "utf-8");
	     conn.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
	     conn.setUseCaches(false);
	          
	     DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	     wr.writeBytes(urlParameters);
	     wr.flush();
	     wr.close();
	     int responseCode = conn.getResponseCode();
	     System.out.println(responseCode);
	        }
	        
	      
	        public static void deleteRequest(String videoFile)
	          throws IOException
	        {
	     URL url = new URL(urlString + "match/delete");
	     HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	     conn.setDoOutput(true);
	     conn.setDoInput(true);
	     String urlParameters = "id=" + videoFile;
	     conn.setRequestMethod("POST");
	     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     conn.setRequestProperty("charset", "utf-8");
	     conn.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
	     conn.setUseCaches(false);
	          
	     DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	     wr.writeBytes(urlParameters);
	     wr.flush();
	     wr.close();
	     int responseCode = conn.getResponseCode();
	     if (responseCode == 200) {
	       System.out.println("Delete Successful!");
	          } else {
	       System.out.println("Fail to delete!");
	          }
	        }
	        
	        
	        public static void searchID(String keyword) throws IOException {
	     URL url = new URL(urlString + "match/search");
	     HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	     InputStream inStream = null;
	     ByteArrayOutputStream out = null;
	     String result = null;
	          
	     conn.setDoOutput(true);
	     conn.setDoInput(true);
	     String urlParameters = "keyword=" + keyword;
	     conn.setRequestMethod("POST");
	     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     conn.setRequestProperty("charset", "utf-8");
	     conn.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
	     conn.setUseCaches(false);
	          
	     DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	     wr.writeBytes(urlParameters);
	     wr.flush();
	     wr.close();
	     int responseCode = conn.getResponseCode();
	     if (responseCode == 200) {
	       inStream = conn.getInputStream();
	       out = new ByteArrayOutputStream();
	            
	       int len = -1;
	       byte[] buffer = new byte['?'];
	       while ((len = inStream.read(buffer)) != -1) {
	         out.write(buffer, 0, len);
	            }
	            
	       out.flush();
	       result = out.toString();
	       System.out.println(result);
	          }
	        }
	        
	        public static void listDetail(String id)
	          throws IOException
	        {
	       URL url = new URL(urlString + "match/list");
	       HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	       InputStream inStream = null;
	       ByteArrayOutputStream out = null;
	       String result = null;
	          
	       conn.setDoOutput(true);
	       conn.setDoInput(true);
	       String urlParameters = "id=" + id;
	       conn.setRequestMethod("POST");
	       conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	       conn.setRequestProperty("charset", "utf-8");
	       conn.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
	       conn.setUseCaches(false);
	          
	       DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	       wr.writeBytes(urlParameters);
	       wr.flush();
	       wr.close();
	       int responseCode = conn.getResponseCode();
	       if (responseCode == 200) {
	         inStream = conn.getInputStream();
	         out = new ByteArrayOutputStream();
	            
	         int len = -1;
	         byte[] buffer = new byte['?'];
	         while ((len = inStream.read(buffer)) != -1) {
	           out.write(buffer, 0, len);
	            }
	            
	         out.flush();
	         result = out.toString();
	         System.out.println(result);
	          }
	        }
	        
	      
	      
	      
//	        private static void convertToObject(String line)
//	        {
//	       ObjectMapper mapper = new ObjectMapper();
//	          
//	          try
//	          {
//	         HashMap<String, MetaInfo> mr = (HashMap)mapper.readValue(line, HashMap.class);
//	            
//	         Collection coll = mr.keySet();
//	         Iterator it = coll.iterator();
//	         while (it.hasNext()) {
//	           String temp = (String)it.next();
//	           System.out.println(temp + ":");
//	           System.out.println(mr.get(temp));
//	            }
//	          }
//	          catch (JsonGenerationException e)
//	          {
//	         e.printStackTrace();
//	          } catch (JsonMappingException e) {
//	         e.printStackTrace();
//	          } catch (IOException e) {
//	         e.printStackTrace();
//	          }
//	        }
	      }