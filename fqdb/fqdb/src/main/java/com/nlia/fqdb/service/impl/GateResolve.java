package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.GateDao;
import com.nlia.fqdb.entity.base.Gate;
import com.nlia.fqdb.service.IGateResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class GateResolve extends AbstractCrudService<Gate, Long>
		implements IGateResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private GateDao gateDao;
	
	@Resource
	private GateManager gateManager;
	
	@Override
	protected GenericDao<Gate, Long> getDao() {
		return gateDao;
	}

	public String parseGateDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Gate gate = new Gate();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(gate, doc, operationMode);
					gate.setCreateUser(ParseXmlUtil.createUser);
					gate.setCreateTime(parseXmlUtil.getSysDate());
					gate.setRemoveFlag("1");
					return "success:" + gateManager.save(gate).getId().toString() + ":new";
				} else {
					gate = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(gate, doc, operationMode);
					gate.setModifyUser(ParseXmlUtil.createUser);
					gate.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + gateManager.save(gate).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					gate = findByKey(doc);
					parseXmlString(gate, doc, operationMode);
					gate.setModifyUser(ParseXmlUtil.createUser);
					gate.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + gateManager.save(gate).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(gate, doc, operationMode);
					gate.setCreateUser(ParseXmlUtil.createUser);
					gate.setCreateTime(parseXmlUtil.getSysDate());
					gate.setRemoveFlag("1");
					return "success:" + gateManager.save(gate).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					gate = findByKey(doc);
					gate.setRemoveFlag("0");
					return "success:" + gateManager.save(gate).getId().toString() + ":del";
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
		
		if((gateManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Gate findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return gateManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Gate gate, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			gate.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Gate/GateCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			gate.setGateCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Gate/GateTerminal");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			gate.setGateTerminal(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Gate/GateDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			gate.setGateDescription(root.getText());
		}
	}
	
}
