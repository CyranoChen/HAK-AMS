package com.nlia.fqdb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.FlightServiceTypeDao;
import com.nlia.fqdb.entity.base.FlightServiceType;
import com.nlia.fqdb.service.IFlightServiceTypeManager;
import com.nlia.fqdb.util.CommonData;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class FlightServiceTypeManager extends AbstractCrudService<FlightServiceType, Long>
		implements IFlightServiceTypeManager {
	@Resource
	private FlightServiceTypeDao flightServiceTypeDao;

	@Override
	protected GenericDao<FlightServiceType, Long> getDao() {
		return flightServiceTypeDao;
	}

	@Override
	public List<FlightServiceType> findByServiceTypeCode(String code) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("serviceTypeCode_equal", code);
		List<FlightServiceType> resultList = flightServiceTypeDao.findBy(filters, null, -1, -1);
		return resultList;
	}

	@Override
	public List<FlightServiceType> findAllFlightServiceType() {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal",
				CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<FlightServiceType> resultList = flightServiceTypeDao.findBy(filters, null, -1, -1);
		return resultList;
	}

	@Override
	public List<FlightServiceType> importByExcel2003(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlightServiceType> importByExcel2007(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	Map<String,String> getCAAC2IATAmap(){
	    Map<String,String> CAAC2IATAmap = new HashMap<>();
	    List<FlightServiceType> resultList=this.findAllFlightServiceType();
	    for (FlightServiceType flightServiceType : resultList) {
	        String serviceTypeCode="";
	        if(null==flightServiceType.getFlightCAACServiceType()){
	            serviceTypeCode=flightServiceType.getServiceTypeCode();
	        }else{
	            serviceTypeCode=flightServiceType.getFlightCAACServiceType();
	        }
	        CAAC2IATAmap.put(serviceTypeCode, serviceTypeCode);
	        if(null!=flightServiceType.getFlightIATAServiceType()){
	            CAAC2IATAmap.put(flightServiceType.getFlightIATAServiceType(), serviceTypeCode);
	        }
        }
	    return CAAC2IATAmap;
	}

}
