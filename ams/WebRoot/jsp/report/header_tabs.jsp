<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="clearfix" id="header_tabs">
	<ul>
		<li><a href="../report/liquidationIncomeCalculation" role="1">清算表</a>
		<li><a href="../report/demand" role="1">南航需求表</a></li>
		<li><a href="../report/stopPark" role="1">停场表</a></li>
		<li><a href="../report/chargeCalculation" role="1">收费计算表</a></li>
		<li><a href="../report/airlineEarnings" role="1">航空公司收益表</a></li>
		<li><a href="../report/seatsEarnings" role="1">机位收益表</a></li>
		<li><a href="../report/flightDynamics" role="1,2,3,4,5">航班动态统计</a></li>
		<li><a href="../report/signingResources" role="1,2,3,4,5">资源签单统计</a></li>
		<li><a href="../report/manifest" role="1,2,3,4,5">舱单统计</a></li>
		<li><a href="../report/bridgeUsed" role="1,2,3,4,5">客桥统计</a></li>
		<li><a href="../report/bridgeEquipment" role="1,2,3,4,5">设备统计</a></li>
		<li><a href="../report/unMateFlight" role="1">未配对航班查询</a></li>
		<li><a href="../report/newRegistration" role="1">新增未配折扣的国内机号</a></li>
	</ul>
</nav>

<script>
	var role = "<%=request.getSession().getAttribute("role")%>";
	var checkRole = function (roleString){
	      return roleString.split(",").indexOf(role) >= 0;
	    };
	var h = window.document.location.href;
	h = h.replace(Global.root,"");
	var selecta = $("#header_tabs a[href$='" + h +"']");
	var x = selecta.parent("li");
	var y = x.parent("ul").parent("li");
	if(y.length > 0){
		x.css("border-left","#3099dd 3px solid");
	}else{
		x.addClass("selected");
	}
	y.addClass("selected");
	var allHref = $("li a");
	for(var i=0;i<allHref.length;i++){
		if(!checkRole(allHref[i].getAttribute("role"))){
			$(allHref[i]).parent().remove();
		}
	}
</script>

<style>
nav {
  height: 43px;
  line-height: 40px;
  font-size: 14px;
  background-color: #f1f1f1;
  box-shadow: 1px 1px 2px rgba(0,0,0,.2);
}
nav li:hover, nav li.selected {
  border-top: #3099dd 3px solid;
  background: #fff;
}

nav li {
  float: left;
  padding: 0 7px;

  border-top: #f1f1f1 3px solid;
  border-right: #d0d0d0 1px solid;
}
</style>
