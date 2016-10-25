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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.service.impl.FlightMateInfoManager;
import com.nlia.fqdb.util.DateUtils;

@Controller
@RequestMapping("flightMateInfo")
public class FlightMateInfoController {
	private static JSONObject FAIL = JSONObject.fromObject("{\"result\":\"fail\"}");
	private static JSONObject SUCCESS = JSONObject.fromObject("{\"result\":\"success\"}");

	
	
	@Resource
	private FlightMateInfoManager flightMateInfoManager;
	
	//生成单个航班配对
	
	@RequestMapping(value = "generate/{id}")
	public @ResponseBody
	JSONObject generateSingle(@PathVariable("id") long id) {
		try {

	           Map<String, Object> mapResult = new HashMap<>();
	           mapResult = flightMateInfoManager.flightMatedInfoInit(id);
	           String ResultMessages = new String();
	           				
	           ResultMessages += "总计航班 ：" + mapResult.get("total") +";";
               ResultMessages += "生成：" +  mapResult.get("done") +";";
               ResultMessages += "缺少对应连班：" +  mapResult.get("noLink")+";";
               ResultMessages += "缺少机号：" + mapResult.get("noReg") +";";
               ResultMessages += "对应连班缺少机号：" + mapResult.get("noLinkReg")+";";
               ResultMessages += "数据不完整：" + mapResult.get("notComplete")+";";
			SUCCESS.put("detail", ResultMessages);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FAIL;
		}
	}
	//生成航班配对
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
		    
	           Map<String, Object> mapResult = new HashMap<>();
	           mapResult = flightMateInfoManager.flightMatedInfoInit(startDate, endDate);
	           String ResultMessages = new String();
	           ResultMessages += "总计航班 ：" + mapResult.get("leftFlightBases") +";";
               ResultMessages += "生成：" +  ((List)mapResult.get("flightMateInfos")).size() +";";
               ResultMessages += "缺少对应连班：" +  mapResult.get("fbNoLinkFlightMes")+";";
               ResultMessages += "缺少机号：" + mapResult.get("fbNoRegisterationMes") +";";
               ResultMessages += "对应连班缺少机号：" + mapResult.get("fbLinkNoRegisterationMes")+";";
               ResultMessages += "数据不完整：" + mapResult.get("fbNotComplate")+";";
			SUCCESS.put("detail", ResultMessages);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return FAIL;
		}
	}
	//航班配对补漏
	@RequestMapping(value = "repairmis")
    public @ResponseBody
    JSONObject repairmis(HttpServletRequest request, HttpServletResponse response) {
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
            List<FlightBase> fbs=flightMateInfoManager.findMisFlightMateInfo(startDate, endDate);
            if(fbs.size()>0){
                Map<String, Object> mapResult = new HashMap<>();
                mapResult = flightMateInfoManager.repairMisFlightMateInfo(fbs);
                String ResultMessages = new String();
                ResultMessages += "总计航班 ：" + mapResult.get("leftFlightBases") +"\n";
                ResultMessages += "生成：" +  ((List)mapResult.get("flightMateInfos")).size() +"\n";
                ResultMessages += "缺少对应连班：" +  mapResult.get("fbNoLinkFlightMes")+"\n";
                ResultMessages += "缺少机号：" + mapResult.get("fbNoRegisterationMes") +"\n";
                ResultMessages += "对应连班缺少机号：" + mapResult.get("fbLinkNoRegisterationMes")+"\n";
                ResultMessages += "数据不完整：" + mapResult.get("fbNotComplate")+"\n";
                SUCCESS.put("detail", ResultMessages);
            }else{
                SUCCESS.put("detail", "没有找到缺漏航班！");
            }
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }
	
	
}
