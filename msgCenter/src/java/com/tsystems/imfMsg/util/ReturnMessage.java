package com.tsystems.imfMsg.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ReturnMessage {
	private Document doc; 
	private Element result;
	private Element code;
	private Element description;
	
	public ReturnMessage() {
		doc=DocumentHelper.createDocument();
		result=doc.addElement("result");
		code=result.addElement("code");		
		description=result.addElement("description");
	}
			
	/**
	 * 根据接口调用结果返回相应信息
	 * @author sunjiawei
	 * @param result 调用结果
	 * @return 返回的信息，xml格式
	 */
	public String getReturnMessage(String value){
		code.addText(value);
		description.addText(getDescription(value));
		return doc.asXML();
	}
	
	public String getDescription(String value){
		String description = "";		
		for(Messages msg:Messages.values()){
			if(value.equals(msg.getValue())){
				description = msg.getInfo();
		        break;
			}
		}
		   
		return description;
	}	
	
	/**
	 * 枚举类错误信息
	 * @author lushuaifeng
	 */
	public enum Messages{		
		MESSAGE0("1000","操作成功"),
		MESSAGE1("1001","数据解析但未执行，对应数据记录不存在"),
		MESSAGE2("1002","数据解析但未执行，对应数据记录已存在"),
		MESSAGE3("1003","数据解析失败，接口返回值未定义"),
		MESSAGE4("1004","数据解析失败，接口返回值格式错误"),		
		MESSAGE5("1005","数据解析失败，接口异常"),
		MESSAGE6("1006","数据解析成功，告警机制异常");		

		
		
		
		private Messages(String value,String info){
			this.setValue(value);
			this.setInfo(info);			
		}
		
		private String value;
		private String info;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
		
	}
	
}
