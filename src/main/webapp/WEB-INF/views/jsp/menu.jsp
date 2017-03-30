<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            <a class="navbar-brand" href="/login" action="login"><spring:message code='view.menu.login'/></a>
            <%} else { %> 
            <a class="navbar-brand" href="/login/logOut" action="logout"><spring:message code='view.menu.logout'/></a>
            <% } %>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">

                <% if ("STUDENT".equals(session.getAttribute("userType")) ||
                       "TEACHER".equals(session.getAttribute("userType"))) { %>

                    <li><a href="/profile"><spring:message code='view.menu.profile'/></a></li>
                    <li><a href="/precenses"><spring:message code='view.menu.presences'/></a></li>
                    <li><a href="/courses"><spring:message code='view.menu.subjects'/></a></li>
                    <li><a href="/grades"><spring:message code='view.menu.grades'/></a></li>
                    <li><a href="/stats"><spring:message code='view.menu.stats'/></a></li>
                <%} %>

                <% if ("ADMIN".equals(session.getAttribute("userType"))) { %>
                    <li><a href="/profile"><spring:message code='view.menu.profile'/></a></li>
                    <li><a href="/subjects"><spring:message code='view.menu.subjects'/></a></li>
                    <li><a href="/courses"><spring:message code='view.menu.courses'/></a></li>
                <%} %>
            </ul>
        </div>
    </div>
</nav>
