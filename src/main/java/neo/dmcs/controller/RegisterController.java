package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.bpm.BpmProcessService;
import neo.dmcs.service.RegisterService;
import neo.dmcs.view.security.RegisterView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * @author Mateusz Wieczorek, 08.04.16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/register")
public class RegisterController {

    /* Obsluga błedów */
    // http://www.mkyong.com/spring-mvc/spring-mvc-form-check-if-a-field-has-an-error/

    private final BpmProcessService bpmProcessService;
    private static final String MVC_DEFAULT = "security/register";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView(MVC_DEFAULT);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("newEmail") String email) {
        ModelAndView mvc = new ModelAndView(MVC_DEFAULT);
        mvc.addObject("newEmail", email);
        return mvc;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute("registerForm") RegisterView form, Locale locale) {
        return bpmProcessService.startProcess(form, locale);
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(WebRequest request,
                                            @RequestParam("token") String token,
                                            @RequestParam("processInstanceId") String processInstanceId) {

        return bpmProcessService.confirmRegistration(request.getLocale(), token, processInstanceId);
    }

}
