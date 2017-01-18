<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>

<html>
    <head>
        <title>Kursy</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/bootstrap.3.3.7.min.css"/>
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="/resources/js/jquery.1.11.3.min.js"></script>
        <script src="/resources/js/bootstrap.3.3.7.min.js"></script>
        <script src="/resources/js/pagination.js"></script>
        <script src="/resources/js/filter.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <div class="container">
            <br><br>
            <jsp:include page="../alert/allAlerts.jsp"/>
            <hr>
            <table class="table table-striped">
            <form action="/grades/save/${courseId}/${studentId}" commandName="addGrade" method="post">
                <thead>
                    <tr>
                        <th>Student</th>
                        <th>Ocena</th>
                        <th>Czy końcowa</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><c:out value="${studentName}"  /></td>
                        <td>
                            <select class="form-control" name="value">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                            </select>
                        </td>
                        <td>
                            <select class="form-control" name="isFinal">
                                <option value="Tak">Tak</option>
                                <option value="Nie">Nie</option>
                            </select>
                        </td>
                        <td><button type="submit" class="btn btn-info">Zapisz ocenę</button></td>
                    </tr>
                </tbody>
            </form>
            </table>
        </div>
        <div class="col-md-12 text-center">
            <ul class="pagination" id="pager"></ul>
        </div>
    </body>
</html>
