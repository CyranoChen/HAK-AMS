package com.wonders.frame.ams.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



public class HttpClientUtil {
	
	public static final int CONNECT_TIME_OUT = 40000;
	
	public static final int SOCKET_TIME_OUT = 40000;
	
	public static String post(HttpServletRequest request, String uri){
		return post(request, uri, null);
	}
	
	public static String post(HttpServletRequest request, String uri,List<BasicNameValuePair> form){
		
		String rtn ="";
		
		uri = getRoot(request) + uri ;
		
		CloseableHttpClient hc = HttpClients.createDefault();
		
		
		
		HttpPost post = new HttpPost(uri);
		
		setTimeout(post);
		

		try {
			
			if(form == null){
				form = new ArrayList<BasicNameValuePair>();
			}
			
			HttpEntity entity = new UrlEncodedFormEntity(form, "UTF-8");
			
			post.setEntity(entity);

			HttpResponse resp = hc.execute(post);
			
			if(resp.getStatusLine().getStatusCode() == 200){
				entity = resp.getEntity();
				rtn = EntityUtils.toString(entity); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtn;
	}

	public static String getRoot(HttpServletRequest request){
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ;
	}
	
	private static <T> void setTimeout(T t){
		
		setTimeout(t , CONNECT_TIME_OUT , SOCKET_TIME_OUT );	
	}
	
	private static<T>  void setTimeout(T t,int socketTimeout,int connectTimeout){
		RequestConfig config = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
		if (t instanceof HttpPost) {
			HttpPost post = (HttpPost) t;
			post.setConfig(config);
		}else if(t instanceof HttpGet){
			HttpGet get = (HttpGet) t;
			get.setConfig(config);
		}else{
			
		}
		
		
	}
}
