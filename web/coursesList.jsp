<%-- 
    Document   : coursesList
    Created on : Nov 11, 2015, 9:29:00 AM
    Author     : mateusz
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>   
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
    <body>

    <sql:setDataSource
        var="myDB"
        driver="com.mysql.jdbc.Driver"
        url="jdbc:mysql://localhost:3306/data"
        user="root" password="root"
        />
    <sql:query var="coursesList" dataSource="${myDB}">
        SELECT * FROM CourseDates;
    </sql:query>

            <div class="container">
                <h2>Lista kursów</h2>
                <p>Lista wszystkich kursów pobrana z bazki</p>            
              <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Kurs ID</th>
                            <th>Czas rozpoczęcia</th>
                            <th>Czas zakończenia</th>
                            <th>Dzień</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="courses" items="${coursesList.rows}">
                            <tr>
                                <td><c:out value="${courses.ID}"  /></td>
                                <td><c:out value="${courses.courseID}" /></td>
                                <td><c:out value="${courses.startTime}" /></td>
                                <td><c:out value="${courses.finishTime}"  /></td>
                                <td><c:out value="${courses.date}"  /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

</body>
</html>
