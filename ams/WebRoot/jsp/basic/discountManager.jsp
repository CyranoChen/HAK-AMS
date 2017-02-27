<%@ page import="com.wonders.frame.ams.utils.AppUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <title>折扣维护</title>
  <%@include file="../../static/included.jsp"%>
    <script src="../../static/uploadifive/jquery.uploadifive.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../../static/uploadifive/uploadifive.css">

  <script>
    var queryflag = true;

    var datafield = "";


    $(function (){

        $("#export").on("click",function (){
            download("./exp?registration="+$("#registration").val() + "&airlineOfFlight="  +$("#airlineOfFlight").val() );
        });




      $("#discountManagerBar").addClass("selected");

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
                    var c = rtn.g.columns;
                    for(var i = 0 ; i < c.length ; i ++){
                      if(i < 5){
                        c[i]['editable'] = false;
                      }
                    }

                    var source =
                    {
                      localdata: rtn.data,
                      datatype: "json",
                      datafields: rtn.g.datafields,
                      id : "registration",
                      updaterow: function (rowid, newdata, commit) {

                        $.post(
                                "./update",
                                {
                                  registration : rowid,
                                  key : datafield,
                                  discount : newdata[datafield]
                                },
                                function (rtn){
                                  commit(rtn);
                                }
                        );
                        // synchronize with the server - send update command
                        // call commit with parameter true if the synchronization with the server is successful
                        // and with parameter false if the synchronization failed.
//                        commit(false);
                      }
                    };

                    var dataAdapter = new $.jqx.dataAdapter(source);

                    $("#grid").on("bindingcomplete",function (){
                      $("#totalNumber").html($(this).jqxGrid('getrows').length + " 条记录");
                      $('#loading').jqxLoader('close');
                      queryflag = true;
                    });

                    $("#grid").on('cellbeginedit', function (event) {
                        datafield = event.args.datafield;
                    });


                    $("#grid").jqxGrid({
                      altrows: true,
                      editable: <%="1".equals(request.getSession().getAttribute("role"))%>,
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
//        theme:Global.theme,
//        sortable:true,
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

<body style="padding: 10px;min-width: 1024px;">
<%@ include file="./headerNavigation.jsp"%>

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
        <input type="button" id="export" class="dc" title=导出" />
          <input type="button" id="import" class="dr" title="导入" />


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







<!--20161017 新增导入功能-->
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
                    uploadScript : './imp',
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
                        $('#clearQueue').trigger("click");
                        $('#uploadPanel').jqxWindow("close");

                        if(rtn == "success"){
                            alert("导入成功!");
                            $("#query_button").click();
                        }else{
                            alert(rtn);
                        }


                    },
                    onError : function(errorType) {
                        uploadedState();
                    },
                    onCancel : function (){
                        uploadedState();
                    },
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


        });
    </script>

</div>







</body>
</html>
