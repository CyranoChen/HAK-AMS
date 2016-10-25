
//$(function (){
	//设置浏览器不使用缓存获取数据。防止陆帅峰框架get api 缓存数据
	$.ajaxSetup (
		{
			cache: false
		}
	);
//})

var Global = {
	root : getRoot() ,
	ajax_listener_switch : true,
	ticket : null,
	ruleTypeId : null,
	userLoginFlag : null,
	userPermissionFlag : null,
	organRootId : null,
	resourceRootId : null,
	theme : "fqs",
	systemMenu : {}
};



var DateFormat = {
    yMd: "yyyy-MM-dd",
    yMd_Hms: "yyyy-MM-dd HH:mm:ss",
    yMd_Hm: "yyyy-MM-dd HH:mm",
    yM: "yyyy-MM",
    Md: "MM-dd",
    Hm: "HH:mm",
    ms: "mm:ss",
    y: "yyyy",
    M: "MM",
    d: "dd",
    h: "HH",
    m: "mm",
    s: "ss"
};

var Chk = {
	spaceCheck : function (str){
		return typeof(str) == "undefined" ? false : $.trim(str).length > 0;
	}
}

/**
 * @see 打开webSocket连接
 * @param url
 * @returns {WebSocket}
 */
function openWs(url){
	return new WebSocket(url);
}

/**
 * @see 向webSocket客户端发送消息
 * @param ws
 * @param content
 */
function sendByWs(ws,content,n){
	if(! isNumber(n)){
		n = 0;
	}
	if(n > 10){
		return false;
	}
	
	if(getWsState(ws) == WebSocket.OPEN){
		ws.send(content);
		return true;
	}
	
	setTimeout(function (){
		return sendByWs(ws,content, ++ n );
	},100);
	

	
}

/**
 * @see 判断对象是否为WebSocket对象
 * @param ws
 * @returns {Boolean}
 */
function isWs(ws){
	return typeof(ws) != "undefined" && ws instanceof Object && ws.constructor == WebSocket;
}

/**
 * @see 获取WebSocket对象状态
 * @param ws
 * @ return CONNECTING = 0、OPEN = 1、CLOSING = 2、CLOSED = 3
 */
function getWsState(ws){
	return isWs(ws) ? ws.readyState : - 1;
}

/**
 * @see 关闭WebSocket连接
 * @param ws
 * @returns {Boolean}
 */
function closeWs(ws){
	var state = getWsState(ws);
	if(state == WebSocket.OPEN || state == WebSocket.CONNECTING){
		ws.close();
		return true;
	}
	return false;
}




/**
 * @see 获取项目根路径
 * @returns
 */
function getRoot(){
    //获取当前网址，如： http://localhost:8080/acdm/xxx/xxx.jsp
    var path=window.document.location.href;
    //获取主机地址之后的目录，如： acdm/xxx/xxx.jsp
    var pathName=window.document.location.pathname;
    //获取主机地址，如： http://localhost:8080
    var localhostPath=path.substring(0,path.indexOf(pathName));
    //获取带"/"的项目名，如：/acdm
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return localhostPath+projectName;
}

function openWindow(url,windowName,w,h){
	w = Number((w + "").toLowerCase().replace("px",""));
	h = Number((h + "").toLowerCase().replace("px",""));
	var top = ( window.screen.height - h ) / 2;
	var left = ( window.screen.width - w ) / 2;
	var win = window.open(url,windowName,"width=" + w + ",height=" + h + ",top=" + top + ",left=" + left + ",resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no");
	return win;
}

/**
 * @see 获取Websocket根路径
 * @returns
 */
function getWebsocketRoot(){
	return getRoot().replace(/^http:/i,"ws:");
}

/**
 * @see 获取Websocket 地址
 * @param url
 * @returns
 */
function getWebsocket(url){
	return getWebsocketRoot() + url;
}

function isEmpty(o){
	if( ! isObject(o)){
		return false;
	}
	for(var k in o){
		return false;
	}
	return true;
}


function isNumber(o){
	return '[object Number]' === Object.prototype.toString.call(o) && isFinite(o);
}
function isArray(o){
	return '[object Array]' === Object.prototype.toString.call(o);
}
function isDate(o){
	 return "[object Date]" === Object.prototype.toString.call(o)  && o.toString() !== 'Invalid Date'  &&  !isNaN(o);
}
function isFunction(o) {
    return '[object Function]' === Object.prototype.toString.call(o);
}
function isObject(o) {
    return '[object Object]' === Object.prototype.toString.call(o);
}
function isString (o) {
    return '[object String]' == Object.prototype.toString.call(o);
}
function length(o){
	return isObject(o) ? Object.keys(o).length : 0;
}

function isMobilePhone(str) {
   var re = /^1\d{10}$/;
   return re.test(str);
}

function isTelephone(str){
	var re = /^(\d{8})|(0\d{2,3}-?\d{7,8})$/;
	 return re.test(str);
}


function toJSONString(o,replacer,space){
	if( ! isObject(o) && ! isArray(o)){
		return "";
	}
	if( ! replacer){
		replacer = function (key,value){
			return value == null ? "" : value;
		}
	}
	return JSON.stringify(o,replacer,space);
}

function toJSON(str,replacer){
	try {
		return JSON.parse(str);
	} catch (e) {
		//alert("exception in function [ commons.js -> toJSON ]  \n " + e.name + ": " + e.message);
		return replacer;
	}
}

function download(url,data){
	$("#tmp-dl-form").remove();
	$("#tmp-dl-iframe").remove();
	
	var form = $("<form id='tmp-dl-form' method='post' target='tmp-dl-iframe'>" +
			"<input type='hidden' name='data'>" +
			"</form>");
	form.attr("action",url);
	form.children("input").val(data);
	var iframe = $("<iframe id='tmp-dl-iframe' style='display:none' name='tmp-dl-iframe'></iframe>")
	$("body").append(iframe).append(form);
	form.submit();

}



function getMin(arr,key,defaultValue){
	
}

function getMax(arr,key,defaultValue){
	var numbers = [defaultValue];
	if(isArray(arr) && isString(key)){
		for(var i in arr){
			numbers.push(arr[i][key]);
		}
	}
	return Math.max.apply(Math, numbers);
}


function toIndex(){
	window.location.href = Global.root;
}


/**
 * localStorage
 */
var local = {
		set : function (key,value){
			if( ! isString(value)){
				value = toJSONString(value);
			}
			
			window.localStorage.setItem(key,value);
		},
		get : function (key){
			return window.localStorage.getItem(key);
		}
};


function selectTab(){
	var h = window.document.location.href;
	h = h.substring(h.lastIndexOf("/") + 1 , h.length);
	h = h.split(".")[0];
	var ht = $("#header_tabs");
	ht.find("a").each(function (){
		var href = $(this).attr("href");
		if(href && href.indexOf(h) >= 0){
			var x = $(this).parent("li");
			var y = x.parent("ul").parent("li");
			if(y.length > 0){
				x.css("border-left","#3099dd 3px solid");
			}else{
				x.addClass("selected");
			}
			y.addClass("selected");

// 			$(this).parent("li").addClass("selected").parent("ul").parent("li").addClass("selected");
			return;
		}
	});

	var group = ht.attr("group");
	$("#leftMenu #" + group).addClass("selected");
}



function createform(url,args){

	var f = $("<form></form>");

	f.attr("action",url);
	f.attr("method","post");

	if(args){
		for(var key in args){
			var input = $("<input/>");
			input.attr("type","hidden");
			input.attr("name",key);
			input.attr("value",args[key]);

			f.append(input);

		}
	}

	$("body").append(f);
	return f;
}

function submitform(url,args){
	createform(url,args).submit();
}

function getTrueLength(str){
	return str.replace(/[^\x00-\xff]/g,"ww").length;
}

function getInputValueFromForm(id,replacer){
	var args = {};
	var  f = $("#" + id);
	f.find("input,textarea").each(function (){
		var key = $(this).attr("id");
		if(replacer && key){
			key = key.replace(replacer,"");
		}
		args[key] = $(this).val();
	})
	return args;
}
