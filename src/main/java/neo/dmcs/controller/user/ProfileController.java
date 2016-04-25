package neo.dmcs.controller.user;

import neo.dmcs.dao.UserDao;
import neo.dmcs.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;

/**
 * @Author Mateusz Wieczorek
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserDao userDao;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView profile(HttpSession httpSession, Model model) {
        ModelAndView mvc = new ModelAndView("user/profile");
        String username = (String) httpSession.getAttribute("username");
        if (!StringUtils.isNotBlank(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }

        prepareProfileView(mvc, username);

        return mvc;
    }

    private void prepareProfileView(ModelAndView mvc, String username) {
        User user = userDao.findByUsername(username);
        mvc.addObject("firstName", user.getFirstName());
        mvc.addObject("lastName", user.getLastName());
        mvc.addObject("ID", user.getId());
        mvc.addObject("email", user.getContact().getEmail());
        mvc.addObject("type", user.getType());
        mvc.addObject("city", user.getContact().getCity());
        mvc.addObject("group", user.getContact().getGroup());
        mvc.addObject("phone", user.getContact().getPhone());
        mvc.addObject("street", user.getContact().getStreet());
    }
}
