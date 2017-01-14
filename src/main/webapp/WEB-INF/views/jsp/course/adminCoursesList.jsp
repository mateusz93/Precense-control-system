<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>

<html>
    <head>
        <title>Kursy</title>
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
            <form action="/courses/new" method="get">
                <td><button type="submit" class="btn btn-success">Dodaj nowy kurs</button></td>
            </form>
            <br>
            <div class="input-group input-group-lg add-on">
                <div class="input-group"> <span class="input-group-addon">Filter</span>
                    <input id="filter" type="text" class="form-control" >
                </div>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Nazwa przedmiotu</th>
                        <th>Nauczyciel</th>
                        <th>Grupa</th>
                        <th>Opis</th>
                    </tr>
                </thead>
                <tbody id="paginationTable" class="searchable">
                    <c:forEach var="course" items="${coursesList}">
                        <tr>
                            <td><c:out value="${course.subject.name}"  /></td>
                            <td><c:out value="${course.teacher.firstName} ${course.teacher.lastName}"  /></td>
                            <td><c:out value="${course.studentGroup}"  /></td>
                            <td><c:out value="${course.description}"  /></td>
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
