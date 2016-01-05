<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Zapisy</title>
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
                response.sendRedirect("/teacherSavesServlet");
            }
        %>
        <div class="container">
            <br><br><br>
            <%
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %> 
            <div class="alert alert-info alert-dismissable">
                <a class="panel-close close" data-dismiss="alert">×</a> 
                <i class="fa fa-coffee"></i>
                <%
                    out.println(message);
                %> 
            </div>
            <% }%> 
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
