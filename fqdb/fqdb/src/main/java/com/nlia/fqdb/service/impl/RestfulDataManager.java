package com.nlia.fqdb.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.service.IRestfulDataManager;
import com.nlia.fqdb.util.RestfulDataUtils;

@Service
public class RestfulDataManager<T> implements IRestfulDataManager<T>{

	@Override
	public List<T> getData(String sys, String[] paths, Class<?> clazz) {
		RestfulDataUtils<T> restfulDataUtils = new RestfulDataUtils<T>(sys, paths, clazz);
		return restfulDataUtils.getEntities();
	}

	
//	/**
//	 * 获得omms的ip
//	 * @return eg,"http://10.1.40.209:8082/omms"
//	 */
//	public static String getFqdbUrl(){
//		String omms =null;
//		try {
//			InputStream inputStream = RestfulDataManager.class.getResourceAsStream("/restfulDataInterface.properties");
//			Properties properties = new Properties();
//			properties.load(inputStream);
//			omms = properties.getProperty("omms.ipandport");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return omms+"omms/";
//	}
}
