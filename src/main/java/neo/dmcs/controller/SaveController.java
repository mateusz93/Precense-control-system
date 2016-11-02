package neo.dmcs.controller;

import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.TeacherCourseRepository;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.enums.MessageType;
import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.service.SaveService;
import neo.dmcs.view.course.SaveView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
@RequestMapping("/saves")
public class SaveController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

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
        User user = userRepository.findByLogin(username);
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
        User user = userRepository.findByLogin(username);
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(user);
        studentCourse.setSubject(teacherCourse.getSubject());
        studentCourse.setSaveTime(new Timestamp((new Date()).getTime()));
        studentCourseRepository.save(studentCourse);

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
