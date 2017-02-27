package com.tsystems.api.service;

import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.tsystems.api.model.vo.XmlModel;
import com.tsystems.api.util.Tools;
import com.tsystems.api.util.WSResult;


public class DataOperationService {
    private static final Logger log = LoggerFactory.getLogger(DataOperationService.class);
	
	private static void init() {
		
	}
	
	static {
		init();
	}		
	
	public static HashMap<String,String> operate(XmlModel xmlModel,String params,WSResult wsr){
		if(xmlModel.getType().equals("FSS2")){
			return operateFSS2(xmlModel,params,wsr);
		}else{
			return null;
		}
		
	}
	private static HashMap<String,String> operateFSS2(XmlModel xmlModel,String params,WSResult wsr){
		HashMap<String,String> result=new HashMap<String,String>();
		String errCode="1003";
		String exception="";
		String detailMsg="";
		
		try{
			JSONObject parObj=JSONObject.fromObject(params);
			JSONObject record=xmlModel.getRecord();
			if(record==null){
				errCode="0003";	
				detailMsg=":数据内容为空";
				
			}else if(!record.containsKey("FlightBase")){
				errCode="0003";	
				detailMsg=":数据内容缺少主键信息";
				
			}else{
				JSONObject serviceInfoObj=new JSONObject();
				Boolean hasKeyValue=false;
				//JSONObject fssObject=new JSONObject();
				
				Iterator it = record.keys();  //key={FlightBase,FlightData,FlightResource,FlightServiceInfo}
				while (it.hasNext()) {  
					String key = (String) it.next();  
					JSONObject values = record.getJSONObject(key);  
					
					if(key.equalsIgnoreCase("FlightServiceInfo")){
						Iterator iv = values.keys(); 
						while(iv.hasNext()){
							String vkey=(String)iv.next();
							serviceInfoObj.put(vkey, Tools.formatDateTime(values.getString(vkey)));//format datetime from 'yyyy-MM-ddTHH:mm:ssZ' or 'yyyy-MM-dd HH:mm:ss.iii' to 'yyyy-MM-dd HH:mm:ss',if not datetime return string 
						}
					}else{
						if(key.equalsIgnoreCase("FlightBase")){					
							String flightScheduledDate=values.getString("flightScheduledDate");
							String flightIdentity=values.getString("flightIdentity");
							String flightDirection=values.getString("flightDirection");
							if(flightScheduledDate==null||flightIdentity==null||flightDirection==null){
								errCode="0003";
								detailMsg=":数据内容主键信息(flightScheduledDate+flightIdentity+flightDirection)不全";
							}else{
								serviceInfoObj.put("flightBaseDate", Tools.formatDateTime(flightScheduledDate));
								serviceInfoObj.put("flightBaseIdentity", flightIdentity);
								serviceInfoObj.put("flightBaseDirection", flightDirection);
								hasKeyValue=true;
							}
						}
						
						//fssObject.put(key, values);
						
					}
															
				}
				
				if(errCode.startsWith("100")&& serviceInfoObj!=null && hasKeyValue && serviceInfoObj.keySet().size()>3){
					String serverUrl =parObj.getString("serviceInfoRestApi");
					
					HashMap<String,String> hmParams=new HashMap<String,String>();
					JSONObject flightServiceInfoVoListObj=new JSONObject();
					JSONObject flightServiceInfoVoObj=new JSONObject();
					flightServiceInfoVoObj.put("FlightServiceInfoVo", serviceInfoObj);
					flightServiceInfoVoListObj.put("FlightServiceInfoVoList", flightServiceInfoVoObj);
					hmParams.put("serviceInfo", Tools.json2Xml(flightServiceInfoVoListObj, "root", new HashMap<String,String>()));
					hmParams.put("type", "update");
					String back=RestAPIService.getData(serverUrl, hmParams);
			
					if(back.startsWith("failure:")){
						errCode="0003";
						detailMsg=":应用内部接口调用失败";
						exception=back.replaceFirst("failure:", "");
					}else{						
						JSONObject obj=JSONObject.fromObject(back);
						if(obj.containsKey("result")){
							if(obj.getString("result").equalsIgnoreCase("fail")){
								errCode="0003";
								if(obj.containsKey("err")&&obj.getString("err").equalsIgnoreCase("noRecord")){
									detailMsg=":无对应服务确认单";
								}else{
									detailMsg=":应用内部接口调用失败";								
								}
							}else if(obj.containsKey("detail")){
								detailMsg=obj.getString("detail");
							}

						}
						
					}
				}
/*					
				if(errCode.startsWith("100") && fssObject!=null && fssObject.containsKey("FlightBase")){
					String serverUrl =parObj.getString("fssRestApi");
					
					HashMap<String,String> hmParams=new HashMap<String,String>();
					hmParams.put("serviceInfo", fssObject.toString());
					String back=RestAPIService.getData(serverUrl, hmParams);
			
					if(back.startsWith("failure:")){
						errCode="0003";
						detailMsg=":应用内部接口调用失败";
						exception=back.replaceFirst("failure:", "");
					}
				}
				*/
				
			}

		}catch(Exception e){
			errCode="0003";
			detailMsg=":接口内部程序异常";
			exception=e.getMessage();
			log.error("unexcepted Exception when {}","operateFSS2",e);
			//e.printStackTrace();
		}
		result.put("errCode", errCode);
		result.put("detailMsg", detailMsg);
		result.put("exception", exception);
		return result;
	
	}
}
