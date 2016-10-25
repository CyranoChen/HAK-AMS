package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.PassengerClass;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IPassengerClassManager extends
		AbstractBaseService<PassengerClass, Long> {

	

	/*
	 * 查指定
	 * 参数：passengerClassCode
	 */
	public List<PassengerClass> findByPassengerClassCode(String passengerClassCode);

	/*
	 * 查询全部
	 */
	public List<PassengerClass> findAllPassengerClass();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<PassengerClass> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<PassengerClass> importByExcel2007(byte[] bytes);
}
