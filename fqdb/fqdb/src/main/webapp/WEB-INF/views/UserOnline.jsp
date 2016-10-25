<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.nlia.fqdb.entity.UserOnLine" %>
<%@page import="java.util.Map.Entry" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<link href="/omms/views/birt/css/reset.css" type="text/css" rel="stylesheet" />
		<script src="../js/jquery-1.7.1.min.js"></script>
		<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
		
		<style type="text/css">
			body{
				background-color: #fff;
				line-height: 200%;
				margin: 0 auto;
			}
			a{
				color: #ddd;
			}
			a:hover, a.selected{
				color: #16cbff;
			}
			.main{
				width: 920px;
				margin: 0 auto;
			}
			
			h1, h3{
				line-height: 150%;
			}
			.bor_bottom{
				border-bottom: #ccc 1px solid;
			}
		
		
		</style>
<script type="text/javascript">
	$(document).ready(function(){
		$("#tabs").hide();
		//setParam('2013-05-13');
	});

	function findAllUserOnLine(){
		$("#findAllUserOnLine").submit();
	}
	function delUser(userid){
		$("#delId").val(userid);
		$("#delectUser").submit();
	}
	function changeUser(userid){
		$("#cId").val(userid);
		$("#changeUser").submit();
	}
	function qeryUser(userid){
		$("#cId").val(userid);
		$("#qeryUser").submit();
	}
	$(function(){
	    //这里使用选择器选定你的FORM表单, 然后调用hide()方法, 即可;
	    $("#delectUser").hide();
	    $("#changeUser").hide();
	});
</script>
	</head>
	
	<body>
		<div class="main">
		 
		<div class="p8 t_c bor_bottom">
			<h1 class="mb10">登录状态修改</h1>
		</div>
		<div >
		<form action="delectUser" id="delectUser" >
			<input type="text" name="id" id="delId"  />
		</form>
		<form action="changeUser" id="changeUser">
			<input type="text" name="id" id="cId" />
		</form>
		<form action="findAllUserOnLine" id="findAllUserOnLine">
			用户名：<input type="text" name="qeryUser" id="qeryUser"  />
			登录状态：
			<select   name="qeryStatus" id="qeryStatus" >
				<option value=''>请选择</option>
				<option value='1'>已登录</option>
				<option value='0'>已退出</option>
			</select>
			<input type="button" id="qeryUsers" name="qeryUsers"  onclick="findAllUserOnLine()" value="查询"/>
		</form>
		<table border=1 width="120%px">
		<tr align="center">
			<td align="center">用户名：   </td>
			<td align="center">登陆时间：</td>
			<td align="center">登录状态：</td>
			<td align="center">退出时间：</td>
			<td align="center">那个系统：</td>
			<td align="center">操作人：    </td>
			<td align="center">操作：        </td>
		</tr>
		
			<%
			List<UserOnLine> UserOnLines = (List<UserOnLine>)request.getAttribute("onLineVo");
			if(UserOnLines != null && UserOnLines.size() > 0){
				for(int i = 0; i < UserOnLines.size(); i++){
					%>
					<tr >
						<td ><%= UserOnLines.get(i).getUsername()%></td>
						<td ><%=UserOnLines.get(i).getLogintime()%></td>
						<td ><%=UserOnLines.get(i).getStatus()==null||"1".equals(UserOnLines.get(i).getStatus())?"已登录":"已退出"%></td>
						<td ><%=UserOnLines.get(i).getLoginOutTime()%></td>
						<td ><%=UserOnLines.get(i).getCode()%></td>
						<td ><%=UserOnLines.get(i).getOpUser()%></td>
						<td >
						<% if(UserOnLines.get(i).getStatus()!=null&&"1".equals(UserOnLines.get(i).getStatus())){%>
						<input name="button" type="button" class="" onclick="changeUser('<%= UserOnLines.get(i).getId()%>');" value="更改状态" /> &nbsp;&nbsp;
						<input name="button" type="button" class="" onclick="delUser('<%= UserOnLines.get(i).getId()%>');" value="删除" /> 
						<%}%>
						</td>
					</tr>
				<% }
				
			}%>
			</table>
		</div>
		
	</body>
</html>