<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8" />
<title>自动化引擎控制台</title>
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
<!--<script src="../js/html5.js"></script>-->
<script src="../js/jquery-1.7.1.js"></script>
<script src="../js/jquery.formalize.js"></script>
<script src="../js/jquery-ui-1.8.18.custom.min.js"></script>
<!--<script src="../js/datepicker/js/jquery-ui-1.8.18.custom.min.js"></script>-->
<!--<script type="text/javascript" src="../ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>-->
<style type="text/css">
	.must{
		color:red;
	}
</style>


<script type="text/javascript">
$(function(){
	$(".odd tr:odd").css("background","#fafafa");		
});

function mamualStart(id){
	$.ajax({
		type : 'post',
		url : 'runService.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			id:id
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){				
				if(obj.alertMsg!=""){
					alert("操作完成"+obj.alertMsg);
				}else{
					alert("操作成功");	
				}			
			}else{
				if(obj.alertMsg!=""){
					alert("操作失败"+obj.alertMsg);
				}else{
					alert("操作失败");	
				}
			}
		}
	});
}

function changeAutoSwitch(id){
	$.ajax({
		type : 'post',
		url : 'changeAutoSwitch.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			id:id
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert("操作成功");
				window.location.href="/msgCenter/autoEngine/showIndex.action";
			}else{
				alert("操作失败");
			}
		}
	});
}

function changeAutoWay(id){
	$.ajax({
		type : 'post',
		url : 'changeAutoWay.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			id:id
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert("操作成功");
				window.location.href="/msgCenter/autoEngine/showIndex.action";
			}else{
				alert("操作失败,"+obj.alertMsg);
			}
		}
	});
}

function cleanCallApiLock(){
	$.ajax({
		type : 'post',
		url : 'cleanCallApiLock.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{			
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){			
			alert("操作成功");				
		}
	});
}

function openModalDialog(url,height,width){
	if(navigator.userAgent.indexOf("Chrome") >0 ){
	var winOption = "height="+height+",width="+width+",top=0,left=0,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,fullscreen=0";
	    	return  window.open(url,window, winOption);
	}
	else{
	    return window.showModalDialog(url, window, 'dialogHeight: ' + height + 'px; dialogWidth: ' + width + 'px;edge: Raised; center: Yes; help: Yes;scroll:no; resizable: no; status: no;resizable:yes');
	}
}

function setting(id){

	//var r=window.showModalDialog("setting.action?id="+id,"","dialogWidth=500px;dialogHeight=275px;scroll=no;status=no");
	var r=openModalDialog("setting.action?id="+id,"275px","500px");

	if(r=="success"){window.location.reload();}
}

function editInitParams(id){
	var r=openModalDialog("editInitParams.action?id="+id,"225px","500px");
	//var r=window.showModalDialog("editInitParams.action?id="+id,"","dialogWidth=500px;dialogHeight=225px;scroll=no;status=no");
	if(r=="success"){window.location.reload();}
}

function showMsgCenter(){
	window.location.href="/msgCenter/imfMsg/showIndex.action";
}

function showLogList(){
	window.location.href="/msgCenter/autoEngine/showLogList.action";
}
</script>





</head>

<body>
<div class="main">
	<div class="ctrl clearfix">
		<div class="posi fl">
			<ul>
				<li class="fin">自动化引擎&gt;&gt;控制台</li>
			</ul>
		</div>
	</div>
<div class="mb10">
	<table width="100%" class="table_1">
			<thead>
				<th colspan="4">&nbsp;&nbsp;服务列表</th>
				<th colspan="4" class="t_r"> 
					<input type="button" value="消息中心" onclick="showMsgCenter()"/> &nbsp;
					<input type="button" value="清空状态锁" onclick="cleanCallApiLock()"/> &nbsp;
					<input type="button" value="接口调用日志" onclick="showLogList()"/> &nbsp;					
					<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
				
				</th>
			</thead>
		<tbody id="formBody">
			<tr class="tit">
				<td class="t_r" style="white-space:nowrap;">序号</td>
				<td class="t_r" style="white-space:nowrap;">服务名</td>
				<td class="t_r" style="white-space:nowrap;">状态</td>	
				<td class="t_r" style="white-space:nowrap;">规则</td>	
				<td class="t_r" style="white-space:nowrap;">默认参数</td>				
				<td class="t_r" style="white-space:nowrap;">手动调用</td>
				<td class="t_r" style="white-space:nowrap;" colspan="2" >自动调用</td>	
			</tr>
			<s:iterator value="#request.apiInfos" id="api" status="st">
			<tr>
				<td width="5%" class="t_c" style="white-space: nowrap;"><s:property value="#st.index+1"/></td>
				<td width="25%" class="t_c" style="align:left;white-space: nowrap;"><s:property value="#api.dataName"/></td>
				<td width="13%" class="t_c" style="white-space: nowrap;">
					<s:if test="#api.params['autoSwitch']=='off'">已关闭自动调用<br></s:if>	
					<s:elseif test="#api.params['autoSwitch']=='on'">已开启自动调用<br></s:elseif>
					<s:if test="#api.params['autoWay']=='loop'">轮询模式</s:if>	
					<s:elseif test="#api.params['autoWay']=='trigger'">触发模式</s:elseif>	
				</td>	
				<td width="5%" class="t_c" style="white-space: nowrap;">

						<input type="button" value="设置" onclick="setting('<s:property value="#api.id"/>');"/> &nbsp;

                </td>						
				<td width="35%" class="t_c" style="white-space: nowrap;" title="点击参数可编辑" onclick="editInitParams('<s:property value="#api.id"/>');"><s:property value="#api.params['initParams']"/></td>	
						
				<td width="5%" class="t_c" style="white-space: nowrap;">
					<input type="button" value="执行"  onclick="mamualStart('<s:property value="#api.id"/>');"/>
				</td>
				
				<td width="7%" class="t_c" style="white-space: nowrap;">				
					<input type="button" onclick="changeAutoWay('<s:property value="#api.id"/>');"
						value="<s:if test="#api.params['autoWay']=='loop'">切换至触发模式</s:if><s:elseif test="#api.params['autoWay']=='trigger'">切换至轮询模式</s:elseif>" />																					
				
                </td>    
                
				<td width="5%" class="t_c" style="white-space: nowrap;" >
					<input type="button" onclick="changeAutoSwitch('<s:property value="#api.id"/>');"
						value="<s:if test="#api.params['autoSwitch']=='off'">开启</s:if><s:elseif test="#api.params['autoSwitch']=='on'">停止</s:elseif>" />																					
                </td>                            	
                
			</tr>
			</s:iterator>						
		</tbody>
		<tr class="tfoot">
			<td colspan="8" class="t_r">
				<input type="button" value="消息中心" onclick="showMsgCenter()"/> &nbsp;				
				<input type="button" value="清空状态锁" onclick="cleanCallApiLock()"/> &nbsp;
				<input type="button" value="接口调用日志" onclick="showLogList()"/> &nbsp;					
				<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
			</td>
		</tr>
	</table>
</div>
<!--Table End--></div>
</body>
</html>
