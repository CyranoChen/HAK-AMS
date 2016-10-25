package com.nlia.fqdb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.AircraftDao;
import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.AircraftAirlineAlteration.VALIDFLAG;
import com.nlia.fqdb.entity.base.Aircraft;
import com.nlia.fqdb.service.IAircraftManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportAircraftByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AircraftManager extends AbstractCrudService<Aircraft, Long>
		implements IAircraftManager {
	@Resource
	private AircraftDao aircraftDao;

	@Override
	protected GenericDao<Aircraft, Long> getDao() {
		return aircraftDao;
	}

	/*
	 * 查指定飞机参数：aircraftRegistration
	 */
	@Override
	public List<Aircraft> findByAircraftRegistration(String aircraftRegistration) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("aircraftRegistration_equal", aircraftRegistration);
		List<Aircraft> airlineList = aircraftDao.findBy(filters, null, -1, -1);
		return airlineList;
	}

	/*
	 * 查询全部飞机
	 */
	@Override
	public List<Aircraft> findAllAircraft() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Aircraft> airlineList = aircraftDao.findBy(filters, null, -1, -1);
		return airlineList;
	}
	
	@Resource
	private ImportAircraftByExcel importAircraftByExcel;
	
	@Override
	public List<Aircraft> importByExcel2003(byte[] bytes) {
		List<Aircraft> aircraftList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			aircraftList = importAircraftByExcel.ImportAircraftByExcel2003(input);
		} catch (IOException e) {
		}
		return aircraftList;
	}

	@Override
	public List<Aircraft> importByExcel2007(byte[] bytes) {
		List<Aircraft> aircraftList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			aircraftList = importAircraftByExcel.ImportAircraftByExcel2007(input);
		} catch (IOException e) {
		}
		return aircraftList;
	}
//	   /*
//     * AircraftAirlineAlteration的变更需要同步修改Aircraft
//     */
//    @Resource
//    private AircraftAirlineAlterationManager aircraftAirlineAlterationManager;
//    
//    @Override
//    public Aircraft save(Aircraft entity){
//        entity=super.save(entity);
//        Map<String, Object> filters = new HashMap<>();
//        filters.put("aircraftRegistration_equal", entity.getAircraftRegistration());
//        filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
//        List<AircraftAirlineAlteration> aircraftAirlineAlterations = aircraftAirlineAlterationManager.findBy(filters);
//        //找不到，新增
//        if(aircraftAirlineAlterations==null || aircraftAirlineAlterations.size()==0){
//            AircraftAirlineAlteration aircraftAirlineAlteration=new AircraftAirlineAlteration();
//            aircraftAirlineAlteration.setVerifyDescription(                       entity.getVerifyDescription());
//            aircraftAirlineAlteration.setAircraftRegistration(                       entity.getAircraftRegistration());
//            aircraftAirlineAlteration.setAircraftType(                               entity.getAircraftType());
//            aircraftAirlineAlteration.setAircraftAirline(                            entity.getAircraftAirline() );
//            aircraftAirlineAlteration.setAircraftDescription(                        entity.getAircraftDescription() );
//            aircraftAirlineAlteration.setAircraftEngineNumber(                       entity.getAircraftEngineNumber() );
//            aircraftAirlineAlteration.setAircraftTakeoffWeight(                      entity.getAircraftTakeoffWeight());
//            aircraftAirlineAlteration.setAircraftLandingWeight(                      entity.getAircraftLandingWeight());
//            aircraftAirlineAlteration.setAircraftPayload(                            entity.getAircraftPayload());
//            aircraftAirlineAlteration.setAircraftSeatCapacity(                       entity.getAircraftSeatCapacity() );
//            aircraftAirlineAlteration.setAircraftNoiseCategory(                      entity.getAircraftNoiseCategory() );
//            aircraftAirlineAlteration.setAircraftHeight(                             entity.getAircraftHeight() );
//            aircraftAirlineAlteration.setAircraftLength(                             entity.getAircraftLength() );
//            aircraftAirlineAlteration.setAircraftWidth(                              entity.getAircraftWidth());
//            aircraftAirlineAlteration.setRemoveFlag(                                 entity.getRemoveFlag());
//            aircraftAirlineAlteration.setCreateUser(                                 entity.getCreateUser());
//            aircraftAirlineAlteration.setCreateTime(                                 entity.getCreateTime() );
//            aircraftAirlineAlteration.setModifyUser(                                 entity.getModifyUser());
//            aircraftAirlineAlteration.setModifyTime(                                 entity.getModifyTime() );
//            aircraftAirlineAlteration.setBasicDataID(                                entity.getBasicDataID() );
//            aircraftAirlineAlteration.setAircraftLeasingAirline(                     entity.getAircraftLeasingAirline());
//            aircraftAirlineAlteration.setAircraftTypeICAOCode(                       entity.getAircraftTypeICAOCode());
//            aircraftAirlineAlteration.setAircraftTypeIATACode(                       entity.getAircraftTypeIATACode());
//            aircraftAirlineAlteration.setValidFlag(VALIDFLAG.INVALID);
//            aircraftAirlineAlteration.setStartDate(                                 entity.getCreateTime() );
//            aircraftAirlineAlteration.setEndDate(                                 entity.getCreateTime() );
//            aircraftAirlineAlteration.setIsWideOrNarrow(                          entity.getIsWideOrNarrow());
//            aircraftAirlineAlterationManager.save(aircraftAirlineAlteration);
//        }
//        return entity;
//    }
}
