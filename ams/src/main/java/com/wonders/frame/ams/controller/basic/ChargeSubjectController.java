package com.wonders.frame.ams.controller.basic;

import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.ChargeSubject;
import com.wonders.frame.ams.model.SelectModel;
import com.wonders.frame.ams.model.basic.RuleExpModel;
import com.wonders.frame.ams.service.basic.ChargeSubjectService;
import com.wonders.frame.core.model.vo.ReturnObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("jsp/basic")
public class ChargeSubjectController {

    @Autowired
    private ChargeSubjectService chargeSubjectService;

    // 页面跳转
    @RequestMapping("chargeSubject")
    public String chargeSubject() {
        return "/basic/chargeSubject";
    }

    @RequestMapping("queryChargeSubject")
    @ResponseBody
    public List<ChargeSubject> query(HttpServletRequest request) {

        String name = request.getParameter("name");
        String chargeProperty = request.getParameter("chargeProperty");
        String chargeTypeId = request.getParameter("chargeTypeId");
        return chargeSubjectService.query(name, chargeProperty, chargeTypeId);
    }

    @RequestMapping("queryDropList")
    @ResponseBody
    public List<SelectModel> queryDropList() {
        return chargeSubjectService.queryDropList();
    }

    @RequestMapping("saveWindowValue")
    @ResponseBody
    public String saveorupdate(HttpServletRequest request, ChargeSubject chargeSubject) {
        if (chargeSubject.getName() == null || chargeSubject.getName().trim().isEmpty()) {

            return "项目名不能为空 !";
        }
        if (chargeSubject.getFormula() == null || chargeSubject.getFormula().trim().isEmpty()) {
            return "公式不能为空 !";
        }

        ReturnObj<ILongIdRemoveFlagModel> status = chargeSubjectService.save(request);
        if (!status.getInfo().getSuccess()) {
            return "保存失败 !";
        }
        Long id = status.getData().getId();
        // 只有在新增一个收费项目的时候才进行ruleId的插入
        if (chargeSubjectService.querySubjectById(id).getChargeRuleId() == null) {
            chargeSubjectService.saveFirstRule(status.getData().getId());
        }
        return "success";
    }

    // 删除一条记录
    @RequestMapping("deleteRowById")
    @ResponseBody
    public String deleteRowById(HttpServletRequest request) {
        if (chargeSubjectService.deleteChargeSubject(request).getInfo().getSuccess()) {
            // 因为chargesubject的记录删除是removeflag的为0
            //chargeSubjectService.deleteRuleById(request);
            return "success";
        }
        return "error";

    }

    // 根据id查找到对应的ChargeSubject
    @RequestMapping("toEditWindow")
    @ResponseBody
    public ChargeSubject toEditWindow(HttpServletRequest request) {
        String theId = request.getParameter("id");
        if (theId == null || theId.trim().isEmpty()) {
            return null;
        }
        long id = Long.parseLong(theId);
        return chargeSubjectService.findById(id);
    }

    @RequestMapping("queryFmiDropList")
    @ResponseBody
    public List<SelectModel> queryFmiDropList() {
        return chargeSubjectService.queryFmiDropList();
    }

    @RequestMapping("queryRuleExp")
    @ResponseBody
    public List<RuleExpModel> queryRuleExp(HttpServletRequest req) {
        return chargeSubjectService.queryRuleExp(req);
    }

    @RequestMapping("queryGroupByGid")
    @ResponseBody
    public RuleExpModel queryGroupByGid(HttpServletRequest req) {
        return  chargeSubjectService.queryGroup(req);
    }

    @RequestMapping("queryExpByEid")
    @ResponseBody
    public RuleExpModel queryExpByEid(HttpServletRequest req) {
        return  chargeSubjectService.queryExp(req);
    }

    @RequestMapping("deleteRuleById")
    @ResponseBody
    public String deleteRuleById(HttpServletRequest req) {
        return chargeSubjectService.deleteRuleById(req);
    }

    @RequestMapping("checkGroup")
    @ResponseBody
    public String checkGroup(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            return "error";
        }
        return chargeSubjectService.checkGroup(Long.parseLong(id));
    }

    @RequestMapping("updateOrSave")
    @ResponseBody
    public String updateOrSave(HttpServletRequest req) {
        return chargeSubjectService.updateOrSave(req);
    }
}












