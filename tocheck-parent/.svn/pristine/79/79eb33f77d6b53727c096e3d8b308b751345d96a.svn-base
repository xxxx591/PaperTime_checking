<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="menu" content="activityList">
    <title>活动审核</title>
</head>
<body>
<!-- Main bar -->
<div class="mainbar">
    <div class="page-head">
        <h2 class="pull-left"><i class="icon-table"></i>活动审核</h2>
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
                            <div class="widget-head">
                                <a href="javascript:audit(1);" class="btn btn-info">通过</a>
                                <a href="javascript:audit(-1);" class="btn btn-danger">不通过</a>
                                <div class="clearfix"></div>
                            </div>
                            <table class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" id="selected-all"></th>
                                    <th>id</th>
                                    <th>用户名</th>
                                    <th>活动ID</th>
                                    <th>提交时间</th>
                                    <th>提交结果</th>
                                    <th>状态</th>
                                    <th>审核时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.getList()}" var="r">
                                    <tr>
                                        <td>
                                            <c:if test="${r.status==0}">
                                                <input type="checkbox" value="${r.id}">
                                            </c:if>
                                        </td>
                                        <td>${r.id}</td>
                                        <td>${r.userName}</td>
                                        <td>${r.actId}</td>
                                        <td><fmt:formatDate value="${r.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td><a href="${r.actResult}" target="_blank">${r.actResult}</a></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${r.status == -1}">未通过</c:when>
                                                <c:when test="${r.status == 0}">待审核</c:when>
                                                <c:when test="${r.status == 1}">已通过</c:when>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${r.approveTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
<script>
    $("#selected-all").click(function () {
        $("tbody").find("input[type='checkbox']").prop("checked", $(this).is(':checked'));
    })
    function audit(status) {
        var valArr = new Array;
        $("tbody").find("input[type='checkbox']:checked").each(function (i) {
            valArr[i] = $(this).val();
        })
        var ids = valArr.join(',');//转换为逗号隔开的字符串
        if (valArr == "") {
            alert("请至少选择一条!");
            return false;
        }
        ;
        $.post('audit.html', {ids: ids, status: status}, function (json) {
            if (json.state) {
                location.reload();
            }else{
                alert(json.message);
            }
        }, 'json')
    }
</script>
</body>
</html>