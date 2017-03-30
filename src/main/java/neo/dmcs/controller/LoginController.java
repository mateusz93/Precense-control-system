package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.*;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.service.LoginService;
import neo.dmcs.service.ReCaptchaService;
import neo.dmcs.view.security.LoginView;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final ReCaptchaService reCaptchaService;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView logIn() {
        return new ModelAndView("security/login");
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("loginForm") LoginView form, HttpServletRequest request, HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView("redirect:/");

        try {
            if (!reCaptchaService.verify(request)) {
                mvc.addObject("message", messageSource.getMessage("recaptcha.incorrect", null, locale));
                mvc.addObject("messageType", MessageType.DANGER.name());
                mvc.setViewName("security/login");
                return mvc;
            }
            loginService.validate(form);
            User user = userRepository.findByEmail(form.getEmail());
            session.setAttribute("username", user.getLogin());
            session.setAttribute("userType", user.getType().name());
        } catch (FieldEmptyException e) {
            log.error(e.getMessage());
            mvc.addObject("message", messageSource.getMessage("emptyField", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectEmailException e) {
            log.error(e.getMessage());
            mvc.addObject("message", messageSource.getMessage("login.incorrectEmail", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectUserTypeException e) {
            log.error(e.getMessage());
            mvc.addObject("message", messageSource.getMessage("login.incorrectUserType", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (UserNotActivedException e) {
            log.error(e.getMessage());
            mvc.addObject("message", messageSource.getMessage("login.userNotActive", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectPasswordException e) {
            log.error(e.getMessage());
            mvc.addObject("message", messageSource.getMessage("register.incorrectPassword", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IOException e) {
            log.error(e.getMessage());
            mvc.addObject("message", messageSource.getMessage("error", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
        }
        return mvc;
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        ModelAndView mvc = new ModelAndView("redirect:/");
        session.removeAttribute("username");
        session.removeAttribute("userType");
        session.invalidate();
        return mvc;
    }

}
