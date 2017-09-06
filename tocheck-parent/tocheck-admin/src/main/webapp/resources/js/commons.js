$.fn.extend({
    ajaxFrom: function (opt) {
        var index;
        var default_opts = ({
            url: $(this).attr('action'),
            data: $(this).serialize(),
            async: true,
            cache: true,
            dataType: 'json',
            method: $(this).attr("method") == null
                ? "POST" : $(this).attr("method"),
            beforeSend: function () {
                index = loading();
            },
            complete: function () {
                layer.close(index);
            },
            success: function () {
            },
            error: function (e) {
            }
        })

        var settings = $.extend(default_opts, opt);
        $.ajax({
            url: settings.url,
            data: settings.data,
            async: settings.async,
            cache: settings.cache,
            dataType: settings.dataType,
            method: settings.method,
            beforeSend: function () {
                settings.beforeSend();
            },
            complete: function () {
                settings.complete();
            },
            success: function (data) {
                settings.success(data);
            },
            error: function (e) {
                settings.error(e);
            }
        })
    },
    ajaxPost: function (opt) {
        $.post($(this).attr('url'), function (data) {
            opt.success(data);
        }, 'json')
    },
    ajaxGet: function (opt) {
        $.get($(this).attr('url'), function (data) {
            opt.success(data);
        }, 'json')
    }
})
$(function () {
    $("*[action='ajax-confirm-delete']").click(function () {
        var tip = $(this).attr("tip");
        tip = (tip == null || tip == undefined) ? "确定要删除吗？" : tip;
        $this = $(this);
        layer.confirm(tip, {
            title:['系统提示' , true],
            btn: ['确定', '取消'] //按钮
        }, function () {
            var index = loading();
            $this.ajaxPost({
                success: function (json) {
                    layer.close(index);
                    if (json.message != '') {
                        if(json.state){
                            layer.msg(json.message,{icon:1});
                        }else{
                            layer.msg(json.message,{icon:2});
                        }
                    }
                    if (json.needrefresh) {
                        window.location.reload();
                    }
                }
            })
        }, function () {
            layer.close();
        });
    })
    $("*[action='ajax-delete']").click(function () {
        loading();
        $(this).ajaxPost({
            success: function (json) {
                if (json.message != '') {
                    if(json.state){
                        layer.msg(json.message,{icon:1});
                    }else{
                        layer.msg(json.message,{icon:2});
                    }
                }
                if (json.needrefresh) {
                    window.location.reload();
                }
                layer.closeAll();
            }
        })
    })
    // 控制左侧菜单焦点
    if ($("meta[name='menu']").length > 0) {
        var menu = $("meta[name='menu']").attr('content');
        $("#nav > li[menu='" + menu + "']").find('a').addClass("open");
    }
    $(".system").click(function () {
        var systemId = "";
        var systemName = "";
        $(".system").each(function () {
            if ($(this).prop('checked')) {
                systemId = systemId == "" ? systemId + $(this).attr('id') : systemId + "," + $(this).attr('id');
                systemName = systemName == "" ? systemName + $(this).val() : systemName + "," + $(this).val();
            }
        })
        $("input[name='systemIds']").val(systemId);
        $("input[name='systemNames']").val(systemName);
    })
    $(".major").click(function () {
        var majorId = "";
        var majorName = "";
        $(".major").each(function () {
            if ($(this).prop('checked')) {
                majorId = majorId == "" ? majorId + $(this).attr('id') : majorId + "," + $(this).attr('id');
                majorName = majorName == "" ? majorName + $(this).val() : majorName + "," + $(this).val();
            }
        })
        $("input[name='majorIds']").val(majorId);
        $("input[name='majorNames']").val(majorName);
    })
})

var paperFileExtReg = /.rar|.zip/;
//获取当前网址，如 http://localhost:8080/demo/xx/xx.jsp
var curWwwPath = window.document.location.href;
//获取主机地址之后的目录，如： tb/xx.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
//获取主机地址，如： http://localhost:8080
var localhostPaht = curWwwPath.substring(0, pos);
//获取带"/"的项目名，如：/tb
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
var PageCommon = {contextPath: localhostPaht + projectName};

function StringBuffer() {
    this.__strings__ = new Array();
}
StringBuffer.prototype.append = function (str) {
    this.__strings__.push(str);
    return this;    //方便链式操作
};
StringBuffer.prototype.toString = function () {
    return this.__strings__.join("");
};

function loading() {
    var index = layer.load(1, {
        shade: [0.1,'#a2a2a2'], //0.1透明度的白色背景
        time:0,
        offset : ['50%' , '50%']
    });
    return index;
}

function errorMsg(message) {
    $(".login-li-ts-p").find("span").text(message);
    $(".login-li-ts-p").show();
    setTimeout(function () {
        $(".login-li-ts-p").hide();
    }, 3000);
}

function myalert(message,icon) {
    layer.alert(message,{title:"系统提示",icon:icon});
}

