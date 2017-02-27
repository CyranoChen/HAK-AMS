<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8" />
<title>消息列表</title>
<link rel="stylesheet" href="../css/formalize.css" />
<link rel="stylesheet" href="../css/page.css" />
<link rel="stylesheet" href="../css/imgs.css" />
<link rel="stylesheet" href="../css/reset.css" />
<!--[if IE 6.0]>
    <script src="js/iepng.js" type="text/javascript"></script>
    <script type="text/javascript">
         EvPNG.fix('div, ul, ol, img, li, input, span, a, h1, h2, h3, h4, h5, h6, p, dl, dt');
    </script>
<![endif]-->
<script src="../js/html5.js"></script>
<script src="../js/jquery-1.7.1.js"></script>
<script src="../js/jquery.formalize.js"></script>
<script src="../js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript">

$(document).ready(function () {
            var $tbInfo = $(".filter .query input:text");
            $tbInfo.each(function () {
                $(this).focus(function () {
                    $(this).attr("placeholder", "");
                });
            });
			
			var $tblAlterRow = $(".table_1 tbody tr:even");
			if ($tblAlterRow.length > 0)
				$tblAlterRow.css("background","#fafafa");			
        });

	
</script>
 
 
<script type="text/javascript">
//跳转到查看页面
function showViewPage(id){
	sonWindow = window.open('showView.action?id='+id);
}

function getKeyValue(id){
	$.ajax({
		type : 'post',
		url : 'getKeyValue.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			id:id
		},
		error:function(){
			alert("操作失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){alert("操作成功");}else{alert("操作失败");}
			$("form").submit();
		}
	});
}

function goPage(pageNo,type){
	//type=0,直接跳转到制定页
	if(type=="0"){
  		var pageCount = $("#totalPageCount").val();
  		var number = $("#number").val();
  		if(!number.match(/^[0-9]*$/)){
  			alert("请输入数字");
  			$("#number").val("");
  			$("#number").focus();
  			return ;
  		}
  		if(parseInt(number)>parseInt(pageCount)){
  			$("#number").val(pageCount);
  			$("#pageNo").val(pageCount);
  		}else{
  			$("#pageNo").val(number);
  		}
  	}
	//type=1,跳转到上一页	       
    if(type=="1"){
    	$("#pageNo").val(parseInt($("#number").val())-1);
    }
	//type=2,跳转到下一页	       
    if(type=="2"){
   		$("#pageNo").val(parseInt($("#number").val())+1);
    }
     //type=3,跳转到最后一页,或第一页
    if(type=="3"){
    	$("#pageNo").val(pageNo);
    }
   	$("#form").submit();
} 


</script> 
 
 
 
<script type="text/javascript">
//发送
function sendData(id){
	$.ajax({
		type : 'post',
		url : 'send.action?random='+Math.random(),
		dataType:'json',
		cache : false,
		data:{
			id:id
		},
		error:function(){
			alert("系统连接失败，请稍后再试！")
		},
		success:function(obj){
			if(obj.success){alert("操作成功");}else{alert("操作失败");}
			$("form").submit();
		}
	});
}


//页面缩略显示
$(function(){
	$("span[id=limitLength1]").each(function(){
		var html = $(this).html();
		if(html.length>20){
			$(this).html(html.substring(0,20)+"...");
		}
	});
	$("span[id=limitLength2]").each(function(){
		var html = $(this).html();
		if(html.length>18){
			$(this).html(html.substring(0,18)+"...");
		}
	});
})



//点击查询按钮，清空页码值
function search(){
	$("#pageNo").val("");
	$("form").submit();
}

function resetSearch(){
	$("#msgType").val("");
	$("#pageNo").val("1");
}
</script>
 
 
 
 
</head>

<body>
	<div class="main"><!--Ctrl-->
	<div class="ctrl clearfix">
		<div class="posi fl">
			<ul>
				<li class="fin">消息中心&gt;&gt;消息列表</li>
			</ul>
		</div>
	</div>	
		<div class="filter">
			<div class="query p8" id="searchArea">
			<s:form action="showList" name="imfMsg" namespace="/imfMsg" method="post" id="form">
			<input type="hidden" name="pageNo" id="pageNo" value="<s:property value='#request.page.currentPage'/>"/>		
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr id="search">
						<td class="t_r" style="white-space:nowrap">服务类型</td>
						<td style="white-space:nowrap">
							<select name="msgType" class="input_large">	
								<s:if test="#request.msgType==null">							
										<option value="" selected="selected">---请选择---</option>
										<option value="FSS">FSS</option>
										<option value="BSS">BSS</option>
								</s:if>
								<s:elseif test="#request.msgType=='FSS'">							
										<option value="">---请选择---</option>
										<option value="FSS" selected="selected">FSS</option>
										<option value="BSS">BSS</option>

								</s:elseif>
								<s:elseif test="#request.msgType=='BSS'">							
										<option value="">---请选择---</option>
										<option value="FSS">FSS</option>
										<option value="BSS" selected="selected">BSS</option>
								</s:elseif>
								
							</select>
						</td>
						<td class="t_r" style="white-space:nowrap">接收系统</td>
						<td style="white-space:nowrap">
							<select name="receiver" class="input_large">	
								<s:if test="#request.receiver==null">							
									<option value="" selected="selected">---请选择---</option>
									<option value="AMS">AMS</option>
								</s:if>
								<s:elseif test="#request.receiver=='AMS'">							
									<option value="">---请选择---</option>
									<option value="AMS" selected="selected">AMS</option>
								</s:elseif>
							</select>
						</td>
						<td class="t_r" style="white-space:nowrap">消息状态</td>
						<td style="white-space:nowrap">
							<select name="status" class="input_large">	
								<s:if test="#request.status==null">							
									<option value="" selected="selected">---请选择---</option>
									<option value="0">待解析入库</option>
									<option value="1">已解析入库</option>
									<option value="8">入库成功(告警异常)</option>									
									<option value="9">解析入库失败</option>									
									<option value="2">订阅状态描述</option>
									<option value="3">系统异常描述</option>

								</s:if>
								<s:elseif test="#request.status==0">							
									<option value="">---请选择---</option>
									<option value="0" selected="selected">待解析入库</option>
									<option value="1">已解析入库</option>
									<option value="8">入库成功(告警异常)</option>									
									<option value="9">解析入库失败</option>									
									<option value="2">订阅状态描述</option>
									<option value="3">系统异常描述</option>							
								</s:elseif>
								<s:elseif test="#request.status==1">							
									<option value="">---请选择---</option>
									<option value="0">待解析入库</option>
									<option value="1" selected="selected">已解析入库</option>
									<option value="8">入库成功(告警异常)</option>									
									<option value="9">解析入库失败</option>									
									<option value="2">订阅状态描述</option>
									<option value="3">系统异常描述</option>								
								</s:elseif>	
								<s:elseif test="#request.status==2">							
									<option value="">---请选择---</option>
									<option value="0"">待解析入库</option>
									<option value="1">已解析入库</option>
									<option value="8">入库成功(告警异常)</option>									
									<option value="9">解析入库失败</option>									
									<option value="2" selected="selected">订阅状态描述</option>
									<option value="3">系统异常描述</option>								
								</s:elseif>
								<s:elseif test="#request.status==3">							
									<option value="">---请选择---</option>
									<option value="0">待解析入库</option>
									<option value="1">已解析入库</option>
									<option value="8">入库成功(告警异常)</option>									
									<option value="9">解析入库失败</option>									
									<option value="2">订阅状态描述</option>
									<option value="3" selected="selected">系统异常描述</option>								
								</s:elseif>	
								<s:elseif test="#request.status==8">							
									<option value="">---请选择---</option>
									<option value="0">待解析入库</option>
									<option value="1">已解析入库</option>
									<option value="8" selected="selected">入库成功(告警异常)</option>									
									<option value="9">解析入库失败</option>									
									<option value="2">订阅状态描述</option>
									<option value="3">系统异常描述</option>							
								</s:elseif>	
								<s:elseif test="#request.status==9">							
									<option value="">---请选择---</option>
									<option value="0">待解析入库</option>
									<option value="1">已解析入库</option>
									<option value="8">入库成功(告警异常)</option>									
									<option value="9" selected="selected">解析入库失败</option>									
									<option value="2">订阅状态描述</option>
									<option value="3">系统异常描述</option>							
								</s:elseif>	
							</select>
						</td>
						<td class="t_r">ServiceType</td>
		        	      <td>
		        	      	<input type="text" name="serviceType" id="serviceType" class="input_large" placeholder="eg:FSS1/FSS2/BSS1..." value="<s:property value="#request.serviceType"/>">
		        	     </td>						
		
					</tr>
					<tr id="search">												
						
						<td class="t_r">MessageSequenceID</td>
		        	      <td>
		        	      	<input type="text" name="messageSequenceId" id="messageSequenceId" class="input_large"  placeholder="eg:1/2/3...." value="<s:property value="#request.messageSequenceId"/>">
		        	     </td>
						<td class="t_r">MessageType</td>
		        	      <td>
		        	      	<input type="text" name="messageType" id="messageType" class="input_large" placeholder="eg:FlightData" value="<s:property value="#request.messageType"/>">
		        	     </td>	
	
						<td class="t_r">OperationMode</td>
		        	      <td>
		        	      	<input type="text" name="operationMode" id="operationMode" class="input_large" placeholder="eg:NEW/MOD/DEL" value="<s:property value="#request.operationMode"/>">
		        	     </td>	
		        	     
		        	    <td class="t_r">FlightIdentity</td>
		        	      <td>
		        	      	<input type="text" name="flightIdentity" id="flightIdentity" class="input_large" placeholder="eg:ZH9709" value="<s:property value="#request.flightIdentity"/>">
		        	     </td>

					</tr>	
					<tr id="search">

		        	    <td class="t_r">FlightDirection</td>
		        	      <td>
		        	      	<input type="text" name="flightDirection" id="flightDirection" class="input_large" placeholder="eg:A/D"  value="<s:property value="#request.flightDirection"/>">
		        	     </td>
						<td class="t_r">FlightScheduledDate</td>
		        	      <td colspan="2">
		        	      	<input type="text" id="flightScheduledDates" name="flightScheduledDates" class="input_large" placeholder="eg:yyyy-MM-dd" value="<s:property value="#request.flightScheduledDates"/>">
		        	       -
		                    <input type="text" id="flightScheduledDatee" name="flightScheduledDatee" class="input_large" placeholder="eg:yyyy-MM-dd"  value="<s:property value='#request.flightScheduledDatee'/>">
		                 </td>	
		                 <td colspan="2"></td>	        	     
		        	    <td class="t_c" >
							<input type="button" value="检 索" onclick="search();"/>&nbsp;&nbsp; 
							<!-- input type="button" value="重 置" onclick="resetSearch()"/-->
						</td>		                 
					</tr>			                 
				</table>
			</s:form>
			</div>
		</div>
		<!--Filter End--> <!--Table-->
		<div class="mb10">
		<table width="100%" class="table_1">
			<thead>
				<th colspan="17">&nbsp;&nbsp;消息列表</th>
			</thead>
			<tbody>
				<tr class="tit">
					<td width="3%;" style="white-space:nowrap;">序号</td>
					<td width="10%;"  class="t_r" style="white-space:nowrap;">消息编号</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">生成时间</td>	
					<td width="5%;"  class="t_r" style="white-space:nowrap;">入库时间</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">服务类型</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">接收系统</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">消息状态</td>
					<td width="3%;"  class="t_r" style="white-space:nowrap;">ST</td>
					<td width="3%;"  class="t_r" style="white-space:nowrap;">MSI</td>					
					<td width="3%;"  class="t_r" style="white-space:nowrap;">MT</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">OM</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">FI</td>	
					<td width="2%;"  class="t_r" style="white-space:nowrap;">FD</td>					
					<td width="5%;"  class="t_r" style="white-space:nowrap;">FSD</td>
	
					<td width="5%;" colspan="3" class="t_r" style="white-space:nowrap;">操作</td>
				</tr>
				<s:iterator value="#request.page.result" id="msg" status="st">
					<tr>
						<td class="t_c" style="white-space: nowrap;">
							<s:property value="#st.index+#request.page.startRow+1"/>
						</td>
                        <td class="t_l" style="white-space: nowrap;" title="<s:property value="#msg.id"/>">
                        	<s:property value="#msg.id"/>
                        </td>
                        <td class="t_c" style="white-space: nowrap;">                                                	
                        	<s:date name="#msg.createTime" format="yyyy-MM-dd HH:mm:ss:SSSS"/>
                        </td>
						<td class="t_c" style="white-space: nowrap;">
							<s:date name="#msg.operateTime" format="yyyy-MM-dd HH:mm:ss:SSSS"/>
						</td>                          
                        <td class="t_c" style="white-space: nowrap;">
                        	<s:property value="#msg.msgType"/>
                        </td>                        
						<td class="t_c" style="white-space: nowrap;">
							<s:property value="#msg.receiver"/>
						</td>  
						
						<td class="t_c" style="white-space: nowrap;">
							<s:if test="#msg.status==0">待解析入库</s:if>
							<s:elseif test="#msg.status==1">已解析入库</s:elseif>
							<s:elseif test="#msg.status==8">入库成功(告警异常)</s:elseif>		
							<s:elseif test="#msg.status==9">解析入库失败</s:elseif>															
							<s:elseif test="#msg.status==2">订阅状态描述</s:elseif>
							<s:elseif test="#msg.status==3">系统异常描述</s:elseif>
						
						</td>                        

                        <td class="t_c" style="white-space: nowrap;">
                        	<s:property value="#msg.serviceType"/>
                        </td>                        
						<td class="t_c" style="white-space: nowrap;">
							<s:property value="#msg.messageSequenceId"/>
						</td>  
						
                        <td class="t_c" style="white-space: nowrap;">
                        	<s:property value="#msg.messageType"/>
                        </td>                        
						<td class="t_c" style="white-space: nowrap;">
							<s:property value="#msg.operationMode"/>
						</td>  
						
                        <td class="t_c" style="white-space: nowrap;">
                        	<s:property value="#msg.flightIdentity"/>
                        </td> 
						<td class="t_c" style="white-space: nowrap;">
							<s:property value="#msg.flightDirection"/>
						</td> 
						                                               
						<td class="t_c" style="white-space: nowrap;">
							<s:property value="#msg.flightScheduledDate"/>
						</td>  												               
						<td class="t_c" style="white-space: nowrap;">
							<a href="javascript:void(0);" onclick="showViewPage( '<s:property value="#msg.id"/>');" style="display:inline;">查看</a>&nbsp;&nbsp;&nbsp;					
						</td>
						<td class="t_c" style="white-space: nowrap;">
							<a href="javascript:void(0);" onclick="getKeyValue( '<s:property value="#msg.id"/>');" style="display:inline;">更新关键值</a>&nbsp;&nbsp;&nbsp;					
						</td>						
						<td class="t_c" style="white-space: nowrap;">
							<a href="javascript:void(0);" onclick="sendData( '<s:property value="#msg.id"/>');" style="display:inline;">
								<s:if test="#msg.status==0">解析入库</s:if>	
								<s:elseif test="#msg.status==9">再次解析</s:elseif>		
								<s:elseif test="#msg.status==1">更新解析</s:elseif>							
							</a>&nbsp;&nbsp;&nbsp;						
						</td>
					</tr>
				</s:iterator>
			</tbody>
			
			<tr class="tfoot">
			      <td colspan="17"><div class="clearfix">
			      <s:if test="#request.page.totalRows==0"><span>无相关数据</span></s:if>
			      <s:else>
			      	<span class="fl">
				      	<s:property value="#request.page.totalRows"/>条记录，当前显示<s:property value="#request.page.startRow+1"/> -
					    <s:if test="#request.page.currentPage==#request.page.totalPages">
					      	<s:property value="#request.page.totalRows"/>条
					    </s:if>
					    <s:else>
					    	<s:property value="#request.page.startRow+#request.page.pageSize"/>条
						</s:else>
				    </span>
			        <ul class="fr clearfix pager">
			          <li>Pages:<s:property value="#request.page.currentPage"/>/<s:property value="#request.page.totalPages"/>
			          	<input type="hidden" value="<s:property value='#request.page.totalPages'/>" id="totalPages">
			            <input type="text"" id="number" name="pageNumber" min="0" max="999" step="1" class="input_tiny" value="<s:property value='#request.page.currentPage'/>" />
			            <input type="button"" name="button" id="button" value="Go" onclick="goPage(0,0)">
		           	  </li>
		           	  
		               <s:if test="#request.page.currentPage==#request.page.totalPages">
		               	 <li><a href="javascript:void(0)">&gt;&gt;</a></li>
		               </s:if>
		               <s:else>
		                <li><a href="javascript:void(0)" onclick="goPage(<s:property value='#request.page.totalPages'/>,3)">&gt;&gt;</a></li>
		               </s:else>
		                
		              <li>
		              	<s:if test="#request.page.currentPage==#request.page.totalPages">	
		              		<a href="javascript:void(0)">下一页</a>
		              	</s:if>
		              	<s:else>
		              		<a href="javascript:void(0)" onclick="goPage(<s:property value='#request.page.currentPage'/>,2)">下一页</a>
		              	</s:else>
		              </li>
		              
		              <li>
		              	<s:if test="#request.page.currentPage==1">
		              		<a href="javascript:void(0)">上一页</a>
		              	</s:if>
		              	<s:else>
		              		<a href="javascript:void(0)" onclick="goPage(<s:property value='#request.page.currentPage'/>,1)">上一页</a>
		              	</s:else>
		              </li>
		              
		              <s:if test="#request.page.currentPage==1">
		              	<li><a href="javascript:void(0)">&lt;&lt;</a></li>
		              </s:if>
		              <s:else>
		              	<li><a href="javascript:void(0)" onclick="goPage(1,3)">&lt;&lt;</a></li>
		              </s:else>
		         </ul>
		       </s:else>
		       </div></td>
		     </tr>
		</table>
		
		</div>
		<!--Table End-->
	</div>
</body>
</html>
