<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE HTML>

<html>
    <head>
        <title><spring:message code='view.course.date.title'/></title>
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
                        <th><spring:message code='view.presence.day'/></th>
                        <th><spring:message code='view.course.time.start'/></th>
                        <th><spring:message code='view.course.time.end'/></th>
                    </tr>
                </thead>
                <tbody id="paginationTable" class="searchable">
                    <c:forEach var="date" items="${datesList}">
                        <tr>
                            <td class="col-md-2"><c:out value="${date.date}"  /></td>
                            <td class="col-md-2"><c:out value="${date.startTime}" /></td>
                            <td class="col-md-2"><c:out value="${date.finishTime}" /></td>
                            <form action="/precenses/check/${date.id}" method="post">
                                <td class="col-md-1"><button name="deleteID" value="${date.id}" type="submit" class="btn btn-info"><spring:message code='view.presence.title'/></button></td>
                            </form>
                            <form action="/precenses/cancel/${date.id}" method="post">
                                <td class="col-md-1"><button name="deleteID" value="${date.id}" type="submit" class="btn btn-danger"><spring:message code='view.course.cancel'/></button></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 text-center">
            <ul class="pagination" id="pager"></ul>
        </div>
    </body>
</html>