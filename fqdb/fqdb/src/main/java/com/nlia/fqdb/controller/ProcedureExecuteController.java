package com.nlia.fqdb.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.service.impl.ProcedureExecuteManager;
import com.nlia.fqdb.util.DateUtils;

@Controller
@RequestMapping("procedure")
public class ProcedureExecuteController {
	private static JSONObject FAIL = JSONObject.fromObject("{\"result\":\"fail\"}");
	private static JSONObject SUCCESS = JSONObject.fromObject("{\"result\":\"success\"}");
	
	@Resource
	private ProcedureExecuteManager procedureExecuteManager;
	//执行存储过程
		@RequestMapping(value = "execute")
		public @ResponseBody
		JSONObject generate(HttpServletRequest request, HttpServletResponse response) {
			try {
			    
	            if(StringUtils.isEmpty(request.getParameter("method"))){
	            	FAIL.put("detail", "need inPutParams:method");
	            	return FAIL;
	            }
			    	            
	            if(StringUtils.isEmpty(request.getParameter("day"))){
	            	FAIL.put("detail", "need inPutParams:day");
	            	return FAIL;
	            }

		        String result = procedureExecuteManager.execute(request.getParameter("method"), request.getParameter("day"));
		        
		        if(result.startsWith("fail")){
		        	return FAIL;
		        }else{
					SUCCESS.put("detail", result);
					return SUCCESS;
		        }
			} catch (Exception e) {
				e.printStackTrace();
				return FAIL;
			}
		}

}
