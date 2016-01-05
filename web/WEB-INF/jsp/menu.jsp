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
                <!--                        <li><a href="teacherStatsServlet">Statystyki</a></li>-->
                <%} else { %> 
                <li><a href="profileServlet">Profil</a></li>
                <li><a href="precensesServlet">Obecności</a></li>
                <li><a href="coursesServlet">Moje przedmioty</a></li>
                <li><a href="savesServlet">Zapisy na zajęcia</a></li>
                <!--                        <li><a href="statsServlet">Statystyki</a></li>-->
                <% }%>
            </ul>
        </div>
    </div>
</nav>
