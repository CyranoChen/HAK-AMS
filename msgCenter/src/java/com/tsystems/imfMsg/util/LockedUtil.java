/** 
* @Title: ConfigProperties.java 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com 
* @date 2013-7-2 下午05:01:35 
* @version V1.0 
*/
package com.tsystems.imfMsg.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class LockedUtil {
	public static List<String> apiCallLockedList=new ArrayList<String>();
	public static String sync_Tag="enableSyncParse";
	public static String key_Tag="enableUpdateKeyValue";	
	public static String parse_Tag="enableParseData";	
	private static Properties properties = new Properties();	

	
	private static void init() {
		properties.setProperty(sync_Tag, "1");
		properties.setProperty(key_Tag, "1");		
		properties.setProperty(parse_Tag, "1");				
	}
	
	static {
		init();
	}
	
	public static boolean addApiCallLocked(String apiCallName){
		if(apiCallLockedList.contains(apiCallName)){
			return false;
		}else{
			apiCallLockedList.add(apiCallName);
			return true;
		}
		
	}
	
	public static void cancelApiCallLocked(String apiCallName){
		apiCallLockedList.remove(apiCallName);
	}
	
	public static void cleanApiCallLockedList(){
		apiCallLockedList.clear();
	}
	
	public static String getPropertyValue(String propertyName){
		return properties.getProperty(propertyName);
	}
	
	public static String getTagType(String type){
		try{
			if(type.equalsIgnoreCase("parse")){
				return parse_Tag;
			}else if(type.equalsIgnoreCase("key")){
				return key_Tag;
			}
		}catch(Exception e){
			return sync_Tag;
		}
		return sync_Tag;
	}
		
	public static void setPropertyValue(String propertyName,String propertyValue){
			properties.setProperty(propertyName, propertyValue);
			
	}
}
