package com.nlia.fqdb.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.nlia.fqdb.util.DateUtils;

@Entity
@Table(name = "FLIGHT_DATA")
public class FlightData implements Serializable {

	private static final long serialVersionUID = 1L;

	/* 航班实体主键 */
	// @OneToOne
	// @JoinColumn(name = "FLIGHT_BASE_ID")
	// private FlightBase flightBase;
	//
	// public FlightBase getFlightBase() {
	// return flightBase;
	// }
	//
	// public void setFlightBase(FlightBase flightBase) {
	// this.flightBase = flightBase;
	// }
	// 进离港标志，和flightbase一致
	@Transient
	private String flightDirection;
	
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
	/* 航班属性 */
	@Column(name = "FLIGHT_PROPERTY", length = 20)
	private String flightProperty;
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

	@Column(name = "AIRPORT_IATA_CODE", length = 20)
	private String airportIATACode;

	@Column(name = "AIRPORT_ICAO_CODE", length = 20)
	private String airportICAOCode;

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

	/**
	 * 计划落地时间
	 */
	@Column(name = "SCHEDULED_LANDING_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledLandingDateTime;

	@Transient
	private String scheduledLandingDateTime2s;

	@Column(name = "ESTIMATED_LANDING_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedLandingDateTime;

	@Transient
	private String estimatedLandingDateTime2s;

	@Column(name = "ACTUAL_LANDING_TIME")
	@Temporal(TIMESTAMP)
	private Date actualLandingDateTime;

	@Transient
	private String actualLandingDateTime2s;

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

	/**
	 * 计划起飞时间
	 */
	@Column(name = "SCHEDULED_TAKE_OFF_TIME")
	@Temporal(TIMESTAMP)
	private Date scheduledTakeOffDateTime;

	@Transient
	private String scheduledTakeOffDateTime2s;

	@Column(name = "ESTIMATED_TAKE_OFF_TIME")
	@Temporal(TIMESTAMP)
	private Date estimatedTakeOffDateTime;

	@Transient
	private String estimatedTakeOffDateTime2s;

	@Column(name = "ACTUAL_TAKE_OFF_TIME")
	@Temporal(TIMESTAMP)
	private Date actualTakeOffDateTime;

	@Transient
	private String actualTakeOffDateTime2s;

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

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

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

	// IDD更新，2013-09-18
	/**
	 * 前站机场IATA代码
	 */
	@Column(name = "IATA_PREVICOUS_AIRPORT", length = 50)
	private String IATAPreviousAirport;

	/**
	 * 下站机场IATA代码
	 */
	@Column(name = "IATA_NEXT_AIRPORT", length = 50)
	private String IATANextAirport;

	/**
	 * 目的机场IATA代码
	 */
	@Column(name = "IATA_DESTINATION_AIRPORT", length = 50)
	private String IATADestinationAirport;

	/**
	 * 前站机场ICAO代码
	 */
	@Column(name = "ICAO_PREVIOUS_AIRPORT", length = 50)
	private String ICAOPreviousAirport;

	/**
	 * 下站机场ICAO代码
	 */
	@Column(name = "ICAO_NEXT_AIRPORT", length = 50)
	private String ICAONextAirport;

	/**
	 * 头等舱旅客数量
	 */
	@Column(name = "FIRST_C_PASSGER_NUM", length = 10)
	private Integer firstClassPassengersNumber;

	/**
	 * 商务舱旅客数量
	 */
	@Column(name = "BUSSINESS_C_PASSENGER_NUM", length = 10)
	private Integer businessClassPassengersNumber;

	/**
	 * 经济舱旅客数量
	 */
	@Column(name = "ECONOMIC_C_PASSGER_NUM", length = 10)
	private Integer economicClassPassengersNumber;

	/**
	 * 全部成人旅客数量
	 */
	@Column(name = "TOTAL_ADULT_PASSGER_NUM", length = 10)
	private Integer totalAdultPassengersNumber;

	/**
	 * 头等舱成人旅客数量
	 */
	@Column(name = "FIRST_C_ADULT_PASSGER_NUM", length = 10)
	private Integer firstClassAdultPassengersNumber;

	/**
	 * 商务舱成人旅客数量
	 */
	@Column(name = "BUSINESS_C_ADULT_PASSGER_NUM", length = 10)
	private Integer businessClassAdultPassengersNumber;

	/**
	 * 经济舱成人旅客数量
	 */
	@Column(name = "ECONOMIC_C_ADULT_PASSGER_NUM", length = 10)
	private Integer economicClassAdultPassengersNumber;

	/**
	 * 头等舱儿童旅客数量
	 */
	@Column(name = "FIRST_C_CHILD_PASSGER_NUM", length = 10)
	private Integer firstClassChildPassengersNumber;

	/**
	 * 商务舱儿童旅客数量
	 */
	@Column(name = "BUSINESSS_C_CHILD_PASSGER_NUM", length = 10)
	private Integer businessClassChildPassengersNumber;

	/**
	 * 经济舱儿童旅客数量
	 */
	@Column(name = "ECONOMIC_C_CHILD_PASSGER_NUM", length = 10)
	private Integer economicClassChildPassengersNumber;

	public Integer getFirstClassPassengersNumber() {
		return firstClassPassengersNumber;
	}

	public void setFirstClassPassengersNumber(Integer firstClassPassengersNumber) {
		this.firstClassPassengersNumber = firstClassPassengersNumber;
	}

	public Integer getBusinessClassPassengersNumber() {
		return businessClassPassengersNumber;
	}

	public void setBusinessClassPassengersNumber(Integer businessClassPassengersNumber) {
		this.businessClassPassengersNumber = businessClassPassengersNumber;
	}

	public Integer getEconomicClassPassengersNumber() {
		return economicClassPassengersNumber;
	}

	public void setEconomicClassPassengersNumber(Integer economicClassPassengersNumber) {
		this.economicClassPassengersNumber = economicClassPassengersNumber;
	}

	public Integer getTotalAdultPassengersNumber() {
		return totalAdultPassengersNumber;
	}

	public void setTotalAdultPassengersNumber(Integer totalAdultPassengersNumber) {
		this.totalAdultPassengersNumber = totalAdultPassengersNumber;
	}

	public Integer getFirstClassAdultPassengersNumber() {
		return firstClassAdultPassengersNumber;
	}

	public void setFirstClassAdultPassengersNumber(Integer firstClassAdultPassengersNumber) {
		this.firstClassAdultPassengersNumber = firstClassAdultPassengersNumber;
	}

	public Integer getBusinessClassAdultPassengersNumber() {
		return businessClassAdultPassengersNumber;
	}

	public void setBusinessClassAdultPassengersNumber(Integer businessClassAdultPassengersNumber) {
		this.businessClassAdultPassengersNumber = businessClassAdultPassengersNumber;
	}

	public Integer getEconomicClassAdultPassengersNumber() {
		return economicClassAdultPassengersNumber;
	}

	public void setEconomicClassAdultPassengersNumber(Integer economicClassAdultPassengersNumber) {
		this.economicClassAdultPassengersNumber = economicClassAdultPassengersNumber;
	}

	public Integer getFirstClassChildPassengersNumber() {
		return firstClassChildPassengersNumber;
	}

	public void setFirstClassChildPassengersNumber(Integer firstClassChildPassengersNumber) {
		this.firstClassChildPassengersNumber = firstClassChildPassengersNumber;
	}

	public Integer getBusinessClassChildPassengersNumber() {
		return businessClassChildPassengersNumber;
	}

	public void setBusinessClassChildPassengersNumber(Integer businessClassChildPassengersNumber) {
		this.businessClassChildPassengersNumber = businessClassChildPassengersNumber;
	}

	public Integer getEconomicClassChildPassengersNumber() {
		return economicClassChildPassengersNumber;
	}

	public void setEconomicClassChildPassengersNumber(Integer economicClassChildPassengersNumber) {
		this.economicClassChildPassengersNumber = economicClassChildPassengersNumber;
	}

	public Integer getTotalInfantPassengersNumber() {
		return totalInfantPassengersNumber;
	}

	public void setTotalInfantPassengersNumber(Integer totalInfantPassengersNumber) {
		this.totalInfantPassengersNumber = totalInfantPassengersNumber;
	}

	public Integer getFirstClassInfantPassengersNumber() {
		return firstClassInfantPassengersNumber;
	}

	public void setFirstClassInfantPassengersNumber(Integer firstClassInfantPassengersNumber) {
		this.firstClassInfantPassengersNumber = firstClassInfantPassengersNumber;
	}

	public Integer getBusinessClassInfantPassengersNumber() {
		return businessClassInfantPassengersNumber;
	}

	public void setBusinessClassInfantPassengersNumber(Integer businessClassInfantPassengersNumber) {
		this.businessClassInfantPassengersNumber = businessClassInfantPassengersNumber;
	}

	public Integer getEconomicClassInfantPassengersNumber() {
		return economicClassInfantPassengersNumber;
	}

	public void setEconomicClassInfantPassengersNumber(Integer economicClassInfantPassengersNumber) {
		this.economicClassInfantPassengersNumber = economicClassInfantPassengersNumber;
	}

	public Integer getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Integer totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * 全部婴儿数量
	 */
	@Column(name = "TOTAL_INFANT_PASSGER_NUM", length = 10)
	private Integer totalInfantPassengersNumber;

	/**
	 * 头等舱婴儿数量
	 */
	@Column(name = "FIRST_C_INFANT_PASSGER_NUM", length = 10)
	private Integer firstClassInfantPassengersNumber;

	/**
	 * 商务舱婴儿数量
	 */
	@Column(name = "BUSINESS_C_INFANT_PASSGER_NUM", length = 10)
	private Integer businessClassInfantPassengersNumber;

	/**
	 * 经济舱婴儿数量
	 */
	@Column(name = "ECONOMIC_C_INFANT_PASSGER_NUM", length = 10)
	private Integer economicClassInfantPassengersNumber;

	/**
	 * 总重量
	 */
	@Column(name = "TOTAL_WEIGHT", length = 10)
	private Integer totalWeight;
	// IDD更新，2013-09-18　end

	// IDD更新，2013-10-11 yaokai
	/**
	 * 实际开舱门时间
	 */
	@Column(name = "ACTUAL_DOOR_OPEN_TIME")
	@Temporal(TIMESTAMP)
	private Date actualDoorOpenDateTime;

	/**
	 * 实际关舱门时间
	 */
	@Column(name = "ACTUAL_DOOR_CLOSE_TIME")
	@Temporal(TIMESTAMP)
	private Date actualDoorCloseDateTime;

	/**
	 * 实际靠桥时间
	 */
	@Column(name = "ACTUAL_ON_BRIDGE_TIME")
	@Temporal(TIMESTAMP)
	private Date actualOnBridgeDateTime;

	/**
	 * 实际撤桥时间
	 */
	@Column(name = "ACTUAL_OFF_BRIDGE_TIME")
	@Temporal(TIMESTAMP)
	private Date actualOffBridgeDateTime;

	public Date getActualDoorOpenDateTime() {
		return actualDoorOpenDateTime;
	}

	public void setActualDoorOpenDateTime(Date actualDoorOpenDateTime) {
		this.actualDoorOpenDateTime = actualDoorOpenDateTime;
	}

	public Date getActualDoorCloseDateTime() {
		return actualDoorCloseDateTime;
	}

	public void setActualDoorCloseDateTime(Date actualDoorCloseDateTime) {
		this.actualDoorCloseDateTime = actualDoorCloseDateTime;
	}

	public Date getActualOnBridgeDateTime() {
		return actualOnBridgeDateTime;
	}

	public void setActualOnBridgeDateTime(Date actualOnBridgeDateTime) {
		this.actualOnBridgeDateTime = actualOnBridgeDateTime;
	}

	public Date getActualOffBridgeDateTime() {
		return actualOffBridgeDateTime;
	}

	public void setActualOffBridgeDateTime(Date actualOffBridgeDateTime) {
		this.actualOffBridgeDateTime = actualOffBridgeDateTime;
	}

	// IDD更新，2013-10-11 end

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
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

	public String getTransportProperty() {
		return transportProperty;
	}

	public void setTransportProperty(String transportProperty) {
		this.transportProperty = transportProperty;
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

	public String getFlightCountryType() {
		return flightCountryType;
	}

	public void setFlightCountryType(String flightCountryType) {
		this.flightCountryType = flightCountryType;
	}

	public Date getScheduledPreviousAirportDepartureDateTime() {
		return scheduledPreviousAirportDepartureDateTime;
	}

	public void setScheduledPreviousAirportDepartureDateTime(Date scheduledPreviousAirportDepartureDateTime) {
		this.scheduledPreviousAirportDepartureDateTime = scheduledPreviousAirportDepartureDateTime;
	}

	public Date getEstimatedPreviousAirportDepartureDateTime() {
		return estimatedPreviousAirportDepartureDateTime;
	}

	public void setEstimatedPreviousAirportDepartureDateTime(Date estimatedPreviousAirportDepartureDateTime) {
		this.estimatedPreviousAirportDepartureDateTime = estimatedPreviousAirportDepartureDateTime;
	}

	public Date getActualPreviousAirportDepartureDateTime() {
		return actualPreviousAirportDepartureDateTime;
	}

	public void setActualPreviousAirportDepartureDateTime(Date actualPreviousAirportDepartureDateTime) {
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

	public void setScheduledNextAirportArrivalDateTime(Date scheduledNextAirportArrivalDateTime) {
		this.scheduledNextAirportArrivalDateTime = scheduledNextAirportArrivalDateTime;
	}

	public Date getEstimatedNextAirportArrivalDateTime() {
		return estimatedNextAirportArrivalDateTime;
	}

	public void setEstimatedNextAirportArrivalDateTime(Date estimatedNextAirportArrivalDateTime) {
		this.estimatedNextAirportArrivalDateTime = estimatedNextAirportArrivalDateTime;
	}

	public Date getActualNextAirportArrivalDateTime() {
		return actualNextAirportArrivalDateTime;
	}

	public void setActualNextAirportArrivalDateTime(Date actualNextAirportArrivalDateTime) {
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

	public void setChangeLandingAirportIATACode(String changeLandingAirportIATACode) {
		this.changeLandingAirportIATACode = changeLandingAirportIATACode;
	}

	public String getChangeLandingAirportICAOCode() {
		return changeLandingAirportICAOCode;
	}

	public void setChangeLandingAirportICAOCode(String changeLandingAirportICAOCode) {
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

	public String getIsOverNightFlight2s() {
		if (isOverNightFlight != null) {
			if (isOverNightFlight.equals("Y"))
				return "是";
			if (isOverNightFlight.equals("N"))
				return "否";
		}
		return "";
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

	public void setInternationalPassengersNumber(Integer internationalPassengersNumber) {
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

	public void setRequireAssitancePassengersNumber(Integer requireAssitancePassengersNumber) {
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

	public String getFlightProperty() {
		return flightProperty;
	}

	public void setFlightProperty(String flightProperty) {
		this.flightProperty = flightProperty;
	}

	public String getIATAPreviousAirport() {
		return IATAPreviousAirport;
	}

	public void setIATAPreviousAirport(String iATAPreviousAirport) {
		IATAPreviousAirport = iATAPreviousAirport;
	}

	public String getIATANextAirport() {
		return IATANextAirport;
	}

	public void setIATANextAirport(String iATANextAirport) {
		IATANextAirport = iATANextAirport;
	}

	public String getIATADestinationAirport() {
		return IATADestinationAirport;
	}

	public void setIATADestinationAirport(String iATADestinationAirport) {
		IATADestinationAirport = iATADestinationAirport;
	}

	public String getICAOPreviousAirport() {
		return ICAOPreviousAirport;
	}

	public void setICAOPreviousAirport(String iCAOPreviousAirport) {
		ICAOPreviousAirport = iCAOPreviousAirport;
	}

	public String getICAONextAirport() {
		return ICAONextAirport;
	}

	public void setICAONextAirport(String iCAONextAirport) {
		ICAONextAirport = iCAONextAirport;
	}

	public String getScheduledLandingDateTime2s() {
		return DateUtils.Date2String(scheduledLandingDateTime);
	}

	public void setScheduledLandingDateTime2s(String scheduledLandingDateTime2s) {
		this.scheduledLandingDateTime2s = scheduledLandingDateTime2s;
	}

	public String getEstimatedLandingDateTime2s() {
		return DateUtils.Date2String(estimatedLandingDateTime);
	}

	public void setEstimatedLandingDateTime2s(String estimatedLandingDateTime2s) {
		this.estimatedLandingDateTime2s = estimatedLandingDateTime2s;
	}

	public String getActualLandingDateTime2s() {
		return DateUtils.Date2String(actualLandingDateTime);
	}

	public void setActualLandingDateTime2s(String actualLandingDateTime2s) {
		this.actualLandingDateTime2s = actualLandingDateTime2s;
	}

	public String getScheduledTakeOffDateTime2s() {
		return DateUtils.Date2String(scheduledTakeOffDateTime);
	}

	public void setScheduledTakeOffDateTime2s(String scheduledTakeOffDateTime2s) {
		this.scheduledTakeOffDateTime2s = scheduledTakeOffDateTime2s;
	}

	public String getEstimatedTakeOffDateTime2s() {
		return DateUtils.Date2String(estimatedTakeOffDateTime);
	}

	public void setEstimatedTakeOffDateTime2s(String estimatedTakeOffDateTime2s) {
		this.estimatedTakeOffDateTime2s = estimatedTakeOffDateTime2s;
	}

	public String getActualTakeOffDateTime2s() {
		return DateUtils.Date2String(actualTakeOffDateTime);
	}

	public void setActualTakeOffDateTime2s(String actualTakeOffDateTime2s) {
		this.actualTakeOffDateTime2s = actualTakeOffDateTime2s;
	}

	public String getFlightDirection() {
		return flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}
    // add by march start 20140825 过站成人、过站儿童、过站婴儿
    // TRANSIT_ADULT_PASSGER_NUM NUMBER(10) 过站成人
    // TRANSIT_CHILD_PASSGER_NUM NUMBER(10) 过站儿童
    // TRANSIT_INFANT_PASSGER_NUM NUMBER(10) 过站婴儿
	@Column(name = "TRANSIT_ADULT_PASSGER_NUM", length = 10)
	private Integer transitAdultPassgerNum  ;
	
	@Column(name = "TRANSIT_CHILD_PASSGER_NUM", length = 10) 
	private Integer transitChildPassgerNum;
	
	@Column(name = "TRANSIT_INFANT_PASSGER_NUM", length = 10)
    private Integer transitInfantPassgerNum;

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
    // add by march start 20140825
    
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
