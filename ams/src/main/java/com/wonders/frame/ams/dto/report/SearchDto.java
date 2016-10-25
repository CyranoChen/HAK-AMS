package com.wonders.frame.ams.dto.report;


public class SearchDto {

	private String startTime; //开始时间
	private String endTime; //结束时间
	private String standNum; //机位
	private String flightDirection; //进离港标识
	private String flightIdentity; //航班号
	private String airLine; //航空公司
	private String registeration; //机号
	private String agent; //代理
	
	
	public SearchDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStandNum() {
		return standNum;
	}
	public void setStandNum(String standNum) {
		this.standNum = standNum;
	}
	public String getFlightDirection() {
		return flightDirection;
	}
	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}
	public String getFlightIdentity() {
		return flightIdentity;
	}
	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}
	public String getAirLine() {
		return airLine;
	}
	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}
	public String getRegisteration() {
		return registeration;
	}
	public void setRegisteration(String registeration) {
		this.registeration = registeration;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
}
