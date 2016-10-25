package com.nlia.fqdb.service;

import java.util.Map;

import com.nlia.fqdb.entity.Discount;
import com.wonders.aiis.core.service.AbstractBaseService;

public interface IDiscountManager extends AbstractBaseService<Discount, Long> {
    
    public Map<String, Object> copyDiscountFromOneAAA(Long aaaid,Map<String, Object> filters);
}
