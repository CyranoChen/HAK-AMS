package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.FlightOperationCodeDao;
import com.nlia.fqdb.entity.base.FlightOperationCode;
import com.nlia.fqdb.service.IFlightOperationCodeResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class FlightOperationCodeResolve extends AbstractCrudService<FlightOperationCode, Long>
		implements IFlightOperationCodeResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private FlightOperationCodeDao flightOperationCodeDao;
	
	@Resource
	private FlightOperationCodeManager flightOperationCodeManager;
	
	@Override
	protected GenericDao<FlightOperationCode, Long> getDao() {
		return flightOperationCodeDao;
	}

	public String parseFlightOperationCodeDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		FlightOperationCode flightOperationCode = new FlightOperationCode();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(flightOperationCode, doc, operationMode);
					flightOperationCode.setCreateUser(ParseXmlUtil.createUser);
					flightOperationCode.setCreateTime(parseXmlUtil.getSysDate());
					flightOperationCode.setRemoveFlag("1");
					return "success:" + flightOperationCodeManager.save(flightOperationCode).getId().toString() + ":new";
				} else {
					flightOperationCode = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(flightOperationCode, doc, operationMode);
					flightOperationCode.setModifyUser(ParseXmlUtil.createUser);
					flightOperationCode.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + flightOperationCodeManager.save(flightOperationCode).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					flightOperationCode = findByKey(doc);
					parseXmlString(flightOperationCode, doc, operationMode);
					flightOperationCode.setModifyUser(ParseXmlUtil.createUser);
					flightOperationCode.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + flightOperationCodeManager.save(flightOperationCode).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(flightOperationCode, doc, operationMode);
					flightOperationCode.setCreateUser(ParseXmlUtil.createUser);
					flightOperationCode.setCreateTime(parseXmlUtil.getSysDate());
					flightOperationCode.setRemoveFlag("1");
					return "success:" + flightOperationCodeManager.save(flightOperationCode).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					flightOperationCode = findByKey(doc);
					flightOperationCode.setRemoveFlag("0");
					return "success:" + flightOperationCodeManager.save(flightOperationCode).getId().toString() + ":del";
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
		
		if((flightOperationCodeManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public FlightOperationCode findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return flightOperationCodeManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(FlightOperationCode flightOperationCode, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			flightOperationCode.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/FlightOperationCode/FlightOperationCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			flightOperationCode.setFlightOperationCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/FlightOperationCode/FlightOperationCodeDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			flightOperationCode.setFlightOperationCodeDescription(root.getText());
		}
		
	}
	
}
