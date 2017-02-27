package com.tsystems.api.util;

import java.util.List;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tsystems.api.model.vo.XmlModel;


public class XmlParser 
{	
	
	/**
	 * @author lushuaifeng
	 * 解析XMl数据，转化成xmlModel对象，规范的xml格式为root/{record}/xxxx
	 * @param xml xml数据
	 * @return 返回用xml数据实例扁平化之后的对象(带JSONArray)
	 */
	public XmlModel parse(String xml){		
		try
		{
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();//root
			XmlModel xmlModel=new XmlModel(root.attributeValue("date"),root.attributeValue("type"));
			
			List<Element> childNodes =root.elements();
			if(childNodes.size()==0){
				return xmlModel;
			}
			
			JSONObject  recordObj = new JSONObject();	
			
			for(int i=0;i<childNodes.size();i++){
				setValueToMap(childNodes.get(i),recordObj);
			}
						
			xmlModel.setRecord(recordObj);
			
			
			return xmlModel;
			
			
		}
		catch(Exception e)
		{
			return null;
		}
	}
	private void setAttributeToMap(Element e,JSONObject obj){
		for(int j=0;j<e.attributeCount();j++){
			String attName=e.attribute(j).getName();
			String attValue=e.attribute(j).getValue();
			obj.put(e.getName()+"@"+attName,attValue);
		}
	}
	
	private void setValueToMap(Element e,JSONObject obj){		
		if(e.elements().size()>0){
			JSONObject childObj=new JSONObject();
			treeWalk((Element)e, childObj);
			obj.put(e.getName(), childObj);			
		}else{	
			obj.put(e.getName(), e.getText());
		}
	}	
	
	/**
	 * 递归法提取节点的值
	 * @param element 节点
	 * @param map 保存节点的值
	 */
	private void treeWalk(Element element, JSONObject obj)
	{		
		List<Element> childs= element.elements();
	    for (int i = 0;i < childs.size(); i++){
	    	Element node = childs.get(i);
	    	
	    	setAttributeToMap(node,obj);
	    	setValueToMap(node,obj);
	    }	
	}
	
}