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
    <input type="text" name="firstName" id="firstName" tabindex="1" class="form-control" placeholder="<spring:message code='view.profile.firstname'/>" value="${firstName}">
</div>
<div class="form-group">
    <input type="text" name="lastName" id="lastName" tabindex="1" class="form-control" placeholder="<spring:message code='view.profile.lastname'/>" value="${lastName}">
</div>
<div class="form-group">
    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="<spring:message code='view.profile.email'/>" value="${newEmail}">
</div>
<div class="form-group">
    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="<spring:message code='view.profile.password'/>" value="${password}">
</div>
<div class="form-group">
    <input type="password" name="confirmPassword" id="confirmPassword" tabindex="2" class="form-control" placeholder="<spring:message code='view.profile.password.confirme'/>" value="${confirmPassword}">
</div>
<div class="form-group">
    <div class="g-recaptcha" data-sitekey="6LfTixkUAAAAAAnoLauOxDDtGY5kV37xoRpNy6ew"></div>
</div>
<div class="form-group">
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="<spring:message code='view.index.register.now'/>">
        </div>
    </div>
</div>