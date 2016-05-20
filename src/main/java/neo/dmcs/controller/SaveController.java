package neo.dmcs.controller;

import neo.dmcs.dao.StudentCourseDao;
import neo.dmcs.dao.TeacherCourseDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.enums.MessageType;
import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.service.SaveService;
import neo.dmcs.view.course.SaveView;
import neo.dmcs.view.course.StudentCourseView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
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
    private TeacherCourseDao teacherCourseDao;

    @Autowired
    private StudentCourseDao studentCourseDao;

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

    @RequestMapping(value = "/{courseId}", method = RequestMethod.POST)
    public ModelAndView signOut(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/studentSaves");

        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userDao.findByUsername(username);
        TeacherCourse teacherCourse = teacherCourseDao.findById(courseId);
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(user);
        studentCourse.setTeacherCourse(teacherCourse);
        studentCourse.setSaveTime(new Timestamp((new Date()).getTime()));
        studentCourseDao.save(studentCourse);

        prepareView(mvc, user);

        mvc.addObject("message", "course.subscribed");
        mvc.addObject("messageType", MessageType.SUCCESS.name());

        return mvc;
    }

    private void prepareView(ModelAndView mvc, User user) {
        List<SaveView> subjectList =  saveService.getSubjects(user);
        mvc.addObject("subjectList", subjectList);
    }

    private boolean isLogged(String username) {
        return StringUtils.isNotBlank(username);
    }
}
