package com.nlia.fqdb.service;

import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.base.Airline;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAirlineManager extends
		AbstractBaseService<Airline, Long> {

	

	/*
	 * 查指定航空公司
	 * 参数：IATACode
	 */
	public List<Airline> findByAirlineIATACode(String IATACode);

	/*
	 * 查询全部航空公司
	 */
	public List<Airline> findAllAirline();
	
	/**
	 * 根据名称查询航空公司
	 */
	public Airline findAirlineByName(String airlineName);
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Airline> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Airline> importByExcel2007(byte[] bytes);
	
	/**
	 * 找出数据库的Iatacode和名字关系
	 * @return
	 */
	public Map<String, String> getIatacodeAndNameRelation();
}
