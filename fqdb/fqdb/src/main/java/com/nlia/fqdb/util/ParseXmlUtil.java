package com.nlia.fqdb.util;

import java.util.Date;

import org.dom4j.Element;
import org.dom4j.Node;

public class ParseXmlUtil {

	public static final String createUser = "IMF";
	
	public Date getSysDate() {
		return DateUtils.Date2Date(new Date());
	}
	
	public boolean isExistNode(Node root) {
		return root != null && !"".equals(root.getText());
	}
	
	public boolean isInsertOrUpdateNode(Node root, String operationMode) {
		
		Element element = (Element)root;
		if(root != null) {
			if("MOD".equals(operationMode)) {
				//System.out.println(element.attribute("nil") == null && element.attribute("OldValue") == null ? false : true);
				//lushuaifeng 20160912
				//return (element.attribute("nil") != null) || (element.attribute("OldValue") != null) ? true : false;
				return true;
			}
			return true;
		}
		return false;
	}
}
