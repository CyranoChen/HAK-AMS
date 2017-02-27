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

package com.tsystems.imfMsg.service;

import java.util.HashMap;
import java.util.List;

import com.tsystems.imfMsg.entity.bo.MsgLog;
import com.tsystems.imfMsg.entity.bo.MsgStore;
import com.tsystems.imfMsg.entity.bo.MsgStoreHistory;
import com.tsystems.imfMsg.entity.vo.Page;

/**
 * 业务服务
 * 
 * @author Administrator
 * @version $Revision$
 * @date 2013-4-8
 * @author modify by $Author$
 * @since 1.0
 */

public interface MsgService {
	public void saveMsgStore(MsgStore msgStore);
	public void deleteMsgStore(MsgStore msgStore);
	public void updateMsgKeyValueByHql(MsgStore msgStore);
	public void updateMsgStatusByHql(MsgStore msgStore);
	public void updateMsgStore(MsgStore msgStore);
	public MsgStore loadMsgStoreById(String id);
	public MsgStore findLatestMsgStoreWithSamePK(String receiver,String scheduledDate,String identity,String direction);
	public List<MsgStore> findMsgStoreList(HashMap<String, String> filter,String sortWay);	
	public Page findMsgStoreByPage(HashMap<String, String> filter,String sortWay);
	
	public List<MsgStoreHistory> findMsgStoreHistoryList(HashMap<String, String> filter,String sortWay);	
	
	public void saveMsgLog(MsgLog msgLog);
	public void deleteMsgLog(MsgLog msgLog);	
	public void updateMsgLog(MsgLog msgLog);
	public MsgLog loadMsgLogById(String id);
	public List<MsgLog> findMsgLogList(HashMap<String, String> filter,String sortWay);
	public Page findMsgLogByPage(HashMap<String, String> filter,String sortWay);
}
