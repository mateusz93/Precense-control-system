<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>

<html>
    <head>
        <title>Profil</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <style type="text/css">
        <%@ include file="WEB-INF/css/profile.css" %>
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
        <div class="container">
            <br><br><br>
            <hr>
            <div class="row">
                <!-- left column -->
                <div class="col-md-3">
                    <div class="text-center">
                        <img src="/https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT2d4kAayBmJjefFVzQR7txYHk9Lzg0bdXeGWkRF7jRUgdZMNtR" class="avatar img-circle" alt="avatar">
                        <h6>Upload a different photo...</h6>

                        <input class="form-control" type="file">
                    </div>
                </div>
                <!-- edit form column -->
                <div class="col-md-9 personal-info">
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
                    <h3>Dane osobiste</h3>
                    <form class="form-horizontal" id="profile-form" action="profileServlet" method="post" role="form">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Imię:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${firstName}" name="firstName" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Nazwisko:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${lastName}" name="lastName" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">PESEL:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${pesel}" name="PESEL" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Email:</label>
                            <div class="col-lg-8">
                                <input class="form-control" readonly="readonly" value="${email}" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Typ:</label>
                            <div class="col-lg-8">
                                <input class="form-control" readonly="readonly" value="${type}" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Index:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${index}" name="index" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Telefon:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${phone}" name="phone" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Ulica:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${street}" name="street" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Miasto:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${city}" name="city" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Login:</label>
                            <div class="col-md-8">
                                <input class="form-control" readonly="readonly" value="<%= session.getAttribute("username")%>" name="login" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Hasło:</label>
                            <div class="col-md-8">
                                <input class="form-control" value="" type="password" name="password" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Potwierdź hasło:</label>
                            <div class="col-md-8">
                                <input class="form-control" value="" type="password" name="confirmPassword" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">
                                <input class="btn btn-primary" type="submit" value="Zapisz zmiany">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <hr>
    </body>
</html>
