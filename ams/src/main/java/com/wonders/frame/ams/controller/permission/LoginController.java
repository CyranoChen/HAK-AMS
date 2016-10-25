package com.wonders.frame.ams.controller.permission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.ams.constants.Constant;
import com.wonders.frame.ams.dto.permission.UserDto;
import com.wonders.frame.ams.service.permission.UserManagerService;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.console.service.UserService;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.BasicCrudService;

@Controller
@RequestMapping("permission")
public class LoginController {
	
	
	
	
	@Autowired
	private UserManagerService userManagerService;
	
	
	@Autowired
	private UserService userService;

	@Autowired
	private BasicCrudService basicCrudService;
	
	private static ThreadLocal<HttpServletRequest> request_local= new ThreadLocal<HttpServletRequest>();
	
	@ResponseBody
	@RequestMapping("login.logout.do")
	public boolean logout(HttpServletRequest request){
		HttpSession session = request.getSession();
    	if(session != null){
    		session.removeAttribute(Constant.LOGIN_USER);
    		session.removeAttribute("role");
    	}
		
		return true;
	}
	
	
	
	@ResponseBody
	@RequestMapping("login.validityCheck.do")
	public boolean validityCheck(HttpServletRequest request,String requestUrl){
		
//		System.out.println(" -------" + requestUrl + "-------  AJAX 检查用户登录状态 ----- AT " + new Date());
		
		return true;
	}
	
	/**
	 * 用户验证
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login.check.do")
	public boolean check(HttpServletRequest request) {
		
		ReturnObj<LoginInfo> ro=userService.login(request.getParameter("loginName"), request.getParameter("password"), "ams");

		if(ro != null && ro.getInfo() != null && ro.getInfo().getSuccess()){
			LoginInfo loginInfo=ro.getData();


			UserDto user = new UserDto();
			user.setId(loginInfo.getUser().getId().toString());
			user.setName(loginInfo.getUser().getName());
			user.setLoginName(loginInfo.getUser().getLoginName());
			
			
			user.setTicket(loginInfo.getTicket());

			
			List<UserDto> datas=userManagerService.queryUser(user);
			
			user=datas.get(0);

			user.setPermission(loginInfo.getPermission());
			
			/**
			 * 把用户对象信息保存到session中
			 */
			request.getSession().setAttribute(Constant.LOGIN_USER, user);
			request.getSession().setAttribute("role", loginInfo.getPermission().get(0).getResourcePath());
			
			System.out.println(request.getSession().getAttribute(Constant.LOGIN_USER));


//			/**
//			 * 保存菜单资源信息到session中
//			 */
//
//			AppUtil.setUserMenuResource(resourceManagerService.getSystemMenu());
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 跳转
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("indexMeun.index.do")
	public String indexMenu(){
		return "../indexMenu.jsp";
	}
	
}
