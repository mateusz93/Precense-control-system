<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="net.tanesha.recaptcha.*" %>
<!DOCTYPE HTML>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title><spring:message code='view.login.title'/></title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/resources/css/bootstrap.3.3.7.min.css"/>
        <link rel="stylesheet" href="/resources/css/register.css"/>
        <script src="/resources/js/jquery.1.11.3.min.js"></script>
        <script src="/resources/js/bootstrap.3.3.7.min.js"></script>
        <script src="/resources/js/register.js"></script>
        <script src="https://www.google.com/recaptcha/api.js" async="async" defer="defer"></script>
    </head>

    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="/"><spring:message code='view.page.main'/></a>
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
                                    <a href="#" class="active" id="login-form-link"><spring:message code='view.login.title'/></a>
                                </div>
                                <div class="col-xs-6">
                                    <a href="#" id="register-form-link"><spring:message code='view.register.title'/></a>
                                </div>
                            </div>
                            <hr>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    <form id="login-form" action="/login/logIn" method="post" commandName="loginForm" role="form" style="display: block;">
                                        <jsp:include page="loginForm.jsp"/>
                                    </form>
                                    <form id="register-form" action="/register/addUser" method="post" commandName="registerForm" role="form" style="display: none;">
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