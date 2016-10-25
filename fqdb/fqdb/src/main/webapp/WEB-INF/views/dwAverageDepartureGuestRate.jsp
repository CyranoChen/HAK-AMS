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
		<form action="dwAverageDepartureGuestRate" method="post" id="form">
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
		<h2 align="center">平均出港客座率走势图</h2>
	</div>
	<div>
		<table class="table_1" width="100%">
			<thead>
				<tr>
				    <td>年份</td>
					<td>1月</td>
					<td>2</td>
					<td>3月</td>
					<td>4月</td>
					<td>5月</td>
					<td>6月</td>
					<td>7月</td>
					<td>8月</td>
					<td>9月</td>
					<td>10月</td>
					<td>11月</td>
					<td>12月</td>

				</tr>
			</thead>
			<tbody>			   
				<c:forEach items="${dwAverageDepartureGuestRates}" var="dwAverageDepartureGuestRate" >
				
					<tr>
					<%-- <c:set var="no" value="${no + 1}" /> --%>
						<td>${dwAverageDepartureGuestRate.year_avg}</td>
				        <td>${dwAverageDepartureGuestRate.m01}</td>		
				        <td>${dwAverageDepartureGuestRate.m02}</td>	
				        <td>${dwAverageDepartureGuestRate.m03}</td>	
				        <td>${dwAverageDepartureGuestRate.m04}</td>	
				        <td>${dwAverageDepartureGuestRate.m05}</td>	
				        <td>${dwAverageDepartureGuestRate.m06}</td>	
				        <td>${dwAverageDepartureGuestRate.m07}</td>	
				        <td>${dwAverageDepartureGuestRate.m08}</td>	
				        <td>${dwAverageDepartureGuestRate.m09}</td>	
				        <td>${dwAverageDepartureGuestRate.m10}</td>	
				        <td>${dwAverageDepartureGuestRate.m11}</td>	
				        <td>${dwAverageDepartureGuestRate.m12}</td>			        
				      

					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</body>
</html>
