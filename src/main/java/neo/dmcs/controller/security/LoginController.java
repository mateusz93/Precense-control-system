package neo.dmcs.controller.security;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.exception.*;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import neo.dmcs.service.LoginService;
import neo.dmcs.service.RegisterService;
import neo.dmcs.view.security.LoginView;
import neo.dmcs.view.security.RegisterView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Controller
@SessionAttributes("username")
@RequestMapping("/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("strings");

    @Autowired
    private RegisterService registerService;
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

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("loginForm") LoginView form, HttpSession session) {
        ModelAndView mvc = new ModelAndView("index");

        try {
            loginService.validate(form);
            Contact contact = contactDao.findByEmail(form.getEmail());
            User user = userDao.findByContact(contact);
            session.setAttribute("username", user.getLogin());
        } catch (IncorrectEmailException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "Niepoprawn");
            mvc.addObject("messageType", "danger");
            return mvc;
        } catch (IncorrectUserTypeException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (UserNotActivedException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (FieldEmptyException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (IncorrectPasswordException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        }
        mvc.addObject("message", "");
        return mvc;
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    public ModelAndView loginForm(@ModelAttribute("loginForm") LoginView form, HttpSession session) {
        ModelAndView mvc = new ModelAndView("index");
        try {
            loginService.validate(form);
            Contact contact = contactDao.findByEmail(form.getEmail());
            User user = userDao.findByContact(contact);
            session.setAttribute("username", user.getLogin());
        } catch (IncorrectEmailException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (IncorrectPasswordException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (IncorrectUserTypeException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (UserNotActivedException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (FieldEmptyException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        }
        mvc.addObject("message", "");
        return mvc;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute("registerForm") RegisterView form, Model model) {
        ModelAndView mvc = new ModelAndView("security/register");
        try {
            registerService.accept(form);
        } catch (FieldEmptyException e) {
            logger.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (DifferentPasswordsException e) {
            logger.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (IncorrectPasswordException e) {
            logger.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        } catch (EmailExistsException e) {
            logger.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", "EEEEEEEEEEE");
            return mvc;
        }
        logger.debug("Form accepted");
        cleanFields(form, mvc);
        mvc.addObject("message", resourceBundle.getString("Konto poprawnie utworzone!"));
        return mvc;
    }

    @RequestMapping(value = "/logOut", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        ModelAndView mvc = new ModelAndView();
        session.setAttribute("username", "");
        mvc.setViewName("index");
        return mvc;
    }


    private void setFields(RegisterView form, ModelAndView mvc) {
        mvc.addObject("newEmail", form.getEmail());
        mvc.addObject("firstName", form.getFirstName());
        mvc.addObject("lastName", form.getLastName());
        mvc.addObject("password", form.getPassword());
        mvc.addObject("confirmPassword", form.getConfirmPassword());
        mvc.addObject("type", form.getType());
    }

    private void cleanFields(RegisterView form, ModelAndView mvc) {
        mvc.addObject("newEmail", "");
        mvc.addObject("firstName", "");
        mvc.addObject("lastName", "");
        mvc.addObject("password", "");
        mvc.addObject("confirmPassword", "");
        mvc.addObject("type", form.getType());
    }
}
