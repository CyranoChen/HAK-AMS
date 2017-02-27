package com.tsystems.api.util;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WSResult {
	private static final Logger log = LoggerFactory.getLogger(WSResult.class);

	private Document doc; 
	private Element result;
	private Element code;
	private Element description;
	private Element params;		
	
	public WSResult() {
		this.doc=DocumentHelper.createDocument();
		this.result=doc.addElement("result");
		this.code=result.addElement("code");		
		this.description=result.addElement("description");
		this.params=result.addElement("params");		
	}
	
	public WSResult(String code) {
		this.doc=DocumentHelper.createDocument();
		this.result=doc.addElement("result");
		this.code=result.addElement("code");		
		this.description=result.addElement("description");
		this.params=result.addElement("params");	
		setCode(code);		
	}	
		
	
	public void setCode(String code){
		this.code.addText(code);
		this.description.addText(getDescriptionByCode(code));
	}
	
	public void setDescription(String description){
		this.description.addText(description);
	}		

	public String getDescriptionByCode(String value){
		String description = "";		
		for(Messages msg:Messages.values()){
			if(value.equals(msg.getValue())){
				description = msg.getInfo();
		        break;
			}
		}
		   
		return description;
	}
	
	public void setParamWithFieldList(List<String[]> pars){
		Element eParam=this.params.addElement("param");
		for(int i=0;i<pars.size();i++){
			String[] par=pars.get(i);
			Element ePar=eParam.addElement(par[0]);
			if(par[0].equalsIgnoreCase("content")){
				ePar.addCDATA(par[1]);				
			}else{
				ePar.addText(par[1]);
			}
			
		}
	}

	public void setParam(String sParam){
		try {
			Document docCon = DocumentHelper.parseText(sParam);
			this.params.add(docCon.getRootElement());
		} catch (DocumentException e) {
			log.error("unexcepted Exception when {}","setParam",e);
			//e.printStackTrace();
			this.params.addText(sParam);
		}
								
	}
	


	public String getResultJson(){
		return Tools.xml2Json(this.doc.asXML());	
	}
	
	public String getResultXML(){
		return this.doc.asXML();	
	}	
	
	public enum Messages{		
		MESSAGE0("1000","查询成功"),	
		MESSAGE2("1001","数据量过大，请分批查询"),		
		MESSAGE3("1002","查无相关数据"),		
		MESSAGE4("1003","操作成功"),			
		MESSAGE5("0001","用户信息效验失败"),
		MESSAGE6("0002","数据类型效验失败"),		
		MESSAGE7("0003","操作失败");	
		
		
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
