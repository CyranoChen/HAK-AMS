package com.wonders.frame.ams.service.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SeatsEarningsService {
	
	@Resource
	private JdbcTemplate jt;
	
    public List<Map<String,Object>> query(String startTime,String endTime){
    	String sql = "select t.STAND_NUM,"
    			+ "  t.STAND_DESCRIPTION,"
    			+ "  count(1)/2 as VEHICLES,"
    			+ "  (NVL(SUM(t.ALIGHTING_AND_TAKE_OFF_FEE),0)+"
    			+ "  NVL(SUM(t.PARKING_FEE),0)+"
    			+ "  NVL(SUM(t.NIGHT),0)+"
    			+ "  NVL(SUM(t.PASSENGER_SERVICE_FEE),0)+"
    			+ "  NVL(SUM(t.BAGGAGE_SECURITY),0)+"
    			+ "  NVL(SUM(t.CARGO_MAIL_SECURITY),0)+"
    			+ "  NVL(SUM(t.STOWAGE_COMMUNICATION_FEE),0)+"
    			+ "  NVL(SUM(t.CARGOMAIL),0)+"
    			+ "  NVL(SUM(t.STATION_SITE),0)+"
    			+ "  NVL(SUM(t.FLIGHT_SERVICE_FEE),0)+"
    			+ "  NVL(SUM(t.FLIGHT_DUTY_FEE),0)+"
    			+ "  NVL(SUM(t.PASSENGER_CAR),0)+"
    			+ "  NVL(SUM(t.PASSENGER_CAR_FERRY),0)+"
    			+ "  NVL(SUM(t.CAR_FERRY_CREW),0)+"
    			+ "  NVL(SUM(t.LIFT_PLATFORM_TRUCKS),0)+"
    			+ "  NVL(SUM(t.VEHICLES_DISABLED),0)+"
    			+ "  NVL(SUM(t.GUIDED_VEHICLE),0)+"
    			+ "  NVL(SUM(t.CARD),0)+"
    			+ "  NVL(SUM(t.GENERALLY_AGENT_FEE),0)+"
    			+ "  NVL(SUM(t.FIRST_CLASS),0)+"
    			+ "  NVL(SUM(t.VIP),0)+"
    			+ "  NVL(SUM(t.BRIDGE),0)+"
    			+ "  NVL(SUM(t.WAREHOUSE_RECEIPTS),0)+"
    			+ "  NVL(SUM(t.BRIDGE_LOAD_POWER),0)+"
    			+ "  NVL(SUM(t.AIR_CONDITIONING),0)+"
    			+ "  NVL(SUM(TRACTOR_CAR),0)) as TOTAL_AMOUNT,"
		        + "  SUM(t.ALIGHTING_AND_TAKE_OFF_FEE) AS ALIGHTING_AND_TAKE_OFF_FEE,"
		        + "  SUM(t.PARKING_FEE) AS PARKING_FEE,"
		        + "  SUM(t.NIGHT) AS NIGHT,"
		        + "  SUM(t.PASSENGER_SERVICE_FEE) AS PASSENGER_SERVICE_FEE,"
		        + "  SUM(t.BAGGAGE_SECURITY) AS BAGGAGE_SECURITY,"
		        + "  SUM(t.CARGO_MAIL_SECURITY) AS CARGO_MAIL_SECURITY,"
		        + "  SUM(t.STOWAGE_COMMUNICATION_FEE) AS STOWAGE_COMMUNICATION_FEE,"
		        + "  SUM(t.CARGOMAIL) AS CARGOMAIL,"
		        + "  SUM(t.STATION_SITE) AS STATION_SITE,"
		        + "  SUM(t.FLIGHT_SERVICE_FEE) AS FLIGHT_SERVICE_FEE,"
		        + "  SUM(t.FLIGHT_DUTY_FEE) AS FLIGHT_DUTY_FEE,"
		        + "  SUM(t.PASSENGER_CAR) AS PASSENGER_CAR,"
		        + "  SUM(t.PASSENGER_CAR_FERRY) AS PASSENGER_CAR_FERRY,"
		        + "  SUM(t.CAR_FERRY_CREW) AS CAR_FERRY_CREW,"
		        + "  SUM(t.LIFT_PLATFORM_TRUCKS) AS LIFT_PLATFORM_TRUCKS,"
		        + "  SUM(t.VEHICLES_DISABLED) AS VEHICLES_DISABLED,"
		        + "  SUM(t.GUIDED_VEHICLE) AS GUIDED_VEHICLE,"
		        + "  SUM(t.CARD) AS CARD,"
		        + "  SUM(t.GENERALLY_AGENT_FEE) AS GENERALLY_AGENT_FEE,"
		        + "  SUM(t.FIRST_CLASS) AS FIRST_CLASS,"
		        + "  SUM(t.VIP) AS VIP,"
		        + "  SUM(t.BRIDGE) AS BRIDGE,"
		        + "  SUM(t.WAREHOUSE_RECEIPTS) AS WAREHOUSE_RECEIPTS,"
    			+ "  SUM(t.BRIDGE_LOAD_POWER) AS BRIDGE_LOAD_POWER,"
    			+ "  SUM(t.AIR_CONDITIONING) AS AIR_CONDITIONING,"
    			+ "  SUM(t.TRACTOR_CAR) AS TRACTOR_CAR "
    			+ "  from v_seatsearnings T WHERE 1 = 1 ";
    	if((startTime!=null&&!("").equals(startTime))&&(endTime!=null&&!("").equals(endTime))){
			sql = sql + " and to_date(t.scheduled_time,'yyyy-MM-dd') between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd') ";
		}
    	sql += " GROUP BY t.STAND_NUM,t.STAND_DESCRIPTION ORDER BY t.STAND_DESCRIPTION";
    	List<Map<String,Object>> list = jt.queryForList(sql,startTime,endTime);
    	return list;
    }
}
