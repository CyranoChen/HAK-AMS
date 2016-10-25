package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.AirlineDao;
import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.service.IAirlineResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AirlineResolve extends AbstractCrudService<Airline, Long>
		implements IAirlineResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private AirlineDao airlineDao;
	
	@Resource
	private AirlineManager airlineManager;
	
	@Override
	protected GenericDao<Airline, Long> getDao() {
		return airlineDao;
	}

	public String parseAirlineDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Airline airline = new Airline();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(airline, doc, operationMode);
					airline.setCreateUser(ParseXmlUtil.createUser);
					airline.setCreateTime(parseXmlUtil.getSysDate());
					airline.setRemoveFlag("1");
					return "success:" + airlineManager.save(airline).getId().toString() + ":new";
				} else {
					airline = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(airline, doc, operationMode);
					airline.setModifyUser(ParseXmlUtil.createUser);
					airline.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + airlineManager.save(airline).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					airline = findByKey(doc);
					parseXmlString(airline, doc, operationMode);
					airline.setModifyUser(ParseXmlUtil.createUser);
					airline.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + airlineManager.save(airline).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(airline, doc, operationMode);
					airline.setCreateUser(ParseXmlUtil.createUser);
					airline.setCreateTime(parseXmlUtil.getSysDate());
					airline.setRemoveFlag("1");
					return "success:" + airlineManager.save(airline).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					airline = findByKey(doc);
					airline.setRemoveFlag("0");
					return "success:" + airlineManager.save(airline).getId().toString() + ":del";
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
		
		if((airlineManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Airline findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return airlineManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Airline airline, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airline.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airline/AirlineIATACode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airline.setAirlineIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airline/AirlineICAOCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airline.setAirlineICAOCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airline/AirlineName");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airline.setAirlineName(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airline/AirlineHomeCountry");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airline.setAirlineHomeCountry(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airline/AirlineAllianceGroup");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airline.setAirlineAllianceGroup(root.getText());
		}
		
//		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airline/AirlineDescription");
//		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
//			airline.setAirlineDescription(root.getText());
//		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airline/AirlineHandler");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airline.setAirlineHandler(root.getText());
		}
	}
}
