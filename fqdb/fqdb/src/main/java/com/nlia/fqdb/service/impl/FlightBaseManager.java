package com.nlia.fqdb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.fqdb.configuration.DataCache;
import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.dao.FlightBaseDao;
import com.nlia.fqdb.dao.FlightViewDao;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightData;
import com.nlia.fqdb.entity.FlightResource;
import com.nlia.fqdb.entity.FlightView;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.service.IClientFlightBaseManager;
import com.nlia.fqdb.service.IFlightBaseManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;
import com.nlia.fqdb.util.ImportFlightBaseByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class FlightBaseManager extends AbstractCrudService<FlightBase, Long> implements IFlightBaseManager {

	@Resource
	private DataCache dataCache;

	@Resource
	private FlightViewDao flightViewDao;

	@Resource
	private FlightBaseDao flightBaseDao;

	@Resource
	private ImportFlightBaseByExcel importFlightBaseByExcel;
	
//	@RESOURCE
//	PRIVATE MASTERSLAVERELATIONMANAGER MASTERSLAVERELATIONMANAGER;
	
//	@Resource(name="clientCallRemoteFlightBaseManager")
//    private IClientFlightBaseManager clientCallRemoteFlightBaseManager;//=(IClientFlightBaseManager) applicationContext.getBean("clientCallRemoteFlightBaseManager"); ;
	
	DataHandler<FlightBase> dataHandler = new DataHandler<>();

	@Override
	protected GenericDao<FlightBase, Long> getDao() {
		return flightBaseDao;
	}


	@Override
	public FlightBase find(Long id) {
		List<FlightBase> flightBases = DataCache.getFlightBases();

		Map<String, Object> filters = new HashMap<>();
		filters.put("id_equal", id);
		List<FlightBase> flights = dataHandler.findBy(flightBases, filters);
		if(flights.isEmpty()){
			//如果内存里找不到，则去数据库查找
			FlightBase entity = flightBaseDao.find(id);
			//内存找不到航班，数据库找到航班
			if(entity != null){
				dataHandler.save(flightBases, entity);
				return entity;
			}else{
			//内存、数据库都找不到航班
				return null;
			}
		}else{
			//内存找到航班
			return flights.get(0);
		}
	}
	
	@Override
	public FlightBase save(FlightBase entity) {
		List<FlightBase> flightBases = DataCache.getFlightBases();
		initFlightTime(entity);
//		sendAlarm(entity);
		entity = super.save(entity);
		initFlightTime(entity);
		if(entity.getLinkFlight() != null){
			initFlightTime(entity.getLinkFlight());
		}
		dataHandler.save(flightBases, entity);
//		DataCache.setFlightBases(flightBases);
		//刷新远端缓存
//        try{
//            clientCallRemoteFlightBaseManager.refreshFlightInCache(entity);
//        } catch (Exception e) {
////            e.printStackTrace();
//            System.out.println(e.getMessage());
//            try{
//                clientCallRemoteFlightBaseManager.refreshFlightInCache(entity);
//            } catch (Exception e1) {
////                e.printStackTrace();
//                System.out.println("第二次刷新失败，"+e1.getMessage());
//            }
//        }
		return entity;
	}
	
	public FlightBase saveSingle(FlightBase entity) {
		List<FlightBase> flightBases = DataCache.getFlightBases();
		//initFlightTime(entity);
//		sendAlarm(entity);
		entity = super.save(entity);
		//initFlightTime(entity);
		if(entity.getLinkFlight() != null){
			initFlightTime(entity.getLinkFlight());
		}
		dataHandler.save(flightBases, entity);
//		DataCache.setFlightBases(flightBases);
		//刷新远端缓存
//        try{
//            clientCallRemoteFlightBaseManager.refreshFlightInCache(entity);
//        } catch (Exception e) {
////            e.printStackTrace();
//            System.out.println(e.getMessage());
//            try{
//                clientCallRemoteFlightBaseManager.refreshFlightInCache(entity);
//            } catch (Exception e1) {
////                e.printStackTrace();
//                System.out.println("第二次刷新失败，"+e1.getMessage());
//            }
//        }
		return entity;
	}

	@Override
	public Collection<FlightBase> save(Collection<FlightBase> entities) {
		List<FlightBase> flightBases = DataCache.getFlightBases();
		entities = super.save(entities);
		dataHandler.save(flightBases, entities);
		DataCache.setFlightBases(flightBases);
		return entities;
	}

	@Override
	public FlightBase remove(FlightBase entity) {
		List<FlightBase> flightBases = DataCache.getFlightBases();
		entity = super.remove(entity);
		dataHandler.remove(flightBases, entity);
		DataCache.setFlightBases(flightBases);
		return entity;
	}

	@Override
	public Collection<FlightBase> remove(Collection<FlightBase> entities) {
		List<FlightBase> flightBases = DataCache.getFlightBases();
		entities = super.remove(entities);
		dataHandler.remove(flightBases, entities);
		DataCache.setFlightBases(flightBases);
		return entities;
	}

	@Override
	public List<FlightBase> findBy(Map<?, ?> filters) {
		List<FlightBase> flightBases = DataCache.getFlightBases();

		return dataHandler.findBy(flightBases, filters);
	}

	@Override
	public List<FlightBase> findBy(Map<?, ?> filters, List<String> sorters) {
		List<FlightBase> flightBases = DataCache.getFlightBases();

		return dataHandler.findBy(flightBases, filters, sorters);
	}

	/*
	 * 分页查询，暂无实现
	 * 
	 * @Override public List<FlightBase> findBy(Map<?, ?> filters, List<String>
	 * sorters, int start, int limit) { List<FlightBase> flightBases =
	 * DataCache.getFlightBases();
	 * 
	 * return dataHandler.findBy(flightBases, filters, sorters); }
	 */

	/*
	 * 刷新内存
	 * 
	 * @Override public void refreshFlightBases(){ Map<String, Object> filters =
	 * new HashMap<>(); filters.put("removeFlag_equal",
	 * CommonData.REMOVEFLAG.NOT_REMOVED.getValue()); List<FlightView> viewList
	 * = flightViewDao.findBy(filters, null, -1, -1);
	 * dataCache.setFlightBasesWithFlightView(viewList); old code Map<String,
	 * Object> filters = new HashMap<>(); filters.put("removeFlag_equal",
	 * CommonData.REMOVEFLAG.NOT_REMOVED.getValue()); List<FlightView> viewList
	 * = flightViewDao.findBy(filters, null, -1, -1);
	 * //DataCache.setFlightBases(flightBaseDao.findBy(filters, null, -1, -1));
	 * DataCache.setFlightBases(convertFlightView(viewList)); }
	 */

	/*
	 * 刷新内存
	 */
	@Override
	public void refreshFlightBases(Date startDate, Date endDate) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		filters.put("flightScheduledDate_greaterThan", startDate);
		filters.put("flightScheduledDate_lessThan", endDate);
		List<FlightView> viewList = flightViewDao.findBy(filters, null, -1, -1);
		dataCache.setFlightBasesWithFlightView(viewList);
	}

	/**
	 * 从excel2003中导入数据到表格中
	 * @param bytes
	 * @return　错误数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<FlightBase> importByExcel2003(byte[] bytes) throws IOException {
		@SuppressWarnings("unused")
		int addCounter = 0, modifyCounter = 0;
		List<FlightBase> flightBaseAllList = new ArrayList<>();
		List<FlightBase> flightBaseErrorList = new ArrayList<>();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		@SuppressWarnings("rawtypes")
		List resultlist = importFlightBaseByExcel.ImportFlightBaseByExcel2003(inputStream);
		flightBaseAllList = (List<FlightBase>) resultlist.get(0);
		flightBaseErrorList = (List<FlightBase>) resultlist.get(1);
		if (flightBaseErrorList.size() == 0) {
			for (FlightBase flightBase : flightBaseAllList) {
				if (isFlightBaseExist(flightBase)) {
					// 更新记录：update
					flightBase.setModifyUser(FQDBMessages.getString("ADMINISTARTOR"));
					flightBase.setModifyTime(new Date());
					this.save(flightBase);
					addCounter++;
				} else {
					// 新增记录：insert
					flightBase.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));
					flightBase.setCreateTime(new Date());
					this.save(flightBase);
					modifyCounter++;
				}
			}
		}
		return flightBaseAllList;
	}

	/**
	 * 从excel2007中导入数据到表格中
	 * @param bytes
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FlightBase> importByExcel2007(byte[] bytes) throws IOException {
		List<FlightBase> flightBaseAllList = new ArrayList<>();
		List<FlightBase> flightBaseErrorList = new ArrayList<>();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		@SuppressWarnings("rawtypes")
		List resultlist = importFlightBaseByExcel.ImportFlightBaseByExcel2007(inputStream);
		flightBaseAllList = (List<FlightBase>) resultlist.get(0);
		flightBaseErrorList = (List<FlightBase>) resultlist.get(1);
		if (flightBaseErrorList.size() == 0) {
			for (FlightBase flightBase : flightBaseAllList) {
				if (isFlightBaseExist(flightBase)) {
					// 更新记录：update
					flightBase.setModifyUser(FQDBMessages.getString("ADMINISTARTOR"));
					flightBase.setModifyTime(new Date());
					this.save(flightBase);
				} else {
					// 新增记录：insert
					flightBase.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));
					flightBase.setCreateTime(new Date());
					this.save(flightBase);
				}
			}
		}
		return flightBaseAllList;
	}

	/**
	 * 功能：判断航班是否存在
	 * 根据日期、航班号全称、进离港属性来判断航班是否存在
	 * @param flightBase
	 * @return true /false
	 * 如果已存在，把数据库中那条记录的id放进@param flightBase
	 */
	@Override
	public boolean isFlightBaseExist(FlightBase flightBase) {
		String flightIdentity, flightDirection;
		Map<String, Object> filters = new HashMap<String, Object>();
		flightIdentity = flightBase.getFlightIdentity();
		flightDirection = flightBase.getFlightDirection();
		filters.put("flightScheduledDate_equal", flightBase.getFlightScheduledDate());
		filters.put("flightIdentity_equal", flightIdentity);
		filters.put("flightDirection_equal", flightDirection);
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<FlightBase> flightBaseList = super.findBy(filters, null, -1, -1);
		if (flightBaseList.size() > 0) {
			// 存在对应记录就取id
			Long id = flightBaseList.get(0).getId();
			flightBase.setId(id);
			if (flightBaseList.get(0).getFlightData() != null) {
				flightBase.getFlightData().setId(flightBaseList.get(0).getFlightData().getId());
			}
			if (flightBaseList.get(0).getFlightResource() != null) {
				flightBase.getFlightResource().setId(flightBaseList.get(0).getFlightResource().getId());
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 跳转到指定页
	 * 跳转到第几页，destinationPageNumber
	 * 每页多少数据，pageSize
	 */
	@Override
	public Map<String, Object> findFlightByPage(int pageSize, int destinationPageNumber, Map<String, Object> filters) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<FlightBase> resultList = new ArrayList<FlightBase>();
		List<String> sorters = new ArrayList<String>();
		int totalPageNumber = 0;

		sorters.add("modifyTime_desc");
		sorters.add("createTime_desc");
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		// 查询总数据量
		// 判断destinationPageNumber是否<totalPageNumber
		long totalResultNumber = quantityOfResult(filters);
		double totalPageNumber_Double = Math.ceil((double) totalResultNumber / pageSize);
		totalPageNumber = (int) totalPageNumber_Double;
		/*
		 * if(destinationPageNumber<1 || destinationPageNumber >
		 * totalPageNumber){ return null; }
		 */

		// 根据destinationPageNumber，totalPageNumber，生成limit（总数）和start（开始为位置）
		int start = 0, limit = 0;
		start = pageSize * (destinationPageNumber - 1);
		limit = pageSize;
		// 查找
		resultList = this.findBy(filters, sorters, start, limit);
		resultMap.put("resultList", resultList);
		resultMap.put("totalPageNumber", totalPageNumber);
		resultMap.put("currentPageNumber", destinationPageNumber);

		return resultMap;
	}

	/*
	 * 找出命中数据的数量,待优化
	 */
	private long quantityOfResult(Map<String, Object> filters) {
		return this.countBy(filters);
	}

//	public List<FlightBase> convertFlightView(List<FlightView> viewList) {
//		List<FlightBase> resultList = new ArrayList<FlightBase>();
//		Map<Long, FlightBase> indexMap = new HashMap<Long, FlightBase>();
//		for (FlightView flightView : viewList) {
//			FlightBase fb = new FlightBase();
//			fb.setId(flightView.getId());
//			fb.setFlightScheduledDate(flightView.getFlightScheduledDate());
//			fb.setFlightIdentity(flightView.getFlightIdentity());
//			fb.setFlightDirection(flightView.getFlightDirection());
//			fb.setFlightNumber(flightView.getFlightNumber());
//			fb.setAirportIATACode(flightView.getAircraftIATACode());
//			fb.setAirportICAOCode(flightView.getAircraftICAOCode());
//			fb.setDepartureAirport(flightView.getDepartureAirport());
//			fb.setDestinationAirport(flightView.getDestinationAirport());
//			fb.setAirlineIATACode(flightView.getAirlineIATACode());
//			fb.setAirlineICAOCode(flightView.getAirlineICAOCode());
//			fb.setFlightSuffix(flightView.getFlightSuffix());
//			fb.setIsMasterFlight(flightView.getIsMasterFlight());
//			fb.setFlightProperty(flightView.getFlightProperty());
//			fb.setEntryExit(flightView.getEntryExit());
//			fb.setLinkFlightSign(flightView.getLinkFlightSign());
//			fb.setIsLock(flightView.getIsLock());
//			fb.setRemoveFlag(flightView.getRemoveFlag());
//
//			FlightData fd = new FlightData();
//			fd.setId(flightView.getFlightDataId());
//			fd.setFlightScheduledDateTime(flightView.getFlightScheduledDateTime());
//			fd.setRegisteration(flightView.getRegisteration());
//			fd.setCallSign(flightView.getCallSign());
//			fd.setFlightProperty(flightView.getFlightProperty());
//			fd.setTransportProperty(flightView.getTransportProperty());
//			fd.setAircraftIATACode(flightView.getAircraftIATACode());
//			fd.setAircraftICAOCode(flightView.getAircraftICAOCode());
//			fd.setFlightCategory(flightView.getFlightCategory());
//			fd.setFlightServiceType(flightView.getFlightServiceType());
//			fd.setIATARouteAirport(flightView.getIATARouteAirport());
//			fd.setIATAOriginAirport(flightView.getIATAOriginAirport());
//			fd.setIATAViaAirport(flightView.getIATAViaAirport());
//			fd.setICAORouteAirport(flightView.getICAORouteAirport());
//			fd.setICAOOriginAirport(flightView.getICAOOriginAirport());
//			fd.setICAOViaAirport(flightView.getICAOViaAirport());
//			fd.setICAODestinationAirport(flightView.getICAODestinationAirport());
//			fd.setAirportIATACode(flightView.getAirportIATACode());
//			fd.setAirportICAOCode(flightView.getAirportICAOCode());
//			fd.setFlightCountryType(flightView.getFlightCountryType());
//			fd.setScheduledPreviousAirportDepartureDateTime(flightView.getScheduledPreviousAirportDepartureDateTime());
//			fd.setEstimatedPreviousAirportDepartureDateTime(flightView.getEstimatedPreviousAirportDepartureDateTime());
//			fd.setActualPreviousAirportDepartureDateTime(flightView.getActualPreviousAirportDepartureDateTime());
//			fd.setTenMilesDateTime(flightView.getTenMilesDateTime());
//			fd.setScheduledLandingDateTime(flightView.getScheduledLandingDateTime());
//			fd.setEstimatedLandingDateTime(flightView.getEstimatedLandingDateTime());
//			fd.setActualLandingDateTime(flightView.getActualLandingDateTime());
//			fd.setScheduledOnBlockDateTime(flightView.getScheduledOnBlockDateTime());
//			fd.setEstimatedOnBlockDateTime(flightView.getEstimatedOnBlockDateTime());
//			fd.setActualOnBlockDateTime(flightView.getActualOnBlockDateTime());
//			fd.setScheduledOffBlockDateTime(flightView.getScheduledOffBlockDateTime());
//			fd.setEstimatedOffBlockDateTime(flightView.getEstimatedOffBlockDateTime());
//			fd.setActualOffBlockDateTime(flightView.getActualOffBlockDateTime());
//			fd.setScheduledTakeOffDateTime(flightView.getScheduledTakeOffDateTime());
//			fd.setEstimatedTakeOffDateTime(flightView.getEstimatedTakeOffDateTime());
//			fd.setActualTakeOffDateTime(flightView.getActualTakeOffDateTime());
//			fd.setScheduledNextAirportArrivalDateTime(flightView.getScheduledNextAirportArrivalDateTime());
//			fd.setEstimatedNextAirportArrivalDateTime(flightView.getEstimatedNextAirportArrivalDateTime());
//			fd.setActualNextAirportArrivalDateTime(flightView.getActualNextAirportArrivalDateTime());
//			fd.setBestKnownDateTime(flightView.getBestKnownDateTime());
//			fd.setOperationStatus(flightView.getOperationStatus());
//			fd.setFlightStatus(flightView.getFlightStatus());
//			fd.setDelayCode(flightView.getDelayCode());
//			fd.setDelayFreeText(flightView.getDelayFreeText());
//			fd.setDiversionAirportIATACode(flightView.getDiversionAirportIATACode());
//			fd.setDiversionAirportICAOCode(flightView.getDiversionAirportICAOCode());
//			fd.setChangeLandingAirportIATACode(flightView.getChangeLandingAirportIATACode());
//			fd.setChangeLandingAirportICAOCode(flightView.getChangeLandingAirportICAOCode());
//			fd.setDisplayCode(flightView.getDisplayCode());
//			fd.setIsTransitFlight(flightView.getIsTransitFlight());
//			fd.setIsOverNightFlight(flightView.getIsOverNightFlight());
//			fd.setIsCashFlight(flightView.getIsCashFlight());
//			fd.setPayloadWeight(flightView.getPayloadWeight());
//			fd.setPassengerSeatingCapacity(flightView.getPassengerSeatingCapacity());
//			fd.setMaximumTakeoffWeight(flightView.getMaximumTakeoffWeight());
//			fd.setCrewNumber(flightView.getCrewNumber());
//			fd.setTotalPassengersNumber(flightView.getTotalPassengersNumber());
//			fd.setInternationalPassengersNumber(flightView.getInternationalPassengersNumber());
//			fd.setDomesticPassengersNumber(flightView.getDomesticPassengersNumber());
//			fd.setTransitPassengersNumber(flightView.getTransitPassengersNumber());
//			fd.setTotalChildrenNumber(flightView.getTotalChildrenNumber());
//			fd.setRequireAssitancePassengersNumber(flightView.getRequireAssitancePassengersNumber());
//			fd.setTotallBaggageWeight(flightView.getTotallBaggageWeight());
//			fd.setInternationalBaggageWeight(flightView.getInternationalBaggageWeight());
//			fd.setDomesticBaggageWeight(flightView.getDomesticBaggageWeight());
//			fd.setTransitlBaggageWeight(flightView.getTransitlBaggageWeight());
//			fd.setTotalCargoWeight(flightView.getTotalCargoWeight());
//			fd.setInternationalCargoWeight(flightView.getInternationalCargoWeight());
//			fd.setDomesticCargoWeight(flightView.getDomesticCargoWeight());
//			fd.setTransitCargoWeight(flightView.getTransitCargoWeight());
//			fd.setTotallMailWeight(flightView.getTotallMailWeight());
//			fd.setInternationalMailWeight(flightView.getInternationalMailWeight());
//			fd.setDomesticMailWeight(flightView.getDomesticMailWeight());
//			fd.setTransitlMailWeight(flightView.getTransitlMailWeight());
//
//			FlightResource fr = new FlightResource();
//			fr.setId(flightView.getFlightResourceId());
//			fr.setFlightTerminalID(flightView.getFlightTerminalID());
//			fr.setAircraftTerminalID(flightView.getAircraftTerminalID());
//			fr.setRunwayID(flightView.getRunwayID());
//			fr.setStandID(flightView.getStandID());
//			fr.setScheduledStandStartDateTime(flightView.getScheduledStandStartDateTime());
//			fr.setScheduledStandEndDateTime(flightView.getScheduledStandEndDateTime());
//			fr.setEstimatedStandStartDateTime(flightView.getEstimatedStandStartDateTime());
//			fr.setEstimcatedStandEndDateTime(flightView.getEstimcatedStandEndDateTime());
//			fr.setActualStandStartDateTime(flightView.getActualStandStartDateTime());
//			fr.setActualStandEndDateTime(flightView.getActualStandEndDateTime());
//			fr.setBaggageBeltID(flightView.getBaggageBeltID());
//			fr.setBaggageBeltStatus(flightView.getBaggageBeltStatus());
//			fr.setScheduledMakeupStartDateTime(flightView.getScheduledMakeupStartDateTime());
//			fr.setScheduledMakeupEndDateTime(flightView.getScheduledMakeupEndDateTime());
//			fr.setActualMakeupStartDateTime(flightView.getActualMakeupStartDateTime());
//			fr.setActualMakeupEndDateTime(flightView.getActualMakeupEndDateTime());
//			fr.setGateID(flightView.getGateID());
//			fr.setGateStatus(flightView.getGateStatus());
//			fr.setScheduledGateStartDateTime(flightView.getScheduledGateStartDateTime());
//			fr.setScheduledGateEndDateTime(flightView.getScheduledGateEndDateTime());
//			fr.setActualGateStartDateTime(flightView.getActualGateStartDateTime());
//			fr.setActualGateEndDateTime(flightView.getActualGateEndDateTime());
//			fr.setBaggageReclaimID(flightView.getBaggageReclaimID());
//			fr.setScheduledFirstBaggageDateTime(flightView.getScheduledFirstBaggageDateTime());
//			fr.setScheduledLastBaggageDateTime(flightView.getScheduledLastBaggageDateTime());
//			fr.setActualFirstBaggageDateTime(flightView.getActualFirstBaggageDateTime());
//			fr.setActualLastBaggageDateTime(flightView.getActualLastBaggageDateTime());
//			fr.setOverViewCheckInRange(flightView.getOverViewCheckInRange());
//			fr.setOverViewCheckInType(flightView.getOverViewCheckInType());
//			fr.setScheduledCheckInFirstBeginDateTime(flightView.getScheduledCheckInFirstBeginDateTime());
//			fr.setScheduledCheckInLastEndDateTime(flightView.getScheduledCheckInLastEndDateTime());
//			fr.setActualCheckInFirstBeginDateTime(flightView.getActualCheckInFirstBeginDateTime());
//			fr.setAcutalCheckInLastEndDateTime(flightView.getAcutalCheckInLastEndDateTime());
//			fr.setCheckInID(flightView.getCheckInID());
//			fr.setCheckInType(flightView.getCheckInType());
//			fr.setCheckInStatus(flightView.getCheckInStatus());
//			fr.setCheckInClassService(flightView.getCheckInClassService());
//			fr.setScheduledCheckInBeginDateTime(flightView.getScheduledCheckInBeginDateTime());
//			fr.setScheduledCheckInEndDateTime(flightView.getScheduledCheckInEndDateTime());
//			fr.setActualCheckInBeginDateTime(flightView.getActualCheckInBeginDateTime());
//			fr.setAcutalCheckInEndDateTime(flightView.getAcutalCheckInEndDateTime());
//			fr.setClosingScheduledFromDateTime(flightView.getClosingScheduledFromDateTime());
//			fr.setClosingScheduledEndDateTime(flightView.getClosingScheduledEndDateTime());
//			fr.setClosingActualEndDateTime(flightView.getClosingActualEndDateTime());
//			fr.setCommentFreeText(flightView.getCommentFreeText());
//
//			fb.setFlightData(fd);
//			fb.setFlightResource(fr);
//			indexMap.put(fb.getId(), fb);
//		}
//		for (FlightView flightView : viewList) {
//			FlightBase fb = indexMap.get(flightView.getId());
//			if (flightView.getLinkFlightId() != null && flightView.getLinkFlightId() != 0l) {
//				fb.setLinkFlight(indexMap.get(flightView.getLinkFlightId()));
//			}
//			resultList.add(fb);
//		}
//		return resultList;
//	}

	public long getPeriodFlightCount(Date startdate, Date enddate) {
		return flightBaseDao.getPeriodFlightCount(startdate, enddate);
	}
	
	
	public List<FlightBase> getPeriodFlight(Date startdate, Date enddate) {
		HashMap<String, Object> filters = new HashMap<String, Object>();
//		List<String> sorts = new ArrayList<>();
//		sorts.add("flightData.flightScheduledDateTime_asc");
		filters.put("flightData.flightScheduledDateTime_greaterThanOrEqualTo", startdate);
		filters.put("flightData.flightScheduledDateTime_lessThanOrEqualTo", enddate);
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		return findBy(filters);
	}

	/**
	 * @param startdate
	 * @param enddate
	 * @param size　每轮的航班数
	 * @param round　第几轮
	 * @return
	 */
	public List<FlightBase> getPeriodFlightByPage(Date startdate, Date enddate, int size, int round) {
		return flightBaseDao.getPeriodFlightByPage(startdate, enddate, size, round);
		
//		HashMap<String, Object> filters = new HashMap<String, Object>();
//		List<String> sorters = new ArrayList<String>();
//		sorters.add("modifyTime_desc");
//		sorters.add("createTime_desc");
//
//		// 生成limit和start,记录的总数和开始位置
//		int start = 0, limit = 0;
//		start = size * (round - 1);
//		limit = size;
//
//		filters.put("flightScheduledDate_greaterThanOrEqualTo", startdate);
//		filters.put("flightScheduledDate_lessThanOrEqualTo", enddate);
//		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
//
//		return findBy(filters, sorters, start, limit);
	}
	/**
     * 通过航班唯一约束取得一个航班
     * @param flightIdentity
     * @param scheduleDate
     * @param direction
     * @return
     */
    public FlightBase findFlightBaseInDb(String flightIdentity, Date scheduleDate, String direction) {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("flightScheduledDate_equal", scheduleDate);
        filters.put("flightIdentity_equal", flightIdentity);
        filters.put("flightDirection_equal", direction);
        filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
        List<FlightBase> flights = findByInDB(filters,null);
        if (flights == null || flights.size() == 0)
            return null;
        return flights.get(0);
    }
	/**
	 * 通过航班唯一约束取得一个航班
	 * @param flightIdentity
	 * @param scheduleDate
	 * @param direction
	 * @return
	 */
	public FlightBase findFlightBase(String flightIdentity, Date scheduleDate, String direction) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("flightScheduledDate_equal", scheduleDate);
		filters.put("flightIdentity_equal", flightIdentity);
		filters.put("flightDirection_equal", direction);
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		List<FlightBase> flights = findBy(filters);
		if (flights == null || flights.size() == 0)
			return null;
		return flights.get(0);
	}

	/**
	 * 航班界面搜索逻辑
	 * 
	 * @return 最后返回由3个list拼接而成，如果未找到，list是size() = 0而不是null
	 */
	public List<FlightBase> findFlightBase(Date startTime, Date endTime, String flightIdentity, FlightBase.TYPE flightDirection, String aircraftIATACode, String flightRegisteration) {
		return findFlightBase(startTime, endTime, flightIdentity, flightDirection, aircraftIATACode, flightRegisteration, false);
	}
	
	/**
	 * 航班界面搜索逻辑
	 * 
	 * @return 最后返回由3个list拼接而成，如果未找到，list是size() = 0而不是null
	 */
	@SuppressWarnings("unchecked")
	public List<FlightBase> findFlightBase(Date startTime, Date endTime, String flightIdentity, FlightBase.TYPE flightDirection, String aircraftIATACode, String flightRegisteration, boolean isHistoryQuery) {

		// 默认取传入时间的前后6小时航班数据.range = 6
		int range = 6;
		Date dateStart;
		Date dateEnd;
		List<FlightBase> result = new ArrayList<>();
		// List<FlightBase> result = new ArrayList<>();
		List<FlightBase> flights = new ArrayList<>();
		List<FlightBase> thatDayFlights = new ArrayList<>();
		List<FlightBase> linkedFlights = new ArrayList<>();
		List<FlightBase> unFinishedFlights = new ArrayList<>();
		// 先根据粗略的时间(startTime-rang)~(endTime+range)、航班号、进离港、机号、机型找出一个小范围的航班cache
		System.out.println("cacheSize:" + DataCache.getFlightBases().size());
		System.out.println("搜索日期从" + DateUtils.getDateBeforeHour(startTime, range).toString() + "到" + DateUtils.getDateAfterHour(endTime, range).toString());

		
		if(isHistoryQuery){
			dateStart=startTime;
			dateEnd=endTime;
			DateUtils.algorithmStart();
			flights = getFlightBaseFromDataBase(dateStart, dateEnd, flightIdentity, flightDirection, aircraftIATACode, flightRegisteration);
			System.out.println("history_search");
			System.out.println("数据库查询航班耗费:" + DateUtils.algorithmFinish() + "秒");
		}
		else{// 取出前后6小时的航班缓存
		    DateUtils.algorithmStart();
			dateStart = DateUtils.getDateBeforeHour(startTime, range);
			dateEnd = DateUtils.getDateAfterHour(endTime, range);
			flights = getFlightBaseFromCache(dateStart, dateEnd, flightIdentity, flightDirection, aircraftIATACode, flightRegisteration);
            System.out.println("缓存查询航班耗费:" + DateUtils.algorithmFinish() + "秒");
		}
		System.out.println("cacheSize_after:" + flights.size());

		// 1.遍历航班cache找出当天的符合航班
		DateUtils.algorithmStart();
		thatDayFlights = getThatDayFlight(startTime, endTime, flights);
		System.out.println("找出当天的符合航班耗费:" + DateUtils.algorithmFinish() + "秒");
		System.out.println("size:" + thatDayFlights.size());

		// 2.找出有连班且连班未在结果内的航班
		DateUtils.algorithmStart();
		// 查找连班和全部的时候需要找linkedFlights，进港离港不需要
		if (flightDirection == null || flightDirection.getValue().equals(FlightBase.TYPE.LINKED_FLIGHT.getValue())) {
			linkedFlights = getLinkedFlight(startTime, endTime, thatDayFlights);
		}
		System.out.println("找出连班未在结果内的航班耗费:" + DateUtils.algorithmFinish() + "秒");
		System.out.println("size:" + linkedFlights.size());

		// 3.找出未完成sp的航班,（假设）总是在那天之前的range小时内
		// fqdb的不记未完成航班

		// 1+2+3得出的就是命中的航班
		result.addAll(unFinishedFlights);
		result.addAll(thatDayFlights);
		result.addAll(linkedFlights);
		
	/*	//写入共享航班号 
		for(FlightBase fb:result){
			if("Y".equals(fb.getIsMasterFlight())){
				String sharedFlightIdentity = masterSlaveRelationManager.findNameByMasterSlaveRelation(fb.getId());
				fb.setSharedFlightIdentity(sharedFlightIdentity);
			}
		}
		*/
		

		// 排序,因为Flightbase 继承comparable
		DateUtils.algorithmStart();
		Collections.sort(result);
		System.out.println("后台排序耗费:" + DateUtils.algorithmFinish() + "秒");

		return result;
	
	}

	/**
	 * 从航班内存里找出指定日期的航班
	 * @author ninja
	 * @param start
	 * @param end
	 * @param flightIdentity
	 * @param flightDirection
	 * @param aircraftIATACode
	 * @param flightRegisteration
	 * @return
	 */
	private List<FlightBase> getFlightBaseFromCache(Date start, Date end, String flightIdentity, FlightBase.TYPE flightDirection, String aircraftIATACode, String flightRegisteration) {
		List<FlightBase> cacheFlights = DataCache.getFlightBases();
		List<FlightBase> list = new ArrayList<>();
		for (FlightBase flight : cacheFlights) {
			if (isFlightMeetQueryPara(flight, start, end, flightIdentity, flightDirection, aircraftIATACode, flightRegisteration)) {
				// 加入结果集里
				list.add(flight);
			}
		}
		return list;
	}

	/**
	 * 从数据库中找到航班
	 * @param start
	 * @param end
	 * @param flightIdentity
	 * @param flightDirection
	 * @param aircraftIATACode
	 * @param flightRegisteration
	 * @return
	 */
	private List<FlightBase> getFlightBaseFromDataBase(Date start, Date end, String flightIdentity, FlightBase.TYPE flightDirection, String aircraftIATACode, String flightRegisteration) {
//		Map<String, Object> filters = new HashMap<>();
	/*	if(start != null)
			filters.put("flightData.flightScheduledDateTime_greaterThan", start);
		if(end != null)	
			filters.put("flightData.flightScheduledDateTime_lessThan", end);
		if(flightIdentity != null && !flightIdentity.isEmpty())
			filters.put("flightIdentity_equal", flightIdentity);
		if(flightDirection != null)
			filters.put("flightDirection_equal", flightDirection.getValue());
		if(aircraftIATACode != null && !aircraftIATACode.isEmpty())
			filters.put("flightData.aircraftIATACode_equal", aircraftIATACode);
		if(flightRegisteration != null && !flightRegisteration.isEmpty())
			filters.put("flightData.registeration_equal", flightRegisteration);
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		//return super.findBy(filters);
*/		//super.findBy(filters);
/*		Map<String, Object> filters = new HashMap<>();
		if(start != null)
			filters.put("flightScheduledDateTime", start);
		if(end != null)	
			filters.put("flightScheduledDateTime", end);
		if(flightIdentity != null && !flightIdentity.isEmpty())
			filters.put("flightIdentity", flightIdentity);
		if(flightDirection != null)
			filters.put("flightDirection", flightDirection.getValue());
		if(aircraftIATACode != null && !aircraftIATACode.isEmpty())
			filters.put("aircraftIATACode", aircraftIATACode);
		if(flightRegisteration != null && !flightRegisteration.isEmpty())
			filters.put("registeration", flightRegisteration);
		filters.put("removeFlag", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());*/
		
		List<FlightView> viewList = flightViewDao.findBySql(start, end, flightIdentity,  flightDirection,  aircraftIATACode,  flightRegisteration);
		DataCache a = new DataCache();
		List<FlightBase> bs = a.convertFlightView(viewList);
		return bs;//super.findBy(filters);
//	List<FlightBase> bs = super.findBy(filters);
//		System.out.println("#####################7###4################"+bs.size());
//		return bs;
	}
	/*	*//**
			* data数据集里是否有target数据
			* 
			* @author ninja
			* @param data
			* @param target
			* @return
			*/
	/*
	 * private boolean isExist(BigDecimal[] data, Long target) {
	 * 
	 * for (BigDecimal datum : data) { if (datum == null || target == null)
	 * return false; else if (datum.longValue() == target) return true; }
	 * 
	 * return false; }
	 */

	/**
	 * 从航班内存里找出指定日期的航班
	 * 
	 * @return
	 */
	private List<FlightBase> getThatDayFlight(Date start, Date end, List<FlightBase> flights) {
		List<FlightBase> cacheFlights = flights;

		List<FlightBase> result = new ArrayList<>();
		for (FlightBase flight : cacheFlights) {
			// if(flight.getRemoveFlag() ==
			// CommonData.REMOVEFLAG.REMOVED.getValue()) continue;
			if (isFlightThatDay(start, end, flight))
				result.add(flight);
		}
		return result;
	}

	/**
	 * @param flights
	 *            thatDayCache
	 * @param startTime
	 *            cache的时间边界
	 * @param endTime
	 *            cache的时间边界
	 * @return
	 */
	private List<FlightBase> getLinkedFlight(Date startTime, Date endTime, List<FlightBase> flights) {
		List<FlightBase> result = new ArrayList<>();
		// 遍历，当连班航班不在cache里时,新增航班对应连班航班(连班航班要求是离港的)
		for (FlightBase flight : flights) {
			if (flight.getFlightDirection().equals(FlightBase.TYPE.INTO_PORT.getValue()) && flight.getLinkFlight() != null) {
				Date date = flight.getLinkFlight().getFlightTime();
				// 当连班航班不在cache里
				if (!DateUtils.isDateDuringPeriod(date, startTime, endTime))
					result.add(flight.getLinkFlight());
			}
			continue;

		}

		return result;
	}

	/**
	 * 从航班内存里找出前一天未完成的航班
	 * 
	 * @return
	 */
	private List<FlightBase> getUnfinishedFlight(Date start, Date end, List<FlightBase> flights) {
		List<FlightBase> cacheFlights = flights;
		List<FlightBase> result = new ArrayList<>();
		for (FlightBase flight : cacheFlights) {
			if (isFlightUnfinished(flight, start, end)) {
				result.add(flight);
			}
		}
		return result;
	}

	/**
	 * 作用：判断航班是否符合未完成服务计划逻辑 逻辑：如果航班没有服务确认单 或者航班的sp开始时间和sp结束时间都为空 返回 true （符合）
	 * 
	 * @param flight
	 * @return
	 */
	private boolean isFlightUnfinished(FlightBase flight, Date start, Date end) {
		try {
			Date date = flight.getFlightTime();
			if (date == null)
				return false;
			//lushuaifeng 20160906
//			if (DateUtils.isDateDuringPeriod(date, start, end)) {
//				if (flight.getFlightServiceInfo() == null || ((flight.getFlightServiceInfo().getServicePlanStartTime() == null && flight.getFlightServiceInfo().getServicePlanEndTime() == null))) {
//					return true;
//				}
//
//			} else {
//				return false;
//			}
			
			if (!DateUtils.isDateDuringPeriod(date, start, end)) {

				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// it works ,but toooo dirty
		// if (DateUtils.isDateDuringPeriod(date, start, end) &&
		// (flight.getFlightServiceInfo() == null ||
		// (flight.getFlightServiceInfo().getServicePlanEndTime() == null &&
		// flight.getFlightServiceInfo().getServicePlanStartTime() == null))) {
		// return true;
		// }
		return false;
	}

	/**
	 * 判断航班时间是否在start和end之间
	 * 
	 * @param start
	 * @param end
	 * @param flights
	 * @return
	 */
	private boolean isFlightThatDay(Date start, Date end, FlightBase flight) {
		try {
			Date date = flight.getFlightTime();
			// Date date = flight.getFlightData().getFlightScheduledDateTime();
			// if (DateUtils.isDateDuringPeriod(date, start, end))
			if (DateUtils.isDateDuringPeriod(date, start, end))
				return true;
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	/**
	 * 航班是否命中查询条件
	 * 隐藏条件：必须是主航班
	 * @param flight
	 * @return
	 */
	private boolean isFlightMeetQueryPara(FlightBase flight, Date startTime, Date endTime, String flightIdentity, FlightBase.TYPE flightDirection, String aircraftIATACode, String flightRegisteration) {
		Date date = flight.getFlightTime();
		boolean hit = true;

		// 是否删除，必有
		
		 if (flight.getRemoveFlag() != null) { 
		     hit = hit &&	 CommonData.REMOVEFLAG.NOT_REMOVED.getValue().equals(flight.getRemoveFlag()); 
		} else { 
		    return false; 
		}
		

		// 判断日期是否符合，必有
		if (startTime != null && endTime != null && date != null) {
			hit = hit && DateUtils.isDateDuringPeriod(date, startTime, endTime);
		} else {
			return false;
		}

		// 判进离港。0是进港，1是离港，可填
		if (flightDirection != null) {
			if (flight.getFlightDirection() != null) {
				hit = hit && flightDirection.getValue().equals(flight.getFlightDirection());
			} else {
				return false;
			}
		} else {
			hit &= true;
		}

		// 判断航班号,用like做(tothink),可填
		if (flightIdentity != null && !flightIdentity.isEmpty()) {
			if (flight.getFlightIdentity() != null) {
				hit = hit && (flight.getFlightIdentity().indexOf(flightIdentity) > -1);
			} else {
				return false;
			}
		} else {
			hit &= true;
		}

		// 判断机型,可填
		if (aircraftIATACode != null && !aircraftIATACode.isEmpty()) {
			if (flight.getFlightData().getAircraftIATACode() != null) {
				hit = hit && (flight.getFlightData().getAircraftIATACode().indexOf(aircraftIATACode) > -1);
			} else {
				return false;
			}
		} else {
			hit &= true;
		}

		// 判断机号,可填
		if (flightRegisteration != null && !flightRegisteration.isEmpty()) {
			if (flight.getFlightData().getRegisteration() != null) {
				hit = hit && (flight.getFlightData().getRegisteration().indexOf(flightRegisteration) > -1);
			} else {
				return false;
			}
		} else {
			hit &= true;
		}

		 //* 隐藏条件：必须是主航班
		if("Y".equals(flight.getIsMasterFlight()))
			hit &= true;
		else
			hit &= false;
		
		return hit;
	}

	/**
	 * 初始化航班业务时间
	 * @param flightView
	 * @return
	 */
	public static void initFlightTime(FlightBase fb) {
		// 设置航班业务时间（进港航班取落地时间，离港航班取起飞时间）
		Date _date = new Date();

		// 如果是进港航班
		if (fb.getFlightDirection() != null && FlightBase.TYPE.INTO_PORT.getValue().equals(fb.getFlightDirection())) {
			if (fb.getFlightData().getEstimatedLandingDateTime() != null) {
				_date = fb.getFlightData().getEstimatedLandingDateTime();
			} else if (fb.getFlightData().getScheduledLandingDateTime() != null) {
				_date = fb.getFlightData().getScheduledLandingDateTime();
			} else {
				_date = DateUtils.getFirstMinuteOfOneday(fb.getFlightScheduledDate());
			}
		}
		// 如果是离港航班
		else if (fb.getFlightDirection() != null && FlightBase.TYPE.OUT_PORT.getValue().equals(fb.getFlightDirection())) {
			if (fb.getFlightData().getEstimatedTakeOffDateTime() != null) {
				_date = fb.getFlightData().getEstimatedTakeOffDateTime();
			} else if (fb.getFlightData().getScheduledTakeOffDateTime() != null) {
				_date = fb.getFlightData().getScheduledTakeOffDateTime();
			} else {
				_date = DateUtils.getFirstMinuteOfOneday(fb.getFlightScheduledDate());
			}
		} else {
			_date = null;
		}
		fb.setFlightTime(_date);
	}

//add by march 20140827 导入配载信息 start
    @Override
    public List<FlightBase> findByInDB(Map<String, Object> filters,
            List<String> sorters) {
        return flightBaseDao.findBy(filters, sorters,-1,-1);
    }


    @Override
    public Map<String, Object> importLoadDataByExcel2003(byte[] bytes)  throws IOException {
        @SuppressWarnings("unused")
        int addCounter = 0, modifyCounter = 0;
        List<FlightBase> flightBaseAllList = new ArrayList<>();
        List<FlightBase> flightBaseErrorList = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        @SuppressWarnings("rawtypes")
        Map<String, Object> result =  importFlightBaseByExcel.ImportFlightBaseLoadDataByExcel2003(inputStream);
        List<FlightBase> resultlist = (List<FlightBase>) result.get("result");
        flightBaseAllList = (List<FlightBase>) resultlist.get(0);
        flightBaseErrorList = (List<FlightBase>) resultlist.get(1);
        if (flightBaseErrorList.size() == 0) {
            for (FlightBase flightBase : flightBaseAllList) {
                if (buildFlightBaseFromLoadDate(flightBase)) {
                    // 更新记录：update
                    flightBase.setModifyUser(FQDBMessages
                            .getString("ADMINISTARTOR"));
                    flightBase.setModifyTime(new Date());
                    this.save(flightBase);
                    addCounter++;
                } else {
                    // 新增记录：insert
//                    flightBase.setCreateUser(FQDBMessages
//                            .getString("ADMINISTARTOR"));
//                    flightBase.setCreateTime(new Date());
//                    this.save(flightBase);
//                    modifyCounter++;
                }
            }
        }
        Map<String, Object> flightBaseResult = new HashMap<>();
        flightBaseResult.put("list", flightBaseAllList);
        flightBaseResult.put("errorMessage", result.get("resultMessage"));
        return flightBaseResult;
    }


    @Override
    public Map<String, Object> importLoadDataByExcel2007(byte[] bytes) throws IOException {
        List<FlightBase> flightBaseAllList = new ArrayList<>();
        List<FlightBase> flightBaseErrorList = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        @SuppressWarnings("rawtypes")
        Map<String, Object> result = importFlightBaseByExcel.ImportFlightBaseLoadDataByExcel2007(inputStream);
        List<FlightBase> resultlist = (List<FlightBase>) result.get("result");
        flightBaseAllList = (List<FlightBase>) resultlist.get(0);
        flightBaseErrorList = (List<FlightBase>) resultlist.get(1);
        if (flightBaseErrorList.size() == 0) {
            for (FlightBase flightBase : flightBaseAllList) {
                if (buildFlightBaseFromLoadDate(flightBase)) {
                    // 更新记录：update
                    flightBase.setModifyUser(FQDBMessages.getString("ADMINISTARTOR"));
                    flightBase.setModifyTime(new Date());
                    this.save(flightBase);
                } else {
//                    // 新增记录：insert
//                    flightBase.setCreateUser(FQDBMessages.getString("ADMINISTARTOR"));
//                    flightBase.setCreateTime(new Date());
//                    this.save(flightBase);
                }
            }
        }
        Map<String, Object> flightBaseResult = new HashMap<>();
        flightBaseResult.put("list", flightBaseAllList);
        flightBaseResult.put("errorMessage", result.get("resultMessage"));
        return flightBaseResult;
    }
    
    public boolean buildFlightBaseFromLoadDate(FlightBase flightBase) {
        String flightIdentity, flightDirection;
        Map<String, Object> filters = new HashMap<String, Object>();
        flightIdentity = flightBase.getFlightIdentity();
        flightDirection = flightBase.getFlightDirection();
        filters.put("flightScheduledDate_equal", flightBase.getFlightScheduledDate());
        filters.put("flightIdentity_equal", flightIdentity);
        filters.put("flightDirection_equal", flightDirection);
        filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
        List<FlightBase> flightBaseList = super.findBy(filters, null, -1, -1);
        if (flightBaseList.size() > 0) {
            FlightBase fb=flightBaseList.get(0);
            if (fb.getFlightData() != null) {
               fb.getFlightData().setTotalAdultPassengersNumber(            flightBase.getFlightData().getTotalAdultPassengersNumber());         
               fb.getFlightData().setTotalChildrenNumber(                   flightBase.getFlightData().getTotalChildrenNumber());                
               fb.getFlightData().setTotalInfantPassengersNumber(         flightBase.getFlightData().getTotalInfantPassengersNumber());        
                                                                                                                                    
               fb.getFlightData().setTotallBaggageWeight(                 flightBase.getFlightData().getTotallBaggageWeight());                
               fb.getFlightData().setTotallMailWeight(                    flightBase.getFlightData().getTotallMailWeight());                   
               fb.getFlightData().setTotalCargoWeight(                    flightBase.getFlightData().getTotalCargoWeight());                   
                                                                                                                                    
               fb.getFlightData().setTransitAdultPassgerNum(             flightBase.getFlightData().getTransitAdultPassgerNum());             
               fb.getFlightData().setTransitChildPassgerNum(             flightBase.getFlightData().getTransitChildPassgerNum());             
               fb.getFlightData().setTransitInfantPassgerNum(            flightBase.getFlightData().getTransitInfantPassgerNum());            
               fb.getFlightData().setTotalAdultPassengersNumber(         flightBase.getFlightData().getTotalAdultPassengersNumber());             
               fb.getFlightData().setTotalChildrenNumber(                flightBase.getFlightData().getTotalChildrenNumber());                
               fb.getFlightData().setTotalInfantPassengersNumber(        flightBase.getFlightData().getTotalInfantPassengersNumber());        
                                                                                                                                    
               fb.getFlightData().setTotallBaggageWeight(                flightBase.getFlightData().getTotallBaggageWeight());                
               fb.getFlightData().setTotallMailWeight(                   flightBase.getFlightData().getTotallMailWeight());                   
               fb.getFlightData().setTotalCargoWeight(                   flightBase.getFlightData().getTotalCargoWeight());                   
            }else{
                return false;
            }
            flightBase=fb;
            return true;
        } else {
            return false;
        }
    }
  //add by march 20140827 导入配载信息 end
    
    /**
     * 根据航班的实际时间从库中获取主航班 
     **/
    public List<FlightBase> getFlightBaseFromDataBaseWithActualTime(Date start, Date end) {
        List<FlightView> viewList = flightViewDao.findBySqlWithActualTime(start, end);
        DataCache a = new DataCache();
        List<FlightBase> bs = a.convertFlightView(viewList);
        return bs;
    }
    
    /**
     * 根据航班的id从库中获取主航班 
     **/
    public List<FlightBase> getFlightBaseFromDataBaseWithId(Long id) {
        List<FlightView> viewList = flightViewDao.findBySqlWithId(id);
        DataCache a = new DataCache();
        List<FlightBase> bs = a.convertFlightView(viewList);
        return bs;
    }
    
  //add by march 为解决双机缓存不一致而增加的方法
    //刷新单条航班
    @Override
    public void refreshFlightInCache(FlightBase entity){
        if(entity!=null && entity instanceof FlightBase){
            List<FlightBase> flightBases = DataCache.getFlightBases();
            dataHandler.save(flightBases, entity);
        }
    }
    //add by march 为解决双机缓存不一致而增加的方法
    
    public List<FlightBase> getFlightBaseMisFlightMateInfoByActualTime(Date start, Date end) {
        List<FlightView> viewList = flightViewDao.findFlightBaseMisFlightMateInfoByActualTime(start, end);
        DataCache a = new DataCache();
        List<FlightBase> bs = a.convertFlightView(viewList);
        return bs;
    }
}
