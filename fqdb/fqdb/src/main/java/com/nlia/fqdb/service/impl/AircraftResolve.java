package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.AircraftDao;
import com.nlia.fqdb.entity.base.Aircraft;
import com.nlia.fqdb.service.IAircraftResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AircraftResolve extends AbstractCrudService<Aircraft, Long>
		implements IAircraftResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private AircraftDao aircraftDao;
	
	@Resource
	private AircraftManager aircraftManager;
	
	@Override
	protected GenericDao<Aircraft, Long> getDao() {
		return aircraftDao;
	}

	public String parseAircraftDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Aircraft aircraft = new Aircraft();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(aircraft, doc, operationMode);
					aircraft.setCreateUser(ParseXmlUtil.createUser);
					aircraft.setCreateTime(parseXmlUtil.getSysDate());
					aircraft.setRemoveFlag("1");
					return "success:" + aircraftManager.save(aircraft).getId().toString() + ":new";
				} else {
					aircraft = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(aircraft, doc, operationMode);
					aircraft.setModifyUser(ParseXmlUtil.createUser);
					aircraft.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + aircraftManager.save(aircraft).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					aircraft = findByKey(doc);
					parseXmlString(aircraft, doc, operationMode);
					aircraft.setModifyUser(ParseXmlUtil.createUser);
					aircraft.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + aircraftManager.save(aircraft).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(aircraft, doc, operationMode);
					aircraft.setCreateUser(ParseXmlUtil.createUser);
					aircraft.setCreateTime(parseXmlUtil.getSysDate());
					aircraft.setRemoveFlag("1");
					return "success:" + aircraftManager.save(aircraft).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					aircraft = findByKey(doc);
					aircraft.setRemoveFlag("0");
					return "success:" + aircraftManager.save(aircraft).getId().toString() + ":del";
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
		
		if((aircraftManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Aircraft findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return aircraftManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Aircraft aircraft, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraft.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Aircraft/AircraftRegistration");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraft.setAircraftRegistration(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Aircraft/AircraftTypeIATACode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraft.setAircraftTypeIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Aircraft/AircraftTypeICAOCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraft.setAircraftTypeICAOCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Aircraft/AircraftOwnerAirline");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraft.setAircraftAirline(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Aircraft/AircraftLeasingAirline");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraft.setAircraftLeasingAirline(root.getText());
		}

	}
	
}
