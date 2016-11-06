package neo.dmcs.controller;

import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isLogged;

/**
 * @Author Mateusz Wieczorek on 11/6/16.
 */
@Controller
@Transactional
@RequestMapping("/grades")
public class GradeController {

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView course(HttpSession session) {
        ModelAndView mvc = new ModelAndView("grade/studentGrades");
        User user = getUserFromSession(session);
        if (!isLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        return mvc;
    }
}
