<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机号</title>
<%@include file="../../static/included.jsp"%>
<script type="text/javascript">
	var role = "<%=request.getSession().getAttribute("role")%>";

	$(function(){
	$("#standCode,#standTerminal").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 50 });
	var standCodeValidation = function(cell,value){
		var regx = /^机库$|^[a-zA-Z0-9]{2,}$/;
		if(!regx.test(value)){
			return { message: "内容必须为\"机库\"或者2位以上的字母或数字!", result: false };
		}
		return true;
	}
	var numValidation = function(cell,value){
		var regx = /^[0-9]{1,3}$|^$/;
		if(!regx.test(value)){
			return { message: "内容必须为1到3位的数字!", result: false };
		}
		return true;
	}
	var mode = null;
	var search=function(){
		var source = {
	            url : "./queryStand",
	            datatype : "json",
	            data : {
	            	standCode : $("#standCode").val(),
	            	standTerminal : $("#standTerminal").val()
	            },
	            id : 'id',
	            datafields : [
	            	{name: 'id',type:'String'},
					{name: 'standLength',type:'String'},
					{name: 'standWidth',type:'String'},
					{name: 'basicDataID',type:'String'},
					{name: 'standCode',type:'String'},
					{name: 'standTerminal',type:'String'},
					{name: 'standCapacity',type:'String'},
					{name: 'standDescription',type:'String'},
					{name: 'standType',type:'String'},
					{name: 'standMaxTailHeight',type:'String'},
					{name: 'standMaxWeight',type:'String'},
					{name: 'standBridgesNumber',type:'String'},
					{name: 'standBoardingType',type:'String'},
					{name: 'standTow',type:'String'},
					{name: 'removeFlag',type:'String'},
					{name: 'createUser',type:'String'},
					{name: 'createTime',type:'date'},
					{name: 'modifyUser',type:'String'},
					{name: 'modifyTime',type:'Date'},
					{name: 'standRefGate',type:'String'}
				]
	        };
	        var dataAdapter = createDataAdapter(source);
	        var dataAdapter = new $.jqx.dataAdapter(source,
	              	{	
				    	loadServerData: function (serverdata, source, callback) {
				    	     $.ajax({
				    	         dataType: source.datatype,
				    	         url: source.url,
				    	         data: serverdata,
				    	         success: function (data, status, xhr) {
				    	        	 if(data){
											if(isArray(data)){
												callback({records:data});
											}else if(isObject(data)) {
												if(data.content){
													var rtn = {records: data.content, totalCount: data.pageInfo.totalRecord};
													//alert(JSON.stringify(rtn));
													callback(rtn);
												}
											}else{
												callback({records : []});
											}
										}
				    	         }
				    	     })
				    	},
				    	
				    	loadComplete: function (data, status, xhr) {
				            source.totalRecords = data.totalCount;
				        },
				        loadError: function (xhr, status, error) {
				            throw new Error("http://services.odata.org: " + error.toString());
				        }
			       });
	        $("#dataTable").jqxDataTable({
	        	width: "100%",
                height : "100%",
                theme:'arctic',
                source: dataAdapter,
                sortable : false,
                editable: true,
                pageable: true,
                serverProcessing: true,
                pagerButtonsCount: 5,
  		     	pageSize : 50,
  		     	selectionMode: 'singlerow',
                altRows: true,
                showToolbar: true,
                editSettings: {
                    saveOnPageChange: true,
                    saveOnBlur: false,
                    saveOnSelectionChange: true,
                    cancelOnEsc: true,
                    saveOnEnter: true,
                    editOnDoubleClick: false,
                    editOnF2: true
                },
                renderToolbar: function(toolBar)
                {
					if(role != "1"){
						return;
					}
                    // appends buttons to the status bar.
                    var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
                    var buttonTemplate = "<div style='float: left; padding: 3px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";
                    //var addButton = $(buttonTemplate);
                    var editButton = $(buttonTemplate);
                    var deleteButton = $(buttonTemplate);
                    var cancelButton = $(buttonTemplate);
                    var updateButton = $(buttonTemplate);
                    //container.append(addButton);
                    container.append(editButton);
                    container.append(deleteButton);
                    container.append(cancelButton);
                    container.append(updateButton);
                    toolBar.append(container);
                    /*
                    addButton.jqxButton({cursor: "pointer", enableDefault: false,  height: 25, width: 25 });
                    addButton.find('div:first').addClass('jqx-icon-plus');
                    addButton.jqxTooltip({ position: 'bottom', content: "新增"});
                    */
                    editButton.jqxButton({ cursor: "pointer", disabled: true, enableDefault: false,  height: 25, width: 25 });
                    editButton.find('div:first').addClass('jqx-icon-edit');
                    editButton.jqxTooltip({ position: 'bottom', content: "编辑"});
                    deleteButton.jqxButton({ cursor: "pointer", disabled: true, enableDefault: false,  height: 25, width: 25 });
                    deleteButton.find('div:first').addClass('jqx-icon-delete');
                    deleteButton.jqxTooltip({ position: 'bottom', content: "删除"});
                    updateButton.jqxButton({ cursor: "pointer", disabled: true, enableDefault: false,  height: 25, width: 25 });
                    updateButton.find('div:first').addClass('jqx-icon-save');
                    updateButton.jqxTooltip({ position: 'bottom', content: "保存修改"});
                    cancelButton.jqxButton({ cursor: "pointer", disabled: true, enableDefault: false,  height: 25, width: 25 });
                    cancelButton.find('div:first').addClass('jqx-icon-cancel');
                    cancelButton.jqxTooltip({ position: 'bottom', content: "取消"});
                    var updateButtons = function (action) {
                        switch (action) {
                            case "Select":
                               // addButton.jqxButton({ disabled: false });
                                deleteButton.jqxButton({ disabled: false });
                                editButton.jqxButton({ disabled: false });
                                cancelButton.jqxButton({ disabled: true });
                                updateButton.jqxButton({ disabled: true });
                                break;
                            case "Unselect":
                                //addButton.jqxButton({ disabled: false });
                                deleteButton.jqxButton({ disabled: true });
                                editButton.jqxButton({ disabled: true });
                                cancelButton.jqxButton({ disabled: true });
                                updateButton.jqxButton({ disabled: true });
                                break;
                            case "Edit":
                                //addButton.jqxButton({ disabled: true });
                                deleteButton.jqxButton({ disabled: true });
                                editButton.jqxButton({ disabled: true });
                                cancelButton.jqxButton({ disabled: false });
                                updateButton.jqxButton({ disabled: false });
                                break;
                            case "End Edit":
                                //addButton.jqxButton({ disabled: false });
                                deleteButton.jqxButton({ disabled: false });
                                editButton.jqxButton({ disabled: false });
                                cancelButton.jqxButton({ disabled: true });
                                updateButton.jqxButton({ disabled: true });
                                break;
                        }
                    }
                    var rowIndex = null;
                    $("#dataTable").on('rowSelect', function (event) {
                        var args = event.args;
                        rowIndex = args.index;
                        updateButtons('Select');
                    });
                    $("#dataTable").on('rowUnselect', function (event) {
                        updateButtons('Unselect');
                    });
                    $("#dataTable").unbind('rowEndEdit').on('rowEndEdit', function (event) {
                        updateButtons('End Edit');
                        //编辑取消后不提交请求
                    	if(mode == "edit-cancle"){
                    		return;
                    	}
                    	var selection = $("#dataTable").jqxDataTable('getSelection');
                        if(selection.length>0){
                    		var rowData = selection[0];
                    		var url;
                    		if(rowData.id){
                    			url="./updateStand";
                    		}else{
                    			url="./saveStand";
                    		}
                    		/*
                    		var data = "{";
                    		for(var key in rowData){
                    			data += key + ":" + rowData[key] + ",";
                    		}
                    		data +="}"
                    		alert(data);
                    		*/
                    		$.post(
        	 	           			url,
        	 	           			rowData,
        		                    function(obj){
        	 	           				//alert(JSON.stringify(obj));
        		                    	if(obj.info.success){
        		                    		alert("保存成功！");
        		                    	}else{
        		                    		alert("保存失败！");
        		                    	}     		
        	 	                });	
                        }
                    });
                    $("#dataTable").on('rowBeginEdit', function (event) {
                        updateButtons('Edit');
                    });
                    
                    /*
                    addButton.click(function (event) {
                        if (!addButton.jqxButton('disabled')) {
                            // add new empty row.
                            $("#dataTable").jqxDataTable('addRow', null, {}, 'first');
                            // select the first row and clear the selection.
                            $("#dataTable").jqxDataTable('clearSelection');
                            $("#dataTable").jqxDataTable('selectRow', 0);
                            // edit the new row.
                            $("#dataTable").jqxDataTable('beginRowEdit', 0);
                            updateButtons('add');
                        }
                    });
                    */
                    
                    cancelButton.click(function (event) {
                        if (!cancelButton.jqxButton('disabled')) {
                            // cancel changes.
                        	if(null!=mode && mode=="edit"){
                            	mode = "edit-cancle";
                            }
                            $("#dataTable").jqxDataTable('endRowEdit', rowIndex, true);
                        }
                    });
                    updateButton.click(function (event) {
                        if (!updateButton.jqxButton('disabled')) {
                        	$("#dataTable").jqxDataTable('endRowEdit', rowIndex, false);
                        }
                    });
                    editButton.click(function () {
                        if (!editButton.jqxButton('disabled')) {
                        	mode = "edit";
                        	var selection = $("#dataTable").jqxDataTable('getSelection');
                        	if(selection.length>0){
                        		var rowData = selection[0];   
                        		$.post(
            	 	           			"./queryStandById",
            		                    data = {
            		           				id:rowData.id,
            		         			},
            		                    function(obj){
            		                    	if(obj){
            		                    		$("#dataTable").jqxDataTable('beginRowEdit', rowIndex);
            		                    	}else{
            		                    		alert("本条数据已删除，请重新查询！");
            		                    	}     		
            	 	                });	
                        	}
                            updateButtons('edit');
                        }
                    });
                    deleteButton.click(function () {
                        if (!deleteButton.jqxButton('disabled')) {
                        	var selection = $("#dataTable").jqxDataTable('getSelection');
                        	if(selection.length > 0){
                               	   if(confirm("确定要删除吗？")){
                               		   for (var i = 0; i < selection.length; i++) {
                               		   	    var rowData = selection[i];    
                    	           		   	$.post(
                    	 	           			"./removeStand",
                    		                    data = {
                    		           				id:rowData.id,
                    		         			},
                    		                    function(obj){
                    		                    	if(obj){
                    		                    		$("#dataTable").jqxDataTable('updateBoundData');
                    		                    		alert("删除成功！");
                    		                    	}else{
                    		                    		alert("删除失败！");
                    		                    	}     		
                    	 	                });	
                               		   }
                          	   	   } 
                        	}else{
                        		alert("请至少选择一行数据");
                        	}	
                            //$("#dataTable").jqxDataTable('deleteRow', rowIndex);
                            //updateButtons('delete');
                        }
                    });
                },
                columns :[
					{datafield:'id',align: "center", cellsalign:"center",text:"id",width:'100px',editable:false},
			        {datafield:'basicDataID',align: "center", cellsalign:"center",text:"basicDataID",width:'150px',hidden:true},
			        {datafield:'standCode',align: "center", cellsalign:"center",text:"机位号",width:'100px',validation:standCodeValidation},
			        {datafield:'standTerminal',align: "center", cellsalign:"center",text:"机位所属候机楼",width:'100px',validation:numValidation},
			        {datafield:'standCapacity',align: "center", cellsalign:"center",text:"机位容积",width:'100px',validation:numValidation},
			        {datafield:'standDescription',align: "center", cellsalign:"center",text:"机位描述说明",width:'140px'},
			        {datafield:'standLength',align: "center", cellsalign:"center",text:"机位长度",width:'120px',validation:numValidation},
			        {datafield:'standWidth',align: "center", cellsalign:"center",text:"机位宽度",width:'120px',validation:numValidation},
			        {datafield:'standType',align: "center", cellsalign:"center",text:"机位类型",width:'100px'},
			        {datafield:'standMaxTailHeight',align: "center", cellsalign:"center",text:"机位最大装箱高度",width:'140px',validation:numValidation},
			        {datafield:'standMaxWeight',align: "center", cellsalign:"center",text:"机位最大承载重量",width:'140px',validation:numValidation},
			        {datafield:'standBridgesNumber',align: "center", cellsalign:"center",text:"机位桥位数量",width:'120px',validation:numValidation},
			        {datafield:'standBoardingType',align: "center", cellsalign:"center",text:"机位登机类型",width:'90px'},
			        {datafield:'standTow',align: "center", cellsalign:"center",text:"机位牵引信息",width:'90px'},
			        {datafield:'removeFlag',align: "center", cellsalign:"center",text:"删除标识",width:'150px',hidden:'true'},
			        {datafield:'createUser',align: "center", cellsalign:"center",text:"创建人",width:'150px',hidden:'true'},
			        {datafield:'createTime',align: "center", cellsalign:"center",text:"创建时间",width:'150px',hidden:'true'},
			        {datafield:'modifyUser',align: "center", cellsalign:"center",text:"修改人",width:'90px',hidden:'true'},
			        {datafield:'modifyTime',align: "center", cellsalign:"center",text:"修改时间",width:'90px',hidden:'true'},
			        {datafield:'standRefGate',align: "center", cellsalign:"center",text:"standRefGate",width:'90px',hidden:true}
				]
            }).on("bindingcomplete",function (){

                //$("#totalNumber").html($(this).jqxGrid('getrows').length + " 条记录");
                //setTimeout(function (){resize();},1000);

            })
	}
	$("#query_button").click(function (){
		$("#dataTable").each(function (){
			$(this).jqxDataTable("destroy");
		});
		$("#dataTable-panel").append($("<div id='dataTable'></div>"));
		search();
	});
	search();
});
</script>
</head>
<body style="padding: 10px;min-width: 1024px;">
<%@ include file="./headerNavigation.jsp"%>
<div class="panel search" style="height:55px" id="search-panel">
	<div class="main clearfix">
		<div>
			<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="lb"><label for="standCode">机位号</label></td>
				<td><input id="standCode" class="formItem"></input></td>
				<td class="lb"><label for=standTerminal>所属航站楼</label></td>
				<td><input id="standTerminal" class="formItem"></input></td>
				<td class="lb">
	              <input type="button" id="query_button" class="find mr8"  />
           		 </td>
			</tr>
			</table>
		</div>
	</div>
</div>

<div id="totalNumber" style="text-align: right;font-weight: bold;font-size: 13px;">&nbsp;</div>

    <div id="dataTable-panel"  style="height:80%;">
      <div id="dataTable"></div>
</div>
</body>
</html>