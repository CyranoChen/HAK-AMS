package com.tsystems.imfMsg.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tsystems.imfMsg.ImfMessageCache;
import com.tsystems.imfMsg.entity.bo.MsgLog;
import com.tsystems.imfMsg.entity.bo.MsgStore;
import com.tsystems.imfMsg.util.AsynWorkUtil;
import com.tsystems.imfMsg.util.LockedUtil;
import com.tsystems.imfMsg.util.ReturnMessage;
import com.tsystems.jms.action.MessageSender;
import com.tsystems.jms.entity.MessageEntity;
import com.tsystems.nlia.fqdb.service.IBaseDataParse;
import com.tsystems.nlia.fqdb.service.IFlightDataResolveOperateServiceOfFqdb;




@Service("imfClientService")
public class ImfClientService {
	private static final Logger log = LoggerFactory.getLogger(ImfClientService.class);
	
	private static MsgService msgService;
//	private static MessageSender messageSender;	
	private static IFlightDataResolveOperateServiceOfFqdb fssDataParseService;
	private static IBaseDataParse bssDataParseService;	

	private static String[] msgKeyFields;
	
	
	private static void init() {

        msgKeyFields=new String[]{"StandID","FlightStatus","EstimatedLandingDateTime",
        		"EstimatedTakeOffDateTime","ActualLandingDateTime","ActualTakeOffDateTime",
        		"GateID","BaggageBeltID"};
    
        		
	}
	
	static {
		init();
	}
	
	
	public synchronized void readAndSaveFssMessage() {
	    try {
	        boolean loop = true;
	        // when the queue is empty,get data from tmp cache
	        if (ImfMessageCache.getFssMessageCache().peekFirst() == null) {
	            synchronized (ImfMessageCache.getFssMessageTmpCache()) {
	            	ImfMessageCache.getFssMessageCacheFromTmp();
	            }
	        }
	        while (ImfMessageCache.getFssMessageCache().peekFirst() != null && loop) {
	            String receivedMessage = ImfMessageCache.getFssMessageCache().peekFirst();
	
	            saveMsg("FSS",receivedMessage);
	            
	            ImfMessageCache.getFssMessageCache().pollFirst();
	            // when the queue is empty,get data from tmp cache
	            if (ImfMessageCache.getFssMessageCache().peekFirst() == null) {
	                synchronized (ImfMessageCache.getFssMessageTmpCache()) {
	                	ImfMessageCache.getFssMessageCacheFromTmp();
	                }
	            }
	
	
	        }
	
	    } catch (Exception e) {
	        log.error("Exception when readAndSaveFssMessage", e);
	        // e.printStackTrace();
	    } finally {
	    	ImfMessageCache.setFssMessageCacheReading(false);
	    }

    }
	
	public synchronized void readAndSaveBssMessage() {
	    try {
	        boolean loop = true;
	        // when the queue is empty,get data from tmp cache
	        if (ImfMessageCache.getBssMessageCache().peekFirst() == null) {
	            synchronized (ImfMessageCache.getBssMessageTmpCache()) {
	            	ImfMessageCache.getBssMessageCacheFromTmp();
	            }
	        }
	        while (ImfMessageCache.getBssMessageCache().peekFirst() != null && loop) {
	            String receivedMessage = ImfMessageCache.getBssMessageCache().peekFirst();
	
	            saveMsg("BSS",receivedMessage);
	            
	            ImfMessageCache.getBssMessageCache().pollFirst();
	            // when the queue is empty,get data from tmp cache
	            if (ImfMessageCache.getBssMessageCache().peekFirst() == null) {
	                synchronized (ImfMessageCache.getBssMessageTmpCache()) {
	                	ImfMessageCache.getBssMessageCacheFromTmp();
	                }
	            }
	
	
	        }
	
	    } catch (Exception e) {
	        log.error("Exception when readAndSaveFssMessage", e);
	        // e.printStackTrace();
	    } finally {
	    	ImfMessageCache.setBssMessageCacheReading(false);
	    }

    }

	public static boolean saveMsg(String msgType,String msgContent){
		String errCode="1000";		
		try{
			String status="0";
			String keyStatus="0";
			if(msgContent.contains("<SubscriptionStatus>Start</SubscriptionStatus>")||msgContent.contains("<SubscriptionStatus>End</SubscriptionStatus>")){
				status="2";
				keyStatus="2";		
			}else if(msgContent.contains("<SystemException>")){
				status="3";		
				keyStatus="2";
			}
			
			MsgStore msg=new MsgStore(msgType,msgContent, "AMS", 
					new Timestamp(System.currentTimeMillis()),status,keyStatus);
			
			msgService.saveMsgStore(msg);			
	
			if(LockedUtil.getPropertyValue(LockedUtil.key_Tag).equals("1")){
				LockedUtil.setPropertyValue(LockedUtil.key_Tag, "0");										
				AsynWorkUtil.getAsynService().addWork(ImfClientService.class,"updateMsgsKeyValue2",new Object[] {});
			}
			
			if(LockedUtil.getPropertyValue(LockedUtil.sync_Tag).equals("1")&& LockedUtil.getPropertyValue(LockedUtil.parse_Tag).equals("1")){
				LockedUtil.setPropertyValue(LockedUtil.parse_Tag, "0");					
				AsynWorkUtil.getAsynService().addWork(ImfClientService.class,"parseMsgsByApi2",new Object[] {}); 
			}		        
			
				
			
		}catch(Exception e){
			log.error("unexcepted Exception when {}","saveMsg",e);
			//e.printStackTrace();
		}
		if(errCode.equals("1000")){
			return true;
		}else{
			return false;
		}		
		

	}	
	
	//异步更新单条记录
	public static void asynParseMsg(MsgStore msg){				  
		AsynWorkUtil.getAsynService().addWork(DataParseService.class,"callParseApi",new Object[] {msg},new DataParseCallBack()); 	
	}	
	/*******************抽取并更新消息中的关键信息************************************************************************************/
	//批量更新关键值，由新的消息驱动，更新500条
	public static void updateMsgsKeyValue(){
		log.debug("===========start updateMsgsKeyValue");
		try{
			HashMap<String,String> filter=new HashMap<String,String>();		
			filter.put("keyStatus", "0");
			List<MsgStore> msgs=msgService.findMsgStoreList(filter, "asc");
			
			for(int i=0;i<msgs.size();i++){
				updateMsgKeyValue(msgs.get(i));
			}
		}catch(Exception e){
			log.error("unexcepted Exception when {}","updateMsgsKeyValue",e);
			//e.printStackTrace();
		}finally{
			LockedUtil.setPropertyValue(LockedUtil.key_Tag, "1");
		}
	}	
	
	//批量更新关键值,由新的消息驱动，每次500条，递归进行，直到解析完当前库中所有记录
	public static void updateMsgsKeyValue2(){
		log.debug("++++++++++++++++++++++++start updateMsgsKeyValue2");
		try{
			HashMap<String,String> filter=new HashMap<String,String>();		
			filter.put("keyStatus", "0");
			List<MsgStore> msgs=msgService.findMsgStoreList(filter, "asc");
			while(msgs!=null && msgs.size()>0){
				//log.debug("+++++++++++start loop "+msgs.size()+ " msgs");
				for(int i=0;i<msgs.size();i++){
					updateMsgKeyValue(msgs.get(i));
				}
				
				msgs=msgService.findMsgStoreList(filter, "asc");
			}

		}catch(Exception e){
			log.error("unexcepted Exception when {}","updateMsgsKeyValue2",e);
			//e.printStackTrace();
		}finally{
			LockedUtil.setPropertyValue(LockedUtil.key_Tag, "1");
			//log.debug("++++++++++++++++++++++++finished updateMsgsKeyValue2,and set key_Tag="+LockedUtil.getPropertyValue(LockedUtil.key_Tag));
		}
		
	}
	
	//解析xml中部分关键字段至数据表字段
	public static void updateMsgKeyValue(MsgStore msg){	
		log.debug("+++++++++++updateMsgKeyValue: id="+msg.getId());
		try {
			Document docMsg = DocumentHelper.parseText(msg.getMsgContent());
			Element root = docMsg.getRootElement();//root
			Element SysInfo=root.element("SysInfo");
			Element sequenceId=SysInfo.element("MessageSequenceID");
			Element messageType=SysInfo.element("MessageType");
			Element serviceType=SysInfo.element("ServiceType");
			Element operationMode=SysInfo.element("OperationMode");	
			
			msg.setMessageSequenceId(sequenceId.getTextTrim());
			msg.setMessageType(messageType.getTextTrim());
			msg.setServiceType(serviceType.getTextTrim());
			msg.setOperationMode(operationMode.getTextTrim());
			if(msg.getMsgType().equalsIgnoreCase("FSS")){
				Element data=root.element("Data");
				if(data!=null){
					Element primaryKey=data.element("PrimaryKey");
					Element flightKey=primaryKey.element("FlightKey");
					Element flightScheduledDate=flightKey.element("FlightScheduledDate");
					Element flightIdentity=flightKey.element("FlightIdentity");
					Element flightDirection=flightKey.element("FlightDirection");				
	
					msg.setFlightScheduledDate(flightScheduledDate.getTextTrim());
					msg.setFlightIdentity(flightIdentity.getTextTrim());
					msg.setFlightDirection(flightDirection.getTextTrim());	

				}
				
			}

			msg.setKeyStatus(Long.parseLong("1"));
			
			msgService.updateMsgKeyValueByHql(msg);				
		} catch (DocumentException e) {
			log.error("unexcepted Exception when {}:"+msg.getMsgType()+","+msg.getReceiver()+",storeId="+msg.getId(),"updateMsgKeyValue",e);
			//e.printStackTrace();

		}			
		
	}	
	/*******************解析消息入库************************************************************************************/		
	//批量解析入库,由新的消息驱动，解析500条
	public static void parseMsgsByApi(){
		log.debug("===========start parseMsgsByApi");
		try{
			HashMap<String,String> filter=new HashMap<String,String>();		
			filter.put("status", "0");
			List<MsgStore> msgs=msgService.findMsgStoreList(filter, "asc");
			
			for(int i=0;i<msgs.size();i++){
				callParseApi3Times(msgs.get(i));				
			}
		}catch(Exception e){
			log.error("unexcepted Exception when {}","parseMsgsByApi",e);
			//e.printStackTrace();
		}finally{
			LockedUtil.setPropertyValue(LockedUtil.parse_Tag, "1");
		}
	}
	//批量解析入库,由新的消息驱动，每次500条，递归进行，直到解析完当前库中所有记录
	public static void parseMsgsByApi2(){
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>start parseMsgsByApi2");
		try{
			HashMap<String,String> filter=new HashMap<String,String>();		
			filter.put("status", "0");
			List<MsgStore> msgs=msgService.findMsgStoreList(filter, "asc");
			while(msgs!=null && msgs.size()>0){
				log.debug(">>>>>>>>>>start loop "+msgs.size()+ " msgs");
				for(int i=0;i<msgs.size();i++){
					callParseApi3Times(msgs.get(i));
					
				}
				
				msgs=msgService.findMsgStoreList(filter, "asc");	
			}
		}catch(Exception e){
			log.error("unexcepted Exception when {}","parseMsgsByApi2",e);
			//e.printStackTrace();
		}finally{
			LockedUtil.setPropertyValue(LockedUtil.parse_Tag, "1");
			//log.debug(">>>>>>>>>>>>>>>>>>>>>>>>finished parseMsgsByApi2,and set parse_Tag="+LockedUtil.getPropertyValue(LockedUtil.parse_Tag));
		}
		
	}
	//每条消息解析入库默认最多解析3次，只要一成功就跳出循环
	public static String callParseApi3Times(MsgStore msg){
		log.debug(">>>>>>>>>>callParseApi3Times: id="+msg.getId());
		String rs="";
		String errCode="";
		for(int k=0;k<3;k++){
			//log.debug(">>>>>>>>>>callParseApi "+(k+1)+" time");	
			rs=callParseApi(msg);
			errCode=rs.split(":")[0];
			if(errCode.equals("1000")||errCode.equals("1001")||errCode.equals("1002")){break;}
		}
		finishParseMsg(rs);
		return errCode;
	}
	//调用接口解析数据入库
	public static String callParseApi(MsgStore msg){
		log.debug(">>>>>>>>>>start callParseApi: id="+msg.getId());		
		String errCode="1000";
		
		String apiResult="";
		
		try{
			if(msg.getMsgType().equalsIgnoreCase("FSS")){
				apiResult=fssDataParseService.parseFlightDataOfXmlString(msg.getMsgContent());
			}else if(msg.getMsgType().equalsIgnoreCase("BSS")){
				apiResult=bssDataParseService.changeParseOfBaseData(msg.getMsgContent());
			}
			
			if(apiResult.contains("success")){
//							
//				//send message to activeMQ 
//				try{
//					MessageEntity messageEntity = new MessageEntity();
//					messageEntity.setId(1L);
//					messageEntity.setName(msg.getMsgType()+"."+msg.getReceiver());
//					messageEntity.setType(apiResult.split(":")[2]);
//					messageEntity.setContent(apiResult.split(":")[1]);
//					messageSender.send(messageEntity);
//				}catch(Exception e){
//					log.error("unexcepted Exception when {}","callParseApi",e);
//					//e.printStackTrace();
//					errCode="1006";
//				}
//							
			}else if(apiResult.contains("failure")){
				//get errorCode from api
				int apiErrorCode=Integer.parseInt(apiResult.split(":")[1]);
				switch(apiErrorCode){
					case 0:	errCode="1001";
							break;
					case 1:	errCode="1002";
							break;
					default:errCode="1003";
				}
				
	
			}else{
				errCode="1004";
			}
		}catch(Exception e){
			log.error("unexcepted Exception when {}:"+msg.getMsgType()+","+msg.getReceiver()+",storeId="+msg.getId(),"callParseApi",e);
			//e.printStackTrace();
			errCode="1005";
		}

		return errCode+":"+msg.getId();
	}
	//调用接口解析完毕后更新记录状态
	public static void finishParseMsg(String parseResult){		
		log.debug(">>>>>>>>>>start finishParseMsg: parseResult="+parseResult);
		String errCode=parseResult.split(":")[0];
		String msgId=parseResult.split(":")[1];
		
		MsgStore msg=msgService.loadMsgStoreById(msgId);	
		String operationInfo="failure";
		
		if(errCode.equals("1000")||errCode.equals("1001")||errCode.equals("1002")){
			msg.setStatus(Long.parseLong("1"));
			operationInfo="success";
		}else if(errCode.equals("1006")){
			msg.setStatus(Long.parseLong("8"));
			operationInfo="warning";
		}else{
			msg.setStatus(Long.parseLong("9"));
		}		
		msg.setOperateTime(new Timestamp(System.currentTimeMillis()));
		
		msgService.updateMsgStatusByHql(msg);	
		//msgService.updateMsgStore(msg);
		//log.debug(">>>>>>>>>>updateMsgStore id="+msg.getId());
		
		ReturnMessage message=new ReturnMessage();
		MsgLog msgLog=new MsgLog(msgId, "update",new Timestamp(System.currentTimeMillis()),
				operationInfo,message.getReturnMessage(errCode));
		msgService.saveMsgLog(msgLog);	
		//log.debug(">>>>>>>>>>saveMsgLog msgId="+msgLog.getStoreId());
		
		//log.debug(">>>>>>>>>>finished finishParseMsg");
	}	
	

	
	/*******************对解析失败的消息再次解析************************************************************************************/
	//再次解析FSS数据
	public static String parseFssMsgByApiAgain(MsgStore msg){	
		log.debug("===========start parseFssMsgByApiAgain: id="+msg.getId());
		if("0".equals(msg.getKeyStatus().toString())){//更新关键字段，以便获得三要素
			ImfClientService.updateMsgKeyValue(msg);
		}
		//获得最新的同航班消息
		MsgStore latestMsg=msgService.findLatestMsgStoreWithSamePK(msg.getReceiver(),
				msg.getFlightScheduledDate(),msg.getFlightIdentity(),msg.getFlightDirection());
		
		//通过接口将最新的航班消息的数据全部更新入库(试3次)
		String rs="";
		String errCode="";
		for(int k=0;k<3;k++){
			rs=callFssFullParseApi(latestMsg);
			errCode=rs.split(":")[0];
			if(errCode.equals("1000")||errCode.equals("1001")||errCode.equals("1002")){break;}
		}
		
		if(errCode.equals("1000")||errCode.equals("1001")||errCode.equals("1002")){
			if(msg.getId()!=latestMsg.getId()){//若当前的消息和最新的关联消息不是同一条
				finishParseMsg("1000:"+msg.getId());//更新当前消息状态
			}
			finishParseMsg(rs);//更新被解析入库的关联消息状态
		}
		
		return errCode;
	}
	//解析消息到全数据解析的接口（新）中
	public static String callFssFullParseApi(MsgStore msg){
		log.debug("===========start callFssFullParseApi: id="+msg.getId());
		String errCode="1000";
		
		String apiResult="";
		
		try{
			if(msg.getMsgType().equalsIgnoreCase("FSS") ){
				apiResult=fssDataParseService.parseFlightDataOfXmlString(msg.getMsgContent());
			}
			
			if(apiResult.contains("failure")){
				//get errorCode from api
				int apiErrorCode=Integer.parseInt(apiResult.split(":")[1]);
				switch(apiErrorCode){
					case 0:	errCode="1001";
							break;
					case 1:	errCode="1002";
							break;
					default:errCode="1003";
				}
				
	
			}else if(!apiResult.contains("success")){
				errCode="1004";
			}
		}catch(Exception e){
			log.error("unexcepted Exception when {}:"+msg.getMsgType()+","+msg.getReceiver()+",storeId="+msg.getId(),"callFssFullParseApi",e);
			//e.printStackTrace();
			errCode="1005";
		}

		return errCode+":"+msg.getId();
	}
	
	
	/*******************************************************************************************************/
	public static MsgService getMsgService() {
		return msgService;
	}
	
	@Autowired
	public void setMsgService(@Qualifier(value="msgService")MsgService msgService) {
		ImfClientService.msgService = msgService;
	}
	
//	@Autowired
//	public void setMessageSender(@Qualifier(value="messageSender")MessageSender messageSender) {
//		ImfClientService.messageSender = messageSender;
//	}	
	
	@Autowired
	public void setIFlightDataResolveOperateServiceOfFqdb(@Qualifier(value="fssDataParseService")IFlightDataResolveOperateServiceOfFqdb fssDataParseService) {
		ImfClientService.fssDataParseService = fssDataParseService;
	}	

	
	@Autowired
	public void setIBaseDataParse(@Qualifier(value="bssDataParseService")IBaseDataParse bssDataParseService) {
		ImfClientService.bssDataParseService = bssDataParseService;
	}

}
