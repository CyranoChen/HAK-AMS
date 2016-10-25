package com.wonders.frame.ams.dto.share;

import java.util.List;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.Permission;

public class WsafUser {
	private String ticket;
	
	private User user;
	
	private List<Permission> permission;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Permission> getPermission() {
		return permission;
	}

	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}

	
	
}
