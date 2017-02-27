<%@ page contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.sendRedirect("imfMsg/showIndex.action");	

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>欢迎</title>
	</head>
	<body>
		<div align="center">
			<H1 align="center">Welcome !!!</H1>
		</div>
	</body>
</html>