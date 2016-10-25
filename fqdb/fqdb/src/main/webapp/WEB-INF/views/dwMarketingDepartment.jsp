<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
<title>Untitled Document</title>
<link rel="stylesheet" href="${ctx}/css/formalize.css">
<link rel="stylesheet" href="${ctx}/css/reset.css">
<link rel="stylesheet" href="${ctx}/css/page.css">
<link href="${ctx}/css/imgs.css" rel="stylesheet" type="text/css">
<link type="text/css"
	href="${ctx}/datepicker/css/flick/jquery-ui-1.8.18.custom.css"
	rel="stylesheet" />

<script src="${ctx}/js/html5.js"></script>
<script src="${ctx}/js/jquery-1.7.1.min.js"></script>
<script src="${ctx}/datepicker/js/jquery-ui-1.10.3.js"></script>
<script src="${ctx}/datepicker/js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript"
	src="${ctx}/ui/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var $tbInfo = $(".search_1 input:text");
		$tbInfo.each(function() {
			$(this).focus(function() {
				$(this).attr("placeholder", "");
			});
		});

		var $tblAlterRow = $(".table_1 tbody tr:odd");
		if ($tblAlterRow.length > 0)
			$tblAlterRow.css("background", "#f7f9fc");
	});
	$(function() {
		$("input[name=startTime]").datepicker({
			inline : true,
			changeYear : true,
			changeMonth : true,
			showOtherMonths : true,
			selectOtherMonths : true
		});
		$("input[name=endTime]").datepicker({
			inline : true,
			changeYear : true,
			changeMonth : true,
			showOtherMonths : true,
			selectOtherMonths : true
		});
	});



	function exportExcel(DivID){

		//先声明Excel插件、Excel工作簿等对像
		var jXls, myWorkbook, myWorksheet;

	//	try {
		//插件初始化失败时作出提示
		//alert(new ActiveXObject('Excel.Application'));
		jXls = new ActiveXObject('Excel.Application');
		//}catch (e) {
	//	alert("无法启动Excel!\n\n如果您确信您的电脑中已经安装了Excel，"+"那么请调整IE的安全级别。\n\n具体操作：\n\n"+"工具 → Internet选项 → 安全 → 自定义级别 → 对没有标记为安全的ActiveX进行初始化和脚本运行 → 启用");
	 //  return false;
	//	}

		//不显示警告 
		jXls.DisplayAlerts = false;

		//创建AX对象excel
		myWorkbook = jXls.Workbooks.Add();
		//myWorkbook.Worksheets(3).Delete();//删除第3个标签页(可不做)
		//myWorkbook.Worksheets(2).Delete();//删除第2个标签页(可不做)

		//获取DOM对像
		var curTb = document.getElementById(DivID);

		//获取当前活动的工作薄(即第一个)
		myWorksheet = myWorkbook.ActiveSheet; 

		//设置工作薄名称
		myWorksheet.name="NP统计";

		//获取BODY文本范围
		var sel = document.body.createTextRange();

		//将文本范围移动至DIV处
		sel.moveToElementText(curTb);

		//选中Range
		sel.select();

		//清空剪贴板
		window.clipboardData.setData('text','');

		//将文本范围的内容拷贝至剪贴板
		sel.execCommand("Copy");

		//将内容粘贴至工作簿
		myWorksheet.Paste();

		//打开工作簿
		jXls.Visible = true;

		//清空剪贴板
		window.clipboardData.setData('text','');
		jXls = null;//释放对像
		myWorkbook = null;//释放对像
		myWorksheet = null;//释放对像
		}

		</script>
	
	
	
	
	
	
</script>
</head>
<body>
	<!--search-->
	<div class="search_1">
		<form action="dwMarketingDepartment" method="post" id="form">
			<table width="100%" class="table_1">
				<tbody>
					<tr>
						<td class="t_r lableTd">开始日期</td>
						<td class="t_l"><input type="text" name="startTime" readonly="readonly" value="${startTime}" /></td>
						<td class="t_r lableTd">结束日期</td>
						<td class="t_l"><input type="text" name="endTime" readonly="readonly" value="${endTime}" /></td>
						<td class="t_r">
							<input type="submit" value="查  询"/>&nbsp; 
							<input type="reset" value="重  置" />&nbsp;
							<input type="button" onClick="exportExcel('tabEnterNpDiv')" value="导出表格">
							
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<!--search end-->
	<div>
		<h2 align="center">市场部</h2>
	</div>
	<div id="tabEnterNpDiv">
		<table id="tabEnterNpDiv1"  class="table_1" width="1000%">
			<thead>
				<tr>
					<td style="width: 93px; ">序号</td>
					<td style="width: 93px; ">JHRQ</td>
					<td style="width: 93px; ">SJRQ</td>
					<td style="width: 93px; ">CYR</td>
					<td style="width: 93px; ">DM</td>
					<td style="width: 93px; ">SS</td>
					<td style="width: 93px; ">HBH</td>
					<td style="width: 93px; ">JX</td>
					<td style="width: 93px; ">HBXZ</td>
					<td style="width: 93px; ">HX</td>
					<td style="width: 93px; ">HXO</td>
					<td style="width: 93px; ">HXFL</td>
					<td style="width: 93px; ">HD</td>
					<td style="width: 93px; ">HDFL</td>
					<td style="width: 93px; ">ZDYZ</td>
					<td style="width: 93px; ">ZDZW</td>
					<td style="width: 93px; ">PEYZ</td>
					<td style="width: 93px; ">PEZW</td>
					<td style="width: 93px; ">KGYZ</td>
					<td style="width: 93px; ">KGZW</td>
					<td style="width: 93px; ">IO</td>
					<td style="width: 93px; ">JCN</td>
					<td style="width: 93px; ">JQJD</td>
					<td style="width: 93px; ">QJSJ</td>
					<td style="width: 93px; ">CR</td>
					<td style="width: 93px; ">ET</td>
					<td style="width: 93px; ">YE</td>
					<td style="width: 93px; ">CRWH</td>
					<td style="width: 93px; ">ETWH</td>
					<td style="width: 93px; ">YEWH</td>
					<td style="width: 93px; ">XL</td>
					<td style="width: 93px; ">YJ</td>
					<td style="width: 93px; ">HW</td>
					<td style="width: 93px; ">PBM</td>
					<td style="width: 93px; ">XG</td>
					<td style="width: 93px; ">BC</td>
					<td style="width: 93px; ">JH</td>
					<td style="width: 93px; ">WJHZ</td>
					<td style="width: 93px; ">XLJS</td>
					<td style="width: 93px; ">OPERATION_STATUS</td>
					<td style="width: 93px; ">FLIGHT_STATUS</td>

				</tr>
			</thead>
			<tbody>			   
				<c:forEach items="${dwMarketingDepartments}" var="dwMarketingDepartment">
					<tr>
					<c:set var="no" value="${no + 1}" />
					<td>${no}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingJHRQ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingSJRQ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingCRY}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingDM}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingC_SS}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingHBH}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingJX}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingHBXZ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingHX}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingHXO}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingHXFL}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingHD}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingHDFL}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingZDYZ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingZDZW}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingPEYZ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingPEZW}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingKGYZ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingKGZW}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingIO}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingJCN}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingJQJD}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingQJSJ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingCR}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingET}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingYE}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingCRWH}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingETWH}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingYEWH}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingXL}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingYJ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingHW}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingPBM}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingXG}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingBC}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingJH}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingWJHZ}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingXLJS}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingOPERATION_STATUS}</td>
			<td style="width: 193px; ">${dwMarketingDepartment.marketingFLIGHT_STATUS}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
</body>
</html>
