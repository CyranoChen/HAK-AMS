package com.tsystems.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsystems.jms.action.MessageSender;
import com.tsystems.jms.entity.MessageEntity;

/** 
 * @ClassName: ActiveMQController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author lushuaifeng
 * @date 2013-7-8 下午03:43:17 
 *  
 */
@Controller
@RequestMapping("/activeMQ")
public class ActiveMQController {
	private static MessageSender messageSender;	
    private static final Logger log = LoggerFactory.getLogger(ActiveMQController.class);
	
	@RequestMapping(value = "sendAlarmMsg/{message}", method = RequestMethod.GET)
	protected @ResponseBody
	String send(@PathVariable String message) {
		String status="true";
		//the format of requestParam message:"FQDB:objectId"
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setId(1L);
		messageEntity.setName(message.split(":")[0]+"."+message.split(":")[1]);
		messageEntity.setType("");
		messageEntity.setContent(message.split(":")[2]);
		try{
			messageSender.send(messageEntity);
		}catch(Exception e){
			status="false";
			log.error("unexcepted Exception when {}","sendAlarmMsg",e);
			//e.printStackTrace();
		}
		return "{\"success\": "+status+"}";
	}
	
	
	@Autowired
	public void setMessageSender(@Qualifier(value="messageSender")MessageSender messageSender) {
		ActiveMQController.messageSender = messageSender;
	}	
}
