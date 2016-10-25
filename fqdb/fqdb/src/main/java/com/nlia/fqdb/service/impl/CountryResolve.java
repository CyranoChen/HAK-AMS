package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.CountryDao;
import com.nlia.fqdb.entity.base.Country;
import com.nlia.fqdb.service.ICountryResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class CountryResolve extends AbstractCrudService<Country, Long>
		implements ICountryResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private CountryDao countryDao;
	
	@Resource
	private CountryManager countryManager;
	
	@Override
	protected GenericDao<Country, Long> getDao() {
		return countryDao;
	}

	public String parseCountryDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Country country = new Country();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(country, doc, operationMode);
					country.setCreateUser(ParseXmlUtil.createUser);
					country.setCreateTime(parseXmlUtil.getSysDate());
					country.setRemoveFlag("1");
					return "success:" + countryManager.save(country).getId().toString() + ":new";
				} else {
					country = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(country, doc, operationMode);
					country.setModifyUser(ParseXmlUtil.createUser);
					country.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + countryManager.save(country).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					country = findByKey(doc);
					parseXmlString(country, doc, operationMode);
					country.setModifyUser(ParseXmlUtil.createUser);
					country.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + countryManager.save(country).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(country, doc, operationMode);
					country.setCreateUser(ParseXmlUtil.createUser);
					country.setCreateTime(parseXmlUtil.getSysDate());
					country.setRemoveFlag("1");
					return "success:" + countryManager.save(country).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					country = findByKey(doc);
					country.setRemoveFlag("0");
					return "success:" + countryManager.save(country).getId().toString() + ":del";
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
		
		if((countryManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Country findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return countryManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Country country, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			country.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Country/CountryIATACode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			country.setCountryIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Country/CountryICAOCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			country.setCountryICAOCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Country/CountryName");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			country.setCountryName(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Country/CountryType");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			country.setCountryType(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Country/CountryArea");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			country.setCountryArea(root.getText());
		}
		
	}
	
}
