package com.nlia.fqdb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.dao.FlightDataDao;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.service.IFlightDataManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class FlightDataManager extends AbstractCrudService<FlightData, Long>implements IFlightDataManager {
	
	
	@Resource
	private FlightDataDao flightDataDao;

	
	DataHandler<FlightBase> dataHandler = new DataHandler<>();
	
	@Override
	protected GenericDao<FlightData, Long> getDao() {
		return flightDataDao;
	}

}
