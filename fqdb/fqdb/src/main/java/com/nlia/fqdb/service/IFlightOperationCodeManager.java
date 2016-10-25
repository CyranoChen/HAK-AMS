package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.FlightOperationCode;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IFlightOperationCodeManager extends
		AbstractBaseService<FlightOperationCode, Long> {


	public List<FlightOperationCode> findByFlightOperationCode(String code );

	public List<FlightOperationCode> findAllFlightOperationCode();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<FlightOperationCode> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<FlightOperationCode> importByExcel2007(byte[] bytes);
}
