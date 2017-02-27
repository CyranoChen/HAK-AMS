/** 
* @Title: Service1Listener.java 
* @Package com.tsystems.listener 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com 
* @date 2013-11-28 下午04:57:40 
* @version V1.0 
*/
package com.tsystems.api.listener;

import java.util.HashMap;
import java.util.Observable;

public class Service1Listener implements java.util.Observer{
	public void update(Observable o, Object arg) {
		HashMap<String,String> params=(HashMap<String,String>)arg;
		String preService=params.get("preService");
		
	}
}
