<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>设备统计</title>
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
	$("#standNum").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 20 });
	$("#export_button").click(function (){
		if($('#grid').jqxGrid('getrows').length<=0){
			alert("没有数据可导出！");
			return false;
		}
		download("../report/beExport");
	});
	
	var search=function(){
		var source = {
	            url : "../report/querybridgeEquipment",
	            datatype : "json",
	            data : {
	            	startTime : $("#startTime").val(),
	            	endTime : $("#endTime").val(),
	            	standNum : $("#standNum").val()
	            },
	            id : 'id',
	            datafields : [
					{name:'SCHEDULED_TIME2',type:'String'},
					{name:'AIRLINE',type:'String'},
					{name:'TYPE',type:'String'},
					{name:'BRIDGE_ELECTRIC_COUNT',type:'String'},
					{name:'BRIDGE_ELECTRIC_USED_TIME',type:'String'},
					{name:'BRIDGE_ELECTRIC_FEE',type:'String'},
					{name:'BRIDGE_AIRCONDITION_COUNT',type:'String'},
					{name:'BRIDGE_AIRCONDITION_USED_TIME',type:'String'},
					{name:'BRIDGE_AIRCONDITION_FEE',type:'String'}
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
					{datafield:'SCHEDULED_TIME2',align: "center", cellsalign:"center",text:"日期"},
					{datafield:'AIRLINE',align: "center", cellsalign:"center",text:"航空公司"},
					{datafield:'TYPE',align: "center", cellsalign:"center",text:"类型"},
					{datafield:'BRIDGE_ELECTRIC_COUNT',align: "center", cellsalign:"right",text:"使用电源航班架次"},
					{datafield:'BRIDGE_ELECTRIC_USED_TIME',align: "center", cellsalign:"right",text:"电源使用时间"},
					{datafield:'BRIDGE_ELECTRIC_FEE',align: "center", cellsalign:"right",text:"电源收入"},
					{datafield:'BRIDGE_AIRCONDITION_COUNT',align: "center", cellsalign:"right",text:"使用空调航班架次"},
					{datafield:'BRIDGE_AIRCONDITION_USED_TIME',align: "center", cellsalign:"right",text:"空调使用时间"},
					{datafield:'BRIDGE_AIRCONDITION_FEE',align: "center", cellsalign:"right",text:"空调收入"}
				]
            }).on("bindingcomplete",function (){
            	var count=0;
				var rows=$(this).jqxGrid('getrows');
				for(var i=0;i<rows.length;i++){
					if(rows[i].SCHEDULED_TIME2=='小计'){
						count++;
					}
				}
                $("#totalNumber").html(($(this).jqxGrid('getrows').length-count) + " 条记录");
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
						<td class="lb"><label for="standNum">机位:</label></td>
			            <td><input id="standNum" class="formItem"/></td>
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
