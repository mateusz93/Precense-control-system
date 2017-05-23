package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.*;
import neo.dmcs.model.Notification;
import neo.dmcs.model.User;
import neo.dmcs.repository.NotificationRepository;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.util.Encryptor;
import neo.dmcs.util.PasswordValidator;
import neo.dmcs.view.user.ProfileGeneralView;
import neo.dmcs.view.user.ProfileNotificationView;
import neo.dmcs.view.user.ProfilePasswordView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Mateusz Wieczorek, 21.04.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProfileService {

    private final NotificationRepository notificationRepository;
    private final MessageSource messageSource;
    private final UserRepository userRepository;

    public void update(ProfileGeneralView form) throws ValidationException {
        form.setFirstName(form.getFirstName().trim());
        form.setLastName(form.getLastName().trim());

        if (!areFieldsNotEmpty(form)) {
            throw new FieldEmptyException("emptyField");
        }
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new DifferentPasswordsException("register.differentPasswords");
        }

        if (!PasswordValidator.validate(form.getPassword())) {
            throw new IncorrectPasswordException("register.incorrectPassword");
        }

        User user = getUpdatedUser(form);
        userRepository.save(user);
    }

    private User getUpdatedUser(ProfileGeneralView form) {
        User user = userRepository.findByEmail(form.getEmail());
        user.setGroup(form.getGroup());
        user.setPhone(form.getPhone());
        user.setCity(form.getCity());
        user.setStreet(form.getStreet());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        return userRepository.save(user);
    }

    private boolean areFieldsNotEmpty(ProfileGeneralView form) {
        return StringUtils.isNotBlank(form.getFirstName()) && StringUtils.isNotBlank(form.getLastName());
    }

    private boolean arePasswordFieldsNotEmpty(ProfilePasswordView form) {
        return StringUtils.isNotBlank(form.getPassword()) && StringUtils.isNotBlank(form.getNewPassword())
                && StringUtils.isNotBlank(form.getAgainNewPassword());
    }

    public ModelAndView uploadFile(MultipartFile file, User user, ModelAndView mvc, Locale locale) {
        try {
            uploadPicture(file, user);
        } catch (ValidationException e) {
            mvc.addObject("message", messageSource.getMessage(e.getMessage(), null, locale));
            mvc.addObject("messageType", MessageType.SUCCESS.name());
            return mvc;
        }
        mvc.addObject("message", messageSource.getMessage("profile.upload.success", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }

    private void uploadPicture(MultipartFile file, User user) throws ValidationException {
        if (file.isEmpty()) {
            throw new FileEmptyException("profile.upload.empty");
        }
        try {
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(new File("src/main/webapp/WEB-INF/resources/images/" + user.getLogin())));
            FileCopyUtils.copy(file.getInputStream(), stream);
            stream.close();
        } catch (IOException e) {
            throw new UploadFileException("profile.upload.fail");
        }
    }

    public ModelAndView updatePassword(ProfilePasswordView form, User user, ModelAndView mvc, Locale locale) {
        try {
            updatePassword(form, user);
        } catch (ValidationException e) {
            mvc.addObject("message", messageSource.getMessage(e.getMessage(), null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        }
        log.debug("Password updated");
        mvc.addObject("message", messageSource.getMessage("profile.password.updated", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return prepareGeneralProfileView(mvc, user);
    }

    private void updatePassword(ProfilePasswordView form, User user) throws ValidationException {
        if (!arePasswordFieldsNotEmpty(form)) {
            throw new FieldEmptyException("emptyField");
        }

        if (!form.getNewPassword().equals(form.getAgainNewPassword())) {
            throw new DifferentPasswordsException("register.differentPasswords");
        }

        if (!PasswordValidator.validate(form.getNewPassword())) {
            throw new IncorrectPasswordException("register.incorrectPassword");
        }
        user.setPassword(Encryptor.encryption(form.getNewPassword()));
        userRepository.save(user);
    }

    private void updateNotificationSettings(User user, ProfileNotificationView form) {
        Notification notification = notificationRepository.findByUser(user);
        if (notification == null) {
            notification = getDefaultNotification(user);
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

    private Notification getDefaultNotification(User user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setCriticalPresenceLevel("");
        notification.setCourseCanceled("");
        notification.setAbsence("");
        notification.setBadMark("");
        notification.setChangeCourseDate("");
        return notification;
    }

    public ModelAndView prepareGeneralProfileView(ProfileGeneralView form, ModelAndView mvc, User user, Locale locale) {
        try {
            update(form);
        } catch (ValidationException e) {
            mvc.addObject("message", messageSource.getMessage(e.getMessage(), null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        }
        log.debug("Profile updated");
        mvc.addObject("message", messageSource.getMessage("profile.updated", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return prepareGeneralProfileView(mvc, user);
    }

    public ModelAndView prepareGeneralProfileView(ModelAndView mvc, User user) {
        prepareGeneralData(mvc, user);
        prepareImage(mvc, user);
        return prepareNotificationData(mvc, user);
    }

    private ModelAndView prepareNotificationData(ModelAndView mvc, User user) {
        Notification notification = notificationRepository.findByUser(user);
        if (notification == null) {
            return mvc;
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
        return mvc;
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

    public ModelAndView prepareNotificationProfileView(ProfileNotificationView form, ModelAndView mvc, User user, Locale locale) {
        updateNotificationSettings(user, form);
        mvc.addObject("message", messageSource.getMessage("profile.updated", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return prepareGeneralProfileView(mvc, user);
    }
}
