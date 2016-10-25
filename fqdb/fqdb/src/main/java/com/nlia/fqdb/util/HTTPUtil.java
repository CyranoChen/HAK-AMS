package com.nlia.fqdb.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class HTTPUtil {
	/**
	 * 执行一个HTTP POST请求，返回请求响应的HTML
	 */
	@SuppressWarnings("static-access")
	public static String doPost(String url, Map<String, String> params) {
		String result = "";
		Form form = Form.form();
		//add params
		if(params != null && !params.isEmpty()){
			Set<Entry<String, String>> paramsSet = params.entrySet();
			for (Iterator<Map.Entry<String, String>> it = paramsSet.iterator(); it.hasNext();) {
				Entry<String, String> entry = (Entry<String, String>) it.next();
				form = form.add(entry.getKey(), entry.getValue());
			}
		}
		
		try {
//			System.out.println(url);
			result = Request.Head("").Post(url).bodyForm(form.build()).execute().returnContent().asString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
