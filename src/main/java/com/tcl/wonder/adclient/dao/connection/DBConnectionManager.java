package com.tcl.wonder.adclient.dao.connection;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
/** 
* @author chenyanlin 
* 
*/ 
public class DBConnectionManager { 
	static private DBConnectionManager instance;//Ψһ���ݿ����ӳع���ʵ���� 
	private Vector<DSConfigBean> drivers  = new Vector<DSConfigBean>();//������Ϣ 
	private Hashtable<String,DBConnectionPool> pools=new Hashtable<String,DBConnectionPool> ();//���ӳ� 
	
	/** 
	  * ʵ���������� 
	  */ 
	public DBConnectionManager() { 
	  this.init(); 
	} 
	/** 
	  * �õ�Ψһʵ�������� 
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
	  * �ͷ����� 
	  * @param name 
	  * @param con 
	  */ 
	public void freeConnection(String name, Connection con) 
	{ 
	  DBConnectionPool pool=(DBConnectionPool)pools.get(name);//���ݹؼ����ֵõ����ӳ� 
	  if(pool!=null) 
	  pool.freeConnection(con);//�ͷ����� 
	} 
	/** 
	  * �õ�һ�����Ӹ������ӳص�����name 
	  * @param name 
	  * @return 
	  */ 
	public Connection getConnection(String name) 
	{ 
	  DBConnectionPool pool=null; 
	  Connection con=null; 
	  pool=(DBConnectionPool)pools.get(name);//�������л�ȡ���ӳ� 
	  con=pool.getConnection();//��ѡ�������ӳ��л������ 
	  if(con!=null) 
	  System.out.println("�õ����ӡ�����"); 
	  return con; 
	} 
	/** 
	  * �õ�һ�����ӣ��������ӳص����ֺ͵ȴ�ʱ�� 
	  * @param name 
	  * @param time 
	  * @return 
	  */ 
	public Connection getConnection(String name, long timeout) 
	{ 
	  DBConnectionPool pool=null; 
	  Connection con=null; 
	  pool=(DBConnectionPool)pools.get(name);//�������л�ȡ���ӳ� 
	  con=pool.getConnection(timeout);//��ѡ�������ӳ��л������ 
	  System.out.println("�õ����ӡ�����"); 
	  return con; 
	} 
	/** 
	  * �ͷ��������� 
	  */ 
	public synchronized void release() 
	{ 
	  Enumeration<DBConnectionPool> allpools=pools.elements(); 
	  while(allpools.hasMoreElements()) 
	  { 
	   DBConnectionPool pool=(DBConnectionPool)allpools.nextElement(); 
	   if(pool!=null)pool.release(); 
	  } 
	  pools.clear(); 
	} 
	/** 
	  * �������ӳ� 
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
	  System.out.println("ioio:"+dsb.getMaxconn()); 
	  pools.put(dsb.getName(), dbpool); 
	} 
	/** 
	  * ��ʼ�����ӳصĲ��� 
	  */ 
	private void init() 
	{ 
	  //������������ 
	  this.loadDrivers(); 
	  //�������ӳ� 
	  Iterator<DSConfigBean> alldriver=drivers.iterator(); 
	  while(alldriver.hasNext()) 
	  { 
	   this.createPools((DSConfigBean)alldriver.next()); 
	   System.out.println("�������ӳء�����"); 
	   
	  } 
	  System.out.println("�������ӳ���ϡ�����"); 
	} 
	/** 
	  * ������������ 
	  * @param props 
	  */ 
	private void loadDrivers() 
	{ 
	  ParseDSConfig pd=new ParseDSConfig(); 
	//��ȡ���ݿ������ļ� 
	  drivers=pd.readConfigInfo("ds-config.xml"); 
	  System.out.println("�����������򡣡���"); 
	} 
}