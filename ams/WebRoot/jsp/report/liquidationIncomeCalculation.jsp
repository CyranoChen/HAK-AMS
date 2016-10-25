<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>清算表</title>
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
		download("../report/lExport");
	});
	
	var search=function(){
		var source = {
	            url : "../report/queryLiquidationIncomeCalculation",
	            datatype : "json",
	            data : {
	            	startTime : $("#startTime").val(),
	            	endTime : $("#endTime").val()
	            },
	            id : 'id',
	            datafields : [
	            	{name:'OPENACCOUNTSAIRPORT',type:'String'},
					{name:'OPERATIONAIRPORT',type:'String'},
					{name:'PAY_AIRLINE',type:'String'},
					{name:'OPERATIONS_AIRLINE',type:'String'},
					{name:'SCHEDULED_TIME',type:'String'},
					{name:'ACTUAL_TIME',type:'String'},
					{name:'FLIGHT_IDENTITY',type:'String'},
					{name:'REGISTERATION',type:'String'},
					{name:'AIRCRAFT_TYPE',type:'String'},
					{name:'FLIGHT_ROUTE',type:'String'},
					{name:'ROUTE_NATURE',type:'String'},
//					{name:'LEG',type:'String'},
 					{name:'LEG0',type:'String'},
					{name:'LEG_NATURE',type:'String'},
//					{name:'LEG1',type:'String'},
					{name:'OCCUR_AIRPORT',type:'String'},
					{name:'START_TIME',type:'String'},
					{name:'END_TIME',type:'String'},
					{name:'LANDING_TIME',type:'String'},
					{name:'LANDING_MARK',type:'String'},
					{name:'FLIGHT_PROPERTY',type:'String'},
					{name:'FLIGHT_STATUS',type:'String'},
					{name:'CHARGE_CODE',type:'String'},
					{name:'QUANTITY',type:'String'},
					{name:'UNIT_PRICE',type:'String'},
					{name:'FEE',type:'String'},
					{name:'NAME',type:'String'},
					{name:'REMARK',type:'String'},
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
				enabletooltips: true,
				sortable : true,
                //autoheight: true,
                editable: false,
                selectionmode: 'singlerow',
                scrollmode: 'logical',
                altrows: true,
                columns :[
					{datafield:'OPENACCOUNTSAIRPORT',align: "center", cellsalign:"center",text:"开账机场代码",width:'100px'},
			        {datafield:'OPERATIONAIRPORT',align: "center", cellsalign:"center",text:"实际运营机场代码",width:'120px'},
			        {datafield:'PAY_AIRLINE',align: "center", cellsalign:"center",text:"付款航空公司代码",width:'120px'},
			        {datafield:'OPERATIONS_AIRLINE',align: "center", cellsalign:"center",text:"实际运营航空公司代码",width:'150px'},
			        {datafield:'SCHEDULED_TIME',align: "center", cellsalign:"center",text:"计划日期",width:'100px'},
			        {datafield:'ACTUAL_TIME',align: "center", cellsalign:"center",text:"实际日期",width:'100px'},
			        {datafield:'FLIGHT_IDENTITY',align: "center", cellsalign:"center",text:"航班号",width:'100px'},
			        {datafield:'REGISTERATION',align: "center", cellsalign:"center",text:"飞机号",width:'60px'},
			        {datafield:'AIRCRAFT_TYPE',align: "center", cellsalign:"center",text:"机型",width:'100px'},
			        {datafield:'FLIGHT_ROUTE',align: "center", cellsalign:"center",text:"航线",width:'120px'},
			        {datafield:'ROUTE_NATURE',align: "center", cellsalign:"center",text:"航线性质",width:'90px'},
//			        {datafield:'LEG',align: "center", cellsalign:"center",text:"航段",width:'120px'},
//			        {datafield:'LEG_NATURE',align: "center", cellsalign:"center",text:"航段性质",width:'90px'},
 				    {datafield:'LEG0',align: "center", cellsalign:"center",text:"航段",width:'105px'},
					 {datafield:'LEG_NATURE',align: "center", cellsalign:"center",text:"航段性质",width:'90px'},
//			        {datafield:'LEG1',align: "center", cellsalign:"center",text:"航段1",width:'105px'},
			        {datafield:'OCCUR_AIRPORT',align: "center", cellsalign:"center",text:"发生机场",width:'90px'},
			        {datafield:'START_TIME',align: "center", cellsalign:"center",text:"开始时间",width:'150px'},
			        {datafield:'END_TIME',align: "center", cellsalign:"center",text:"结束时间",width:'150px'},
			        {datafield:'LANDING_TIME',align: "center", cellsalign:"center",text:"起降时间",width:'150px'},
			        {datafield:'LANDING_MARK',align: "center", cellsalign:"center",text:"起降标志",width:'90px'},
			        {datafield:'FLIGHT_PROPERTY',align: "center", cellsalign:"center",text:"任务性质",width:'90px'},
			        {datafield:'FLIGHT_STATUS',align: "center", cellsalign:"center",text:"航班状态",width:'90px'},
			        {datafield:'CHARGE_CODE',align: "center", cellsalign:"center",text:"费用名称",width:'150px'},
			        {datafield:'QUANTITY',align: "center", cellsalign:"center",text:"数量",width:'100px'},
			        {datafield:'UNIT_PRICE',align: "center", cellsalign:"center",text:"单价",width:'100px'},
			        {datafield:'FEE',align: "center", cellsalign:"center",text:"金额",width:'100px'},
			        {datafield:'NAME',align: "center", cellsalign:"left",text:"备注",width:'400px'},
			        {datafield:'REMARK',align: "center", cellsalign:"left",text:"计算公式",width:'400px'}
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
