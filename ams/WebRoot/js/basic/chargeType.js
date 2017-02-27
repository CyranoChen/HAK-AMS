/**
 * 这个版本是将工具栏的功能进行了取消
 */
$(function () {
    $("#chargeName, #chargeCode").jqxInput({width: 70,height: 25, theme: "arctic",maxLength: 50});

    //var rowindex = $('#jqxGrid').jqxGrid('getselectedrowindex');
    var rowIndex = null;
    /*var nullValidation = function(obj){
     if (obj.message == 'success') {
     return true;
     } else {
     //$("#dataTable").jqxGrid('showvalidationpopup', rowIndex, obj.datafield, obj.message);
     return false;
     }
     }*/
    

    var search = function () {
        var source = {
            url: "./queryChargeType",
            datatype: "json",
            type: "post",
            data: {
                chargeName: $("#chargeName").val(),
                chargeCode: $("#chargeCode").val()
            },
            id: 'id',
            datafields: [
                {name: 'id', type: 'String'},
                {name: 'createTime', type: 'date'},
                {name: 'createUser', type: 'String'},
                {name: 'modifyTime', type: 'date'},
                {name: 'modifyUser', type: 'String'},
                {name: 'name', type: 'String'},
                {name: 'removeFlag', type: 'String'},
                {name: 'chargeCode', type: 'String'},
                {name: 'remk', type: 'String'},
                // 此字段显示的是收费项目的子类数目
                {name: 'subjectCount', type: 'String'}
            ],

            updaterow: function (rowid, rowdata, commit) {
                var url = './updateChargeType';
                    $.post(
                        url,
                        rowdata,
                        function (obj) {
                            if (obj.message == 'success') {
                                commit(true);
                            } else {

                                setTimeout(function (obj) {
                                    commit(false);
                                }, 10)
                                alert(obj.message);
                            }

                    });


            }
        };

        /*data-blind*/
        var dataAdapter = new $.jqx.dataAdapter(source,
            {
                loadServerData: function (serverdata, source, callback) {
                    $.ajax({
                        dataType: source.datatype,
                        url: source.url,
                        data: serverdata,
                        success: function (data) {
                            if (data) {
                                if (isArray(data)) {
                                    callback({
                                        records: data
                                    });
                                } else if (isObject(data)) {
                                    if (data.content) {
                                        var rtn = {
                                            records: data.content,
                                            totalCount: data.pageInfo.totalRecord
                                        };
                                        callback(rtn);
                                    }
                                } else {
                                    callback({records: []});
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

        $("#dataTable").jqxGrid(
            {
                width: "100%",
                height:"100%",
                theme: 'light',
                source: dataAdapter,
                pageable: false,
                sortable: true,
                altrows: true,
                enabletooltips: true,
                showfilterrow : true, // 设置可过滤
                filterable: true,
                editable: true,
                scrollmode:"logical", // 滚动的时候, 按照行来滚动, 不会出现半行的情况
                //showtoolbar:true,
                selectionmode: 'singlerow',
                editmode: 'selectedrow',
                showsortcolumnbackground: false,
                columns: [
                    {datafield: 'id', align: "center", cellsalign: "center", text: "id", width: '100px', editable: false },
                    {datafield: 'chargeCode', align: "center", cellsalign: "center", text: "收费类型编码", width: '200px'}, // , validation: nullValidation
                    {datafield: 'name', align: "center", cellsalign: "left", text: "收费类型", width: '350px'},//此处校验预留: ,
                    {datafield: 'remk', align: "center", cellsalign: "center", text: "备注", width: '100px', },
                    {datafield: 'subjectCount', align: "center", cellsalign: "center", text: "收费项目数目", width: '200px', editable: false, filtercondition: 'equal' }
                ],
            }).on("bindingcomplete", function () {

            //$("#totalNumber").html($(this).jqxGrid('getrows').length + " 条记录");
            //setTimeout(function (){resize();},1000);

        })
    }

    //获取所编辑列的序号
    $("#dataTable").on('cellbeginedit', function (event) {
        var args = event.args;
        rowIndex = args.rowindex;
    });

    //查询按钮
    $("#query_button").click(function () {
        $("#dataTable").each(function () {
            $(this).jqxGrid("destroy");
        });
        $("#dataTable-panel").append($("<div id='dataTable'></div>"));
        search();
    });
    search();
});
