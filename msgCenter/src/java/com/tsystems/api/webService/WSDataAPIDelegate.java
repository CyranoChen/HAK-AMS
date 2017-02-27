package com.tsystems.api.webService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;


@javax.jws.WebService(targetNamespace = "http://webService.api.tsystems.com/", serviceName = "WSDataAPIService", portName = "DataAPI_Handler")
public class WSDataAPIDelegate {
	@Resource
	private WebServiceContext wsContext; 
	
	com.tsystems.api.webService.WSDataAPI dataAPI = new com.tsystems.api.webService.WSDataAPI();


	
	public String getDataInfo(String userName, String password,String date1,String date2,String dataType) {
		String clientIP=getClientIP();
		return dataAPI.getDataInfo(userName, password, date1, date2,dataType,"0", clientIP);
	}
	
	public String getDataByBatch(String userName, String password,String date1,String date2,String dataType,String batch) {
		String clientIP=getClientIP();
		return dataAPI.getDataInfo(userName, password, date1, date2,dataType, batch,clientIP);
	}
	
	public String setDataInfo(String userName, String password,String date,String content) {
		String clientIP=getClientIP();
		return dataAPI.setDataInfo(userName, password, date, content,clientIP);
	}
	
	private String getClientIP(){
		MessageContext mc=wsContext.getMessageContext();
		HttpServletRequest request=(HttpServletRequest)mc.get(mc.SERVLET_REQUEST);
		String remoteAddress=request.getRemoteAddr();
		return remoteAddress;
	}
}