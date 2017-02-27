/** 
 * Copyright (c) 1995-2011 Tsystems Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Tsystems.
 * You shall not disclose such Confidential Information and shall use it only in accordance 
 * with the terms of the license agreement you entered into with Tsystems. 
 *
 */

package com.tsystems.imfMsg.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tsystems.imfMsg.dao.MsgLogDao;
import com.tsystems.imfMsg.dao.MsgStoreDao;
import com.tsystems.imfMsg.dao.MsgStoreHistoryDao;
import com.tsystems.imfMsg.entity.bo.MsgLog;
import com.tsystems.imfMsg.entity.bo.MsgStore;
import com.tsystems.imfMsg.entity.bo.MsgStoreHistory;
import com.tsystems.imfMsg.entity.vo.Page;
import com.tsystems.imfMsg.service.MsgService;

/**
 * @author Administrator
 * @version $Revision$
 * @date 2013-5-14
 * @author modify by $Author$
 * @since 1.0
 */
@Service("msgService")
@Transactional(value="txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class MsgServiceImpl implements MsgService {
	@Autowired
	private MsgStoreDao msgStoreDao;
	
	@Autowired
	private MsgStoreHistoryDao msgStoreHistoryDao;
	
	@Autowired
	private MsgLogDao msgLogDao;	

	public void saveMsgStore(MsgStore msgStore){
		msgStoreDao.save(msgStore);
	}
	public void deleteMsgStore(MsgStore msgStore){
		msgStoreDao.delete(msgStore);
	}
	public void updateMsgStore(MsgStore msgStore){
		msgStoreDao.update(msgStore);
	}
	public void updateMsgStatusByHql(MsgStore msgStore){
		msgStoreDao.updateStatusByHql(msgStore);
	}
	public void updateMsgKeyValueByHql(MsgStore msgStore){
		msgStoreDao.updateKeyValueByHql(msgStore);
	}
	public MsgStore loadMsgStoreById(String id){
		MsgStore msg=msgStoreDao.loadById(id);
		return msg;
	}	
	
	public MsgStore findLatestMsgStoreWithSamePK(String receiver,String scheduledDate,String identity,String direction){
		MsgStore msg=msgStoreDao.findLatestMsgStoreWithSamePK(receiver,scheduledDate,identity,direction);
		return msg;
	}
	public List<MsgStore> findMsgStoreList(HashMap<String, String> filter,String sortWay){
		Page page=msgStoreDao.findListByPage(filter, sortWay);
		List<MsgStore> msgs=page.getResult();
		return msgs;
	}
	
	public Page findMsgStoreByPage(HashMap<String, String> filter,String sortWay){
		Page page=msgStoreDao.findListByPage(filter, sortWay);
		return page;
	}	
	
	
	public List<MsgStoreHistory> findMsgStoreHistoryList(HashMap<String, String> filter,String sortWay){
		Page page=msgStoreHistoryDao.findListByPage(filter, sortWay);
		List<MsgStoreHistory> msgs=page.getResult();
		return msgs;
	}
	
	
	public void saveMsgLog(MsgLog msgLog){
		msgLogDao.save(msgLog);
	}
	public void deleteMsgLog(MsgLog msgLog){
		msgLogDao.delete(msgLog);
	}	
	public void updateMsgLog(MsgLog msgLog){
		msgLogDao.update(msgLog);
	}
	public MsgLog loadMsgLogById(String id){
		MsgLog log= msgLogDao.loadById(id);
		return log;
	}
	
	public List<MsgLog> findMsgLogList(HashMap<String, String> filter,String sortWay){
		Page page=msgLogDao.findListByPage(filter, sortWay);
		List<MsgLog> logs=page.getResult();
		return logs;
	}
	
	public Page findMsgLogByPage(HashMap<String, String> filter,String sortWay){
		Page page=msgLogDao.findListByPage(filter, sortWay);
		return page;
	}	
			
	
}
