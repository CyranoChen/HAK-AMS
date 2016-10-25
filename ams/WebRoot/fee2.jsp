<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%response.setHeader("Access-Control-Allow-Origin", "*");%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收费条目详情</title>
<%@include file="./static/included.jsp"%>
<link rel="stylesheet" href="../../static/wonders/fee/css/formalize.css">
    <link rel="stylesheet" href="../../static/wonders/fee/css/reset.css">
    <link href="../../static/wonders/fee/css/pages.css" rel="stylesheet" type="text/css">
    <link href="../../static/wonders/fee/css/imgs.css" rel="stylesheet" type="text/css">
     <style>
        body{
            background-color: #fff;
            line-height: 200%;
            margin: 0 auto;
        }
        a{
            color: #ddd;
        }
        a:hover, a.selected{
            color: #16cbff;
        }
        .main{
            width: 920px;
            margin: 0 auto;
        }
        
        h1, h3{
            line-height: 150%;
        }
        h3{
            font-weight: normal;
        }
        
        h6{
            color: #999;
        }
        .bg1{
            background-color: #e1e1e1;
        }
        .bg2{
            background-color: #eee;
        }
        .bor_top{
            border-top: #ccc 1px solid;
        }
        .bor_bottom{
            border-bottom: #ccc 1px solid;
        }
        .bor_right{
            border-right: #ccc 1px solid;
        }
        .mb5{
            margin-bottom: 3px;
        }
        .mt10{
            margin-top: 10px;
        }
        .plr24{
            padding: 0 24px;
        }
        .plr10{
            padding: 0 8px;
        }
    </style>
    <link rel="shortcut icon" href="../../static/wonders/css/default/images/favicon.ico" type="image/x-icon" />
    <script type="text/javascript">
    
    $(function(){
    	var id = <%=request.getAttribute("id")%>;
		$.post(
			"search",
			{flightMateInfoId:id},
			function(rtn){
				var currentFlightMateInfo = rtn.currentFlightMateInfo;
				var linkedFlightMateInfo = rtn.linkedFlightMateInfo;
				var chargeTerms = rtn.chargeTerms;
				var flightDirection = rtn.currentFlightMateInfo.flightDirection;
				if(flightDirection=="0"){
		       		$("#arrival").css("background-color","#CFCFCF");
		       	}else{
		       		$("#departure").css("background-color","#CFCFCF");
		       	}
				$("[direction=arrive]").each(function (){
                    var $this = $(this);
                    var name = $this.attr("colname");
                    var value=null;
                    if(flightDirection!=null && flightDirection =="0"){
                    	value = currentFlightMateInfo[name];
                    }else{
                    	value = linkedFlightMateInfo[name];
                    }
                    if(typeof(value) === 'undefined' || value == null){
                        value = "";
                    }
                    $this.html(value);
                });
				$("[direction=departure]").each(function (){
                    var $this = $(this);
                    var name = $this.attr("colname");
                    var value=null;
                    if(flightDirection!=null && flightDirection =="1"){
                    	value = currentFlightMateInfo[name];
                    }else{
                    	value = linkedFlightMateInfo[name];
                    }
                    if(typeof(value) === 'undefined' || value == null){
                        value = "";
                    }
                    $this.html(value);
                });
				if(flightDirection =="0"){
					$("#title").html(currentFlightMateInfo["aFlightIdentity"]+"收费条目详情");
				}else{
					$("#title").html(currentFlightMateInfo["dFlightIdentity"]+"收费条目详情");
				}
				
				$("#arrivalFlight").html("进港");
				$("#departureFlight").html("离港");
				//日期格式转换
				$("#aExecuteTime").html(new Date(Number($("#aExecuteTime").html())).format("yyyy-MM-dd"));
				$("#dExecuteTime").html(new Date(Number($("#dExecuteTime").html())).format("yyyy-MM-dd"));
				$("#landedTime").html(new Date(Number($("#landedTime").html())).format("yyyy-MM-dd HH:mm"));
				$("#takeOffTime").html(new Date(Number($("#takeOffTime").html())).format("yyyy-MM-dd HH:mm"));
				$("#scheduledLandedTime").html(new Date(Number($("#scheduledLandedTime").html())).format("yyyy-MM-dd HH:mm"));
				$("#scheduledTakeOffTime").html(new Date(Number($("#scheduledTakeOffTime").html())).format("yyyy-MM-dd HH:mm"));
				var source =
	            {
	                localdata: chargeTerms,
	                datatype: "array",
	                datafields:
	                [
	                    { name: 'id', type: 'string' },
	                    { name: 'name', type: 'string' },
	                    { name: 'fee', type: 'float' }
	                ]
	            };
	            var dataAdapter = new $.jqx.dataAdapter(source);
				$("#chargeTermsGrid").jqxGrid({
	                height: 500,
	                sortable: false,
	                altrows: true,
	                enabletooltips: true,
	                editable: true,
	                editmode:'dblclick',
	            	theme:"arctic",
	                width:'100%',
	                source: dataAdapter,
	                columnsresize: true,
	                columns: [
					  { text: 'id', datafield: 'id', align: 'center',cellsalign: 'center', width: '10px',editable:false,hidden:true},        
	                  { text: '收费名称', datafield: 'name', align: 'left',cellsalign: 'left', width: '85%',editable:false},
	                  { text: '金额', datafield: 'fee', align: 'center',cellsalign: 'right', width: '15%',cellsformat:"f2", columntype: 'numberinput',
	                      createeditor: function (row, cellvalue, editor) {
	                          editor.jqxNumberInput({ digits: 10 });
	                      }}
	              ]
	            });
				
				$("#chargeTermsGrid").on('cellvaluechanged', function (event){
					var args = event.args;
					//行序号
				    var rowIndex = args.rowindex;
					var data = $("#chargeTermsGrid").jqxGrid('getrowdata',rowIndex);
					$.post(
						"updateChargeTerm",
						data = {
		           			id:data.id,
		           			fee:data.fee
		         		},
						function(rtn){
							if(rtn){
								alert("更新成功");
							}else{
								alert("更新失败");
							}							
						}
					);
				});
			}
			
		);
    });	
    </script>
</head>
<body>
   <form name="feeForm" action="/ams/fqs/save">
    <input type="hidden" name ="flightMateInfoId" id="flightMateInfoId" value="${flightMateInfoId}">
    <input type="hidden" name="first"  value="first">
	<div class="main">
		<div class="p8 t_c bor_bottom">
			<h1 class="mb10" colname="title" id="title">&nbsp;</h1>
		</div>
		<div class="clearfix mb10 mt10 plr8">
			<div class="w50p fl bor_right" id="arrival">
				<h3 colname="aFlightIdentity" direction="arrive">&nbsp;</h3>
				<table width="100%">
					<tr>
						<td>执行日期：</td>
						<td colname="executeTime" direction="arrive" id="aExecuteTime"></td>
						<td  >进/离港：</td>
						<td colname="arrivalFlight" direction="arrive" id="arrivalFlight"></td>
					</tr>
					<tr>
						<td  >航空公司：</td>
						<td class="t_l" colname="airLineName" direction="arrive"></td>
						<td  >计划降落：</td>
						<td colname="scheduledLandedTime" direction="arrive" id="scheduledLandedTime"></td>
					</tr>
					<tr>
						<td  >飞机机号：</td>
						<td colname="aRegisteration" direction="arrive"></td>
						<td  >实际降落：</td>
						<td colname="landedTime" direction="arrive" id="landedTime"></td>

					</tr>
					<tr>
						<td  >航班性质：</td>
						<td colname="aFlightProperty" direction="arrive"></td>
						<td  >始发机场：</td>
						<td colname="iataOriginAirport" direction="arrive"></td>
					</tr>
				</table>
			</div>
			<div class="w49p fr" id="departure">
				<h3 colname="dFlightIdentity" direction="departure">&nbsp;</h3>
				<table width="100%">
					<tr>
						<td  >执行日期：</td>
						<td colname="executeTime" direction="departure" id="dExecuteTime"></td>
						<td  >进/离港：</td>
						<td colname="departureFlight" direction="departure" id="departureFlight"></td>
					</tr>
					<tr>
						<td  >航空公司：</td>
						<td colname="airLineName" direction="departure"></td>
						<td  >计划起飞：</td>
						<td colname="scheduledTakeOffTime" direction="departure" id="scheduledTakeOffTime"></td>
					</tr>
					<tr>
						<td  >飞机机号：</td>
						<td colname="dRegisteration" direction="departure"></td>						
						<td  >实际起飞：</td>
						<td colname="takeOffTime" direction="departure" id="takeOffTime"></td>
					</tr>
					<tr>
						<td  >航班性质：</td>
						<td colname="dFlightProperty" direction="departure"></td>						
						<td  >目的机场：</td>
						<td colname="iataDestinationAirport" direction="departure"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="clearfix mb5">
			<div class="mb10 plr8">
				 <div id="chargeTermsGrid"></div>
			</div>	
		</div>			
   </form>
</body>
</html>