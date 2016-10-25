package com.nlia.fqdb.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "SYNTHETIC_CHARGE_TERM_VIEW")
public class SyntheticChargeTermDBView implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@Column(name = "AIRLINE_ICAO_CODE", length = 3)
	private String airlineICAOCode;
	
	@Column(name = "AIRLINE_NAME", length = 50)
	private String airlineName;
	
	@Column(name = "A_FLIGHT_IDENTITY", length = 20)
	private String aFlightIdentity;
	
	@Column(name = "D_FLIGHT_IDENTITY", length = 20)
	private String dFlightIdentity;
	
	@Column(name = "REGISTERATION", length = 20)
	private String registeration;
	
	@Column(name = "FLIGHT_ROUTE", length = 20)
	private String flightRoute;
	
	@Column(name = "FLIGHT_PART_0", length = 20)
	private String flightPart0;
	
	@Column(name = "FLIGHT_PART_1", length = 20)
	private String flightPart1;
	
	@Column(name = "EXECUTE_TIME")
	@Temporal(TIMESTAMP)
	private Date executeTime;
	
	@Column(name = "LANDED_TIME")
	@Temporal(TIMESTAMP)
	private Date landedTime;
	
	@Column(name = "TAKE_OFF_TIME")
	@Temporal(TIMESTAMP)
	private Date takeOffTime;
	
	@Column(name = "FLIGHT_PROPERTY",length = 255)
	private String flightProperty;
	
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
	
	@Column(name = "FLIGHT_DIRECTION")
	private String flightDirection;
	
	@OneToOne
	@JoinColumn(name = "FLIGHT_MATE_INFO_ID")
	private FlightMateInfo flightMateInfo;
	
	/**
	 * 停场费
	 */
	@Column(name = "PARKING_FEE")
	private double parkingFee;
	
	/**
	 * 安检费
	 */
	@Column(name = "SECURITY_CHECK_FEE")
	private double securityCheckFee;
	
	/**
	 * 旅客服务费
	 */
	@Column(name = "PASSENGER_SERVICE_FEE")
	private double passengerServiceFee;
	
	/**
	 * 起降费
	 */
	@Column(name = "ALIGHTING_AND_TAKE_OFF_FEE")
	private double alightingAndTakeOffFee;
	
	/**
	 * 客桥费
	 */
	@Column(name = "BRIDGE_FEE")
	private double bridgeFee;
	
	/**
	 * 值机柜台
	 */
	@Column(name = "CHECK_IN_COUNTER_FEE")
	private double check_inCounterFee;
	
	/**
	 * 运输服务费
	 */
	@Column(name = "TARNSPORTATION_SERVICE_FEE")
	private double transportationServiceFee;
	
	/**
	 * 飞机服务费
	 */
	@Column(name = "FLIGHT_SERVICE_FEE")
	private double flightServiceFee;
	
	/**
	 * 飞机勤务
	 */
	@Column(name = "FLIGHT_DUTY_FEE")
	private double flightDutyFee;
	
	/**
	 * 货物邮件服务费
	 */
	@Column(name = "WARES_AND_POST_FEE")
	private double waresAndPostFee;
	
	/**
	 * 一般代理服务费
	 */
	@Column(name = "GENERALLY_AGENT_FEE")
	private double generallyAgentFee;
	
	/**
	 * 售补票柜台
	 */
	@Column(name = "BOOKING_OFFICE_FEE")
	private double bookingOfficeFee;
	
	@Column(name = "FLIGHT_COUNTRY_TYPE", length = 10)
	private String flightCountryType;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}  

	public String getRegisteration() {
		return registeration;
	}

	public void setRegisteration(String registeration) {
		this.registeration = registeration;
	}

	public String getFlightRoute() {
		return flightRoute;
	}

	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
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

	public Date getLandedTime() {
		return landedTime;
	}

	public void setLandedTime(Date landedTime) {
		this.landedTime = landedTime;
	}

	public Date getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	public String getFlightProperty() {
		return flightProperty;
	}

	public void setFlightProperty(String flightProperty) {
		this.flightProperty = flightProperty;
	}

	public FlightMateInfo getFlightMateInfo() {
		return this.flightMateInfo;
	}

	public void setFlightMateInfo(FlightMateInfo flightMateInfo) {
		this.flightMateInfo = flightMateInfo;
	}   
	public double getParkingFee() {
		return this.parkingFee;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}   
	public double getSecurityCheckFee() {
		return this.securityCheckFee;
	}

	public void setSecurityCheckFee(double securityCheckFee) {
		this.securityCheckFee = securityCheckFee;
	}   
	public double getPassengerServiceFee() {
		return this.passengerServiceFee;
	}

	public void setPassengerServiceFee(double passengerServiceFee) {
		this.passengerServiceFee = passengerServiceFee;
	}   
	public double getAlightingAndTakeOffFee() {
		return this.alightingAndTakeOffFee;
	}

	public void setAlightingAndTakeOffFee(double alightingAndTakeOffFee) {
		this.alightingAndTakeOffFee = alightingAndTakeOffFee;
	}   
	public double getBridgeFee() {
		return this.bridgeFee;
	}

	public void setBridgeFee(double bridgeFee) {
		this.bridgeFee = bridgeFee;
	}   
	public double getCheck_inCounterFee() {
		return this.check_inCounterFee;
	}

	public void setCheck_inCounterFee(double check_inCounterFee) {
		this.check_inCounterFee = check_inCounterFee;
	}   
	public double getTransportationServiceFee() {
		return this.transportationServiceFee;
	}

	public void setTransportationServiceFee(double transportationServiceFee) {
		this.transportationServiceFee = transportationServiceFee;
	}   
	public double getFlightServiceFee() {
		return this.flightServiceFee;
	}

	public void setFlightServiceFee(double flightServiceFee) {
		this.flightServiceFee = flightServiceFee;
	}   
	public double getFlightDutyFee() {
		return this.flightDutyFee;
	}

	public void setFlightDutyFee(double flightDutyFee) {
		this.flightDutyFee = flightDutyFee;
	}   
	public double getWaresAndPostFee() {
		return this.waresAndPostFee;
	}

	public void setWaresAndPostFee(double waresAndPostFee) {
		this.waresAndPostFee = waresAndPostFee;
	}   
	public double getGenerallyAgentFee() {
		return this.generallyAgentFee;
	}

	public void setGenerallyAgentFee(double generallyAgentFee) {
		this.generallyAgentFee = generallyAgentFee;
	}   
	public double getBookingOfficeFee() {
		return this.bookingOfficeFee;
	}

	public void setBookingOfficeFee(double bookingOfficeFee) {
		this.bookingOfficeFee = bookingOfficeFee;
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
	/*
	 * 配载、通信、集装设备管理
	 */
	@Column(name = "STOWAGE_COMMUNICATION_FEE")
	private double stowageCommunicationFee;

    public double getStowageCommunicationFee() {
        return stowageCommunicationFee;
    }

    public void setStowageCommunicationFee(double stowageCommunicationFee) {
        this.stowageCommunicationFee = stowageCommunicationFee;
    }

	public String getAirlineICAOCode() {
		return airlineICAOCode;
	}

	public void setAirlineICAOCode(String airlineICAOCode) {
		this.airlineICAOCode = airlineICAOCode;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getaFlightIdentity() {
		return aFlightIdentity;
	}

	public void setaFlightIdentity(String aFlightIdentity) {
		this.aFlightIdentity = aFlightIdentity;
	}

	public String getdFlightIdentity() {
		return dFlightIdentity;
	}

	public void setdFlightIdentity(String dFlightIdentity) {
		this.dFlightIdentity = dFlightIdentity;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public String getFlightCountryType() {
		return flightCountryType;
	}

	public void setFlightCountryType(String flightCountryType) {
		this.flightCountryType = flightCountryType;
	}

	public String getFlightDirection() {
		return flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}
    
    
    
}
