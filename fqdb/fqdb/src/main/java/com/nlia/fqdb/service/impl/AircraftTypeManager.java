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

import com.nlia.fqdb.dao.AircraftTypeDao;
import com.nlia.fqdb.entity.base.AircraftType;
import com.nlia.fqdb.service.IAircraftTypeManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportAircraftTypeByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AircraftTypeManager extends AbstractCrudService<AircraftType, Long>
		implements IAircraftTypeManager {

	@Resource
	private AircraftTypeDao aircraftTypeDao;

	@Override
	protected GenericDao<AircraftType, Long> getDao() {
		return aircraftTypeDao;
	}



	/*
	 * 查指定机型
	 * 参数：IATACode
	 */
	@Override
	public List<AircraftType> findByAircraftTypeIATACode(String IATACode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("aircraftTypeIATACode_equal", IATACode);
		List<AircraftType> airlineList = aircraftTypeDao.findBy(filters, null, -1, -1);
		return airlineList;
	}

	/*
	 * 查询全部机型
	 */
	@Override
	public List<AircraftType> findAllAircraftType() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<AircraftType> airlineList = aircraftTypeDao.findBy(filters, null, -1, -1);
		return airlineList;
	}
	
	@Resource
	private ImportAircraftTypeByExcel importAircraftTypeByExcel;
	
	@Override
	public List<AircraftType> importByExcel2003(byte[] bytes) {
		List<AircraftType> aircraftTypeList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			aircraftTypeList = importAircraftTypeByExcel.ImportAircraftTypeByExcel2003(input);
		} catch (IOException e) {
		}
		return aircraftTypeList;
	}

	@Override
	public List<AircraftType> importByExcel2007(byte[] bytes) {
		List<AircraftType> aircraftTypeList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			aircraftTypeList = importAircraftTypeByExcel.ImportAircraftTypeByExcel2007(input);
		} catch (IOException e) {
		}
		return aircraftTypeList;
	}
}
