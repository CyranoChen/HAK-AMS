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

import com.tsystems.imfMsg.entity.bo.MsgStoreHistory;
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

public interface MsgStoreHistoryDao {
	public void save(MsgStoreHistory msgStore);
	public void delete(MsgStoreHistory msgStore);
	public void update(MsgStoreHistory msgStore);
	public MsgStoreHistory loadById(String id);
	public Page findListByPage(HashMap<String, String> filter,String sortWay);		
}
