package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.StandDao;
import com.nlia.fqdb.entity.base.Stand;
import com.nlia.fqdb.service.IStandResolve;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ParseXmlUtil;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class StandResolve extends AbstractCrudService<Stand, Long>
		implements IStandResolve {

	ParseXmlUtil parseXmlUtil = new ParseXmlUtil();
	
	@Resource
	private StandDao standDao;
	
	@Resource
	private StandManager standManager;
	
	@Override
	protected GenericDao<Stand, Long> getDao() {
		return standDao;
	}

	public String parseStandDataOfXmlString(String xmlString) {
		
		Document doc;
		boolean isExist = false;
		String operationMode = "";
		
		Stand stand = new Stand();
		
		try {
			
			doc = DocumentHelper.parseText(xmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(parseXmlUtil.isExistNode(root)) operationMode = root.getText();
			
			isExist = isExist(doc);
			
			if("NEW".equals(operationMode)) {				
				if(!isExist) {
					parseXmlString(stand, doc, operationMode);
					stand.setCreateUser(ParseXmlUtil.createUser);
					stand.setCreateTime(parseXmlUtil.getSysDate());
					stand.setRemoveFlag("1");
					return "success:" + standManager.save(stand).getId().toString() + ":new";
				} else {
					stand = findByKey(doc);
					operationMode = "MOD";
					parseXmlString(stand, doc, operationMode);
					stand.setModifyUser(ParseXmlUtil.createUser);
					stand.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + standManager.save(stand).getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				
				if(isExist) {					
					stand = findByKey(doc);
					parseXmlString(stand, doc, operationMode);
					stand.setModifyUser(ParseXmlUtil.createUser);
					stand.setModifyTime(parseXmlUtil.getSysDate());
					return "success:" + standManager.save(stand).getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlString(stand, doc, operationMode);
					stand.setCreateUser(ParseXmlUtil.createUser);
					stand.setCreateTime(parseXmlUtil.getSysDate());
					stand.setRemoveFlag("1");
					return "success:" + standManager.save(stand).getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if (isExist) {
					stand = findByKey(doc);
					stand.setRemoveFlag("0");
					return "success:" + standManager.save(stand).getId().toString() + ":del";
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
		
		if((standManager.findBy(filters, null, -1, -1)).size() > 0)
			return true;
		else 
			return false;
	}
	
	public Stand findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isExistNode(root)) {
			filters.put("basicDataID_equal", root.getText());
		}
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		return standManager.findBy(filters, null, -1, -1).get(0);
	}
	
	public void parseXmlString(Stand stand, Document doc, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataID");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			stand.setBasicDataID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Stand/StandCode");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			stand.setStandCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Stand/StandTerminal");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			stand.setStandTerminal(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Stand/StandCapacity");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			if(root.getText().trim().length()>0){
			        stand.setStandCapacity(Long.valueOf(root.getText()));
			}else{
			    stand.setStandCapacity(null);
			}
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Stand/StandDescription");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			stand.setStandDescription(root.getText());
		}
		
		// 缺
		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Stand/StandRefGate");
		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
			stand.setStandRefGate(root.getText());
		}
//		
//		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Stand/StandLength");
//		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
//			stand.setStandLength(Long.valueOf(root.getText()));
//		}
//		
//		root = doc.selectSingleNode("/IMFRoot/Data/BasicData/Stand/StandWidth");
//		if(parseXmlUtil.isInsertOrUpdateNode(root, operationMode)) {
//			stand.setStandWidth(Long.valueOf(root.getText()));
//		}
	}
	
}
