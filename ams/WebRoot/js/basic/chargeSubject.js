/**
 * 收费项目
 *
 */
$(function () {
    // 获取选中行的行号
    var rowIndex =  null;

    var rowid = null;

    // 渲染类型名称输入框
    $('#name_b').jqxInput({ height:20});

    // 查询状态
    var query_flag = true;

    var search = function () {

        if(!query_flag){
            return;
        }
        query_flag = false;

        $('#name_b').jqxInput();
        // 设置数据源
        var source = {
            url: "./queryChargeSubject",
            datatype: "json",
            type: "post",
            data: {
                // 查询是通过项目名称, 收费性质(下拉单选), 收费类型(下拉多选)联合查询
                name: $("#name_b").val(),
                chargeProperty: $("#chargeProperty_b").val(),
                chargeTypeId: $("#chargeType_b").val()
            },
            id: 'id',
            datafields: [
                {name: 'id', type: 'String'},
                {name: 'name', type: 'String'},
                {name: 'chargeProperty', type: 'String'},
                {name: 'chargeType', type: 'String'},
                {name: 'formula', type: 'String'},
                {name: 'parameter', type: 'String'},
                {name: 'currency', type: 'String'},
                {name: 'currencyUnit', type: 'String'}

            ]
        };

        /*data-blind*/
        var dataAdapter = createDataAdapter(source);

        /*// 获取选中行的行号
         var rowIndex =  null;*/
        $("#dataTable").jqxGrid({
            width: "100%", //
            height: "95%",
            theme: 'light',
            source: dataAdapter,
            pageable: false,
            sortable: true,
            altrows: true,
            enabletooltips: true,
            showfilterrow: true, // 设置可过滤
            filterable: true,
            //本表不允许行修改
            editable: false,
            scrollmode: "logical", // 滚动的时候, 按照行来滚动, 不会出现半行的情况
            showtoolbar: true,
            selectionmode: 'singlerow',
            editmode: 'selectedrow',
            // 分组显示
            showsortcolumnbackground: false,
            groupable: true,
            showgroupsheader: false, // 不显示分组条件栏
            groups: ['chargeType'],
            groupsexpandedbydefault: true,
            columns: [
                {datafield: 'id', align: "center", cellsalign: "center", text: "id", width: '8%'},
                {datafield: 'name', align: "center", cellsalign: "left", text: "项目名称", width: '15%'},
                {datafield: 'chargeProperty', align: "center", cellsalign: "left", text: "收费性质", width: '10%'},
                /*此处的收费类型需要通过和收费类型表进行关联查出, 原字段为chargeTypeId*/
                {datafield: 'chargeType', align: "center", cellsalign: "left", text: "收费类型", width: '10%'},
                {datafield: 'formula', align: "center", cellsalign: "left", text: "公式", width: '20%'},
                {datafield: 'parameter', align: "center", cellsalign: "left", text: "参数", width: '20%'},
                {datafield: 'currency', align: "center", cellsalign: "center", text: "货币", width: '10%'},
                {datafield: 'currencyUnit', align: "center", cellsalign: "center", text: "货币单位", width: '7%'}
            ],

            // 工具栏设置
            renderToolbar: function (toolBar) {
                if (role != "1") {
                    return;
                }
                // appends buttons to the status bar.
                var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
                var buttonTemplate = "<div style='float: left; padding: 3px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";
                var addButton = $(buttonTemplate);
                var editButton = $(buttonTemplate);
                var deleteButton = $(buttonTemplate);
                var viewButton = $(buttonTemplate);
                var updateButton = $(buttonTemplate);
                container.append(addButton);
                container.append(deleteButton);
                toolBar.append(container);

                addButton.jqxButton({
                    cursor: "pointer",
                    disabled: true,
                    enableDefault: false,
                    height: 25,
                    width: 25
                });
                addButton.find('div:first').addClass('jqx-icon-plus');
                addButton.jqxButton({ disabled: false });

                deleteButton.jqxButton({
                    cursor: "pointer",
                    disabled: true,
                    enableDefault: false,
                    height: 25,
                    width: 25
                });
                deleteButton.find('div:first').addClass('jqx-icon-delete');
                deleteButton.jqxTooltip({position: 'bottom', content: "删除"});

                var updateButtons = function (action) {
                    switch (action) {
                        case "Select":
                            //addButton.jqxButton({ disabled: false });
                            deleteButton.jqxButton({disabled: false});
                            editButton.jqxButton({disabled: false});
                            viewButton.jqxButton({disabled: false});
                            // cancelButton.jqxButton({disabled: true});
                            // updateButton.jqxButton({disabled: true});
                            break;
                        case "Unselect":
                            //addButton.jqxButton({ disabled: false });
                            deleteButton.jqxButton({disabled: true});
                            editButton.jqxButton({disabled: true});
                            viewButton.jqxButton({disabled: true});

                            //cancelButton.jqxButton({disabled: true});
                            // updateButton.jqxButton({disabled: true});
                            break;
                        case "Edit":
                            addButton.jqxButton({ disabled: true });
                            deleteButton.jqxButton({disabled: true});
                            editButton.jqxButton({disabled: true});
                            viewButton.jqxButton({disabled: true});

                            //cancelButton.jqxButton({disabled: false});
                            //updateButton.jqxButton({disabled: false});
                            break;
                        case "End Edit":
                            //addButton.jqxButton({ disabled: false });
                            deleteButton.jqxButton({disabled: false});
                            editButton.jqxButton({disabled: false});
                            viewButton.jqxButton({disabled: false});

                            //cancelButton.jqxButton({disabled: true});
                            //updateButton.jqxButton({disabled: true});
                            break;
                        case "Pop Win":
                            //addButton.jqxButton({ disabled: false });
                            deleteButton.jqxButton({disabled: false});
                            editButton.jqxButton({disabled: false});
                            //cancelButton.jqxButton({disabled: true});
                            //updateButton.jqxButton({disabled: true});
                            break;
                    }
                };
                // 获取选中行的行号
                /* var rowIndex =  null;*/
                $("#dataTable").on('rowSelect rowselect', function (event) {
                    event.stopPropagation();
                    //此处由于在用户选择数据时, Grid会优先执行rowselect 再执行rowunselect事件
                    //导致按钮再次被禁用, 所以延迟该事件延迟100毫秒
                    setTimeout(function () {
                        updateButtons('Select');
                    }, 100);
                });

                // 行选中状态
                $("#dataTable").on('rowselect', function (event) {
                    updateButtons('Select');
                });

                //行未选中状态
                $("#dataTable").on('rowunselect', function (event) {
                    updateButtons('Unselect');
                });

                // 行双击的时候, 弹出修改框
                $('#dataTable').on('rowdoubleclick', function () {
                    rowIndex = $('#dataTable').jqxGrid('selectedrowindex');

                    // 弹出修改的窗口
                    $('#windowContainer').jqxWindow({ title: '修改收费项目' });
                    windowInit();
                    // 在点击了修改按钮的时候, 先进行取值
                    openEditWindow(rowIndex);
                });

                /*按钮事件触发*/
                addButton.click(function (event) {
                    if (!updateButton.jqxButton('disabled')) {
                        // 弹出新增的窗口
                        $('#windowContainer').jqxWindow({ title: '新增收费项目' });
                        windowInit();
                        openEditWindow(-1);
                    }
                    updateButtons('Select');
                });

                deleteButton.click(function () {
                    if (!deleteButton.jqxButton('disabled')) {
                        rowIndex = $('#dataTable').jqxGrid('selectedrowindex');
                        if (rowIndex == -1){
                            alert("请选择一条记录 !");
                            return;
                        }
                        var rowid = $('#dataTable').jqxGrid('getrowid', rowIndex);
                        // 弹出是否确认删除的消息框
                        if (confirm("是否确认删除本行数据?")) {
                            var id = {"id" : rowid};
                            $.post(
                                "./deleteRowById",
                                id,
                                function (obj) {
                                    if (obj == 'success') {
                                        $("#dataTable").jqxGrid('deleterow', rowid);
                                    }
                                }
                            );
                        } else {
                            updateButtons('Select');
                        }
                    }
                });

                // 根据行号打开该条记录的窗口
                function openEditWindow(rowIndex) {
                    rowid = rowIndex == -1 ? null : $('#dataTable').jqxGrid('getrowid', rowIndex);
                    var id = {"id" : rowid};
                    $.post(
                        "./toEditWindow",
                        id,
                        function (obj) {
                            for(var key in obj){
                                $('#form [id=' + key + ']').val(obj[key]);
                                if (key == 'parameter' && obj[key]) {
                                    var items = obj[key].split(',');
                                    for (var i = 0; i < items.length; i++) {
                                        $("#parameter").jqxDropDownList('checkItem', items[i] );
                                    }
                                }
                            }
                        },
                        'json'
                    );
                    createTreeGrid();
                    windowOpen();
                }
            }
        }).on("bindingcomplete", function () {
            $('#dataTable').jqxGrid('refresh');
            query_flag = true;
        });

        // 表格收费性质下拉列表
        $('#chargeProperty_b').jqxDropDownList({selectedIndex: 0, width: '150', height: '25', autoDropDownHeight: true });

        // 收费类型下拉列表, 当数据取到的时候才生成下拉列表
        $.ajax({
            url : './queryDropList',
            dataType: 'json',
            success : function (rtn){
                $('#chargeType_b').jqxDropDownList({placeHolder: "请选择:", checkboxes: true,source: rtn,width: '200', height: '25',displayMember: 'key',valueMember: 'value'});
                // window中的收费类型下拉选择(单选)
                $('#chargeTypeId').jqxDropDownList({ source: rtn,width: '100%',height: '25',displayMember: 'key',valueMember: 'value'});
            }
        });

        // window参数下拉列表
        $.ajax({
            url : './queryFmiDropList',
            dataType: 'json',
            success : function (rtn){
                $('#parameter').jqxDropDownList({ placeHolder: "请选择:", filterable: true, filterPlaceHolder: " 输入参数",checkboxes: true, source: rtn, width: '100%', height: '25', displayMember: 'key', valueMember: 'value'});
                $('#exp').jqxDropDownList({ placeHolder: "请选择表达式:", filterable: true, filterPlaceHolder: " 输入参数", source: rtn, width: 430, height: '20', displayMember: 'key', valueMember: 'value'});
            }
        });

        // 创建window面板
        $('#windowContainer').jqxWindow({
            height: 415,
            maxWidth : 998,
            resizable: false,
            autoOpen :false,
            isModal: true,
            modalOpacity: 0.5,
            draggable:true,

            // 初始化面板中的组件
            initContent: function () {
                $('#name').jqxInput({height:20});
                $('#chargeTypeId').jqxDropDownList({selectedIndex: 0, height:20 });
                $('#chargeProperty').jqxDropDownList({width: '100%',height:20,autoDropDownHeight: true, selectedIndex: 0});
                $('#currency').jqxDropDownList({ width: '100%', height:20, autoDropDownHeight: true, selectedIndex: 0});
                // $('#currencyUnit').jqxInput({ height:20, disabled: true });
                $('#formula').jqxInput({width: '100%', height:20});
                $('#parameter').jqxDropDownList({height:20});
                $('#showChecked').jqxTooltip({position: 'bottom', content: "参数列表"});
                $('#afeeCoe').jqxInput({width: '100%', height:20});
                $('#dfeeCoe').jqxInput({ width: '100%',height:20});
                $('#unitPrice').jqxInput({ width: '100%',height:20 });
                $('#subjectCode').jqxInput({width: '100%', height:20 });
                $('#description').jqxTextArea({ placeHolder: '请输入描述...', width: '100%', height: 90, minLength: 1 });
                $("#cancel").jqxButton({ width: 80,  height: 35 });
                $("#submit").jqxButton({ width: 80, height: 35 });
                // $('#listParameter').jqxListBox({width: 215, height: 360, enableHover: false, enableSelection: false });
                $('#parameterChecked').jqxWindow({
                    height: 415,
                    width: 463,
                    resizable: false,
                    autoOpen :false,
                    title: "参数列表",
                    zIndex : 99999,
                    modalOpacity: 0,
                    initContent: function () {

                        $('#listParameter').jqxListBox({
                            width: 448,
                            height: 375,
                            enableHover: false,
                            enableSelection: false
                        });
                    }
                });

                $("#chargeRuleTable").on("bindingComplete", function (event) {
                    $('#chargeRuleTable').jqxGrid('expandAll');
                });

                $('#windowContainer').on('close', function () {
                    windowInit();
                    // 当外层容器执行了关闭之后, 此面板其实没有关闭, 所以在下一次还会判定为打开状态, 需要在这里手动关闭
                    $('#parameterChecked').jqxWindow("close");
                });

                // 单击提交按钮
                $('#submit').on('click', function () {
                    // 先进行公式校验, 如果公式通过了校验, 那么就可以进行提交, 否则, 弹出错误提示
                    var rtn = formulaValidation();
                    if (rtn != 'success') {
                        alert(rtn);
                        return;
                    }
                    var reg = /^\d+(\.\d+)?$/;   /*(\d+\.\d+|\d+)*/  //\d+(\.\d+)?
                    var afee = $('#afeeCoe').val();
                    var dfee = $('#dfeeCoe').val();
                    if (!reg.test(afee)) {
                        alert("进港系数须为数字 !");
                        $('#afeeCoe').trigger("focus");
                        return;
                    }
                    if (!reg.test(dfee)) {
                        alert("离港系数须为数字 !");
                        $('#dfeeCoe').trigger("focus");
                        return;
                    }
                    var args = {};
                    var url = "./saveWindowValue";
                    $('#form .formItem').each(function (){
                        if ($('#currency').val() == '人民币') {
                            $('#currencyUnit').val('元');
                        } else {
                            $('#currencyUnit').val('美元');
                        }
                        args[this.id] = $(this).val();
                    });
                    $.post(
                        url,
                        args,
                        function (obj) {
                            if (obj == 'success') {
                                windowClose();
                                search();
                            } else {
                                alert(obj);
                            }
                        }
                    );
                });

                $('#showChecked').on('click', function () {

                    if(! $('#parameterChecked').jqxWindow("isOpen")){
                        var displayItems = listParaChecked();
                        $('#listParameter').jqxListBox({
                            source: displayItems
                        });
                        var $window = $('#windowContainer');
                        var offset = $window.offset();
                        var title = $('#windowContainer').jqxWindow("title");
                        if (title == "新增收费项目") {
                            $('#parameterChecked').jqxWindow({
                                position: {
                                    x: offset.left + 541,
                                    y: offset.top
                                }
                            }).jqxWindow("open");
                        } else {
                            $('#parameterChecked').jqxWindow({
                                position: {
                                    x: offset.left + 532,
                                    y: offset.top
                                }
                            }).jqxWindow("open");
                        }
                    } else {
                        $('#parameterChecked').jqxWindow("close");
                    }
                });

                $('#parameter').on('checkChange', function () {
                    var displayItems = listParaChecked();
                    $('#listParameter').jqxListBox({
                        source: displayItems
                    });
                    //$('#parameterChecked').jqxWindow("open");
                });

                function listParaChecked() {
                    var $paraItems = $('#parameter').text();
                    var displayItems =  new Array();
                    if ($paraItems != "请选择:") {
                        var items = $paraItems.split(',');
                        for (var i = 1; i <= items.length; i++) {
                            var str = "{" + i + "}-" + items[i - 1];
                            displayItems.push(str);
                        }
                    } else {
                        displayItems.push("没有已选参数");
                    }
                    return displayItems;
                }
            }
        });

        // 收费项目主窗体组件初始化
        function windowInit() {
            $('#form .formItem').each(function (){
                $(this).val('');
            });
            $('#afeeCoe').val('1');
            $('#dfeeCoe').val('1');
            // 清空参数下拉列表所选
            var items = $("#parameter").val().split(',');
            for (var i = 0; i < items.length; i++) {
                $("#parameter").jqxDropDownList('uncheckItem', items[i] );
            }
            $('#parameterChecked').hide();
            // 给货币单元初始化
            $('#currency').jqxDropDownList({selectedIndex: 0});
            $('#currencyUnit').val(' 元');

        }

        // 收费项目主窗口关闭方法, 需要先对面板上的参数进行初始化
        function windowClose() {
            windowInit();
            $('#windowContainer').jqxWindow('close');
        }

        // 收费项目主窗口开启方法, 需要根据不同的设置情况进行打开
        function windowOpen () {
            var title = $('#windowContainer').jqxWindow("title");
            if (title == "新增收费项目") {
                $('#windowContainer').jqxWindow({width:570});
                $('#parameter').jqxDropDownList({ width: 406});
                $('#chargeTypeId').jqxDropDownList({width: '100%'});
                $('#chargeRule').hide();
            } else {
                $('#windowContainer').jqxWindow({width:995});
                $('#parameter').jqxDropDownList({ width: 375});
                $('#chargeTypeId').jqxDropDownList({width: 163});
                $('#chargeRule').show();
            }
            var p = $('#dataTable').offset();
            $('#windowContainer').jqxWindow({
                position: {
                    x: p.left + 30,
                    y: p.top + 33
                }
            }).jqxWindow("open");
        }

        // 公式校验
        function formulaValidation() {
            var paraItems = $('#parameter').val().split(',');
            var itemLen = 0;
            if (!paraItems[0]) {
                // 因为在数组为{""}的时候, length为1, 所以需要在此-1
                itemLen = paraItems.length - 1;
            } else {
                itemLen = paraItems.length;
            }
            //console.log("itemLen: " + itemLen);
            var formulaText = $('#formula').val();
            try {
                //console.log("formula:" + formulaText);
                if(itemLen >= 1) {
                    // 需要在开始判断是不是存在这样的格式: 1+1{1}1+{1}{2}
                    var firstReg = /\d\{|}\d|}\{/;
                    //console.log("firstFormulaReg:" + formulaText.replace(firstReg, 'a'))
                    if (firstReg.test(formulaText)) {
                        //console.log("firstFormulaReg2:" + formulaText);
                        return "公式错误: 数据之间缺少运算符 !";
                    }
                    var reg = "";
                    if (itemLen < 10) {
                        reg = "\{[1-" + itemLen + "]{1}\}";
                    } else {
                        // 针对{23}等两位数的占位处理
                        var n = itemLen/10;
                        reg = "(\{[1-9]{1}}|\{" + n.toFixed(0) + "[0-" + itemLen%10 + "]{1}})";
                    }
                    //console.log("reg: " + reg);
                    var regex = new RegExp(reg, "g");
                    // 将所有占位的地方替换成为1, 执行计算
                    formulaText = formulaText.replace(regex, '1');
                    /*console.log("Text:" + formulaText);
                     console.log("indexOf{: " + formulaText.indexOf('{') + "\n");
                     console.log("");*/
                    // 替换完之后, 表达式只要还存在{就判定为错
                    if (formulaText.indexOf('{') != -1) {
                        return "公式错误: 有多出来的{   !";
                    }
                } else {
                    // 当itemLen< 1的时候, 只要出现{就判定为错, 因为当没有参数的时候是不需要占位的
                    if (formulaText.indexOf('{') != -1) {
                        return "公式错误: 有多出来的{   !";
                    }
                }
                if (isNaN(parseInt(eval(formulaText)))) {

                    return "公式错误: 存在非法字符 !";
                }
            } catch(e) {
                return "您输入的公式有错误 !";
            }
            //console.log("win");
            return "success";
        }

        $('#cancel').on('click', function () {
            windowClose();
        });
    };



    // 查询按钮
    $("#query_button").click(function () {
        if (query_flag) {
            $("#dataTable").each(function () {
                $(this).jqxGrid("destroy");
            });
            $("#dataTable-panel").append($("<div id='dataTable'></div>"));
            search();
        }
    });

    var createTreeGrid = function (){
        // 修改的时候才有收费的规则进行查询
        var source1 = {
            url: "./queryRuleExp",
            data: {
                id: rowid
            },
            dataType: "json",
            id: 'id',
            dataFields: [
                { name: 'id', type: 'string' },
                { name: 'key', type: 'string' },
                { name: 'logic', type: 'string' },
                { name: 'value', type: 'string' },
                { name: 'pid', type: 'string' }
            ],
            hierarchy: {
                keyDataField: { name: 'id' },
                parentDataField: { name: 'pid' }
            }

        };

        destroyGrid("chargeRuleTable");
        var dataAdapter1 = new $.jqx.dataAdapter(source1, {
            loadServerData: function (serverdata, source, callback) {
                // 如果这边不做适配的话, 那就近似于同步模式, 所以一定得一不发起请求
                $.ajax({
                    dataType: source.datatype,
                    url: source.url,
                    data: serverdata,
                    success: function (data) {
                        if (data) {
                            callback({
                                records: data
                            });
                        }
                    }
                })
            }
            //
            // loadComplete: function (data, status, xhr) {
            //     source.totalRecords = data.totalCount;
            // },
            // loadError: function (xhr, status, error) {
            //     throw new Error("http://services.odata.org: " + error.toString());
            // }
        });

        // 收费规则模块
        $("#chargeRuleTable").jqxTreeGrid({
            width: "100%",  //636
            height: 360,
            sortable: false,
            editable: false,
            showToolbar: true,
            source: dataAdapter1,

            renderToolbar: function (toolbar) {
                var container = $("<div style='overflow: hidden; position: relative; height: 100%; width: 100%;'></div>");
                var buttonTemplate = "<div style='float: left; padding: 3px; margin: 2px;'><div style='margin: 4px; width: 16px; height: 16px;'></div></div>";
                var addButton = $(buttonTemplate);
                var editButton = $(buttonTemplate);
                var deleteButton = $(buttonTemplate);
                var cancelButton = $(buttonTemplate);
                var updateButton = $(buttonTemplate);
                container.append(addButton);
                container.append(deleteButton);
                toolbar.append(container);

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

                var updateButtons =  function (action) {
                    switch (action) {
                        case "Select":
                            addButton.jqxButton({ disabled: false });
                            deleteButton.jqxButton({ disabled: false });
                            editButton.jqxButton({ disabled: false });
                            //cancelButton.jqxButton({ disabled: true });
                            updateButton.jqxButton({ disabled: true });
                            break;
                        case "Unselect":
                            addButton.jqxButton({ disabled: false });
                            deleteButton.jqxButton({ disabled: true });
                            editButton.jqxButton({ disabled: true });
                            //cancelButton.jqxButton({ disabled: true });
                            updateButton.jqxButton({ disabled: true });
                            break;
                        case "Edit":
                            addButton.jqxButton({ disabled: true });
                            deleteButton.jqxButton({ disabled: true });
                            editButton.jqxButton({ disabled: true });
                            //cancelButton.jqxButton({ disabled: false });
                            updateButton.jqxButton({ disabled: false });
                            break;
                        case "End Edit":
                            addButton.jqxButton({ disabled: false });
                            deleteButton.jqxButton({ disabled: false });
                            editButton.jqxButton({ disabled: false });
                            //cancelButton.jqxButton({ disabled: true });
                            updateButton.jqxButton({ disabled: true });
                            break;
                    }

                };

                // 双击行, 弹出修改窗口
                $("#chargeRuleTable").on('rowDoubleClick', function (event){
                    var selection = $("#chargeRuleTable").jqxTreeGrid('getSelection');
                    // 查找到选中记录的id
                    var id = selection[0].id;
                    $("#roeId").val(id);
                    var clz = id.charAt(0);
                    // 先要根据id查找到对应的数据并进行展示
                    if (clz == 'G') {
                        var gid = {id: id.substr(1)};
                        $.post(
                            './queryGroupByGid',
                            gid,
                            function (rtn) {
                                openRuleExpAM("修改收费规则", clz, rtn);
                            },
                            'json'
                        )
                    } else {
                        var eid = {id : id.substr(1)};
                        $.post(
                            './queryExpByEid',
                            eid,
                            function (rtn) {
                                openRuleExpAM("修改收费规则", clz, rtn);
                            },
                            'json'
                        )
                    }

                });

                // 点击新增按钮, 弹出窗口
                addButton.click(function () {
                    $("#roeId").val("");
                    var selection = $("#chargeRuleTable").jqxTreeGrid('getSelection');
                    if (!selection.length) {
                        alert("请选择一条记录 !");
                        return;
                    }
                    // 1. 首先先在Group表中插入一条数据, 默认的组为and
                    // $.post('./insertFirstGroup');
                    // 2. 已有记录, 获取所选行的id, 判断是组还是表达式
                    var id = selection[0].id;
                    var clz = id.charAt(0);
                    if (clz == 'G') {
                        initAomWindow();
                        // 如果是组的话, 先要进行后台检索, 看下挂的是空还是表达式还是组, 目前实现的为给用户提供默认的状态
                        $.post(
                            'checkGroup',
                            {
                                "id": id.substr(1)
                            },
                            function(rtn) {
                                if (rtn == 'N') {
                                    initAomWindow(rtn);
                                } else if (rtn == 'G' || rtn == 'E') {
                                    initAomWindow(rtn);
                                } else {
                                    alert(rtn);
                                }

                            }
                        );

                    } else {
                        // 当选中的是exp的时候, 需要插入的只能是id
                        alert("请选择组进行添加操作");
                        return;
                    }

                    $('#ruleExpAM').jqxWindow({title: '新增收费规则'}).jqxWindow("open");
                });

                $("#chargeRuleTable").on('rowSelect', function (event) {
                    updateButtons('Select');
                });
                $("#chargeRuleTable").on('rowUnselect', function (event) {
                    updateButtons('Unselect');
                });
                $("#chargeRuleTable").on('bindingComplete', function (event){
                    $("#chargeRuleTable").jqxTreeGrid("expandAll");
                });

                $('#ruleExpAM').jqxWindow({
                    height: 80,
                    width: 610,
                    maxWidth : 998,
                    resizable: false,
                    autoOpen :false,
                    isModal: true,
                    modalOpacity: 0.5,
                    initContent: function () {
                        $('#goeSelect').jqxDropDownList({ width: '65', height: '20', autoDropDownHeight: true});
                        $('#group').jqxDropDownList({ width: '200', height: '20', autoDropDownHeight: true});
                        $('#logicOpt').jqxDropDownList({ width: '45', height: '20', autoDropDownHeight: true});
                        $('#value').jqxTextArea({ placeHolder: '请输入值...', height: 50, width: '99%', minLength: 1 });
                        $('#sub, #can').jqxButton({ width: 60, height: 20 });
                        initRuleExp();
                        $('#goeSelect').on('change', function () {
                            initRuleExp();
                        });
                        // 提交按钮
                        $('#sub').click(function () {
                            // 先获取该行记录组的原始id
                            var sid = $("#chargeRuleTable").jqxTreeGrid('getSelection')[0].id;
                            var pid = $("#chargeRuleTable").jqxTreeGrid('getSelection')[0].pid;
                            // 获取目前面板上所有的组件的值, 准备存入数据库
                            $.post (
                                './updateOrSave',
                                {
                                    'id': $("#roeId").val(),
                                    'select': $("#goeSelect").val(),
                                    'pid' : sid,
                                    'group': $('#group').val(),
                                    "exp": $('#exp').val(),
                                    "opt": $('#logicOpt').val(),
                                    "value": $('#value').val(),
                                    "gid": pid
                                },
                                function (obj) {
                                    if (obj == 'success') {
                                        // alert("保存成功 !");
                                        // 刷新无效
                                        //$("#chargeRuleTable").jqxTreeGrid('refresh');
                                        $('#ruleExpAM').jqxWindow('close');
                                        createTreeGrid();
                                    } else {
                                        alert(obj);
                                    }
                                }
                            );
                        });

                        // 取消按钮
                        $('#can').click(function () {
                            $('#ruleExpAM').jqxWindow("close");
                        });
                    }
                });

                // 初始化新增窗口
                function initAomWindow(clz) {
                    if (clz == 'G'){
                        $("#goeSelect,#group").jqxDropDownList({selectedIndex:0});
                    } else if (clz == 'E'){
                        $("#goeSelect").jqxDropDownList({selectedIndex:1});
                        $("#exp, #logicOpt").jqxDropDownList({selectedIndex:0});
                        $('#value').val("");
                    } else {
                        $("#goeSelect,#group").jqxDropDownList({selectedIndex:0});
                        $("#exp, #logicOpt").jqxDropDownList({selectedIndex:0});
                        $('#value').val("");
                    }
                }

                // 删除收费规则
                deleteButton.click(function () {
                    if (!deleteButton.jqxButton('disabled')) {
                        var selection = $("#chargeRuleTable").jqxTreeGrid('getSelection');

                        if (!selection.length) {
                            alert("请选择一条记录 !");
                            return;
                        }
                        var sid = {"id": selection[0].id} ;
                        if (confirm("是否要删除本条记录 ?")) {
                            $.post(
                                './deleteRuleById',
                                sid,
                                function (rtn) {
                                    if (rtn == "success") {
                                        createTreeGrid();
                                    } else {
                                        alert(rtn);
                                    }
                                },
                                'text'
                            )
                        }
                    }
                });

                function initRuleExp() {
                    var selectText = $('#goeSelect').text();
                    if (selectText == "组") {
                        $('#ruleExpAM').jqxWindow({height: 85});
                        $('#group').show();
                        $('#exp').hide();
                        $('#logic').hide();
                        $('#valueArea').hide();
                        $('#sub').css('margin-left', '100px');
                        $('#can').css('margin-left', '30px');
                    } else {
                        $('#ruleExpAM').jqxWindow({height: 175});
                        $('#group').hide();
                        $('#exp').css('position', 'relative').show();
                        $('#logic').css({'position':'relative'}).show();
                        $('#valueArea').show();
                        $('#sub').css('margin-left', '180px');
                        $('#can').css('margin-left', '50px');
                    }
                }

                function openRuleExpAM(title, clz, obj) {
                    if (clz == "G") {
                        initRuleExp();
                        $('#goeSelect').val(clz);
                        $('#group').val(obj.key);
                    } else if (clz == "E") {
                        initRuleExp();
                        $('#goeSelect').val(clz);
                        $('#logicOpt').val(obj.logic);
                        $('#exp').val(obj.key);
                        $('#value').val(obj.value);
                    }
                    var offset = $('#windowContainer').offset();
                    $('#ruleExpAM').jqxWindow({ title: title, position: { x: offset.left + 472, y: offset.top + 150} }).jqxWindow("open");
                }
            },
            columns: [
                { text: '字段(连接符)',  dataField: 'key', align:"center", width: "60%",cellsalign:"left" },
                { text: '操作符',  dataField: 'logic', width: "15%", align:"center",cellsalign:"center"},
                { text: '值', dataField: 'value', width: "25%", align:"center",cellsalign:"center"}
            ]
        });
    };

    search();
});