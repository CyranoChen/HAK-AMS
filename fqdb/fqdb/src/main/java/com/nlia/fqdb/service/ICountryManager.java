package com.nlia.fqdb.service;

import java.util.List;

import com.nlia.fqdb.entity.base.Country;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface ICountryManager extends
		AbstractBaseService<Country, Long> {


	public List<Country> findByCountryIATACode(String code);

	public List<Country> findAllCountry();
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<Country> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<Country> importByExcel2007(byte[] bytes);
}
