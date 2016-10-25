package com.nlia.fqdb.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.Discount;
import com.nlia.fqdb.entity.base.Airline;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class DiscountDao extends GenericDao<Discount, Long>{
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void updateAAAIdById(Map<Long, Long> delAAAIds) {
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        String sql = "update AIRCRAFT_DISCOUNT set AAA_ID=? where AAA_ID=?";
        for (Long key : delAAAIds.keySet()) {
            batchArgs.add(new Object[] { 
                    delAAAIds.get(key), 
                    key
            });
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);

    }

    public List<Discount> findByAAAIds(List<Long> ids) {
        StringBuffer sb = new StringBuffer();
//        sb.append("from Discount t  where t.removeFlag='1' and");
        sb.append("from Discount t where ");
        sb.append(" (1<>1 ");
        for(Long id : ids){
            sb.append(" or t.aircraftAirlineAlterationId = ").append(id);
        }
        //默认升序 代码中认为LONG型 NULL值最小，但ORACLE中认为NULL最大，故对chargeSubjectId特殊处理一下
        sb.append(") order by t.aircraftAirlineAlterationId asc ,t.chargeTypeId asc,NVL(t.chargeSubjectId,'-1') asc");
        Query query = entityManager.createQuery(sb.toString());
        return query.getResultList();
    }
}
