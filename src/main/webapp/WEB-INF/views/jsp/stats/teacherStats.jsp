<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>

<html>
    <head>
        <title><spring:message code='view.stats.title'/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/bootstrap.3.3.7.min.css"/>
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="/resources/js/jquery.1.11.3.min.js"></script>
        <script src="/resources/js/bootstrap.3.3.7.min.js"></script>
        <script src="/resources/js/loader.js"></script>
        <script src="/resources/js/api.js"></script>
        <script src="/resources/js/pagination.js"></script>
        <script src="/resources/js/filter.js"></script>
        <script src="/resources/js/studentsGrades.js"></script>
        <script src="/resources/js/studentsPrecenses.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>

        <div class="container">
            <br><br><br>
            <div class="col-md-2">
                <ul class="nav nav-pills nav-stacked">
                    <li class="active"><a href="#tab1" data-toggle="tab"><spring:message code='view.stats.presences'/></a></li>
                    <li><a href="#tab2" data-toggle="tab"><spring:message code='view.stats.grades'/></a></li>
                </ul>
            </div>
            <div class="col-md-8 personal-info">
                <div class="tab-content">
                    <div class="tab-pane fade in active" id="tab1">
                        <div id="precenses"></div>
                    </div>
                    <div class="tab-pane fade" id="tab2">
                        <div id="chart_div"></div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
