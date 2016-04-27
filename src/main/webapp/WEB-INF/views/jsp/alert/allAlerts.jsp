<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.*,javax.servlet.http.* "%>
<%@ page import="java.text.NumberFormat,java.util.Date" %>
<!DOCTYPE HTML>

<% Locale locale = request.getLocale();
ResourceBundle resource = ResourceBundle.getBundle("strings_pl");

String message = (String) request.getAttribute("message");
String messageType = (String) request.getAttribute("messageType");

if (!"".equals(message) && message != null) {
    if (locale != null && "en".equalsIgnoreCase(locale.getLanguage())) {
        resource = ResourceBundle.getBundle("strings_en");
    }

    if ("success".equalsIgnoreCase(messageType)) { %>
        <div class="alert alert-success alert-dismissable">
            <a class="panel-close close" data-dismiss="alert">×</a>
            <i class="fa fa-coffee"></i>
            <% String messageText = resource.getString(message);
               out.print(new String(messageText.getBytes("ISO-8859-1"), "UTF-8")); %>
        </div>
    <% }
    if ("info".equalsIgnoreCase(messageType)) { %>
        <div class="alert alert-info alert-dismissable">
            <a class="panel-close close" data-dismiss="alert">×</a>
            <i class="fa fa-coffee"></i>
            <% String messageText = resource.getString(message);
               out.print(new String(messageText.getBytes("ISO-8859-1"), "UTF-8")); %>
        </div>

    <% }
    if ("warning".equalsIgnoreCase(messageType)) { %>
        <div class="alert alert-warning alert-dismissable">
            <a class="panel-close close" data-dismiss="alert">×</a>
            <i class="fa fa-coffee"></i>
            <% String messageText = resource.getString(message);
               out.print(new String(messageText.getBytes("ISO-8859-1"), "UTF-8")); %>
        </div>

    <% }
    if ("danger".equalsIgnoreCase(messageType)) { %>
        <div class="alert alert-danger alert-dismissable">
            <a class="panel-close close" data-dismiss="alert">×</a>
            <i class="fa fa-coffee"></i>
            <% String messageText = resource.getString(message);
               out.print(new String(messageText.getBytes("ISO-8859-1"), "UTF-8")); %>
        </div>

    <% }
}
%>
