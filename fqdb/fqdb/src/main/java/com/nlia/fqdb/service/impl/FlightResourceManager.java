package com.nlia.fqdb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.FlightResourceDao;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.service.IFlightResourceManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class FlightResourceManager extends AbstractCrudService<FlightResource, Long>implements IFlightResourceManager {
	
	
	@Resource
	private FlightResourceDao flightResourceDao;
	
	@Override
	protected GenericDao<FlightResource, Long> getDao() {
		return flightResourceDao;
	}

}
