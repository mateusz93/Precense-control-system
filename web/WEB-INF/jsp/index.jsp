<%@page import="java.sql.DriverManager"%>

<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<html>
    <head>
        <title>Welcome to Spring Web MVC project</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <body>
        <div class="container">
            <h2></h2>

            <div class="alert alert-success">
                <strong>Hurra!</strong> Udało Ci się uruchomić aplikację webową. Wejdź na http://www.w3schools.com/bootstrap/
                aby uzyskać więcej informacji o BootStrapie
            </div>

        </div>
       

        <table border="0">
            <thead>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <form action="response.jsp">
                            <strong>Kliknij obok aby zobaczyć co potrafi BootStrap </strong>
                            
                            
                            <input type="submit" value="Tutaj :)" name="submit" />
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
        
         <table border="0">
            <thead>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <form action="login.jsp">
                            <strong>Przejdz na strone logowania </strong>
                            
                            
                            <input type="submit" value="Log in" name="submit" />
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
        
<!--        
        <%
            java.sql.Connection conn = null;
            String url = "jdbc:mysql://localhost:3306/data";
            String Driver = "com.mysql.jdbc.Driver";
            String username="root";
            String password="root";
            try {
                Class.forName(Driver);
                conn = DriverManager.getConnection(url, username, password);
                if(conn!=null) {
                 out.println("Connected to database");   
                } else {
                    out.println("Not connected to database");
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            
        %>
        -->
    </body>
</html>
