<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>客桥统计</title>
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
    
	$("#standNum").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 20 });
	$("#export_button").click(function (){
		if($('#grid').jqxGrid('getrows').length<=0){
			alert("没有数据可导出！");
			return false;
		}
		download("../report/buExport");
	});
	
	var search=function(){
		
		var dateRange = $("#scheduledTime").val().split(" - ");
		
		if(new Date(dateRange[1]).getTime() - new Date(dateRange[0]).getTime() > 31*24*60*60*1000){
            alert("日期最大跨度不允许超过31天");
            return;
        }
		
		var source = {
	            url : "../report/querybridgeUsed",
	            datatype : "json",
	            data : {
	            	startTime : dateRange[0],
	            	endTime : dateRange[1],
	            	standNum : $("#standNum").val()
	            },
	            id : 'id',
	            datafields : [
					{name:'SCHEDULED_TIME2',type:'String'},
					{name:'AIRLINE',type:'String'},
					{name:'TWIN_TIME',type:'String'},
					{name:'TWIN_FEE',type:'String'},
					{name:'SINGLE_TIME',type:'String'},
					{name:'SINGLE_FEE',type:'String'}
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
					{datafield:'SINGLE_TIME',align: "center", cellsalign:"right",text:"单桥使用时间"},
					{datafield:'SINGLE_FEE',align: "center", cellsalign:"right",text:"单桥收入"},
					{datafield:'TWIN_TIME',align: "center", cellsalign:"right",text:"双桥使用时间"},
					{datafield:'TWIN_FEE',align: "center", cellsalign:"right",text:"双桥收入"}
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
						<td><div id="scheduledTime" class="formItem"></div></td>
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
