package com.nlia.fqdb.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nlia.fqdb.dao.CheckInDeskDao;
import com.nlia.fqdb.entity.base.CheckInDesk;
import com.nlia.fqdb.service.ICheckInDeskManager;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.util.base.ImportCheckInDeskByExcel;
import com.wonders.aiis.core.dao.GenericDao;
import com.wonders.aiis.core.service.AbstractCrudService;

@Service
public class CheckInDeskManager extends AbstractCrudService<CheckInDesk, Long>
	implements ICheckInDeskManager {
    @Resource
    private CheckInDeskDao checkInDeskDao;

    @Override
    protected GenericDao<CheckInDesk,Long> getDao() {
	return checkInDeskDao;
    }

    /*
     * 查指定值机柜台
     * 参数：aircraftRegistration
     */
    @Override
    public List<CheckInDesk> findByCheckInDeskCode(String checkInDeskCode ) {
	Map<String, Object> filters = new HashMap<>();
	filters.put("removeFlag_equal",CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
	filters.put("checkInDeskCode_equal", checkInDeskCode );
	List<CheckInDesk> airlineList = checkInDeskDao.findBy(filters, null, -1, -1);
	return airlineList;
    }

    /*
     * 查询全部值机柜台
     */
    @Override
    public List<CheckInDesk> findAllCheckInDesk() {
	Map<String, Object> filters = new HashMap<>();
	filters.put("removeFlag_equal",
		CommonData.REMOVEFLAG.NOT_REMOVED.getValue());
	List<CheckInDesk> airlineList = checkInDeskDao.findBy(filters, null, -1, -1);
	return airlineList;
    }
	
	@Resource
	private ImportCheckInDeskByExcel importCheckInDeskByExcel;
	
	@Override
	public List<CheckInDesk> importByExcel2003(byte[] bytes) {
		List<CheckInDesk> checkInDeskList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			checkInDeskList = importCheckInDeskByExcel.ImportCheckInDeskByExcel2003(input);
		} catch (IOException e) {
		}
		return checkInDeskList;
	}

	@Override
	public List<CheckInDesk> importByExcel2007(byte[] bytes) {
		List<CheckInDesk> checkInDeskList = new ArrayList<>();

		InputStream input = new ByteArrayInputStream(bytes);
		try {
			checkInDeskList = importCheckInDeskByExcel.ImportCheckInDeskByExcel2007(input);
		} catch (IOException e) {
		}
		return checkInDeskList;
	}
}
