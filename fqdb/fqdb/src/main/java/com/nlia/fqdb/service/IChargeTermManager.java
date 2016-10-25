package com.nlia.fqdb.service;

import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.ChargeTerm;
import com.wonders.aiis.core.service.AbstractBaseService;

@Repository
public interface IChargeTermManager extends AbstractBaseService<ChargeTerm, Long>{

}
