<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <title>航班收费计算查询</title>
  <%@include file="../../static/included.jsp"%>

  <script>


  </script>
  <script src="../../js/fqs/flightMateCharge.js"></script>

</head>

<body style="padding: 10px;min-width: 1024px;">

  <div class="panel search" style="height:55px" id="search-panel">
    <div class="main clearfix">
      <div class="clear"></div>
      <div>
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>


            <td class="lb"><label for="flightDirection">进离港标识:</label></td>
            <td>
              <select id="flightDirection" class="formItem">
                <option selected value="">全部</option>
                <option  value="0">进港</option>
                <option value="1">离港</option>
              </select>
            </td>
            <td class="lb"><label for="scheduledTime">航班执行日:</label></td>
            <td><div id="scheduledTime" class="formItem"></div></td>
            <td class="lb"><label for="flightIdentity">航班号:</label></td>
            <td><input id="flightIdentity" class="formItem"/></td>
            <td class="lb"><label for="airLineName">航空公司:</label></td>
            <td><input id="airLineName" class="formItem"/></td>
            <td class="lb"><label for="registeration">机号:</label></td>
            <td><input id="registeration" class="formItem"/></td>
            <%--<td class="lb"><label for="standFlag">远近机位标识:</label></td>--%>
            <%--<td>--%>
              <%--<select id="standFlag" class="formItem">--%>
                <%--<option selected value="">全部</option>--%>
                <%--<option  value="0">近机位</option>--%>
                <%--<option value="1">远机位</option>--%>
              <%--</select>--%>
            <%--</td>--%>
      <td class="lb">
        <input type="button" id="query_button" class="find mr8"  />
        <input type="button" id="cal" class="cl" title="计算" />
      </td>
      </tr>
      </table>
    </div>
    </div>
   </div>


  <table width="100%" style="height: 90%">
    <tr>
      <td  style="height: 25px;text-align: right;vertical-align:bottom;"><span>未计算 </span><span id="totalNumber_notcalGrid"></span></td>
      <td  style="height: 25px;text-align: right;vertical-align: bottom;"><span>已计算 </span><span  id="totalNumber_calGrid" ></span></td>
    </tr>
    <tr>
      <td width="50%" style="padding-right: 5px;">
          <div id="notcalGrid">
          </div>
      </td>

      <td width="50%" style="padding-left: 5px;">
          <div id="calGrid">
          </div>
      </td>
    </tr>
  </table>


<div id="loading"></div>


</body>
</html>
