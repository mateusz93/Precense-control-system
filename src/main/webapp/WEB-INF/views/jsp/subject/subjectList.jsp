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
            <form action="/subjects/new" method="get">
                <td><button type="submit" class="btn btn-success">Dodaj nowy przedmiot</button></td>
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
                        <th>Nazwa</th>
                        <th>Rok studiów</th>
                        <th>Kierunek</th>
                        <th>Minimalna liczba obecności</th>
                        <th>Liczba zajęć</th>
                        <th>Opis</th>
                    </tr>
                </thead>
                <tbody id="paginationTable" class="searchable">
                    <c:forEach var="subject" items="${subjectList}">
                        <tr>
                            <td><c:out value="${subject.name}"  /></td>
                            <td><c:out value="${subject.yearOfStudy}"  /></td>
                            <td><c:out value="${subject.field.name}"  /></td>
                            <td><c:out value="${subject.minQuantity}"  /></td>
                            <td><c:out value="${subject.quantity}"  /></td>
                            <td><c:out value="${subject.description}"  /></td>

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
