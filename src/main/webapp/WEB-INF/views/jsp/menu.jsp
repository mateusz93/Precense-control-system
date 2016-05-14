<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <% if ("".equals(session.getAttribute("username")) || session.getAttribute("username") == null) { %>
            <a class="navbar-brand" href="/login" action="login">Zaloguj</a>
            <%} else { %> 
            <a class="navbar-brand" href="/login/logOut" action="logout">Wyloguj</a>
            <% } %>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">

                <li><a href="/profile">Profil</a></li>
                <li><a href="/precenses">Obecności</a></li>
                <li><a href="/courses">Moje przedmioty</a></li>
            <!--<li><a href="statsServlet">Statystyki</a></li>-->

                <% if ("Student".equals(session.getAttribute("userType"))) { %>
                    <li><a href="/saves">Zapisy na zajęcia</a></li>
                <%} %>
            </ul>
        </div>
    </div>
</nav>
