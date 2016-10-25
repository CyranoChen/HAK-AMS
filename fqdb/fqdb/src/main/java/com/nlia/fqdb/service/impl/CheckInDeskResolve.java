package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.CheckInDeskDao;
import com.nlia.fqdb.entity.base.CheckInDesk;
import com.nlia.fqdb.service.ICheckInDeskResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class CheckInDeskResolve extends AbstractCrudService<CheckInDesk, Long>
		implements ICheckInDeskResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private CheckInDeskDao checkInDeskDao;
	
	@Resource
	private CheckInDeskManager checkInDeskManager;
	
	@Override
	protected GenericDao<CheckInDesk, Long> getDao() {
		return checkInDeskDao;
	}

	public String parseCheckInDeskDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		CheckInDesk checkInDesk = new CheckInDesk();

		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(checkInDesk, doc, operationMode);
					checkInDesk.setCreateUser(ParseXmlUtil.createUser);
					checkInDesk.setCreateTime(parseXmlUtil.getSysDate());
					checkInDesk.setRemoveFlag("1");
					return "success:" + checkInDeskManager.save(checkInDesk).getId().toString() + ":new";
				} else {
					checkInDesk = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(checkInDesk, doc, operationMode);
					checkInDesk.setModifyUser(ParseXmlUtil.createUser);
					checkInDesk.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + checkInDeskManager.save(checkInDesk).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					checkInDesk = findByKey(doc);
					parseXmlString(checkInDesk, doc, operationMode);
					checkInDesk.setModifyUser(ParseXmlUtil.createUser);
					checkInDesk.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + checkInDeskManager.save(checkInDesk).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(checkInDesk, doc, operationMode);
					checkInDesk.setCreateUser(ParseXmlUtil.createUser);
					checkInDesk.setCreateTime(parseXmlUtil.getSysDate());
					checkInDesk.setRemoveFlag("1");
					return "success:" + checkInDeskManager.save(checkInDesk).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					checkInDesk = findByKey(doc);
					checkInDesk.setRemoveFlag("0");
					return "success:" + checkInDeskManager.save(checkInDesk).getId().toString() + ":del";
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
		
		if((checkInDeskManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public CheckInDesk findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return checkInDeskManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(CheckInDesk checkInDesk, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			checkInDesk.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/CheckInDesk/CheckInDeskCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			checkInDesk.setCheckInDeskCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/CheckInDesk/CheckInDeskTerminal");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			checkInDesk.setCheckInDeskTerminal(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/CheckInDesk/CheckInDeskType");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			checkInDesk.setCheckInDeskType(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/CheckInDesk/CheckInDeskDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			checkInDesk.setCheckInDeskDescription(root.getText());
		}
	}
	
}
