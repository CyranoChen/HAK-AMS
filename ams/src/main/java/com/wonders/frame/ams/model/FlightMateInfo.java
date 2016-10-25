package com.wonders.frame.ams.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 3701 on 2015/12/11.
 */
@Entity
@Table(name = "FLIGHT_MATE_INFO")
public class FlightMateInfo implements ILongIdRemoveFlagModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String aRegisteration;

    @Basic
    @Column(name = "A_REGISTERATION")
    public String getaRegisteration() {
        return aRegisteration;
    }

    public void setaRegisteration(String aRegisteration) {
        this.aRegisteration = aRegisteration;
    }

    private Long flightServiceInfoId;

    @Basic
    @Column(name = "FLIGHT_SERVICE_INFO_ID")
    public Long getFlightServiceInfoId() {
        return flightServiceInfoId;
    }

    public void setFlightServiceInfoId(Long flightServiceInfoId) {
        this.flightServiceInfoId = flightServiceInfoId;
    }

    private Long flightBaseId;

    @Basic
    @Column(name = "FLIGHT_BASE_ID")
    public Long getFlightBaseId() {
        return flightBaseId;
    }

    public void setFlightBaseId(Long flightBaseId) {
        this.flightBaseId = flightBaseId;
    }

    private Long aircraftId;

    @Basic
    @Column(name = "AIRCRAFT_ID")
    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
    }

    private String matchMethod;

    @Basic
    @Column(name = "MATCH_METHOD")
    public String getMatchMethod() {
        return matchMethod;
    }

    public void setMatchMethod(String matchMethod) {
        this.matchMethod = matchMethod;
    }

    private Timestamp createTime;

    @Basic
    @Column(name = "CREATE_TIME")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    private String createUser;

    @Basic
    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    private Timestamp modifyTime;

    @Basic
    @Column(name = "MODIFY_TIME")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    private String modifyUser;

    @Basic
    @Column(name = "MODIFY_USER")
    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    private String remark;

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String removeFlag;

    @Basic
    @Column(name = "REMOVE_FLAG")
    public String getRemoveFlag() {
        return removeFlag;
    }

    public void setRemoveFlag(String removeFlag) {
        this.removeFlag = removeFlag;
    }

    private String landTime;

    @Basic
    @Column(name = "LAND_TIME")
    public String getLandTime() {
        return landTime;
    }

    public void setLandTime(String landTime) {
        this.landTime = landTime;
    }

    private Timestamp landedTime;

    @Basic
    @Column(name = "LANDED_TIME")
    public Timestamp getLandedTime() {
        return landedTime;
    }

    public void setLandedTime(Timestamp landedTime) {
        this.landedTime = landedTime;
    }

    private Timestamp takeOffTime;

    @Basic
    @Column(name = "TAKE_OFF_TIME")
    public Timestamp getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(Timestamp takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    private Long aircraftTakeOffWeight;

    @Basic
    @Column(name = "AIRCRAFT_TAKE_OFF_WEIGHT")
    public Long getAircraftTakeOffWeight() {
        return aircraftTakeOffWeight;
    }

    public void setAircraftTakeOffWeight(Long aircraftTakeOffWeight) {
        this.aircraftTakeOffWeight = aircraftTakeOffWeight;
    }

    private String leadCarUsedCount;

    @Basic
    @Column(name = "LEAD_CAR_USED_COUNT")
    public String getLeadCarUsedCount() {
        return leadCarUsedCount;
    }

    public void setLeadCarUsedCount(String leadCarUsedCount) {
        this.leadCarUsedCount = leadCarUsedCount;
    }

    private String passengerBridgeNumber;

    @Basic
    @Column(name = "PASSENGER_BRIDGE_NUMBER")
    public String getPassengerBridgeNumber() {
        return passengerBridgeNumber;
    }

    public void setPassengerBridgeNumber(String passengerBridgeNumber) {
        this.passengerBridgeNumber = passengerBridgeNumber;
    }

    private String tractorsUsedCount;

    @Basic
    @Column(name = "TRACTORS_USED_COUNT")
    public String getTractorsUsedCount() {
        return tractorsUsedCount;
    }

    public void setTractorsUsedCount(String tractorsUsedCount) {
        this.tractorsUsedCount = tractorsUsedCount;
    }

    private Long airTruckTime;

    @Basic
    @Column(name = "AIR_TRUCK_TIME")
    public Long getAirTruckTime() {
        return airTruckTime;
    }

    public void setAirTruckTime(Long airTruckTime) {
        this.airTruckTime = airTruckTime;
    }

    private Long airconditioningTime;

    @Basic
    @Column(name = "AIRCONDITIONING_TIME")
    public Long getAirconditioningTime() {
        return airconditioningTime;
    }

    public void setAirconditioningTime(Long airconditioningTime) {
        this.airconditioningTime = airconditioningTime;
    }

    private String airdromeUsedByCrewCount;

    @Basic
    @Column(name = "AIRDROME_USED_BY_CREW_COUNT")
    public String getAirdromeUsedByCrewCount() {
        return airdromeUsedByCrewCount;
    }

    public void setAirdromeUsedByCrewCount(String airdromeUsedByCrewCount) {
        this.airdromeUsedByCrewCount = airdromeUsedByCrewCount;
    }

    private Long deicingVehicleTime;

    @Basic
    @Column(name = "DEICING_VEHICLE_TIME")
    public Long getDeicingVehicleTime() {
        return deicingVehicleTime;
    }

    public void setDeicingVehicleTime(Long deicingVehicleTime) {
        this.deicingVehicleTime = deicingVehicleTime;
    }

    private Long electricTruckTime;

    @Basic
    @Column(name = "ELECTRIC_TRUCK_TIME")
    public Long getElectricTruckTime() {
        return electricTruckTime;
    }

    public void setElectricTruckTime(Long electricTruckTime) {
        this.electricTruckTime = electricTruckTime;
    }

    private Long liftingPlatformCarTime;

    @Basic
    @Column(name = "LIFTING_PLATFORM_CAR_TIME")
    public Long getLiftingPlatformCarTime() {
        return liftingPlatformCarTime;
    }

    public void setLiftingPlatformCarTime(Long liftingPlatformCarTime) {
        this.liftingPlatformCarTime = liftingPlatformCarTime;
    }

    private Long passengerBridgeTime;

    @Basic
    @Column(name = "PASSENGER_BRIDGE_TIME")
    public Long getPassengerBridgeTime() {
        return passengerBridgeTime;
    }

    public void setPassengerBridgeTime(Long passengerBridgeTime) {
        this.passengerBridgeTime = passengerBridgeTime;
    }

    private String flightCountryType;

    @Basic
    @Column(name = "FLIGHT_COUNTRY_TYPE")
    public String getFlightCountryType() {
        return flightCountryType;
    }

    public void setFlightCountryType(String flightCountryType) {
        this.flightCountryType = flightCountryType;
    }

    private String delayCode;

    @Basic
    @Column(name = "DELAY_CODE")
    public String getDelayCode() {
        return delayCode;
    }

    public void setDelayCode(String delayCode) {
        this.delayCode = delayCode;
    }

    private String isNightFlight;

    @Basic
    @Column(name = "IS_NIGHT_FLIGHT")
    public String getIsNightFlight() {
        return isNightFlight;
    }

    public void setIsNightFlight(String isNightFlight) {
        this.isNightFlight = isNightFlight;
    }

    private String isPeakFlight;

    @Basic
    @Column(name = "IS_PEAK_FLIGHT")
    public String getIsPeakFlight() {
        return isPeakFlight;
    }

    public void setIsPeakFlight(String isPeakFlight) {
        this.isPeakFlight = isPeakFlight;
    }

    private String isVia;

    @Basic
    @Column(name = "IS_VIA")
    public String getIsVia() {
        return isVia;
    }

    public void setIsVia(String isVia) {
        this.isVia = isVia;
    }

    private String specialPlane;

    @Basic
    @Column(name = "SPECIAL_PLANE")
    public String getSpecialPlane() {
        return specialPlane;
    }

    public void setSpecialPlane(String specialPlane) {
        this.specialPlane = specialPlane;
    }

    private String aFlightIdentity;

    @Basic
    @Column(name = "A_FLIGHT_IDENTITY")
    public String getaFlightIdentity() {
        return aFlightIdentity;
    }

    public void setaFlightIdentity(String aFlightIdentity) {
        this.aFlightIdentity = aFlightIdentity;
    }

    private String dFlightIdentity;

    @Basic
    @Column(name = "D_FLIGHT_IDENTITY")
    public String getdFlightIdentity() {
        return dFlightIdentity;
    }

    public void setdFlightIdentity(String dFlightIdentity) {
        this.dFlightIdentity = dFlightIdentity;
    }

    private String aAircraftType;

    @Basic
    @Column(name = "A_AIRCRAFT_TYPE")
    public String getaAircraftType() {
        return aAircraftType;
    }

    public void setaAircraftType(String aAircraftType) {
        this.aAircraftType = aAircraftType;
    }

    private String dAircraftType;

    @Basic
    @Column(name = "D_AIRCRAFT_TYPE")
    public String getdAircraftType() {
        return dAircraftType;
    }

    public void setdAircraftType(String dAircraftType) {
        this.dAircraftType = dAircraftType;
    }

    private String aStandNum;

    @Basic
    @Column(name = "A_STAND_NUM")
    public String getaStandNum() {
        return aStandNum;
    }

    public void setaStandNum(String aStandNum) {
        this.aStandNum = aStandNum;
    }

    private String dStandNum;

    @Basic
    @Column(name = "D_STAND_NUM")
    public String getdStandNum() {
        return dStandNum;
    }

    public void setdStandNum(String dStandNum) {
        this.dStandNum = dStandNum;
    }

    private String flightDirection;

    @Basic
    @Column(name = "FLIGHT_DIRECTION")
    public String getFlightDirection() {
        return flightDirection;
    }

    public void setFlightDirection(String flightDirection) {
        this.flightDirection = flightDirection;
    }

    private Long linkFlightBaseId;

    @Basic
    @Column(name = "LINK_FLIGHT_BASE_ID")
    public Long getLinkFlightBaseId() {
        return linkFlightBaseId;
    }

    public void setLinkFlightBaseId(Long linkFlightBaseId) {
        this.linkFlightBaseId = linkFlightBaseId;
    }

    private Timestamp executeTime;

    @Basic
    @Column(name = "EXECUTE_TIME")
    public Timestamp getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Timestamp executeTime) {
        this.executeTime = executeTime;
    }

    private String airLineName;

    @Basic
    @Column(name = "AIR_LINE_NAME")
    public String getAirLineName() {
        return airLineName;
    }

    public void setAirLineName(String airLineName) {
        this.airLineName = airLineName;
    }

    private Long airLineId;

    @Basic
    @Column(name = "AIR_LINE_ID")
    public Long getAirLineId() {
        return airLineId;
    }

    public void setAirLineId(Long airLineId) {
        this.airLineId = airLineId;
    }

    private String isWideOrNarrow;

    @Basic
    @Column(name = "IS_WIDE_OR_NARROW")
    public String getIsWideOrNarrow() {
        return isWideOrNarrow;
    }

    public void setIsWideOrNarrow(String isWideOrNarrow) {
        this.isWideOrNarrow = isWideOrNarrow;
    }

    private Long passengerInternal;

    @Basic
    @Column(name = "PASSENGER_INTERNAL")
    public Long getPassengerInternal() {
        return passengerInternal;
    }

    public void setPassengerInternal(Long passengerInternal) {
        this.passengerInternal = passengerInternal;
    }

    private Long passengerInternational;

    @Basic
    @Column(name = "PASSENGER_INTERNATIONAL")
    public Long getPassengerInternational() {
        return passengerInternational;
    }

    public void setPassengerInternational(Long passengerInternational) {
        this.passengerInternational = passengerInternational;
    }

    private Integer airMailWeightInternal;

    @Basic
    @Column(name = "AIR_MAIL_WEIGHT_INTERNAL")
    public Integer getAirMailWeightInternal() {
        return airMailWeightInternal;
    }

    public void setAirMailWeightInternal(Integer airMailWeightInternal) {
        this.airMailWeightInternal = airMailWeightInternal;
    }

    private Long airMailWeightInternational;

    @Basic
    @Column(name = "AIR_MAIL_WEIGHT_INTERNATIONAL")
    public Long getAirMailWeightInternational() {
        return airMailWeightInternational;
    }

    public void setAirMailWeightInternational(Long airMailWeightInternational) {
        this.airMailWeightInternational = airMailWeightInternational;
    }

    private Long airCargoWeightInternal;

    @Basic
    @Column(name = "AIR_CARGO_WEIGHT_INTERNAL")
    public Long getAirCargoWeightInternal() {
        return airCargoWeightInternal;
    }

    public void setAirCargoWeightInternal(Long airCargoWeightInternal) {
        this.airCargoWeightInternal = airCargoWeightInternal;
    }

    private Long airCargoWeightInternational;

    @Basic
    @Column(name = "AIR_CARGO_WEIGHT_INTERNATIONAL")
    public Long getAirCargoWeightInternational() {
        return airCargoWeightInternational;
    }

    public void setAirCargoWeightInternational(Long airCargoWeightInternational) {
        this.airCargoWeightInternational = airCargoWeightInternational;
    }

    private String flightCountryTypeQuery;

    @Basic
    @Column(name = "FLIGHT_COUNTRY_TYPE_QUERY")
    public String getFlightCountryTypeQuery() {
        return flightCountryTypeQuery;
    }

    public void setFlightCountryTypeQuery(String flightCountryTypeQuery) {
        this.flightCountryTypeQuery = flightCountryTypeQuery;
    }

    private String flightCountryTypeCharge;

    @Basic
    @Column(name = "FLIGHT_COUNTRY_TYPE_CHARGE")
    public String getFlightCountryTypeCharge() {
        return flightCountryTypeCharge;
    }

    public void setFlightCountryTypeCharge(String flightCountryTypeCharge) {
        this.flightCountryTypeCharge = flightCountryTypeCharge;
    }

    private String flightVoyage;

    @Basic
    @Column(name = "FLIGHT_VOYAGE")
    public String getFlightVoyage() {
        return flightVoyage;
    }

    public void setFlightVoyage(String flightVoyage) {
        this.flightVoyage = flightVoyage;
    }

    private String flightPart1;

    @Basic
    @Column(name = "FLIGHT_PART_1")
    public String getFlightPart1() {
        return flightPart1;
    }

    public void setFlightPart1(String flightPart1) {
        this.flightPart1 = flightPart1;
    }

    private String flightRoute;

    @Basic
    @Column(name = "FLIGHT_ROUTE")
    public String getFlightRoute() {
        return flightRoute;
    }

    public void setFlightRoute(String flightRoute) {
        this.flightRoute = flightRoute;
    }

    private String landAndTakeoffFeeCoe;

    @Basic
    @Column(name = "LAND_AND_TAKEOFF_FEE_COE")
    public String getLandAndTakeoffFeeCoe() {
        return landAndTakeoffFeeCoe;
    }

    public void setLandAndTakeoffFeeCoe(String landAndTakeoffFeeCoe) {
        this.landAndTakeoffFeeCoe = landAndTakeoffFeeCoe;
    }

    private String partingFeeCoe;

    @Basic
    @Column(name = "PARTING_FEE_COE")
    public String getPartingFeeCoe() {
        return partingFeeCoe;
    }

    public void setPartingFeeCoe(String partingFeeCoe) {
        this.partingFeeCoe = partingFeeCoe;
    }

    private String pasServiceFeeCoe;

    @Basic
    @Column(name = "PAS_SERVICE_FEE_COE")
    public String getPasServiceFeeCoe() {
        return pasServiceFeeCoe;
    }

    public void setPasServiceFeeCoe(String pasServiceFeeCoe) {
        this.pasServiceFeeCoe = pasServiceFeeCoe;
    }

    private String pasSecurityCheckFeeCoe;

    @Basic
    @Column(name = "PAS_SECURITY_CHECK_FEE_COE")
    public String getPasSecurityCheckFeeCoe() {
        return pasSecurityCheckFeeCoe;
    }

    public void setPasSecurityCheckFeeCoe(String pasSecurityCheckFeeCoe) {
        this.pasSecurityCheckFeeCoe = pasSecurityCheckFeeCoe;
    }

    private String pasCargoSecurityCheckCoe;

    @Basic
    @Column(name = "PAS_CARGO_SECURITY_CHECK_COE")
    public String getPasCargoSecurityCheckCoe() {
        return pasCargoSecurityCheckCoe;
    }

    public void setPasCargoSecurityCheckCoe(String pasCargoSecurityCheckCoe) {
        this.pasCargoSecurityCheckCoe = pasCargoSecurityCheckCoe;
    }

    private String basicFeeCoe;

    @Basic
    @Column(name = "BASIC_FEE_COE")
    public String getBasicFeeCoe() {
        return basicFeeCoe;
    }

    public void setBasicFeeCoe(String basicFeeCoe) {
        this.basicFeeCoe = basicFeeCoe;
    }

    private String stowageCommunicationFeeCoe;

    @Basic
    @Column(name = "STOWAGE_COMMUNICATION_FEE_COE")
    public String getStowageCommunicationFeeCoe() {
        return stowageCommunicationFeeCoe;
    }

    public void setStowageCommunicationFeeCoe(String stowageCommunicationFeeCoe) {
        this.stowageCommunicationFeeCoe = stowageCommunicationFeeCoe;
    }

    private String packagingFacility;

    @Basic
    @Column(name = "PACKAGING_FACILITY")
    public String getPackagingFacility() {
        return packagingFacility;
    }

    public void setPackagingFacility(String packagingFacility) {
        this.packagingFacility = packagingFacility;
    }

    private String passengerLuggageFeeCoe;

    @Basic
    @Column(name = "PASSENGER_LUGGAGE_FEE_COE")
    public String getPassengerLuggageFeeCoe() {
        return passengerLuggageFeeCoe;
    }

    public void setPassengerLuggageFeeCoe(String passengerLuggageFeeCoe) {
        this.passengerLuggageFeeCoe = passengerLuggageFeeCoe;
    }

    private String cagroMailFeeCoe;

    @Basic
    @Column(name = "CAGRO_MAIL_FEE_COE")
    public String getCagroMailFeeCoe() {
        return cagroMailFeeCoe;
    }

    public void setCagroMailFeeCoe(String cagroMailFeeCoe) {
        this.cagroMailFeeCoe = cagroMailFeeCoe;
    }

    private String apronServiceFeeCoe;

    @Basic
    @Column(name = "APRON_SERVICE_FEE_COE")
    public String getApronServiceFeeCoe() {
        return apronServiceFeeCoe;
    }

    public void setApronServiceFeeCoe(String apronServiceFeeCoe) {
        this.apronServiceFeeCoe = apronServiceFeeCoe;
    }

    private String viaFlightServiceFeeCoe;

    @Basic
    @Column(name = "VIA_FLIGHT_SERVICE_FEE_COE")
    public String getViaFlightServiceFeeCoe() {
        return viaFlightServiceFeeCoe;
    }

    public void setViaFlightServiceFeeCoe(String viaFlightServiceFeeCoe) {
        this.viaFlightServiceFeeCoe = viaFlightServiceFeeCoe;
    }

    private String preflightServiceFeeCoe;

    @Basic
    @Column(name = "PREFLIGHT_SERVICE_FEE_COE")
    public String getPreflightServiceFeeCoe() {
        return preflightServiceFeeCoe;
    }

    public void setPreflightServiceFeeCoe(String preflightServiceFeeCoe) {
        this.preflightServiceFeeCoe = preflightServiceFeeCoe;
    }

    private String postflightServiceFeeCoe;

    @Basic
    @Column(name = "POSTFLIGHT_SERVICE_FEE_COE")
    public String getPostflightServiceFeeCoe() {
        return postflightServiceFeeCoe;
    }

    public void setPostflightServiceFeeCoe(String postflightServiceFeeCoe) {
        this.postflightServiceFeeCoe = postflightServiceFeeCoe;
    }

    private String sundryDutiesFeeCoe;

    @Basic
    @Column(name = "SUNDRY_DUTIES_FEE_COE")
    public String getSundryDutiesFeeCoe() {
        return sundryDutiesFeeCoe;
    }

    public void setSundryDutiesFeeCoe(String sundryDutiesFeeCoe) {
        this.sundryDutiesFeeCoe = sundryDutiesFeeCoe;
    }

    private String routineCheckFeeCoe;

    @Basic
    @Column(name = "ROUTINE_CHECK_FEE_COE")
    public String getRoutineCheckFeeCoe() {
        return routineCheckFeeCoe;
    }

    public void setRoutineCheckFeeCoe(String routineCheckFeeCoe) {
        this.routineCheckFeeCoe = routineCheckFeeCoe;
    }

    private String flithtDispatchFeeCoe;

    @Basic
    @Column(name = "FLITHT_DISPATCH_FEE_COE")
    public String getFlithtDispatchFeeCoe() {
        return flithtDispatchFeeCoe;
    }

    public void setFlithtDispatchFeeCoe(String flithtDispatchFeeCoe) {
        this.flithtDispatchFeeCoe = flithtDispatchFeeCoe;
    }

    private String leadcarFeeCoe;

    @Basic
    @Column(name = "LEADCAR_FEE_COE")
    public String getLeadcarFeeCoe() {
        return leadcarFeeCoe;
    }

    public void setLeadcarFeeCoe(String leadcarFeeCoe) {
        this.leadcarFeeCoe = leadcarFeeCoe;
    }

    private String airTruckFeeCoe;

    @Basic
    @Column(name = "AIR_TRUCK_FEE_COE")
    public String getAirTruckFeeCoe() {
        return airTruckFeeCoe;
    }

    public void setAirTruckFeeCoe(String airTruckFeeCoe) {
        this.airTruckFeeCoe = airTruckFeeCoe;
    }

    private String electricTruckFeeCoe;

    @Basic
    @Column(name = "ELECTRIC_TRUCK_FEE_COE")
    public String getElectricTruckFeeCoe() {
        return electricTruckFeeCoe;
    }

    public void setElectricTruckFeeCoe(String electricTruckFeeCoe) {
        this.electricTruckFeeCoe = electricTruckFeeCoe;
    }

    private String tractorFeeCoe;

    @Basic
    @Column(name = "TRACTOR_FEE_COE")
    public String getTractorFeeCoe() {
        return tractorFeeCoe;
    }

    public void setTractorFeeCoe(String tractorFeeCoe) {
        this.tractorFeeCoe = tractorFeeCoe;
    }

    private String airConditionTruckFeeCoe;

    @Basic
    @Column(name = "AIR_CONDITION_TRUCK_FEE_COE")
    public String getAirConditionTruckFeeCoe() {
        return airConditionTruckFeeCoe;
    }

    public void setAirConditionTruckFeeCoe(String airConditionTruckFeeCoe) {
        this.airConditionTruckFeeCoe = airConditionTruckFeeCoe;
    }

    private String deicingTruckFeeCoe;

    @Basic
    @Column(name = "DEICING_TRUCK_FEE_COE")
    public String getDeicingTruckFeeCoe() {
        return deicingTruckFeeCoe;
    }

    public void setDeicingTruckFeeCoe(String deicingTruckFeeCoe) {
        this.deicingTruckFeeCoe = deicingTruckFeeCoe;
    }

    private String snowRemovalTruckFeeCoe;

    @Basic
    @Column(name = "SNOW_REMOVAL_TRUCK_FEE_COE")
    public String getSnowRemovalTruckFeeCoe() {
        return snowRemovalTruckFeeCoe;
    }

    public void setSnowRemovalTruckFeeCoe(String snowRemovalTruckFeeCoe) {
        this.snowRemovalTruckFeeCoe = snowRemovalTruckFeeCoe;
    }

    private String singleAirBrageFeeCoe;

    @Basic
    @Column(name = "SINGLE_AIR_BRAGE_FEE_COE")
    public String getSingleAirBrageFeeCoe() {
        return singleAirBrageFeeCoe;
    }

    public void setSingleAirBrageFeeCoe(String singleAirBrageFeeCoe) {
        this.singleAirBrageFeeCoe = singleAirBrageFeeCoe;
    }

    private String doubleAirBrageFeeCoe;

    @Basic
    @Column(name = "DOUBLE_AIR_BRAGE_FEE_COE")
    public String getDoubleAirBrageFeeCoe() {
        return doubleAirBrageFeeCoe;
    }

    public void setDoubleAirBrageFeeCoe(String doubleAirBrageFeeCoe) {
        this.doubleAirBrageFeeCoe = doubleAirBrageFeeCoe;
    }

    private String passenageCarFeeCoe;

    @Basic
    @Column(name = "PASSENAGE_CAR_FEE_COE")
    public String getPassenageCarFeeCoe() {
        return passenageCarFeeCoe;
    }

    public void setPassenageCarFeeCoe(String passenageCarFeeCoe) {
        this.passenageCarFeeCoe = passenageCarFeeCoe;
    }

    private String airdromeUsedByCrewFeeCoe;

    @Basic
    @Column(name = "AIRDROME_USED_BY_CREW_FEE_COE")
    public String getAirdromeUsedByCrewFeeCoe() {
        return airdromeUsedByCrewFeeCoe;
    }

    public void setAirdromeUsedByCrewFeeCoe(String airdromeUsedByCrewFeeCoe) {
        this.airdromeUsedByCrewFeeCoe = airdromeUsedByCrewFeeCoe;
    }

    private String airdromeUsedByPasFeeCoe;

    @Basic
    @Column(name = "AIRDROME_USED_BY_PAS_FEE_COE")
    public String getAirdromeUsedByPasFeeCoe() {
        return airdromeUsedByPasFeeCoe;
    }

    public void setAirdromeUsedByPasFeeCoe(String airdromeUsedByPasFeeCoe) {
        this.airdromeUsedByPasFeeCoe = airdromeUsedByPasFeeCoe;
    }

    private String liftingPlatformCarFeeCoe;

    @Basic
    @Column(name = "LIFTING_PLATFORM_CAR_FEE_COE")
    public String getLiftingPlatformCarFeeCoe() {
        return liftingPlatformCarFeeCoe;
    }

    public void setLiftingPlatformCarFeeCoe(String liftingPlatformCarFeeCoe) {
        this.liftingPlatformCarFeeCoe = liftingPlatformCarFeeCoe;
    }

    private String unroutineCheckFeeCoe;

    @Basic
    @Column(name = "UNROUTINE_CHECK_FEE_COE")
    public String getUnroutineCheckFeeCoe() {
        return unroutineCheckFeeCoe;
    }

    public void setUnroutineCheckFeeCoe(String unroutineCheckFeeCoe) {
        this.unroutineCheckFeeCoe = unroutineCheckFeeCoe;
    }

    private String speCarForDisabledFeeCoe;

    @Basic
    @Column(name = "SPE_CAR_FOR_DISABLED_FEE_COE")
    public String getSpeCarForDisabledFeeCoe() {
        return speCarForDisabledFeeCoe;
    }

    public void setSpeCarForDisabledFeeCoe(String speCarForDisabledFeeCoe) {
        this.speCarForDisabledFeeCoe = speCarForDisabledFeeCoe;
    }

    private Long routineCheckTime;

    @Basic
    @Column(name = "ROUTINE_CHECK_TIME")
    public Long getRoutineCheckTime() {
        return routineCheckTime;
    }

    public void setRoutineCheckTime(Long routineCheckTime) {
        this.routineCheckTime = routineCheckTime;
    }

    private Long unroutineCheckTime;

    @Basic
    @Column(name = "UNROUTINE_CHECK_TIME")
    public Long getUnroutineCheckTime() {
        return unroutineCheckTime;
    }

    public void setUnroutineCheckTime(Long unroutineCheckTime) {
        this.unroutineCheckTime = unroutineCheckTime;
    }

    private Long psssengerCarUsedTime;

    @Basic
    @Column(name = "PSSSENGER_CAR_USED_TIME")
    public Long getPsssengerCarUsedTime() {
        return psssengerCarUsedTime;
    }

    public void setPsssengerCarUsedTime(Long psssengerCarUsedTime) {
        this.psssengerCarUsedTime = psssengerCarUsedTime;
    }

    private String airdromeUsedByPasCount;

    @Basic
    @Column(name = "AIRDROME_USED_BY_PAS_COUNT")
    public String getAirdromeUsedByPasCount() {
        return airdromeUsedByPasCount;
    }

    public void setAirdromeUsedByPasCount(String airdromeUsedByPasCount) {
        this.airdromeUsedByPasCount = airdromeUsedByPasCount;
    }

    private Long aircraftPayload;

    @Basic
    @Column(name = "AIRCRAFT_PAYLOAD")
    public Long getAircraftPayload() {
        return aircraftPayload;
    }

    public void setAircraftPayload(Long aircraftPayload) {
        this.aircraftPayload = aircraftPayload;
    }

    private Long aircraftSeatCapacity;

    @Basic
    @Column(name = "AIRCRAFT_SEAT_CAPACITY")
    public Long getAircraftSeatCapacity() {
        return aircraftSeatCapacity;
    }

    public void setAircraftSeatCapacity(Long aircraftSeatCapacity) {
        this.aircraftSeatCapacity = aircraftSeatCapacity;
    }

    private String aFlightProperty;

    @Basic
    @Column(name = "A_FLIGHT_PROPERTY")
    public String getaFlightProperty() {
        return aFlightProperty;
    }

    public void setaFlightProperty(String aFlightProperty) {
        this.aFlightProperty = aFlightProperty;
    }

    private String speCarForDisabledCount;

    @Basic
    @Column(name = "SPE_CAR_FOR_DISABLED_COUNT")
    public String getSpeCarForDisabledCount() {
        return speCarForDisabledCount;
    }

    public void setSpeCarForDisabledCount(String speCarForDisabledCount) {
        this.speCarForDisabledCount = speCarForDisabledCount;
    }

    private Timestamp executeDay;

    @Basic
    @Column(name = "EXECUTE_DAY")
    public Timestamp getExecuteDay() {
        return executeDay;
    }

    public void setExecuteDay(Timestamp executeDay) {
        this.executeDay = executeDay;
    }

    private Long childInternal;

    @Basic
    @Column(name = "CHILD_INTERNAL")
    public Long getChildInternal() {
        return childInternal;
    }

    public void setChildInternal(Long childInternal) {
        this.childInternal = childInternal;
    }

    private Long babyInternal;

    @Basic
    @Column(name = "BABY_INTERNAL")
    public Long getBabyInternal() {
        return babyInternal;
    }

    public void setBabyInternal(Long babyInternal) {
        this.babyInternal = babyInternal;
    }

    private Long babyInternational;

    @Basic
    @Column(name = "BABY_INTERNATIONAL")
    public Long getBabyInternational() {
        return babyInternational;
    }

    public void setBabyInternational(Long babyInternational) {
        this.babyInternational = babyInternational;
    }

    private Long childInternational;

    @Basic
    @Column(name = "CHILD_INTERNATIONAL")
    public Long getChildInternational() {
        return childInternational;
    }

    public void setChildInternational(Long childInternational) {
        this.childInternational = childInternational;
    }

    private Long viaAdult;

    @Basic
    @Column(name = "VIA_ADULT")
    public Long getViaAdult() {
        return viaAdult;
    }

    public void setViaAdult(Long viaAdult) {
        this.viaAdult = viaAdult;
    }

    private Long viaChild;

    @Basic
    @Column(name = "VIA_CHILD")
    public Long getViaChild() {
        return viaChild;
    }

    public void setViaChild(Long viaChild) {
        this.viaChild = viaChild;
    }

    private Long viaBaby;

    @Basic
    @Column(name = "VIA_BABY")
    public Long getViaBaby() {
        return viaBaby;
    }

    public void setViaBaby(Long viaBaby) {
        this.viaBaby = viaBaby;
    }

    private String garbageTruckUseCount;

    @Basic
    @Column(name = "GARBAGE_TRUCK_USE_COUNT")
    public String getGarbageTruckUseCount() {
        return garbageTruckUseCount;
    }

    public void setGarbageTruckUseCount(String garbageTruckUseCount) {
        this.garbageTruckUseCount = garbageTruckUseCount;
    }

    private String waterTruckUseCount;

    @Basic
    @Column(name = "WATER_TRUCK_USE_COUNT")
    public String getWaterTruckUseCount() {
        return waterTruckUseCount;
    }

    public void setWaterTruckUseCount(String waterTruckUseCount) {
        this.waterTruckUseCount = waterTruckUseCount;
    }

    private String cesspoolageTruckUseCount;

    @Basic
    @Column(name = "CESSPOOLAGE_TRUCK_USE_COUNT")
    public String getCesspoolageTruckUseCount() {
        return cesspoolageTruckUseCount;
    }

    public void setCesspoolageTruckUseCount(String cesspoolageTruckUseCount) {
        this.cesspoolageTruckUseCount = cesspoolageTruckUseCount;
    }

    private String flightPart2;

    @Basic
    @Column(name = "FLIGHT_PART_2")
    public String getFlightPart2() {
        return flightPart2;
    }

    public void setFlightPart2(String flightPart2) {
        this.flightPart2 = flightPart2;
    }

    private Long luggageWeightInternal;

    @Basic
    @Column(name = "LUGGAGE_WEIGHT_INTERNAL")
    public Long getLuggageWeightInternal() {
        return luggageWeightInternal;
    }

    public void setLuggageWeightInternal(Long luggageWeightInternal) {
        this.luggageWeightInternal = luggageWeightInternal;
    }

    private Long luggageWeightInternational;

    @Basic
    @Column(name = "LUGGAGE_WEIGHT_INTERNATIONAL")
    public Long getLuggageWeightInternational() {
        return luggageWeightInternational;
    }

    public void setLuggageWeightInternational(Long luggageWeightInternational) {
        this.luggageWeightInternational = luggageWeightInternational;
    }

    private String flightPart0;

    @Basic
    @Column(name = "FLIGHT_PART_0")
    public String getFlightPart0() {
        return flightPart0;
    }

    public void setFlightPart0(String flightPart0) {
        this.flightPart0 = flightPart0;
    }

    private Long linkFlightMateInfoId;

    @Basic
    @Column(name = "LINK_FLIGHT_MATE_INFO_ID")
    public Long getLinkFlightMateInfoId() {
        return linkFlightMateInfoId;
    }

    public void setLinkFlightMateInfoId(Long linkFlightMateInfoId) {
        this.linkFlightMateInfoId = linkFlightMateInfoId;
    }

    private String dFlightProperty;

    @Basic
    @Column(name = "D_FLIGHT_PROPERTY")
    public String getdFlightProperty() {
        return dFlightProperty;
    }

    public void setdFlightProperty(String dFlightProperty) {
        this.dFlightProperty = dFlightProperty;
    }

    private String dRegisteration;

    @Basic
    @Column(name = "D_REGISTERATION")
    public String getdRegisteration() {
        return dRegisteration;
    }

    public void setdRegisteration(String dRegisteration) {
        this.dRegisteration = dRegisteration;
    }

    private String isHighDensity;

    @Basic
    @Column(name = "IS_HIGH_DENSITY")
    public String getIsHighDensity() {
        return isHighDensity;
    }

    public void setIsHighDensity(String isHighDensity) {
        this.isHighDensity = isHighDensity;
    }

    private String isPackagingFacility;

    @Basic
    @Column(name = "IS_PACKAGING_FACILITY")
    public String getIsPackagingFacility() {
        return isPackagingFacility;
    }

    public void setIsPackagingFacility(String isPackagingFacility) {
        this.isPackagingFacility = isPackagingFacility;
    }

    private String iataOriginAirport;

    @Basic
    @Column(name = "IATA_ORIGIN_AIRPORT")
    public String getIataOriginAirport() {
        return iataOriginAirport;
    }

    public void setIataOriginAirport(String iataOriginAirport) {
        this.iataOriginAirport = iataOriginAirport;
    }

    private String iataDestinationAirport;

    @Basic
    @Column(name = "IATA_DESTINATION_AIRPORT")
    public String getIataDestinationAirport() {
        return iataDestinationAirport;
    }

    public void setIataDestinationAirport(String iataDestinationAirport) {
        this.iataDestinationAirport = iataDestinationAirport;
    }

    private String aircraftService;

    @Basic
    @Column(name = "AIRCRAFT_SERVICE")
    public String getAircraftService() {
        return aircraftService;
    }

    public void setAircraftService(String aircraftService) {
        this.aircraftService = aircraftService;
    }

    private String stat;

    @Basic
    @Column(name = "STAT")
    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    private Long bridgeAirconditionUsedTime;

    @Basic
    @Column(name = "BRIDGE_AIRCONDITION_USED_TIME")
    public Long getBridgeAirconditionUsedTime() {
        return bridgeAirconditionUsedTime;
    }

    public void setBridgeAirconditionUsedTime(Long bridgeAirconditionUsedTime) {
        this.bridgeAirconditionUsedTime = bridgeAirconditionUsedTime;
    }

    private Long bridgeElectricUsedTime;

    @Basic
    @Column(name = "BRIDGE_ELECTRIC_USED_TIME")
    public Long getBridgeElectricUsedTime() {
        return bridgeElectricUsedTime;
    }

    public void setBridgeElectricUsedTime(Long bridgeElectricUsedTime) {
        this.bridgeElectricUsedTime = bridgeElectricUsedTime;
    }

    private Long deicingTruckUsedTime;

    @Basic
    @Column(name = "DEICING_TRUCK_USED_TIME")
    public Long getDeicingTruckUsedTime() {
        return deicingTruckUsedTime;
    }

    public void setDeicingTruckUsedTime(Long deicingTruckUsedTime) {
        this.deicingTruckUsedTime = deicingTruckUsedTime;
    }

    private Timestamp scheduledTime;

    @Basic
    @Column(name = "SCHEDULED_TIME")
    public Timestamp getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Timestamp scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    private Timestamp scheduledLandedTime;

    @Basic
    @Column(name = "SCHEDULED_LANDED_TIME")
    public Timestamp getScheduledLandedTime() {
        return scheduledLandedTime;
    }

    public void setScheduledLandedTime(Timestamp scheduledLandedTime) {
        this.scheduledLandedTime = scheduledLandedTime;
    }

    private Timestamp scheduledTakeOffTime;

    @Basic
    @Column(name = "SCHEDULED_TAKE_OFF_TIME")
    public Timestamp getScheduledTakeOffTime() {
        return scheduledTakeOffTime;
    }

    public void setScheduledTakeOffTime(Timestamp scheduledTakeOffTime) {
        this.scheduledTakeOffTime = scheduledTakeOffTime;
    }

    private String aStandFlag;

    @Basic
    @Column(name = "A_STAND_FLAG")
    public String getaStandFlag() {
        return aStandFlag;
    }

    public void setaStandFlag(String aStandFlag) {
        this.aStandFlag = aStandFlag;
    }

    private String dStandFlag;

    @Basic
    @Column(name = "D_STAND_FLAG")
    public String getdStandFlag() {
        return dStandFlag;
    }

    public void setdStandFlag(String dStandFlag) {
        this.dStandFlag = dStandFlag;
    }

    private Long vipCount;

    @Basic
    @Column(name = "VIP_COUNT")
    public Long getVipCount() {
        return vipCount;
    }

    public void setVipCount(Long vipCount) {
        this.vipCount = vipCount;
    }

    private Long firstClassCount;

    @Basic
    @Column(name = "FIRST_CLASS_COUNT")
    public Long getFirstClassCount() {
        return firstClassCount;
    }

    public void setFirstClassCount(Long firstClassCount) {
        this.firstClassCount = firstClassCount;
    }

    private Long vipFirstAirdromeUsedCount;

    @Basic
    @Column(name = "VIP_FIRST_AIRDROME_USED_COUNT")
    public Long getVipFirstAirdromeUsedCount() {
        return vipFirstAirdromeUsedCount;
    }

    public void setVipFirstAirdromeUsedCount(Long vipFirstAirdromeUsedCount) {
        this.vipFirstAirdromeUsedCount = vipFirstAirdromeUsedCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightMateInfo that = (FlightMateInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (aRegisteration != null ? !aRegisteration.equals(that.aRegisteration) : that.aRegisteration != null)
            return false;
        if (flightServiceInfoId != null ? !flightServiceInfoId.equals(that.flightServiceInfoId) : that.flightServiceInfoId != null)
            return false;
        if (flightBaseId != null ? !flightBaseId.equals(that.flightBaseId) : that.flightBaseId != null) return false;
        if (aircraftId != null ? !aircraftId.equals(that.aircraftId) : that.aircraftId != null) return false;
        if (matchMethod != null ? !matchMethod.equals(that.matchMethod) : that.matchMethod != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (modifyUser != null ? !modifyUser.equals(that.modifyUser) : that.modifyUser != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (removeFlag != null ? !removeFlag.equals(that.removeFlag) : that.removeFlag != null) return false;
        if (landTime != null ? !landTime.equals(that.landTime) : that.landTime != null) return false;
        if (landedTime != null ? !landedTime.equals(that.landedTime) : that.landedTime != null) return false;
        if (takeOffTime != null ? !takeOffTime.equals(that.takeOffTime) : that.takeOffTime != null) return false;
        if (aircraftTakeOffWeight != null ? !aircraftTakeOffWeight.equals(that.aircraftTakeOffWeight) : that.aircraftTakeOffWeight != null)
            return false;
        if (leadCarUsedCount != null ? !leadCarUsedCount.equals(that.leadCarUsedCount) : that.leadCarUsedCount != null)
            return false;
        if (passengerBridgeNumber != null ? !passengerBridgeNumber.equals(that.passengerBridgeNumber) : that.passengerBridgeNumber != null)
            return false;
        if (tractorsUsedCount != null ? !tractorsUsedCount.equals(that.tractorsUsedCount) : that.tractorsUsedCount != null)
            return false;
        if (airTruckTime != null ? !airTruckTime.equals(that.airTruckTime) : that.airTruckTime != null) return false;
        if (airconditioningTime != null ? !airconditioningTime.equals(that.airconditioningTime) : that.airconditioningTime != null)
            return false;
        if (airdromeUsedByCrewCount != null ? !airdromeUsedByCrewCount.equals(that.airdromeUsedByCrewCount) : that.airdromeUsedByCrewCount != null)
            return false;
        if (deicingVehicleTime != null ? !deicingVehicleTime.equals(that.deicingVehicleTime) : that.deicingVehicleTime != null)
            return false;
        if (electricTruckTime != null ? !electricTruckTime.equals(that.electricTruckTime) : that.electricTruckTime != null)
            return false;
        if (liftingPlatformCarTime != null ? !liftingPlatformCarTime.equals(that.liftingPlatformCarTime) : that.liftingPlatformCarTime != null)
            return false;
        if (passengerBridgeTime != null ? !passengerBridgeTime.equals(that.passengerBridgeTime) : that.passengerBridgeTime != null)
            return false;
        if (flightCountryType != null ? !flightCountryType.equals(that.flightCountryType) : that.flightCountryType != null)
            return false;
        if (delayCode != null ? !delayCode.equals(that.delayCode) : that.delayCode != null) return false;
        if (isNightFlight != null ? !isNightFlight.equals(that.isNightFlight) : that.isNightFlight != null)
            return false;
        if (isPeakFlight != null ? !isPeakFlight.equals(that.isPeakFlight) : that.isPeakFlight != null) return false;
        if (isVia != null ? !isVia.equals(that.isVia) : that.isVia != null) return false;
        if (specialPlane != null ? !specialPlane.equals(that.specialPlane) : that.specialPlane != null) return false;
        if (aFlightIdentity != null ? !aFlightIdentity.equals(that.aFlightIdentity) : that.aFlightIdentity != null)
            return false;
        if (dFlightIdentity != null ? !dFlightIdentity.equals(that.dFlightIdentity) : that.dFlightIdentity != null)
            return false;
        if (aAircraftType != null ? !aAircraftType.equals(that.aAircraftType) : that.aAircraftType != null)
            return false;
        if (dAircraftType != null ? !dAircraftType.equals(that.dAircraftType) : that.dAircraftType != null)
            return false;
        if (aStandNum != null ? !aStandNum.equals(that.aStandNum) : that.aStandNum != null) return false;
        if (dStandNum != null ? !dStandNum.equals(that.dStandNum) : that.dStandNum != null) return false;
        if (flightDirection != null ? !flightDirection.equals(that.flightDirection) : that.flightDirection != null)
            return false;
        if (linkFlightBaseId != null ? !linkFlightBaseId.equals(that.linkFlightBaseId) : that.linkFlightBaseId != null)
            return false;
        if (executeTime != null ? !executeTime.equals(that.executeTime) : that.executeTime != null) return false;
        if (airLineName != null ? !airLineName.equals(that.airLineName) : that.airLineName != null) return false;
        if (airLineId != null ? !airLineId.equals(that.airLineId) : that.airLineId != null) return false;
        if (isWideOrNarrow != null ? !isWideOrNarrow.equals(that.isWideOrNarrow) : that.isWideOrNarrow != null)
            return false;
        if (passengerInternal != null ? !passengerInternal.equals(that.passengerInternal) : that.passengerInternal != null)
            return false;
        if (passengerInternational != null ? !passengerInternational.equals(that.passengerInternational) : that.passengerInternational != null)
            return false;
        if (airMailWeightInternal != null ? !airMailWeightInternal.equals(that.airMailWeightInternal) : that.airMailWeightInternal != null)
            return false;
        if (airMailWeightInternational != null ? !airMailWeightInternational.equals(that.airMailWeightInternational) : that.airMailWeightInternational != null)
            return false;
        if (airCargoWeightInternal != null ? !airCargoWeightInternal.equals(that.airCargoWeightInternal) : that.airCargoWeightInternal != null)
            return false;
        if (airCargoWeightInternational != null ? !airCargoWeightInternational.equals(that.airCargoWeightInternational) : that.airCargoWeightInternational != null)
            return false;
        if (flightCountryTypeQuery != null ? !flightCountryTypeQuery.equals(that.flightCountryTypeQuery) : that.flightCountryTypeQuery != null)
            return false;
        if (flightCountryTypeCharge != null ? !flightCountryTypeCharge.equals(that.flightCountryTypeCharge) : that.flightCountryTypeCharge != null)
            return false;
        if (flightVoyage != null ? !flightVoyage.equals(that.flightVoyage) : that.flightVoyage != null) return false;
        if (flightPart1 != null ? !flightPart1.equals(that.flightPart1) : that.flightPart1 != null) return false;
        if (flightRoute != null ? !flightRoute.equals(that.flightRoute) : that.flightRoute != null) return false;
        if (landAndTakeoffFeeCoe != null ? !landAndTakeoffFeeCoe.equals(that.landAndTakeoffFeeCoe) : that.landAndTakeoffFeeCoe != null)
            return false;
        if (partingFeeCoe != null ? !partingFeeCoe.equals(that.partingFeeCoe) : that.partingFeeCoe != null)
            return false;
        if (pasServiceFeeCoe != null ? !pasServiceFeeCoe.equals(that.pasServiceFeeCoe) : that.pasServiceFeeCoe != null)
            return false;
        if (pasSecurityCheckFeeCoe != null ? !pasSecurityCheckFeeCoe.equals(that.pasSecurityCheckFeeCoe) : that.pasSecurityCheckFeeCoe != null)
            return false;
        if (pasCargoSecurityCheckCoe != null ? !pasCargoSecurityCheckCoe.equals(that.pasCargoSecurityCheckCoe) : that.pasCargoSecurityCheckCoe != null)
            return false;
        if (basicFeeCoe != null ? !basicFeeCoe.equals(that.basicFeeCoe) : that.basicFeeCoe != null) return false;
        if (stowageCommunicationFeeCoe != null ? !stowageCommunicationFeeCoe.equals(that.stowageCommunicationFeeCoe) : that.stowageCommunicationFeeCoe != null)
            return false;
        if (packagingFacility != null ? !packagingFacility.equals(that.packagingFacility) : that.packagingFacility != null)
            return false;
        if (passengerLuggageFeeCoe != null ? !passengerLuggageFeeCoe.equals(that.passengerLuggageFeeCoe) : that.passengerLuggageFeeCoe != null)
            return false;
        if (cagroMailFeeCoe != null ? !cagroMailFeeCoe.equals(that.cagroMailFeeCoe) : that.cagroMailFeeCoe != null)
            return false;
        if (apronServiceFeeCoe != null ? !apronServiceFeeCoe.equals(that.apronServiceFeeCoe) : that.apronServiceFeeCoe != null)
            return false;
        if (viaFlightServiceFeeCoe != null ? !viaFlightServiceFeeCoe.equals(that.viaFlightServiceFeeCoe) : that.viaFlightServiceFeeCoe != null)
            return false;
        if (preflightServiceFeeCoe != null ? !preflightServiceFeeCoe.equals(that.preflightServiceFeeCoe) : that.preflightServiceFeeCoe != null)
            return false;
        if (postflightServiceFeeCoe != null ? !postflightServiceFeeCoe.equals(that.postflightServiceFeeCoe) : that.postflightServiceFeeCoe != null)
            return false;
        if (sundryDutiesFeeCoe != null ? !sundryDutiesFeeCoe.equals(that.sundryDutiesFeeCoe) : that.sundryDutiesFeeCoe != null)
            return false;
        if (routineCheckFeeCoe != null ? !routineCheckFeeCoe.equals(that.routineCheckFeeCoe) : that.routineCheckFeeCoe != null)
            return false;
        if (flithtDispatchFeeCoe != null ? !flithtDispatchFeeCoe.equals(that.flithtDispatchFeeCoe) : that.flithtDispatchFeeCoe != null)
            return false;
        if (leadcarFeeCoe != null ? !leadcarFeeCoe.equals(that.leadcarFeeCoe) : that.leadcarFeeCoe != null)
            return false;
        if (airTruckFeeCoe != null ? !airTruckFeeCoe.equals(that.airTruckFeeCoe) : that.airTruckFeeCoe != null)
            return false;
        if (electricTruckFeeCoe != null ? !electricTruckFeeCoe.equals(that.electricTruckFeeCoe) : that.electricTruckFeeCoe != null)
            return false;
        if (tractorFeeCoe != null ? !tractorFeeCoe.equals(that.tractorFeeCoe) : that.tractorFeeCoe != null)
            return false;
        if (airConditionTruckFeeCoe != null ? !airConditionTruckFeeCoe.equals(that.airConditionTruckFeeCoe) : that.airConditionTruckFeeCoe != null)
            return false;
        if (deicingTruckFeeCoe != null ? !deicingTruckFeeCoe.equals(that.deicingTruckFeeCoe) : that.deicingTruckFeeCoe != null)
            return false;
        if (snowRemovalTruckFeeCoe != null ? !snowRemovalTruckFeeCoe.equals(that.snowRemovalTruckFeeCoe) : that.snowRemovalTruckFeeCoe != null)
            return false;
        if (singleAirBrageFeeCoe != null ? !singleAirBrageFeeCoe.equals(that.singleAirBrageFeeCoe) : that.singleAirBrageFeeCoe != null)
            return false;
        if (doubleAirBrageFeeCoe != null ? !doubleAirBrageFeeCoe.equals(that.doubleAirBrageFeeCoe) : that.doubleAirBrageFeeCoe != null)
            return false;
        if (passenageCarFeeCoe != null ? !passenageCarFeeCoe.equals(that.passenageCarFeeCoe) : that.passenageCarFeeCoe != null)
            return false;
        if (airdromeUsedByCrewFeeCoe != null ? !airdromeUsedByCrewFeeCoe.equals(that.airdromeUsedByCrewFeeCoe) : that.airdromeUsedByCrewFeeCoe != null)
            return false;
        if (airdromeUsedByPasFeeCoe != null ? !airdromeUsedByPasFeeCoe.equals(that.airdromeUsedByPasFeeCoe) : that.airdromeUsedByPasFeeCoe != null)
            return false;
        if (liftingPlatformCarFeeCoe != null ? !liftingPlatformCarFeeCoe.equals(that.liftingPlatformCarFeeCoe) : that.liftingPlatformCarFeeCoe != null)
            return false;
        if (unroutineCheckFeeCoe != null ? !unroutineCheckFeeCoe.equals(that.unroutineCheckFeeCoe) : that.unroutineCheckFeeCoe != null)
            return false;
        if (speCarForDisabledFeeCoe != null ? !speCarForDisabledFeeCoe.equals(that.speCarForDisabledFeeCoe) : that.speCarForDisabledFeeCoe != null)
            return false;
        if (routineCheckTime != null ? !routineCheckTime.equals(that.routineCheckTime) : that.routineCheckTime != null)
            return false;
        if (unroutineCheckTime != null ? !unroutineCheckTime.equals(that.unroutineCheckTime) : that.unroutineCheckTime != null)
            return false;
        if (psssengerCarUsedTime != null ? !psssengerCarUsedTime.equals(that.psssengerCarUsedTime) : that.psssengerCarUsedTime != null)
            return false;
        if (airdromeUsedByPasCount != null ? !airdromeUsedByPasCount.equals(that.airdromeUsedByPasCount) : that.airdromeUsedByPasCount != null)
            return false;
        if (aircraftPayload != null ? !aircraftPayload.equals(that.aircraftPayload) : that.aircraftPayload != null)
            return false;
        if (aircraftSeatCapacity != null ? !aircraftSeatCapacity.equals(that.aircraftSeatCapacity) : that.aircraftSeatCapacity != null)
            return false;
        if (aFlightProperty != null ? !aFlightProperty.equals(that.aFlightProperty) : that.aFlightProperty != null)
            return false;
        if (speCarForDisabledCount != null ? !speCarForDisabledCount.equals(that.speCarForDisabledCount) : that.speCarForDisabledCount != null)
            return false;
        if (executeDay != null ? !executeDay.equals(that.executeDay) : that.executeDay != null) return false;
        if (childInternal != null ? !childInternal.equals(that.childInternal) : that.childInternal != null)
            return false;
        if (babyInternal != null ? !babyInternal.equals(that.babyInternal) : that.babyInternal != null) return false;
        if (babyInternational != null ? !babyInternational.equals(that.babyInternational) : that.babyInternational != null)
            return false;
        if (childInternational != null ? !childInternational.equals(that.childInternational) : that.childInternational != null)
            return false;
        if (viaAdult != null ? !viaAdult.equals(that.viaAdult) : that.viaAdult != null) return false;
        if (viaChild != null ? !viaChild.equals(that.viaChild) : that.viaChild != null) return false;
        if (viaBaby != null ? !viaBaby.equals(that.viaBaby) : that.viaBaby != null) return false;
        if (garbageTruckUseCount != null ? !garbageTruckUseCount.equals(that.garbageTruckUseCount) : that.garbageTruckUseCount != null)
            return false;
        if (waterTruckUseCount != null ? !waterTruckUseCount.equals(that.waterTruckUseCount) : that.waterTruckUseCount != null)
            return false;
        if (cesspoolageTruckUseCount != null ? !cesspoolageTruckUseCount.equals(that.cesspoolageTruckUseCount) : that.cesspoolageTruckUseCount != null)
            return false;
        if (flightPart2 != null ? !flightPart2.equals(that.flightPart2) : that.flightPart2 != null) return false;
        if (luggageWeightInternal != null ? !luggageWeightInternal.equals(that.luggageWeightInternal) : that.luggageWeightInternal != null)
            return false;
        if (luggageWeightInternational != null ? !luggageWeightInternational.equals(that.luggageWeightInternational) : that.luggageWeightInternational != null)
            return false;
        if (flightPart0 != null ? !flightPart0.equals(that.flightPart0) : that.flightPart0 != null) return false;
        if (linkFlightMateInfoId != null ? !linkFlightMateInfoId.equals(that.linkFlightMateInfoId) : that.linkFlightMateInfoId != null)
            return false;
        if (dFlightProperty != null ? !dFlightProperty.equals(that.dFlightProperty) : that.dFlightProperty != null)
            return false;
        if (dRegisteration != null ? !dRegisteration.equals(that.dRegisteration) : that.dRegisteration != null)
            return false;
        if (isHighDensity != null ? !isHighDensity.equals(that.isHighDensity) : that.isHighDensity != null)
            return false;
        if (isPackagingFacility != null ? !isPackagingFacility.equals(that.isPackagingFacility) : that.isPackagingFacility != null)
            return false;
        if (iataOriginAirport != null ? !iataOriginAirport.equals(that.iataOriginAirport) : that.iataOriginAirport != null)
            return false;
        if (iataDestinationAirport != null ? !iataDestinationAirport.equals(that.iataDestinationAirport) : that.iataDestinationAirport != null)
            return false;
        if (aircraftService != null ? !aircraftService.equals(that.aircraftService) : that.aircraftService != null)
            return false;
        if (stat != null ? !stat.equals(that.stat) : that.stat != null) return false;
        if (bridgeAirconditionUsedTime != null ? !bridgeAirconditionUsedTime.equals(that.bridgeAirconditionUsedTime) : that.bridgeAirconditionUsedTime != null)
            return false;
        if (bridgeElectricUsedTime != null ? !bridgeElectricUsedTime.equals(that.bridgeElectricUsedTime) : that.bridgeElectricUsedTime != null)
            return false;
        if (deicingTruckUsedTime != null ? !deicingTruckUsedTime.equals(that.deicingTruckUsedTime) : that.deicingTruckUsedTime != null)
            return false;
        if (scheduledTime != null ? !scheduledTime.equals(that.scheduledTime) : that.scheduledTime != null)
            return false;
        if (scheduledLandedTime != null ? !scheduledLandedTime.equals(that.scheduledLandedTime) : that.scheduledLandedTime != null)
            return false;
        if (scheduledTakeOffTime != null ? !scheduledTakeOffTime.equals(that.scheduledTakeOffTime) : that.scheduledTakeOffTime != null)
            return false;
        if (aStandFlag != null ? !aStandFlag.equals(that.aStandFlag) : that.aStandFlag != null) return false;
        if (dStandFlag != null ? !dStandFlag.equals(that.dStandFlag) : that.dStandFlag != null) return false;
        if (vipCount != null ? !vipCount.equals(that.vipCount) : that.vipCount != null) return false;
        if (firstClassCount != null ? !firstClassCount.equals(that.firstClassCount) : that.firstClassCount != null)
            return false;
        if (vipFirstAirdromeUsedCount != null ? !vipFirstAirdromeUsedCount.equals(that.vipFirstAirdromeUsedCount) : that.vipFirstAirdromeUsedCount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (aRegisteration != null ? aRegisteration.hashCode() : 0);
        result = 31 * result + (flightServiceInfoId != null ? flightServiceInfoId.hashCode() : 0);
        result = 31 * result + (flightBaseId != null ? flightBaseId.hashCode() : 0);
        result = 31 * result + (aircraftId != null ? aircraftId.hashCode() : 0);
        result = 31 * result + (matchMethod != null ? matchMethod.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (modifyUser != null ? modifyUser.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (removeFlag != null ? removeFlag.hashCode() : 0);
        result = 31 * result + (landTime != null ? landTime.hashCode() : 0);
        result = 31 * result + (landedTime != null ? landedTime.hashCode() : 0);
        result = 31 * result + (takeOffTime != null ? takeOffTime.hashCode() : 0);
        result = 31 * result + (aircraftTakeOffWeight != null ? aircraftTakeOffWeight.hashCode() : 0);
        result = 31 * result + (leadCarUsedCount != null ? leadCarUsedCount.hashCode() : 0);
        result = 31 * result + (passengerBridgeNumber != null ? passengerBridgeNumber.hashCode() : 0);
        result = 31 * result + (tractorsUsedCount != null ? tractorsUsedCount.hashCode() : 0);
        result = 31 * result + (airTruckTime != null ? airTruckTime.hashCode() : 0);
        result = 31 * result + (airconditioningTime != null ? airconditioningTime.hashCode() : 0);
        result = 31 * result + (airdromeUsedByCrewCount != null ? airdromeUsedByCrewCount.hashCode() : 0);
        result = 31 * result + (deicingVehicleTime != null ? deicingVehicleTime.hashCode() : 0);
        result = 31 * result + (electricTruckTime != null ? electricTruckTime.hashCode() : 0);
        result = 31 * result + (liftingPlatformCarTime != null ? liftingPlatformCarTime.hashCode() : 0);
        result = 31 * result + (passengerBridgeTime != null ? passengerBridgeTime.hashCode() : 0);
        result = 31 * result + (flightCountryType != null ? flightCountryType.hashCode() : 0);
        result = 31 * result + (delayCode != null ? delayCode.hashCode() : 0);
        result = 31 * result + (isNightFlight != null ? isNightFlight.hashCode() : 0);
        result = 31 * result + (isPeakFlight != null ? isPeakFlight.hashCode() : 0);
        result = 31 * result + (isVia != null ? isVia.hashCode() : 0);
        result = 31 * result + (specialPlane != null ? specialPlane.hashCode() : 0);
        result = 31 * result + (aFlightIdentity != null ? aFlightIdentity.hashCode() : 0);
        result = 31 * result + (dFlightIdentity != null ? dFlightIdentity.hashCode() : 0);
        result = 31 * result + (aAircraftType != null ? aAircraftType.hashCode() : 0);
        result = 31 * result + (dAircraftType != null ? dAircraftType.hashCode() : 0);
        result = 31 * result + (aStandNum != null ? aStandNum.hashCode() : 0);
        result = 31 * result + (dStandNum != null ? dStandNum.hashCode() : 0);
        result = 31 * result + (flightDirection != null ? flightDirection.hashCode() : 0);
        result = 31 * result + (linkFlightBaseId != null ? linkFlightBaseId.hashCode() : 0);
        result = 31 * result + (executeTime != null ? executeTime.hashCode() : 0);
        result = 31 * result + (airLineName != null ? airLineName.hashCode() : 0);
        result = 31 * result + (airLineId != null ? airLineId.hashCode() : 0);
        result = 31 * result + (isWideOrNarrow != null ? isWideOrNarrow.hashCode() : 0);
        result = 31 * result + (passengerInternal != null ? passengerInternal.hashCode() : 0);
        result = 31 * result + (passengerInternational != null ? passengerInternational.hashCode() : 0);
        result = 31 * result + (airMailWeightInternal != null ? airMailWeightInternal.hashCode() : 0);
        result = 31 * result + (airMailWeightInternational != null ? airMailWeightInternational.hashCode() : 0);
        result = 31 * result + (airCargoWeightInternal != null ? airCargoWeightInternal.hashCode() : 0);
        result = 31 * result + (airCargoWeightInternational != null ? airCargoWeightInternational.hashCode() : 0);
        result = 31 * result + (flightCountryTypeQuery != null ? flightCountryTypeQuery.hashCode() : 0);
        result = 31 * result + (flightCountryTypeCharge != null ? flightCountryTypeCharge.hashCode() : 0);
        result = 31 * result + (flightVoyage != null ? flightVoyage.hashCode() : 0);
        result = 31 * result + (flightPart1 != null ? flightPart1.hashCode() : 0);
        result = 31 * result + (flightRoute != null ? flightRoute.hashCode() : 0);
        result = 31 * result + (landAndTakeoffFeeCoe != null ? landAndTakeoffFeeCoe.hashCode() : 0);
        result = 31 * result + (partingFeeCoe != null ? partingFeeCoe.hashCode() : 0);
        result = 31 * result + (pasServiceFeeCoe != null ? pasServiceFeeCoe.hashCode() : 0);
        result = 31 * result + (pasSecurityCheckFeeCoe != null ? pasSecurityCheckFeeCoe.hashCode() : 0);
        result = 31 * result + (pasCargoSecurityCheckCoe != null ? pasCargoSecurityCheckCoe.hashCode() : 0);
        result = 31 * result + (basicFeeCoe != null ? basicFeeCoe.hashCode() : 0);
        result = 31 * result + (stowageCommunicationFeeCoe != null ? stowageCommunicationFeeCoe.hashCode() : 0);
        result = 31 * result + (packagingFacility != null ? packagingFacility.hashCode() : 0);
        result = 31 * result + (passengerLuggageFeeCoe != null ? passengerLuggageFeeCoe.hashCode() : 0);
        result = 31 * result + (cagroMailFeeCoe != null ? cagroMailFeeCoe.hashCode() : 0);
        result = 31 * result + (apronServiceFeeCoe != null ? apronServiceFeeCoe.hashCode() : 0);
        result = 31 * result + (viaFlightServiceFeeCoe != null ? viaFlightServiceFeeCoe.hashCode() : 0);
        result = 31 * result + (preflightServiceFeeCoe != null ? preflightServiceFeeCoe.hashCode() : 0);
        result = 31 * result + (postflightServiceFeeCoe != null ? postflightServiceFeeCoe.hashCode() : 0);
        result = 31 * result + (sundryDutiesFeeCoe != null ? sundryDutiesFeeCoe.hashCode() : 0);
        result = 31 * result + (routineCheckFeeCoe != null ? routineCheckFeeCoe.hashCode() : 0);
        result = 31 * result + (flithtDispatchFeeCoe != null ? flithtDispatchFeeCoe.hashCode() : 0);
        result = 31 * result + (leadcarFeeCoe != null ? leadcarFeeCoe.hashCode() : 0);
        result = 31 * result + (airTruckFeeCoe != null ? airTruckFeeCoe.hashCode() : 0);
        result = 31 * result + (electricTruckFeeCoe != null ? electricTruckFeeCoe.hashCode() : 0);
        result = 31 * result + (tractorFeeCoe != null ? tractorFeeCoe.hashCode() : 0);
        result = 31 * result + (airConditionTruckFeeCoe != null ? airConditionTruckFeeCoe.hashCode() : 0);
        result = 31 * result + (deicingTruckFeeCoe != null ? deicingTruckFeeCoe.hashCode() : 0);
        result = 31 * result + (snowRemovalTruckFeeCoe != null ? snowRemovalTruckFeeCoe.hashCode() : 0);
        result = 31 * result + (singleAirBrageFeeCoe != null ? singleAirBrageFeeCoe.hashCode() : 0);
        result = 31 * result + (doubleAirBrageFeeCoe != null ? doubleAirBrageFeeCoe.hashCode() : 0);
        result = 31 * result + (passenageCarFeeCoe != null ? passenageCarFeeCoe.hashCode() : 0);
        result = 31 * result + (airdromeUsedByCrewFeeCoe != null ? airdromeUsedByCrewFeeCoe.hashCode() : 0);
        result = 31 * result + (airdromeUsedByPasFeeCoe != null ? airdromeUsedByPasFeeCoe.hashCode() : 0);
        result = 31 * result + (liftingPlatformCarFeeCoe != null ? liftingPlatformCarFeeCoe.hashCode() : 0);
        result = 31 * result + (unroutineCheckFeeCoe != null ? unroutineCheckFeeCoe.hashCode() : 0);
        result = 31 * result + (speCarForDisabledFeeCoe != null ? speCarForDisabledFeeCoe.hashCode() : 0);
        result = 31 * result + (routineCheckTime != null ? routineCheckTime.hashCode() : 0);
        result = 31 * result + (unroutineCheckTime != null ? unroutineCheckTime.hashCode() : 0);
        result = 31 * result + (psssengerCarUsedTime != null ? psssengerCarUsedTime.hashCode() : 0);
        result = 31 * result + (airdromeUsedByPasCount != null ? airdromeUsedByPasCount.hashCode() : 0);
        result = 31 * result + (aircraftPayload != null ? aircraftPayload.hashCode() : 0);
        result = 31 * result + (aircraftSeatCapacity != null ? aircraftSeatCapacity.hashCode() : 0);
        result = 31 * result + (aFlightProperty != null ? aFlightProperty.hashCode() : 0);
        result = 31 * result + (speCarForDisabledCount != null ? speCarForDisabledCount.hashCode() : 0);
        result = 31 * result + (executeDay != null ? executeDay.hashCode() : 0);
        result = 31 * result + (childInternal != null ? childInternal.hashCode() : 0);
        result = 31 * result + (babyInternal != null ? babyInternal.hashCode() : 0);
        result = 31 * result + (babyInternational != null ? babyInternational.hashCode() : 0);
        result = 31 * result + (childInternational != null ? childInternational.hashCode() : 0);
        result = 31 * result + (viaAdult != null ? viaAdult.hashCode() : 0);
        result = 31 * result + (viaChild != null ? viaChild.hashCode() : 0);
        result = 31 * result + (viaBaby != null ? viaBaby.hashCode() : 0);
        result = 31 * result + (garbageTruckUseCount != null ? garbageTruckUseCount.hashCode() : 0);
        result = 31 * result + (waterTruckUseCount != null ? waterTruckUseCount.hashCode() : 0);
        result = 31 * result + (cesspoolageTruckUseCount != null ? cesspoolageTruckUseCount.hashCode() : 0);
        result = 31 * result + (flightPart2 != null ? flightPart2.hashCode() : 0);
        result = 31 * result + (luggageWeightInternal != null ? luggageWeightInternal.hashCode() : 0);
        result = 31 * result + (luggageWeightInternational != null ? luggageWeightInternational.hashCode() : 0);
        result = 31 * result + (flightPart0 != null ? flightPart0.hashCode() : 0);
        result = 31 * result + (linkFlightMateInfoId != null ? linkFlightMateInfoId.hashCode() : 0);
        result = 31 * result + (dFlightProperty != null ? dFlightProperty.hashCode() : 0);
        result = 31 * result + (dRegisteration != null ? dRegisteration.hashCode() : 0);
        result = 31 * result + (isHighDensity != null ? isHighDensity.hashCode() : 0);
        result = 31 * result + (isPackagingFacility != null ? isPackagingFacility.hashCode() : 0);
        result = 31 * result + (iataOriginAirport != null ? iataOriginAirport.hashCode() : 0);
        result = 31 * result + (iataDestinationAirport != null ? iataDestinationAirport.hashCode() : 0);
        result = 31 * result + (aircraftService != null ? aircraftService.hashCode() : 0);
        result = 31 * result + (stat != null ? stat.hashCode() : 0);
        result = 31 * result + (bridgeAirconditionUsedTime != null ? bridgeAirconditionUsedTime.hashCode() : 0);
        result = 31 * result + (bridgeElectricUsedTime != null ? bridgeElectricUsedTime.hashCode() : 0);
        result = 31 * result + (deicingTruckUsedTime != null ? deicingTruckUsedTime.hashCode() : 0);
        result = 31 * result + (scheduledTime != null ? scheduledTime.hashCode() : 0);
        result = 31 * result + (scheduledLandedTime != null ? scheduledLandedTime.hashCode() : 0);
        result = 31 * result + (scheduledTakeOffTime != null ? scheduledTakeOffTime.hashCode() : 0);
        result = 31 * result + (aStandFlag != null ? aStandFlag.hashCode() : 0);
        result = 31 * result + (dStandFlag != null ? dStandFlag.hashCode() : 0);
        result = 31 * result + (vipCount != null ? vipCount.hashCode() : 0);
        result = 31 * result + (firstClassCount != null ? firstClassCount.hashCode() : 0);
        result = 31 * result + (vipFirstAirdromeUsedCount != null ? vipFirstAirdromeUsedCount.hashCode() : 0);
        return result;
    }
}
