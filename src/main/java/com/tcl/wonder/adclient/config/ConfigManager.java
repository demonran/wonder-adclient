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

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author liuran
 * 2015Äê1ÔÂ23ÈÕ
 *
 */
public class ConfigManager {

    private static final Log log = LogFactory.getLog(ConfigManager.class);

    private static Properties config;

    private static ConfigManager instance;

    private ConfigManager() {
        loadConfig();
    }

    /**
     * Returns the singleton instance of ConfigManger.
     * 
     * @return the instance
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                instance = new ConfigManager();
            }
        }
        return instance;
    }

    /**
     * Loads the default configuration file.
     */
    public void loadConfig() {
        loadConfig("conf/config.properties");
    }

    /**
     * Loads the specific configuration file.
     * 
     * @param configFileName the file name
     */
    public void loadConfig(String configFileName) {
        try {
//        	config = new PropertyResourceBundle(new FileInputStream(configFileName));        
        	config = new Properties();
        	config.load(new FileInputStream(configFileName));
            log.info("Configuration loaded: " + configFileName);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Configuration loading error: "
                    + configFileName, ex);
        }
    }

    /**
     * Returns the loaded configuration object.
     * 
     * @return the configuration
     */
    public Properties getConfig() {
        return config;
    }

}
