<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>机位收益表</title>
  <%@include file="../../static/included.jsp"%>
<script type="text/javascript">
$(function(){
	/* $("#startTime,#endTime").jqxDateTimeInput({
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
    }); */
    
    $("#scheduledTime").jqxDateTimeInput({
        formatString: "yyyy-MM-dd",
        showCalendarButton: true,
        allowKeyboardDelete : false,
        width: 200,
        height: 25,
        theme:"arctic",
        culture: 'zh-CN',
        allowNullDate: false,
        showFooter: true,
        selectionMode: 'range',
        todayString: "今天",
        clearString: ""
        //value : "2016/08/01"
    });
	
	$("#export_button").click(function (){
		if($('#grid').jqxGrid('getrows').length<=0){
			alert("没有数据可导出！");
			return false;
		}
		download("../report/seExport");
	});
	
	var search=function(){
		
		var dateRange = $("#scheduledTime").val().split(" - ");
		
		if(new Date(dateRange[1]).getTime() - new Date(dateRange[0]).getTime() > 31*24*60*60*1000){
            alert("日期最大跨度不允许超过31天");
            return;
        }
		var source = {
	            url : "../report/querySeatsEarnings",
	            datatype : "json",
	            data : {
	            	startTime : dateRange[0],
	            	endTime : dateRange[1]
	            },
	            id : 'id',
	            datafields : [
	            	{name:'STAND_NUM',type:'String'},
	            	{name:'STAND_DESCRIPTION',type:'String'},
	            	{name:'VEHICLES',type:'String'},
	            	{name:'TOTAL_AMOUNT',type:'String'},
	            	{name:'ALIGHTING_AND_TAKE_OFF_FEE',type:'String'},
	            	{name:'PARKING_FEE',type:'String'},
	            	{name:'NIGHT',type:'String'},
	            	{name:'PASSENGER_SERVICE_FEE',type:'String'},
	            	{name:'BAGGAGE_SECURITY',type:'String'},
	            	{name:'CARGO_MAIL_SECURITY',type:'String'},
	            	{name:'STOWAGE_COMMUNICATION_FEE',type:'String'},
	            	{name:'CARGOMAIL',type:'String'},
	            	{name:'STATION_SITE',type:'String'},
	            	{name:'FLIGHT_SERVICE_FEE',type:'String'},    
	            	{name:'FLIGHT_DUTY_FEE',type:'String'},
	            	{name:'PASSENGER_CAR',type:'String'},
	            	{name:'PASSENGER_CAR_FERRY',type:'String'},
	            	{name:'CAR_FERRY_CREW',type:'String'},
	            	{name:'LIFT_PLATFORM_TRUCKS',type:'String'},
	            	{name:'VEHICLES_DISABLED',type:'String'},
	            	{name:'GUIDED_VEHICLE',type:'String'},
	            	{name:'CARD',type:'String'},
	            	{name:'GENERALLY_AGENT_FEE',type:'String'},
	            	{name:'FIRST_CLASS',type:'String'},
	            	{name:'VIP',type:'String'},  
	            	{name:'BRIDGE',type:'String'},  
	            	{name:'WAREHOUSE_RECEIPTS',type:'String'},  
	            	{name:'BRIDGE_LOAD_POWER',type:'String'},  
	            	{name:'AIR_CONDITIONING',type:'String'},
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
					{datafield:'STAND_NUM',align: "center", cellsalign:"center",text:"机位",width:'80px'},
					{datafield:'STAND_DESCRIPTION',align: "center", cellsalign:"left",text:"备注",width:'120px'},
					{datafield:'VEHICLES',align: "center", cellsalign:"center",text:"架次",width:'80px'},
					{datafield:'TOTAL_AMOUNT',align: "center", cellsalign:"right",text:"收费总额",width:'100px'},
					
					{datafield:'ALIGHTING_AND_TAKE_OFF_FEE',align: "center", cellsalign:"right",text:"起降服务费",width:'100px'},
					{datafield:'PARKING_FEE',align: "center", cellsalign:"right",text:"停场费",width:'100px'},
					{datafield:'NIGHT',align: "center", cellsalign:"right",text:"夜航附加费",width:'100px'},
					{datafield:'PASSENGER_SERVICE_FEE',align: "center", cellsalign:"right",text:"国内流向旅客服务费",width:'150px'},
					{datafield:'BAGGAGE_SECURITY',align: "center", cellsalign:"right",text:"国内流向旅客行李安检费",width:'150px'},
					{datafield:'CARGO_MAIL_SECURITY',align: "center", cellsalign:"right",text:"国内流向货物邮件安检费",width:'150px'},
					{datafield:'STOWAGE_COMMUNICATION_FEE',align: "center", cellsalign:"right",text:"配载通信旅客行李与集装设备费",width:'200px'},
					{datafield:'CARGOMAIL',align: "center", cellsalign:"right",text:"货物和邮件服务费",width:'120px'},
					{datafield:'STATION_SITE',align: "center", cellsalign:"right",text:"站坪服务费",width:'100px'},
					{datafield:'FLIGHT_SERVICE_FEE',align: "center", cellsalign:"right",text:"飞机服务",width:'100px'},
					{datafield:'FLIGHT_DUTY_FEE',align: "center", cellsalign:"right",text:"飞机勤务",width:'100px'},
					{datafield:'PASSENGER_CAR',align: "center", cellsalign:"right",text:"客梯车",width:'100px'},
					{datafield:'PASSENGER_CAR_FERRY',align: "center", cellsalign:"right",text:"旅客摆渡车",width:'100px'},
					{datafield:'CAR_FERRY_CREW',align: "center", cellsalign:"right",text:"机组摆渡车",width:'100px'},
					{datafield:'LIFT_PLATFORM_TRUCKS',align: "center", cellsalign:"right",text:"升降平台车",width:'100px'},
					{datafield:'VEHICLES_DISABLED',align: "center", cellsalign:"right",text:"残疾人登机车",width:'100px'},
					{datafield:'GUIDED_VEHICLE',align: "center", cellsalign:"right",text:"引导车",width:'100px'},
					{datafield:'CARD',align: "center", cellsalign:"center",text:"行李签牌",width:'100px'},
					{datafield:'GENERALLY_AGENT_FEE',align: "center", cellsalign:"right",text:"代理服务费",width:'100px'},
					{datafield:'FIRST_CLASS',align: "center", cellsalign:"right",text:"头等舱费",width:'100px'},
					{datafield:'VIP',align: "center", cellsalign:"right",text:"VIP费",width:'100px'},
					{datafield:'BRIDGE',align: "center", cellsalign:"right",text:"廊桥",width:'100px'},
					{datafield:'WAREHOUSE_RECEIPTS',align: "center", cellsalign:"right",text:"电子舱单",width:'100px'},
					{datafield:'BRIDGE_LOAD_POWER',align: "center", cellsalign:"right",text:"桥载电源",width:'100px'},
					{datafield:'AIR_CONDITIONING',align: "center", cellsalign:"right",text:"桥载空调",width:'100px'},
					{datafield:'TRACTOR_CAR',align: "center", cellsalign:"right",text:"牵引车",width:'100px'}
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
<div class="panel search" style="height:55px" id="search-panel">
    <div class="main clearfix">
      <div class="clear"></div>
      <div>
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="lb"><label for="scheduledTime">航班执行日:</label></td>
            <td><div id="scheduledTime" class="formItem"></div></td>
            <td class="lb">
              <input type="button" id="query_button" class="find mr8"  />
              <input type="button" id="export_button" value="" class="dc" title="导出" />
            </td>
          </tr>
        </table>
      </div>
    </div>
   </div>
   
   <div id="totalNumber" style="text-align: right;font-weight: bold;font-size: 13px;">&nbsp;</div>

    <div id="grid-panel"  style="height:80%;">
      <div id="grid"></div>
    </div>
	
</body>
</html>
