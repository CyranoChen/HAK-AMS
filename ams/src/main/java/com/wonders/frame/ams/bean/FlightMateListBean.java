package com.wonders.frame.ams.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.DateUtil;
import com.wonders.frame.ams.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by 3701 on 2015/12/11.
 */
public class FlightMateListBean {
    private Long id;
    private String flightDirection; //航班方向
    private String airLineName; //航空公司
    private String airlineHandler; //代理
    private String registeration; //机号
    private String scheduledTime; //计划执行日
    private String aFlightIdentity; //进港航班号
    private String scheduledLandedTime; //计划到达时间
    private String landedTime; //实际到达时间
    private String dFlightIdentity; //离港航班号
    private String scheduledTakeOffTime; //计划起飞时间
    private String takeOffTime; //实际起飞时间
    private String aircraftService; //0 航前 1 航后 2过站
    private String iataOriginAirport; //始发
    private String iataDestinationAirport; //终点
    private String standNum; //机位
    private String standFlag; //远近标识
    private String stat;

    private String icaoCode;
    private String airlineIataCode;
    private String aircraftType;

    /**
     * 摆渡车 用于告警变色计算
     */
    private String airdromeUsedByPasCount;
    private String airdromeUsedByCrewCount;
    private String psssengerCarUsedTime;
    /**
     * 廊桥  用于告警变色计算
     */
    private String bridgeElectricUsedTime;
    private String bridgeAirconditionUsedTime;
    private String passengerBridgeTime;

    private String vipCount;
    private String firstClassCount;


    private String abnormalFlag;

    private String alarmMessage;


    @JsonIgnore
    private HashSet<String> alarmMessageCode = new HashSet<String>();

    private String updateRole;


    private String isError;







    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightDirection() {
        if("0".equals(flightDirection)){
            return "A";
        }else if("1".equals(flightDirection)){
            return "D";
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

    public String getaFlightIdentity() {
        if("0".equals(aircraftService) || "2".equals(aircraftService)){
            return aFlightIdentity;
        }
        return "";
    }

    public void setaFlightIdentity(String aFlightIdentity) {
        this.aFlightIdentity = aFlightIdentity;
    }

    public String getScheduledLandedTime() {
        if("0".equals(aircraftService) || "2".equals(aircraftService)){
            return getFormatTime(this.scheduledLandedTime);
        }
        return "";
    }

    public void setScheduledLandedTime(String scheduledLandedTime) {
        this.scheduledLandedTime = scheduledLandedTime;
    }

    public String getLandedTime() {
        if("0".equals(aircraftService) || "2".equals(aircraftService)){
            return getFormatTime(this.landedTime);
        }
        return "";
    }

    public void setLandedTime(String landedTime) {
        this.landedTime = landedTime;
    }

    public String getdFlightIdentity() {
        if("1".equals(aircraftService) || "2".equals(aircraftService)){
            return dFlightIdentity;
        }
        return "";
    }

    public void setdFlightIdentity(String dFlightIdentity) {
        this.dFlightIdentity = dFlightIdentity;
    }

    public String getScheduledTakeOffTime() {
        if("1".equals(aircraftService) || "2".equals(aircraftService)){
            return getFormatTime(this.scheduledTakeOffTime);
        }
        return "";

    }

    public void setScheduledTakeOffTime(String scheduledTakeOffTime) {
        this.scheduledTakeOffTime = scheduledTakeOffTime;
    }

    public String getTakeOffTime() {
        if("1".equals(aircraftService) || "2".equals(aircraftService)){
            return getFormatTime(this.takeOffTime);
        }
        return "";
    }

    public void setTakeOffTime(String takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public String getIataOriginAirport() {
        if("1".equals(this.flightDirection)){
            return "HAK";
        }
        return iataOriginAirport;
    }

    public void setIataOriginAirport(String iataOriginAirport) {
        this.iataOriginAirport = iataOriginAirport;
    }

    public String getIataDestinationAirport() {
        if("0".equals(this.flightDirection)){
            return "HAK";
        }
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
        if("0".equals(standFlag)){
            return "近";
        }else if("1".equals(standFlag)){
            return "远";
        }
        return standFlag;
    }

    public void setStandFlag(String standFlag) {
        this.standFlag = standFlag;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }



    private String getFormatTime(String time){
        if(Chk.spaceCheck(time)){
            String [] t = time.split(" ");
            if(Chk.spaceCheck(this.scheduledTime)){
                int d = DateUtil.getIntervalDay(t[0], this.scheduledTime, "yyyy/MM/dd");
                return t[1] + ( d == 0 ? "" : ("(" + (d > 0 ? "+" : "" ) + d + ")") );
            }else{
                return t[1];
            }

        }else{
            return "";
        }
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
//
//
//    a.	不管航前、航后、过站航班一视同仁，均需进行5个约束检查，分别为机号、航空公司、实际进离港、远机位廊桥、近机位摆渡车。
//    b.	关于实际进离港的判断，进港只判着陆时间，离港只判起飞时间。所有航班均应用此规则。

    public String getAlarmMessage() {
        alarmMessage = "";
        if( ! Chk.spaceCheck(this.standNum) || "HOLD".equalsIgnoreCase(this.standNum)){
            alarmMessageCode.add("0");
            alarmMessage += "航班信息中缺少机位<br/>";
        }

        if( ! Chk.spaceCheck(this.registeration)){
            alarmMessageCode.add("1");
            alarmMessage += "航班信息中缺少机号（ 航班取消）<br/>";
        }

        if( ! Chk.spaceCheck(this.airLineName)){
            alarmMessageCode.add("2");
            alarmMessage += "航班信息中缺少航空公司<br/>";
        }


        if("0".equals(this.flightDirection)){
            if(! Chk.spaceCheck(this.landedTime)){
                alarmMessageCode.add("3");
                alarmMessage += "航班信息中缺少实际降落时间<br/>";
            }

            if(Chk.spaceCheck(this.aFlightIdentity) && Chk.spaceCheck(this.airlineIataCode)){
                if( ! this.airlineIataCode.equals(this.aFlightIdentity.substring(0, 2))){
                    alarmMessageCode.add("4");
                    alarmMessage += "航班信息中航空公司二字码与航班号不符<br/>";
                }
            }

        }else if("1".equals(this.flightDirection)){
            if(! Chk.spaceCheck(this.takeOffTime)){
                alarmMessageCode.add("5");
                alarmMessage += "航班信息中缺少实际起飞时间<br/>";
            }

            if(Double.parseDouble(StringUtil.nullOrEmptyTo(vipCount,"0")) > Double.parseDouble(StringUtil.nullOrEmptyTo(firstClassCount,"0"))){
                alarmMessageCode.add("6");
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
                    alarmMessageCode.add("7");
                    alarmMessage += "客梯车或旅客摆渡车与近机位冲突<br/>";
                }
            }else if("1".equals(this.standFlag)){
                if( ( this.bridgeElectricUsedTime != null && ! "0".equals(this.bridgeElectricUsedTime))
                        || ( this.bridgeAirconditionUsedTime != null && ! "0".equals(this.bridgeAirconditionUsedTime))
                        || ( this.passengerBridgeTime != null && ! "0".equals(this.passengerBridgeTime)) ){
                    alarmMessageCode.add("8");
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

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }


    public String getIsError() {
        return Chk.spaceCheck(alarmMessage) ? "是" : "否";
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public String getAircraftService() {
        return aircraftService;
    }

    public void setAircraftService(String aircraftService) {
        this.aircraftService = aircraftService;
    }

	public String getAirlineHandler() {
		return airlineHandler;
	}

	public void setAirlineHandler(String airlineHandler) {
		this.airlineHandler = airlineHandler;
	}


    public String getAbnormalFlag() {
        if("0".equals(abnormalFlag)){
            return "";
        }else if("1".equals(abnormalFlag)){
            return "备降";
        }else if("2".equals(abnormalFlag)){
            return "返航";
        }else if("3".equals(abnormalFlag)){
            return "取消";
        }else{
            return abnormalFlag;
        }
    }

    public void setAbnormalFlag(String abnormalFlag) {
        this.abnormalFlag = abnormalFlag;
    }

    public String getPsssengerCarUsedTime() {
        return psssengerCarUsedTime;
    }

    public void setPsssengerCarUsedTime(String psssengerCarUsedTime) {
        this.psssengerCarUsedTime = psssengerCarUsedTime;
    }

    public String getAirlineIataCode() {
        return airlineIataCode;
    }

    public void setAirlineIataCode(String airlineIataCode) {
        this.airlineIataCode = airlineIataCode;
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

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public HashSet<String> getAlarmMessageCode() {
        return alarmMessageCode;
    }

    public void setAlarmMessageCode(HashSet<String> alarmMessageCode) {
        this.alarmMessageCode = alarmMessageCode;
    }
}
