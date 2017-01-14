<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>

<html>
    <head>
        <title>Dodawanie przedmiotu</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/bootstrap.3.3.7.min.css"/>
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="/resources/js/jquery.1.11.3.min.js"></script>
        <script src="/resources/js/bootstrap.3.3.7.min.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <div class="container">
            <br><br>
            <jsp:include page="../alert/allAlerts.jsp"/>
            <h3>Nowy kurs</h3>
            <form id="newSubjectForm" action="/subjects/save" method="post" commandName="newSubjectForm" role="form" method="post">
                <div class="form-group">
                    <label>Nazwa przedmiotu</label>
                    <input type="text" id="name" name="name" class="form-control">
                </div>
                <div class="form-group">
                    <label>Rok studiów</label>
                    <input type="text" id="yearOfStudy" name="yearOfStudy" class="form-control">
                </div>
                <div class="form-group">
                    <label>Kierunek</label>
                    <select class="form-control" name="field" value="${fields}">
                        <c:forEach var="f" items="${fieldList}">
                            <option type="text" name="field" value="${f.name}">${f.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Liczba lekcji</label>
                    <input type="text" id="quantity" name="quantity" class="form-control">
                </div>
                <div class="form-group">
                    <label>Minimalna ilość obecności</label>
                    <input type="text" id="minQuantity" name="minQuantity" class="form-control">
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
