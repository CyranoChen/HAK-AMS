package com.tsystems.api.dao;

import java.util.HashMap;

import com.tsystems.api.model.bo.McApiLog;
import com.tsystems.imfMsg.entity.vo.Page;


public interface McApiLogDao{
	public void save(McApiLog mcApiLog);
	public void delete(McApiLog mcApiLog);
	public void update(McApiLog mcApiLog);
	public McApiLog loadById(String id);
	public Page findListByPage(HashMap<String, String> filter,String sortWay);	
}