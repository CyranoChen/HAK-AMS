package com.tsystems.jms.action;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import com.tsystems.jms.entity.MessageEntity;

//@Component("messageSender")
@Service(("messageSender"))
@Scope("prototype")
public class MessageSender {

	@Resource
	private JmsTemplate jmsTemplate; 
	       
    public void send(MessageEntity message) { 
          
        jmsTemplate.convertAndSend(message, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message msg) throws JMSException {
				msg.setStringProperty("messagetype", "test");
				return msg;
			}
		});
    }
    
    public void send() { 
        
		jmsTemplate.send(new MessageCreator() {			
			@Override
			public Message createMessage(Session arg0) throws JMSException {
				// TODO Auto-generated method stub
				return arg0.createTextMessage("This is test");
			}
		}); 
    }
}
