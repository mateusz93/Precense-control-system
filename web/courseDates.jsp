<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Terminy kursów</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <style type="text/css">
        <%@ include file="WEB-INF/css/menu.css" %>
    </style>

    <body>
        <jsp:include page="WEB-INF/jsp/menu.jsp"/>
        <%
            if ((session.getAttribute("username") == null) || (session.getAttribute("username") == "")) {
                response.sendRedirect("login.jsp");
            }
            
            if ("Teacher".equals(session.getAttribute("type"))) {
        %>
        <div class="container">
            <br><br><br>
            <form action="addCourseDateServlet" method="get">
                <td><button type="submit" value="${courseID}" name="courseID" class="btn btn-success">Dodaj termin</button></td>
            </form>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Dzień</th>
                        <th>Czas rozpoczęcia</th>
                        <th>Czas zakończenia</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dates" items="${datesList}">
                        <tr>
                            <td><c:out value="${dates.date}"  /></td>
                            <td><c:out value="${dates.startTime}" /></td>
                            <td><c:out value="${dates.finishTime}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <% }%>    
    </body>
</html>