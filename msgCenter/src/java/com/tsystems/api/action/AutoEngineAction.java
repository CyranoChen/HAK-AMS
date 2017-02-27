package com.tsystems.api.action;

import java.io.IOException;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tsystems.api.model.bo.McApiInfo;
import com.tsystems.api.model.bo.McApiLog;
import com.tsystems.api.model.vo.McApiInfoVo;
import com.tsystems.api.service.AutoEngineService;
import com.tsystems.api.service.McApiInfoService;
import com.tsystems.api.service.RestAPIService;
import com.tsystems.api.util.Tools;
import com.tsystems.imfMsg.entity.vo.Page;
import com.tsystems.imfMsg.util.LockedUtil;
import com.tsystems.schedule.model.bo.TScheduleConfig;

@ParentPackage("struts-default")
@Namespace(value="/autoEngine")
@Component("autoEngineAction")
@Scope("prototype")
public class AutoEngineAction extends ActionSupport {
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	//public HttpSession session = request.getSession();	    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private McApiInfoService mcApiInfoService;
	
	@Action(value="showIndex",results={@Result(name="success",location="/jsp/autoEngine/index.jsp")})
	public String showIndex() {	 
		List<McApiInfo> mcApiInfos=mcApiInfoService.findByUserMethod("omms", "autoEngine");
		List<McApiInfoVo> mcApiInfoVos=new ArrayList<McApiInfoVo>();
		for(int i=0;i<mcApiInfos.size();i++){
			McApiInfoVo mcApiInfoVo=new McApiInfoVo(mcApiInfos.get(i));
			mcApiInfoVos.add(mcApiInfoVo);
			
		}
		request.setAttribute("apiInfos", mcApiInfoVos);
		return "success";
	}
	
	@Action(value="showLogList",results={@Result(name="success",location="/jsp/api/logList.jsp")})
	public String showLogList() {
		HashMap<String,String> filter=new HashMap<String,String>();		
		Map<String,String> mParams=request.getParameterMap();		
		Set<String> key = mParams.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String paramName = (String) it.next();
            String paramValue=request.getParameter(paramName);
            if(!paramValue.equals("")){
            	filter.put(paramName, paramValue);
            }
        }
		
        if(filter.get("pageNo")==null||filter.get("pageNo").equals("")){
        	 filter.put("pageNo", "1");
        }
        filter.put("removed", "0");
        
		Page msgPage=mcApiInfoService.findLogByPage(filter, "desc");

		request.setAttribute("page", msgPage);
		Set<String> filterKey = filter.keySet();
        for (Iterator itFilter = filterKey.iterator(); itFilter.hasNext();) {
            String filterName = (String) itFilter.next();
            String filterValue=filter.get(filterName);
            request.setAttribute(filterName, filterValue);
        }
        
        List<McApiInfo> apiInfos=mcApiInfoService.findAllApiInfo();
        HashMap<String,String> hmApiName=new HashMap<String,String>();
        List<String> apiIds=new ArrayList<String>();
        for(int i=0;i<apiInfos.size();i++){
        	hmApiName.put(apiInfos.get(i).getId(),apiInfos.get(i).getDataName());
        	apiIds.add(apiInfos.get(i).getId());
        }
        request.setAttribute("hmApiName", hmApiName);
        request.setAttribute("apiIds", apiIds);
		return "success";
	}	
	
	@Action(value="viewLog",results={@Result(name="success",location="/jsp/api/logView.jsp")})
	public String showView() {	
		String logId=request.getParameter("id");
				
		McApiLog log=mcApiInfoService.loadLogById(logId);
		request.setAttribute("log", log);
		
		String apiId=log.getApiId();
		if(apiId!=null && !apiId.equals("")){
			McApiInfo apiInfo=mcApiInfoService.loadById(apiId);
			request.setAttribute("apiName", apiInfo.getDataName());
		}
		return "success";
	}
	
	
	@Action(value="setting",results={@Result(name="success",location="/jsp/autoEngine/setting.jsp")})
	public String setting() {	
		String apiId=request.getParameter("id");				
		McApiInfo apiInfo=mcApiInfoService.loadById(apiId);
		McApiInfoVo apiInfoVo=new McApiInfoVo(apiInfo);	

		if(apiInfoVo.getParams().containsKey("scheduleId") &&!apiInfoVo.getParams().getString("scheduleId").equals("")){
			TScheduleConfig scheduleConfig=mcApiInfoService.loadScheduleConfigById(apiInfoVo.getParams().getString("scheduleId"));
			request.setAttribute("scheduleConfig", scheduleConfig);
		}
		request.setAttribute("apiInfo", apiInfoVo);		
		
		
        List<McApiInfo> apiInfos=mcApiInfoService.findAllTriggerApiInfo();
        HashMap<String,String> hmApiName=new HashMap<String,String>();
        List<String> apiIds=new ArrayList<String>();
        for(int i=0;i<apiInfos.size();i++){
        	hmApiName.put(apiInfos.get(i).getId(),apiInfos.get(i).getDataName());
        	apiIds.add(apiInfos.get(i).getId());
        }
        request.setAttribute("hmApiName", hmApiName);
        request.setAttribute("apiIds", apiIds);
        
		return "success";
	}	

	@Action(value="editInitParams",results={@Result(name="success",location="/jsp/autoEngine/editInitParams.jsp")})
	public String editInitParams() {	
		String apiId=request.getParameter("id");				
		McApiInfo apiInfo=mcApiInfoService.loadById(apiId);
		McApiInfoVo apiInfoVo=new McApiInfoVo(apiInfo);
		request.setAttribute("apiInfo", apiInfoVo);
		return "success";
	}	
	
	@Action(value="updateRuleConfig")
	public String updateRuleConfig() {	
		String apiId=request.getParameter("id");
		String time=request.getParameter("time");
		String interval=request.getParameter("interval");
		String loopRemark=request.getParameter("loopRemark");	
		String nextStep=request.getParameter("nextStep");	
		String triggerRemark=request.getParameter("triggerRemark");		
		try{		
			McApiInfo apiInfo=mcApiInfoService.loadById(apiId);
			McApiInfoVo apiInfoVo=new McApiInfoVo(apiInfo);
			String autoWay=apiInfoVo.getParams().getString("autoWay");
			if(autoWay.equals("loop")){
				if(apiInfoVo.getParams().containsKey("scheduleId") &&!apiInfoVo.getParams().getString("scheduleId").equals("")){
					TScheduleConfig scheduleConfig=mcApiInfoService.loadScheduleConfigById(apiInfoVo.getParams().getString("scheduleId"));				
					scheduleConfig.setTime(time);
					scheduleConfig.setInterval(interval);
					mcApiInfoService.updateScheduleConfig(scheduleConfig);
				}				
				apiInfoVo.getParams().put("loopRemark", loopRemark);
			}else{
				apiInfoVo.getParams().put("nextStep", nextStep);
				apiInfoVo.getParams().put("triggerRemark", triggerRemark);
			}
			
			apiInfo.setParams(apiInfoVo.getParams().toString());
			mcApiInfoService.update(apiInfo);			

			actionWrite("{\"success\":true}");
		}catch(Exception e){
			log.error("unexcepted Exception when {}","updateRuleConfig",e);
			//e.printStackTrace();		
			actionWrite("{\"success\":false}");
		}
		return null;
	}
	
	@Action(value="changeInitParams")
	public String changeInitParams() {	
		String apiId=request.getParameter("id");
		String initParams=request.getParameter("initParams");		
		try{		
			McApiInfo apiInfo=mcApiInfoService.loadById(apiId);
			McApiInfoVo apiInfoVo=new McApiInfoVo(apiInfo);		
			apiInfoVo.getParams().put("initParams", initParams);
			apiInfo.setParams(apiInfoVo.getParams().toString());
			mcApiInfoService.update(apiInfo);

			actionWrite("{\"success\":true}");
		}catch(Exception e){
			log.error("unexcepted Exception when {}","changeInitParams",e);
			//e.printStackTrace();		
			actionWrite("{\"success\":false}");
		}
		return null;
	}	
	
	@Action(value="changeAutoWay")
	public String changeAutoWay() {	
		String apiId=request.getParameter("id");
		List<McApiInfo> preApis=mcApiInfoService.findByNextStep(apiId);
		if(preApis==null||preApis.size()==0){				
			try{		
				McApiInfo apiInfo=mcApiInfoService.loadById(apiId);
				McApiInfoVo apiInfoVo=new McApiInfoVo(apiInfo);
				String autoWay=apiInfoVo.getParams().getString("autoWay");
	
				
				if(autoWay.equalsIgnoreCase("loop")){
					apiInfoVo.getParams().put("autoWay", "trigger");
				}else{
					apiInfoVo.getParams().put("autoWay", "loop");		
				}
				
				apiInfo.setParams(apiInfoVo.getParams().toString());
				mcApiInfoService.update(apiInfo);
				actionWrite("{\"success\":true}");
			}catch(Exception e){
				log.error("unexcepted Exception when {}","changeAutoWay",e);
				//e.printStackTrace();		
				actionWrite("{\"success\":false}");
			}
		}else{
			String preServiceName="【";
			for(int i=0;i<preApis.size();i++){
				if(i>0){preServiceName+=",";}
				preServiceName+=preApis.get(i).getDataName();
			}
			preServiceName+="】";
			actionWrite("{\"success\":false,\"alertMsg\":\"不可切换为轮询模式，因为当前服务已经被服务"+preServiceName+"设置为触发服务，\"}");
		}
		return null;
	}
	
	@Action(value="changeAutoSwitch")
	public String changeAutoSwitch() {	
		String apiId=request.getParameter("id");
		try{		
			McApiInfo apiInfo=mcApiInfoService.loadById(apiId);
			McApiInfoVo apiInfoVo=new McApiInfoVo(apiInfo);
			String autoSwitch=apiInfoVo.getParams().getString("autoSwitch");
			
			if(autoSwitch.equalsIgnoreCase("off")){
				apiInfoVo.getParams().put("autoSwitch", "on");
			}else{
				apiInfoVo.getParams().put("autoSwitch", "off");
									
			}	
			apiInfo.setParams(apiInfoVo.getParams().toString());
			mcApiInfoService.update(apiInfo);	
					
			
			if(apiInfoVo.getParams().containsKey("scheduleId") &&!apiInfoVo.getParams().getString("scheduleId").equals("")){
				
				TScheduleConfig scheduleConfig=mcApiInfoService.loadScheduleConfigById(apiInfoVo.getParams().getString("scheduleId"));
				
				String autoWay=apiInfoVo.getParams().getString("autoWay");
				
				if(apiInfoVo.getParams().getString("autoSwitch").equals("on")){
					if(autoWay.equals("loop")){					
						scheduleConfig.setRemoved("0");
					}	
					scheduleConfig.setName(apiInfoVo.getDataType());
					scheduleConfig.setDescription(apiInfoVo.getDataName());
					scheduleConfig.setParam("{\"id\":\""+apiId+"\"}");
				}else{					
					scheduleConfig.setRemoved("1");														
				}
				
				mcApiInfoService.updateScheduleConfig(scheduleConfig);	
			}
			
		
			actionWrite("{\"success\":true}");
		}catch(Exception e){
			log.error("unexcepted Exception when {}","changeAutoSwitch",e);
			//e.printStackTrace();		
			actionWrite("{\"success\":false}");
		}
		return null;
	}
	@Action(value="cleanCallApiLock")
	public void cleanCallApiLockedList() {	
		LockedUtil.cleanApiCallLockedList();
	}
	@Action(value="runService")
	public String runService() {	
		String apiId=request.getParameter("id");
		String param=request.getParameter("param");
		String preInitParams=request.getParameter("preInitParams");
		String curInitParams="";
		Boolean isSuccess=true;
		String exception="";
		String result="";
		String callWay="manual";
		String restApiBackInfo="";
		Date callTime=new Timestamp(System.currentTimeMillis());
		if(apiId==null){
			JSONObject paramObj=JSONObject.fromObject(param);
			if(paramObj.containsKey("id")){
				apiId=paramObj.getString("id");
				callWay="loop";
			}else{
				actionWrite("{\"success\":false}");
				return null;
			}
		}
		if(preInitParams!=null){
			callWay="trigger";
		}		
		McApiInfo apiInfo=mcApiInfoService.loadById(apiId);

		McApiInfoVo apiInfoVo=new McApiInfoVo(apiInfo);

		if(apiInfoVo.getParams().containsKey("restApi")){
			try{
				HashMap<String,String> hm=new HashMap<String,String>();
				String restAPi=apiInfoVo.getParams().getString("restApi");
								
				curInitParams=dealWithInitParams(apiInfoVo.getParams(),preInitParams,hm);
				String apiCallName=apiInfo.getDataType()+":"+curInitParams;
				
				log.info("======================"+callWay+" starting "+apiInfo.getDataName()+" by params"+curInitParams);	
				log.debug("the name in callApiLockedList:"+apiCallName);
				
				boolean lockedSuccess=LockedUtil.addApiCallLocked(apiCallName);
				
				if(!lockedSuccess){
					isSuccess=false;
					exception="the api is being call,so this call will not be execute!";
					restApiBackInfo=",由于当前接口正在被调用执行中,所以当前请求被终止!";
				}else{
					result=RestAPIService.getData(restAPi, hm);
					
					LockedUtil.cancelApiCallLocked(apiCallName);
					
					if(result.startsWith("failure:")){
						isSuccess=false;
						exception=result.replaceFirst("failure:", "");
					}
					
					
					if(isSuccess){			
						JSONObject resultObj=JSONObject.fromObject(result);
						if(!resultObj.containsKey("result")||resultObj.getString("result").equals("fail")){
							isSuccess=false;				
						}else{
							if(resultObj.containsKey("successNumber")){
								String succNum=resultObj.getString("successNumber");
								restApiBackInfo+=",成功"+succNum+"条";
							}
							if(resultObj.containsKey("failNumber")){
								String failNum=resultObj.getString("failNumber");
								restApiBackInfo+=",失败"+failNum+"条";
							}
							if(resultObj.containsKey("detail")){
								restApiBackInfo+=","+resultObj.getString("detail");
							}						
							
						}
					}
					
					if(exception.equals("Connection reset")&&!isSuccess){
						isSuccess=true;		
						log.info("======================"+callWay+" starting "+apiInfo.getDataName()+" by params"+curInitParams+ "returns: Connection reset");
						result="";
						exception="";
					}
				}
			}catch(Exception e){
				isSuccess=false;
				exception="error:"+e.getMessage()+"\n,return of API:"+result;				
				log.error("unexcepted Exception when {}","runService",e);
				//e.printStackTrace();
			}
		}
		McApiLog apiLog=new McApiLog(apiInfoVo.getId(),"admin",callTime,new Timestamp(System.currentTimeMillis()),callWay,apiInfoVo.getMethodName());
		apiLog.setCallResult(isSuccess?"success":"failure");
		apiLog.setBackInfo(exception.equals("")?result:"");
		apiLog.setException(exception);
		mcApiInfoService.saveLog(apiLog);

		actionWrite("{\"success\":"+isSuccess+",\"alertMsg\":\""+restApiBackInfo+"\"}");
		
		String autoSwitch=apiInfoVo.getParams().getString("autoSwitch");
		
		//if(isSuccess && autoSwitch.equalsIgnoreCase("on") && autoWay.equalsIgnoreCase("trigger")){				
		if(autoSwitch.equalsIgnoreCase("on") && apiInfoVo.getParams().containsKey("nextStep") 
				&&!apiInfoVo.getParams().getString("nextStep").equals("")){		
			String actionUrl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/autoEngine/runService.action";
			AutoEngineService.startNextService(actionUrl,apiInfoVo.getParams().getString("nextStep"), curInitParams);
		}
		
		return null;
	}	
		
	private String dealWithInitParams(JSONObject apiParamsObj,String preInitParams,HashMap<String,String> hm){
		JSONObject currentParamsObj=new JSONObject();

		if(preInitParams!=null && !preInitParams.equals("")){
			JSONObject preInitParamsObj=JSONObject.fromObject(preInitParams);
			Iterator it = preInitParamsObj.keys();  
			while (it.hasNext()) {  
				String key = (String) it.next();  
				String value = preInitParamsObj.getString(key);  				
				hm.put(key, value);	
				currentParamsObj.put(key, value);
			}
		}
		if(apiParamsObj.containsKey("initParams")&&!apiParamsObj.getString("initParams").equals("")){
			JSONObject initParamsObj=JSONObject.fromObject(apiParamsObj.getString("initParams"));
			Iterator it = initParamsObj.keys();  
			while (it.hasNext()) {  
				String key = (String) it.next();  
				String value = initParamsObj.getString(key);  
				if(value.startsWith("today")){
					String[] dateParams=value.split(" ");
					String sDays=dateParams[0].replaceFirst("today", "").replace("+", "");
					if(sDays.equals("")){sDays="0";}
					value=Tools.getDay(Integer.parseInt(sDays));
					
					if(dateParams.length>1){
						if(dateParams[1].startsWith("now")){
							String sTimes=dateParams[1].replaceFirst("now", "").replace("+", "");
							//if(sTimes.equals("")){sTimes="0";}
							value+=" "+Tools.getTime(sTimes);							
						}else{
							value+=" "+dateParams[1];
						}
						
					}
				}
				hm.put(key, value);		
				currentParamsObj.put(key, value);
			}
		}
		
		return currentParamsObj.toString();
	}
	
	
	public void actionWrite(String str){
		if(response==null) return;
		Writer w = null;
		
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			
			w = response.getWriter();
			str=str.replaceAll("\n", " ; ");
			w.write(str);
			
			w.flush();
		} catch (IOException e) {
			log.error("unexcepted Exception when {}","actionWrite",e);
			//e.printStackTrace();
		}finally{
			if(w!=null){
				try {
					w.close();
				} catch (IOException e) {
					log.error("unexcepted Exception when {}","actionWrite close Writer",e);
					//e.printStackTrace();
				}
			}
		}
	}

}