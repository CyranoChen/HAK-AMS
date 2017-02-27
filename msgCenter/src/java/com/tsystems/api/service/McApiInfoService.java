package com.tsystems.api.service;

import java.util.HashMap;
import java.util.List;

import com.tsystems.api.model.bo.McApiInfo;
import com.tsystems.api.model.bo.McApiLog;
import com.tsystems.imfMsg.entity.vo.Page;
import com.tsystems.schedule.model.bo.TScheduleConfig;



/**
 * 业务服务
 * 
 * @author lushuaifeng
 * @version $Revision$
 * @date 2013-10-22
 * @author modify by $Author$
 * @since 1.0
 */

public interface McApiInfoService {
	public List<McApiInfo> findAllApiInfo(); 
	public List<McApiInfo> findAllTriggerApiInfo();	
	public List<McApiInfo> findByNextStep(String id);		
	public List<McApiInfo> findByUserName(String userName); 
	public List<McApiInfo> findByUserMethodType(String userName,String methodName,String dataType);
	public List<McApiInfo> findByUserMethod(String userName,String methodName);
	public McApiInfo loadById(String id); 	
	public void update(McApiInfo mcApiInfo);
	public TScheduleConfig loadScheduleConfigById(String id);
	public void updateScheduleConfig(TScheduleConfig scheduleConfig);	
	
	public void saveLog(McApiLog mcApiLog);
	public void deleteLog(McApiLog mcApiLog);
	public void updateLog(McApiLog mcApiLog);
	public McApiLog loadLogById(String id);	
	public Page findLogByPage(HashMap<String, String> filter,String sortWay);	
		
}
