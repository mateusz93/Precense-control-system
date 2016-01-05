<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    session.setAttribute("username", null);
    session.invalidate();
    response.sendRedirect("index.htm");
%>
