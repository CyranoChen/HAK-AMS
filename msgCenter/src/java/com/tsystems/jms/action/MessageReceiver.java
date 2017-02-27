package com.tsystems.jms.action;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.tsystems.jms.entity.MessageEntity;

@Component("messageReceiver")
@Scope("prototype")
public class MessageReceiver implements MessageListener {

	@Resource
	private JmsTemplate jmsTemplate;

	public void receive() {
		MessageEntity messageEntity = (MessageEntity)jmsTemplate.receiveAndConvert();
		System.out.println(messageEntity);
		System.out.println(messageEntity.getContent());
	}
	
	@Override
	public void onMessage(Message message) {  
        if(message instanceof TextMessage){  
            TextMessage text=(TextMessage)message;  
            try {  
               System.out.println(text.getText());  
           } catch (Exception e) {  
           }  
        } else {
        	System.out.println("Thank you!!!");
        }
   } 
}
