<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<html>
    <head>
        <title><spring:message code='view.save.title'/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/bootstrap.3.3.7.min.css"/>
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="/resources/js/jquery.1.11.3.min.js"></script>
        <script src="/resources/js/bootstrap.3.3.7.min.js"></script>
        <script src="/resources/js/pagination.js"></script>
        <script src="/resources/js/filter.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <div class="container">
            <br><br>
            <jsp:include page="../alert/allAlerts.jsp"/>
            <hr>
            <div class="input-group input-group-lg add-on">
                <div class="input-group"> <span class="input-group-addon">Filter</span>
                    <input id="filter" type="text" class="form-control" >
                </div>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th><spring:message code='view.name'/></th>
                        <th><spring:message code='view.subject.quantity'/></th>
                        <th><spring:message code='view.teacher'/></th>
                        <th><spring:message code='view.subject.description'/></th>
                    </tr>
                </thead>
                <tbody id="paginationTable" class="searchable">
                    <c:forEach var="course" items="${subjectList}">
                        <tr>
                            <td><c:out value="${course.subjectName}"  /></td>
                            <td><c:out value="${course.coursesQuantity}"  /></td>
                            <td><c:out value="${course.teacherName}"  /></td>
                            <td><c:out value="${course.description}"  /></td>
                            <form action="/saves/${course.id}" method="post">
                                <td><button type="submit" class="btn btn-success"><spring:message code='view.save.save'/></button></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 text-center">
            <ul class="pagination" id="pager"></ul>
        </div>
    </body>
</html>
