package com.nlia.fqdb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.obcs.entity.AlarmType;
import com.nlia.obcs.entity.AlarmType.AlarmCode;
import com.nlia.obcs.vo.AlarmMode;
import com.nlia.fqdb.dao.AlarmModeDao;
import com.nlia.fqdb.dao.AlarmTypeDao;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.service.IAlarmTypeManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AlarmTypeManager extends AbstractCrudService<AlarmType, Long> implements IAlarmTypeManager {

	@Resource
	private AlarmTypeDao alarmTypeDao;
	@Resource
	private IAlarmTypeManager alarmTypeManager;

	@Resource
	private AlarmModeDao alarmModeDao;

	@Override
	public AlarmType addAlarmType(AlarmType alarmType) {
		alarmType.setRemoveFlag("1");
		alarmType.setStatus("1");
		alarmType.setCreateTime(DateUtils.Date2Date(new Date(), "yyyy-MM-dd HH:mm"));
		alarmType.setCreateUser(CommonData.ADMIN);
		return alarmTypeDao.save(alarmType);
	}

	@Override
	public AlarmType modifyAlarmType(AlarmType alarmType) {
		alarmType.setRemoveFlag("1");
		alarmType.setStatus("1");
		alarmType.setModifyTime(DateUtils.Date2Date(new Date(), "yyyy-MM-dd HH:mm"));
		alarmType.setModifyUser(CommonData.ADMIN);
		return alarmTypeDao.save(alarmType);
	}

	@Override
	public AlarmType removeAlarmType(AlarmType alarmType) {
		return alarmTypeDao.save(alarmType);
	}

	@Override
	public List<AlarmType> queryAlarmType(Map<String, Object> filters, Map<String, String> sorts) {
		return null;
	}

	@Override
	@Transactional
	public List<AlarmType> enableAlarmType(List<AlarmType> alarmTypes) {
		for (AlarmType alarmType : alarmTypes) {
			alarmType.setStatus("1");
		}
		return (List<AlarmType>) alarmTypeManager.save(alarmTypes);
	}

	@Override
	@Transactional
	public List<AlarmType> disableAlarmType(List<AlarmType> alarmTypes) {
		for (AlarmType alarmType : alarmTypes) {
			alarmType.setStatus(AlarmMode.MODE_STATUS.DISABLED.getValue());
		}
		return (List<AlarmType>) alarmTypeManager.save(alarmTypes);
	}

	@Override
	protected GenericDao<AlarmType, Long> getDao() {
		return alarmTypeDao;
	}

	@Override
	public AlarmMode addAlarmMode(AlarmMode alarmMode) {
		alarmMode.setStatus(AlarmMode.MODE_STATUS.ENABLED.getValue());
		return alarmModeDao.save(alarmMode);
	}

	@Override
	public AlarmMode modifyAlarmMode(AlarmMode alarmMode) {
		return alarmModeDao.save(alarmMode);
	}

	@Override
	public AlarmMode removeAlarmMode(AlarmMode alarmMode) {
		alarmMode = alarmModeDao.save(alarmMode);
		return alarmMode;
	}

	@Override
	public List<AlarmMode> queryAlarmMode(Map<String, Object> filters) {
		List<AlarmMode> alarmModes = alarmModeDao.findBy(filters, null, 0, Integer.MAX_VALUE);
		return alarmModes;
	}

	@Override
	public List<AlarmMode> queryAlarmMode(Map<String, Object> filters, List<String> sorters) {
		List<AlarmMode> alarmModes = alarmModeDao.findBy(filters, sorters, 0, Integer.MAX_VALUE);
		return alarmModes;
	}

	@Override
	@Transactional
	public List<AlarmMode> removeAlarModes(List<AlarmMode> alarmModes) {
		try {
			for (int i = 0; i < alarmModes.size(); i++) {
				alarmModeDao.remove(alarmModes.get(i).getId());
			}

			return alarmModes;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public List<AlarmMode> enableAlarmMode(List<AlarmMode> alarmModes) {
		List<AlarmMode> tempAlarmModes = new ArrayList<>();
		AlarmMode tempAlarmMode;
		try {
			for (AlarmMode alarmMode : alarmModes) {
				alarmMode.setStatus("1");
				tempAlarmMode = alarmModeDao.save(alarmMode);
				tempAlarmModes.add(tempAlarmMode);
			}
			return tempAlarmModes;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public List<AlarmMode> disableAlarmMode(List<AlarmMode> alarmModes) {
		List<AlarmMode> tempAlarmModes = new ArrayList<>();
		AlarmMode tempAlarmMode;
		try {
			for (AlarmMode alarmMode : alarmModes) {
				alarmMode.setStatus("0");
				tempAlarmMode = alarmModeDao.save(alarmMode);
				tempAlarmModes.add(tempAlarmMode);
			}
			return tempAlarmModes;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ninja优化 
	 * 验证航班是否产生告警，仅告警当日航班。
	 * 如果航班需要告警则返回告警字符串，否则返回一个空的list。list.size()==0.
	 * 
	 * @return
	 */
	public AlarmType getFlightAlarmType(FlightBase flightBase, FlightBase flightBaseOld, String status) {
		Date todayStart = DateUtils.getZeroOfToday();
		Date todayEnd = DateUtils.getLatestTimeOfToday();
		AlarmType alarmType = null;
		if (flightBase.getFlightData().getFlightScheduledDateTime() == null || flightBase.getFlightData().getFlightScheduledDateTime().before(todayStart)
				|| flightBase.getFlightData().getFlightScheduledDateTime().after(todayEnd)) {
			return null;
		} else {
			if (status.equals("ADD")) {
				// 新增航班
				alarmType = find("FLIGHT_ADD");
			} else if (status.equals("UPDATE")) {
				if (!flightBase.getFlightData().getFlightScheduledDateTime().equals(flightBaseOld.getFlightData().getFlightScheduledDateTime())) {
					// 预计起飞/到达时间发生变化
					alarmType = find("FLIGHT_BUSINESSTIME_CHANGE");// types.add("");
				}
				if (flightBase.getFlightData().getOperationStatus() != null) {
					if (flightBase.getFlightData().getOperationStatus().equals("CNCL")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("CNCL"))) {
						alarmType = find("FLIGHT_CNCL");// 常规取消
					} else if (flightBase.getFlightData().getOperationStatus().equals("CNLAD")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("CNLAD"))) {
						alarmType = find("FLIGHT_CNLAD");// 取消,并新增航班
					} else if (flightBase.getFlightData().getOperationStatus().equals("DELET")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("DELETET"))) {
						alarmType = find("FLIGHT_DELETET");// 延误有延误时间
					} else if (flightBase.getFlightData().getOperationStatus().equals("DELNT")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("DELNT"))) {
						alarmType = find("FLIGHT_DELNT");// 延误无延误时间，预计时间为空
					} else if (flightBase.getFlightData().getOperationStatus().equals("DIVAL")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("DIVAL"))) {
						alarmType = find("FLIGHT_DIVAL");// 备降（备出）
					} else if (flightBase.getFlightData().getOperationStatus().equals("DIVCH")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("DIVCH"))) {
						alarmType = find("FLIGHT_DIVCH");// 改降
					} else if (flightBase.getFlightData().getOperationStatus().equals("RFA")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("RETRN"))) {
						alarmType = find("FLIGHT_RETRN");// 航班返航
					} else if (flightBase.getFlightData().getOperationStatus().equals("RFT")
							&& (flightBaseOld.getFlightData().getOperationStatus() == null || !flightBaseOld.getFlightData().getOperationStatus().equals("SLIBK"))) {
						alarmType = find("FLIGHT_SLIBK");// 航班滑回
					}
					// EARLY　提前航班
					// NOOPE Flight No Operation 未执行航班

				}
				if (flightBaseOld.getFlightResource() != null && flightBase.getFlightResource() != null) {
					if ((flightBaseOld.getFlightResource().getStandID() == null && flightBase.getFlightResource().getStandID() != null) || flightBaseOld.getFlightResource().getStandID() != null
							&& !flightBase.getFlightResource().getStandID().equals(flightBaseOld.getFlightResource().getStandID())) {
						alarmType = find("FLIGHT_STAND_CHANGE");// types.add("航班机位变更");
					}
					if ((flightBaseOld.getFlightResource().getGateID() == null && flightBase.getFlightResource().getGateID() != null)
							|| (flightBaseOld.getFlightResource().getGateID() != null && !flightBase.getFlightResource().getGateID().equals(flightBaseOld.getFlightResource().getGateID()))) {
						alarmType = find("FLIGHT_GATE_CHANGE");// types.add("航班登机门变更");
					}
					if ((flightBaseOld.getFlightResource().getCheckInID() == null && flightBase.getFlightResource().getCheckInID() != null)
							|| (flightBaseOld.getFlightResource().getCheckInID() != null && !flightBase.getFlightResource().getCheckInID().equals(flightBaseOld.getFlightResource().getCheckInID()))) {
						alarmType = find("FLIGHT_CHECKIN_CHANGE");// types.add("航班值机柜台变更");
					}
					if ((flightBaseOld.getFlightResource().getBaggageBeltID() == null && flightBase.getFlightResource().getBaggageBeltID() != null)
							|| (flightBaseOld.getFlightResource().getBaggageBeltID() != null && !flightBase.getFlightResource().getBaggageBeltID()
									.equals(flightBaseOld.getFlightResource().getBaggageBeltID()))) {
						alarmType = find("FLIGHT_BAGGAGE_CHANGE");// types.add("航班行李转盘变更");
					}
				}
			}
			return alarmType;
		}

	}

	/**
	 * 通过alarmCode查询AlarmType
	 * 
	 * @param alarmCode
	 * @return
	 */
	public AlarmType find(String alarmCode) {
		AlarmCode alarm_Code = AlarmCode.valueOf(alarmCode);
		if (alarm_Code == null)
			return null;
		return alarmTypeDao.find(alarm_Code);
	}

	public AlarmType find(AlarmCode alarmCode) {
		return alarmTypeDao.find(alarmCode);
	}

}
