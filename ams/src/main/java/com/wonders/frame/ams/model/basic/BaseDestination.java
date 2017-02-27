package com.wonders.frame.ams.model.basic;

import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;

import javax.persistence.*;


@Entity
@Table(name = "BASE_AIRPORT")
public class BaseDestination implements ILongIdRemoveFlagModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;


    /**
     *  机场所在城市
     */
    @Column(name = "AIRPORT_CITY", length = 10)
    private String airportCity;

    /**
     *  机场所在国家
     */
    @Column(name = "AIRPORT_COUNTRY", length = 10)
    private String airportCountry;

    /**
     *   所在国家类型
     */
    @Column(name = "AIRPORT_COUNTRY_TYPE", length = 10)
    private String airportCountryType;

    /**
     *   机场描述
     */
    @Column(name = "AIRPORT_DESCRIPTION", length = 10)
    private String airportDescription;


    /**
     *    机场三字码
     */
    @Column(name = "AIRPORT_IATA_CODE", length = 10)
    private String airportIATACode;

    /**
     *    机场四字码
     */
    @Column(name = "AIRPORT_ICAO_CODE", length = 10)
    private String airportICAOCode;

    /**
     *   存放德电传过来的基础数据的唯一标识符，用于更新删除操作
     */
    @Column(name = "BASIC_DATA_ID", length = 10)
    private String basicDataId;

    /**
     *  机场路由名称
     */
    @Column(name = "AIRPORT_ROUTING_NAME", length = 10)
    private String airportRoutingName;

    /**
     *    逻辑删除
     */
    @Column(name = "REMOVE_FLAG", length = 10)
    private String removeFlag;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public void setAirportCity(String airportCity) {
        this.airportCity = airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    public void setAirportCountry(String airportCountry) {
        this.airportCountry = airportCountry;
    }

    public String getAirportCountryType() {
        return airportCountryType;
    }

    public void setAirportCountryType(String airportCountryType) {
        this.airportCountryType = airportCountryType;
    }

    public String getAirportDescription() {
        return airportDescription;
    }

    public void setAirportDescription(String airportDescription) {
        this.airportDescription = airportDescription;
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

    public String getBasicDataId() {
        return basicDataId;
    }

    public void setBasicDataId(String basicDataId) {
        this.basicDataId = basicDataId;
    }

    public String getAirportRoutingName() {
        return airportRoutingName;
    }

    public void setAirportRoutingName(String airportRoutingName) {
        this.airportRoutingName = airportRoutingName;
    }

    @Override
    public String getRemoveFlag() {
        return removeFlag;
    }

    @Override
    public void setRemoveFlag(String removeFlag) {
        this.removeFlag = removeFlag;
    }

    @Override
    public String toString() {
        return "BaseDestination{" +
                "airportCity='" + airportCity + '\'' +
                ", airportCountry='" + airportCountry + '\'' +
                ", basicDataId='" + basicDataId + '\'' +
                '}';
    }
}
