<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>注册</title>
    <link href="/resources/style/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/style/font-awesome.css">
    <link href="/resources/style/style.css" rel="stylesheet">
    <script src="/resources/js/html5shim.js"></script>
    <script src="/resources/js/jquery.js"></script>
</head>
<body>

<div class="admin-form">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <!-- Widget starts -->
                <div class="widget wred" style="margin-top: 150px">
                    <div class="widget-head">
                        <i class="icon-lock"></i> 注册
                    </div>
                    <div class="widget-content">
                        <div class="padd">
                            <form class="form-horizontal" action="/register.html" method="post" id="ajax-submit-form">
                                <!-- Username -->
                                <div class="form-group">
                                    <label class="control-label col-lg-3">用户帐号</label>
                                    <div class="col-lg-9">
                                        <input type="text" class="form-control"  name="userName">
                                    </div>
                                </div>
                                <!-- Password -->
                                <div class="form-group">
                                    <label class="control-label col-lg-3">用户密码</label>
                                    <div class="col-lg-9">
                                        <input type="password" class="form-control"  name="password">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-3">检测系统</label>
                                    <div class="col-lg-9">
                                        <input type="checkbox" value="知网硕博VIP5.1" id="1" class="system">知网硕博VIP5.1
                                        <input type="checkbox" value="知网AMLC" id="2" class="system">知网AMLC
                                        <input type="checkbox" value="知网PMLC" id="3" class="system">知网PMLC
                                        <input type="hidden" name="systemIds">
                                        <input type="hidden" name="systemNames">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-3">检测专业</label>
                                    <div class="col-lg-9">
                                        <c:forEach items="${list}" var="r">
                                           <input type="checkbox"  class="major" id="${r.id}" value="${r.name}">${r.name}
                                        </c:forEach>
                                        <input type="hidden" name="majorIds">
                                        <input type="hidden" name="majorNames">
                                    </div>
                                </div>
                                <p class="login-li-ts-p" style="display:none;padding-left:90px;">
                                    <i class="login-li-ts-i">×</i>
                                    <span class="login-li-s"></span>
                                </p>
                                <!-- Accept box and button s-->
                                <div class="form-group">
                                    <div class="col-lg-9 col-lg-offset-3">
                                        <button type="button" class="btn btn-danger" id="register">注册</button>
                                        <button type="reset" class="btn btn-success">重置</button>
                                    </div>
                                </div>
                                <br />
                            </form>
                        </div>
                    </div>
                    <div class="widget-foot">
                        已有账号? <a href="/login.html">Login</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- JS -->
<script src="/resources/js/bootstrap.js"></script>
<script src="/resources/js/commons.js"></script>
<script src="/resources/js/layer/layer.js"></script>
<script src="/resources/js/register.js"></script>
</body>
</html>
