<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dodawanie kursu</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <style type="text/css">
        <%@ include file="WEB-INF/css/menu.css" %>
    </style>

    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>                        
                    </button>
                    <% if ((session.getAttribute("username") == null)) { %>
                    <a class="navbar-brand" href="login.jsp" action="login.jsp">Zaloguj</a>
                    <%} else { %> 
                    <a class="navbar-brand" href="logout.jsp" action="logout.jsp">Wyloguj</a>
                    <% } %>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav navbar-right">
                        <% if ("Teacher".equals(session.getAttribute("type"))) { %>
                        <li><a href="profileServlet">Profil</a></li>
                        <li><a href="teacherPrecensesServlet">Obecności</a></li>
                        <li><a href="teacherCoursesServlet">Moje przedmioty</a></li>
                        <li><a href="teacherSavesServlet">Zapisy na zajęcia</a></li>
                        <li><a href="teacherStatsServlet">Statystyki</a></li>
                            <%} else { %> 
                        <li><a href="profileServlet">Profil</a></li>
                        <li><a href="precensesServlet">Obecności</a></li>
                        <li><a href="coursesServlet">Moje przedmioty</a></li>
                        <li><a href="savesServlet">Zapisy na zajęcia</a></li>
                        <li><a href="statsServlet">Statystyki</a></li>
                            <% }%>
                    </ul>
                </div>
            </div>
        </nav>
        <%
            if ((session.getAttribute("username") == null) || (session.getAttribute("username") == "")) {
                response.sendRedirect("login.jsp");
            }
            
            if ("Teacher".equals(session.getAttribute("type"))) {
        %>
        <div class="container">
            <br><br><br>
            <h3>Dodaj nowy kurs</h3>
            <form action="addSubjectServlet" method="post">
                <div class="form-group">
                    <label>Nazwa przedmiotu</label>
                    <input type="text" name="subjectName" class="form-control">
                </div>
                <div class="input-append btn-group">
                    <label>Wydział</label>
                    <input type="text" name="departmentName" class="form-control">
                    <a class="btn btn-primary dropdown-toggle right" data-toggle="dropdown" href="#">Lista wydziałów
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="#"><i class="icon-pencil"></i> 1</a></li>
                        <li><a href="#"><i class="icon-trash"></i> 2</a></li>
                        <li><a href="#"><i class="icon-ban-circle"></i> 3</a></li>
                    </ul>
                </div>
                <div class="form-group">
                    <label>Typ</label>
                    <input type="text" name="type" class="form-control">
                </div>
                <div class="form-group">
                    <label>Ilość zajęć</label>
                    <input type="text" name="quantity" class="form-control">
                </div>
                <div class="form-group">
                    <label>Minimalna ilość obecności</label>
                    <input type="text" name="min" class="form-control">
                </div>
                <div class="form-group">
                    <label>Opis</label>
                    <input type="text" name="description" class="form-control">
                </div>
                <td><button type="submit" class="btn btn-success">Zatwierdź</button></td>
            </form>
        </div>
        <% }%>
    </body>
</html>
