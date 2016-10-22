<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
   <head>
      <title>Profil</title>
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
      <link rel="stylesheet" href="/resources/css/profile.css"/>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
      <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
   </head>
   <body>
      <jsp:include page="../menu.jsp"/>
      <div class="container">
         <br><br>
         <jsp:include page="../alert/allAlerts.jsp"/>
         <hr>
         <div class="row">
            <!-- left column -->
            <div class="col-md-3">
               <div class="text-center">
                  <img src="${photoPath}" />
                  <form method="POST" enctype="multipart/form-data" action="/profile/upload">
                     <input class="form-control" type="file" name="file" />
                     <input type="submit" value="Upload" />
                  </form>
               </div>
            </div>
            <!-- edit form column -->
            <div class="col-md-9 personal-info">
               <ul class="nav nav-pills">
                  <li class="active"><a href="#tab1" data-toggle="tab">Ogólne</a></li>
                  <li><a href="#tab2" data-toggle="tab">Powiadomienia</a></li>
                  <li><a href="#tab3" data-toggle="tab">Hasło</a></li>
               </ul>
               <div class="tab-content">
                  <div class="tab-pane fade in active" id="tab1">
                     <h3>Dane osobiste</h3>
                     <form class="form-horizontal" id="profile-general-form" action="/profile/general" commandName="profileForm" method="post" role="form">
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Imię:</label>
                           <div class="col-lg-8">
                              <input class="form-control" value="${firstName}" name="firstName" type="text">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Nazwisko:</label>
                           <div class="col-lg-8">
                              <input class="form-control" value="${lastName}" name="lastName" type="text">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Index:</label>
                           <div class="col-lg-8">
                              <input class="form-control" readonly="readonly" value="${ID}" name="ID" type="text">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Email:</label>
                           <div class="col-lg-8">
                              <input class="form-control" readonly="readonly" value="${email}" name="email" type="text">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Typ:</label>
                           <div class="col-lg-8">
                              <input class="form-control" readonly="readonly" value="${type}" name="type" type="text">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Telefon:</label>
                           <div class="col-lg-8">
                              <input class="form-control" value="${phone}" name="phone" type="text">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-md-3 control-label"></label>
                           <div class="col-md-8">
                              <input class="btn btn-success" type="submit" value="Zapisz zmiany">
                           </div>
                        </div>
                     </form>
                  </div>
                  <div class="tab-pane fade" id="tab2">
                     <h3>System powiadomień</h3>
                     <form class="form-horizontal" id="profile-notification-form" action="/profile/notification" commandName="profileForm" method="post" role="form">
                        <div class="form-group">
                           <label class="col-lg-4 control-label"></label>
                           <div class="col-lg-8">
                              <label class="col-lg-3 control-label">EMail</label>
                              <label class="col-lg-3 control-label">SMS</label>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-4 control-label">Odwołane zajęcia:</label>
                           <div class="col-lg-8">
                              <label class="col-lg-3 control-label">
                              <input name="courseCanceledEmail" ${'on' == courseCanceledEmail ? 'checked' : ''} type="checkbox">
                              </label>
                              <label class="col-lg-3 control-label">
                              <input name="courseCanceledSMS" ${'on' == courseCanceledSMS ? 'checked' : ''} type="checkbox">
                              </label>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-4 control-label">Zmiana terminu zajęć:</label>
                           <div class="col-lg-8">
                              <label class="col-lg-3 control-label">
                              <input name="changeCourseDateEmail" ${'on' == changeCourseDateEmail ? 'checked' : ''} type="checkbox">
                              </label>
                              <label class="col-lg-3 control-label">
                              <input name="changeCourseDateSMS" ${'on' == changeCourseDateSMS ? 'checked' : ''} type="checkbox">
                              </label>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-4 control-label">Nieobecność na zajęciach:</label>
                           <div class="col-lg-8">
                              <label class="col-lg-3 control-label">
                              <input name="absenceEmail" ${'on' == absenceEmail ? 'checked' : ''} type="checkbox">
                              </label>
                              <label class="col-lg-3 control-label">
                              <input name="absenceSMS" ${'on' == absenceSMS ? 'checked' : ''} type="checkbox">
                              </label>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-4 control-label">Krytyczny poziom obecności:</label>
                           <div class="col-lg-8">
                              <label class="col-lg-3 control-label">
                              <input name="criticalPresenceLevelEmail" ${'on' == criticalPresenceLevelEmail ? 'checked' : ''} type="checkbox">
                              </label>
                              <label class="col-lg-3 control-label">
                              <input name="criticalPresenceLevelSMS" ${'on' == criticalPresenceLevelSMS ? 'checked' : ''} type="checkbox">
                              </label>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-4 control-label">Ocena niedostateczna:</label>
                           <div class="col-lg-8">
                              <label class="col-lg-3 control-label">
                              <input name="badMarkEmail" ${'on' == badMarkEmail ? 'checked' : ''} type="checkbox">
                              </label>
                              <label class="col-lg-3 control-label">
                              <input name="badMarkSMS" ${'on' == badMarkSMS ? 'checked' : ''} type="checkbox">
                              </label>
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-md-3 control-label"></label>
                           <div class="col-md-8">
                              <input class="btn btn-success" type="submit" value="Zapisz zmiany">
                           </div>
                        </div>
                     </form>
                  </div>
                  <div class="tab-pane fade" id="tab3">
                     <h3>Zmiana hasła</h3>
                     <form class="form-horizontal" id="profile-notification-form" action="/profile/password" commandName="profileForm" method="post" role="form">
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Aktualne hasło:</label>
                           <div class="col-lg-8">
                              <input class="form-control" type="password" name="password">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Nowe hasło:</label>
                           <div class="col-lg-8">
                              <input class="form-control" type="password" name="newPassword">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-lg-3 control-label">Powtórz nowe hasło:</label>
                           <div class="col-lg-8">
                              <input class="form-control" type="password" name="againNewPassword">
                           </div>
                        </div>
                        <div class="form-group">
                           <label class="col-md-3 control-label"></label>
                           <div class="col-md-8">
                              <input class="btn btn-success" type="submit" value="Zapisz zmiany">
                           </div>
                        </div>
                     </form>
                  </div>
               </div>
            </div>
         </div>
      </div>
      <hr>
   </body>
</html>