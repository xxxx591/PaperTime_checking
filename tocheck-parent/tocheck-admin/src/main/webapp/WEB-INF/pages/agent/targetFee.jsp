<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="menu" content="orderSettlement">
    <title>订单结算(检测费)</title>
</head>
<body>
<!-- Main bar -->
<div class="mainbar">
    <div class="page-head">
        <h2 class="pull-left"><i class="icon-table"></i>订单结算(检测费)</h2>
        <div class="clearfix"></div>
    </div>
    <div class="matter">
        <div class="container">
            <!-- Table -->
            <div class="row">

                <div class="col-md-12">
                    <div class="widget" style="margin-top: 0px;">
                        <div class="widget-head">
                            <a class="btn btn-danger" href="/agent/targetFee.html">检测费</a>
                            <a class="btn btn-primary" href="/agent/flowFee.html">流量费</a>
                            <a class="btn btn-success" href="javascript:void(0);" action="ajax-confirm-delete" tip="确定要全部提现吗?" url="targetWithdraw.ajax">申请提现</a>
                            <div class="clearfix"></div>
                        </div>
                        <div class="widget-content">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr align="center" style="color: #3e3e4a;font-weight:bold;">
                                    <td>论文标题</td>
                                    <td>检测系统</td>
                                    <td>类型</td>
                                    <td>结算金额(检测费)</td>
                                    <td>状态</td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.getList()}" var="r">
                                    <tr align="center">
                                        <td>${r.title}</td>
                                        <td>${r.systemName}</td>
                                        <td>
                                            <c:if test="${r.isRush==0}">
                                                普通
                                            </c:if>
                                            <c:if test="${r.isRush==1}">
                                                抢单
                                            </c:if>
                                        </td>
                                        <td>${r.targetFee}</td>
                                        <td>
                                            <c:if test="${r.settlementStatus == 1 || r.settlementStatus == 5}">
                                                待结算
                                            </c:if>
                                            <c:if test="${r.settlementStatus == 3 || r.settlementStatus ==7}">
                                                已结算
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <%
                                Enumeration eNames = request.getParameterNames();
                                String pageUrl = "?";
                                while (eNames.hasMoreElements()) {
                                    String name = (String) eNames.nextElement();
                                    if (!name.equals("p")) {
                                        String value = request.getParameter(name);
                                        pageUrl = pageUrl + name + "=" + value + "&";
                                    }
                                }
                            %>
                            <div class="widget-foot">
                                <ul class="pagination pull-right">
                                    <c:choose>
                                        <c:when test="${page.hasPreviousPage}">
                                            <li><a href="<%=pageUrl%>p=${page.prePage}">Prev</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="javascript:void(0)">Prev</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:forEach items="${page.navigatepageNums}" var="r">
                                        <c:choose>
                                            <c:when test="${page.pageNum == r}">
                                                <li><a href="javascript:void(0)"
                                                       style="background-color: #bfbfbf">${r}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href="<%=pageUrl%>p=${r}">${r}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:choose>
                                        <c:when test="${page.hasNextPage}">
                                            <li><a href="<%=pageUrl%>p=${page.nextPage}">Next</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="javascript:void(0)">Next</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
