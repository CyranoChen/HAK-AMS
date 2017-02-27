/** 
 * Copyright (c) 1995-2011 Tsystems Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Tsystems.
 * You shall not disclose such Confidential Information and shall use it only in accordance 
 * with the terms of the license agreement you entered into with Tsystems. 
 *
 */

package com.tsystems.imfMsg.action;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tsystems.imfMsg.entity.bo.MsgStore;
import com.tsystems.imfMsg.entity.bo.MsgStoreHistory;
import com.tsystems.imfMsg.entity.vo.Page;
import com.tsystems.imfMsg.imfClient.BssClient;
import com.tsystems.imfMsg.imfClient.FssClient;
import com.tsystems.imfMsg.service.ImfClientService;
import com.tsystems.imfMsg.service.MsgService;
import com.tsystems.imfMsg.util.LockedUtil;

@ParentPackage("struts-default")
@Namespace(value="/imfMsg")
@Controller("imfMsgAction")
@Scope("prototype")
public class ImfMsgAction extends ActionSupport {
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
	//public HttpSession session = request.getSession();	

    private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MsgService msgService;
/*	@Autowired
	public void setMsgService(@Qualifier("msgService")MsgService service) {
		this.msgService = service;
	}*/
	
	@Action(value="setSyncSwitch")
	public String setSyncSwitch(){
		String type=request.getParameter("type");
		String tag=request.getParameter("tag");
		String switchType=LockedUtil.getTagType(type);
		LockedUtil.setPropertyValue(switchType,tag);
		String value=LockedUtil.getPropertyValue(switchType);
		if(value.equals(tag)){
			actionWrite("{\"success\":true}");
		}else{
			actionWrite("{\"success\":false}");
		}
		
		return null;
	}
	
	@Action(value="getSyncSwitch")
	public String getSyncSwitch(){
		String type=request.getParameter("type");
		String switchType=LockedUtil.getTagType(type);
		String value=LockedUtil.getPropertyValue(switchType);
			actionWrite("{\"value of "+switchType+"\":"+value+"}");

		
		return null;
	}	
	
	@Action(value="showIndex",results={@Result(name="success",location="/jsp/imfMsg/index.jsp")})
	public String showIndex() {	 
		request.setAttribute("syncStatus", LockedUtil.getPropertyValue(LockedUtil.sync_Tag));
		return "success";
	}
	
	@Action(value="refreshStatus",results={@Result(name="success",location="/jsp/imfMsg/index.jsp")})
	public String refreshStatus() {	
		request.setAttribute("syncStatus", LockedUtil.getPropertyValue(LockedUtil.sync_Tag));	
        request.setAttribute("FssStatus", FssClient.connectionStatus());
        request.setAttribute("BssStatus", BssClient.connectionStatus());
		return "success";
	}
	
	@Action(value="showList",results={@Result(name="success",location="/jsp/imfMsg/list.jsp")})
	public String showList() {
		HashMap<String,String> filter=new HashMap<String,String>();		
		Map<String,String> mParams=request.getParameterMap();		
		Set<String> key = mParams.keySet();
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String paramName =  it.next();
            String paramValue=request.getParameter(paramName);
            if(!paramValue.equals("")){
            	filter.put(paramName, paramValue);
            }
        }
		
        if(filter.get("pageNo")==null||filter.get("pageNo").equals("")){
        	 filter.put("pageNo", "1");
        }
        
		Page msgPage=msgService.findMsgStoreByPage(filter, "desc");
		
		request.setAttribute("page", msgPage);
		Set<String> filterKey = filter.keySet();
        for (Iterator<String> itFilter = filterKey.iterator(); itFilter.hasNext();) {
            String filterName =  itFilter.next();
            String filterValue=filter.get(filterName);
            request.setAttribute(filterName, filterValue);
        }
		return "success";
	}	
	

	
	@Action(value="showView",results={@Result(name="success",location="/jsp/imfMsg/view.jsp")})
	public String showView() {	
		String msgId=request.getParameter("id");
				
		MsgStore msg=msgService.loadMsgStoreById(msgId);

		request.setAttribute("msg", msg);
		return "success";
	}
	
	//更新获取某条消息的关键值
	@Action(value="getKeyValue")
	public String getKeyValue() {	
		String msgId=request.getParameter("id");
		MsgStore msg=msgService.loadMsgStoreById(msgId);
		ImfClientService.updateMsgKeyValue(msg);
		actionWrite("{\"success\":true}");
		return null;
	}
	
	//查询所有已解析或解析失败的消息中关键值未更新的记录，并更新这些记录的关键值
	@Action(value="updateAllMissedKeyValue")
	public String updateAllMissedKeyValue() {
		log.info("----updateAllMissedKeyValue manual starting......");
		updateAllMissedKeyValueByBatch();
		log.info("----updateAllMissedKeyValue manual finished");
		actionWrite("{\"success\":true}");
		return null;
	}
	//分批次更新关键值，直到更新完
	private void updateAllMissedKeyValueByBatch() {		
		int recNum=0;
		HashMap<String,String> filter=new HashMap<String,String>();		
		filter.put("keyStatus", "0");
		List<MsgStore> msgs=msgService.findMsgStoreList(filter, "asc");
		while(msgs!=null && msgs.size()>0){			
			for(int i=0;i<msgs.size();i++){
				ImfClientService.updateMsgKeyValue(msgs.get(i));
				//log.info("----"+i+" ok....");
			}
			recNum+=msgs.size();
			log.info("----"+recNum+" records update......");
			msgs=msgService.findMsgStoreList(filter, "asc");
		}
	}
	
	//接收消息
	@Action(value="receive")
	public String receiveMsg() {
		String msgType=request.getParameter("msgType");
		String msgContent=request.getParameter("msgContent");
		boolean suc=ImfClientService.saveMsg(msgType, msgContent);
		if(suc){
			actionWrite("{\"success\":true}");
		}else{
			actionWrite("{\"success\":false}");
		}
		return null;
	}
	//解析消息入库
	@Action(value="send")
	public String sendMsg() {	
		String msgId=request.getParameter("id");
			
		String errCode="1000";
		MsgStore msg=msgService.loadMsgStoreById(msgId);

		errCode=ImfClientService.callParseApi3Times(msg);
				
		if(errCode.equals("1000")||errCode.equals("1001")||errCode.equals("1002")){
			actionWrite("{\"success\":true}");
		}else{
			actionWrite("{\"success\":false}");
		}
		return null;
	}
	//解析符合条件的所有消息入库（发送至对应的解析接口）
	@Action(value="sendAll")
	public String sendAll() {
		log.info("----sendAll manual starting......");
		HashMap<String,String> filter=new HashMap<String,String>();		
		Map<String,String> mParams=request.getParameterMap();		
		Set<String> key = mParams.keySet();
        for (Iterator<String> it = key.iterator(); it.hasNext();) {
            String paramName =  it.next();
            String paramValue=request.getParameter(paramName);
            if(!paramValue.equals("")){
            	filter.put(paramName, paramValue);
            }
        }
        
        String sendResult=sendByBatch(filter);
        
		log.info("----sendAll manual finished");
		actionWrite("{\"success\":true,\"recNum\":"+sendResult+"}");
		return null;
	}
	//按条件分批次解析
	private String sendByBatch(HashMap<String,String> filter){		
		int successRec=0;  
        int totalRec=0;
        
		List<MsgStore> msgs=msgService.findMsgStoreList(filter, "asc");	
		
		while(msgs!=null && msgs.size()>0){

			for(int i=0;i<msgs.size();i++){
				String errCode=ImfClientService.callParseApi3Times(msgs.get(i));

				if(errCode.equals("1000")||errCode.equals("1001")||errCode.equals("1002")){
					successRec++;
				}
			}
			totalRec+=msgs.size();
			log.info("----"+totalRec+" records send......");

			msgs=msgService.findMsgStoreList(filter, "asc");	
		}
		
		return successRec+"/"+totalRec;
	}
	
	//从历史数据库中获取符合条件的消息并保存入消息数据库（模拟批量接收）
	@Action(value="imitateImfSend")
	public String resendAll() {
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
	        
		List<MsgStoreHistory> msgs=msgService.findMsgStoreHistoryList(filter, "asc");
		
		for(int i=0;i<msgs.size();i++){
			MsgStoreHistory msh=msgs.get(i);
			ImfClientService.saveMsg(msh.getMsgType(), msh.getMsgContent());
		}

		actionWrite("{\"success\":true}");
		return null;
	}	
	
	
	@Action(value="startIMFListener")
	public String startIMFListener() {
		String msgType=request.getParameter("msgType");
		boolean suc=false;
		if(msgType.equalsIgnoreCase("FSS")){
			suc=FssClient.start();
		}else if(msgType.equalsIgnoreCase("BSS")){
			suc=BssClient.start();
		}
		if(suc){
			actionWrite("{\"success\":true}");
		}else{
			actionWrite("{\"success\":false}");
		}
		return null;
	}	
	
	@Action(value="stopIMFListener")
	public String stopIMFListener() {
		String msgType=request.getParameter("msgType");
		boolean suc=false;
		if(msgType.equalsIgnoreCase("FSS")){
			suc=FssClient.stop();
		}else if(msgType.equalsIgnoreCase("BSS")){
			suc=BssClient.stop();
		}
		if(suc){
			actionWrite("{\"success\":true}");
		}else{
			actionWrite("{\"success\":false}");
		}
		return null;
	}
	
	@Action(value="restartIMFListener")
	public String restartIMFListener() {
		String msgType=request.getParameter("msgType");
		boolean suc=false;
		if(msgType.equalsIgnoreCase("FSS")){
			suc=FssClient.stop();
			if(suc){suc=FssClient.start();}
		}else if(msgType.equalsIgnoreCase("BSS")){
			suc=BssClient.stop();
			if(suc){suc=BssClient.start();}
		}
		if(suc){
			actionWrite("{\"success\":true}");
		}else{
			actionWrite("{\"success\":false}");
		}
		return null;
	}
	


	
	public void actionWrite(String str){
		if(response==null) return;
		Writer w = null;
		
		try {
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			
			w = response.getWriter();
			
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
