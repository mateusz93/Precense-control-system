<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Oceny</title>
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
                    <input id="filter" type="text" class="form-control">
                </div>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Ocena</th>
                        <th>Poprzednia ocena</th>
                        <th>Ocena koncowa?</th>
                        <th>Data wystawienia</th>
                    </tr>
                </thead>
                <tbody id="paginationTable" class="searchable">
                    <c:forEach var="gradeDetails" items="${gradesDetailsList}">
                        <tr>
                            <td><c:out value="${gradeDetails.value}"  /></td>
                            <td><c:out value="${gradeDetails.previousGrade.value}"  /></td>
                            <c:if test="${gradeDetails.finalGrade eq true}">
                                <td><c:out value="Tak"  /></td>
                            </c:if>
                            <c:if test="${gradeDetails.finalGrade eq false}">
                                <td><c:out value="" /></td>
                            </c:if>
                            <td><c:out value="${gradeDetails.time}"  /></td>
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