// -----------轮播图JS开始------------------
// (function () {
//     var mySwiper = new Swiper('.swiper-container', {
//         direction: 'horizontal',
//         loop: true, //是否开启自动轮播
//         autoplay: 4000, //轮播时间
//         speed: 1500, //轮播速度
//         pagination: '.swiper-pagination', // 如果需要分页器
//         paginationClickable: true, // 点击分页器
//     })
// });
// --------- tab 页面切换 --------
$(function () {
    var self;
    $('.tab-box ul li').on('click', function () {
        self = $(this);
        self.addClass('active').siblings('li').removeClass('active')
        $('.tab-right-active').removeClass('tab-right-active');
        $('.tab-right-content').eq($('.tab-box ul li').index(self)).addClass('tab-right-active')
        if ($('.tab-box ul li').index(self) == 2||$('.tab-box ul li').index(self) == 4) {
            $('.right-ab').css('display','none');
            $('.lwcc-left').css('width','1366px');
            $('.tab-right-content').css('width','1226px');
        }else{
            $('.right-ab').css('display','block');
            $('.lwcc-left').css('width','926px');
            $('.tab-right-content').css('width','783px');
        }
    })
})
//当多行输入框'copy-ts'中为空的时候提示
$(function () {
    $(".ztwb-bt").click(function () {
        if ($(".copy-tex").val() == "") {
            $(".copy-ts").remove();
            $(".copy-tex").before('<p class="copy-ts">（输入框内还未添加内容）</p>');
        } else {
            $(this).parents(".ztwb-d").hide().parents(".lwcc-left").find(".scwb-ts").show();
            $(".lwcc-left-bt").hide();
        }
    });
    $(".copy-tex").on('textarea change', function () {
        if (!$(".copy-tex").val() == "") {
            $(".copy-ts").remove();
        }
    });
});
//显示余额不足弹框函数
function Tk() {
    $(".yebz-tk").show();
    $("body").append('<div class="max-ceng"></div>')
}
//隐藏余额不足弹框函数
function unTk() {
    $(".yebz-tk").hide();
    $(".max-ceng").remove();
}
//关闭弹框
$(".yebz-tk-bt").click(function () {
    $(".yebz-tk").hide();
    $(".max-ceng").remove();
});
//提交论文成功
function gbtk() {
    $(".permissions").after('<div class="max-ceng"></div>');
    $(".permissions").show();
}
//关闭提交成功弹框
function ungbtk() {
    $(".max-ceng").remove();
    $(".permissions").fadeOut();
}
$('.navbar-list-ol li a').css('color', '#fff')
$('.navbar-list-ol li').on('mouseenter', function () {
    $(this).find('a').css('color', '#333')
    $(this).css({'background': '#fff'})
})
$('.navbar-list-ol li').on('mouseleave', function () {
    $(this).find('a').css('color', '#fff')
    $(this).css({'background': '#213B5B'})
})
// 登录框切换
$(function(){
    $('.login-qh-wx').on('click',function(){
        $('.sm-box').css('display','block').siblings('.login-d').css('display','none')
    });
    $('.login-wx').on('click',function(){
        $('.sm-box').css('display','block').siblings('.login-d').css('display','none')
    });
    $('.login-qh-pc').on('click',function(){
        $('.dl-box').css('display','block').siblings('.login-d').css('display','none')
    });
    $('.login-wjmm').on('click',function(){
        $('.zhmm-box').css('display','block').siblings('.login-d').css('display','none')
    })
    $('.login-ljzc').on('click',function(){
        $('.zc-box').css('display','block').siblings('.login-d').css('display','none')
    })
    $('.login-h3-a').on('click',function(){
        $('.zc-box').css('display','none').siblings('.dl-box').css('display','block').siblings('.login-d').css('display','none')
    })
})
function ljzc(){
        $('.zc-box').css('display','block').siblings('.login-d').css('display','none')
}
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
