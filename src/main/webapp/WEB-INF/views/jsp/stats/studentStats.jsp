<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
    <head>
        <title>Statystyki</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/bootstrap.3.3.7.min.css"/>
        <link rel="stylesheet" href="/resources/css/menu.css"/>
        <script src="/resources/js/jquery.1.11.3.min.js"></script>
        <script src="/resources/js/bootstrap.3.3.7.min.js"></script>
        <script src="/resources/js/loader.js"></script>
        <script src="/resources/js/api.js"></script>
        <script src="/resources/js/pagination.js"></script>
        <script src="/resources/js/filter.js"></script>
        <script src="/resources/js/studentsStats.js"></script>
    </head>
    <body>
        <jsp:include page="../menu.jsp"/>

        <div class="container">
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
                        <div class="col-md-7">
                            <h4>Statystyki obecności</h4>
                            <div id="globalPresenceAverage">
                            </div>
                        </div>
                        <div class="col-md-5">

                        </div>
                    </div>
                    <div class="tab-pane fade" id="tab2">
                        <div class="col-md-7">
                            <h4>Statystyki końcowych ocen</h4>
                            <div id="finalGradesAverage">
                            </div>
                        </div>
                        <div class="col-md-5">
                            <h4>Statystyki wszystkich ocen</h4>
                            <div id="globalGradesAverage">
                            </div>
                        </div>
                    <!--
                        <h4>Średnia ocen z poszczegolnych przedmiotów</h4>
                        <div id="subjectGradesAverage">
                        </div>
                    -->
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
