<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>

<html>
    <head>
        <title><spring:message code='view.subject.add.title'/></title>
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
            <h3><spring:message code='view.subject.new'/></h3>
            <form id="newSubjectForm" action="/subjects/save" method="post" commandName="newSubjectForm" role="form" method="post">
                <div class="form-group">
                    <label><spring:message code='view.subject.name'/></label>
                    <input type="text" id="name" name="name" class="form-control">
                </div>
                <div class="form-group">
                    <label><spring:message code='view.subject.year'/></label>
                    <input type="text" id="yearOfStudy" name="yearOfStudy" class="form-control">
                </div>
                <div class="form-group">
                    <label><spring:message code='view.subject.field'/></label>
                    <select class="form-control" name="field" value="${fields}">
                        <c:forEach var="f" items="${fieldList}">
                            <option type="text" name="field" value="${f.name}">${f.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label><spring:message code='view.subject.quantity'/></label>
                    <input type="text" id="quantity" name="quantity" class="form-control">
                </div>
                <div class="form-group">
                    <label><spring:message code='view.subject.minQuantity'/></label>
                    <input type="text" id="minQuantity" name="minQuantity" class="form-control">
                </div>
                <div class="form-group">
                    <label><spring:message code='view.subject.description'/></label>
                    <input type="text" id="description" name="description" class="form-control">
                </div>
                <td><button type="submit" class="btn btn-success"><spring:message code='view.subject.apply'/></button></td>
            </form>
        </div>
    </body>
</html>
