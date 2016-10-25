<%@page import="com.wonders.frame.ams.constants.Constant"%>
<%
	String staticPath = request.getContextPath();
	String staticBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ staticPath + "/";
	
%>
<!-- jqWidgets css -->
<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/jqwidgets/styles/jqx.base.css">
<%--<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/jqwidgets/styles/jqx.metro.css">--%>
<%--<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/jqwidgets/styles/jqx.office.css">--%>
<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/jqwidgets/styles/jqx.arctic.css">
 <!-- wonders css -->
<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/wonders/css/formalize.css">
<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/wonders/css/reset.css">
<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/wonders/css/pages.css">
<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/wonders/css/default/img.css">


<%--<link rel="stylesheet" type="text/css" href="<%=staticBasePath%>static/wonders/css/font-awesome.min.css">--%>
<script src="<%=staticBasePath%>static/wonders/js/extend.js"></script>
<!-- jQuery -->
<script src="<%=staticBasePath%>static/jq/jquery-1.11.2.min.js"></script>
<!-- jqWidgets -->
<script src="<%=staticBasePath%>static/jqwidgets/jqxcore.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxcalendar.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxdatetimeinput.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxbuttons.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxcheckbox.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxcombobox.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxdata.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxdata.export.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxdatatable.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxdropdownlist.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.aggregates.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.columnsreorder.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.columnsresize.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.edit.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.export.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.filter.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.grouping.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.pager.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.selection.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.sort.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxgrid.storage.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxinput.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxlistbox.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxlistmenu.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxloader.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxmaskedinput.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxmenu.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxnumberinput.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxpasswordinput.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxresponse.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxscrollbar.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxtabs.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxtextarea.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxtooltip.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxtree.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxtreegrid.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxvalidator.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxwindow.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/jqxformattedinput.js"></script>
<%--<script src="<%=staticBasePath%>static/jqwidgets/jqx-all.js"></script>--%>
<script src="<%=staticBasePath%>static/jqwidgets/globalization/globalize.js"></script>
<script src="<%=staticBasePath%>static/jqwidgets/globalization/globalize.culture.zh-CN.js"></script>
<!-- wonders -->
<script src="<%=staticBasePath%>static/wonders/js/commonsJquery.js"></script>
<script src="<%=staticBasePath%>static/wonders/js/commons.js"></script>
<script src="<%=staticBasePath%>static/wonders/js/commonsJqx.js"></script>

<script>
	Global.userLoginFlag = '<%=Constant.USER_LOGIN_FLAG%>';
</script>




