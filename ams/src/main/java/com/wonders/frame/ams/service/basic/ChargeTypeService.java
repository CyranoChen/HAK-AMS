package com.wonders.frame.ams.service.basic;


import com.wonders.frame.ams.constants.Constant;
import com.wonders.frame.ams.dao.basic.ChargeTypeDao;
import com.wonders.frame.ams.dao.basic.impl.ChargeTypeJdbcDao;
import com.wonders.frame.ams.dto.permission.UserDto;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.ChargeType;
import com.wonders.frame.ams.utils.DateUtil;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.service.SingleCrudService;
import com.wonders.frame.core.utils.SingleParamsConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ChargeTypeService {

    @Resource
    private ChargeTypeDao chargeTypeDao;

    @Autowired
    private ChargeTypeJdbcDao chargeTypeJdbcDao;

    @Resource
    private SingleCrudService<ILongIdRemoveFlagModel,Long> basicCrudService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 查找所有的收费类型
    public List<ChargeType> query(String chargeName, String chargeCode) {
        HashMap<String,String> filter = new HashMap<String,String>();
        filter.put("name_l", chargeName.toUpperCase());
        filter.put("chargeCode_l", chargeCode.toUpperCase());
        LinkedHashMap<String,String> sorter = new LinkedHashMap<String,String>();
        sorter.put("name","asc");
        List<ChargeType> typeList = chargeTypeDao.findAll(filter, sorter);
        List<ChargeType> countList = chargeTypeJdbcDao.querySubjectCount();
        for(ChargeType type : typeList) {
            for(ChargeType type1 : countList) {
                if (type.getId() == type1.getId()) {
                    type.setSubjectCount(type1.getSubjectCount());
                }
            }
        }
        return typeList;
    }


    // 根据id查找指定的收费类型
    public ChargeType queryChargeTypeById(Long id) {
        return chargeTypeDao.findById(id);
    }

    // 根据指定的字段进行查找
    // 保存修改的记录
    public ReturnObj<ILongIdRemoveFlagModel> update(HttpServletRequest req){
        SingleModelParams smp = SingleParamsConvertUtil.getModelParams(ChargeType.class, req);
        String loginName = ((UserDto)req.getSession().getAttribute(Constant.LOGIN_USER)).getLoginName();
        smp.addData("modifyUser",loginName);
        smp.addData("modifyTime", DateUtil.getDateString());
        return basicCrudService.saveOrUpdate(smp);
    }


}
