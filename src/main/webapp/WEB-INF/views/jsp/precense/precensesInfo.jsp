<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<!-- http://www.bootply.com/aKYODoZowf -->

<html>
    <head>
        <title>Szczegóły obecności</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="/resources/js/pagination.js"></script>
    </head>

    <body>
        <jsp:include page="../menu.jsp"/>
        <div class="container">
            <br><br><br>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Dzień</th>
                        <th>Czas rozpoczęcia</th>
                        <th>Czas zakończenia</th>
                        <th>Obecność</th>
                    </tr>
                </thead>
                <tbody id="myTable">
                    <c:forEach var="dates" items="${datesList}">
                        <tr>
                            <td><c:out value="${dates.date}"  /></td>
                            <td><c:out value="${dates.startTime}" /></td>
                            <td><c:out value="${dates.finishTime}" /></td>
                            <td>
                                <form role="form">
                                    <c:if test="${dates.status == 'Obecny'}">
                                        <label class="radio-inline">
                                            <input checked disabled type="radio" name="optionRadio">Obecny
                                        </label>
                                        <label class="radio-inline">
                                            <input disabled type="radio" name="optionRadio">Nieobecny
                                        </label>
                                        <label class="radio-inline">
                                            <input disabled type="radio" name="optionRadio">Spóźniony
                                        </label>
                                    </c:if>
                                    <c:if test="${dates.status == 'Nieobecny'}">
                                        <label class="radio-inline">
                                            <input disabled type="radio" name="optionRadio">Obecny
                                        </label>
                                        <label class="radio-inline">
                                            <input checked disabled type="radio" name="optionRadio">Nieobecny
                                        </label>
                                        <label class="radio-inline">
                                            <input disabled type="radio" name="optionRadio">Spóźniony
                                        </label>
                                    </c:if>
                                    <c:if test="${dates.status == 'Spozniony'}">
                                        <label class="radio-inline">
                                            <input disabled type="radio" name="optionRadio">Obecny
                                        </label>
                                        <label class="radio-inline">
                                            <input disabled type="radio" name="optionRadio">Nieobecny
                                        </label>
                                        <label class="radio-inline">
                                            <input checked disabled type="radio" name="optionRadio">Spóźniony
                                        </label>
                                    </c:if>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 text-center">
            <ul class="pagination" id="myPager"></ul>
        </div>
    </body>
</html>