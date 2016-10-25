package com.nlia.fqdb.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.SyntheticChargeTermDao;
import com.nlia.fqdb.entity.SyntheticChargeTerm;
import com.nlia.fqdb.service.ISyntheticChargeTermManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class SyntheticChargeTermManager extends AbstractCrudService<SyntheticChargeTerm, Long> implements ISyntheticChargeTermManager{

	@Resource
	private SyntheticChargeTermDao syntheticChargeTermDao;
	
	@Override
	protected GenericDao<SyntheticChargeTerm, Long> getDao() {
		return syntheticChargeTermDao;
	}

}
