package com.wonders.frame.ams.dao.basic.impl;

import com.wonders.frame.ams.model.basic.ChargeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/*
    本类的作用在于可以在收费类型中根据收费大类查询出对应的收费项目数, 并将查询出来的ChargeType
    进行组成list并且进行返回
 */
@Repository
public class ChargeTypeJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 收费科目查询, 查询出对象, 将在Service层进行根据id的匹配
    public List<ChargeType> querySubjectCount() {
        String sql = "SELECT t.id, s.count "
                + "FROM CHARGE_TYPE t LEFT JOIN "
                + "(SELECT CHARGE_TYPE_ID AS typeid, count(*) AS count "
                + "FROM CHARGE_SUBJECT "
                + "WHERE remove_flag = 1 "
                + "GROUP BY CHARGE_TYPE_ID) s "
                + "ON t.id = s.typeid";
        List<ChargeType> list = jdbcTemplate.query(sql, new SubRowMapper());
        return list;
    }

    // 查询的是收费类型的id和费用名称的对应关系
    public List<ChargeType> queryChargeTypeIdAndName() {
        String sql = "SELECT id, name FROM CHARGE_TYPE ORDER BY name ASC";
        List<ChargeType> list = jdbcTemplate.query(sql, new SubRowMapper1());
        return list;
    }

    class SubRowMapper implements RowMapper<ChargeType> {

        /*
         * index:正在被访问的记录的下标。
         */
        public ChargeType mapRow(ResultSet rst, int index) throws SQLException {
            ChargeType type = new ChargeType();
            type.setId((long)rst.getInt("id"));
            type.setSubjectCount(rst.getInt("count"));
            return type;
        }

    }

    class SubRowMapper1 implements RowMapper<ChargeType> {

        /*
         * index:正在被访问的记录的下标。
         */
        public ChargeType mapRow(ResultSet rst, int index) throws SQLException {
            ChargeType type = new ChargeType();
            type.setId((long)rst.getInt("id"));
            type.setName(rst.getString("name"));
            return type;
        }

    }
}
