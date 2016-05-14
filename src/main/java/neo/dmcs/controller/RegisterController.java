package neo.dmcs.controller;

import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.EmailExistsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.service.LoginService;
import neo.dmcs.service.RegisterService;
import neo.dmcs.view.security.RegisterView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Mateusz Wieczorek, 08.04.16.
 */
@Controller
@SessionAttributes("username")
@RequestMapping("/register")
public class RegisterController {

    /* Obsluga błedów */
    // http://www.mkyong.com/spring-mvc/spring-mvc-form-check-if-a-field-has-an-error/

    private final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;


    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("newEmail") String email) {
        ModelAndView mvc = new ModelAndView("security/register");
        mvc.addObject("newEmail", email);
        return mvc;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute("registerForm") RegisterView form) {
        ModelAndView mvc = new ModelAndView("security/register");
        try {
            registerService.accept(form);
        } catch (FieldEmptyException e) {
            logger.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", "emptyField");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        } catch (DifferentPasswordsException e) {
            logger.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", "register.differentPasswords");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        } catch (IncorrectPasswordException e) {
            logger.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", "register.incorrectPassword");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        } catch (EmailExistsException e) {
            logger.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", "register.emailUsed");
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        }

        logger.debug("Form accepted");
        cleanFields(form, mvc);
        mvc.addObject("message", "register.userCreated");
        mvc.addObject("messageType", MessageType.SUCCESS.name());

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
