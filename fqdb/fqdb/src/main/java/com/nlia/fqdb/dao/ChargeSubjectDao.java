package com.nlia.fqdb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.ChargeSubject;
import com.nlia.fqdb.entity.ChargeSubject.CHARGE_PROPERTY;
import com.nlia.fqdb.entity.ChargeType;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class ChargeSubjectDao extends GenericDao<ChargeSubject, Long>{



    public List<ChargeSubject> findWithOutRuleGroup() {
        StringBuffer sb = new StringBuffer();
        sb.append("select t.id,t.name,t.description,t.chargeProperty,t.formula,t.parameter,t.currency,t.currencyUnit,t.chargeType from ChargeSubject t  where t.removeFlag='1'");
       
       
        Query query = entityManager.createQuery(sb.toString());
        List<Object> list = query.getResultList();
        List<ChargeSubject> result = new ArrayList<ChargeSubject>();
        for(int i = 0 ; i < list.size() ; i ++){
            ChargeSubject cs = new ChargeSubject();
            Object [] o = (Object[]) list.get(i);
            cs.setId(Long.parseLong(o[0].toString()));
            cs.setName(o[1].toString());
//            cs.setDescription(o[2].toString());
            
            cs.setChargeType((ChargeType)o[8]);
            result.add(cs);
        }
        return result;
    }

}
