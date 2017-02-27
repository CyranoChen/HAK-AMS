<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8" />
<title>消息中心首页</title>
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
<style type="text/css">
	.must{
		color:red;
	}
</style>


<script type="text/javascript">
$(function(){
	$(".odd tr:odd").css("background","#fafafa");		
});

function updateAllMissedKeyValue(){
	$.ajax({
		type : 'post',
		url : 'updateAllMissedKeyValue.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{			
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert("操作成功");				
			}else{
				alert("操作失败");
			}
		}
	});
}
function setSyncSwitch(type,tag){
	$.ajax({
		type : 'post',
		url : 'setSyncSwitch.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			type:type,
			tag:tag,
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert("操作成功");
				window.location.href="/msgCenter/imfMsg/showIndex.action";
			}else{
				alert("操作失败");
			}
		}
	});
}

function startListener(msgType){
	$.ajax({
		type : 'post',
		url : 'startIMFListener.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			msgType:msgType
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert("操作成功");
				window.location.href="/msgCenter/imfMsg/refreshStatus.action";
			}else{
				alert("操作失败");
			}
		}
	});
}

function stopListener(msgType){
	$.ajax({
		type : 'post',
		url : 'stopIMFListener.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			msgType:msgType
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert("操作成功");
				window.location.href="/msgCenter/imfMsg/refreshStatus.action";
			}else{
				alert("操作失败");
			}
		}
	});
}
function restartListener(msgType){
	$.ajax({
		type : 'post',
		url : 'restartIMFListener.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			msgType:msgType
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert("操作成功");
				window.location.href="/msgCenter/imfMsg/refreshStatus.action";
			}else{
				alert("操作失败");
			}
		}
	});
}

function resendAll(msgType,receiver){
	$.ajax({
		type : 'post',
		url : 'sendAll.action',
		dataType:'json',
		cache : false,
		data:{
			msgType:msgType,
			receiver:receiver,
			status:9
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert(obj.msg+"条记录操作成功");
				window.location.href="/msgCenter/imfMsg/refreshStatus.action";
			}else{
				alert("操作失败");
			}
		}
	});
}

function sendAll(msgType,receiver){
	$.ajax({
		type : 'post',
		url : 'sendAll.action',
		dataType:'json',
		cache : false,
		data:{
			msgType:msgType,
			receiver:receiver,
			status:0
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert(obj.msg+"条记录操作成功");
				window.location.href="/msgCenter/imfMsg/refreshStatus.action";
			}else{
				alert("操作失败");
			}
		}
	});
}

function refreshStatus(){
	window.location.href="/msgCenter/imfMsg/refreshStatus.action";
}

function showList(){
	window.location.href="/msgCenter/imfMsg/showList.action";
}

function showAutoEngine(){
	window.location.href="/msgCenter/autoEngine/showIndex.action";
}


</script>





</head>

<body>
<div class="main">
	<div class="ctrl clearfix">
		<div class="posi fl">
			<ul>
				<li class="fin">消息中心&gt;&gt;控制台</li>
			</ul>
		</div>
	</div>
<div class="mb10">
	<table width="100%" class="table_1">
		<thead>
			<th colspan="3" class="t_r">
				<input type="button" value="自动化引擎" onclick="showAutoEngine()"/> &nbsp;
				<input type="button" value="消息列表" onclick="showList()"/> &nbsp;
				<input type="button" value="刷新状态" onclick="refreshStatus()"/> &nbsp;
				<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
			</th>
		</thead>
		<tbody id="formBody">
			<tr>
				<td class="t_r lableTd" width="5%" rowspan="1">同步解析</td>
				<td width="10%" style="color:red;">状态：
							<s:if test="#request.syncStatus==0">已关闭</s:if>
							<s:elseif test="#request.syncStatus==1">已开启</s:elseif>							
				<td width="85%">												
					<input type="button" value="开启同步" onclick="setSyncSwitch('sync','1');"/> &nbsp;	
					<input type="button" value="关闭同步" onclick="setSyncSwitch('sync','0');"/> &nbsp;	
				</td>
															
			</tr>
			<tr>
				<td class="t_r lableTd" width="5%" rowspan="1"></td>
				<td width="10%" style="color:red;">注意：只在未知异常导致同步解析失效时使用					
				<td width="85%">												
					<input type="button" value="开启批量解析开关" onclick="setSyncSwitch('parse','1');"/> &nbsp;
					<input type="button" value="开启关键值更新开关" onclick="setSyncSwitch('key','1');"/> &nbsp;	
					<input type="button" value="关键值补更" onclick="updateAllMissedKeyValue();"/> &nbsp;
				</td>
															
			</tr>
			<!-- tr>				
				<td width="10%">手动操作		</td>					
				<td width="85%">																	

				</td>
							
			</tr-->
						
			<tr>
				<td class="t_r lableTd" width="5%">FSS服务</td>
				<td width="10%" style="color:red;">状态：<s:property value='#request.FssStatus'/></td>					
				<td width="85%">												
					<input type="button" value="订阅" onclick="startListener('FSS');"/> &nbsp;	
					<input type="button" value="取消订阅" onclick="stopListener('FSS');"/> &nbsp;
					<input type="button" value="重新订阅" onclick="restartListener('FSS');"/> &nbsp;		
					<input type="button" value="解析数据至AMS" onclick="sendAll('FSS','AMS');"/> &nbsp;					
					<input type="button" value="重新解析数据至AMS" onclick="resendAll('FSS','AMS');"/> &nbsp;													
		
				</td>
							
			</tr>
			<tr>
				<td class="t_r lableTd" width="5%">BSS服务</td>
				<td width="10%" style="color:red;">状态：<s:property value='#request.BssStatus'/></td>					
				<td width="85%">
					<input type="button" value="订阅" onclick="startListener('BSS');"/> &nbsp;	
					<input type="button" value="取消订阅" onclick="stopListener('BSS');"/> &nbsp;
					<input type="button" value="重新订阅" onclick="restartListener('BSS');"/> &nbsp;		
					<input type="button" value="解析数据至AMS" onclick="sendAll('BSS','AMS');"/> &nbsp;					
					<input type="button" value="重新解析数据至AMS" onclick="resendAll('BSS','AMS');"/> &nbsp;																	
				</td>	
							
			</tr>							
		</tbody>
		<tr class="tfoot">
			<td colspan="3" class="t_r">
				<input type="button" value="自动化引擎" onclick="showAutoEngine()"/> &nbsp;
				<input type="button" value="消息列表" onclick="showList()"/> &nbsp;
				<input type="button" value="刷新状态" onclick="refreshStatus()"/> &nbsp;
				<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
			</td>
		</tr>
	</table>
</div>
<!--Table End--></div>
</body>
</html>
