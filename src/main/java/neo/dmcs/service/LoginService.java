package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserStatus;
import neo.dmcs.exception.*;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.util.Encryptor;
import neo.dmcs.view.security.LoginView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

/**
 * @author Mateusz Wieczorek, 30.03.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final CaptchaService captchaService;

    public void validate(LoginView form) throws ValidationException {
        if (StringUtils.isBlank(form.getPassword()) || StringUtils.isBlank(form.getType()) || StringUtils.isBlank(form.getEmail())) {
            throw new FieldEmptyException("emptyField");
        }

        User user = userRepository.findByEmail(form.getEmail());
        if (user == null) {
            throw new IncorrectEmailException("login.incorrectEmail");
        }
        if (!Encryptor.encryption(form.getPassword()).equals(user.getPassword())) {
            throw new IncorrectPasswordException("register.incorrectPassword");
        }
        if (!user.getType().name().equalsIgnoreCase(form.getType())) {
            throw new IncorrectUserTypeException("login.incorrectUserType");
        }
        if (user.getStatus().equalsIgnoreCase(UserStatus.INACTIVE.name())) {
            throw new UserNotActivatedException("login.userNotActive");
        }

        user.setLastLogin(new Timestamp((new Date()).getTime()));
        userRepository.save(user);
    }

    public ModelAndView logout(HttpSession session) {
        ModelAndView mvc = new ModelAndView("redirect:/");
        session.removeAttribute("username");
        session.removeAttribute("userType");
        session.invalidate();
        return mvc;
    }

    public ModelAndView login(LoginView form, HttpServletRequest request, HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView("redirect:/");
        try {
            captchaService.verify(request);
            validate(form);
            User user = userRepository.findByEmail(form.getEmail());
            session.setAttribute("username", user.getLogin());
            session.setAttribute("userType", user.getType().name());
        } catch (ValidationException e) {
            mvc.addObject("message", messageSource.getMessage(e.getMessage(), null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IOException e) {
            log.error("Problem with captcha. ", e);
            mvc.addObject("message", messageSource.getMessage("error", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
        }
        return mvc;
    }
}
