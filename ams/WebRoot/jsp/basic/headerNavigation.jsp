<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="clearfix" id="headerNavigation">
	<ul>
		<li><a href="/ams/jsp/basic/aircraftAirlineAlteration">飞机所属航空公司维护</a></li>
		<li><a href="/ams/jsp/basic/baseAirline">航空公司</a></li>
		<li><a href="/ams/jsp/basic/baseDestination">目的地</a></li>
		<li><a href="/ams/jsp/basic/baseStand">机位</a></li>
		<li><a href="/ams/jsp/basic/baseAircraft">机号</a></li>
		<li id="discountManagerBar"><a href="/ams/basic/discountManager/index">折扣</a></li>
		<li><a href="/ams/jsp/basic/chargeType">收费类型</a></li>
		<li><a href="/ams/jsp/basic/chargeSubject">收费项目</a></li>
	</ul>
</nav>

<script>

	var h = window.document.location.href;
	h = h.replace(Global.root + "/jsp/basic","");

	var selecta = $("#headerNavigation a[href$='" + h +"']");
	var x = selecta.parent("li");
	var y = x.parent("ul").parent("li");
	if(y.length > 0){
		x.css("border-left","#3099dd 3px solid");
	}else{
		x.addClass("selected");
	}
	y.addClass("selected");

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
  padding: 0 30px;

  border-top: #f1f1f1 3px solid;
  border-right: #d0d0d0 1px solid;
}
</style>
