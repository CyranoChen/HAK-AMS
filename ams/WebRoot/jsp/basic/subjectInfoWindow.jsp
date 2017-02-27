<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<div id="windowContainer">
    <div>
        <table width="100%">
            <tr>
                <td width="53%">
                    <table cellspacing="0" cellpadding="0" style="width: 100%" id="form" class="fl" border="0">
                        <input type="hidden" id="id" class="formItem">
                        <tr>
                            <td class="lb" width="17%" style="text-align: right">
                                <label for="name"><span style="color: red">*</span>项目名称</label>
                            </td>
                            <td width="33%">
                                <input id="name" class="formItem"/>
                            </td>
                            <td class="lb" width="17%" style="text-align: right"><label for="chargeTypeId">收费类型</label>
                            </td>
                            <%--下拉列表--%>
                            <td width="33%">
                                <div id="chargeTypeId" class="formItem"></div>
                            </td>
                        </tr>
                        <tr>
                            <td class="lb" style="text-align: right"><label for="chargeProperty">收费性质</label></td>
                            <td class="lb">
                                <select id="chargeProperty" class="formItem">
                                    <option value="0">航空性业务收费</option>
                                    <option value="1">非航空性业务重要收费</option>
                                    <option value="2">非航空性业务其他收费</option>
                                </select>
                            </td>

                            <td class="lb" style="text-align: right"><label for="currency">货币</label></td>
                            <td class="lb">
                                <select id="currency" class="formItem">
                                    <option value="人民币">人民币</option>
                                    <option value="美元">美元</option>
                                </select>
                            </td>
                        </tr>
                        <tr>

                            <input type="hidden" id="currencyUnit" class="formItem" value=" 元">

                            <td class="lb" style="text-align: right"><label for="formula"><span
                                    style="color: red">*</span>公式</label></td>
                            <td colspan="3">
                                <input id="formula" class="formItem">

                            </td>
                        </tr>
                        <tr>
                            <td class="lb" style="text-align: right"><label for="parameter">参数</label></td>
                            <td colspan="3">
                                <div id="parameter" class="formItem fl" style="display: inline-block"></div>
                                <input type="button" id="showChecked" class="find mr8 fl "
                                       style="  width: 28px; margin-left: 3px; height: 23px; margin-right: 0" value="..."/>
                            </td>

                        </tr>
                        <tr>
                            <td class="lb" style="text-align: right"><label for="afeeCoe">进港系数</label></td>
                            <td>
                                <input id="afeeCoe" class="formItem">
                            </td>
                            <td class="lb" style="text-align: right"><label for="dfeeCoe">离港系数</label></td>
                            <td>
                                <input id="dfeeCoe" class="formItem">
                            </td>
                        </tr>
                        <tr>
                            <td class="lb" style="text-align: right"><label for="unitPrice">单价</label></td>
                            <td>
                                <input id="unitPrice" class="formItem">
                            </td>
                            <td class="lb" style="text-align: right"><label for="subjectCode">编码</label></td>
                            <td>
                                <input id="subjectCode" class="formItem">
                            </td>
                        </tr>
                        <tr>
                            <td class="lb" style="text-align: right"><label for="description">描述</label></td>
                            <td colspan="3">
                                <textarea id="description" class="formItem"></textarea>
                                <span style="color: red; font-size: 5px;">注: 公式中用{1}代替参数位置, 符号须使用英文符号</span>
                            </td>
                        </tr>

                    </table>
                </td>
                <td width="47%" rowspan="2" id="chargeRule">

                    <div>
                        <div id="dataTable-panel" style="height:80%; width: 100%">
                            <div id="chargeRuleTable"></div>
                        </div>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <input type="button" id="submit" value="确定" style="margin-left: 140px;">
                    <input type="button" id="cancel" value="取消" style="margin-left: 50px;">
                </td>
            </tr>
        </table>
    </div>
</div>

<%--添加修改ruleExp--%>
<div id="ruleExpAM" class="fl" style="display: none;">
    <input type="hidden" id="roeId">
    <div  style="padding: 15px 10px;">
        <div class="formItem" style="float: left;" id="groupOrExp" >
            <select id="goeSelect" >
                <option value="G">组</option>
                <option value="E">表达式</option>
            </select>
        </div>
        <div style="float: left; margin-left: 10px;">
            <select id="group" >
                <option value="and">and</option>
                <option value="or">or</option>
            </select>
        </div>
        <div id="exp" style="float: left; margin-left: 10px; display: none;"></div>
        <div style="float: left; margin-left: 10px; margin-bottom: 10px" id="logic">
            <select id="logicOpt">
                <option value="=">=</option>
                <option value="!=">!=</option>
                <option value=">">&gt;</option>
                <option value=">=">&gt;=</option>
                <option value="<">&lt;</option>
                <option value="<=">&lt;=</option>
                <option value="in">in</option>
            </select>
        </div>
        <div id="valueArea" style=" margin-bottom: 10px">
            <textarea id="value"></textarea>
        </div>
        <input type="button" id="sub" value="确定" style="margin-left: 100px"/>
        <input type="button" id="can" value="取消" style="margin-left: 30px"/>
    </div>
</div>

<div id="parameterChecked" class="fl"  style="display: none;">
    <div class="main clearfix">
        <div id="listParameter">

        </div>
    </div>
</div>

