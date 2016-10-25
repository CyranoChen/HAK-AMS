package com.nlia.fqdb.service;

import java.util.Date;
import java.util.List;

import com.nlia.fqdb.entity.SyntheticChargeTermDBView;

public interface ISyntheticChargeTermDBViewManager {
	public List<SyntheticChargeTermDBView> findBySql(Date start, Date end,String airlineName,
			String flightCountryType,String aFlightIdentity,String dFlightIdentity,String flightdirection);
}
