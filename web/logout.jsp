<%-- 
    Document   : logout
    Created on : Nov 15, 2015, 11:36:14 AM
    Author     : mateusz
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
session.setAttribute("username", null);
session.invalidate();
response.sendRedirect("index.htm");
%>
