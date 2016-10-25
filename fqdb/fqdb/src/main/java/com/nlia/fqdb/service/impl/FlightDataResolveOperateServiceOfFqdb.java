package com.nlia.fqdb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.fqdb.dao.FlightBaseDao;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.service.IFlightDataResolveOperateServiceOfFqdb;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;


/**
 * @author zhuhaijian
 * @time 2013-06-19
 * @decription 接收德电的xml格式的航班信息解析并对数据库进行增、删和改的操作
 */
@Service
public class FlightDataResolveOperateServiceOfFqdb extends AbstractCrudService<FlightBase, Long> implements IFlightDataResolveOperateServiceOfFqdb {

	@Resource
	private FlightBaseDao flightBaseDao;
	
	@Resource
	private FlightBaseManager flightBaseManager;
	
	@Override
	protected GenericDao<FlightBase, Long> getDao() {
		return flightBaseDao;
	}
	@Transactional
	public String parseFlightDataOfXmlString(String flightDataOfXmlString) {
		
		Document doc;
		boolean isExist = false; // 判断当前xml记录的航班信息数据表是否存在
		String createUser = "IMF";
        String operationMode = "";
		try {
			
			FlightBase flightBase = new FlightBase();
			FlightData flightData = new FlightData();
			FlightResource flightResource = new FlightResource();
			flightBase.setFlightData(flightData);
			flightBase.setFlightResource(flightResource);
			flightBase.setIsLock("0");
			//System.out.println("---------------------------XML---------------------------");
			//System.out.println(flightDataOfXmlString);
			//System.out.println("---------------------------XML---------------------------");
			doc = DocumentHelper.parseText(flightDataOfXmlString);
			Node root = doc.selectSingleNode("/IMFRoot/SysInfo/OperationMode");			
			if(isExistNode(root)) operationMode = root.getText();
			
			root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightDirection");
			
			isExist = isExistFlight(doc);
			if("NEW".equals(operationMode)) {
				if(!isExist) {
					parseXmlToFlightBase(doc, flightBase, operationMode);
					parseXmlToFlightData(doc, flightData, operationMode);
					parseXmlToFlightResource(doc, flightResource, operationMode);
					
					flightBase.setCreateUser(createUser);
					flightBase.setRemoveFlag("1");
					flightBase.setCreateTime(DateUtils.Date2Date(new Date()));
					
					flightData.setCreateUser(createUser);
					flightData.setRemoveFlag("1");
					flightData.setCreateTime(DateUtils.Date2Date(new Date()));
					
					flightResource.setCreateUser(createUser);
					flightResource.setRemoveFlag("1");
					flightResource.setCreateTime(DateUtils.Date2Date(new Date()));
					//解析主从航班
					if("Y".equals(flightBase.getIsMasterFlight())){
					    parseSlaveFlight(flightBase, doc);
					}
					flightBase = flightBaseManager.save(flightBase);
					//解析连班
					parseLinkFlight(flightBase);	
//					//解析主从航班
//					if("Y".equals(flightBase.getIsMasterFlight())){
//						parseSlaveFlight(flightBase, doc, "NEW");
//					}
					return "success:" + flightBase.getId().toString() + ":new";
				} else {
					operationMode = "MOD";
					flightBase = findByKey(doc);
					//FlightBase flightBaseOld = flightBase.clone();
					parseXmlToFlightBase(doc, flightBase, operationMode);
					parseXmlToFlightData(doc, flightBase.getFlightData(), operationMode);
					parseXmlToFlightResource(doc, flightBase.getFlightResource(), operationMode);
					
					flightBase.setModifyUser(createUser);
					flightBase.setModifyTime(DateUtils.Date2Date(new Date()));
					
					flightBase.getFlightData().setModifyUser(createUser);
					flightBase.getFlightData().setModifyTime(DateUtils.Date2Date(new Date()));
					
					flightBase.getFlightResource().setModifyUser(createUser);
					flightBase.getFlightResource().setModifyTime(DateUtils.Date2Date(new Date()));
					//解析主从航班
                    if("Y".equals(flightBase.getIsMasterFlight())){
                        parseSlaveFlight(flightBase, doc);
                    }
					flightBase = flightBaseManager.save(flightBase);
					//解析连班
					parseLinkFlight(flightBase);
//					//解析主从航班
//					if("Y".equals(flightBase.getIsMasterFlight())){
//						parseSlaveFlight(flightBase, doc, "EXIST");
//					}
					return "success:" + flightBase.getId().toString() + ":mod";
				}
			}
			if("MOD".equals(operationMode)) {
				if(isExist) {
					flightBase = findByKey(doc);
					//FlightBase flightBaseOld = flightBase.clone();
					parseXmlToFlightBase(doc, flightBase, operationMode);
					parseXmlToFlightData(doc, flightBase.getFlightData(), operationMode);
					parseXmlToFlightResource(doc, flightBase.getFlightResource(), operationMode);
					
					flightBase.setModifyUser(createUser);
					flightBase.setModifyTime(DateUtils.Date2Date(new Date()));
					
					flightBase.getFlightData().setModifyUser(createUser);
					flightBase.getFlightData().setModifyTime(DateUtils.Date2Date(new Date()));
//					flightBase.getFlightData().setOperationStatus("CAN");
					
					flightBase.getFlightResource().setModifyUser(createUser);
					flightBase.getFlightResource().setModifyTime(DateUtils.Date2Date(new Date()));
					//解析主从航班
                    if("Y".equals(flightBase.getIsMasterFlight())){
                        parseSlaveFlight(flightBase, doc);
                    }
					flightBase = flightBaseManager.save(flightBase);
					//解析连班
					parseLinkFlight(flightBase);
//					//解析主从航班
//					if("Y".equals(flightBase.getIsMasterFlight())){
//						parseSlaveFlight(flightBase, doc, "MOD");
//					}
					
					return "success:" + flightBase.getId().toString() + ":mod";
				} else {
					operationMode = "NEW";
					parseXmlToFlightBase(doc, flightBase, operationMode);
					parseXmlToFlightData(doc, flightData, operationMode);
					parseXmlToFlightResource(doc, flightResource, operationMode);
					
					flightBase.setCreateUser(createUser);
					flightBase.setRemoveFlag("1");
					flightBase.setCreateTime(DateUtils.Date2Date(new Date()));
					
					flightData.setCreateUser(createUser);
					flightData.setRemoveFlag("1");
					flightData.setCreateTime(DateUtils.Date2Date(new Date()));
					
					flightResource.setCreateUser(createUser);
					flightResource.setRemoveFlag("1");
					flightResource.setCreateTime(DateUtils.Date2Date(new Date()));
					//解析主从航班
                    if("Y".equals(flightBase.getIsMasterFlight())){
                        parseSlaveFlight(flightBase, doc);
                    }
					flightBase = flightBaseManager.save(flightBase);
					//解析连班
					parseLinkFlight(flightBase);	
//					//解析主从航班
//					if("Y".equals(flightBase.getIsMasterFlight())){
//						parseSlaveFlight(flightBase, doc, "NEW");
//					}
					return "success:" + flightBase.getId().toString() + ":new";
				}
			}
			if("DEL".equals(operationMode)) {
				if(isExist) {
					flightBase = findByKey(doc);
					return "success:" + flightBaseManager.remove(flightBase).getId() + ":del";
				} else {
					return "failure:0:no";
				}
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return "failure:2:no";
	}
	
	private void parseXmlToFlightResource(Document doc, FlightResource flightResource, String operationMode) {
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Terminal/FlightTerminalID");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setFlightTerminalID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Terminal/AircraftTerminalID");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setAircraftTerminalID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Runway/RunwayID");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setRunwayID(root.getText());
		}
        ////////////////////////////////////
        Date lastScheduledStandStartDateTime = null;
        Node rightStand = null;

        root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightDirection");
        if (isExistNode(root)) {
            String direct = root.getText();
            //modifi by march 20151124 HAK Rename IMFRoot/Data/FlightData/Airport/Stand to IMFRoot/Data/FlightData/Airport/Stand/GroundMovement
            //List<Node> standids = doc.selectNodes("/IMFRoot/Data/FlightData/Airport/Stand");
            List<Node> standids = doc.selectNodes("/IMFRoot/Data/FlightData/Airport/GroundMovement");
            //modifi by march 20151124 HAK Rename IMFRoot/Data/FlightData/Airport/Stand to IMFRoot/Data/FlightData/Airport/Stand/GroundMovement
            if ("A".equals(direct)) {
                // 取计划开始时间最小的机位
                for (Node stand : standids) {
                    Date ScheduledStandStartDateTime = stringToDate(stand
                            .selectSingleNode("ScheduledStandStartDateTime")
                            .getText());
                    if (lastScheduledStandStartDateTime == null
                            || lastScheduledStandStartDateTime
                                    .compareTo(ScheduledStandStartDateTime) > 0) {
                        lastScheduledStandStartDateTime = ScheduledStandStartDateTime;
                        rightStand = stand;
                    }
                }
            } else if ("D".equals(direct)) {
                // 取计划开始时间最大的机位
                for (Node stand : standids) {
                    Date ScheduledStandStartDateTime = stringToDate(stand
                            .selectSingleNode("ScheduledStandStartDateTime")
                            .getText());
                    if (lastScheduledStandStartDateTime == null
                            || lastScheduledStandStartDateTime
                                    .compareTo(ScheduledStandStartDateTime) < 0) {
                        lastScheduledStandStartDateTime = ScheduledStandStartDateTime;
                        rightStand = stand;
                    }
                }
            }
        }

        root = rightStand.selectSingleNode("StandID");
        if (isInsertOrUpdateNode(root, operationMode)) {
            flightResource.setStandID(root.getText());
        }
        root = rightStand.selectSingleNode("ScheduledStandStartDateTime");
        if (isInsertOrUpdateNode(root, operationMode)) {
            flightResource.setScheduledStandStartDateTime(stringToDate(root
                    .getText()));
        }
        root = rightStand.selectSingleNode("ScheduledStandEndDateTime");
        if (isInsertOrUpdateNode(root, operationMode)) {
            flightResource.setScheduledStandEndDateTime(stringToDate(root
                    .getText()));
        }
        root = rightStand.selectSingleNode("ActualStandStartDateTime");
        if (isInsertOrUpdateNode(root, operationMode)) {
            flightResource.setActualStandStartDateTime(stringToDate(root
                    .getText()));
        }
        root = rightStand.selectSingleNode("ActualStandEndDateTime");
        if (isInsertOrUpdateNode(root, operationMode)) {
            flightResource.setActualStandEndDateTime(stringToDate(root
                    .getText()));
        }
        /////////////////////////////////////
        

		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageMakeup/BaggageBeltID");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setBaggageBeltID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageMakeup/BaggageBeltStatus");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setBaggageBeltStatus(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageMakeup/ScheduledMakeupStartDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setScheduledMakeupStartDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageMakeup/ScheduledMakeupEndDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setScheduledMakeupEndDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageMakeup/ActualMakeupStartDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setActualMakeupStartDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageMakeup/ActualMakeupEndDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setActualMakeupEndDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Gate/GateID");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setGateID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Gate/GateStatus");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setGateStatus(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Gate/ScheduledGateStartDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setScheduledGateStartDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Gate/ScheduledGateEndDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setScheduledGateEndDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Gate/ActualGateStartDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setActualGateStartDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/Gate/ActualGateEndDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setActualGateEndDateTime(stringToDate(root.getText()));
			
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageReclaim/BaggageReclaimID");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setBaggageReclaimID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageReclaim/ScheduledReclaimStartDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setScheduledFirstBaggageDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageReclaim/ScheduledReclaimEndDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setScheduledLastBaggageDateTime(stringToDate(root.getText()));
		}
///////////////////因字段取反，故意这样修改
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageReclaim/ActualReclaimStartDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			//flightResource.setActualFirstBaggageDateTime(stringToDate(root.getText()));
		    flightResource.setFirstBaggageDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageReclaim/ActualReclaimEndDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			//flightResource.setActualLastBaggageDateTime(stringToDate(root.getText()));
		    flightResource.setLastBaggageDateTime(stringToDate(root.getText()));
		}
		
		//add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageReclaim/FirstBaggageDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			//flightResource.setFirstBaggageDateTime(stringToDate(root.getText()));
		    flightResource.setActualFirstBaggageDateTime(stringToDate(root.getText()));
		}
		//add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/BaggageReclaim/LastBaggageDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			//flightResource.setLastBaggageDateTime(stringToDate(root.getText()));
		    flightResource.setActualLastBaggageDateTime(stringToDate(root.getText()));
		}
///////////////////因字段取反，故意这样修改
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/CheckInDesk/CheckInDeskID");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setCheckInID(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/CheckInDesk/CheckInType");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setCheckInType(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/CheckInDesk/CheckInStatus");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setCheckInStatus(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/CheckInDesk/CheckInClassService");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setCheckInClassService(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/CheckInDesk/ScheduledCheckInBeginDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setScheduledCheckInBeginDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/CheckInDesk/ScheduledCheckInEndDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setScheduledCheckInEndDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/CheckInDesk/ActualCheckInBeginDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setActualCheckInBeginDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Airport/CheckInDesk/AcutalCheckInEndDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightResource.setAcutalCheckInEndDateTime(stringToDate(root.getText()));
		}
		
	}

	private void parseXmlToFlightData(Document doc, FlightData flightData, String operationMode) {
		
		Node root;
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightDirection");
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightScheduledDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setFlightScheduledDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/Registration");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setRegisteration(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/CallSign");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setCallSign(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/AircraftType/AircraftIATACode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setAircraftIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/AircraftType/AircraftICAOCode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setAircraftICAOCode(root.getText());
		}
		
		
		/**
		 * modifi by march 20151124 因HAK而修改
		 * Add  IMFRoot/Data/FlightData/General/FlightServiceType/FlightCAACServiceType
		 * Add  IMFRoot/Data/FlightData/General/FlightServiceType/FlightIATAServiceType
		 * Remove IMFRoot/Data/FlightData/General/FlightServiceType
		 */
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightServiceType");
//        if(isInsertOrUpdateNode(root, operationMode)) {
//            flightData.setFlightServiceType(root.getText());
//        }
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightServiceType/FlightCAACServiceType");
        if (isInsertOrUpdateNode(root, operationMode)) {
            flightData.setFlightCAACServiceType(root.getText());
            flightData.setFlightServiceType(root.getText());
        }
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightServiceType/FlightIATAServiceType");
        if (isInsertOrUpdateNode(root, operationMode)) {
            flightData.setFlightIATAServiceType(root.getText());
            
        }

		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/IATARoute/IATAOriginAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setIATAOriginAirport(root.getText());
		}
		
		// add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/IATARoute/IATAPreviousAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setIATAPreviousAirport(root.getText());
		}
		// add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/IATARoute/IATANextAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setIATANextAirport(root.getText());
		}
		// add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/IATARoute/IATADestinationAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setIATADestinationAirport(root.getText());
		}
		
		//remark by march 20151124 HAK Remove IMFRoot/Data/FlightData/General/FlightRoute/IATARoute/IATAViaAirport
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/IATARoute/IATAViaAirport");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setIATAViaAirport(root.getText());
//		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/ICAORoute/ICAOOriginAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setICAOOriginAirport(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/ICAORoute/ICAOPreviousAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setICAOPreviousAirport(root.getText());
		}
		//remark by march 20151124 HAK Remove IMFRoot/Data/FlightData/General/FlightRoute/ICAOoute/ICAOViaAirport
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/ICAORoute/ICAOViaAirport");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setICAOViaAirport(root.getText());
//		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/ICAORoute/ICAONextAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setICAONextAirport(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightRoute/ICAORoute/ICAODestinationAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setICAODestinationAirport(root.getText());
		}
		/**
         * modifi by march 20151124 因HAK而修改
         * Create IMFRoot/Data/FlightData/General/FlightRoute/IATARoute/IATAFullRoute/AirportIATACode with attribute "LegNo"
         * Create IMFRoot/Data/FlightData/General/FlightRoute/ICAOoute/ICAOFullRoute/AirportICAOCode with attribute "LegNo"
        */
		//将航段信息以逗号分隔方式保存
		List<Node> IATAFullRoutes  = doc.selectNodes("/IMFRoot/Data/FlightData/General/FlightRoute/IATARoute/IATAFullRoute/AirportIATACode","@LegNo");
		String IATAFullRouteStr="";
		for(Node IATAFullRoute:IATAFullRoutes){
		    String tmp=IATAFullRoute.getText();
		    if(!tmp.isEmpty()){
		        IATAFullRouteStr+=tmp+",";
		    }
		}
		if(IATAFullRouteStr.length()>0){
			flightData.setFullRouteAirportIATACode(IATAFullRouteStr.substring(0, IATAFullRouteStr.length()-1));
		}
		 
		List<Node> ICAOFullRoutes  = doc.selectNodes("/IMFRoot/Data/FlightData/General/FlightRoute/ICAORoute/ICAOFullRoute/AirportICAOCode","@LegNo");
	    String ICAOFullRouteStr="";
	    for(Node ICAOFullRoute:ICAOFullRoutes){
	        String tmp=ICAOFullRoute.getText();
	        if(!tmp.isEmpty()){
	           ICAOFullRouteStr+=tmp+",";
	        }
	    }
	    if(ICAOFullRouteStr.length()>0){
	    	flightData.setFullRouteAirportICAOCode(ICAOFullRouteStr.substring(0, ICAOFullRouteStr.length()-1));
	    }
	    //将航段信息以逗号分隔方式保存
	    
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/FlightCountryType");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setFlightCountryType(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/PreviousAirportDepartureDateTime/ScheduledPreviousAirportDepartureDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setScheduledPreviousAirportDepartureDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/PreviousAirportDepartureDateTime/EstimatedPreviousAirportDepartureDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setEstimatedPreviousAirportDepartureDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/PreviousAirportDepartureDateTime/ActualPreviousAirportDepartureDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualPreviousAirportDepartureDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/TenMilesDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setTenMilesDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/LandingDateTime/ScheduledLandingDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setScheduledLandingDateTime(stringToDate(root.getText()));
		}
			
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/LandingDateTime/EstimatedLandingDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setEstimatedLandingDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/LandingDateTime/ActualLandingDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualLandingDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/OnBlockDateTime/ScheduledOnBlockDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setScheduledOnBlockDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/OnBlockDateTime/EstimatedOnBlockDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setEstimatedOnBlockDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/OnBlockDateTime/ActualOnBlockDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualOnBlockDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/OffBlockDateTime/ScheduledOffBlockDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setScheduledOffBlockDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/OffBlockDateTime/EstimatedOffBlockDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setEstimatedOffBlockDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/OffBlockDateTime/ActualOffBlockDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualOffBlockDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/TakeOffDateTime/ScheduledTakeOffDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setScheduledTakeOffDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/TakeOffDateTime/EstimatedTakeOffDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setEstimatedTakeOffDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/TakeOffDateTime/ActualTakeOffDateTime");
        if (isInsertOrUpdateNode(root, operationMode)) {
            flightData.setActualTakeOffDateTime(stringToDate(root.getText()));
        }
		
		//add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/DoorOpenDateTime/ActualDoorOpenDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualDoorOpenDateTime(stringToDate(root.getText()));
		}
		
		//add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/DoorCloseDateTime/ActualDoorCloseDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualDoorCloseDateTime(stringToDate(root.getText()));
		}
		
		//add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/OnBridgeDateTime/ActualOnBridgeDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualOnBridgeDateTime(stringToDate(root.getText()));
		}
				
		//add yaokai
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/OffBridgeDateTime/ActualOffBridgeDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualOffBridgeDateTime(stringToDate(root.getText()));
		}
						
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/NextAirportArrivalDateTime/ScheduledNextAirportArrivalDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setScheduledNextAirportArrivalDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/NextAirportArrivalDateTime/EstimatedNextAirportArrivalDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setEstimatedNextAirportArrivalDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/NextAirportArrivalDateTime/ActualNextAirportArrivalDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setActualNextAirportArrivalDateTime(stringToDate(root.getText()));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/BestKnownDateTime");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setBestKnownDateTime(stringToDate(root.getText()));
		}
		
		//add by march 20151124 因HAK而增加
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/OperationalDateTime/FinalApproachTime");
        if(isInsertOrUpdateNode(root, operationMode)) {
            flightData.setFinalApproachTime(stringToDate(root.getText()));
        }
        //add by march 20151124 因HAK而增加
        
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/OperationStatus");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setOperationStatus(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/FlightStatus");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setFlightStatus(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/DelayReason/DelayCode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setDelayCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/DelayReason/DelayFreeText");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setDelayFreeText(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/DiversionAirport/AirportIATACode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setDiversionAirportIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/DiversionAirport/AirportICAOCode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setDiversionAirportICAOCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/ChangeLandingAirport/AirportIATACode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setAirportIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/ChangeLandingAirport/AirportICAOCode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setAirportICAOCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/DisplayCode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setDisplayCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/IsTransitFlight");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setIsTransitFlight(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/IsOverNightFlight");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setIsOverNightFlight(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Status/IsCashFlight");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightData.setIsCashFlight(root.getText());
		}
//remark by march 20150914 不解析客货行邮数据		
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/PassengersNumber/TotalPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setTotalPassengersNumber(stringTonteger(root.getText()));
//		}
//		
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/PassengersNumber/FirstClassPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setFirstClassPassengersNumber(stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/PassengersNumber/BusinessClassPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setBusinessClassPassengersNumber(stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/PassengersNumber/EconomicClassPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setEconomicClassPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/AdultPassengers/TotalAdultPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setTotalAdultPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/AdultPassengers/FirstClassAdultPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setFirstClassAdultPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/AdultPassengers/BusinessClassAdultPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setBusinessClassAdultPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/AdultPassengers/EconomicClassAdultPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setEconomicClassAdultPassengersNumber (stringTonteger(root.getText()));
//		}
//		
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/ChildPassengers/TotalChildPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setTotalChildrenNumber(stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/ChildPassengers/FirstClassChildPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setFirstClassChildPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/ChildPassengers/BusinessClassChildPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setBusinessClassChildPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/ChildPassengers/EconomicClassChildPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setEconomicClassChildPassengersNumber (stringTonteger(root.getText()));
//		}
//		
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/InfantPassengers/TotalInfantPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setTotalInfantPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/InfantPassengers/FirstClassInfantPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setFirstClassInfantPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/InfantPassengers/BusinessClassInfantPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setBusinessClassInfantPassengersNumber (stringTonteger(root.getText()));
//		}
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/InfantPassengers/EconomicClassInfantPassengersNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setEconomicClassInfantPassengersNumber (stringTonteger(root.getText()));
//		}
//	
//		//add yaokai
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/Weight/TotalWeight");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setTotalWeight(stringTonteger(root.getText()));
//		}
//		
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/Weight/BaggageWeight");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setTotallBaggageWeight(stringTonteger(root.getText()));
//		}
//		
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/Weight/CargoWeight");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setTotalCargoWeight(stringTonteger(root.getText()));
//		}
//		
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/Passenger/Weight/MailWeight");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightData.setTotallMailWeight(stringTonteger(root.getText()));
//		}
		
	}

//	/**
//	 * 解析航班数据变更到从航班上。
//	 * 
//	 * @param doc
//	 * @param masterFlightBase
//	 * @param operationMode
//	 */
//	@Transactional
//	private void parseXmlToSlaveFlights(Document doc, FlightBase masterFlightBase, String operationMode) {
//		List<FlightBase> slaveFlights = masterSlaveRelationDao.findSlaveFlights(masterFlightBase.getId());
//		for (FlightBase fb : slaveFlights) {
//			parseXmlToFlightBase(doc, fb, operationMode, false);
//			parseXmlToFlightData(doc, fb.getFlightData(), operationMode);
//			parseXmlToFlightResource(doc, fb.getFlightResource(), operationMode);
//		}
//
//		flightBaseManager.save(slaveFlights);
//	}
	
    private void parseXmlToFlightBase(Document doc, FlightBase flightBase, String operationMode) {
        parseXmlToFlightBase(doc, flightBase, operationMode, true);
    }

    private void parseXmlToFlightBase(Document doc, FlightBase flightBase, String operationMode, boolean isMasterFlight) {
		
		Node root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightScheduledDate");
		if (isMasterFlight) {

			if (isInsertOrUpdateNode(root, operationMode)) {
				flightBase.setFlightScheduledDate(DateUtils.String2Date(root.getText(), "yyyy-MM-dd"));
			}

			root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightIdentity");
			if (isInsertOrUpdateNode(root, operationMode)) {
				flightBase.setFlightIdentity(root.getText());
			}

			root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightDirection");
			if (isInsertOrUpdateNode(root, operationMode)) {
				flightBase.setFlightDirection("A".equals(root.getText()) ? "0" : "1");
			}
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/DepartureAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightBase.setDepartureAirport(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/Payload/DestinationAirport");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightBase.setDestinationAirport(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/BaseAirport/AirportIATACode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightBase.setAirportIATACode(root.getText());
		}
		//
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/BaseAirport/AirportICAOCode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightBase.setAirportICAOCode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/DetailedIdentity/AirlineIATACode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightBase.setAirlineIATACode(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/DetailedIdentity/AirlineICAOCode");
		if(isInsertOrUpdateNode(root, operationMode)) {
			flightBase.setAirlineICAOCode(root.getText());
		}
		//remark by march 20151124 HAK Remove IMFRoot/Data/PrimaryKey/FlightKey/DetailedIdentity/FlightNumber
//		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/DetailedIdentity/FlightNumber");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightBase.setFlightNumber(root.getText());
//		}
		//remark by march 20151124 HAK Remove IMFRoot/Data/PrimaryKey/FlightKey/DetailedIdentity/FlightSuffix
//		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/DetailedIdentity/FlightSuffix");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightBase.setFlightSuffix(root.getText());
//		}
		//remark by march 20151124 HAK Remove IMFRoot/Data/FlightData/General/IsMasterFlight
//		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/IsMasterFlight");
//		if(isInsertOrUpdateNode(root, operationMode)) {
//			flightBase.setIsMasterFlight(root.getText());
//		}
		if (isMasterFlight) {
		    flightBase.setIsMasterFlight("Y");//add by march 20151124
    		//解析连班
    		// root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightScheduledDate");
    		if (isInsertOrUpdateNode(doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightScheduledDate"), "")
    				&& isInsertOrUpdateNode(doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightIdentity"), "")
    				&& isInsertOrUpdateNode(doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightDirection"), "")) {
    			FlightBase linkFlight = findByLinkFlight(doc);
    			// 假如绑定子航班，则修正为绑定该子航班的主航班
    			if(null!=linkFlight){
        			if("N".equals(linkFlight.getIsMasterFlight())){
        				long slaveFlightId = linkFlight.getId();
//        				FlightBase masterFlight = masterSlaveRelationDao.findMasterFlight(slaveFlightId);
//        				if(masterFlight != null)
//        					linkFlight = masterFlight; 
        			}
        			// 解除之前的连班关系
        			FlightBase linkFlight_before = flightBase.getLinkFlight();
        			if (linkFlight_before != null) {
        				linkFlight_before.setLinkFlight(null);
        				flightBaseManager.save(linkFlight_before);
        			}
        			// 建立新的连班关系
        			flightBase.setLinkFlight(linkFlight);
        			if (linkFlight != null) {
        			  //add by march 连班的连班也要清除  start
        			    FlightBase link_linkFlight=linkFlight.getLinkFlight();
        			    if(link_linkFlight != null){
        			        link_linkFlight.setLinkFlight(null);
        	                flightBaseManager.save(link_linkFlight); 
        			    }
        			  //add by march 连班的连班也要清除  end
        				linkFlight.setLinkFlight(flightBase);//modifi by march 怀疑是写错了
        				flightBaseManager.save(linkFlight);
        			}
    			}else{//拆连班
    			    FlightBase linkFlight_before = flightBase.getLinkFlight();
                    if (linkFlight_before != null) {
                        linkFlight_before.setLinkFlight(null);
                        flightBaseManager.save(linkFlight_before);
                    }
                    flightBase.setLinkFlight(null);
    			}
    			// 拆除航班
    		} else if (isCancelLinkFlight(doc, operationMode)) {
    			FlightBase linkFlight_before = flightBase.getLinkFlight();
    			if (linkFlight_before != null) {
    				linkFlight_before.setLinkFlight(null);
    				flightBaseManager.save(linkFlight_before);
    			}
    			flightBase.setLinkFlight(null);
    		}
		}
		else{//add by march 20151124
		    flightBase.setIsMasterFlight("N");
		}

	}

	public boolean isExistFlight(Document doc) {
		
		FlightBase tempFlightBase = new FlightBase();
		FlightData tempFlightData = new FlightData();
		FlightResource tempFlightResource = new FlightResource();
		tempFlightBase.setFlightData(tempFlightData);
		tempFlightBase.setFlightResource(tempFlightResource);

		Node root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightScheduledDate");
		if(isExistNode(root)) {
			tempFlightBase.setFlightScheduledDate(DateUtils.String2Date(root.getText(), "yyyy-MM-dd"));
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightIdentity");
		if(isExistNode(root)) {
			tempFlightBase.setFlightIdentity(root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightDirection");
		if(isExistNode(root)) {
			tempFlightBase.setFlightDirection("A".equals(root.getText()) ? "0" : "1");
		}
		
		return flightBaseManager.isFlightBaseExist(tempFlightBase);
	}
	
	public FlightBase findByKey(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightScheduledDate");
		if(isExistNode(root)) {
			filters.put("flightScheduledDate_equal", DateUtils.String2Date(root.getText(), "yyyy-MM-dd"));
		}
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightIdentity");
		if(isExistNode(root)) {
			filters.put("flightIdentity_equal", root.getText());
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/PrimaryKey/FlightKey/FlightDirection");
		if(isExistNode(root)) {
			filters.put("flightDirection_equal", "A".equals(root.getText()) ? "0" : "1");
		}
		
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<FlightBase> resultList = flightBaseManager.findBy(filters, null, -1, -1);
		if(resultList.size() > 0){
			return resultList.get(0);
		}else {
			return null;
		}
	}
	
	public FlightBase findByLinkFlight(Document doc) {
		
		Map<String, Object> filters = new HashMap<String, Object>();
		
		Node root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightScheduledDate");
		if(isExistNode(root)) {
			filters.put("flightScheduledDate_equal", DateUtils.String2Date(root.getText(), "yyyy-MM-dd"));
		}else {
			return null;
		}
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightIdentity");
		if(isExistNode(root)) {
			filters.put("flightIdentity_equal", root.getText());
		}else {
			return null;
		}
		
		root = doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightDirection");
		if(isExistNode(root)) {
			filters.put("flightDirection_equal", "A".equals(root.getText()) ? "0" : "1");
		}
		
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<FlightBase> resultList = flightBaseManager.findBy(filters, null, -1, -1);
		if(resultList.size() > 0){
			return resultList.get(0);
		}else {
			return null;
		}
	}
	
	private boolean isExistNode(Node root) {
		
		return root != null && !"".equals(root.getText());
	}
	
	public boolean isInsertOrUpdateNode(Node root, String operationMode) {
		
		Element element = (Element)root;
		if(root != null) {
//modifi by march  不判断oldvalue 只要有节点就要解析
//			if("MOD".equals(operationMode)) {
//				//System.out.println(element.attribute("nil") == null && element.attribute("OldValue") == null ? false : true);
//				return (element.attribute("nil") != null) || (element.attribute("OldValue") != null) ? true : false;
//			}
//		    modifi by march  不判断oldvalue 只要有节点就要解析
			return true;
		}
		return false;
	}

	/**
	 * 是否拆除连班航班
	 * 
	 * @param root
	 * @param operationMode
	 * @return
	 */
	public boolean isCancelLinkFlight(Document doc, String operationMode) {
		Element element_FlightScheduledDate = (Element) doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightScheduledDate");
		Element element_FlightIdentity = (Element) doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightIdentity");
		Element element_FlightDirection = (Element) doc.selectSingleNode("/IMFRoot/Data/FlightData/General/LinkFlight/FlightDirection");
		List<Element> elements = new ArrayList<>();
		elements.add(element_FlightScheduledDate);
		elements.add(element_FlightIdentity);
		elements.add(element_FlightDirection);
		for (Element element : elements) {
			if (element != null) {
				if ("MOD".equals(operationMode)) {
					// 有修改，且修改为'空'
					if (element.attribute("OldValue") != null && StringUtils.isEmpty(element.getText()))
						return true;
				}
			}
		}
		return false;
	}
	//解析共享航班
	public void parseSlaveFlight(FlightBase flightBase, Document doc){
	    //modifi by march 20151124 HAK Rename IMFRoot/Data/FlightData/General/MasterOrSlaveFlight to IMFRoot/Data/FlightData/General/SlaveFlight
	    //List<Node> nodes = doc.selectNodes("/IMFRoot/Data/FlightData/General/MasterOrSlaveFlight");
	    List<Node> nodes = doc.selectNodes("/IMFRoot/Data/FlightData/General/SlaveFlight");
	    String sharedFlightIdentity="";
	    for(Node node : nodes){
            Element element = (Element)node;            
            sharedFlightIdentity += element.elementText("FlightIdentity");
            sharedFlightIdentity += "|";
	    }
	    if(sharedFlightIdentity.length()>0 && sharedFlightIdentity.endsWith("|")){
	        sharedFlightIdentity=sharedFlightIdentity.substring(0, sharedFlightIdentity.length()-1);
	        flightBase.setSharedFlightIdentity(sharedFlightIdentity);
	    }
	}
	
//	// 根据IsMasterFlight的取值对从航班进行解析//remark by march 20151124 never used
//	@SuppressWarnings("unchecked")
//	public void parseSlaveFlight(FlightBase flightBase, Document doc, String operationMode) {
//      List<Node> nodes = doc.selectNodes("/IMFRoot/Data/FlightData/General/MasterOrSlaveFlight");
//		if("NEW".equals(operationMode)){
//			insertSlaveFlight(nodes, flightBase);
//		}else if("EXIST".equals(operationMode)){
//			masterSlaveRelationDao.removeByMaster(flightBase);
//			masterSlaveRelationDao.removeFlightSlaveByMaster(flightBase);
//			insertSlaveFlight(nodes, flightBase);
//		}else if("MOD".equals(operationMode)){
//			for(Node node : nodes){
//				Element element = (Element)node;
//				if(element.element("FlightIdentity").attribute("OldValue") != null 
//						|| element.element("FlightScheduledDate").attribute("OldValue") != null 
//						|| element.element("FlightDirection").attribute("OldValue") != null){
//					masterSlaveRelationDao.removeByMaster(flightBase);
//					masterSlaveRelationDao.removeFlightSlaveByMaster(flightBase);
//					insertSlaveFlight(nodes, flightBase);
//					break;
//				}
//			}
//		}
//		
//	}
//	//插入主从航班//remark by march 20151124 never used
//	public void insertSlaveFlight(List<Node> nodes, FlightBase flightBase){
//		for(Node node : nodes){
//			Element element = (Element)node;
//			
//			String flightIdentity = element.elementText("FlightIdentity");
//			String flightScheduledDate = element.elementText("FlightScheduledDate");
//			String flightDirection = element.elementText("FlightDirection");
//			if (flightIdentity != null && !"".equals(flightIdentity.trim()) && flightScheduledDate != null && !"".equals(flightScheduledDate.trim()) && flightDirection != null
//					&& !"".equals(flightDirection.trim())) {
//				FlightBase flightSlave = new FlightBase();
//				flightSlave.setFlightIdentity(flightIdentity);
//				flightSlave.setFlightScheduledDate(DateUtils.String2Date(flightScheduledDate, "yyyy-MM-dd"));
//				flightSlave.setFlightDirection("A".equals(flightDirection) ? "0" : "1");
//				flightSlave.setIsMasterFlight("N");
//				flightSlave.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
//				flightSlave.setCreateUser("IMF");
//				flightSlave.setCreateTime(DateUtils.Date2Date(new Date()));
//				flightSlave.setFlightData(flightBase.getFlightData());
//				flightSlave.setFlightResource(flightBase.getFlightResource());
//				flightSlave = flightBaseManager.save(flightSlave);
//				
//				MasterSlaveRelation msr = new MasterSlaveRelation();
//				msr.setFlightMaster(flightBase);
//				msr.setFlightSlave(flightSlave);
//				msr.setCreateUser("IMF");
//				msr.setRemoveFlag(CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
//				msr.setCreateTime(DateUtils.Date2Date(new Date()));
//				masterSlaveRelationDao.save(msr);
//			}
//		}
//	}
	//解析连班
	public void parseLinkFlight(FlightBase flightBase){
		FlightBase linkFlight = flightBase.getLinkFlight();
		// 假如绑定子航班，则修正为绑定该子航班的主航班
		if(null!=linkFlight){
    		if("N".equals(linkFlight.getIsMasterFlight())){
    			long slaveFlightId = linkFlight.getId();
//    			FlightBase masterFlight = masterSlaveRelationDao.findMasterFlight(slaveFlightId);
//    			if(masterFlight != null)
//    				linkFlight = masterFlight; 
    		}
		}
		if (linkFlight != null) {
			if ("0".equals(flightBase.getFlightDirection())) {
				flightBase.setLinkFlightSign(flightBase.getId() != null ? String.valueOf(flightBase.getId()) : null);
				if (!flightBase.equals(linkFlight.getLinkFlight())) {
					linkFlight.setLinkFlight(flightBase);
					linkFlight.setLinkFlightSign(flightBase.getId() != null ? String.valueOf(flightBase.getId()) : null);
					flightBaseManager.save(linkFlight);
				}
			} else if ("1".equals(flightBase.getFlightDirection())) {
				flightBase.setLinkFlightSign(linkFlight.getId() != null ? String.valueOf(linkFlight.getId()) : null);
				if (!flightBase.equals(linkFlight.getLinkFlight())) {
					linkFlight.setLinkFlight(flightBase);
					linkFlight.setLinkFlightSign(linkFlight.getId() != null ? String.valueOf(linkFlight.getId()) : null);
					flightBaseManager.save(linkFlight);
				}
			}
		}
	}
	
	public Date stringToDate(String dateStr) {
		
		return DateUtils.String2Date(StringUtils.replaceChars(dateStr, 'T', ' '), "yyyy-MM-dd HH:mm:ss");
	}

	public Integer stringTonteger(String numStr) {
		
		return NumberUtils.createInteger("".equals(numStr)? "0" : numStr);
	}
	
}
