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
		<form action="dwDailyFreightVolume" method="post" id="form">
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
		<h2 align="center">日运输量统计表</h2>
	</div>
	<div>
		<table class="table_1" width="100%">
			<thead>
				<tr>
					<td>机场</td>
					<td>日期</td>
					<td>架次</td>
					<td>同期架次</td>
					<td>旅客</td>
					<td>同期旅客</td>
					<td>其中过站</td>
					<td>同期过站</td>
					<td>货邮</td>
					<td>同期货邮</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${dwDailyFreightVolumes}" var="dwDailyFreightVolume">
					<tr>
						<td>${dwDailyFreightVolume.airport}</td>
						<td><fmt:formatDate value="${dwDailyFreightVolume.statDate}"
								pattern="yyyy-MM-dd" /></td>
						<td>${dwDailyFreightVolume.samePeriodTimes}</td>
						<td>${dwDailyFreightVolume.flightTimes}</td>
						<td>${dwDailyFreightVolume.samePeriodPsgNum}</td>
						<td>${dwDailyFreightVolume.passengerNum}</td>
						<td>${dwDailyFreightVolume.viaPsg}</td>
						<td>${dwDailyFreightVolume.samePeriodViaPsg}</td>
						<td>${dwDailyFreightVolume.samePeriodCargoMailWeight}</td>
						<td>${dwDailyFreightVolume.cargoMailWeight}</td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>

