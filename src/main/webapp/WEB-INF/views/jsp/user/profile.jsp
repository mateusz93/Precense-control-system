<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>

<html>
    <head>
        <title>Profil</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="/resources/css/profile.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <body>
        <jsp:include page="../menu.jsp"/>
        <div class="container">
            <br><br>
            <jsp:include page="../alert/allAlerts.jsp"/>
            <hr>
            <div class="row">
                <!-- left column -->
                <div class="col-md-3">
                    <div class="text-center">
                        <img src="${photoPath}" />
                        <form method="POST" enctype="multipart/form-data" action="/profile/upload">
                            <input class="form-control" type="file" name="file" />
                            <input type="submit" value="Upload" />
                        </form>
                    </div>
                </div>
                <!-- edit form column -->
                <div class="col-md-9 personal-info">
                    <h3>Dane osobiste</h3>
                    <form class="form-horizontal" id="profile-form" action="/profile" commandName="profileForm" method="post" role="form">
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Imię:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${firstName}" name="firstName" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Nazwisko:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${lastName}" name="lastName" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Index:</label>
                            <div class="col-lg-8">
                                <input class="form-control" readonly="readonly" value="${ID}" name="ID" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Email:</label>
                            <div class="col-lg-8">
                                <input class="form-control" readonly="readonly" value="${email}" name="email" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Grupa dziekańska:</label>
                            <div class="col-lg-8">
                                <input class="form-control" readonly="readonly" value="${group}" name="group" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Typ:</label>
                            <div class="col-lg-8">
                                <input class="form-control" readonly="readonly" value="${type}" name="type" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Telefon:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${phone}" name="phone" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Miasto:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${city}" name="city" type="text">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">Ulica:</label>
                            <div class="col-lg-8">
                                <input class="form-control" value="${street}" name="street" type="text">
                            </div>
                        </div>
                        <br>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Hasło:</label>
                            <div class="col-md-8">
                                <input class="form-control" value="" type="password" name="password" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Potwierdź hasło:</label>
                            <div class="col-md-8">
                                <input class="form-control" value="" type="password" name="confirmPassword" >
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"></label>
                            <div class="col-md-8">
                                <input class="btn btn-primary" type="submit" value="Zapisz zmiany">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <hr>
    </body>
</html>
