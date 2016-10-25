package com.nlia.fqdb.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.lang.Long;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "SYNTHETIC_CHARGE_TERM")
public class SyntheticChargeTerm implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue
	@Column(name="ID")
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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
