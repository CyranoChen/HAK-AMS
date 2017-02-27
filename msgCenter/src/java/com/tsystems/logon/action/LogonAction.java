package com.tsystems.logon.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tsystems.ConfigProperties;

@ParentPackage("struts-default")
@Namespace(value="/logon")
@Controller("logonAction")
@Scope("prototype")
public class LogonAction extends ActionSupport {
	public ActionContext actionContext = ActionContext.getContext();
	public HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
	public HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	

	@Action(value="login",results={@Result(name="success",location="/jsp/logon/login.jsp")})
	public String login(){
		request.getSession().invalidate();
		return "success";
	}
	
	@Action(value="check",results={@Result(name="failure",location="login.action",type="redirect"),
			@Result(name="success",location="../imfMsg/showIndex.action",type="redirect")})
	public String check(){
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		if(userName.equals(ConfigProperties.getProperty(ConfigProperties.Login_User))
				&& password.equals(ConfigProperties.getProperty(ConfigProperties.Login_Password))){
			request.getSession().setAttribute(ConfigProperties.getProperty(ConfigProperties.Login_Session), userName);
			return "success";
		}else{
			request.getSession().invalidate();
			return "failure";
		}
	}
	
	

}

