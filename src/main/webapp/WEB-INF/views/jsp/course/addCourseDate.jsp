<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <title>Dodanie terminu zajęć</title>
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
            <h3>Dodaj nowy termin zajęć</h3>
            <form action="/courses/addCourseDate/${teacherCourseId}" method="post" commandName="courseDateForm" role="form">
                <div class="form-group">
                    <label>Data</label>
                    <input type="text" placeholder="Format: YYYY-MM-DD" name="date" value="${date}" class="form-control">
                </div>
                <div class="form-group">
                    <label>Czas rozpoczęcia</label>
                    <input type="text" placeholder="Format: GG:MM:SS" name="startTime" value="${startTime}" class="form-control">
                </div>
                <div class="form-group">
                    <label>Czas zakończenia</label>
                    <input type="text" placeholder="Format: GG:MM:SS" name="finishTime" value="${finishTime}" class="form-control">
                </div>
                <input type="hidden" name="isEdited" value="${isEdited}" class="form-control">
                <input type="hidden" name="dateId" value="${dateId}" class="form-control">
                <td><button type="submit"  class="btn btn-success">Zatwierdź</button></td>
            </form>
        </div>
    </body>
</html>
