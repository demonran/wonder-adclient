package com.tcl.wonder.adclient.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.tcl.wonder.adclient.utlis.Utilities;

public class DBConnectionPool
{
	private Connection con = null;
	private int inUsed = 0; // ʹ�õ�������
	private ArrayList<NConnection> freeConnections = new ArrayList<NConnection>();// ��������������
	private int minConn; // ��С������
	private int maxConn; // �������
	private String name; // ���ӳ�����
	private String password; // ����
	private String url; // ���ݿ����ӵ�ַ
	private String driver; // ����
	private String user; // �û���
	public Timer timer; // ��ʱ
	private String type; //���ݿ�����
	private String host;

	/** 
  * 
  */
	public DBConnectionPool()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * �������ӳ�
	 * 
	 * @param driver
	 * @param name
	 * @param URL
	 * @param user
	 * @param password
	 * @param maxConn
	 */
	public DBConnectionPool(String name, String driver, String URL, String user, String password,
			int maxConn)
	{
		this.name = name;
		this.driver = driver;
		this.url = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}

	/**
	 * ���꣬�ͷ�����
	 * 
	 * @param con
	 */
	public synchronized void freeConnection(NConnection con)
	{
		this.freeConnections.add(con);// ��ӵ��������ӵ�ĩβ
		this.inUsed--;
	}

	/**
	 * timeout ����timeout�õ�����
	 * 
	 * @param timeout
	 * @return
	 */
	public synchronized NConnection getConnection(long timeout)
	{
		NConnection con = null;
		if (this.freeConnections.size() > 0)
		{
			con = (NConnection) this.freeConnections.get(0);
			if (con == null)
				con = getConnection(timeout); // �����������
		} else
		{
			while(timeout - 1000 >0)
			{
				Utilities.sleep(1000);
				con = newConnection(); // �½�����
			}
			
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed)
		{
			con = null;// �ﵽ�������������ʱ���ܻ�������ˡ�
		}
		if (con != null)
		{
			this.inUsed++;
		}
		return con;
	}

	/**
	 * 
	 * �����ӳ���õ�����
	 * 
	 * @return
	 */
	public synchronized NConnection getConnection()
	{
		NConnection con = null;
		
		if (this.freeConnections.size() > 0)
		{
			con =  freeConnections.remove(0);// ������ӷ����ȥ�ˣ��ʹӿ���������ɾ��
			if (con == null)
				con = getConnection(); // �����������
		} 
		else
		{
			Utilities.sleepSecond(1);
			con = newConnection(); // �½�����
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed)
		{
			con = null;// �ȴ� �����������ʱ
		}
		if (con != null)
		{
			this.inUsed++;
			System.out.println("�õ���" + this.name + "�������ӣ�����" + inUsed + "��������ʹ��!");
		}
			
		
		return con;
		
	}

	/**
	 * �ͷ�ȫ������
	 * 
	 */
	public synchronized void release()
	{
		Iterator<NConnection> allConns = this.freeConnections.iterator();
		while (allConns.hasNext())
		{
			NConnection con =  allConns.next();
			try
			{
				con.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}

		}
		this.freeConnections.clear();

	}

	/**
	 * ����������
	 * 
	 * @return
	 */
	private NConnection newConnection()
	{
		if("redis".equals(type))
		{
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxTotal(1000);
			jedisPoolConfig.setMinIdle(100);
			jedisPoolConfig.setTestOnBorrow(true);
			jedisPoolConfig.setMaxWaitMillis(300000);
			
			JedisShardInfo shardInfo = new JedisShardInfo(host, 6379, 300000,"instance:01");
			
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			shards.add(shardInfo);
			
			ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,shards);
			ShardedJedis sharedJedis = shardedJedisPool.getResource();
			
			return new NConnection(sharedJedis);
			
		}else
		{
			try
			{
				Class.forName(driver);
				con = DriverManager.getConnection(url, user, password);
				
			} catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				System.out.println("sorry can't find db driver!");
			} catch (SQLException e1)
			{
				e1.printStackTrace();
				System.out.println("sorry can't create Connection!");
			}
			return new NConnection(con);
		}
		
		

	}

	/**
	 * @return the driver
	 */
	public String getDriver()
	{
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	/**
	 * @return the maxConn
	 */
	public int getMaxConn()
	{
		return maxConn;
	}

	/**
	 * @param maxConn
	 *            the maxConn to set
	 */
	public void setMaxConn(int maxConn)
	{
		this.maxConn = maxConn;
	}

	/**
	 * @return the minConn
	 */
	public int getMinConn()
	{
		return minConn;
	}

	/**
	 * @param minConn
	 *            the minConn to set
	 */
	public void setMinConn(int minConn)
	{
		this.minConn = minConn;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the user
	 */
	public String getUser()
	{
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user)
	{
		this.user = user;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}
	
	
	
	
}
