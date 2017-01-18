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

                <% if ("Student".equals(session.getAttribute("userType")) ||
                       "Teacher".equals(session.getAttribute("userType"))) { %>

                    <li><a href="/profile">Profil</a></li>
                    <li><a href="/precenses">Obecności</a></li>
                    <li><a href="/courses">Przedmioty</a></li>
                    <li><a href="/grades">Oceny</a></li>
                    <li><a href="/stats">Statystyki</a></li>
                <%} %>

                <% if ("Admin".equals(session.getAttribute("userType"))) { %>
                    <li><a href="/profile">Profil</a></li>
                    <li><a href="/subjects">Przedmioty</a></li>
                    <li><a href="/courses">Kursy</a></li>

                <%} %>
            </ul>
        </div>
    </div>
</nav>
