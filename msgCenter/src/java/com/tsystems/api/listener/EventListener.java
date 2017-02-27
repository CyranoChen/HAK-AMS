
package com.tsystems.api.listener;

import java.util.HashMap;
import java.util.Observable;

import com.tsystems.api.service.RestAPIService;


public class EventListener implements java.util.Observer{
	private String apiId;
	private String actionUrl;
	public EventListener(String apiId,String actionUrl){
		this.apiId=apiId;
		this.actionUrl=actionUrl;
	}
	public void update(Observable o, Object arg) {
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("id", this.apiId);
		hm.put("preInitParams", (String)arg);
		//String url="http://localhost:8081/msgCenter/autoEngine/runService.action";
		String result=RestAPIService.getData(this.actionUrl, hm);		
		
	}

}
