package com.tcl.wonder.adclient.dao.connection;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
/**
 * @author liuran
 * 2015年1月22日
 *
 */
public class DBConnectionManager { 
	static private DBConnectionManager instance;//唯一数据库连接池管理实例类 
	private Vector<DSConfigBean> drivers  = new Vector<DSConfigBean>();//驱动信息 
	private Hashtable<String,DBConnectionPool> pools=new Hashtable<String,DBConnectionPool> ();//连接池 
	
	/** 
	  * 实例化管理类 
	  */ 
	public DBConnectionManager() { 
	  this.init(); 
	} 
	/** 
	  * 得到唯一实例管理类 
	  * @return 
	  */ 
	static synchronized public DBConnectionManager getInstance() 
	{ 
	  if(instance==null) 
	  { 
	   instance=new DBConnectionManager(); 
	  } 
	  return instance; 
	  
	} 
	/** 
	  * 释放连接 
	  * @param name 
	  * @param con 
	  */ 
	public void freeConnection(String name, NConnection con) 
	{ 
	  DBConnectionPool pool=pools.get(name);//根据关键名字得到连接池 
	  if(pool!=null) 
	  pool.freeConnection(con);//释放连接 
	} 
	/** 
	  * 得到一个连接根据连接池的名字name 
	  * @param name 
	  * @return 
	  */ 
	public NConnection getConnection(String name) 
	{ 
	  DBConnectionPool pool=null; 
	  NConnection con=null; 
	  pool=pools.get(name);//从名字中获取连接池 
	  con=pool.getConnection();//从选定的连接池中获得连接 
	  if(con!=null) 
	  System.out.println("得到连接。。。"); 
	  return con; 
	} 
	/** 
	  * 得到一个连接，根据连接池的名字和等待时间 
	  * @param name 
	  * @param time 
	  * @return 
	  */ 
	public NConnection getConnection(String name, long timeout) 
	{ 
	  DBConnectionPool pool=null; 
	  NConnection con=null; 
	  pool=pools.get(name);//从名字中获取连接池 
	  con=pool.getConnection(timeout);//从选定的连接池中获得连接 
	  System.out.println("得到连接。。。"); 
	  return con; 
	} 
	/** 
	  * 释放所有连接 
	  */ 
	public synchronized void release() 
	{ 
	  Enumeration<DBConnectionPool> allpools=pools.elements(); 
	  while(allpools.hasMoreElements()) 
	  { 
	   DBConnectionPool pool=allpools.nextElement(); 
	   if(pool!=null)pool.release(); 
	  } 
	  pools.clear(); 
	} 
	/** 
	  * 创建连接池 
	  * @param props 
	  */ 
	private void createPools(DSConfigBean dsb) 
	{ 
	  DBConnectionPool dbpool=new DBConnectionPool(); 
	  dbpool.setName(dsb.getName()); 
	  dbpool.setDriver(dsb.getDriver()); 
	  dbpool.setUrl(dsb.getUrl()); 
	  dbpool.setUser(dsb.getUsername()); 
	  dbpool.setPassword(dsb.getPassword()); 
	  dbpool.setMaxConn(dsb.getMaxconn()); 
	  dbpool.setType(dsb.getType());
	  dbpool.setHost(dsb.getHost());
	  System.out.println("ioio:"+dsb.getMaxconn()); 
	  pools.put(dsb.getName(), dbpool); 
	} 
	/** 
	  * 初始化连接池的参数 
	  */ 
	private void init() 
	{ 
	  //加载驱动程序 
	  this.loadDrivers(); 
	  //创建连接池 
	  Iterator<DSConfigBean> alldriver=drivers.iterator(); 
	  while(alldriver.hasNext()) 
	  { 
	   this.createPools(alldriver.next()); 
	   System.out.println("创建连接池。。。"); 
	   
	  } 
	  System.out.println("创建连接池完毕。。。"); 
	} 
	/** 
	  * 加载驱动程序 
	  * @param props 
	  */ 
	private void loadDrivers() 
	{ 
	  ParseDSConfig pd=new ParseDSConfig(); 
	//读取数据库配置文件 
	  drivers=pd.readConfigInfo("conf/ds-config.xml"); 
	  System.out.println("加载驱动程序。。。"); 
	} 
}