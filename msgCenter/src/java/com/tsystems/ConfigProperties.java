/***********************************************************************
 *                                                                     *
 *         Copyright (c) Ultra Electronics Airport Systems 2006        *
 *                         All rights reserved                         *
 *                                                                     *
 ***********************************************************************
 *
 * Filename:           TestClientProperties
 * Module Name:        
 * Subsystem Name:
 * Originating Author: paul.crisp
 * Design Reference:
 *
 ***********************************************************************
 *
 * Description:
 * Data Access class to access properties file for Test client
 * 
 ***********************************************************************
 * Modification History
 *
 * $Log: TestClientProperties.java,v $
 * Revision 1.1  2009/11/19 09:45:43  richard.stokes
 * Initial checkin
 *
 * Revision 1.1  2008/10/28 10:57:48  daniel.williams
 * Imported from PVGSI
 *
 * Revision 1.1  2008/10/23 13:47:03  daniel.williams
 * Imported from PVGSI
 *
 * Revision 1.1  2008/10/22 10:36:01  daniel.williams
 * Imported from PVGSI
 *
 * Revision 1.4  2006/12/08 14:52:25  paul.crisp
 * CA/033  updated test client so can change status request / response timeouts
 *
 * Revision 1.3  2006/11/29 11:48:51  simon.souter
 * Connection made more robust
 * Now supports multiple URLs.
 * Retries failed connections.
 *
 * Revision 1.2  2006/11/16 13:22:55  paul.crisp
 * CA|1904 AFDS service fixes so works with GXI
 *
 * Revision 1.1  2006/11/15 10:29:27  paul.crisp
 * New Test Client to run normal running test script for any subscription based services - e.g. AFDS, BDDS, RDDS
 *
 * Revision 1.1  2006/11/15 10:11:23  paul.crisp
 * New Test Client to run normal running test script for any subscription based services - e.g. AFDS, BDDS, RDDS
 *
 *
 ***********************************************************************/
package com.tsystems;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data Access class to access properties file for Test client
 * 
 * @author paul.crisp
 * @version $Revision: 1.1 $ $Date: 2009/11/19 09:45:43 $
 */
public class ConfigProperties {
	private static final Logger log = LoggerFactory.getLogger(ConfigProperties.class);

	private static final Properties properties=new Properties();
	
	public static final String Login_Session = "login.session";
	public static final String Login_User = "login.user";
	public static final String Login_Password = "login.password";

	
	static{
		try {
			InputStream inputStream = ConfigProperties.class.getResourceAsStream("/config.properties");
			properties.load(inputStream);
			inputStream.close();

		} catch (IOException e) {
			log.debug("Exception in ConfigProperties:{} ", e.getMessage());
		}
	}



	public static String getProperty(String key) {
		String value = properties.getProperty(key);

		if (value == null || value.length() == 0) {
			log.debug(key + " in config.properties not found.");
			value = null;
		}
		return value;
	}

}