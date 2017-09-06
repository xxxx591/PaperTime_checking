<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="menu" content="checkTask">
    <title>检测任务</title>
</head>
<body>
<!-- Main bar -->
<div class="mainbar">
    <div class="page-head">
        <h2 class="pull-left"><i class="icon-table"></i>检测任务</h2>
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
                                    <td>检测状态</td>
                                    <td>类型</td>
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
                                            <c:choose>
                                                <c:when test="${r.status == 0}">
                                                    待检测
                                                </c:when>
                                                <c:when test="${r.status == 2}">
                                                    正在检测
                                                </c:when>
                                                <c:when test="${r.status == 3}">
                                                    检测完成
                                                </c:when>
                                                <c:when test="${r.status == 4}">
                                                    已下载报告
                                                </c:when>
                                                <c:when test="${r.status == 5}">
                                                    申请结算
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${r.isRush==0}">
                                                普通
                                            </c:if>
                                            <c:if test="${r.isRush==1}">
                                                抢单
                                            </c:if>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${r.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <c:if test="${r.status <4}">
                                                <a  class="btn btn-primary" href="downloadPaper/${r.id}.html">下载</a>
                                                <label style="display: inline-block; border: 1px solid #ddd; width: 100px; height: 31px; padding: 0 5px; box-sizing: border-box; vertical-align: middle; margin: 0 3px; border-radius: 4px; overflow: hidden;">
                                                    <input type="text" disabled id="fileName${r.id}" style="border: none; background: #fafafa ; height: 31px; display: block; width: 100%; line-height: 30px; color: #666;">
                                                </label>
                                                <a  class="btn btn-primary" style="position: relative; width: 74px;">
                                                    选择
                                                    <input type="file" id="file${r.id}" name="file${r.id}" onchange="uploadPaperFile(this,${r.id})" value="选择文件" style="display: block; height: 31px; width: 74px; position: absolute; left: 0; top: 0; opacity: 0;">
                                                </a>
                                                <a  class="btn btn-primary" href="javascript:void(0)" onclick="upload(${r.id},${r.systemId})">上传</a>
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
<script src="${__base}/resources/js/ajaxfileupload.js"></script>
<script src="${__base}/resources/js/agent/checkTask.js"></script>
</body>
</html>
