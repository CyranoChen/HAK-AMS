package com.tsystems.api.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tsystems.api.dao.McApiInfoDao;
import com.tsystems.api.dao.McApiLogDao;
import com.tsystems.api.model.bo.McApiInfo;
import com.tsystems.api.model.bo.McApiLog;
import com.tsystems.api.service.McApiInfoService;
import com.tsystems.imfMsg.entity.vo.Page;
import com.tsystems.schedule.common.service.CommonService;
import com.tsystems.schedule.main.ScheduleMain;
import com.tsystems.schedule.model.bo.TScheduleConfig;


/**
 * @author lushuaifeng
 * @version $Revision$
 * @date 2013-10-22
 * @author modify by $Author$
 * @since 1.0
 */
@Service("mcApiInfoService")
@Transactional(value="txManager",propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class McApiInfoServiceImpl implements McApiInfoService {
	@Autowired
	private McApiInfoDao mcApiInfoDao;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private McApiLogDao mcApiLogDao;	

	/*apiInfo*/
	public List<McApiInfo> findAllApiInfo() {
		return mcApiInfoDao.findAllApiInfo();
	}
	
	public List<McApiInfo> findAllTriggerApiInfo() {
		return mcApiInfoDao.findAllTriggerApiInfo();
	}	
	
	public List<McApiInfo> findByNextStep(String id){
		return mcApiInfoDao.findByNextStep(id);
	}	
	
	public List<McApiInfo> findByUserName(String userName) {
		return mcApiInfoDao.findByUserName(userName);
	}
	
	public List<McApiInfo> findByUserMethodType(String userName,String methodName,String dataType){
		return mcApiInfoDao.findByUserMethodType(userName, methodName, dataType);
	}
			
	public List<McApiInfo> findByUserMethod(String userName,String methodName){
		return mcApiInfoDao.findByUserMethod(userName, methodName);
	}
	
	public McApiInfo loadById(String id) {
		return mcApiInfoDao.loadById(id);
	}	
	
	public void update(McApiInfo mcApiInfo) {
		mcApiInfoDao.update(mcApiInfo);
	}	
	/*scheduleConfig*/
	public TScheduleConfig loadScheduleConfigById(String id){
		return (TScheduleConfig)commonService.load(id, TScheduleConfig.class);
	}	
	
	public void updateScheduleConfig(TScheduleConfig scheduleConfig){
		ScheduleMain.cancelTask(scheduleConfig.getId());
		commonService.update(scheduleConfig);
		if(scheduleConfig.getRemoved().equals("0")){
			ScheduleMain.addTask(scheduleConfig);
		}
//		commonService.update(scheduleConfig);
//		ScheduleMain.restart();	
	}	
	
	/*apiLog*/
	public void saveLog(McApiLog mcApiLog){
		mcApiLogDao.save(mcApiLog);
	}
	public void deleteLog(McApiLog mcApiLog){
		mcApiLogDao.delete(mcApiLog);
	}
	public void updateLog(McApiLog mcApiLog){
		mcApiLogDao.update(mcApiLog);
	}
	public McApiLog loadLogById(String id){
		return mcApiLogDao.loadById(id);
	}	
	
/*	public List<MsgStore> findMsgStoreList(HashMap<String, String> filter,String sortWay){
		Page page=mcApiLogDao.findListByPage(filter, sortWay);
		List<MsgStore> msgs=page.getResult();
		return msgs;
	}*/
	
	public Page findLogByPage(HashMap<String, String> filter,String sortWay){
		Page page=mcApiLogDao.findListByPage(filter, sortWay);
		return page;
	}
	
}
