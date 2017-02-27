package com.tsystems.api.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestAPIService {
	private static final Logger log = LoggerFactory.getLogger(RestAPIService.class);
	
	private static void init() {
		
	}
	
	static {
		init();
	}		
	public static String getDataByGet(String serverUrl, HashMap<String, String> params) {
		String result = "";
		try {
			Request.Head(URI.create("text/html;charset=utf-8"));
			Request request = Request.Get(serverUrl);
	
			if(params != null && !params.isEmpty()){
				Form form = Form.form();
		        Set<String> keySet = params.keySet();
		        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
		            String key = (String) it.next();
		            form.add(key, params.get(key));
		        }
				request = request.bodyForm(form.build());
			}
		
			Response response = request.execute();
			result = response.returnContent().asString();
		} catch (Exception e) {
			result="failure:"+e.getMessage();
			log.error("unexcepted Exception when {}","getDataByGet",e);
		}
		return result;
	}
	public static String getData(String serverUrl,HashMap<String,String> params){
		String result = "";
        try {
	        Form form = Form.form();
	        Set<String> keySet = params.keySet();
	        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
	            String key = (String) it.next();
	            form.add(key, params.get(key));
	        }
            Request.Head("");
			result = Request.Post(serverUrl).bodyForm(form.build()).execute().returnContent().asString();
			
        } catch (Exception e) {
			result="failure:"+e.getMessage();
			log.error("unexcepted Exception when {}","getData",e);
        } 
        return result;
	}
	 
	public static String getDataByHttpConnection(String serverUrl,HashMap<String,String> params){
		String result = "";
		String param = "";
		URL url = null;
		HttpURLConnection http = null;
		
		try {
			url = new URL(serverUrl);
			http = (HttpURLConnection) url.openConnection();
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setUseCaches(false);
			http.setConnectTimeout(1200000);
			http.setReadTimeout(1200000);
			http.setRequestMethod("POST");
			//http.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");			
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.connect();
			
	        Set<String> key = params.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String pname = (String) it.next();
	            param+="&"+pname+"="+params.get(pname);
	        }
			
         	OutputStreamWriter osw=new OutputStreamWriter(http.getOutputStream(),"utf-8");
          	osw.write(param);
          	osw.flush();
          	osw.close();
         			
			if (http.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(),"utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
			}
		} catch (Exception e) {			
			result="failure:"+e.getMessage();
			log.error("unexcepted Exception when {}","getDataByHttpConnection",e);
			//e.printStackTrace();
		} finally {
			if (http != null) http.disconnect();
		}
	
		
		return result;
	}
	
    public static void main(String[] args){
    	HashMap<String,String> hm=new HashMap<String,String>();

		//hm.put("generateDate", "2014-06-27");
		//hm.put("flightScheduledDate", "2014-02-13");
		//hm.put("flightIdentity", "NJ8001");
		//hm.put("flightDirection", "D");
		//hm.put("startDate", "2014-01-01 01:00:00");
		//hm.put("endDate", "2014-01-01 23:00:00");
		//hm.put("serviceCode", "AIRCRAFT-P");	
		//System.out.print(Tools.getCurrentDateTime());
		//hm.put("startdate", "2014-06-06");
		//hm.put("enddate", "2014-06-07");
		//hm.put("batch", "19");
		//String s=getData("http://10.1.40.209:8082/omms/flight/refreshCache",hm);//OMMS刷新
		
    	//String s=getData("http://192.169.1.125:8080/fqdb/flight/refreshCache",hm);//FQDB刷新
		//String s=getData("http://10.1.40.209:8082/fqdb/flight/refreshCache",hm);//FQDB刷新
		//返回"";
		
    	//String s=getData("http://10.1.40.209:8082/omms/serviceplan/generateServicePlan",hm);//服务计划
		
		//String s=getData("http://10.1.40.209:8082/omms/dwWorkLoad/calcaulateWorkLoad",hm);//工作量评估
		//String s=getData("http://10.1.40.209:8082/omms/task/taskAllocationHandle",hm);//任务分配
		//String s=getData("http://10.1.40.209:8082/omms/task/taskExecute",hm);//任务执行
		//String s=getData("http://192.169.1.124:8080/omms/flightChange/delayFlights",hm);//任务延期
		//String s=getData("http://192.169.1.124:8080/omms/flightChange/redoFlights",hm);//任务重排	
		//String s=getData("http://localhost:8081/msgCenter/api/serviceInfo/sync",hm);//服务确认单同步
    	//String s=getData("http://10.1.40.209:8082/omms/dwWorkLoad/generateShiftCalendar",hm);//人员日历
    	//String s=getData("http://10.1.40.209:8082/omms/dwWorkLoad/generateFacilityCalenda",hm);//车辆日历
		//String s=getData("http://172.17.1.27/omms/views/calculateFlightServiceInfo",hm);//计算服务确认单
		
    	hm.put("startdate", "2013-12-31 00:00:00");
    	hm.put("enddate", "2014-01-01 23:23:59");
    	hm.put("batch", "1");
    	long start = (new Date()).getTime();				
		String s=getData("http://localhost:8080/fqdb/flight/getPeriodFlightInJson",hm);//webService接口：getFss
		long end = (new Date()).getTime();	
		System.out.println("......use time："+(end-start)/1000+"."+(end)%start+"ms");	
		//String s=getData("http://10.1.40.209:8082/fqdb/flight/getPeriodFlightInJson",hm);//webService接口：getFss
    	//System.out.println("-------"+Tools.getCurrentDateTime());
		//System.out.println("info:"+s);
    	
    	//JSONObject obj=new JSONObject();
    	
		//obj.put("pp","111");
		
		//System.out.println(obj.toString());
	}
	
}


