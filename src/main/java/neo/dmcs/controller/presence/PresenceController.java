package neo.dmcs.controller.presence;

import neo.dmcs.dao.CustomDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.User;
import neo.dmcs.service.PrecenseService;
import neo.dmcs.view.precense.StudentPrecensesView;
import neo.dmcs.view.precense.TeacherPrecensesView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by mateusz on 25.03.16.
 */
@Controller
@RequestMapping("/precenses")
public class PresenceController {

    private final Logger logger = LoggerFactory.getLogger(PresenceController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private CustomDao customDao;

    @Autowired
    private PrecenseService precenseService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView precense(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        String username = (String) httpSession.getAttribute("username");
        if (!StringUtils.isNotBlank(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userDao.findByUsername(username);
        prepareView(mvc, user);
        return mvc;
    }

    private void prepareView(ModelAndView mvc, User user) {
        if (UserType.Student.name().equals(user.getType())) {
            prepareStudentView(mvc, user);
        } else {
            prepareTeacherView(mvc, user);
        }
    }

    private void prepareStudentView(ModelAndView mvc, User user) {
        List<StudentPrecensesView> precensesList = customDao.findStudentPrecensesByUserId(user.getId());
        mvc.setViewName("precense/studentPrecenses");
        mvc.addObject("coursesList", precensesList);
    }

    private void prepareTeacherView(ModelAndView mvc, User user) {
        List<TeacherPrecensesView> precensesList = customDao.findTeacherPrecensesByUserId(user.getId());
        mvc.setViewName("precense/teacherPrecenses");
        mvc.addObject("coursesList", precensesList);
    }

}
