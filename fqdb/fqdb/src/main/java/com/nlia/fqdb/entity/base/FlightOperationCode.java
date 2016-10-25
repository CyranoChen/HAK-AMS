package com.nlia.fqdb.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.nlia.fqdb.util.AbstractPersistable;

@Entity
@Table(name = "BASE_FLIGHT_OPERATION_CODE")
public class FlightOperationCode extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@Column(name = "BASIC_DATA_ID", length = 20)
	private String basicDataID;
	
	/**
	 * 航班运营状态代码
	 */
	@Column(name = "FLIGHT_OPERATION_CODE", length = 10)
	private String flightOperationCode;
	
	/**
	 * 航班运营状态代码说明
	 */
	@Column(name = "FLIGHT_OPERATION_CODE_DESCRIPT", length = 50)
	private String flightOperationCodeDescription;
	
	public String getBasicDataID() {
		return basicDataID;
	}

	public void setBasicDataID(String basicDataID) {
		this.basicDataID = basicDataID;
	}

	public String getFlightOperationCode() {
		return flightOperationCode;
	}

	public void setFlightOperationCode(String flightOperationCode) {
		this.flightOperationCode = flightOperationCode;
	}

	public String getFlightOperationCodeDescription() {
		return flightOperationCodeDescription;
	}

	public void setFlightOperationCodeDescription(String flightOperationCodeDescription) {
		this.flightOperationCodeDescription = flightOperationCodeDescription;
	}
}
