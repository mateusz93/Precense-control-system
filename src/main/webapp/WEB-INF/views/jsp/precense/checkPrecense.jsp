<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sprawdź obecność</title>
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
            <div class="input-group input-group-lg add-on">
                <div class="input-group"> <span class="input-group-addon">Filter</span>
                    <input id="filter" type="text" class="form-control" >
                </div>
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Imię</th>
                        <th>Nazwisko</th>
                        <th>Obecność</th>
                    </tr>
                </thead>
                <tbody id="paginationTable" class="searchable">
                    <form action="/precenses/update/${courseDateId}" modelAttribute="studentWrapper" method="post" commandName="precenseForm" role="form">
                        <c:forEach var="student" items="${studentWrapper.students}">
                            <tr>
                                <input type="hidden" name="ID" value="${student.ID}"/>
                                <td><c:out value="${student.firstName}"  /></td>
                                <input type="hidden" name="firstName" value="${student.firstName}"/>
                                <td><c:out value="${student.lastName}" /></td>
                                <input type="hidden" name="lastName" value="${student.lastName}"/>
                                <td>

                                    <c:if test="${student.precenseStatus != 'Obecny' &&
                                                  student.precenseStatus != 'Nieobecny' &&
                                                  student.precenseStatus != 'Spozniony'}">

                                        <select class="form-control" name="precenseStatus" value="${precenseStatus}">
                                            <option value="" selected></option>
                                            <option value="Obecny">Obecny</option>
                                            <option value="Nieobecny">Nieobecny</option>
                                            <option value="Spozniony">Spóźniony</option>
                                        </select>
                                    </c:if>

                                    <c:if test="${student.precenseStatus == 'Spozniony'}">
                                        <select class="form-control" name="precenseStatus" value="${precenseStatus}">
                                            <option value=""></option>
                                            <option value="Obecny">Obecny</option>
                                            <option value="Nieobecny">Nieobecny</option>
                                            <option value="Spozniony" selected>Spóźniony</option>
                                        </select>
                                    </c:if>

                                    <c:if test="${student.precenseStatus == 'Nieobecny'}">
                                        <select class="form-control" name="precenseStatus" value="${precenseStatus}">
                                            <option value=""></option>
                                            <option value="Obecny">Obecny</option>
                                            <option value="Nieobecny" selected>Nieobecny</option>
                                            <option value="Spozniony">Spóźniony</option>
                                        </select>
                                    </c:if>

                                    <c:if test="${student.precenseStatus == 'Obecny'}">
                                        <select class="form-control" name="precenseStatus" value="${precenseStatus}">
                                            <option value=""></option>
                                            <option value="Obecny" selected>Obecny</option>
                                            <option value="Nieobecny">Nieobecny</option>
                                            <option value="Spozniony">Spóźniony</option>
                                        </select>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <input type="hidden" name="courseDateId" value="${courseDateId}"/>
                        <button value="submit" type="submit" class="btn btn-success">Zatwierdź</button>
                    </form>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 text-center">
            <ul class="pagination" id="pager"></ul>
        </div>
    </body>
</html>