package com.nlia.fqdb.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nlia.fqdb.entity.ChargeUnit;

public class ChargeUnitListVo implements Serializable{

	private static final long serialVersionUID = 1L;

	
	List<ChargeUnit> chargeUnitList = new ArrayList<ChargeUnit>();
	
	int totaldata;
		
	private String verifyDescription;


	public List<ChargeUnit> getChargeUnitList() {
		return chargeUnitList;
	}

	public void setChargeUnitList(List<ChargeUnit> chargeUnitList) {
		this.chargeUnitList = chargeUnitList;
	}

	public int getTotaldata() {
		return totaldata;
	}

	public void setTotaldata(int totaldata) {
		this.totaldata = totaldata;
	}


	public String getVerifyDescription() {
		return verifyDescription;
	}

	public void setVerifyDescription(String verifyDescription) {
		this.verifyDescription = verifyDescription;
	}

}
