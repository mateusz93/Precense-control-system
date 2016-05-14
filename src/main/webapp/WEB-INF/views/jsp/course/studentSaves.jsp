<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<html>
    <head>
        <title>Zapisy</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
            <form action="savesServlet" method="post">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Nazwa</th>
                            <th>Wydział</th>
                            <th>Typ zajęć</th>
                            <th>Ilość zajęć</th>
                            <th>Prowadzący</th>
                            <th>Opis</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="subjects" items="${subjectList}">
                            <tr>
                                <td><c:out value="${subjects.subjectName}"  /></td>
                                <td><c:out value="${subjects.departmentName}" /></td>
                                <td><c:out value="${subjects.type}" /></td>
                                <td><c:out value="${subjects.coursesQuantity}"  /></td>
                                <td><c:out value="${subjects.teacherName}"  /></td>
                                <td><c:out value="${subjects.description}"  /></td>
                                <td><button name="ID" value="${subjects.id}" type="submit" class="btn btn-success">Zapisz się</button></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</html>
