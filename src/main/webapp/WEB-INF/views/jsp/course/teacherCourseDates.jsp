<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<html>
    <head>
        <title>Terminy kursów</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <div class="container">
            <jsp:include page="../alert/allAlerts.jsp"/>
            <form action="/courses/addOne/${teacherCourseId}" method="post">
                <td><button type="submit" value="${teacherCourseId}" name="teacherCourseId" class="btn btn-success">Dodaj termin</button></td>
            </form>
        <!--
            <form action="/courses/addSeveral/${courseID}" method="post">
                <td><button type="submit" value="${courseID}" name="courseID" class="btn btn-success">Dodaj kilka terminów</button></td>
            </form>
        -->
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Dzień</th>
                        <th>Czas rozpoczęcia</th>
                        <th>Czas zakończenia</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="date" items="${datesList}">
                        <tr>
                            <td class="col-md-2"><c:out value="${date.date}"  /></td>
                            <td class="col-md-2"><c:out value="${date.startTime}" /></td>
                            <td class="col-md-2"><c:out value="${date.finishTime}" /></td>
                        <!--
                            <form action="/courses/edit/${date.id}" method="post">
                                <td class="col-md-1"><button name="editID" value="${date.id}" type="submit" class="btn btn-warning">Edytuj</button></td>
                            </form>
                        -->
                            <form action="/courses/delete/${date.id}" method="post">
                                <td class="col-md-1"><button name="deleteID" value="${date.id}" type="submit" class="btn btn-danger">Usuń</button></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>  
    </body>
</html>