package com.nlia.fqdb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ChargeObject implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long id;

	private String airline;

	private String subsidiaryAirline;

	private BigDecimal dicount;

	private String other;

	private ChargeUnit chargeUnit;

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

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getSubsidiaryAirline() {
		return subsidiaryAirline;
	}

	public void setSubsidiaryAirline(String subsidiaryAirline) {
		this.subsidiaryAirline = subsidiaryAirline;
	}

	public BigDecimal getDicount() {
		return dicount;
	}

	public void setDicount(BigDecimal dicount) {
		this.dicount = dicount;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public ChargeUnit getChargeUnit() {
		return chargeUnit;
	}

	public void setChargeUnit(ChargeUnit chargeUnit) {
		this.chargeUnit = chargeUnit;
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

}
