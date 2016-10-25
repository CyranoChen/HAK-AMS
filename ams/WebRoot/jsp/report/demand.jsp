<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>南航需求表</title>
  <%@include file="../../static/included.jsp"%>
<script type="text/javascript">
$(function(){
	$("#startTime,#endTime").jqxDateTimeInput({
        formatString: "yyyy-MM-dd",
        showCalendarButton: true,
        width: 110,
        height: 25,
        theme:"arctic",
        culture: 'zh-CN',
        allowNullDate: false,
        showFooter: true,
        todayString: "今天",
        clearString: ""
    });
	
	$("#export_button").click(function (){
		if($('#grid').jqxGrid('getrows').length<=0){
			alert("没有数据可导出！");
			return false;
		}
		download("../report/dExport");
	});
	
	var search=function(){
		var source = {
	            url : "../report/queryDemand",
	            datatype : "json",
	            data : {
	            	startTime : $("#startTime").val(),
	            	endTime : $("#endTime").val()
	            },
	            id : 'id',
	            datafields : [
					{name:'AIRLINE_NAME',type:'String'},  
					{name:'AIRLINE_ICAO_CODE',type:'String'},  
					{name:'SCHEDULED_TIME',type:'String'},  
					{name:'ACTUAL_TIME',type:'String'},  
					{name:'FLIGHT_IDENTITY',type:'String'},  
					{name:'FLIGHT_PROPERTY',type:'String'},
					{name:'LANDING_MARK',type:'String'},  
					{name:'LANDING_TIME',type:'String'},  
					{name:'STAND_NUM',type:'String'},  
					{name:'REGISTERATION',type:'String'},  
					{name:'AIRCRAFT_TYPE',type:'String'},  
					{name:'FLIGHT_ROUTE',type:'String'},  
					{name:'LEG',type:'String'},  
					{name:'ROUTE_NATURE',type:'String'},  
					{name:'OCCUR_AIRPORT',type:'String'},  
					{name:'TOTAL_AMOUNT',type:'String'},  
					{name:'A_COUNT',type:'String'},  
					{name:'A_UNIT_PRICE',type:'String'},  
					{name:'ALIGHTING_AND_TAKE_OFF_FEE',type:'String'},  
					{name:'B_COUNT',type:'String'},  
					{name:'B_UNIT_PRICE',type:'String'},  
					{name:'PARKING_FEE',type:'String'},  
					{name:'C_COUNT',type:'String'},  
					{name:'C_UNIT_PRICE',type:'String'},  
					{name:'NIGHT',type:'String'},  
					{name:'D_COUNT',type:'String'},  
					{name:'D_UNIT_PRICE',type:'String'},  
					{name:'PASSENGER_SERVICE_FEE',type:'String'},  
					{name:'E_COUNT',type:'String'},  
					{name:'E_UNIT_PRICE',type:'String'},  
					{name:'BAGGAGE_SECURITY',type:'String'},  
					{name:'F_COUNT',type:'String'},  
					{name:'F_UNIT_PRICE',type:'String'},  
					{name:'CARGO_MAIL_SECURITY',type:'String'},  
					{name:'G_COUNT',type:'String'},  
					{name:'G_UNIT_PRICE',type:'String'},  
					{name:'STOWAGE_COMMUNICATION_FEE',type:'String'},  
					{name:'H_COUNT',type:'String'},  
					{name:'H_UNIT_PRICE',type:'String'},  
					{name:'CARGOMAIL',type:'String'},  
					{name:'I_COUNT',type:'String'},  
					{name:'I_UNIT_PRICE',type:'String'},  
					{name:'STATION_SITE',type:'String'},  
					{name:'J_COUNT',type:'String'},  
					{name:'J_UNIT_PRICE',type:'String'},  
					{name:'FLIGHT_SERVICE_FEE',type:'String'},    
					{name:'K_COUNT',type:'String'},  
					{name:'K_UNIT_PRICE',type:'String'},    
					{name:'FLIGHT_DUTY_FEE',type:'String'},  
					{name:'L_COUNT',type:'String'},  
					{name:'L_UNIT_PRICE',type:'String'},   
					{name:'PASSENGER_CAR',type:'String'},  
					{name:'M_COUNT',type:'String'},  
					{name:'M_UNIT_PRICE',type:'String'},  
					{name:'PASSENGER_CAR_FERRY',type:'String'},  
					{name:'N_COUNT',type:'String'},  
					{name:'N_UNIT_PRICE',type:'String'},   
					{name:'CAR_FERRY_CREW',type:'String'},  
					{name:'O_COUNT',type:'String'},  
					{name:'O_UNIT_PRICE',type:'String'},   
					{name:'LIFT_PLATFORM_TRUCKS',type:'String'},  
					{name:'P_COUNT',type:'String'},  
					{name:'P_UNIT_PRICE',type:'String'},   
					{name:'VEHICLES_DISABLED',type:'String'},  
					{name:'Q_COUNT',type:'String'},  
					{name:'Q_UNIT_PRICE',type:'String'},   
					{name:'GUIDED_VEHICLE',type:'String'},  
					{name:'R_COUNT',type:'String'},  
					{name:'R_UNIT_PRICE',type:'String'},   
					{name:'CARD',type:'String'},  
					{name:'S_COUNT',type:'String'},  
					{name:'S_UNIT_PRICE',type:'String'},  
					{name:'GENERALLY_AGENT_FEE',type:'String'},  
					{name:'T_COUNT',type:'String'},  
					{name:'T_UNIT_PRICE',type:'String'},  
					{name:'FIRST_CLASS',type:'String'},  
					{name:'U_COUNT',type:'String'},  
					{name:'U_UNIT_PRICE',type:'String'},  
					{name:'VIP',type:'String'},   
					{name:'V_COUNT',type:'String'},  
					{name:'V_UNIT_PRICE',type:'String'},  
					{name:'BRIDGE',type:'String'},  
					{name:'W_COUNT',type:'String'},  
					{name:'W_UNIT_PRICE',type:'String'},  
					{name:'WAREHOUSE_RECEIPTS',type:'String'},  
					{name:'X_COUNT',type:'String'},  
					{name:'X_UNIT_PRICE',type:'String'},  
					{name:'BRIDGE_LOAD_POWER',type:'String'},  
					{name:'Y_COUNT',type:'String'},  
					{name:'Y_UNIT_PRICE',type:'String'},  
					{name:'AIR_CONDITIONING',type:'String'},
					{name:'Z_COUNT',type:'String'},  
					{name:'Z_UNIT_PRICE',type:'String'},  
					{name:'TRACTOR_CAR',type:'String'}
	            ]
	        };
	        var dataAdapter = createDataAdapter(source);
	        
	        $("#grid").jqxGrid({
                width: "100%",
                height : "100%",
                theme:'arctic',
                source: dataAdapter,
                showfilterrow: true,
                filterable: true,
                sortable : true,
                //autoheight: true,
                editable: false,
                selectionmode: 'singlerow',
                scrollmode: 'logical',
                altrows: true,
                columns :[
					{datafield:'AIRLINE_NAME',align: "center", cellsalign:"center",text:"航空公司",width:'180px'},
					{datafield:'AIRLINE_ICAO_CODE',align: "center", cellsalign:"center",text:"子公司",width:'60px'},
					{datafield:'SCHEDULED_TIME',align: "center", cellsalign:"center",text:"计划日期",width:'100px'},
					{datafield:'ACTUAL_TIME',align: "center", cellsalign:"center",text:"实际日期",width:'100px'},
					{datafield:'FLIGHT_IDENTITY',align: "center", cellsalign:"center",text:"航班号",width:'100px'},
					{datafield:'FLIGHT_PROPERTY',align: "center", cellsalign:"center",text:"航班性质",width:'80px'},
					{datafield:'LANDING_MARK',align: "center", cellsalign:"center",text:"进离港",width:'80px'},
					{datafield:'LANDING_TIME',align: "center", cellsalign:"center",text:"实际起降",width:'80px'},
					{datafield:'STAND_NUM',align: "center", cellsalign:"center",text:"机位",width:'80px'},
					{datafield:'REGISTERATION',align: "center", cellsalign:"center",text:"飞机号",width:'60px'},
					{datafield:'AIRCRAFT_TYPE',align: "center", cellsalign:"center",text:"机型",width:'100px'},
					{datafield:'FLIGHT_ROUTE',align: "center", cellsalign:"center",text:"航线",width:'120px'},
					{datafield:'LEG',align: "center", cellsalign:"center",text:"航段",width:'100px'},
					{datafield:'ROUTE_NATURE',align: "center", cellsalign:"center",text:"标志",width:'50px'},
					{datafield:'OCCUR_AIRPORT',align: "center", cellsalign:"center",text:"发生机场",width:'80px'},
					{datafield:'TOTAL_AMOUNT',align: "center", cellsalign:"right",text:"收费总额",width:'100px'},
					
					{datafield:'A_COUNT',align: "center", cellsalign:"right",text:"架次",columngroup: 'a',width:'100px'},
					{datafield:'A_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'a',width:'100px'},
					{datafield:'ALIGHTING_AND_TAKE_OFF_FEE',align: "center", cellsalign:"right",text:"金额",columngroup: 'a',width:'100px'},
					
					{datafield:'B_COUNT',align: "center", cellsalign:"right",text:"架次",columngroup: 'b',width:'100px'},
					{datafield:'B_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'b',width:'100px'},
					{datafield:'PARKING_FEE',align: "center", cellsalign:"right",text:"金额",columngroup: 'b',width:'100px'},
					
					{datafield:'C_COUNT',align: "center", cellsalign:"right",text:"架次",columngroup: 'c',width:'100px'},
					{datafield:'C_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'c',width:'100px'},
					{datafield:'NIGHT',align: "center", cellsalign:"right",text:"金额",columngroup: 'c',width:'100px'},
					
					{datafield:'D_COUNT',align: "center", cellsalign:"right",text:"人次",columngroup: 'd',width:'100px'},
					{datafield:'D_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'd',width:'100px'},
					{datafield:'PASSENGER_SERVICE_FEE',align: "center", cellsalign:"right",text:"金额",columngroup: 'd',width:'100px'},
					
					{datafield:'AA_COUNT',align: "center", cellsalign:"right",text:"人次",columngroup: 'aa',width:'100px'},
					{datafield:'AA_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'aa',width:'100px'},
					{datafield:'PASSENGER_INT_SERVICE_FEE',align: "center", cellsalign:"right",text:"金额",columngroup: 'aa',width:'100px'},
					
					{datafield:'E_COUNT',align: "center", cellsalign:"right",text:"人次",columngroup: 'e',width:'100px'},
					{datafield:'E_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'e',width:'100px'},
					{datafield:'BAGGAGE_SECURITY',align: "center", cellsalign:"right",text:"金额",columngroup: 'e',width:'100px'},
					
					{datafield:'AB_COUNT',align: "center", cellsalign:"right",text:"人次",columngroup: 'ab',width:'100px'},
					{datafield:'AB_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'ab',width:'100px'},
					{datafield:'BAGGAGE_INT_SECURITY',align: "center", cellsalign:"right",text:"金额",columngroup: 'ab',width:'100px'},
					
					{datafield:'F_COUNT',align: "center", cellsalign:"right",text:"吨数",columngroup: 'f',width:'100px'},
					{datafield:'F_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'f',width:'100px'},
					{datafield:'CARGO_MAIL_SECURITY',align: "center", cellsalign:"right",text:"金额",columngroup: 'f',width:'100px'},
					
					{datafield:'AC_COUNT',align: "center", cellsalign:"right",text:"吨数",columngroup: 'ac',width:'100px'},
					{datafield:'AC_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'ac',width:'100px'},
					{datafield:'CARGO_MAIL_INT_SECURITY',align: "center", cellsalign:"right",text:"金额",columngroup: 'ac',width:'100px'},
					
					{datafield:'G_COUNT',align: "center", cellsalign:"right",text:"架次",columngroup: 'g',width:'100px'},
					{datafield:'G_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'g',width:'100px'},
					{datafield:'STOWAGE_COMMUNICATION_FEE',align: "center", cellsalign:"right",text:"金额",columngroup: 'g',width:'100px'},
					
					{datafield:'H_COUNT',align: "center", cellsalign:"right",text:"架次",columngroup: 'h',width:'100px'},
					{datafield:'H_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'h',width:'100px'},
					{datafield:'CARGOMAIL',align: "center", cellsalign:"right",text:"金额",columngroup: 'h',width:'100px'},
					
					{datafield:'I_COUNT',align: "center", cellsalign:"right",text:"架次",columngroup: 'i',width:'100px'},
					{datafield:'I_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'i',width:'100px'},
					{datafield:'STATION_SITE',align: "center", cellsalign:"right",text:"金额",columngroup: 'i',width:'100px'},
					
					{datafield:'J_COUNT',align: "center", cellsalign:"right",text:"架次",columngroup: 'j',width:'100px'},
					{datafield:'J_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'j',width:'100px'},
					{datafield:'FLIGHT_SERVICE_FEE',align: "center", cellsalign:"right",text:"金额",columngroup: 'j',width:'100px'},
					
					{datafield:'K_COUNT',align: "center", cellsalign:"right",text:"架次",columngroup: 'k',width:'100px'},
					{datafield:'K_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'k',width:'100px'},
					{datafield:'FLIGHT_DUTY_FEE',align: "center", cellsalign:"right",text:"金额",columngroup: 'k',width:'100px'},
					
					{datafield:'L_COUNT',align: "center", cellsalign:"right",text:"小时",columngroup: 'l',width:'100px'},
					{datafield:'L_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'l',width:'100px'},
					{datafield:'PASSENGER_CAR',align: "center", cellsalign:"right",text:"金额",columngroup: 'l',width:'100px'},
					
					{datafield:'M_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 'm',width:'100px'},
					{datafield:'M_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'm',width:'100px'},
					{datafield:'PASSENGER_CAR_FERRY',align: "center", cellsalign:"right",text:"金额",columngroup: 'm',width:'100px'},
					
					{datafield:'N_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 'n',width:'100px'},
					{datafield:'N_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'n',width:'100px'},
					{datafield:'CAR_FERRY_CREW',align: "center", cellsalign:"right",text:"金额",columngroup: 'n',width:'100px'},
					
					{datafield:'O_COUNT',align: "center", cellsalign:"right",text:"小时",columngroup: 'o',width:'100px'},
					{datafield:'O_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'o',width:'100px'},
					{datafield:'LIFT_PLATFORM_TRUCKS',align: "center", cellsalign:"right",text:"金额",columngroup: 'o',width:'100px'},
					
					{datafield:'P_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 'p',width:'100px'},
					{datafield:'P_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'p',width:'100px'},
					{datafield:'VEHICLES_DISABLED',align: "center", cellsalign:"right",text:"金额",columngroup: 'p',width:'100px'},
					
					{datafield:'Q_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 'q',width:'100px'},
					{datafield:'Q_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'q',width:'100px'},
					{datafield:'GUIDED_VEHICLE',align: "center", cellsalign:"right",text:"金额",columngroup: 'q',width:'100px'},
					
					{datafield:'R_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 'r',width:'100px'},
					{datafield:'R_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'r',width:'100px'},
					{datafield:'CARD',align: "center", cellsalign:"right",text:"金额",columngroup: 'r',width:'100px'},
					
					{datafield:'S_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 's',width:'100px'},
					{datafield:'S_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 's',width:'100px'},
					{datafield:'GENERALLY_AGENT_FEE',align: "center", cellsalign:"right",text:"金额",columngroup: 's',width:'100px'},
					
					{datafield:'T_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 't',width:'100px'},
					{datafield:'T_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 't',width:'100px'},
					{datafield:'FIRST_CLASS',align: "center", cellsalign:"right",text:"金额",columngroup: 't',width:'100px'},
					
					{datafield:'U_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 'u',width:'100px'},
					{datafield:'U_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'u',width:'100px'},
					{datafield:'VIP',align: "center", cellsalign:"right",text:"金额",columngroup: 'u',width:'100px'},
					
					{datafield:'V_COUNT',align: "center", cellsalign:"right",text:"时间",columngroup: 'v',width:'100px'},
					{datafield:'V_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'v',width:'100px'},
					{datafield:'BRIDGE',align: "center", cellsalign:"right",text:"金额",columngroup: 'v',width:'100px'},
					
					{datafield:'W_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 'w',width:'100px'},
					{datafield:'W_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'w',width:'100px'},
					{datafield:'WAREHOUSE_RECEIPTS',align: "center", cellsalign:"right",text:"金额",columngroup: 'w',width:'100px'},
					
					{datafield:'X_COUNT',align: "center", cellsalign:"right",text:"时间",columngroup: 'x',width:'100px'},
					{datafield:'X_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'x',width:'100px'},
					{datafield:'BRIDGE_LOAD_POWER',align: "center", cellsalign:"right",text:"金额",columngroup: 'x',width:'100px'},
					
					{datafield:'Y_COUNT',align: "center", cellsalign:"right",text:"时间",columngroup: 'y',width:'100px'},
					{datafield:'Y_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'y',width:'100px'},
					{datafield:'AIR_CONDITIONING',align: "center", cellsalign:"right",text:"金额",columngroup: 'y',width:'100px'},
					
					{datafield:'Z_COUNT',align: "center", cellsalign:"right",text:"次数",columngroup: 'z',width:'100px'},
					{datafield:'Z_UNIT_PRICE',align: "center", cellsalign:"right",text:"单价",columngroup: 'z',width:'100px'},
					{datafield:'TRACTOR_CAR',align: "center", cellsalign:"right",text:"金额",columngroup: 'z',width:'100px'}
				],
                columngroups: [
                               { text: '起降服务费', align: 'center', name: 'a' },
                               { text: '停场费', align: 'center', name: 'b' },
                               { text: '夜航附加费', align: 'center', name: 'c' },
                               { text: '国内流向旅客服务费', align: 'center', name: 'd' },
                               { text: '国际流向旅客服务费', align: 'center', name: 'aa' },
                               { text: '国内流向旅客行李安检费', align: 'center', name: 'e' },
                               { text: '国际流向旅客行李安检费', align: 'center', name: 'ab' },
                               { text: '国内流向货物邮件安检费', align: 'center', name: 'f' },
                               { text: '国际流向货物邮件安检费', align: 'center', name: 'ac' },
                               { text: '配载通信旅客行李与集装设备费', align: 'center', name: 'g' },
                               { text: '货物和邮件服务费', align: 'center', name: 'h' },
                               { text: '站坪服务费', align: 'center', name: 'i' },
                               { text: '飞机服务', align: 'center', name: 'j' },
                               { text: '飞机勤务', align: 'center', name: 'k' },
                               { text: '客梯车', align: 'center', name: 'l' },
                               { text: '旅客摆渡车', align: 'center', name: 'm' },
                               { text: '机组摆渡车', align: 'center', name: 'n' },
                               { text: '升降平台车', align: 'center', name: 'o' },
                               { text: '残疾人登机车', align: 'center', name: 'p' },
                               { text: '引导车', align: 'center', name: 'q' },
                               { text: '行李签牌', align: 'center', name: 'r' },
                               { text: '代理服务费', align: 'center', name: 's' },
                               { text: '头等舱费', align: 'center', name: 't' },
                               { text: 'VIP费', align: 'center', name: 'u' },
                               { text: '廊桥', align: 'center', name: 'v' },
                               { text: '电子舱单', align: 'center', name: 'w' },
                               { text: '桥载电源', align: 'center', name: 'x' },
                               { text: '桥载空调', align: 'center', name: 'y' },
                               { text: '牵引车', align: 'center', name: 'z' }
                           ] 
            }).on("bindingcomplete",function (){

                $("#totalNumber").html($(this).jqxGrid('getrows').length + " 条记录");
                //setTimeout(function (){resize();},1000);

            })
	}
	$("#query_button").click(function (){
		search();
	});
	search();
});
</script>
</head>


<body  style="padding: 10px;min-width: 1024px;">
<%@ include file="./header_tabs.jsp"%>
	<div class="panel search" style="height: 55px" id="search-panel">
		<div class="main clearfix">
			<div class="clear"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="lb"><label for="scheduledTime">航班执行日:</label></td>
						<td><div id="startTime" class="formItem"></div></td>
						<td>-</td>
						<td><div id="endTime" class="formItem"></div></td>
						<td class="lb"><input type="button" id="query_button"
							class="find mr8" /> <input type="button" id="export_button"
							value="" class="dc" title="导出" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<div id="totalNumber"
		style="text-align: right; font-weight: bold; font-size: 13px;">&nbsp;</div>

	<div id="grid-panel" style="height: 80%;">
		<div id="grid"></div>
	</div>

</body>
</html>
