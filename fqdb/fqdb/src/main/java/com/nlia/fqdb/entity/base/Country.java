package com.nlia.fqdb.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.nlia.fqdb.util.AbstractPersistable;

@Entity
@Table(name = "BASE_COUNTRY")
public class Country extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@Column(name = "BASIC_DATA_ID", length = 20)
	private String basicDataID;
	
	/**
	 * 城市三字码
	 */
	@Column(name = "COUNTRY_IATA_CODE", length = 10)
	private String countryIATACode;
	

	/**
	 * 城市四字码
	 */
	@Column(name = "COUNTRY_ICAO_CODE", length = 50)
	private String countryICAOCode;
	
	/**
	 * 城市名
	 */
	@Column(name = "COUNTRY_NAME", length = 50)
	private String countryName;
	
	/**
	 * 城市类型
	 */
	@Column(name = "COUNTRY_TYPE", length = 50)
	private String countryType;
	
	/**
	 * 城市所在区域
	 */
	@Column(name = "COUNTRY_AREA", length = 50)
	private String countryArea;

	public String getBasicDataID() {
		return basicDataID;
	}

	public void setBasicDataID(String basicDataID) {
		this.basicDataID = basicDataID;
	}

	public String getCountryIATACode() {
		return countryIATACode;
	}

	public void setCountryIATACode(String countryIATACode) {
		this.countryIATACode = countryIATACode;
	}

	public String getCountryICAOCode() {
		return countryICAOCode;
	}

	public void setCountryICAOCode(String countryICAOCode) {
		this.countryICAOCode = countryICAOCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryType() {
		return countryType;
	}

	public void setCountryType(String countryType) {
		this.countryType = countryType;
	}

	public String getCountryArea() {
		return countryArea;
	}

	public void setCountryArea(String countryArea) {
		this.countryArea = countryArea;
	}

}
