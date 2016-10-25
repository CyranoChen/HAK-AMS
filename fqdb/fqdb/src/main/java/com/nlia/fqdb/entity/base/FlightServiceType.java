package com.nlia.fqdb.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.nlia.fqdb.util.AbstractPersistable;

@Entity
@Table(name = "BASE_FLIGHT_SERVICE_TYPE")
public class FlightServiceType extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@Column(name = "BASIC_DATA_ID", length = 20)
	private String basicDataID;
	
	/**
	 * 服务类型代码
	 */
	@Column(name = "SERVICE_TYPE_CODE", length = 10)
	private String serviceTypeCode;
	
	/**
	 * 服务类型描述
	 */
	@Column(name = "SERVICE_TYPE_DESCRIPTION", length = 50)
	private String serviceTypeDescription;
	
	public String getBasicDataID() {
		return basicDataID;
	}

	public void setBasicDataID(String basicDataID) {
		this.basicDataID = basicDataID;
	}

	public String getServiceTypeCode() {
		return serviceTypeCode;
	}

	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}

	public String getServiceTypeDescription() {
		return serviceTypeDescription;
	}

	public void setServiceTypeDescription(String serviceTypeDescription) {
		this.serviceTypeDescription = serviceTypeDescription;
	}
	/*
     * add by march 20151124 因HAK而修改
     */
	@Column(name = "FLIGHT_IATA_SERVICE_TYPE", length = 20)
	private String flightIATAServiceType;
	@Column(name = "FLIGHT_CAAC_SERVICE_TYPE", length = 20)
	private String flightCAACServiceType;

    public String getFlightIATAServiceType() {
        return flightIATAServiceType;
    }

    public void setFlightIATAServiceType(String flightIATAServiceType) {
        this.flightIATAServiceType = flightIATAServiceType;
    }

    public String getFlightCAACServiceType() {
        return flightCAACServiceType;
    }

    public void setFlightCAACServiceType(String flightCAACServiceType) {
        this.flightCAACServiceType = flightCAACServiceType;
    }

    

    
    /*
     * add by march 20151124 因HAK而修改
     */
	
}
