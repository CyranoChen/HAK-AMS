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
<style type="text/css">
	.bg1 td, .bg2 td, .bg1:hover td, .bg2:hover td{
		background: #f5f5f5;
		border-right:#bfcfda 1px solid;
		text-align:center;
	}
	.bg1 td{
		color: #000;
		
	}
	.bg2 td{
		color: #7a7a7a;
	}
</style>
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
		<form action="dwAirlineFlightNormal" method="post" id="form">
			<table width="100%" class="table_1">
				<tbody>
					<tr>
						<td class="t_r lableTd">开始日期</td>
						<td class="t_l"><input type="text" id="" name="startTime" readonly="readonly" value="${startTime}" /></td>
						<td class="t_r lableTd">结束日期</td>
						<td class="t_l"><input type="text" id="" name="endTime" readonly="readonly" value="${endTime}" /></td>
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
		<h2 align="center">航空公司航班正常统计表</h2>
	</div>
	<div>
		<table class="table_1" width="100%">
				<tr class="bg1">
					<td rowspan="2">机场</td>
					<td colspan="3">放行班次</td>
					<td rowspan="2">放行正常率</td>
					<td colspan="11">不正常原因</td>
					<td rowspan="2">同比</td>
					<td rowspan="2">环比</td>
				</tr>
				<tr class="bg2">
					<td>放行班次</td>
					<td>正常</td>
					<td>不正常</td>
					<td>天气</td>
					<td>公司</td>
					<td>流量</td>
					<td>军事活动</td>
					<td>空管</td>
					<td>机场</td>
					<td>联检</td>
					<td>油料</td>
					<td>离港系统</td>
					<td>旅客</td>
					<td>公共安全</td>
				</tr>
				<tr class="bg2">
					<td>1</td>
					<td>2</td>
					<td>3</td>
					<td>4</td>
					<td>5</td>
					<td>6</td>
					<td>7</td>
					<td>8</td>
					<td>9</td>
					<td>10</td>
					<td>11</td>
					<td>12</td>
					<td>13</td>
					<td>14</td>
					<td>15</td>
					<td>16</td>
					<td>17</td>
					<td>18</td>
				</tr>
				<c:forEach items="${dwAirlineFlightNormal}" var="dwAirlineFlightNormals">
					<tr>
						<td>${dwAirlineFlightNormal.airline}</td>
						<td>${dwAirlineFlightNormal.legClassesPlan}</td>
						<td>${dwAirlineFlightNormal.legClassesNormal}</td>
						<td>${dwAirlineFlightNormal.legClassesAbnormal}</td>
						<td>${dwAirlineFlightNormal.flightNormalRatio}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonWeather}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonCompany}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonFlow}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonMilitary}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonAirTraffic}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonAirport}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonInspection}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonOilPlants}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonDepart}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonPassenger}</td>
						<td>${dwAirlineFlightNormal.abnormalReasonSecurity}</td>
						<td>${dwAirlineFlightNormal.dayGrowthRatio}</td>
						<td>${dwAirlineFlightNormal.linkRelativeRatio}</td>
					</tr>
				</c:forEach>
		</table>
	</div>
</body>
</html>
