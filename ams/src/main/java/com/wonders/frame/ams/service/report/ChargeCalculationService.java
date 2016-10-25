package com.wonders.frame.ams.service.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChargeCalculationService {
	
	@Resource
	private JdbcTemplate jt;
	
    public List<Map<String,Object>> query(String startTime,String endTime,String sqlWhere){
    	String sql = "SELECT "
    			+ "  T.AIRLINE_NAME,"
    			+ "  T.AIRLINE_ICAO_CODE,"
    			+ "  T.AIRCRAFT_TYPE,"
    			+ "  T.ROUTE_NATURE,"
    			+ "  T.SERVICE_TYPE_DESCRIPTION,"
    			+ "  T.FLIGHT_PROPERTY,"
    			+ "  COUNT(1)/2 AS VEHICLES,"
    			+ "  SUM(T.ALIGHTING_AND_TAKE_OFF_FEE) AS ALIGHTING_AND_TAKE_OFF_FEE,"//起降服务费
    			+ "  SUM(T.NIGHT) AS NIGHT,"//夜航附加费
    			+ "  SUM(T.PASSENGER_SERVICE_FEE) AS PASSENGER_SERVICE_FEE,"//旅客服务费
    			+ "  SUM(T.PARKING_FEE) AS PARKING_FEE,"//停场费
    			+ "  SUM(T.BAGGAGE_SECURITY) AS BAGGAGE_SECURITY,"//旅客行李安检费
    			+ "  SUM(T.CARGO_MAIL_SECURITY) AS CARGO_MAIL_SECURITY,"//货物邮件安检费
    			+ "  SUM(T.STOWAGE_COMMUNICATION_FEE) AS STOWAGE_COMMUNICATION_FEE,"//配载通信旅客行李与集装设备费
    			+ "  SUM(T.CARGOMAIL) AS CARGOMAIL,"//货物和邮件服务费
    			+ "  SUM(T.STATION_SITE) AS STATION_SITE,"//站坪服务费
    			+ "  SUM(T.FLIGHT_SERVICE_FEE) AS FLIGHT_SERVICE_FEE,"//飞机服务费
    			+ "  SUM(T.FLIGHT_SERVICE_FEE_BEFORE) AS FLIGHT_SERVICE_FEE_BEFORE,"//飞机服务费航前
    			+ "  SUM(T.FLIGHT_SERVICE_FEE_AFTER) AS FLIGHT_SERVICE_FEE_AFTER,"//飞机服务费航后
    			+ "  SUM(T.FLIGHT_DUTY_FEE) AS FLIGHT_DUTY_FEE,"//飞机勤务费
    			+ "  SUM(T.PASSENGER_CAR) AS PASSENGER_CAR,"//客梯车
    			+ "  SUM(T.PASSENGER_CAR_FERRY) AS PASSENGER_CAR_FERRY,"//旅客摆渡车
    			+ "  SUM(T.CAR_FERRY_CREW) AS CAR_FERRY_CREW,"//机组摆渡车
    			+ "  SUM(T.LIFT_PLATFORM_TRUCKS) AS LIFT_PLATFORM_TRUCKS,"//升降平台车
    			+ "  SUM(T.VEHICLES_DISABLED) AS VEHICLES_DISABLED,"//残疾人登机车
    			+ "  SUM(T.GUIDED_VEHICLE) AS GUIDED_VEHICLE,"//引导车
    			+ "  SUM(T.BRIDGE) AS BRIDGE,"//登机桥
    			+ "  SUM(T.POWER_CAR) AS POWER_CAR,"//电源车
    			+ "  SUM(T.AIR_CAR) AS AIR_CAR,"//汽源车
    			+ "  SUM(T.TRACTOR_CAR) AS TRACTOR_CAR,"//牵引车
    			+ "  SUM(T.WATER_VEHICLE) AS WATER_VEHICLE,"//清水车（空）
    			+ "  SUM(T.CARD) AS CARD,"//行李签牌费
    			+ "  SUM(T.GENERALLY_AGENT_FEE) AS GENERALLY_AGENT_FEE,"//代理服务费
    			+ "  SUM(T.FIRST_CLASS) AS FIRST_CLASS,"//头等舱费
    			+ "  SUM(T.VIP) AS VIP,"//VIP费 
    			+ "  SUM(T.SEWAGE_VEHICLES) AS SEWAGE_VEHICLES,"//污水车（空）
    			+ "  SUM(T.WAREHOUSE_RECEIPTS) AS WAREHOUSE_RECEIPTS," //电子仓单
    			+ "  SUM(T.BRIDGE_LOAD_POWER) AS BRIDGE_LOAD_POWER,"//桥载电源
    			+ "  SUM(T.BRIDGE_LOAD_POWER_BEFORE) AS BRIDGE_LOAD_POWER_BEFORE,"//桥载电源(航前)
    			+ "  SUM(T.BRIDGE_LOAD_POWER_AFTER) AS BRIDGE_LOAD_POWER_AFTER,"//桥载电源(航后)
    			+ "  SUM(T.AIR_CONDITIONING) AS AIR_CONDITIONING, " //桥载空调
    			+ "  SUM(T.AIR_CONDITIONING_BEFORE) AS AIR_CONDITIONING_BEFORE, " //桥载空调(航前)
    			+ "  SUM(T.AIR_CONDITIONING_AFTER) AS AIR_CONDITIONING_AFTER " //桥载空调（航后）
    			+ "  FROM V_CHARGECALCULATION T WHERE 1 = 1 " + sqlWhere;
    	if((startTime!=null&&!("").equals(startTime))&&(endTime!=null&&!("").equals(endTime))){
			sql = sql + " and to_date(t.scheduled_time,'yyyy-MM-dd') between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd') ";
		}
    	sql += " GROUP BY T.AIRLINE_NAME,T.AIRLINE_ICAO_CODE,T.AIRCRAFT_TYPE,T.ROUTE_NATURE,T.SERVICE_TYPE_DESCRIPTION,T.FLIGHT_PROPERTY "
    			+ " ORDER BY T.ROUTE_NATURE,T.AIRLINE_ICAO_CODE,T.AIRLINE_NAME,T.AIRCRAFT_TYPE ";
    	List<Map<String,Object>> list = jt.queryForList(sql,startTime,endTime);
    	return list;
    }
}
