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

import com.nlia.fqdb.dao.AirportDao;
import com.nlia.fqdb.entity.base.Airport;
import com.nlia.fqdb.service.IAirportManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportAirportByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AirportManager extends AbstractCrudService<Airport, Long>
		implements IAirportManager {

	@Resource
	private AirportDao airportDao;

	@Override
	protected GenericDao<Airport, Long> getDao() {
		return airportDao;
	}

	/*
	 * 查询指定机场 
	 * 参数：IATACode
	 */
	@Override
	public List<Airport> findByAirportIATACode(String IATACode) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("airportIATACode_equal", IATACode);
		List<Airport> airportList = airportDao.findBy(filters, null, -1, -1);
		return airportList;
	}
	
	/*
	 * 查询所有机场
	 */
	@Override
	public List<Airport> findAllAirport(){
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<Airport> airportList = airportDao.findBy(filters, null, -1, -1);
		return airportList;
	}
	
	@Resource
	private ImportAirportByExcel importAirportByExcel;
	
	@Override
	public List<Airport> importByExcel2003(byte[] bytes) {
		List<Airport> airportList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			airportList = importAirportByExcel.ImportAirportByExcel2003(input);
		} catch (IOException e) {
		}
		return airportList;
	}

	@Override
	public List<Airport> importByExcel2007(byte[] bytes) {
		List<Airport> airportList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			airportList = importAirportByExcel.ImportAirportByExcel2007(input);
		} catch (IOException e) {
		}
		return airportList;
	}
	
	//找出数据库的Iatacode和名字关系
	@Override
	public Map<String,String> getIatacodeAndNameRelation() {
		Map<String,String> relation = new HashMap<>();
		List<Airport> airlines = findAllAirport();
		for(Airport airport : airlines){
			relation.put(airport.getAirportIATACode(), airport.getAirportCity());
		}
		return relation;
	}
	
	//找出数据库的Iatacode和名字简称关系
		@Override
		public Map<String,String> getIatacodeAndShortNameRelation() {
			Map<String,String> relation = new HashMap<>();
			List<Airport> airlines = findAllAirport();
			for(Airport airport : airlines){
				relation.put(airport.getAirportIATACode(), airport.getAirportRoutingName());
			}
			return relation;
		}
}
