package com.nlia.fqdb.specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

import com.nlia.fqdb.entity.ChargeSubject;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.service.impl.AircraftAirlineAlterationManager;
import com.nlia.fqdb.util.DateUtils;
import com.nlia.fqdb.vo.RuleGroup;
import com.nlia.fqdb.vo.RuleSingleExpression;


public class ChargeUtils {
	@Resource
	private AircraftAirlineAlterationManager aircraftAirlineAlterationManager;
	
	private Map<String, String> identityFieldMap = new HashMap<>();
	private Map<String, String> identityFieldMapCH2Eg = new HashMap<>();
	private List<String> list = new ArrayList<>();
	private ScriptEngineManager factory = new ScriptEngineManager();
	ScriptEngine engine = factory.getEngineByName("JavaScript");
	public ChargeUtils(FlightMateInfo flightMateInfo){
	    double landTime = 0, passengerBridgeNumber = 0,passengerCarUsedTime = 0,passengerCarUsed30munite = 0, airdromeCountUsedByPassenger = 0, airdromeCountUsedByCrew = 0,
                specialVehiclesCountForDisabled = 0, liftingPlatformCarUsedTime = 0, leadCarUsedCount = 0 , electricSourceTruckUsedTime = 0, airconditioningVehicleUsedTime = 0,
                routineCheckTime = 0,passengerBridgeServiceTime = 0,tractorsUsedCount=0;
        String tmpstr="";
        double tmpTime=0;
            //判断计算客桥数量
            tmpstr=flightMateInfo.getPassengerBridgeNumber();
            passengerBridgeNumber =  (tmpstr!=null && DateUtils.isDecimal(tmpstr))? Double.parseDouble(tmpstr):0;
            //旅客摆渡车使用次数
            tmpstr=flightMateInfo.getAirdromeUsedByPasCount();
            airdromeCountUsedByPassenger = (tmpstr!=null && DateUtils.isDecimal(tmpstr))? Double.parseDouble(tmpstr):0;
            //机组摆渡车使用次数
            tmpstr=flightMateInfo.getAirdromeUsedByCrewCount();
            airdromeCountUsedByCrew = (tmpstr!=null && DateUtils.isDecimal(tmpstr))? Double.parseDouble(tmpstr):0;
            //残疾人专用车使用次数
            tmpstr=flightMateInfo.getSpeCarForDisabledCount();
            specialVehiclesCountForDisabled = (tmpstr!=null && DateUtils.isDecimal(tmpstr))? Double.parseDouble(tmpstr):0;
            //引导车使用次数
            tmpstr=flightMateInfo.getLeadCarUsedCount();
            leadCarUsedCount= (tmpstr!=null && DateUtils.isDecimal(tmpstr))? Double.parseDouble(tmpstr):0;
            //牵引车
            tmpstr=flightMateInfo.getTractorsUsedCount();
            tractorsUsedCount= (tmpstr!=null && DateUtils.isDecimal(tmpstr))? Double.parseDouble(tmpstr):0;
            //电源车使用时间（小时）
            tmpTime=(flightMateInfo.getElectricTruckTime()!=null ? flightMateInfo.getElectricTruckTime():0);
            electricSourceTruckUsedTime = tmpTime;
            //升降平台车使用时间（小时）
            tmpTime= (flightMateInfo.getLiftingPlatformCarTime()!=null?flightMateInfo.getLiftingPlatformCarTime():0);
            liftingPlatformCarUsedTime = tmpTime;
            //空调车使用时间（小时）
            tmpTime= (flightMateInfo.getAirconditioningTime()!=null?flightMateInfo.getAirconditioningTime():0);//AIRCONDITIONING_TIME
            airconditioningVehicleUsedTime = tmpTime;
            //客梯车使用时间（小时） //PSSSENGER_CAR_USED_TIME
            tmpTime=(flightMateInfo.getPsssengerCarUsedTime()!=null?flightMateInfo.getPsssengerCarUsedTime():0);
            passengerCarUsedTime = tmpTime;
            //客梯车使用时间（半小时）
            tmpTime= (flightMateInfo.getPsssengerCarUsedTime()!=null?flightMateInfo.getPsssengerCarUsedTime():0);//PSSSENGER_CAR_USED_TIME
            passengerCarUsed30munite = tmpTime;
            //例行检查时间（小时）
            tmpTime= (flightMateInfo.getRoutineCheckTime()!=null?flightMateInfo.getRoutineCheckTime():0);//ROUTINE_CHECK_TIME
            routineCheckTime = tmpTime;
            //客桥使用时间（半小时）
            tmpTime= (flightMateInfo.getPassengerBridgeTime()!=null?flightMateInfo.getPassengerBridgeTime():0);//PASSENGER_BRIDGE_TIME
            passengerBridgeServiceTime = tmpTime;
            
            
            
        //取离港航班性质有
        identityFieldMapCH2Eg.put("飞机最大起飞全重（吨）", "aircraftTakeoffWeight");
        identityFieldMapCH2Eg.put("国内出港成人数", "departureAdultNumber");
        identityFieldMapCH2Eg.put("过站旅客数", "viaPassengerNumber");
        identityFieldMapCH2Eg.put("客桥数量", "passengerBridgeNumber");
        identityFieldMapCH2Eg.put("国际出港成人数", "intAdultNumber");
        identityFieldMapCH2Eg.put("飞机座位数", "aircraftSeatCapacity");
        identityFieldMapCH2Eg.put("国内货物邮件重量（吨）", "airMailWeight");
        identityFieldMapCH2Eg.put("国际货物邮件重量（吨）", "intAirMailWeight");//国际货物邮件重量
        identityFieldMapCH2Eg.put("飞机最大业载（吨）", "aircraftPayload");
        identityFieldMapCH2Eg.put("客梯车使用时间（分钟）", "passengerCarUsedTime");
        identityFieldMapCH2Eg.put("旅客摆渡车使用次数", "airdromeCountUsedByPassenger");
        identityFieldMapCH2Eg.put("机组摆渡车使用次数", "airdromeCountUsedByCrew");
        identityFieldMapCH2Eg.put("残疾人专用车使用次数", "specialVehiclesCountForDisabled");
        identityFieldMapCH2Eg.put("升降平台车使用时间（分钟）", "liftingPlatformCarUsedTime");
        identityFieldMapCH2Eg.put("引导车使用次数", "leadCarUsedCount");
        identityFieldMapCH2Eg.put("电源车使用时间（分钟）", "electricSourceTruckUsedTime");
        identityFieldMapCH2Eg.put("牵引车使用次数", "tractorsUsedCount");
        identityFieldMapCH2Eg.put("空调车使用时间（分钟）", "airconditioningVehicleUsedTime");
        identityFieldMapCH2Eg.put("例行检查时间（分钟）", "routineCheckTime");
        identityFieldMapCH2Eg.put("客桥使用时间（分钟）", "passengerBridgeServiceTime");
        identityFieldMapCH2Eg.put("航班飞机类型", "isWideOrNarrow");
        
        identityFieldMapCH2Eg.put("停场天数", "landTime");
        
        identityFieldMapCH2Eg.put("气源车使用时间（分钟）", "airTruckTime");//气源车使用时间
        
        identityFieldMapCH2Eg.put("桥载空调使用时间（分钟）", "bridgeAirconditionUsedTime");
        identityFieldMapCH2Eg.put("桥载电源使用时间（分钟）", "bridgeElectricUsedTime");
        identityFieldMapCH2Eg.put("除冰车使用时间（分钟）", "deicingTruckUsedTime");
        
        
        identityFieldMapCH2Eg.put("国内儿童", "childInternal");
        identityFieldMapCH2Eg.put("国际儿童", "childInternational");
        identityFieldMapCH2Eg.put("国内婴儿", "babyInternal");
        identityFieldMapCH2Eg.put("国际婴儿", "babyInternational");
        identityFieldMapCH2Eg.put("残疾人登机车使用(次数)", "speBoardCarUsedCount");
        identityFieldMapCH2Eg.put("vip人数", "vipCount");
        identityFieldMapCH2Eg.put("头等舱人数", "firstClassCount");
        identityFieldMapCH2Eg.put("vip头等舱摆渡车人数", "vipFirstAirdromeUsedCount");
        identityFieldMapCH2Eg.put("进离港", "isInOrOut");
        
        
        
        identityFieldMap.put("aircraftTakeoffWeight", "" + flightMateInfo.getAircraftTakeOffWeight());
        identityFieldMap.put("flightCountryType", flightMateInfo.getFlightCountryType());
        identityFieldMap.put("type",flightMateInfo.getFlightCountryType());
        identityFieldMap.put("isNightFlight",flightMateInfo.getIsNightFlight());   //是否夜航
        identityFieldMap.put("isPeakFlight",flightMateInfo.getIsPeakFlight());    //是否高峰
        identityFieldMap.put("specialPlane", "" +flightMateInfo.getSpecialPlane());
        //identityFieldMap.put("landTime", "" + landTime);
        identityFieldMap.put("landTime", "" +flightMateInfo.getLandTime());
        identityFieldMap.put("departureAdultNumber", "" + flightMateInfo.getPassengerInternal());//国内成人
        identityFieldMap.put("intAdultNumber","" + flightMateInfo.getPassengerInternational());//国际成人
        identityFieldMap.put("viaPassengerNumber", "" + flightMateInfo.getViaAdult());//过站成人
        identityFieldMap.put("passengerBridgeNumber", "" + passengerBridgeNumber);
        identityFieldMap.put("passengerCarUsedTime", "" + passengerCarUsedTime);
        identityFieldMap.put("passengerCarUsed30munite", "" + passengerCarUsed30munite);  
        identityFieldMap.put("airMailWeight", "" + (nullToZero(flightMateInfo.getAirMailWeightInternal()) + nullToZero(flightMateInfo.getAirCargoWeightInternal())));//国内货物邮件重量
        identityFieldMap.put("intAirMailWeight", "" +(nullToZero(flightMateInfo.getAirMailWeightInternational()) + nullToZero(flightMateInfo.getAirCargoWeightInternational())));//国际货物邮件重量
        identityFieldMap.put("airdromeCountUsedByPassenger", "" + airdromeCountUsedByPassenger);
        identityFieldMap.put("airdromeCountUsedByCrew", "" + airdromeCountUsedByCrew);
        identityFieldMap.put("specialVehiclesCountForDisabled", "" + specialVehiclesCountForDisabled);
        identityFieldMap.put("liftingPlatformCarUsedTime", "" + liftingPlatformCarUsedTime);
        identityFieldMap.put("leadCarUsedCount", "" + leadCarUsedCount);
        identityFieldMap.put("electricSourceTruckUsedTime", "" + electricSourceTruckUsedTime);
        identityFieldMap.put("airconditioningVehicleUsedTime", "" + airconditioningVehicleUsedTime);
        identityFieldMap.put("tractorsUsedCount", "" + tractorsUsedCount );
        identityFieldMap.put("routineCheckTime", "" + routineCheckTime);
        identityFieldMap.put("passengerBridgeServiceTime", "" + passengerBridgeServiceTime);
        identityFieldMap.put("aircraftPayload", flightMateInfo.getAircraftPayload()!=null? "" + flightMateInfo.getAircraftPayload():"");
        identityFieldMap.put("aircraftSeatCapacity", flightMateInfo.getAircraftSeatCapacity()!=null? "" + flightMateInfo.getAircraftSeatCapacity():"");
        identityFieldMap.put("flightMission", flightMateInfo.getFlightDirection().equals("0")?flightMateInfo.getAFlightProperty():flightMateInfo.getDFlightProperty());
        identityFieldMap.put("isWideOrNarrow", flightMateInfo.getIsWideOrNarrow());
        
        identityFieldMap.put("airTruckTime","" +flightMateInfo.getAirTruckTime());//气源车使用时间
        identityFieldMap.put("bridgeAirconditionUsedTime",""+flightMateInfo.getBridgeAirconditionUsedTime());//桥载空调使用时间
        identityFieldMap.put("bridgeElectricUsedTime",""+flightMateInfo.getBridgeElectricUsedTime());//桥载电源使用时间
        identityFieldMap.put("deicingTruckUsedTime",""+flightMateInfo.getDeicingTruckUsedTime());//除冰车使用时间
        
        
        identityFieldMap.put("childInternal",""+flightMateInfo.getChildInternal());                             //国内儿童
        identityFieldMap.put("childInternational",""+flightMateInfo.getChildInternational());                   //国际儿童
        identityFieldMap.put("babyInternal",""+flightMateInfo.getBabyInternal());                               //国内婴儿
        identityFieldMap.put("babyInternational",""+flightMateInfo.getBabyInternational());                     //国际婴儿
        identityFieldMap.put("vipCount",""+flightMateInfo.getVipCount());                                       //vip人数
        identityFieldMap.put("firstClassCount",""+flightMateInfo.getFirstClassCount());                         //头等舱人数
        identityFieldMap.put("vipFirstAirdromeUsedCount",""+flightMateInfo.getVipFirstAirdromeUsedCount());     //vip头等舱摆渡车人数
        identityFieldMap.put("speBoardCarUsedCount",""+flightMateInfo.getSpeBoardCarUsedCount());           //残疾人登机车使用次数
        identityFieldMap.put("isInOrOut",""+flightMateInfo.getFlightDirection());                               //进离港
        identityFieldMap.put("passengerLoadFactor",""+flightMateInfo.getPassengerLoadFactor());                 //客座率
        
        
        identityFieldMap.put("landandtakeofffeecoe",             flightMateInfo.getLandAndTakeoffFeeCoe()              );//起降费系数             
        identityFieldMap.put("partingfeecoe",                    flightMateInfo.getPartingFeeCoe()                       );//停场费系数           
        identityFieldMap.put("passervicefeecoe",                 flightMateInfo.getPasServiceFeeCoe()                   );//旅客服务费系数      
        identityFieldMap.put("passecuritycheckfeecoe",           flightMateInfo.getPasSecurityCheckFeeCoe()            );//旅客行李安检费费系数   
        identityFieldMap.put("pascargosecuritycheckcoe",         flightMateInfo.getPasCargoSecurityCheckCoe()          );//旅客货物安检费费系数   
        identityFieldMap.put("basicfeecoe",                      flightMateInfo.getBasicFeeCoe()                         );//基本收费系数         
        identityFieldMap.put("stowagecommunicationfeecoe",       flightMateInfo.getStowageCommunicationFeeCoe()         );//配载通信费系数        
        identityFieldMap.put("packagingfacility",                flightMateInfo.getPackagingFacility()                  );//集装设备收费系数      
        identityFieldMap.put("passengerluggagefeecoe",           flightMateInfo.getPassengerLuggageFeeCoe()             );//旅客和行李费系数      
        identityFieldMap.put("cagromailfeecoe",                  flightMateInfo.getCagroMailFeeCoe()                    );//货物邮件费系数        
        identityFieldMap.put("apronservicefeecoe",               flightMateInfo.getApronServiceFeeCoe()                 );//站坪（机坪）服务费系数
        identityFieldMap.put("viaflightservicefeecoe",           flightMateInfo.getViaFlightServiceFeeCoe()            );//过站服务费系数         
        identityFieldMap.put("preflightservicefeecoe",           flightMateInfo.getPreflightServiceFeeCoe()             );//航前服务费系数        
        identityFieldMap.put("postflightservicefeecoe",          flightMateInfo.getPostflightServiceFeeCoe()            );//航后服务费系数        
        identityFieldMap.put("sundrydutiesfeecoe",               flightMateInfo.getSundryDutiesFeeCoe()                 );//勤务费系数            
        identityFieldMap.put("routinecheckfeecoe",               flightMateInfo.getRoutineCheckFeeCoe()                 );//例行检查费系数        
        identityFieldMap.put("flithtdispatchfeecoe",             flightMateInfo.getFlithtDispatchFeeCoe()               );//飞机放行费系数        
        identityFieldMap.put("leadcarfeecoe",                    flightMateInfo.getLeadcarFeeCoe()                       );//引导车费系数         
        identityFieldMap.put("airtruckfeecoe",                   flightMateInfo.getAirTruckFeeCoe()                     );//气源车费系数          
        identityFieldMap.put("electrictruckfeecoe",              flightMateInfo.getElectricTruckFeeCoe()                );//电源车费系数          
        identityFieldMap.put("tractorfeecoe",                    flightMateInfo.getTractorFeeCoe()                       );//牵引车费系数         
        identityFieldMap.put("airconditiontruckfeecoe",          flightMateInfo.getAirConditionTruckFeeCoe()           );//空调车费系数           
        identityFieldMap.put("deicingtruckfeecoe",               flightMateInfo.getDeicingTruckFeeCoe()                 );//除冰车费系数          
        identityFieldMap.put("snowremovaltruckfeecoe",           flightMateInfo.getSnowRemovalTruckFeeCoe()            );//扫雪车费系数           
        identityFieldMap.put("passenagecarfeecoe",               flightMateInfo.getPassenageCarFeeCoe()                 );//客梯车费系数          
        identityFieldMap.put("singleairbragefeecoe",             flightMateInfo.getSingleAirBrageFeeCoe()              );//单廊桥费系数           
        identityFieldMap.put("doubleairbragefeecoe",             flightMateInfo.getDoubleAirBrageFeeCoe()              );//双廊桥费系数           
        identityFieldMap.put("airdromeusedbycrewfeecoe",         flightMateInfo.getAirdromeUsedByCrewFeeCoe()         );//机组摆渡车费系数        
        identityFieldMap.put("airdromeusedbypasfeecoe",          flightMateInfo.getAirdromeUsedByPasFeeCoe()          );//旅客摆渡车费系数        
        identityFieldMap.put("liftingplatformcarfeecoe",         flightMateInfo.getLiftingPlatformCarFeeCoe()          );//升降平台车费系数       
        identityFieldMap.put("unroutinecheckfeecoe",             flightMateInfo.getUnroutineCheckFeeCoe()               );//非例行检查费系数      
        identityFieldMap.put("specarfordisabledfeecoe",          flightMateInfo.getSpeCarForDisabledFeeCoe()         );//残疾人车费系数           

        identityFieldMap.put("aircraftService",                  flightMateInfo.getAircraftService());//add by march 20151110 飞机服务类型
        
        ///add by lushuaifeng 20161012
        identityFieldMap.put("flightPart0Type",flightMateInfo.getFlightPart0Type());// 航段0类型        
        identityFieldMap.put("airlineId",""+flightMateInfo.getAirLine().getId());// 航空公司ID  
        identityFieldMapCH2Eg.put("航段0类型","flightPart0Type");//航段0类型
        identityFieldMapCH2Eg.put("航空公司ID","airlineId");//航空公司ID
  
        //
        identityFieldMapCH2Eg.put("起降费系数"                   ,                 "landandtakeofffeecoe"              );//起降费系数
        identityFieldMapCH2Eg.put("停场费系数"                   ,                 "partingfeecoe"                     );//停场费系数
        identityFieldMapCH2Eg.put("旅客服务费系数"               ,                 "passervicefeecoe"                  );//旅客服务费系数
        identityFieldMapCH2Eg.put("旅客行李安检费费系数"         ,                 "passecuritycheckfeecoe"            );//旅客行李安检费费系数
        identityFieldMapCH2Eg.put("旅客货物安检费费系数"         ,                 "pascargosecuritycheckcoe"          );//旅客货物安检费费系数
        identityFieldMapCH2Eg.put("基本收费系数"                 ,                 "basicfeecoe"                       );//基本收费系数
        identityFieldMapCH2Eg.put("配载通信费系数"               ,                 "stowagecommunicationfeecoe"        );//配载通信费系数
        identityFieldMapCH2Eg.put("集装设备收费系数"             ,                 "packagingfacility"                 );//集装设备收费系数
        identityFieldMapCH2Eg.put("旅客和行李费系数"             ,                 "passengerluggagefeecoe"            );//旅客和行李费系数
        identityFieldMapCH2Eg.put("货物邮件费系数"               ,                 "cagromailfeecoe"                   );//货物邮件费系数
        identityFieldMapCH2Eg.put("站坪（机坪）服务费系数"       ,                 "apronservicefeecoe"                );//站坪（机坪）服务费系数
        identityFieldMapCH2Eg.put("过站服务费系数"               ,                 "viaflightservicefeecoe"            );//过站服务费系数
        identityFieldMapCH2Eg.put("航前服务费系数"               ,                 "preflightservicefeecoe"            );//航前服务费系数
        identityFieldMapCH2Eg.put("航后服务费系数"               ,                 "postflightservicefeecoe"           );//航后服务费系数
        identityFieldMapCH2Eg.put("勤务费系数"                   ,                 "sundrydutiesfeecoe"                );//勤务费系数
        identityFieldMapCH2Eg.put("例行检查费系数"               ,                 "routinecheckfeecoe"                );//例行检查费系数
        identityFieldMapCH2Eg.put("引导车费系数"                 ,                 "leadcarfeecoe"                     );//引导车费系数
        identityFieldMapCH2Eg.put("气源车费系数"                 ,                 "airtruckfeecoe"                    );//气源车费系数
        identityFieldMapCH2Eg.put("电源车费系数"                 ,                 "electrictruckfeecoe"               );//电源车费系数
        identityFieldMapCH2Eg.put("牵引车费系数"                 ,                 "tractorfeecoe"                     );//牵引车费系数
        identityFieldMapCH2Eg.put("空调车费系数"                 ,                 "airconditiontruckfeecoe"           );//空调车费系数
        identityFieldMapCH2Eg.put("扫雪车费系数"                 ,                 "snowremovaltruckfeecoe"            );//扫雪车费系数
        identityFieldMapCH2Eg.put("客梯车费系数"                 ,                 "passenagecarfeecoe"                );//客梯车费系数
        identityFieldMapCH2Eg.put("机组摆渡车费系数"             ,                 "airdromeusedbycrewfeecoe"          );//机组摆渡车费系数
        identityFieldMapCH2Eg.put("旅客摆渡车费系数"             ,                 "airdromeusedbypasfeecoe"           );//旅客摆渡车费系数
        identityFieldMapCH2Eg.put("升降平台车费系数"             ,                 "liftingplatformcarfeecoe"          );//升降平台车费系数
        identityFieldMapCH2Eg.put("非例行检查费系数"             ,                 "unroutinecheckfeecoe"              );//非例行检查费系数
        identityFieldMapCH2Eg.put("残疾人车费系数"               ,                 "specarfordisabledfeecoe"           );//残疾人车费系数
        identityFieldMapCH2Eg.put("飞机放行费系数"               ,                 "flithtdispatchfeecoe"              );//飞机放行费系数
        identityFieldMapCH2Eg.put("除冰车费系数"                 ,                 "deicingtruckfeecoe"                );//除冰车费系数
        identityFieldMapCH2Eg.put("单廊桥费系数"                 ,                 "singleairbragefeecoe"              );//单廊桥费系数
        identityFieldMapCH2Eg.put("双廊桥费系数"                 ,                 "doubleairbragefeecoe"              );//双廊桥费系数
        
        //

        identityFieldMap.put("flightProperty",flightMateInfo.getFlightDirection().equals("0")? flightMateInfo.getAFlightProperty():flightMateInfo.getDFlightProperty());  //航班性质
        identityFieldMap.put("delayCode",flightMateInfo.getDelayCode());   //原因
        identityFieldMap.put("airlineCountryType",flightMateInfo.getAirLine().getAirlineCountryType());  //公司性质
        
        identityFieldMap.put("loadFactor","" + flightMateInfo.getPassengerLoadFactor());  //客座率
        
        identityFieldMap.put("isHighDensity",flightMateInfo.getIsHighDensity());//高密度座位标识
        identityFieldMap.put("isPackagingFacility", flightMateInfo.getIsPackagingFacility());//使用集装设备标识

    }
	/**
	 * 构造函数，用Map来储存日航班计划信息
	 * 
	 * @param dailyFlightPlan
	 */
//	public ChargeUtils(FlightMate flightMate, FlightInfo flightInfo) {
//		int landTime = 0, passengerBridgeNumber = 0, passengerCarUsedTime = 0, passengerCarUsed30munite = 0, airdromeCountUsedByPassenger = 0, airdromeCountUsedByCrew = 0,
//				specialVehiclesCountForDisabled = 0, liftingPlatformCarUsedTime = 0, leadCarUsedCount = 0 , electricSourceTruckUsedTime = 0, airconditioningVehicleUsedTime = 0,
//				routineCheckTime = 0,passengerBridgeServiceTime = 0;
//		
//		if(flightMate != null && flightMate.getArrivedFlightBase() != null & flightMate.getDepartureFlightBase()!=null){
//		  //判断计算停场时间
//		    if(flightMate.getArrivedFlightBase().getFlightData().getActualLandingDateTime() != null && flightMate.getDepartureFlightBase().getFlightData().getActualTakeOffDateTime() != null){
//		    	landTime = (int) Math.ceil(Math.abs(DateUtils.getDifferHour(flightMate.getDepartureFlightBase().getFlightData().getActualTakeOffDateTime(),flightMate.getArrivedFlightBase().getFlightData().getActualLandingDateTime())));
//		    }
//		}
//		
//		if (flightMate != null && flightMate.getArrivalFlightServiceInfo() != null && flightMate.getDepartureFlightServiceInfo() != null) {
////			//判断计算停场时间
////			if ((flightMate.getArrivalFlightServiceInfo().getLandedTime() != null && flightMate.getArrivalFlightServiceInfo().getTakeOffTime() != null) || (
////					flightMate.getDepartureFlightServiceInfo().getLandedTime() != null && flightMate.getDepartureFlightServiceInfo().getTakeOffTime() != null)) {
////				landTime = (int) Math.ceil(Math.abs(DateUtils.getDifferHour(flightMate.getArrivalFlightServiceInfo().getLandedTime(), flightMate.getArrivalFlightServiceInfo().getTakeOffTime())) +
////						Math.abs(DateUtils.getDifferHour(flightMate.getDepartureFlightServiceInfo().getLandedTime(), flightMate.getDepartureFlightServiceInfo().getTakeOffTime())));
////			}else if(flightMate.getArrivalFlightServiceInfo().getLandedTime() != null && flightMate.getArrivalFlightServiceInfo().getTakeOffTime() == null &&
////					flightMate.getDepartureFlightServiceInfo().getLandedTime() == null && flightMate.getDepartureFlightServiceInfo().getTakeOffTime() != null){
////				landTime = (int) Math.ceil(Math.abs(DateUtils.getDifferHour(flightMate.getArrivalFlightServiceInfo().getLandedTime(), flightMate.getDepartureFlightServiceInfo().getTakeOffTime())));
////			}
//			
//			//判断计算客桥数量
//			passengerBridgeNumber = calculateCount(flightMate.getDepartureFlightServiceInfo().getPassengerBridgeNumber(), flightMate.getArrivalFlightServiceInfo().getPassengerBridgeNumber());
//			//旅客摆渡车使用次数
//			airdromeCountUsedByPassenger = calculateCount(flightMate.getArrivalFlightServiceInfo().getAirdromeCountUsedByPassenger(), flightMate.getDepartureFlightServiceInfo().getAirdromeCountUsedByPassenger());
//			//机组摆渡车使用次数
//			airdromeCountUsedByCrew = calculateCount(flightMate.getArrivalFlightServiceInfo().getAirdromeCountUsedByCrew(), flightMate.getDepartureFlightServiceInfo().getAirdromeCountUsedByCrew());
//			//残疾人专用车使用次数
//			specialVehiclesCountForDisabled = calculateCount(flightMate.getArrivalFlightServiceInfo().getSpecialVehiclesCountForDisabled(), flightMate.getDepartureFlightServiceInfo().getSpecialVehiclesCountForDisabled());
//			//引导车使用次数
//			leadCarUsedCount= calculateCount(flightMate.getArrivalFlightServiceInfo().getLeadCarUsedCount(), flightMate.getDepartureFlightServiceInfo().getLeadCarUsedCount());
//			
//			//电源车使用时间（小时）
//			electricSourceTruckUsedTime = getDwellTime(flightMate.getArrivalFlightServiceInfo().getElectricSourceTruckUsedStartTime(), flightMate.getArrivalFlightServiceInfo().getElectricSourceTruckUsedEndTime(), 
//					flightMate.getDepartureFlightServiceInfo().getElectricSourceTruckUsedStartTime(), flightMate.getDepartureFlightServiceInfo().getElectricSourceTruckUsedEndTime());
//			//升降平台车使用时间（小时）
//			liftingPlatformCarUsedTime = getDwellTime(flightMate.getArrivalFlightServiceInfo().getLiftingPlatformCarUsedStartTime(), flightMate.getArrivalFlightServiceInfo().getLiftingPlatformCarUsedEndTime(),
//					flightMate.getDepartureFlightServiceInfo().getLiftingPlatformCarUsedStartTime(), flightMate.getDepartureFlightServiceInfo().getLiftingPlatformCarUsedEndTime());
//			//客梯车使用时间（小时）
//			passengerCarUsedTime = getDwellTime(flightMate.getArrivalFlightServiceInfo().getPassengerCarUsedStartTime(), flightMate.getArrivalFlightServiceInfo().getPassengerCarUsedEndTime(),
//					flightMate.getDepartureFlightServiceInfo().getPassengerCarUsedStartTime(), flightMate.getDepartureFlightServiceInfo().getPassengerCarUsedEndTime());
//			//空调车使用时间（小时）
//			airconditioningVehicleUsedTime = getDwellTime(flightMate.getArrivalFlightServiceInfo().getAirconditioningVehicleUsedStartTime(), flightMate.getArrivalFlightServiceInfo().getAirconditioningVehicleUsedEndTime(),
//					flightMate.getDepartureFlightServiceInfo().getAirconditioningVehicleUsedStartTime(), flightMate.getDepartureFlightServiceInfo().getAirconditioningVehicleUsedEndTime());
//			//客梯车使用时间（半小时）
//			passengerCarUsed30munite = getDwellTimeFor30Minute(flightMate.getArrivalFlightServiceInfo().getPassengerCarUsedStartTime(), flightMate.getArrivalFlightServiceInfo().getPassengerCarUsedEndTime(),
//					flightMate.getDepartureFlightServiceInfo().getPassengerCarUsedStartTime(), flightMate.getDepartureFlightServiceInfo().getPassengerCarUsedEndTime());
//			//例行检查时间（小时）
//			routineCheckTime = getDwellTime(flightMate.getArrivalFlightServiceInfo().getRoutineCheckStartTime(), flightMate.getArrivalFlightServiceInfo().getRoutineCheckEndTime(), 
//					flightMate.getDepartureFlightServiceInfo().getRoutineCheckStartTime(), flightMate.getDepartureFlightServiceInfo().getRoutineCheckEndTime());
//			//客桥使用时间（半小时）
//			passengerBridgeServiceTime = getDwellTimeFor30Minute(flightMate.getArrivalFlightServiceInfo().getPassengerBridgeServicedStartTime(), flightMate.getArrivalFlightServiceInfo().getPassengerBridgeServicedEndTime(),
//					flightMate.getDepartureFlightServiceInfo().getPassengerBridgeServicedStartTime(), flightMate.getDepartureFlightServiceInfo().getPassengerBridgeServicedEndTime());
//		}
//		//取离港航班性质有
//		identityFieldMapCH2Eg.put("飞机最大起飞全重（吨）", "aircraftTakeoffWeight");
//		identityFieldMapCH2Eg.put("国内出港成人数", "departureAdultNumber");
//		identityFieldMapCH2Eg.put("过站旅客数", "viaPassengerNumber");
//		identityFieldMapCH2Eg.put("客桥数量", "passengerBridgeNumber");
//		identityFieldMapCH2Eg.put("飞机座位数", "aircraftSeatCapacity");
//		identityFieldMapCH2Eg.put("货物邮件重量（吨）", "airMailWeight");
//		identityFieldMapCH2Eg.put("飞机最大业载（吨）", "aircraftPayload");
//		identityFieldMapCH2Eg.put("客梯车使用时间（分钟）", "passengerCarUsedTime");
//		identityFieldMapCH2Eg.put("旅客摆渡车使用次数", "airdromeCountUsedByPassenger");
//		identityFieldMapCH2Eg.put("机组摆渡车使用次数", "airdromeCountUsedByCrew");
//		identityFieldMapCH2Eg.put("残疾人专用车使用次数", "specialVehiclesCountForDisabled");
//		identityFieldMapCH2Eg.put("升降平台车使用时间（分钟）", "liftingPlatformCarUsedTime");
//		identityFieldMapCH2Eg.put("引导车使用次数", "leadCarUsedCount");
//		identityFieldMapCH2Eg.put("电源车使用时间（分钟）", "electricSourceTruckUsedTime");
//		identityFieldMapCH2Eg.put("牵引车使用次数", "tractorsUsedCount");
//		identityFieldMapCH2Eg.put("空调车使用时间（分钟）", "airconditioningVehicleUsedTime");
//		identityFieldMapCH2Eg.put("例行检查时间（分钟）", "routineCheckTime");
//		identityFieldMapCH2Eg.put("客桥使用时间（分钟）", "passengerBridgeServiceTime");
//		identityFieldMapCH2Eg.put("航班飞机类型", "isWideOrNarrow");
//		
//		identityFieldMapCH2Eg.put("停场时间", "landTime");
//		
//		identityFieldMap.put("aircraftTakeoffWeight", flightInfo.getAircraft().getAircraftTakeoffWeight().toString());
//		String flightCountryType = flightMate.getDepartureFlightBase().getFlightData().getFlightCountryType();
//		if(flightCountryType.equals("D")){
//			flightCountryType = "0";
//		}else if(flightCountryType.equals("I")){
//			flightCountryType = "1";
//		}else if(flightCountryType.equals("R")){
//			flightCountryType = "2";
//		}else if(flightCountryType.equals("M")){
//			flightCountryType = "3";
//		}
//		identityFieldMap.put("flightCountryType", flightCountryType);
//		identityFieldMap.put("type", flightInfo.getType());
//		identityFieldMap.put("isPeakFlight", flightInfo.getIsPeakFlight());
//		identityFieldMap.put("isNightFlight", flightInfo.getIsNightFlight());
//		identityFieldMap.put("specialPlane", flightInfo.getSpecialPlane());
//		identityFieldMap.put("landTime", "" + landTime);
//		identityFieldMap.put("departureAdultNumber", flightInfo.getDepartureAdultNumber());
//		identityFieldMap.put("viaPassengerNumber", flightInfo.getViaPassengerNumber());
//		identityFieldMap.put("passengerBridgeNumber", "" + passengerBridgeNumber);
//		identityFieldMap.put("passengerCarUsedTime", "" + passengerCarUsedTime);
//		identityFieldMap.put("passengerCarUsed30munite", "" + passengerCarUsed30munite);
//		identityFieldMap.put("departureAdultNumber", flightInfo.getDepartureAdultNumber());
//		identityFieldMap.put("airMailWeight", flightInfo.getAirMailWeight()!=null&&flightInfo.getAirMailWeight().length()>0&&!"null".equals(flightInfo.getAirMailWeight()) ? "" + Math.ceil(Double.valueOf(flightInfo.getAirMailWeight())) : "");
//		identityFieldMap.put("airdromeCountUsedByPassenger", "" + airdromeCountUsedByPassenger);
//		identityFieldMap.put("airdromeCountUsedByCrew", "" + airdromeCountUsedByCrew);
//		identityFieldMap.put("specialVehiclesCountForDisabled", "" + specialVehiclesCountForDisabled);
//		identityFieldMap.put("liftingPlatformCarUsedTime", "" + liftingPlatformCarUsedTime);
//		identityFieldMap.put("leadCarUsedCount", "" + leadCarUsedCount);
//		identityFieldMap.put("electricSourceTruckUsedTime", "" + electricSourceTruckUsedTime);
//		identityFieldMap.put("airconditioningVehicleUsedTime", "" + airconditioningVehicleUsedTime);
//		identityFieldMap.put("tractorsUsedCount", "" + calculateCount(flightMate.getArrivalFlightServiceInfo()!=null?flightMate.getArrivalFlightServiceInfo().getTractorsUsedCount():null, flightMate.getDepartureFlightServiceInfo()!=null?flightMate.getDepartureFlightServiceInfo().getTractorsUsedCount():null));
//		identityFieldMap.put("routineCheckTime", "" + routineCheckTime);
//		identityFieldMap.put("passengerBridgeServiceTime", "" + passengerBridgeServiceTime);
//		identityFieldMap.put("aircraftPayload", flightInfo.getAircraft().getAircraftPayload()!=null? "" + flightInfo.getAircraft().getAircraftPayload():"");
//		identityFieldMap.put("aircraftSeatCapacity", flightInfo.getAircraft().getAircraftPayload()!=null? "" + flightInfo.getAircraft().getAircraftSeatCapacity():"");
//		identityFieldMap.put("flightMission", flightInfo.getFlightMission());
//		String iswideornarr="1";
//		if(flightInfo.getAircraft().getIsWideOrNarrow()!=null && flightInfo.getAircraft().getIsWideOrNarrow().equals("L")){
//		    iswideornarr="0";
//		}
//		identityFieldMap.put("isWideOrNarrow", iswideornarr);//判断是不是宽体，是宽体就替换0，不是宽体就替换换1,相当于默认是窄体
//		
////		Map<String, Object> filters = new HashMap<>();
////		filters.put("aircraftRegistration_equal",flightMate.getRegisteration());
////		filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());	
////		AircraftAirlineAlteration aircraftAirlineAlteration = aircraftAirlineAlterationManager.getAircraftAirlineAlteration(flightMate);
////		identityFieldMap.put("isWideOrNarrow",""+aircraftAirlineAlteration.getIsWideOrNarrow());
//	}
	
	/**
	 * 航班配对属性匹配收费项目
	 * @param chargeSubject
	 * @return isMatch
	 */
	public boolean isChargeSubjectMatch(ChargeSubject chargeSubject){
		String expression = "";
		list.clear();
		if (chargeSubject.getChargeRule() != null) {
			expression = spliceExpression(chargeSubject.getChargeRule(), expression);
			String str = expression.substring(0, 4);
			int stringLength = str.length();
			if ("&&".equals(str.trim()) || "||".equals(str.trim())) {
				expression = expression.substring(stringLength, expression.length());
			}
		}else 
			return true;
		return getMathValue(list, expression);
	}
	
	
	/**
	 * 显示表达式
	 * @param chargeSubject
	 * @return 表达式
	 */
	public String showExpression(ChargeSubject chargeSubject){
		String expression = "";
		list.clear();
		if (chargeSubject.getChargeRule() != null) {
			expression = spliceExpression(chargeSubject.getChargeRule(), expression);
		}
		
		return expression;
	}
	/**
     * @param chargeSubject
     * - 收费项目
     * @param fee
     * - 原始金额
     * @param exp
     * - 折扣收费规则表达式
     * @return discount
     * - 最小折扣金额
     */
    public Double getDiscount(ChargeSubject chargeSubject, Double fee,StringBuffer exp){
        
        identityFieldMapCH2Eg.put("起降费", "fee");
        identityFieldMapCH2Eg.put("停场费", "fee");
        identityFieldMapCH2Eg.put("客桥费", "fee");
        identityFieldMapCH2Eg.put("旅客服务费", "fee");
        identityFieldMapCH2Eg.put("旅客行李安检费", "fee");
        identityFieldMapCH2Eg.put("货物邮件安检费", "fee");
        identityFieldMap.put("fee", fee.toString());
        //lushuaifeng 20160907
        /*
        Double currency = 0.00;
        int i = 0;
        if (chargeSubject.getSubjectAndDiscountRuleRelations() == null || chargeSubject.getSubjectAndDiscountRuleRelations().size() == 0) {
            return fee;
        }
        for (ChargeSubjectAndDiscountRuleRelation subjectAndDiscountRuleRelation : chargeSubject.getSubjectAndDiscountRuleRelations()) {
            i++;
            list.clear();
            String expression = "";
            if (subjectAndDiscountRuleRelation != null && subjectAndDiscountRuleRelation.getDiscount() != null) {
                expression = spliceExpression(subjectAndDiscountRuleRelation.getDiscount().getRuleGroup(), expression);
            }else{
                return fee;
            }
            if (getMathValue(list, expression)) {
                exp.append(";");
                exp.append(expression); 
                Double value = calculateCharge(getParameter(subjectAndDiscountRuleRelation.getDiscount().getParameter()),subjectAndDiscountRuleRelation.getDiscount().getFormula(),exp);
                if (i == 1) {
                    currency = value;
                }
                currency = currency <= value ? currency : value;
            }else{
                return fee;
            }
        }
        return currency;
        */
        return fee;
    }
	/**
	 * @param chargeSubject
	 * - 收费项目
	 * @param fee
	 * - 原始金额
	 * @return discount
	 * - 最小折扣金额
	 */
	public Double getDiscount(ChargeSubject chargeSubject, Double fee){
		
		identityFieldMapCH2Eg.put("起降费", "fee");
		identityFieldMapCH2Eg.put("停场费", "fee");
		identityFieldMapCH2Eg.put("客桥费", "fee");
		identityFieldMapCH2Eg.put("旅客服务费", "fee");
		identityFieldMapCH2Eg.put("旅客行李安检费", "fee");
		identityFieldMapCH2Eg.put("货物邮件安检费", "fee");
		identityFieldMap.put("fee", fee.toString());
		 //lushuaifeng 20160907
		/*
		Double currency = 0.00;
		int i = 0;
		if (chargeSubject.getSubjectAndDiscountRuleRelations() == null || chargeSubject.getSubjectAndDiscountRuleRelations().size() == 0) {
			return fee;
		}
		for (ChargeSubjectAndDiscountRuleRelation subjectAndDiscountRuleRelation : chargeSubject.getSubjectAndDiscountRuleRelations()) {
			i++;
			list.clear();
			String expression = "";
			if (subjectAndDiscountRuleRelation != null && subjectAndDiscountRuleRelation.getDiscount() != null) {
				expression = spliceExpression(subjectAndDiscountRuleRelation.getDiscount().getRuleGroup(), expression);
			}else{
				return fee;
			}
			if (getMathValue(list, expression)) {
				Double value = calculateCharge(getParameter(subjectAndDiscountRuleRelation.getDiscount().getParameter()),subjectAndDiscountRuleRelation.getDiscount().getFormula());
				if (i == 1) {
					currency = value;
				}
				currency = currency <= value ? currency : value;
			}else{
				return fee;
			}
		}
		return currency;
		*/
		return fee;
	}
	/**
     * 计算收费项目金额
     * @param chargeSubject
     * @param exp 
     * @return value
     */
    public Double getSubjectCharge(ChargeSubject chargeSubject,StringBuffer exp){
        Double value = calculateCharge(getParameter(chargeSubject.getParameter()), chargeSubject.getFormula(),exp);
        return value;
    }
	/**
	 * 计算收费项目金额
	 * @param chargeSubject
	 * @return value
	 */
	public Double getSubjectCharge(ChargeSubject chargeSubject){
		Double value = calculateCharge(getParameter(chargeSubject.getParameter()), chargeSubject.getFormula());
		return value;
	}
    
    /**
     * 航班配对属性匹配规则
     * @param list
     * - 参数
     * @param option
     * -表达式
     * @return isMatch
     */
    private boolean getMathValue(List<String> list, String option){
    	boolean backFlag = true;
		try {
			for(int i=0; i< list.size();i++){
				String str = identityFieldMap.get(list.get(i));
				if (str == null || str.equals("")) {
					return false;
				}
				option = option.replaceAll(list.get(i), str);
			}
			Object o = engine.eval(option);
			backFlag = Boolean.parseBoolean(o.toString());
		} catch (ScriptException e) {
			return false;
		}
		return backFlag;
    }
    /**
     * 根据公式和参数计算收费
     * @param list
     * - 参数
     * @param option
     * - 表达式
     * * @param retoption
     * - 表达式
     * @return value
     */
    private Double  calculateCharge(List<String> list, String option,StringBuffer exp){
        String tmp=option;
        Double backValue = 0.0;
        try {
            for (int i = 0; i < list.size(); i++) {
                String str = identityFieldMapCH2Eg.get(list.get(i));
                if (str == null || str.equals("") || identityFieldMap.get(str) == null ) {
                    System.out.println("参数" + list.get(i) +"不存在！");
                    return backValue;
                }
                tmp=StringUtils.replace(tmp, "{" + (i + 1) + "}",list.get(i));
                option = StringUtils.replace(option, "{" + (i + 1) + "}", identityFieldMap.get(str));
            }
            exp.append(";");
            exp.append(tmp); 
            exp.append( ";");
            exp.append(option);
            Object o = engine.eval(option);
            backValue = Double.parseDouble(o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return backValue;
    }
    
    /**
     * 根据公式和参数计算收费
     * @param list
     * - 参数
     * @param option
     * - 表达式
     * @return value
     */
    private Double  calculateCharge(List<String> list, String option){
    	
    	Double backValue = 0.0;
    	try {
			for (int i = 0; i < list.size(); i++) {
				String str = identityFieldMapCH2Eg.get(list.get(i));
				if (str == null || str.equals("") || identityFieldMap.get(str) == null ) {
					return backValue;
				}
				option = StringUtils.replace(option, "{" + (i + 1) + "}", identityFieldMap.get(str));
			}
			Object o = engine.eval(option);
			backValue = Double.parseDouble(o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return backValue;
    }
    
    /**
	 * 拼接收费规则表达式
	 * @param chargeRule
	 */
	private String spliceExpression(RuleGroup chargeRule, String expression){

		if (chargeRule != null && chargeRule.getRuleSingleExpressions() != null) {
			int i = 0;
			for (RuleSingleExpression singleExpression : chargeRule.getRuleSingleExpressions()) {
				i++;
				if (chargeRule.getRuleSingleExpressions().size() == 1  || chargeRule.getRuleSingleExpressions().size() == i) {
					expression += getExpression(singleExpression.getIdentity(), singleExpression.getOperator(), singleExpression.getExpressionValue());
					list.add(singleExpression.getIdentity());
				}else{
					expression += getExpression(singleExpression.getIdentity(), singleExpression.getOperator(), singleExpression.getExpressionValue());
					list.add(singleExpression.getIdentity());
					expression += getConnector(chargeRule.getConnector());
				}
				
			}
		}
		if (chargeRule != null && chargeRule.getRuleGroups() != null) {
			for (RuleGroup ruleGroup : chargeRule.getRuleGroups()) {
				expression = expression + getConnector(chargeRule.getConnector()) + "(";
				//递归表达式
				expression += spliceExpression(ruleGroup, "");
				expression = expression + ")";
			}
		}
		return expression;
	}

	private String getConnector(String connector){
		if (connector.equals("and")) {
			return " && ";
		}else if(connector.equals("or")){
			return " || ";
		}else return "";
	}
	
	/**
	 * 表达式转换
	 * @param identity
	 * @param operator
	 * @param expressionValue
	 * @return
	 */
	private String getExpression(String identity, String operator, String expressionValue){
		if (operator.equals("=")) {
			return identity + " == " + expressionValue;
		}else if(operator.equals("in")){
			if (expressionValue != null) {
				String values[] = expressionValue.split(",");
				String value = "(";
				for (int i = 0 ; i < values.length - 1; i++) {
					value += identity + " == " + values[i] + " || ";
				}
				value += identity + " == " + values[values.length - 1] + ")";
				return value;
			}else 
				return identity + " == " + expressionValue;
		}
		return identity + " " + operator + " " + expressionValue;
	}
	
	/**
	 * 收费项目参数分割
	 * @param parameter
	 * @return
	 */
	private List<String> getParameter(String parameter){
		List<String> list = new ArrayList<>();
		if (parameter != null ) {
			String[] selectedParameters = parameter.split(",");
			if (selectedParameters != null) {
				for (int i = 0; i < selectedParameters.length; i++) {
					list.add(selectedParameters[i]);
				}
			}
		}
		return list;
	}
	
	//获取时间间隔的总时间
	private int getDwellTime(Date startTime1, Date endTime1, Date startTime2, Date endTime2){
		int count = 0;
		if ((startTime1 != null && endTime1 != null) || (startTime2 != null && endTime2 != null)) {
			count = (int) Math.ceil(Math.abs(DateUtils.getDifferHour(startTime1, endTime1)) +
					Math.abs(DateUtils.getDifferHour(startTime2, endTime2)));
		}else if(startTime1 != null && endTime1 == null &&startTime2 == null && endTime2 != null){
			count = (int) Math.ceil(Math.abs(DateUtils.getDifferHour(startTime1, endTime2)));
		}
		return count;
	}
	
	private int getDwellTimeFor30Minute(Date startTime1, Date endTime1, Date startTime2, Date endTime2){
		int countFor30Minute = 0;
		if ((startTime1 != null && endTime1 != null) || (startTime2 != null && endTime2 != null)) {
			//半小时
			countFor30Minute = (int) Math.ceil(Math.abs(DateUtils.getDifferHour(startTime1, endTime1) * 2) +
					Math.abs(DateUtils.getDifferHour(startTime2, endTime2) * 2));
		}else if(startTime1 != null && endTime1 == null &&startTime2 == null && endTime2 != null){
			//半小时
			countFor30Minute = (int) Math.ceil(Math.abs(DateUtils.getDifferHour(startTime1, endTime2) * 2));
		}
		return countFor30Minute;
	}
	
	private int calculateCount(String arrival, String departure){
		if (arrival != null && departure != null ) {
			return Integer.valueOf(departure) + Integer.valueOf(arrival);
		}else if (arrival != null && departure == null) {
			return Integer.valueOf(arrival);
		}else if(departure != null && arrival == null){
			return Integer.valueOf(departure);
		}
		return 0;
	}
	
	
	/**
	 * 从flightMateInfo对象中获取数据,封装到Map中
	 * @param fm
	 * @return
	 */
	public static Map<String,String> getFlightMateInfoMap(FlightMateInfo fm){
		Map<String,String> m = new LinkedHashMap<String,String>();
		toFlightInfoMap(fm, m,"");
		return m;
	}
	
	
	private static void toFlightInfoMap(Object o,Map<String,String> m,String parentName){
		try {
			Field [] fs = o.getClass().getDeclaredFields();
			for(Field f : fs){
				f.setAccessible(true);
				if( f.getAnnotation(Transient.class) != null){
					continue;
				}
				String name = f.getName();
				Object value = f.get(o);
				String stringValue;
				
				if(value == null){
					stringValue = "";
				}else if(value instanceof Number){
					stringValue = String.valueOf(value);
				}else if(value instanceof String){
					stringValue = (String)value;
				}else if(value instanceof Date){
					stringValue = DateUtils.Date2String((Date)value);
				}else if(value instanceof Collection){//集合跳过
				    continue;
				}
				else{
					//注意请勿引用自身对象作为属性造成死循环
					toFlightInfoMap(value, m,parentName + name + ".");
					continue;
				}
				m.put(parentName + name, stringValue);
			}
		} catch (Exception e) {
//			如果此处捕获异常可能是对象中存在 非数字\字符串\日期\对象外的 属性引起(如集合\数组等)
//			直接捕获异常后 不进行处理即可跳过该属性
//			e.printStackTrace();
		}
	}
		
	public Double nullToZero(Double num){
		return num==null?0:num;
	}	
}
