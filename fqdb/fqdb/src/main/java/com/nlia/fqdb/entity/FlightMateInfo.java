package com.nlia.fqdb.entity;

import static javax.persistence.CascadeType.REFRESH;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.nlia.fqdb.entity.base.Aircraft;
import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.util.DateUtils;


/**
 * The persistent class for the FLIGHT_MATE_INFO database table.
 * 
 */
@Entity
@Table(name="FLIGHT_MATE_INFO")
public class FlightMateInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name="A_AIRCRAFT_TYPE")
	private String aAircraftType;

	@Column(name="A_FLIGHT_IDENTITY")
	private String aFlightIdentity;

	@Column(name="A_STAND_NUM")
	private String aStandNum;

	@Column(name="AIR_CARGO_WEIGHT_INTERNATIONAL")
	private Double airCargoWeightInternational;

	@Column(name="AIR_CARGO_WEIGHT_INTERNAL")
	private Double airCargoWeightInternal;

	@Column(name="AIR_CONDITION_TRUCK_FEE_COE")
	private String airConditionTruckFeeCoe;

	@OneToOne(cascade = REFRESH)
	@JoinColumn(name = "AIR_LINE_ID")
	private Airline airline;

	@Column(name="AIR_LINE_NAME")
	private String airlineName;

	@Column(name="AIR_MAIL_WEIGHT_INTERNAL")
	private Double airMailWeightInternal;

	@Column(name="AIR_MAIL_WEIGHT_INTERNATIONAL")
	private Double airMailWeightInternational;

	@Column(name="AIR_TRUCK_FEE_COE")
	private String airTruckFeeCoe;

	@Column(name="AIR_TRUCK_TIME")
	private Double airTruckTime;

	@Column(name="AIRCONDITIONING_TIME")
	private Double airconditioningTime;

	@OneToOne(cascade = REFRESH)
	@JoinColumn(name = "AIRCRAFT_ID")
	private Aircraft aircraft;

	@Column(name="AIRCRAFT_PAYLOAD")
	private Double aircraftPayload;

	@Column(name="AIRCRAFT_SEAT_CAPACITY")
	private Double aircraftSeatCapacity;

	@Column(name="AIRCRAFT_TAKE_OFF_WEIGHT")
	private Double aircraftTakeOffWeight;

	@Column(name="AIRDROME_USED_BY_CREW_COUNT")
	private String airdromeUsedByCrewCount;

	@Column(name="AIRDROME_USED_BY_CREW_FEE_COE")
	private String airdromeUsedByCrewFeeCoe;

	@Column(name="AIRDROME_USED_BY_PAS_COUNT")
	private String airdromeUsedByPasCount;

	@Column(name="AIRDROME_USED_BY_PAS_FEE_COE")
	private String airdromeUsedByPasFeeCoe;

	@Column(name="DELAY_CODE")
	private String delayCode;

	@Column(name="APRON_SERVICE_FEE_COE")
	private String apronServiceFeeCoe;

	@Column(name="BASIC_FEE_COE")
	private String basicFeeCoe;

	@Column(name="CAGRO_MAIL_FEE_COE")
	private String cagroMailFeeCoe;
	
	@Column(name="CHILD_INTERNAL")
	private Double childInternal;
	
	@Column(name="BABY_INTERNAL")
	private Double babyInternal;
	
	@Column(name="CHILD_INTERNATIONAL")
	private Double childInternational;
	
	@Column(name="BABY_INTERNATIONAL")
	private Double babyInternational;

	@Column(name="CREATE_TIME")
	private Date createTime;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="D_AIRCRAFT_TYPE")
	private String dAircraftType;

	@Column(name="D_FLIGHT_IDENTITY")
	private String dFlightIdentity;

	@Column(name="D_STAND_NUM")
	private String dStandNum;

	@Column(name="DEICING_TRUCK_FEE_COE")
	private String deicingTruckFeeCoe;

	@Column(name="DEICING_VEHICLE_TIME")
	private Double deicingVehicleTime;

	@Column(name="DOUBLE_AIR_BRAGE_FEE_COE")
	private String doubleAirBrageFeeCoe;

	@Column(name="ELECTRIC_TRUCK_FEE_COE")
	private String electricTruckFeeCoe;

	@Column(name="ELECTRIC_TRUCK_TIME")
	private Double electricTruckTime;

	@Column(name="EXECUTE_TIME")
	private Date executeTime;

	@Column(name="FLIGHT_BASE_ID")
	private Long flightBaseId;

	@Column(name="FLIGHT_COUNTRY_TYPE")
	private String flightCountryType;

	@Column(name="FLIGHT_COUNTRY_TYPE_CHARGE")
	private String flightCountryTypeCharge;

	@Column(name="FLIGHT_COUNTRY_TYPE_QUERY")
	private String flightCountryTypeQuery;

	@Column(name="FLIGHT_DIRECTION")
	private String flightDirection;

	@Column(name="FLIGHT_PART_0")
	private String flightPart0;
	
	@Column(name="FLIGHT_PART_1")
	private String flightPart1;
	
	@Column(name="FLIGHT_PART_2")
	private String flightPart2;

	@Column(name="A_FLIGHT_PROPERTY")
	private String aflightProperty;

	@Column(name="D_FLIGHT_PROPERTY")
	private String dflightProperty;
	
	@Column(name="FLIGHT_ROUTE")
	private String flightRoute;

	@Column(name="FLIGHT_SERVICE_INFO_ID")
	private Long flightServiceInfoId;

	@Column(name="FLIGHT_VOYAGE")
	private String flightVoyage;

	@Column(name="FLITHT_DISPATCH_FEE_COE")
	private String flithtDispatchFeeCoe;

	@Column(name="IS_NIGHT_FLIGHT")
	private String isNightFlight;

	@Column(name="IS_PEAK_FLIGHT")
	private String isPeakFlight;

	@Column(name="IS_VIA")
	private String isVia;

	@Column(name="IS_WIDE_OR_NARROW")
	private String isWideOrNarrow;

	@Column(name="LAND_AND_TAKEOFF_FEE_COE")
	private String landAndTakeoffFeeCoe;

	@Column(name="LAND_TIME")
	private Double landTime;

	@Column(name="LANDED_TIME")
	private Date landedTime;

	@Column(name="LEAD_CAR_USED_COUNT")
	private String leadCarUsedCount;

	@Column(name="LEADCAR_FEE_COE")
	private String leadcarFeeCoe;

	@Column(name="LIFTING_PLATFORM_CAR_FEE_COE")
	private String liftingPlatformCarFeeCoe;

	@Column(name="LIFTING_PLATFORM_CAR_TIME")
	private Double liftingPlatformCarTime;

	@Column(name="LINK_FLIGHT_BASE_ID")
	private Long linkFlightBaseId;
	
	@Column(name="LINK_FLIGHT_MATE_INFO_ID")
	private Long linkFlighMateInfoId;

	@Column(name="LUGGAGE_WEIGHT_INTERNAL")
	private Double luggageWeightInternal;
	
	@Column(name="LUGGAGE_WEIGHT_INTERNATIONAL")
	private Double luggageWeightInternational;

	@Column(name="MATCH_METHOD")
	private String matchMethod;

	@Column(name="MODIFY_TIME")
	private Date modifyTime;

	@Column(name="MODIFY_USER")
	private String modifyUser;
	


	@Column(name="PACKAGING_FACILITY")
	private String packagingFacility;

	@Column(name="PARTING_FEE_COE")
	private String partingFeeCoe;

	@Column(name="PAS_CARGO_SECURITY_CHECK_COE")
	private String pasCargoSecurityCheckCoe;

	@Column(name="PAS_SECURITY_CHECK_FEE_COE")
	private String pasSecurityCheckFeeCoe;

	@Column(name="PAS_SERVICE_FEE_COE")
	private String pasServiceFeeCoe;

	@Column(name="PASSENGER_INTERNAL")
	private Double passengerInternal;

	@Column(name="PASSENGER_INTERNATIONAL")
	private Double passengerInternational;

	@Column(name="PASSENAGE_CAR_FEE_COE")
	private String passenageCarFeeCoe;

	@Column(name="PASSENGER_BRIDGE_NUMBER")
	private String passengerBridgeNumber;

	@Column(name="PASSENGER_BRIDGE_TIME")
	private Double passengerBridgeTime;

	@Column(name="PASSENGER_LUGGAGE_FEE_COE")
	private String passengerLuggageFeeCoe;

	@Column(name="POSTFLIGHT_SERVICE_FEE_COE")
	private String postflightServiceFeeCoe;

	@Column(name="PREFLIGHT_SERVICE_FEE_COE")
	private String preflightServiceFeeCoe;

	@Column(name="PSSSENGER_CAR_USED_TIME")
	private Double psssengerCarUsedTime;

	@Column(name="A_REGISTERATION")
	private String aregisteration;

	@Column(name="D_REGISTERATION")
	private String dregisteration;
	
	private String remark;

	@Column(name="REMOVE_FLAG")
	private String removeFlag;

	@Column(name="ROUTINE_CHECK_FEE_COE")
	private String routineCheckFeeCoe;

	@Column(name="ROUTINE_CHECK_TIME")
	private Double routineCheckTime;

	@Column(name="SINGLE_AIR_BRAGE_FEE_COE")
	private String singleAirBrageFeeCoe;

	@Column(name="SNOW_REMOVAL_TRUCK_FEE_COE")
	private String snowRemovalTruckFeeCoe;

	@Column(name="SPE_CAR_FOR_DISABLED_COUNT")
	private String speCarForDisabledCount;

	@Column(name="SPE_CAR_FOR_DISABLED_FEE_COE")
	private String speCarForDisabledFeeCoe;

	@Column(name="SPECIAL_PLANE")
	private String specialPlane;

	@Column(name="STOWAGE_COMMUNICATION_FEE_COE")
	private String stowageCommunicationFeeCoe;

	@Column(name="SUNDRY_DUTIES_FEE_COE")
	private String sundryDutiesFeeCoe;

	@Column(name="TAKE_OFF_TIME")
	private Date takeOffTime;

	@Column(name="TRACTOR_FEE_COE")
	private String tractorFeeCoe;

	@Column(name="TRACTORS_USED_COUNT")
	private String tractorsUsedCount;

	@Column(name="UNROUTINE_CHECK_FEE_COE")
	private String unroutineCheckFeeCoe;

	@Column(name="UNROUTINE_CHECK_TIME")
	private Double unroutineCheckTime;

	@Column(name="VIA_FLIGHT_SERVICE_FEE_COE")
	private String viaFlightServiceFeeCoe;
	
	@Column(name="VIA_ADULT")
	private Double viaAdult;
	
	@Column(name="VIA_CHILD")
	private Double viaChild;
	
	@Column(name="VIA_BABY")
	private Double viaBaby;
	
	@Column(name="GARBAGE_TRUCK_USE_COUNT")
	private String garbageTruckUseCount;
	
	@Column(name="WATER_TRUCK_USE_COUNT")
	private String waterTruckUseCount;
	
	@Column(name="CESSPOOLAGE_TRUCK_USE_COUNT")
	private String cesspoolageTruckUseCount;
	
    public FlightMateInfo() {
    	this.setCreateTime(new Timestamp(System.currentTimeMillis()));
        this.setMatchMethod("0");
        this.setRemoveFlag("1");
        this.setIsVia("N");
        this.setIsHighDensity("N");
        this.setIsNightFlight("N");
        this.setIsPeakFlight("N");
        this.setIsPackagingFacility("N");
        this.setIsWideOrNarrow("1");
        this.setLandAndTakeoffFeeCoe("0.5");              //起降费系数            
        this.setPartingFeeCoe("0.5");                       //停场费系数            
        this.setPasServiceFeeCoe("1");                   //旅客服务费系数       
        this.setPasSecurityCheckFeeCoe("1");            //旅客行李安检费费系数       
        this.setPasCargoSecurityCheckCoe("1");            //旅客货物安检费费系数
        this.setBasicFeeCoe("0.5");                         //基本收费系数         
        this.setStowageCommunicationFeeCoe("0.5");         //配载通信费系数         
        this.setPackagingFacility("0.5");                       //集装设备收费系数
        this.setPassengerLuggageFeeCoe("0.5");             //旅客和行李费系数        
        this.setCagroMailFeeCoe("0.5");                    //货物邮件费系数         
        this.setApronServiceFeeCoe("0.5");                 //站坪（机坪）服务费系数     
        this.setViaFlightServiceFeeCoe("0.5");            //过站服务费系数          
        this.setPreflightServiceFeeCoe("1");             //航前服务费系数         
        this.setPostflightServiceFeeCoe("1");            //航后服务费系数         
        this.setSundryDutiesFeeCoe("0.5");                 //勤务费系数           
        this.setRoutineCheckFeeCoe("0.5");                 //例行检查费系数         
        this.setLeadcarFeeCoe("1");                       //引导车费系数         
        this.setAirTruckFeeCoe("1");                     //气源车费系数          
        this.setElectricTruckFeeCoe("1");                //电源车费系数          
        this.setTractorFeeCoe("1");                       //牵引车费系数         
        this.setAirConditionTruckFeeCoe("1");           //空调车费系数           
        this.setSnowRemovalTruckFeeCoe("1");            //扫雪车费系数           
        this.setPassenageCarFeeCoe("1");                 //客梯车费系数          
        this.setAirdromeUsedByCrewFeeCoe("1");         //机组摆渡车费系数          
        this.setAirdromeUsedByPasFeeCoe("1");          //旅客摆渡车费系数          
        this.setLiftingPlatformCarFeeCoe("1");          //升降平台车费系数         
        this.setUnroutineCheckFeeCoe("1");               //非例行检查费系数        
        this.setSpeCarForDisabledFeeCoe("1");         //残疾人车费系数            
        this.setFlithtDispatchFeeCoe("1");               //飞机放行费系数         
        this.setDeicingTruckFeeCoe("1");                 //除冰车费系数          
        this.setSingleAirBrageFeeCoe("1");              //单廊桥费系数           
        this.setDoubleAirBrageFeeCoe("1");              //双廊桥费系数           
        this.setUnroutineCheckTime(0D);                  //非例行检查时间默认0
        this.setStat("1");                              //默认生成成功
        
        this.airdromeUsedByCrewCount="0";                  
        this.airdromeUsedByPasCount="0";                   
        
        this.flightVoyage="0";                             
        this.leadCarUsedCount="0";                         
        this.packagingFacility="0";                        
        this.passengerBridgeNumber="0";                    
        this.speCarForDisabledCount="0";                   
        this.tractorsUsedCount="0";                        
        this.garbageTruckUseCount="0";                     
        this.waterTruckUseCount="0";                       
        this.cesspoolageTruckUseCount="0";                 
                                                       
        this.airCargoWeightInternational=0D;              
        this.airCargoWeightInternal=0D;                   
        this.airMailWeightInternal=0D;                    
        this.airMailWeightInternational=0D;               
        this.airTruckTime=0D;                             
        this.airconditioningTime=0D;                      
        this.aircraftPayload=0D;                          
        this.aircraftSeatCapacity=0D;                     
        this.aircraftTakeOffWeight=0D;                    
        this.childInternal=0D;                            
        this.babyInternal=0D;                             
        this.childInternational=0D;                       
        this.babyInternational=0D;                        
        this.deicingVehicleTime=0D;                       
        this.electricTruckTime=0D;                        
        this.landTime=0D;                                 
        this.liftingPlatformCarTime=0D;                   
        this.luggageWeightInternal=0D;                    
        this.luggageWeightInternational=0D;               
        this.passengerInternal=0D;                        
        this.passengerInternational=0D;                   
        this.passengerBridgeTime=0D;                      
        this.psssengerCarUsedTime=0D;                     
        this.routineCheckTime=0D;                         
        this.unroutineCheckTime=0D;                       
        this.viaAdult=0D;                                 
        this.viaChild=0D;                                 
        this.viaBaby=0D;                                  
        this.bridgeAirconditionUsedTime=0D;
        this.bridgeElectricUsedTime=0D;
        this.deicingTruckUsedTime=0D;
        this.firstClassCount=0D;
        this.vipCount=0D;
        this.vipFirstAirdromeUsedCount=0D;
        this.speBoardCarUsedCount=0D;
        this.passengerLoadFactor=0.00;
        this.passengerBridgeNumber="1";
        this.abnormalFlag="0";
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAAircraftType() {
		return this.aAircraftType;
	}

	public void setAAircraftType(String aAircraftType) {
		this.aAircraftType = aAircraftType;
	}

	public String getAFlightIdentity() {
		return this.aFlightIdentity;
	}

	public void setAFlightIdentity(String aFlightIdentity) {
		this.aFlightIdentity = aFlightIdentity;
	}

	public String getAStandNum() {
		return this.aStandNum;
	}

	public void setAStandNum(String aStandNum) {
		this.aStandNum = aStandNum;
	}

	public Double getAirCargoWeightInternational() {
		return this.airCargoWeightInternational;
	}

	public void setAirCargoWeightInternational(Double airCargoWeightInternational) {
		this.airCargoWeightInternational = airCargoWeightInternational;
	}

	public Double getAirCargoWeightInternal() {
		return this.airCargoWeightInternal;
	}

	public void setAirCargoWeightInternal(Double airCargoWeightInternal) {
		this.airCargoWeightInternal = airCargoWeightInternal;
	}

	public String getAirConditionTruckFeeCoe() {
		return this.airConditionTruckFeeCoe;
	}

	public void setAirConditionTruckFeeCoe(String airConditionTruckFeeCoe) {
		this.airConditionTruckFeeCoe = airConditionTruckFeeCoe;
	}

	public Airline getAirLine() {
		return this.airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public String getAirlineName() {
		return this.airlineName;
	}

	public void setAirlineName(String airLineName) {
		this.airlineName = airLineName;
	}

	public Double getAirMailWeightInternal() {
		return this.airMailWeightInternal;
	}

	public void setAirMailWeightInternal(Double airMailWeightInternal) {
		this.airMailWeightInternal = airMailWeightInternal;
	}

	public Double getAirMailWeightInternational() {
		return this.airMailWeightInternational;
	}

	public void setAirMailWeightInternational(Double airMailWeightInternational) {
		this.airMailWeightInternational = airMailWeightInternational;
	}

	public String getAirTruckFeeCoe() {
		return this.airTruckFeeCoe;
	}

	public void setAirTruckFeeCoe(String airTruckFeeCoe) {
		this.airTruckFeeCoe = airTruckFeeCoe;
	}

	public Double getAirTruckTime() {
		return this.airTruckTime;
	}

	public void setAirTruckTime(Double airTruckTime) {
		this.airTruckTime = airTruckTime;
	}

	public Double getAirconditioningTime() {
		return this.airconditioningTime;
	}

	public void setAirconditioningTime(Double airconditioningTime) {
		this.airconditioningTime = airconditioningTime;
	}

	public Aircraft getAircraft() {
		return this.aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public Double getAircraftPayload() {
		return this.aircraftPayload;
	}

	public void setAircraftPayload(Double aircraftPayload) {
		this.aircraftPayload = aircraftPayload;
	}

	public Double getAircraftSeatCapacity() {
		return this.aircraftSeatCapacity;
	}

	public void setAircraftSeatCapacity(Double aircraftSeatCapacity) {
		this.aircraftSeatCapacity = aircraftSeatCapacity;
	}

	public Double getAircraftTakeOffWeight() {
		return this.aircraftTakeOffWeight;
	}

	public void setAircraftTakeOffWeight(Double aircraftTakeOffWeight) {
		this.aircraftTakeOffWeight = aircraftTakeOffWeight;
	}

	public String getAirdromeUsedByCrewCount() {
		return this.airdromeUsedByCrewCount;
	}

	public void setAirdromeUsedByCrewCount(String airdromeUsedByCrewCount) {
		this.airdromeUsedByCrewCount = airdromeUsedByCrewCount;
	}

	public String getAirdromeUsedByCrewFeeCoe() {
		return this.airdromeUsedByCrewFeeCoe;
	}

	public void setAirdromeUsedByCrewFeeCoe(String airdromeUsedByCrewFeeCoe) {
		this.airdromeUsedByCrewFeeCoe = airdromeUsedByCrewFeeCoe;
	}

	public String getAirdromeUsedByPasCount() {
		return this.airdromeUsedByPasCount;
	}

	public void setAirdromeUsedByPasCount(String airdromeUsedByPasCount) {
		this.airdromeUsedByPasCount = airdromeUsedByPasCount;
	}

	public String getAirdromeUsedByPasFeeCoe() {
		return this.airdromeUsedByPasFeeCoe;
	}

	public void setAirdromeUsedByPasFeeCoe(String airdromeUsedByPasFeeCoe) {
		this.airdromeUsedByPasFeeCoe = airdromeUsedByPasFeeCoe;
	}

	public String getDelayCode() {
		return this.delayCode;
	}

	public void setDelayCode(String delayCode) {
		this.delayCode = delayCode;
	}

	public String getApronServiceFeeCoe() {
		return this.apronServiceFeeCoe;
	}

	public void setApronServiceFeeCoe(String apronServiceFeeCoe) {
		this.apronServiceFeeCoe = apronServiceFeeCoe;
	}

	public String getBasicFeeCoe() {
		return this.basicFeeCoe;
	}

	public void setBasicFeeCoe(String basicFeeCoe) {
		this.basicFeeCoe = basicFeeCoe;
	}

	public String getCagroMailFeeCoe() {
		return this.cagroMailFeeCoe;
	}

	public void setCagroMailFeeCoe(String cagroMailFeeCoe) {
		this.cagroMailFeeCoe = cagroMailFeeCoe;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDAircraftType() {
		return this.dAircraftType;
	}

	public void setDAircraftType(String dAircraftType) {
		this.dAircraftType = dAircraftType;
	}

	public String getDFlightIdentity() {
		return this.dFlightIdentity;
	}

	public void setDFlightIdentity(String dFlightIdentity) {
		this.dFlightIdentity = dFlightIdentity;
	}

	public String getDStandNum() {
		return this.dStandNum;
	}

	public void setDStandNum(String dStandNum) {
		this.dStandNum = dStandNum;
	}

	public String getDeicingTruckFeeCoe() {
		return this.deicingTruckFeeCoe;
	}

	public void setDeicingTruckFeeCoe(String deicingTruckFeeCoe) {
		this.deicingTruckFeeCoe = deicingTruckFeeCoe;
	}

	public Double getDeicingVehicleTime() {
		return this.deicingVehicleTime;
	}

	public void setDeicingVehicleTime(Double deicingVehicleTime) {
		this.deicingVehicleTime = deicingVehicleTime;
	}

	public String getDoubleAirBrageFeeCoe() {
		return this.doubleAirBrageFeeCoe;
	}

	public void setDoubleAirBrageFeeCoe(String doubleAirBrageFeeCoe) {
		this.doubleAirBrageFeeCoe = doubleAirBrageFeeCoe;
	}

	public String getElectricTruckFeeCoe() {
		return this.electricTruckFeeCoe;
	}

	public void setElectricTruckFeeCoe(String electricTruckFeeCoe) {
		this.electricTruckFeeCoe = electricTruckFeeCoe;
	}

	public Double getElectricTruckTime() {
		return this.electricTruckTime;
	}

	public void setElectricTruckTime(Double electricTruckTime) {
		this.electricTruckTime = electricTruckTime;
	}

	public Date getExecuteTime() {
		return this.executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
		this.executeTime2s=DateUtils.Date2String(executeTime);
	}

	public Long getFlightBaseId() {
		return this.flightBaseId;
	}

	public void setFlightBaseId(Long flightBaseId) {
		this.flightBaseId = flightBaseId;
	}

	public String getFlightCountryType() {
		return this.flightCountryType;
	}

	public void setFlightCountryType(String flightCountryType) {
		this.flightCountryType = flightCountryType;
	}

	public String getFlightCountryTypeCharge() {
		return this.flightCountryTypeCharge;
	}

	public void setFlightCountryTypeCharge(String flightCountryTypeCharge) {
		this.flightCountryTypeCharge = flightCountryTypeCharge;
	}

	public String getFlightCountryTypeQuery() {
		return this.flightCountryTypeQuery;
	}

	public void setFlightCountryTypeQuery(String flightCountryTypeQuery) {
		this.flightCountryTypeQuery = flightCountryTypeQuery;
	}

	public String getFlightDirection() {
		return this.flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}

	public String getFlightPart0() {
		return flightPart0;
	}

	public void setFlightPart0(String flightPart0) {
		this.flightPart0 = flightPart0;
	}

	public String getFlightPart1() {
		return flightPart1;
	}

	public void setFlightPart1(String flightPart1) {
		this.flightPart1 = flightPart1;
	}

	public String getFlightPart2() {
		return flightPart2;
	}

	public void setFlightPart2(String flightPart2) {
		this.flightPart2 = flightPart2;
	}


	public String getAFlightProperty() {
		return this.aflightProperty;
	}

	public void setAFlightProperty(String aflightProperty) {
		this.aflightProperty = aflightProperty;
	}

    public String getDFlightProperty() {
        return this.dflightProperty;
    }

    public void setDFlightProperty(String dflightProperty) {
        this.dflightProperty = dflightProperty;
    }

	public String getFlightRoute() {
		return this.flightRoute;
	}

	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}

	public Long getFlightServiceInfoId() {
		return this.flightServiceInfoId;
	}

	public void setFlightServiceInfoId(Long flightServiceInfoId) {
		this.flightServiceInfoId = flightServiceInfoId;
	}

	public String getFlightVoyage() {
		return this.flightVoyage;
	}

	public void setFlightVoyage(String flightVoyage) {
		this.flightVoyage = flightVoyage;
	}

	public String getFlithtDispatchFeeCoe() {
		return this.flithtDispatchFeeCoe;
	}

	public void setFlithtDispatchFeeCoe(String flithtDispatchFeeCoe) {
		this.flithtDispatchFeeCoe = flithtDispatchFeeCoe;
	}

	public String getIsNightFlight() {
		return this.isNightFlight;
	}

	public void setIsNightFlight(String isNightFlight) {
		this.isNightFlight = isNightFlight;
	}

	public String getIsPeakFlight() {
		return this.isPeakFlight;
	}

	public void setIsPeakFlight(String isPeakFlight) {
		this.isPeakFlight = isPeakFlight;
	}

	public String getIsVia() {
		return this.isVia;
	}

	public void setIsVia(String isVia) {
		this.isVia = isVia;
	}

	public String getIsWideOrNarrow() {
		return this.isWideOrNarrow;
	}

	public void setIsWideOrNarrow(String isWideOrNarrow) {
		this.isWideOrNarrow = isWideOrNarrow;
	}

	public String getLandAndTakeoffFeeCoe() {
		return this.landAndTakeoffFeeCoe;
	}

	public void setLandAndTakeoffFeeCoe(String landAndTakeoffFeeCoe) {
		this.landAndTakeoffFeeCoe = landAndTakeoffFeeCoe;
	}

	public Double getLandTime() {
		return this.landTime;
	}

	public void setLandTime(Double landTime) {
		this.landTime = landTime;
	}

	public Date getLandedTime() {
		return this.landedTime;
	}

	public void setLandedTime(Date landedTime) {
		this.landedTime = landedTime;
	}

	public String getLeadCarUsedCount() {
		return this.leadCarUsedCount;
	}

	public void setLeadCarUsedCount(String leadCarUsedCount) {
		this.leadCarUsedCount = leadCarUsedCount;
	}

	public String getLeadcarFeeCoe() {
		return this.leadcarFeeCoe;
	}

	public void setLeadcarFeeCoe(String leadcarFeeCoe) {
		this.leadcarFeeCoe = leadcarFeeCoe;
	}

	public String getLiftingPlatformCarFeeCoe() {
		return this.liftingPlatformCarFeeCoe;
	}

	public void setLiftingPlatformCarFeeCoe(String liftingPlatformCarFeeCoe) {
		this.liftingPlatformCarFeeCoe = liftingPlatformCarFeeCoe;
	}

	public Double getLiftingPlatformCarTime() {
		return this.liftingPlatformCarTime;
	}

	public void setLiftingPlatformCarTime(Double liftingPlatformCarTime) {
		this.liftingPlatformCarTime = liftingPlatformCarTime;
	}

	public Long getLinkFlightBaseId() {
		return this.linkFlightBaseId;
	}

	public void setLinkFlightBaseId(Long linkFlightBaseId) {
		this.linkFlightBaseId = linkFlightBaseId;
	}

	public Long getLinkFlighMateInfoId() {
		return linkFlighMateInfoId;
	}

	public void setLinkFlighMateInfoId(Long linkFlighMateInfoId) {
		this.linkFlighMateInfoId = linkFlighMateInfoId;
	}
	
	public String getMatchMethod() {
		return this.matchMethod;
	}

	public void setMatchMethod(String matchMethod) {
		this.matchMethod = matchMethod;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getPackagingFacility() {
		return this.packagingFacility;
	}

	public void setPackagingFacility(String packagingFacility) {
		this.packagingFacility = packagingFacility;
	}

	public String getPartingFeeCoe() {
		return this.partingFeeCoe;
	}

	public void setPartingFeeCoe(String partingFeeCoe) {
		this.partingFeeCoe = partingFeeCoe;
	}

	public String getPasCargoSecurityCheckCoe() {
		return this.pasCargoSecurityCheckCoe;
	}

	public void setPasCargoSecurityCheckCoe(String pasCargoSecurityCheckCoe) {
		this.pasCargoSecurityCheckCoe = pasCargoSecurityCheckCoe;
	}

	public String getPasSecurityCheckFeeCoe() {
		return this.pasSecurityCheckFeeCoe;
	}

	public void setPasSecurityCheckFeeCoe(String pasSecurityCheckFeeCoe) {
		this.pasSecurityCheckFeeCoe = pasSecurityCheckFeeCoe;
	}

	public String getPasServiceFeeCoe() {
		return this.pasServiceFeeCoe;
	}

	public void setPasServiceFeeCoe(String pasServiceFeeCoe) {
		this.pasServiceFeeCoe = pasServiceFeeCoe;
	}

	public Double getPassengerInternal() {
		return this.passengerInternal;
	}

	public void setPassengerInternal(Double passengerInternal) {
		this.passengerInternal = passengerInternal;
	}

	public Double getPassengerInternational() {
		return this.passengerInternational;
	}

	public void setPassengerInternational(Double passengerInternational) {
		this.passengerInternational = passengerInternational;
	}

	public String getPassenageCarFeeCoe() {
		return this.passenageCarFeeCoe;
	}

	public void setPassenageCarFeeCoe(String passenageCarFeeCoe) {
		this.passenageCarFeeCoe = passenageCarFeeCoe;
	}

	public String getPassengerBridgeNumber() {
		return this.passengerBridgeNumber;
	}

	public void setPassengerBridgeNumber(String passengerBridgeNumber) {
		this.passengerBridgeNumber = passengerBridgeNumber;
	}

	public Double getPassengerBridgeTime() {
		return this.passengerBridgeTime;
	}

	public void setPassengerBridgeTime(Double passengerBridgeTime) {
		this.passengerBridgeTime = passengerBridgeTime;
	}

	public String getPassengerLuggageFeeCoe() {
		return this.passengerLuggageFeeCoe;
	}

	public void setPassengerLuggageFeeCoe(String passengerLuggageFeeCoe) {
		this.passengerLuggageFeeCoe = passengerLuggageFeeCoe;
	}

	public String getPostflightServiceFeeCoe() {
		return this.postflightServiceFeeCoe;
	}

	public void setPostflightServiceFeeCoe(String postflightServiceFeeCoe) {
		this.postflightServiceFeeCoe = postflightServiceFeeCoe;
	}

	public String getPreflightServiceFeeCoe() {
		return this.preflightServiceFeeCoe;
	}

	public void setPreflightServiceFeeCoe(String preflightServiceFeeCoe) {
		this.preflightServiceFeeCoe = preflightServiceFeeCoe;
	}

	public Double getPsssengerCarUsedTime() {
		return this.psssengerCarUsedTime;
	}

	public void setPsssengerCarUsedTime(Double psssengerCarUsedTime) {
		this.psssengerCarUsedTime = psssengerCarUsedTime;
	}

	public String getAregisteration() {
		return this.aregisteration;
	}

	public void setAregisteration(String aregisteration) {
		this.aregisteration = aregisteration;
	}

    public String getDregisteration() {
        return this.dregisteration;
    }

    public void setDregisteration(String dregisteration) {
        this.dregisteration = dregisteration;
    }

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemoveFlag() {
		return this.removeFlag;
	}

	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}

	public String getRoutineCheckFeeCoe() {
		return this.routineCheckFeeCoe;
	}

	public void setRoutineCheckFeeCoe(String routineCheckFeeCoe) {
		this.routineCheckFeeCoe = routineCheckFeeCoe;
	}

	public Double getRoutineCheckTime() {
		return this.routineCheckTime;
	}

	public void setRoutineCheckTime(Double routineCheckTime) {
		this.routineCheckTime = routineCheckTime;
	}

	public String getSingleAirBrageFeeCoe() {
		return this.singleAirBrageFeeCoe;
	}

	public void setSingleAirBrageFeeCoe(String singleAirBrageFeeCoe) {
		this.singleAirBrageFeeCoe = singleAirBrageFeeCoe;
	}

	public String getSnowRemovalTruckFeeCoe() {
		return this.snowRemovalTruckFeeCoe;
	}

	public void setSnowRemovalTruckFeeCoe(String snowRemovalTruckFeeCoe) {
		this.snowRemovalTruckFeeCoe = snowRemovalTruckFeeCoe;
	}

	public String getSpeCarForDisabledCount() {
		return this.speCarForDisabledCount;
	}

	public void setSpeCarForDisabledCount(String speCarForDisabledCount) {
		this.speCarForDisabledCount = speCarForDisabledCount;
	}

	public String getSpeCarForDisabledFeeCoe() {
		return this.speCarForDisabledFeeCoe;
	}

	public void setSpeCarForDisabledFeeCoe(String speCarForDisabledFeeCoe) {
		this.speCarForDisabledFeeCoe = speCarForDisabledFeeCoe;
	}

	public String getSpecialPlane() {
		return this.specialPlane;
	}

	public void setSpecialPlane(String specialPlane) {
		this.specialPlane = specialPlane;
	}

	public String getStowageCommunicationFeeCoe() {
		return this.stowageCommunicationFeeCoe;
	}

	public void setStowageCommunicationFeeCoe(String stowageCommunicationFeeCoe) {
		this.stowageCommunicationFeeCoe = stowageCommunicationFeeCoe;
	}

	public String getSundryDutiesFeeCoe() {
		return this.sundryDutiesFeeCoe;
	}

	public void setSundryDutiesFeeCoe(String sundryDutiesFeeCoe) {
		this.sundryDutiesFeeCoe = sundryDutiesFeeCoe;
	}

	public Date getTakeOffTime() {
		return this.takeOffTime;
	}

	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	public String getTractorFeeCoe() {
		return this.tractorFeeCoe;
	}

	public void setTractorFeeCoe(String tractorFeeCoe) {
		this.tractorFeeCoe = tractorFeeCoe;
	}

	public String getTractorsUsedCount() {
		return this.tractorsUsedCount;
	}

	public void setTractorsUsedCount(String tractorsUsedCount) {
		this.tractorsUsedCount = tractorsUsedCount;
	}

	public String getUnroutineCheckFeeCoe() {
		return this.unroutineCheckFeeCoe;
	}

	public void setUnroutineCheckFeeCoe(String unroutineCheckFeeCoe) {
		this.unroutineCheckFeeCoe = unroutineCheckFeeCoe;
	}

	public Double getUnroutineCheckTime() {
		return this.unroutineCheckTime;
	}

	public void setUnroutineCheckTime(Double unroutineCheckTime) {
		this.unroutineCheckTime = unroutineCheckTime;
	}

	public String getViaFlightServiceFeeCoe() {
		return this.viaFlightServiceFeeCoe;
	}

	public void setViaFlightServiceFeeCoe(String viaFlightServiceFeeCoe) {
		this.viaFlightServiceFeeCoe = viaFlightServiceFeeCoe;
	}
	
	public String getGarbageTruckUseCount() {
		return garbageTruckUseCount;
	}

	public void setGarbageTruckUseCount(String garbageTruckUseCount) {
		this.garbageTruckUseCount = garbageTruckUseCount;
	}

	public String getWaterTruckUseCount() {
		return waterTruckUseCount;
	}

	public void setWaterTruckUseCount(String waterTruckUseCount) {
		this.waterTruckUseCount = waterTruckUseCount;
	}

	public String getCesspoolageTruckUseCount() {
		return cesspoolageTruckUseCount;
	}

	public void setCesspoolageTruckUseCount(String cesspoolageTruckUseCount) {
		this.cesspoolageTruckUseCount = cesspoolageTruckUseCount;
	}
	
	public Double getChildInternal() {
		return childInternal;
	}

	public void setChildInternal(Double childInternal) {
		this.childInternal = childInternal;
	}

	public Double getBabyInternal() {
		return babyInternal;
	}

	public void setBabyInternal(Double babyInternal) {
		this.babyInternal = babyInternal;
	}

	public Double getChildInternational() {
		return childInternational;
	}

	public void setChildInternational(Double childInternational) {
		this.childInternational = childInternational;
	}

	public Double getBabyInternational() {
		return babyInternational;
	}
	
	public void setBabyInternational(Double babyInternational) {
		this.babyInternational = babyInternational;
	}
	
	public Double getViaAdult() {
		return viaAdult;
	}

	public void setViaAdult(Double viaAdult) {
		this.viaAdult = viaAdult;
	}

	public Double getViaChild() {
		return viaChild;
	}

	public void setViaChild(Double viaChild) {
		this.viaChild = viaChild;
	}

	public Double getViaBaby() {
		return viaBaby;
	}

	public void setViaBaby(Double viaBaby) {
		this.viaBaby = viaBaby;
	}
	
	public Double getLuggageWeightInternal() {
		return luggageWeightInternal;
	}

	public void setLuggageWeightInternal(Double luggageWeightInternal) {
		this.luggageWeightInternal = luggageWeightInternal;
	}

	public Double getLuggageWeightInternational() {
		return luggageWeightInternational;
	}

	public void setLuggageWeightInternational(Double luggageWeightInternational) {
		this.luggageWeightInternational = luggageWeightInternational;
	}

	@Transient
    //校验出错信息
    private String verifyDescription;

    public String getVerifyDescription() {
        return verifyDescription;
    }
    
    @Transient
    /*
     * 出错位置
     * 如{1:{1;2}}　第一行有，第一个第二个单元格出错
     */
    private Map<Integer,ArrayList<Integer>> errorMessage;

    public Map<Integer, ArrayList<Integer>> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Map<Integer, ArrayList<Integer>> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setVerifyDescription(String verifyDescription) {
        this.verifyDescription = verifyDescription;
    }
	
    
    /*
     * 高密度座位标志
     */
    @Column(name = "IS_HIGH_DENSITY", length = 1)
    private String isHighDensity;

    public String getIsHighDensity() {
        return isHighDensity;
    }

    public void setIsHighDensity(String isHighDensity) {
        this.isHighDensity = isHighDensity;
    }
    
    /*
     * 是否集装设备
     */
    @Column(name="IS_PACKAGING_FACILITY", length = 1)
    private String isPackagingFacility;

    public String getIsPackagingFacility() {
        return isPackagingFacility;
    }

    public void setIsPackagingFacility(String isPackagingFacility) {
        this.isPackagingFacility = isPackagingFacility;
    }
    /*
     * 始发站
     */
    @Column(name="iata_origin_airport", length = 20)
    private String iataOriginAirport;
    public String getIataOriginAirport() {
        return iataOriginAirport;
    }

    public void setIataOriginAirport(String iataOriginAirport) {
        this.iataOriginAirport = iataOriginAirport;
    }

    /*
     * 目的站
     */
    @Column(name="iata_destination_airport", length = 20)
    private String iataDestinationAirport;

    public String getIataDestinationAirport() {
        return iataDestinationAirport;
    }

    public void setIataDestinationAirport(String iataDestinationAirport) {
        this.iataDestinationAirport = iataDestinationAirport;
    }
    
    //add by march 20151110 飞机服务类型PRE("0"), AFT("1"), TR("2");
    @Column(name="AIRCRAFT_SERVICE")
    private String aircraftService;
    
    public String getAircraftService() {
        return aircraftService;
    }

    public void setAircraftService(String aircraftService) {
        this.aircraftService = aircraftService;
    }
    //add by march 20151127 增加状态位 STAT 0：生成异常 ；1：生成OK； 10：计算收费异常；
    @Column(name="STAT", length = 20)
    private String stat;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
    
    //add by march 20151203 桥载空调使用时间,桥载电源使用时间,除冰车使用时间
    @Column(name="BRIDGE_AIRCONDITION_USED_TIME")
    private Double bridgeAirconditionUsedTime;
    
    @Column(name="BRIDGE_ELECTRIC_USED_TIME")
    private Double bridgeElectricUsedTime;

    @Column(name="DEICING_TRUCK_USED_TIME")
    private Double deicingTruckUsedTime;

    public Double getBridgeAirconditionUsedTime() {
        return bridgeAirconditionUsedTime;
    }

    public void setBridgeAirconditionUsedTime(Double bridgeAirconditionUsedTime) {
        this.bridgeAirconditionUsedTime = bridgeAirconditionUsedTime;
    }

    public Double getBridgeElectricUsedTime() {
        return bridgeElectricUsedTime;
    }

    public void setBridgeElectricUsedTime(Double bridgeElectricUsedTime) {
        this.bridgeElectricUsedTime = bridgeElectricUsedTime;
    }
    
    public Double getDeicingTruckUsedTime() {
        return deicingTruckUsedTime;
    }

    public void setDeicingTruckUsedTime(Double deicingTruckUsedTime) {
        this.deicingTruckUsedTime = deicingTruckUsedTime;
    }
    
    @Transient
    private String executeTime2s;

    public String getExecuteTime2s() {
        this.executeTime2s=DateUtils.Date2String(executeTime);
        return executeTime2s;
    }

    public void setExecuteTime2s(String executeTime2s) {
        this.executeTime2s = executeTime2s;
    }
    
    //add by march 20151214
    /*
     * 计划执行日
     */
    @Column(name="SCHEDULED_TIME")
    private Date scheduledTime;

    /*
     * 计划到达时间
     */
    @Column(name="SCHEDULED_LANDED_TIME")
    private Date scheduledLandedTime;

    /*
     * 计划起飞时间
     */
    @Column(name="SCHEDULED_TAKE_OFF_TIME") 
    private Date scheduledTakeOffTime;

    /*
     * 进港远近机位标识
     */
    @Column(name="A_STAND_FLAG")
    private String aStandFlag;

    /*
     * 离港远近机位标识
     */
    @Column(name="D_STAND_FLAG") 
    private String dStandFlag;

    /*
     * vip人数
     */
    @Column(name="VIP_COUNT")
    private Double vipCount;

    /*
     * 头等舱人数
     */
    @Column(name="FIRST_CLASS_COUNT")
    private Double firstClassCount;

    /*
     * vip头等舱摆渡车人数
     */
    @Column(name="VIP_FIRST_AIRDROME_USED_COUNT")
    private Double vipFirstAirdromeUsedCount;

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Date getScheduledLandedTime() {
        return scheduledLandedTime;
    }

    public void setScheduledLandedTime(Date scheduledLandedTime) {
        this.scheduledLandedTime = scheduledLandedTime;
    }

    public Date getScheduledTakeOffTime() {
        return scheduledTakeOffTime;
    }

    public void setScheduledTakeOffTime(Date scheduledTakeOffTime) {
        this.scheduledTakeOffTime = scheduledTakeOffTime;
    }

    public Double getVipCount() {
        return vipCount;
    }

    public void setVipCount(Double vipCount) {
        this.vipCount = vipCount;
    }

    public Double getFirstClassCount() {
        return firstClassCount;
    }

    public void setFirstClassCount(Double firstClassCount) {
        this.firstClassCount = firstClassCount;
    }

    public Double getVipFirstAirdromeUsedCount() {
        return vipFirstAirdromeUsedCount;
    }

    public void setVipFirstAirdromeUsedCount(Double vipFirstAirdromeUsedCount) {
        this.vipFirstAirdromeUsedCount = vipFirstAirdromeUsedCount;
    }
    
    public String getAStandFlag() {
        return aStandFlag;
    }

    public void setAStandFlag(String aStandFlag) {
        this.aStandFlag = aStandFlag;
    }

    public String getDStandFlag() {
        return dStandFlag;
    }

    public void setDStandFlag(String dStandFlag) {
        this.dStandFlag = dStandFlag;
    }
    /*
     * 残疾人登机车
     */
    @Column(name="SPE_BOARD_CAR_USED_COUNT")
    private Double speBoardCarUsedCount;

    public Double getSpeBoardCarUsedCount() {
        return speBoardCarUsedCount;
    }

    public void setSpeBoardCarUsedCount(Double speBoardCarUsedCount) {
        this.speBoardCarUsedCount = speBoardCarUsedCount;
    }
    
    /*
     * 客座率
     */
    @Column(name="PASSENGERLOAD_FACTOR")
    private double passengerLoadFactor;

    public double getPassengerLoadFactor() {
        return passengerLoadFactor;
    }

    public void setPassengerLoadFactor(double passengerLoadFactor) {
        this.passengerLoadFactor = passengerLoadFactor;
    }
    
    @Column(name="AAA_ID")
    private Long aircraftAirlineAlterationId;

    public Long getAircraftAirlineAlterationId() {
        return aircraftAirlineAlterationId;
    }

    public void setAircraftAirlineAlterationId(Long aircraftAirlineAlterationId) {
        this.aircraftAirlineAlterationId = aircraftAirlineAlterationId;
    }
    
    /*
     * 过站行李重量
     */
    @Column(name="LUGGAGE_WEIGHT_VIA")
    private Double luggageWeightVia;
    /*
     * 过站邮件重量
     */
    @Column(name="AIR_MAIL_WEIGHT_VIA")
    private Double airMailWeightVia;
    /*
     * 国内行李数量
     */
    @Column(name="LUGGAGE_NUM_INTERNAL")
    private Double luggageNumInternal;
    /*
     * 国际行李数量
     */
    @Column(name="LUGGAGE_NUM_INTERNATIONAL")
    private Double luggageNumInternational;
    /*
     * 过站行李数量
     */
    @Column(name="LUGGAGE_NUM_VIA")
    private Double luggageNumVia;
    /*
     * 过站货物重量
     */
    @Column(name="AIR_CARGO_WEIGHT_VIA")
    private Double airCargoWeightVia;

    public Double getLuggageWeightVia() {
        return luggageWeightVia;
    }

    public void setLuggageWeightVia(Double luggageWeightVia) {
        this.luggageWeightVia = luggageWeightVia;
    }

    public Double getAirMailWeightVia() {
        return airMailWeightVia;
    }

    public void setAirMailWeightVia(Double airMailWeightVia) {
        this.airMailWeightVia = airMailWeightVia;
    }

    public Double getLuggageNumInternal() {
        return luggageNumInternal;
    }

    public void setLuggageNumInternal(Double luggageNumInternal) {
        this.luggageNumInternal = luggageNumInternal;
    }

    public Double getLuggageNumInternational() {
        return luggageNumInternational;
    }

    public void setLuggageNumInternational(Double luggageNumInternational) {
        this.luggageNumInternational = luggageNumInternational;
    }

    public Double getLuggageNumVia() {
        return luggageNumVia;
    }

    public void setLuggageNumVia(Double luggageNumVia) {
        this.luggageNumVia = luggageNumVia;
    }

    public Double getAirCargoWeightVia() {
        return airCargoWeightVia;
    }

    public void setAirCargoWeightVia(Double airCargoWeightVia) {
        this.airCargoWeightVia = airCargoWeightVia;
    }
    
   /*
    * 不正常标志 0 正常 1 备降 2 返航
    */
    @Column(name="ABNORMAL_FLAG")
    private String abnormalFlag;

    public String getAbnormalFlag() {
        return abnormalFlag;
    }

    public void setAbnormalFlag(String abnormalFlag) {
        this.abnormalFlag = abnormalFlag;
    }
    
    
    //航空公司代理
    @Column(name="AIRLINE_HANDLER")
    private String airlineHandler;

    public String getAirlineHandler() {
        return airlineHandler;
    }

    public void setAirlineHandler(String airlineHandler) {
        this.airlineHandler = airlineHandler;
    }
    
    /*
     * 航段0类型,0:国内;1:国外
     */
     @Column(name="FLIGHT_PART_0_TYPE")
     private String flightPart0Type;

     public String getFlightPart0Type() {
         return flightPart0Type;
     }

     public void setFlightPart0Type(String flightPart0Type) {
         this.flightPart0Type = flightPart0Type;
     }
     
}