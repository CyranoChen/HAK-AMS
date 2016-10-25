package com.nlia.fqdb.service;

import java.util.List;
import java.util.Map;


import com.nlia.fqdb.entity.ChargeUnit;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IChargeUnitManager extends
		AbstractBaseService<ChargeUnit, Long> {

	/**
	 * 新增收费项目
	 * 
	 * @param chargeUnit
	 * @return
	 */
	public ChargeUnit addChargeUnit(ChargeUnit chargeUnit);

	/**
	 * 修改收费项目
	 * 
	 * @param chargeUnit
	 * @return
	 */
	public ChargeUnit modifyChargeUnit(ChargeUnit chargeUnit);

	/**
	 * 删除收费项目
	 * 
	 * @param chargeUnit
	 * @return
	 */
	public ChargeUnit removeChargeUnit(ChargeUnit chargeUnit);

	/**
	 * 查询收费项目
	 * 
	 * @param filters
	 * @param sorts
	 * @return
	 */
	public List<ChargeUnit> queryChargeUnit(Map<String, Object> filters,
			Map<String, String> sorts);
	
	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return
	 */
	public List<ChargeUnit> importByExcel2003(byte[] bytes);
	
	/**
	 * 从excel2007中导入数据到表格
	 * @param bytes
	 * @return
	 */
	public List<ChargeUnit> importByExcel2007(byte[] bytes);
	
}
