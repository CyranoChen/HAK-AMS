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

@Entity

@Table(name = "CHARGE_TERM_VIEW")
public class ChargeTermDBView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "AIRLINE_ICAO_CODE", length = 3)
	private String airlineICAOCode;
	
	@Column(name = "AIRLINE_NAME", length = 50)
	private String airlineName;
	
	@Column(name = "EXECUTE_TIME")
	@Temporal(TIMESTAMP)
	private Date executeTime;
	
	@Column(name = "FLIGHT_IDENTITY", length = 20)
	private String flightIdentity;
	
	@Column(name = "REGISTERATION", length = 20)
	private String registeration;
	
	@Column(name = "FLIGHT_ROUTE", length = 20)
	private String flightRoute;
	
	@Column(name = "FLIGHT_PART_0", length = 20)
	private String flightPart0;
	
	@Column(name = "FLIGHT_PART_1", length = 20)
	private String flightPart1;
	
	@Column(name = "LANDED_TIME")
	@Temporal(TIMESTAMP)
	private Date landedTime;
	
	@Column(name = "TAKE_OFF_TIME")
	@Temporal(TIMESTAMP)
	private Date takeOffTime;
	
	@Column(name = "FLIGHT_PROPERTY",length = 255)
	private String flightProperty;
	
	@Column(name = "NAME", length = 255)
	private String name;
	
	@Column(name = "FEE")
	private Double fee;
	
	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;
	
	@Column(name = "CHARGE_TYPE_NAME")
	private String chargeTypeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public String getFlightIdentity() {
		return flightIdentity;
	}

	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}

	public String getChargeTypeName() {
		return chargeTypeName;
	}

	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	
	
	
}
