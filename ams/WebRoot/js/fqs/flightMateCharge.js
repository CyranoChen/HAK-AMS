var flightId = null;
var g = null;


var errorMsg = null;


$(document).ready(function () {


    var resize = function (){
        $("#calGrid,#notcalGrid").jqxGrid('render');
        $("div.jqx-grid-column-header").css("z-index",0);

    };

    $(window).on("resize",function (){
        resize();
    });

    /*
     * 初始化查询条件的控件
     */
    $("#flightDirection").jqxDropDownList({ width: 70, height: 25,theme:"arctic"});
    $("#flightIdentity,#airLineName,#registeration").jqxInput({ width: 70, height: 25,theme:"arctic",maxLength : 20 });
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
        //value : "2015/07/22"
    });



    var updateGridSource = function (gid,data){
        var source =
        {
            id : "id",
            localdata: data,
            datatype: "json",
            datafields: g.datafields
        };
        var dataAdapter = new $.jqx.dataAdapter(source);


        $("#" + gid).jqxGrid({
            source: dataAdapter
        });

    }

    var buildGrid = function (gid){
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
                                //c['cellclassname'] = cellclass;
                                //c['cellsrenderer'] = cellsrender;
                            }
                        }




                    }
                }
            });
        }

        var grid = $("#" + gid);

        grid.jqxGrid({
            width: "100%",
            height : "100%",
            theme:'arctic',
            enabletooltips: true,
            source: [],
            showfilterrow: true,
            columnsresize:true,
            filterable: true,
            sortable : true,
            columnsResize:true,
            //autoheight: true,
            editable: false,
            selectionmode: 'checkbox',
            altrows: true,
            columns: g.columns
        }).on("bindingcomplete",function (){
            $("#totalNumber_"+gid).html($(this).jqxGrid('getrows').length + " 条记录");
            resize();
        });



        //if(gid == "notcalGrid"){
        //    grid.jqxGrid({
        //        selectionmode: 'checkbox'
        //    });
        //}


    }
    buildGrid("calGrid");
    buildGrid("notcalGrid");
    
    $("#calGrid").on('celldoubleclick', function (event) {
    	var rowIndex = event.args.rowindex;
		var rowId = $("#calGrid").jqxGrid('getrowid',rowIndex);
		$.post(
	    		"../findChargeTermByFmiId",
	    		{
	    			flightMateInfoId : rowId
	    		},
	    		function(obj){
	    			if(obj.length>0){
	    		    	window.open("../fee2/index?id=" + rowId);
	    			}else{
	    				alert("该航班未生成条目");
	    			}
	    		}
	    	)
    });
    
    /**
     * 查询事件
     */
    $("#query_button").click(function (){

        var dateRange = $("#scheduledTime").val().split(" - ");


        if(new Date(dateRange[1]).getTime() - new Date(dateRange[0]).getTime() > 31*24*60*60*1000){
            alert("日期最大跨度不允许超过31天");
            return;
        }


        $("#calGrid,#notcalGrid").jqxGrid("showloadelement");

        $.post(
            "./query",
            {
                flightDirection : $("#flightDirection").val(),
                //standFlag : $("#standFlag").val(),
                flightIdentity : $("#flightIdentity").val(),
                airLineName : $("#airLineName").val(),
                registeration : $("#registeration").val(),
                scheduledTime : $("#scheduledTime").val()
            },
            function (rtn){
                updateGridSource("calGrid",rtn["1"]);
                updateGridSource("notcalGrid",rtn["0"]);
                $("#calGrid,#notcalGrid").jqxGrid("hideloadelement");
            }
        );

    }).click();



    $("#search-panel").css("margin-top","10px");


    $("#loading").jqxLoader({ isModal: true,imagePosition: 'top',width: 100, height: 60 });

    /**
     * 提交事件
     */
    $("#cal").click(function (){
        var ids = [];
        var g = $("#calGrid");
        var idxes = g.jqxGrid('getselectedrowindexes');

        for(var i = 0 ; i < idxes.length ; i ++){
            ids.push(g.jqxGrid('getrowid', idxes[i]));
        }

        g = $("#notcalGrid");
        idxes = g.jqxGrid('getselectedrowindexes');
        for(var i = 0 ; i < idxes.length ; i ++){
            ids.push(g.jqxGrid('getrowid', idxes[i]));
        }

        if(ids.length == 0){
            alert("请至少选择一条航班数据!");
            return;
        }

        $("#cal").attr("disabled","disabled");
        $('#loading').jqxLoader('open');

        $.post(
            "./cal",
            {ids : ids.join(",")},
            function (rtn){
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

                }else{
                    alert("执行异常!");
                }
                $("#cal").removeAttr("disabled");
                $('#loading').jqxLoader('close');
                $("#calGrid,#notcalGrid").jqxGrid('clearselection');
                $("#query_button").click();
            }
        );




    });





});