
function showHide(){
//alert($(parent.document).find("frameset[id=main]").attr("cols"));
	if ($(parent.document).find("frameset[id=main]").attr("cols") == "210,*") {
		//alert(1);
		$(parent.document).find("frameset[id=main]").attr("cols","0,*");
		$("#show").attr("src","/portal/css/default/images/sideBar_arrow_right.jpg");
		$("#show").attr("title","展开");
	}else{
		//alert(2);
		$(parent.document).find("frameset[id=main]").attr("cols","210,*");
		$("#show").attr("src","/portal/css/default/images/sideBar_arrow_left.jpg");
		$("#show").attr("title","收起");
	}
}


function loadShow(){
	if ($(parent.document).find("frameset[id=main]").attr("cols") == "210,*") {
		//alert(1);
		//$(parent.document).find("frameset[id=main]").attr("cols","0,*");
		$("#show").attr("src","/portal/css/default/images/sideBar_arrow_left.jpg");
		$("#show").attr("title","收起");
	}else{
		//alert(2);
		//$(parent.document).find("frameset[id=main]").attr("cols","210,*");
		
		$("#show").attr("src","/portal/css/default/images/sideBar_arrow_right.jpg");
		$("#show").attr("title","展开");
	}
}

