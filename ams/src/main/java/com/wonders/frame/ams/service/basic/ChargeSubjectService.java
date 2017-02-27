package com.wonders.frame.ams.service.basic;

import com.wonders.frame.ams.constants.Constant;
import com.wonders.frame.ams.dao.BaseDao;
import com.wonders.frame.ams.dao.basic.ChargeSubjectDao;


import com.wonders.frame.ams.dao.basic.ChargeSubjectJdbcDao;
import com.wonders.frame.ams.dao.basic.impl.ChargeTypeJdbcDao;
import com.wonders.frame.ams.dto.permission.UserDto;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.ChargeSubject;
import com.wonders.frame.ams.model.basic.ChargeType;
import com.wonders.frame.ams.model.SelectModel;
import com.wonders.frame.ams.model.basic.RuleExpModel;
import com.wonders.frame.ams.utils.DateUtil;
import com.wonders.frame.ams.utils.ResourceUtil;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.service.SingleCrudService;
import com.wonders.frame.core.utils.SingleParamsConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ChargeSubjectService {

    @Autowired
    private ChargeSubjectDao chargeSubjectDao;

    @Autowired
    private ChargeTypeJdbcDao chargeTypeJdbcDao;

    @Resource
    private ChargeSubjectJdbcDao chargeSubjectJdbcDao;

    @Resource
    private SingleCrudService<ILongIdRemoveFlagModel,Long> basicCrudService;

    public ChargeSubject querySubjectById(Long id) {
        return chargeSubjectDao.findById(id);
    }

    public List<ChargeSubject> query(String name, String chargeProperty, String chargeTypeId) {
        // 需要使用_in
        HashMap<String,String> filter = new HashMap<String,String>();
        // 项目名称支持模糊查询
        filter.put("name_l", name.toUpperCase());
        filter.put("chargeProperty_l", chargeProperty.toUpperCase());
        filter.put("chargeTypeId_in", chargeTypeId.toUpperCase());
        filter.put("removeFlag", "1");
        LinkedHashMap<String,String> sorter = new LinkedHashMap<String,String>();
        // 按照收费类型的id进行递增排序
        sorter.put("chargeTypeId", "asc");
        sorter.put("name", "asc");
        List<ChargeSubject> subjects = chargeSubjectDao.findAll(filter, sorter);
        for (ChargeSubject cs : subjects) {
            if (cs.getChargeProperty().equals("0")) {
                cs.setChargeProperty("航空性业务收费");
            }
            if (cs.getChargeProperty().equals("1")) {
                cs.setChargeProperty("非航空性业务重要收费");
            }
            if (cs.getChargeProperty().equals("2")) {
                cs.setChargeProperty("非航空性业务其他收费");
            }
        }
        // 根据收费类型Id绑定收费类型名称
        List<ChargeType> chargeTypes = chargeTypeJdbcDao.queryChargeTypeIdAndName();
        for (ChargeType ct : chargeTypes) {
            for (ChargeSubject cs : subjects) {
                if (ct.getId() == (long)cs.getChargeTypeId()) {
                    cs.setChargeType(ct.getName());
                }
            }
        }

        return subjects;
    }

    // 收费类型下拉列表查询
    public List<SelectModel> queryDropList() {
        List<ChargeType> chargeTypes = chargeTypeJdbcDao.queryChargeTypeIdAndName();
        List<SelectModel> models = new ArrayList<SelectModel>();
        for (ChargeType type : chargeTypes) {
            SelectModel model = new SelectModel();
            model.setKey(type.getName());
            model.setValue(type.getId() + "");
            models.add(model);
        }
        return models;
    }

    // 保存新增
    public ReturnObj<ILongIdRemoveFlagModel> save(HttpServletRequest req){
        SingleModelParams smp = SingleParamsConvertUtil.getModelParams(ChargeSubject.class, req);
        String loginName = ((UserDto)req.getSession().getAttribute(Constant.LOGIN_USER)).getLoginName();
        String id = req.getParameter("id");
        System.out.println("id: "+id);
        if (id == null || id.trim().isEmpty()) {
            smp.addData("createUser", loginName);
            smp.addData("createTime", DateUtil.getDateString());
        } else {
            smp.addData("modifyUser", loginName);
            smp.addData("modifyTime", DateUtil.getDateString());
        }
        smp.addData("removeFlag", "1");
        return basicCrudService.saveOrUpdate(smp);
    }

    public void saveFirstRule(long id) {
        // 先查询到对应的subject, 对ruleid进行设置后, 再保存一次
        chargeSubjectJdbcDao.updateSubjectRuleId(id);
        // 然后给rule表添加默认的记录, id为subject的外键, connect默认
        chargeSubjectJdbcDao.insertFirstRule(id);
    }

    public ReturnObj<Integer> deleteChargeSubject(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        long s_id = Long.parseLong(id);
        // 在删除一条chargeSubject的时候, 需要将对应的rulegroup根节点进行删除将这个rule中所有的子节点和子表达式全部删除
        List<RuleExpModel> rules = chargeSubjectJdbcDao.queryRulesById(s_id);
        for (RuleExpModel rule : rules) {
            String r_id = rule.getId();
            Long o_id = Long.parseLong(r_id.substring(1));
            if (r_id.charAt(0) == 'E') {
                chargeSubjectJdbcDao.deleteExpById(o_id);
            } else {
                chargeSubjectJdbcDao.deleteGroupById(o_id);
            }
        }
        return basicCrudService.removeById(ChargeSubject.class, s_id);
    }

    public ChargeSubject findById(long id) {
        return chargeSubjectDao.findById(id);
    }

    public List<SelectModel> queryFmiDropList() {
        // false将文件加载入缓存, 下次再使用的时候, 先从缓存中找, 如果缓存中没有的话, 将从文件中再次读取
        LinkedHashMap<String,String> pros = new LinkedHashMap<String,String>((Map) ResourceUtil.getProperties("flightMate/fightMateInfo", false));
        Set<Map.Entry<String, String>> entries = pros.entrySet();
        List<SelectModel> models = new ArrayList<SelectModel>();
        for (Map.Entry<String, String> entry : entries) {
            SelectModel model = new SelectModel();
            model.setKey(entry.getKey() + "(" + entry.getValue() + ")");
            model.setValue(entry.getKey());
            models.add(model);
        }
        // 将model按照字典顺序进行排序
        Collections.sort(models, new Comparator<SelectModel>() {
            @Override
            public int compare(SelectModel o1, SelectModel o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return models;
    }

    public List<RuleExpModel> queryRuleExp(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            System.out.println(id);
            return new ArrayList<RuleExpModel>();
        }
        ChargeSubject cs = findById(Long.parseLong(id));
        if (cs == null) {
            return new ArrayList<RuleExpModel>();
        }
        if (cs.getChargeRuleId() == null) {
            return new ArrayList<RuleExpModel>();
        }
        LinkedHashMap<String,String> pros = new LinkedHashMap<String,String>((Map) ResourceUtil.getProperties("flightMate/fightMateInfo", false));
        Set<Map.Entry<String, String>> entries = pros.entrySet();
        List<RuleExpModel> ruleExpModels = chargeSubjectJdbcDao.queryRulesById(cs.getChargeRuleId());
        for (RuleExpModel rem : ruleExpModels) {
            for (Map.Entry<String, String> entry : entries) {
                if (rem == null || rem.getKey() == null || rem.getKey().trim().isEmpty()) {
                    continue;
                }
                if (rem.getKey().equals(entry.getKey())) {
                    rem.setKey(entry.getKey() + "(" + entry.getValue() + ")");
                }
            }
        }
        return ruleExpModels;
    }


    public RuleExpModel queryGroup(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return chargeSubjectJdbcDao.queryGroupById(Long.parseLong(id));
    }

    public RuleExpModel queryExp(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        return chargeSubjectJdbcDao.queryExpById(Long.parseLong(id));
    }

    public String deleteRuleById(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            return "error";
        }
        long ruleId = Long.parseLong(id.substring(1));
        if (id.charAt(0) == 'E') {
            return  chargeSubjectJdbcDao.deleteExpById(ruleId);
        } else {
            String pid = chargeSubjectJdbcDao.queryGroupById(Long.parseLong(id.substring(1))).getPid();
            // 如果这条记录的pid只是一个G的话, 那这条记录一定是根节点, 根节点不可以删除
            if ("G".equals(pid)) {
                return "根节点不可以删除 !";
            }
            if (chargeSubjectJdbcDao.queryRulesById(ruleId).size() != 1) {
                return "请先删除该组子元素 !";
            }
           return chargeSubjectJdbcDao.deleteGroupById(ruleId);
        }
    }

    // 检查组下面的是表达式, 组还是没有任何东西
    public String checkGroup(Long id) {
        List<RuleExpModel> list = chargeSubjectJdbcDao.queryRulesById(id);
        if (list.size() == 1) {
            return "N";
        }
        for (RuleExpModel model : list) {
            // 将list中的组自己的本身去除掉, 当对象中的key存在or或者and的时候, 说明下面一定是组
            if (!model.getId().equals("G" + id) && (model.getKey().equals("or") || model.getKey().equals("and")) ) {
                return "G";
            }
        }
        return "E";
    }

    public String updateOrSave(HttpServletRequest req) {
        String id = req.getParameter("id");
        String pid = req.getParameter("pid").substring(1);
        String group = req.getParameter("group");
        String exp = req.getParameter("exp");
        String opt = req.getParameter("opt");
        String value = req.getParameter("value");
        String select = req.getParameter("select");
        // 记录所属组的id
        String gid = req.getParameter("gid");

        // 如果没有id, 那就是新增
        if (id == null || id.trim().isEmpty()) {
            String check = checkGroup(Long.parseLong(pid));
            // 如果是组的话
            if ("G".equals(select)) {
                if ("E".equals(check)) {
                    return "该组只能添加表达式 !";
                }
               return chargeSubjectJdbcDao.insertGroup(group, pid);
            } else {
                if ("G".equals(check)) {
                    return "该组只能添加组 !";
                }
               return chargeSubjectJdbcDao.insertExp(exp, opt, value, pid);
            }
        } else {
            id = id.substring(1);
            if ("G".equals(select)) {
                // 当组下面都是表达式的时候, 就不能存入组
                gid = gid.substring(1);
                if (gid != null && !gid.trim().isEmpty() && "E".equals(checkGroup(Long.parseLong(gid)))) {
                    return "不可以修改为组 !";
                }
                return chargeSubjectJdbcDao.updateGroupById(id, group);
            } else {
                if ("G".equals(gid)) {
                    return "根节点不可以为表达式 !";
                }
                if ("G".equals(checkGroup(Long.parseLong(gid.substring(1))))) {
                    return "不可以修改为表达式 !";
                }
                return chargeSubjectJdbcDao.updateExpById(id, exp, opt, value);
            }
        }
    }

}

























