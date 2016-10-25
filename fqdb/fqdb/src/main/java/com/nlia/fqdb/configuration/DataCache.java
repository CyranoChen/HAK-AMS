package com.nlia.fqdb.configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.entity.FlightView;
import com.nlia.fqdb.util.DateUtils;

@Service
public class DataCache implements IDataCache {

	private static List<FlightBase> flightBases = new ArrayList<>();

	public static List<FlightBase> getFlightBases() {
		return flightBases;
	}

	public static void setFlightBases(List<FlightBase> flightBases) {
		DataCache.flightBases = flightBases;
	}

	public void setFlightBasesWithFlightView(List<FlightView> flightViewList) {
		setFlightBases(convertFlightView(flightViewList));
	}

	/**
	 * 把flightview转换成内存数据（一个list的flightBase）
	 * 
	 * @param viewList
	 * @return
	 */
	public List<FlightBase> convertFlightView(List<FlightView> viewList) {
		List<FlightBase> resultList = new ArrayList<FlightBase>();
		Map<Long, FlightBase> indexMap = new HashMap<Long, FlightBase>();
//		BigDecimal[] flightIdsHasTask = taskManager.getFlightBaseIdHasTask(startTime, endTime);
		
		for (FlightView flightView : viewList) {
			FlightBase fb = new FlightBase();
			fb.setId(flightView.getId());
			fb.setFlightScheduledDate(flightView.getFlightScheduledDate());
			fb.setFlightIdentity(flightView.getFlightIdentity());
			fb.setFlightDirection(flightView.getFlightDirection());
			fb.setFlightNumber(flightView.getFlightNumber());
			fb.setAirportIATACode(flightView.getAirportIATACode());
			fb.setAirportICAOCode(flightView.getAirportICAOCode());
			fb.setDepartureAirport(flightView.getDepartureAirport());
			fb.setDestinationAirport(flightView.getDestinationAirport());
			fb.setAirlineIATACode(flightView.getAirlineIATACode());
			fb.setAirlineICAOCode(flightView.getAirlineICAOCode());
			fb.setFlightSuffix(flightView.getFlightSuffix());
			fb.setIsMasterFlight(flightView.getIsMasterFlight());
			fb.setFlightProperty(flightView.getFlightProperty());
			fb.setEntryExit(flightView.getEntryExit());
			fb.setLinkFlightSign(flightView.getLinkFlightSign());
			fb.setIsLock(flightView.getIsLock());
			fb.setRemoveFlag(flightView.getRemoveFlag());
			//初始化航班业务时间，进离港分开，进港取( 预计降落 == null)?(计划降落):(预计降落).离港取(预计起飞 == null)?(计划起飞):(预计起飞)
			fb.setFlightTime(initFlightTime(flightView));
		
			//共享航班
			fb.setSharedFlightIdentity(flightView.getSharedFlightIdentity());
			
			FlightData fd = new FlightData();
			if (flightView.getFlightDataId() != null) {
				//add by Ninja
				fd.setFlightDirection(flightView.getFlightDirection());
				fd.setId(flightView.getFlightDataId());
				fd.setFlightScheduledDateTime(flightView.getFlightScheduledDateTime());
				fd.setRegisteration(flightView.getRegisteration());
				fd.setCallSign(flightView.getCallSign());
				fd.setFlightProperty(flightView.getFlightProperty());
				fd.setTransportProperty(flightView.getTransportProperty());
				fd.setAircraftIATACode(flightView.getAircraftIATACode());
				fd.setAircraftICAOCode(flightView.getAircraftICAOCode());
				fd.setFlightCategory(flightView.getFlightCategory());
				fd.setFlightServiceType(flightView.getFlightServiceType());
				fd.setIATARouteAirport(flightView.getIATARouteAirport());
				fd.setIATAOriginAirport(flightView.getIATAOriginAirport());
				fd.setIATADestinationAirport(flightView.getIATADestinationAirport());
				fd.setIATAViaAirport(flightView.getIATAViaAirport());
				fd.setICAORouteAirport(flightView.getICAORouteAirport());
				fd.setICAOOriginAirport(flightView.getICAOOriginAirport());
				fd.setICAOViaAirport(flightView.getICAOViaAirport());
				fd.setICAODestinationAirport(flightView.getICAODestinationAirport());
				fd.setAirportIATACode(flightView.getAirportIATACode());
				fd.setAirportICAOCode(flightView.getAirportICAOCode());
				fd.setFlightCountryType(flightView.getFlightCountryType());
				fd.setScheduledPreviousAirportDepartureDateTime(flightView.getScheduledPreviousAirportDepartureDateTime());
				fd.setEstimatedPreviousAirportDepartureDateTime(flightView.getEstimatedPreviousAirportDepartureDateTime());
				fd.setActualPreviousAirportDepartureDateTime(flightView.getActualPreviousAirportDepartureDateTime());
				fd.setTenMilesDateTime(flightView.getTenMilesDateTime());
				fd.setScheduledLandingDateTime(flightView.getScheduledLandingDateTime());
				fd.setEstimatedLandingDateTime(flightView.getEstimatedLandingDateTime());
				fd.setActualLandingDateTime(flightView.getActualLandingDateTime());
				fd.setScheduledOnBlockDateTime(flightView.getScheduledOnBlockDateTime());
				fd.setEstimatedOnBlockDateTime(flightView.getEstimatedOnBlockDateTime());
				fd.setActualOnBlockDateTime(flightView.getActualOnBlockDateTime());
				fd.setScheduledOffBlockDateTime(flightView.getScheduledOffBlockDateTime());
				fd.setEstimatedOffBlockDateTime(flightView.getEstimatedOffBlockDateTime());
				fd.setActualOffBlockDateTime(flightView.getActualOffBlockDateTime());
				fd.setScheduledTakeOffDateTime(flightView.getScheduledTakeOffDateTime());
				fd.setEstimatedTakeOffDateTime(flightView.getEstimatedTakeOffDateTime());
				fd.setActualTakeOffDateTime(flightView.getActualTakeOffDateTime());
				fd.setScheduledNextAirportArrivalDateTime(flightView.getScheduledNextAirportArrivalDateTime());
				fd.setEstimatedNextAirportArrivalDateTime(flightView.getEstimatedNextAirportArrivalDateTime());
				fd.setActualNextAirportArrivalDateTime(flightView.getActualNextAirportArrivalDateTime());
				fd.setBestKnownDateTime(flightView.getBestKnownDateTime());
				fd.setOperationStatus(flightView.getOperationStatus());
				fd.setFlightStatus(flightView.getFlightStatus());
				fd.setDelayCode(flightView.getDelayCode());
				fd.setDelayFreeText(flightView.getDelayFreeText());
				fd.setDiversionAirportIATACode(flightView.getDiversionAirportIATACode());
				fd.setDiversionAirportICAOCode(flightView.getDiversionAirportICAOCode());
				fd.setChangeLandingAirportIATACode(flightView.getChangeLandingAirportIATACode());
				fd.setChangeLandingAirportICAOCode(flightView.getChangeLandingAirportICAOCode());
				fd.setDisplayCode(flightView.getDisplayCode());
				fd.setIsTransitFlight(flightView.getIsTransitFlight());
				fd.setIsOverNightFlight(flightView.getIsOverNightFlight());
				fd.setIsCashFlight(flightView.getIsCashFlight());
				fd.setPayloadWeight(flightView.getPayloadWeight());
				fd.setPassengerSeatingCapacity(flightView.getPassengerSeatingCapacity());
				fd.setMaximumTakeoffWeight(flightView.getMaximumTakeoffWeight());
				fd.setCrewNumber(flightView.getCrewNumber());
				fd.setTotalPassengersNumber(flightView.getTotalPassengersNumber());
				fd.setInternationalPassengersNumber(flightView.getInternationalPassengersNumber());
				fd.setDomesticPassengersNumber(flightView.getDomesticPassengersNumber());
				fd.setTransitPassengersNumber(flightView.getTransitPassengersNumber());
				fd.setTotalChildrenNumber(flightView.getTotalChildrenNumber());
				fd.setRequireAssitancePassengersNumber(flightView.getRequireAssitancePassengersNumber());
				fd.setTotallBaggageWeight(flightView.getTotallBaggageWeight());
				fd.setInternationalBaggageWeight(flightView.getInternationalBaggageWeight());
				fd.setDomesticBaggageWeight(flightView.getDomesticBaggageWeight());
				fd.setTransitlBaggageWeight(flightView.getTransitlBaggageWeight());
				fd.setTotalCargoWeight(flightView.getTotalCargoWeight());
				fd.setInternationalCargoWeight(flightView.getInternationalCargoWeight());
				fd.setDomesticCargoWeight(flightView.getDomesticCargoWeight());
				fd.setTransitCargoWeight(flightView.getTransitCargoWeight());
				fd.setTotallMailWeight(flightView.getTotallMailWeight());
				fd.setInternationalMailWeight(flightView.getInternationalMailWeight());
				fd.setDomesticMailWeight(flightView.getDomesticMailWeight());
				fd.setTransitlMailWeight(flightView.getTransitlMailWeight());
				//add by march start 增加下站机场IATA、前站机场IATA
                fd.setIATANextAirport(flightView.getIATANextAirport());
                fd.setIATAPreviousAirport(flightView.getIATAPreviousAirport());
                fd.setTotalAdultPassengersNumber(flightView.getTotalAdultPassengersNumber());
                fd.setTotalInfantPassengersNumber(flightView.getTotalInfantPassengersNumber());
                fd.setTransitAdultPassgerNum(flightView.getTransitAdultPassgerNum());
                fd.setTransitChildPassgerNum(flightView.getTransitChildPassgerNum());
                fd.setTransitInfantPassgerNum(flightView.getTransitInfantPassgerNum());
                //add by march end
                
                /*
                 * add by march 20151124 因HAK而修改
                 */
                fd.setFinalApproachTime(flightView.getFinalApproachTime());
                fd.setFlightCAACServiceType(flightView.getFlightCAACServiceType());
                fd.setFlightIATAServiceType(flightView.getFlightIATAServiceType());
                fd.setFullRouteAirportIATACode(flightView.getFullRouteAirportIATACode());
                fd.setFullRouteAirportICAOCode(flightView.getFullRouteAirportICAOCode());
                /*
                 * add by march 20151124 因HAK而修改
                 */
			} else {
				fd = null;
			}

			FlightResource fr = new FlightResource();
			if (flightView.getFlightResourceId() != null) {
				fr.setId(flightView.getFlightResourceId());
				fr.setFlightTerminalID(flightView.getFlightTerminalID());
				fr.setAircraftTerminalID(flightView.getAircraftTerminalID());
				fr.setRunwayID(flightView.getRunwayID());
				fr.setStandID(flightView.getStandID());
				fr.setScheduledStandStartDateTime(flightView.getScheduledStandStartDateTime());
				fr.setScheduledStandEndDateTime(flightView.getScheduledStandEndDateTime());
				fr.setEstimatedStandStartDateTime(flightView.getEstimatedStandStartDateTime());
				fr.setEstimcatedStandEndDateTime(flightView.getEstimcatedStandEndDateTime());
				fr.setActualStandStartDateTime(flightView.getActualStandStartDateTime());
				fr.setActualStandEndDateTime(flightView.getActualStandEndDateTime());
				fr.setBaggageBeltID(flightView.getBaggageBeltID());
				fr.setBaggageBeltStatus(flightView.getBaggageBeltStatus());
				fr.setScheduledMakeupStartDateTime(flightView.getScheduledMakeupStartDateTime());
				fr.setScheduledMakeupEndDateTime(flightView.getScheduledMakeupEndDateTime());
				fr.setActualMakeupStartDateTime(flightView.getActualMakeupStartDateTime());
				fr.setActualMakeupEndDateTime(flightView.getActualMakeupEndDateTime());
				fr.setGateID(flightView.getGateID());
				fr.setGateStatus(flightView.getGateStatus());
				fr.setScheduledGateStartDateTime(flightView.getScheduledGateStartDateTime());
				fr.setScheduledGateEndDateTime(flightView.getScheduledGateEndDateTime());
				fr.setActualGateStartDateTime(flightView.getActualGateStartDateTime());
				fr.setActualGateEndDateTime(flightView.getActualGateEndDateTime());
				fr.setBaggageReclaimID(flightView.getBaggageReclaimID());
				fr.setScheduledFirstBaggageDateTime(flightView.getScheduledFirstBaggageDateTime());
				fr.setScheduledLastBaggageDateTime(flightView.getScheduledLastBaggageDateTime());
				fr.setActualFirstBaggageDateTime(flightView.getActualFirstBaggageDateTime());
				fr.setActualLastBaggageDateTime(flightView.getActualLastBaggageDateTime());
				fr.setOverViewCheckInRange(flightView.getOverViewCheckInRange());
				fr.setOverViewCheckInType(flightView.getOverViewCheckInType());
				fr.setScheduledCheckInFirstBeginDateTime(flightView.getScheduledCheckInFirstBeginDateTime());
				fr.setScheduledCheckInLastEndDateTime(flightView.getScheduledCheckInLastEndDateTime());
				fr.setActualCheckInFirstBeginDateTime(flightView.getActualCheckInFirstBeginDateTime());
				fr.setAcutalCheckInLastEndDateTime(flightView.getAcutalCheckInLastEndDateTime());
				fr.setCheckInID(flightView.getCheckInID());
				fr.setCheckInType(flightView.getCheckInType());
				fr.setCheckInStatus(flightView.getCheckInStatus());
				fr.setCheckInClassService(flightView.getCheckInClassService());
				fr.setScheduledCheckInBeginDateTime(flightView.getScheduledCheckInBeginDateTime());
				fr.setScheduledCheckInEndDateTime(flightView.getScheduledCheckInEndDateTime());
				fr.setActualCheckInBeginDateTime(flightView.getActualCheckInBeginDateTime());
				fr.setAcutalCheckInEndDateTime(flightView.getAcutalCheckInEndDateTime());
				fr.setClosingScheduledFromDateTime(flightView.getClosingScheduledFromDateTime());
				fr.setClosingScheduledEndDateTime(flightView.getClosingScheduledEndDateTime());
				fr.setClosingActualEndDateTime(flightView.getClosingActualEndDateTime());
				fr.setCommentFreeText(flightView.getCommentFreeText());
				fr.setPreviousgateID(flightView.getPreviousgateID());
				fr.setPreviousStandID(flightView.getPreviousStandID());
			} else {
				fr = null;
			}
/*
			FlightServiceInfo fsi = new FlightServiceInfo();
			if (flightView.getFsiId() != null) {
				fsi.setId(flightView.getFsiId());
				fsi.setBridgeAirconditionStartTime(flightView.getBridgeAirconditionStartTime());
				fsi.setBridgeAirconditionEndTime(flightView.getBridgeAirconditionEndTime());
				fsi.setBridge400PowerStartTime(flightView.getBridge400PowerStartTime());
				fsi.setBridge400PowerEndTime(flightView.getBridge400PowerEndTime());
				fsi.setRoutineCheckCount(flightView.getRoutineCheckCount());
				fsi.setFlightNumber(flightView.getFsiFlightNumber());
				fsi.setScheduleDate(flightView.getScheduleDate());
				fsi.setFlightDirection(flightView.getFsiFlightDirection());
				fsi.setPassengerBridgeNumber(flightView.getPassengerBridgeNumber());
				fsi.setPassengerCarUsedStartTime(flightView.getPassengerCarUsedStartTime());
				fsi.setPassengerCarUsedEndTime(flightView.getPassengerCarUsedEndTime());
				fsi.setAirdromeCountUsedByPassenger(flightView.getAirdromeCountUsedByPassenger());
				fsi.setAirdromeCountUsedByCrew(flightView.getAirdromeCountUsedByCrew());
				fsi.setLiftingPlatformCarUsedStartTime(flightView.getLiftingPlatformCarUsedStartTime());
				fsi.setLiftingPlatformCarUsedEndTime(flightView.getLiftingPlatformCarUsedEndTime());
				fsi.setSpecialVehiclesCountForDisabled(flightView.getSpecialVehiclesCountForDisabled());
				fsi.setRoutineCheckStartTime(flightView.getRoutineCheckStartTime());
				fsi.setRoutineCheckEndTime(flightView.getRoutineCheckEndTime());
				fsi.setLeadCarUsedCount(flightView.getLeadCarUsedCount());
				fsi.setAirSourceTruckUsedStartTime(flightView.getAirSourceTruckUsedStartTime());
				fsi.setAirSourceTruckUsedEndTime(flightView.getAirSourceTruckUsedEndTime());
				fsi.setElectricSourceTruckUsedStartTime(flightView.getElectricSourceTruckUsedStartTime());
				fsi.setElectricSourceTruckUsedEndTime(flightView.getElectricSourceTruckUsedEndTime());
				fsi.setDeicingVehicleUsedStartTime(flightView.getDeicingVehicleUsedStartTime());
				fsi.setDeicingVehicleUsedEndTime(flightView.getDeicingVehicleUsedEndTime());
				fsi.setAirconditioningVehicleUsedStartTime(flightView.getAirconditioningVehicleUsedStartTime());
				fsi.setAirconditioningVehicleUsedEndTime(flightView.getAirconditioningVehicleUsedEndTime());
				fsi.setTractorsUsedCount(flightView.getTractorsUsedCount());
				fsi.setLandedTime(flightView.getLandedTime());
				fsi.setTakeOffTime(flightView.getTakeOffTime());
				fsi.setPassengerBridgeServicedStartTime(flightView.getPassengerBridgeServicedStartTime());
				fsi.setPassengerBridgeServicedEndTime(flightView.getPassengerBridgeServicedEndTime());
				fsi.setIsServiceProvidedForPassengers(flightView.getIsServiceProvidedForPassengers());
				fsi.setIsServiceProvidedForSecurity(flightView.getIsServiceProvidedForSecurity());
				fsi.setRoutinePermit(flightView.getRoutinePermit());
				fsi.setAircraftService(flightView.getAircraftService());
				fsi.setRemk(flightView.getRemk());
				fsi.setComfirmToSign(flightView.getComfirmToSign());
				fsi.setTerminalPassengerNumber(flightView.getTerminalPassengerNumber());
				
				fsi.setRemoveFlag(flightView.getFsiRemoveFlag());
				fsi.setCreateUser(flightView.getCreateUser());
				fsi.setCreateTime(flightView.getCreateTime());
				fsi.setModifyTime(flightView.getModifyTime());
				fsi.setModifyUser(flightView.getModifyUser());
				if(flightView.getServicePlanStartTime() != null || flightView.getServicePlanEndTime() != null) {
					fsi.setServicePlanStartTime(flightView.getServicePlanStartTime() );
					fsi.setServicePlanEndTime(flightView.getServicePlanEndTime() );
				}
				fsi.setLocked(flightView.isLocked());
				//add by march 2010505 增加 垃圾车、清水车、污水车使用次数 start
				fsi.setGarbageTruckUsedCount(flightView.getGarbageTruckUsedCount());
				fsi.setWaterTruckUsedCount(flightView.getWaterTruckUsedCount());
				fsi.setCesspoolageTruckUsedCount(flightView.getCesspoolageTruckUsedCount());
				//add by march 2010505 增加 垃圾车、清水车、污水车使用次数 end
				fsi.setFlightBase(fb);
				//打印标志
				fsi.setPrinted(flightView.isPrinted());
				//打印标志
			} else {
				fsi = null;
			}
*/
			fb.setFlightData(fd);
			fb.setFlightResource(fr);
//			fb.setFlightServiceInfo(fsi);

			indexMap.put(fb.getId(), fb);
		}

		for (FlightView flightView : viewList) {
			FlightBase fb = indexMap.get(flightView.getId());
			if (flightView.getLinkFlightId() != null && flightView.getLinkFlightId() != 0l) {
				fb.setLinkFlight(indexMap.get(flightView.getLinkFlightId()));
			}
			resultList.add(fb);
		}
		return resultList;
	}

	/**
	 * 
	 * @param flightView
	 * @return
	 */
	private static Date initFlightTime(FlightView flightView) {
		//设置航班业务时间（进港航班取落地时间，离港航班取起飞时间）
		Date _date = new Date();

		//如果是进港航班
		if (flightView.getFlightDirection() != null && FlightBase.TYPE.INTO_PORT.getValue().equals(flightView.getFlightDirection())) {
			if(flightView.getEstimatedLandingDateTime() != null){
				_date = flightView.getEstimatedLandingDateTime();
			}else if (flightView.getScheduledLandingDateTime() != null){
				_date = flightView.getScheduledLandingDateTime();
			}else{
				_date = DateUtils.getFirstMinuteOfOneday(flightView.getFlightScheduledDate());
			}
		}
		//如果是离港航班
		else if(flightView.getFlightDirection() != null && FlightBase.TYPE.OUT_PORT.getValue().equals(flightView.getFlightDirection())) {
			if(flightView.getEstimatedTakeOffDateTime() != null){
				_date = flightView.getEstimatedTakeOffDateTime();
			}else if (flightView.getScheduledTakeOffDateTime() != null){
				_date = flightView.getScheduledTakeOffDateTime();
			}else{
				_date = DateUtils.getFirstMinuteOfOneday(flightView.getFlightScheduledDate());
			}
		}else{
			_date = null;
		}
		
		return _date;
	}
	
	/**
	 * 找出FlightBase，如果未命中则返回null
	 * @param FlightBaseId
	 * @return
	 */
	public static FlightBase findFlightBase(long flightBaseId) {
		for(FlightBase fb : getFlightBases()){
			if(fb.getId() == flightBaseId)
				return fb;
		}
		
		return null;
	}
	
	/**
	 * 新增航班
	 * 新增成功返回true;
	 * @param flightBase
	 * @return
	 */
	public static boolean insertFlightBase(FlightBase flightBase) {
		boolean isExist = false;
		if(findFlightBase(flightBase.getId()) != null)
			isExist = true;
		
		if(isExist)
			return false;
		else{
			getFlightBases().add(flightBase);
			return true;
		}
	}
	
	/**
	 * 修改航班
	 * 修改成功返回true;
	 * @param flightBase
	 * @return
	 */
	public static boolean modifyFlightBase(FlightBase flightBase) {
		FlightBase source = findFlightBase(flightBase.getId());
		
		if(source == null)
			return false;
		else{
			BeanUtils.copyProperties(source, flightBase);
			return true;
		}
	}
	
	
	/**
	 * 删除航班，依赖于FlightBase的equals()
	 * 删除成功则返回true
	 * @param flightBase
	 * @return
	 */
	public static boolean removeFlightBase(FlightBase flightBase){
		List<FlightBase> flights = getFlightBases();
		return flights.remove(flightBase);
	}
	
}