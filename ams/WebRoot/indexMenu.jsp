<%@page import="com.wonders.frame.ams.constants.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.wonders.frame.ams.dto.permission.UserDto"%>
<%@page import="com.wonders.frame.ams.utils.AppUtil"%>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>海口美兰机场财务核算系统</title>
<%@include file="./static/included.jsp"%>
    <script type="text/javascript">
        $(document).ready(function () {
//			$("menu").height($(window).height()-70);
			//此处使用css替换梁邦明 使用js控制 dom高度宽度的方法
			//修复 右边窗口 掉下去的问题
			//edit by wangsq 20160824
			//
//			$(".content").width($(window).width()-100);
//			$(".content").height($(window).height()-70);
        });
//            window.onresize = function(){
//			$("menu").height($(window).height()-68);
//			$(".content").width($(window).width()-100);
//			}
        var role = "<%=request.getSession().getAttribute("role")%>";
    </script>
<link rel="shortcut icon" href="./static/wonders/css/default/images/favicon.ico" type="image/x-icon" />

	<style>
		.view-content{
			position:absolute;
			top:70px;
			right:0;
			bottom:0;
			left:0;
			overflow:hidden;
			width:100%;
		}

		.view-content-left{
			background-color: #21649b;
			position:absolute;
			top:0;
			left:0;
			bottom:0;
			/*overflow-x: hidden;*/
			/*overflow-y: hidden;*/
			width:80px;
			min-width: 80px;
			float: left;
		}

		.view-content-main{
			position:absolute;
			top:0;
			right:0;
			bottom:0;
			left:80px;
			overflow:auto;
			float: left;

			/*width: 100%;*/
		}
	</style>

</head>

<body class="mw1002">
<header class="clearfix">
  <div class="logo fl"></div>
  <%	
  		UserDto user = (UserDto)request.getSession().getAttribute("login_user");
  		System.out.println(user.getRoleName());
		if(user == null){
			user = new UserDto();
		}
	%>
  <dl class="user fr">
		<a href="#">
			<dt class="fl mr8">
				<img src="./static/wonders/css/default/images/1.gif" />
			</dt>
			<dd class="fl">
				<h6 class="name"><font color="white"><%=user.getRoleName()%></font></h6>
				<h6>
					<span class="name"><%=user.getLoginName()%></span>  
					<span id="userLogout" class="name" style="margin-left: 10;">退出登录</span>
				</h6>
			</dd>
		</a>
  </dl>
  <!--  
  <div class="but fr">
    <input name="" type="button" class="mes">
    <input name="" type="button" class="setting">
  </div>
  -->
  <dl class="time fr clearfix">
			<dt class="fl mr8" id="clock"></dt>
			<dd class="fl">
				<h3 id="EEE"></h3>
				<h5 id="YYYYMMDD"></h5>
			</dd>
  </dl>
</header>

<!-- top 时间--> 
<script>
$(document).ready(function () {
		 function userLogout(){
			$.post(
					Global.root + "/permission/login.logout.do",
					function (){
						window.location.href = Global.root + "/login.jsp";
					}
				);
		}


		$("#userLogout").on("click",function (){
			userLogout();
		});
		
		/*
		var userOptMenu = $("#userOptMenu").jqxMenu({ 
			width: '120px', 
			height: '140px', 
			autoOpenPopup: false, 
			mode: 'popup',
			theme : "arctic"
		});
		
		$("#settingBar .setting").on('click', function (event) {
		        var scrollTop = $(window).scrollTop();
		        var scrollLeft = $(window).scrollLeft();
		        userOptMenu.jqxMenu('open', parseInt(event.clientX) + 5 + scrollLeft, parseInt(event.clientY) + 5 + scrollTop);
		        return false;
		}); */
		
		//时钟
		var clock = setInterval(function() {
			var d = new Date();
			var t = d.format("yyyy年MM月dd HH:mm:ss EEE").split(" ");
			$("#clock").html(t[1]);
			$("#YYYYMMDD").html(t[0] + "日");
			$("#EEE").html(t[2]);
		}, 1000);

		
		var showDetailTab = function (role){
        	if (role == 5){
           		$('#flightMateCharge').remove();
           	 	//$('#financialReport').remove();
           		$('#systemsConfigure').remove();
            }else if(role == 4){
            	$('#flightMateCharge').remove();
           	 	//$('#financialReport').remove();
           		$('#systemsConfigure').remove();
            }else if(role == 3){
            	$('#flightMateCharge').remove();
           	    //$('#financialReport').remove();
           		$('#systemsConfigure').remove();
            }else if(role == 2){
            	$('#flightMateCharge').remove();
           	 	//$('#financialReport').remove();
           		$('#systemsConfigure').remove();
            }
        };
        showDetailTab(role);
        function gotoPage(selection){
    		if(selection == 1){
    			$("#iframe").attr("src","./fqs/flightMateSearch/index");
    		}else if(selection == 2){
    			$("#iframe").attr("src","./fqs/flightMateCharge/index");
    		}else if(selection == 3){
    			if(role == 1){
    				$("#iframe").attr("src","./report/liquidationIncomeCalculation");
    			}else{
    				$("#iframe").attr("src","./report/flightDynamics");
    			}
    			
    		}else if(selection == 4){
    			$("#iframe").attr("src","./fqs/systemsConfigure/index");
    		}else if(selection == 5){
    			$("#iframe").attr("src","./jsp/basic/aircraftAirlineAlteration");
    		}
    		return false;
    	}
        $("#flightMateSearch").on("click",function(){
        	gotoPage(1);
        	$("#flightMateCharge").removeClass("selected");
        	$("#financialReport").removeClass("selected");
        	$("#systemsConfigure").removeClass("selected");
        	$("#flightMateSearch").addClass("selected");
        	$("#basicData").removeClass("selected");
        })
        
        $("#flightMateCharge").on("click",function(){
        	gotoPage(2);
        	$("#flightMateCharge").addClass("selected");
        	$("#financialReport").removeClass("selected");
        	$("#systemsConfigure").removeClass("selected");
        	$("#flightMateSearch").removeClass("selected");
        	$("#basicData").removeClass("selected");
        })
        
        $("#financialReport").on("click",function(){
        	gotoPage(3);
        	$("#flightMateCharge").removeClass("selected");
        	$("#financialReport").addClass("selected");
        	$("#systemsConfigure").removeClass("selected");
        	$("#flightMateSearch").removeClass("selected");
        	$("#basicData").removeClass("selected");
        })
        
        $("#systemsConfigure").on("click",function(){
        	gotoPage(4);
        	$("#flightMateCharge").removeClass("selected");
        	$("#financialReport").removeClass("selected");
        	$("#systemsConfigure").addClass("selected");
        	$("#flightMateSearch").removeClass("selected");
        	$("#basicData").removeClass("selected");
        })
        
         $("#basicData").on("click",function(){
        	gotoPage(5);
        	$("#flightMateCharge").removeClass("selected");
        	$("#financialReport").removeClass("selected");
        	$("#systemsConfigure").removeClass("selected");
        	$("#flightMateSearch").removeClass("selected");
        	$("#basicData").addClass("selected");
        })
        
})
</script>

<div class="view-content">
	<div class="view-content-left">
		<menu>
			<ul>
				<li style="display: block;" class="selected me2" id="flightMateSearch"><a href="#" >航班查询</a></li>
				<li style="display: block;" class="me3" id="flightMateCharge"><a href="#" >收费计算</a></li>
				<li style="display: block;" class="me4" id="financialReport"><a href="#" >统计报表</a></li>
				<li style="display: block;" class="me6" id="basicData"><a href="#" >基础数据</a></li>
				<li style="display: block;" class="me5" id="systemsConfigure"><a href="#" >系统设置</a></li>
			</ul>
		</menu>
	</div>
    <div class="view-content-main">
    	<iframe width="100%" height="100%"  id="iframe" src="./fqs/flightMateSearch/index"></iframe>
    </div>
</div>
</body>
</html>
