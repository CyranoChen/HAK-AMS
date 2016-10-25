package com.nlia.fqdb.vo;

import java.io.Serializable;

public class SyntheticChargeInfoVo implements Serializable{
	
	private Long flightMateInfoId;	//航班ID
	private Long chargeTypeId;		//收费类型
	private Double fee;						//总金额
	
	public Long getFlightMateInfoId() {
		return flightMateInfoId;
	}
	public void setFlightMateInfoId(Long flightMateInfoId) {
		this.flightMateInfoId = flightMateInfoId;
	}
	public Long getChargeTypeId() {
		return chargeTypeId;
	}
	public void setChargeTypeId(Long chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	
}
