/*
*author:Null
*DATE:2013.5.24
*/
$(window).resize(function() {
    (".content-min").css("min-height",$(window).height()-135);
});
$(function(){		   
	//头页登录-
    $(".content-min").css("min-height",$(window).height()-135);
    //header登陆
    $(".dlcg").mouseover(function () {
        $(this).find(".yh-d").stop().show();
    });
    $(".dlcg").mouseleave(function () {
        $(this).find(".yh-d").stop().hide();
    });

    
    
});

//删除提示
function scts() {
    unscts();
    $("body").append('<div class="max-ceng"></div>')
    $("body").append('<div class="sc-ts"><p class="sc-ts-p1"><i class="sc-ts-i"></i><span>您确认要删除此条报告吗？</span></p><p class="sc-ts-p2"><a href="javascript:unscts();" class="sc-ts-a1">取消</a><a href="" class="sc-ts-a2">删除</a></p></div>');
}
function unscts() {
    $(".max-ceng").remove();
    $(".sc-ts").remove();
}




