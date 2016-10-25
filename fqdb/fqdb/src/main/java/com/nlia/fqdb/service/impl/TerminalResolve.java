package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.TerminalDao;
import com.nlia.fqdb.entity.base.Terminal;
import com.nlia.fqdb.service.ITerminalResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class TerminalResolve extends AbstractCrudService<Terminal, Long>
		implements ITerminalResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private TerminalDao terminalDao;
	
	@Resource
	private TerminalManager terminalManager;
	
	@Override
	protected GenericDao<Terminal, Long> getDao() {
		return terminalDao;
	}

	public String parseTerminalDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Terminal terminal = new Terminal();
	
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(terminal, doc, operationMode);
					terminal.setCreateUser(ParseXmlUtil.createUser);
					terminal.setCreateTime(parseXmlUtil.getSysDate());
					terminal.setRemoveFlag("1");
					return "success:" + terminalManager.save(terminal).getId().toString() + ":new";
				} else {
					terminal = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(terminal, doc, operationMode);
					terminal.setModifyUser(ParseXmlUtil.createUser);
					terminal.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + terminalManager.save(terminal).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					terminal = findByKey(doc);
					parseXmlString(terminal, doc, operationMode);
					terminal.setModifyUser(ParseXmlUtil.createUser);
					terminal.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + terminalManager.save(terminal).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(terminal, doc, operationMode);
					terminal.setCreateUser(ParseXmlUtil.createUser);
					terminal.setCreateTime(parseXmlUtil.getSysDate());
					terminal.setRemoveFlag("1");
					return "success:" + terminalManager.save(terminal).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					terminal = findByKey(doc);
					terminal.setRemoveFlag("0");
					return "success:" + terminalManager.save(terminal).getId().toString() + ":del";
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
		
		if((terminalManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Terminal findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return terminalManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Terminal terminal, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			terminal.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Terminal/TerminalCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			terminal.setTerminalCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Terminal/TermialDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			terminal.setTermialDescription(root.getText());
		}
	}
	
}
