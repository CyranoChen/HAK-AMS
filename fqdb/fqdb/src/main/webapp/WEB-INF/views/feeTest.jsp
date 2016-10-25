<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>feeTest</title>
<link rel="stylesheet" href="../fee/css/formalize.css">
    <link rel="stylesheet" href="../fee/css/reset.css">
    <link href="../fee/css/pages.css" rel="stylesheet" type="text/css">
    <link href="../fee/css/imgs.css" rel="stylesheet" type="text/css">
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
        .plr24{
            padding: 0 24px;
        }
    </style>
</head>
<body>
   <form name="feeTestform" action="/fqdb/feeTest/save">
    <input type="hidden" name ="flightMateId" id="flightMateId" value="${flightMateId}">
    <input type="hidden" name="first"  value="first">
	<div class="main">
		<div class="p8 t_c bor_bottom">
			<h1 class="mb10">${title} 收费条目详情</h1>
			<h5 class="t_r">${arrivalScheduleDate}</h5>
		</div>
		<div class="clearfix mb10 plr8">
			<div class="t_r">收费主体：${chargeTarget}</div>
			<div class="w50p fl bor_right">
				<h3>${arrival}</h3>
				<div class="clearfix">
					<div class="w49p fl">
						<div>执行日期：${arrivalScheduleDate}</div>
						<div>航空公司：${arrivalAirlineICAOCode}</div>
						<div>机号：${aregisteration}</div>
						<div>航班性质：${arrivalServiceType}</div>
					</div>
					<div class="w49p fl">
						<div>进/离港：${arrivalFlight}</div>
						<div>降落时间：${arrivalLandTime}</div>
						<div>起飞机场：${previousAirport}</div>
					</div>
				</div>
			</div>
			<div class="w49p fr">
				<h3>${departure}</h3>
				<div class="clearfix">
					<div class="w49p fl">
						<div>执行日期：${departureScheduleDate}</div>
						<div>航空公司：${departureAirlineICAOCode}</div>
						<div>机号：${dregisteration}</div>
						<div>航班性质：${departureServiceType}</div>
					</div>
					<div class="w49p fl">
						<div>进/离港：${departureFlight}</div>
						<div>起飞时间：${departureTakeOffTime}</div>
						<div>落地机场：${destinationAirport}</div>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix bor_top bor_bottom mb5">
			<div class="mb10">
				<div class="bg2 plr8">收费条目详情</div>
				<ul class="plr24">
				 <c:forEach items="${chargeTermsList}" var="chargeTerm">
					   <li class="clearfix">
					       <span class="fl w38p">${chargeTerm.chargeSubject.name}</span> 
					       <span class="fr">
					       <input style="text-align:right;" type="text" name="${chargeTerm.id}" id="${chargeTerm.id}" value="${chargeTerm.fee}"/>
					       </span>
					   </li>
				 </c:forEach>
				</ul>
			</div>
			<h3 class="clearfix bor_top plr24">
            <input class="fr" type="button" name="button" value="保  存" onclick="feeTestform.submit()">
        </h3>	
		</div>			
   </form>
</body>
</html>