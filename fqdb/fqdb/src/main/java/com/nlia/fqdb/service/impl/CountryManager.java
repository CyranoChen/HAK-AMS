package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.CountryDao;
import com.nlia.fqdb.entity.base.Country;
import com.nlia.fqdb.service.ICountryManager;
import com.nlia.fqdb.util.CommonData;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class CountryManager extends AbstractCrudService<Country, Long>
		implements ICountryManager {
	@Resource
	private CountryDao countryDao;

	@Override
	protected GenericDao<Country, Long> getDao() {
		return countryDao;
	}

	@Override
	public List<Country> findByCountryIATACode(String code) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("countryIATACode_equal", code);
		List<Country> resultList = countryDao.findBy(filters, null, -1, -1);
		return resultList;
	}

	@Override
	public List<Country> findAllCountry() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Country> resultList = countryDao.findBy(filters, null, -1, -1);
		return resultList;
	}

	@Override
	public List<Country> importByExcel2003(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Country> importByExcel2007(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

}
