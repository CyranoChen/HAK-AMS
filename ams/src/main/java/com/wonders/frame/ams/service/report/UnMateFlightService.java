package com.wonders.frame.ams.service.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UnMateFlightService {
	
	@Resource
	private JdbcTemplate jt;
	
    public List<Map<String,Object>> query(String startTime,String endTime){
    	String sql = "select case fb.flight_direction when '0' then 'A' when '1' then 'D' end flight_direction,"
    			+ "       ba.airline_description as air_line_name,"
    			+ "       fd.registeration,"
    			+ "       fb.flight_identity,"
    			+ "       to_char(fb.flight_scheduled_date, 'yyyy/mm/dd') scheduled_date,"
				+ "       decode(fb.flight_direction,'0',"
				+ "              to_char(fd.scheduled_landing_time, 'hh24:mi'),"
				+ "              to_char(fd.scheduled_take_off_time, 'hh24:mi')) scheduled_time,"
    			+ "       decode(fb.flight_direction,'0',"
    			+ "              to_char(fd.actual_landing_time, 'yyyy/mm/dd hh24:mi'),"
    			+ "              to_char(fd.actual_take_off_time, 'yyyy/mm/dd hh24:mi')) actual_time,"
    			+ "       decode(fb.flight_direction, '0', fd.iata_origin_airport, 'HAK') iata_origin_airport,"
    			+ "       decode(fb.flight_direction, '0', 'HAK', fd.iata_destination_airport) iata_destination_airport"
    			+ "  from flight_base fb"
    			+ " left join flight_data fd"
    			+ "    on fb.flight_data_id = fd.id"
    			+ "   and fd.remove_flag = '1'"
    			+ " left join aircraft_airline_alteration aaa"
    			+ "    on fd.registeration = aaa.aircraft_registration "
    			+ "    and aaa.remove_flag = '1'"
    			+ "    and fb.flight_scheduled_date between aaa.start_date and aaa.end_date"
    			+ " left join base_airline ba on ba.id = aaa.current_subairline_id"
    			+ " where fb.remove_flag = '1'"
    			+ "   and not exists "
    			+ "       (select 1"
    			+ "          from flight_mate_info fmi"
    			+ "          where fmi.remove_flag = '1' and fmi.flight_base_id = fb.id";
    	if((startTime!=null&&!("").equals(startTime))&&(endTime!=null&&!("").equals(endTime))){
			sql += "          and to_char(decode(fb.flight_direction, '0',fmi.scheduled_landed_time,fmi.scheduled_take_off_time ), 'yyyy-mm-dd') between ? and ? ";
		}
    	sql += "          )";
		if((startTime!=null&&!("").equals(startTime))&&(endTime!=null&&!("").equals(endTime))){
			sql += "   and to_char(fb.flight_scheduled_date, 'yyyy-mm-dd') between ? and ? ";
		}


		//20161001 未配对航班，order by 执行日, 计划时间
		sql = "select t.* from ( " + sql + " ) t order by t.scheduled_date,t.scheduled_time";

    	List<Map<String,Object>> list = jt.queryForList(sql,startTime,endTime,startTime,endTime);
    	return list;
    }
}
