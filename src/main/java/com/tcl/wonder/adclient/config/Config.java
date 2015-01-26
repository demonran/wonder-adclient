/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.tcl.wonder.adclient.config;

import java.util.Properties;

/**
 * 
 * @author liuran
 * 2015Äê1ÔÂ23ÈÕ
 *
 */
public class Config {
	
	private static Properties config = ConfigManager.getInstance().getConfig();

    public static String getString(String key) {
        return config.getProperty(key);
    }

    public static String getString(String key, String def) {
        return  config.getProperty(key)==null? def :  config.getProperty(key);
    }

    public static int getInt(String key) {
        try
		{
			return Integer.valueOf(config.getProperty(key));
		} catch (Exception e)
		{
			return 0;
		}
    }

    public static int getInt(String key, int def) {
    	try
 		{
 			return Integer.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			return def;
 		}
    }

    public static long getLong(String key) {
    	try
 		{
 			return Long.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			return 0;
 		}
    }

    public static long getLong(String key, long def) {
    	try
 		{
 			return Long.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			return def;
 		}
    }

    public static float getFloat(String key) {
    	try
 		{
 			return Float.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			return 0;
 		}
    }

    public static float getFloat(String key, float def) {
    	try
 		{
 			return Float.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			return def;
 		}
    }

    public static double getDouble(String key) {
    	try
 		{
 			return Double.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			return 0;
 		}
    }

    public static double getDouble(String key, double def) {
    	try
 		{
 			return Double.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			return def;
 		}
    }

    public static boolean getBoolean(String key) {
    	try
 		{
 			return Boolean.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			e.printStackTrace();
 			return false;
 		}
    }

    public static boolean getBoolean(String key, boolean def) {
    	try
 		{
 			return Boolean.valueOf(config.getProperty(key));
 		} catch (Exception e)
 		{
 			return def;
 		}
    }


    public static void setProperty(String key, String value) {
    	config.setProperty(key, value);
    }
    
}
