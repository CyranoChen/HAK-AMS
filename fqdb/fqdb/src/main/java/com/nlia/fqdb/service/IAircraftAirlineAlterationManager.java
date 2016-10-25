package com.nlia.fqdb.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IAircraftAirlineAlterationManager extends AbstractBaseService<AircraftAirlineAlteration, Long>{
	public AircraftAirlineAlteration getAircraftAirlineAlteration(FlightBase flightBase,String registeration);
	public Object[] addAircraftAirlineAlteration(AircraftAirlineAlteration aircraftAirlineAlteration);
	public Object[] modifyAircraftAirlineAlteration(AircraftAirlineAlteration aircraftAirlineAlteration);
	public Map<String, Object> importAircraftAirlineAlterationDataByExcel2003(byte[] bytes) throws IOException;
	public Map<String, Object> importAircraftAirlineAlterationDataByExcel2007(byte[] bytes) throws IOException;


	//导入CAACSC格式,即清算中心格式
	public Map<String, Object> importAircraftAirlineAlterationDataFromCAACSCExcel2003(byte[] bytes) throws IOException;
    public Map<String, Object> importAircraftAirlineAlterationDataFromCAACSCExcel2007(byte[] bytes) throws IOException;
}
