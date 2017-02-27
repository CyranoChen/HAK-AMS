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
				<li class="fin">接口调用&gt;&gt;日志列表&gt;&gt;查看日志</li>
			</ul>
		</div>
	</div>
<div class="mb10">
	<table width="100%" class="table_1">
		<thead>
			<th colspan="6" class="t_r">
				<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
			</th>
		</thead>
		<tbody id="formBody">
			<tr>
				<td class="t_r lableTd" width="5%">调用时间</td>
				<td width="20%">
					<s:date name="#request.log.callTime" format="yyyy-MM-dd HH:mm:ss"/>					
				</td>	
				<td class="t_r lableTd" width="5%">结束时间</td>
				<td width="20%">
					<s:date name="#request.log.finishTime" format="yyyy-MM-dd HH:mm:ss"/>					
				</td>				
				<td class="t_r lableTd" width="5%">调用者</td>
				<td width="20%">
					<s:property value="#request.log.caller"/>
				</td>
										
			</tr>
			<tr>
				<td class="t_r lableTd" width="5%">调用方式</td>
				<td width="20%">
                    <s:if test="#request.log.callWay=='manual'">手动调用</s:if>
					<s:elseif test="#request.log.callWay=='loop'">自动轮询调用</s:elseif>     
					<s:elseif test="#request.log.callWay=='trigger'">自动触发调用</s:elseif>    				
				</td>		
				<td class="t_r lableTd" width="5%">调用方法</td>
				<td width="20%">
	                <s:if test="#request.log.callMethod=='get'">查询</s:if>
					<s:elseif test="#request.log.callMethod=='sync'">同步</s:elseif>
					<s:elseif test="#request.log.callMethod=='set'">更新</s:elseif>
					<s:elseif test="#request.log.callMethod=='autoEngine'">自动化引擎</s:elseif>								
				</td>	
				<td class="t_r lableTd" width="5%">接口名称</td>
				<td width="20%">
					<s:property value="#request.apiName"/>接口
				</td>					
			</tr>			
			<tr>
				<td class="t_r lableTd" width="5%">调用结果</td>
				<td width="20%">
					<s:if test="#request.log.callResult=='success'">成功</s:if>
					<s:elseif test="#request.log.callResult=='failure'">失败</s:elseif>					
				</td>
				<td colspan="4"/>											
			</tr>	
										
			<tr height="150px;">
				<td class="t_r lableTd"><strong>接口返回信息</strong></td>
				<td colspan="5">
					<textarea rows="12" cols="20"  readonly="readonly"><s:property value="#request.log.backInfo"/></textarea>					
				</td>
				
			</tr>	
			<tr height="150px;">
				<td class="t_r lableTd"><strong>接口异常信息</strong></td>
				<td colspan="5">
					<textarea rows="12" cols="20"  readonly="readonly"><s:property value="#request.log.exception"/></textarea>					
				</td>
				
			</tr>
		</tbody>
		<tr class="tfoot">
			<td colspan="6" class="t_r">
				<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
			</td>
		</tr>
	</table>
</div>
<!--Table End--></div>
</body>
</html>
