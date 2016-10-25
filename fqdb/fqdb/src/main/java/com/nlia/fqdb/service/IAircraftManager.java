package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.Aircraft;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAircraftManager extends
		AbstractBaseService<Aircraft, Long> {

	

	/**
	 * 查指定飞机
	 * 参数：IATACode
	 */
	public List<Aircraft> findByAircraftRegistration(String aircraftRegistration );

	/*
	 * 查询全部飞机
	 */
	public List<Aircraft> findAllAircraft();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Aircraft> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Aircraft> importByExcel2007(byte[] bytes);
}
