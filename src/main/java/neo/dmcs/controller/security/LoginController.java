package neo.dmcs.controller.security;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.*;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import neo.dmcs.service.LoginService;
import neo.dmcs.view.security.LoginView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ContactDao contactDao;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView logIn() {
        return new ModelAndView("security/login");
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("loginForm") LoginView form, HttpSession session) {
        ModelAndView mvc = new ModelAndView("index");

        try {
            loginService.validate(form);
            Contact contact = contactDao.findByEmail(form.getEmail());
            User user = userDao.findByContact(contact);
            session.setAttribute("username", user.getLogin());
            session.setAttribute("userType", user.getType());
        } catch (FieldEmptyException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "emptyField");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectEmailException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "login.incorrectEmail");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectUserTypeException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "login.incorrectUserType");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (UserNotActivedException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "login.userNotActive");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
        } catch (IncorrectPasswordException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "register.incorrectPassword");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/login");
            return mvc;
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
