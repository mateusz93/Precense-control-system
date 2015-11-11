<%-- 
    Document   : login
    Created on : Nov 10, 2015, 9:31:21 PM
    Author     : mateusz
--%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login Application</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>
    <body>

        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                <a class="navbar-brand" href="index.htm">SKO</a>
            </div>
            </div>
        </nav>


            <form action="loginServlet" method="post">
            <section id="login">
                <div class="container">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="form-wrap">
                                <h1>Log in to your account</h1>
                                <form role="form" action="javascript:;" method="post" id="login-form" autocomplete="off">
                                    <div class="form-group">
                                        <label for="text" class="sr-only">Login</label>
                                        <input type="text" name="type" id="type" class="form-control" placeholder="Type">
                                    </div>
                                    <div class="form-group">
                                        <label for="text" class="sr-only">Login</label>
                                        <input type="text" name="username" id="email" class="form-control" placeholder="Login">
                                    </div>
                                    <div class="form-group">
                                        <label for="userpass" class="sr-only">Password</label>
                                        <input type="password" name="userpass" id="key" class="form-control" placeholder="Password">
                                    </div>
                                    <div class="checkbox">
                                        <span class="character-checkbox" onclick="showPassword()"></span>
                                        <span class="label">Show password</span>
                                    </div>
                                    <input type="submit" id="btn-login" class="btn btn-custom btn-lg btn-block" value="Log in">
                                </form>
                                <!--                                <a href="javascript:;" class="forget" data-toggle="modal" data-target=".forget-modal">Forgot your password?</a>
                                                                <hr>-->
                            </div>
                        </div> 
                    </div> 
                </div> 
            </section>
                </form>


        <style type="text/css">
            .navbar {
                margin-bottom: 0;
                background-color: #f4511e;
                z-index: 9999;
                border: 0;
                font-size: 12px !important;
                line-height: 1.42857143 !important;
                letter-spacing: 4px;
                border-radius: 0;
                font-family: Montserrat, sans-serif;
            }
            .navbar li a, .navbar .navbar-brand {
                color: #fff !important;
            }
            .navbar-nav li a:hover, .navbar-nav li.active a {
                color: #f4511e !important;
                background-color: #fff !important;
            }
            .navbar-default .navbar-toggle {
                border-color: transparent;
                color: #fff !important;
            }
            /*    --------------------------------------------------
                    :: Login Section
                    -------------------------------------------------- */
            #login {
                padding-top: 50px
            }
            #login .form-wrap {
                width: 40%;
                margin: 0 auto;
            }
            #login h1 {
/*                color: #1fa67b;*/
                color:#f4511e;
                font-size: 18px;
                text-align: center;
                font-weight: bold;
                padding-bottom: 20px;
            }
            #login .form-group {
                margin-bottom: 25px;
            }
            #login .checkbox {
                margin-bottom: 20px;
                position: relative;
                -webkit-user-select: none;
                -moz-user-select: none;
                -ms-user-select: none;
                -o-user-select: none;
                user-select: none;
            }
            #login .checkbox.show:before {
                content: '\e013';
/*                color: #1fa67b;*/
                color: #f4511e;
                font-size: 17px;
                margin: 1px 0 0 3px;
                position: absolute;
                pointer-events: none;
                font-family: 'Glyphicons Halflings';
            }
            #login .checkbox .character-checkbox {
                width: 25px;
                height: 25px;
                cursor: pointer;
                border-radius: 3px;
                border: 1px solid #ccc;
                vertical-align: middle;
                display: inline-block;
            }
            #login .checkbox .label {
                color: #6d6d6d;
                font-size: 13px;
                font-weight: normal;
            }
            #login .btn.btn-custom {
                font-size: 14px;
                margin-bottom: 20px;
            }
            #login .forget {
                font-size: 13px;
                text-align: center;
                display: block;
            }

            /*    --------------------------------------------------
                    :: Inputs & Buttons
                    -------------------------------------------------- */
            .form-control {
                color: #212121;
            }
            .btn-custom {
                color: #fff;
/*                background-color: #1fa67b;*/
                background-color: #f4511e;
            }
            .btn-custom:hover,
            .btn-custom:focus {
                color: #fff;
            }

            /*    --------------------------------------------------
                :: Footer
                    -------------------------------------------------- */
            #footer {
                color: #6d6d6d;
                font-size: 12px;
                text-align: center;
            }
            #footer p {
                margin-bottom: 0;
            }
            #footer a {
                color: inherit;
            }
        </style>

        <script type="text/javascript">
            function showPassword() {

                var key_attr = $('#key').attr('type');

                if (key_attr != 'text') {
                    $('.checkbox').addClass('show');
                    $('#key').attr('type', 'text');
                } else {
                    $('.checkbox').removeClass('show');
                    $('#key').attr('type', 'password');

                }
            }
        </script>
    </body>
</html>