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

import com.nlia.fqdb.dao.BaggageMakeupDao;
import com.nlia.fqdb.entity.base.BaggageMakeup;
import com.nlia.fqdb.service.IBaggageMakeupManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportBaggageMakeupByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class BaggageMakeupManager extends AbstractCrudService<BaggageMakeup, Long>
		implements IBaggageMakeupManager {

	@Resource
	private BaggageMakeupDao baggageMakeupDao;

	@Override
	protected GenericDao<BaggageMakeup, Long> getDao() {
		return baggageMakeupDao;
	}



	/*
	 * 查指定行李分拣盘
	 * 参数：baggageMakeupCode
	 */
	@Override
	public List<BaggageMakeup> findByBaggageMakeupCode(String baggageMakeupCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("baggageMakeupCode_equal", baggageMakeupCode);
		List<BaggageMakeup> airlineList = baggageMakeupDao.findBy(filters, null, -1, -1);
		return airlineList;
	}

	/*
	 * 查询全部行李分拣盘
	 */
	@Override
	public List<BaggageMakeup> findAllBaggageMakeup() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<BaggageMakeup> airlineList = baggageMakeupDao.findBy(filters, null, -1, -1);
		return airlineList;
	}
	
	@Resource
	private ImportBaggageMakeupByExcel importBaggageMakeupByExcel;
	
	@Override
	public List<BaggageMakeup> importByExcel2003(byte[] bytes) {
		List<BaggageMakeup> baggageMakeupList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			baggageMakeupList = importBaggageMakeupByExcel.ImportBaggageMakeupByExcel2003(input);
		} catch (IOException e) {
		}
		return baggageMakeupList;
	}

	@Override
	public List<BaggageMakeup> importByExcel2007(byte[] bytes) {
		List<BaggageMakeup> baggageMakeupList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			baggageMakeupList = importBaggageMakeupByExcel.ImportBaggageMakeupByExcel2007(input);
		} catch (IOException e) {
		}
		return baggageMakeupList;
	}
}
