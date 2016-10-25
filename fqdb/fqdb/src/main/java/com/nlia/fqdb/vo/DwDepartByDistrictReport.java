package com.nlia.fqdb.vo;

import java.math.BigDecimal;


public class DwDepartByDistrictReport {
	/*架次*/
	private BigDecimal departTimes;
	/*机场所在区域*/
	private String airportDistrict;
	/*总旅客数*/
	private BigDecimal totalPassengerNum;
	/*过站旅客数*/
	private BigDecimal transitPassengersNum;
	/*总货物数*/
	private BigDecimal totalCargoWeight;

	public DwDepartByDistrictReport() {
	}

	public DwDepartByDistrictReport(String airportDistrict,BigDecimal departTimes,BigDecimal totalPassengerNum,BigDecimal transitPassengersNum,BigDecimal totalCargoWeight) {
		this.departTimes = departTimes;
		this.airportDistrict = airportDistrict;
		this.totalPassengerNum = totalPassengerNum;
		this.transitPassengersNum = transitPassengersNum;
		this.totalCargoWeight = totalCargoWeight;
	}

	public BigDecimal getDepartTimes() {
		return departTimes;
	}

	public void setDepartTimes(BigDecimal departTimes) {
		this.departTimes = departTimes;
	}

	public String getAirportDistrict() {
		return airportDistrict;
	}

	public void setAirportDistrict(String airportDistrict) {
		this.airportDistrict = airportDistrict;
	}

	public BigDecimal getTotalPassengerNum() {
		return totalPassengerNum;
	}

	public void setTotalPassengerNum(BigDecimal totalPassengerNum) {
		this.totalPassengerNum = totalPassengerNum;
	}

	public BigDecimal getTransitPassengersNum() {
		return transitPassengersNum;
	}

	public void setTransitPassengersNum(BigDecimal transitPassengersNum) {
		this.transitPassengersNum = transitPassengersNum;
	}

	public BigDecimal getTotalCargoWeight() {
		return totalCargoWeight;
	}

	public void setTotalCargoWeight(BigDecimal totalCargoWeight) {
		this.totalCargoWeight = totalCargoWeight;
	}

}
