package com.nlia.fqdb.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.obcs.entity.AlarmModeAndStaffRelation;
import com.nlia.fqdb.dao.AlarmModeAndStaffRelationDao;
import com.nlia.fqdb.service.IAlarmModeAndStaffRelationManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AlarmModeAndStaffRelationManager extends AbstractCrudService<AlarmModeAndStaffRelation, Long> implements IAlarmModeAndStaffRelationManager {

	@Resource
	private AlarmModeAndStaffRelationDao alarmModeAndStaffRelationDao;
	
	@Override
	public AlarmModeAndStaffRelation addAlarmModeAndStaffRelation(AlarmModeAndStaffRelation alarmModeAndStaffRelation) {
		return alarmModeAndStaffRelationDao.save(alarmModeAndStaffRelation);
	}
	
	@Override
	public AlarmModeAndStaffRelation modifyAlarmModeAndStaffRelation(AlarmModeAndStaffRelation alarmModeAndStaffRelation) {
		return alarmModeAndStaffRelationDao.save(alarmModeAndStaffRelation);
	}

	@Override
	public AlarmModeAndStaffRelation removeAlarmModeAndStaffRelation(AlarmModeAndStaffRelation alarmModeAndStaffRelation) {
		return null;
	}

	@Override
	public List<AlarmModeAndStaffRelation> queryAlarmModeAndStaffRelation(List<AlarmModeAndStaffRelation> alarmModeAndStaffRelations) {
		return null;
	}

	@Override
	protected GenericDao<AlarmModeAndStaffRelation, Long> getDao() {
		return alarmModeAndStaffRelationDao;
	}

	@Override
	@Transactional
	public boolean deleteAlarmModeAndStaffRelations(List<AlarmModeAndStaffRelation> alarmModeAndStaffRelations) {
		try {
			for (AlarmModeAndStaffRelation alarmModeAndStaffRelation : alarmModeAndStaffRelations) {
				alarmModeAndStaffRelationDao.remove(alarmModeAndStaffRelation.getId());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	@Transactional
	public List<AlarmModeAndStaffRelation> subscription(List<AlarmModeAndStaffRelation> addAlarmModeAndStaffRelations , List<AlarmModeAndStaffRelation> deleteAlarmModeAndStaffRelations){
		List<AlarmModeAndStaffRelation> alarmModeAndStaffRelations = null;
		try {
			alarmModeAndStaffRelations = (List<AlarmModeAndStaffRelation>)this.save(addAlarmModeAndStaffRelations);
			this.deleteAlarmModeAndStaffRelations(deleteAlarmModeAndStaffRelations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alarmModeAndStaffRelations;
	}

}
