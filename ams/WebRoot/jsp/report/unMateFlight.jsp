<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>未配对航班查询</title>
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
		download("../report/uExport");
	});
	
	var search=function(){
		var source = {
	            url : "../report/queryUnMateFlight",
	            datatype : "json",
	            data : {
	            	startTime : $("#startTime").val(),
	            	endTime : $("#endTime").val()
	            },
	            id : 'id',
	            datafields : [
					{name:'FLIGHT_DIRECTION',type:'String'},
					{name:'AIR_LINE_NAME',type:'String'},
					{name:'REGISTERATION',type:'String'},
					{name:'FLIGHT_IDENTITY',type:'String'},
					{name:'SCHEDULED_TIME',type:'String'},
					{name:'ACTUAL_TIME',type:'String'},
					{name:'IATA_ORIGIN_AIRPORT',type:'String'},
					{name:'IATA_DESTINATION_AIRPORT',type:'String'}
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
					{datafield:'FLIGHT_DIRECTION',align: "center", cellsalign:"center",text:"进离港" },
					{datafield:'AIR_LINE_NAME',align: "center", cellsalign:"center",text:"航空公司" },
					{datafield:'REGISTERATION',align: "center", cellsalign:"center",text:"机号" },
					{datafield:'FLIGHT_IDENTITY',align: "center", cellsalign:"center",text:"航班号" },
					{datafield:'SCHEDULED_DATE',align: "center", cellsalign:"center",text:"执行日" },
					{datafield:'SCHEDULED_TIME',align: "center", cellsalign:"center",text:"计划时间" },
					{datafield:'ACTUAL_TIME',align: "center", cellsalign:"center",text:"实际时间" },
					{datafield:'IATA_ORIGIN_AIRPORT',align: "center", cellsalign:"center",text:"始发" },
					{datafield:'IATA_DESTINATION_AIRPORT',align: "center", cellsalign:"center",text:"终点" }
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
            <td><div id="startTime" class="formItem"></div></td>
            <td>-</td>
            <td><div id="endTime" class="formItem"></div></td>
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
