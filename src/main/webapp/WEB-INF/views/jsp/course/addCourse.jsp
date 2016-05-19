<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>

<html>
    <head>
        <title>Dodawanie kursu</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <div class="container">
            <br><br>
            <jsp:include page="../alert/allAlerts.jsp"/>
            <h3>Nowy kurs</h3>
            <form id="newCourseForm" action="/courses/save" method="post" commandName="newCourseForm" role="form" method="post">
                <div class="form-group">
                    <label>Nazwa przedmiotu</label>
                    <input type="text" id="subjectName" name="subjectName" class="form-control">
                </div>
                <div class="input-append btn-group">
                    <label>Wydział</label>
                    <select class="form-control" name="departmentID">
                        <c:forEach var="department" items="${departments}">
                            <option value="${department.id}"><c:out value="${department.name}"/></option>
                        </c:forEach>
                    </select>
                    <br>
                </div>
                <div class="form-group">
                    <label>Typ</label>
                    <select class="form-control" name="type" id="type">
                        <option value="Cwiczenia">Ćwiczenia</option>
                        <option value="Laboratorium">Laboratorium</option>
                        <option value="Wyklad">Wykład</option>
                        <option value="Projekt">Projekt</option>
                        <option value="Seminarium">Seminarium</option>
                    </select>
                    <br>
                </div>
                <div class="form-group">
                    <label>Ilość zajęć</label>
                    <input type="text" id="quantity" name="quantity" class="form-control">
                </div>
                <div class="form-group">
                    <label>Minimalna ilość obecności</label>
                    <input type="text" id="min" name="min" class="form-control">
                </div>
                <div class="form-group">
                    <label>Opis</label>
                    <input type="text" id="description" name="description" class="form-control">
                </div>
                <td><button type="submit" class="btn btn-success">Zatwierdź</button></td>
            </form>
        </div>
    </body>
</html>
