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

import com.nlia.fqdb.dao.AirlineDao;
import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.service.IAirlineManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportAirlineByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AirlineManager extends AbstractCrudService<Airline, Long>
		implements IAirlineManager {

	@Resource
	private AirlineDao airlineDao;

	@Override
	protected GenericDao<Airline, Long> getDao() {
		return airlineDao;
	}



	/*
	 * 查指定航空公司
	 * 参数：IATACode
	 */
	@Override
	public List<Airline> findByAirlineIATACode(String IATACode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("airlineIATACode_equal", IATACode);
		List<Airline> airlineList = airlineDao.findBy(filters, null, -1, -1);
		return airlineList;
	}
	
	/**
	 * 根据名称查询航空公司
	 */
	public Airline findAirlineByName(String airlineName){
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("airlineName_equal", airlineName);
		List<Airline> airlineList = airlineDao.findBy(filters, null, -1, -1);
		return airlineList.get(0);
	}
	
	/*
	 * 查询全部航空公司
	 */
	@Override
	public List<Airline> findAllAirline() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Airline> airlineList = airlineDao.findBy(filters, null, -1, -1);
		return airlineList;
	}
	
	@Resource
	private ImportAirlineByExcel importAirlineByExcel;
	
	@Override
	public List<Airline> importByExcel2003(byte[] bytes) {
		List<Airline> airlineList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			airlineList = importAirlineByExcel.ImportAirlineByExcel2003(input);
		} catch (IOException e) {
		}
		return airlineList;
	}

	@Override
	public List<Airline> importByExcel2007(byte[] bytes) {
		List<Airline> airlineList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			airlineList = importAirlineByExcel.ImportAirlineByExcel2007(input);
		} catch (IOException e) {
		}
		return airlineList;
	}
	
	//找出数据库的Iatacode和名字关系
	@Override
	public Map<String,String> getIatacodeAndNameRelation() {
		Map<String,String> relation = new HashMap<>();
		List<Airline> airlines = findAllAirline();
		for(Airline airline : airlines){
			relation.put(airline.getAirlineIATACode(), airline.getAirlineName());
		}
		return relation;
	}
}
