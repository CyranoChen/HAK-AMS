package com.nlia.fqdb.service;

import java.util.Date;
import java.util.List;

import com.nlia.fqdb.entity.ChargeTermDBView;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IChargeTermDBViewManager extends AbstractBaseService<ChargeTermDBView, Long>{
	public List<ChargeTermDBView> findBySql(Date start, Date end, String chargeTermName,String airlineName,String chargeTypeName);
	public List<ChargeTermDBView> findBySqlWithId(Long id);
}
