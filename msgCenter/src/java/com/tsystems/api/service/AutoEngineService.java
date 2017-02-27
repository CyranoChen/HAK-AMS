package com.tsystems.api.service;

import java.util.Observer;

import com.tsystems.api.listener.EventListener;
import com.tsystems.api.listener.EventSource;

public class AutoEngineService {
	public static void startNextService(String actionUrl,String nextApis,String params){
		String[] apis=nextApis.split(",");
		EventSource es=new com.tsystems.api.listener.EventSource();
		for(int i=apis.length-1;i>=0;i--){
			Observer el=new EventListener(apis[i],actionUrl);
			es.addObserver(el);
		}
		
		es.notify(params);
	}
}
