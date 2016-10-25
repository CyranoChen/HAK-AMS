<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>清算表</title>
  <%@include file="../../static/included.jsp"%>
	<script>
		var queryflag = true;

		var datafield = "";


		$(function (){


			//20161010 导出功能

			$("#export").on("click",function (){
				download("./exp?registration="+$("#registration").val() + "&airlineOfFlight="  +$("#airlineOfFlight").val() );
			});


			buildInput("registration",10,"120");
			buildInput("airlineOfFlight",6,"80");

			$("#loading").jqxLoader({ isModal: true,imagePosition: 'top',width: 100, height: 60 });

			var query = function (data){

				data = isObject(data) ? data : {};

				if(queryflag){
					queryflag = false;
				}
				$("#loading").jqxLoader("open");
				$.post(
						"./query",
						data,
						function (rtn){
							if(rtn){
								var source =
								{
									localdata: rtn.data,
									datatype: "json",
									datafields: rtn.g.datafields,
									id : "registration"
								};

								var dataAdapter = new $.jqx.dataAdapter(source);

								$("#grid").on("bindingcomplete",function (){
									$("#totalNumber").html($(this).jqxGrid('getrows').length + " 条记录");
									$('#loading').jqxLoader('close');
									queryflag = true;
								});



								$("#grid").jqxGrid({
									altrows: true,
									enabletooltips: true,
									width:"100%",
									source : dataAdapter,
									height : "100%",
									autoRowHeight: false,
									sortable:true,
									scrollmode : "logical",
									filterable : true,
									showfilterrow: true,
									selectionMode: "multiplecellsadvanced",
									columns : rtn.g.columns,
									columngroups: rtn.g.groups
								});


							}
						}
				);
			};


			$("#query_button").on("click",function (){

				destroyGrid("grid");
				query({
					registration : $("#registration").val(),
					airlineOfFlight : $("#airlineOfFlight").val()
				});
			});
			query();
		});

	</script>
</head>


<body  style="padding: 10px;min-width: 1024px;">
<%@ include file="./header_tabs.jsp"%>
<div class="panel search" style="height:55px" id="search-panel">
    <div class="main clearfix">
      <div class="clear"></div>
      <div>
		  <table border="0" cellspacing="0" cellpadding="0">
			  <tr>

				  <td class="lb"><label for="registration">机号:</label></td>
				  <td><input id="registration" class="formItem"/></td>
				  <td class="lb"><label for="registration">航空公司:</label></td>
				  <td><input id="airlineOfFlight" class="airlineOfFlight"/></td>
				  <td class="lb">
					  <input type="button" id="query_button" class="find mr8"  />
					  <input type="button" id="export" class="dc" title=导出 />

				  </td>
			  </tr>
		  </table>
      </div>
    </div>
   </div>

<div style="text-align: right;font-weight: bold;font-size: 13px;" id="totalNumber"></div>
<div style="width: 100%;height: 80%;">
	<div id="grid"></div>
</div>


<div id="loading"></div>
	
</body>
</html>
