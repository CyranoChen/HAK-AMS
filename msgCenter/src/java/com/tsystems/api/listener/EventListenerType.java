
package com.tsystems.api.listener;

import java.util.Observer;

public class EventListenerType {
	private static final String SERVICE_TYPE1="service";
	public static Observer getListener(String type){
		Observer listener=null;
		if(type.equals(SERVICE_TYPE1)){
			listener=new Service1Listener();
		}
		
		return listener;
	}
}
