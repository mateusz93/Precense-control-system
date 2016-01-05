<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
    <head>
        <title>System kontroli obecności</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <style type="text/css">
        <%@ include file="../css/main.css" %>
    </style>

    <body>
        <jsp:include page="menu.jsp"/>

        <div class="jumbotron text-center">
            <h1>System kontroli obecności</h1> 
            <p>Sprawdzaj obecność codziennie w łatwy sposób</p> 
            <form class="form-inline" action="registerServlet" method="post">           
                <input type="email" class="form-control" name="newEmail" size="50" placeholder="Email" required>
                <button type="submit" class="btn btn-danger">Zarejestruj</button>
            </form>
        </div>
    </body>
</html>
