package com.wonders.frame.ams.dto.basic;

/**
 * Created by wangsq on 2016/9/14.
 */
public class DiscountDto {

    private String id;
    private String registration;
    private String chargetypeId;
    private String chargesubjectId;
    private String discount;

    private String chargeTypeName;
    private String chargeSubjectName;


    private String airlineOfFlight; //该机号 飞机 所属航空公司
    private String aircraftTypeIataCode;    //机型
    private String aircraftSeatCapacity;    //座位数
    private String airlineDescription; // 航空公司名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getChargetypeId() {
        return chargetypeId;
    }

    public void setChargetypeId(String chargetypeId) {
        this.chargetypeId = chargetypeId;
    }

    public String getChargesubjectId() {
        return chargesubjectId;
    }

    public void setChargesubjectId(String chargesubjectId) {
        this.chargesubjectId = chargesubjectId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public String getChargeSubjectName() {
        return chargeSubjectName;
    }

    public void setChargeSubjectName(String chargeSubjectName) {
        this.chargeSubjectName = chargeSubjectName;
    }

    public String getAirlineOfFlight() {
        return airlineOfFlight;
    }

    public void setAirlineOfFlight(String airlineOfFlight) {
        this.airlineOfFlight = airlineOfFlight;
    }

    public String getAircraftTypeIataCode() {
        return aircraftTypeIataCode;
    }

    public void setAircraftTypeIataCode(String aircraftTypeIataCode) {
        this.aircraftTypeIataCode = aircraftTypeIataCode;
    }

    public String getAircraftSeatCapacity() {
        return aircraftSeatCapacity;
    }

    public void setAircraftSeatCapacity(String aircraftSeatCapacity) {
        this.aircraftSeatCapacity = aircraftSeatCapacity;
    }

    public String getAirlineDescription() {
        return airlineDescription;
    }

    public void setAirlineDescription(String airlineDescription) {
        this.airlineDescription = airlineDescription;
    }
}
