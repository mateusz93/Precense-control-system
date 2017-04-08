package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Mateusz Wieczorek on 10/18/16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView info(@RequestParam("email") String email,
                             @RequestParam("subject") String subject,
                             @RequestParam("content") String content) {
        ModelAndView mvc = new ModelAndView("index");
        contactService.sendEmail(email, subject, content);
        return mvc;
    }
}
