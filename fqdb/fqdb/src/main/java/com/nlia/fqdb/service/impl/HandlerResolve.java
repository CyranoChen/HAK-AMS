package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.HandlerDao;
import com.nlia.fqdb.entity.base.Handler;
import com.nlia.fqdb.service.IHandlerResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class HandlerResolve extends AbstractCrudService<Handler, Long>
		implements IHandlerResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private HandlerDao handlerDao;
	
	@Resource
	private HandlerManager handlerManager;
	
	@Override
	protected GenericDao<Handler, Long> getDao() {
		return handlerDao;
	}

	public String parseHandlerDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Handler handler = new Handler();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(handler, doc, operationMode);
					handler.setCreateUser(ParseXmlUtil.createUser);
					handler.setCreateTime(parseXmlUtil.getSysDate());
					handler.setRemoveFlag("1");
					return "success:" + handlerManager.save(handler).getId().toString() + ":new";
				} else {
					handler = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(handler, doc, operationMode);
					handler.setModifyUser(ParseXmlUtil.createUser);
					handler.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + handlerManager.save(handler).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					handler = findByKey(doc);
					parseXmlString(handler, doc, operationMode);
					handler.setModifyUser(ParseXmlUtil.createUser);
					handler.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + handlerManager.save(handler).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(handler, doc, operationMode);
					handler.setCreateUser(ParseXmlUtil.createUser);
					handler.setCreateTime(parseXmlUtil.getSysDate());
					handler.setRemoveFlag("1");
					return "success:" + handlerManager.save(handler).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					handler = findByKey(doc);
					handler.setRemoveFlag("0");
					return "success:" + handlerManager.save(handler).getId().toString() + ":del";
				} else {
					return "failure:0:no";
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return "failure:2:no";
	}
	
	public boolean isExist(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();

		Node root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		
		if(parseXmlUtil.isExistNode(root))
			filters.put("basicDataID_equal", root.getText());
		
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		if((handlerManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Handler findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return handlerManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Handler handler, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			handler.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Handler/HandlerCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			handler.setHandlerCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Handler/HandlerName");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			handler.setHandlerName(root.getText());
		}
				
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Handler/HandlerDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			handler.setHandlerDescription(root.getText());
		}		
	}	
}
