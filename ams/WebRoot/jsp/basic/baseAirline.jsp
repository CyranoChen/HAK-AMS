<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>航空公司</title>
<%@include file="../../static/included.jsp"%>
<script type="text/javascript">

	var role = "<%=request.getSession().getAttribute("role")%>";

	$(function(){
	$("#airlineIATACode,#airlineICAOCode,#airlineName").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 50 });
	$("#airlineName").jqxInput({width: 140});
	var stringValidation = function(cell,value){
		if(value == ""){
			return true;
		}
		var regx = /^[A-Za-z0-9]{1,10}$/;
		if(!regx.test(value)){
			return { message: "内容必须为1到10位的字母或数字!", result: false };
		}
		return true;
	}
	var airlinesMap = {};
	var airlines;
	var mode = null;
	$.ajax({
			async:false,
   			url:"./findAllAirline",
   			success: function(data){
   			   airlines = data;
               for(var i=0;i<data.length;i++){
            	   airlinesMap[data[i].id] = data[i].airlineName;
               }
             }
         });
	
	var search=function(){
		var source = {
				type : "POST",
	            url : "./queryAirline",
	            datatype : "json",
	            data : {
	            	airlineIATACode : $("#airlineIATACode").val(),
	            	airlineICAOCode : $("#airlineICAOCode").val(),
	            	airlineName : $("#airlineName").val()
	            },
	            id : 'id',
	            datafields : [
	            	{name: 'id',type:'String'},
					{name: 'basicDataID',type:'String'},
					{name: 'airlineIATACode',type:'String'},
					{name: 'airlineICAOCode',type:'String'},
					{name: 'airlineUniqueCode',type:'String'},
					{name: 'airlineName',type:'String'},
					{name: 'airlineHomeCountry',type:'String'},
					{name: 'airlineAllianceGroup',type:'String'},
					{name: 'airlineDescription',type:'String'},
					{name: 'airlineHandler',type:'String'},
					{name: 'airlineCountryType',type:'String'},
					{name: 'parentAirline',type:'String'},
					{name: 'removeFlag',type:'String'},
					{name: 'createUser',type:'String'},
					{name: 'createTime',type:'date'},
					{name: 'modifyUser',type:'String'},
					{name: 'modifyTime',type:'date'},
					{name: 'verifyDescription',type:'String'}	
				]
	        };
	       // var dataAdapter = createDataAdapter(source);
	        var dataAdapter = new $.jqx.dataAdapter(source,
	              	{	
				    	loadServerData: function (serverdata, source, callback) {
				    	     $.ajax({
				    	    	 type : source.type ? source.type : "GET",
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
                columnsResize:true,
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
                      //编辑取消后不提交请求
                    	if(mode == "edit-cancle"){
                    		return;
                    	}
                        var selection = $("#dataTable").jqxDataTable('getSelection');
                    	if(selection.length>0){
                    		var rowData = selection[0];
                    		var url;
                    		if(rowData.id){
                    			url="./updateAirline";
                    		}else{
                    			url="./saveAirline";
                    		}
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
                    updateButtons('End Edit');
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
                            updateButtons('End Edit');
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
            	 	           			"./queryAirlineById",
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
                    	 	           			"./removeAirline",
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
			        {datafield:'basicDataID',align: "center", cellsalign:"center",text:"basicDataID",width:'120px',validation:stringValidation},
			        {datafield:'airlineIATACode',align: "center", cellsalign:"center",text:"航空公司二字码",width:'120px',validation:stringValidation},
			        {datafield:'airlineICAOCode',align: "center", cellsalign:"center",text:"航空公司三字码",width:'130px',validation:stringValidation},
			        {datafield:'airlineUniqueCode',align: "center", cellsalign:"center",text:"航空公司唯一标识",width:'150px',validation:stringValidation},
			        {datafield:'airlineName',align: "center", cellsalign:"center",text:"航空公司名称",width:'240px'},
			        {datafield:'airlineHomeCountry',align: "center", cellsalign:"center",text:"航空公司所属国家",width:'120px',validation:stringValidation},
			        {datafield:'airlineAllianceGroup',align: "center", cellsalign:"center",text:"所属联盟",width:'60px'},
			        {datafield:'airlineDescription',align: "center", cellsalign:"center",text:"航空公司描述",width:'100px'},
			        {datafield:'parentAirline',align: "center", cellsalign:"center",text:"父航空公司",width:'240px',columntype: 'template',width:'240px',
			        	createEditor: function (row, cellvalue, editor, cellText, width, height) {
	                        // construct the editor. 
	                        editor.jqxDropDownList({
	                        	filterable: true, 
	                        	placeHolder : "请选择",
	                        	filterPlaceHolder : "关键字",
	                        	searchMode : 'containsignorecase',
	                        	source: airlines,
	                        	displayMember: 'airlineName',
	                        	valueMember: 'id', 
	                        	width: width,
	                        	height: height
	                        }).on('bindingComplete', function (event) {
	                        });
                   		 },
                    	initEditor: function (row, cellvalue, editor, celltext, width, height) {
	                        // set the editor's current value. The callback is called each time the editor is displayed.
	                        editor.jqxDropDownList({ width: width, height: height });
	                        editor.val(cellvalue);
                    	},
                    	getEditorValue: function (row, cellvalue, editor) {
                        	// return the editor's value.
                        	//alert("getEditorValue");
                       	 return editor.val();
                    	},cellsRenderer: function (row, column, value, rowData) {
        					if(value != null && "" != value){
        						return airlinesMap[value]; 
        					}else{
        						return value;
        					}
        				}
                    },
                    {datafield:'airlineHandler',align: "center", cellsalign:"center",text:"航空公司代理",width:'120px'},
			        {datafield:'airlineCountryType',align: "center", cellsalign:"center",text:"airlineCountryType",width:'90px',hidden:'true'},
			        {datafield:'removeFlag',align: "center", cellsalign:"center",text:"删除标识",width:'90px',hidden:'true'},
			        {datafield:'createUser',align: "center", cellsalign:"center",text:"创建人",width:'90px',hidden:'true'},
			        {datafield:'createTime',align: "center", cellsalign:"center",text:"创建时间",width:'150px',cellsformat:'dd-MM-yyyy',hidden:'true'},
			        {datafield:'modifyUser',align: "center", cellsalign:"center",text:"修改人",width:'150px',hidden:'true'},
			        {datafield:'modifyTime;  aircraftWidth',align: "center", cellsalign:"center",text:"修改时间",width:'150px',cellsformat:'dd-MM-yyyy',hidden:'true'}
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
				<td class="lb"><label for="airlineIATACode">公司二字码</label></td>
				<td><input id="airlineIATACode" class="formItem"></input></td>
				<td class="lb"><label for=airlineICAOCode>公司三字码</label></td>
				<td><input id="airlineICAOCode" class="formItem"></input></td>
				<td class="lb"><label for=airlineName>航空公司名称</label></td>
				<td><input id="airlineName" class="formItem"></input></td>
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