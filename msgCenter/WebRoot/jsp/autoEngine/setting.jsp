<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache,must-revalidate"> 
<title>规则设置</title>
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


<script type="text/javascript"><!--
$(function(){
	$(".odd tr:odd").css("background","#fafafa");	
	$('#stepNames span').click(function(){
			var id=$(this).attr("id");
			$(this).remove();  			
			
			var steps=$("#nextStep").val();
			var arrStep=steps.split(",");
			arrStep.splice($.inArray(id,arrStep),1);			
			$("#nextStep").val(arrStep.join(","));
	});
	
});


function addStep(){
	var stepId=$("#choseStep").val();
	if(stepId==""){return;}
	var stepText=$("#choseStep option:selected").text();
	
	var steps=$("#nextStep").val();
	if(steps!=""){
		var arrStep=steps.split(",");
		var isExist=false;
		for(var i=0;i<arrStep.length;i++){
			if(arrStep[i]==stepId){
				isExist=true;
				break;
			}
		}	
	}
	
	if(!isExist){		
		var lobj = $("<span id='"+stepId+"' title='点击删除'>"+stepText+"X</span>"); 
		lobj.click(function(){
			var id=$(this).attr("id");
			$(this).remove();  			
			var steps=$("#nextStep").val();
			var arrStep=steps.split(",");
			arrStep.splice($.inArray(id,arrStep),1);			
			$("#nextStep").val(arrStep.join(","));
		});
			
		lobj.appendTo($('#stepNames'));  
		if(steps!=""){
			arrStep[arrStep.length]=stepId;
			$("#nextStep").val(arrStep.join(","));
		}else{
			$("#nextStep").val(stepId);
		}
		
	}else{
		alert("所选服务已经存在");
	}
}

function submitForm(){
		var id = $("#id").val();
		var autoWay = $("#autoWay").val();
		if(autoWay=="loop"){
			var time = $("#time").val();
			if(time==""){
				$("#time").focus();
				alert("初始时间不能为空");			
				return;
			}
						
			var interval = $("#interval").val();
			if(interval==""){
				$("#interval").focus();
				alert("轮询间隔不能为空");			
				return;
			}

			var loopRemark = $("#loopRemark").val();
			/*
			if(loopRemark==""){
				$("#loopRemark").focus();
				alert("备注不能为空");
				return;
			}
			*/
		}else{
			var nextStep = $("#nextStep").val();
					
			var triggerRemark = $("#triggerRemark").val();
			/*
			if(triggerRemark==""){
				$("#triggerRemark").focus();
				alert("备注不能为空");
				return;
			}
			*/

		}
				
		$.ajax({
		type : 'post',
		url : 'updateRuleConfig.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			id:id,
			time:time,
			interval:interval,
			loopRemark:loopRemark,
			nextStep:nextStep,
			triggerRemark:triggerRemark			
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){
				alert("操作成功");
				window.returnValue="success";
				window.close();
			}else{
				alert("操作失败");
			}
		}
	});
		
}
--></script>





</head>

<body>
<div class="main">
	<div class="ctrl clearfix">
		<div class="posi fl">
			<ul>
				<li class="fin">f自动化引擎&gt;&gt;设置<s:if test="#request.apiInfo.params['autoWay']=='loop'">轮询模式</s:if><s:else>触发模式</s:else></>规则参数</li>
			</ul>
		</div>
	</div>
<div class="mb10">
	<table width="100%" class="table_1">
	<input type="hidden" id="id" name="id"  value="<s:property value='#request.apiInfo.id'/>"/>
	<input type="hidden" id="autoWay" name="autoWay"  value="<s:property value="#request.apiInfo.params['autoWay']"/>"/>	
		<tbody id="formBody">
			<s:if test="#request.apiInfo.params['autoWay']=='loop'">
			<tr>
				<td class="t_r lableTd" width="16%">初始时间</td>
				<td width="34%">
					<input type="text" id="time" name="time" class="input_large" value="<s:property value='#request.scheduleConfig.time'/>"/>格式：23:30 				
				</td>	
				<td class="t_r lableTd" width="16%">轮询间隔</td>
				<td width="34%">
					<input type="text" id="interval" name="interval" class="input_large" value="<s:property value='#request.scheduleConfig.interval'/>"/>格式：24H/15M/10S 	
				</td>											
			</tr>

			<tr>
				<td class="t_r lableTd" width="16%">备注</td>
				<td colspan="3" width="84%">
					<textarea id="loopRemark" name="loopRemark" rows="10"><s:property value="#request.apiInfo.params['loopRemark']"/></textarea> 	
				</td>											
			</tr>	
			</s:if>
			<s:else>
			<tr>
				<td class="t_c lableTd"  width="16%">触发服务</td>
				<td colspan="2" width="50%">
					<select id="choseStep" class="input_large">					
						<option value="">---请选择---</option>								
						<s:iterator value="#request.apiIds" id="api" status="st">
							<option value="<s:property value="#api"/>" ><s:property value="#request.hmApiName[#api]"/></option>
						</s:iterator>								
					</select> 	
					<input type="button" onclick="addStep()" value="添加"/>
				</td>
				<td class="t_c lableTd" width="34%">备注</td>
				
			</tr>	
			<tr>	
				<td colspan="3" width="66%">
					<div id="stepNames">
					<s:iterator value="#request.apiInfo.params['nextStep'].split(',')" id="step" status="st">
					<span id='<s:property value="#step"/>' title="点击删除"><s:property value="#request.hmApiName[#step]"/>X</span>
					</s:iterator>
					</div>
					<input type="hidden" id="nextStep" name="nextStep" class="input_large" value="<s:property value='#request.apiInfo.params["nextStep"]'/>"/>
				</td>	
				<td width="34%">
					<textarea id="triggerRemark" name="triggerRemark" rows="11" ><s:property value="#request.apiInfo.params['triggerRemark']"/></textarea> 	
				</td>													
			</tr>
			</s:else>		
		</tbody>	
		<tr class="tfoot">
			<td colspan="4" class="t_c">
				<input type="button" value="确定" onclick="submitForm();"/> &nbsp;
				<input type="button" value="取消" onclick="window.close();"/> &nbsp;				
			</td>
		</tr>
	</table>
</div>
<!--Table End--></div>
</body>
</html>
