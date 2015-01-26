package com.tcl.wonder.adclient.log4j;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;

public class WonderLog4jAppender extends FileAppender
{
	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO,
			int bufferSize) throws IOException
	{
		String newFile = getWriteFile(fileName);
		if(!newFile.equals(fileName))
		{
			new File(fileName).renameTo(new File(newFile));
		}
//		int pointIndex = fileName.lastIndexOf('.');
//		fileName = fileName.substring(0,pointIndex)+"_"+getDate()+fileName.substring(pointIndex);
		super.setFile(fileName, append, bufferedIO, bufferSize);
	}
	
	

	/**
	 * 根据日志的名字获取日志文件
	 * @param loggerName
	 * @return
	 */
	public String getWriteFile(String loggerName){
		File file=null;
		file=new File(loggerName);
		if(file.exists()){
			int filenum= 1;
			if(loggerName.length() > 21)
			{
				filenum=Integer.parseInt(loggerName.substring(22,25))+1;
			}
			
			String num=null;
			if(filenum<10){
				num="00"+filenum;
			}else if(filenum<100){
				num="0"+filenum;
			}else{
				num=""+filenum;
			}
			String newName=loggerName.substring(0,21)+"."+num;
			loggerName=getWriteFile(newName);
		}
		return loggerName;
	}
	
	/**
	 * 获取当日的日期
	 * @return
	 */
	public String  getDate(){
		Date date=new Date();
		SimpleDateFormat myDateFmt=new SimpleDateFormat("yyyy-MM-dd");
		return myDateFmt.format(date);
	}
	
	
}
