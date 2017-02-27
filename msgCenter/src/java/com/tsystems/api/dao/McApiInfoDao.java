package com.tsystems.api.dao;

import java.util.List;

import com.tsystems.api.model.bo.McApiInfo;

public interface McApiInfoDao{
	public List<McApiInfo> findAllApiInfo();
	public List<McApiInfo> findAllTriggerApiInfo();	
	public List<McApiInfo> findByNextStep(String id);		
	public List<McApiInfo> findByUserName(String userName); 
	public List<McApiInfo> findByUserMethodType(String userName,String methodName,String dataType);
	public List<McApiInfo> findByUserMethod(String userName,String methodName);
	public McApiInfo loadById(String id);
	public void update(McApiInfo mcApiInfo);	
}