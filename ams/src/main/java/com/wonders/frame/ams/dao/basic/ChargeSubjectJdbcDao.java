package com.wonders.frame.ams.dao.basic;

import com.wonders.frame.ams.dao.BaseDao;
import com.wonders.frame.ams.model.basic.RuleExpModel;
import com.wonders.frame.core.model.bo.Rule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ChargeSubjectJdbcDao extends BaseDao {

    @Resource
    private BaseDao baseDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    /*根据传入的ruleid查询到规则id以及它的字表达式的id*/
    public List<RuleExpModel> queryRulesById(long id) {
        String sql = "select 'G'||t.id id , t.connector key, '' logic , '' value ,'G'||t.parent_rule_group_id pid " +
                "from rule_group t " +
                "connect by prior t.id = t.parent_rule_group_id " +
                "start with id = ? " +  // and t.parent_rule_group_id is null
                " union " +
                " select 'E'||e.id id ,e.identity key, e.operator logic, e.expression_value value ,'G'||e.rule_group_id pid  from rule_single_expression  e " +
                "where e.rule_group_id in ( " +
                "select t.id " +
                "from rule_group t " +
                "where not exists (select 1 from rule_group g where t.id = g.parent_rule_group_id) " +
                "connect by prior t.id = t.parent_rule_group_id " +
                "start with id = ?  )"; //and t.parent_rule_group_id is null

        List<RuleExpModel> rules = super.queryForList(sql,RuleExpModel.class,new Object[] {id,id});
        return rules;

    }

    // 更新新增的subject的ruleId
    public void updateSubjectRuleId(long id) {
        String sql = "update CHARGE_SUBJECT t set charge_rule_id = ? where id = ?";
        jdbcTemplate.update(sql, new Object[]{id, id});
    }

    // 新插入一条ruleGroup
    public void insertFirstRule(long id) {
        String sql = "insert into RULE_GROUP (ID, CONNECTOR) values (?, 'and')";
        jdbcTemplate.update(sql, new Object[]{id});
    }

    public RuleExpModel queryGroupById(long id) {
        String sql = "select 'G'||t.id id , t.connector key, '' logic , '' value ,'G'||t.parent_rule_group_id pid from rule_group t where id = ?";
        return super.queryForObject(sql, RuleExpModel.class, new Object[] {id});
    }

    public RuleExpModel queryExpById(long id) {
        String sql = "select 'E'||e.id id ,e.identity key, e.operator logic, e.expression_value value ,'G'||e.rule_group_id pid " +
                "from rule_single_expression e " +
                "where id = ?";
        return super.queryForObject(sql, RuleExpModel.class, new Object[] {id});
    }

    public String updateGroupById(String id, String group) {
        try {
            if (id == null || id.trim().isEmpty() || group == null || group.trim().isEmpty()) {
                return "error";
            }
            String sql = "update RULE_GROUP set CONNECTOR = ? where ID = ?";
            jdbcTemplate.update(sql, new Object[]{group, Long.parseLong(id)});
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String updateExpById(String id, String exp, String opt, String value) {
        try {
            if (id == null || id.trim().isEmpty()) {
                return "error";
            }
            String sql = "update RULE_SINGLE_EXPRESSION set EXPRESSION_VALUE = ?, OPERATOR= ? , IDENTITY= ? where id = ?";
            jdbcTemplate.update(sql, new Object[]{value, opt, exp, Long.parseLong(id)});
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteExpById(long id) {
        try {
        String sql = "delete from RULE_SINGLE_EXPRESSION where ID = ?";
        jdbcTemplate.update(sql, new Object[]{id});
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteGroupById(long id) {
        String sql = "delete from RULE_GROUP where ID = ?";
        jdbcTemplate.update(sql, new Object[]{id});
        return "success";
    }

    // 新增组和表达式的时候, 和subject共用一个seq
    public String insertGroup(String group, String pid) {
        // 如果存入的值没有这些, 说明存的是表达式而不是组, 需要直接报错
        if (group == null || group.trim().isEmpty()) {
            return "本组中只能添加组 !";
        }
        try {
            String sql = "insert into RULE_GROUP values (" + getNextVal() + ", ?, ?)";
            jdbcTemplate.update(sql, new Object[]{group, pid});
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    public String insertExp(String exp, String opt, String value, String pid) {
        // 如果存入的值没有这些, 说明存的是组而不是表达式, 需要直接报错
        if (exp == null || opt == null) {
            return "本组中只能添加表达式 !";
        }
        try {
            String sql = "insert into RULE_SINGLE_EXPRESSION values (" +getNextVal() + ", 'FlightMateInfo', ?, ?, ?, 1, ? )";
            jdbcTemplate.update(sql, new Object[]{value, exp, opt, pid});
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    public Long getNextVal() {
        return baseDao.getSeq("HIBERNATE_SEQUENCE");
    }
}




































