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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView logIn() {
        return new ModelAndView("security/login");
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("loginForm") LoginView form, HttpServletRequest request, HttpSession session) {
        ModelAndView mvc = new ModelAndView("index");

        try {
            if (!reCaptchaService.verify(request)) {
                mvc.addObject("message", "recaptcha.incorrect");
                mvc.addObject("messageType", MessageType.DANGER.name());
                mvc.setViewName("security/login");
                return mvc;
            }
            loginService.validate(form);
            User user = userRepository.findByEmail(form.getEmail());
            session.setAttribute("username", user.getLogin());
            session.setAttribute("userType", user.getType());
        } catch (FieldEmptyException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "emptyField");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectEmailException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "login.incorrectEmail");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectUserTypeException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "login.incorrectUserType");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (UserNotActivedException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "login.userNotActive");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectPasswordException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "register.incorrectPassword");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IOException e) {
            log.error(e.getMessage());
            mvc.addObject("message", "error");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
        }
        return mvc;
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        ModelAndView mvc = new ModelAndView("index");
        session.removeAttribute("username");
        session.removeAttribute("userType");
        session.invalidate();
        return mvc;
    }

}
