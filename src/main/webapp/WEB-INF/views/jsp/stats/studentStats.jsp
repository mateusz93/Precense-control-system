<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
    <head>
        <title>Statystyki</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript" src="http://www.google.com/jsapi"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="/resources/js/pagination.js"></script>
        <script src="/resources/js/filter.js"></script>
        <script src="/resources/js/studentsGrades.js"></script>
        <script src="/resources/js/studentsPrecenses.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>
        <br><br><br>
        <div class="col-md-2">
            <ul class="nav nav-pills nav-stacked">
                <li class="active"><a href="#tab1" data-toggle="tab">Obecności</a></li>
                <li><a href="#tab2" data-toggle="tab">Oceny</a></li>
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
    </body>
</html>
