//重写jquery的ajax方法 用作异步请求的 用户登录状态判断
//jQuery(function($) {
// 备份jquery的ajax方法
    var _ajax = $.ajax;
    $.ajax = function(opt) {
//        var _success = opt && opt.success || function(a, b) {};
        var _opt = $.extend(opt, {
        	complete : function (XMLHttpRequest,textStatus){
        		if(XMLHttpRequest.responseText == Global.userLoginFlag){
                    toIndex();
                    return;
        		}
        		
        	}
        });
        return _ajax(_opt);
    };
//});
