/**
 * Created by Administrator on 2017/6/15.
 */
$(function () {
    $("#register").click(function () {
        $.ajax({
            url: "/register.html",
            method: "post",
            data: $(this).closest('form').serialize(),
            success: function (json) {
                if (json.state) {
                    location.href = "/login.html";
                } else {
                    layer.msg(json.message);
                }
            }
        })
    })

    $("#agent-login").click(function () {
        $.ajax({
            url: "/agentLogin.html",
            method: "post",
            data: $(this).closest('form').serialize(),
            success: function (json) {
                if (json.state) {
                    location.href = "/agent/info.html";
                } else {
                    layer.msg(json.message);
                }
            }
        })
    })
    $("#admin-login").click(function () {
        $.ajax({
            url: "/adminLogin.html",
            method: "post",
            data: $(this).closest('form').serialize(),
            success: function (json) {
                if (json.state) {
                    location.href = "/admin/agentList.html";
                } else {
                    layer.msg(json.message);
                }
            }
        })
    })
})
