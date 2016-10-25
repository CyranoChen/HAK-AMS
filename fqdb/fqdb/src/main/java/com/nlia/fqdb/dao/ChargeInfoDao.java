package com.nlia.fqdb.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nlia.fqdb.entity.FlightMateInfo;
import com.nlia.fqdb.entity.SyntheticChargeTerm;
import com.nlia.fqdb.util.CommonData;
import com.nlia.fqdb.vo.ChargeInfoVo;
import com.nlia.fqdb.vo.ChargeTermVo;
import com.nlia.fqdb.vo.SyntheticChargeInfoVo;

@Repository
public class ChargeInfoDao {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * 从base_airline表中 补全 chargeTarget数据以保证数据完整性
	 */
	public int fullChargeTarget(){
		String sql = " insert into charge_target (id,create_time,create_user,modify_time,modify_user,remove_flag,airline_id,subairline_id) "
				  + " select hibernate_sequence.nextval,sysdate,'系统管理员',null,null,'1',nvl(parent_airline_id, id),id"
				  + " from base_airline  "
				  + "where id not in (select distinct subairline_id from charge_target) ";
		return jdbcTemplate.update(sql);

	}
	
	
	
	/**
	 * 查询所有收费计算时所用到的信息,机号\收费对象\有效期\收费项目
	 */
	public List<ChargeInfoVo> queryChargeInfo(){
		String sql = " select "
							+ " t.id target_id, "
							+"  t.subairline_id, "
							+ " wmsys.wm_concat(x.charge_subject_id) subject_id "
						+ " from "
							+ " charge_target t "
						+ " left join "
							+ " charge_target_x_charge_subject x"
						+ " on "
							+ " t.id = x.charge_target_id"
						+ " and "
							+ "t.remove_flag = '" +CommonData.REMOVEFLAG.NOT_REMOVED.getValue() + "'" 
						+ " group by "
							+ "t.id, "
							+ "t.subairline_id " ;
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper(ChargeInfoVo.class));

	}
	
	
	/**
	 * 删除原有的统计数据
	 * @param args
	 */
	public void deleteSyntheticChargeTerm(List<Long> flightMateInfoIds){
		StringBuffer sb = new StringBuffer(" where 1 <> 1 ");
		for(Long id : flightMateInfoIds){
			sb.append(" or id = ").append(id);
		}
		//先删除原有的统计数据
		jdbcTemplate.update("delete from synthetic_charge_term " + sb.toString());
		
	}
	
	/**
	 * 保存SyntheticChargeTerm
	 * @param terms
	 */
	public void saveSyntheticChargeTerm(List<SyntheticChargeTerm> args){
		String sql = "insert into synthetic_charge_term ("
				+ "id, "
				+ "alighting_and_take_off_fee, "
				+ "booking_office_fee, "
				+ "bridge_fee, "
				+ "check_in_counter_fee, "
				+ "flight_duty_fee, "
				+ "flight_service_fee, "
				+ "generally_agent_fee, "
				+ "parking_fee, "
				+ "passenger_service_fee, "
				+ "security_check_fee, "
				+ "tarnsportation_service_fee, "
				+ "wares_and_post_fee, "
				+ "flight_mate_info_id, "
				+ "remove_flag, "
//				+ "modify_time, "
//				+ "modify_user, "
//				+ "create_time, "
//				+ "create_user, "
				+ "stowage_communication_fee) "
				+ "values ( hibernate_sequence.nextval, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		List<Object []>  batchArgs = new ArrayList<Object []>();
		for(SyntheticChargeTerm t : args){
			batchArgs.add(new Object [] {
					t.getAlightingAndTakeOffFee(),
					t.getBookingOfficeFee(),
					t.getBridgeFee(),
					t.getCheck_inCounterFee(),
					t.getFlightDutyFee(),
					t.getFlightServiceFee(),
					t.getGenerallyAgentFee(),
					t.getParkingFee(),
					t.getPassengerServiceFee(),
					t.getSecurityCheckFee(),
					t.getTransportationServiceFee(),
					t.getWaresAndPostFee(),
					t.getFlightMateInfo().getId(),
					t.getRemoveFlag(),
					t.getStowageCommunicationFee()
			});
		}
		
		jdbcTemplate.batchUpdate(sql, batchArgs);

		
	}
	
	/**
	 * 根据ID获取charge_term统计数据
	 */
	public List<SyntheticChargeInfoVo> getSumChargeTerm(List<Long> flightMateInfoIds){
		StringBuffer sb = new StringBuffer(" where 1 <> 1 ");
		for(Long id : flightMateInfoIds){
			sb.append(" or t.flight_mate_info_id = ").append(id);
		}

		String sql = "select t.flight_mate_info_id, t.charge_type_id, sum(t.fee) fee "
 						+ " from charge_term t "
 						+ sb.toString()
 						+ " group by t.charge_type_id, t.flight_mate_info_id ";
		
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper(SyntheticChargeInfoVo.class));
	}
	
	
	
	/**
	 * 批量更新flight_mate_info 状态标识
	 */
	public int updateFlightMateInfoStat(List<Long> flightMateInfoIds,String stat){
		
		StringBuffer sb = new StringBuffer("update flight_mate_info set stat = ?  where 1 <> 1 ");
		for(Long id : flightMateInfoIds){
			sb.append(" or id = ").append(id);
		}
		return jdbcTemplate.update(sb.toString(), stat);
	}

	
	/**
	 * 批量插入收费条目,返回所有收费航班的ID
	 * @param args
	 * @return
	 */
	public List<Long> saveChargeTerms(List<ChargeTermVo> args){
	    HashSet<Long> hashset=new HashSet<Long>();


		
		 String delsql="update charge_term set remove_flag='0' where flight_mate_info_id=?";
		 
		//List<Object []>  batchArgs = new ArrayList<Object []>();
		List<Object []>  batchArgdel = new ArrayList<Object []>();
		for(ChargeTermVo t : args){
			/*
			batchArgs.add(new Object [] {
					t.getCreateTime(),
					t.getCreateUser(),
					t.getCurrency(),
					t.getCurrencyUnit(),
					t.getFee(),
					t.getModifyTime(),
					t.getModifyUser(),
					t.getRemoveFlag(),
					t.getChargeTargetId(),
					t.getChargeTypeId(),
					t.getFlightMateInfoId(),
					t.getName(),
					t.getChargeSubjectId(),
					t.getRemark()
			});
			*/
			if(!hashset.contains(t.getFlightMateInfoId())){
			    hashset.add(t.getFlightMateInfoId());
			    batchArgdel.add(new Object[]{t.getFlightMateInfoId()});
			}
		}
		
		
//		int [] types = new int[] {
//				Types.TIMESTAMP,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.FLOAT,
//				Types.TIMESTAMP,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.NUMERIC,
//				Types.NUMERIC,
//				Types.NUMERIC,
//				Types.VARCHAR,
//				Types.NUMERIC,
//				Types.VARCHAR
//		};
		
		jdbcTemplate.batchUpdate(delsql, batchArgdel);
//		jdbcTemplate.batchUpdate(sql, batchArgs,types);		

		loopExecuteBatchInsertChargeTerm(args,10000);
		
		//计算折后价
		if(!args.isEmpty()){
		 String feeXdiscountsql = "update charge_term ct set ct.fee"
		          + "=(select g.feeXdiscount from getfeexdiscount g where g.id=ct.id and ct.create_time=?) "
		          + "where exists (select 1 from getfeexdiscount g where g.id=ct.id and ct.create_time=?)";
		Date ct= args.get(0).getCreateTime();
		jdbcTemplate.update(feeXdiscountsql,new Object[]{ct,ct},new int[]{Types.TIMESTAMP,Types.TIMESTAMP});
		}
		
		//备降航班旅客服务费打对折
        if (!args.isEmpty()) {
            String bjsql = "update CHARGE_TERM ct set ct.fee="
                    + "(select t.fee/2 from CHARGE_TERM t left join flight_mate_info fmi on "
                    + "t.flight_mate_info_id=fmi.id and fmi.remove_flag='1' and t.remove_flag='1' where t.charge_type_id=4 "
                    + "and fmi.abnormal_flag='1' and t.id=ct.id and ct.create_time=?)，ct.remark=ct.remark || ';备降航班旅客服务费打对折'"
                    + "where exists(select 1 from CHARGE_TERM t left join flight_mate_info fmi on "
                    + "t.flight_mate_info_id=fmi.id and fmi.remove_flag='1' and t.remove_flag='1' where t.charge_type_id=4 "
                    + "and fmi.abnormal_flag='1' and t.id=ct.id and ct.create_time=?)";
            Date ct = args.get(0).getCreateTime();
            jdbcTemplate.update(bjsql, new Object[] { ct, ct }, new int[] {
                    Types.TIMESTAMP, Types.TIMESTAMP });
        }
        //返航 天气、机械原因航空性收费为0
        String  fhsql = "update charge_term ct set ct.fee=0.00,ct.remark=ct.remark || ';返航 天气、机械原因航空性收费为0' where ct.remove_flag='1' "
                + " and ct.charge_subject_id in (select id from charge_subject cs where cs.charge_property='0' and cs.remove_flag='1')"
                + " and ct.flight_mate_info_id in ("
                + " select id from flight_mate_info fmi where fmi.remove_flag='1' "
                + " and fmi.abnormal_flag='2' and fmi.delay_code in ("
                + "select bdc.delay_code from BASE_DELAY_CODE bdc where (bdc.delay_reason like '%天气%'  or bdc.delay_reason like '%机械%'  ) "
                + "and bdc.remove_flag='1'))";
        jdbcTemplate.update(fhsql);
        
		return new ArrayList<Long>(hashset);
	}
	
	public void loopExecuteBatchInsertChargeTerm(List<ChargeTermVo> list,int batchSize){
		int loop=list.size()/batchSize;
		
	   	int ys=list.size()%batchSize;
	   	if(ys==0){
	   		loop--;
	   	}
	   	
	   	for(int i=0;i<=loop;i++){
	       	int toIndex=(i+1)*batchSize;
	       	
	       	if(i==loop){
	       		toIndex=list.size();
	       	}       
	       	

	       	batchInsertChargeTerm(list.subList(i*batchSize, toIndex));

	   	}
	}
	
	public void batchInsertChargeTerm(final List<ChargeTermVo> list){
	    String sql = "insert into charge_term ( "
				+ "id, "							
				+ "create_time, "
				+ "create_user, "
				+ "currency, "
				+ "currency_unit, "
				+ "fee_befor_discount, "
				+ "modify_time, "
				+ "modify_user, "
				+ "remove_flag, "
				+ "charge_target_id, "
				+ "charge_type_id, "
				+ "flight_mate_info_id, "
				+ "name, "
				+ "charge_subject_id, "
				+ "remark,"
				+ "fee"
				+ " ) "
				+ " values ( hibernate_sequence.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0 ) ";
	    
		jdbcTemplate.batchUpdate(sql, 
				new BatchPreparedStatementSetter(){

					@Override
					public int getBatchSize() {
						return list.size();
					}

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						ChargeTermVo ctv=list.get(i);
						ps.setTimestamp(1, new Timestamp(ctv.getCreateTime().getTime()));
						ps.setString(2,ctv.getCreateUser());						
						ps.setString(3,ctv.getCurrency());
						ps.setString(4,ctv.getCurrencyUnit());
						ps.setDouble(5,ctv.getFee()); 
						if(ctv.getModifyTime()!=null){						
							ps.setTimestamp(6,new Timestamp(ctv.getModifyTime().getTime()));
						}else{
							ps.setTimestamp(6,null);
						}
						ps.setString(7,ctv.getModifyUser());
						ps.setString(8,ctv.getRemoveFlag());
						ps.setLong(9,ctv.getChargeTargetId());
						ps.setLong(10,ctv.getChargeTypeId());
						ps.setLong(11,ctv.getFlightMateInfoId());
						ps.setString(12,ctv.getName());
						ps.setLong(13,ctv.getChargeSubjectId());
						ps.setString(14,ctv.getRemark());
						
					}
			
		});
		
		
	}
	public static void main(String[] args){
		int[] a={1,2,3,4};
		StringBuffer sb=new StringBuffer();
		for(int i:a){
			sb.append(i).append(",");
		}
		System.out.println(sb.toString());
	}
	
}
