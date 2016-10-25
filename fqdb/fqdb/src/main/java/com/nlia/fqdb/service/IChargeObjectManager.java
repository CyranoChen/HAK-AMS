package com.nlia.fqdb.service;

import java.util.List;
import java.util.Map;

import com.nlia.fqdb.entity.ChargeObject;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IChargeObjectManager extends
		AbstractBaseService<ChargeObject, Long> {

	/**
	 * 新增收费对象
	 * 
	 * @param ChargeObject
	 * @return
	 */
	public ChargeObject addchargeObject(ChargeObject chargeObject);

	/**
	 * 修改收费对象
	 * 
	 * @param ChargeObject
	 * @return
	 */
	public ChargeObject modifychargeObject(ChargeObject chargeObject);

	/**
	 * 删除收费对象
	 * 
	 * @param ChargeObject
	 * @return
	 */
	public ChargeObject removechargeObject(ChargeObject chargeObject);

	/**
	 * 查询收费对象
	 * 
	 * @param filters
	 * @param sorts
	 * @return
	 */
	public List<ChargeObject> queryChargeObject(Map<String, Object> filters,
			Map<String, String> sorts);

}
