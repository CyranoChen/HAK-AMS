<%@ page import="com.wonders.frame.ams.controller.fqs.FlightMateSearchController" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <title>航班查询</title>
  <%@include file="../../static/included.jsp"%>

  <script>
    var role = "<%=request.getSession().getAttribute("role")%>";
//    document.oncontextmenu = function (){
//      return false;
//    };


  </script>
  <script src="../../js/fqs/flightMateSearch.js"></script>
  <script src="../../static/uploadifive/jquery.uploadifive.min.js"></script>
  <link rel="stylesheet" type="text/css" href="../../static/uploadifive/uploadifive.css">

  <style>
      .update{
        width: 20%;
        /*height: 100%;*/
        text-align: center;
        border-right: 1px #CCC solid;
      }



      .jqx-loader-modal{
        z-index: 300;
      }

      .update:last-child{
        border:0;
      }

      .red{
        background-color: #CC3333;
      }

      .purple{
        /*background-color: #000000;*/
      }

      .yellow{
        background-color: #FFFFCC;
      }

      .orange{
        background-color: #FFAA00;
      }

      .green{
        background-color: #9ACD32;
      }

      .brown{
        background-color: #999966;
      }

      .blue{
        background-color: #6699CC;
      }





  </style>
</head>

<body style="padding: 10px;min-width: 1160px;">






<div class="panel search" style="height:55px" id="search-panel">
    <div class="main clearfix">
      <div class="clear"></div>
      <div>
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>


            <td class="lb"><label for="flightDirection">进离:</label></td>
            <td>
              <select id="flightDirection" class="formItem">
                <option selected value="">全部</option>
                <option  value="0">进港</option>
                <option value="1">离港</option>
              </select>
            </td>
            <td class="lb"><label for="scheduledTime">运营日:</label></td>
            <td><div id="scheduledTime" class="formItem"></div></td>
            <td class="lb"><label for="flightIdentity">航班号:</label></td>
            <td><input id="flightIdentity" class="formItem"/></td>
            <td class="lb"><label for="airLineName">航空公司:</label></td>
            <td><input id="airLineName" class="formItem"/></td>
            <td class="lb"><label for="registeration">机号:</label></td>
            <td><input id="registeration" class="formItem"/></td>
            <td class="lb"><label for="standFlag">机位:</label></td>
            <td>
              <select id="standFlag" class="formItem">
                <option selected value="">全部</option>
                <option  value="0">近</option>
                <option value="1">远</option>
              </select>
            </td>
            <td class="lb"><label for="error">告警:</label></td>
            <td>
              <select id="error" class="formItem">
                <option selected value="">全部</option>
                <option  value="0">缺少机位</option>
                <option  value="1">缺少机号</option>
                <option  value="2">缺少航空公司</option>
                <option  value="3">缺少实际降落时间</option>
                <option  value="4">二字码与航班号不符</option>
                <option  value="5">缺少实际起飞时间</option>
                <option  value="6">VIP人数>头等舱人数</option>
                <option  value="7">近机位规则冲突</option>
                <option  value="8">远机位规则冲突</option>
              </select>
            </td>
            <!-- <td class="lb"><label for="airlineHandler">代理:</label></td>
            <td>
              <select id="airlineHandler" class="formItem">
                <option selected value="">全部</option>
                <option  value="0">海航代理</option>
                <option value="1">南航代理</option>
                <option value="2">美兰代理</option>
              </select>
            </td> -->
            <td class="lb">
              <input type="button" id="query_button" class="find mr8"  />
              <input type="button" id="import" class="dr" title="舱单导入" />
              <input type="button" id="export" class="dc" title="航班导出" />
            </td>
          </tr>
        </table>
      </div>
    </div>
   </div>

  <div id="totalNumber" style="text-align: right;font-weight: bold;font-size: 13px;">&nbsp;</div>

    <div id="grid-panel">
      <div id="grid"></div>
    </div>

    <div id="detail-panel" class="r-detail" style="width: 100%;height: 200px;">
      <div style="height: 20px;font-weight: bold;" id="flightDetailPreview">&nbsp;</div>
      <div id='jqxTabs' >
        <ul class="tabs_001">
          <li class="selected"><a href="#">航班</a></li>
          <li><a href="#">舱单</a></li>
          <li><a href="#">航段</a></li>
          <li><a href="#">汽服</a></li>
          <li><a href="#">廊桥</a></li>
          <li><a href="#">VIP</a></li>
          <li><a href="#">提示</a></li>
        </ul>
        <div id="flightDetail" role="1">
          <table width="100%" class="detail">
            <tr>
              <th colspan="5" id="flightId">
                航班基础信息
              </th>
              <th  colspan="3" style="padding: 0;">
                <button id="position_btn" class="fr">定位</button>
                <button id="cal_btn" class="fr">收费计算</button>
                <button id="load_btn" class="fr">配载</button>
                <button id="match_btn" class="fr">配对</button>
              </th>
            </tr>
            <tr>
              <td class="t_r">航班号*</td>
              <td colname="flightIdentity" editable="true">&nbsp;</td>
              <td class="t_r">执行日</td>
              <td colname="scheduledTime">&nbsp;</td>
              <td class="t_r">进港时间</td>
              <td colname="landedTime">&nbsp;</td>
              <td class="t_r">离港时间</td>
              <td colname="takeOffTime">&nbsp;</td>
            </tr>
            <tr>
              <td class="t_r">机位*</td>
              <td colname="standNum" editable="true" >&nbsp;</td>
              <td class="t_r">航班性质</td>
              <td colname="flightProperty">&nbsp;</td>
              <td class="t_r">进港始发</td>
              <td colname="iataOriginAirport">&nbsp;</td>
              <td class="t_r">离港终点</td>
              <td colname="iataDestinationAirport">&nbsp;</td>
            </tr>
            <%--<tr>--%>

              <%--<td class="t_r">代理</td>--%>
              <%--<td colname="airlineHandler">&nbsp;</td>--%>
              <%--<td class="t_r"></td>--%>
              <%--<td>&nbsp;</td>--%>
              <%--<td class="t_r">&nbsp;</td>--%>
              <%--<td>&nbsp;</td>--%>
              <%--<td class="t_r">&nbsp;</td>--%>
              <%--<td>&nbsp;</td>--%>

            <%--</tr>--%>
          </table>

          <table width="100%" class="detail">
            <tr>
              <th colspan="8">航班详细信息</th>
            </tr>
            <tr>
              <td class="t_r">停场时间</td>
              <td colname="landTime">&nbsp;</td>
              <td class="t_r">航线</td>
              <td colname="flightRoute">&nbsp;</td>
              <%--<td class="t_r">生成方式</td>--%>
              <%--<td colname="matchMethod">&nbsp;</td>--%>
              <td class="t_r">航前航后</td>
              <td colname="aircraftService">&nbsp;</td>
              <td class="t_r">夜航</td>
              <td  colname="isNightFlight">&nbsp;</td>
              <%--<td class="t_r">延误原因编码</td>--%>
              <%--<td colname="delayCode">&nbsp;</td>--%>
            </tr>
            <tr>
              <td class="t_r">航线性质</td>
              <td colname="flightCountryType">&nbsp;</td>

              <td class="t_r">航段0</td>
              <td colname="flightPart0">&nbsp;</td>
              <td class="t_r">航段1</td>
              <td colname="flightPart1">&nbsp;</td>
              <%--<td class="t_r">航段2</td>--%>
              <%--<td colname="flightPart2">&nbsp;</td>              --%>
              <td class="t_r">航段性质</td>
              <td colname="flightPart0Type">&nbsp;</td>
            </tr>
            <%--<tr>--%>


              <%--&lt;%&ndash;<td class="t_r">是否高峰</td>&ndash;%&gt;--%>
              <%--&lt;%&ndash;<td  colname="isPeakFlight">&nbsp;</td>&ndash;%&gt;--%>
              <%--<td class="t_r">专机</td>--%>
              <%--<td  colname="specialPlane">&nbsp;</td>--%>
              <%--<td class="t_r">&nbsp;</td>--%>
              <%--<td>&nbsp;</td>--%>
              <%--<td class="t_r">&nbsp;</td>--%>
              <%--<td>&nbsp;</td>--%>

            <%--</tr>--%>

          </table>


          <table width="100%" class="detail">
            <tr>
              <th colspan="8">飞机详细信息</th>
            </tr>
            <tr>
              <td class="t_r">机型</td>
              <td colname="aircraftType">&nbsp;</td>
              <td class="t_r">机号</td>
              <td colname="registeration">&nbsp;</td>
              <td class="t_r">飞机最大业载</td>
              <td colname="aircraftPayload">&nbsp;</td>
              <td class="t_r">座位数</td>
              <td colname="aircraftSeatCapacity">&nbsp;</td>
            </tr>
            <tr>
              <td class="t_r">最大起飞全重</td>
              <td colname="aircraftTakeOffWeight">&nbsp;</td>
              <td class="t_r">宽窄体</td>
              <td colname="isWideOrNarrow">&nbsp;</td>
              <td class="t_r">客座率</td>
              <td colname="passengerloadFactor">&nbsp;</td>
              <%--<td class="t_r">客桥数量</td>--%>
              <%--<td colname="passengerBridgeNumber">&nbsp;</td>--%>
              <td class="t_r">旅客服务费打折</td>
              <td colname="isHighDensity">&nbsp;</td>
            </tr>
            <%--<tr>--%>
              <%--<td class="t_r">备注</td>--%>
              <%--<td colspan="7" colname="remark">&nbsp;</td>--%>
            <%--</tr>--%>

          </table>
        </div>
        <div id="manifestDetail" role="1,2">
          <table width="100%" class="detail">
            <tr>
              <th colspan="6">国内</th>
            </tr>
            <tr>
              <td class="t_r">国内成人</td>
              <td colname="passengerInternal" editable="true"></td>
              <td class="t_r">国内儿童</td>
              <td colname="childInternal" editable="true"></td>
              <td class="t_r">国内婴儿</td>
              <td colname="babyInternal" editable="true"></td>

            </tr>
            <tr>
              <td class="t_r">国内货物重量</td>
              <td colname="airCargoWeightInternal" editable="true"></td>
              <td class="t_r">国内行李重量</td>
              <td colname="luggageWeightInternal" editable="true"></td>
              <td class="t_r">国内邮件重量</td>
              <td colname="airMailWeightInternal" editable="true"></td>
            </tr>
            <tr>
              <td class="t_r">国内行李件数</td>
              <td colname="luggageNumInternal" editable="true"></td>
              <td class="t_r"></td>
              <td></td>
              <td class="t_r"></td>
              <td></td>
            </tr>
          </table>
          <table width="100%" class="detail">
            <tr>
              <th colspan="6">国际</th>
            </tr>
            <tr>
              <td class="t_r">国际成人</td>
              <td colname="passengerInternational" editable="true"></td>
              <td class="t_r">国际儿童</td>
              <td  colname="childInternational" editable="true"></td>
              <td class="t_r">国际婴儿</td>
              <td  colname="babyInternational" editable="true"></td>
            </tr>
            <tr>
              <td class="t_r">国际货物重量</td>
              <td colname="airCargoWeightInternational" editable="true"></td>
              <td class="t_r">国际行李重量</td>
              <td colname="luggageWeightInternational" editable="true"></td>
              <td class="t_r">国际邮件重量</td>
              <td colname="airMailWeightInternational" editable="true"></td>
            </tr>
            <tr>
              <td class="t_r">国际行李件数</td>
              <td colname="luggageNumInternational" editable="true"></td>
              <td class="t_r"></td>
              <td></td>
              <td class="t_r"></td>
              <td></td>
            </tr>
          </table>
          <table width="100%" class="detail">
            <tr>
              <th colspan="6">过站</th>
            </tr>
            <tr>
              <td class="t_r">过站成人</td>
              <td colname="viaAdult" editable="true"></td>
              <td class="t_r">过站儿童</td>
              <td colname="viaChild" editable="true"></td>
              <td class="t_r">过站婴儿</td>
              <td colname="viaBaby" editable="true"></td>
            </tr>
            <%--<tr>--%>
              <%--<td class="t_r">过站货物重量</td>--%>
              <%--<td colname="airCargoWeightVia" editable="true"></td>--%>
              <%--<td class="t_r">过站行李重量</td>--%>
              <%--<td colname="luggageWeightVia" editable="true"></td>--%>
              <%--<td class="t_r">过站邮件重量</td>--%>
              <%--<td colname="airMailWeightVia" editable="true"></td>--%>
            <%--</tr>--%>
            <tr>
              <td class="t_r">过站行李件数</td>
              <td colname="luggageNumVia" editable="true"></td>
              <td class="t_r"></td>
              <td></td>
              <td class="t_r"></td>
              <td></td>
            </tr>
          </table>
        </div>

        <div>
          <div style="padding: 5px;">
            <button id="calLoadData">舱单保存</button>
          </div>
          <div id="loadData" role="1,2"></div>
        </div>

        <div>
          <div id="carserviceDetail" role="1,3"></div>
        </div>
        <div>
          <div>
            <table>
              <tr>
                <td style="vertical-align: middle;">廊桥类型:</td>
                <td>
                  <select id="passengerBridgeNumber" role="1,4">
                    <option value="1" selected>单桥</option>
                    <option value="2">双桥</option>
                  </select>
                </td>
              </tr>
            </table>

          </div>
          <div id="bridgeserviceDetail" role="1,4"></div>
        </div>
        <div id="vipDetail" role="1,5">
          <table width="100%" class="detail">
            <tr>
              <th colspan="8">VIP信息</th>
            </tr>
            <tr>
              <td class="t_r">vip人数</td>
              <td colname="vipCount" editable="true">&nbsp;</td>
              <td class="t_r">头等舱人数(含vip)</td>
              <td colname="firstClassCount" editable="true">&nbsp;</td>
              <td class="t_r">贵宾摆渡车人数</td>
              <td colname="vipFirstAirdromeUsedCount" editable="true">&nbsp;</td>

            </tr>
          </table>
        </div>
        <div id="alarmDetail">
          <div style="font-size: 16px;font-weight: bold;color: red;padding: 10px;font-family: 'Microsoft YaHei'" id="alarmMessage">

          </div>
        </div>
      </div>
    </div>

  <div id="uploadPanel">
    <div>
      舱单导入
    </div>

    <div>
      <div>
        <form>
          <div id="queue" ></div>
          <input id="file_upload" name="file_upload" type="file" multiple="true">
        </form>
      </div>
      <br/>
      <br/>
      <div style="text-align: center;">
        <button id="startUpload">开始上传</button>
        <button id="clearQueue">清空列表</button>
      </div>
    </div>

  </div>


<div id="loader">
</div>

</body>
</html>
