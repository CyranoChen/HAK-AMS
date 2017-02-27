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

package com.tsystems.imfMsg.dao;

import java.util.HashMap;
import java.util.List;

import com.tsystems.imfMsg.entity.bo.MsgStore;
import com.tsystems.imfMsg.entity.vo.Page;

/**
 * 实体名称
 * 
 * @author Administrator
 * @version $Revision$
 * @date 2013-4-8
 * @author modify by $Author$
 * @since 1.0
 */

public interface MsgStoreDao {
	public void save(MsgStore msgStore);
	public void delete(MsgStore msgStore);
	public void update(MsgStore msgStore);
	public void updateKeyValueByHql(MsgStore msgStore);	
	public void updateStatusByHql(MsgStore msgStore);		
	public MsgStore loadById(String id);
	public MsgStore findLatestMsgStoreWithSamePK(String receiver,String scheduledDate,String identity,String direction);
	public Page findListByPage(HashMap<String, String> filter,String sortWay);		
}
