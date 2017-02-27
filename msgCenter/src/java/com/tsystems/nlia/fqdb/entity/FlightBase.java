package com.tsystems.nlia.fqdb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FlightBase implements Serializable {

	
	
	private static final long serialVersionUID = 1L;
	/* 航班计划日期*/
	private Date flightScheduledDate;
	/* 航班号全称*/
	private String flightIdentity;
	/* 航班方向*/
	private String flightDirection;
	/* 机场三字码*/
	private String airportIATACode;
	/* 起飞机场 */
	private String departureAirport;
	/* 降落机场 */
	private String destinationAirport;
	/* 机场四字码*/
	private String airportICAOCode;
	/* 航空公司二字码*/
	private String airlineIATACode;
	/* 航空公司三字码*/
	private String airlineICAOCode;
	/* 航班号*/
	private String flightNumber;
	/*航班后缀 */
	private String flightSuffix;
	/* 是否主航班*/
	private String isMasterFlight;
	/* 航班性质 内地、国际、港澳航班 */
	private String flightProperty;
	/* 出入境性质 出入境、非出入境 */
	private String entryExit;

	private FlightBase linkFlight;

	private FlightData flightData;

	private FlightResource flightResource;

	private Long id;

	private String removeFlag;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	//校验出错信息
	private String verifyDescription;

	/*
	 * 出错位置
	 * 如{1:{1;2}}　第一行有，第一个第二个单元格出错
	 */
	private Map<Integer,List<Integer>> errorMessage;

	public Map<Integer, List<Integer>> getErrorMessage() {
	    return errorMessage;
	}

	public void setErrorMessage(Map<Integer, List<Integer>> errorMessage) {
	    this.errorMessage = errorMessage;
	}
	
	public String getVerifyDescription() {
	    return verifyDescription;
	}

	public void setVerifyDescription(String verifyDescription) {
	    this.verifyDescription = verifyDescription;
	}
	
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

	public FlightBase getLinkFlight() {
		return linkFlight;
	}

	public void setLinkFlight(FlightBase linkFlight) {
		this.linkFlight = linkFlight;
	}

	public FlightData getFlightData() {
		return flightData;
	}

	public void setFlightData(FlightData flightData) {
		this.flightData = flightData;
	}

	public FlightResource getFlightResource() {
		return flightResource;
	}

	public void setFlightResource(FlightResource flightResource) {
		this.flightResource = flightResource;
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

	/*
	 * TRANSPORT_PROPERTY 客货属性 PASSENGER_PLANE 客机 CARGO_PLANE 货机
	 */
	public static enum TRANSPORT_PROPERTY {
		PASSENGER_PLANE("P"), CARGO_PLANE("C");
		private final String value;

		public String getValue() {
			return this.value;
		}

		TRANSPORT_PROPERTY(String value) {
			this.value = value;
		}
	}

	/*
	 * TYPE 进离港 INTO_PORT 进港 OUT_PORT 离港
	 */
	public static enum TYPE {
		INTO_PORT("0"), OUT_PORT("1");

		private final String value;

		public String getValue() {
			return this.value;
		}

		TYPE(String value) {
			this.value = value;
		}
	}
	/**
	 * FLIGHTPROPERTY 航班性质 
	 * INLAND 内地 
	 * INTERNATIONAL 国际 
	 * HKANDMACAO 港澳
	 */
	public static enum FLIGHTPROPERTY {
		INLAND("0"), INTERNATIONAL("1"), HKANDMACAO("2");

		private final String value;

		public String getValue() {
			return this.value;
		}

		FLIGHTPROPERTY(String value) {
			this.value = value;
		}
	}

	/**
	 * ENTRYEXIT 出入境性质 
	 * NOTENTRYEXIT 非出入境 
	 * ENTRYEXIT 出入境
	 */
	public static enum ENTRYEXIT {
		NOTENTRYEXIT("0"), ENTRYEXIT("1");

		private final String value;

		public String getValue() {
			return this.value;
		}

		ENTRYEXIT(String value) {
			this.value = value;
		}
	}
	
	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		FlightBase that = (FlightBase) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {

		int hashCode = 17;

		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}
}
