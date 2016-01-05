<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dodanie terminu zajęć</title>
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
            <h3>Dodaj nowy termin zajęć</h3>
            <form action="addCourseDateServlet" method="post">
                <div class="form-group">
                    <label>Data</label>
                    <input type="text" placeholder="Format: YYYY-MM-DD" name="date" class="form-control">
                </div>
                <div class="form-group">
                    <label>Czas rozpoczęcia</label>
                    <input type="text" placeholder="Format: GG:MM:SS" name="startTime" class="form-control">
                </div>
                <div class="form-group">
                    <label>Czas zakończenia</label>
                    <input type="text" placeholder="Format: GG:MM:SS" name="finishTime" class="form-control">
                </div>
                <td><button type="submit"  class="btn btn-success">Zatwierdź</button></td>
            </form>
        </div>
        <% }%>
    </body>
</html>
