<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    session.setAttribute("username", null);
    session.invalidate();
    response.sendRedirect("index.htm");
%>
