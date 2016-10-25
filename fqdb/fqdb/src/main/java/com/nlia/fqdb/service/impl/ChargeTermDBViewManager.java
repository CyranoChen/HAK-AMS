package com.nlia.fqdb.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.fqdb.dao.ChargeTermDBViewDao;
import com.nlia.fqdb.dao.ChargeTermDao;
import com.nlia.fqdb.entity.ChargeTerm;
import com.nlia.fqdb.entity.ChargeTermDBView;
import com.nlia.fqdb.service.IChargeTermDBViewManager;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;
@Service
public class ChargeTermDBViewManager extends AbstractCrudService<ChargeTermDBView, Long> implements IChargeTermDBViewManager{

	@Resource
	private ChargeTermDBViewDao chargeTermDBViewDao;
	
	@Resource
	private ChargeTermDao chargeTermDao;
	
	@Resource
	private ChargeTermManager chargeTermManager;
	
	@Transactional
	public List<ChargeTermDBView> findBySql(Date start, Date end, String chargeTermName,String airlineName,String chargeTypeName){
		return chargeTermDBViewDao.findBySql(start, end, chargeTermName, airlineName, chargeTypeName);
	}
	
	public List<ChargeTermDBView> findBySqlWithId(Long id){
		return chargeTermDBViewDao.findBySqlWithId(id);
	}

	@Override
	protected GenericDao<ChargeTermDBView, Long> getDao() {
		
		return chargeTermDBViewDao;
	}
	
	@Override
	public Collection<ChargeTermDBView> remove(Collection<ChargeTermDBView> chargeTermDBViews) {
		List<ChargeTermDBView> result = new ArrayList<>(chargeTermDBViews.size());
		if (chargeTermDBViews != null && !chargeTermDBViews.isEmpty()) {
			for (ChargeTermDBView chargeTermDBView : chargeTermDBViews) {
				try {
					ChargeTerm ct = chargeTermDao.find(chargeTermDBView.getId());
					MethodUtils.invokeMethod(ct, "setRemoveFlag", "0");
					ChargeTerm ctRtn = chargeTermManager.save(ct);
					ChargeTermDBView ctDBView = chargeTermDBViewDao.find(ctRtn.getId());
					result.add(ctDBView);
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
