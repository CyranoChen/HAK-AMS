<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>收费项目</title>
    <%@include file="../../static/included.jsp" %>
    <script type="text/javascript">
        // 从Session中获得当前登录的对象
        var role = "<%=request.getSession().getAttribute("role")%>";
    </script>
    <script type="text/javascript" language="JavaScript" src="../../js/basic/chargeSubject.js"></script>

    <style type="text/css">
        table td {
            padding: 5px;
        }
        #chargeRuleTable .jqx-cell{
            padding: 4px;
            padding-top: 5px;
            /*font-size: 80%;*/
            text-overflow: ellipsis;
            white-space:nowrap;
            overflow:hidden;
        }

    </style>
</head>
<body style="padding: 10px; min-width: 1024px;">
<%@ include file="./headerNavigation.jsp" %>
<div class="panel search" style="height:55px" id="search-panel">
    <div class="main clearfix">
        <div>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <%--加上_b的原因是为了和window的对应属性进行区分--%>
                    <td class="lb"><label for="name_b">项目名称</label></td>
                    <td>
                        <input id="name_b" class="formItem"/>
                    </td>
                    <td class="lb"><label for="chargeProperty_b">收费性质</label></td>
                    <td>
                        <select id="chargeProperty_b" class="formItem">
                            <option value="">请选择:</option>
                            <option value="0">航空性业务收费</option>
                            <option value="1">非航空性业务重要收费</option>
                            <option value="2">非航空性业务其他收费</option>
                        </select>
                    </td>

                    <td class="lb"><label for="chargeType_b">收费类型</label></td>
                    <td>
                        <%--多选下拉列表--%>
                        <div id="chargeType_b" class="formItem"></div>
                    </td>
                    <td>
                        <input type="button" id="query_button" class="find mr8"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<div id="totalNumber" style="text-align: right;font-weight: bold;font-size: 13px;">&nbsp;</div>
<%--数据显示区--%>
<div id="dataTable-panel" style="height:80%; width: 100%;">
    <div id="dataTable"></div>
</div>

<%-- 增加/修改窗口 --%>
<jsp:include page="subjectInfoWindow.jsp"/>
</body>
</html>