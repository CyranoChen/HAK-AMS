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

import com.nlia.fqdb.dao.TaxiwayDao;
import com.nlia.fqdb.entity.base.Taxiway;
import com.nlia.fqdb.service.ITaxiwayManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportTaxiwayByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class TaxiwayManager extends AbstractCrudService<Taxiway, Long>
		implements ITaxiwayManager {

	@Resource
	private TaxiwayDao taxiwayDao;

	@Override
	protected GenericDao<Taxiway, Long> getDao() {
		return taxiwayDao;
	}

	/*
	 * 查指定
	 * 参数：taxiwayCode
	 */
	@Override
	public List<Taxiway> findByTaxiwayCode(String taxiwayCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("taxiwayCode_equal", taxiwayCode);
		List<Taxiway> taxiwayList = taxiwayDao.findBy(filters, null, -1, -1);
		return taxiwayList;
	}

	/*
	 * 查询
	 */
	@Override
	public List<Taxiway> findAllTaxiway() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Taxiway> taxiwayList = taxiwayDao.findBy(filters, null, -1, -1);
		return taxiwayList;
	}
	
	@Resource
	private ImportTaxiwayByExcel importTaxiwayByExcel;
	
	@Override
	public List<Taxiway> importByExcel2003(byte[] bytes) {
		List<Taxiway> taxiwayList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			taxiwayList = importTaxiwayByExcel.ImportTaxiwayByExcel2003(input);
		} catch (IOException e) {
		}
		return taxiwayList;
	}

	@Override
	public List<Taxiway> importByExcel2007(byte[] bytes) {
		List<Taxiway> taxiwayList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			taxiwayList = importTaxiwayByExcel.ImportTaxiwayByExcel2007(input);
		} catch (IOException e) {
		}
		return taxiwayList;
	}
}
