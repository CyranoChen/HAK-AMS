package com.wonders.frame.ams.controller.basic;

import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;
import com.wonders.frame.ams.model.basic.ChargeType;
import com.wonders.frame.ams.service.basic.ChargeTypeService;
import com.wonders.frame.core.model.vo.ReturnObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("jsp/basic")
public class ChargeTypeController {

    @Autowired
    private ChargeTypeService chargeTypeService;

    @RequestMapping("chargeType")
    public String chargeType() {
        return "/basic/chargeType";
    }

    // 根据字段要求查找收费类型的列表
    @RequestMapping("queryChargeType")
    @ResponseBody
    public List<ChargeType> queryChargeType(String chargeName, String chargeCode) {
        return chargeTypeService.query(chargeName, chargeCode);
    }

    // 修改并保存
    @RequestMapping("updateChargeType")
    @ResponseBody
    public Map<String, String> updateChargeType(HttpServletRequest req) {
        // 设置一个Map用于存放错误信息
        Map<String, String> errorMap = new HashMap<String, String>();

        // 当修改的字段为空或者超过了数据库的字符长度限制的时候, 返回一个error
        if (req.getParameter("name").trim().isEmpty() ) {
            errorMap.put("datafield", "name");
            errorMap.put("message", "收费类型不能为空!");
            return errorMap;
        }
        if (req.getParameter("chargeCode").trim().isEmpty()) {
            errorMap.put("datafield", "chargeCode");
            errorMap.put("message", "类型编码不能为空!");
            return errorMap;
        }
        if (req.getParameter("chargeCode").length() > 255) {
            errorMap.put("datafield", "name");
            errorMap.put("message", "类型编码输入过长!");
            return errorMap;
        }
        if (req.getParameter("name").length() > 255) {
            errorMap.put("datafield", "chargeCode");
            errorMap.put("message", "收费类型输入过长!");
            return errorMap;
        }
        if( ! chargeTypeService.update(req).getInfo().getSuccess()){
            errorMap.put("message", "保存失败!");
            return errorMap;
        }
        errorMap.put("message", "success");
        return errorMap;
    }

}
