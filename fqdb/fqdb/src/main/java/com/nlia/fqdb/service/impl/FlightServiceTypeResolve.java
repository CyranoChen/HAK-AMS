package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.FlightServiceTypeDao;
import com.nlia.fqdb.entity.base.FlightServiceType;
import com.nlia.fqdb.service.IFlightServiceTypeResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class FlightServiceTypeResolve extends AbstractCrudService<FlightServiceType, Long>
		implements IFlightServiceTypeResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private FlightServiceTypeDao flightServiceTypeDao;
	
	@Resource
	private FlightServiceTypeManager flightServiceTypeManager;
	
	@Override
	protected GenericDao<FlightServiceType, Long> getDao() {
		return flightServiceTypeDao;
	}

	public String parseFlightServiceTypeDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		FlightServiceType flightServiceType = new FlightServiceType();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(flightServiceType, doc, operationMode);
					flightServiceType.setCreateUser(ParseXmlUtil.createUser);
					flightServiceType.setCreateTime(parseXmlUtil.getSysDate());
					flightServiceType.setRemoveFlag("1");
					return "success:" + flightServiceTypeManager.save(flightServiceType).getId().toString() + ":new";
				} else {
					flightServiceType = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(flightServiceType, doc, operationMode);
					flightServiceType.setModifyUser(ParseXmlUtil.createUser);
					flightServiceType.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + flightServiceTypeManager.save(flightServiceType).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					flightServiceType = findByKey(doc);
					parseXmlString(flightServiceType, doc, operationMode);
					flightServiceType.setModifyUser(ParseXmlUtil.createUser);
					flightServiceType.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + flightServiceTypeManager.save(flightServiceType).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(flightServiceType, doc, operationMode);
					flightServiceType.setCreateUser(ParseXmlUtil.createUser);
					flightServiceType.setCreateTime(parseXmlUtil.getSysDate());
					flightServiceType.setRemoveFlag("1");
					return "success:" + flightServiceTypeManager.save(flightServiceType).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					flightServiceType = findByKey(doc);
					flightServiceType.setRemoveFlag("0");
					return "success:" + flightServiceTypeManager.save(flightServiceType).getId().toString() + ":del";
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
		
		if((flightServiceTypeManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public FlightServiceType findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return flightServiceTypeManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(FlightServiceType flightServiceType, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			flightServiceType.setBasicDataID(root.getText());
		}
		
		
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/FlightServiceType/ServiceTypeDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			flightServiceType.setServiceTypeDescription(root.getText());
		}
		
		/**
	     * add by march 20151124 因HAK而修改
	     * Rename IMFRoot/Data/BasicData/FlightServiceType/ServiceTypeCode to IMFRoot/Data/BasicData/FlightServiceType/FlightIATAServiceType
         * Add IMFRoot/Data/BasicData/FlightServiceType/FlightCAACServiceType
	     */
//		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/FlightServiceType/ServiceTypeCode");
//        if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
//            flightServiceType.setServiceTypeCode(root.getText());
//        }
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/FlightServiceType/FlightIATAServiceType");
        if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
            flightServiceType.setFlightIATAServiceType(root.getText());
            
        }
        root = doc.selectSingleNode("/IMFRoot/Data/BasicData/FlightServiceType/FlightCAACServiceType");
        if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
            flightServiceType.setFlightCAACServiceType(root.getText());
            flightServiceType.setServiceTypeCode(root.getText());
        }
		/*
	     * add by march 20151124 因HAK而修改
	     */
		
	}
	
}
