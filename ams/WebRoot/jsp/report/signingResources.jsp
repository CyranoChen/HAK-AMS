<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>资源签单统计</title>
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
	 $("#flightIdentity,#airLine,#registeration").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 20 });
	 $("#agent").jqxDropDownList({ width: 80, height: 25,theme:"arctic"});
	$("#export_button").click(function (){
		if($('#grid').jqxGrid('getrows').length<=0){
			alert("没有数据可导出！");
			return false;
		}
		download("../report/srExport");
	});
	
	var search=function(){
		var source = {
	            url : "../report/querysigningResources",
	            datatype : "json",
	            data : {
	            	startTime : $("#startTime").val(),
	            	endTime : $("#endTime").val(),
	            	flightIdentity : $("#flightIdentity").val(),
	            	airLine : $("#airLine").val(),
	            	registeration : $("#registeration").val(),
	            	agent : $("#agent").val()
	            },
	            id : 'id',
	            datafields : [
					{name:'ID',type:'String'},
					{name:'SCHEDULED_TIME',type:'String'},
					{name:'AIRLINE_IATA_CODE',type:'String'},
					{name:'FLIGHT_IDENTITY',type:'String'},
					{name:'FLIGHT_DIRECTION',type:'String'},
					{name:'ACTUAL_TIME',type:'String'},
					{name:'AIRLINE_NAME',type:'String'},
					{name:'REGISTERATION',type:'String'},
					{name:'NAME',type:'String'},
					{name:'START_TIME',type:'String'},
					{name:'END_TIME',type:'String'},
					{name:'QUANTITY',type:'String'},
					{name:'UNIT',type:'String'},
					{name:'CREATE_USER',type:'String'},
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
					{datafield:'SCHEDULED_TIME',align: "center", cellsalign:"center",text:"日期",width:'80px'},
					{datafield:'AIRLINE',align: "center", cellsalign:"center",text:"航空公司"},
					{datafield:'FLIGHT_IDENTITY',align: "center", cellsalign:"center",text:"航班号"},
					{datafield:'FLIGHT_DIRECTION',align: "center", cellsalign:"center",text:"进离港"},
					{datafield:'ACTUAL_TIME',align: "center", cellsalign:"center",text:"实际起降"},
					{datafield:'AIRLINE_NAME',align: "center", cellsalign:"center",text:"子公司",width:'150px'},
					{datafield:'REGISTERATION',align: "center", cellsalign:"center",text:"飞机号"},
					{datafield:'NAME',align: "center", cellsalign:"center",text:"资源名称"},
					{datafield:'START_TIME',align: "center", cellsalign:"center",text:"开始时间"},
					{datafield:'END_TIME',align: "center", cellsalign:"center",text:"结束时间"},
					{datafield:'QUANTITY',align: "center", cellsalign:"center",text:"收费计数"},
					{datafield:'UNIT',align: "center", cellsalign:"center",text:"单位",width:'80px'},
					{datafield:'CREATE_USER',align: "center", cellsalign:"center",text:"签单人",width:'80px'},
					{datafield:'AGENT',align: "center", cellsalign:"center",text:"代理",width:'80px'}	
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
						<td><div id="startTime" class="formItem"></div></td>
						<td>-</td>
						<td><div id="endTime" class="formItem"></div></td>
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
