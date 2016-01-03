<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sprawdź obecność</title>
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
            <form action="updatePrecensesServlet" method="post">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Imię</th>
                            <th>Nazwisko</th>
                            <th>Obecność</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td><c:out value="${student.firstName}"  /></td>
                                <td><c:out value="${student.lastName}" /></td>
                                <td>

                                    <c:if test="${student.precenseStatus == 'Obecny'}">
                                        <label class="radio-inline">
                                            <input checked type="radio" name="${student.ID}_precenseStatus" value="Obecny"/>Obecny
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="${student.ID}_precenseStatus" value="Nieobecny"/>Nieobecny
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="${student.ID}_precenseStatus" value="Spozniony"/>Spóźniony
                                        </label>
                                    </c:if>
                                    <c:if test="${student.precenseStatus == 'Nieobecny'}">
                                        <label class="radio-inline">
                                            <input type="radio" name="${student.ID}_precenseStatus" value="Obecny"/>Obecny
                                        </label>
                                        <label class="radio-inline">
                                            <input checked type="radio" name="${student.ID}_precenseStatus" value="Nieobecny"/>Nieobecny
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="${student.ID}_precenseStatus" value="Spozniony"/>Spóźniony
                                        </label>
                                    </c:if>
                                    <c:if test="${student.precenseStatus == 'Spozniony'}">
                                        <label class="radio-inline">
                                            <input type="radio" name="${student.ID}_precenseStatus" value="Obecny"/>Obecny
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="${student.ID}_precenseStatus" value="Nieobecny"/>Nieobecny
                                        </label>
                                        <label class="radio-inline">
                                            <input checked type="radio" name="${student.ID}_precenseStatus" value="Spozniony"/>Spóźniony
                                        </label>
                                    </c:if>
                                    <c:if test="${student.precenseStatus != 'Obecny' &&
                                                  student.precenseStatus != 'Nieobecny' && 
                                                  student.precenseStatus != 'Spozniony'}">
                                          <label class="radio-inline">
                                              <input type="radio" name="${student.ID}_precenseStatus" value="Obecny"/>Obecny
                                          </label>
                                          <label class="radio-inline">
                                              <input type="radio" name="${student.ID}_precenseStatus" value="Nieobecny"/>Nieobecny
                                          </label>
                                          <label class="radio-inline">
                                              <input type="radio" name="${student.ID}_precenseStatus" value="Spozniony"/>Spóźniony
                                          </label>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button value="submit" type="submit" class="btn btn-success">Zatwierdź</button>
            </form>
        </div>
        <% }%>    
    </body>
</html>