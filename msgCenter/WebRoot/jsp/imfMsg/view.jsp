<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8" />
<title>消息查看</title>
<link rel="stylesheet" href="../css/formalize.css" />
<link rel="stylesheet" href="../css/page.css" />
<link rel="stylesheet" href="../css/imgs.css" />
<link rel="stylesheet" href="../css/reset.css" />
<link type="text/css" href="../js/datepicker/css/flick/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<!--[if IE 6.0]>
    <script src="js/iepng.js" type="text/javascript"></script>
    <script type="text/javascript">
         EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
    </script>
<![endif]-->
<script src="../js/html5.js"></script>
<script src="../js/jquery-1.7.1.js"></script>
<script src="../js/jquery.formalize.js"></script>
<script src="../js/jquery-ui-1.8.18.custom.min.js"></script>
<script src="../js/datepicker/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="../ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<%String basePath = request.getContextPath(); %>
<style type="text/css">
	.must{
		color:red;
	}
</style>


<script type="text/javascript">
$(function(){
	$(".odd tr:odd").css("background","#fafafa");		
});



</script>





</head>

<body>
<div class="main">
	<div class="ctrl clearfix">
		<div class="posi fl">
			<ul>
				<li class="fin">消息中心&gt;&gt;消息列表&gt;&gt;查看消息</li>
			</ul>
		</div>
	</div>
<div class="mb10">
	<table width="100%" class="table_1">
		<thead>
			<th colspan="8" class="t_r">
				<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
			</th>
		</thead>
		<tbody id="formBody">
			<tr>
				<td class="t_r lableTd" width="5%">消息编号</td>
				<td width="20%">
					<s:property value="#request.msg.id"/>
				</td>
				<td class="t_r lableTd" width="5%">生成时间</td>
				<td width="20%">
					<s:date name="#request.msg.createTime" format="yyyy-MM-dd HH:mm:ss"/>					
				</td>	
				<td class="t_r lableTd" width="5%">服务类型</td>
				<td width="20%">
					<s:property value="#request.msg.msgType"/>
				</td>
				<td class="t_r lableTd" width="5%">接收系统</td>
				<td width="20%">
					<s:property value="#request.msg.receiver"/>
				</td>							
			</tr>
			<tr>
				<td class="t_r lableTd" width="5%">消息类型</td>
				<td width="20%">
					<s:if test="#request.msg.status==0">待解析入库</s:if>
					<s:elseif test="#request.msg.status==1">已解析入库</s:elseif>
					<s:elseif test="#request.msg.status==8">入库成功(告警异常)</s:elseif>		
					<s:elseif test="#request.msg.status==9">解析入库失败</s:elseif>					
					<s:elseif test="#request.msg.status==2">订阅状态描述</s:elseif>
					<s:elseif test="#request.msg.status==3">系统异常描述</s:elseif>
				</td>			
				<td class="t_r lableTd" width="5%">解析入库时间</td>
				<td width="20%">
					<s:date name="#request.msg.operateTime" format="yyyy-MM-dd HH:mm:ss"/>	
				</td>
				<td class="t_r lableTd" width="5%">ServiceType</td>
				<td width="20%">
					<s:property value="#request.msg.serviceType"/>
				</td>
				<td class="t_r lableTd" width="5%">MessageSequenceID</td>
				<td width="20%">
					<s:property value="#request.msg.messageSequenceId"/>					
				</td>				
			</tr>	
			<tr>	
				<td class="t_r lableTd" width="5%">MessageType</td>
				<td width="20%">
					<s:property value="#request.msg.messageType"/>
				</td>
				<td class="t_r lableTd" width="5%">OperationMode</td>
				<td width="20%">
					<s:property value="#request.msg.operationMode"/>					
				</td>					
				<td class="t_r lableTd" width="5%">FlightIdentity</td>
				<td width="20%">
					<s:property value="#request.msg.flightIdentity"/>
				</td>
				<td class="t_r lableTd" width="5%">FlightDirection</td>
				<td width="20%">
					<s:property value="#request.msg.flightDirection"/>
				</td>				
			</tr>	
			<tr>	
				<td class="t_r lableTd" width="5%">FlightScheduledDate</td>
				<td width="20%">
					<s:property value="#request.msg.flightScheduledDate"/>
				</td>
				<td class="t_r lableTd" colspan="6"></td>
					
			</tr>								
			<tr height="300px;">
				<td class="t_r lableTd"><strong>消息内容</strong></td>
				<td colspan="7">
					<textarea rows="25" cols="20"><s:property value="#request.msg.msgContent"/></textarea>					
				</td>
				
			</tr>			
		</tbody>
		<tr class="tfoot">
			<td colspan="4" class="t_r">
				<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
			</td>
		</tr>
	</table>
</div>
<!--Table End--></div>
</body>
</html>
