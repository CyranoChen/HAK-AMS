<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>停场表</title>
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
		download("../report/sExport");
	});
	
	var search=function(){
		
		var dateRange = $("#scheduledTime").val().split(" - ");
		
		if(new Date(dateRange[1]).getTime() - new Date(dateRange[0]).getTime() > 31*24*60*60*1000){
            alert("日期最大跨度不允许超过31天");
            return;
        }	
		
		var source = {
	            url : "../report/queryStopPark",
	            datatype : "json",
	            data : {
	            	startTime : dateRange[0],
	            	endTime : dateRange[1]
	            },
	            id : 'id',
	            datafields : [
					{name:'AIRLINE_IATA_CODE',type:'String'},
					{name:'AIRLINE_NAME',type:'String'},
					{name:'AIRLINE_ICAO_CODE',type:'String'},
					{name:'AIRCRAFT_TYPE',type:'String'},
					{name:'ACTUAL_TIME',type:'String'},
					{name:'SCHEDULED_TIME',type:'String'},
					{name:'VEHICLES',type:'String'},
					{name:'REGISTERATION',type:'String'},
					{name:'FLIGHT_IDENTITY',type:'String'},
					{name:'SERVICE_TYPE_DESCRIPTION',type:'String'},
					{name:'STAND_NUM',type:'String'},
					{name:'ACTUAL_TAKE_OFF_TIME',type:'String'},
					{name:'ACTUAL_LANDING_TIME',type:'String'},
					{name:'LANDED_TIME',type:'String'},
					{name:'FLIGHT_ROUTE',type:'String'},
					{name:'ROUTE_NATURE',type:'String'},
					{name:'D_ADULT',type:'String'},
					{name:'D_CHILE',type:'String'},
					{name:'D_BABY',type:'String'},
					{name:'VIA_ADULT',type:'String'},
					{name:'VIA_CHILD',type:'String'},
					{name:'VIA_BABY',type:'String'},
					{name:'CARGO_WEIGHT',type:'String'},
					{name:'MAIL_WEIGHT',type:'String'}
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
					{datafield:'AIRLINE_IATA_CODE',align: "center", cellsalign:"center",text:"航空公司",width:'80px'},
					{datafield:'AIRLINE_NAME',align: "center", cellsalign:"center",text:"航空公司|名称",width:'180px'},
					{datafield:'AIRLINE_ICAO_CODE',align: "center", cellsalign:"center",text:"子公司",width:'60px'},
					{datafield:'AIRCRAFT_TYPE',align: "center", cellsalign:"center",text:"机型",width:'100px'},
					{datafield:'ACTUAL_TIME',align: "center", cellsalign:"center",text:"实际日期",width:'100px'},
					{datafield:'SCHEDULED_TIME',align: "center", cellsalign:"center",text:"计划日期",width:'100px'},
					{datafield:'VEHICLES',align: "center", cellsalign:"center",text:"架次",width:'60px'},
					{datafield:'REGISTERATION',align: "center", cellsalign:"center",text:"机号",width:'60px'},
					{datafield:'FLIGHT_IDENTITY',align: "center", cellsalign:"center",text:"航班号",width:'100px'},
//					{datafield:'SERVICE_TYPE_DESCRIPTION',align: "center", cellsalign:"center",text:"航班任务",width:'80px'},
					{datafield:'FLIGHT_PROPERTY',align: "center", cellsalign:"center",text:"航班任务",width:'80px'},
					{datafield:'STAND_NUM',align: "center", cellsalign:"center",text:"机位",width:'60px'},
					{datafield:'ACTUAL_LANDING_TIME',align: "center", cellsalign:"center",text:" 实际降落",width:'150px'},
					{datafield:'ACTUAL_TAKE_OFF_TIME',align: "center", cellsalign:"center",text:"实际起飞",width:'150px'},
					{datafield:'LANDED_TIME',align: "center", cellsalign:"center",text:"停场时间",width:'80px'},
					{datafield:'FLIGHT_ROUTE',align: "center", cellsalign:"center",text:"航线",width:'120px'},
					{datafield:'ROUTE_NATURE',align: "center", cellsalign:"center",text:"航线类型",width:'80px'},
					{datafield:'D_ADULT',align: "center", cellsalign:"center",text:"出港成人",width:'100px'},
					{datafield:'D_CHILE',align: "center", cellsalign:"center",text:"出港儿童",width:'100px'},
					{datafield:'D_BABY',align: "center", cellsalign:"center",text:"出港婴儿",width:'100px'},
					{datafield:'VIA_ADULT',align: "center", cellsalign:"center",text:"过站成人",width:'100px'},
					{datafield:'VIA_CHILD',align: "center", cellsalign:"center",text:"过站儿童",width:'100px'},
					{datafield:'VIA_BABY',align: "center", cellsalign:"center",text:"过站婴儿",width:'100px'},
					{datafield:'CARGO_WEIGHT',align: "center", cellsalign:"center",text:"货物重量",width:'100px'},
					{datafield:'MAIL_WEIGHT',align: "center", cellsalign:"center",text:"邮件重量",width:'100px'}					
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


<body style="padding: 10px; min-width: 1024px;">
	<%@ include file="./header_tabs.jsp"%>
	<div class="panel search" style="height: 55px" id="search-panel">
		<div class="main clearfix">
			<div class="clear"></div>
			<div>
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="lb"><label for="scheduledTime">航班执行日:</label></td>
						<td><div id="scheduledTime" class="formItem"></div></td>
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
