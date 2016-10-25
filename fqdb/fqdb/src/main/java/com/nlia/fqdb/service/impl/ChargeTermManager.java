package com.nlia.fqdb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.ChargeTermDao;
import com.nlia.fqdb.entity.ChargeSubject;
import com.nlia.fqdb.entity.ChargeTarget;
import com.nlia.fqdb.entity.ChargeTerm;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.service.IChargeTermManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class ChargeTermManager extends AbstractCrudService<ChargeTerm, Long> implements IChargeTermManager {

	@Resource
	private ChargeTermDao chargeTermDao;

	@Resource
	private ChargeTermManager chargeTermManager;

	@Override
	protected GenericDao<ChargeTerm, Long> getDao() {
		return chargeTermDao;
	}

	
	public ChargeTerm addChargeTerm(ChargeTarget chargeTarget, FlightMateInfo flightMateInfo, ChargeSubject chargeSubject, Double discountFee, String expression) {
	    ChargeTerm chargeTerm=null;
	    if (discountFee == null) {
            discountFee = 0.00;
        }
        List<ChargeTerm> chargeTerms;

        Map<String, Object> filters = new HashMap<>();
        filters.put("flightMateInfo_equal", flightMateInfo);
        filters.put("chargeTarget_equal", chargeTarget);
        filters.put("chargeSubject_equal", chargeSubject);
        filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
        chargeTerms = chargeTermManager.findBy(filters);

        if (chargeTerms == null || chargeTerms.size() == 0) {
            chargeTerm = new ChargeTerm();
        } else {
            chargeTerm = chargeTerms.get(0);
        }
        chargeTerm.setChargeSubject(chargeSubject);
        chargeTerm.setChargeTarget(chargeTarget);
        chargeTerm.setChargeType(chargeSubject.getChargeType());
        chargeTerm.setFlightMateInfo(flightMateInfo);
        chargeTerm.setCurrency(chargeSubject.getCurrency());
        chargeTerm.setFee(discountFee);
        chargeTerm.setRemark(expression);
        chargeTerm
                .setName((flightMateInfo.getFlightDirection().equals("0")?flightMateInfo.getAFlightIdentity() + "_A" :flightMateInfo.getDFlightIdentity() + "_D")+ "_" +DateUtils.Date2String(flightMateInfo.getExecuteTime(),"yyyy-MM-dd") + "_"
                        + chargeSubject.getChargeType().getName() +"_"+chargeSubject.getName());
        chargeTerm.setCreateTime(DateUtils.Date2Date(new Date()));
        chargeTerm.setCreateUser("admin");
        chargeTerm.setCurrencyUnit(chargeSubject.getCurrencyUnit());
        chargeTerm.setRemoveFlag("1");

        chargeTerm = this.save(chargeTerm);
        return chargeTerm;
    }


}
