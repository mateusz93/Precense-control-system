package neo.dmcs.controller;

import neo.dmcs.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Mateusz Wieczorek on 10/18/16.
 */
@Controller
@RequestMapping("/contact")
public class ContactController {

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView info(@RequestParam("email") String email,
                             @RequestParam("subject") String subject,
                             @RequestParam("content") String content) {
        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("index");
        logger.info("Email: " + email);
        logger.info("Subject: " + subject);
        logger.info("Content: " + content);
        contactService.sendEmail(email, subject, content);
        return mvc;
    }
}
