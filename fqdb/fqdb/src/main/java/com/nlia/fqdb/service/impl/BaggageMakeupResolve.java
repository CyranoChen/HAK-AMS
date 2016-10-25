package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.BaggageMakeupDao;
import com.nlia.fqdb.entity.base.BaggageMakeup;
import com.nlia.fqdb.service.IBaggageMakeupResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class BaggageMakeupResolve extends AbstractCrudService<BaggageMakeup, Long>
		implements IBaggageMakeupResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private BaggageMakeupDao baggageMakeupDao;
	
	@Resource
	private BaggageMakeupManager baggageMakeupManager;
	
	@Override
	protected GenericDao<BaggageMakeup, Long> getDao() {
		return baggageMakeupDao;
	}

	public String parseBaggageMakeupDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		BaggageMakeup baggageMakeup = new BaggageMakeup();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(baggageMakeup, doc, operationMode);
					baggageMakeup.setCreateUser(ParseXmlUtil.createUser);
					baggageMakeup.setCreateTime(parseXmlUtil.getSysDate());
					baggageMakeup.setRemoveFlag("1");
					return "success:" + baggageMakeupManager.save(baggageMakeup).getId().toString() + ":new";
				} else {
					baggageMakeup = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(baggageMakeup, doc, operationMode);
					baggageMakeup.setModifyUser(ParseXmlUtil.createUser);
					baggageMakeup.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + baggageMakeupManager.save(baggageMakeup).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					baggageMakeup = findByKey(doc);
					parseXmlString(baggageMakeup, doc, operationMode);
					baggageMakeup.setModifyUser(ParseXmlUtil.createUser);
					baggageMakeup.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + baggageMakeupManager.save(baggageMakeup).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(baggageMakeup, doc, operationMode);
					baggageMakeup.setCreateUser(ParseXmlUtil.createUser);
					baggageMakeup.setCreateTime(parseXmlUtil.getSysDate());
					baggageMakeup.setRemoveFlag("1");
					return "success:" + baggageMakeupManager.save(baggageMakeup).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					baggageMakeup = findByKey(doc);
					baggageMakeup.setRemoveFlag("0");
					return "success:" + baggageMakeupManager.save(baggageMakeup).getId().toString() + ":del";
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
		
		if((baggageMakeupManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public BaggageMakeup findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return baggageMakeupManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(BaggageMakeup baggageMakeup, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageMakeup.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageMakeup/BaggageMakeupCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageMakeup.setBaggageMakeupCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageMakeup/BaggageTerminal");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageMakeup.setBaggageTerminal(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageMakeup/BaggageDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageMakeup.setBaggageDescription(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageMakeup/BaggageMakeupCapacity");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageMakeup.setBaggageMakeupCapacity(root.getText());
		}
	}
	
}
