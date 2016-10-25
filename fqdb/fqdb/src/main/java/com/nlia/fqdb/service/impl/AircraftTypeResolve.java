package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.AircraftTypeDao;
import com.nlia.fqdb.entity.base.AircraftType;
import com.nlia.fqdb.service.IAircraftTypeResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AircraftTypeResolve extends AbstractCrudService<AircraftType, Long>
		implements IAircraftTypeResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private AircraftTypeDao aircraftTypeDao;
	
	@Resource
	private AircraftTypeManager aircraftTypeManager;
	
	@Override
	protected GenericDao<AircraftType, Long> getDao() {
		return aircraftTypeDao;
	}

	public String parseAircraftTypeDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		AircraftType aircraftType = new AircraftType();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(aircraftType, doc, operationMode);
					aircraftType.setCreateUser(ParseXmlUtil.createUser);
					aircraftType.setCreateTime(parseXmlUtil.getSysDate());
					aircraftType.setRemoveFlag("1");
					return "success:" + aircraftTypeManager.save(aircraftType).getId().toString() + ":new";
				} else {
					aircraftType = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(aircraftType, doc, operationMode);
					aircraftType.setModifyUser(ParseXmlUtil.createUser);
					aircraftType.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + aircraftTypeManager.save(aircraftType).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					aircraftType = findByKey(doc);
					parseXmlString(aircraftType, doc, operationMode);
					aircraftType.setModifyUser(ParseXmlUtil.createUser);
					aircraftType.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + aircraftTypeManager.save(aircraftType).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(aircraftType, doc, operationMode);
					aircraftType.setCreateUser(ParseXmlUtil.createUser);
					aircraftType.setCreateTime(parseXmlUtil.getSysDate());
					aircraftType.setRemoveFlag("1");
					return "success:" + aircraftTypeManager.save(aircraftType).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					aircraftType = findByKey(doc);
					aircraftType.setRemoveFlag("0");
					return "success:" + aircraftTypeManager.save(aircraftType).getId().toString() + ":del";
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
		
		if((aircraftTypeManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public AircraftType findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return aircraftTypeManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(AircraftType aircraftType, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftTypeIATACode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftTypeIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftTypeICAOCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftTypeICAOCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftSubTypeIATACode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftSubTypeIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftManufactory");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftManufactory(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftEngineNumber");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftEngineNumber(!root.getText().equals("")?Integer.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftHeight");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftHeight(!root.getText().equals("")?Integer.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftLength");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftLength(!root.getText().equals("")?Integer.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftWidth");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftWidth(!root.getText().equals("")?Integer.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircarftTakeoffWeight");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftTakeoffWeight(!root.getText().equals("")?Integer.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftSeatCapacity");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftSeatCapacity(!root.getText().equals("")?Integer.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircarftPayload");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftPayload(!root.getText().equals("")?Integer.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircarftLandingWeight");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftLandingWeight(!root.getText().equals("")?Integer.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftTypeDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftTypeDescription(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftNoiseCategory");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftNoiseCategory(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/BoardingBridgeRequired");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setBoardingBridgeRequired(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftMode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftMode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/AircraftType/AircraftSizaCategory");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			aircraftType.setAircraftSizaCategory(root.getText());
		}

	}
	
}
