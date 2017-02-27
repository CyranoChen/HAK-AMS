<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache,must-revalidate"> 
<title>设置初始化参数</title>
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

function submitForm(){
		var id = $("#id").val();
		var initParams = $("#initParams").val();
		$.ajax({
		type : 'post',
		url : 'changeInitParams.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			id:id,
			initParams:initParams
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
</script>





</head>

<body>
<div class="main">
	<div class="ctrl clearfix">
		<div class="posi fl">
			<ul>
				<li class="fin">自动化引擎&gt;&gt;设置初始化参数</li>
			</ul>
		</div>
	</div>
<div class="mb10">
	<table width="100%" class="table_1">
	<input type="hidden" id="id" name="id"  value="<s:property value='#request.apiInfo.id'/>"/>
		<tbody id="formBody">
			<tr>
				<td class="t_r lableTd" width="20%">初始化参数<br>(json格式)<br>eg:{"param1":"111",<br>"param2":"222"}</td>
				<td width="80%">
					<textarea id="initParams" name="initParams" rows="10"><s:property value="#request.apiInfo.params['initParams']"/></textarea> 				
				</td>	
			</tr>
		</tbody>
		<tr class="tfoot">
			<td colspan="2" class="t_c">
				<input type="button" value="确定" onclick="submitForm();"/> &nbsp;
				<input type="button" value="取消" onclick="window.close();"/> &nbsp;				
			</td>
		</tr>
	</table>
</div>
<!--Table End--></div>
</body>
</html>
