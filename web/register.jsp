<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <style type="text/css">
        <%@ include file="WEB-INF/css/register.css" %>
    </style>

    <script type="text/javascript">
        <%@ include file="WEB-INF/js/register.js" %>
    </script>

    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.htm">Strona główna</a>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
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
                                    <form id="login-form" action="loginServlet" method="post" role="form" style="display: none;">
                                        <div class="form-group">
                                            <input type="text" name="type" id="type" tabindex="1" class="form-control" placeholder="Typ" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Nazwa użytkownika" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="userpass" id="password" tabindex="2" class="form-control" placeholder="Hasło">
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Zaloguj">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <form id="register-form" action="registerServlet" method="post" role="form" style="display: block;">
                                        <div class="form-group">
                                            <input type="text" name="firstName" id="firstName" tabindex="1" class="form-control" placeholder="Imię" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" name="lastName" id="lastName" tabindex="1" class="form-control" placeholder="Nazwisko" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" name="type" id="type" tabindex="1" class="form-control" placeholder="Typ" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Nazwa użytkownika" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email" value="${newEmail}">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Hasło">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="confirmPassword" id="confirmPassword" tabindex="2" class="form-control" placeholder="Potwierdź hasło">
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Zarejestruj teraz">
                                                </div>
                                            </div>
                                        </div>
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
