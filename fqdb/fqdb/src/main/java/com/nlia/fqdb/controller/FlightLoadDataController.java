package com.nlia.fqdb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.service.impl.FlightBaseManager;
import com.nlia.fqdb.service.impl.FlightLoadDataManager;
import com.nlia.fqdb.util.DateUtils;
import com.nlia.fqdb.util.JsonUtil;
import com.nlia.fqdb.vo.FlightIdentifier;

@Controller
@RequestMapping("flightLoadData")
public class FlightLoadDataController {
	//MAX_VALUE为单次查询最大的航班数
	private final static int MAX_VALUE= 100;
	private static JSONObject FAIL = JSONObject.fromObject("{\"result\":\"fail\"}");
	private static JSONObject SUCCESS = JSONObject.fromObject("{\"result\":\"success\"}");

	
	
	@Resource
	private FlightLoadDataManager flightLoadDataManager;
	
	//生成单个航班配载
	
	@RequestMapping(value = "generate/{id}")
	public @ResponseBody
	JSONObject generateSingle(@PathVariable("id") long id) {
		try {

			 Map<String, Object> mapResult = flightLoadDataManager.generate(id);
			SUCCESS.put("detail", "新生成配载数据" + mapResult.get("total") +"条");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FAIL;
		}
	}
	//刷新内存
	@RequestMapping(value = "generate")
	public @ResponseBody
	JSONObject generate(HttpServletRequest request, HttpServletResponse response) {
		try {
		    Date startDate = null;// = DateUtils.getFirstMinuteOfOneday(DateUtils.getDefineDay(new Date(), -7));
            Date endDate = null;// = DateUtils.getLastMinuteOfOneday(new Date());
		    
		    if(!StringUtils.isEmpty(request.getParameter("generateDate"))){
		        Date strDate = DateUtils.String2Date(request.getParameter("generateDate"), "yyyy-MM-dd HH:mm");
		        startDate = DateUtils.getZeroOfOneday(strDate);
                endDate = DateUtils.getLastMinuteOfOneday(strDate);
		    }else{
			
    			
    			if(!StringUtils.isEmpty(request.getParameter("startDate")) 
    					&& !StringUtils.isEmpty(request.getParameter("endDate"))){
    				startDate = DateUtils.String2Date(request.getParameter("startDate"), "yyyy-MM-dd HH:mm");
    				endDate = DateUtils.String2Date(request.getParameter("endDate"), "yyyy-MM-dd HH:mm");
    			}
		    }
			List<FlightLoadData> listFlightLoadData = flightLoadDataManager.generate(startDate, endDate);
			SUCCESS.put("detail", "新生成配载数据" + listFlightLoadData.size() +"条");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FAIL;
		}
	}
	
	
	
}
