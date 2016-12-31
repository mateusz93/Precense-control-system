package neo.dmcs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Mateusz Wieczorek on 31.12.2016.
 */
@Controller
public class WelcomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView startPage() {
        return new ModelAndView("index");
    }
}
