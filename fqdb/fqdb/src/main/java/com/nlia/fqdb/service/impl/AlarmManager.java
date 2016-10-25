package com.nlia.fqdb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.obcs.entity.AlarmInformation;
import com.nlia.obcs.entity.AlarmType;
import com.nlia.obcs.entity.AlarmType.AlarmCode;
import com.nlia.fqdb.service.impl.AlarmModeAndInfoRelationManager;
import com.nlia.fqdb.service.IAlarmManager;

/**
 * 告警消息/同步消息 处理类
 * @author ninja_chen
 *
 */
@Service
public class AlarmManager implements IAlarmManager {
	
	@Resource
	private AlarmInformationManager alarmInformationManager;
	
	@Resource
	private AlarmModeAndInfoRelationManager alarmModeAndInfoRelationManager;

	@Resource
	private RestfulDataManager<String> restfulDataManager;
	
//	@Resource
//	private AlarmTypeManager alarmTypeManager;
	
	
	/**
	 * 存储告警信息且发送告警信息id到客户端
	 * 作用：同步所有客户端的实体数据（如serviceplan数据）,并标记为已读
	 * @param entity
	 * @param code
	 * @return
	 */
	@Override
	public boolean alarm(Object entity, String code) {
		AlarmCode alarmCode;
		try {
			alarmCode = AlarmType.AlarmCode.valueOf(code);
			return alarm(entity, alarmCode);
//			if(alarmCode == null) return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		/*// 获取服务告警类型,如　开舱门延迟开始告警
		AlarmInformation alarmInformation = alarmInformationManager.saveAlarmInformation(entity, AlarmType.AlarmCode.valueOf(code));
		if (alarmInformation == null) {
			return false;
		}
		System.out.println(alarmInformation.getInformationEntity() + alarmInformation.getEntityId() + "产生数据同步消息,消息id:" + alarmInformation.getId());
		String[] path_msg = new String[] { "msgCenter/api/activeMQ/sendAlarmMsg/", "SSMS:SSMS:"+alarmInformation.getId().toString() };
		List<String> result = restfulDataManager.getData("msgCenter", path_msg, String.class);
		System.out.println("发MQ结果：" + result.toString());
		alarmModeAndInfoRelationManager.setAlarmModeReaded(alarmInformation.getId());*/
	}
	/**
	 * 存储告警信息且发送告警信息id到客户端
	 * 作用：同步所有客户端的实体数据（如serviceplan数据）,并标记为已读
	 * @param entity
	 * @param alarmCode
	 * @return
	 */
	@Override
	public boolean alarm(Object entity, AlarmCode alarmCode) {
		// 获取服务告警类型,如　开舱门延迟开始告警
		AlarmInformation alarmInformation = alarmInformationManager.saveAlarmInformation(entity, alarmCode);
		if (alarmInformation == null) {
			return false;
		}
		System.out.println(alarmInformation.getInformationEntity() + alarmInformation.getEntityId() + "产生数据同步消息,消息id:" + alarmInformation.getId());
		String[] path_msg = new String[] { "msgCenter/api/activeMQ/sendAlarmMsg/", "SSMS:SSMS:"+alarmInformation.getId().toString() };
		List<String> result = restfulDataManager.getData("msgCenter", path_msg, String.class);
		System.out.println("发MQ结果：" + result.toString());
		alarmModeAndInfoRelationManager.setAlarmModeReaded(alarmInformation.getId());
		return true;
	}
//	/**
//	 * 存储告警信息且发送告警信息id到客户端
//	 * 作用：同步所有客户端的实体数据（如serviceplan数据）
//	 * @param <T>
//	 * @param entity
//	 * @param code
//	 * @return
//	 */
//	@Override
//	public <T> boolean alarm(Collection<T> entity, AlarmCode code) {
//		// 获取服务告警类型,如　开舱门延迟开始告警
//		AlarmInformation alarmInformation = alarmInformationManager.saveAlarmInformation(entity, code);
//		if (alarmInformation == null) {
//			return false;
//		}
//		System.out.println(alarmInformation.getInformationEntity() + alarmInformation.getEntityId() + "产生数据同步消息,消息id:" + alarmInformation.getId());
//		String[] path_msg = new String[] { "msgCenter/api/activeMQ/sendAlarmMsg/", "SSMS:SSMS:"+alarmInformation.getId().toString() };
//		List<String> result = restfulDataManager.getData("msgCenter", path_msg, String.class);
//		System.out.println("发MQ结果：" + result.toString());
//		return true;
//	}
	
	/**
	 * 存储告警信息且发送告警信息id到客户端
	 * 作用：同步所有客户端的实体数据（如serviceplan数据）
	 * @param entity
	 * @param code
	 * @return
	 */
	@Override
	public boolean alarmWithOutSend(Object entity, AlarmCode code) {
		// 获取服务告警类型,如　开舱门延迟开始告警
		AlarmInformation alarmInformation = alarmInformationManager.saveAlarmInformation(entity, code);
		if (alarmInformation == null) {
			return false;
		}
		System.out.println(alarmInformation.getInformationEntity() + alarmInformation.getEntityId() + "产生数据同步消息,消息id:" + alarmInformation.getId());
		String[] path_msg = new String[] { "msgCenter/api/activeMQ/sendAlarmMsg/", "SSMS:SSMS:"+alarmInformation.getId().toString() };
		List<String> result = restfulDataManager.getData("msgCenter", path_msg, String.class);
		System.out.println("发MQ结果：" + result.toString());
		return true;
	}
}
