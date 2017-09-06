<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="menu" content="withdrawRecord">
    <title>提现记录</title>
</head>
<body>
<!-- Main bar -->
<div class="mainbar">
    <div class="page-head">
        <h2 class="pull-left"><i class="icon-table"></i>提现记录</h2>
        <div class="clearfix"></div>
    </div>
    <div class="matter">
        <div class="container">
            <!-- Table -->
            <div class="row">

                <div class="col-md-12">
                    <div class="widget" style="margin-top: 0px;">
                        <div class="widget-content">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr align="center" style="color: #3e3e4a;font-weight:bold;">
                                    <td>ID</td>
                                    <td>申请金额</td>
                                    <td>申请时间</td>
                                    <td>审核时间</td>
                                    <td>状态</td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.getList()}" var="r">
                                    <tr align="center">
                                        <td>${r.id}</td>
                                        <td>${r.amount}</td>
                                        <td><fmt:formatDate value="${r.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td><fmt:formatDate value="${r.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td>
                                             <c:choose>
                                                 <c:when test="${r.status ==0}">审核中</c:when>
                                                 <c:when test="${r.status ==1}">已打款</c:when>
                                                 <c:when test="${r.status ==2}">已拒绝,请联系客服</c:when>
                                             </c:choose>
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
