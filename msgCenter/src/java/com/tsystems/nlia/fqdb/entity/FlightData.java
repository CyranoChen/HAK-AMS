package com.tsystems.nlia.fqdb.entity;

import java.io.Serializable;
import java.util.Date;

public class FlightData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Date flightScheduledDateTime;
	/*注册号*/
	private String registeration;
	

	private String callSign;
	/*客货属性*/
	private String transportProperty;
	/*航班属性*/
	private String flightProperty;
	/*飞机三字码*/
	private String aircraftIATACode;
	
	private String aircraftICAOCode;
	
	private String flightCategory;
	
	private String flightServiceType;
	/**
	 * 航班航路
	 */
	private String IATARouteAirport;
	
	private String IATAOriginAirport;
	
	private String IATAViaAirport;
	
	private String ICAO_Route_Airport;
	
	private String ICAOOriginAirport;
	
	private String ICAOViaAirport;
	
	private String ICAODestinationAirport;
	
	private String airportIATACode;
	
	private String airportICAOCode;
	
	private String flightCountryType;
	
	private Date scheduledPreviousAirportDepartureDateTime;
	
	private Date estimatedPreviousAirportDepartureDateTime;
	
	private Date actualPreviousAirportDepartureDateTime;
	
	private Date TenMilesDateTime;
	
	/**
	 * 计划落地时间
	 */
	private Date scheduledLandingDateTime;
	
	private Date estimatedLandingDateTime;
	
	private Date actualLandingDateTime;
	
	private Date scheduledOnBlockDateTime;
	
	private Date estimatedOnBlockDateTime;
	
	private Date actualOnBlockDateTime;
	
	private Date scheduledOffBlockDateTime;
	
	private Date estimatedOffBlockDateTime;
	
	private Date actualOffBlockDateTime;
	
	/**
	 * 计划起飞时间
	 */
	private Date scheduledTakeOffDateTime;
	
	private Date estimatedTakeOffDateTime;
	
	private Date actualTakeOffDateTime;
	
	private Date scheduledNextAirportArrivalDateTime;
	
	private Date estimatedNextAirportArrivalDateTime;
	
	private Date actualNextAirportArrivalDateTime;
	
	private Date bestKnownDateTime;
	
	private String operationStatus;
	
	private String flightStatus;
	
	private String delayCode;
	
	private String delayFreeText;
	
	private String diversionAirportIATACode;
	
	private String diversionAirportICAOCode;
	
	private String changeLandingAirportIATACode;
	
	private String changeLandingAirportICAOCode;
	
	private String displayCode;
	
	private String isTransitFlight;
	
	private String isOverNightFlight;
	
	private String isCashFlight;
	
	private Integer payloadWeight;
	
	private Integer passengerSeatingCapacity;
	
	private Integer maximumTakeoffWeight;
	
	private Integer crewNumber;
	
	private Integer totalPassengersNumber;
	
	private Integer internationalPassengersNumber;
	
	private Integer domesticPassengersNumber;
	
	private Integer transitPassengersNumber;
	
	private Integer totalChildrenNumber;
	
	private Integer requireAssitancePassengersNumber;
	
	private Integer totallBaggageWeight;
	
	private Integer internationalBaggageWeight;
	
	private Integer domesticBaggageWeight;
	
	private Integer transitlBaggageWeight;
	
	private Integer totalCargoWeight;
	
	private Integer internationalCargoWeight;
	
	private Integer domesticCargoWeight;
	
	private Integer transitCargoWeight;
	
	private Integer totallMailWeight;
	
	private Integer internationalMailWeight;
	
	private Integer domesticMailWeight;
	
	private Integer transitlMailWeight;
	
	private Long id;

	private String removeFlag;
	
	private String createUser;
	
	private Date createTime;
	
	private String modifyUser;
	
	private Date modifyTime;

	

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

	public String getICAO_Route_Airport() {
		return ICAO_Route_Airport;
	}

	public void setICAO_Route_Airport(String iCAO_Route_Airport) {
		ICAO_Route_Airport = iCAO_Route_Airport;
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
		return TenMilesDateTime;
	}

	public void setTenMilesDateTime(Date tenMilesDateTime) {
		TenMilesDateTime = tenMilesDateTime;
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
	public String getFlightProperty() {
		return flightProperty;
	}
	public void setFlightProperty(String flightProperty) {
		this.flightProperty = flightProperty;
	}
	

    // add by march start 20140825
    
    /*
     * add by march 20151124 因HAK而修改
     */
//	
//
//    private Date finalApproachTime;
//    
//    private String flightCAACServiceType;
//
//    private String flightIATAServiceType;
//
//    private String fullRouteAirportIATACode;
//
//    private String fullRouteAirportICAOCode;
//
//    public String getFlightCAACServiceType() {
//        return flightCAACServiceType;
//    }
//
//    public void setFlightCAACServiceType(String flightCAACServiceType) {
//        this.flightCAACServiceType = flightCAACServiceType;
//    }
//
//    public String getFlightIATAServiceType() {
//        return flightIATAServiceType;
//    }
//
//    public void setFlightIATAServiceType(String flightIATAServiceType) {
//        this.flightIATAServiceType = flightIATAServiceType;
//    }
//
//    public String getFullRouteAirportIATACode() {
//        return fullRouteAirportIATACode;
//    }
//
//    public void setFullRouteAirportIATACode(String fullRouteAirportIATACode) {
//        this.fullRouteAirportIATACode = fullRouteAirportIATACode;
//    }
//
//    public String getFullRouteAirportICAOCode() {
//        return fullRouteAirportICAOCode;
//    }
//
//    public void setFullRouteAirportICAOCode(String fullRouteAirportICAOCode) {
//        this.fullRouteAirportICAOCode = fullRouteAirportICAOCode;
//    }
//
//    public Date getFinalApproachTime() {
//        return finalApproachTime;
//    }
//
//    public void setFinalApproachTime(Date finalApproachTime) {
//        this.finalApproachTime = finalApproachTime;
//    }
//
//    
    
    /*
     * add by march 20151124 因HAK而修改
     */


}
