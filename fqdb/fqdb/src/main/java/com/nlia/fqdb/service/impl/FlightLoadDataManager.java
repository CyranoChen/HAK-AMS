package com.nlia.fqdb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Column;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nlia.fqdb.configuration.DataCache;
import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.dao.FlightBaseDao;
import com.nlia.fqdb.dao.FlightLoadDataDao;
import com.nlia.fqdb.dao.FlightMateInfoDao;
import com.nlia.fqdb.dao.FlightViewDao;
import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.FlightBase;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.entity.FlightView;
import com.nlia.fqdb.entity.base.Airline;
import com.nlia.fqdb.entity.base.Airport;
import com.nlia.fqdb.service.IFlightLoadDataManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.DateUtils;
import com.nlia.fqdb.util.ImportFlightLoadDataByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class FlightLoadDataManager extends
        AbstractCrudService<FlightLoadData, Long> implements
        IFlightLoadDataManager {

    @Resource
    private FlightLoadDataDao flightLoadDataDao;

	@Resource
	private FlightBaseManager flightBaseManager;
	
    @Resource
    private FlightBaseDao flightBaseDao;

    @Resource
    private ImportFlightLoadDataByExcel importFlightLoadDataByExcel;

    @Resource
    private AircraftAirlineAlterationManager aircraftAirlineAlterationManager;
    
    @Resource
    private AirportManager airportManager;
    
    @Resource
    private FlightMateInfoDao flightMateInfoDao;
    
    @Resource
    private FlightViewDao flightViewDao;
    
    @Override
    protected GenericDao<FlightLoadData, Long> getDao() {
        return flightLoadDataDao;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Map<String, Object> importFlightLoadDataByExcel2003(byte[] bytes)
            throws IOException {
        @SuppressWarnings("unused")
        List<FlightLoadData> flightLoadDataAllList = new ArrayList<>();
        List<FlightLoadData> flightLoadDataErrorList = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        @SuppressWarnings("rawtypes")
        Map<String, Object> result = importFlightLoadDataByExcel
                .ImportFlightLoadDataByExcel2003(inputStream);
        return saveFlightLoadData(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> importFlightLoadDataByExcel2007(byte[] bytes)
            throws IOException {
        List<FlightLoadData> flightLoadDataAllList = new ArrayList<>();
        List<FlightLoadData> flightLoadDataErrorList = new ArrayList<>();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        @SuppressWarnings("rawtypes")
        Map<String, Object> result = importFlightLoadDataByExcel
                .ImportFlightLoadDataByExcel2007(inputStream);
        return saveFlightLoadData(result);
    }

    private Map<String, Object> saveFlightLoadData(Map<String, Object> result) {
        int addCounter = 0, modifyCounter = 0;
        List<FlightLoadData> flightLoadDataAllList;
        List<FlightLoadData> flightLoadDataErrorList;
        List<FlightLoadData> resultlist = (List<FlightLoadData>) result
                .get("result");
        flightLoadDataAllList = (List<FlightLoadData>) resultlist.get(0);
        flightLoadDataErrorList = (List<FlightLoadData>) resultlist.get(1);
        if (flightLoadDataErrorList.size() == 0) {
            List<Long> ids = new ArrayList<>();
            for (FlightLoadData flightLoadData : flightLoadDataAllList) {
                this.save(flightLoadData);
                ids.add(flightLoadData.getFlightdataid());
            }
        }
        Map<String, Object> flightLoadDataResult = new HashMap<>();
        flightLoadDataResult.put("list", flightLoadDataAllList);
        flightLoadDataResult.put("errorList", flightLoadDataErrorList);
        flightLoadDataResult.put("errorMessage", result.get("resultMessage"));
        return flightLoadDataResult;
    }
    @Override
    public void updateFDbyFLD(List<Long> ids) {
        List<Long> voIds = new ArrayList<>();

        int size = ids.size() / 1000 + 1;

        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                voIds.addAll(ids.subList(1000 * i, 1000 * (i + 1)));
            } else {
                voIds.addAll(ids.subList(1000 * i, ids.size()));
            }
            flightLoadDataDao.updateFlightDataByFdids(voIds);
            voIds.clear();
        }
    }

    public List<FlightLoadData> generate(List<FlightBase> bs) {
        List<FlightLoadData> retlist = new ArrayList<>();
        Map<String, Object> filters = new HashMap<>();
        List<String> sorters = new ArrayList<>();

       
        List<FlightLoadData> listFlightLoadDataFromView = new ArrayList<FlightLoadData>();
        if (bs.size() > 0) {
            // 查找航空公司
            List<Airport> airports = airportManager.findAllAirport();
            Map<String, Airport> airportCountryTypeMap = new HashMap<String, Airport>();
            for (Airport airport : airports) {
                airportCountryTypeMap.put(airport.getAirportIATACode() + "_" + airport.getAirportICAOCode(), airport);
            }

            // 查找机号所属航空公司
            filters.clear();
            // filters.put("endDate_lessThanOrEqualTo",endTime);//小于等于结束时间
            filters.put("validFlag_equal", AircraftAirlineAlteration.VALIDFLAG.VALID);
            // filters.put("startDate_greaterThanOrEqualTo",startTime);//大于等于开始时间
            filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
            List<AircraftAirlineAlteration> aircraftAirlineAlterations = aircraftAirlineAlterationManager.findBy(filters);

            // 根据FB生成FlightLoadData
            List<Long> ids = new ArrayList<Long>();
            for (FlightBase fb : bs) {
                ids.add(fb.getId());
                listFlightLoadDataFromView.addAll(converFlightBaseToFlightLoadData(airportCountryTypeMap, fb));
            }
            // listFlightLoadDataFromView排序
            DataHandler<FlightLoadData> dataHandler = new DataHandler<>();
            sorters.add("flightbaseid_asc");
            sorters.add("hd_asc");
            listFlightLoadDataFromView = dataHandler.sort(listFlightLoadDataFromView, sorters);

            // 查找FlightLoadData数据
            List<FlightLoadData> listFlightLoadDataFromTab = this.findByFbIds(ids);
            listFlightLoadDataFromTab = dataHandler.sort(listFlightLoadDataFromTab, sorters);
            int indexView = 0, indexTab = 0;
            List<Long> delFLDids = new ArrayList<Long>();
            if (listFlightLoadDataFromView.size() > 0 && listFlightLoadDataFromTab.size() > 0) {
                boolean loopend = false;
                while (!loopend) {
                    // 循环listFlightLoadDataFromView listFlightLoadDataFromTab
                    FlightLoadData flInTab = listFlightLoadDataFromTab.get(indexTab);
                    FlightLoadData flInView = listFlightLoadDataFromView.get(indexView);
                    int res = flInTab.getFlightbaseid().compareTo(flInView.getFlightbaseid()); // flightbaseid
                    if (res == 0)// flightbaseid相同
                    {
                        res = flInTab.getHd().compareTo(flInView.getHd());// HD
                        if (res == 0)// HD相同，跳过该航班
                        {
                            if ("0".equals(flInTab.getGenerateMethod())) {// 自动生成的，覆盖
                                flInView.setId(flInTab.getId());
                                // flInView = this.save(flInView);
                                retlist.add(flInView);
                            }
                            indexTab++;
                            indexView++;
                        } else if (res < 0)// listFlightLoadDataFromTab小
                        {
                            if ("0".equals(flInTab.getGenerateMethod())) {// 自动生成的
                                delFLDids.add(flInTab.getId());
                            }
                            indexTab++;

                        } else// listFlightLoadDataFromTab大，入库
                        {
                            FlightLoadData flightLoadData = flInView;
                            flightLoadData = setYzZwByAircraftAirlineAlteration(aircraftAirlineAlterations, flightLoadData);
                            // flightLoadData = this.save(flightLoadData);
                            retlist.add(flightLoadData);
                            indexView++;
                        }
                    } else if (res < 0)// listFlightLoadDataFromTab小
                    {
                        if ("0".equals(flInTab.getGenerateMethod())) {
                            delFLDids.add(flInTab.getId());
                        }
                        indexTab++;
                    } else// listFlightLoadDataFromTab大，入库
                    {
                        FlightLoadData flightLoadData = flInView;
                        flightLoadData = setYzZwByAircraftAirlineAlteration(aircraftAirlineAlterations, flightLoadData);
                        // flightLoadData = this.save(flightLoadData);
                        retlist.add(flightLoadData);
                        indexView++;
                    }
                    if (indexView == listFlightLoadDataFromView.size() || indexTab == listFlightLoadDataFromTab.size()) {
                        loopend = true;
                    }
                }
            }

            for (; indexView < listFlightLoadDataFromView.size(); indexView++)// 如果listFlightLoadDataFromView还有剩余，全部入库
            {
                FlightLoadData flightLoadData = listFlightLoadDataFromView.get(indexView);
                flightLoadData = setYzZwByAircraftAirlineAlteration(aircraftAirlineAlterations, flightLoadData);
                // flightLoadData = this.save(flightLoadData);
                retlist.add(flightLoadData);
            }

            for (; indexTab < listFlightLoadDataFromTab.size(); indexTab++)// listFlightLoadDataFromTab有剩余，删除
            {
                if ("0".equals(listFlightLoadDataFromTab.get(indexTab).getGenerateMethod())) {// 自动生成的
                    delFLDids.add(listFlightLoadDataFromTab.get(indexTab).getId());
                }
            }
            // 删除listFlightLoadDataFromTab中没有找到的FLD
            if (delFLDids.size() > 0) {
                for (Long id : delFLDids) {
                    this.delete(id);
                }
            }
            // 入库
            retlist = (List<FlightLoadData>) this.save(retlist);
            

        }
        return retlist;
    }
    
    
    @Override
    public Map<String,Object> generate(Long id) {
        Map<String, Object> filters = new HashMap<>();
        // 查找FB
	 	 List<FlightBase> fbs = flightBaseManager
	                .getFlightBaseFromDataBaseWithId(id);
        List<FlightLoadData> flds=generate(fbs);
        
        updateFlightMateInfo(flds);
    	
        Map<String,Object> result=new HashMap<String,Object>();
		result.put("total",flds.size() );
        return result;
       
    }
    
    @Override
    public List<FlightLoadData> generate(Date startTime, Date endTime) {
        Map<String, Object> filters = new HashMap<>();
        // 查找FB
        filters.clear();
        filters.put("removeFlag_equal",
                CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
        filters.put("flightScheduledDate_greaterThanOrEqualTo", startTime);
        filters.put("flightScheduledDate_lessThanOrEqualTo", endTime);
        List<FlightView> viewList = flightViewDao.findBy(filters, null, -1, -1);
        DataCache a = new DataCache();
        List<FlightBase> bs = a.convertFlightView(viewList);
        List<FlightLoadData> flds=generate(bs);
        
        int cleatNum=clearInvalidData(startTime,endTime);
		
		System.out.println("-------------clear "+cleatNum+" flightLoadData ");
        
        
        updateFlightMateInfo(flds);
    	

        return flds;
       
    }
    
	private int clearInvalidData(Date startTime, Date endTime) {
		return flightLoadDataDao.clear(startTime, endTime);
	}
	
    //lushuaifeng 20160913
    private void updateFlightMateInfo(List<FlightLoadData> flds){
    	if(flds==null ||flds.isEmpty()){
    		return;
    	}
    	
    	 HashMap<Long,List<FlightLoadData>> hmFlightBaseIdFlightLoadDatas=new HashMap<Long,List<FlightLoadData>>();
         List<Long> ids=new ArrayList<Long>();
         
         for(FlightLoadData fld:flds){
        	if(!ids.contains(fld.getFlightbaseid())){
        		ids.add(fld.getFlightbaseid());
        	}
         	
         	if(hmFlightBaseIdFlightLoadDatas.containsKey(fld.getFlightbaseid())){
         		hmFlightBaseIdFlightLoadDatas.get(fld.getFlightbaseid()).add(fld);
         	}else{
         		List<FlightLoadData> values=new ArrayList<FlightLoadData>();
         		values.add(fld);
         		hmFlightBaseIdFlightLoadDatas.put(fld.getFlightbaseid(), values);
         	}
         }

     	List<FlightMateInfo> fmis=flightMateInfoDao.findByFlightBases(ids, null);
     	
     	if(fmis==null || fmis.isEmpty()){
     		return;
     	}
     	
     	for(FlightMateInfo fmi:fmis){
     		
     		if(hmFlightBaseIdFlightLoadDatas.containsKey(fmi.getFlightBaseId())){
     			
     			List<FlightLoadData> list=hmFlightBaseIdFlightLoadDatas.get(fmi.getFlightBaseId());
     			
     			for(FlightLoadData fld:list){
     				fmi.setFlightRoute(fld.getHx());
     				if(fld.getLegNo()!=null){
     					switch(fld.getLegNo()){
 	    					case "0":
 	    						fmi.setFlightPart0(fld.getHd());
 	    						setFlightPart0Type(fmi,fld);
 	    						break;
 	    					case "1":
 	    						fmi.setFlightPart1(fld.getHd());
 	    						break;
 	    					case "2":
 	    						fmi.setFlightPart2(fld.getHd());
 	    						break;
     						default:
     							break;
     					}
     				}
     			}
     			
     	     	flightMateInfoDao.save(fmi);
     			
     		}
     	}
     	

     	
    }
    
    private void setFlightPart0Type(FlightMateInfo fmi,FlightLoadData fld){  
    	if(fld.getHdfl()==null){
    		return;
    	}
    	
    	switch(fld.getHdfl()){
    		case "DOM":
    			fmi.setFlightPart0Type("0");
    			break;
    		default:
    			fmi.setFlightPart0Type("1");
    			break;
    	}
    }
    

    private FlightLoadData setYzZwByAircraftAirlineAlteration(List<AircraftAirlineAlteration> aircraftAirlineAlterations,FlightLoadData flightLoadData){
        if(flightLoadData.getJh()!=null && aircraftAirlineAlterations.size()>0){
            DataHandler<AircraftAirlineAlteration> dataHandler = new DataHandler<>();
            Map<String, Object> filters = new HashMap<>();
            filters.put("endDate_greaterThanOrEqualTo",flightLoadData.getJhrq());
            filters.put("startDate_lessThanOrEqualTo",flightLoadData.getJhrq());//开始、结束时间在flightLoadData.getJhrq的两边
            filters.put("aircraftRegistration_equal",flightLoadData.getJh());
            List<AircraftAirlineAlteration> aircraftAirlineAlterationList = dataHandler.findBy(aircraftAirlineAlterations, filters);
            if(!aircraftAirlineAlterationList.isEmpty()){
                if(aircraftAirlineAlterationList.size()>1){
                    System.out.println("多条AircraftAirlineAlteration，取第1条");
                }
                AircraftAirlineAlteration aircraftAirlineAlteration=aircraftAirlineAlterationList.get(0);
                flightLoadData.setZdyz(aircraftAirlineAlteration.getAircraftPayload()==null?0:aircraftAirlineAlteration.getAircraftPayload());
                flightLoadData.setZdzw(aircraftAirlineAlteration.getAircraftSeatCapacity()==null?0:aircraftAirlineAlteration.getAircraftSeatCapacity());
            }
        }
        return flightLoadData;
    }
    
    //更具FMI去生成FLD
    public List<FlightLoadData> generateByFMI(List<FlightMateInfo> fmis) {

        List<FlightLoadData> retlist = new ArrayList<>();
        Map<String, Object> filters = new HashMap<>();
        List<String> sorters = new ArrayList<>();

       
        List<FlightLoadData> listFlightLoadDataFromView = new ArrayList<FlightLoadData>();
        if (fmis.size() > 0) {
            // 查找航空公司
            List<Airport> airports = airportManager.findAllAirport();
            Map<String, Airport> airportCountryTypeMap = new HashMap<String, Airport>();
            for (Airport airport : airports) {
                airportCountryTypeMap.put(airport.getAirportIATACode() + "_" + "null", airport);
            }

            // 查找机号所属航空公司
            filters.clear();
            // filters.put("endDate_lessThanOrEqualTo",endTime);//小于等于结束时间
            filters.put("validFlag_equal", AircraftAirlineAlteration.VALIDFLAG.VALID);
            // filters.put("startDate_greaterThanOrEqualTo",startTime);//大于等于开始时间
            filters.put("removeFlag_equal", CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
            List<AircraftAirlineAlteration> aircraftAirlineAlterations = aircraftAirlineAlterationManager.findBy(filters);

            // 根据FMI生成FlightLoadData
            List<Long> ids = new ArrayList<Long>();
            for (FlightMateInfo fmi : fmis) {
                ids.add(fmi.getId());
                listFlightLoadDataFromView.addAll(converFMI2FLD(airportCountryTypeMap, fmi));
            }
            // listFlightLoadDataFromView排序
            DataHandler<FlightLoadData> dataHandler = new DataHandler<>();
            sorters.add("flightbaseid_asc");
            sorters.add("hd_asc");
            listFlightLoadDataFromView = dataHandler.sort(listFlightLoadDataFromView, sorters);

            // 查找FlightLoadData数据
            List<FlightLoadData> listFlightLoadDataFromTab = this.findByFbIds(ids);
            listFlightLoadDataFromTab = dataHandler.sort(listFlightLoadDataFromTab, sorters);
            int indexView = 0, indexTab = 0;
            List<Long> delFLDids = new ArrayList<Long>();
            if (listFlightLoadDataFromView.size() > 0 && listFlightLoadDataFromTab.size() > 0) {
                boolean loopend = false;
                while (!loopend) {
                    // 循环listFlightLoadDataFromView listFlightLoadDataFromTab
                    FlightLoadData flInTab = listFlightLoadDataFromTab.get(indexTab);
                    FlightLoadData flInView = listFlightLoadDataFromView.get(indexView);
                    int res = flInTab.getFlightbaseid().compareTo(flInView.getFlightbaseid()); // flightbaseid
                    if (res == 0)// flightbaseid相同
                    {
                        res = flInTab.getHd().compareTo(flInView.getHd());// HD
                        if (res == 0)// HD相同，跳过该航班
                        {
                            if ("0".equals(flInTab.getGenerateMethod())) {// 自动生成的，覆盖
                                flInView.setId(flInTab.getId());
                                // flInView = this.save(flInView);
                                retlist.add(flInView);
                            }
                            indexTab++;
                            indexView++;
                        } else if (res < 0)// listFlightLoadDataFromTab小
                        {
                            if ("0".equals(flInTab.getGenerateMethod())) {// 自动生成的
                                delFLDids.add(flInTab.getId());
                            }
                            indexTab++;

                        } else// listFlightLoadDataFromTab大，入库
                        {
                            FlightLoadData flightLoadData = flInView;
                            flightLoadData = setYzZwByAircraftAirlineAlteration(aircraftAirlineAlterations, flightLoadData);
                            // flightLoadData = this.save(flightLoadData);
                            retlist.add(flightLoadData);
                            indexView++;
                        }
                    } else if (res < 0)// listFlightLoadDataFromTab小
                    {
                        if ("0".equals(flInTab.getGenerateMethod())) {
                            delFLDids.add(flInTab.getId());
                        }
                        indexTab++;
                    } else// listFlightLoadDataFromTab大，入库
                    {
                        FlightLoadData flightLoadData = flInView;
                        flightLoadData = setYzZwByAircraftAirlineAlteration(aircraftAirlineAlterations, flightLoadData);
                        // flightLoadData = this.save(flightLoadData);
                        retlist.add(flightLoadData);
                        indexView++;
                    }
                    if (indexView == listFlightLoadDataFromView.size() || indexTab == listFlightLoadDataFromTab.size()) {
                        loopend = true;
                    }
                }
            }

            for (; indexView < listFlightLoadDataFromView.size(); indexView++)// 如果listFlightLoadDataFromView还有剩余，全部入库
            {
                FlightLoadData flightLoadData = listFlightLoadDataFromView.get(indexView);
                flightLoadData = setYzZwByAircraftAirlineAlteration(aircraftAirlineAlterations, flightLoadData);
                // flightLoadData = this.save(flightLoadData);
                retlist.add(flightLoadData);
            }

            for (; indexTab < listFlightLoadDataFromTab.size(); indexTab++)// listFlightLoadDataFromTab有剩余，删除
            {
                if ("0".equals(listFlightLoadDataFromTab.get(indexTab).getGenerateMethod())) {// 自动生成的
                    delFLDids.add(listFlightLoadDataFromTab.get(indexTab).getId());
                }
            }
            // 删除listFlightLoadDataFromTab中没有找到的FLD
            if (delFLDids.size() > 0) {
                for (Long id : delFLDids) {
                    this.delete(id);
                }
            }
            // 入库
            retlist = (List<FlightLoadData>) this.save(retlist);
        }
        return retlist;
    
    }
    private List<FlightLoadData> converFMI2FLD(Map<String, Airport> airportCountryTypeMap,FlightMateInfo fmi){
        List<FlightLoadData> list = new ArrayList<FlightLoadData>();
        
        Date JHRQ=null;
        if(fmi.getFlightDirection().equals("0")){
            JHRQ=fmi.getScheduledLandedTime();
        }else if(fmi.getFlightDirection().equals("1")){
            JHRQ=fmi.getScheduledLandedTime();
        }
        Date SJRQ;
        
        String CYR="";
        if (null != fmi.getAirLine()) {
            CYR = fmi.getAirLine().getAirlineIATACode();// fb.getAirlineIATACode();
        }
        if(null==CYR){
            CYR="";
        }
        String DM=CYR;
        String SS="";
        String HBH="";
        if(fmi.getFlightDirection().equals("0")){
            HBH=fmi.getAFlightIdentity().replace(CYR, "");
        }else if(fmi.getFlightDirection().equals("1")){
            HBH=fmi.getDFlightIdentity().replace(CYR, "");
        }
        
        String JX="";
        if(null!=fmi.getAircraft()){
            JX=fmi.getAircraft().getAircraftTypeIATACode();// fb.getFlightData().getAircraftIATACode();
        }
        if(fmi.getFlightDirection().equals("0")){
            JX=fmi.getAAircraftType();
        }else if(fmi.getFlightDirection().equals("1")){
            JX=fmi.getDAircraftType();
        }
        
        String HBXZ="";//fb.getFlightData().getFlightCAACServiceType();
        String HX = "";
        String HX0 = "";
        String HXFL="";
        if(null!= fmi.getFlightCountryType()){
            switch (fmi.getFlightCountryType()) {
            case "1":
                HXFL="INT";
                break;
            case "0":
                HXFL="DOM";
                break;
            case "2":
                HXFL="REG";
                break;
            case "3":
                HXFL="MIX";
                break;
            default:
                System.out.println("error :" +fmi.getId().toString() + " fmi.getFlightCountryType() " + fmi.getFlightCountryType() +"not in (I,D,R,M)\n");
                return list;
            }
        }
        List<String> HD = new ArrayList();
        List<String> HDFL = new ArrayList();
        String IO="";
        String JQJD="";
        String QJSJ="";
        String PBM=DateUtils.Date2String(new Date(), "MM/dd/yyHH:mm:ss");
        String XG="FALSE";
        String BC="FALSE";
        String JH="";
        if(fmi.getFlightDirection().equals("0")){
            JH=fmi.getAregisteration();
        }else if(fmi.getFlightDirection().equals("1")){
            JH=fmi.getDregisteration();
        }
        String OPERATIONSTATUS="";
        String FLIGHTSTATUS="";
        Long FLIGHTBASEID=fmi.getId();
        Long FLIGHTDATAID=null;
        String FLIGHTIDENTITY="";
        if(fmi.getFlightDirection().equals("0")){
            FLIGHTIDENTITY=fmi.getAFlightIdentity();
        }else if(fmi.getFlightDirection().equals("1")){
            FLIGHTIDENTITY=fmi.getDFlightIdentity();
        }
        
        
       
        
//        List<String> iataRouts =new ArrayList();
//        List<String> icaoRouts =new ArrayList();
//        if(null!=fb.getFlightData().getFullRouteAirportIATACode() && !fb.getFlightData().getFullRouteAirportIATACode().isEmpty()){
//            iataRouts.addAll(java.util.Arrays.asList(fb.getFlightData().getFullRouteAirportIATACode().split(",")));
//        }
//        if(null!=fb.getFlightData().getFullRouteAirportICAOCode() && !fb.getFlightData().getFullRouteAirportICAOCode().isEmpty()){
//            icaoRouts.addAll(java.util.Arrays.asList(fb.getFlightData().getFullRouteAirportICAOCode().split(",")));
//        }
//        if(iataRouts.size()>icaoRouts.size()){
//            for(int i=icaoRouts.size();i<iataRouts.size();i++){
//                icaoRouts.add(null);
//            }
//        }
//        if(iataRouts.size()<icaoRouts.size()){
//            for(int i=iataRouts.size();i<icaoRouts.size();i++){
//                iataRouts.add(null);
//            }
//        }
        String airportRoutingName = "";
        String localAirportRoutingName = "";
        Airport airport = null;
        for (int i = 0; i < 3; i++) {
            String flightPart="";
            switch(i){
                case 0:
                    flightPart=fmi.getFlightPart0();
                    break;
                case 1:
                    flightPart=fmi.getFlightPart1();
                    break;
                case 2:
                    flightPart=fmi.getFlightPart2();
                    break;
            }
            if (null != flightPart && !flightPart.isEmpty()) {
                String iata = "";
                if (fmi.getFlightDirection().equals("0")) {
                    iata = flightPart.replace("-HAK", "");
                } else {
                    iata = flightPart.replace("HAK-", "");
                }
                airport = airportCountryTypeMap.get(iata + "_" + "null");
                if (null != airport) {
                    airportRoutingName = airport.getAirportRoutingName();
                    if (null != airportRoutingName && !airportRoutingName.isEmpty()) {
                        HX0 += airportRoutingName;
                    }
                    HX0 += "-";
                    switch (airport.getAirportCountryType()) {
                    case "I":
                        HDFL.add("INT");
                        break;
                    case "D":
                        HDFL.add("DOM");
                        break;
                    case "R":
                        HDFL.add("REG");
                        break;
                    default:
                        // HDFL.add("");
                        // break;
                        System.out.println("error :" + fmi.getId() + " " + iata + ".getAirportRoutingName() not in (I,D,R)\n");
                        continue;
                    }
                    HD.add(flightPart);
                } else {
                    System.out.println("error :" + fmi.getId() + " airport " + iata + " not found\n");
                    continue;
                }
            }
        }
//        airport = airportCountryTypeMap.get(fb.getAirportIATACode() + "_"+ fb.getAirportICAOCode());
        localAirportRoutingName = "琼";
//        if(null!=fb.getFlightData().getFullRouteAirportIATACode() && !fb.getFlightData().getFullRouteAirportIATACode().isEmpty()){
//            HX = fb.getFlightData().getFullRouteAirportIATACode().replace(",", "-");
//        }
        HX=fmi.getFlightRoute();
        if(!HX0.isEmpty()){
            HX0=HX0.substring(0, HX0.length()-1);
        }
        if (fmi.getFlightDirection().equals("0")) {
//            HX += "-" + "HAK";
            HX0 +="-" + localAirportRoutingName;
            if(fmi.getLandedTime()!=null){
                SJRQ=fmi.getLandedTime();
                QJSJ=DateUtils.Date2String(fmi.getLandedTime(), "HH:mm");
            }else{
                SJRQ=fmi.getScheduledLandedTime();
                QJSJ=DateUtils.Date2String(fmi.getScheduledLandedTime(), "HH:mm");
            }
            JQJD=DateUtils.Date2String(fmi.getScheduledLandedTime(),"HH:mm");
            IO="I";
        }else{
//            HX = "HAK" + "-" + HX;
            HX0 =localAirportRoutingName + "-" +HX0;
            if(fmi.getTakeOffTime()!=null){
                SJRQ=fmi.getTakeOffTime();
                QJSJ=DateUtils.Date2String(fmi.getTakeOffTime(), "HH:mm");
            }else{
                SJRQ=fmi.getScheduledTakeOffTime();
                QJSJ=DateUtils.Date2String(fmi.getScheduledTakeOffTime(), "HH:mm");
            }
            JQJD=DateUtils.Date2String(fmi.getScheduledTakeOffTime(),"HH:mm");
            IO="O";
        }

        for(int i=0;i<HD.size();i++){
            FlightLoadData flightLoadData=new FlightLoadData();
            int legno=i;
            //进港航班航段排序与离港相反，从近海口开始排序
            if(fmi.getFlightDirection().equals("0")){
            	legno=HD.size()-1-i;
            }
            
            flightLoadData.setLegNo(String.valueOf(legno));
            //flightLoadData.setLegNo(String.valueOf(i));
            flightLoadData.setGenerateMethod("0");
            flightLoadData.setJhrq(JHRQ);
            flightLoadData.setSjrq(SJRQ);
            flightLoadData.setCyr(CYR);
            flightLoadData.setDm(DM);
            flightLoadData.setSs(SS);
            flightLoadData.setHbh(HBH);
            flightLoadData.setJx(JX);
            flightLoadData.setHbxz(HBXZ);
            flightLoadData.setHx(HX);
            flightLoadData.setHx0(HX0);
            flightLoadData.setHxfl(HXFL);
            
            flightLoadData.setHd(HD.get(i)); 
            flightLoadData.setHdfl(HDFL.get(i));
            flightLoadData.setIo(IO);
            flightLoadData.setJqjd(JQJD);
            flightLoadData.setQjsj(QJSJ);
            flightLoadData.setPbm(PBM);
            flightLoadData.setXg(XG);
            flightLoadData.setBc(BC);
            flightLoadData.setJh(JH);
            flightLoadData.setOperationstatus(OPERATIONSTATUS);
            flightLoadData.setFlightstatus(FLIGHTSTATUS);
            flightLoadData.setFlightbaseid(FLIGHTBASEID);
            flightLoadData.setFlightdataid(FLIGHTDATAID);
            flightLoadData.setFlightIdentity(FLIGHTIDENTITY);
            list.add(flightLoadData);
        }
        return list;
    }
     
    
    private List<FlightLoadData> converFlightBaseToFlightLoadData(Map<String, Airport> airportCountryTypeMap,FlightBase fb) {
        List<FlightLoadData> list = new ArrayList<FlightLoadData>();
       
//        if((null!=fb.getFlightData().getActualLandingDateTime() && fb.getFlightDirection().equals("0"))
//         || (null!=fb.getFlightData().getActualTakeOffDateTime() && fb.getFlightDirection().equals("1"))
//          ){
        	Date scheduledTime=null;
        	if(fb.getFlightDirection().equals("0")){
        		scheduledTime=DateUtils.getRunningDate(fb.getFlightData().getScheduledLandingDateTime());
        	}else{
        		scheduledTime=DateUtils.getRunningDate(fb.getFlightData().getScheduledTakeOffDateTime());
        	}
        	Date JHRQ=scheduledTime;
            //Date JHRQ=fb.getFlightScheduledDate();
                        
            Date SJRQ;
            
            String CYR=fb.getAirlineIATACode();
            String DM=fb.getAirportIATACode();
            String SS="";
            String HBH="";
            if(fb.getAirlineIATACode()!=null){
                HBH=fb.getFlightIdentity().replace(fb.getAirlineIATACode(), "");
            }else{
                HBH=fb.getFlightIdentity(); 
            }
            String JX=fb.getFlightData().getAircraftIATACode();
            String HBXZ=fb.getFlightData().getFlightCAACServiceType();
            String HX = "";
            String HX0 = "";
            String HXFL="";
            if(null!=fb.getFlightData().getFlightCountryType()){
                switch (fb.getFlightData().getFlightCountryType()) {
                case "I":
                    HXFL="INT";
                    break;
                case "D":
                    HXFL="DOM";
                    break;
                case "R":
                    HXFL="REG";
                    break;
                case "M":
                    HXFL="MIX";
                    break;
                default:
//                    HXFL="";
//                    break;
                    System.out.println("error :" +fb.toString() + " fb.getFlightData().getFlightCountryType() not in (I,D,R,M)\n");
                    return list;
                }
            }
//            else{
//                HXFL="";
//            }
            List<String> HD = new ArrayList();
            List<String> HDFL = new ArrayList();
    //        Long ZDYZ;
    //        Long ZDZW;
    //        Long PEYZ;
    //        Long PEZW;
    //        Long KGYZ;
    //        Long KGZW;
            String IO="";
    //        Long JCN;
            String JQJD="";
            String QJSJ="";
    //        Long CR;
    //        Long ET;
    //        Long YE;
    //        Long CRWH;
    //        Long ETWH;
    //        Long YEWH;
    //        Long XL;
    //        Long YJ;
    //        Long HW;
            String PBM=DateUtils.Date2String(new Date(), "MM/dd/yyHH:mm:ss");
            String XG="FALSE";
            String BC="FALSE";
            String JH=fb.getFlightData().getRegisteration();
    //        Long WJHZ;
    //        Long XLJS;
            String OPERATIONSTATUS=fb.getFlightData().getOperationStatus();
            String FLIGHTSTATUS=fb.getFlightData().getFlightStatus();
            Long FLIGHTBASEID=fb.getId();
            Long FLIGHTDATAID=fb.getFlightData().getId();
            String FLIGHTIDENTITY=fb.getFlightIdentity();
            
            
           
            
            List<String> iataRouts =new ArrayList();
            List<String> icaoRouts =new ArrayList();
            if(null!=fb.getFlightData().getFullRouteAirportIATACode() && !fb.getFlightData().getFullRouteAirportIATACode().isEmpty()){
                iataRouts.addAll(java.util.Arrays.asList(fb.getFlightData().getFullRouteAirportIATACode().split(",")));
            }
            if(null!=fb.getFlightData().getFullRouteAirportICAOCode() && !fb.getFlightData().getFullRouteAirportICAOCode().isEmpty()){
                icaoRouts.addAll(java.util.Arrays.asList(fb.getFlightData().getFullRouteAirportICAOCode().split(",")));
            }
            if(iataRouts.size()>icaoRouts.size()){
                for(int i=icaoRouts.size();i<iataRouts.size();i++){
                    icaoRouts.add(null);
                }
            }
            if(iataRouts.size()<icaoRouts.size()){
                for(int i=iataRouts.size();i<icaoRouts.size();i++){
                    iataRouts.add(null);
                }
            }
            String airportRoutingName = "";
            String localAirportRoutingName = "";
            Airport airport = null;
            for (int i = 0; i < iataRouts.size(); i++) {
                if(fb.getAirportIATACode().equals(iataRouts.get(i))||fb.getAirportICAOCode().equals(icaoRouts.get(i))){//等于本场，跳过
                    continue;
                }
                airport = airportCountryTypeMap.get(iataRouts.get(i) + "_"+ icaoRouts.get(i));
                if (null != airport) {
                    airportRoutingName = airport.getAirportRoutingName();
                    if (null != airportRoutingName && !airportRoutingName.isEmpty()) {
                        HX0 += airportRoutingName;
                    }
                    HX0 += "-";
                    switch (airport.getAirportCountryType()) {
                    case "I":
                        HDFL.add("INT");
                        break;
                    case "D":
                        HDFL.add("DOM");
                        break;
                    case "R":
                        HDFL.add("REG");
                        break;
                    default:
//                      HDFL.add("");
//                      break;
                        System.out.println("error :" +fb.toString() + " " + iataRouts.get(i) + "_"+ icaoRouts.get(i) + ".getAirportRoutingName() not in (I,D,R)\n");
                        continue;
                    }
                    if(fb.getFlightDirection().equals("0")){ 
                        HD.add(iataRouts.get(i) + "-" + fb.getAirportIATACode());
                    }else if(fb.getFlightDirection().equals("1")){
                        HD.add(fb.getAirportIATACode() + "-" + iataRouts.get(i));
                    }
                }else{
                    System.out.println("error :" +fb.toString() + " airport " + iataRouts.get(i) + "_"+ icaoRouts.get(i) + " not found\n");
                    continue;
                }
            }
            airport = airportCountryTypeMap.get(fb.getAirportIATACode() + "_"+ fb.getAirportICAOCode());
            localAirportRoutingName = airport.getAirportRoutingName();
            if(null!=fb.getFlightData().getFullRouteAirportIATACode() && !fb.getFlightData().getFullRouteAirportIATACode().isEmpty()){
                HX = fb.getFlightData().getFullRouteAirportIATACode().replace(",", "-");
            }
            if(!HX0.isEmpty()){
                HX0=HX0.substring(0, HX0.length()-1);
            }
            if (fb.getFlightDirection().equals("0")) {
                HX += "-" + fb.getAirportIATACode();
                HX0 +="-" + localAirportRoutingName;
                if(fb.getFlightData().getActualLandingDateTime()!=null){
                    SJRQ=fb.getFlightData().getActualLandingDateTime();
                    QJSJ=DateUtils.Date2String(fb.getFlightData().getActualLandingDateTime(), "HH:mm");
                }else{
                    SJRQ=fb.getFlightData().getScheduledLandingDateTime();
                    QJSJ=DateUtils.Date2String(fb.getFlightData().getScheduledLandingDateTime(), "HH:mm");
                }
                JQJD=DateUtils.Date2String(fb.getFlightData().getScheduledLandingDateTime(),"HH:mm");
                IO="I";
            }else{
                HX = fb.getAirportIATACode() + "-" + HX;
                HX0 =localAirportRoutingName + "-" +HX0;
                if(fb.getFlightData().getActualTakeOffDateTime()!=null){
                    SJRQ=fb.getFlightData().getActualTakeOffDateTime();
                    QJSJ=DateUtils.Date2String(fb.getFlightData().getActualTakeOffDateTime(), "HH:mm");
                }else{
                    SJRQ=fb.getFlightData().getScheduledTakeOffDateTime();
                    QJSJ=DateUtils.Date2String(fb.getFlightData().getScheduledTakeOffDateTime(), "HH:mm");
                }
                JQJD=DateUtils.Date2String(fb.getFlightData().getScheduledTakeOffDateTime(),"HH:mm");
                IO="O";
            }

            for(int i=0;i<HD.size();i++){
                FlightLoadData flightLoadData=new FlightLoadData();
                int legno=i;
                //进港航班航段排序与离港相反，从近海口开始排序
                if(fb.getFlightDirection().equals("0")){
                	legno=HD.size()-1-i;
                }
                
                flightLoadData.setLegNo(String.valueOf(legno));
                
                flightLoadData.setGenerateMethod("0");
                flightLoadData.setJhrq(JHRQ);
                flightLoadData.setSjrq(SJRQ);
                flightLoadData.setCyr(CYR);
                flightLoadData.setDm(DM);
                flightLoadData.setSs(SS);
                flightLoadData.setHbh(HBH);
                flightLoadData.setJx(JX);
                flightLoadData.setHbxz(HBXZ);
                flightLoadData.setHx(HX);
                flightLoadData.setHx0(HX0);
                flightLoadData.setHxfl(HXFL);
                
                flightLoadData.setHd(HD.get(i)); 
                flightLoadData.setHdfl(HDFL.get(i));
                flightLoadData.setIo(IO);
                flightLoadData.setJqjd(JQJD);
                flightLoadData.setQjsj(QJSJ);
                flightLoadData.setPbm(PBM);
                flightLoadData.setXg(XG);
                flightLoadData.setBc(BC);
                flightLoadData.setJh(JH);
                flightLoadData.setOperationstatus(OPERATIONSTATUS);
                flightLoadData.setFlightstatus(FLIGHTSTATUS);
                flightLoadData.setFlightbaseid(FLIGHTBASEID);
                flightLoadData.setFlightdataid(FLIGHTDATAID);
                flightLoadData.setFlightIdentity(FLIGHTIDENTITY);
                list.add(flightLoadData);
            }
//        }
        return list;
    }

    public List<FlightLoadData> findByFbIds(List<Long> ids){
        return flightLoadDataDao.findByFbIds(ids);
    }
    
//    private List<FlightLoadData> findByFMIIds(List<Long> ids){
//        return flightLoadDataDao.findByFMIIds(ids);
//    }
 

}
