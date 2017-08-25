/**
 * Created by GuangRi on 2016/10/21.
 */
$(function () {
    initLoginPage();

    mobileLoginEventHandle();

    mobileRegisterEventHandle();

    forgetPwdEventHandle();
});

function clearWarnInfo(val) {
    var li = val.parent().parent();
    li.removeClass('login-li-error');
    var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
    warnP.children('span').html('');
    warnP.hide();
}

/**
 * 初始化登录页面操作控制
 */
function initLoginPage() {

    $('#mobile').val($.cookie("userName"));
    $('#password').val($.cookie("password"));

    $('.login-bd li').removeClass('login-li-error');
    $('.login-li-ts-p').hide();
    // 去注册
    $('.login-qzc').click(function () {
        $(".zc-box").show().siblings(".login-d").hide();
    });

    // 去登录
    $('.login-qh-pc').click(function () {
        $(".dl-box").show().siblings(".login-d").hide();
    });
    $('.login-h3-a').click(function () {
        $(".dl-box").show().siblings(".login-d").hide();
    });

    // 显示服务条款
    $('.fwtk-a').click(function () {
        $(".max-ceng").remove();
        $(".fwtk-d").addClass("fwtk-d01").show();
        $("body").append('<div class="max-ceng"></div>');
    });

    // 隐藏服务条款
    $(".fwtk-d").hide();
    $('.fwtk-d-bt').click(function () {
        $(".fwtk-d").removeClass("fwtk-d01");
        $(".max-ceng").remove();
    });
    $('.zcxy-top-i').click(function () {
        $(".fwtk-d").removeClass("fwtk-d01");
        $(".max-ceng").remove();
    });

    // 微信扫码登录
    $('.login-qh-wx').click(function () {
        generateWeChatLoginQRCode();
    });
    $('.login-wx').click(function () {
        generateWeChatLoginQRCode();
    });

    $('.dl-sm-a').click(function () {
        $('.dl-d').hide();
    });

    // 忘记密码
    $('.login-wjmm').click(function () {
        $(".zhmm-box").show().siblings(".login-d").hide();
    });
    // 从会话过期登录弹窗跳转到登录页面时直接显示找回密码
    var operation = $(".weChatDiv input[name='operation']").val();
    if (operation == 'login-wjmm') {
        $(".zhmm-box").show().siblings(".login-d").hide();
    } else if (operation == 'login-register') {
        $(".zc-box").show().siblings(".login-d").hide();
    }
}

/**
 * 手机号登录操作处理
 */
function mobileLoginEventHandle() {
    $('#mobile').blur(function () {
        checkMobileNo($(this), $(this).val());
    }).focus(function () {
        clearWarnInfo($(this));
    });

    $('#password').blur(function () {
        checkInputPassword($(this), $(this).val());
    }).focus(function () {
        clearWarnInfo($(this));
    });

    $('#btnMobileLogin').click(function () {
        clearWarnInfo($('#mobile'));

        var mobileNode = $('#mobile');
        if (!checkMobileNo(mobileNode, mobileNode.val())) {
            return;
        }

        var passwordNode = $('#password');
        var isPasswordOK = checkInputPassword(passwordNode, passwordNode.val());
        if (!isPasswordOK) {
            return;
        }

        if (!$('#duox01').is(':checked')) {
            var warnP = passwordNode.parent().parent().siblings('.login-li-ts').find('.login-li-ts-p');
            warnP.children('span').html('请选择同意并遵守服务条款');
            warnP.show();
            return;
        }

        $.ajax({
            url: '/user/login.html',
            type: 'POST',
            cache: false,
            dataType: 'json',
            data: {
                mobile: mobileNode.val(),
                password: passwordNode.val(),
            },
            success: function (msg) {
                if (msg.respCode == RESP_SUCCESS) {
                    if ($('#duox02').is(':checked')) {
                        // 存储登录信息到cookie
                        $.cookie('userName', mobileNode.val(), {expires: 7});
                        $.cookie('password', passwordNode.val(), {expires: 7});
                    } else {
                        // 清除cookie中的登录信息
                        $.cookie('userName', '', {expires: -1});
                        $.cookie('password', '', {expires: -1});
                    }

                    var redoURL = msg.redoURL;
                    if (redoURL && redoURL.length > 0) {
                        window.location.href = redoURL;
                    } else {
                        window.location.href = "/paper/uploadText.html";
                    }
                } else {
                    var warnP = passwordNode.parent().parent().siblings('.login-li-ts').find('.login-li-ts-p');
                    warnP.children('span').html(msg.respDesc);
                    warnP.show();
                    switch (msg.respCode) {
                        case RESP_PWD_INCORRECT: {
                            passwordNode.parent().parent().addClass('login-li-error');
                            break;
                        }
                        case RESP_USER_NOT_EXIST: {
                            mobileNode.parent().parent().addClass('login-li-error');
                            break;
                        }
                    }
                }
            }
        });
    });
}

/**
 * 生成微信登录二维码
 */
function generateWeChatLoginQRCode() {
    $(".sm-box").show().siblings(".login-d").hide();

    var obj = new WxLogin({
        id: "weChatLoginQR",
        appid: $(".weChatDiv input[name='appId']").val(),
        scope: "snsapi_login",
        redirect_uri: encodeURIComponent($(".weChatDiv input[name='redirectUri']").val()),
        state: "0",
        style: "black"
        // href: "https://www.papertime.cc/resources/css/wechat.login.css"
    });
}

/**
 * 手机号注册操作处理
 */
function mobileRegisterEventHandle() {
    $("#sendRegisterSmsCode").click(function () {
        sendRegisterSmsCode(this);
    });

    $('#smsCode').focus(function () {
        var li = $(this).parent().parent().parent();
        li.removeClass('login-li-error');
        var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
        warnP.children('span').html('');
        warnP.hide();
    });

    $('#regMobile').blur(function () {
        checkMobileNo($(this), $(this).val());
    }).focus(function () {
        clearWarnInfo($(this));
    });

    $('#regPassword').blur(function () {
        checkInputPassword($(this), $(this).val());
    }).focus(function () {
        clearWarnInfo($(this));
    });

    $('#regPassword2').blur(function () {
        checkInputPassword($('#regPassword'), $('#regPassword').val(), $(this), $(this).val());
    }).focus(function () {
        clearWarnInfo($(this));
    });

    $('#btnMobileRegister').click(function () {
        mobileRegister();
    });
}

/**
 * 手机号注册
 */
function mobileRegister() {
    clearWarnInfo($('#regMobile'));

    var mobileNode = $('#regMobile');
    if (!checkMobileNo(mobileNode, mobileNode.val())) {
        return;
    }

    var passwordNode = $('#regPassword');
    var passwordNode2 = $('#regPassword2');
    var isPasswordOK = checkInputPassword(passwordNode, passwordNode.val(), passwordNode2, passwordNode2.val());
    if (!isPasswordOK) {
        return;
    }

    var smsCodeNode = $('#smsCode');
    var smsCode = smsCodeNode.val();
    if (isEmpty(smsCode) || smsCode.length != 4) {
        var li = smsCodeNode.parent().parent().parent();
        li.addClass('login-li-error');
        var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
        warnP.children('span').html('请输入4位短信验证码');
        warnP.show();
        return;
    }

    if (!$('#duox04').is(':checked')) {
        var warnP = passwordNode.parent().parent().siblings('.login-li-ts').find('.login-li-ts-p');
        warnP.children('span').html('请选择同意并遵守服务条款');
        warnP.show();
        return;
    }

    $.ajax({
        url: '/user/register.html',
        type: 'POST',
        cache: false,
        dataType: 'json',
        data: {
            mobile: mobileNode.val(),
            password: passwordNode.val(),
            smsCode: smsCode
        },
        success: function (msg) {
            if (msg.respCode == RESP_SUCCESS) {
                window.location.href = "/paper/uploadText.html";
            } else {
                var warnP = passwordNode.parent().parent().siblings('.login-li-ts').find('.login-li-ts-p');
                warnP.children('span').html(msg.respDesc);
                warnP.show();
                switch (msg.respCode) {
                    case RESP_SMS_INCORRECT: {
                        smsCodeNode.parent().parent().parent().addClass('login-li-error');
                        break;
                    }
                    case RESP_USER_NOT_EXIST: {
                        mobileNode.parent().parent().addClass('login-li-error');
                        break;
                    }
                }
            }
        }
    });
}

function sendRegisterSmsCode(val) {
    var mobileNode = $('#regMobile');
    var mobileNo = mobileNode.val();
    var wrapVal = $(val);
    var warnP = wrapVal.parent().parent().parent().siblings('.login-li-ts').find('.login-li-ts-p');
    warnP.children('span').html('');
    warnP.hide();
    if (!checkMobileNo(mobileNode, mobileNo)) {
        return;
    }
    var tokenName = $("input[name='paperTimeTokenName']").val();
    var tokenValue = $("input[name='" + tokenName + "']").val();
    var data = {};
    var tokenPrev = "paperTimeTokenName";
    var token = tokenName;
    var mobile = "mobile";
    var sendType = "sendType";
    data[tokenPrev] = tokenName;
    data[token] = tokenValue;
    data[mobile] = mobileNo;
    data[sendType] = 0;

    $.ajax({
        url: '/user/sendSmsCode.html',
        type: 'POST',
        cache: false,
        dataType: 'json',
        data: data,
        success: function (msg) {
            if (msg.respCode == RESP_SUCCESS) {
                sendSmsCodeCountDown(val);
                wrapVal.addClass("inp-yzms");
            } else {
                warnP.children('span').html(msg.respDesc);
                warnP.show();
                if (msg.respCode == RESP_USER_NOT_EXIST) {
                    mobileNode.parent().parent().addClass('login-li-error');
                } else {
                    wrapVal.parent().parent().parent().addClass('login-li-error');
                }
            }
        }
    });
}

function forgetPwdEventHandle() {
    $('#userName').focus(function () {
        var li = $(this).parent().parent();
        li.removeClass('login-li-error');
        var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
        warnP.children('span').html('');
        warnP.hide();
    }).blur(function () {
        if (isEmpty($(this).val()) || !mobilenoReg.test($(this).val())) {
            var li = $(this).parent().parent();
            li.addClass('login-li-error');
            var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
            warnP.children('span').html('请正确填写您的登录用户名');
            warnP.show();
        }
    });

    $('#forgetPwdSmsCode').focus(function () {
        var li = $(this).parent().parent().parent();
        li.removeClass('login-li-error');
        var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
        warnP.children('span').html('');
        warnP.hide();
    }).blur(function () {
        var smsCode = $(this).val();
        if (isEmpty(smsCode) || smsCode.length != 4) {
            var li = $(this).parent().parent().parent();
            li.addClass('login-li-error');
            var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
            warnP.children('span').html('请输入4位短信验证码');
            warnP.show();
        }
    });

    $('#newPassword').focus(function () {
        var li = $(this).parent().parent();
        li.removeClass('login-li-error');
        var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
        warnP.children('span').html('');
        warnP.hide();
    }).blur(function () {
        if (!passwordReg.test($(this).val())) {
            var li = $(this).parent().parent();
            li.addClass('login-li-error');
            var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
            warnP.children('span').html('密码为6到16位字母、数字和特殊字符');
            warnP.show();
        }
    });

    $('#newPassword2').focus(function () {
        var li = $(this).parent().parent();
        li.removeClass('login-li-error');
        var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
        warnP.children('span').html('');
        warnP.hide();
    }).blur(function () {
        if (!passwordReg.test($(this).val())) {
            var li = $(this).parent().parent();
            li.addClass('login-li-error');
            var warnP = li.siblings('.login-li-ts').find('.login-li-ts-p');
            warnP.children('span').html('密码为6到16位字母、数字和特殊字符');
            warnP.show();
        }
    });

    $('#sendForgetPwdSmsCode').click(function () {
        sendForgetPwdSmsCode(this);
    });

    //找回密码第一步，验证用户名是否存在
    $('.login-li').on('click', '.zhmm-box-bt01', function () {
        showSmsCodeSendButton($(this));
    });
    //找回密码第二步，校验短信验证码是否正确
    $(".login-li").on("click", ".zhmm-box-bt02", function () {
        validateForgetPwdSmsCode(this);
    });
    //找回密码第三步，设置新密码
    $(".login-li").on("click", ".zhmm-box-bt03", function () {
        resetPassword($(this))
    });
}

function showSmsCodeSendButton(btnYz) {
    var warnP = btnYz.parent().siblings('.login-li-ts').find('.login-li-ts-p');
    warnP.children('span').html('');
    warnP.hide();

    var userNameNode = $('#userName');
    if (isEmpty(userNameNode.val()) || !mobilenoReg.test(userNameNode.val())) {
        warnP.children('span').html('请填写您的登录用户名');
        warnP.show();
        return;
    }

    $.ajax({
        url: '/user/isUserExist.html',
        type: 'POST',
        cache: false,
        dataType: 'json',
        data: {
            'userName': userNameNode.val()
        },
        success: function (msg) {
            if (msg.respCode == RESP_SUCCESS) {
                $(".zhmm-box-li").show();
                btnYz.text("重置密码").removeClass("zhmm-box-bt01").off().addClass("zhmm-box-bt02");
            } else {
                userNameNode.parent().parent().addClass('login-li-error');
                warnP.children('span').html(msg.respDesc);
                warnP.show();
            }
        }
    });
}
function sendForgetPwdSmsCode(btnSend) {
    var warnP = $(btnSend).parent().parent().parent().siblings('.login-li-ts').find('.login-li-ts-p');
    warnP.children('span').html('');
    warnP.hide();

    var userNameNode = $('#userName');
    if (isEmpty(userNameNode.val()) || !mobilenoReg.test(userNameNode.val())) {
        warnP.children('span').html('请正确填写您的登录用户名');
        warnP.show();
        return;
    }

    var tokenName = $("#register-token").attr("name");
    var tokenValue = $("#register-token").val();
    var data = {};
    var tokenPrev = "paperTimeTokenName";
    var token = tokenName;
    var mobile = "userName";
    var sendType = "sendType";
    data[tokenPrev] = tokenName;
    data[token] = tokenValue;
    data[mobile] = userNameNode.val();
    data[sendType] = 1;


    $.ajax({
        url: '/user/sendSmsCode.html',
        type: 'POST',
        cache: false,
        dataType: 'json',
        data: data,
        success: function (msg) {
            if (msg.respCode == RESP_SUCCESS) {
                sendSmsCodeCountDown(btnSend);
                $(btnSend).addClass('inp-yzms');
            } else {
                warnP.children('span').html(msg.respDesc);
                warnP.show();
            }
        }
    });
}

/**
 * 校验短信验证码
 */
function validateForgetPwdSmsCode(btnValidate) {
    var warnP = $(btnValidate).parent().siblings('.login-li-ts').find('.login-li-ts-p');
    warnP.children('span').html('');
    warnP.hide();

    var userNameNode = $('#userName');
    if (isEmpty(userNameNode.val()) || !mobilenoReg.test(userNameNode.val())) {
        warnP.children('span').html('请正确填写您的登录用户名');
        warnP.show();
        return;
    }

    var smsCodeNode = $('#forgetPwdSmsCode');
    if (isEmpty(smsCodeNode.val()) || smsCodeNode.val().length != 4) {
        warnP.children('span').html('请输入短信验证码');
        warnP.show();
        return;
    }

    $.ajax({
        url: '/user/validateSmsCode.html',
        type: 'POST',
        cache: false,
        dataType: 'json',
        data: {
            'userName': userNameNode.val(),
            'smsCode': smsCodeNode.val(),
            'sendType': 1
        },
        success: function (msg) {
            if (msg.respCode == RESP_SUCCESS) {
                $(".zhmm-box-li").hide();
                $(".zhmm-box-zh").hide();
                $(".zhmm-box-mm").show();
                $(btnValidate).text("确认密码").removeClass("zhmm-box-bt02").off().addClass("zhmm-box-bt03");//移除第一次的点击事件
            } else {
                warnP.children('span').html(msg.respDesc);
                warnP.show();
            }
        }
    });
}

/**
 * 重置密码
 */
function resetPassword(btnReset) {
    var warnP = $(btnReset).parent().siblings('.login-li-ts').find('.login-li-ts-p');
    warnP.children('span').html('');
    warnP.hide();

    var passwordNode = $('#newPassword');
    var password2Node = $('#newPassword2');
    var isPasswordOK = checkInputPassword(passwordNode, passwordNode.val(), password2Node, password2Node.val());
    if (!isPasswordOK) {
        return;
    }

    $.ajax({
        url: '/user/resetPassword.html',
        type: 'POST',
        cache: false,
        dataType: 'json',
        data: {
            'userName': $('#userName').val(),
            'password': password2Node.val()
        },
        success: function (msg) {
            if (msg.respCode == RESP_SUCCESS) {
                $(".login-ul").hide();
                $(".cg-d").show();
                $(".login-h3-a").hide();
                redirectCountDown($('.cg-d-i'));
            } else {
                warnP.children('span').html(msg.respDesc);
                warnP.show();
            }
        }
    });
}

var sendSmsCountDownTime = 60;
var redirectCountDownTime = 3;

/**
 * 短信重新发送倒计时
 * @param val
 */
function sendSmsCodeCountDown(val) {
    if (sendSmsCountDownTime == 0) {
        val.removeAttribute('disabled');
        val.value = "点击获取验证码";
        $(".inp-yzm").removeClass("inp-yzms");
        $(".grxx-li-yzm").removeClass("inp-yzms");
        sendSmsCountDownTime = 5;
    } else {
        val.setAttribute('disabled', true);
        val.value = "重新发送(" + sendSmsCountDownTime + ")";
        sendSmsCountDownTime--;
        setTimeout(function () {
            sendSmsCodeCountDown(val);
        }, 1000);
    }
}

function redirectCountDown(val) {
    if (redirectCountDownTime == 1) {
        window.location.href = '/user/login.html';
        redirectCountDownTime = 3;
    } else {
        val.html(redirectCountDownTime);
        redirectCountDownTime--;
        setTimeout(function () {
            redirectCountDown(val);
        }, 1000);
    }
}