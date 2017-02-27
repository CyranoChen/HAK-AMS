package com.wonders.frame.ams.dao.basic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.wonders.frame.ams.dto.basic.AircraftAirlineAlterationDto;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.vo.basic.AircraftAirlineAlterationVo;
import com.wonders.frame.core.dao.MultiDao;
import com.wonders.frame.core.model.vo.MultiQuerySqlElement;
import com.wonders.frame.core.model.vo.PlaceholderParam;
import com.wonders.frame.core.model.vo.SimplePage;
import org.springframework.stereotype.Repository;

@Repository
public class AircraftAirlineAlterationDaoImpl {
	
	@Resource
	public MultiDao multiDao;
	
	private MultiQuerySqlElement multiQuerySqlElement;
	
	public SimplePage<AircraftAirlineAlterationVo> findbyPage(AircraftAirlineAlterationDto aaaform,Integer pageNum, Integer pageSize){
		String sql = "select "
						+"a.id a_id,"
						+"a.aircraft_registration a_aircraftRegistration,"  
						+"a.current_airline_id a_currentAirline,"
						+"a.current_subairline_id a_currentSubairline,"
						+"a.original_airline_id a_originalAirline,"
						+"a.original_subairline_id a_originalSubairline,"
						+"a.create_time a_createTime,"
						+"a.create_user a_createUser,"
						+"a.end_date a_endDate,"
						+"a.modify_time a_modifyTime,"
						+"a.modify_user a_modifyUser,"
						+"a.remove_flag a_removeFlag,"
						+"a.start_date a_startDate,"
						+"a.valid_flag a_validFlag, "
						+"a.remark a_remark,"
						+"a.airline_of_flight a_airlineOfFlight,"
						+"a.aircraft_airline a_aircraftAirline,"
						+"a.aircraft_description a_aircraftDescription,"
						+"a.aricraft_engine_number a_aircraftEngineNumber,"
						+"a.aircraft_height a_aircraftHeight,"
						+"a.aircraft_landing_weight a_aircraftLandingWeight,"
						+"a.aircraft_lenght a_aircraftLength,"
						+"a.aircraft_noise_category a_aircraftNoiseCategory,"
						+"a.aircraft_payload a_aircraftPayload,"
						+"a.aircraft_seat_capacity a_aircraftSeatCapacity,"
						+"a.aircraft_takeoff_weight a_aircraftTakeoffWeight,"
						+"a.aircraft_type a_aircraftType,"
						+"a.aircraft_width a_aircraftWidth,"
						+"a.basic_data_id a_basicDataID,"
						+"a.aircraft_leasing_airline a_aircraftLeasingAirline,"
						+"a.aircraft_type_iata_code a_aircraftTypeIATACode,"
						+"a.aircraft_type_icao_code a_aircraftTypeICAOCode,"
						+"a.is_wide_or_narrow a_isWideOrNarrow,"
						+"a.is_high_density a_isHighDensity,"
						+"a.is_packaging_facility a_isPackagingFacility"
						+ " from aircraft_airline_alteration a"
//				+ "b.AIRLINE_NAME originalAirline,c.AIRLINE_NAME originalSubairline,d.AIRLINE_NAME currentAirline,e.AIRLINE_NAME currentSubairline from AIRCRAFT_AIRLINE_ALTERATION a"
									+" left join base_airline b on a.original_airline_id = b.id"
                                    +" left join base_airline c on a.original_subairline_id = c.id"
                                    +" left join base_airline d on a.current_airline_id = d.id"
                                    +" left join base_airline e on a.current_subairline_id = e.id"
				+ " where a.remove_flag = 1 and 1=1";

		List<PlaceholderParam> paramList = new ArrayList<PlaceholderParam>();
		if(null != aaaform && null == aaaform.getId()){
			if(Chk.spaceCheck(aaaform.getAircraftRegistration())){
				sql +=" and a.AIRCRAFT_REGISTRATION like ?";
				paramList.add(new PlaceholderParam("%" +aaaform.getAircraftRegistration()+ "%",String.class));
			}
			if(Chk.spaceCheck(aaaform.getCurrentAirline())){
				sql +=" and d.AIRLINE_NAME like ?";
				paramList.add(new PlaceholderParam("%" +aaaform.getCurrentAirline().toString() + "%",String.class));
			}
			if(Chk.spaceCheck(aaaform.getCurrentSubAirline())){
				sql +=" and e.AIRLINE_NAME like ?";
				paramList.add(new PlaceholderParam("%" + aaaform.getCurrentSubAirline().toString() +"%",String.class));
			}
			if(Chk.spaceCheck(aaaform.getAirlineOfFlight())){
				sql +=" and a.AIRLINE_OF_FLIGHT like ?";
				paramList.add(new PlaceholderParam("%" + aaaform.getAirlineOfFlight().toUpperCase()+ "%",String.class));
			}
			if(Chk.spaceCheck(aaaform.getAircraftTypeICAOCode())){
				sql +=" and a.AIRCRAFT_TYPE_ICAO_CODE like ?";
				paramList.add(new PlaceholderParam("%" + aaaform.getAircraftTypeICAOCode().toUpperCase() + "%",String.class));
			}
			if(null != aaaform.getAircraftSeatCapacity()){
				sql +=" and a.AIRCRAFT_SEAT_CAPACITY = ?";
				paramList.add(new PlaceholderParam(aaaform.getAircraftSeatCapacity().toString(),Long.class));
			}
			if(null != aaaform.getAircraftTakeoffWeight()){
				sql +=" and a.AIRCRAFT_TAKEOFF_WEIGHT = ?";
				paramList.add(new PlaceholderParam(aaaform.getAircraftTakeoffWeight().toString(),Long.class));
			}
			if(null != aaaform.getAircraftPayload()){
				sql +=" and a.AIRCRAFT_PAYLOAD = ?";
				paramList.add(new PlaceholderParam(aaaform.getAircraftPayload().toString(),Long.class));
			}
			if(Chk.spaceCheck(aaaform.getIsWideOrNarrow())){
				sql +=" and a.IS_WIDE_OR_NARROW = ?";
				paramList.add(new PlaceholderParam(aaaform.getIsWideOrNarrow(),String.class));
			}
			if(Chk.spaceCheck(aaaform.getIsHighDensity())){
				sql +=" and a.IS_HIGH_DENSITY = ?";
				paramList.add(new PlaceholderParam(aaaform.getIsHighDensity(),String.class));
			}
			if(Chk.spaceCheck(aaaform.getValidTime())){
				sql += " and a.START_DATE <= to_date(?,'yyyy-MM-dd') and a.END_DATE >= to_date(?,'yyyy-MM-dd')";
				paramList.add(new PlaceholderParam(aaaform.getValidTime(),String.class));
				paramList.add(new PlaceholderParam(aaaform.getValidTime(),String.class));
			}
		}else if(null != aaaform && null != aaaform.getId()){
			sql +=" and a.ID = ?";
			paramList.add(new PlaceholderParam(aaaform.getId().toString(),Long.class));
		}
		sql +=" order by a.aircraft_registration asc";

		System.out.println(sql);
		HashMap<String,Class<?>> entityMap=new HashMap<String,Class<?>>();
		multiQuerySqlElement=new MultiQuerySqlElement(sql,paramList,entityMap);

		try {
			SimplePage<AircraftAirlineAlterationVo> rs=multiDao.findByPage(multiQuerySqlElement, pageNum, pageSize, AircraftAirlineAlterationVo.class);
			
//			System.out.println(JacksonMapper.toJson(rs));
			
			return rs;
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
	}
}
