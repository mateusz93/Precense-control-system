<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kursy</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <style>
        body {
            font: 400 15px Lato, sans-serif;
            line-height: 1.8;
            color: #818181;
        }
        h2 {
            font-size: 24px;
            text-transform: uppercase;
            color: #303030;
            font-weight: 600;
            margin-bottom: 30px;
        }
        h4 {
            font-size: 19px;
            line-height: 1.375em;
            color: #303030;
            font-weight: 400;
            margin-bottom: 30px;
        }  
        .jumbotron {
            background-color: #f4511e;
            color: #fff;
            padding: 100px 25px;
            font-family: Montserrat, sans-serif;
        }
        .container-fluid {
            padding: 60px 50px;
        }
        .bg-grey {
            background-color: #f6f6f6;
        }
        .logo-small {
            color: #f4511e;
            font-size: 50px;
        }
        .logo {
            color: #f4511e;
            font-size: 200px;
        }
        .thumbnail {
            padding: 0 0 15px 0;
            border: none;
            border-radius: 0;
        }
        .thumbnail img {
            width: 100%;
            height: 100%;
            margin-bottom: 10px;
        }
        .carousel-control.right, .carousel-control.left {
            background-image: none;
            color: #f4511e;
        }
        .carousel-indicators li {
            border-color: #f4511e;
        }
        .carousel-indicators li.active {
            background-color: #f4511e;
        }
        .item h4 {
            font-size: 19px;
            line-height: 1.375em;
            font-weight: 400;
            font-style: italic;
            margin: 70px 0;
        }
        .item span {
            font-style: normal;
        }
        .panel {
            border: 1px solid #f4511e; 
            border-radius:0 !important;
            transition: box-shadow 0.5s;
        }
        .panel:hover {
            box-shadow: 5px 0px 40px rgba(0,0,0, .2);
        }
        .panel-footer .btn:hover {
            border: 1px solid #f4511e;
            background-color: #fff !important;
            color: #f4511e;
        }
        .panel-heading {
            color: #fff !important;
            background-color: #f4511e !important;
            padding: 25px;
            border-bottom: 1px solid transparent;
            border-top-left-radius: 0px;
            border-top-right-radius: 0px;
            border-bottom-left-radius: 0px;
            border-bottom-right-radius: 0px;
        }
        .panel-footer {
            background-color: white !important;
        }
        .panel-footer h3 {
            font-size: 32px;
        }
        .panel-footer h4 {
            color: #aaa;
            font-size: 14px;
        }
        .panel-footer .btn {
            margin: 15px 0;
            background-color: #f4511e;
            color: #fff;
        }
        .navbar {
            margin-bottom: 0;
            background-color: #f4511e;
            z-index: 9999;
            border: 0;
            font-size: 14px !important;
            line-height: 1.42857143 !important;
            letter-spacing: 2px;
            border-radius: 0;
            font-family: Montserrat, sans-serif;
        }
        .navbar li a, .navbar .navbar-brand {
            color: #fff !important;
        }
        .navbar-nav li a:hover, .navbar-nav li.active a {
            color: #f4511e !important;
            background-color: #fff !important;
        }
        .navbar-default .navbar-toggle {
            border-color: transparent;
            color: #fff !important;
        }
        footer .glyphicon {
            font-size: 20px;
            margin-bottom: 20px;
            color: #f4511e;
        }
        @media screen and (max-width: 768px) {
            .col-sm-4 {
                text-align: center;
                margin: 25px 0;
            }
            .btn-lg {
                width: 100%;
                margin-bottom: 35px;
            }
        }
        @media screen and (max-width: 480px) {
            .logo {
                font-size: 150px;
            }
        }
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
    %>


    <%
        if ("Teacher".equals(session.getAttribute("type"))) {
    %>
    <div class="container">
        <h2>Lista kursów</h2>
        <form action="addSubjectServlet" method="get">
            <td><button type="submit" class="btn btn-success">Dodaj</button></td>
        </form>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Nazwa</th>
                    <th>Wydział</th>
                    <th>Typ zajęć</th>
                    <th>Ilość zajęć</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="courses" items="${coursesList}">
                    <tr>
                        <td><c:out value="${courses.subjectName}"  /></td>
                        <td><c:out value="${courses.departmentName}" /></td>
                        <td><c:out value="${courses.type}" /></td>
                        <td><c:out value="${courses.quantity}"  /></td>
                        <form action="courseInfoServlet" method="post">
                            <c:set var="subjectName" value="${courses.subjectName}" scope="request" />
                            <td><button name="info" value="${courses.id}" scope="request" type="submit" class="btn btn-info">Szczegóły</button></td>
                        </form>
                    </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <% }%>

</body>
</html>
