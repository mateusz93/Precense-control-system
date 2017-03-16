package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.model.StudentCourse;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.repository.StudentCourseRepository;
import neo.dmcs.repository.TeacherCourseRepository;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.service.SaveService;
import neo.dmcs.view.course.SaveView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/saves")
public class SaveController {

    private final UserRepository userRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final SaveService saveService;

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView save(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/studentSaves");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        prepareView(mvc, user);
        return mvc;
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/{courseId}", method = RequestMethod.POST)
    public ModelAndView signOut(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/studentSaves");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(user);
        studentCourse.setTeacherCourse(teacherCourse);
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
