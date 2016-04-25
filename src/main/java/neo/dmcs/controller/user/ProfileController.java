package neo.dmcs.controller.user;

import neo.dmcs.dao.UserDao;
import neo.dmcs.service.ProfileService;
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

import java.util.ResourceBundle;

/**
 * @Author Mateusz Wieczorek
 */
@Controller
@SessionAttributes("username")
@RequestMapping("/profile")
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("strings");

    @Autowired
    private ProfileService registerService;

    @Autowired
    private UserDao userDao;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView profile(Model model) {
        ModelAndView mvc = new ModelAndView("user/profile");
        userDao.

        return mvc;
    }
}
