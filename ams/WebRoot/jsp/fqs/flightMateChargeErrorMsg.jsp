<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <title>航班收费计算异常信息</title>






</head>

<body style="padding: 10px;min-width: 1024px;">

    <div style="padding: 3px;">
      <h2>航班计算异常信息:</h2>
    </div>
    <div id="msg">
      <%=request.getSession().getAttribute("calErrorMsg")%>
    </div>



</body>
</html>
