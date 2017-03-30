<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dodanie kilka terminu zajęć</title>
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
            <h3>Dodaj kilka terminów zajęć</h3>
            <form name="form1" action="addSeveralCourseDatesServlet" method="post">
                <div class="form-group">
                    <label>Data pierwszych zajęć</label>
                    <input type="text" placeholder="YYYY-MM-DD" name="firstDate" value="${firstDate}" class="form-control">
                </div>
                <div class="form-group">
                    <label>Data ostatnich zajęć</label>
                    <input type="text" placeholder="YYYY-MM-DD" name="lastDate" value="${lastDate}" class="form-control">
                </div>
<!--
                <div class="controlset-pad">
                    <input type="checkbox" name="others" onclick="enable(this.checked)" class="medium" />
                    <label>Dodaj maksymalną ilość terminów</label>
                </div>
                <div class="field4">
                    <label>Name on credit card if different from above</label>
                    <input type="text" name="other_name" class="medium" enabled="enabled" />
                </div>
-->
                <div class="form-group">
                    <label>Odstęp pomiędzy zajęciami</label>
                    <select class="form-control" name="space">
                        <option value="1">1 tydzień</option>
                        <option value="2">2 tygodnie</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Czas rozpoczęcia</label>
                    <input type="text" placeholder="GG:MM:SS" name="startTime" value="${startTime}" class="form-control">
                </div>
                <div class="form-group">
                    <label>Czas zakończenia</label>
                    <input type="text" placeholder="GG:MM:SS" name="finishTime" value="${finishTime}" class="form-control">
                </div>
                <br>
                <td><button type="submit" name="courseDateID" value="${ID}" class="btn btn-success">Zatwierdź</button></td>
                <br>
            </form>
        </div>
    </body>
</html>
