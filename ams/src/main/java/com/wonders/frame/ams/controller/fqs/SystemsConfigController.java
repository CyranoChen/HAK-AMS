package com.wonders.frame.ams.controller.fqs;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wonders.frame.ams.utils.Chk;

/**
 * Created by 5325 on 2016/02/18.
 */

@Controller
@RequestMapping("fqs/systemsConfigure")
public class SystemsConfigController {
	@RequestMapping("index")
    public String index(HttpServletRequest request,String role) throws IOException {

        if( ! Chk.integerCheck(role,"+") || Integer.parseInt(role) > 5){
            role = "1";
        }
        return "/fqs/systemsConfigure";
    }
}
