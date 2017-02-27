var flightId = null;
var flightBaseId = null;
var loadDataCol = null;
var g = null;
var tabHeight = 200;//修改时请注意同时修改页面DIV的style高度
var allowUpdate = false;
var detailTabs = {
    table: ["flightDetail","manifestDetail","vipDetail"],
    grid : ["carserviceDetail","bridgeserviceDetail"],
    gridHeight : [tabHeight - 40,tabHeight - 70]
} ;


$(document).ready(function () {


    $("#loader").jqxLoader({ isModal: true, width: 100, height: 60, imagePosition: 'top',text:"处理中..." });

    /**
     * 20161007追加 定位功能
     */
    $('#position_btn').jqxButton({
        width : 90,
        height : 25,
    }).on('click',function (){

        if(flightId){
            var g = $("#grid");
            var idx = g.jqxGrid('getrowboundindexbyid', flightId);

            if(idx || idx == 0){
                g.jqxGrid('scrolloffset', idx * 25, 0);
                g.jqxGrid("selectrow",idx);
            }

        }
    });

    /**
     * 20160908追加 单条航班重新配对功能
     */

    $('#match_btn').jqxButton({
        width : 90,
        height : 25,
    }).on('click',function (){


        $('#loader').jqxLoader('open');

        $.post(
            "./flightMatch?baseId=" + flightBaseId,
            function(rtn){
                $('#loader').jqxLoader('close');

                if(rtn == "success"){
                    alert("配对完成");
                    $("#grid").trigger("rowdoubleclick");
                    //$("#query_button").trigger("click");
                }else{
                    alert(rtn);
                }
            }
        );


    });

    
    /**
     * 20161215追加 时间段航班配对功能
     */

    $('#matchByDate_btn').on('click',function (){


        $('#loader').jqxLoader('open');
        
        
        var dateRange = $("#scheduledTime").val().split(" - ");

        $.post(
            "./flightMatchByDate?startTime=" + dateRange[0] + "&endTime=" + dateRange[1],
            function(rtn){
                $('#loader').jqxLoader('close');

                if(rtn.success == true){
                	alert('开始日期：' + rtn.msg.startTime + 
                		  '\n结束日期：' + rtn.msg.endTime +
                		  '\n总计航班：' + rtn.msg.total + 
                		  '\n生成：' + rtn.msg.done + 
                		  '\n缺少对应连班：' + rtn.msg.noLink);
                }else{
                	alert(rtn.msg);
                }
            }
        );


    });
    
    
    /**
     * 20161001追加 单条航班重新配载功能
     */
    $('#load_btn').jqxButton({
        width : 90,
        height : 25,
    }).on('click',function (){
        $('#loader').jqxLoader('open');
        $.post(
            "./flightLoad?baseId=" + flightBaseId,
            function(rtn){
                $('#loader').jqxLoader('close');

                if(rtn == "success"){
                    alert("配载完成");
                    $("#grid").trigger("rowdoubleclick");
                    //$("#query_button").trigger("click");
                }else{
                    alert(rtn);
                }
            }
        );
    });



    /**
     * 20161001追加 单条航班收费计算
     */
    $('#cal_btn').jqxButton({
        width : 90,
        height : 25,
    }).on('click',function (){
        $('#loader').jqxLoader('open');
        $.post(
            "../flightMateCharge/cal?ids=" + flightId,
            function(rtn){
                $('#loader').jqxLoader('close');
                if(rtn){
                    //alert("执行成功!总共生成" + rtn + "条 收费条目");
                    var success = Number(rtn["success"]);
                    var failed = Number(rtn["failed"]);
                    var total = success + failed;
                    var result = "执行完成! 计算航班数量:" + total + ", 其中计算成功:" + success + ", 计算失败:" + failed;

                    if(failed > 0){
                        result += ",点击确定查看计算异常信息";
                    }
                    alert(result);
                    if(failed > 0){
                        var win = window.open("./toErrorPage","error","resizable=yes,scrollbars=yes,status=no,toolbar=no,menubar=no,location=no");
                        win.focus();
                    }

                    //$("#query_button").trigger("click");


                }else{
                    alert("执行异常!");
                }
            }
        );
    });


    /**
     * 20160324 追加 禁止修改舱单数据
     * @type {boolean}
     */

    $("#manifestDetail td[colname]").each(function (){
        $(this).removeAttr("editable");
    })

    var loadDataFirst = true;
    var loadData = function (id){

        var g = $("#loadData");
        var  hasPermission = checkRole(g.attr("role"));

        var dataAdapter = createDataAdapter(    {
            updaterow : function (rowid, rowdata, commit) {
                if( ! allowUpdate){
                    commit(true);
                    return;
                }
                if(flightId){

                    $.ajax({
                        url :  "./updateLoadData",
                        data :   {
                            loadDataId : rowdata.id,
                            id:flightId,
                            colname : loadDataCol,
                            value : rowdata[loadDataCol],
                            role : role
                        },
                        "success" :  function(rtn){
                            if(isString(rtn)){
                                commit(false);
                                alert(rtn);
                            }else{
                                commit(true);
                                var g = $("#grid");
                                var rowdata = g.jqxGrid('getrowdatabyid', flightId);
                                for(var key in rtn){
                                    if( rowdata.hasOwnProperty(key) ){
                                        rowdata[key] = rtn[key];
                                    }
                                }
                                g.jqxGrid("updaterow",flightId,rowdata);
                            }
                        },
                        async : false,
                        type : "post"

                    });

                }
            },
            url : "./getLoadData?id=" +id ,
            datatype : "json",
            datafields: [
                { name: 'id', type: 'string' },
                { name: 'hd', type: 'string' },
                { name: 'hdfl', type: 'string' },
                { name: 'zdyz', type: 'string' },
                { name: 'zdzw', type: 'string' },
                { name: 'peyz', type: 'string' },
                { name: 'pezw', type: 'string' },
                { name: 'kgyz', type: 'string' },
                { name: 'kgzw', type: 'string' },
                { name: 'io', type: 'string' },
                { name: 'jcn', type: 'string' },
                { name: 'qjsj', type: 'string' },
                { name: 'cr', type: 'string' },
                { name: 'et', type: 'string' },
                { name: 'ye', type: 'string' },
                { name: 'crwh', type: 'string' },
                { name: 'etwh', type: 'string' },
                { name: 'yewh', type: 'string' },
                { name: 'xl', type: 'string' },
                { name: 'yj', type: 'string' },
                { name: 'hw', type: 'string' },
                { name: 'wjhz', type: 'string' },
                { name: 'xljs', type: 'string' },
                { name: 'legno', type: 'string' },
                { name: 'generateMethod', type: 'string' }
            ],
            id : "id"
        });

        if(loadDataFirst){
            loadDataFirst = false;

            $('#calLoadData').jqxButton({
                width : 90,
                height : 25,
            }).on('click',function (){
                $.post(
                    "./calLoadData?id=" + flightId,
                    function (rtn){
                        if(rtn == "success"){
//                            alert("操作成功!");
                            $("#grid").trigger("rowdoubleclick");
                        }else {
                            alert(rtn);
                        }
                    }
                );
            });

            //$('#addLoadData').jqxButton({
            //    width : 50,
            //    height : 25,
            //}).on('click',function (){
            //    $.post(
            //        "./addLoadData",
            //        function(rtn){
            //            $('#jqxGrid').jqxGrid('addrow', rtn.id, rtn);
            //        }
            //    )
            //
            //});
            //
            //
            //$('#deleteLoadData').jqxButton({
            //    width : 50,
            //    height : 25,
            //}).on('click',function (){
            //    var grid = $("#loadData");
            //    var cell = grid.jqxGrid('getselectedcell');
            //    var id = grid.jqxGrid('getrowid', cell.rowindex);
            //    alert(id);
            //});
            //
            //
            //if(role != "1"){
            //    $("#addLoadData,#deleteLoadData").hide();
            //}

            g.jqxGrid({
                height: tabHeight - 70,
                width: '99%'  ,
                theme:'arctic',
                editable: true,
                columnsresize:true,
                source:  dataAdapter,
                altrows: true,
                sortable: false,
                selectionmode: 'singlecell',
                columns: (function (hasPermission){
                    var item = [
                        { text: '航段信息', datafield: 'hd',align:"center",cellsalign:"center",editable:false },
                        //{ text: '航段性质', datafield: 'hdfl',align:"center",cellsalign:"center",editable:false },
                        { text: '成人', datafield: 'cr',align:"center",cellsalign:"center",editable:true },
                        { text: '儿童', datafield: 'et',align:"center",cellsalign:"center",editable:true },
                        { text: '婴儿', datafield: 'ye',align:"center",cellsalign:"center",editable:true },
                        { text: '过站成人', datafield: 'crwh',align:"center",cellsalign:"center",editable:true },
                        { text: '过站儿童', datafield: 'etwh',align:"center",cellsalign:"center",editable:true },
                        { text: '过站婴儿', datafield: 'yewh',align:"center",cellsalign:"center",editable:true },


                        { text: '货物(KG)', datafield: 'hw',align:"center",cellsalign:"center",editable:true,decimalDigits:0},
                        { text: '邮件(KG)', datafield: 'yj',align:"center",cellsalign:"center",editable:true,decimalDigits:0},

                        { text: '行李件数', datafield: 'xljs',align:"center",cellsalign:"center",editable:true },

                        { text: '行李(KG)', datafield: 'xl',align:"center",cellsalign:"center",editable:true,decimalDigits:0},

                        //{ text: '外交护照', datafield: 'wjhz',align:"center",cellsalign:"center",editable:true },

                        { text: '最大业载', datafield: 'zdyz',align:"center",cellsalign:"center",editable:false },
                        { text: '最大座位', datafield: 'zdzw',align:"center",cellsalign:"center",editable:false }
                        //{ text: '标识', datafield: 'generateMethod',align:"center",cellsalign:"center",editable:false }
                        //{ text: '配额业载', datafield: 'peyz',align:"center",cellsalign:"center",editable:false },
                        //{ text: '配额座位', datafield: 'pezw',align:"center",cellsalign:"center",editable:false },
                        //{ text: '配额业载', datafield: 'kgyz',align:"center",cellsalign:"center",editable:false },
                        //{ text: '可供业载', datafield: 'kgzw',align:"center",cellsalign:"center",editable:false },
                        //{ text: '进出港', datafield: 'io',align:"center",cellsalign:"center",editable:false },
                        //{ text: '架次', datafield: 'jcn',align:"center",cellsalign:"center",editable:false },
                        //{ text: '起降时间', datafield: 'qjsj',align:"center",cellsalign:"center",editable:false }


                    ];

                    for(var i = 0 ; i < item.length  ; i ++){
                        var m = item[i];
                        if(m['editable']){

                            if(hasPermission){
                                m['columntype'] = 'numberinput';
                                var decimalDigits = m.hasOwnProperty("decimalDigits") ? m["decimalDigits"] : 0;


                                (function (d){
                                    m['createeditor'] = function (row, value, editor) {
                                        editor.jqxNumberInput({ inputMode: 'simple', spinButtons: true,spinButtonsStep:1,decimalDigits:d,theme:"arctic",spinButtonsWidth:25,min:0,max:99999999 });
                                    };
                                })(decimalDigits);


                            }else{
                                m['editable'] = false;
                            }
                        }
                    }
                    return  item;
                }(hasPermission))
            });

            g.on('cellbeginedit', function (event) {
                loadDataCol = event.args.datafield;
            });
        }else{
            g.jqxGrid({
                source:dataAdapter
            });
        }


    };


    /**
     * 根据不同role显示不同详情
     */
    //var showDetailTab = function (role){
    //	if (role == 5){
    //   	 $('#jqxTabs').jqxTabs("removeAt",3);
    //   	 $('#jqxTabs').jqxTabs("removeAt",2);
    //   	 $('#jqxTabs').jqxTabs("removeAt",1);
    //    }else if(role == 4){
    //    	$('#jqxTabs').jqxTabs("removeAt",4);
    //      	$('#jqxTabs').jqxTabs("removeAt",3);
    //      	$('#jqxTabs').jqxTabs("removeAt",2);
    //    }else if(role == 3){
    //    	$('#jqxTabs').jqxTabs("removeAt",4);
    //      	$('#jqxTabs').jqxTabs("removeAt",2);
    //      	$('#jqxTabs').jqxTabs("removeAt",1);
    //    }else if(role == 2){
    //
    //    }
    //};

    var isNum = function (value){
        if(value == "" || value.trim() == ""){
            return true;
        }
        return(/^\d{1,8}([.][\d])?$/.test(value));
    };

    var resize = function (){
        var h = $("#detail-panel").is(":hidden") ? 0 : tabHeight;
        var ch = h+55+10+10 + 10;
        if(h > 0){
            ch += 25;
        }

        $("#grid-panel").height($("body").height() -ch);
        $("#grid").jqxGrid('refresh');
    };

    $(window).on("resize",function (){
        resize();
    });
    resize();
    /**
     * 初始化查询条件的控件
     */
    $("#flightDirection,#standFlag,#airlineHandler").jqxDropDownList({ width: 60, height: 25,theme:"arctic"});
    $("#flightIdentity,#airLineName,#registeration").jqxInput({ width: 50, height: 25,theme:"arctic",maxLength : 20 });
    $("#error").jqxDropDownList({
        width: 120,
        height: 25,
        theme:"arctic",
        filterable : true,
        searchMode : 'containsignorecase',
        filterPlaceHolder : "",
        placeHolder : "全部航班",
        itemHeight : 30,
        checkboxes : true
    });
    //$("#error").on("checkChange",function (event){
    //    var item = event.args.item;
    //    if(item.value == ""){
    //        if( item.checked){
    //            $(this).jqxDropDownList('checkAll');
    //        }else{
    //            $(this).jqxDropDownList('uncheckAll');
    //        }
    //    };
    //});

    $("#scheduledTime").jqxDateTimeInput({
        formatString: "yyyy/MM/dd",
        showCalendarButton: true,
        allowKeyboardDelete : false,
        width: 200,
        height: 25,
        theme:"arctic",
        culture: 'zh-CN',
        allowNullDate: false,
        showFooter: true,
        selectionMode: 'range',
        todayString: "今天",
        clearString: ""
        //value : "2016/08/01"
    });

    var checkRole = function (roleString){
        return roleString.split(",").indexOf(role) >= 0;
    };

    var cellclass = function (row, columnfield, value,data) {
        if (columnfield == "standFlag") {
            if(value == "近"){
                return "green";
            }else if(value == "远"){
                return "yellow";
            }else{
                return "";
            }
        }

        if(columnfield == "updateRole"){
            return "";
        }

        if(data.alarmMessage){
            return "orange";
        }


        /**
         * 暂停
         */
        //if(role == "3" || role == "4"){
        //    if(data.updateRole){
        //        var uproles = data.updateRole.split(",");
        //    }
        //
        //
        //}

        //if(data.stat == "0"){
        //    return "red";
        //}


        return "";
    };

    var roles = {
        "1" : "#796bb8",
        "2" : "#00bcee",
        "3" : "#b7c800",
        "4" : "#ffbb00",
        "5" : "#c70158"
    };

    var roleName = {
        "1" : "财",
        "2" : "舱",
        "3" : "汽",
        "4" : "廊",
        "5" : "V"
    };

    var cellsrender =  function (row, columnfield, value, defaulthtml, columnproperties) {
        if(columnfield == "updateRole"){
            var h = '<div style="text-overflow: ellipsis; overflow: hidden; padding-bottom: 2px; text-align: center; margin-top: 4px;"><table width="100%"><tr>';
            var r = value.split(",");
            for(var key in roles) {
                var cls = "update";
                if (r.indexOf(key) >= 0) {
                    h += ( '<td class="update" style="background-color:' + roles[key] + '">' + roleName[key] + '</td>');
                } else {
                    h += ( '<td class="update">' + "&nbsp;" + '</td>');
                }
            }

            h += '</tr></table></div>';
            return h;

        }
        //if(columnfield == "standFlag"){
        //    var r = "";
        //    if(value == "1"){
        //        r = "远";
        //    }else if(value == "0"){
        //        r = "近";
        //    }
        //    return '<div style="text-overflow: ellipsis; overflow: hidden; padding-bottom: 2px; text-align: center; margin-top: 4px;">' + r + '</div>';
        //}else if(columnfield == "flightDirection"){
        //    var r = "";
        //    if(value == "1"){
        //        r = "D";
        //    }else if(value == "0"){
        //        r = "A";
        //    }
        //    return '<div style="text-overflow: ellipsis; overflow: hidden; padding-bottom: 2px; text-align: center; margin-top: 4px;">' + r + '</div>';
        //}
    };

    var first = true;
    /**
     * 提交事件
     */

    var  source = null;

    $("#query_button").click(function (){
        if( ! g){
            $.ajax({
                url : "./getGridCols",
                type : "post",
                async : false,
                success : function (data){
                    //alert(toJSONString(data));
                    if(data){
                        g = data;
                        if(g.columns){
                            for(var i = 0 ; i < g.columns.length ; i ++){
                                var c = g.columns[i];
                                c['cellclassname'] = cellclass;
                                c['cellsrenderer'] = cellsrender;
                                var key = c['datafield'];
                                if(key == "scheduledTime"){
                                    c['filtertype'] = "list";
                                }else if(key == "standNum" || key == "stat"){
                                    c['filtercondition'] = "equal";
                                }
                            }
                        }

                        source = {
                            url : "./query",
                            datatype : "json",
                            type : "POST",
                            data : {},
                            id : 'id',
                            datafields : g.datafields
                        };
                    }
                }
            });
        }

        var dateRange = $("#scheduledTime").val().split(" - ");


        if(new Date(dateRange[1]).getTime() - new Date(dateRange[0]).getTime() > 31*24*60*60*1000){
            alert("日期最大跨度不允许超过31天");
            return;
        }

        //destroyGrid("grid");

        source.data =  {
            flightDirection : $("#flightDirection").val(),
            standFlag : $("#standFlag").val(),
            flightIdentity : $("#flightIdentity").val(),
            airLineName : $("#airLineName").val(),
            airlineHandler : $("#airlineHandler").val(),
            registeration : $("#registeration").val(),
            scheduledTime : $("#scheduledTime").val(),
            error : $("#error").val()
        };





        var dataAdapter = createDataAdapter(source);

        if(first){
            first = false;
            $("#grid").jqxGrid({
                width: "100%",
                height : "100%",
                theme:'arctic',
                source: dataAdapter,
                showfilterrow: true,
                filterable: true,
                sortable : true,
                enabletooltips: true,
                //autoheight: true,
                editable: false,
                selectionmode: 'singlerow',
                altrows: true,
                columns: g.columns,
                handlekeyboardnavigation: function(event)
                {
                    var key = event.charCode ? event.charCode : event.keyCode ? event.keyCode : 0;
                    if (key == 13) {
                        $("#grid").trigger("rowdoubleclick");
                        return true;
                    }

                }
            }).on("bindingcomplete",function (){

                $("#totalNumber").html($(this).jqxGrid('getrows').length + " 条记录");

                resize();

            }).on('rowselect', function (event)
            {
                //flightId = event.args.row.id;

            }).on('rowdoubleclick', function (event) {
                if(true){
                    //var rowid =  flightId;
                    var g = $("#grid");
                    var rowid = g.jqxGrid("getrowid",g.jqxGrid("getselectedrowindex"));
                    flightId = rowid;
                    if(rowid){
                        allowUpdate = false;
                        $.post(
                            "./detail?id=" + rowid,
                            function (b){

                            //alert(toJSONString(b));fl
                                if(b){
                                    if( ! b['stat'] || b['stat'] == '0'){
                                        $("#cal_btn").hide();
                                    }else{
                                        $("#cal_btn").show();
                                    }


                                    flightBaseId = b['flightBaseId'];


                                    $("#flightId").attr('title',flightId + "," + flightBaseId);

                                    //把航班预览信息放到 tab上方,以便于用户知道详细数据是属于哪个航班
                                    $("#flightDetailPreview").html(b['flightDirection']
                                        + b['abnormalFlag']
                                        + " - " + b['airLineName']
                                        + " - " + b['airlineHandler']
                                        + " - " + b['scheduledTime']
                                        + " - " + b['aFlightIdentity'] + " (" + b['scheduledLandedTime'] + ")"
                                        + " - " + b['dFlightIdentity'] + " (" + b['scheduledTakeOffTime'] + ")"
                                        + " - " + "(" +b['aircraftType'] + ")"

                                    );

                                    //显示tabs
                                    $("#detail-panel").show();
                                    resize();
                                    //flightId = b.id;
                                    /**
                                     * 填充航班\仓单\vip 详细 列表 信息
                                     */
                                    $("td[colname]").each(function (){
                                        var $this = $(this);
                                        var name = $this.attr("colname");
                                        var value = b[name];
                                        if(typeof(value) === 'undefined' || value == null){
                                            value = "";
                                        }
                                        if($this.attr("editable")){
                                            $this.children("input").val(value);
                                        }else{
                                            $this.html(value);
                                        }
                                    });

                                    /**
                                     * 填充廊桥中的 登机桥数量
                                     */
                                    $("#passengerBridgeNumber").val(b['passengerBridgeNumber']);

                                    /**
                                     * 填充资源\廊桥信息
                                     */
                                    for(var i = 0 ; i < detailTabs.grid.length ; i ++){
                                        var g = $("#" + detailTabs.grid[i]);
                                        var data = g.jqxGrid("getrows");
                                        var ids = [];
                                        var values = [];
                                        for(var j = 0 ; j < data.length ; j ++){
                                            var d = data[j];
                                            var key = d.colname;
                                            var value = b[key];
                                            if(typeof(value) === 'undefined' || value == null){
                                                value = "";
                                            }
                                            d.quantity = value;

                                            ids.push(key);
                                            values.push(d);
                                        }
                                        g.jqxGrid("updaterow",ids,values);
                                    }
                                    /**
                                     * 填充提示信息
                                     */
                                        //alert(b['alarmMessage']);
                                    $("#alarmMessage").html(b['alarmMessage']);

                                    //获取从表信息 并且 装载数据
                                    //flight_load_data

                                    loadData(rowid);




                                }else{
                                    flightId = null;
                                    alert("该航班数据可能已经被修改或删除,请重新查询");
                                }
                                allowUpdate = true;
                            }
                        );
                    }
                }
            });
        }else{
            $('#grid').jqxGrid('updatebounddata');

            //$("#grid").jqxGrid({
            //    source: dataAdapter
            //});
        }
    }).click();

    $("#search-panel,#detail-panel").css("margin-top","10px");

    var updateInfo = function (key,value,callback){
        $.post(
            "./resourceUpdate",
            {
                id : flightId,
                colname: key,
                value :value,
                role:role
            },
            function (rtn){
                if(isString(rtn)){
                    alert(rtn);
                    rtn = false;
                }else{
                    /**
                     * 客座率
                     */
                    if(rtn.hasOwnProperty("passengerloadFactor")){
                        //alert(rtn["passengerloadFactor"]);
                        $("td[colname=passengerloadFactor]").html(rtn["passengerloadFactor"]);
                    }

                    var g = $("#grid");
                    var rowdata = g.jqxGrid('getrowdatabyid', flightId);

                    for(var key in rtn){
                        if( rowdata.hasOwnProperty(key) ){
                            rowdata[key] = rtn[key];
                        }
                    }


                    //rowdata.alarmMessage = rtn.alarmMessage;
                    g.jqxGrid("updaterow",flightId,rowdata);
                    $("#alarmMessage").html(rtn['alarmMessage']);
                    rtn = true;
                }


                if(isFunction(callback)){
                    callback(rtn);
                }


            }
        );
    };

    var updaterow =  function (rowid, rowdata, commit) {
        // synchronize with the server - send update command
        // call commit with parameter true if the synchronization with the server is successful
        // and with parameter false if the synchronization failder.
        //    alert();
        //commit(true);

        //alert(flightId);
        if( ! allowUpdate){
            commit(true);
            return;
        }
        if(flightId){
            updateInfo(rowdata.colname,rowdata.quantity,function (rtn){
                commit(rtn);
            });
        }
    };


    var pbn = $("#passengerBridgeNumber");
    pbn.jqxDropDownList({ width: 70, height: 25 });
    if(! checkRole(pbn.attr("role"))){
        pbn.jqxDropDownList({disabled:true});
    }


    $("#passengerBridgeNumber").on('change', function (event){
        if(allowUpdate){
            var args = event.args;
            if (args) {
                updateInfo("passengerBridgeNumber", args.item.value);
            }
        }

    });


    for(var i = 0 ; i < detailTabs.grid.length ; i ++){
        var g = $("#" + detailTabs.grid[i]);
        if(g.length == 0){
            return;
        }
        var h = detailTabs.gridHeight[i];

        var  hasPermission = checkRole(g.attr("role"));

        g.jqxGrid({
            height: h,
            width: '99%'  ,
            theme:'arctic',
            editable: true,
            columnsresize:true,
            source:  createDataAdapter(    {
                updaterow : updaterow,
                url : "./resource?type=" + detailTabs.grid[i],
                datatype : "json",
                datafields: [
                    { name: 'resourceName', type: 'string' },
                    { name: 'colname', type: 'string' },
                    { name: 'startTime', type: 'string' },
                    { name: 'endTime', type: 'string' },
                    { name: 'quantity', type: 'string' },
                    { name: 'unit', type: 'string' },
                    { name: 'unitPrice', type: 'string' }
                ],
                id : "colname"
            }),
            altrows: true,
            sortable: false,
            selectionmode: 'singlecell',
            columns: (function (hasPermission){
                var item = [
                    { text: '资源', datafield: 'resourceName',align:"right",cellsalign:"right",editable:false },
                    //{ text: '开始时间', datafield: 'startTime',align:"right",cellsalign:"right",editable:false },
                    //{ text: '结束时间', datafield: 'endTime',align:"right",cellsalign:"right",editable:false}
                ];

                if(hasPermission){
                    item.push({
                        text: '数量', datafield: 'quantity', align: "right", cellsalign: "right",//columntype: 'numberinput',cellsformat:"d",
                        // columntype: 'combobox',
                        columntype: 'numberinput',
                        createeditor: function (row, value, editor) {
                            //editor.jqxComboBox({ source: countriesAdapter, displayMember: 'label', valueMember: 'value' });
                            editor.jqxNumberInput({ inputMode: 'simple', spinButtons: true,spinButtonsStep:5,decimalDigits:1,theme:"arctic",spinButtonsWidth:25,min:0,max:99999999 });
                        }
                    });
                }else{
                    item.push( { text: '数量', datafield: 'quantity',align:"right",cellsalign:"right",editable:false})
                }


                item.push({text: '单位', datafield: 'unit' ,align:"right",cellsalign:"right",editable:false});
                item.push({text: '单价', datafield: 'unitPrice',align:"right",cellsalign:"right",editable:false});
                return  item;
            }(hasPermission))
        });
    }



    /**
     * 仓单信息,汽服,廊桥 内信息可修改
     * edity by wangsq 20151218 ---- 航班信息中 [航班号] [机位也] 也需要可修改
     */
    for(var i = 0 ; i < detailTabs.table.length ; i ++){
        var tab = $("#" + detailTabs.table[i]);
        if(tab.length == 0){
            return;
        }
        //没有权限的角色 去除editable标识
        var  hasPermission = checkRole(tab.attr("role"));
        tab.find("td[colname][editable]").each(function (){
            if( ! hasPermission){
                $(this).removeAttr("editable");
            }else{
                var input = $("<input/>");
                $(this).append(input);
                input.jqxInput({
                    maxLength:8,
                    width : "90%",
                    height:"100%"
                }).on("change",function (){
                    if(allowUpdate){
                        var $this = $(this);
                        var colname = $this.parent().attr("colname");
                        var value = $this.val();
                        if(colname == "flightIdentity" || colname == "standNum"){

                        }else{
                            if(!isNum(value)){
                                alert("参数格式不正确");
                                $this.focus();
                                $this.val("");
                                return;
                            }

                            if(colname == "firstClassCount" || colname == "vipCount" || colname == "vipFirstAirdromeUsedCount"){
                                var n = Number(value);
                                if(n < 0 || n > 100){
                                    alert("参数范围在0-100之间");
                                    $this.focus();
                                    $this.val("");
                                    return;
                                }
                            }

                        }

                        updateInfo($this.parent().attr("colname"),$this.val(),function (rtn){
                            if( ! rtn){
                                $this.focus();
                                $this.val("");
                            }
                        });
                    }
                });
                input.css("border-color","#fff");
            }

        });
    }


    //$("#manifestDetail .jqx-input,#vipDetail .jqx-input").each(function (){
    //    $(this).css("border-color","#fff");
    //});


    // Create jqxTabs.
    $('#jqxTabs').jqxTabs({ width: '100%', height:'100%', position: 'top',theme:'arctic'});
    //$('#jqxTabs').jqxTabs({ selectedItem: (role - 1)});
    /**
     * 隐藏下方详细信息栏
     */
    $("#jqxTabs div.jqx-tabs-header").on("dblclick",function (){
        $("#detail-panel").hide();
        resize();
    }).trigger("dblclick");



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
            uploadScript : './manifestImport',
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
                if(rtn.indexOf("manifest_import_back_file") >= 0){
                    dl = true;
                    rtn = rtn.replace("manifest_import_back_file","");
                }
                alert(rtn);
                if(dl){
                    download("./backfile");
                }
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

    //切到详细tab第一栏
    if(role < 2){
        $('#jqxTabs').jqxTabs({ selectedItem: role-1 });
    }else{
        $('#jqxTabs').jqxTabs({ selectedItem: role });
    }


});