<%@page import="java.sql.DriverManager"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <title>Profil</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <sql:setDataSource
        var="myDB"
        driver="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost:3306/data"
        user="root" password="root"
        />

    <c:set var="userName" value="${sessionScope.username}"/>

    <sql:query var="user" dataSource="${myDB}">
        SELECT * FROM Users WHERE login = ?;
        <sql:param value="${userName}" />
    </sql:query>

    <c:forEach var="u" items="${user.rows}">
        <c:set var="userID" value="${u.ID}"/>
        <c:set var="firstName" value="${u.firstName}"/>
        <c:set var="lastName" value="${u.lastName}"/>
        <c:set var="index" value="${u.index}"/>
        <c:set var="type" value="${u.type}"/>
        <c:set var="password" value="${u.password}"/>
    </c:forEach>

    <sql:query var="contact" dataSource="${myDB}">
        SELECT * FROM Contacts WHERE userID = ?;
        <sql:param value="${userID}" />
    </sql:query>
        
    <c:forEach var="c" items="${contact.rows}">
        <c:set var="email" value="${c.email}"/>
        <c:set var="PESEL" value="${c.PESEL}"/>
        <c:set var="phone" value="${c.phone}"/>
        <c:set var="street" value="${c.street}"/>
        <c:set var="city" value="${c.city}"/>
    </c:forEach>
        

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
                <%
                    if ((session.getAttribute("username") == null) || (session.getAttribute("username") == "")) {
                %>
                <a class="navbar-brand" href="login.jsp" action="login.jsp">Zaloguj</a>
                <%} else {
                %>
                <a class="navbar-brand" href="logout.jsp" action="logout.jsp">Wyloguj</a>
                <%
                    }
                %>
                <!--                <a class="navbar-brand" href="login.jsp" action="login.jsp">Zaloguj</a>-->
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="profil.jsp">Profil</a></li>
                    <li><a href="precenses.jsp">Obecności</a></li>
                    <li><a href="coursesList.jsp">Moje przedmioty</a></li>
                    <li><a href="saves.jsp">Zapisy na zajęcia</a></li>
                    <li><a href="stats.jsp">Statystyki</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <%
            if ((session.getAttribute("username") == null) || (session.getAttribute("username") == "")) {
        %>
        <c:redirect url="/login.jsp"/>
        <%} else {
        %>
        <h2>Profil</h2>
        <p>Profil</p>
        <hr>
        <div class="row">
            <!-- left column -->
            <div class="col-md-3">
                <div class="text-center">
                    <img src="//placehold.it/100" class="avatar img-circle" alt="avatar">
                    <h6>Upload a different photo...</h6>

                    <input class="form-control" type="file">
                </div>
            </div>

            <!-- edit form column -->
            <div class="col-md-9 personal-info">
                <div class="alert alert-info alert-dismissable">
                    <a class="panel-close close" data-dismiss="alert">×</a> 
                    <i class="fa fa-coffee"></i>
                    Poprawnie załadowano dane profilu z bazy.
                </div>
                <h3>Dane osobiste</h3>

                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Imię:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${firstName}" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Nazwisko:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${lastName}" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">PESEL:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${PESEL}" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Email:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${email}" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Typ:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${type}" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Index:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${index}" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Telefon:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${phone}" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Ulica:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${street}" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label">Miasto:</label>
                        <div class="col-lg-8">
                            <input class="form-control" value="${city}" type="text">
                        </div>
                    </div>
                    <!--                    <div class="form-group">
                                            <label class="col-lg-3 control-label">Time Zone:</label>
                                            <div class="col-lg-8">
                                                <div class="ui-select">
                                                    <select id="user_time_zone" class="form-control">
                                                        <option value="Hawaii">(GMT-10:00) Hawaii</option>
                                                        <option value="Alaska">(GMT-09:00) Alaska</option>
                                                        <option value="Pacific Time (US &amp; Canada)">(GMT-08:00) Pacific Time (US &amp; Canada)</option>
                                                        <option value="Arizona">(GMT-07:00) Arizona</option>
                                                        <option value="Mountain Time (US &amp; Canada)">(GMT-07:00) Mountain Time (US &amp; Canada)</option>
                                                        <option value="Central Time (US &amp; Canada)" selected="selected">(GMT-06:00) Central Time (US &amp; Canada)</option>
                                                        <option value="Eastern Time (US &amp; Canada)">(GMT-05:00) Eastern Time (US &amp; Canada)</option>
                                                        <option value="Indiana (East)">(GMT-05:00) Indiana (East)</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>-->
                    <div class="form-group">
                        <label class="col-md-3 control-label">login:</label>
                        <div class="col-md-8">
                            <input class="form-control" value="<%= session.getAttribute("username")%>" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">Hasło:</label>
                        <div class="col-md-8">
                            <input class="form-control" value="" type="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">Potwierdz hasło:</label>
                        <div class="col-md-8">
                            <input class="form-control" value="" type="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"></label>
                        <div class="col-md-8">
                            <input class="btn btn-primary" value="Zapisz zmiany" type="button">
                            <span></span>
                            <input class="btn btn-default" value="Anuluj" type="reset">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <hr>
    <%
        }
    %>


</body>
</html>
