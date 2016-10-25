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

import com.nlia.fqdb.dao.HandlerDao;
import com.nlia.fqdb.entity.base.Handler;
import com.nlia.fqdb.service.IHandlerManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportHandlerByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class HandlerManager extends AbstractCrudService<Handler, Long>
		implements IHandlerManager {

	@Resource
	private HandlerDao handlerDao;

	@Override
	protected GenericDao<Handler, Long> getDao() {
		return handlerDao;
	}

	/*
	 * 查指定代理
	 * 参数：handlerCode
	 */
	@Override
	public List<Handler> findByHandlerCode(String handlerCode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("handlerCode_equal", handlerCode);
		List<Handler> handlerList = handlerDao.findBy(filters, null, -1, -1);
		return handlerList;
	}

	/*
	 * 查询全部
	 */
	@Override
	public List<Handler> findAllHandler() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Handler> handlerList = handlerDao.findBy(filters, null, -1, -1);
		return handlerList;
	}
	
	@Resource
	private ImportHandlerByExcel importHandlerByExcel;
	
	@Override
	public List<Handler> importByExcel2003(byte[] bytes) {
		List<Handler> handlerList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			handlerList = importHandlerByExcel.ImportHandlerByExcel2003(input);
		} catch (IOException e) {
		}
		return handlerList;
	}

	@Override
	public List<Handler> importByExcel2007(byte[] bytes) {
		List<Handler> handlerList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			handlerList = importHandlerByExcel.ImportHandlerByExcel2007(input);
		} catch (IOException e) {
		}
		return handlerList;
	}
}
