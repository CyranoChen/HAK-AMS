function addDetail(obj){
//	alert(getRootPath);
	var getValue = $(obj).parent().children("input[type=text]").val().replace(/(^\s*)|(\s*$)/g,'');
	if(getValue==""){
		alert("请选择单位/部门");
		$(obj).parent().children("input[type=text]").focus();
	}else if(getValue.indexOf("\"")!=-1){
		alert("单位/部门不能包含引号！");
		$(obj).parent().children("input[type=text]").focus();
	}else if(getValue.indexOf(":")!=-1){
		alert("单位/部门不能包含冒号！");
		$(obj).parent().children("input[type=text]").focus();
	}else if(getValue.indexOf(",")!=-1){
		alert("单位/部门不能包含逗号！");
		$(obj).parent().children("input[type=text]").focus();
	}else{
		$(obj).parent().append("<p><input type='checkbox' id='detailCheck'/>&nbsp;&nbsp;<input type='text' class='input_small' id='detailInput' onblur='checkValueInput(this)' style='display:inline;text-align:right'/>&nbsp;&nbsp;<img src='"+
				getRootPath()+"/images/arrow_left.jpg' width='20px' height='15px' style='display:inline'/>&nbsp;&nbsp;<span style='display:inline'>" +getValue + "</span></p>");
		var num = $(obj).parent().find("p").length-1;
		$(obj).parent().children("p:eq("+num+")").children("#detailInput").focus();
		$(obj).parent().children("input[type=text]").val("");
	}
}

function deleteDetail(obj){
	var num = 0;
	$("input[id=detailCheck]").each(function(index){
		if($(this).attr("checked")=="checked"){
			num += 1;
			$(this).parent("p").remove();
		}
	});
	if(num==0){
		alert("请选择要删除的资金来源！");
	}
}

function checkValueInput(obj){
	if(!$.isNumeric($(obj).val())){
		alert("资金来源金额请输入数字！");
		$(obj).focus();
	}
}

function saveDetail(obj){
	if($(obj).parent().find("p").length>0){
		var saveValue = "{";
		$(obj).parent().children("p").each(function(index){
			if(index>0){
				saveValue += ",";
			}
			saveValue += "\""+$(this).children("span").html()+"\":\""+parseFloat($(this).children("#detailInput").val()).toFixed(4)+"\"";
		});
		saveValue += "}";
		$(obj).val(saveValue);
	}
}

function loadEditPage(obj){
	if($(obj).val().indexOf("{")!=-1){
		var getValueArray = new Array();
		getValueArray = $(obj).val().replace("{","").replace("}","").replaceAll("\"","").split(",");
		for(var i=0;i<getValueArray.length;i++){
			$(obj).parent().append("<p><input type='checkbox' id='detailCheck'/>&nbsp;&nbsp;<input type='text' class='input_small' id='detailInput' onblur='checkValueInput(this)' style='display:inline;text-align:right' value='"+
					getValueArray[i].split(":")[1]+"'/>&nbsp;&nbsp;<img src='"+	getRootPath()+"/images/arrow_left.jpg' width='20px' height='15px' style='display:inline'/>&nbsp;&nbsp;<span style='display:inline'>" +
					getValueArray[i].split(":")[0] +"</span></p>");
		}
	}
}

function loadViewPage(obj){
	if($(obj).val().indexOf("{")!=-1){
		var getValueArray = new Array();
		getValueArray = $(obj).val().replace("{","").replace("}","").replaceAll("\"","").split(",");
		for(var i=0;i<getValueArray.length;i++){
			$(obj).parent().append("<p>"+getValueArray[i].split(":")[0]+"："+getValueArray[i].split(":")[1]+"</p>");
		}
	}
}

//replaceall方法
String.prototype.replaceAll  = function(s1,s2){  
  return this.replace(new RegExp(s1,"gm"),s2);   
}

//js获取项目名，如：/uimcardprj
function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    //return(localhostPaht+projectName);
    return(projectName);
}