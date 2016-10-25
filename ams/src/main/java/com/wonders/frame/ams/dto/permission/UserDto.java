package com.wonders.frame.ams.dto.permission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wonders.frame.console.model.vo.Permission;

public class UserDto {
	private String id;
	private String name;
	private String loginName;
	private String password;
	private String mobile1;
	private String mobile2;
	private String telephone;
	private String email;
	private String gender;
	private String organId;
	private String organName;
	private String roleId;
	private String roleName;
	private String imoId;
	
	private List<Permission> permission;

	private Set<Integer> permissionId = null;
	
	private String ticket;



	public boolean hasPermission (Integer id){

		if(permissionId == null){
			permissionId = new HashSet<Integer>();
			if(permission != null){
				for(Permission p : permission){
					permissionId.add(p.getResourceId());
				}
			}
		}

		return permissionId.contains(id);

	}

	
	public UserDto(){
		
	}
	

	
	public UserDto(String id) {
		super();
		this.id = id;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public List<Permission> getPermission() {
		return permission;
	}
	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}
	public String getImoId() {
		return imoId;
	}
	public void setImoId(String imoId) {
		this.imoId = imoId;
	}
	
}
