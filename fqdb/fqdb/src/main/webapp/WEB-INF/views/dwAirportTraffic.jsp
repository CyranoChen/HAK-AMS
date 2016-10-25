<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
<title>Untitled Document</title>
<link rel="stylesheet" href="${ctx}/css/formalize.css">
<link rel="stylesheet" href="${ctx}/css/reset.css">
<link rel="stylesheet" href="${ctx}/css/page.css">
<link href="${ctx}/css/imgs.css" rel="stylesheet" type="text/css">
<link type="text/css"
	href="${ctx}/datepicker/css/flick/jquery-ui-1.8.18.custom.css"
	rel="stylesheet" />

<script src="${ctx}/js/html5.js"></script>
<script src="${ctx}/js/jquery-1.7.1.min.js"></script>
<script src="${ctx}/datepicker/js/jquery-ui-1.10.3.js"></script>
<script src="${ctx}/datepicker/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript"
	src="${ctx}/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var $tbInfo = $(".search_1 input:text");
		$tbInfo.each(function() {
			$(this).focus(function() {
				$(this).attr("placeholder", "");
			});
		});

		var $tblAlterRow = $(".table_1 tbody tr:odd");
		if ($tblAlterRow.length > 0)
			$tblAlterRow.css("background", "#f7f9fc");
	});
	$(function() {
		$("input[name=startTime]").datepicker({
			inline : true,
			changeYear : true,
			changeMonth : true,
			showOtherMonths : true,
			selectOtherMonths : true
		});
		$("input[name=endTime]").datepicker({
			inline : true,
			changeYear : true,
			changeMonth : true,
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});

</script>
</head>

<body>
	<!--search-->
	<div class="search_1">
		<form action="dwAirportTraffic" method="post" id="form">
			<table width="100%" class="table_1">
				<tbody>
					<tr>
						<td class="t_r lableTd">开始日期</td>
						<td class="t_l"><input type="text" name="startTime" readonly="readonly" value="${startTime}" /></td>
						<td class="t_r lableTd">结束日期</td>
						<td class="t_l"><input type="text" name="endTime" readonly="readonly" value="${endTime}" /></td>
						<td class="t_r">
							<input type="submit" value="查  询"/>&nbsp; 
							<input type="reset" value="重  置" />&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<!--search end-->
	<div>
		<h2 align="center">机场业务量分月统计表</h2>
	</div>
	<div>
		<table class="table_1" width="100%">
			<thead>
				<tr>
					<td rowspan="2">项目</td>
					<td rowspan="2">运输起降架次（次）</td>
					<td rowspan="2">旅客（人）</td>
					<td rowspan="2">其中过站旅客（人）</td>
					<td colspan="2">货邮（顿）</td>
					<td colspan="5">其中：外航</td>

				</tr>
                               	<tr>
					<td >货物</td>
					<td >邮件</td>
					<td >架次</td>
					<td >旅客</td>
					<td >过站旅客</td>
					<td >邮件（吨）</td>
                    <td >货物（吨）</td>
				</tr>
			</thead>
			<tbody>			   
				<c:forEach items="${dwAirportTraffics}" var="dwAirportTraffic">
					<tr>
						<td>${dwAirportTraffic.month}</td>
						<td>${dwAirportTraffic.transportTimes}</td>
						<td>${dwAirportTraffic.passengerNum}</td>
						<td>${dwAirportTraffic.viaPassengerNum}</td>
						<td>${dwAirportTraffic.cargoWeight}</td>
						<td>${dwAirportTraffic.mailWeight}</td>
						<td>${dwAirportTraffic.foreignTransportTimes}</td>
						<td>${dwAirportTraffic.foreignPassengerNum}</td>
						<td>${dwAirportTraffic.foreignViaPassengerNum}</td>
						<td>${dwAirportTraffic.foreignCargoWeight}</td>
						<td>${dwAirportTraffic.foreignMailWeight}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</body>
</html>
