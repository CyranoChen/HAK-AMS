package com.nlia.fqdb.entity;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "FLIGHT_VIEW")
public class FlightView implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "CREATE_USER", length = 20)
	private String createUser;
	@Column(name = "CREATE_TIME")
	@Temporal(TIMESTAMP)
	private Date createTime;
	@Column(name = "MODIFY_USER", length = 20)
	private String modifyUser;
	@Temporal(TIMESTAMP)
	@Column(name = "MODIFY_TIME")
	private Date modifyTime;
	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

	/* 航班计划日期 */
	@Column(name = "FLIGHT_SCHEDULED_DATE")
	@Temporal(DATE)
	private Date flightScheduledDate;
	/* 航班号全称 */
	@Column(name = "FLIGHT_IDENTITY", length = 20)
	private String flightIdentity;
	/* 航班方向 */
	@Column(name = "FLIGHT_DIRECTION", length = 20)
	private String flightDirection;
	/* 机场三字码 */
	@Column(name = "AIRPORT_IATA_CODE", length = 20)
	private String airportIATACode;
	/* 机场四字码 */
	@Column(name = "AIRPORT_ICAO_CODE", length = 20)
	private String airportICAOCode;
	/* 航空公司二字码 */
	@Column(name = "AIRLINE_IATA_CODE", length = 20)
	private String airlineIATACode;
	/* 航空公司三字码 */
	@Column(name = "AIRLINE_ICAO_CODE", length = 20)
	private String airlineICAOCode;
	/* 起飞机场 */
	@Column(name = "DEPARTURE_AIRPORT", length = 20)
	private String departureAirport;
	/* 降落机场 */
	@Column(name = "DESTINATION_AIRPORT", length = 20)
	private String destinationAirport;
	/* 航班号 */
	@Column(name = "FLIGHT_NUMBER", length = 20)
	private String flightNumber;
	/* 航班后缀 */
	@Column(name = "FLIGHT_SUFFIX", length = 20)
	private String flightSuffix;
	/* 是否主航班 */
	@Column(name = "IS_MASTER_FLIGHT", length = 1)
	private String isMasterFlight;
	/* 航班性质 内地、国际、港澳航班 */
	@Column(name = "FLIGHT_PROPERTY")
	private String flightProperty;
	/* 出入境性质 出入境、非出入境 */
	@Column(name = "ENTRY_EXIT")
	private String entryExit;
	/* 连班id */
	@Column(name = "LINK_FLIGHT_ID")
	private Long linkFlightId;
	/* 连班标记 */
	@Column(name = "LINK_FLIGHT_SIGN", length = 38)
	private String linkFlightSign;
	/* 是否加锁(0未锁,1加锁) */
	@Column(name = "IS_LOCK", length = 1)
	private String isLock;
	/********************************* FLIGHT_DATA ********************************************/
	/* 航班数据id */
	@Column(name = "FLIGHT_DATA_ID")
	private Long flightDataId;
	/* 航班计划时间 */
	@Column(name = "FLIGHT_SCHEDULED_DATE_TIME")
	@Temporal(TIMESTAMP)
	private Date flightScheduledDateTime;
	/* 注册号 */
	@Column(name = "REGISTERATION", length = 20)
	private String registeration;

	@Column(name = "CALL_SIGN", length = 20)
	private String callSign;
	/* 客货属性 */
	@Column(name = "TRANSPORT_PROPERTY", length = 20)
	private String transportProperty;

	/* 飞机三字码 */
	@Column(name = "AIRCRAFT_IATA_CODE", length = 20)
	private String aircraftIATACode;

	@Column(name = "AIRCRAFT_ICAO_CODE", length = 20)
	private String aircraftICAOCode;

	@Column(name = "FLIGHT_CATEGORY", length = 20)
	private String flightCategory;

	@Column(name = "FLIGHT_SERVICETYPE", length = 20)
	private String flightServiceType;
	/**
	 * 航班航路
	 */
	@Column(name = "IATA_ROUTE_AIRPORT", length = 20)
	private String IATARouteAirport;

	@Column(name = "IATA_ORIGIN_AIRPORT", length = 20)
	private String IATAOriginAirport;

	/**
	 * 目的机场IATA代码
	 */
	@Column(name = "IATA_DESTINATION_AIRPORT", length = 50)
	private String IATADestinationAirport;

	@Column(name = "IATA_VIA_AIRPORT", length = 20)
	private String IATAViaAirport;

	@Column(name = "ICAO_ROUTE_AIRPORT", length = 20)
	private String ICAORouteAirport;

	@Column(name = "ICAO_ORIGIN_AIRPORT", length = 20)
	private String ICAOOriginAirport;

	@Column(name = "ICAO_VIA_AIRPORT", length = 20)
	private String ICAOViaAirport;

	@Column(name = "ICAO_DESTINATION_AIRPORT", length = 20)
	private String ICAODestinationAirport;

	@Column(name = "FLIGHT_COUNTRY_TYPE", length = 20)
	private String flightCountryType;

	@Column(name = "SCHEDULED_PA_DEPARTURE_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledPreviousAirportDepartureDateTime;

	@Column(name = "ESTIMATED_PA_DEPARTURE_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedPreviousAirportDepartureDateTime;

	@Column(name = "ACTUAL_PA_DEPARTURE_TIME")
	@Temporal(TIMESTAMP)
	private Date actualPreviousAirportDepartureDateTime;

	@Column(name = "TEN_MILES_TIME")
	@Temporal(TIMESTAMP)
	private Date tenMilesDateTime;

	/* 计划落地时间 */
	@Column(name = "SCHEDULED_LANDING_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledLandingDateTime;

	@Column(name = "ESTIMATED_LANDING_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedLandingDateTime;

	@Column(name = "ACTUAL_LANDING_TIME")
	@Temporal(TIMESTAMP)
	private Date actualLandingDateTime;

	@Column(name = "SCHEDULED_ON_BLOCK_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledOnBlockDateTime;

	@Column(name = "ESTIMATED_ON_BLOCK_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedOnBlockDateTime;

	@Column(name = "ACTUAL_ON_BLOCK_TIME")
	@Temporal(TIMESTAMP)
	private Date actualOnBlockDateTime;

	@Column(name = "SCHEDULED_OFF_BLOCK_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledOffBlockDateTime;

	@Column(name = "ESTIMATED_OFF_BLOCK_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedOffBlockDateTime;

	@Column(name = "ACTUAL_OFF_BLOCK_TIME")
	@Temporal(TIMESTAMP)
	private Date actualOffBlockDateTime;

	/* 计划起飞时间 */
	@Column(name = "SCHEDULED_TAKE_OFF_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledTakeOffDateTime;

	@Column(name = "ESTIMATED_TAKE_OFF_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedTakeOffDateTime;

	@Column(name = "ACTUAL_TAKE_OFF_TIME")
	@Temporal(TIMESTAMP)
	private Date actualTakeOffDateTime;

	@Column(name = "SCHEDULED_NA_ARRIVAL_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledNextAirportArrivalDateTime;

	@Column(name = "ESTIMATED_NA_ARRIVAL_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedNextAirportArrivalDateTime;

	@Column(name = "ACTUAL_NA_ARRIVAL_TIME")
	@Temporal(TIMESTAMP)
	private Date actualNextAirportArrivalDateTime;

	@Column(name = "BEST_KNOWN_TIME")
	@Temporal(TIMESTAMP)
	private Date bestKnownDateTime;

	@Column(name = "OPERATION_STATUS", length = 20)
	private String operationStatus;

	@Column(name = "FLIGHT_STATUS", length = 20)
	private String flightStatus;

	@Column(name = "DELAY_CODE", length = 20)
	private String delayCode;

	@Column(name = "DELAY_FREE_TEXT", length = 20)
	private String delayFreeText;

	@Column(name = "DIVERSION_AIRPORT_IATA_CODE", length = 20)
	private String diversionAirportIATACode;

	@Column(name = "DIVERSION_AIRPORT_ICAO_CODE", length = 20)
	private String diversionAirportICAOCode;

	@Column(name = "CHANGE_LA_IATA_CODE", length = 20)
	private String changeLandingAirportIATACode;

	@Column(name = "CHANGE_LA_ICAO_CODE", length = 20)
	private String changeLandingAirportICAOCode;

	@Column(name = "DISPLAY_CODE", length = 20)
	private String displayCode;

	@Column(name = "IS_TRANSIT_FLIGHT", length = 20)
	private String isTransitFlight;

	@Column(name = "IS_OVER_NIGHT_FLIGHT", length = 20)
	private String isOverNightFlight;

	@Column(name = "IS_CASH_FLIGHT", length = 20)
	private String isCashFlight;

	@Column(name = "PAYLOAD_WEIGHT", length = 10)
	private Integer payloadWeight;

	@Column(name = "PASSENGER_SEATING_CAPACITY", length = 10)
	private Integer passengerSeatingCapacity;

	@Column(name = "MAXIMUM_TAKEOFF_WEIGHT", length = 10)
	private Integer maximumTakeoffWeight;

	@Column(name = "CREW_NUM", length = 10)
	private Integer crewNumber;

	@Column(name = "TOTAL_PASSGER_NUM", length = 10)
	private Integer totalPassengersNumber;

	@Column(name = "INTERNATIONAL_PASSGER_NUM", length = 10)
	private Integer internationalPassengersNumber;

	@Column(name = "DOMESTIC_PASSGER_NUM", length = 10)
	private Integer domesticPassengersNumber;

	@Column(name = "TRANSIT_PASSGER_NUM", length = 10)
	private Integer transitPassengersNumber;

	@Column(name = "TOTAL_CHILDREN_NUM", length = 10)
	private Integer totalChildrenNumber;

	@Column(name = "R_ASSITANCE_PASSGER_NUM", length = 10)
	private Integer requireAssitancePassengersNumber;

	@Column(name = "TOTALL_BAGGAGE_WEIGHT", length = 10)
	private Integer totallBaggageWeight;

	@Column(name = "INTERNATIONAL_BAGGAGE_WEIGHT", length = 10)
	private Integer internationalBaggageWeight;

	@Column(name = "DOMESTIC_BAGGAGE_WEIGHT", length = 10)
	private Integer domesticBaggageWeight;

	@Column(name = "TRANSITL_BAGGAGE_WEIGHT", length = 10)
	private Integer transitlBaggageWeight;

	@Column(name = "TOTAL_CARGO_WEIGHT", length = 10)
	private Integer totalCargoWeight;

	@Column(name = "INTERNATIONAL_CARGO_WEIGHT", length = 10)
	private Integer internationalCargoWeight;

	@Column(name = "DOMESTIC_CARGO_WEIGHT", length = 10)
	private Integer domesticCargoWeight;

	@Column(name = "TRANSIT_CARGO_WEIGHT", length = 10)
	private Integer transitCargoWeight;

	@Column(name = "TOTALL_MAIL_WEIGHT", length = 10)
	private Integer totallMailWeight;

	@Column(name = "INTERNATIONAL_MAIL_WEIGHT", length = 10)
	private Integer internationalMailWeight;

	@Column(name = "DOMESTIC_MAIL_WEIGHT", length = 10)
	private Integer domesticMailWeight;

	@Column(name = "TRANSITL_MAIL_WEIGHT", length = 10)
	private Integer transitlMailWeight;

	/********************************* FLIGHT_RESOURCE ********************************************/
	@Column(name = "FLIGHT_RESOURCE_ID")
	private Long flightResourceId;

	@Column(name = "FLIGHT_TERMINAL_ID", length = 20)
	private String flightTerminalID;

	@Column(name = "AIRCRAFT_TERMINAL_ID", length = 20)
	private String aircraftTerminalID;

	@Column(name = "RUNWAY_ID", length = 20)
	private String runwayID;

	@Column(name = "STAND_ID", length = 20)
	private String standID;

	@Column(name = "SCHEDULED_STAND_START_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledStandStartDateTime;

	@Column(name = "SCHEDULED_STAND_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledStandEndDateTime;

	@Column(name = "ESTIMATED_STAND_START_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedStandStartDateTime;

	@Column(name = "ESTIMCATED_STAND_END_TIME")
	@Temporal(TIMESTAMP)
	private Date estimcatedStandEndDateTime;

	@Column(name = "ACTUAL_STAND_START_TIME")
	@Temporal(TIMESTAMP)
	private Date actualStandStartDateTime;

	@Column(name = "ACTUAL_STAND_END_TIME")
	@Temporal(TIMESTAMP)
	private Date actualStandEndDateTime;

	@Column(name = "BAGGAGE_BELT_ID", length = 20)
	private String baggageBeltID;

	@Column(name = "BAGGAGE_BELT_STATUS", length = 20)
	private String baggageBeltStatus;

	@Column(name = "SCHEDULED_MAKEUP_START_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledMakeupStartDateTime;

	@Column(name = "SCHEDULED_MAKEUP_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledMakeupEndDateTime;

	@Column(name = "ACTUAL_MAKEUP_START_TIME")
	@Temporal(TIMESTAMP)
	private Date actualMakeupStartDateTime;

	@Column(name = "ACTUAL_MAKEUP_END_TIME")
	@Temporal(TIMESTAMP)
	private Date actualMakeupEndDateTime;

	@Column(name = "PREVIOUS_STAND_ID", length = 20)
	private String previousStandID;

	@Column(name = "PREVIOUS_GATE_ID", length = 20)
	private String previousgateID;

	@Column(name = "GATE_ID", length = 20)
	private String gateID;

	@Column(name = "GATE_STATUS", length = 20)
	private String gateStatus;

	@Column(name = "SCHEDULED_GATE_START_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledGateStartDateTime;

	@Column(name = "SCHEDULED_GATE_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledGateEndDateTime;

	@Column(name = "ACTUAL_GATE_START_TIME")
	@Temporal(TIMESTAMP)
	private Date actualGateStartDateTime;

	@Column(name = "ACTUAL_GATE_END_TIME")
	@Temporal(TIMESTAMP)
	private Date actualGateEndDateTime;

	@Column(name = "BAGGAGE_RECLAIM_ID", length = 20)
	private String baggageReclaimID;

	@Column(name = "SCHEDULED_FIRST_BAGGAGE_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledFirstBaggageDateTime;

	@Column(name = "SCHEDULED_LAST_BAGGAGE_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledLastBaggageDateTime;

	@Column(name = "ACTUAL_FIRST_BAGGAGE_TIME")
	@Temporal(TIMESTAMP)
	private Date actualFirstBaggageDateTime;

	@Column(name = "ACTUAL_LAST_BAGGAGE_TIME")
	@Temporal(TIMESTAMP)
	private Date actualLastBaggageDateTime;

	@Column(name = "OVER_VIEW_CHECK_IN_RANGE", length = 20)
	private String overViewCheckInRange;

	@Column(name = "OVER_VIEW_CHECK_IN_TYPE", length = 20)
	private String overViewCheckInType;

	@Column(name = "SCHEDULED_CIF_BEGIN_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledCheckInFirstBeginDateTime;

	@Column(name = "SCHEDULED_CIL_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledCheckInLastEndDateTime;

	@Column(name = "ACTUAL_CIF_BEGIN_TIME")
	@Temporal(TIMESTAMP)
	private Date actualCheckInFirstBeginDateTime;

	@Column(name = "ACUTAL_CIL_END_TIME")
	@Temporal(TIMESTAMP)
	private Date acutalCheckInLastEndDateTime;

	@Column(name = "CHECK_IN_ID", length = 20)
	private String checkInID;

	@Column(name = "CHECK_IN_TYPE", length = 20)
	private String checkInType;

	@Column(name = "CHECK_IN_STATUS", length = 20)
	private String checkInStatus;

	@Column(name = "CHECK_IN_CLASS_SERVICE", length = 20)
	private String checkInClassService;

	@Column(name = "SCHEDULED_CHECK_IN_BEGIN_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledCheckInBeginDateTime;

	@Column(name = "SCHEDULED_CHECK_IN_END_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledCheckInEndDateTime;

	@Column(name = "ACTUAL_CHECK_IN_BEGIN_TIME")
	@Temporal(TIMESTAMP)
	private Date actualCheckInBeginDateTime;

	@Column(name = "ACUTAL_CHECK_IN_END_TIME")
	@Temporal(TIMESTAMP)
	private Date acutalCheckInEndDateTime;

	@Column(name = "CLOSING_SCHEDULED_FROM_TIME")
	@Temporal(TIMESTAMP)
	private Date closingScheduledFromDateTime;

	@Column(name = "CLOSING_SCHEDULED_END_TIME")
	@Temporal(TIMESTAMP)
	private Date closingScheduledEndDateTime;

	@Column(name = "CLOSING_ACTUAL_END_TIME")
	@Temporal(TIMESTAMP)
	private Date closingActualEndDateTime;

	@Column(name = "COMMENT_FREE_TEXT", length = 200)
	private String commentFreeText;

	//
	// /********************************* FLIGHT_SERVICE_INFO
	// ********************************************/
	//
	// /*Add by Ninja
	// * 桥载空调的开始使用和结束时间、
	// * 桥载400HZ电源的开始时间和结束时间
	// */
	//
	// @Column(name = "FSI_ID")
	// private Long fsiId;
	// /**
	// * 桥载空调的开始使用时间
	// */
	// @Column(name = "BRIDGE_AIRCONDITION_START_TIME")
	// private Date bridgeAirconditionStartTime;
	// /**
	// * 桥载空调的结束使用时间
	// */
	// @Column(name = "BRIDGE_AIRCONDITION_END_TIME")
	// private Date bridgeAirconditionEndTime;
	// /**
	// * 桥载400HZ电源的开始使用时间
	// */
	// @Column(name = "BRIDGE_400_POWER_START_TIME")
	// private Date bridge400PowerStartTime;
	// /**
	// * 桥载400HZ电源的结束使用时间
	// */
	// @Column(name = "BRIDGE_400_POWER_END_TIME")
	// private Date bridge400PowerEndTime;
	//
	// /**
	// * 例行检查次数
	// */
	// @Column(name = "ROUTINE_CHECK_COUNT")
	// private String routineCheckCount;
	//
	// /**
	// * 航班号
	// */
	// @Column(name = "FSI_FLIGHT_NUMBER")
	// private String fsiFlightNumber;
	//
	// /*
	// @OneToOne
	// @JoinColumn(name = "FLIGHT_BASE")
	// private FlightBase flightBase;*/
	//
	// /**
	// * 计划时间
	// */
	// @Column(name = "SCHEDULE_DATE")
	// @Temporal(DATE)
	// private Date scheduleDate;
	//
	// /**
	// * 进离港
	// */
	// // @Column(name = "FSI_FLIGHT_DIRECTION")
	// // private FLIGHT_DIRECTION fsiFlightDirection;
	//
	// /**
	// * 客桥数量
	// */
	// @Column(name = "PASSENGER_BRIDGE_NUMBER")
	// private String passengerBridgeNumber;
	//
	// /**
	// * 客梯车使用靠接时间
	// */
	// @Column(name = "PASSENGER_CAR_USED_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date passengerCarUsedStartTime;
	//
	// /**
	// * 客梯车使用撤离时间
	// */
	// @Column(name = "PASSENGER_CAR_USED_END_TIME")
	// @Temporal(TIMESTAMP)
	// private Date passengerCarUsedEndTime;
	//
	// /**
	// * 旅客摆渡车使用次数
	// */
	// @Column(name = "AIRDROME_USED_BY_PASSENGER")
	// private String airdromeCountUsedByPassenger;
	//
	// /**
	// * 机组摆渡车使用次数
	// */
	// @Column(name = "AIRDROME_USED_BY_CREW")
	// private String airdromeCountUsedByCrew;
	//
	// /**
	// * 升降平台车使用靠接时间
	// */
	// @Column(name = "LIFTING_PLATFORM_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date liftingPlatformCarUsedStartTime;
	//
	// /**
	// * 升降平台车使用撤离时间
	// */
	// @Column(name = "LIFTING_PLATFORM_END_TIME")
	// @Temporal(TIMESTAMP)
	// private Date liftingPlatformCarUsedEndTime;
	//
	// /**
	// * 残疾人专用车使用次数
	// */
	// @Column(name = "SPECIAL_VEHICLES_FOR_DISABLED")
	// private String specialVehiclesCountForDisabled;
	//
	// /**
	// * 例行检查开始时间
	// */
	// @Column(name = "ROUTINE_CHECK_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date routineCheckStartTime;
	//
	// /**
	// * 例行检查结束时间
	// */
	// @Column(name = "ROUTINE_CHECK_END_TIME")
	// @Temporal(TIMESTAMP)
	// private Date routineCheckEndTime;
	//
	// /**
	// * 引导车使用次数
	// */
	// @Column(name = "LEAD_CAR_USED_COUNT")
	// private String leadCarUsedCount;
	//
	// /**
	// * 气源车使用靠接时间
	// */
	// @Column(name = "AIR_TRUCK_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date airSourceTruckUsedStartTime;
	//
	// /**
	// * 气源车使用撤离时间
	// */
	// @Column(name = "AIR_TRUCK_END_TIME")
	// @Temporal(TIMESTAMP)
	// private Date airSourceTruckUsedEndTime;
	//
	// /**
	// * 电源车使用靠接时间
	// */
	// @Column(name = "ELECTRIC_TRUCK_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date electricSourceTruckUsedStartTime;
	//
	// /**
	// * 电源车使用撤离时间
	// */
	// @Column(name = "ELECTRIC_TRUCK_END_TIME")
	// @Temporal(TIMESTAMP)
	// private Date electricSourceTruckUsedEndTime;
	//
	// /**
	// * 除冰车使用靠接时间
	// */
	// @Column(name = "DEICING_VEHICLE_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date deicingVehicleUsedStartTime;
	//
	// /**
	// * 除冰车使用撤离时间
	// */
	// @Column(name = "DEICING_VEHICLE_END_TIME")
	// @Temporal(TIMESTAMP)
	// private Date deicingVehicleUsedEndTime;
	//
	// /**
	// * 空调车使用靠接时间
	// */
	// @Column(name = "AIRCONDITIONING_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date airconditioningVehicleUsedStartTime;
	//
	// /**
	// * 空调车使用撤离时间
	// */
	// @Column(name = "AIRCONDITIONING_End_TIME")
	// @Temporal(TIMESTAMP)
	// private Date airconditioningVehicleUsedEndTime;
	//
	// /**
	// * 牵引车使用次数
	// */
	// @Column(name = "TRACTORS_USED_COUNT")
	// private String tractorsUsedCount;
	//
	// /**
	// * 停场时间
	// */
	// @Column(name = "LANDED_TIME")
	// @Temporal(TIMESTAMP)
	// private Date landedTime;
	//
	// /**
	// * 离场时间
	// */
	// @Column(name = "TAKE_OFF_TIME")
	// @Temporal(TIMESTAMP)
	// private Date takeOffTime;
	//
	// /**
	// * 客桥服务开始时间
	// */
	// @Column(name = "PASSENGER_BRIDGE_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date passengerBridgeServicedStartTime;
	//
	// /**
	// * 客桥服务结束时间
	// */
	// @Column(name = "PASSENGER_BRIDGE_END_TIME")
	// @Temporal(TIMESTAMP)
	// private Date passengerBridgeServicedEndTime;
	//
	// // private String non_routine_time;
	//
	// /**
	// * 是否提供旅客服务
	// */
	// @Column(name = "IS_SERVICE_FOR_PASSENGERS")
	// private String isServiceProvidedForPassengers;
	//
	// /**
	// * 是否提供安检服务
	// */
	// @Column(name = "IS_SERVICE_FOR_SECURITY")
	// private String isServiceProvidedForSecurity;
	//
	// // @Column(name = "ROUTINE_PERMIT")
	// // private ROUTINE_PERMIT routinePermit;
	// //
	// // @Column(name = "AIRCRAFT_SERVICE")
	// // private AIRCRAFT_SERVICE aircraftService;
	//
	// @Column(name = "REMK")
	// private String remk;
	//
	// /**
	// * 确认签名
	// */
	// @Column(name = "COMFIRM_TO_SIGN")
	// private String comfirmToSign;
	//
	// /**
	// * 备降候机楼人数
	// */
	// @Column(name = "TERMINAL_PASSENGER_NUMBER")
	// private Long terminalPassengerNumber;
	//
	// /**
	// * 服务计划开始时间（一个开始就是开始）
	// */
	// @Column(name="SERVICE_PLAN_START_TIME")
	// @Temporal(TIMESTAMP)
	// private Date servicePlanStartTime;
	//
	// /**
	// * 服务计划总结束时间（所有task结束才算结束）
	// */
	// @Column(name="SERVICE_PLAN_END_TIME")
	// @Temporal(TIMESTAMP)
	// private Date servicePlanEndTime;
	//
	// // /**
	// // * 航班业务时间（进港取落地时间，离港取起飞时间）
	// // */
	// // @Column(name="FLIGHT_TIME")
	// // @Temporal(TIMESTAMP)
	// // private Date flightTime;
	//
	// @Column(name = "FSI_REMOVE_FLAG", length = 1)
	// private String fsiRemoveFlag;
	//
	// @Column(name = "FSI_CREATE_USER")
	// private String fsiCreateUser;
	//
	// @Column(name = "FSI_CREATE_TIME")
	// @Temporal(TIMESTAMP)
	// private Date fsiCreateTime;
	//
	// @Column(name = "FSI_MODIFY_USER")
	// private String fsiModifyUser;
	//
	// @Temporal(TIMESTAMP)
	// @Column(name = "FSI_MODIFY_TIME")
	// private Date fsiModifyTime;
	//
	// @Column(name = "IS_LOCKED")
	// private boolean isLocked;
	//
	// public boolean isLocked() {
	// return isLocked;
	// }
	//
	// public void setLocked(boolean isLocked) {
	// this.isLocked = isLocked;
	// }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getFlightScheduledDate() {
		return flightScheduledDate;
	}

	public void setFlightScheduledDate(Date flightScheduledDate) {
		this.flightScheduledDate = flightScheduledDate;
	}

	public String getFlightIdentity() {
		return flightIdentity;
	}

	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}

	public String getFlightDirection() {
		return flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}

	public String getAirportIATACode() {
		return airportIATACode;
	}

	public void setAirportIATACode(String airportIATACode) {
		this.airportIATACode = airportIATACode;
	}

	public String getAirportICAOCode() {
		return airportICAOCode;
	}

	public void setAirportICAOCode(String airportICAOCode) {
		this.airportICAOCode = airportICAOCode;
	}

	public String getAirlineIATACode() {
		return airlineIATACode;
	}

	public void setAirlineIATACode(String airlineIATACode) {
		this.airlineIATACode = airlineIATACode;
	}

	public String getAirlineICAOCode() {
		return airlineICAOCode;
	}

	public void setAirlineICAOCode(String airlineICAOCode) {
		this.airlineICAOCode = airlineICAOCode;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightSuffix() {
		return flightSuffix;
	}

	public void setFlightSuffix(String flightSuffix) {
		this.flightSuffix = flightSuffix;
	}

	public String getIsMasterFlight() {
		return isMasterFlight;
	}

	public void setIsMasterFlight(String isMasterFlight) {
		this.isMasterFlight = isMasterFlight;
	}

	public String getFlightProperty() {
		return flightProperty;
	}

	public void setFlightProperty(String flightProperty) {
		this.flightProperty = flightProperty;
	}

	public String getEntryExit() {
		return entryExit;
	}

	public void setEntryExit(String entryExit) {
		this.entryExit = entryExit;
	}

	public Long getLinkFlightId() {
		return linkFlightId;
	}

	public void setLinkFlightId(Long linkFlightId) {
		this.linkFlightId = linkFlightId;
	}

	public String getLinkFlightSign() {
		return linkFlightSign;
	}

	public void setLinkFlightSign(String linkFlightSign) {
		this.linkFlightSign = linkFlightSign;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public Long getFlightDataId() {
		return flightDataId;
	}

	public void setFlightDataId(Long flightDataId) {
		this.flightDataId = flightDataId;
	}

	public Date getFlightScheduledDateTime() {
		return flightScheduledDateTime;
	}

	public void setFlightScheduledDateTime(Date flightScheduledDateTime) {
		this.flightScheduledDateTime = flightScheduledDateTime;
	}

	public String getRegisteration() {
		return registeration;
	}

	public void setRegisteration(String registeration) {
		this.registeration = registeration;
	}

	public String getCallSign() {
		return callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

	public String getTransportProperty() {
		return transportProperty;
	}

	public void setTransportProperty(String transportProperty) {
		this.transportProperty = transportProperty;
	}

	public String getAircraftIATACode() {
		return aircraftIATACode;
	}

	public void setAircraftIATACode(String aircraftIATACode) {
		this.aircraftIATACode = aircraftIATACode;
	}

	public String getAircraftICAOCode() {
		return aircraftICAOCode;
	}

	public void setAircraftICAOCode(String aircraftICAOCode) {
		this.aircraftICAOCode = aircraftICAOCode;
	}

	public String getFlightCategory() {
		return flightCategory;
	}

	public void setFlightCategory(String flightCategory) {
		this.flightCategory = flightCategory;
	}

	public String getFlightServiceType() {
		return flightServiceType;
	}

	public void setFlightServiceType(String flightServiceType) {
		this.flightServiceType = flightServiceType;
	}

	public String getIATARouteAirport() {
		return IATARouteAirport;
	}

	public void setIATARouteAirport(String iATARouteAirport) {
		IATARouteAirport = iATARouteAirport;
	}

	public String getIATAOriginAirport() {
		return IATAOriginAirport;
	}

	public void setIATAOriginAirport(String iATAOriginAirport) {
		IATAOriginAirport = iATAOriginAirport;
	}

	public String getIATAViaAirport() {
		return IATAViaAirport;
	}

	public void setIATAViaAirport(String iATAViaAirport) {
		IATAViaAirport = iATAViaAirport;
	}

	public String getICAORouteAirport() {
		return ICAORouteAirport;
	}

	public void setICAORouteAirport(String iCAORouteAirport) {
		ICAORouteAirport = iCAORouteAirport;
	}

	public String getICAOOriginAirport() {
		return ICAOOriginAirport;
	}

	public void setICAOOriginAirport(String iCAOOriginAirport) {
		ICAOOriginAirport = iCAOOriginAirport;
	}

	public String getICAOViaAirport() {
		return ICAOViaAirport;
	}

	public void setICAOViaAirport(String iCAOViaAirport) {
		ICAOViaAirport = iCAOViaAirport;
	}

	public String getICAODestinationAirport() {
		return ICAODestinationAirport;
	}

	public void setICAODestinationAirport(String iCAODestinationAirport) {
		ICAODestinationAirport = iCAODestinationAirport;
	}

	public String getFlightCountryType() {
		return flightCountryType;
	}

	public void setFlightCountryType(String flightCountryType) {
		this.flightCountryType = flightCountryType;
	}

	public Date getScheduledPreviousAirportDepartureDateTime() {
		return scheduledPreviousAirportDepartureDateTime;
	}

	public void setScheduledPreviousAirportDepartureDateTime(
			Date scheduledPreviousAirportDepartureDateTime) {
		this.scheduledPreviousAirportDepartureDateTime = scheduledPreviousAirportDepartureDateTime;
	}

	public Date getEstimatedPreviousAirportDepartureDateTime() {
		return estimatedPreviousAirportDepartureDateTime;
	}

	public void setEstimatedPreviousAirportDepartureDateTime(
			Date estimatedPreviousAirportDepartureDateTime) {
		this.estimatedPreviousAirportDepartureDateTime = estimatedPreviousAirportDepartureDateTime;
	}

	public Date getActualPreviousAirportDepartureDateTime() {
		return actualPreviousAirportDepartureDateTime;
	}

	public void setActualPreviousAirportDepartureDateTime(
			Date actualPreviousAirportDepartureDateTime) {
		this.actualPreviousAirportDepartureDateTime = actualPreviousAirportDepartureDateTime;
	}

	public Date getTenMilesDateTime() {
		return tenMilesDateTime;
	}

	public void setTenMilesDateTime(Date tenMilesDateTime) {
		this.tenMilesDateTime = tenMilesDateTime;
	}

	public Date getScheduledLandingDateTime() {
		return scheduledLandingDateTime;
	}

	public void setScheduledLandingDateTime(Date scheduledLandingDateTime) {
		this.scheduledLandingDateTime = scheduledLandingDateTime;
	}

	public Date getEstimatedLandingDateTime() {
		return estimatedLandingDateTime;
	}

	public void setEstimatedLandingDateTime(Date estimatedLandingDateTime) {
		this.estimatedLandingDateTime = estimatedLandingDateTime;
	}

	public Date getActualLandingDateTime() {
		return actualLandingDateTime;
	}

	public void setActualLandingDateTime(Date actualLandingDateTime) {
		this.actualLandingDateTime = actualLandingDateTime;
	}

	public Date getScheduledOnBlockDateTime() {
		return scheduledOnBlockDateTime;
	}

	public void setScheduledOnBlockDateTime(Date scheduledOnBlockDateTime) {
		this.scheduledOnBlockDateTime = scheduledOnBlockDateTime;
	}

	public Date getEstimatedOnBlockDateTime() {
		return estimatedOnBlockDateTime;
	}

	public void setEstimatedOnBlockDateTime(Date estimatedOnBlockDateTime) {
		this.estimatedOnBlockDateTime = estimatedOnBlockDateTime;
	}

	public Date getActualOnBlockDateTime() {
		return actualOnBlockDateTime;
	}

	public void setActualOnBlockDateTime(Date actualOnBlockDateTime) {
		this.actualOnBlockDateTime = actualOnBlockDateTime;
	}

	public Date getScheduledOffBlockDateTime() {
		return scheduledOffBlockDateTime;
	}

	public void setScheduledOffBlockDateTime(Date scheduledOffBlockDateTime) {
		this.scheduledOffBlockDateTime = scheduledOffBlockDateTime;
	}

	public Date getEstimatedOffBlockDateTime() {
		return estimatedOffBlockDateTime;
	}

	public void setEstimatedOffBlockDateTime(Date estimatedOffBlockDateTime) {
		this.estimatedOffBlockDateTime = estimatedOffBlockDateTime;
	}

	public Date getActualOffBlockDateTime() {
		return actualOffBlockDateTime;
	}

	public void setActualOffBlockDateTime(Date actualOffBlockDateTime) {
		this.actualOffBlockDateTime = actualOffBlockDateTime;
	}

	public Date getScheduledTakeOffDateTime() {
		return scheduledTakeOffDateTime;
	}

	public void setScheduledTakeOffDateTime(Date scheduledTakeOffDateTime) {
		this.scheduledTakeOffDateTime = scheduledTakeOffDateTime;
	}

	public Date getEstimatedTakeOffDateTime() {
		return estimatedTakeOffDateTime;
	}

	public void setEstimatedTakeOffDateTime(Date estimatedTakeOffDateTime) {
		this.estimatedTakeOffDateTime = estimatedTakeOffDateTime;
	}

	public Date getActualTakeOffDateTime() {
		return actualTakeOffDateTime;
	}

	public void setActualTakeOffDateTime(Date actualTakeOffDateTime) {
		this.actualTakeOffDateTime = actualTakeOffDateTime;
	}

	public Date getScheduledNextAirportArrivalDateTime() {
		return scheduledNextAirportArrivalDateTime;
	}

	public void setScheduledNextAirportArrivalDateTime(
			Date scheduledNextAirportArrivalDateTime) {
		this.scheduledNextAirportArrivalDateTime = scheduledNextAirportArrivalDateTime;
	}

	public Date getEstimatedNextAirportArrivalDateTime() {
		return estimatedNextAirportArrivalDateTime;
	}

	public void setEstimatedNextAirportArrivalDateTime(
			Date estimatedNextAirportArrivalDateTime) {
		this.estimatedNextAirportArrivalDateTime = estimatedNextAirportArrivalDateTime;
	}

	public Date getActualNextAirportArrivalDateTime() {
		return actualNextAirportArrivalDateTime;
	}

	public void setActualNextAirportArrivalDateTime(
			Date actualNextAirportArrivalDateTime) {
		this.actualNextAirportArrivalDateTime = actualNextAirportArrivalDateTime;
	}

	public Date getBestKnownDateTime() {
		return bestKnownDateTime;
	}

	public void setBestKnownDateTime(Date bestKnownDateTime) {
		this.bestKnownDateTime = bestKnownDateTime;
	}

	public String getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}

	public String getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	public String getDelayCode() {
		return delayCode;
	}

	public void setDelayCode(String delayCode) {
		this.delayCode = delayCode;
	}

	public String getDelayFreeText() {
		return delayFreeText;
	}

	public void setDelayFreeText(String delayFreeText) {
		this.delayFreeText = delayFreeText;
	}

	public String getDiversionAirportIATACode() {
		return diversionAirportIATACode;
	}

	public void setDiversionAirportIATACode(String diversionAirportIATACode) {
		this.diversionAirportIATACode = diversionAirportIATACode;
	}

	public String getDiversionAirportICAOCode() {
		return diversionAirportICAOCode;
	}

	public void setDiversionAirportICAOCode(String diversionAirportICAOCode) {
		this.diversionAirportICAOCode = diversionAirportICAOCode;
	}

	public String getChangeLandingAirportIATACode() {
		return changeLandingAirportIATACode;
	}

	public void setChangeLandingAirportIATACode(
			String changeLandingAirportIATACode) {
		this.changeLandingAirportIATACode = changeLandingAirportIATACode;
	}

	public String getChangeLandingAirportICAOCode() {
		return changeLandingAirportICAOCode;
	}

	public void setChangeLandingAirportICAOCode(
			String changeLandingAirportICAOCode) {
		this.changeLandingAirportICAOCode = changeLandingAirportICAOCode;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	public String getIsTransitFlight() {
		return isTransitFlight;
	}

	public void setIsTransitFlight(String isTransitFlight) {
		this.isTransitFlight = isTransitFlight;
	}

	public String getIsOverNightFlight() {
		return isOverNightFlight;
	}

	public void setIsOverNightFlight(String isOverNightFlight) {
		this.isOverNightFlight = isOverNightFlight;
	}

	public String getIsCashFlight() {
		return isCashFlight;
	}

	public void setIsCashFlight(String isCashFlight) {
		this.isCashFlight = isCashFlight;
	}

	public Integer getPayloadWeight() {
		return payloadWeight;
	}

	public void setPayloadWeight(Integer payloadWeight) {
		this.payloadWeight = payloadWeight;
	}

	public Integer getPassengerSeatingCapacity() {
		return passengerSeatingCapacity;
	}

	public void setPassengerSeatingCapacity(Integer passengerSeatingCapacity) {
		this.passengerSeatingCapacity = passengerSeatingCapacity;
	}

	public Integer getMaximumTakeoffWeight() {
		return maximumTakeoffWeight;
	}

	public void setMaximumTakeoffWeight(Integer maximumTakeoffWeight) {
		this.maximumTakeoffWeight = maximumTakeoffWeight;
	}

	public Integer getCrewNumber() {
		return crewNumber;
	}

	public void setCrewNumber(Integer crewNumber) {
		this.crewNumber = crewNumber;
	}

	public Integer getTotalPassengersNumber() {
		return totalPassengersNumber;
	}

	public void setTotalPassengersNumber(Integer totalPassengersNumber) {
		this.totalPassengersNumber = totalPassengersNumber;
	}

	public Integer getInternationalPassengersNumber() {
		return internationalPassengersNumber;
	}

	public void setInternationalPassengersNumber(
			Integer internationalPassengersNumber) {
		this.internationalPassengersNumber = internationalPassengersNumber;
	}

	public Integer getDomesticPassengersNumber() {
		return domesticPassengersNumber;
	}

	public void setDomesticPassengersNumber(Integer domesticPassengersNumber) {
		this.domesticPassengersNumber = domesticPassengersNumber;
	}

	public Integer getTransitPassengersNumber() {
		return transitPassengersNumber;
	}

	public void setTransitPassengersNumber(Integer transitPassengersNumber) {
		this.transitPassengersNumber = transitPassengersNumber;
	}

	public Integer getTotalChildrenNumber() {
		return totalChildrenNumber;
	}

	public void setTotalChildrenNumber(Integer totalChildrenNumber) {
		this.totalChildrenNumber = totalChildrenNumber;
	}

	public Integer getRequireAssitancePassengersNumber() {
		return requireAssitancePassengersNumber;
	}

	public void setRequireAssitancePassengersNumber(
			Integer requireAssitancePassengersNumber) {
		this.requireAssitancePassengersNumber = requireAssitancePassengersNumber;
	}

	public Integer getTotallBaggageWeight() {
		return totallBaggageWeight;
	}

	public void setTotallBaggageWeight(Integer totallBaggageWeight) {
		this.totallBaggageWeight = totallBaggageWeight;
	}

	public Integer getInternationalBaggageWeight() {
		return internationalBaggageWeight;
	}

	public void setInternationalBaggageWeight(Integer internationalBaggageWeight) {
		this.internationalBaggageWeight = internationalBaggageWeight;
	}

	public Integer getDomesticBaggageWeight() {
		return domesticBaggageWeight;
	}

	public void setDomesticBaggageWeight(Integer domesticBaggageWeight) {
		this.domesticBaggageWeight = domesticBaggageWeight;
	}

	public Integer getTransitlBaggageWeight() {
		return transitlBaggageWeight;
	}

	public void setTransitlBaggageWeight(Integer transitlBaggageWeight) {
		this.transitlBaggageWeight = transitlBaggageWeight;
	}

	public Integer getTotalCargoWeight() {
		return totalCargoWeight;
	}

	public void setTotalCargoWeight(Integer totalCargoWeight) {
		this.totalCargoWeight = totalCargoWeight;
	}

	public Integer getInternationalCargoWeight() {
		return internationalCargoWeight;
	}

	public void setInternationalCargoWeight(Integer internationalCargoWeight) {
		this.internationalCargoWeight = internationalCargoWeight;
	}

	public Integer getDomesticCargoWeight() {
		return domesticCargoWeight;
	}

	public void setDomesticCargoWeight(Integer domesticCargoWeight) {
		this.domesticCargoWeight = domesticCargoWeight;
	}

	public Integer getTransitCargoWeight() {
		return transitCargoWeight;
	}

	public void setTransitCargoWeight(Integer transitCargoWeight) {
		this.transitCargoWeight = transitCargoWeight;
	}

	public Integer getTotallMailWeight() {
		return totallMailWeight;
	}

	public void setTotallMailWeight(Integer totallMailWeight) {
		this.totallMailWeight = totallMailWeight;
	}

	public Integer getInternationalMailWeight() {
		return internationalMailWeight;
	}

	public void setInternationalMailWeight(Integer internationalMailWeight) {
		this.internationalMailWeight = internationalMailWeight;
	}

	public Integer getDomesticMailWeight() {
		return domesticMailWeight;
	}

	public void setDomesticMailWeight(Integer domesticMailWeight) {
		this.domesticMailWeight = domesticMailWeight;
	}

	public Integer getTransitlMailWeight() {
		return transitlMailWeight;
	}

	public void setTransitlMailWeight(Integer transitlMailWeight) {
		this.transitlMailWeight = transitlMailWeight;
	}

	public Long getFlightResourceId() {
		return flightResourceId;
	}

	public void setFlightResourceId(Long flightResourceId) {
		this.flightResourceId = flightResourceId;
	}

	public String getFlightTerminalID() {
		return flightTerminalID;
	}

	public void setFlightTerminalID(String flightTerminalID) {
		this.flightTerminalID = flightTerminalID;
	}

	public String getAircraftTerminalID() {
		return aircraftTerminalID;
	}

	public void setAircraftTerminalID(String aircraftTerminalID) {
		this.aircraftTerminalID = aircraftTerminalID;
	}

	public String getRunwayID() {
		return runwayID;
	}

	public void setRunwayID(String runwayID) {
		this.runwayID = runwayID;
	}

	public String getStandID() {
		return standID;
	}

	public void setStandID(String standID) {
		this.standID = standID;
	}

	public Date getScheduledStandStartDateTime() {
		return scheduledStandStartDateTime;
	}

	public void setScheduledStandStartDateTime(Date scheduledStandStartDateTime) {
		this.scheduledStandStartDateTime = scheduledStandStartDateTime;
	}

	public Date getScheduledStandEndDateTime() {
		return scheduledStandEndDateTime;
	}

	public void setScheduledStandEndDateTime(Date scheduledStandEndDateTime) {
		this.scheduledStandEndDateTime = scheduledStandEndDateTime;
	}

	public Date getEstimatedStandStartDateTime() {
		return estimatedStandStartDateTime;
	}

	public void setEstimatedStandStartDateTime(Date estimatedStandStartDateTime) {
		this.estimatedStandStartDateTime = estimatedStandStartDateTime;
	}

	public Date getEstimcatedStandEndDateTime() {
		return estimcatedStandEndDateTime;
	}

	public void setEstimcatedStandEndDateTime(Date estimcatedStandEndDateTime) {
		this.estimcatedStandEndDateTime = estimcatedStandEndDateTime;
	}

	public Date getActualStandStartDateTime() {
		return actualStandStartDateTime;
	}

	public void setActualStandStartDateTime(Date actualStandStartDateTime) {
		this.actualStandStartDateTime = actualStandStartDateTime;
	}

	public Date getActualStandEndDateTime() {
		return actualStandEndDateTime;
	}

	public void setActualStandEndDateTime(Date actualStandEndDateTime) {
		this.actualStandEndDateTime = actualStandEndDateTime;
	}

	public String getBaggageBeltID() {
		return baggageBeltID;
	}

	public void setBaggageBeltID(String baggageBeltID) {
		this.baggageBeltID = baggageBeltID;
	}

	public String getBaggageBeltStatus() {
		return baggageBeltStatus;
	}

	public void setBaggageBeltStatus(String baggageBeltStatus) {
		this.baggageBeltStatus = baggageBeltStatus;
	}

	public Date getScheduledMakeupStartDateTime() {
		return scheduledMakeupStartDateTime;
	}

	public void setScheduledMakeupStartDateTime(
			Date scheduledMakeupStartDateTime) {
		this.scheduledMakeupStartDateTime = scheduledMakeupStartDateTime;
	}

	public Date getScheduledMakeupEndDateTime() {
		return scheduledMakeupEndDateTime;
	}

	public void setScheduledMakeupEndDateTime(Date scheduledMakeupEndDateTime) {
		this.scheduledMakeupEndDateTime = scheduledMakeupEndDateTime;
	}

	public Date getActualMakeupStartDateTime() {
		return actualMakeupStartDateTime;
	}

	public void setActualMakeupStartDateTime(Date actualMakeupStartDateTime) {
		this.actualMakeupStartDateTime = actualMakeupStartDateTime;
	}

	public Date getActualMakeupEndDateTime() {
		return actualMakeupEndDateTime;
	}

	public void setActualMakeupEndDateTime(Date actualMakeupEndDateTime) {
		this.actualMakeupEndDateTime = actualMakeupEndDateTime;
	}

	public String getGateID() {
		return gateID;
	}

	public void setGateID(String gateID) {
		this.gateID = gateID;
	}

	public String getGateStatus() {
		return gateStatus;
	}

	public void setGateStatus(String gateStatus) {
		this.gateStatus = gateStatus;
	}

	public Date getScheduledGateStartDateTime() {
		return scheduledGateStartDateTime;
	}

	public void setScheduledGateStartDateTime(Date scheduledGateStartDateTime) {
		this.scheduledGateStartDateTime = scheduledGateStartDateTime;
	}

	public Date getScheduledGateEndDateTime() {
		return scheduledGateEndDateTime;
	}

	public void setScheduledGateEndDateTime(Date scheduledGateEndDateTime) {
		this.scheduledGateEndDateTime = scheduledGateEndDateTime;
	}

	public Date getActualGateStartDateTime() {
		return actualGateStartDateTime;
	}

	public void setActualGateStartDateTime(Date actualGateStartDateTime) {
		this.actualGateStartDateTime = actualGateStartDateTime;
	}

	public Date getActualGateEndDateTime() {
		return actualGateEndDateTime;
	}

	public void setActualGateEndDateTime(Date actualGateEndDateTime) {
		this.actualGateEndDateTime = actualGateEndDateTime;
	}

	public String getBaggageReclaimID() {
		return baggageReclaimID;
	}

	public void setBaggageReclaimID(String baggageReclaimID) {
		this.baggageReclaimID = baggageReclaimID;
	}

	public Date getScheduledFirstBaggageDateTime() {
		return scheduledFirstBaggageDateTime;
	}

	public void setScheduledFirstBaggageDateTime(
			Date scheduledFirstBaggageDateTime) {
		this.scheduledFirstBaggageDateTime = scheduledFirstBaggageDateTime;
	}

	public Date getScheduledLastBaggageDateTime() {
		return scheduledLastBaggageDateTime;
	}

	public void setScheduledLastBaggageDateTime(
			Date scheduledLastBaggageDateTime) {
		this.scheduledLastBaggageDateTime = scheduledLastBaggageDateTime;
	}

	public Date getActualFirstBaggageDateTime() {
		return actualFirstBaggageDateTime;
	}

	public void setActualFirstBaggageDateTime(Date actualFirstBaggageDateTime) {
		this.actualFirstBaggageDateTime = actualFirstBaggageDateTime;
	}

	public Date getActualLastBaggageDateTime() {
		return actualLastBaggageDateTime;
	}

	public void setActualLastBaggageDateTime(Date actualLastBaggageDateTime) {
		this.actualLastBaggageDateTime = actualLastBaggageDateTime;
	}

	public String getOverViewCheckInRange() {
		return overViewCheckInRange;
	}

	public void setOverViewCheckInRange(String overViewCheckInRange) {
		this.overViewCheckInRange = overViewCheckInRange;
	}

	public String getOverViewCheckInType() {
		return overViewCheckInType;
	}

	public void setOverViewCheckInType(String overViewCheckInType) {
		this.overViewCheckInType = overViewCheckInType;
	}

	public Date getScheduledCheckInFirstBeginDateTime() {
		return scheduledCheckInFirstBeginDateTime;
	}

	public void setScheduledCheckInFirstBeginDateTime(
			Date scheduledCheckInFirstBeginDateTime) {
		this.scheduledCheckInFirstBeginDateTime = scheduledCheckInFirstBeginDateTime;
	}

	public Date getScheduledCheckInLastEndDateTime() {
		return scheduledCheckInLastEndDateTime;
	}

	public void setScheduledCheckInLastEndDateTime(
			Date scheduledCheckInLastEndDateTime) {
		this.scheduledCheckInLastEndDateTime = scheduledCheckInLastEndDateTime;
	}

	public Date getActualCheckInFirstBeginDateTime() {
		return actualCheckInFirstBeginDateTime;
	}

	public void setActualCheckInFirstBeginDateTime(
			Date actualCheckInFirstBeginDateTime) {
		this.actualCheckInFirstBeginDateTime = actualCheckInFirstBeginDateTime;
	}

	public Date getAcutalCheckInLastEndDateTime() {
		return acutalCheckInLastEndDateTime;
	}

	public void setAcutalCheckInLastEndDateTime(
			Date acutalCheckInLastEndDateTime) {
		this.acutalCheckInLastEndDateTime = acutalCheckInLastEndDateTime;
	}

	public String getCheckInID() {
		return checkInID;
	}

	public void setCheckInID(String checkInID) {
		this.checkInID = checkInID;
	}

	public String getCheckInType() {
		return checkInType;
	}

	public void setCheckInType(String checkInType) {
		this.checkInType = checkInType;
	}

	public String getCheckInStatus() {
		return checkInStatus;
	}

	public void setCheckInStatus(String checkInStatus) {
		this.checkInStatus = checkInStatus;
	}

	public String getCheckInClassService() {
		return checkInClassService;
	}

	public void setCheckInClassService(String checkInClassService) {
		this.checkInClassService = checkInClassService;
	}

	public Date getScheduledCheckInBeginDateTime() {
		return scheduledCheckInBeginDateTime;
	}

	public void setScheduledCheckInBeginDateTime(
			Date scheduledCheckInBeginDateTime) {
		this.scheduledCheckInBeginDateTime = scheduledCheckInBeginDateTime;
	}

	public Date getScheduledCheckInEndDateTime() {
		return scheduledCheckInEndDateTime;
	}

	public void setScheduledCheckInEndDateTime(Date scheduledCheckInEndDateTime) {
		this.scheduledCheckInEndDateTime = scheduledCheckInEndDateTime;
	}

	public Date getActualCheckInBeginDateTime() {
		return actualCheckInBeginDateTime;
	}

	public void setActualCheckInBeginDateTime(Date actualCheckInBeginDateTime) {
		this.actualCheckInBeginDateTime = actualCheckInBeginDateTime;
	}

	public Date getAcutalCheckInEndDateTime() {
		return acutalCheckInEndDateTime;
	}

	public void setAcutalCheckInEndDateTime(Date acutalCheckInEndDateTime) {
		this.acutalCheckInEndDateTime = acutalCheckInEndDateTime;
	}

	public Date getClosingScheduledFromDateTime() {
		return closingScheduledFromDateTime;
	}

	public void setClosingScheduledFromDateTime(
			Date closingScheduledFromDateTime) {
		this.closingScheduledFromDateTime = closingScheduledFromDateTime;
	}

	public Date getClosingScheduledEndDateTime() {
		return closingScheduledEndDateTime;
	}

	public void setClosingScheduledEndDateTime(Date closingScheduledEndDateTime) {
		this.closingScheduledEndDateTime = closingScheduledEndDateTime;
	}

	public Date getClosingActualEndDateTime() {
		return closingActualEndDateTime;
	}

	public void setClosingActualEndDateTime(Date closingActualEndDateTime) {
		this.closingActualEndDateTime = closingActualEndDateTime;
	}

	public String getCommentFreeText() {
		return commentFreeText;
	}

	public void setCommentFreeText(String commentFreeText) {
		this.commentFreeText = commentFreeText;
	}

	public String getRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}

	//
	// public Date getBridgeAirconditionStartTime() {
	// return bridgeAirconditionStartTime;
	// }
	//
	// public void setBridgeAirconditionStartTime(Date
	// bridgeAirconditionStartTime) {
	// this.bridgeAirconditionStartTime = bridgeAirconditionStartTime;
	// }
	//
	// public Date getBridgeAirconditionEndTime() {
	// return bridgeAirconditionEndTime;
	// }
	//
	// public void setBridgeAirconditionEndTime(Date bridgeAirconditionEndTime)
	// {
	// this.bridgeAirconditionEndTime = bridgeAirconditionEndTime;
	// }
	//
	// public Date getBridge400PowerStartTime() {
	// return bridge400PowerStartTime;
	// }
	//
	// public void setBridge400PowerStartTime(Date bridge400PowerStartTime) {
	// this.bridge400PowerStartTime = bridge400PowerStartTime;
	// }
	//
	// public Date getBridge400PowerEndTime() {
	// return bridge400PowerEndTime;
	// }
	//
	// public void setBridge400PowerEndTime(Date bridge400PowerEndTime) {
	// this.bridge400PowerEndTime = bridge400PowerEndTime;
	// }
	//
	// public String getRoutineCheckCount() {
	// return routineCheckCount;
	// }
	//
	// public void setRoutineCheckCount(String routineCheckCount) {
	// this.routineCheckCount = routineCheckCount;
	// }
	//
	// public Date getScheduleDate() {
	// return scheduleDate;
	// }
	//
	// public void setScheduleDate(Date scheduleDate) {
	// this.scheduleDate = scheduleDate;
	// }
	//
	//
	// public String getPassengerBridgeNumber() {
	// return passengerBridgeNumber;
	// }
	//
	// public void setPassengerBridgeNumber(String passengerBridgeNumber) {
	// this.passengerBridgeNumber = passengerBridgeNumber;
	// }
	//
	// public Date getPassengerCarUsedStartTime() {
	// return passengerCarUsedStartTime;
	// }
	//
	// public void setPassengerCarUsedStartTime(Date passengerCarUsedStartTime)
	// {
	// this.passengerCarUsedStartTime = passengerCarUsedStartTime;
	// }
	//
	// public Date getPassengerCarUsedEndTime() {
	// return passengerCarUsedEndTime;
	// }
	//
	// public void setPassengerCarUsedEndTime(Date passengerCarUsedEndTime) {
	// this.passengerCarUsedEndTime = passengerCarUsedEndTime;
	// }
	//
	// public String getAirdromeCountUsedByPassenger() {
	// return airdromeCountUsedByPassenger;
	// }
	//
	// public void setAirdromeCountUsedByPassenger(String
	// airdromeCountUsedByPassenger) {
	// this.airdromeCountUsedByPassenger = airdromeCountUsedByPassenger;
	// }
	//
	// public String getAirdromeCountUsedByCrew() {
	// return airdromeCountUsedByCrew;
	// }
	//
	// public void setAirdromeCountUsedByCrew(String airdromeCountUsedByCrew) {
	// this.airdromeCountUsedByCrew = airdromeCountUsedByCrew;
	// }
	//
	// public Date getLiftingPlatformCarUsedStartTime() {
	// return liftingPlatformCarUsedStartTime;
	// }
	//
	// public void setLiftingPlatformCarUsedStartTime(Date
	// liftingPlatformCarUsedStartTime) {
	// this.liftingPlatformCarUsedStartTime = liftingPlatformCarUsedStartTime;
	// }
	//
	// public Date getLiftingPlatformCarUsedEndTime() {
	// return liftingPlatformCarUsedEndTime;
	// }
	//
	// public void setLiftingPlatformCarUsedEndTime(Date
	// liftingPlatformCarUsedEndTime) {
	// this.liftingPlatformCarUsedEndTime = liftingPlatformCarUsedEndTime;
	// }
	//
	// public String getSpecialVehiclesCountForDisabled() {
	// return specialVehiclesCountForDisabled;
	// }
	//
	// public void setSpecialVehiclesCountForDisabled(String
	// specialVehiclesCountForDisabled) {
	// this.specialVehiclesCountForDisabled = specialVehiclesCountForDisabled;
	// }
	//
	// public Date getRoutineCheckStartTime() {
	// return routineCheckStartTime;
	// }
	//
	// public void setRoutineCheckStartTime(Date routineCheckStartTime) {
	// this.routineCheckStartTime = routineCheckStartTime;
	// }
	//
	// public Date getRoutineCheckEndTime() {
	// return routineCheckEndTime;
	// }
	//
	// public void setRoutineCheckEndTime(Date routineCheckEndTime) {
	// this.routineCheckEndTime = routineCheckEndTime;
	// }
	//
	// public String getLeadCarUsedCount() {
	// return leadCarUsedCount;
	// }
	//
	// public void setLeadCarUsedCount(String leadCarUsedCount) {
	// this.leadCarUsedCount = leadCarUsedCount;
	// }
	//
	// public Date getAirSourceTruckUsedStartTime() {
	// return airSourceTruckUsedStartTime;
	// }
	//
	// public void setAirSourceTruckUsedStartTime(Date
	// airSourceTruckUsedStartTime) {
	// this.airSourceTruckUsedStartTime = airSourceTruckUsedStartTime;
	// }
	//
	// public Date getAirSourceTruckUsedEndTime() {
	// return airSourceTruckUsedEndTime;
	// }
	//
	// public void setAirSourceTruckUsedEndTime(Date airSourceTruckUsedEndTime)
	// {
	// this.airSourceTruckUsedEndTime = airSourceTruckUsedEndTime;
	// }
	//
	// public Date getElectricSourceTruckUsedStartTime() {
	// return electricSourceTruckUsedStartTime;
	// }
	//
	// public void setElectricSourceTruckUsedStartTime(Date
	// electricSourceTruckUsedStartTime) {
	// this.electricSourceTruckUsedStartTime = electricSourceTruckUsedStartTime;
	// }
	//
	// public Date getElectricSourceTruckUsedEndTime() {
	// return electricSourceTruckUsedEndTime;
	// }
	//
	// public void setElectricSourceTruckUsedEndTime(Date
	// electricSourceTruckUsedEndTime) {
	// this.electricSourceTruckUsedEndTime = electricSourceTruckUsedEndTime;
	// }
	//
	// public Date getDeicingVehicleUsedStartTime() {
	// return deicingVehicleUsedStartTime;
	// }
	//
	// public void setDeicingVehicleUsedStartTime(Date
	// deicingVehicleUsedStartTime) {
	// this.deicingVehicleUsedStartTime = deicingVehicleUsedStartTime;
	// }
	//
	// public Date getDeicingVehicleUsedEndTime() {
	// return deicingVehicleUsedEndTime;
	// }
	//
	// public void setDeicingVehicleUsedEndTime(Date deicingVehicleUsedEndTime)
	// {
	// this.deicingVehicleUsedEndTime = deicingVehicleUsedEndTime;
	// }
	//
	// public Date getAirconditioningVehicleUsedStartTime() {
	// return airconditioningVehicleUsedStartTime;
	// }
	//
	// public void setAirconditioningVehicleUsedStartTime(Date
	// airconditioningVehicleUsedStartTime) {
	// this.airconditioningVehicleUsedStartTime =
	// airconditioningVehicleUsedStartTime;
	// }
	//
	// public Date getAirconditioningVehicleUsedEndTime() {
	// return airconditioningVehicleUsedEndTime;
	// }
	//
	// public void setAirconditioningVehicleUsedEndTime(Date
	// airconditioningVehicleUsedEndTime) {
	// this.airconditioningVehicleUsedEndTime =
	// airconditioningVehicleUsedEndTime;
	// }
	//
	// public String getTractorsUsedCount() {
	// return tractorsUsedCount;
	// }
	//
	// public void setTractorsUsedCount(String tractorsUsedCount) {
	// this.tractorsUsedCount = tractorsUsedCount;
	// }
	//
	// public Date getLandedTime() {
	// return landedTime;
	// }
	//
	// public void setLandedTime(Date landedTime) {
	// this.landedTime = landedTime;
	// }
	//
	// public Date getTakeOffTime() {
	// return takeOffTime;
	// }
	//
	// public void setTakeOffTime(Date takeOffTime) {
	// this.takeOffTime = takeOffTime;
	// }
	//
	// public Date getPassengerBridgeServicedStartTime() {
	// return passengerBridgeServicedStartTime;
	// }
	//
	// public void setPassengerBridgeServicedStartTime(Date
	// passengerBridgeServicedStartTime) {
	// this.passengerBridgeServicedStartTime = passengerBridgeServicedStartTime;
	// }
	//
	// public Date getPassengerBridgeServicedEndTime() {
	// return passengerBridgeServicedEndTime;
	// }
	//
	// public void setPassengerBridgeServicedEndTime(Date
	// passengerBridgeServicedEndTime) {
	// this.passengerBridgeServicedEndTime = passengerBridgeServicedEndTime;
	// }
	//
	//
	// public String getIsServiceProvidedForPassengers() {
	// return isServiceProvidedForPassengers;
	// }
	//
	// public void setIsServiceProvidedForPassengers(String
	// isServiceProvidedForPassengers) {
	// this.isServiceProvidedForPassengers = isServiceProvidedForPassengers;
	// }
	//
	// public String getIsServiceProvidedForSecurity() {
	// return isServiceProvidedForSecurity;
	// }
	//
	// public void setIsServiceProvidedForSecurity(String
	// isServiceProvidedForSecurity) {
	// this.isServiceProvidedForSecurity = isServiceProvidedForSecurity;
	// }

	// public ROUTINE_PERMIT getRoutinePermit() {
	// return routinePermit;
	// }
	//
	// public void setRoutinePermit(ROUTINE_PERMIT routinePermit) {
	// this.routinePermit = routinePermit;
	// }
	//
	// public AIRCRAFT_SERVICE getAircraftService() {
	// return aircraftService;
	// }
	//
	// public void setAircraftService(AIRCRAFT_SERVICE aircraftService) {
	// this.aircraftService = aircraftService;
	// }

	// public String getRemk() {
	// return remk;
	// }
	//
	// public void setRemk(String remk) {
	// this.remk = remk;
	// }
	//
	// public String getComfirmToSign() {
	// return comfirmToSign;
	// }
	//
	// public void setComfirmToSign(String comfirmToSign) {
	// this.comfirmToSign = comfirmToSign;
	// }
	//
	// public Long getTerminalPassengerNumber() {
	// return terminalPassengerNumber;
	// }
	//
	// public void setTerminalPassengerNumber(Long terminalPassengerNumber) {
	// this.terminalPassengerNumber = terminalPassengerNumber;
	// }

	// public Long getFsiId() {
	// return fsiId;
	// }
	//
	// public void setFsiId(Long fsiId) {
	// this.fsiId = fsiId;
	// }
	//
	// public Date getServicePlanStartTime() {
	// return servicePlanStartTime;
	// }
	//
	// public void setServicePlanStartTime(Date servicePlanStartTime) {
	// this.servicePlanStartTime = servicePlanStartTime;
	// }
	//
	// public Date getServicePlanEndTime() {
	// return servicePlanEndTime;
	// }
	//
	// public void setServicePlanEndTime(Date servicePlanEndTime) {
	// this.servicePlanEndTime = servicePlanEndTime;
	// }
	//
	// public String getFsiFlightNumber() {
	// return fsiFlightNumber;
	// }
	//
	// public void setFsiFlightNumber(String fsiFlightNumber) {
	// this.fsiFlightNumber = fsiFlightNumber;
	// }

	// public FLIGHT_DIRECTION getFsiFlightDirection() {
	// return fsiFlightDirection;
	// }
	//
	// public void setFsiFlightDirection(FLIGHT_DIRECTION fsiFlightDirection) {
	// this.fsiFlightDirection = fsiFlightDirection;
	// }

	// public String getFsiRemoveFlag() {
	// return fsiRemoveFlag;
	// }
	//
	// public void setFsiRemoveFlag(String fsiRemoveFlag) {
	// this.fsiRemoveFlag = fsiRemoveFlag;
	// }
	//
	// public String getFsiCreateUser() {
	// return fsiCreateUser;
	// }
	//
	// public void setFsiCreateUser(String fsiCreateUser) {
	// this.fsiCreateUser = fsiCreateUser;
	// }
	//
	// public Date getFsiCreateTime() {
	// return fsiCreateTime;
	// }
	//
	// public void setFsiCreateTime(Date fsiCreateTime) {
	// this.fsiCreateTime = fsiCreateTime;
	// }
	//
	// public String getFsiModifyUser() {
	// return fsiModifyUser;
	// }
	//
	// public void setFsiModifyUser(String fsiModifyUser) {
	// this.fsiModifyUser = fsiModifyUser;
	// }
	//
	// public Date getFsiModifyTime() {
	// return fsiModifyTime;
	// }
	//
	// public void setFsiModifyTime(Date fsiModifyTime) {
	// this.fsiModifyTime = fsiModifyTime;
	// }

	public String getIATADestinationAirport() {
		return IATADestinationAirport;
	}

	public void setIATADestinationAirport(String iATADestinationAirport) {
		IATADestinationAirport = iATADestinationAirport;
	}

	// add by march start 增加下站机场IATA、前站机场IATA
	/**
	 * 下站机场IATA代码
	 */
	@Column(name = "IATA_NEXT_AIRPORT", length = 50)
	private String IATANextAirport;

	public String getIATANextAirport() {
		return IATANextAirport;
	}

	public void setIATANextAirport(String iATANextAirport) {
		IATANextAirport = iATANextAirport;
	}

	/**
	 * 前站机场IATA代码
	 */
	@Column(name = "IATA_PREVICOUS_AIRPORT", length = 50)
	private String IATAPreviousAirport;

	public String getIATAPreviousAirport() {
		return IATAPreviousAirport;
	}

	public void setIATAPreviousAirport(String iATAPreviousAirport) {
		IATAPreviousAirport = iATAPreviousAirport;
	}

	// add by march end

	public String getPreviousStandID() {
		return previousStandID;
	}

	public void setPreviousStandID(String previousStandID) {
		this.previousStandID = previousStandID;
	}

	public String getPreviousgateID() {
		return previousgateID;
	}

	public void setPreviousgateID(String previousgateID) {
		this.previousgateID = previousgateID;
	}

//	// 打印标志
//	@Column(name = "IS_PRINTED")
//	private boolean isPrinted;
//
//	public boolean isPrinted() {
//		return isPrinted;
//	}
//
//	public void setPrinted(boolean isPrinted) {
//		this.isPrinted = isPrinted;
//	}
//
//	// 打印标志
//
//	// add by march 2010505 FSI增加 垃圾车、清水车、污水车使用次数 start
//	// 垃圾车使用次数
//	@Column(name = "GARBAGE_TRUCK_USED_COUNT")
//	private String garbageTruckUsedCount;
//	// 清水车使用次数
//	@Column(name = "WATER_TRUCK_USED_COUNT")
//	private String waterTruckUsedCount;
//	// 污水车使用次数
//	@Column(name = "CESSPOOLAGE_TRUCK_USED_COUNT")
//	private String cesspoolageTruckUsedCount;

//	public String getGarbageTruckUsedCount() {
//		return garbageTruckUsedCount;
//	}
//
//	public void setGarbageTruckUsedCount(String garbageTruckUsedCount) {
//		this.garbageTruckUsedCount = garbageTruckUsedCount;
//	}
//
//	public String getWaterTruckUsedCount() {
//		return waterTruckUsedCount;
//	}
//
//	public void setWaterTruckUsedCount(String waterTruckUsedCount) {
//		this.waterTruckUsedCount = waterTruckUsedCount;
//	}
//
//	public String getCesspoolageTruckUsedCount() {
//		return cesspoolageTruckUsedCount;
//	}
//
//	public void setCesspoolageTruckUsedCount(String cesspoolageTruckUsedCount) {
//		this.cesspoolageTruckUsedCount = cesspoolageTruckUsedCount;
//	}

	// add by march 2010505 FSI增加 垃圾车、清水车、污水车使用次数 end

	@Column(name = "SHARED_FLIGHT_IDENTITY")
	private String sharedFlightIdentity;

	public String getSharedFlightIdentity() {
		return sharedFlightIdentity;
	}

	public void setSharedFlightIdentity(String sharedFlightIdentity) {
		this.sharedFlightIdentity = sharedFlightIdentity;
	}

	/**
	 * 全部成人旅客数量
	 */
	@Column(name = "TOTAL_ADULT_PASSGER_NUM", length = 10)
	private Integer totalAdultPassengersNumber;

	/**
	 * 全部婴儿数量
	 */
	@Column(name = "TOTAL_INFANT_PASSGER_NUM", length = 10)
	private Integer totalInfantPassengersNumber;

	// 过站成人
	@Column(name = "TRANSIT_ADULT_PASSGER_NUM", length = 10)
	private Integer transitAdultPassgerNum;
	// 过站儿童
	@Column(name = "TRANSIT_CHILD_PASSGER_NUM", length = 10)
	private Integer transitChildPassgerNum;
	// 过站婴儿
	@Column(name = "TRANSIT_INFANT_PASSGER_NUM", length = 10)
	private Integer transitInfantPassgerNum;

	public Integer getTotalAdultPassengersNumber() {
		return totalAdultPassengersNumber;
	}

	public void setTotalAdultPassengersNumber(Integer totalAdultPassengersNumber) {
		this.totalAdultPassengersNumber = totalAdultPassengersNumber;
	}

	public Integer getTotalInfantPassengersNumber() {
		return totalInfantPassengersNumber;
	}

	public void setTotalInfantPassengersNumber(
			Integer totalInfantPassengersNumber) {
		this.totalInfantPassengersNumber = totalInfantPassengersNumber;
	}

	public Integer getTransitAdultPassgerNum() {
		return transitAdultPassgerNum;
	}

	public void setTransitAdultPassgerNum(Integer transitadultpassgernum) {
		this.transitAdultPassgerNum = transitadultpassgernum;
	}

	public Integer getTransitChildPassgerNum() {
		return transitChildPassgerNum;
	}

	public void setTransitChildPassgerNum(Integer transitchildpassgernum) {
		this.transitChildPassgerNum = transitchildpassgernum;
	}

	public Integer getTransitInfantPassgerNum() {
		return transitInfantPassgerNum;
	}

	public void setTransitInfantPassgerNum(Integer transitinfantpassgernum) {
		this.transitInfantPassgerNum = transitinfantpassgernum;
	}

	/*
	 * add by march 20151124 因HAK而修改
	 */
	@Column(name = "FINAL_APPROACH_TIME")
	@Temporal(TIMESTAMP)
	private Date finalApproachTime;

	@Column(name = "FLIGHT_CAAC_SERVICE_TYPE", length = 20)
	private String flightCAACServiceType;
	@Column(name = "FLIGHT_IATA_SERVICE_TYPE", length = 20)
	private String flightIATAServiceType;
	@Column(name = "FULL_ROUTE_AIRPORT_IATA_CODE", length = 255)
	private String fullRouteAirportIATACode;
	@Column(name = "FULL_ROUTE_AIRPORT_ICAO_CODE", length = 255)
	private String fullRouteAirportICAOCode;

	public String getFlightCAACServiceType() {
		return flightCAACServiceType;
	}

	public void setFlightCAACServiceType(String flightCAACServiceType) {
		this.flightCAACServiceType = flightCAACServiceType;
	}

	public String getFlightIATAServiceType() {
		return flightIATAServiceType;
	}

	public void setFlightIATAServiceType(String flightIATAServiceType) {
		this.flightIATAServiceType = flightIATAServiceType;
	}

	public String getFullRouteAirportIATACode() {
		return fullRouteAirportIATACode;
	}

	public void setFullRouteAirportIATACode(String fullRouteAirportIATACode) {
		this.fullRouteAirportIATACode = fullRouteAirportIATACode;
	}

	public String getFullRouteAirportICAOCode() {
		return fullRouteAirportICAOCode;
	}

	public void setFullRouteAirportICAOCode(String fullRouteAirportICAOCode) {
		this.fullRouteAirportICAOCode = fullRouteAirportICAOCode;
	}

	public Date getFinalApproachTime() {
		return finalApproachTime;
	}

	public void setFinalApproachTime(Date finalApproachTime) {
		this.finalApproachTime = finalApproachTime;
	}

	/*
	 * add by march 20151124 因HAK而修改
	 */
}
