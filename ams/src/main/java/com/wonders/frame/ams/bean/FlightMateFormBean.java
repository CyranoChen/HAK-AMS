package com.wonders.frame.ams.bean;

/**
 * Created by 3701 on 2015/12/11.
 */
public class FlightMateFormBean {

    private String flightIdentity;
    private String scheduledTime;
    
    private String airlineHandler;

    private String airLineName;
    private String registeration;

    private String flightDirection;

    private String standFlag;


    public String getFlightIdentity() {
        return flightIdentity;
    }

    public void setFlightIdentity(String flightIdentity) {
        this.flightIdentity = flightIdentity;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getAirLineName() {
        return airLineName;
    }

    public void setAirLineName(String airLineName) {
        this.airLineName = airLineName;
    }

    public String getRegisteration() {
        return registeration;
    }

    public void setRegisteration(String registeration) {
        this.registeration = registeration;
    }

    public String getFlightDirection() {
        return flightDirection;
    }

    public void setFlightDirection(String flightDirection) {
        this.flightDirection = flightDirection;
    }

    public String getStandFlag() {
        return standFlag;
    }

    public void setStandFlag(String standFlag) {
        this.standFlag = standFlag;
    }

	public String getAirlineHandler() {
		return airlineHandler;
	}

	public void setAirlineHandler(String airlineHandler) {
		this.airlineHandler = airlineHandler;
	}


}
