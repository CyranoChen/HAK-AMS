package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.FlightOperationCodeDao;
import com.nlia.fqdb.entity.base.FlightOperationCode;
import com.nlia.fqdb.service.IFlightOperationCodeManager;
import com.nlia.fqdb.util.CommonData;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class FlightOperationCodeManager extends AbstractCrudService<FlightOperationCode, Long>
		implements IFlightOperationCodeManager {
	@Resource
	private FlightOperationCodeDao flightOperationCodeDao;

	@Override
	protected GenericDao<FlightOperationCode, Long> getDao() {
		return flightOperationCodeDao;
	}

	@Override
	public List<FlightOperationCode> findByFlightOperationCode(String code) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("flightOperationCode_equal", code);
		List<FlightOperationCode> resultList = flightOperationCodeDao.findBy(filters, null, -1, -1);
		return resultList;
	}

	@Override
	public List<FlightOperationCode> findAllFlightOperationCode() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<FlightOperationCode> resultList = flightOperationCodeDao.findBy(filters, null, -1, -1);
		return resultList;
	}

	@Override
	public List<FlightOperationCode> importByExcel2003(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlightOperationCode> importByExcel2007(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

}
