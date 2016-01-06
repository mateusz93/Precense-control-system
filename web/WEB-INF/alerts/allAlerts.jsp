<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<% String message = (String) request.getAttribute("message"); %> 

<% if ("Błędny email lub hasło!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>

<%}
    if ("Nie podałeś hasła!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>

<%}
    if ("Pole nie może być puste!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
  
<%}
    if ("Niepoprawny format!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
    
<%}
    if ("Niepoprawny typ! Dozwolone: Teacher, Student".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>

<%}
    if ("Na wybranym wydziale nie znaleziono przedmiotu o podanej nazwie!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
    
<%}
    if ("Hasła są różne!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>

<%}
    if ("Zaaktualizowano profil!".equals(message)) { %> 
<div class="alert alert-success alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
    
<%}
    if ("Konto poprawnie utworzone!".equals(message)) { %> 
<div class="alert alert-success alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
    
<%}
    if ("Niepoprawne hasło!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
    
<%}
    if ("Pole może zawierać tylko cyfry!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>

<%}
    if ("Zapisano na kurs".equals(message)) { %> 
<div class="alert alert-success alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
   
<%}
    if ("Dodano nowy kurs".equals(message)) { %> 
<div class="alert alert-success alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>  
    
<%}
    if ("Wypisano z kursu".equals(message)) { %> 
<div class="alert alert-info alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>

<%}
    if ("Dodano nowy termin.".equals(message)) { %> 
<div class="alert alert-success alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
    
<%}
    if ("Wystąpił błąd podczas dodawania nowego terminu!".equals(message)) { %> 
<div class="alert alert-danger alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
<%}
    if ("Zaaktualizowano listę obecności".equals(message)) { %> 
<div class="alert alert-info alert-dismissable">
    <a class="panel-close close" data-dismiss="alert">×</a> 
    <i class="fa fa-coffee"></i>
    <%
        out.print(message);
    %> 
</div>
<%}%> 