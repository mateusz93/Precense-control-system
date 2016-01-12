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
        %>
        <div class="container">
            <br><br>
            <jsp:include page="WEB-INF/alerts/allAlerts.jsp"/>
            <h4><% out.print(request.getParameter("subjectName").trim()); %></h4>
            
            <% if ("Teacher".equals(session.getAttribute("type"))) { %>
            <form action="addCourseDateServlet" method="get">
                <td><button type="submit" value="${courseID}" name="courseID" class="btn btn-success">Dodaj termin</button></td>
                <td><button type="submit" value="${courseID}" name="courseID2" class="btn btn-success">Dodaj kilka terminów</button></td>
            </form>
            <% } %>  
            
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
                            <td class="col-md-2"><c:out value="${dates.date}"  /></td>
                            <td class="col-md-2"><c:out value="${dates.startTime}" /></td>
                            <td class="col-md-2"><c:out value="${dates.finishTime}" /></td>
                            <% if ("Teacher".equals(session.getAttribute("type"))) { %>
                            <form action="editCourseDateServlet" method="post">
                                <td class="col-md-1"><button name="editID" value="${dates.id}" type="submit" class="btn btn-warning">Edytuj</button></td>
                            </form>
                            <form action="deleteCourseDateServlet" method="post">
                                <input type="hidden" name="subjectName" value="${subjectName}"/>
                                <input type="hidden" name="info" value="${info}"/>
                                <td class="col-md-1"><button name="deleteID" value="${dates.id}" type="submit" class="btn btn-danger">Usuń</button></td>
                            </form>
                            <% } %>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>  
    </body>
</html>