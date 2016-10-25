<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset=utf-8 />
<title>登录</title>
<link rel="shortcut icon" href="./static/wonders/css/default/images/favicon.ico" type="image/x-icon" />
	<%@ include file="static/included.jsp"%>
	
	<script type="text/javascript">
		if (window != top) {
			top.location.href = location.href; 
		}
		$(function (){
			$("#password").on("keypress",function (event){
				if(event.keyCode == 13){
					$("#submit:enabled").click();
				}
			});


			$("#submit").click(function (){
				var loginName = $("#loginName").val();
				var password = $("#password").val();
				
				if($.trim(loginName) == "" || $.trim(password) == ""){
					alert("登录名和密码必须输入!");
					return ;
				}
				var that = $(this);

				that.attr("disabled","disabled").val("登录中,请稍等");
				$.post(
					"./permission/login.check.do",
					{
						loginName : loginName,
						password : password
					},
					function (rtn){
						if(rtn){
							/* $.post(
									"./srm/login.tourl.do",
									{ tourl : $("#tourl").val() },
									function (url){
//										alert(url);
										if(url){
											window.location.href = url;
										}else{
											alert("跳转路径获取异常!");
										}
									}
							) */
							window.location.href = "indexMenu.jsp";
						}else{
							$("#password").val("");
							alert("登录名或密码错误!");
						}
						that.removeAttr("disabled").val("登 录");
					}
				)
			});
		});
	</script>
</head>

<body class="login" style="height: auto;width: auto;">
	<div class="body clearfix">
    	<div class="logo fl"></div>
        <div class="form_bg fr">
        	<hgroup class="clearfix">
            	<h2 class="fl">用户登录</h2>
                <h4 class="fl">|</h4>
                <h3 class="fl">User Login</h3>
            </hgroup>
            <div class="con">
            	<form class="clearfix">
                	<input type="text" placeholder="用户名" id="loginName"/>
                    <input type="password" placeholder="密码" id="password"/>
					<%
						String tourl = request.getParameter("tourl");
					%>

					<input type="hidden" id="tourl" value='<%= tourl == null ? "" : tourl%>'>
                    <div class="mb10 clearfix">
<!--                     	<input name="" class="mr8" type="checkbox" value="">忘记密码 -->
                    </div>
                    <input id="submit" type="button" value="登 录">
                </form>
            </div>
        </div>
    </div>


<%--<button id="cl">CLICK</button>--%>



</body>
</html>
