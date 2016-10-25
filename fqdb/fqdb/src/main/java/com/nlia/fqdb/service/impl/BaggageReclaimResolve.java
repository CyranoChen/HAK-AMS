package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.BaggageReclaimDao;
import com.nlia.fqdb.entity.base.BaggageReclaim;
import com.nlia.fqdb.service.IBaggageReclaimResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class BaggageReclaimResolve extends AbstractCrudService<BaggageReclaim, Long>
		implements IBaggageReclaimResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private BaggageReclaimDao baggageReclaimDao;
	
	@Resource
	private BaggageReclaimManager baggageReclaimManager;
	
	@Override
	protected GenericDao<BaggageReclaim, Long> getDao() {
		return baggageReclaimDao;
	}

	public String parseBaggageReclaimDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		BaggageReclaim baggageReclaim = new BaggageReclaim();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(baggageReclaim, doc, operationMode);
					baggageReclaim.setCreateUser(ParseXmlUtil.createUser);
					baggageReclaim.setCreateTime(parseXmlUtil.getSysDate());
					baggageReclaim.setRemoveFlag("1");
					return "success:" + baggageReclaimManager.save(baggageReclaim).getId().toString() + ":new";
				} else {
					baggageReclaim = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(baggageReclaim, doc, operationMode);
					baggageReclaim.setModifyUser(ParseXmlUtil.createUser);
					baggageReclaim.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + baggageReclaimManager.save(baggageReclaim).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					baggageReclaim = findByKey(doc);
					parseXmlString(baggageReclaim, doc, operationMode);
					baggageReclaim.setModifyUser(ParseXmlUtil.createUser);
					baggageReclaim.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + baggageReclaimManager.save(baggageReclaim).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(baggageReclaim, doc, operationMode);
					baggageReclaim.setCreateUser(ParseXmlUtil.createUser);
					baggageReclaim.setCreateTime(parseXmlUtil.getSysDate());
					baggageReclaim.setRemoveFlag("1");
					return "success:" + baggageReclaimManager.save(baggageReclaim).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					baggageReclaim = findByKey(doc);
					baggageReclaim.setRemoveFlag("0");
					return "success:" + baggageReclaimManager.save(baggageReclaim).getId().toString() + ":del";
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
		
		if((baggageReclaimManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public BaggageReclaim findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return baggageReclaimManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(BaggageReclaim baggageReclaim, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageReclaim.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageReclaim/BaggageReclaimCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageReclaim.setBaggageReclaimCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageReclaim/BaggageReclaimTerminal");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageReclaim.setBaggageReclaimTerminal(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageReclaim/BaggageReclaimCapacity");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageReclaim.setBaggageReclaimCapacity(Long.valueOf(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageReclaim/BaggageReclaimDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageReclaim.setBaggageReclaimDescription(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/BaggageReclaim/BaggageRecliamArea");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			baggageReclaim.setBaggageMakeupArea(root.getText());
		}
	}
	
}
