package com.nlia.fqdb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nlia.fqdb.entity.ChargeTerm;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.service.impl.ChargeTermManager;
import com.nlia.fqdb.service.impl.FlightMateInfoManager;
import com.nlia.fqdb.util.CommonData;

@Controller
@RequestMapping("feeTest")
public class FeeTestController {

	@Resource
	private ChargeTermManager chargeTermManager;
	
//	@Resource
//	private FlightMateManager flightMateManager;
	@Resource
    private FlightMateInfoManager flightMateInfoManager;
	
	@RequestMapping(value = "/{flightMateId}",method = RequestMethod.GET)
	public String findFeeByFlightMate(Model model, @PathVariable Long flightMateId,ServletRequest request){
		Map<String, Object> filters = new HashMap<>();
		//filters.put("flightMate.id_equal", flightMate);
		filters.put("flightMateInfo.id_equal", flightMateId);
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<String> sorters = new ArrayList<>();
        sorters.add("chargeSubject.description_asc");
		List<ChargeTerm> chargeTermsList = chargeTermManager.findBy(filters,sorters);
//		double totalFee = 0, totalTakeOff = 0, totalLand = 0, totalServiceFee = 0, totalBridge = 0, totalCheck = 0;
		String chargeTarget = "";
		if (chargeTermsList != null && chargeTermsList.size() > 0) {
			chargeTarget = chargeTermsList.get(0).getChargeTarget().getSubairline().getAirlineName();
		}
//		for (ChargeTerm chargeTerm : chargeTermsList) {
//			//起降费
//			if (chargeTerm.getChargeType().getId() == 1L) {
//				totalTakeOff += chargeTerm.getFee();
//			}
//			//停场费
//			else if(chargeTerm.getChargeType().getId() == 2L){
//				totalLand += chargeTerm.getFee();
//			}
//			//客桥费
//			else if(chargeTerm.getChargeType().getId() == 3L){
//				totalBridge += chargeTerm.getFee();
//			}
//			//旅客服务费
//			else if(chargeTerm.getChargeType().getId() == 4L){
//				totalServiceFee += chargeTerm.getFee();
//			}
//			//安检费-旅客行李 //安检-货物邮件
//			else if(chargeTerm.getChargeType().getId() == 5L||chargeTerm.getChargeType().getId() == 6L){
//                totalCheck += chargeTerm.getFee();
//            }
//		}
//		totalFee = totalBridge + totalCheck + totalLand + totalServiceFee + totalTakeOff;
		FlightMateInfo currentFlightMate = flightMateInfoManager.find(flightMateId);
		model.addAttribute("title", currentFlightMate.getAFlightIdentity() + "/" + currentFlightMate.getDFlightIdentity());
		model.addAttribute("arrival", currentFlightMate.getAFlightIdentity());
		model.addAttribute("departure", currentFlightMate.getDFlightIdentity());
		model.addAttribute("arrivalScheduleDate", currentFlightMate.getLandedTime().toString().substring(0, 10));
		model.addAttribute("departureScheduleDate", currentFlightMate.getTakeOffTime().toString().substring(0, 10));
		model.addAttribute("arrivalFlight","进港");
		model.addAttribute("departureFlight", "离港");
		model.addAttribute("arrivalServiceType", currentFlightMate.getAFlightProperty());
		model.addAttribute("departureServiceType", currentFlightMate.getDFlightProperty());
		model.addAttribute("aregisteration", currentFlightMate.getAregisteration());
		model.addAttribute("dregisteration", currentFlightMate.getDregisteration());
		model.addAttribute("arrivalAirlineICAOCode", currentFlightMate.getAFlightIdentity().substring(0, 2));
		model.addAttribute("departureAirlineICAOCode", currentFlightMate.getDFlightIdentity().substring(0, 2));
		model.addAttribute("arrivalLandTime", currentFlightMate.getLandedTime());
		model.addAttribute("departureTakeOffTime", currentFlightMate.getTakeOffTime());
		model.addAttribute("destinationAirport", currentFlightMate.getIataDestinationAirport());
		model.addAttribute("previousAirport", currentFlightMate.getIataOriginAirport());
		model.addAttribute("chargeTermsList", chargeTermsList);
//		model.addAttribute("totalTakeOff", totalTakeOff);
//		model.addAttribute("totalLand", totalLand);
//		model.addAttribute("totalServiceFee", totalServiceFee);
//		model.addAttribute("totalBridge", totalBridge);
//		model.addAttribute("totalCheck", totalCheck);
//		model.addAttribute("totalFee", totalFee);
		model.addAttribute("flightMateId", flightMateId);
		model.addAttribute("chargeTarget", chargeTarget);
		return "feeTest";
	}
	
//	@RequestMapping(value = "/otherfee/{flightMateId}",method = RequestMethod.GET)
//	public String findOtherFeeByFlightMate(Model model,ServletRequest request, @PathVariable Long flightMateId){
//		Map<String, Object> filters = new HashMap<>();
//		filters.put("flightMate.id_equal", flightMateId);
//		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
//		List<ChargeTerm> chargeTermsList = chargeTermManager.findBy(filters);
//		List<ChargeTerm> AgencyList = new ArrayList<>();
//		List<ChargeTerm> serviceList = new ArrayList<>();
//		List<ChargeTerm> passengerElevatorLoadingList = new ArrayList<>();
//		String chargeTarget = "";
//		if (chargeTermsList != null && chargeTermsList.size() > 0) {
//			chargeTarget = chargeTermsList.get(0).getChargeTarget().getSubairline().getAirlineName();
//		}
//		
//		double totalOther = 0, totalAgency = 0, totalCompound = 0, totalPassengerElevatorLoading = 0, totalService = 0, totalPostSerivce = 0, totalFlightDuty = 0;
//		for (ChargeTerm chargeTerm : chargeTermsList) {
//			//一般代理服务
//			if (chargeTerm.getChargeType().getId() == 11L) {
//				totalAgency += chargeTerm.getFee();
//				AgencyList.add(chargeTerm);
//			}
//			//配载、通信集装设备管理及旅客与行李服务
//			else if(chargeTerm.getChargeType().getId() == 12L){
//				totalCompound += chargeTerm.getFee();
//			}
//			//货物和邮件服务
//			else if(chargeTerm.getChargeType().getId() == 13L){
//				totalPostSerivce += chargeTerm.getFee();
//			}
//			//客梯、装卸和地面运输服务
//			else if(chargeTerm.getChargeType().getId() == 14L){
//			    totalPassengerElevatorLoading += chargeTerm.getFee();
//			    passengerElevatorLoadingList.add(chargeTerm);
//			}
//			//飞机服务费
//			else if(chargeTerm.getChargeType().getId() == 15L){
//				totalService += chargeTerm.getFee();
//				serviceList.add(chargeTerm);
//			}
//			//飞机勤务
//			else if(chargeTerm.getChargeType().getId() == 16L){
//				totalFlightDuty += chargeTerm.getFee();
//			}
//		}
//		
//		totalOther = totalAgency + totalCompound + totalService + totalPostSerivce + totalFlightDuty;
//		FlightMateInfo currentFlightMateInfo = flightMateInfoManager.find(flightMateId);
//		model.addAttribute("title", currentFlightMateInfo.getAFlightIdentity() + "/" + currentFlightMateInfo.getDFlightIdentity());
//        model.addAttribute("arrival", currentFlightMateInfo.getAFlightIdentity());
//        model.addAttribute("departure", currentFlightMateInfo.getDFlightIdentity());
//        model.addAttribute("arrivalScheduleDate", currentFlightMateInfo.getLandedTime().toString().substring(0, 10));
//        model.addAttribute("departureScheduleDate", currentFlightMateInfo.getTakeOffTime().toString().substring(0, 10));
//        model.addAttribute("arrivalFlight","进港");
//        model.addAttribute("departureFlight", "离港");
//        model.addAttribute("arrivalServiceType", currentFlightMateInfo.getAFlightProperty());
//        model.addAttribute("departureServiceType", currentFlightMateInfo.getDFlightProperty());
//        model.addAttribute("aregisteration", currentFlightMateInfo.getAregisteration());
//        model.addAttribute("dregisteration", currentFlightMateInfo.getDregisteration());
//        model.addAttribute("arrivalAirlineICAOCode", currentFlightMateInfo.getAFlightIdentity().substring(0, 2));
//        model.addAttribute("departureAirlineICAOCode", currentFlightMateInfo.getDFlightIdentity().substring(0, 2));
//		model.addAttribute("chargeTermsList", chargeTermsList);
//		model.addAttribute("flightMateId", flightMateId);
//		model.addAttribute("arrivalLandTime", currentFlightMateInfo.getLandedTime());
//        model.addAttribute("departureTakeOffTime", currentFlightMateInfo.getTakeOffTime());
//        model.addAttribute("destinationAirport", "");//
//        model.addAttribute("previousAirport", "");//
//		model.addAttribute("totalOther", totalOther);
//		model.addAttribute("totalAgency", totalAgency);
//		model.addAttribute("totalCompound", totalCompound);
//		model.addAttribute("totalService", totalService);
//		model.addAttribute("totalPostSerivce", totalPostSerivce);
//		model.addAttribute("totalFlightDuty", totalFlightDuty);
//		model.addAttribute("AgencyList", AgencyList);
//		model.addAttribute("serviceList", serviceList);
//		model.addAttribute("chargeTarget", chargeTarget);
//		model.addAttribute("totalPassengerElevatorLoading", totalPassengerElevatorLoading);
//		return "otherfee";
//	}
	
	@RequestMapping(value = "/save",method = RequestMethod.GET)
	public String saveFee(Model model,ServletRequest request){
		String flightMateId = request.getParameter("flightMateId");
		Map<String, Object> filters = new HashMap<>();
		filters.put("flightMateInfo.id_equal", flightMateId);
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<ChargeTerm> chargeTermsList = chargeTermManager.findBy(filters);
//		List<ChargeTerm> AgencyList = new ArrayList<>();
//		List<ChargeTerm> serviceList = new ArrayList<>();
		List<ChargeTerm> toSaveList = new ArrayList<>();
		for (ChargeTerm chargeTerm : chargeTermsList) {
			String feeText = request.getParameter("" + chargeTerm.getId());
			if (feeText != null && chargeTerm.getFee() != Double.valueOf(feeText)) {
				chargeTerm.setFee(Double.valueOf(feeText));
				toSaveList.add(chargeTerm);
			}
		}
		toSaveList = (List<ChargeTerm>) chargeTermManager.save(toSaveList);
		
//		double totalFee = 0, totalTakeOff = 0, totalLand = 0, totalServiceFee = 0, totalBridge = 0, totalCheck = 0;
//		double totalOther = 0, totalAgency = 0, totalCompound = 0, totalService = 0, totalPostSerivce = 0, totalFlightDuty = 0;
		String chargeTarget = "";
		if (chargeTermsList != null && chargeTermsList.size() > 0) {
			chargeTarget = chargeTermsList.get(0).getChargeTarget().getSubairline().getAirlineName();
		}
//		for (ChargeTerm chargeTerm : chargeTermsList) {
//			//起降费
//			if (chargeTerm.getChargeType().getId() == 1L) {
//				totalTakeOff += chargeTerm.getFee();
//			}
//			//停场费
//			else if(chargeTerm.getChargeType().getId() == 2L){
//				totalLand += chargeTerm.getFee();
//			}
//			//客桥费
//			else if(chargeTerm.getChargeType().getId() == 31L){
//				totalBridge += chargeTerm.getFee();
//			}
//			//旅客服务费
//			else if(chargeTerm.getChargeType().getId() == 4L){
//				totalService += chargeTerm.getFee();
//			}
//			//安检费-旅客行李 //安检-货物邮件
//			else if(chargeTerm.getChargeType().getId() == 5L||chargeTerm.getChargeType().getId() == 6L){
//                totalCheck += chargeTerm.getFee();
//            }
//			//一般代理服务
//			else if (chargeTerm.getChargeType().getId() == 11L) {
//				totalAgency += chargeTerm.getFee();
//				AgencyList.add(chargeTerm);
//			}
//			//配载、通信集装设备管理及旅客与行李服务
//			else if(chargeTerm.getChargeType().getId() == 12L){
//				totalCompound += chargeTerm.getFee();
//			}
//			//货物和邮件服务
//			else if(chargeTerm.getChargeType().getId() == 13L){
//				totalPostSerivce += chargeTerm.getFee();
//			}
//			//飞机服务费
//			else if(chargeTerm.getChargeType().getId() == 15L){
//				totalService += chargeTerm.getFee();
//				serviceList.add(chargeTerm);
//			}
//			//飞机勤务
//			else if(chargeTerm.getChargeType().getId() == 16L){
//				totalFlightDuty += chargeTerm.getFee();
//			}
//		}
//		totalFee = totalBridge + totalCheck + totalLand + totalServiceFee + totalTakeOff;
//		totalOther = totalAgency + totalCompound + totalService + totalPostSerivce + totalFlightDuty;
		
		FlightMateInfo currentFlightMateInfo = flightMateInfoManager.find(Long.valueOf(flightMateId));
		model.addAttribute("title", currentFlightMateInfo.getAFlightIdentity() + "/" + currentFlightMateInfo.getDFlightIdentity());
        model.addAttribute("arrival", currentFlightMateInfo.getAFlightIdentity());
        model.addAttribute("departure", currentFlightMateInfo.getDFlightIdentity());
        model.addAttribute("arrivalScheduleDate", currentFlightMateInfo.getLandedTime().toString().substring(0, 10));
        model.addAttribute("departureScheduleDate", currentFlightMateInfo.getTakeOffTime().toString().substring(0, 10));
        model.addAttribute("arrivalFlight","进港");
        model.addAttribute("departureFlight", "离港");
        model.addAttribute("arrivalServiceType", currentFlightMateInfo.getAFlightProperty());
        model.addAttribute("departureServiceType", currentFlightMateInfo.getDFlightProperty());
        model.addAttribute("aregisteration", currentFlightMateInfo.getAregisteration());
        model.addAttribute("dregisteration", currentFlightMateInfo.getDregisteration());
        model.addAttribute("arrivalAirlineICAOCode", currentFlightMateInfo.getAFlightIdentity().substring(0, 2));
        model.addAttribute("departureAirlineICAOCode", currentFlightMateInfo.getDFlightIdentity().substring(0, 2));
        model.addAttribute("arrivalLandTime", currentFlightMateInfo.getLandedTime());
        model.addAttribute("departureTakeOffTime", currentFlightMateInfo.getTakeOffTime());
        model.addAttribute("destinationAirport", currentFlightMateInfo.getIataDestinationAirport());//
        model.addAttribute("previousAirport", currentFlightMateInfo.getIataOriginAirport());//
		model.addAttribute("chargeTermsList", chargeTermsList);
//		model.addAttribute("totalTakeOff", totalTakeOff);
//		model.addAttribute("totalLand", totalLand);
//		model.addAttribute("totalServiceFee", totalServiceFee);
//		model.addAttribute("totalBridge", totalBridge);
//		model.addAttribute("totalCheck", totalCheck);
//		model.addAttribute("totalFee", totalFee);
		model.addAttribute("flightMateId", flightMateId);
		model.addAttribute("chargeTarget", chargeTarget);

//		model.addAttribute("totalOther", totalOther);
//		model.addAttribute("totalAgency", totalAgency);
//		model.addAttribute("totalCompound", totalCompound);
//		model.addAttribute("totalService", totalService);
//		model.addAttribute("totalPostSerivce", totalPostSerivce);
//		model.addAttribute("totalFlightDuty", totalFlightDuty);
//		model.addAttribute("AgencyList", AgencyList);
//		model.addAttribute("serviceList", serviceList);
//		if (request.getParameter("first").equals("first")) {
//			return "feeTest";
//		}else if(request.getParameter("first").equals("second")){
//			return "otherfee";
//		}
		return "feeTest";
	}
	
//	private String flightDirection(String direction){
//		 if(direction.equals("ARRIVAL")){
//	    	  return "进港";
//	      }else if(direction.equals("DEPARTURE")){
//	    	  return "离港";
//	      }else{
//	    	  return "";
//	      }
//	}
}
