package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.DelayCodeDao;
import com.nlia.fqdb.entity.base.DelayCode;
import com.nlia.fqdb.service.IDelayCodeResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class DelayCodeResolve extends AbstractCrudService<DelayCode, Long>
		implements IDelayCodeResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private DelayCodeDao delayCodeDao;
	
	@Resource
	private DelayCodeManager delayCodeManager;
	
	@Override
	protected GenericDao<DelayCode, Long> getDao() {
		return delayCodeDao;
	}

	public String parseDelayCodeDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		DelayCode delayCode = new DelayCode();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(delayCode, doc, operationMode);
					delayCode.setCreateUser(ParseXmlUtil.createUser);
					delayCode.setCreateTime(parseXmlUtil.getSysDate());
					delayCode.setRemoveFlag("1");
					return "success:" + delayCodeManager.save(delayCode).getId().toString() + ":new";
				} else {
					delayCode = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(delayCode, doc, operationMode);
					delayCode.setModifyUser(ParseXmlUtil.createUser);
					delayCode.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + delayCodeManager.save(delayCode).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					delayCode = findByKey(doc);
					parseXmlString(delayCode, doc, operationMode);
					delayCode.setModifyUser(ParseXmlUtil.createUser);
					delayCode.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + delayCodeManager.save(delayCode).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(delayCode, doc, operationMode);
					delayCode.setCreateUser(ParseXmlUtil.createUser);
					delayCode.setCreateTime(parseXmlUtil.getSysDate());
					delayCode.setRemoveFlag("1");
					return "success:" + delayCodeManager.save(delayCode).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					delayCode = findByKey(doc);
					delayCode.setRemoveFlag("0");
					return "success:" + delayCodeManager.save(delayCode).getId().toString() + ":del";
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
		
		if((delayCodeManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public DelayCode findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return delayCodeManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(DelayCode delayCode, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			delayCode.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/DelayCode/DelayCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			delayCode.setDelayCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/DelayCode/DelayReason");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			delayCode.setDelayReason(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/DelayCode/DelaySubCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			delayCode.setDelaySubCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/DelayCode/DelaySubReason");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			delayCode.setDelaySubReason(root.getText());
		}
	}
	
}
