<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
    <head>
        <title>System kontroli obecności</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <style type="text/css">
        <%@ include file="../css/main.css" %>
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

        <div class="jumbotron text-center">
            <h1>System kontroli obecności</h1> 
            <p>Sprawdzaj obecność codziennie w łatwy sposób</p> 
            <form class="form-inline" action="registerServlet" method="post">           
                <input type="email" class="form-control" name="newEmail" size="50" placeholder="Email" required>
                <button type="submit" class="btn btn-danger">Zarejestruj</button>
            </form>
        </div>

    </body>
</html>
