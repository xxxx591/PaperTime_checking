<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="menu" content="index">
    <title>个人信息-tocheck后台管理系统</title>
</head>

<body>
<div class="mainbar">
    <div class="page-head">
        <h2 class="pull-left">
            <span class="page-meta">个人信息</span>
        </h2>
        <div class="clearfix"></div>
    </div>
    <div class="matter">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="widget wred" style="margin-top:0px;">
                        <div class="widget-content">
                            <div class="padd">
                                <div class="form profile">
                                    <form class="form-horizontal form-xg" method="post" action="modifyInfo.ajax">
                                        <div class="form-group">
                                            <label class="control-label col-lg-3">用户名:</label>
                                            <div class="col-lg-6">
                                                <span class="xg-s"
                                                      style="display: block; float: left; margin-right: 0px; line-height: 34px; font-size: 14px;">${agentUser.userName}</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-lg-3">支付宝账号:</label>
                                            <div class="col-lg-2">
                                                <input type="text" class="form-control" name="alipayAccount"
                                                       value="${agentUser.alipayAccount}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-lg-3">推广URL:</label>
                                            <div class="col-lg-6">
                                                <span class="xg-s"
                                                      style="display: block; float: left; margin-right: 0px; line-height: 34px; font-size: 14px;">${agentUser.url}</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-lg-3">可检测系统:</label>
                                            <div class="col-lg-6">
                                                <c:set var="systemIds" value="${fn:split(agentUser.systemIds,',')}"/>
                                                <input type="checkbox" value="知网硕博VIP5.1" id="1" class="system"
                                                <c:forEach items="${systemIds}" var="s">
                                                       <c:if test="${s == 1}">checked</c:if>
                                                </c:forEach>>
                                                知网硕博VIP5.1
                                                <input type="checkbox" value="知网AMLC" id="2" class="system"
                                                <c:forEach items="${systemIds}" var="s">
                                                       <c:if test="${s == 2}">checked</c:if>
                                                </c:forEach>>
                                                知网AMLC
                                                <input type="checkbox" value="知网PMLC" id="3" class="system"
                                                <c:forEach items="${systemIds}" var="s">
                                                       <c:if test="${s == 3}">checked</c:if>
                                                </c:forEach>>
                                                知网PMLC
                                                <input type="hidden" name="systemIds" value="${agentUser.systemIds}">
                                                <input type="hidden" name="systemNames" value="${agentUser.systemNames}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-lg-3">可检测专业:</label>
                                            <div class="col-lg-6">
                                                <c:set var="majorIds" value="${fn:split(agentUser.majorIds,',')}"/>
                                                <c:forEach items="${list}" var="r">
                                                    <input type="checkbox" class="major" id="${r.id}" value="${r.name}"
                                                    <c:forEach items="${majorIds}" var="s">
                                                           <c:if test="${s == r.id}">checked</c:if>
                                                    </c:forEach>>
                                                    ${r.name}
                                                </c:forEach>
                                                <input type="hidden" name="majorIds" value="${agentUser.majorIds}">
                                                <input type="hidden" name="majorNames" value="${agentUser.majorNames}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-lg-3">注册时间:</label>
                                            <div class="col-lg-6">
                                                <span class="xg-s"
                                                      style="display: block; float: left; margin-right: 0px; line-height: 34px; font-size: 14px;">
                                                    <fmt:formatDate value="${agentUser.createTime}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-lg-3"></label>
                                            <div class="col-lg-6 ">
                                                <button type="button" class="btn btn-success" id="save">保存</button>
                                                <button type="reset" class="btn btn-default">重置</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="clearfix"></div>
<script>
    $(function () {
        $("#save").click(function () {
            loading();
            $(this).closest('form').ajaxFrom({
                success: function (json) {
                    if (json.state) {
                        location.reload();
                    } else {
                        layer.msg(json.message);
                    }
                    layer.closeAll();
                }, error: function () {
                    layer.closeAll();
                }
            })
        })
    })
</script>
</body>