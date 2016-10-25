package com.nlia.fqdb.service;

import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Airport;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAirportManager extends AbstractBaseService<Airport, Long> {

	/*
	 * 查询指定机场 参数：IATACode
	 */
	public List<Airport> findByAirportIATACode(String IATACode);

	/*
	 * 查询所有机场
	 */
	public List<Airport> findAllAirport();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Airport> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Airport> importByExcel2007(byte[] bytes);

	/**
	 * 找出数据库的Iatacode和名字关系
	 * @return
	 */
	public Map<String,String> getIatacodeAndNameRelation();
	
	/**
	 * 找出数据库的Iatacode和名字简称关系
	 * @return
	 */
	public Map<String,String> getIatacodeAndShortNameRelation();
}
