<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html >
<html lang="en">
<head>
<meta charset="utf-8" />
<title>接口调用日志</title>
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
	sonWindow = window.open('viewLog.action?id='+id);
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

function showAutoEngine(){
	window.location.href="/msgCenter/autoEngine/showIndex.action";
}

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
				<li class="fin">接口调用&gt;&gt;日志列表</li>
			</ul>
		</div>
	</div>	
		<div class="filter">
			<div class="query p8" id="searchArea">
			<s:form action="showLogList" name="autoEngine" namespace="/autoEngine" method="post" id="form">
			<input type="hidden" name="pageNo" id="pageNo" value="<s:property value='#request.page.currentPage'/>"/>		
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr id="search">									
						<td class="t_r">调用者</td>
		        	    <td>
		        	      	<input type="text" name="caller" id="caller" class="input_large"  value="<s:property value="#request.caller"/>">
		        	    </td>
						<td class="t_r">调用方式</td>
		        	    <td>
		        	    	<select name="callWay" class="input_large">														
								<option value="" <s:if test="#request.callWay==null">selected="selected"</s:if>	>---请选择---</option>
								<option value="manual" <s:if test="#request.callWay=='manual'">selected="selected"</s:if>>手动调用</option>
								<option value="loop" <s:if test="#request.callWay=='loop'">selected="selected"</s:if>>自动轮询调用</option>
								<option value="trigger" <s:if test="#request.callWay=='trigger'">selected="selected"</s:if>>自动触发调用</option>																								
							</select>		        	      	
		        	    </td>	
						<td class="t_r">调用时间</td>
		        	    <td colspan="2">
		        	      	<input type="text" id="callTimes" name="callTimes" class="input_large" placeholder="eg:yyyy-MM-dd" value="<s:property value="#request.callTimes"/>">
		        	       -
		                    <input type="text" id="callTimee" name="callTimee" class="input_large" placeholder="eg:yyyy-MM-dd"  value="<s:property value='#request.callTimee'/>">
		                 </td>				        	    																							
					</tr>
					<tr id="search">	
						<td class="t_r">调用方法</td>
		        	    <td>
		        	      	<select name="callMethod" class="input_large">														
								<option value="" <s:if test="#request.callMethod==null">selected="selected"</s:if>	>---请选择---</option>
								<option value="get" <s:if test="#request.callMethod=='get'">selected="selected"</s:if>>查询</option>
								<option value="sync" <s:if test="#request.callMethod=='sync'">selected="selected"</s:if>>同步</option>
								<option value="set" <s:if test="#request.callMethod=='set'">selected="selected"</s:if>>更新</option>								
								<option value="autoEngine" <s:if test="#request.callMethod=='autoEngine'">selected="selected"</s:if>>自动引擎</option>																								
							</select>
		        	    </td>					
						<td class="t_r" style="white-space:nowrap">接口名称</td>
						<td style="white-space:nowrap">
							<select name="apiId" class="input_large">					
							<option value="" <s:if test="#request.apiId==null">selected="selected"</s:if>>---请选择---</option>								
							<s:iterator value="#request.apiIds" id="api" status="st">
								<option value="<s:property value="#api"/>" <s:if test="#request.apiId==#api">selected="selected"</s:if> ><s:property value="#request.hmApiName[#api]"/>接口</option>
							</s:iterator>								
							</select>
						</td>	
						<td class="t_r" style="white-space:nowrap">调用结果</td>
						<td style="white-space:nowrap">
							<select name="callResult" class="input_large">														
									<option value="" <s:if test="#request.callResult==null">selected="selected"</s:if>	>---请选择---</option>
									<option value="success" <s:if test="#request.callResult=='success'">selected="selected"</s:if>>成功</option>
									<option value="failure" <s:if test="#request.callResult=='failure'">selected="selected"</s:if>>失败</option>														
							</select>
						</td>												        	     
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
				<th colspan="4">&nbsp;&nbsp;日志列表</th>
				<th colspan="5" class="t_r"> 
					<input type="button" value="自动化引擎" onclick="showAutoEngine()"/> &nbsp;				
					<input type="button" value="关 闭" onclick="window.close();"/> &nbsp;
				
				</th>
			</thead>
			<tbody>
				<tr class="tit">
					<td width="3%;" style="white-space:nowrap;">序号</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">调用时间</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">结束时间</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">调用者</td>	
					<td width="5%;"  class="t_r" style="white-space:nowrap;">调用方式</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">调用方法</td>
					<td width="10%;" class="t_r" style="white-space:nowrap;">接口名称</td>					

					<td width="5%;"  class="t_r" style="white-space:nowrap;">调用结果</td>
					<td width="5%;"  class="t_r" style="white-space:nowrap;">操作</td>					
				</tr>
				<s:iterator value="#request.page.result" id="log" status="st">
					<tr>
						<td class="t_c" style="white-space: nowrap;">
							<s:property value="#st.index+#request.page.startRow+1"/>
						</td>
						<td class="t_c" style="white-space: nowrap;">
							<s:date name="#log.callTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>  
						<td class="t_c" style="white-space: nowrap;">
							<s:date name="#log.finishTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>  						                        
                        <td class="t_l" style="white-space: nowrap;">
                        	<s:property value="#log.caller"/>
                        </td>
                        <td class="t_l" style="white-space: nowrap;">
                        	<s:if test="#log.callWay=='manual'">手动调用</s:if>
							<s:elseif test="#log.callWay=='loop'">自动轮询调用</s:elseif>     
							<s:elseif test="#log.callWay=='trigger'">自动触发调用</s:elseif>    							                   
                        </td>
                        <td class="t_l" style="white-space: nowrap;">
                            <s:if test="#log.callMethod=='get'">查询</s:if>
							<s:elseif test="#log.callMethod=='sync'">同步</s:elseif>
							<s:elseif test="#log.callMethod=='set'">更新</s:elseif>
							<s:elseif test="#log.callMethod=='autoEngine'">自动化引擎</s:elseif>
                        </td>                        
                        <td class="t_l" style="white-space: nowrap;">
                        	<s:property value="#request.hmApiName[#log.apiId]"/>接口
                        </td>
                        <td class="t_c" style="white-space: nowrap;">
                        	<s:if test="#log.callResult=='success'">成功</s:if>
							<s:elseif test="#log.callResult=='failure'">失败</s:elseif>
                        </td>                        
						<td class="t_c" style="white-space: nowrap;">
							<input type="button" value="查看" onclick="showViewPage( '<s:property value="#log.id"/>');"/>
						</td>  											
					</tr>
				</s:iterator>
			</tbody>
			
			<tr class="tfoot">
			      <td colspan="9"><div class="clearfix">
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
