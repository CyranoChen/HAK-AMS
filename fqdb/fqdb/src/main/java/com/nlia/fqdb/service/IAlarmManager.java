package com.nlia.fqdb.service;

import com.nlia.obcs.entity.AlarmType.AlarmCode;

/**
 * 告警消息/同步消息 处理类
 * @author ninja_chen
 *
 */
public interface IAlarmManager {
	
	
	/**
	 * 存储告警信息且发送告警信息id到客户端
	 * 作用：同步所有客户端的实体数据（如serviceplan数据）
	 * @param entity
	 * @param code
	 * @return
	 */
	public boolean alarm(Object entity, String code);
	/**
	 * 存储告警信息且发送告警信息id到客户端
	 * 作用：同步所有客户端的实体数据（如serviceplan数据）
	 * @param entity
	 * @param alarmCode
	 * @return
	 */
	public boolean alarm(Object entity, AlarmCode alarmCode);
//	/**
//	 * 存储告警信息且发送告警信息id到客户端
//	 * 作用：同步所有客户端的实体数据（如serviceplan数据）
//	 * @param <T>
//	 * @param entity
//	 * @param code
//	 * @return
//	 */
//	public <T> boolean alarm(Collection<T> entity, AlarmCode code);
	
	/**
	 * 存储告警信息且发送告警信息id到客户端
	 * 作用：同步所有客户端的实体数据（如serviceplan数据）
	 * @param entity
	 * @param code
	 * @return
	 */
	public boolean alarmWithOutSend(Object entity, AlarmCode code);
}
