package com.wonders.frame.ams.bean;

import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.StringUtil;

/**
 * Created by 3701 on 2015/12/14.
 */
public class FlightMateDetailBean {
	// Fields
	private Long id;
	public String flightDirection;//public 修饰 勿改
	private String airLineName;
	private String airlineHandler;
	private String registeration;
	private String scheduledTime;
	private String flightIdentity;
	private String aFlightIdentity;
	private String dFlightIdentity;
	private String flightBaseId;
	private String scheduledLandedTime;
	private String landedTime;
	private String scheduledTakeOffTime;
	private String takeOffTime;
	private String iataOriginAirport;
	private String iataDestinationAirport;
	private String standNum;
	private String standFlag;
	private String flightProperty;
	private String landTime;
	private String flightRoute;
	private String matchMethod;
	private String delayCode;
	private String flightPart0;
	private String flightPart1;
	private String flightPart2;
	private String isNightFlight;
	private String isPeakFlight;
	private String aircraftService;
	private String specialPlane;
	private String aircraftType;
	private String aircraftPayload;
	private String aircraftSeatCapacity;
	private String aircraftTakeOffWeight;
	private String isWideOrNarrow;
	private String passengerBridgeNumber;
	private String remark;
	private String passengerInternal;
	private String babyInternal;
	private String childInternal;
	private String airCargoWeightInternal;
	private String luggageWeightInternal;
	private String airMailWeightInternal;
	private String luggageNumInternal;
	private String passengerInternational;
	private String babyInternational;
	private String childInternational;
	private String airCargoWeightInternational;
	private String luggageWeightInternational;
	private String airMailWeightInternational;
	private String luggageNumInternational;
	private String viaAdult;
	private String viaBaby;
	private String viaChild;
	private String luggageWeightVia;
	private String airMailWeightVia;
	private String luggageNumVia;
	private String airCargoWeightVia;
	private String leadCarUsedCount;
	private String isHighDensity;
	private String airTruckTime;
	private String electricTruckTime;
	private String tractorsUsedCount;
	private String airconditioningTime;
	private Double passengerloadFactor;
	private String deicingTruckUsedTime;
	private String psssengerCarUsedTime;
	private String airdromeUsedByPasCount;
	private String airdromeUsedByCrewCount;
	private String liftingPlatformCarTime;
	private String unroutineCheckTime;
	private String routineCheckTime;
	private String speBoardCarUsedCount;
	private String garbageTruckUseCount;
	private String cesspoolageTruckUseCount;
	private String waterTruckUseCount;
	private String bridgeElectricUsedTime;
	private String bridgeAirconditionUsedTime;
	private String passengerBridgeTime;
	private String vipCount;
	private String firstClassCount;
	private String vipFirstAirdromeUsedCount;
	private String updateRole;
	private String stat;
	private String flightCountryType;
	private String flightPart0Type;

	private String abnormalFlag;

	private String airlineIataCode;

	/**
	 * 用作告警信息判断
	 *  缺少机位， 远机位使用廊桥， 近机位使用摆渡车
	 */
	private String alarmMessage;

	public FlightMateDetailBean() {
	}

	public FlightMateDetailBean(Long id) {
		this.id = id;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlightDirection() {
		if (("0").equals(flightDirection)) {
			return "进港";
		} else if (("1").equals(flightDirection)) {
			return "离港";
		}
		return flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}

	public String getAirLineName() {
		return airLineName;
	}

	public void setAirLineName(String airLineName) {
		this.airLineName = airLineName;
	}

	public String getRegisteration() {
		return registeration;
	}

	public void setRegisteration(String registeration) {
		this.registeration = registeration;
	}

	public String getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getFlightIdentity() {
		return flightIdentity;
	}

	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}

	public String getScheduledLandedTime() {
		return scheduledLandedTime;
	}

	public void setScheduledLandedTime(String scheduledLandedTime) {
		this.scheduledLandedTime = scheduledLandedTime;
	}

	public String getLandedTime() {
		return landedTime;
	}

	public void setLandedTime(String landedTime) {
		this.landedTime = landedTime;
	}

	public String getScheduledTakeOffTime() {
		return scheduledTakeOffTime;
	}

	public void setScheduledTakeOffTime(String scheduledTakeOffTime) {
		this.scheduledTakeOffTime = scheduledTakeOffTime;
	}

	public String getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(String takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	public String getIataOriginAirport() {
		return iataOriginAirport;
	}

	public void setIataOriginAirport(String iataOriginAirport) {
		this.iataOriginAirport = iataOriginAirport;
	}

	public String getIataDestinationAirport() {
		return iataDestinationAirport;
	}

	public void setIataDestinationAirport(String iataDestinationAirport) {
		this.iataDestinationAirport = iataDestinationAirport;
	}

	public String getStandNum() {
		return standNum;
	}

	public void setStandNum(String standNum) {
		this.standNum = standNum;
	}

	public String getStandFlag() {
		if (("0").equals(standFlag)) {
			return "远机位";
		} else if (("1").equals(standFlag)) {
			return "近机位";
		}
		return standFlag;
	}

	public void setStandFlag(String standFlag) {
		this.standFlag = standFlag;
	}

	public String getFlightProperty() {
		return flightProperty;
	}

	public void setFlightProperty(String flightProperty) {
		this.flightProperty = flightProperty;
	}

	public String getLandTime() {
		return landTime;
	}

	public void setLandTime(String landTime) {
		this.landTime = landTime;
	}

	public String getFlightRoute() {
		return flightRoute;
	}

	public void setFlightRoute(String flightRoute) {
		this.flightRoute = flightRoute;
	}

	public String getMatchMethod() {
		return matchMethod;
	}

	public void setMatchMethod(String matchMethod) {
		this.matchMethod = matchMethod;
	}

	public String getDelayCode() {
		return delayCode;
	}

	public void setDelayCode(String delayCode) {
		this.delayCode = delayCode;
	}

	public String getFlightPart0() {
		return flightPart0;
	}

	public void setFlightPart0(String flightPart0) {
		this.flightPart0 = flightPart0;
	}

	public String getFlightPart1() {
		return flightPart1;
	}

	public void setFlightPart1(String flightPart1) {
		this.flightPart1 = flightPart1;
	}

	public String getFlightPart2() {
		return flightPart2;
	}

	public void setFlightPart2(String flightPart2) {
		this.flightPart2 = flightPart2;
	}

	public String getIsNightFlight() {
		if (("1").equals(isNightFlight)) {
			return "是";
		} else if (("0").equals(isNightFlight)) {
			return "否";
		}
		return isNightFlight;
	}

	public void setIsNightFlight(String isNightFlight) {
		this.isNightFlight = isNightFlight;
	}

	public String getIsPeakFlight() {
		if (("0").equals(isPeakFlight)) {
			return "是";
		} else if (("1").equals(isPeakFlight)) {
			return "否";
		}
		return isPeakFlight;
	}

	public void setIsPeakFlight(String isPeakFlight) {
		this.isPeakFlight = isPeakFlight;
	}

	public String getAircraftService() {
		if (("1").equals(aircraftService)) {
			return "航前";
		} else if (("0").equals(aircraftService)) {
			return "航后";
		} else if ("2".equals(aircraftService)) {
			return "过站";
		}else{
			return "";
		}
	}

	public void setAircraftService(String aircraftService) {
		this.aircraftService = aircraftService;
	}

	public String getSpecialPlane() {
		return specialPlane;
	}

	public void setSpecialPlane(String specialPlane) {
		this.specialPlane = specialPlane;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public String getAircraftPayload() {
		return aircraftPayload;
	}

	public void setAircraftPayload(String aircraftPayload) {
		this.aircraftPayload = aircraftPayload;
	}

	public String getAircraftSeatCapacity() {
		return aircraftSeatCapacity;
	}

	public void setAircraftSeatCapacity(String aircraftSeatCapacity) {
		this.aircraftSeatCapacity = aircraftSeatCapacity;
	}

	public String getAircraftTakeOffWeight() {
		return aircraftTakeOffWeight;
	}

	public void setAircraftTakeOffWeight(String aircraftTakeOffWeight) {
		this.aircraftTakeOffWeight = aircraftTakeOffWeight;
	}

	public String getIsWideOrNarrow() {
		//宽窄体
		if("1".equals(isWideOrNarrow)){
			return "窄";
		}else if("0".equals(isWideOrNarrow)){
			return "宽";
		}else{
			return isWideOrNarrow;
		}
	}

	public void setIsWideOrNarrow(String isWideOrNarrow) {
		this.isWideOrNarrow = isWideOrNarrow;
	}

	public String getPassengerBridgeNumber() {
		return passengerBridgeNumber;
	}

	public void setPassengerBridgeNumber(String passengerBridgeNumber) {
		this.passengerBridgeNumber = passengerBridgeNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPassengerInternal() {
		return passengerInternal;
	}

	public void setPassengerInternal(String passengerInternal) {
		this.passengerInternal = passengerInternal;
	}

	public String getBabyInternal() {
		return babyInternal;
	}

	public void setBabyInternal(String babyInternal) {
		this.babyInternal = babyInternal;
	}

	public String getChildInternal() {
		return childInternal;
	}

	public void setChildInternal(String childInternal) {
		this.childInternal = childInternal;
	}

	public String getAirCargoWeightInternal() {
		return airCargoWeightInternal;
	}

	public void setAirCargoWeightInternal(String airCargoWeightInternal) {
		this.airCargoWeightInternal = airCargoWeightInternal;
	}

	public String getLuggageWeightInternal() {
		return luggageWeightInternal;
	}

	public void setLuggageWeightInternal(String luggageWeightInternal) {
		this.luggageWeightInternal = luggageWeightInternal;
	}

	public String getAirMailWeightInternal() {
		return airMailWeightInternal;
	}

	public void setAirMailWeightInternal(String airMailWeightInternal) {
		this.airMailWeightInternal = airMailWeightInternal;
	}

	public String getPassengerInternational() {
		return passengerInternational;
	}

	public void setPassengerInternational(String passengerInternational) {
		this.passengerInternational = passengerInternational;
	}

	public String getBabyInternational() {
		return babyInternational;
	}

	public void setBabyInternational(String babyInternational) {
		this.babyInternational = babyInternational;
	}

	public String getChildInternational() {
		return childInternational;
	}

	public void setChildInternational(String childInternational) {
		this.childInternational = childInternational;
	}

	public String getAirCargoWeightInternational() {
		return airCargoWeightInternational;
	}

	public void setAirCargoWeightInternational(
			String airCargoWeightInternational) {
		this.airCargoWeightInternational = airCargoWeightInternational;
	}

	public String getLuggageWeightInternational() {
		return luggageWeightInternational;
	}

	public void setLuggageWeightInternational(String luggageWeightInternational) {
		this.luggageWeightInternational = luggageWeightInternational;
	}

	public String getAirMailWeightInternational() {
		return airMailWeightInternational;
	}

	public void setAirMailWeightInternational(String airMailWeightInternational) {
		this.airMailWeightInternational = airMailWeightInternational;
	}

	public String getViaAdult() {
		return viaAdult;
	}

	public void setViaAdult(String viaAdult) {
		this.viaAdult = viaAdult;
	}

	public String getViaBaby() {
		return viaBaby;
	}

	public void setViaBaby(String viaBaby) {
		this.viaBaby = viaBaby;
	}

	public String getViaChild() {
		return viaChild;
	}

	public void setViaChild(String viaChild) {
		this.viaChild = viaChild;
	}

	public String getLeadCarUsedCount() {
		return leadCarUsedCount;
	}

	public void setLeadCarUsedCount(String leadCarUsedCount) {
		this.leadCarUsedCount = leadCarUsedCount;
	}

	public String getAirTruckTime() {
		return airTruckTime;
	}

	public void setAirTruckTime(String airTruckTime) {
		this.airTruckTime = airTruckTime;
	}

	public String getElectricTruckTime() {
		return electricTruckTime;
	}

	public void setElectricTruckTime(String electricTruckTime) {
		this.electricTruckTime = electricTruckTime;
	}

	public String getTractorsUsedCount() {
		return tractorsUsedCount;
	}

	public void setTractorsUsedCount(String tractorsUsedCount) {
		this.tractorsUsedCount = tractorsUsedCount;
	}

	public String getAirconditioningTime() {
		return airconditioningTime;
	}

	public void setAirconditioningTime(String airconditioningTime) {
		this.airconditioningTime = airconditioningTime;
	}

	public String getDeicingTruckUsedTime() {
		return deicingTruckUsedTime;
	}

	public void setDeicingTruckUsedTime(String deicingTruckUsedTime) {
		this.deicingTruckUsedTime = deicingTruckUsedTime;
	}

	public String getPsssengerCarUsedTime() {
		return psssengerCarUsedTime;
	}

	public void setPsssengerCarUsedTime(String psssengerCarUsedTime) {
		this.psssengerCarUsedTime = psssengerCarUsedTime;
	}

	public String getAirdromeUsedByPasCount() {
		return airdromeUsedByPasCount;
	}

	public void setAirdromeUsedByPasCount(String airdromeUsedByPasCount) {
		this.airdromeUsedByPasCount = airdromeUsedByPasCount;
	}

	public String getAirdromeUsedByCrewCount() {
		return airdromeUsedByCrewCount;
	}

	public void setAirdromeUsedByCrewCount(String airdromeUsedByCrewCount) {
		this.airdromeUsedByCrewCount = airdromeUsedByCrewCount;
	}

	public String getLiftingPlatformCarTime() {
		return liftingPlatformCarTime;
	}

	public void setLiftingPlatformCarTime(String liftingPlatformCarTime) {
		this.liftingPlatformCarTime = liftingPlatformCarTime;
	}

	public String getUnroutineCheckTime() {
		return unroutineCheckTime;
	}

	public void setUnroutineCheckTime(String unroutineCheckTime) {
		this.unroutineCheckTime = unroutineCheckTime;
	}

	public String getRoutineCheckTime() {
		return routineCheckTime;
	}

	public void setRoutineCheckTime(String routineCheckTime) {
		this.routineCheckTime = routineCheckTime;
	}

	public String getSpeBoardCarUsedCount() {
		return speBoardCarUsedCount;
	}

	public void setSpeBoardCarUsedCount(String speBoardCarUsedCount) {
		this.speBoardCarUsedCount = speBoardCarUsedCount;
	}

	public String getGarbageTruckUseCount() {
		return garbageTruckUseCount;
	}

	public void setGarbageTruckUseCount(String garbageTruckUseCount) {
		this.garbageTruckUseCount = garbageTruckUseCount;
	}

	public String getCesspoolageTruckUseCount() {
		return cesspoolageTruckUseCount;
	}

	public void setCesspoolageTruckUseCount(String cesspoolageTruckUseCount) {
		this.cesspoolageTruckUseCount = cesspoolageTruckUseCount;
	}

	public String getWaterTruckUseCount() {
		return waterTruckUseCount;
	}

	public void setWaterTruckUseCount(String waterTruckUseCount) {
		this.waterTruckUseCount = waterTruckUseCount;
	}

	public String getBridgeElectricUsedTime() {
		return bridgeElectricUsedTime;
	}

	public void setBridgeElectricUsedTime(String bridgeElectricUsedTime) {
		this.bridgeElectricUsedTime = bridgeElectricUsedTime;
	}

	public String getBridgeAirconditionUsedTime() {
		return bridgeAirconditionUsedTime;
	}

	public void setBridgeAirconditionUsedTime(String bridgeAirconditionUsedTime) {
		this.bridgeAirconditionUsedTime = bridgeAirconditionUsedTime;
	}

	public String getPassengerBridgeTime() {
		return passengerBridgeTime;
	}

	public void setPassengerBridgeTime(String passengerBridgeTime) {
		this.passengerBridgeTime = passengerBridgeTime;
	}

	public String getVipCount() {
		return vipCount;
	}

	public void setVipCount(String vipCount) {
		this.vipCount = vipCount;
	}

	public String getFirstClassCount() {
		return firstClassCount;
	}

	public void setFirstClassCount(String firstClassCount) {
		this.firstClassCount = firstClassCount;
	}

	public String getVipFirstAirdromeUsedCount() {
		return vipFirstAirdromeUsedCount;
	}

	public void setVipFirstAirdromeUsedCount(String vipFirstAirdromeUsedCount) {
		this.vipFirstAirdromeUsedCount = vipFirstAirdromeUsedCount;
	}

	public String getAlarmMessage() {
		alarmMessage = "";
		if( ! Chk.spaceCheck(this.standNum) || "HOLD".equalsIgnoreCase(this.standNum)){
			alarmMessage += "航班信息中缺少机位<br/>";
		}

		if( ! Chk.spaceCheck(this.registeration)){
			alarmMessage += "航班信息中缺少机号（ 航班取消）<br/>";
		}

		if( ! Chk.spaceCheck(this.airLineName)){
			alarmMessage += "航班信息中缺少航空公司<br/>";
		}

		if("0".equals(this.flightDirection)){
			if(! Chk.spaceCheck(this.landedTime)){
				alarmMessage += "航班信息中缺少实际降落时间<br/>";
			}

			if(Chk.spaceCheck(this.aFlightIdentity) && Chk.spaceCheck(this.airlineIataCode)){
				if( ! this.airlineIataCode.equals(this.aFlightIdentity.substring(0, 2))){
					alarmMessage += "航班信息中航空公司二字码与航班号不符<br/>";
				}
			}

		}else if("1".equals(this.flightDirection)){
			if(! Chk.spaceCheck(this.takeOffTime)){
				alarmMessage += "航班信息中缺少实际起飞时间<br/>";
			}

			if(Double.parseDouble(StringUtil.nullOrEmptyTo(vipCount,"0")) > Double.parseDouble(StringUtil.nullOrEmptyTo(firstClassCount,"0"))){
				alarmMessage += "VIP人数大于头等舱人数<br/>";
			}

			if(Chk.spaceCheck(this.dFlightIdentity) && Chk.spaceCheck(this.airlineIataCode)){
				if( ! this.airlineIataCode.equals(this.dFlightIdentity.substring(0, 2))){
					alarmMessage += "航班信息中航空公司二字码与航班号不符<br/>";
				}
			}

		}else{
			alarmMessage += "非法的航班方向<br/>";
		}


		if("0".equals(this.standFlag)){
//            if( ( this.airdromeUsedByCrewCount != null && ! "0".equals(this.airdromeUsedByCrewCount) )
			if( ( this.psssengerCarUsedTime != null && ! "0".equals(this.psssengerCarUsedTime) )
					|| ( this.airdromeUsedByPasCount != null && ! "0".equals(this.airdromeUsedByPasCount) )   ){
				alarmMessage += "客梯车或旅客摆渡车与近机位冲突<br/>";
			}
		}else if("1".equals(this.standFlag)){
			if( ( this.bridgeElectricUsedTime != null && ! "0".equals(this.bridgeElectricUsedTime))
					|| ( this.bridgeAirconditionUsedTime != null && ! "0".equals(this.bridgeAirconditionUsedTime))
					|| ( this.passengerBridgeTime != null && ! "0".equals(this.passengerBridgeTime)) ){
				alarmMessage += "远机位航班使用了廊桥<br/>";
			}
		}


		return alarmMessage;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public String getUpdateRole() {
		return updateRole;
	}

	public void setUpdateRole(String updateRole) {
		this.updateRole = updateRole;
	}

	public String getLuggageNumInternal() {
		return luggageNumInternal;
	}

	public void setLuggageNumInternal(String luggageNumInternal) {
		this.luggageNumInternal = luggageNumInternal;
	}

	public String getLuggageNumInternational() {
		return luggageNumInternational;
	}

	public void setLuggageNumInternational(String luggageNumInternational) {
		this.luggageNumInternational = luggageNumInternational;
	}

	public String getLuggageWeightVia() {
		return luggageWeightVia;
	}

	public void setLuggageWeightVia(String luggageWeightVia) {
		this.luggageWeightVia = luggageWeightVia;
	}

	public String getAirMailWeightVia() {
		return airMailWeightVia;
	}

	public void setAirMailWeightVia(String airMailWeightVia) {
		this.airMailWeightVia = airMailWeightVia;
	}

	public String getLuggageNumVia() {
		return luggageNumVia;
	}

	public void setLuggageNumVia(String luggageNumVia) {
		this.luggageNumVia = luggageNumVia;
	}

	public String getAirCargoWeightVia() {
		return airCargoWeightVia;
	}

	public void setAirCargoWeightVia(String airCargoWeightVia) {
		this.airCargoWeightVia = airCargoWeightVia;
	}

	public String getIsHighDensity() {
		if("0".equals(isHighDensity)){
			return "N";
		}else if("1".equals(isHighDensity)){
			return "Y";
		}
		return isHighDensity;
	}

	public void setIsHighDensity(String isHighDensity) {
		this.isHighDensity = isHighDensity;
	}

	public Double getPassengerloadFactor() {
		return passengerloadFactor;
	}

	public void setPassengerloadFactor(Double passengerloadFactor) {
		this.passengerloadFactor = passengerloadFactor;
	}

	public String getAirlineHandler() {
		return airlineHandler;
	}

	public void setAirlineHandler(String airlineHandler) {
		this.airlineHandler = airlineHandler;
	}

	public String getaFlightIdentity() {
		return aFlightIdentity;
	}

	public void setaFlightIdentity(String aFlightIdentity) {
		this.aFlightIdentity = aFlightIdentity;
	}

	public String getdFlightIdentity() {
		return dFlightIdentity;
	}

	public void setdFlightIdentity(String dFlightIdentity) {
		this.dFlightIdentity = dFlightIdentity;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getFlightBaseId() {
		return flightBaseId;
	}

	public void setFlightBaseId(String flightBaseId) {
		this.flightBaseId = flightBaseId;
	}

	public String getFlightCountryType() {
		if("0".equals(flightCountryType)){
			return "DOM";
		}else if("1".equals(flightCountryType)){
			return "INT";
		}else if("2".equals(flightCountryType)){
			return "REG";
		}else if("3".equals(flightCountryType)){
			return "MIX";
		}else{
			return flightCountryType;
		}
	}

	public void setFlightCountryType(String flightCountryType) {
		this.flightCountryType = flightCountryType;
	}

	public String getAbnormalFlag() {
		if("0".equals(abnormalFlag)){
			return "";
		}else if("1".equals(abnormalFlag)){
			return " (备降)";
		}else if("2".equals(abnormalFlag)){
			return " (返航)";
		}else if("3".equals(abnormalFlag)){
			return " (取消)";
		}else{
			return abnormalFlag;
		}
	}

	public void setAbnormalFlag(String abnormalFlag) {
		this.abnormalFlag = abnormalFlag;
	}

	public String getFlightPart0Type() {
		if("0".equals(flightPart0Type)){
			return "DOM";
		}else if("1".equals(flightPart0Type)){
			return "INT";}
		else{
			return flightPart0Type;
		}
	}

	public void setFlightPart0Type(String flightPart0Type) {
		this.flightPart0Type = flightPart0Type;
	}

	public String getAirlineIataCode() {
		return airlineIataCode;
	}

	public void setAirlineIataCode(String airlineIataCode) {
		this.airlineIataCode = airlineIataCode;
	}
}
