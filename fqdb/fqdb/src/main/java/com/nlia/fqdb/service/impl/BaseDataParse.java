package com.nlia.fqdb.service.impl;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.nlia.fqdb.service.IBaseDataParse;

@Service
public class BaseDataParse implements IBaseDataParse {

	@Resource
	TerminalResolve terminalResolve;
	
	@Resource
	RunwayResolve runwayResolve;
	
	@Resource
	StandResolve standResolve;
	
	@Resource
	CheckInDeskResolve checkInDeskResolve;
	
	@Resource
	BaggageMakeupResolve baggageMakeupResolve;
	
	@Resource
	GateResolve gateResolve;
	
	@Resource
	BaggageReclaimResolve baggageReclaimResolve;
	
	@Resource
	AirlineResolve airlineResolve;
	
	@Resource
	HandlerResolve handlerResolve;
	
	@Resource
	AircraftResolve aircraftResolve;
	
	@Resource
	AircraftTypeResolve aircraftTypeResolve;
	
	@Resource
	AirportResolve airportResolve;
	
	@Resource
	DelayCodeResolve delayCodeResolve;
	
	@Resource
	PassengerClassResolve passengerClassResolve;
	
	@Resource
	FlightOperationCodeResolve flightOperationCodeResolve;
	
	@Resource
	FlightServiceTypeResolve flightServiceTypeResolve;
	
	@Resource
	CountryResolve countryResolve;
	
//	@Resource
//	CommonCheckInDeskResolve commonCheckInDeskResolve;

	public String changeParseOfBaseData(String xmlString) {
		
		String backMsg = "";
		Document doc;
		try {
			doc = DocumentHelper.parseText(xmlString);
			Node bssRoot = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/BasicDataKey/BasicDataCategory");
			Node rssRoot = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/ResourceKey/ResourceCategory");
			if(bssRoot != null){
				if("Terminal".equals(bssRoot.getText())) {
					backMsg = terminalResolve.parseTerminalDataOfXmlString(xmlString);
				}
				if("Runway".equals(bssRoot.getText())) {
					backMsg = runwayResolve.parseRunwayDataOfXmlString(xmlString);
				}
				if("Stand".equals(bssRoot.getText())) {
					backMsg = standResolve.parseStandDataOfXmlString(xmlString);
				}
				if("CheckInDesk".equals(bssRoot.getText())) {
					backMsg = checkInDeskResolve.parseCheckInDeskDataOfXmlString(xmlString);
				}
				if("BaggageMakeup".equals(bssRoot.getText())) {
					backMsg = baggageMakeupResolve.parseBaggageMakeupDataOfXmlString(xmlString);
				}
				if("Gate".equals(bssRoot.getText())) {
					backMsg = gateResolve.parseGateDataOfXmlString(xmlString);
				}
				if("BaggageReclaim".equals(bssRoot.getText())) {
					backMsg = baggageReclaimResolve.parseBaggageReclaimDataOfXmlString(xmlString);
				}
				if("Airline".equals(bssRoot.getText())) {
//					backMsg = airlineResolve.parseAirlineDataOfXmlString(xmlString);
					//lushuaifeng 20161010 //默认失败，以便于以后解析
				    backMsg= "failure:2:no";
				}
				if("Handler".equals(bssRoot.getText())) {
					backMsg = handlerResolve.parseHandlerDataOfXmlString(xmlString);
				}
				if("Aircraft".equals(bssRoot.getText())) {
					backMsg = aircraftResolve.parseAircraftDataOfXmlString(xmlString);
				}
				if("AircraftType".equals(bssRoot.getText())) {
					backMsg = aircraftTypeResolve.parseAircraftTypeDataOfXmlString(xmlString);
				}
				if("Airport".equals(bssRoot.getText())) {
					backMsg = airportResolve.parseAirportDataOfXmlString(xmlString);
				}
				if("DelayCode".equals(bssRoot.getText())) {
					backMsg = delayCodeResolve.parseDelayCodeDataOfXmlString(xmlString);
				}
				if("PassengerClass".equals(bssRoot.getText())) {
					backMsg = passengerClassResolve.parsePassengerClassDataOfXmlString(xmlString);
				}
				if("FlightOperationCode".equals(bssRoot.getText())) {
					backMsg = flightOperationCodeResolve.parseFlightOperationCodeDataOfXmlString(xmlString);
				}
				if("FlightServiceType".equals(bssRoot.getText())) {
					backMsg = flightServiceTypeResolve.parseFlightServiceTypeDataOfXmlString(xmlString);
				}
				if("Country".equals(bssRoot.getText())) {
					backMsg = countryResolve.parseCountryDataOfXmlString(xmlString);
				}
			}
//			else if (rssRoot != null){
//				/**
//				 * RSS
//				 */
//				if("CommonCheckInDesk".equals(rssRoot.getText())) {
//					backMsg = commonCheckInDeskResolve.parseCommonCheckInDeskDataOfXmlString(xmlString);
//				}
//			}
			
		} catch (DocumentException e) {
		    System.out.printf("-------------------------------------------------------\n" + xmlString + "\n" + backMsg + "\n-------------------------------------------------------\n" );
			e.printStackTrace();
		}
		return backMsg;
	}
}
