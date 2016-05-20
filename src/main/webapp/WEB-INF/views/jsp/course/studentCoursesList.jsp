<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kursy</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>

        <div class="container">
            <br><br><br>
            <jsp:include page="../alert/allAlerts.jsp"/>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Nazwa</th>
                        <th>Wydział</th>
                        <th>Typ zajęć</th>
                        <th>Ilość zajęć</th>
                        <th>Prowadzący</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${coursesList}">
                        <tr>
                            <td><c:out value="${course.subjectName}"  /></td>
                            <td><c:out value="${course.departmentName}" /></td>
                            <td><c:out value="${course.type}" /></td>
                            <td><c:out value="${course.coursesQuantity}"  /></td>
                            <td><c:out value="${course.teacherName}"  /></td>
                            <form action="/courses/info/${course.courseID}" method="post">
                                <td><button type="submit" class="btn btn-info">Pokaż terminy</button></td>
                            </form>
                            <form action="/courses/unSubscribe/${course.courseID}" method="post">
                                <td><button type="submit" class="btn btn-danger">Wypisz się</button></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </body>
</html>
