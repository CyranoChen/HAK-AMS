<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.nlia.fqdb.entity.FlightServiceInfo" %>
<!DOCTYPE html>
<html >
<head>
<meta charset="UTF-8" />
<title>打印服务确认单</title>
	<link rel="stylesheet" href="/fqdb/css/reset.css" />
	<style>
		body{
			background-color: #fff;
			line-height: 150%;
			margin: 0 auto;
		}
		.main{
			width: 920px;
			margin: 0 auto;
		}
		table.print{
			width: 100%;
			border-left: #000 1px solid;
			border-top: #000 1px solid;
			font-size: 12px;
		}
		table.print td{
			padding: 5px;
			line-height: 24px;
			border-right: #000 1px solid;
			border-bottom: #000 1px solid;
		}
		h1, h3{
			line-height: 150%;
		}
		h3{
			font-weight: normal;
		}
		span{
			color: #cc1d01;
		}
		table .t_r{
			width: 15%;
			background-color: #eee;
			color: #333;
		}
		.bg1{
			background-color: #e1e1e1;
		}
		.bg2{
			background-color: #eee;
		}

	</style>
<%
FlightServiceInfo flightServiceInfo = (FlightServiceInfo)request.getAttribute("flightServiceInfo");
System.out.println(flightServiceInfo.getId());
long id = flightServiceInfo.getId();
%>
</head>
<body>
	<div class="main">
		<div class="p8 t_c mb10">
			<h1 class="mb10"><%=flightServiceInfo.getFlightNumber() %>&nbsp;&nbsp;服务确认单</h1>
			<div class="t_r"><a href="javascript:window.print();">打印</a></div>
			<h5 class="t_r"><%=FlightServiceInfo.getFormatDate(null) %></h5>
		</div>
		<table class="print">
			<tr>
				<td colspan="4" class="bg1">航班基本信息</td>
			</tr>
			<tr>
				<td class="t_r">航班号</td>
				<td><span><%=flightServiceInfo.getFlightNumber() %></span></td>
				<td class="t_r">航班日期</td>
				<td><%=flightServiceInfo.getScheduleDate() %></td>
			</tr>
			<tr>
				<td class="t_r">航空公司</td>
				<td><%=flightServiceInfo.getFlightBase().getAirlineIATACode() %></td>
				<td class="t_r">机号</td>
				<td><%=flightServiceInfo.getFlightBase().getFlightData().getRegisteration() == null? "" : flightServiceInfo.getFlightBase().getFlightData().getRegisteration()%></td>
			</tr>
			<tr>
				<td class="t_r">飞行性质</td>
				<td><%=flightServiceInfo.getFlightBase().getFlightData().getFlightServiceType() %></td>
				<td class="t_r">备降候机楼人数</td>
				<td><%= flightServiceInfo.getTerminalPassengerNumber() == null ? "" :flightServiceInfo.getTerminalPassengerNumber() %></td>
			</tr>
		</table>
		<table class="print" style="border-top:none;">
			<tr>
				<td class="t_r">是否过夜</td>
				<td><%=flightServiceInfo.getFlightBase().getFlightData().getIsOverNightFlight2s() %></td>
				<td class="t_r">飞机服务</td>
				<td>
				<%= flightServiceInfo.getAircraftService().getValue() %>
				
				</td>
				<td class="t_r">机务检查及放行</td>
				<td>
				<%if(flightServiceInfo.getRoutinePermit().equals(FlightServiceInfo.ROUTINE_PERMIT.P_A_FLIGHTCHECK)) {%>
				航前+航后
				<%}else {%>
				短停
				<%}%>
				
				</td>
			</tr>
			<tr>
				<td colspan="6" class="bg1">资源使用情况</td>
			</tr>
			<tr>
				<td class="t_r">客桥靠桥时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getPassengerBridgeServicedStartTime())%></td>
				<td class="t_r">客梯车靠接时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getPassengerCarUsedStartTime())%></td>
				<td class="t_r">电源车靠接时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getElectricSourceTruckUsedStartTime())%></td>
			</tr>
			<tr>
				<td class="t_r">客桥离桥时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getPassengerBridgeServicedEndTime())%></td>
				<td class="t_r">客梯车撤离时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getPassengerCarUsedEndTime())%></td>
				<td class="t_r">电源车撤离时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getElectricSourceTruckUsedEndTime())%></td>
			</tr>
			<tr>
				<td class="t_r">升降平台车靠接时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getLiftingPlatformCarUsedStartTime())%></td>
				<td class="t_r">除冰车靠接时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getDeicingVehicleUsedStartTime())%></td>
				<td class="t_r">空调车靠接时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getAirconditioningVehicleUsedStartTime())%></td>
			</tr>
			<tr>
				<td class="t_r">升降平台车撤离时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getLiftingPlatformCarUsedEndTime())%></td>
				<td class="t_r">除冰车撤离时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getDeicingVehicleUsedEndTime())%></td>
				<td class="t_r">空调车撤离时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getAirconditioningVehicleUsedEndTime())%></td>
			</tr>
			<tr>
				<td class="t_r">桥载空调开始时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getBridgeAirconditionStartTime())%></td>
				<td class="t_r">桥载电源开始时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getBridge400PowerStartTime())%></td>
				<td class="t_r"></td>
				<td></td>
			</tr>
			<tr>
				<td class="t_r">桥载空调结束时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getBridgeAirconditionEndTime())%></td>
				<td class="t_r">桥载电源结束时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getBridge400PowerEndTime())%></td>
				<td class="t_r"></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="6" class="bg1">车辆使用信息</td>
			</tr>
			<tr>
				<td class="t_r">机组摆渡车（次）</td>
				<td><%= flightServiceInfo.getAirdromeCountUsedByCrew() == null ? "" : flightServiceInfo.getAirdromeCountUsedByCrew()%></td>
				<td class="t_r">旅客摆渡车（次）</td>
				<td><%= flightServiceInfo.getAirdromeCountUsedByPassenger() == null ? "" : flightServiceInfo.getAirdromeCountUsedByPassenger()%></td>
				<td class="t_r">残疾人专用车（次）</td>
				<td><%= flightServiceInfo.getSpecialVehiclesCountForDisabled() == null ? "" : flightServiceInfo.getSpecialVehiclesCountForDisabled()%></td>
			</tr>
			<tr>
				<td class="t_r">引导车（次）</td>
				<td><%= flightServiceInfo.getLeadCarUsedCount() == null ? "" : flightServiceInfo.getLeadCarUsedCount()%></td>
				<td class="t_r">牵引车（次）</td>
				<td><%= flightServiceInfo.getTractorsUsedCount() == null ? "" : flightServiceInfo.getTractorsUsedCount()%></td>
				<td class="t_r">例行检查（次）</td>
				<td><%= flightServiceInfo.getRoutineCheckCount() == null ? "" : flightServiceInfo.getRoutineCheckCount()%></td>
			</tr>
			<tr>
				<td colspan="6" class="bg1">备注信息</td>
			</tr>
			<tr>
				<td colspan="6"><%= flightServiceInfo.getRemk() == null ? "" : flightServiceInfo.getRemk()%><br><br></td>
			</tr>
			<tr>
				<td colspan="6"><div>确认签名：</div><br /><br /><br /><br /><br /></td>
			</tr>
		</table>
		<div class="t_r"><a href="javascript:window.print();">打印</a></div>
	</div>
</body>