<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>舱单统计</title>
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
	$("#agent,#flightDirection").jqxDropDownList({ width: 80, height: 25,theme:"arctic"});
	$("#flightIdentity,#airLine,#registeration").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 20 });
	$("#export_button").click(function (){
		if($('#grid').jqxGrid('getrows').length<=0){
			alert("没有数据可导出！");
			return false;
		}
		download("../report/mExport");
	});
	
	var search=function(){
		var source = {
	            url : "../report/querymanifest",
	            datatype : "json",
	            data : {
	            	startTime : $("#startTime").val(),
	            	endTime : $("#endTime").val(),
	            	flightIdentity : $("#flightIdentity").val(),
	            	airLine : $("#airLine").val(),
	            	registeration : $("#registeration").val(),
	            	agent : $("#agent").val(),
	            	flightDirection : $("#flightDirection").val()
	            },
	            id : 'id',
	            datafields : [
					{name:'ID',type:'String'},
					{name:'SCHEDULED_TIME',type:'String'},
					{name:'FLIGHT_DIRECTION',type:'String'},
					{name:'AIRLINE',type:'String'},
					{name:'FLIGHT_IDENTITY',type:'String'},
					{name:'REGISTERATION',type:'String'},
					{name:'D_ALL',type:'String'},
					{name:'D_ADULT',type:'String'},
					{name:'D_CHILD',type:'String'},
					{name:'D_BABY',type:'String'},
					{name:'CARGO_NUM',type:'String'},
					{name:'CARGO_WEIGHT',type:'String'},
					{name:'MAIL_NUM',type:'String'},
					{name:'MAIL_WEIGHT',type:'String'},
					{name:'LUGGAGE_NUM',type:'String'},
					{name:'LUGGAGE_WEIGHT',type:'String'},
					{name:'VIP_COUNT',type:'String'},
					{name:'FIRST_CLASS_COUNT',type:'String'},
					{name:'ECONOMIC_COUNT',type:'String'},
					{name:'VIA_ALL',type:'String'},
					{name:'VIA_ADULT',type:'String'},
					{name:'VIA_CHILD',type:'String'},
					{name:'VIA_BABY',type:'String'},
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
					{datafield:'FLIGHT_DIRECTION',align: "center", cellsalign:"center",text:"进离港",width:'80px'},
					{datafield:'AIRLINE',align: "center", cellsalign:"center",text:"航空公司",width:'80px'},
					{datafield:'FLIGHT_IDENTITY',align: "center", cellsalign:"center",text:"航班号",width:'80px'},
					{datafield:'REGISTERATION',align: "center", cellsalign:"center",text:"机号",width:'80px'},
					{datafield:'D_ALL',align: "center", cellsalign:"center",text:"人数",columngroup: 'a',width:'80px'},
					{datafield:'D_ADULT',align: "center", cellsalign:"center",text:"成人数",columngroup: 'a',width:'80px'},
					{datafield:'D_CHILD',align: "center", cellsalign:"center",text:"儿童数",columngroup: 'a',width:'80px'},
					{datafield:'D_BABY',align: "center", cellsalign:"center",text:"婴儿数",columngroup: 'a',width:'80px'},
					{datafield:'CARGO_NUM',align: "center", cellsalign:"center",text:"件数",columngroup: 'b',width:'80px'},
					{datafield:'CARGO_WEIGHT',align: "center", cellsalign:"center",text:"重量",columngroup: 'b',width:'80px'},
					{datafield:'MAIL_NUM',align: "center", cellsalign:"center",text:"件数",columngroup: 'c',width:'80px'},
					{datafield:'MAIL_WEIGHT',align: "center", cellsalign:"center",text:"重量",columngroup: 'c',width:'80px'},
					{datafield:'LUGGAGE_NUM',align: "center", cellsalign:"center",text:"件数",columngroup: 'd',width:'80px'},
					{datafield:'LUGGAGE_WEIGHT',align: "center", cellsalign:"center",text:"重量",columngroup: 'd',width:'80px'},
					{datafield:'VIP_COUNT',align: "center", cellsalign:"center",text:"VIP人数",columngroup: 'e',width:'80px'},
					{datafield:'FIRST_CLASS_COUNT',align: "center", cellsalign:"center",text:"头等舱人数",columngroup: 'e',width:'80px'},
					{datafield:'ECONOMIC_COUNT',align: "center", cellsalign:"center",text:"经济舱人数",columngroup: 'e',width:'80px'},
					{datafield:'VIA_ALL',align: "center", cellsalign:"center",text:"总人数",columngroup: 'f',width:'80px'},
					{datafield:'VIA_ADULT',align: "center", cellsalign:"center",text:"成人数",columngroup: 'f',width:'80px'},
					{datafield:'VIA_CHILD',align: "center", cellsalign:"center",text:"儿童数",columngroup: 'f',width:'80px'},
					{datafield:'VIA_BABY',align: "center", cellsalign:"center",text:"婴儿数",columngroup: 'f',width:'80px'},
					{datafield:'AGENT',align: "center", cellsalign:"center",text:"代理",width:'80px'}
				],
                columngroups: [
                               { text: '本站出港', align: 'center', name: 'a' },
                               { text: '出港货物', align: 'center', name: 'b' },
                               { text: '出港邮件', align: 'center', name: 'c' },
                               { text: '出港行李', align: 'center', name: 'd' },
                               { text: '出港旅客', align: 'center', name: 'e' },
                               { text: '实际过站旅客', align: 'center', name: 'f' }
                           ] 
            }).on("bindingcomplete",function (){
            	var count=0;
				if($(this).jqxGrid('getrows').length>0){
					count=$(this).jqxGrid('getrows').length-1;
				}
                $("#totalNumber").html(count + " 条记录");
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
			            <td class="lb"><label for="flightDirection">进离港标识:</label></td>
			            <td>
			              <select id="flightDirection" class="formItem">
			              	<option  value="">全部</option>
			                <option  value="A">进港</option>
			                <option value="D" selected>离港</option>
			              </select>
			            </td>
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
