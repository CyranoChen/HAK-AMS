package com.nlia.fqdb.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class AircraftAirlineAlterationDao extends GenericDao<AircraftAirlineAlteration, Long>{

    /**
     * 删除所有aircraft_airline_alteration
     **/
    public void removeAll(){
        StringBuffer hql = new StringBuffer();
        hql.append("update aircraft_airline_alteration set remove_flag='0'");
        Query query = entityManager.createNativeQuery(hql.toString());
        query.executeUpdate();
    }
}
