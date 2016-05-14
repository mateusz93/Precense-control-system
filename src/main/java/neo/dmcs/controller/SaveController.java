package neo.dmcs.controller;

import neo.dmcs.dao.UserDao;
import neo.dmcs.model.User;
import neo.dmcs.service.SaveService;
import neo.dmcs.view.course.SaveView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
@Controller
@RequestMapping("/saves")
public class SaveController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SaveService saveService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView save(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/studentSaves");

        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userDao.findByUsername(username);
        prepareView(mvc, user);
        return mvc;
    }

    private void prepareView(ModelAndView mvc, User user) {
        List<SaveView> subjectList =  saveService.getSubjects(user);
        mvc.setViewName("course/studentSaves");
        mvc.addObject("subjectList", subjectList);
    }

    private boolean isLogged(String username) {
        return StringUtils.isNotBlank(username);
    }
}
