package com.tsystems.imfMsg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tsystems.imfMsg.entity.bo.MsgStore;
import com.tsystems.jms.action.MessageSender;
import com.tsystems.jms.entity.MessageEntity;
import com.tsystems.nlia.fqdb.service.IBaseDataParse;
import com.tsystems.nlia.fqdb.service.IFlightDataResolveOperateServiceOfFqdb;



@Service("dataParseService")
public class DataParseService {
	private static final Logger log = LoggerFactory.getLogger(DataParseService.class);
	
	private static MessageSender messageSender;	
	private static IFlightDataResolveOperateServiceOfFqdb fssDataParseService;
	private static IBaseDataParse bssDataParseService;	

	
		
	public static String callParseApi(MsgStore msg){		
		String errCode="1000";
		
		String apiResult="";
		
		try{
			if(msg.getMsgType().equalsIgnoreCase("FSS")&& msg.getReceiver().equalsIgnoreCase("FQDB") ){
				apiResult=fssDataParseService.parseFlightDataOfXmlString(msg.getMsgContent());
			}else if(msg.getMsgType().equalsIgnoreCase("BSS")&& msg.getReceiver().equalsIgnoreCase("FQDB") ){
				apiResult=bssDataParseService.changeParseOfBaseData(msg.getMsgContent());
			}
			
			if(apiResult.contains("success")){
							
				//send message to activeMQ 
				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setId(1L);
				messageEntity.setName(msg.getMsgType()+"."+msg.getReceiver());
				messageEntity.setType(apiResult.split(":")[2]);
				messageEntity.setContent(apiResult.split(":")[1]);
				messageSender.send(messageEntity);
							
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
			log.error("unexcepted Exception when {}","callParseApi",e);
			//e.printStackTrace();
			errCode="1004";
		}

		return errCode+":"+msg.getId();
	}

	@Autowired
	public void setMessageSender(@Qualifier(value="messageSender")MessageSender messageSender) {
		DataParseService.messageSender = messageSender;
	}	
	
	@Autowired
	public void setIFlightDataResolveOperateServiceOfFqdb(@Qualifier(value="fssDataParseService")IFlightDataResolveOperateServiceOfFqdb fssDataParseService) {
		DataParseService.fssDataParseService = fssDataParseService;
	}	

	
	@Autowired
	public void setIBaseDataParse(@Qualifier(value="bssDataParseService")IBaseDataParse bssDataParseService) {
		DataParseService.bssDataParseService = bssDataParseService;
	}
	
	

}
