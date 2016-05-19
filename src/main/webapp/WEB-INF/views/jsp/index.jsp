<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
    <head>
        <title>System kontroli obecności</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="/resources/css/main.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>

    <body>
        <jsp:include page="menu.jsp"/>

        <div class="jumbotron text-center">
            <h1>System kontroli obecności</h1> 
            <p>Sprawdzaj obecność codziennie w łatwy sposób</p>
            <% if ("".equals(session.getAttribute("username")) || session.getAttribute("username") == null) { %>
                <form class="form-inline" action="register" method="post">
                    <input type="email" class="form-control" name="newEmail" size="50" placeholder="Email" required>
                    <button type="submit" class="btn btn-danger">Zarejestruj</button>
                </form>
            <% } %>
        </div>

        <div id="googleMap" style="height:400px;width:100%;"></div>

        <!-- Add Google Maps -->
        <script src="http://maps.googleapis.com/maps/api/js"></script>
        <script>
        var myCenter = new google.maps.LatLng(51.746066, 19.455357);

        function initialize() {
            var mapProp = {
                center:myCenter,
                zoom:14,
                scrollwheel:false,
                draggable:false,
                mapTypeId:google.maps.MapTypeId.ROADMAP
            };

            var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);

            var marker = new google.maps.Marker({
                position:myCenter,
            });

            marker.setMap(map);
        }

        google.maps.event.addDomListener(window, 'load', initialize);
        </script>
    </body>
</html>
