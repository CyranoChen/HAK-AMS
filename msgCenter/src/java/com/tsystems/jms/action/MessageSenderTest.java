package com.tsystems.jms.action;

import javax.jms.JMSException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tsystems.jms.entity.MessageEntity;

public class MessageSenderTest {

	public void test() throws JMSException {
		
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setId(1L);
		messageEntity.setType("test");
		messageEntity.setName("activeMq");
		messageEntity.setContent("This is activeMq's test");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");  
        MessageSender sender=(MessageSender)context.getBean("messageSender");  
        MessageReceiver receive=(MessageReceiver)context.getBean("messageReceiver");
        
        //sender.send();
        sender.send(messageEntity);
        receive.receive();  
    }
	
	public static void main(String[] args) {
		MessageSenderTest messageTest = new MessageSenderTest();
		try {
			messageTest.test();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
