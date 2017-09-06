<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="menu" content="rushOrder">
    <title>抢单池</title>
</head>
<body>
<!-- Main bar -->
<div class="mainbar">
    <div class="page-head">
        <h2 class="pull-left"><i class="icon-table"></i>抢单池</h2>
        <div class="clearfix"></div>
    </div>
    <div class="matter">
        <div class="container">
            <!-- Table -->
            <div class="row">

                <div class="col-md-12">
                    <div class="widget" style="margin-top: 0px;">
                        <form method="post">
                            <%--<div class="widget-head">--%>
                                <%--用户名:<input type="text" class="form-control" name="userName" value="" style="width:250px;display: inline-block;margin-right: 20px;">--%>
                                <%--<button type="submit" class="btn btn-primary">查询</button>--%>
                                <%--<div class="clearfix"></div>--%>
                            <%--</div>--%>
                        <%--</form>--%>
                        <div class="widget-content">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr align="center" style="color: #3e3e4a;font-weight:bold;">
                                    <td>id</td>
                                    <td>论文标题</td>
                                    <td>检测系统</td>
                                    <td>论文作者</td>
                                    <td>付款金额</td>
                                    <td>提交时间</td>
                                    <td>操作</td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.getList()}" var="r">
                                    <tr align="center">
                                        <td>${r.id}</td>
                                        <td>${r.title}</td>
                                        <td>${r.systemName}</td>
                                        <td>${r.author}</td>
                                        <td>${r.money}</td>
                                        <td>
                                            <fmt:formatDate value="${r.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <a  class="btn btn-primary" href="javascript:void(0)" action='ajax-delete' url="rushOrder.ajax?id=${r.id}">抢单</a>
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
<script src="${__base}/resources/js/ajaxfileupload.js"></script>
<script src="${__base}/resources/js/agent/checkTask.js"></script>
</body>
</html>
