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

import com.nlia.fqdb.dao.RunwayDao;
import com.nlia.fqdb.entity.base.Runway;
import com.nlia.fqdb.service.IRunwayManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportRunwayByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class RunwayManager extends AbstractCrudService<Runway, Long>
		implements IRunwayManager {

	@Resource
	private RunwayDao runwayDao;

	@Override
	protected GenericDao<Runway, Long> getDao() {
		return runwayDao;
	}

	/*
	 * 查指定
	 * 参数：runwayCode
	 */
	@Override
	public List<Runway> findByRunwayCode(String runwayCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("runwayCode_equal", runwayCode);
		List<Runway> runwayList = runwayDao.findBy(filters, null, -1, -1);
		return runwayList;
	}

	/*
	 * 查询
	 */
	@Override
	public List<Runway> findAllRunway() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Runway> runwayList = runwayDao.findBy(filters, null, -1, -1);
		return runwayList;
	}
	
	@Resource
	private ImportRunwayByExcel importRunwayByExcel;
	
	@Override
	public List<Runway> importByExcel2003(byte[] bytes) {
		List<Runway> runwayList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			runwayList = importRunwayByExcel.ImportRunwayByExcel2003(input);
		} catch (IOException e) {
		}
		return runwayList;
	}

	@Override
	public List<Runway> importByExcel2007(byte[] bytes) {
		List<Runway> runwayList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			runwayList = importRunwayByExcel.ImportRunwayByExcel2007(input);
		} catch (IOException e) {
		}
		return runwayList;
	}
}
