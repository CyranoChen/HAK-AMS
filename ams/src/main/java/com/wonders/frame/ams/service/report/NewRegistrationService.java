package com.wonders.frame.ams.service.report;

import com.wonders.frame.ams.dao.BaseDao;
import com.wonders.frame.ams.utils.Chk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 3701 on 2016/10/19.
 */
@Service
public class NewRegistrationService {


    @Autowired
    private BaseDao baseDao;

    public List<Map<String,Object>> queryNewRegistration(String registration,String airlineOfFlight ){
        String sql = "select aaa.aircraft_registration, ba.airline_description, ba.airline_iata_code, aaa.aircraft_type_iata_code, aaa.aircraft_seat_capacity," +
                "aaa.is_high_density, aaa.aircraft_takeoff_weight, aaa.aircraft_payload  " +
                "from aircraft_airline_alteration aaa inner join base_airline ba " +
                "on ba.remove_flag =1 and ba.id = aaa.current_airline_id and ba.airline_home_country = 'CN'  " +
                "where aaa.remove_flag = 1 " +
                "and  to_char(aaa.end_date,'yyyy') = '2099'  " +
                "and aaa.aircraft_registration not in  " +
                "(select distinct ad.registration from aircraft_discount ad where ad.remove_flag = 1) " ;

        List<Object> args = new ArrayList<Object>();

        if(Chk.spaceCheck(registration)){
            sql += " and aaa.aircraft_registration like ? ";
            args.add("%" + registration + "%");
        }

        if(Chk.spaceCheck(airlineOfFlight)){
            sql += " and ba.airline_iata_code like ? ";
            args.add("%" + airlineOfFlight + "%");
        }


          sql +=       " order by ba.airline_iata_code, aaa.aircraft_registration";

        return baseDao.getJdbcTemplate().queryForList(sql,(Object [])args.toArray(new String[args.size()]));
    }
}
