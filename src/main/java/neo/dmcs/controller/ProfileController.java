package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.model.Notification;
import neo.dmcs.model.User;
import neo.dmcs.repository.NotificationRepository;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.service.ProfileService;
import neo.dmcs.view.user.ProfileGeneralView;
import neo.dmcs.view.user.ProfileNotificationView;
import neo.dmcs.view.user.ProfilePasswordView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView profile(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("user/profile");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        prepareProfileView(mvc, user);
        return mvc;
    }

    @RequestMapping(value = "/general", method = RequestMethod.POST)
    public ModelAndView profileGeneral(@ModelAttribute("profileForm") ProfileGeneralView form,
                                       HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("user/profile");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        try {
            profileService.update(form);
        } catch (FieldEmptyException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "emptyField");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        } catch (DifferentPasswordsException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "register.differentPasswords");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        } catch (IncorrectPasswordException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "register.incorrectPassword");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        }

        log.debug("Profile updated");
        mvc.addObject("message", "profile.updated");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        prepareProfileView(mvc, user);
        return mvc;
    }

    @RequestMapping(value = "/notification", method = RequestMethod.POST)
    public ModelAndView profileNotification(@ModelAttribute("profileForm") ProfileNotificationView form,
                                            HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("user/profile");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        saveNewNotificationSettings(user, form);
        mvc.addObject("message", "profile.updated");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        prepareProfileView(mvc, user);
        return mvc;
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ModelAndView profilePassword(@ModelAttribute("profileForm") ProfilePasswordView form,
                                        HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("user/profile");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }

        try {
            profileService.updatePassword(form, httpSession);
        } catch (FieldEmptyException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "emptyField");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        } catch (DifferentPasswordsException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "register.differentPasswords");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        } catch (IncorrectPasswordException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "register.incorrectPassword");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "error");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        }

        log.debug("Password updated");
        mvc.addObject("message", "profile.password.updated");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        prepareProfileView(mvc, user);
        return mvc;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file,
                                         HttpSession session) {
        ModelAndView mvc = new ModelAndView("user/profile");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }

        if (file.isEmpty()) {
            mvc.addObject("message", "profile.upload.empty");
            mvc.addObject("messageType", MessageType.DANGER.name());
        } else {
            try {
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/webapp/WEB-INF/resources/images/" + user.getLogin())));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                mvc.addObject("message", "profile.upload.success");
                mvc.addObject("messageType", MessageType.SUCCESS.name());
            } catch (Exception e) {
                mvc.addObject("message", "profile.upload.fail");
                mvc.addObject("messageType", MessageType.DANGER.name());
            }
        }

        return mvc;
    }

    private void saveNewNotificationSettings(User user, ProfileNotificationView form) {
        Notification notification = notificationRepository.findByUser(user);
        if (notification == null) {
            notification = new Notification();
            notification.setUser(user);
            notification.setCriticalPresenceLevel("");
            notification.setCourseCanceled("");
            notification.setAbsence("");
            notification.setBadMark("");
            notification.setChangeCourseDate("");
        }
        if (form.getCriticalPresenceLevelEmail() != null) {
            notification.setCriticalPresenceLevel("EMAIL ");
        } else {
            notification.setCriticalPresenceLevel("");
        }
        if (form.getCriticalPresenceLevelSMS() != null) {
            notification.setCriticalPresenceLevel(notification.getCriticalPresenceLevel() + "SMS");
        }
        if (form.getCourseCanceledEmail() != null) {
            notification.setCourseCanceled("EMAIL ");
        } else {
            notification.setCourseCanceled("");
        }
        if (form.getCourseCanceledSMS() != null) {
            notification.setCourseCanceled(notification.getCourseCanceled() + "SMS");
        }
        if (form.getAbsenceEmail() != null) {
            notification.setAbsence("EMAIL ");
        } else {
            notification.setAbsence("");
        }
        if (form.getAbsenceSMS() != null) {
            notification.setAbsence(notification.getAbsence() + "SMS");
        }
        if (form.getBadMarkEmail() != null) {
            notification.setBadMark("EMAIL ");
        } else {
            notification.setBadMark("");
        }
        if (form.getBadMarkSMS() != null) {
            notification.setBadMark(notification.getBadMark() + "SMS");
        }
        if (form.getChangeCourseDateEmail() != null) {
            notification.setChangeCourseDate("EMAIL ");
        } else {
            notification.setChangeCourseDate("");
        }
        if (form.getChangeCourseDateSMS() != null) {
            notification.setChangeCourseDate(notification.getChangeCourseDate() + "SMS");
        }
        notificationRepository.save(notification);
    }

    private void prepareProfileView(ModelAndView mvc, User user) {
        prepareGeneralData(mvc, user);
        prepareImage(mvc, user);
        prepareNotificationData(mvc, user);
    }

    private void prepareNotificationData(ModelAndView mvc, User user) {
        Notification notification = notificationRepository.findByUser(user);
        if (notification == null) {
            return;
        }
        if (notification.getCourseCanceled().contains("EMAIL")) {
            mvc.addObject("courseCanceledEmail", "on");
        }
        if (notification.getCourseCanceled().contains("SMS")) {
            mvc.addObject("courseCanceledSMS", "on");
        }
        if (notification.getAbsence().contains("EMAIL")) {
            mvc.addObject("absenceEmail", "on");
        }
        if (notification.getAbsence().contains("SMS")) {
            mvc.addObject("absenceSMS", "on");
        }
        if (notification.getBadMark().contains("EMAIL")) {
            mvc.addObject("badMarkEmail", "on");
        }
        if (notification.getBadMark().contains("SMS")) {
            mvc.addObject("badMarkSMS", "on");
        }
        if (notification.getChangeCourseDate().contains("EMAIL")) {
            mvc.addObject("changeCourseDateEmail", "on");
        }
        if (notification.getChangeCourseDate().contains("SMS")) {
            mvc.addObject("changeCourseDateSMS", "on");
        }
        if (notification.getCriticalPresenceLevel().contains("EMAIL")) {
            mvc.addObject("criticalPresenceLevelEmail", "on");
        }
        if (notification.getCriticalPresenceLevel().contains("SMS")) {
            mvc.addObject("criticalPresenceLevelSMS", "on");
        }
    }

    private void prepareImage(ModelAndView mvc, User user) {
        File f = new File("src/main/webapp/WEB-INF/resources/images/" + user.getLogin());
        if (f.exists() && !f.isDirectory()) {
            mvc.addObject("photoPath", "/resources/images/" + user.getLogin());
        } else {
            mvc.addObject("photoPath", "/resources/images/default.png");
        }
    }

    private User prepareGeneralData(ModelAndView mvc, User user) {
        mvc.addObject("firstName", user.getFirstName());
        mvc.addObject("lastName", user.getLastName());
        mvc.addObject("ID", user.getId());
        mvc.addObject("email", user.getEmail());
        mvc.addObject("type", user.getType());
        mvc.addObject("city", user.getCity());
        mvc.addObject("group", user.getGroup());
        mvc.addObject("phone", user.getPhone());
        mvc.addObject("street", user.getStreet());
        return user;
    }

}
