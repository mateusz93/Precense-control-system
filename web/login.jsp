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
            <form action="loginServlet" method="post">
            <!--                    <fieldset style="width: 300px">
                                        <legend> Login to App </legend>
                                        <table>
                                                <tr>
                                                        <td>User ID</td>
                                                        <td><input type="text" name="username" required="required" /></td>
                                                    </tr>
                                                <tr>
                                                        <td>Password</td>
                                                        <td><input type="password" name="userpass" required="required" /></td>
                                                    </tr>
                                                <tr>
                                                        <td><input type="submit" value="Login" /></td>
                                                    </tr>
                                            </table>
                                    </fieldset>-->
            <section id="login">
                <div class="container">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="form-wrap">
                                <h1>Log in to your account</h1>
                                <form role="form" action="javascript:;" method="post" id="login-form" autocomplete="off">
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


<!--        <div class="modal fade forget-modal" tabindex="-1" role="dialog" aria-labelledby="myForgetModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">×</span>
                            <span class="sr-only">Close</span>
                        </button>
                        <h4 class="modal-title">Recovery password</h4>
                    </div>
                    <div class="modal-body">
                        <p>Type your email account</p>
                        <input type="email" name="recovery-email" id="recovery-email" class="form-control" autocomplete="off">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-custom">Recovery</button>
                    </div>
                </div> 
            </div> 
        </div> -->

<!--        <footer id="footer">
            <div class="container">
                <div class="row">
                    <div class="col-xs-12">
                        <p>Page © - 2015</p>
                        <p>Powered by <strong><a target="_blank">Mati</a></strong></p>
                    </div>
                </div>
            </div>
        </footer>-->

        <style type="text/css">
            /*    --------------------------------------------------
                    :: Login Section
                    -------------------------------------------------- */
            #login {
                padding-top: 50px
            }
            #login .form-wrap {
                width: 50%;
                margin: 0 auto;
            }
            #login h1 {
                color: #1fa67b;
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
                color: #1fa67b;
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
                background-color: #1fa67b;
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