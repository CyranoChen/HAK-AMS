<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>飞机所属航空公司维护</title>
<%@include file="../../static/included.jsp"%>

	<script src="../../static/uploadifive/jquery.uploadifive.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../../static/uploadifive/uploadifive.css">

<script type="text/javascript">

var role = "<%=request.getSession().getAttribute("role")%>";
$(function(){
	var stringValidation = function(cell,value){
		var regx = /^[A-Za-z0-9]{2,10}$/;
		if(!regx.test(value)){
			return { message: "内容必须为2-10位字母或数字", result: false };
		}
		return true;
	}
	var string1_3Validation = function(cell,value){
		var regx = /^[A-Za-z0-9]{1,3}$/;
		if(!regx.test(value)){
			return { message: "内容必须为1-3位字母或数字", result: false };
		}
		return true;
	}
	var aircrafRegiTypeValidation = function(cell,value){
		var regx = /^[A-Za-z0-9-]{4,15}$/;
		if(!regx.test(value)){
			return { message: "内容必须为4-15位字母、数字、中划线", result: false };
		}
		return true;
	}
	var numValidation = function(cell,value){
		var regx = /^[0-9]{1,3}$|^$/;
		if(!regx.test(value)){
			return { message: "内容必须为1到3位的数字", result: false };
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
	
	$("#aircraftRegistration,#id,#currentAirline,#currentSubAirline,#airlineOfFlight,#aircraftTypeICAOCode,#aircraftSeatCapacity,#aircraftTakeoffWeight,#aircraftPayload").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 50 });
	$("#isWideOrNarrow,#isHighDensity").jqxDropDownList({ width: 70, height: 25,dropDownHeight:75,theme:"arctic"});
	$("#validTime").jqxDateTimeInput({
        formatString: "yyyy-MM-dd",
        showCalendarButton: true,
        allowKeyboardDelete : false,
        width: 100,
        height: 25,
        theme:"arctic",
        culture: 'zh-CN',
        allowNullDate: false,
        showFooter: true,
        selectionMode: 'default',
        todayString: "今天",
        clearString: ""
    });
	var first = true;
	var search=function(){
		var source = {
				type : "POST",
	            url : "./queryAircraftAirlineAlteration",
	            datatype : "json",
	            data : {
	            	id                   : $("#id").val(),
	            	aircraftRegistration : $("#aircraftRegistration").val(),  
	            	currentAirline       : $("#currentAirline").val(),
	            	currentSubAirline    : $("#currentSubAirline").val(),
	            	airlineOfFlight      : $("#airlineOfFlight").val(),
	            	aircraftTypeICAOCode : $("#aircraftTypeICAOCode").val(),
	            	aircraftSeatCapacity : $("#aircraftSeatCapacity").val(),
	            	aircraftTakeoffWeight: $("#aircraftTakeoffWeight").val(),
	            	aircraftPayload      : $("#aircraftPayload").val(),
	            	isWideOrNarrow       : $("#isWideOrNarrow").val(),
	                isHighDensity        : $("#isHighDensity").val(),
	                validTime            : $("#validTime").val()
	            },
	            id : 'id',
	            datafields : [
	            	{name: 'id',type:'String'},
					{name: 'aircraftRegistration',type:'String'},
					{name: 'originalAirline',type:'String'},
					{name: 'originalSubairline',type:'String'},
					{name: 'currentAirline',type:'String'},
					{name: 'currentSubairline',type:'String'},
					{name: 'airlineOfFlight',type:'String'},
					{name: 'startDate',type:'date'},
					{name: 'endDate',type:'date'},
					{name: 'validFlag',type:'String'},
					{name: 'remark',type:'String'},
					{name: 'removeFlag',type:'String'},
					{name: 'createUser',type:'String'},
					{name: 'createTime',type:'String'},
					{name: 'modifyUser',type:'String'},
					{name: 'modifyTime',type:'String'},
					{name: 'aircraftLeasingAirline',type:'String'},
					{name: 'aircraftTypeICAOCode',type:'String'},
					{name: 'aircraftTypeIATACode',type:'String'},
					{name: 'aircraftType',type:'String'},
					{name: 'aircraftAirline',type:'String'},
					{name: 'aircraftDescription',type:'String'},
					{name: 'aircraftEngineNumber',type:'String'},
					{name: 'aircraftTakeoffWeight',type:'String'},
					{name: 'aircraftLandingWeight',type:'String'},
					{name: 'aircraftPayload',type:'String'},
					{name: 'aircraftSeatCapacity',type:'String'},
					{name: 'aircraftNoiseCategory',type:'String'},
					{name: 'aircraftHeight',type:'String'},
					{name: 'aircraftLength',type:'String'},
					{name: 'aircraftWidth',type:'String'},
					{name: 'basicDataID',type:'String'},
					{name: 'isWideOrNarrow',type:'String'},
					{name: 'isHighDensity',type:'String'},
					{name: 'isPackagingFacility',type:'String'}
				]
	        };
		
		var airlineSource = {
	            url : "./findAllAirline",
	            datatype : "json",
	            id : 'id',
	            datafields : [
	            	{name: 'id',type:'String'},
					{name: 'airlineName',type:'String'}
				]
	        };

	        //var dataAdapter = createDataAdapter(source);
	        var dataAdapter = new $.jqx.dataAdapter(source,
              	{	
			    	loadServerData: function (serverdata, source, callback) {
			    	     $.ajax({
			    	    	 type : source.type ? source.type : "GET",
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
			            throw new Error(error.toString());
			        }
		       });
			if(first){
				first = false;
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
	                autoRowHeight: false,
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
							return ;
						}

	                    // appends buttons to the status bar.
	                    var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
	                    var buttonTemplate = "<div style='float: left; padding: 3px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";
	                    var addButton = $(buttonTemplate);
	                    var editButton = $(buttonTemplate);
	                    var deleteButton = $(buttonTemplate);
	                    var cancelButton = $(buttonTemplate);
	                    var updateButton = $(buttonTemplate);
	                    container.append(addButton);
	                    container.append(editButton);
	                    container.append(deleteButton);
	                    container.append(cancelButton);
	                    container.append(updateButton);
	                    toolBar.append(container);
	                    
	                    addButton.jqxButton({cursor: "pointer",disabled: false,enableDefault: false,  height: 25, width: 25 });
	                    addButton.find('div:first').addClass('jqx-icon-plus');
	                    addButton.jqxTooltip({ position: 'bottom', content: "新增"});
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
	                                addButton.jqxButton({ disabled: false });
	                                deleteButton.jqxButton({ disabled: false });
	                                editButton.jqxButton({ disabled: false });
	                                cancelButton.jqxButton({ disabled: true });
	                                updateButton.jqxButton({ disabled: true });
	                                break;
	                            case "Unselect":
	                                addButton.jqxButton({ disabled: false });
	                                deleteButton.jqxButton({ disabled: true });
	                                editButton.jqxButton({ disabled: true });
	                                cancelButton.jqxButton({ disabled: true });
	                                updateButton.jqxButton({ disabled: true });
	                                break;
	                            case "Edit":
	                                addButton.jqxButton({ disabled: true });
	                                deleteButton.jqxButton({ disabled: true });
	                                editButton.jqxButton({ disabled: true });
	                                cancelButton.jqxButton({ disabled: false });
	                                updateButton.jqxButton({ disabled: false });
	                                break;
	                            case "End Edit":
	                                addButton.jqxButton({ disabled: false });
	                                deleteButton.jqxButton({ disabled: false });
	                                editButton.jqxButton({ disabled: false });
	                                cancelButton.jqxButton({ disabled: true });
	                                updateButton.jqxButton({ disabled: true });
	                                break;
	                            case "Search":
	                            	addButton.jqxButton({ disabled: false });
	                                deleteButton.jqxButton({ disabled: true });
	                                editButton.jqxButton({ disabled: true });
	                                cancelButton.jqxButton({ disabled: true });
	                                updateButton.jqxButton({ disabled: true });
	                                break;	
	                        }
	                    }
	                    var currentPageNum = 0;
	                    $('#dataTable').on('pageChanged', function (event){
                   		    // event args.
                   		    var args = event.args;
                   		    // page num.
                   		    currentPageNum = args.pagenum;
	                    });
	                    var rowIndex = null;
	                    $("#dataTable").on('rowSelect', function (event) {
	                        var args = event.args;
	                        rowIndex = args.index;
	                        updateButtons('Select');
	                    });
	                    $("#dataTable").on('rowUnselect', function (event) {
	                    	$("#dataTable").jqxDataTable('endRowEdit', rowIndex, true);
	                        updateButtons('Unselect');
	                    });
	                    $("#dataTable").unbind('rowEndEdit').on('rowEndEdit', function (event) {

	                    	var selection = $("#dataTable").jqxDataTable('getSelection');
	                    	var data = event.args.row
	                    	if(data && !data.id && !data.aircraftRegistration){
	                    		$("#dataTable").jqxDataTable('deleteRow', rowIndex);
	                    		return;
	                    	}
	                    	//编辑取消后不提交请求
	                    	if(mode == "edit-cancle"){
	                    		return;
	                    	}
	                    	if(selection.length>0){
	                    		var rowData = selection[0];
	                    		var url;
	                    		if(rowData.id){
	                    			url="./updateAircraftAirlineAlteration";
	                    		}else{
	                    			url="./saveAircraftAirlineAlteration";
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
	        		                    		//$("#dataTable").jqxDataTable('updateBoundData');
	        		                    		alert("保存成功！");
												search();
	        		                    		updateButtons('End Edit');
	        		                    	}else{
	        		                    		alert("保存失败！");
	        		                    		updateButtons('End Edit');
	        		                    	}     		
	        	 	                });	
	                        }
	                    });
	                    $("#dataTable").on('rowBeginEdit', function (event) {
	                        updateButtons('Edit');
	                    });
	                    $("#dataTable").unbind('bindingComplete').on('bindingComplete',function(event){
	                    	if(mode && mode=="add"){
	                    		mode=null;
		                    	$("#dataTable").jqxDataTable('addRow', '', {}, 'first');
	                            // select the first row and clear the selection.
	                            //$("#dataTable").jqxDataTable('clearSelection');
	                            //$("#dataTable").jqxDataTable('selectRow', 0);                          
	                            // edit the new row.
	                            $("#dataTable").jqxDataTable('beginRowEdit', 0);
	                            /*
	                            updateButtons('add');
	                            */
	                    	}
	                    })
	                    
	                    addButton.click(function (event) {
	                        if (!addButton.jqxButton('disabled')) {
	                        	mode = "add";
	                            // add new empty row.
	                            if(currentPageNum != 0){
	                            	$("#dataTable").jqxDataTable('goToPage', 0);
	                            }else{
	                            	mode=null;
	                            	$("#dataTable").jqxDataTable('addRow', '', {}, 'first');
		                            // select the first row and clear the selection.
		                            $("#dataTable").jqxDataTable('clearSelection');
		                            $("#dataTable").jqxDataTable('selectRow', 0);
		                            // edit the new row.
		                            $("#dataTable").jqxDataTable('beginRowEdit', 0);
		                            updateButtons('add');
	                            }  
	                        }
	                    });
	                    
	                    
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
	                        	//updateButtons('End Edit');
	                        }
	                    });
	                    editButton.click(function () {
	                        if (!editButton.jqxButton('disabled')) {
	                        	mode = "edit";
	                        	var selection = $("#dataTable").jqxDataTable('getSelection');
	                        	if(selection.length>0){
	                        		var rowData = selection[0];   
	                        		$.post(
	            	 	           			"./queryAircraftAirlineAlterationById",
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
	                            //updateButtons('Edit');
	                        }
	                    });
	                    deleteButton.click(function () {
	                        if (!deleteButton.jqxButton('disabled')) {
	                        	var selection = $("#dataTable").jqxDataTable('getSelection');
	                        	if(selection.length > 0){
	                               	   if(confirm("确定要删除吗？") && "" != selection[0].id){
	                               		   for (var i = 0; i < selection.length; i++) {
	                               		   	    var rowData = selection[i];    
	                    	           		   	$.post(
	                    	 	           			"./removeAircraftAirlineAlteration",
	                    		                    data = {
	                    		           				id:rowData.id,
	                    		         			},
	                    		                    function(obj){
	                    		                    	if(obj){
	                    		                    		$("#dataTable").jqxDataTable('updateBoundData');
	                    		                    		alert("删除成功！");
	                    		                    		updateButtons('delete');
	                    		                    	}else{
	                    		                    		alert("删除失败！");
	                    		                    	}     		
	                    	 	                });	
	                               		   }
	                          	   	   }
	                               	   /*
	                               	   else{
	                          	   		$("#dataTable").jqxDataTable('deleteRow', rowIndex);
	                          	   		updateButtons('delete');
	                          	   	   } 
	                               	   */
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
				        {datafield:'aircraftRegistration',align: "center", cellsalign:"center",text:"机号",width:'120px',validation: stringValidation},
				        {datafield:'originalAirline',align: "center", cellsalign:"center",text:"原属航空公司",width:'120px',hidden:"true"},
				        {datafield:'originalSubairline',align: "center", cellsalign:"center",text:"原属航空分公司",width:'150px',hidden:"true"},
				        {datafield:'currentAirline',align: "center", cellsalign:"center",text:"现属航空公司",columntype: 'template',width:'240px',
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
		                        	selectedIndex: 1,
		                        	width: width,
		                        	height: height
		                        });
	                   		 },
	                    	initEditor: function (row, cellvalue, editor, celltext, width, height) {
		                        // set the editor's current value. The callback is called each time the editor is displayed.
		                        editor.jqxDropDownList({ width: width, height: height });
		                        //alert(cellvalue);
		                        if(cellvalue){
		                        	editor.val(cellvalue);
		                        }else{
		                        	//alert("");
		                        	editor.val({}); 
		                        }          
	                    	},
	                    	getEditorValue: function (row, cellvalue, editor) {
	                        	// return the editor's value.
	                       	 return editor.val();
	                    	},cellsRenderer: function (row, column, value, rowData) {
	                    		if(value != null && "" != value){
		    						return airlinesMap[value]; 
		    					}else{
		    						return '<div style="min-height: 18px">' + value + '</div>';
		    					}
	        				}
	                    },
				        {datafield:'currentSubairline',align: "center", cellsalign:"center",text:"现属航空分公司",columntype: 'template',width:'240px',
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
		                        	selectedIndex: 1,
		                        	width: width,
		                        	height: height
		                        })                       
	               		 	},
		                	initEditor: function (row, cellvalue, editor, celltext, width, height) {
		                        // set the editor's current value. The callback is called each time the editor is displayed.
		                        editor.jqxDropDownList({ width: width, height: height });
		                        if(cellvalue){
		                        	editor.val(cellvalue);
		                        }else{
		                        	editor.val({}); 
		                        }
		                            
		                	},
		                	getEditorValue: function (row, cellvalue, editor) {
		                    	// return the editor's value.
		                   	 return editor.val();
		                	},cellsRenderer: function (row, column, value, rowData) {
		    					if(value != null && "" != value){
		    						return airlinesMap[value]; 
		    					}else{
		    						return '<div style="min-height: 18px">' + value + '</div>';
		    					}
		    				}
	                	},
				        {datafield:'airlineOfFlight',align: "center", cellsalign:"center",text:"航空公司二字码",width:'100px',validation: string1_3Validation},
				        {datafield:'startDate',align: "center", cellsalign:"center",text:"开始日期",width:'100px',columntype: 'template',
	                    	createEditor: function (row, cellvalue, editor, cellText, width, height) {
		                        // construct the editor. 
		                        editor.jqxDateTimeInput({
		                            formatString: "yyyy-MM-dd",
		                            showCalendarButton: true,
		                            allowKeyboardDelete : false,
		                            width: 100,
		                            height: 25,
		                            theme:"arctic",
		                            culture: 'zh-CN',
		                            allowNullDate: false,
		                            showFooter: true,
		                            selectionMode: 'default',
		                            todayString: "今天",
		                            clearString: ""
		                        })
	               		 	},
		                	initEditor: function (row, cellvalue, editor, celltext, width, height) {
		                        // set the editor's current value. The callback is called each time the editor is displayed.
		                        editor.jqxDateTimeInput({ width: width, height: height });
								if(cellvalue){
									editor.val(cellvalue);	
								}
		                	},
		                	getEditorValue: function (row, cellvalue, editor) {
		                    	// return the editor's value.
		                   	 	return editor.val();
		                	}
				        },
				        {datafield:'endDate',align: "center", cellsalign:"center",text:"结束日期",width:'100px',columntype: 'template',
	                    	createEditor: function (row, cellvalue, editor, cellText, width, height) {
		                        // construct the editor. 
		                        editor.jqxDateTimeInput({
		                            formatString: "yyyy-MM-dd",
		                            showCalendarButton: true,
		                            allowKeyboardDelete : false,
		                            width: 100,
		                            height: 25,
		                            theme:"arctic",
		                            culture: 'zh-CN',
		                            allowNullDate: false,
		                            showFooter: true,
		                            selectionMode: 'default',
		                            todayString: "今天",
		                            clearString: ""
		                        })
	               		 	},
		                	initEditor: function (row, cellvalue, editor, celltext, width, height) {
		                        // set the editor's current value. The callback is called each time the editor is displayed.
		                        editor.jqxDateTimeInput({ width: width, height: height });
								if(cellvalue){
									editor.val(cellvalue);	
								}
		                	},
		                	getEditorValue: function (row, cellvalue, editor) {
		                    	// return the editor's value.
		                   	 return editor.val();
		                	}
				        },
				        {datafield:'aircraftTypeICAOCode',align: "center", cellsalign:"center",text:"机型三字码",width:'90px'/*,validation: aircrafRegiTypeValidation*/},
				        {datafield:'aircraftTypeIATACode',align: "center", cellsalign:"center",text:"机型二字码",width:'90px'/*,validation: aircrafRegiTypeValidation*/},
				        {datafield:'basicDataID',align: "center", cellsalign:"center",text:"basicDataID",width:'90px',hidden:true},
				        {datafield:'aircraftSeatCapacity',align: "center", cellsalign:"center",text:"最大客座数",width:'100px',validation: numValidation},
				        {datafield:'aircraftTakeoffWeight',align: "center", cellsalign:"center",text:"最大起飞重量",width:'100px',validation: numValidation},
				        {datafield:'aircraftPayload',align: "center", cellsalign:"center",text:"最大配载数",width:'100px',validation: numValidation},
				        {datafield:'isWideOrNarrow',align: "center", cellsalign:"center",text:"宽窄体",width:'60px',columntype: 'template',
	                    	createEditor: function (row, cellvalue, editor, cellText, width, height) {
		                        // construct the editor. 
		                        editor.jqxDropDownList({
		                        	placeHolder : "请选择",
		                        	selectedIndex: 1,
		                        	source: ['P','L'], 
		                        	width: width,
		                        	height: height,
		                        	dropDownHeight:'50px'
		                        })
	               		 	},
		                	initEditor: function (row, cellvalue, editor, celltext, width, height) {
		                        // set the editor's current value. The callback is called each time the editor is displayed.
		                        editor.jqxDropDownList({ width: width, height: height });
		                        editor.val(cellvalue);
		                	},
		                	getEditorValue: function (row, cellvalue, editor) {
		                    	// return the editor's value.
		                   	 return editor.val();
		                	}			        	
				        },
				        {datafield:'isHighDensity',align: "center", cellsalign:"center",text:"客座率打折",width:'80px',columntype: 'template',
	                    	createEditor: function (row, cellvalue, editor, cellText, width, height) {
		                        // construct the editor. 
		                        editor.jqxDropDownList({
		                        	placeHolder : "请选择",
		                        	selectedIndex: 1,
		                        	source: ['N','Y'], 
		                        	width: width,
		                        	height: height,
		                        	dropDownHeight:'50px'
		                        })
	               		 	},
		                	initEditor: function (row, cellvalue, editor, celltext, width, height) {
		                        // set the editor's current value. The callback is called each time the editor is displayed.
		                        editor.jqxDropDownList({ width: width, height: height });
		                        editor.val(cellvalue);
		                	},
		                	getEditorValue: function (row, cellvalue, editor) {
		                    	// return the editor's value.
		                   	 return editor.val();
		                	}	
		                },
				        {datafield:'isPackagingFacility',align: "center", cellsalign:"center",text:"集装设备",width:'150px',hidden:true},
				        {datafield:'aircraftType',align: "center", cellsalign:"center",text:"飞机类型",width:'100px'},
				        {datafield:'aircraftNoiseCategory',align: "center", cellsalign:"center",text:"噪音级别",width:'100px'},
				        {datafield:'aircraftHeight',align: "center", cellsalign:"center",text:"高度",width:'120px',validation: numValidation},
				        {datafield:'aircraftLength',align: "center", cellsalign:"center",text:"长度",width:'90px',validation: numValidation},
				        {datafield:'aircraftWidth',align: "center", cellsalign:"center",text:"宽度",width:'120px',validation: numValidation},
				        {datafield:'aircraftLandingWeight',align: "center", cellsalign:"center",text:"最大落地重量",width:'100px',validation: numValidation},
				        {datafield:'aircraftEngineNumber',align: "center", cellsalign:"center",text:"引擎数量",width:'150px',validation: numValidation},
				        {datafield:'aircraftAirline',align: "center", cellsalign:"center",text:"所属航空公司",width:'120px'},
				        {datafield:'aircraftDescription',align: "center", cellsalign:"center",text:"描述说明",width:'120px'},
				        {datafield:'aircraftLeasingAirline',align: "center", cellsalign:"center",text:"租赁航空公司",width:'150px'},
				        {datafield:'validFlag',align: "center", cellsalign:"center",text:"有效标识",width:'120px',hidden:true},
				        {datafield:'remark',align: "center", cellsalign:"center",text:"备注",width:'90px',hidden:true},
				        {datafield:'removeFlag',align: "center", cellsalign:"center",text:"删除标志",width:'120px',hidden:true},
				        {datafield:'createUser',align: "center", cellsalign:"center",text:"创建人",width:'90px',hidden:"true"},
				        {datafield:'createTime',align: "center", cellsalign:"center",text:"创建时间",width:'90px',hidden:true},
				        {datafield:'modifyUser',align: "center", cellsalign:"center",text:"修改人",width:'150px',hidden:true},
				        {datafield:'modifyTime',align: "center", cellsalign:"center",text:"修改时间",width:'150px',hidden:true}
					]
	            })
			}else{
				$("#dataTable").jqxDataTable({
					source: dataAdapter
				});
			}             
	}


	$("#query_button").click(function (){
		search();
	});
	search();
});
</script>
</head>
<body style="padding: 10px;min-width: 1024px;">
<%@ include file="./headerNavigation.jsp"%>
<div class="panel search" style="height:110px" id="search-panel">
	<div class="main clearfix">
		<div>
			<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="lb"><label for="aircraftRegistration">机号</label></td>
				<td><input id="aircraftRegistration" class="formItem"></input></td>
           		<td class="lb"><label for="id">id</label></td>
				<td><input id="id" class="formItem"></input></td>
				<td class="lb"><label for="currentAirline">现属航空公司</label></td>
				<td><input id="currentAirline" class="formItem"></input></td>
				<td class="lb"><label for="currentSubAirline">现属航空分公司</label></td>
				<td><input id="currentSubAirline" class="formItem"></input></td>
				<td class="lb"><label for="airlineOfFlight">航空公司二字码</label></td>
				<td><input id="airlineOfFlight" class="formItem"></input></td>
				<td class="lb"><label for="aircraftTypeICAOCode">机型三字码</label></td>
				<td><input id="aircraftTypeICAOCode" class="formItem"></input></td>	
			</tr>
			<tr>
			<td class="lb"><label for="aircraftSeatCapacity">最大客座数</label></td>
				<td><input id="aircraftSeatCapacity" class="formItem"></input></td>
				<td class="lb"><label for="aircraftTakeoffWeight">最大起飞全重</label></td>
				<td><input id="aircraftTakeoffWeight" class="formItem"></input></td>
				<td class="lb"><label for="aircraftPayload">最大配载数</label></td>
				<td><input id="aircraftPayload" class="formItem"></input></td>
				<td class="lb"><label for="isWideOrNarrow">宽窄体:</label></td>
           		<td>
             		<select id="isWideOrNarrow" class="formItem">
               		<option selected value="">全部</option>
	                <option  value="P">P</option>
	                <option value="L">L</option>
              		</select>
            	</td>
            	<td class="lb"><label for="isHighDensity">客座率打折:</label></td>
           		<td>
             		<select id="isHighDensity" class="formItem">
               		<option selected value="">全部</option>
	                <option  value="Y">Y</option>
	                <option value="N">N</option>
              		</select>
            	</td>
				<td class="lb"><label for="validTime">有效时间：</label></td>
				<td><div id="validTime" class="formItem"></div></td>
				<td class="lb">
	              <input type="button" id="query_button" class="find mr8"  />
					<input type="button" id="import" class="dr" title="机号导入" />
           		</td>
			</tr>
			</table>
		</div>
	</div>
</div>

<div id="totalNumber" style="text-align: right;font-weight: bold;font-size: 13px;">&nbsp;</div>

    <div id="dataTable-panel"  style="height:75%;">
      <div id="dataTable"></div>
</div>


<div id="uploadPanel">
	<div>
		机号导入
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



	<script>
		$(function (){
			var uploadingState = function (){
				var btn = $("#startUpload");
				btn.jqxButton("disabled",true);
				btn.val("处理中");

			}

			var uploadedState = function (){
				var btn = $("#startUpload");
				btn.jqxButton("disabled",false);
				btn.val("开始上传");

			}


			$("#import").on("click", function() {

				$('#file_upload').uploadifive({
					auto : false,
					queueID : 'queue',
// 			checkScript : './uploadgoodsImgCheck.htm',	//文件不存在返回0 存在返回1
					uploadScript : './registrationImport',
					//fileType     : 'image/png',
					//buttonClass: 'upload_btn',
					buttonText : '选择文件',
					uploadLimit : 0, 	//同时上传数量
					queueSizeLimit : 1,
					fileSizeLimit : 102400, //限制size
					multi : false,
					removeCompleted : true,
					formData : {
						//category :category,
					},
					onUploadComplete : function(file, data) {
						var rtn = eval(data);
						var dl = false;
						//获取错误的返回文件
//						if(rtn.indexOf("manifest_import_back_file") >= 0){
//							dl = true;
//							rtn = rtn.replace("manifest_import_back_file","");
//						}
						alert(rtn);
//						if(dl){
//							download("./backfile");
//						}
						//uploadedState();
						$('#clearQueue').trigger("click");
					},
					onError : function(errorType) {
						uploadedState();
					},
					onCancel : function (){
						uploadedState();
					},
					//onClearQueue : function (){
					//    uploadedState();
					//},
					onQueueComplete : function(uploads) {
						uploadedState();
					}
				});

				$("div.uploadifive-button").css({
					"-webkit-border-radius" : 0,
					"-moz-border-radius" : 0,
					"border-radius" : 0,
					"border" : 0,
					"margin" : "0 auto",
					"width" : "100%"
				});

				$('#uploadPanel').jqxWindow('open');

			});

			$('#uploadPanel').jqxWindow({
				height: 200,
				width: 400,
				resizable: false,
				isModal: true,
				modalOpacity: 0.8,
				autoOpen :false,
				initContent: function () {
					$('#startUpload').jqxButton({
						template : "primary",
						width : 90,
						height : 25
					}).on('click',function (){
						uploadingState();
						$('#file_upload').uploadifive('upload');
					});


					$('#clearQueue').jqxButton({
						width : 90,
						height : 25
					}).on('click',function (){
						$('#file_upload').uploadifive('clearQueue');
					});
				}
			}).on('close',function (){
				$('#file_upload').uploadifive('destroy');
			});

			$("#export").on("click",function (){
				download("./export");
			});
		});
	</script>

</div>

</body>
</html>