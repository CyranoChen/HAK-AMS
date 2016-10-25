package com.nlia.fqdb.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.fqdb.dao.AlarmInformationDao;
import com.nlia.fqdb.dao.AlarmModeDao;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.service.IAlarmInformationManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;
import com.nlia.obcs.entity.AlarmInformation;
import com.nlia.obcs.entity.AlarmModeAndInfoRelation;
import com.nlia.obcs.entity.AlarmType;
import com.nlia.obcs.entity.AlarmType.AlarmCode;
import com.nlia.obcs.vo.AlarmMode;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AlarmInformationManager extends AbstractCrudService<AlarmInformation, Long> implements IAlarmInformationManager {

	@Resource
	private AlarmInformationDao alarmInformationDao;

	@Resource
	private AlarmModeDao alarmModeDao;

	@Resource
	private FlightBaseManager flightBaseManager;

	@Resource
	private AlarmModeAndInfoRelationManager alarmModeAndInfoRelationManager;

	@Resource
	private AlarmInformationManager alarmInformationManager;

	@Resource
	private AlarmTypeManager alarmTypeManager;

	// @Resource
	// private ServicePlanManager servicePlanManager;

	private List<AlarmModeAndInfoRelation> alarmModeAndInfoRelations;

	@Override
	public AlarmInformation addAlarmInformation(AlarmInformation alarmInformation) {
		alarmInformation.setCreateUser(CommonData.ADMIN);
		alarmInformation.setCreateTime(DateUtils.Date2Date(new Date()));
		alarmInformation.setStatus("1");
		alarmInformation.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		alarmInformation.setAlarmInfoScheduledDateTime(DateUtils.Date2Date(new Date()));
		return alarmInformationDao.save(alarmInformation);
	}

	/**
	 * 生成告警信息,并且存入数据库 InformationEntity:"FlightBase" EntityId:flightbase.id
	 * 
	 * @param obj
	 * @return
	 */
	public AlarmInformation generateAlarmInformation(Object obj) {
		String entityId = "";
		try {
			Method meth = obj.getClass().getMethod("getId");
			entityId = meth.invoke(obj) == null ? "0" : meth.invoke(obj).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		AlarmInformation alarmInformation = new AlarmInformation();
		alarmInformation.setStatus("1");
		alarmInformation.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		alarmInformation.setInformationEntity(obj.getClass().getSimpleName());
		alarmInformation.setEntityId(entityId);
		alarmInformation.setCreateUser(CommonData.ADMIN);
		alarmInformation.setCreateTime(new Date());
		alarmInformation.setAlarmInfoScheduledDateTime(new Date());
		return alarmInformationDao.save(alarmInformation);
	}

	@Override
	public AlarmInformation modifyAlarmInformation(AlarmInformation alarmInformation) {
		return alarmInformationDao.save(alarmInformation);
	}

	@Override
	public AlarmInformation removeAlarmInformation(AlarmInformation alarmInformation) {
		return null;
	}

	@Override
	public List<AlarmInformation> queryAlarmInformation(List<AlarmInformation> alarmInformations) {
		return (List<AlarmInformation>) alarmInformationManager.save(alarmInformations);
	}

	/*
	 * 判断哪类告警 生成告警信息，存库 返回告警信息的id (non-Javadoc)
	 * 
	 * @see
	 * com.nlia.omms.service.IAlarmInformationManager#receiveAlarmMsg(java.lang
	 * .String, com.nlia.omms.entity.FlightBase, java.lang.String)
	 */
	@Override
	public String receiveAlarmMsg(String msg, FlightBase flightBaseOld, String status) {
		AlarmInformation alarmInformation = addDataToInfoAndRelations(msg, flightBaseOld, status);
		// if (alarmInformation != null) {
		// String[] path_msg = new String[] {
		// "msgCenter/api/activeMQ/sendAlarmMsg/",
		// alarmInformation.getInformationEntity() + ":" + "SSMS:" + msg };
		// restfulDataManager.getData("restful", path_msg, String.class);
		// }
		if (alarmInformation == null)
			return "-1";
		return alarmInformation.getId().toString();

	}

	/**
	 * @param msg
	 *            是flightbase的id
	 * @param flightBaseOld
	 * @param status
	 * @return
	 */
	@Transactional
	private AlarmInformation addDataToInfoAndRelations(String msg, FlightBase flightBaseOld, String status) {
		FlightBase flightBase = flightBaseManager.find(Long.valueOf(msg));
		List<String> types = getFlightAlarmType(flightBase, flightBaseOld, status); // 获取航班告警类型
		if (types != null && types.size() > 0) {
			List<AlarmMode> alarmModes = alarmModeDao.getAlarmModesByType(types);
			if (alarmModes != null && alarmModes.size() > 0) {
				AlarmInformation alarmInformation = addAlarmInformation(addAlarmInfoWhenEntityMatchFlight(msg, flightBase));
				receiveMsgAddRelations(alarmModes, alarmInformation);
				return alarmInformation;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 通用方法：告警信息入库, 出错则返回null
	 * @param obj（必须有getId方法）
	 * @param alarmCode
	 * @return AlarmInformation
	 */
	@Override
	public AlarmInformation saveAlarmInformation(Object obj, AlarmCode alarmCode) {
		AlarmType alarmType = alarmTypeManager.find(alarmCode);
		return saveAlarmInformation(obj, alarmType);
	}

	/**
	 * 通用方法：(航班/服务的)告警信息入库, 出错则返回null
	 * alarmInfomation和所有的alarmType关联的alarmMode都会关联
	 * @param obj（必须有getId方法）
	 * @param alarmType
	 * @return AlarmInformation
	 */
	@Transactional
	public AlarmInformation saveAlarmInformation(Object obj, AlarmType alarmType) {
		if (obj == null || alarmType == null || alarmType.getId() == null || alarmType.getId() == 0)
			return null;
		List<AlarmMode> alarmModes = alarmModeDao.getAlarmModesByAlarmTypeCode(alarmType.getAlarmCode());
		if (alarmModes != null && alarmModes.size() > 0) {
			AlarmInformation alarmInformation = generateAlarmInformation(obj);
			// 告警信息的关联关系存入数据库（info&mode关联库），默认为未读状态。
			receiveMsgAddRelations(alarmModes, alarmInformation);
			return alarmInformation;

		} else {
			return null;
		}
	}

//	/**
//	 * 验证服务是否触发告警，如果发生告警则告警信息入库。
//	 * 
//	 * @param sp
//	 * @param flag
//	 * @return InformationEntity='service'
//	 */
//	@Transactional
//	public AlarmInformation addDataToInfoAndRelations(ServicePlan sp, String type) {
//		if (type == null || type.isEmpty())
//			return null;
//		List<AlarmMode> alarmModes = alarmModeDao.getAlarmModesBySpecial(type);
//		if (alarmModes != null && alarmModes.size() > 0) {
//			AlarmInformation alarmInformation = addAlarmInformation(addAlarmInfoWhenEntityMatchService(sp, type));
//			// 告警信息存数据库，info&mode存库
//			receiveMsgAddRelations(alarmModes, alarmInformation);
//			return alarmInformation;
//
//		} else {
//			return null;
//		}
//	}

	/**
	 * 添加航班告警
	 * 
	 * @param msg
	 * @return
	 */
	public AlarmInformation addAlarmInfoWhenEntityMatchFlight(String msg, FlightBase flightBase) {
		AlarmInformation alarmInformation = new AlarmInformation();
		alarmInformation.setEntityId(msg);
		alarmInformation.setInformationEntity(flightBase.getClass().getSimpleName());
		alarmInformation.setMessageContent(flightBase.getFlightIdentity());
		return alarmInformation;
	}

//	/**
//	 * 生成服务告警
//	 * 
//	 * @param msg
//	 * @return
//	 */
//	public AlarmInformation addAlarmInfoWhenEntityMatchService(ServicePlan sp, String type) {
//		String entityId = sp.getId().toString();
//		StringBuffer messageContent = new StringBuffer("");
//		messageContent.append("航班号：");
//		messageContent.append(sp.getName());
//		messageContent.append("_");
//		messageContent.append(type);
//		// messageContent.append(" ");
//		// messageContent.append("建议进行上客服务");
//		AlarmInformation alarmInformation = new AlarmInformation();
//		alarmInformation.setEntityId(entityId);
//		alarmInformation.setInformationEntity("service");
//		alarmInformation.setMessageContent(messageContent.toString());
//		return alarmInformation;
//	}

	/**
	 * 向modeAndInfo表添加数据
	 * 
	 * @param alarmModes
	 * @param alarmInformation
	 * @return
	 */
	public List<AlarmModeAndInfoRelation> receiveMsgAddRelations(List<AlarmMode> alarmModes, AlarmInformation alarmInformation) {
		alarmModeAndInfoRelations = new ArrayList<>();
		if (alarmModes.size() > 0) {
			for (AlarmMode alarmMode : alarmModes) {
				AlarmModeAndInfoRelation alarmModeAndInfoRelation = new AlarmModeAndInfoRelation();
				alarmModeAndInfoRelation.setMode(alarmMode);
				alarmModeAndInfoRelation.setIsAlreadyRead(AlarmModeAndInfoRelation.IS_ALREADY_READ.UNREAD);
				alarmModeAndInfoRelation.setAlarmInformation(alarmInformation);
				alarmModeAndInfoRelation.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
				alarmModeAndInfoRelations.add(alarmModeAndInfoRelation);
			}
		}

		alarmModeAndInfoRelations = (List<AlarmModeAndInfoRelation>) alarmModeAndInfoRelationManager.save(alarmModeAndInfoRelations);
		return alarmModeAndInfoRelations;
	}

	/**
	 * 验证航班是否产生告警，仅告警当日航班。 如果航班需要告警则返回告警字符串，否则返回一个空的list。list.size()==0.
	 * 
	 * @return
	 */
	private List<String> getFlightAlarmType(FlightBase flightBase, FlightBase flightBaseOld, String status) {
		Date todayStart = DateUtils.getZeroOfToday();
		Date todayEnd = DateUtils.getLatestTimeOfToday();
		List<String> types = new ArrayList<>();
		if (flightBase.getFlightData().getFlightScheduledDateTime() == null || flightBase.getFlightData().getFlightScheduledDateTime().before(todayStart)
				|| flightBase.getFlightData().getFlightScheduledDateTime().after(todayEnd)) {
			return null;
		} else {
			if (status.equals("ADD")) {
				types.add("新增航班");
			} else if (status.equals("UPDATE")) {
				if (!flightBase.getFlightData().getFlightScheduledDateTime().equals(flightBaseOld.getFlightData().getFlightScheduledDateTime())) {
					types.add("预计起飞/到达时间发生变化");
				}
				if (flightBase.getFlightData().getOperationStatus() != null) {
					if (flightBase.getFlightData().getOperationStatus().equals("CNCL")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("CNCL"))) {
						types.add("航班取消");// 常规取消
					} else if (flightBase.getFlightData().getOperationStatus().equals("CNLAD")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("CNLAD"))) {
						types.add("航班取消");// 新增航班取消
					} else if (flightBase.getFlightData().getOperationStatus().equals("DELETET")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("DELETET"))) {
						types.add("航班延误");// 延误有延误时间
					} else if (flightBase.getFlightData().getOperationStatus().equals("DELNT")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("DELNT"))) {
						types.add("航班延误");// 延误无延误时间，预计时间为空
					} else if (flightBase.getFlightData().getOperationStatus().equals("DIVAL")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("DIVAL"))) {
						types.add("航班备降");// 备降（备出）
					} else if (flightBase.getFlightData().getOperationStatus().equals("DIVCH")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("DIVCH"))) {
						types.add("航班改降");// 改降
					} else if (flightBase.getFlightData().getOperationStatus().equals("RETRN")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("RETRN"))) {
						types.add("航班返航");
					} else if (flightBase.getFlightData().getOperationStatus().equals("SLIBK")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("SLIBK"))) {
						types.add("航班滑回");
					}
					// EARLY　提前航班
					// NOOPE Flight No Operation 未执行航班

				}
				if (flightBaseOld.getFlightResource() != null && flightBase.getFlightResource() != null) {
					if ((flightBaseOld.getFlightResource().getStandID() == null && flightBase.getFlightResource().getStandID() != null) || flightBaseOld.getFlightResource().getStandID() != null
							&& !flightBase.getFlightResource().getStandID().equals(flightBaseOld.getFlightResource().getStandID())) {
						types.add("航班机位变更");
					}
					if ((flightBaseOld.getFlightResource().getGateID() == null && flightBase.getFlightResource().getGateID() != null)
							|| (flightBaseOld.getFlightResource().getGateID() != null && !flightBase.getFlightResource().getGateID().equals(flightBaseOld.getFlightResource().getGateID()))) {
						types.add("航班登机门变更");
					}
					if ((flightBaseOld.getFlightResource().getCheckInID() == null && flightBase.getFlightResource().getCheckInID() != null)
							|| (flightBaseOld.getFlightResource().getCheckInID() != null && !flightBase.getFlightResource().getCheckInID().equals(flightBaseOld.getFlightResource().getCheckInID()))) {
						types.add("航班值机柜台变更");
					}
					if ((flightBaseOld.getFlightResource().getBaggageBeltID() == null && flightBase.getFlightResource().getBaggageBeltID() != null)
							|| (flightBaseOld.getFlightResource().getBaggageBeltID() != null && !flightBase.getFlightResource().getBaggageBeltID()
									.equals(flightBaseOld.getFlightResource().getBaggageBeltID()))) {
						types.add("航班行李转盘变更");
					}
				}
			}
			return types;
		}

	}

	/**
	 * 检测航班是否触发告警
	 * 触发则返回alarminfomation的id，
	 * 没触发返回-1
	 * @param flight
	 * @param status (ADD,UPDATE)
	 * @return
	 */
	public long hitAlarmRule(FlightBase oldFlight, String status) {
		// 航班告警旧逻辑
		/*
		 * String alarmInfoId =
		 * alarmInformationManager.receiveAlarmMsg(oldFlight
		 * .getId().toString(),oldFlight,status); return
		 * Long.valueOf(alarmInfoId);
		 */
		// 航班告警新逻辑（通用告警逻辑）
		FlightBase flight = flightBaseManager.find(oldFlight.getId());
		AlarmType alarmType = alarmTypeManager.getFlightAlarmType(flight, oldFlight, status);
		if (alarmType == null)
			return -1l;
		AlarmInformation alarmInformation = saveAlarmInformation(flight, alarmType);
		return alarmInformation == null ? -1l : alarmInformation.getId();
	}

	@Override
	protected GenericDao<AlarmInformation, Long> getDao() {
		return alarmInformationDao;
	}

	/**
	 * 获得实体对象
	 * @param ai
	 * @return
	 */
	@Override
	public Object getEntity(AlarmInformation ai) {
		return alarmInformationDao.getEntity(ai);
	}

}
