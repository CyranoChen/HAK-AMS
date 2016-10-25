package com.nlia.fqdb.entity;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.nlia.fqdb.service.impl.FlightBaseManager;

@SuppressWarnings("rawtypes")
@Entity
@Table(name = "FLIGHT_BASE")
public class FlightBase implements Serializable, Comparable {

	private static final long serialVersionUID = 1L;
	/**
	 * 航班业务时间（进港取落地时间，离港取起飞时间）
	 * 只存在内存里
	 */
	@Transient
	private Date flightTime;

	/* 航班计划日期*/
	@Column(name = "FLIGHT_SCHEDULED_DATE")
	@Temporal(DATE)
	private Date flightScheduledDate;
	/* 航班号全称*/
	@Column(name = "FLIGHT_IDENTITY", length = 20)
	private String flightIdentity;
	/* 航班方向*/
	@Column(name = "FLIGHT_DIRECTION", length = 20)
	private String flightDirection;
	/* 机场三字码*/
	@Column(name = "AIRPORT_IATA_CODE", length = 20)
	private String airportIATACode;
	/* 起飞机场 */
	@Column(name = "DEPARTURE_AIRPORT", length = 20)
	private String departureAirport;
	/* 降落机场 */
	@Column(name = "DESTINATION_AIRPORT", length = 20)
	private String destinationAirport;
	/* 机场四字码*/
	@Column(name = "AIRPORT_ICAO_CODE", length = 20)
	private String airportICAOCode;
	/* 航空公司二字码*/
	@Column(name = "AIRLINE_IATA_CODE", length = 20)
	private String airlineIATACode;
	/* 航空公司三字码*/
	@Column(name = "AIRLINE_ICAO_CODE", length = 20)
	private String airlineICAOCode;
	/* 航班号*/
	@Column(name = "FLIGHT_NUMBER", length = 20)
	private String flightNumber;
	/*航班后缀 */
	@Column(name = "FLIGHT_SUFFIX", length = 20)
	private String flightSuffix;
	/* 是否主航班*/
	@Column(name = "IS_MASTER_FLIGHT", length = 1)
	private String isMasterFlight;
	/* 航班性质 内地、国际、港澳航班 */
	@Column(name = "FLIGHT_PROPERTY")
	private String flightProperty;
	/* 出入境性质 出入境、非出入境 */
	@Column(name = "ENTRY_EXIT")
	private String entryExit;
	
	/* 连班标记*/
	@Column(name = "LINK_FLIGHT_SIGN", length = 38)
	private String linkFlightSign;
	
	@OneToOne
	@JoinColumn(name = "LINK_FLIGHT_ID")
	private FlightBase linkFlight;

	@OneToOne(cascade = { PERSIST, MERGE, REFRESH })
	@JoinColumn(name = "FLIGHT_DATA_ID")
	private FlightData flightData;

	@OneToOne(cascade = { PERSIST, MERGE, REFRESH })
	@JoinColumn(name = "FLIGHT_RESOURCE_ID")
	private FlightResource flightResource;
	
	//是否加锁(0未锁,1加锁)
	@Column(name = "IS_LOCK", length = 1)
	private String isLock;
	
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

	@Transient
	//校验出错信息
	private String verifyDescription;
	
//	@Transient
	@Column(name="SHARED_FLIGHT_IDENTITY" ,length=100)
	//共享航班号（mu1234|mu3456|mu4567）
	private String sharedFlightIdentity;

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
	
	public String getVerifyDescription() {
	    return verifyDescription;
	}

	
	public String getSharedFlightIdentity() {
		return sharedFlightIdentity;
	}

	public void setSharedFlightIdentity(String sharedFlightIdentity) {
		this.sharedFlightIdentity = sharedFlightIdentity;
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

	public Date getFlightTime() {
		if(flightTime == null) {
			try {
				FlightBaseManager.initFlightTime(this);
			} catch (NullPointerException e) {
				if(flightData != null && flightData.getFlightScheduledDateTime() != null)
					flightTime = this.flightData.getFlightScheduledDateTime();
			} 
		}
		return flightTime;
	}

	public void setFlightTime(Date flightTime) {
		this.flightTime = flightTime;
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
		INTO_PORT("0"), OUT_PORT("1"),LINKED_FLIGHT("2");

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
	
	/**
	 * 设置创建人，创建时间
	 * @return
	 */
	public boolean setCreateInfo(String createUser){
		this.setCreateTime(new Date());
		this.setCreateUser(createUser);
		this.getFlightData().setCreateTime(new Date());
		this.getFlightData().setCreateUser(createUser);
		this.getFlightResource().setCreateTime(new Date());
		this.getFlightResource().setCreateUser(createUser);
		return true;
	}
	
	/**
	 * 设置修改人，修改时间
	 * @return
	 */
	public boolean setModifyInfo(String modifyUser){
		this.setModifyTime(new Date());
		this.setModifyUser(modifyUser);
		this.getFlightData().setModifyTime(new Date());
		this.getFlightData().setModifyUser(modifyUser);
		this.getFlightResource().setModifyTime(new Date());
		this.getFlightResource().setModifyUser(modifyUser);
		return true;
	}
	
	@Override
	public int compareTo(Object o) {
//		FlightBase args1 = this.flightTime.getTime();
//		FlightBase args2 = (FlightBase)o;
		FlightBase args = (FlightBase)o;
//		return (int)(this.flightTime.getTime() - args.getFlightTime().getTime()) ;
		return this.flightTime.getTime() == args.getFlightTime().getTime() ? 0 :  
            (this.flightTime.getTime()  > args.getFlightTime().getTime() ? 1 : -1);
	}
	

	public String getFlightDirectionInBussiness() {
		if(TYPE.INTO_PORT.getValue().equals(flightDirection)){
			return "进港";
		}
		if(TYPE.OUT_PORT.getValue().equals(flightDirection)){
			return "离港";
		}
		if(TYPE.LINKED_FLIGHT.getValue().equals(flightDirection)){
			return "连班";
		}
		return  "";
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("{\"flightId:\" " +"\"" + getId()+ "\"");
		sb.append(", \"flightScheduledDate:\" " +"\""+ getFlightScheduledDate() +"\"");
		sb.append(", \"flightIdentity:\" " +"\""+ getFlightIdentity()+"\"");
		sb.append(", \"flightDirection:\" " +"\""+ getFlightDirectionInBussiness() +"\"}");
		return sb.toString();
		
	}

}
