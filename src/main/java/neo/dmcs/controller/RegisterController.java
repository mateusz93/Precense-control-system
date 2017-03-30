package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.EmailExistsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.service.LoginService;
import neo.dmcs.service.RegisterService;
import neo.dmcs.view.security.RegisterView;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * @Author Mateusz Wieczorek, 08.04.16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@SessionAttributes("username")
@RequestMapping("/register")
public class RegisterController {

    /* Obsluga błedów */
    // http://www.mkyong.com/spring-mvc/spring-mvc-form-check-if-a-field-has-an-error/

    private final RegisterService registerService;
    private final LoginService loginService;
    private final MessageSource messageSource;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("newEmail") String email) {
        ModelAndView mvc = new ModelAndView("security/register");
        mvc.addObject("newEmail", email);
        return mvc;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute("registerForm") RegisterView form, Locale locale) {
        ModelAndView mvc = new ModelAndView("security/register");
        try {
            registerService.accept(form);
        } catch (FieldEmptyException e) {
            log.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", messageSource.getMessage("emptyField", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        } catch (DifferentPasswordsException e) {
            log.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", messageSource.getMessage("register.differentPasswords", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        } catch (IncorrectPasswordException e) {
            log.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", messageSource.getMessage("register.incorrectPassword", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        } catch (EmailExistsException e) {
            log.error(e.getMessage());
            setFields(form, mvc);
            mvc.addObject("message", messageSource.getMessage("register.emailUsed", null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        }

        log.debug("Form accepted");
        cleanFields(form, mvc);
        mvc.addObject("message", messageSource.getMessage("register.userCreated", null, locale));
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
