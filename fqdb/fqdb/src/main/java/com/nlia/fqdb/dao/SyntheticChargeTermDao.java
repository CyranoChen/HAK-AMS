package com.nlia.fqdb.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.SyntheticChargeTerm;
import com.wonders.aiis.core.dao.GenericDao;

@Repository
public class SyntheticChargeTermDao extends GenericDao<SyntheticChargeTerm, Long>{

	@SuppressWarnings("unchecked")
	public List<SyntheticChargeTerm> findBy(Date scheduleDate , String flightNumber , String airlineICAOCode , String registeration){
		String hql = "from SyntheticChargeTerm sct where sct.removeFlag = 1 ";
		if (registeration != null && !"".equals(registeration)) {
			hql += " and (sct.flightMateInfo.aregisteration like '%:registeration%' or sct.flightMateInfo.dregisteration like '%:registeration%' )";
		}
		if (flightNumber != null && !"".equals(flightNumber)) {
			hql += " and (sct.flightMateInfo.aFlightIdentity like '%:flightNumber%' or " +
					"sct.flightMateInfo.dFlightIdentity like '%:flightNumber%')";
		}
		if (airlineICAOCode != null && !"".equals(airlineICAOCode)) {
			hql += " and sct.flightMateInfo.airline.airlineICAOCode like '%:airlineICAOCode%' ";
		}
		Query query;
		if ("".equals(registeration) && !"".equals(flightNumber) && !"".equals(airlineICAOCode)) {
			query = entityManager.createQuery(hql).
					setParameter("flightNumber", flightNumber).
					setParameter("flightNumber", flightNumber).
					setParameter("airlineICAOCode", airlineICAOCode);
		}else if(!"".equals(registeration) && "".equals(flightNumber) && !"".equals(airlineICAOCode)){
			query = entityManager.createQuery(hql).
					setParameter("registeration", registeration).
					setParameter("registeration", registeration).
					setParameter("airlineICAOCode", airlineICAOCode).
					setParameter("airlineICAOCode", airlineICAOCode);
		}else if(!"".equals(registeration) && !"".equals(flightNumber) && "".equals(airlineICAOCode)){
			query = entityManager.createQuery(hql).
					setParameter("registeration", registeration).
					setParameter("registeration", registeration).
					setParameter("flightNumber", flightNumber).
					setParameter("flightNumber", flightNumber);
		}else if("".equals(registeration) && "".equals(flightNumber) && !"".equals(airlineICAOCode)){
			query = entityManager.createQuery(hql).
					setParameter("airlineICAOCode", airlineICAOCode);
		}else if("".equals(registeration) && !"".equals(flightNumber) && "".equals(airlineICAOCode)){
			query = entityManager.createQuery(hql).
					setParameter("flightNumber", flightNumber).
					setParameter("flightNumber", flightNumber);
		}else if(!"".equals(registeration) && "".equals(flightNumber) && "".equals(airlineICAOCode)){
			query = entityManager.createQuery(hql).
					setParameter("registeration", registeration).
					setParameter("registeration", registeration);
		}else if(!"".equals(registeration) && !"".equals(flightNumber) && !"".equals(airlineICAOCode)){
			query = entityManager.createQuery(hql).
					setParameter("registeration", registeration).
					setParameter("registeration", registeration).
					setParameter("flightNumber", flightNumber).
					setParameter("flightNumber", flightNumber).
					setParameter("airlineICAOCode", airlineICAOCode);
		}else {
			query = entityManager.createQuery(hql);
		}
		
		return query.getResultList();
	}
}
