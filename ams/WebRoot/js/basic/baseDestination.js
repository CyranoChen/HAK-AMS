$(function(){
    $("#airportIATACode, #airportICAOCode").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 50 });
    var length20Validation = function(cell,value){
        if(value.trim() == ""){
            return { message: "内容必须为1到20位的字母或数字!", result: false };
        }
        var regx = /^[A-Za-z0-9]{1,20}$/;
        if(!regx.test(value)){
            return { message: "内容必须为1到20位的字母或数字!", result: false };
        }
        return true;
    };
    // 三字码长度验证
    var length3Validation = function(cell,value){
        if(value.trim() == ""){
            return { message: "内容必须为3位字母!", result: false };
        }
        var regx = /^[A-Za-z]{3}$/;
        if(!regx.test(value)){
            return { message: "内容必须为3位字母!", result: false };
        }
        return true;
    }
    // 四字码长度验证
    var length4Validation = function(cell,value){
        if(value.trim() == ""){
            return { message: "内容必须为4位字母!", result: false };
        }
        var regx = /^[A-Za-z]{4}$/;
        if(!regx.test(value)){
            return { message: "内容必须为4位字母!", result: false };
        }
        return true;
    }
    /*var airlinesMap = {};
    var airlines;
    var mode = null;
    $.ajax({
        async:false,
        url:".",
        success: function(data){
            airlines = data;
            for(var i=0;i<data.length;i++){
                airlinesMap[data[i].id] = data[i].airlineName;
            }
        }
    });
*/
    var search = function(){
        var source = {
            type : "POST",
            url : "./queryDestination",
            datatype : "json",
            data : {
                airportIATACode : $("#airportIATACode").val(),
                airportICAOCode : $("#airportICAOCode").val()
            },
            id : 'id',
            datafields : [
                {name: 'id',type:'String'},
                {name: 'basicDataId',type:'String'},
                {name: 'airportCity',type:'String'},
                {name: 'airportCountry',type:'String'},
                {name: 'airportCountryType',type:'String'},
                {name: 'airportDescription',type:'String'},
                {name: 'airportIATACode',type:'String'},
                {name: 'airportICAOCode',type:'String'},
                {name: 'airportRoutingName',type:'String'},
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
                        // 如果有id的话, 更新, 没有的话, 新增
                        if(rowData.id){
                            url="./updateDestination";
                        }else{
                            url="./saveDestination";
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
                                "./queryDestinationById",
                                data = {
                                    id:rowData.id
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
                                        "./removeDestination",
                                        data = {
                                            id:rowData.id
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
                {datafield:'id',align: "center", cellsalign:"center",text:"id",width:'10%',editable:false},
                {datafield:'basicDataId',align: "center", cellsalign:"center",text:"basicDataID",width:'10%',validation:length20Validation},
                {datafield:'airportCity',align: "center", cellsalign:"center",text:"机场所在城市",width:'10%'},
                {datafield:'airportCountry',align: "center", cellsalign:"center",text:"机场所在国家",width:'10%'},
                {datafield:'airportCountryType',align: "center", cellsalign:"center",text:"所在国家类型",width:'10%'},
                {datafield:'airportDescription',align: "center", cellsalign:"center",text:"机场描述",width:'20%'},
                {datafield:'airportIATACode',align: "center", cellsalign:"center",text:"机场三字码",width:'10%',validation:length3Validation},
                {datafield:'airportICAOCode',align: "center", cellsalign:"center",text:"机场四字码",width:'10%',validation:length4Validation},
                {datafield:'airportRoutingName',align: "center", cellsalign:"center",text:"机场路由",width:'10%'}
          
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