package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.RunwayDao;
import com.nlia.fqdb.entity.base.Runway;
import com.nlia.fqdb.service.IRunwayResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class RunwayResolve extends AbstractCrudService<Runway, Long>
		implements IRunwayResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private RunwayDao runwayDao;
	
	@Resource
	private RunwayManager runwayManager;
	
	@Override
	protected GenericDao<Runway, Long> getDao() {
		return runwayDao;
	}

	public String parseRunwayDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Runway runway = new Runway();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(runway, doc, operationMode);
					runway.setCreateUser(ParseXmlUtil.createUser);
					runway.setCreateTime(parseXmlUtil.getSysDate());
					runway.setRemoveFlag("1");
					return "success:" + runwayManager.save(runway).getId().toString() + ":new";
				} else {
					runway = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(runway, doc, operationMode);
					runway.setModifyUser(ParseXmlUtil.createUser);
					runway.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + runwayManager.save(runway).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					runway = findByKey(doc);
					parseXmlString(runway, doc, operationMode);
					runway.setModifyUser(ParseXmlUtil.createUser);
					runway.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + runwayManager.save(runway).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(runway, doc, operationMode);
					runway.setCreateUser(ParseXmlUtil.createUser);
					runway.setCreateTime(parseXmlUtil.getSysDate());
					runway.setRemoveFlag("1");
					return "success:" + runwayManager.save(runway).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					runway = findByKey(doc);
					runway.setRemoveFlag("0");
					return "success:" + runwayManager.save(runway).getId().toString() + ":del";
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
		
		if((runwayManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Runway findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return runwayManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Runway runway, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			runway.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Runway/RunwayCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			runway.setRunwayCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Runway/RunwayDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			runway.setRunwayDescription(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Runway/RunwayLength");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			runway.setRunwayLength(!root.getText().equals("")?Long.valueOf(root.getText()):null);
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Runway/RunwayWidth");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			runway.setRunwayWidth(!root.getText().equals("")?Long.valueOf(root.getText()):null);
		}
	}
	
}
