package com.wonders.frame.ams.service.basic;

import com.wonders.frame.ams.constants.Constant;
import com.wonders.frame.ams.dao.basic.BaseDestinationDao;
import com.wonders.frame.ams.dto.permission.UserDto;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.BaseDestination;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.DateUtil;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.service.SingleCrudService;
import com.wonders.frame.core.utils.SingleParamsConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class BaseDestinationService {

    @Resource(name = "baseDestinationDao")
    private BaseDestinationDao baseDestinationDao;

    @Resource
    private SingleCrudService<ILongIdRemoveFlagModel,Long> basicCrudService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SimplePage<BaseDestination> query(String airportIATACode, String airportICAOCode, String pagenum, String pagesize){
        HashMap<String,String> fliter = new HashMap<String,String>();
        LinkedHashMap<String,String> sorter = new LinkedHashMap<String,String>();
        Integer pageSize = Integer.parseInt(pagesize);
        Integer pageNum = Integer.parseInt(pagenum) + 1;
        if(Chk.spaceCheck(airportIATACode)) {
            fliter.put("airportIATACode_l", airportIATACode.toUpperCase());
        }
        if(Chk.spaceCheck(airportICAOCode)) {
            fliter.put("airportICAOCode_l", airportICAOCode.toUpperCase());
        }
        fliter.put("removeFlag", Constant.NOT_REMOVED);
        sorter.put("airportIATACode", "asc");
        SimplePage<BaseDestination> baseDestinations = baseDestinationDao.findByPage(fliter, sorter, pageNum, pageSize);
        return baseDestinations;
    }

    public BaseDestination findBaseDestinationById(long id) {
        return  baseDestinationDao.findById(id);
    }

    // 移除
    public ReturnObj<Integer> remove(HttpServletRequest req) {
        String id = req.getParameter("id");
        if(null != id){
            logger.debug("remove:id="+id);
            String[] ids=id.split(",");
            if(ids.length>1){
                List<Long> idList=new ArrayList<Long>();
                for(String i:ids){
                    idList.add(Long.valueOf(i));
                }
                return basicCrudService.removeByIds(BaseDestination.class, idList);
            }else{
                return basicCrudService.removeById(BaseDestination.class, Long.valueOf(id));
            }
        }else{
            return null;
        }
    }

    // 修改
    public ReturnObj<ILongIdRemoveFlagModel> update(HttpServletRequest req){
        SingleModelParams smp = SingleParamsConvertUtil.getModelParams(BaseDestination.class, req);
        String loginName = ((UserDto)req.getSession().getAttribute(Constant.LOGIN_USER)).getLoginName();
        smp.addData("modifyUser",loginName);
        smp.addData("modifyTime", DateUtil.getDateString());
        return basicCrudService.saveOrUpdate(smp);
    }

    // 新增
    public ReturnObj<ILongIdRemoveFlagModel> save(HttpServletRequest req){
        SingleModelParams smp = SingleParamsConvertUtil.getModelParams(BaseDestination.class, req);
        String loginName = req.getSession().getAttribute(Constant.LOGIN_USER).toString();
        smp.addData("createUser",loginName);
        smp.addData("createTime",DateUtil.getDateString());
        smp.addData("removeFlag","1");
        return basicCrudService.saveOrUpdate(smp);
    }

}
