<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>收费类型</title>
    <%@include file="../../static/included.jsp" %>
    <script type="text/javascript">
        // 从Session中获得当前登录的对象
        var role = "<%=request.getSession().getAttribute("role")%>";
    </script>
    <script type="text/javascript" language="JavaScript" src="../../js/basic/chargeType.js"></script>
</head>
<body style="padding: 10px;min-width: 1024px;">
<%@ include file="./headerNavigation.jsp" %>
<div class="panel search" style="height:55px" id="search-panel">
    <div class="main clearfix">
        <div>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="lb"><label for="chargeCode">收费类型编码</label></td>
                    <td><input id="chargeCode" class="formItem" /></td>
                    <td class="lb"><label for="chargeName">类型名称</label></td>
                    <td><input id="chargeName" class="formItem" /></td>
                    <td class="lb">
                        <input type="button" id="query_button" class="find mr8"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<div id="totalNumber" style="text-align: right;font-weight: bold;font-size: 13px;">&nbsp;</div>

<div id="dataTable-panel" style="height:80%;">
    <div id="dataTable"></div>
</div>
</body>
</html>