package com.nlia.fqdb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.service.impl.FlightBaseManager;
import com.nlia.fqdb.util.DateUtils;
import com.nlia.fqdb.util.JsonUtil;
import com.nlia.fqdb.vo.FlightIdentifier;

@Controller
@RequestMapping("flight")
public class FlightController {
	//MAX_VALUE为单次查询最大的航班数
	private final static int MAX_VALUE= 100;
	private static JSONObject FAIL = JSONObject.fromObject("{\"result\":\"fail\"}");
	private static JSONObject SUCCESS = JSONObject.fromObject("{\"result\":\"success\"}");

	
	private final static String TOO_MANY = "[{\"count\":\"ninja\",\"max\":\""+MAX_VALUE+"\"}]";
	
	
	@Resource
	private FlightBaseManager flightBaseManager;
	

	//刷新内存
	@RequestMapping(value = "refreshCache")
	public @ResponseBody
	JSONObject refreshCache(HttpServletRequest request, HttpServletResponse response) {
		try {
			Date startDate = DateUtils.getFirstMinuteOfOneday(DateUtils.getDefineDay(new Date(), -7));
			Date endDate = DateUtils.getLastMinuteOfOneday(new Date());
			
			if(!StringUtils.isEmpty(request.getParameter("startDate")) 
					&& !StringUtils.isEmpty(request.getParameter("endDate"))){
				startDate = DateUtils.String2Date(request.getParameter("startDate"), "yyyy-MM-dd HH:mm:ss");
				endDate = DateUtils.String2Date(request.getParameter("endDate"), "yyyy-MM-dd HH:mm:ss");
			}
			flightBaseManager.refreshFlightBases(startDate, endDate);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FAIL;
		}
	}
	
	@RequestMapping(value = "alwaysok")
	public @ResponseBody
	JSONObject alwaysOk(HttpServletRequest request, HttpServletResponse response) {
		return SUCCESS;
	}
	
	//获得json格式的某个时间段的航班
	@RequestMapping(value = "getPeriodFlightInJson", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	JSONArray getPeriodFlight(HttpServletRequest request, HttpServletResponse response) {
		JSONArray FAIL = JSONArray.fromObject("[{\"result\":\"fail\"}]");
		JSONArray  NO_INFO = JSONArray.fromObject("[]");
		List<Object>objects =new ArrayList<>();
		List<String> objNames =new ArrayList<>();
		List<String> jsonObjects = new ArrayList<>(); 
		FlightBase flightBase;
		FlightData flightData;
		FlightResource flightResource;
		List<FlightBase> flightBases;
		int batch = 0;
		String sStartDate = request.getParameter("startdate");
		String sEndDate = request.getParameter("enddate");
		Date startdate = null;
		Date enddate = null;
		if(sStartDate.contains(":")){
			startdate = DateUtils.String2Date(sStartDate,"yyyy-MM-dd HH:mm:ss");
		}else{
			startdate = DateUtils.String2Date(sStartDate,"yyyy-MM-dd");
		}
		if(sEndDate.contains(":")){
			enddate = DateUtils.String2Date(sEndDate,"yyyy-MM-dd HH:mm:ss");
		}else{
			enddate = DateUtils.String2Date(sEndDate,"yyyy-MM-dd");
			enddate = DateUtils.getLatestTimeOfOneday(enddate);
		}
//		System.out.println("startDate= "+ DateUtils.Date2String(startdate,"yyy-MM-dd HH:mm:ss"));
//		System.out.println("endDate= "+ DateUtils.Date2String(enddate,"yyy-MM-dd HH:mm:ss"));
		try{
			batch = Integer.valueOf(request.getParameter("batch"));
		}catch(Exception e){
			System.out.println("batch is not valid");
			return FAIL;
		}
		
		if(batch == 0){
				flightBases = flightBaseManager.getPeriodFlight(startdate, enddate);
		}else{
			//分页
				flightBases = flightBaseManager.getPeriodFlightByPage(startdate, enddate, MAX_VALUE, batch);
		}
		
		if(flightBases == null  ){
			return FAIL;
		}
		
		if(flightBases.size() == 0){
			return NO_INFO;
		}
		
		if(flightBases.size() == 1 && flightBases.get(0) == null ){
			return NO_INFO;
		}
		
		if(flightBases.size() > MAX_VALUE){
			String result = TOO_MANY.replaceAll("ninja", String.valueOf(flightBases.size()));
			return JSONArray.fromObject(result);
		}
		long start = (new Date()).getTime();	
		List<Long> flightBaseIdList=new ArrayList<Long>();
		for(int i=0; i< flightBases.size(); i++){
			flightBaseIdList.add(flightBases.get(i).getId());
		}
		Long[] flightBaseIds=flightBaseIdList.toArray(new Long[flightBaseIdList.size()]);
		
		//HashMap<String,FlightServiceInfo> hmFlightServiceInfo = flightServiceInfoManager.findByFlightBaseIds(flightBaseIds);
		for(int i=0; i< flightBases.size(); i++){
			flightBase = flightBases.get(i);
			flightData = flightBase.getFlightData();
			flightBase.setFlightData(null);
			flightResource = flightBase.getFlightResource();
			flightBase.setFlightResource(null);
			//FlightServiceInfo flightServiceInfo = hmFlightServiceInfo.get(flightBase.getId().toString());
			objects.clear();
			objects.add(flightBase);
			objects.add(flightData);
			objects.add(flightResource);

			objNames.clear();
			objNames.add("flightBase");
			objNames.add("flightData");
			objNames.add("flightResource");
			/*
			if(flightServiceInfo != null){
				objects.add(flightServiceInfo);
				objNames.add("flightServiceInfo");
			}
			List<FlightIdentifier> slaveFlights = masterSlaveRelationManager.findFlightIdentifierByMasterFlightId(flightBase.getId());
				
			if(slaveFlights != null){
				for(FlightIdentifier identifier : slaveFlights){
					objects.add(identifier);
					objNames.add("slaveFlight");
				}
			}
			*/
			String singleModule = JsonUtil.ObjectsToJsonObject(objects, objNames);
			jsonObjects.add(singleModule);
		}
		
		long end = (new Date()).getTime();	
		System.out.println("......use time："+(end-start)/1000+"."+(end)%start+"ms");
		return JSONArray.fromObject(JsonUtil.JsonObjectsToArray(jsonObjects));
	}
	
	//获得某个时间段的航班个数
	@RequestMapping(value = "getPeriodFlightCount")
	public @ResponseBody
	Long getPeriodFlightCount(HttpServletRequest request, HttpServletResponse response) {

		Date startdate = DateUtils.String2Date(request.getParameter("startdate"),"yyyy-MM-dd");
		Date enddate = DateUtils.String2Date(request.getParameter("enddate"),"yyyy-MM-dd");
//		List<FlightBase> flightBases = flightBaseManager.getPeriodFlight(startdate, enddate);
//		return String.valueOf(flightBases.size());
		return flightBaseManager.getPeriodFlightCount(startdate, enddate);
	}
	
}
