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

import com.nlia.fqdb.dao.BaggageReclaimDao;
import com.nlia.fqdb.entity.base.BaggageReclaim;
import com.nlia.fqdb.service.IBaggageReclaimManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportBaggageReclaimByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class BaggageReclaimManager extends AbstractCrudService<BaggageReclaim, Long>
		implements IBaggageReclaimManager {
	@Resource
	private BaggageReclaimDao baggageReclaimCodeDao;

	@Override
	protected GenericDao<BaggageReclaim, Long> getDao() {
		return baggageReclaimCodeDao;
	}

	/*
	 * 查指定行李提取盘：aircraftRegistration
	 */
	@Override
	public List<BaggageReclaim> findByBaggageReclaimCode(String baggageReclaimCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("baggageReclaimCode_equal", baggageReclaimCode);
		List<BaggageReclaim> airlineList = baggageReclaimCodeDao.findBy(filters, null, -1, -1);
		return airlineList;
	}

	/*
	 * 查询全部飞机
	 */
	@Override
	public List<BaggageReclaim> findAllBaggageReclaim() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<BaggageReclaim> airlineList = baggageReclaimCodeDao.findBy(filters, null, -1, -1);
		return airlineList;
	}
	
	@Resource
	private ImportBaggageReclaimByExcel importBaggageReclaimByExcel;
	
	@Override
	public List<BaggageReclaim> importByExcel2003(byte[] bytes) {
		List<BaggageReclaim> baggageReclaimList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			baggageReclaimList = importBaggageReclaimByExcel.ImportBaggageReclaimByExcel2003(input);
		} catch (IOException e) {
		}
		return baggageReclaimList;
	}

	@Override
	public List<BaggageReclaim> importByExcel2007(byte[] bytes) {
		List<BaggageReclaim> baggageReclaimList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			baggageReclaimList = importBaggageReclaimByExcel.ImportBaggageReclaimByExcel2007(input);
		} catch (IOException e) {
		}
		return baggageReclaimList;
	}
}
