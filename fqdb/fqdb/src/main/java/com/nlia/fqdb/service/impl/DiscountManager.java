package com.nlia.fqdb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.configuration.DataHandler;
import com.nlia.fqdb.dao.DiscountDao;
import com.nlia.fqdb.entity.AircraftAirlineAlteration;
import com.nlia.fqdb.entity.Discount;
import com.nlia.fqdb.entity.FlightLoadData;
import com.nlia.fqdb.service.IDiscountManager;
import com.nlia.fqdb.util.CommonData;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class DiscountManager extends AbstractCrudService<Discount, Long>
		implements IDiscountManager {
	@Resource
	private DiscountDao discountDao;
	@Resource
	private AircraftAirlineAlterationManager aircraftAirlineAlterationManager;
	
	@Override
	protected GenericDao<Discount, Long> getDao() {
		return discountDao;
	}

	public List<Discount> findByAAAIds(List<Long>ids){
	    return discountDao.findByAAAIds(ids);
	}
	
    @Override
    public Map<String, Object> copyDiscountFromOneAAA(Long aaaid,
            Map<String, Object> filters) {
        Map<String, Object> ret = new HashMap<>();
        List<Discount> addDiscount = new ArrayList<Discount>();
        List<Discount> delDiscount = new ArrayList<Discount>();
        List<Discount> updateDiscount= new ArrayList<Discount>();
        List<Discount> findDiscountByAAAId= new ArrayList<Discount>();
        List<Long> ids = new ArrayList<>();
        List<AircraftAirlineAlteration> aaas = aircraftAirlineAlterationManager.findBy(filters);
        ret.put("listAAA", aaas);
        if (aaas.size() > 0) {
            Map<String, Object> filters1 = new HashMap<>();
            filters1.put("removeFlag_equal",
                    CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
            filters1.put("aircraftAirlineAlterationId_equal", aaaid);
            findDiscountByAAAId = this.findBy(filters1);
            //生成的Discount List 
            List<Discount> disocuntInMem = new ArrayList<Discount>();
            if (findDiscountByAAAId.size() > 0) {
                for (AircraftAirlineAlteration aaa : aaas) {
                    ids.add(aaa.getId());
                    for (Discount discount : findDiscountByAAAId) {
                        try {
                            Discount tmpDiscount = (Discount) discount.clone();
                            tmpDiscount.setId(null);
                            tmpDiscount.setAircraftAirlineAlterationId(aaa
                                    .getId());
                            disocuntInMem.add(tmpDiscount);
                        } catch (CloneNotSupportedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                //排序disocuntInMem
                DataHandler dataHandlerDisocunt=new DataHandler<Discount>();
                List<String> sorters=new ArrayList();
                sorters.add("aircraftAirlineAlterationId_asc");
                sorters.add("chargeTypeId_asc");
                sorters.add("chargeSubjectId_asc");
                disocuntInMem=dataHandlerDisocunt.sort(disocuntInMem, sorters);
                
                //数据库中的 Discount List
                List<Discount> discountInDB = this.findByAAAIds(ids);
               
                
                //循环disocuntInMem discountInDB得到addDiscount,delDiscount,updateDiscount
                int indexMen = 0, indexDb = 0;
                if(discountInDB.size()>0 && disocuntInMem.size()>0){
                    boolean loopend = false;
                    while (!loopend) {
                        Discount dIndb=discountInDB.get(indexDb);
                        Discount dInMen=disocuntInMem.get(indexMen);
                        int res = dIndb.getAircraftAirlineAlterationId()
                                .compareTo(dInMen.getAircraftAirlineAlterationId()); // AircraftAirlineAlterationId
                        if (res == 0)// getAircraftAirlineAlterationId相同
                        {
                            res =dIndb.getChargeTypeId().compareTo(dInMen.getChargeTypeId());
                            if (res == 0)// getChargeTypeId相同
                            {
                               Long chargeSubjectIdDB=dIndb.getChargeSubjectId()==null?-1:dIndb.getChargeSubjectId();
                               Long chargeSubjectIMen=dInMen.getChargeSubjectId()==null?-1:dInMen.getChargeSubjectId();
                               res = chargeSubjectIdDB.compareTo(chargeSubjectIMen);
                               if(res==0)//getChargeSubjectId相同,覆盖
                               {
                                   dInMen.setId(dIndb.getId());
                                   updateDiscount.add(dInMen);
                                   indexDb++;
                                   indexMen++;
                               }else if (res < 0)// dIndb小,删除dIndb
                               {
                                   delDiscount.add(dIndb);
                                   indexDb++;
                               } else// dIndb大，dInMen入库
                               {
                                   addDiscount.add(dInMen);
                                   indexMen++;
                               }
                            } else if (res < 0)// dIndb小,删除dIndb
                            {
                                delDiscount.add(dIndb);
                                indexDb++;
                            } else// dIndb大，dInMen入库
                            {
                                addDiscount.add(dInMen);
                                indexMen++;
                            }
                        } else if (res < 0)// dIndb小,删除dIndb
                        {
                            delDiscount.add(dIndb);
                            indexDb++;
                        } else// dIndb大，dInMen入库
                        {
                            addDiscount.add(dInMen);
                            indexMen++;
                        }
                        if (indexMen == disocuntInMem.size()
                                || indexDb == discountInDB.size()) {
                            loopend = true;
                        }
                    }
                }
                for (; indexMen < disocuntInMem.size(); indexMen++)// 如果disocuntInMem还有剩余，全部入库
                {
                    addDiscount.add(disocuntInMem.get(indexMen));
                }
                
                for(;indexDb<discountInDB.size();indexDb++)//discountInDB有剩余，删除
                {
                    delDiscount.add(discountInDB.get(indexDb));
                }
                
                // 删除delDiscount
                this.remove(delDiscount);
                //更新updateDiscount
                updateDiscount=(List<Discount>) this.save(updateDiscount);
                //保存addDiscount
                addDiscount = (List<Discount>) this.save(addDiscount);
            }
        }
        ret.put("findDiscount", findDiscountByAAAId);
        ret.put("addDiscount", addDiscount);
        ret.put("delDiscount", delDiscount);
        ret.put("updateDiscount", updateDiscount);
        return ret;
    }
    

}
