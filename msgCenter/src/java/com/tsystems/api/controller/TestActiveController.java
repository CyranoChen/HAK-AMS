package com.tsystems.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * @ClassName: ActiveMQController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author lushuaifeng
 * @date 2013-7-8 下午03:43:17 
 *  
 */
@Controller
@RequestMapping("/testActive")
public class TestActiveController {	
	@RequestMapping(value = "/MC", method = RequestMethod.GET)
	protected @ResponseBody
	String testMCActive() {
		return "{\"success\":true}";
	}

}
