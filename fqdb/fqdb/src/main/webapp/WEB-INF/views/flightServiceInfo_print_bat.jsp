<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.nlia.fqdb.entity.FlightServiceInfo" %>
<%@ page import="com.nlia.fqdb.vo.FlightServiceInfoHistoryVo" %>
<%@ page import="com.nlia.fqdb.entity.FlightServiceInfoConfig" %>
<%@ page import="java.util.List;" %>
<!DOCTYPE html>
<html >
<head>
<meta charset="UTF-8" />
<title>打印服务确认单</title>
	<link rel="stylesheet" href="css/reset.css" />
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
			table-layout:fixed;
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
		P { page-break-after: always }
	</style>

</head>
<script type="text/javascript">
function printTure()   //打印函数
{
    document.all('qingkongyema').click();//同上
    document.all("dayinDiv").style.display="none";//同上
    window.print();
    document.all("dayinDiv").style.display="";
}
var hkey_key;
var hkey_root="HKEY_CURRENT_USER";
var hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
//设置网页打印的页眉页脚为空
function pagesetup_null()
{   
    var RegWsh = new ActiveXObject("WScript.Shell");
    hkey_key="header";
    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
    hkey_key="footer";
    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
}
//设置网页打印的页眉页脚为默认值
function pagesetup_default()
{
  try{
    var RegWsh = new ActiveXObject("WScript.Shell");
    hkey_key="header";
    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P");
    hkey_key="footer";
    RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&u&b&d");
  }catch(e){}
}
</SCRIPT>
<body>
<%
int FlightServiceInfoCount=(Integer)request.getAttribute("flightServiceInfoCount");
for (int i=0;i<FlightServiceInfoCount;i++){
    String currentSubairline=(String)request.getAttribute("currentSubairline" +i);
    if("null".equals(currentSubairline)||currentSubairline==null){
        currentSubairline="";
    }
	FlightServiceInfo flightServiceInfo = (FlightServiceInfo)request.getAttribute("flightServiceInfo"+i);
	//if("1".equals(flightServiceInfo.getFlightBase().getFlightDirection())) continue;
	if("1".equals(flightServiceInfo.getFlightBase().getFlightDirection())) {
	    if(flightServiceInfo.getFlightBase().getLinkFlight()!=null && flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
	    	flightServiceInfo=flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo();
	    }else{
	        continue;
	    }
	}
	List <FlightServiceInfoHistoryVo> vos = (List<FlightServiceInfoHistoryVo>)request.getAttribute("service_usage_detail"+i);
	System.out.println(flightServiceInfo.getId());
	long id = flightServiceInfo.getId();
	FlightServiceInfo flightServiceInfoLink = new FlightServiceInfo();
	//List <FlightServiceInfoHistoryVo> vos = (List<FlightServiceInfoHistoryVo>)request.getAttribute("service_usage_detail"+i);
	for (int t=0;t<FlightServiceInfoCount;t++){
		if (i==t) continue;
		FlightServiceInfo flightServiceInfoCompare = (FlightServiceInfo)request.getAttribute("flightServiceInfo"+t);
		if(flightServiceInfo.getFlightBase().getId().equals(flightServiceInfoCompare.getFlightBase().getLinkFlight().getId())){
			flightServiceInfoLink=flightServiceInfoCompare;
			List <FlightServiceInfoHistoryVo> vosCompare= (List<FlightServiceInfoHistoryVo>)request.getAttribute("service_usage_detail"+t);
			vos.addAll(vosCompare);
		}
	}
	//飞机服务
	String fjfw="";
	if(flightServiceInfo.getAircraftService() != null ){
	    if(flightServiceInfo.getAircraftService().equals(FlightServiceInfo.AIRCRAFT_SERVICE.PRE)){
	        fjfw="航前";
	    }else if(flightServiceInfo.getAircraftService().equals(FlightServiceInfo.AIRCRAFT_SERVICE.AFT)){
	        fjfw="航后";
	    }else if(flightServiceInfo.getAircraftService().equals(FlightServiceInfo.AIRCRAFT_SERVICE.TR)){
	        fjfw="短停";
	    }
	}
	//机务检查放行
	String jwjcfx="";
	String bjhbsyhjlrs="";
	if(flightServiceInfo.getFlightBase().getLinkFlight()!=null && flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
	    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getTerminalPassengerNumber() != null){
	        bjhbsyhjlrs=String.valueOf(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getTerminalPassengerNumber());
	    }
	    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getRoutinePermit()!=null){
		    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getRoutinePermit().equals(FlightServiceInfo.ROUTINE_PERMIT.P_A_FLIGHTCHECK)){
		    	jwjcfx="航前+航后";
		    }else if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getRoutinePermit().equals(FlightServiceInfo.ROUTINE_PERMIT.TRANSIT_CHECK)){
		            jwjcfx="短停";
		    }
		}
	}
	/*String qyc="";
	String qyjw="16,17,18,1,2,3,4,5,6,7,8,9,10,13,14,15,66,67,68,69,70,71,72,73,98,99,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,279";
	if(flightServiceInfo.getTractorsUsedCount() != null 
	  && flightServiceInfo.getFlightBase().getFlightResource().getStandID()!=null
	  && qyjw.contains(flightServiceInfo.getFlightBase().getFlightResource().getStandID())){
	    qyc="1";
	}*/
	
	
	
	//旅客摆渡车
	String lkbdc="";
	String alkbdc="0";//进港旅客摆渡车
	if(flightServiceInfo.getAirdromeCountUsedByPassenger() != null) {
	    alkbdc=flightServiceInfo.getAirdromeCountUsedByPassenger();
	}
	String dlkbdc="0";//离港旅客摆渡车        
	if(flightServiceInfo.getFlightBase().getLinkFlight()!=null){
	    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
	        if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getAirdromeCountUsedByPassenger()!=null){
	            dlkbdc=flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getAirdromeCountUsedByPassenger();
	        }
	    }
	}
	lkbdc=String.valueOf(Integer.valueOf(alkbdc)+Integer.valueOf(dlkbdc));
	if("0".equals(lkbdc)){
	    lkbdc="";
	}
	//机组摆渡车
	String jzbdc="";
	String ajzbdc="0";//进港机组摆渡车
	if(flightServiceInfo.getAirdromeCountUsedByCrew() != null){
	    ajzbdc=flightServiceInfo.getAirdromeCountUsedByCrew();
	}
	String djzbdc="0";//离港机组摆渡车
	if(flightServiceInfo.getFlightBase().getLinkFlight()!=null){
	    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
	        if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getAirdromeCountUsedByCrew()!=null){
	            djzbdc=flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getAirdromeCountUsedByCrew();
	        }
	    }
	}
	jzbdc=String.valueOf(Integer.valueOf(ajzbdc)+Integer.valueOf(djzbdc));
	if("0".equals(jzbdc)){
	    jzbdc="";
	}
	
	//引导车
	String ydc="";
	String aydc="1";//进港引导车
	aydc=flightServiceInfo.getLeadCarUsedCount() == null ? "1" :flightServiceInfo.getLeadCarUsedCount();
	String dydc="0";//离港引导车
	if(flightServiceInfo.getFlightBase().getLinkFlight()!=null){
	    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
	        if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getLeadCarUsedCount()!=null){
	            dydc=flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getLeadCarUsedCount();
	        }
	    }
	}
	ydc=String.valueOf(Integer.valueOf(aydc)+Integer.valueOf(dydc));
	if("0".equals(ydc)){
	    ydc="";
	}
	
	//牵引车
	String qyc="";
	if(flightServiceInfo.getFlightBase().getLinkFlight()!=null){
		if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
			if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getTractorsUsedCount()!=null){
				qyc=flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getTractorsUsedCount();
			}
		}
	}
	
	//垃圾车
	String ljc="";
	String aljc="0";//进港垃圾车
	aljc=flightServiceInfo.getGarbageTruckUsedCount() == null ? "0" :flightServiceInfo.getGarbageTruckUsedCount();
	String dljc="0";//离港垃圾车
	if(flightServiceInfo.getFlightBase().getLinkFlight()!=null){
	    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
	        if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getGarbageTruckUsedCount()!=null){
	            dljc=flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getGarbageTruckUsedCount();
	        }
	    }
	}
	ljc=String.valueOf(Integer.valueOf(aljc)+Integer.valueOf(dljc));
	if("0".equals(ljc)){
	    ljc="";
	}
	
	//清水车
	String qsc="";
	String aqsc="0";//进港清水车
	aqsc=flightServiceInfo.getWaterTruckUsedCount()== null ? "0" :flightServiceInfo.getWaterTruckUsedCount();
	String dqsc="0";//离港清水车
	if(flightServiceInfo.getFlightBase().getLinkFlight()!=null){
	    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
	        if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getWaterTruckUsedCount()!=null){
	            dqsc=flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getWaterTruckUsedCount();
	        }
	    }
	}
	qsc=String.valueOf(Integer.valueOf(aqsc)+Integer.valueOf(dqsc));
	if("0".equals(qsc)){
	    qsc="";
	}
	
	//污水车
	String wsc="";
	String awsc="0";//进港污水车
	awsc=flightServiceInfo.getCesspoolageTruckUsedCount() == null ? "0" :flightServiceInfo.getCesspoolageTruckUsedCount();
	String dwsc="0";//离港污水车
	if(flightServiceInfo.getFlightBase().getLinkFlight()!=null){
	    if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo()!=null){
	        if(flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getCesspoolageTruckUsedCount()!=null){
	            dwsc=flightServiceInfo.getFlightBase().getLinkFlight().getFlightServiceInfo().getCesspoolageTruckUsedCount();
	        }
	    }
	}
	wsc=String.valueOf(Integer.valueOf(awsc)+Integer.valueOf(dwsc));
	if("0".equals(wsc)){
	    wsc="";
	}
%>

<% if(i%2==0&&i>0){%>
<P>
<%}

if(i==0) 
{%>
 <div class="t_r" id="dayinDiv" name="dayinDiv"><a href="javascript:printTure();">打印</a>
 <input type="hidden" name="qingkongyema" id="qingkongyema" class="tab" value="清空页码"
onclick="pagesetup_null()"></div>
<!--<div class="t_r"><a href="javascript:window.print();">打印</a></div>-->
<%}
if((i%2==0 && i>0)){%>

<% }else{%>
<div class="main">
<%} %>
		<div class="p8 t_c mb10">
			<h1 align="center" class="mb10">南京禄口国际机场服务确认单</h1>
			<h5 align="left" class="t_r"><%=currentSubairline%></h5>
			<h5 align="right" class="t_r"><%=flightServiceInfo.getFlightBase().getFlightData().getScheduledLandingDateTime2s().substring(0,10)%></h5>
		</div>
		<table class="print">
			<tr>
				<td class="bg1">航班基本信息</td>
				<td colspan="1" class="bg1"></td>
				<td class="t_r">航班号</td>
				<td colspan="3"><span><%=flightServiceInfo.getFlightBase().getFlightIdentity() %>(进)/<%=flightServiceInfo.getFlightBase().getLinkFlight().getFlightIdentity() %>(离)</span></td>
			</tr>
			<tr>
				<td class="t_r">机号</td>
				<td><span><%=flightServiceInfo.getFlightBase().getFlightData().getRegisteration() == null? "" : flightServiceInfo.getFlightBase().getFlightData().getRegisteration()%></span></td>
				<td class="t_r">机型</td>
				<td><span><%=flightServiceInfo.getFlightBase().getFlightData().getAircraftICAOCode() == null? "" : flightServiceInfo.getFlightBase().getFlightData().getAircraftICAOCode()%></td>
				<td class="t_r">飞行性质</td>
				<td><%=flightServiceInfo.getFlightBase().getFlightData().getFlightServiceType() %>(进)<span>/</span><%=flightServiceInfo.getFlightBase().getLinkFlight().getFlightData().getFlightServiceType() %>(离)</td>
			</tr>
			<tr>
				<td class="t_r">落地时间</td>
				<td><%=flightServiceInfo.getFlightBase().getFlightData().getActualLandingDateTime2s() %></td>
				<td class="t_r">机位</td>
				<td><%=flightServiceInfo.getFlightBase().getFlightResource().getStandID()== null ? "" :flightServiceInfo.getFlightBase().getFlightResource().getStandID() %>/<%=flightServiceInfo.getFlightBase().getLinkFlight().getFlightResource().getStandID()== null ? "" :flightServiceInfo.getFlightBase().getLinkFlight().getFlightResource().getStandID()%></td>
				<td class="t_r">起飞时间</td>
				<td><%=flightServiceInfo.getFlightBase().getLinkFlight().getFlightData().getActualTakeOffDateTime2s() %></td>
			</tr>
			<tr>
				<td class="t_r">飞机服务</td>
				<td><%=fjfw%></td>
				<td class="t_r">机务检查与放行</td>
				<td><%=jwjcfx%></td>
				<td class="t_r">备降航班使用候机楼人数</td>
				<td><%=bjhbsyhjlrs%></td>
			</tr>

		
		</table>
		<table class="print" style="border-top:none;">
			<tr>
				<td colspan="6" class="bg1">服务计时信息</td>
			</tr>
			<!--<tr>
				<td class="t_r">客桥靠桥时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getPassengerBridgeServicedStartTime())%></td>
				<td class="t_r">桥载空调开始时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getBridgeAirconditionStartTime())%></td>
				<td class="t_r">桥载电源开始时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getBridge400PowerStartTime())%></td>
				
			</tr>
			
			<tr>
				<td class="t_r">客桥撤离时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getPassengerBridgeServicedEndTime())%></td>
				<td class="t_r">桥载电源结束时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getBridge400PowerEndTime())%></td>
				<td class="t_r">客梯车靠接时间</td>
				<td><%= FlightServiceInfo.getFormatDate(flightServiceInfo.getPassengerCarUsedStartTime())%></td>
			</tr>-->
				<%if(vos == null || vos.size() == 0) {%>
				<tr>
				<td colspan="6" >暂无使用情况</td>
				</tr>
				<%}else {
					for(int p=vos.size()-1;p>=0;p--){
						if(vos.get(p).getName().contains("上时间")||vos.get(p).getName().contains("撤时间")||vos.get(p).getName().contains("次数")||vos.get(p).getName().contains("例行检查")){
							vos.remove(p);
						}
					}
				   int loopCount = vos.size()/3;
				   System.out.println("loopCount=" + loopCount);
				   for(int j=0;j<loopCount;j++){
					   FlightServiceInfoHistoryVo vo1 = vos.get(j*3);
					   FlightServiceInfoHistoryVo vo2 = vos.get(j*3+1);
					   FlightServiceInfoHistoryVo vo3 = vos.get(j*3+2);
					   %>	
					   <tr>
					<td class="t_r"><%= vo1.getName()%>(开始) </td>
					<td ><%= FlightServiceInfo.getFormatDate(vo1.getStartTime())%> </td>
					<td class="t_r"><%= vo2.getName()%> (开始)</td>
					<td ><%= FlightServiceInfo.getFormatDate(vo2.getStartTime())%> </td>
					<td class="t_r"><%= vo3.getName()%> (开始)</td>
					<td ><%= FlightServiceInfo.getFormatDate(vo3.getStartTime())%> </td>
					</tr>
					 <tr>
					<td class="t_r"><%= vo1.getName()%>(结束) </td>
					<td ><%= FlightServiceInfo.getFormatDate(vo1.getEndTime())%> </td>
					<td class="t_r"><%= vo2.getName()%> (结束)</td>
					<td ><%= FlightServiceInfo.getFormatDate(vo2.getEndTime())%> </td>
					<td class="t_r"><%= vo3.getName()%> (结束)</td>
					<td ><%= FlightServiceInfo.getFormatDate(vo3.getEndTime())%> </td>
					</tr>
					 <%}
					 if (vos.size()%3==1){
						 FlightServiceInfoHistoryVo vo = vos.get(loopCount*3);
						 %>
						  <tr>
					<td class="t_r"><%= vo.getName()%>(开始) </td>
					<td><%= FlightServiceInfo.getFormatDate(vo.getStartTime())%> </td>
					<td class="t_r"></td>
					<td> </td>
					<td class="t_r"></td>
					<td> </td>
					</tr>
					 <tr>
					<td class="t_r"><%= vo.getName()%>(结束) </td>
					<td><%= FlightServiceInfo.getFormatDate(vo.getEndTime())%> </td>
					<td class="t_r"></td>
					<td> </td>
					<td class="t_r"></td>
					<td></td>
					</tr>
					<%}
					 if (vos.size()%3==2){
						 FlightServiceInfoHistoryVo vo1 = vos.get(loopCount*3);
						 FlightServiceInfoHistoryVo vo2 = vos.get(loopCount*3+1);
						 %>
						  <tr>
					<td class="t_r"><%= vo1.getName()%>(开始) </td>
					<td><%= FlightServiceInfo.getFormatDate(vo1.getStartTime())%> </td>
					<td class="t_r"><%= vo2.getName()%>(开始) </td>
					<td><%= FlightServiceInfo.getFormatDate(vo2.getStartTime())%> </td>
					<td class="t_r"></td>
					<td> </td>
					</tr>
					 <tr>
					<td class="t_r"><%= vo1.getName()%>(结束) </td>
					<td><%= FlightServiceInfo.getFormatDate(vo1.getEndTime())%> </td>
					<td class="t_r"><%= vo2.getName()%>(结束) </td>
					<td><%= FlightServiceInfo.getFormatDate(vo2.getEndTime())%> </td>
					<td class="t_r"></td>
					<td></td>
					</tr>
					<%}
					 } 
					   
				%>
		</table>
		
		<%-- 
		<table class="print" style="border-top:none;">
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
		</table> --%>
		
		<table class="print" style="border-top:none;">
			<tr>
				<td colspan="6" class="bg1">车辆使用信息</td>
			</tr>
			<tr>
				<td class="t_r">机组摆渡车（次）</td>
				<td><%= jzbdc%></td>
				<td class="t_r">旅客摆渡车（次）</td>
				<td><%= lkbdc%></td>
				<td class="t_r">残疾人专用车（次）</td>
				<td><%= flightServiceInfo.getSpecialVehiclesCountForDisabled() == null ? "" : flightServiceInfo.getSpecialVehiclesCountForDisabled()%></td>
			</tr>
			<tr>
				<td class="t_r">引导车（次）</td>
				<td><%= ydc %></td>
				<td class="t_r">牵引车（次）</td>
				<td><%= qyc %></td>
				<td class="t_r">垃圾车（次）</td>
				<td><%= ljc %></td>
			</tr>
			<tr>
				<td class="t_r">清水车（次）</td>
				<td><%= qsc %></td>
				<td class="t_r">污水车（次）</td>
				<td><%= wsc %></td>
				<td class="t_r"></td>
				<td><space></space></td>
			</tr>
			<tr>
				<td colspan="6" class="bg1">备注信息</td>
			</tr>
			<tr>
				<td colspan="6"><%= flightServiceInfo.getRemk() == null ? "" : flightServiceInfo.getRemk()%><br><br></td>
			</tr>
			<tr>
				<td colspan="6"><div>航空公司确认签名：</div><br /><br /></td>
			</tr>
		</table>		
		
		<!-- <script type="text/javascript">
        function codeAddress() {
            alert('ok');
        	//var muti = '${pageContext.request.flightServiceInfo}';
        	var muti = ['ab', 'cd', 'ef'];
        	muti.forEach(ShowResults);
        	//console.log(muti);
        }
        
        function ShowResults(value, index, ar) {
        	console.log("value: " + value);
        	console.log(" index: " + index);
        	console.log("<br />");
        }
        window.onload = codeAddress;
        </script> -->
	</div>
<%
}
%>
	
</body>