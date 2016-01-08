<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<div class="form-group">
    <select class="form-control" name="type">
        <option value="Student">Student</option>
        <option value="Teacher">Nauczyciel</option>
    </select>
</div>
<div class="form-group">
    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email" value="">
</div>
<div class="form-group">
    <input type="password" name="userpass" id="password" tabindex="2" class="form-control" placeholder="Hasło">
</div>
<div class="form-group">
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Zaloguj">
        </div>
    </div>
</div>