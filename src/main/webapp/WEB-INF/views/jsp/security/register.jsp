<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="http://localhost:8080/WebApp/resources/css/register.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="http://localhost:8080/WebApp/resources/js/register.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/WebApp/">Strona główna</a>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                     <jsp:include page="../alert/allAlerts.jsp"/>
                    <div class="panel panel-login">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-6">
                                    <a href="#" id="login-form-link">Logowanie</a>
                                </div>
                                <div class="col-xs-6">
                                    <a href="#" class="active" id="register-form-link">Rejestracja</a>
                                </div>
                            </div>
                            <hr>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form id="login-form" action="login/logIn" method="post" commandName="loginForm" role="form" style="display: none;">
                                        <jsp:include page="loginForm.jsp"/>
                                    </form>
                                    <form id="register-form" action="register/addUser" method="post" commandName="registerForm" role="form" style="display: block;">
                                        <jsp:include page="registerForm.jsp"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
