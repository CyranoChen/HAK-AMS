package com.nlia.fqdb.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestfulDataUtils<T> {

	private String uri;
	private String[] paths;
	private Class<?> clazz;
//	private String fqdbIpAndPort;
	private String sercurityIpAndPort;
//	Map <String,Class> mymap = new HashMap<String,Class>();

	public RestfulDataUtils(String sys, String[] paths, Class<?> clazz) {
		try {
			InputStream inputStream = getClass().getResourceAsStream("/restfulDataInterface.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
//			fqdbIpAndPort = properties.getProperty("fqdb.ipandport");
			sercurityIpAndPort = properties.getProperty("security.ipandport");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(sys.equals("security")){
			this.uri = sercurityIpAndPort + sys;
		}
//		if(sys.equals("fqdb")){
//			this.uri = fqdbIpAndPort + sys;
//		}else if(sys.equals("security")){
//			this.uri = sercurityIpAndPort + sys;
//		}
		this.paths = paths;
		this.clazz = clazz;
	}

	private URI getBaseURI() {
		return UriBuilder.fromUri(uri).build();
	}

	@SuppressWarnings("unchecked")
	public List<T> getEntities() {
//		mymap.put("roles", Role.class);
//		mymap.put("authorities", Authority.class);
		// 创建客户端配置
		ClientConfig config = new DefaultClientConfig();
		// 得到客户端配置
		Client client = Client.create(config);
		// 通过URI得到客户端的service
		WebResource service = client.resource(getBaseURI());
		for(String path : paths){
			service = service.path(path);
		}
		String json = service.accept(MediaType.APPLICATION_JSON).get(String.class);
		if(json.equals("")){
			return null;
		}
		JSONArray array;
		try{
			array = JSONArray.fromObject(json);
		}catch(JSONException e){
			JSONObject object = JSONObject.fromObject(json);
			array = JSONArray.fromObject(object);
		}
		
		List<T> entities = new ArrayList<>();
		// Map<Long, String> departmentMap = new HashMap<>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			T entity = (T) JSONObject.toBean(jsonObject, clazz);
			entities.add(entity);
		}

		return entities;
	}
}
