package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.AirportDao;
import com.nlia.fqdb.entity.base.Airport;
import com.nlia.fqdb.service.IAirportResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AirportResolve extends AbstractCrudService<Airport, Long>
		implements IAirportResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private AirportDao airportDao;
	
	@Resource
	private AirportManager airportManager;
	
	@Override
	protected GenericDao<Airport, Long> getDao() {
		return airportDao;
	}

	public String parseAirportDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Airport airport = new Airport();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(airport, doc, operationMode);
					airport.setCreateUser(ParseXmlUtil.createUser);
					airport.setCreateTime(parseXmlUtil.getSysDate());
					airport.setRemoveFlag("1");
					return "success:" + airportManager.save(airport).getId().toString() + ":new";
				} else {
					airport = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(airport, doc, operationMode);
					airport.setModifyUser(ParseXmlUtil.createUser);
					airport.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + airportManager.save(airport).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					airport = findByKey(doc);
					parseXmlString(airport, doc, operationMode);
					airport.setModifyUser(ParseXmlUtil.createUser);
					airport.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + airportManager.save(airport).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(airport, doc, operationMode);
					airport.setCreateUser(ParseXmlUtil.createUser);
					airport.setCreateTime(parseXmlUtil.getSysDate());
					airport.setRemoveFlag("1");
					return "success:" + airportManager.save(airport).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					airport = findByKey(doc);
					airport.setRemoveFlag("0");
					return "success:" + airportManager.save(airport).getId().toString() + ":del";
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
		
		if((airportManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Airport findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return airportManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Airport airport, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportIATACode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportICAOCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportICAOCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportCountry");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportCountry(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportCountryType");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportCountryType(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportCity");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportCity(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportRegion");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportRegion(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportTimezone");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportTimezone(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportDistance");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportDistance(!root.getText().equals("")?Long.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportDescription(root.getText());
		}
		
		//add by Ninja
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportRoutingName");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			airport.setAirportRoutingName(root.getText());
		}
		
		/*
	     * add by march 20151124 因HAK而修改
	     */
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportChineseShortName");
        if (parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
            airport.setAirportChineseShortName(root.getText());
        }
        root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportChineseFullName");
        if (parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
            airport.setAirportChineseFullName(root.getText());
        }
        root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportEnglishShortName");
        if (parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
            airport.setAirportEnglishShortName(root.getText());
        }
        root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Airport/AirportEnglishFullName");
        if (parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
            airport.setAirportEnglishFullName(root.getText());
        }
		/*
	     * add by march 20151124 因HAK而修改
	     */
	}
	
}
