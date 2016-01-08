<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>


<div class="form-group">
    <select class="form-control" name="type">
        <option value="Student">Student</option>
        <option value="Teacher">Nauczyciel</option>
    </select>
</div>
<div class="form-group">
    <input type="text" name="firstName" id="firstName" tabindex="1" class="form-control" placeholder="Imię" value="">
</div>
<div class="form-group">
    <input type="text" name="lastName" id="lastName" tabindex="1" class="form-control" placeholder="Nazwisko" value="">
</div>
<div class="form-group">
    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email" value="${request.getParameter("email")}">
</div>
<div class="form-group">
    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Hasło">
</div>
<div class="form-group">
    <input type="password" name="confirmPassword" id="confirmPassword" tabindex="2" class="form-control" placeholder="Potwierdź hasło">
</div>
<div class="form-group">
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Zarejestruj teraz">
        </div>
    </div>
</div>