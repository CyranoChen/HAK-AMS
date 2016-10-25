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
@Table(name = "PAIR_FLIGHT")
public class PairFlight implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TIMESTAMP)
	@Column(name = "execute_Date")
	private Date executeDate;

	@Column(name = "AIRLINE", length = 50)
	private String airline;

	@Column(name = "SUBSIDIARY_AIRLINE", length = 50)
	private String subsidiaryAirline;

	@Column(name = "PLANE_NUMBER", length = 50)
	private String planeNumber;

	@Column(name = "ARRIVE_FLIGHT_NUMBER", length = 20)
	private String arriveFlightNumber;

	@Column(name = "DEPARTURE_FLIGHT_NUMBER", length = 20)
	private String departureFlightNumber;

	@Column(name = "TAKE_OFF_PROPERTIES", length = 20)
	private String takeOffProperties;

	@Column(name = "FLIGHT_DESTINATION", length = 50)
	private String flightDestination;

	@Column(name = "FLIGHT_ROUTE", length = 50)
	private String flightRoute;

	@Column(name = "AIRCRAFT_CODE", length = 20)
	private String AircraftCode;

	@Temporal(TIMESTAMP)
	@Column(name = "DEPARTURE_TIME")
	private Date departureTime;

	@Temporal(TIMESTAMP)
	@Column(name = "ARRIVE_TIME")
	private Date arriveTime;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getSubsidiaryAirline() {
		return subsidiaryAirline;
	}

	public void setSubsidiaryAirline(String subsidiaryAirline) {
		this.subsidiaryAirline = subsidiaryAirline;
	}

	public String getPlaneNumber() {
		return planeNumber;
	}

	public void setPlaneNumber(String planeNumber) {
		this.planeNumber = planeNumber;
	}

	public String getArriveFlightNumber() {
		return arriveFlightNumber;
	}

	public void setArriveFlightNumber(String arriveFlightNumber) {
		this.arriveFlightNumber = arriveFlightNumber;
	}

	public String getDepartureFlightNumber() {
		return departureFlightNumber;
	}

	public void setDepartureFlightNumber(String departureFlightNumber) {
		this.departureFlightNumber = departureFlightNumber;
	}

	public String getTakeOffProperties() {
		return takeOffProperties;
	}

	public void setTakeOffProperties(String takeOffProperties) {
		this.takeOffProperties = takeOffProperties;
	}

	public String getFlightDestination() {
		return flightDestination;
	}

	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}

	public String getFlightRoute() {
		return flightRoute;
	}

	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}

	public String getAircraftCode() {
		return AircraftCode;
	}

	public void setAircraftCode(String aircraftCode) {
		AircraftCode = aircraftCode;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
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

}
