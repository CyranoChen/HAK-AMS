package com.tsystems.api.listener;


public class EventSource extends java.util.Observable{
	public void notify(String params){
		setChanged();
		notifyObservers(params);
	}

}
