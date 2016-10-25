package com.nlia.fqdb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.dao.AircraftAirlineAlterationDao;
import com.nlia.fqdb.dao.DiscountDao;
import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.Discount;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.message.FQDBMessages;
import com.nlia.fqdb.service.IAircraftAirlineAlterationManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.ImportAircraftAirlineAlterationByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class AircraftAirlineAlterationManager extends AbstractCrudService<AircraftAirlineAlteration, Long> implements IAircraftAirlineAlterationManager{

	@Resource
	private AircraftAirlineAlterationDao aircraftAirlineAlterationDao;
	
	@Resource
	private ImportAircraftAirlineAlterationByExcel importAircraftAirlineAlterationByExcel;
	
	@Resource
	private DiscountManager discountManager;
	
	
	
	
	/** 
	 * 
	 */
	@Transactional
	@Override
	public Object[] addAircraftAirlineAlteration(AircraftAirlineAlteration aircraftAirlineAlteration){
	    List<Discount> discounts;
		Map<String, Object> filters = new HashMap<>();
		filters.put("aircraftRegistration_equal", aircraftAirlineAlteration.getAircraftRegistration());
		filters.put("startDate_lessThan", aircraftAirlineAlteration.getStartDate());
		filters.put("endDate_greaterThan", aircraftAirlineAlteration.getEndDate());
		filters.put("originalSubairline_equal", aircraftAirlineAlteration.getOriginalAirline());
		filters.put("currentSubairline_equal", aircraftAirlineAlteration.getCurrentSubairline());
		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
		
		List<String> sorters = new ArrayList<>();
		sorters.add("startDate_asc");
		Object[] objects;
		List<AircraftAirlineAlteration> tempAircraftAirlineAlterations = findBy(filters, sorters);
		if (tempAircraftAirlineAlterations != null && tempAircraftAirlineAlterations.size() != 0) {
			objects = new Object[3];
			objects[0] = false;
		}else{
		    discounts=aircraftAirlineAlteration.getDiscounts();
			objects = new Object[3];
			objects[0] = true;
			tempAircraftAirlineAlterations.clear();
			tempAircraftAirlineAlterations.add(aircraftAirlineAlteration);
			tempAircraftAirlineAlterations = (List<AircraftAirlineAlteration>) save(tempAircraftAirlineAlterations);
			objects[1] = tempAircraftAirlineAlterations;
			
			if(discounts.size()>0){
			    for(Discount discount:discounts){
			        discount.setAircraftAirlineAlterationId(tempAircraftAirlineAlterations.get(0).getId());
			    }
			    objects[2]=discountManager.save(discounts);
	        }
		}
//		Object[] objects = isAircraftAirlineAlterationMatch(aircraftAirlineAlteration, tempAircraftAirlineAlterations);
//		aircraftAirlineAlterations = (List<AircraftAirlineAlteration>) objects[1];
//		if ((boolean)objects[0]) {
//			if (aircraftAirlineAlterations != null) {
//				aircraftAirlineAlterations.add(aircraftAirlineAlteration);
//				aircraftAirlineAlterations = (List<AircraftAirlineAlteration>) save(aircraftAirlineAlterations);
//			}else{
//				aircraftAirlineAlteration = save(aircraftAirlineAlteration);
//				aircraftAirlineAlterations = new ArrayList<>();
//				aircraftAirlineAlterations.add(aircraftAirlineAlteration);
//			}
//		}
//		objects[1] = aircraftAirlineAlterations;
		
		return objects;
	}
	
	@Transactional
	@Override
	public Object[] modifyAircraftAirlineAlteration(AircraftAirlineAlteration aircraftAirlineAlteration){
	    List<Discount> discounts;
//		List<AircraftAirlineAlteration> tempAircraftAirlineAlterations = new ArrayList<>();
		List<AircraftAirlineAlteration> aircraftAirlineAlterations = new ArrayList<>();
//		Map<String, Object> filters = new HashMap<>();
//		filters.put("aircraftRegistration_equal", aircraftAirlineAlteration.getAircraftRegistration());
//		filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
//		
//		List<String> sorters = new ArrayList<>();
//		sorters.add("startDate_asc");
//		
//		tempAircraftAirlineAlterations = findBy(filters, sorters);
//		for (AircraftAirlineAlteration tempAircraftAirlineAlteration : tempAircraftAirlineAlterations) {
//			if (aircraftAirlineAlteration.getId().equals(tempAircraftAirlineAlteration.getId())) {
//				tempAircraftAirlineAlterations.remove(tempAircraftAirlineAlteration);
//				if (tempAircraftAirlineAlterations == null || tempAircraftAirlineAlterations.size() <= 0) {
//					break;
//				}
//			}
//		}
//		Object[] objects = isAircraftAirlineAlterationMatch(aircraftAirlineAlteration, tempAircraftAirlineAlterations);
//		aircraftAirlineAlterations = (List<AircraftAirlineAlteration>) objects[1];
//		if ((boolean)objects[0]) {
//			if (aircraftAirlineAlterations != null) {
//				aircraftAirlineAlterations.add(aircraftAirlineAlteration);
		Object[] objects = new Object[3];
		try {
		    discounts=aircraftAirlineAlteration.getDiscounts();
			aircraftAirlineAlteration = save(aircraftAirlineAlteration);
			aircraftAirlineAlterations.add(aircraftAirlineAlteration);
			objects[0] = true;
			if(discounts.size()>0){
                objects[2]=discountManager.save(discounts);
            }
		} catch (Exception e) {
			objects[0] = false;
		}
//			}else{
//				aircraftAirlineAlteration = save(aircraftAirlineAlteration);
//				aircraftAirlineAlterations = new ArrayList<>();
//				aircraftAirlineAlterations.add(aircraftAirlineAlteration);
//			}
//		}
				
		objects[1] = aircraftAirlineAlterations;
		return objects;
	}
	
	@Override
	protected GenericDao<AircraftAirlineAlteration, Long> getDao() {
		return aircraftAirlineAlterationDao;
	}
	
	/**
	 * 航空公司变更新增无交集逻辑判断
	 * @param aircraftAirlineAlteration
	 * @param aircraftAirlineAlterations
	 * @return object[2]
	 *  -object[0] aircraftAirlineAlteration是否满足可入库的条件的Boolean值
	 *  -object[1] aircraftAirlineAlteration满足条件时需要更改原数据库数据的list集合
	 */
	private Object[] isAircraftAirlineAlterationMatch(AircraftAirlineAlteration aircraftAirlineAlteration, List<AircraftAirlineAlteration> aircraftAirlineAlterations){
		Object[] objects = new Object[2];
		boolean flag = true;
		Date startDate = aircraftAirlineAlteration.getStartDate();
		Date endDate = aircraftAirlineAlteration.getEndDate();
		List<AircraftAirlineAlteration> tempAircraftAirlineAlterations = new ArrayList<>();
		for (AircraftAirlineAlteration tempAircraftAirlineAlteration : aircraftAirlineAlterations) {
			//新增航空公司变更在已有数据之间，则将已有数据1分为2，即一条开始时间不变结束时间为新增数据开始时间，一条结束时间不变开始时间为新增数据结束时间
			if (startDate.after(tempAircraftAirlineAlteration.getStartDate()) && endDate.before(tempAircraftAirlineAlteration.getEndDate())) {
				AircraftAirlineAlteration newAircraftAirlineAlteration = new AircraftAirlineAlteration();
				newAircraftAirlineAlteration = tempAircraftAirlineAlteration;
				newAircraftAirlineAlteration.setEndDate(startDate);
				tempAircraftAirlineAlteration.setStartDate(endDate);
				tempAircraftAirlineAlterations.add(newAircraftAirlineAlteration);
				tempAircraftAirlineAlterations.add(tempAircraftAirlineAlteration);
			//新增数据与已有数据后交叉，则将已有数据结束时间设置为新增数据开始时间	
			}else if (startDate.after(tempAircraftAirlineAlteration.getStartDate()) && startDate.before(tempAircraftAirlineAlteration.getEndDate()) && 
					endDate.after(tempAircraftAirlineAlteration.getEndDate())) {
				tempAircraftAirlineAlteration.setEndDate(startDate);
				tempAircraftAirlineAlterations.add(tempAircraftAirlineAlteration);
			//新增数据与已有数据前交叉，则将已有数据开始时间设置为新增数据结束时间
			}else if (startDate.before(tempAircraftAirlineAlteration.getStartDate()) && endDate.before(tempAircraftAirlineAlteration.getEndDate()) && 
					endDate.after(tempAircraftAirlineAlteration.getStartDate())) {
				tempAircraftAirlineAlteration.setStartDate(endDate);
				tempAircraftAirlineAlterations.add(tempAircraftAirlineAlteration);
			}else if(startDate.before(tempAircraftAirlineAlteration.getStartDate()) && endDate.before(tempAircraftAirlineAlteration.getStartDate()) || 
					startDate.after(tempAircraftAirlineAlteration.getEndDate()) && endDate.after(tempAircraftAirlineAlteration.getEndDate())){
				flag = true;
			}else{
				flag = false;
				break;
			}
		}
		objects[0] = flag;
		objects[1] = tempAircraftAirlineAlterations;
		return objects;
	}
//	/*
//	 * AircraftAirlineAlteration的变更需要同步修改Aircraft
//	 */
//	@Resource
//    private AircraftManager aircraftManager;
//    
//	@Override
//    public AircraftAirlineAlteration save(AircraftAirlineAlteration entity) {
//	    entity=super.save(entity);
//	    List<Aircraft> aircrafts = aircraftManager.findByAircraftRegistration(entity.getAircraftRegistration());
//	    //找到并且有变化，则更新
//	    if(aircrafts!=null && aircrafts.size()>0){
//	        Aircraft aircraft=aircrafts.get(0);
//	        boolean needSync=false;
//            if (entity.getAircraftTypeICAOCode() != null
//                    && !entity.getAircraftTypeICAOCode().equals(
//                            aircraft.getAircraftTypeICAOCode())) {
//                aircraft.setAircraftTypeICAOCode(entity.getAircraftTypeICAOCode());
//                needSync=true;
//            }
//            if (entity.getAircraftTypeIATACode() != null
//                    && !entity.getAircraftTypeIATACode().equals(
//                            aircraft.getAircraftTypeIATACode())) {
//                aircraft.setAircraftTypeIATACode(entity.getAircraftTypeIATACode());
//                needSync=true;
//            }
//            if (entity.getAircraftType() != null
//                    && !entity.getAircraftType().equals(
//                            aircraft.getAircraftType())) {
//                aircraft.setAircraftType(entity.getAircraftType());
//                needSync=true;
//            }
//            if (entity.getAircraftAirline() != null
//                    && !entity.getAircraftAirline().equals(
//                            aircraft.getAircraftAirline())) {
//                aircraft.setAircraftAirline(entity.getAircraftAirline());
//                needSync=true;
//            }
//            if (entity.getAircraftDescription() != null
//                    && !entity.getAircraftDescription().equals(
//                            aircraft.getAircraftDescription())) {
//                aircraft.setAircraftDescription(entity.getAircraftDescription());
//                needSync=true;
//            }
//            if (entity.getAircraftEngineNumber() != null
//                    && !entity.getAircraftEngineNumber().equals(
//                            aircraft.getAircraftEngineNumber())) {
//                aircraft.setAircraftEngineNumber(entity.getAircraftEngineNumber());
//                needSync=true;
//            }
//            if (entity.getAircraftTakeoffWeight() != null
//                    && !entity.getAircraftTakeoffWeight().equals(
//                            aircraft.getAircraftTakeoffWeight())) {
//                aircraft.setAircraftTakeoffWeight(entity.getAircraftTakeoffWeight());
//                needSync=true;
//            }
//            if (entity.getAircraftLandingWeight() != null
//                    && !entity.getAircraftLandingWeight().equals(
//                            aircraft.getAircraftLandingWeight())) {
//                aircraft.setAircraftLandingWeight(entity.getAircraftLandingWeight());
//                needSync=true;
//            }
//            if (entity.getAircraftPayload() != null
//                    && !entity.getAircraftPayload().equals(
//                            aircraft.getAircraftPayload())) {
//                aircraft.setAircraftPayload(entity.getAircraftPayload());
//                needSync=true;
//            }
//            if (entity.getAircraftSeatCapacity() != null
//                    && !entity.getAircraftSeatCapacity().equals(
//                            aircraft.getAircraftSeatCapacity())) {
//                aircraft.setAircraftSeatCapacity(entity.getAircraftSeatCapacity());
//                needSync=true;
//            }
//            if (entity.getAircraftNoiseCategory() != null
//                    && !entity.getAircraftNoiseCategory().equals(
//                            aircraft.getAircraftNoiseCategory())) {
//                aircraft.setAircraftNoiseCategory(entity.getAircraftNoiseCategory());
//                needSync=true;
//            }
//            if (entity.getAircraftHeight() != null
//                    && !entity.getAircraftHeight().equals(
//                            aircraft.getAircraftHeight())) {
//                aircraft.setAircraftHeight(entity.getAircraftHeight());
//                needSync=true;
//            }
//            if (entity.getAircraftLength() != null
//                    && !entity.getAircraftLength().equals(
//                            aircraft.getAircraftLength())) {
//                aircraft.setAircraftLength(entity.getAircraftLength());
//                needSync=true;
//            }
//            if (entity.getAircraftWidth() != null
//                    && !entity.getAircraftWidth().equals(
//                            aircraft.getAircraftWidth())) {
//                aircraft.setAircraftWidth(entity.getAircraftWidth());
//                needSync=true;
//            }
//            if (entity.getVerifyDescription() != null
//                    && !entity.getVerifyDescription().equals(
//                            aircraft.getVerifyDescription())) {
//                aircraft.setVerifyDescription(entity.getVerifyDescription());
//                needSync=true;
//            }
//            if (entity.getIsWideOrNarrow() != null
//                    && !entity.getIsWideOrNarrow().equals(
//                            aircraft.getIsWideOrNarrow())) {
//                aircraft.setIsWideOrNarrow(entity.getIsWideOrNarrow());
//                needSync=true;
//            }
//            if(needSync){
//                aircraft.setModifyTime(new Date());
//                aircraft.setModifyUser("admin");
//                aircraftManager.save(aircraft);
//            }
//	    }else{//没有，新增
//	        Aircraft aircraft=new Aircraft();
//	        aircraft.setAircraftRegistration(entity.getAircraftRegistration());
//	        aircraft.setRemoveFlag("1");
//	        aircraft.setBasicDataID(entity.getAircraftRegistration());
//            if (entity.getAircraftTypeICAOCode() != null
//                    && !entity.getAircraftTypeICAOCode().equals(
//                            aircraft.getAircraftTypeICAOCode())) {
//                aircraft.setAircraftTypeICAOCode(entity.getAircraftTypeICAOCode());
//            }
//            if (entity.getAircraftTypeIATACode() != null
//                    && !entity.getAircraftTypeIATACode().equals(
//                            aircraft.getAircraftTypeIATACode())) {
//                aircraft.setAircraftTypeIATACode(entity.getAircraftTypeIATACode());
//            }
//            if (entity.getAircraftType() != null
//                    && !entity.getAircraftType().equals(
//                            aircraft.getAircraftType())) {
//                aircraft.setAircraftType(entity.getAircraftType());
//            }
//            if (entity.getAircraftAirline() != null
//                    && !entity.getAircraftAirline().equals(
//                            aircraft.getAircraftAirline())) {
//                aircraft.setAircraftAirline(entity.getAircraftAirline());
//            }
//            if (entity.getAircraftDescription() != null
//                    && !entity.getAircraftDescription().equals(
//                            aircraft.getAircraftDescription())) {
//                aircraft.setAircraftDescription(entity.getAircraftDescription());
//            }
//            if (entity.getAircraftEngineNumber() != null
//                    && !entity.getAircraftEngineNumber().equals(
//                            aircraft.getAircraftEngineNumber())) {
//                aircraft.setAircraftEngineNumber(entity.getAircraftEngineNumber());
//            }
//            if (entity.getAircraftTakeoffWeight() != null
//                    && !entity.getAircraftTakeoffWeight().equals(
//                            aircraft.getAircraftTakeoffWeight())) {
//                aircraft.setAircraftTakeoffWeight(entity.getAircraftTakeoffWeight());
//            }
//            if (entity.getAircraftLandingWeight() != null
//                    && !entity.getAircraftLandingWeight().equals(
//                            aircraft.getAircraftLandingWeight())) {
//                aircraft.setAircraftLandingWeight(entity.getAircraftLandingWeight());
//            }
//            if (entity.getAircraftPayload() != null
//                    && !entity.getAircraftPayload().equals(
//                            aircraft.getAircraftPayload())) {
//                aircraft.setAircraftPayload(entity.getAircraftPayload());
//            }
//            if (entity.getAircraftSeatCapacity() != null
//                    && !entity.getAircraftSeatCapacity().equals(
//                            aircraft.getAircraftSeatCapacity())) {
//                aircraft.setAircraftSeatCapacity(entity.getAircraftSeatCapacity());
//            }
//            if (entity.getAircraftNoiseCategory() != null
//                    && !entity.getAircraftNoiseCategory().equals(
//                            aircraft.getAircraftNoiseCategory())) {
//                aircraft.setAircraftNoiseCategory(entity.getAircraftNoiseCategory());
//            }
//            if (entity.getAircraftHeight() != null
//                    && !entity.getAircraftHeight().equals(
//                            aircraft.getAircraftHeight())) {
//                aircraft.setAircraftHeight(entity.getAircraftHeight());
//            }
//            if (entity.getAircraftLength() != null
//                    && !entity.getAircraftLength().equals(
//                            aircraft.getAircraftLength())) {
//                aircraft.setAircraftLength(entity.getAircraftLength());
//            }
//            if (entity.getAircraftWidth() != null
//                    && !entity.getAircraftWidth().equals(
//                            aircraft.getAircraftWidth())) {
//                aircraft.setAircraftWidth(entity.getAircraftWidth());
//            }
//            if (entity.getVerifyDescription() != null
//                    && !entity.getVerifyDescription().equals(
//                            aircraft.getVerifyDescription())) {
//                aircraft.setVerifyDescription(entity.getVerifyDescription());
//            }
//            aircraft.setCreateTime(new Date());
//            aircraft.setCreateUser("admin");
//            aircraftManager.save(aircraft);
//	    }
//        return entity;
//	    
//	}
	
	 @Override
	    public Map<String, Object> importAircraftAirlineAlterationDataByExcel2003(byte[] bytes)  throws IOException {
	        @SuppressWarnings("unused")
	        
	        int addCounter = 0, modifyCounter = 0;
	        List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList = new ArrayList<>();
	        List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList = new ArrayList<>();
	        InputStream inputStream = new ByteArrayInputStream(bytes);
	        @SuppressWarnings("rawtypes")
	        Map<String, Object> result =  importAircraftAirlineAlterationByExcel.ImportAircraftAirlineAlterationDataByExcel2003(inputStream);
	        return SaveAndDel(result);
	    }


        @Override
	    public Map<String, Object> importAircraftAirlineAlterationDataByExcel2007(byte[] bytes) throws IOException {
            int addCounter = 0, modifyCounter = 0;
            List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList = new ArrayList<>();
            List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList = new ArrayList<>();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            @SuppressWarnings("rawtypes")
            Map<String, Object> result =  importAircraftAirlineAlterationByExcel.ImportAircraftAirlineAlterationDataByExcel2007(inputStream);
            return SaveAndDel(result);
	    }
        
        
        
        private Map<String, Object> SaveAndDel(Map<String, Object> result) {
            List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList;
            List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList;
            List<AircraftAirlineAlteration> resultlist = (List<AircraftAirlineAlteration>) result.get("result");
            aircraftAirlineAlterationAllList = (List<AircraftAirlineAlteration>) resultlist.get(0);
            aircraftAirlineAlterationErrorList = (List<AircraftAirlineAlteration>) resultlist.get(1);
            String columnNameError=(String) result.get("resultMessage");
            DataHandler<AircraftAirlineAlteration> dataHandler = new DataHandler<>();
            Map<String, Object> filters = new HashMap<>();
            if (aircraftAirlineAlterationErrorList.size() == 0 && (columnNameError==null||"".equals(columnNameError))) {
                List<AircraftAirlineAlteration> allList=this.findAll();
                for (AircraftAirlineAlteration aircraftAirlineAlteration : aircraftAirlineAlterationAllList) {
//                  AircraftAirlineAlteration a =buildAircraftAirlineAlterationFromLoadDate(aircraftAirlineAlteration);
//                  if (a!=null && a.getId()!=null) {
//                      // 更新记录：update
//                      a.setModifyUser(FQDBMessages
//                              .getString("ADMINISTARTOR"));
//                      a.setModifyTime(new Date());
//                      this.save(a);
//                      addCounter++;
//                  } else {
                    filters.clear();
                    filters.put("aircraftRegistration_equal", aircraftAirlineAlteration.getAircraftRegistration());
                    filters.put("startDate_lessThanOrEqualTo",aircraftAirlineAlteration.getEndDate());
                    filters.put("endDate_greaterThanOrEqualTo",aircraftAirlineAlteration.getStartDate());
                    List<AircraftAirlineAlteration> AircraftAirlineAlterationListInDB = dataHandler.findBy(allList, filters);
                    
//                      // 新增记录：insert
                        aircraftAirlineAlteration.setBasicDataID(aircraftAirlineAlteration.getAircraftRegistration());
                        aircraftAirlineAlteration.setCreateUser(FQDBMessages
                                .getString("ADMINISTARTOR"));
                        aircraftAirlineAlteration.setCreateTime(new Date());
                        this.save(aircraftAirlineAlteration);
//                      modifyCounter++;
//                  }
                }
            }
            Map<String, Object> aircraftAirlineAlterationResult = new HashMap<>();
            List retlist = new ArrayList();
            retlist.add(aircraftAirlineAlterationAllList);
            retlist.add(aircraftAirlineAlterationErrorList);
            aircraftAirlineAlterationResult.put("list", retlist);
            aircraftAirlineAlterationResult.put("errorMessage", result.get("resultMessage"));
            return aircraftAirlineAlterationResult;
        }
	    
//	    public AircraftAirlineAlteration buildAircraftAirlineAlterationFromLoadDate(AircraftAirlineAlteration aircraftAirlineAlteration) {
//	        String aircraftRegistration;
//	        AircraftAirlineAlteration aAlter=null;
//	        List<String> sorts = new ArrayList<>();
//	        Map<String, Object> filters = new HashMap<String, Object>();
//	        aircraftRegistration = aircraftAirlineAlteration.getAircraftRegistration();
//	        filters.put("aircraftRegistration_equal", aircraftRegistration);
//	        filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
//	        sorts.add("modifyTime_asc");//每次都取离当前时间最远的进行更新
//	        sorts.add("createTime_asc");//每次都取离当前时间最远的进行更新
//	        List<AircraftAirlineAlteration> aircraftAirlineAlterationList = findBy(filters, sorts, -1, -1);
//	        if (aircraftAirlineAlterationList.size() > 0) {
//	        	aAlter = aircraftAirlineAlterationList.get(0);
//                aAlter.setAircraftRegistration(        aircraftAirlineAlteration.getAircraftRegistration());         
//                aAlter.setAircraftTypeICAOCode(        aircraftAirlineAlteration.getAircraftTypeICAOCode());
//                aAlter.setAircraftTypeIATACode(        aircraftAirlineAlteration.getAircraftTypeIATACode());
//                aAlter.setAircraftDescription(         aircraftAirlineAlteration.getAircraftDescription());        
//                                                                                               
//                aAlter.setAircraftEngineNumber(        aircraftAirlineAlteration.getAircraftEngineNumber());                
//                aAlter.setAircraftTakeoffWeight(       aircraftAirlineAlteration.getAircraftTakeoffWeight());                   
//                aAlter.setAircraftLandingWeight(       aircraftAirlineAlteration.getAircraftLandingWeight());                   
//                                                                                                 
//                aAlter.setAircraftPayload(             aircraftAirlineAlteration.getAircraftPayload());             
//                aAlter.setAircraftSeatCapacity(        aircraftAirlineAlteration.getAircraftSeatCapacity());             
//                aAlter.setAircraftNoiseCategory(       aircraftAirlineAlteration.getAircraftNoiseCategory());            
//                aAlter.setAircraftHeight(              aircraftAirlineAlteration.getAircraftHeight());             
//                aAlter.setAircraftLength(              aircraftAirlineAlteration.getAircraftLength());                
//                aAlter.setAircraftWidth(               aircraftAirlineAlteration.getAircraftWidth());        
//                                                                                                 
//                aAlter.setOriginalAirline(             aircraftAirlineAlteration.getOriginalAirline());                
//                aAlter.setOriginalSubairline(          aircraftAirlineAlteration.getOriginalSubairline());                   
//                aAlter.setCurrentAirline(              aircraftAirlineAlteration.getCurrentAirline());
//                aAlter.setCurrentSubairline(           aircraftAirlineAlteration.getCurrentSubairline());             
//                aAlter.setAirlineOfFlight(             aircraftAirlineAlteration.getAirlineOfFlight());             
//                aAlter.setStartDate(                   aircraftAirlineAlteration.getStartDate());            
//                aAlter.setEndDate(                     aircraftAirlineAlteration.getEndDate());             
//                aAlter.setValidFlag(                   aircraftAirlineAlteration.getValidFlag());  
//	            aircraftAirlineAlteration=aAlter;
////	            return true;
//	        } 
//	        return aAlter;
//	    }
	    
	    /** 
		 * 根据flightBase、机号查询目的航空分公司
		 */
		@Override
		public AircraftAirlineAlteration getAircraftAirlineAlteration(FlightBase flightBase,String registeration) {
			
			Map<String, Object> filters = new HashMap<>();
			Date searchDate;
			if(flightBase.getFlightDirection().equals("0")){
				searchDate = flightBase.getFlightData().getActualLandingDateTime();
			}else{
				searchDate = flightBase.getFlightData().getActualTakeOffDateTime();
			}
			filters.put("aircraftRegistration_equal", registeration);
			filters.put("endDate_greaterThan", searchDate);
			filters.put("startDate_lessThan", searchDate);
			filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
			List<AircraftAirlineAlteration> aircraftAirlineAlterations = this.findBy(filters);
			if(aircraftAirlineAlterations != null && aircraftAirlineAlterations.size() > 0)
				return aircraftAirlineAlterations.get(0);
			else 
				return null;
		}

//导入CAACSC格式,即清算中心格式
        @Override
        public Map<String, Object> importAircraftAirlineAlterationDataFromCAACSCExcel2003(
                byte[] bytes) throws IOException {
@SuppressWarnings("unused")
            
            int addCounter = 0, modifyCounter = 0;
            List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList = new ArrayList<>();
            List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList = new ArrayList<>();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            @SuppressWarnings("rawtypes")
            Map<String, Object> result =  importAircraftAirlineAlterationByExcel.ImportAircraftAirlineAlterationDataFromCAACSCExcel2003(inputStream);
            return SaveAndDelCAACSCData(result);
        }


        @Override
        public Map<String, Object> importAircraftAirlineAlterationDataFromCAACSCExcel2007(
                byte[] bytes) throws IOException {
@SuppressWarnings("unused")
            
            int addCounter = 0, modifyCounter = 0;
            List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList = new ArrayList<>();
            List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList = new ArrayList<>();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            @SuppressWarnings("rawtypes")
            Map<String, Object> result =  importAircraftAirlineAlterationByExcel.ImportAircraftAirlineAlterationDataFromCAACSCExcel2007(inputStream);
            return SaveAndDelCAACSCData(result);
        }


    private Map<String, Object> SaveAndDelCAACSCData(Map<String, Object> result) {
        Map<Long, Long> deletAAAIds = new HashMap<Long, Long>();
        List<AircraftAirlineAlteration> aircraftAirlineAlterationAllList;
        List<AircraftAirlineAlteration> aircraftAirlineAlterationErrorList;
        List<AircraftAirlineAlteration> resultlist = (List<AircraftAirlineAlteration>) result.get("result");
        aircraftAirlineAlterationAllList = (List<AircraftAirlineAlteration>) resultlist.get(0);
        aircraftAirlineAlterationErrorList = (List<AircraftAirlineAlteration>) resultlist.get(1);
        String columnNameError = (String) result.get("resultMessage");
        DataHandler<AircraftAirlineAlteration> dataHandler = new DataHandler<>();
        Map<String, Object> filters = new HashMap<>();
        int successCount=0;        
        String errorMessage="";
        boolean hasError=false;
        
        if(aircraftAirlineAlterationErrorList.size()>0){
        	for(AircraftAirlineAlteration aircraftAirlineAlteration:aircraftAirlineAlterationErrorList){
        		errorMessage+=aircraftAirlineAlteration.getVerifyDescription();
        	}
        	
        	hasError=true;
        }
        
        if(columnNameError != null && !"".equals(columnNameError)){
        	errorMessage=columnNameError+errorMessage;
        	hasError=true;
        }
        
        
        
        
        if (!hasError) {
            List<AircraftAirlineAlteration> allList = this.findAll();
            for (AircraftAirlineAlteration aircraftAirlineAlteration : aircraftAirlineAlterationAllList) {
                filters.clear();
                filters.put("aircraftRegistration_equal",
                        aircraftAirlineAlteration.getAircraftRegistration());
                filters.put("startDate_lessThanOrEqualTo",
                        aircraftAirlineAlteration.getEndDate());
                filters.put("endDate_greaterThanOrEqualTo",
                        aircraftAirlineAlteration.getStartDate());
                filters.put("removeFlag_equal",
                        CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
                
                List<AircraftAirlineAlteration> AircraftAirlineAlterationListInDB = dataHandler
                        .findBy(allList, filters);
                List<Long> currentAAAIds = new ArrayList<Long>();
                for (AircraftAirlineAlteration a : AircraftAirlineAlterationListInDB) {// 找到时间冲突的，删除
                    a.setRemoveFlag("0");
                    this.save(a);
                    currentAAAIds.add(a.getId());// 记录已删除的AAA_ID
//                    aircraftAirlineAlteration.setIsWideOrNarrow(a
//                            .getIsWideOrNarrow());
                    aircraftAirlineAlteration.setIsHighDensity(a
                            .getIsHighDensity());
                }
                // // 新增记录：insert
                aircraftAirlineAlteration
                        .setBasicDataID(aircraftAirlineAlteration
                                .getAircraftRegistration());
                aircraftAirlineAlteration.setCreateUser(FQDBMessages
                        .getString("ADMINISTARTOR"));
                aircraftAirlineAlteration.setCreateTime(new Date());
                aircraftAirlineAlteration = this
                        .save(aircraftAirlineAlteration);
                successCount++;

                for (Long id : currentAAAIds) {// 记录已删除的AAA_ID和新增AAA的对应关系
                    deletAAAIds.put(id, aircraftAirlineAlteration.getId());
                }
            }
        }
        
        //更新AIRCRAFT_DISCOUNT的AAA_ID
        DiscountDao discountDao=(DiscountDao) discountManager.getDao();
        discountDao.updateAAAIdById(deletAAAIds);
        
        Map<String, Object> aircraftAirlineAlterationResult = new HashMap<>();
//        List retlist = new ArrayList();
//        retlist.add(aircraftAirlineAlterationAllList);
//        retlist.add(aircraftAirlineAlterationErrorList);
//        aircraftAirlineAlterationResult.put("list", retlist);
//        aircraftAirlineAlterationResult.put("errorMessage",
//                result.get("resultMessage"));

        aircraftAirlineAlterationResult.put("totalCount",aircraftAirlineAlterationAllList.size());
        aircraftAirlineAlterationResult.put("successCount",successCount);        
        aircraftAirlineAlterationResult.put("errorCount",aircraftAirlineAlterationAllList.size()-successCount);
        aircraftAirlineAlterationResult.put("error",errorMessage);
        
        
        return aircraftAirlineAlterationResult;
    }


        public List<AircraftAirlineAlteration> findAll() {
            Map<String, Object> filters = new HashMap<>();
            filters.put("removeFlag_equal",
                    CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
            List<AircraftAirlineAlteration> allList = aircraftAirlineAlterationDao.findBy(filters, null, -1, -1);
            return allList;
        }
}


