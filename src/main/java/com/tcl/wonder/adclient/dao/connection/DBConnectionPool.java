package com.tcl.wonder.adclient.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

import com.tcl.wonder.adclient.utlis.Utilities;

public class DBConnectionPool
{
	private Connection con = null;
	private int inUsed = 0; // ʹ�õ�������
	private ArrayList<Connection> freeConnections = new ArrayList<Connection>();// ��������������
	private int minConn; // ��С������
	private int maxConn; // �������
	private String name; // ���ӳ�����
	private String password; // ����
	private String url; // ���ݿ����ӵ�ַ
	private String driver; // ����
	private String user; // �û���
	public Timer timer; // ��ʱ

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
	public synchronized void freeConnection(Connection con)
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
	public synchronized Connection getConnection(long timeout)
	{
		Connection con = null;
		if (this.freeConnections.size() > 0)
		{
			con = (Connection) this.freeConnections.get(0);
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
	public synchronized Connection getConnection()
	{
		Connection con = null;
		if (this.freeConnections.size() > 0)
		{
			con = (Connection) this.freeConnections.get(0);
			this.freeConnections.remove(0);// ������ӷ����ȥ�ˣ��ʹӿ���������ɾ��
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
		Iterator<Connection> allConns = this.freeConnections.iterator();
		while (allConns.hasNext())
		{
			Connection con = (Connection) allConns.next();
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
	private Connection newConnection()
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
		return con;

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
}
