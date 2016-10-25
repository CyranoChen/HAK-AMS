package com.nlia.fqdb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

public class ChargeUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String describe;

	private String unitdescribe;

	private String currency;

	private String chargeType;

	private String isDomestic;

	private String removeFlag;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;
	
	@Transient
	private String verifyDescription;
	
	@Transient
	private Map<Integer,List<Integer>> errorMessage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getUnitdescribe() {
		return unitdescribe;
	}

	public void setUnitdescribe(String unitdescribe) {
		this.unitdescribe = unitdescribe;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getIsDomestic() {
		return isDomestic;
	}

	public void setIsDomestic(String isDomestic) {
		this.isDomestic = isDomestic;
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

	public String getVerifyDescription() {
		return verifyDescription;
	}

	public void setVerifyDescription(String verifyDescription) {
		this.verifyDescription = verifyDescription;
	}

	public Map<Integer,List<Integer>> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(Map<Integer,List<Integer>> errorMessage) {
		this.errorMessage = errorMessage;
	}

	

}
