package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.PassengerClassDao;
import com.nlia.fqdb.entity.base.PassengerClass;
import com.nlia.fqdb.service.IPassengerClassResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class PassengerClassResolve extends AbstractCrudService<PassengerClass, Long>
		implements IPassengerClassResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private PassengerClassDao passengerClassDao;
	
	@Resource
	private PassengerClassManager passengerClassManager;
	
	@Override
	protected GenericDao<PassengerClass, Long> getDao() {
		return passengerClassDao;
	}

	public String parsePassengerClassDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		PassengerClass passengerClass = new PassengerClass();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(passengerClass, doc, operationMode);
					passengerClass.setCreateUser(ParseXmlUtil.createUser);
					passengerClass.setCreateTime(parseXmlUtil.getSysDate());
					passengerClass.setRemoveFlag("1");
					return "success:" + passengerClassManager.save(passengerClass).getId().toString() + ":new";
				} else {
					passengerClass = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(passengerClass, doc, operationMode);
					passengerClass.setModifyUser(ParseXmlUtil.createUser);
					passengerClass.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + passengerClassManager.save(passengerClass).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					passengerClass = findByKey(doc);
					parseXmlString(passengerClass, doc, operationMode);
					passengerClass.setModifyUser(ParseXmlUtil.createUser);
					passengerClass.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + passengerClassManager.save(passengerClass).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(passengerClass, doc, operationMode);
					passengerClass.setCreateUser(ParseXmlUtil.createUser);
					passengerClass.setCreateTime(parseXmlUtil.getSysDate());
					passengerClass.setRemoveFlag("1");
					return "success:" + passengerClassManager.save(passengerClass).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					passengerClass = findByKey(doc);
					passengerClass.setRemoveFlag("0");
					return "success:" + passengerClassManager.save(passengerClass).getId().toString() + ":del";
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
		
		if((passengerClassManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public PassengerClass findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return passengerClassManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(PassengerClass passengerClass, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			passengerClass.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/PassengerClass/PassengerClassCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			passengerClass.setPassengerClassCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/PassengerClass/PassengerClassName");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			passengerClass.setPassengerClassName(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/PassengerClass/PassengerClassDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			passengerClass.setPassengerClassDescription(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/PassengerClass/PassengerClassAirline");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			passengerClass.setPassengerClassAirline(root.getText());
		}
	}	
}
