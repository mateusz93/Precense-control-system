<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="form-group">
    <select class="form-control" name="type" value="${type}">
        <option value="STUDENT"><spring:message code='view.student'/></option>
        <option value="TEACHER"><spring:message code='view.teacher'/></option>
        <option value="ADMIN"><spring:message code='view.admin'/></option>
    </select>
</div>
<div class="form-group">
    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="<spring:message code='view.profile.email'/>" value="${email}">
</div>
<div class="form-group">
    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="<spring:message code='view.profile.password'/>">
</div>
<div class="form-group">
    <div class="g-recaptcha" data-sitekey="6LfTixkUAAAAAAnoLauOxDDtGY5kV37xoRpNy6ew"></div>
</div>
<div class="form-group">
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="<spring:message code='view.login'/>">
        </div>
    </div>
</div>