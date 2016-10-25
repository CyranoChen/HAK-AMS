package com.nlia.fqdb.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.SyntheticChargeTermDBViewDao;
import com.nlia.fqdb.entity.SyntheticChargeTermDBView;
import com.nlia.fqdb.service.ISyntheticChargeTermDBViewManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;
@Service
public class SyntheticChargeTermDBViewManager extends AbstractCrudService<SyntheticChargeTermDBView, Long> implements ISyntheticChargeTermDBViewManager{

	@Resource
	private SyntheticChargeTermDBViewDao syntheticChargeTermDBViewDao;
	
	@Override
	protected GenericDao<SyntheticChargeTermDBView, Long> getDao() {
		
		return syntheticChargeTermDBViewDao;
	}
	
	public List<SyntheticChargeTermDBView> findBySql(Date start, Date end,String airlineName,
			String flightCountryType,String aFlightIdentity,String dFlightIdentity,String flightDirection){
		return syntheticChargeTermDBViewDao.findBySql(start, end, airlineName, flightCountryType, aFlightIdentity, dFlightIdentity,flightDirection);
	}
}

