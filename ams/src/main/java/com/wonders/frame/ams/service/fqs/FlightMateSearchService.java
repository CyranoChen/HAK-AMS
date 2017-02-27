package com.wonders.frame.ams.service.fqs;

import com.wonders.frame.ams.bean.FlightLoadDataBean;
import com.wonders.frame.ams.bean.FlightMateDetailBean;
import com.wonders.frame.ams.bean.FlightMateFormBean;
import com.wonders.frame.ams.bean.FlightMateListBean;
import com.wonders.frame.ams.dao.BaseDao;
import com.wonders.frame.ams.utils.ArrayUtil;
import com.wonders.frame.ams.utils.Chk;
import com.wonders.frame.ams.utils.ReflectUtil;
import com.wonders.frame.ams.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 3701 on 2015/12/11.
 */
@Service
public class FlightMateSearchService {

    @Resource
    private BaseDao baseDao;


    public Long getInsertSeq(){
        return baseDao.getSeq("hibernate_sequence");
    }


    public String updateLoadData(Long id,String colname,String value){
        return update(id,"flight_load_data",colname,value);
    }


//    @Transactional
    public String update(Long id ,String table ,String colname,String value){
        if(id == null){
            return "该航班数据可能已经被修改或删除,请重新查询";
        }
        if( ! Chk.illegalCharacterCheck(colname) || ! Chk.illegalCharacterCheck(value)){
            return "参数存在非法字符!";
        }


//        if(  Chk.spaceCheck(value) && ! Chk.numberCheck(value)){
//            return "非法的数值!";
//        }
        String sql = "";
        if(Chk.spaceCheck(value)){
            sql = "update " + table + " set " + StringUtil.parseCamelCase(colname) + " = '" + value + "' where id = " + id;
        }else{
            sql = "update " + table + " set " + StringUtil.parseCamelCase(colname) + " =  null  where id = " + id;
        }

        int n = baseDao.getJdbcTemplate().update(sql);
        return "success";

    }




    public String updatePassengerloadFactor(Long id){
        String sql =
                " select round(decode(nvl(f.aircraft_seat_capacity, 0), " +
                "              0, " +
                "              0, " +
                "              (f.passenger_Internal + f.child_Internal + f.baby_Internal + f.passenger_International + f.child_International + f.baby_International) / (f.aircraft_seat_capacity)) , " +
                        "2 )  RTN " +
                "  from flight_mate_info f where f.id = ? ";

        Map<String,Object> m = baseDao.getJdbcTemplate().queryForMap(sql, id);
        String passengerloadFactor = m.get("RTN").toString();

        baseDao.getJdbcTemplate().update("update flight_mate_info f set f.passengerload_factor = " + passengerloadFactor + " where f.id = ? " ,id);

        return passengerloadFactor;

    }

    public List<FlightLoadDataBean> queryByFlightMateInfoId(Long id){
        String sql = "select " +
                "l.id,hd,hdfl,zdyz,zdzw,peyz,pezw,kgyz,kgzw,io,jcn,qjsj,cr,et,ye,crwh,etwh,yewh,xl,yj,hw,wjhz,xljs,l.legno,l.generate_method " +
                " from flight_load_data l,flight_mate_info f where l.flight_base_id = f.flight_base_id and f.id = ? order by l.legno";
        return baseDao.queryForList(sql,FlightLoadDataBean.class,id);
    }


    public FlightMateDetailBean queryById(Long id){
        if(id == null){
            return null;
        }

        String sql = " select " +
                "       f.id," +
                "       f.flight_direction," +
                "       f.air_line_name," +
                "       f.airline_handler," +
                "       decode(f.flight_direction,'0',f.a_registeration,f.d_registeration) registeration," +
                "       decode(f.flight_direction,'0',f.a_flight_identity,f.d_flight_identity) flight_identity," +
                "       f.a_flight_identity, " +
                "       f.d_flight_identity, " +
                "       f.flight_base_id, " +
                "       to_char(f.scheduled_time,'yyyy/mm/dd') scheduled_time," +
                "       to_char(f.scheduled_landed_time,'yyyy/mm/dd hh24:mi') scheduled_landed_time," +
                "       to_char(f.landed_time,'yyyy/mm/dd hh24:mi') landed_time," +
                "       to_char(f.scheduled_take_off_time,'yyyy/mm/dd hh24:mi') scheduled_take_off_time," +
                "       to_char(f.take_off_time,'yyyy/mm/dd hh24:mi') take_off_time," +
                "       f.iata_origin_airport," +
                "       f.iata_destination_airport," +
                "       decode(f.flight_direction,'0',f.a_stand_num,f.d_stand_num) stand_num," +
                "       decode(f.flight_direction,'0',f.a_stand_flag,f.d_stand_flag) stand_flag," +
                "       decode(f.flight_direction,'0',f.a_flight_property,f.d_flight_property) flight_property," +
                "       f.land_time," +
//                "       f.flight_route," +
                "       ( select hx from flight_load_data where flight_base_id = f.flight_base_id and rownum = 1 ) flight_route," +
                "       f.match_method," +
                "       f.delay_code," +
//                "       f.flight_part_0 flightPart0," +
//                "       f.flight_part_1 flightPart1," +
//                "       f.flight_part_2 flightPart2," +
                "       ( select hd from flight_load_data where legno = 0 and flight_base_id = f.flight_base_id and rownum = 1 ) flightPart0," +
                "       ( select hd from flight_load_data where legno = 1 and flight_base_id = f.flight_base_id and rownum = 1 ) flightPart1," +
                "       ( select hd from flight_load_data where legno = 2 and flight_base_id = f.flight_base_id and rownum = 1 ) flightPart2," +
                "       f.is_night_flight," +
                "       f.is_peak_flight," +
                "       f.aircraft_service," +
                "       f.special_plane," +
                "       decode(f.flight_direction,'0',f.a_aircraft_type,f.d_aircraft_type) aircraft_type," +
                "       f.aircraft_payload," +
                "       f.aircraft_seat_capacity," +
                "       f.aircraft_take_off_weight," +
                "       f.is_wide_or_narrow," +
                "       f.passenger_bridge_number," +
                "       f.remark," +
                "       f.passenger_internal," +
                "       f.baby_internal," +
                "       f.child_internal," +
                "       f.air_cargo_weight_internal," +
                "       f.luggage_weight_internal," +
                "       f.air_mail_weight_internal," +
                "       f.luggage_num_internal," +
                "       f.passenger_international," +
                "       f.baby_international," +
                "       f.child_international," +
                "       f.air_cargo_weight_international," +
                "       f.luggage_weight_international," +
                "       f.air_mail_weight_international," +
                "       f.luggage_num_international," +
                "       f.via_adult," +
                "       f.via_baby," +
                "       f.via_child," +
                "       f.luggage_weight_via," +
                "       f.air_mail_weight_via," +
                "       f.luggage_num_via," +
                "       f.air_cargo_weight_via," +
                "       f.lead_car_used_count," +
                "       f.air_truck_time," +
                "       f.electric_truck_time," +
                "       f.tractors_used_count," +
                "       f.airconditioning_time," +
                "       f.deicing_truck_used_time," +
                "       f.psssenger_car_used_time," +
                "       f.airdrome_used_by_pas_count," +
                "       f.airdrome_used_by_crew_count," +
                "       f.lifting_platform_car_time," +
                "       f.unroutine_check_time," +
                "       f.routine_check_time," +
                "       f.spe_board_car_used_count," +
                "       f.garbage_truck_use_count," +
                "       f.cesspoolage_truck_use_count," +
                "       f.water_truck_use_count," +
                "       f.bridge_electric_used_time," +
                "       f.bridge_aircondition_used_time," +
                "       f.passenger_bridge_time," +
                "       f.vip_count," +
                "       f.first_class_count," +
                "       f.vip_first_airdrome_used_count," +
                "       f.update_role," +
                "       f.is_high_density," +
                "       f.passengerload_factor, " +
                "       f.stat, " +
                "       f.flight_country_type, " +
                "       f.flight_part_0_type flightPart0Type, " +
                "       f.abnormal_flag, " +
                "       a.airline_iata_code "  +
        "  from flight_mate_info f  left join base_airline a on f.air_line_id = a.id " +
                "   where f.id = " + id;

        return baseDao.queryForObject(sql, FlightMateDetailBean.class);


    }



    public List<FlightMateListBean> list(FlightMateFormBean form){
        List<Object> args = new ArrayList<Object>();
        String sql = "select r.* from  ( " +
                "  select f.id," +
                "       f.flight_direction," +
                "       f.air_line_name," +
                "       f.airline_handler," +
                "       decode(f.flight_direction,'0',f.a_registeration,f.d_registeration) registeration," +
                "       decode(f.flight_direction,'0',f.a_flight_identity,f.d_flight_identity) flight_identity," +
                "       to_char(f.scheduled_time,'yyyy/mm/dd') scheduled_time," +
                "       f.a_flight_identity," +
                "       to_char(f.scheduled_landed_time,'yyyy/mm/dd hh24:mi') scheduled_landed_time," +
                "       to_char(f.landed_time,'yyyy/mm/dd hh24:mi') landed_time," +
                "       f.d_flight_identity," +
                "       to_char(f.scheduled_take_off_time,'yyyy/mm/dd hh24:mi') scheduled_take_off_time," +
                "       to_char(f.take_off_time,'yyyy/mm/dd hh24:mi') take_off_time," +
                "       f.iata_origin_airport," +
                "       f.iata_destination_airport," +
                "       f.stat," +
                "       decode(f.flight_direction,'0',f.a_stand_num,f.d_stand_num) stand_num," +
                "       decode(f.flight_direction,'0',f.a_stand_flag,f.d_stand_flag) stand_flag," +
                "       airdrome_used_by_pas_count," +
                "       airdrome_used_by_crew_count," +
                "       psssenger_car_used_time," +
                "       bridge_electric_used_time," +
                "       aircraft_service," +
                "       bridge_aircondition_used_time," +
                "       passenger_bridge_time," +
                "       vip_count," +
                "       first_class_count," +
                "       abnormal_flag," +
                "       update_role," +
                "       stat \"stat\" ," +
                "       nvl(a.airline_unique_code,a.airline_icao_code) icao_code, " +
                "       a.airline_iata_code, " +
                "       decode(f.flight_direction,'0',f.a_aircraft_type,f.d_aircraft_type) aircraft_type," +
                "       f.remove_flag " +
                /**
                 * edit by 20160105
                 * 获取三字码 需要关联base_airline表
                 */

                "  from flight_mate_info f left join base_airline a on f.air_line_id = a.id ) r where r.remove_flag = '1'";

        if(Chk.spaceCheck(form.getFlightIdentity())){
            sql += " and r.flight_identity like ? ";
            args.add("%" + form.getFlightIdentity().toUpperCase() + "%");
        }

        if(Chk.spaceCheck(form.getAirLineName())){
            sql += " and  ( r.air_line_name like ? or icao_code = ? or a_flight_identity like ? or d_flight_identity like ? )";
            String a = form.getAirLineName();
            args.add("%" + a + "%");
            args.add(a.toUpperCase());
            args.add(a.toUpperCase() + "%");
            args.add(a.toUpperCase() + "%");
        }

        if(Chk.spaceCheck(form.getFlightDirection())){
            sql += " and r.flight_direction = ? ";
            args.add(form.getFlightDirection());
        }

//        form.setScheduledTime("2015/07/22");
        if(Chk.spaceCheck(form.getScheduledTime())){
            String [] dr = form.getScheduledTime().split(" - ");
            if(dr.length == 2){
                if(Chk.spaceCheck(dr[0])){
                    sql += " and r.scheduled_time >= ? ";
                    args.add(dr[0]);
                }
                if(Chk.spaceCheck(dr[1])){
                    sql += " and r.scheduled_time <= ? ";
                    args.add(dr[1]);
                }
            }

        }

        if(Chk.spaceCheck(form.getRegisteration())){
            sql += " and r.registeration like ? ";
            args.add("%" + form.getRegisteration().toUpperCase() + "%");
        }

        if(Chk.spaceCheck(form.getStandFlag())){
            sql += " and r.stand_flag = ? ";
            args.add(form.getStandFlag());
        }
        
        if("0".equals(form.getAirlineHandler())){
            sql += " and r.airline_handler = ? ";
            args.add("海航代理");
        }
        
        if("1".equals(form.getAirlineHandler())){
            sql += " and r.airline_handler = ? ";
            args.add("南航代理");
        }
        
        if("2".equals(form.getAirlineHandler())){
            sql += " and r.airline_handler = ? ";
            args.add("美兰代理");
        }

//        sql += " order by r.scheduled_take_off_time,r.flight_direction";
        sql += " order by decode(r.flight_direction,'0',r.scheduled_landed_time,r.scheduled_take_off_time),r.flight_direction,r.d_flight_identity";




        return baseDao.queryForList(sql, FlightMateListBean.class, ArrayUtil.toArray(args, Object.class));

    }


    public String getXmlToExport(Map<String,String> key,List<FlightMateListBean> list){
        String [] keys = new String [key.size()];
        StringBuffer col = new StringBuffer();
        StringBuffer sb = new StringBuffer();

        sb.append("<Row>");

        int k = 0;
        for(Iterator<String> it = key.keySet().iterator() ; it.hasNext() ; ){

            keys[k] = it.next();

            String text = key.get(keys[k]);
            if("0".equals(text)){
                keys[k] = null;
            }else{

                sb.append("<Cell ss:StyleID=\"s1\"><Data ss:Type=\"String\">");

                sb.append(text);
                sb.append("</Data></Cell>");

                col.append( " <Column ss:AutoFitWidth=\"0\" ss:Width=\"100\"/>");
            }

            k ++;
        }
        sb.append("</Row>");


        Class clz = FlightMateListBean.class;

        int size = list.size();
        for(int i = 0 ; i < size ; i ++){
            sb.append("<Row>");
            for(int j = 0 ; j < keys.length ; j ++){
                if(keys[j] != null){
                    String value = ReflectUtil.getValueByGet(keys[j],list.get(i));
                    sb.append("<Cell><Data ss:Type=\"String\">").append(StringUtil.nullToEmptyString(value)).append("</Data></Cell>");
                }

            }

            sb.append("</Row>");

        }




        String xml = "";
        xml += "	<?xml version=\"1.0\"?>";
        xml += "	<?mso-application progid=\"Excel.Sheet\"?>";
        xml += "	<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"";
        xml += "	 xmlns:o=\"urn:schemas-microsoft-com:office:office\"";
        xml += "	 xmlns:x=\"urn:schemas-microsoft-com:office:excel\"";
        xml += "	 xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"";
        xml += "	 xmlns:html=\"http://www.w3.org/TR/REC-html40\">";

        xml += "	 <Styles>";

        xml += "<Style ss:ID=\"Default\" ss:Name=\"Normal\">";
        xml += "<Alignment ss:Vertical=\"Bottom\"/>";
        xml += "<Borders/>";
        xml += "<Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"9\" ss:Color=\"#000000\"/>";
        xml += "<Interior/>";
        xml += "<NumberFormat/>";
        xml += "<Protection/>";
        xml += "</Style>";


        xml += "	  <Style ss:ID=\"s1\">";
        xml += "	  	   <Interior ss:Color=\"#C6E0B4\" ss:Pattern=\"Solid\"/>";
        xml += "	  </Style>";
        xml += "	  <Style ss:ID=\"s2\">";
        xml += "	  <Interior ss:Color=\"#DBDBDB\" ss:Pattern=\"Solid\"/>";
        xml += "	  </Style>";
        xml += "	  </Styles>";


        xml += "	 <Worksheet ss:Name=\"Sheet1\">";

        xml += "	  <Table  x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultRowHeight=\"12\">";


        xml += col.toString();
        xml += sb.toString();
        xml += "	  </Table>";

        xml += "	 </Worksheet>";
        xml += "	</Workbook>";


        return xml;
    }


}
