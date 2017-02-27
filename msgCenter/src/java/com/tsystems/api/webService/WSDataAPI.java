package com.tsystems.api.webService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tsystems.api.model.bo.McApiInfo;
import com.tsystems.api.model.bo.McApiLog;
import com.tsystems.api.model.vo.XmlModel;
import com.tsystems.api.service.DataOperationService;
import com.tsystems.api.service.McApiInfoService;
import com.tsystems.api.service.RestAPIService;
import com.tsystems.api.util.Tools;
import com.tsystems.api.util.WSResult;
import com.tsystems.api.util.XmlParser;





/**
 * 接口，用于接受数据提供方的数据。
 * @author lushuaifeng
 *
 */ 
@Service("wsDataAPIService")
public class WSDataAPI {

	/**
	 * 导入外部应用数据
	 * @param userName 用户名。
	 * @param password 用户密码（采用MD5加密）。
	 * @param date 业务数据对应日期。（与XML根节点上<root date="YYYY-MM-DD">一致）.
	 * @param content 字符串（XML格式，不限长度）。
	 * @return 1000 成功；1001 数据量过大，请分批查询;1002 查无相关数据;1003 操作成功;
	 * 0001 用户信息错误;0002 数据类型错误;0003 操作失败;
	 * 0004 内部错误:应用接口异常;	0005 数据日期效验失败;0006 数据格式效验失败");			
	 */
	private static final Logger log = LoggerFactory.getLogger(WSDataAPI.class);
	private static McApiInfoService mcApiInfoService;		
	
	public String getDataInfo(String userName, String password,String date1,String date2,String dataType ,String batch,String clientIP){
		String msg="";			
		String errCode="1000";//查询成功
		String exception="";
		HashMap<String,String> checkResult=checkUserValiate(errCode,userName, password,dataType,clientIP,"get");

		errCode=checkResult.get("errCode");
		String detailMsg=checkResult.get("detailMsg");		
		String serverUrl=checkResult.get("params");
		String currentUserId=checkResult.get("currentUserId");
		McApiLog apiLog=new McApiLog(currentUserId,clientIP,new Timestamp(System.currentTimeMillis()),"manual","get");
		WSResult wsRs=new WSResult();

		if(errCode.equals("1000")){
			HashMap<String,String> hmParams=new HashMap<String,String>();
			hmParams.put("startdate", date1);
			hmParams.put("enddate", date2);
			hmParams.put("batch", batch);
			String result=RestAPIService.getData(serverUrl, hmParams);
			//String result="[{'flightBase':{'testField1':'111中文测试','testField2':'112'},'flightData':{'testField3':'121','testField4':'122'}},{'flightBase':{'testField1':'211','testField2':'212'},'flightData':{'testField3':'221','testField4':'222'}}]";
			//String result="[{'count':'535','max':'100'}]";
			//String result="[]";
			if(result.startsWith("failure:")){
				errCode="0003";
				detailMsg=":应用内部接口调用失败";
				exception=result.replaceFirst("failure:", "");
			}else{
				try{
					JSONArray jsonArray=JSONArray.fromObject(result);
					
					if(jsonArray==null||jsonArray.size()==0){
						errCode="1002";
					}else{
						for(int i=0;i<jsonArray.size();i++){
							JSONObject jsonObj=jsonArray.getJSONObject(i);
							List<String[]> pars=new ArrayList<String[]>();
							if(jsonObj.containsKey("result")&&jsonObj.getString("result").equals("fail")){
								errCode="0003";
								detailMsg=":应用内部接口调用失败";
								exception=result;
							}else if(jsonObj.containsKey("count")){
								pars.add(new String[]{"count",(String)jsonObj.get("count")});
								pars.add(new String[]{"max",(String)jsonObj.get("max")});
								detailMsg=":共"+(String)jsonObj.get("count")+"条，每批最多获取" +(String)jsonObj.get("max")+"条";	
								errCode="1001";
							}else{
								HashMap<String,String> attributeHm=new HashMap<String,String>();
								attributeHm.put("date", Tools.getCurrentDate());
								attributeHm.put("type",dataType);
								String xml=Tools.json2Xml(jsonObj, "root",attributeHm);
								pars.add(new String[]{"content",xml});
							}
							wsRs.setParamWithFieldList(pars);
						}		
					}
				}catch(Exception e){
					errCode="0003";
					detailMsg=":接口内部程序异常";
					exception=e.getMessage();
					log.error("unexcepted Exception when {}","getDataInfo",e);
					//e.printStackTrace();
				}
			}
		}
		wsRs.setCode(errCode);
		wsRs.setDescription(detailMsg);		
		msg=wsRs.getResultXML();
		apiLog.setCallResult(errCode.startsWith("100")?"success":"failure");
		apiLog.setBackInfo(errCode.equals("1000")?"":msg);
		apiLog.setException(exception);
		mcApiInfoService.saveLog(apiLog);
		return msg;
	}
	
	
	public String setDataInfo(String userName, String password,String date,String content,String clientIP){
		String msg="";			
		String errCode="1003";//操作成功
		String detailMsg="";
		String currentUserId="";
		String params="";
		String exception="";
		XmlParser xmlParser=new XmlParser();
		XmlModel xmlModel=xmlParser.parse(content);
		if(xmlModel==null){
			errCode="0003";
			detailMsg=":数据格式效验失败";
		}else if(!xmlModel.getDate().equals(date)){
			errCode="0003";
			detailMsg=":数据日期效验失败";
		}else{
			HashMap<String,String> checkResult=checkUserValiate(errCode,userName, password,xmlModel.getType(),clientIP,"set");
			errCode=checkResult.get("errCode");
			detailMsg=checkResult.get("detailMsg");		
			params=checkResult.get("params");
			currentUserId=checkResult.get("currentUserId");
		}
				
		WSResult wsRs=new WSResult();
		McApiLog apiLog=new McApiLog(currentUserId,clientIP,new Timestamp(System.currentTimeMillis()),"manual","set");
		if(errCode.equals("1003")){			
			HashMap<String,String> operateResult=DataOperationService.operate(xmlModel, params, wsRs);
			errCode=operateResult.get("errCode");
			detailMsg=operateResult.get("detailMsg");		
			exception=operateResult.get("exception");												
		}

		wsRs.setCode(errCode);
		wsRs.setDescription(detailMsg);		
		msg=wsRs.getResultXML();
		
		apiLog.setCallResult(errCode.startsWith("100")?"success":"failure");
		apiLog.setBackInfo(msg);
		apiLog.setException(exception);
		apiLog.setFinishTime(new Timestamp(System.currentTimeMillis()));
		mcApiInfoService.saveLog(apiLog);
		
		return msg;
	}
	
	private HashMap<String,String> checkUserValiate(String errCode,String userName, String password,String dataType,String clientIP,String methodName){
		log.info("WebServiceAPI was Called:"+userName+"("+clientIP+") "+methodName+" "+dataType);		
		HashMap<String,String> checkResult=new HashMap<String,String>();
		String currentUserId="";
		String params="";
		String detailMsg="";
		
		List<McApiInfo> matchedUsers=new ArrayList<McApiInfo>();
		if(userName!=null && !userName.equals("")&& password!=null && !password.equals("")){			
			List<McApiInfo> users=mcApiInfoService.findByUserName(userName);			
			if(users==null ||users.size()==0){
				errCode="0001";
				detailMsg=":无此用户";
			}else{
				
				for(int i=0;i<users.size();i++){
					McApiInfo user=(McApiInfo)users.get(i);
					if(Tools.getMD5(user.getPassword()).equals(password)&& user.getMethodName().contains(methodName) && (user.getClientIP().contains(clientIP)||user.getClientIP().equalsIgnoreCase("xxx.xxx.xxx.xxx"))){
						matchedUsers.add(user);
					}
				}
				if(matchedUsers.size()==0){
					errCode="0001";
					detailMsg=":密码错误/无"+methodName+"权限/IP限制["+clientIP+"]";
				}else{
					if(dataType!=null && !dataType.equals("")){
						
						for(int j=0;j<matchedUsers.size();j++){
							
							McApiInfo matchedUser=(McApiInfo)matchedUsers.get(j);
							
							if(matchedUser.getDataType().equalsIgnoreCase(dataType)){
								currentUserId=matchedUser.getId();
								params=matchedUser.getParams();
								break;
							}
							
						}
					}else{
						errCode="0002";
						detailMsg=":数据类型不可为空";
					}
					
					if(currentUserId==null){
						errCode="0002";
						detailMsg=":当前用户无此数据类型权限";
					}	
				}
				
			}
			
		}else{
			errCode="0001";
			detailMsg=":用户名或密码不可为空";			
		}
		checkResult.put("errCode", errCode);
		checkResult.put("detailMsg", detailMsg);
		checkResult.put("currentUserId", currentUserId);
		checkResult.put("params", params);
		return checkResult;
	}		
	
	
	public static McApiInfoService getMcApiInfoService() {
		return mcApiInfoService;
	}
	
	@Autowired
	public void setMcApiInfoService(@Qualifier(value="mcApiInfoService")McApiInfoService mcApiInfoService) {
		WSDataAPI.mcApiInfoService = mcApiInfoService;
	}	

}
