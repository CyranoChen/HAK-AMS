package com.tsystems.msgCenter.controller;

import java.util.ArrayList;
import java.util.List;

import com.tsystems.aviation.imf.client.commons.ImfServiceType;
import com.tsystems.aviation.imf.client.exception.ImfClientConnectionException;
import com.tsystems.aviation.imf.client.exception.ImfClientInvalidServiceTypeException;
import com.tsystems.aviation.imf.client.exception.ImfServerUnknowException;
import com.tsystems.aviation.imf.client.message.ImfMessageListener;
import com.tsystems.aviation.imf.client.subsystem.ImfRsClient;

/*
 * Copyright 2013 T-systems.com All right reserved. This software is the
 * confidential and proprietary information of T-systems.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with T-systems.com.
 */
/**
 * class XUSTest.java's description on implement: TODO description on implement 
 *
 * @author cchen2 2013-4-24 上午11:03:58
 */
public class XRSTest {

    static ImfRsClient        rsclient = null;

    static ImfMessageListener listener = new ImfMessageListener() {

                                           @Override
                                           public void handleMessage(String msg) {
                                               System.out.println("received message from IMF " + msg);

                                               List<String> messages = new ArrayList<String>();

                                               String us = "<?xml version='1.0' encoding='UTF-8'?><IMFRoot xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:noNamespaceSchemaLocation='../OverView.xsd'><SysInfo><MessageSequenceID>1</MessageSequenceID><MessageType>FlightData</MessageType><ServiceType>FUS</ServiceType><OperationMode>NEW</OperationMode><SendDateTime>2012-07-01T13:15:00</SendDateTime><CreateDateTime>2012-07-01T13:15:00</CreateDateTime><OriginalDateTime>2012-07-01T13:14:00</OriginalDateTime><Receiver>IMF</Receiver><Sender>ORMS</Sender><Owner>ORMS</Owner><Priority>5</Priority></SysInfo><Data><PrimaryKey><FlightKey><FlightScheduledDate>2012-07-01</FlightScheduledDate><FlightIdentity>CA1234</FlightIdentity><FlightDirection>A</FlightDirection><BaseAirport><AirportIATACode>NKG</AirportIATACode></BaseAirport><DetailedIdentity><AirlineIATACode>CA</AirlineIATACode><AirlineICAOCode>CCA</AirlineICAOCode><FlightNumber>1234</FlightNumber></DetailedIdentity></FlightKey></PrimaryKey><FlightData><General><FlightScheduledDateTime>2012-07-01T20:15:00</FlightScheduledDateTime><Registration>B222</Registration><CallSign>DLH1234</CallSign><AircraftType><AircraftIATACode>737</AircraftIATACode><AircraftICAOCode>B737</AircraftICAOCode></AircraftType><FlightServiceType>REG</FlightServiceType><FlightRoute><IATARoute><IATAOriginAirport>PEK</IATAOriginAirport><IATAPreviousAirport>PEK</IATAPreviousAirport><IATAViaAirport>SHA</IATAViaAirport></IATARoute></FlightRoute><FlightCountryType>D</FlightCountryType><IsMasterFlight>Y</IsMasterFlight><MasterOrSlaveFlight><FlightIdentity>MU1234</FlightIdentity><FlightScheduledDate>2012-07-01</FlightScheduledDate><FlightDirection>A</FlightDirection></MasterOrSlaveFlight></General><OperationalDateTime><PreviousAirportDepartureDateTime><ScheduledPreviousAirportDepartureDateTime>2012-07-01T18:00:00</ScheduledPreviousAirportDepartureDateTime><EstimatedPreviousAirportDepartureDateTime>2012-07-01T18:00:00</EstimatedPreviousAirportDepartureDateTime><ActualPreviousAirportDepartureDateTime>2012-07-01T18:00:00</ActualPreviousAirportDepartureDateTime></PreviousAirportDepartureDateTime><TenMilesDateTime>2012-07-01T20:00:00</TenMilesDateTime><LandingDateTime><ScheduledLandingDateTime>2012-07-01T20:15:00</ScheduledLandingDateTime><EstimatedLandingDateTime>2012-07-01T20:15:00</EstimatedLandingDateTime><ActualLandingDateTime>2012-07-01T20:15:00</ActualLandingDateTime></LandingDateTime><OnBlockDateTime><ScheduledOnBlockDateTime>2012-07-01T20:20:00</ScheduledOnBlockDateTime><EstimatedOnBlockDateTime>2012-07-01T20:20:00</EstimatedOnBlockDateTime><ActualOnBlockDateTime>2012-07-01T20:20:00</ActualOnBlockDateTime></OnBlockDateTime><OffBlockDateTime><ScheduledOffBlockDateTime xsi:nil='true'/><EstimatedOffBlockDateTime xsi:nil='true'/><ActualOffBlockDateTime xsi:nil='true'/></OffBlockDateTime><TakeOffDateTime><ScheduledTakeOffDateTime xsi:nil='true'/><EstimatedTakeOffDateTime xsi:nil='true'/><ActualTakeOffDateTime xsi:nil='true'/></TakeOffDateTime><NextAirportArrivalDateTime><ScheduledNextAirportArrivalDateTime xsi:nil='true'/><EstimatedNextAirportArrivalDateTime xsi:nil='true'/><ActualNextAirportArrivalDateTime xsi:nil='true'/></NextAirportArrivalDateTime><BestKnownDateTime xsi:nil='true'/></OperationalDateTime><Status><OperationStatus>SCH</OperationStatus><FlightStatus>PLN</FlightStatus><DelayReason><DelayCode/><DelayFreeText/></DelayReason><DiversionAirport/><ChangeLandingAirport/><DisplayCode>P</DisplayCode><IsTransitFlight>N</IsTransitFlight><IsOverNightFlight>N</IsOverNightFlight><IsCashFlight>N</IsCashFlight></Status><Payload><Passenger><PassengersNumber><TotalPassengersNumber>100</TotalPassengersNumber><FirstClassPassengersNumber>5</FirstClassPassengersNumber><BusinessClassPassengersNumber>6</BusinessClassPassengersNumber><EconomicClassPassengersNumber>89</EconomicClassPassengersNumber></PassengersNumber><AdultPassengers><TotalAdultPassengersNumber>80</TotalAdultPassengersNumber><FirstClassAdultPassengersNumber>3</FirstClassAdultPassengersNumber><BusinessClassAdultPassengersNumber>5</BusinessClassAdultPassengersNumber><EconomicClassAdultPassengersNumber>72</EconomicClassAdultPassengersNumber></AdultPassengers><ChildPassengers><TotalChildPassengersNumber>15</TotalChildPassengersNumber><FirstClassChildPassengersNumber>1</FirstClassChildPassengersNumber><BusinessClassChildPassengersNumber>0</BusinessClassChildPassengersNumber><EconomicClassChildPassengersNumber>14</EconomicClassChildPassengersNumber></ChildPassengers><InfantPassengers><TotalInfantPassengersNumber>5</TotalInfantPassengersNumber><FirstClassInfantPassengersNumber>1</FirstClassInfantPassengersNumber><BusinessClassInfantPassengersNumber>1</BusinessClassInfantPassengersNumber><EconomicClassInfantPassengersNumber>3</EconomicClassInfantPassengersNumber></InfantPassengers></Passenger><Weight><TotalWeight>100</TotalWeight><BaggageWeight>30</BaggageWeight><CargoWeight>20</CargoWeight><MailWeight>50</MailWeight></Weight></Payload><Airport><Terminal><FlightTerminalID>2</FlightTerminalID><AircraftTerminalID/></Terminal><Runway><RunwayID>1</RunwayID></Runway><Stand><StandID>10</StandID><ScheduledStandStartDateTime>2012-07-01T20:00:00</ScheduledStandStartDateTime><ScheduledStandEndDateTime>2012-07-01T21:00:00</ScheduledStandEndDateTime><ActualStandStartDateTime>2012-07-01T20:00:00</ActualStandStartDateTime><ActualStandEndDateTime>2012-07-01T21:00:00</ActualStandEndDateTime></Stand><BaggageMakeup><BaggageBeltID/><BaggageBeltStatus/><ScheduledMakeupStartDateTime xsi:nil='true'/><ScheduledMakeupEndDateTime  xsi:nil='true'/><ActualMakeupStartDateTime  xsi:nil='true'/><ActualMakeupEndDateTime  xsi:nil='true'/></BaggageMakeup><Gate><GateID>3</GateID><GateStatus>S</GateStatus><ScheduledGateStartDateTime>2012-07-01T20:00:00</ScheduledGateStartDateTime><ScheduledGateEndDateTime>2012-07-01T20:30:00</ScheduledGateEndDateTime><ActualGateStartDateTime>2012-07-01T20:00:00</ActualGateStartDateTime><ActualGatelEndDateTime>2012-07-01T20:30:00</ActualGatelEndDateTime></Gate><BaggageReclaim><BaggageReclaimID>5</BaggageReclaimID><ScheduledReclaimStartDateTime>2012-07-01T20:00:00</ScheduledReclaimStartDateTime><ScheduledReclaimEndDateTime>2012-07-01T20:30:00</ScheduledReclaimEndDateTime><ActualReclaimStartDateTime>2012-07-01T20:00:00</ActualReclaimStartDateTime><ActualReclaimEndDateTime>2012-07-01T20:30:00</ActualReclaimEndDateTime><FirstBaggageDateTime>2012-07-01T20:00:00</FirstBaggageDateTime><LastBaggageDateTime>2012-07-01T20:30:00</LastBaggageDateTime></BaggageReclaim><CheckInDesk><CheckInID/><CheckInType xsi:nil='true'/><CheckInStatus  xsi:nil='true'/><CheckInClassService/><ScheduledCheckInBeginDateTime xsi:nil='true'/><ScheduledCheckInEndDateTime xsi:nil='true'/><ActualCheckInBeginDateTime  xsi:nil='true'/><AcutalCheckInEndDateTime  xsi:nil='true'/>                 </CheckInDesk></Airport></FlightData></Data></IMFRoot>";
                                               messages.add(us);

                                               try {
                                                   rsclient.response(messages);
                                               } catch (Exception e) {
                                                   e.printStackTrace();
                                               }
                                           }
                                       };

    public static void main(String[] args) throws ImfClientInvalidServiceTypeException, ImfClientConnectionException,
                                          ImfServerUnknowException {

        rsclient = ImfRsClient.getImfRsClient(ImfServiceType.FRS, listener);
    }
}
