package com.tsystems.imfMsg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsystems.imfMsg.service.ImfClientService;
import com.tsystems.imfMsg.util.AsynWorkUtil;

public class ImfMessageCache {

	private static final Logger log = LoggerFactory
			.getLogger(ImfMessageCache.class);

	// save received messages into cache by messageType
	private static List<String> fssMessageTmpCache = java.util.Collections
			.synchronizedList(new LinkedList<String>());
	
	private static List<String> bssMessageTmpCache = java.util.Collections
	.synchronizedList(new LinkedList<String>());
	
	private static LinkedList<String> fssMessageCache = new LinkedList<String>();
	
	private static LinkedList<String> bssMessageCache = new LinkedList<String>();

	// tag the using status of received message cache
	private static boolean isFssMessageCacheReading = false;
	
	private static boolean isBssMessageCacheReading = false;


	private static void init() {
		
	}

	static {
		init();
	}


	public static void addFssMessage(String txtMessage) {

		fssMessageTmpCache.add(txtMessage);
		if (!isFssMessageCacheReading) {
			ImfMessageCache.setFssMessageCacheReading(true);
			AsynWorkUtil.getAsynService().addWork(ImfClientService.class,"readAndSaveFssMessage",new Object[] {});
		}

	}
	
	public static void addBssMessage(String txtMessage) {

		bssMessageTmpCache.add(txtMessage);
		if (!isBssMessageCacheReading) {
			ImfMessageCache.setBssMessageCacheReading(true);
			AsynWorkUtil.getAsynService().addWork(ImfClientService.class,"readAndSaveBssMessage",new Object[] {});
		}

	}



	// **************************************set message
	// cache*****************************************************/
	public static void getFssMessageCacheFromTmp() {
		log
				.debug("******************start******move afds data from tmp to cache");

		Iterator<String> it = fssMessageTmpCache.iterator();
		while (it.hasNext()) {
			fssMessageCache.add(it.next());
		}
		fssMessageTmpCache.clear();

		log
				.debug("******************end******move fss data from tmp to cache");
	}
	
	public static void getBssMessageCacheFromTmp() {
		log
				.debug("******************start******move bss data from tmp to cache");


		Iterator<String> it = bssMessageTmpCache.iterator();
		while (it.hasNext()) {
			bssMessageCache.add(it.next());
		}
		bssMessageTmpCache.clear();

		log
				.debug("******************end******move bss data from tmp to cache");
	}

	public static List<String> getFssMessageTmpCache() {
		return fssMessageTmpCache;
	}

	public static List<String> getBssMessageTmpCache() {
		return bssMessageTmpCache;
	}

	
	public static LinkedList<String> getFssMessageCache() {
		return fssMessageCache;
	}
	
	public static LinkedList<String> getBssMessageCache() {
		return bssMessageCache;
	}

	public static void setFssMessageCache(
			LinkedList<String> fssMessageCache) {
		ImfMessageCache.fssMessageCache = fssMessageCache;
	}	

	public static void setBssMessageCache(
			LinkedList<String> bssMessageCache) {
		ImfMessageCache.bssMessageCache = bssMessageCache;
	}

	public static boolean isFssMessageCacheReading() {
		return isFssMessageCacheReading;
	}

	public static boolean isBssMessageCacheReading() {
		return isBssMessageCacheReading;
	}
	
	public static void setFssMessageCacheReading(
			boolean isFssMessageCacheReading) {
		ImfMessageCache.isFssMessageCacheReading = isFssMessageCacheReading;
	}

	public static void setBssMessageCacheReading(
			boolean isBssMessageCacheReading) {
		ImfMessageCache.isBssMessageCacheReading = isBssMessageCacheReading;
	}
	
	

}
