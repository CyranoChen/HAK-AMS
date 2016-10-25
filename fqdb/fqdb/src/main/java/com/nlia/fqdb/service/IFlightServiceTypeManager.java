package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.FlightServiceType;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IFlightServiceTypeManager extends
		AbstractBaseService<FlightServiceType, Long> {


	public List<FlightServiceType> findByServiceTypeCode(String code);

	public List<FlightServiceType> findAllFlightServiceType();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<FlightServiceType> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<FlightServiceType> importByExcel2007(byte[] bytes);
}
