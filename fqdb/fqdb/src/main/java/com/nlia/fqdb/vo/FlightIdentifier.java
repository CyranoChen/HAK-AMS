package com.nlia.fqdb.vo;

import java.io.Serializable;
import java.util.Date;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightBase.TYPE;

public class FlightIdentifier implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public FlightIdentifier(Object[] values){
		if(values.length == 4){
			this.flightId = (Long) values[0];
			this.flightScheduledDate =  (Date) values[1];
			this.flightIdentity =  (String) values[2];
			this.flightDirection =  (String) values[3];
		}else{
			
		}
	}
	
	public FlightIdentifier(FlightBase fb){
		this.flightId = fb.getId();
		this.flightScheduledDate =  fb.getFlightScheduledDate();
		this.flightIdentity =  fb.getFlightIdentity();
		this.flightDirection =  fb.getFlightDirection();
	}
	
	/*航班主键id*/
	private Long flightId;
	
	/* 航班计划日期*/
	private Date flightScheduledDate;
	
	/* 航班号全称*/
	private String flightIdentity;

	/* 航班方向*/
	private String flightDirection;

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public Date getFlightScheduledDate() {
		return flightScheduledDate;
	}

	public void setFlightScheduledDate(Date flightScheduledDate) {
		this.flightScheduledDate = flightScheduledDate;
	}

	public String getFlightIdentity() {
		return flightIdentity;
	}

	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}

	public String getFlightDirection() {
		return flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}
	
	public String getFlightDirectionInBussiness() {
		if(TYPE.INTO_PORT.equals(flightDirection)){
			return "进港";
		}
		if(TYPE.OUT_PORT.equals(flightDirection)){
			return "离港";
		}
		if(TYPE.LINKED_FLIGHT.equals(flightDirection)){
			return "连班";
		}
		return  "";
	}

//	@Override
//	public String toString(){
//		StringBuffer sb = new StringBuffer();
//		sb.append(" ID: " + getFlightId());
//		sb.append(", FlightIdentity: " + getFlightIdentity());
//		sb.append(", FlightDirection: " + getFlightDirectionInBussiness());
//		sb.append(", FlightScheduledDate: " + getFlightScheduledDate());
//		return sb.toString();
//		
//	}	
}
