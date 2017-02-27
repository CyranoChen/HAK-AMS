<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8" />
<title>消息中心登录页面</title>
<link rel="stylesheet" href="../css/formalize.css" />
<link rel="stylesheet" href="../css/page.css" />
<link rel="stylesheet" href="../css/imgs.css" />
<link rel="stylesheet" href="../css/reset.css" />

<style type="text/css">
	body{ text-align:center}
	.div{ margin:a; width:300px; border:1px solid #F00} 
</style>


<script type="text/javascript">
</script>
</head>

<body>
<div style="background:#f2f2f2;width:100%;height:100%;align:center;">
<div  style="margin:0 auto;border:0;padding:3px;width:300px;height:100px">
</div>
	<div style="background:#f9f9f9;margin:0 auto;border:1px solid #999;padding:3px;width:300px;vertical-align:middle">
               <form id="loginForm" action="check.action" method="post">
                   <table width="100%" height="200" border="0">
                       <tr>
                         <td colspan="2" style="text-align:center;font-size:20px;font-weight:bold"><label>登录信息</label>                         
                       </tr>
                       <tr>
                       <td class="lableTd t_r">用户名　</td>
                       <td><input type="text" id="userName"  name="userName" class="input_large" maxlength="30"></td>
                       </tr>
                       <tr>
                       <td class="lableTd t_r">密　码　</td>
                       <td><input type="password" id="password"  name="password" class="input_large" maxlength="30"></td>
                       </tr>
                       <tr>
                         <td colspan="2" style="text-align:center">
                         <input type="submit" value="登 录">
                         </td>
                       </tr>
                   </table>
               </form>

	</div>
<!--Table End--></div>
</body>
</html>
