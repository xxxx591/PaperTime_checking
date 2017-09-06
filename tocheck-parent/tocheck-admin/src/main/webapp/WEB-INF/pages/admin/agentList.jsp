<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="menu" content="agentList">
    <title>代理商审核</title>
</head>
<body>
<!-- Main bar -->
<div class="mainbar">
    <div class="page-head">
        <h2 class="pull-left"><i class="icon-table"></i>代理商审核</h2>
        <div class="clearfix"></div>
    </div>
    <!-- Page heading ends -->
    <!-- Matter -->
    <div class="matter">
        <div class="container">
            <!-- Table -->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget" style="margin-top: 0px">
                        <div class="widget-content">
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>账号</th>
                                    <th>备注</th>
                                    <th>支付宝账号</th>
                                    <th>推广链接</th>
                                    <th>可检测系统</th>
                                    <th>可检测专业</th>
                                    <th>状态</th>
                                    <th>注册时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.getList()}" var="r">
                                    <tr>
                                        <td>${r.id}</td>
                                        <td>${r.userName}</td>
                                        <td>${r.note}</td>
                                        <td>${r.alipayAccount}</td>
                                        <td>${r.url}</td>
                                        <td>${r.systemNames}</td>
                                        <td>${r.majorNames}</td>
                                        <td>
                                              <c:choose>
                                                  <c:when test="${r.status == 0}">未通过</c:when>
                                                  <c:when test="${r.status == 1}">待审核</c:when>
                                                  <c:when test="${r.status == 2}">已通过</c:when>
                                              </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${r.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td>
                                            <c:if test="${r.status == 1}">
                                                <div class="btn-group1">
                                                    <button class="btn btn-xs btn-success" action="ajax-confirm-delete" tip="确定通过？" url="pass.ajax?id=${r.id}">通过</button>
                                                    <button class="btn btn-xs btn-danger" action="ajax-confirm-delete"  tip="确定拒绝？" url="unPass.ajax?id=${r.id}">拒绝</button>
                                                </div>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <%
                                Enumeration eNames = request.getParameterNames();
                                String pageUrl="?";
                                while(eNames.hasMoreElements()){
                                    String name= (String)eNames.nextElement();
                                    if(!name.equals("p")){
                                        String value = request.getParameter(name);
                                        pageUrl=pageUrl+name+"="+value+"&";
                                    }
                                }
                            %>
                            <div class="widget-foot">
                                <ul class="pagination pull-right">
                                    <c:choose>
                                        <c:when test="${page.hasPreviousPage}">
                                            <li ><a href="<%=pageUrl%>p=${page.prePage}">Prev</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="javascript:void(0)">Prev</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:forEach items="${page.navigatepageNums}" var="r">
                                        <c:choose>
                                            <c:when test="${page.pageNum == r}">
                                                <li><a href="javascript:void(0)" style="background-color: #bfbfbf">${r}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href="<%=pageUrl%>p=${r}" >${r}</a></li>
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