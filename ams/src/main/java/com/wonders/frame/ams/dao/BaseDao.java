package com.wonders.frame.ams.dao;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class BaseDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    
    public <T> List<T> queryForList(String sql,Class<?> clz,Object ...objects){
        return ( objects == null || objects.length == 0 ) ?
                jdbcTemplate.query(sql, new BeanPropertyRowMapper(clz)) : jdbcTemplate.query(sql ,objects,new BeanPropertyRowMapper(clz));
    }



    public <T> T queryForObject(String sql,Class<?> clz,Object ...objects){
        List<T> list = queryForList(sql,clz,objects);
        return ( list != null && ! list.isEmpty() ) ? list.get(0) : null;
    }




    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }



    public Long getSeq(String seqName){
        String sql = "select " + seqName + ".nextval SEQ from dual";
        Map m = getJdbcTemplate().queryForMap(sql);
        Long seq = Long.parseLong(m.get("SEQ").toString());
        return seq;
    }

}
