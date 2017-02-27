<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>航班动态统计</title>
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
    
	 $("#flightIdentity,#airLine,#registeration").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 20 });
	 $("#agent,#flightDirection").jqxDropDownList({ width: 80, height: 25,theme:"arctic"});
	$("#export_button").click(function (){
		if($('#grid').jqxGrid('getrows').length<=0){
			alert("没有数据可导出！");
			return false;
		}
		download("../report/fdExport");
	});
	
	var search=function(){
		
		var dateRange = $("#scheduledTime").val().split(" - ");
		
		if(new Date(dateRange[1]).getTime() - new Date(dateRange[0]).getTime() > 31*24*60*60*1000){
            alert("日期最大跨度不允许超过31天");
            return;
        }
		
		var source = {
	            url : "../report/queryflightDynamics",
	            datatype : "json",
	            data : {
	            	startTime : dateRange[0],
	            	endTime : dateRange[1],
	            	flightDirection : $("#flightDirection").val(),
	            	flightIdentity : $("#flightIdentity").val(),
	            	airLine : $("#airLine").val(),
	            	registeration : $("#registeration").val(),
	            	agent : $("#agent").val()
	            },
	            id : 'id',
	            datafields : [
					{name:'ID',type:'String'},
					{name:'FLIGHT_DIRECTION',type:'String'},
					{name:'SCHEDULED_TIME',type:'String'},
					{name:'AIRLINE',type:'String'},
					{name:'FLIGHT_IDENTITY',type:'String'},
					{name:'REGISTERATION',type:'String'},
					{name:'FLIGHT_ROUTE',type:'String'},
					{name:'ACTUAL_TIME',type:'String'},
					{name:'AIRCRAFT_TYPE',type:'String'},
					{name:'SERVICE_TYPE_DESCRIPTION',type:'String'},
					{name:'AIRCRAFT_PAYLOAD',type:'String'},
					{name:'ADULT',type:'String'},
					{name:'CHILE',type:'String'},
					{name:'BABY',type:'String'},
					{name:'LUGGAGE',type:'String'},
					{name:'CARGO_WEIGHT',type:'String'},
					{name:'MAIL_WEIGHT',type:'String'},
					{name:'AGENT',type:'String'}

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
					{datafield:'SCHEDULED_TIME',align: "center", cellsalign:"center",text:"计划日期",width:'80px'},
					{datafield:'AIRLINE',align: "center", cellsalign:"center",text:"航空公司",width:'80px'},
					{datafield:'FLIGHT_IDENTITY',align: "center", cellsalign:"center",text:"航班号",width:'80px'},
					{datafield:'REGISTERATION',align: "center", cellsalign:"center",text:"机号",width:'80px'},
					{datafield:'FLIGHT_ROUTE',align: "center", cellsalign:"center",text:"航线",width:'120px'},
					{datafield:'ACTUAL_TIME',align: "center", cellsalign:"center",text:"实际起降",width:'120px'},
					{datafield:'AIRCRAFT_TYPE',align: "center", cellsalign:"center",text:"机型",width:'80px'},
					{datafield:'SERVICE_TYPE_DESCRIPTION',align: "center", cellsalign:"center",text:"航班性质",width:'80px'},
					{datafield:'AIRCRAFT_PAYLOAD',align: "center", cellsalign:"center",text:"可供业载",width:'80px'},
					{datafield:'ADULT',align: "center", cellsalign:"center",text:"成人数"},
					{datafield:'CHILE',align: "center", cellsalign:"center",text:"儿童"},
					{datafield:'BABY',align: "center", cellsalign:"center",text:"婴儿"},
					{datafield:'LUGGAGE',align: "center", cellsalign:"center",text:"行李"},
					{datafield:'CARGO_WEIGHT',align: "center", cellsalign:"center",text:"邮件"},
					{datafield:'MAIL_WEIGHT',align: "center", cellsalign:"center",text:"货物"},
					{datafield:'AGENT',align: "center", cellsalign:"center",text:"代理"}			
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
						<td class="lb"><label for="flightDirection">进离港标识:</label></td>
			            <td>
			              <select id="flightDirection" class="formItem">
			                <option  value="0">进港</option>
			                <option value="1">离港</option>
			              </select>
			            </td>
						<td class="lb"><label for="flightIdentity">航班号:</label></td>
			            <td><input id="flightIdentity" class="formItem"/></td>
			            
			            <td class="lb"><label for="airLine">航空公司:</label></td>
			            <td><input id="airLine" class="formItem"/></td>
			            <td class="lb"><label for="registeration">机号:</label></td>
			            <td><input id="registeration" class="formItem"/></td>
			            
						<td class="lb"><label for="agent">代理:</label></td>
			            <td><select id="agent" class="formItem">
				                <option selected value="">全部</option>
				                <option  value="0">海航代理</option>
				                <option value="1">南航代理</option>
				                <option value="2">美兰代理</option>
				              </select>
						</td>
			            
			            
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
