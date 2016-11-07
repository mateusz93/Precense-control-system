package neo.dmcs.controller;

import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.CourseDate;
import neo.dmcs.model.Subject;
import neo.dmcs.model.TeacherCourse;
import neo.dmcs.model.User;
import neo.dmcs.repository.*;
import neo.dmcs.service.CourseService;
import neo.dmcs.view.course.CourseDateView;
import neo.dmcs.view.course.NewCourseView;
import neo.dmcs.view.course.StudentCourseView;
import neo.dmcs.view.course.TeacherCourseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek on 25.03.16.
 */
@Controller
@Transactional
@RequestMapping("/courses")
public class CourseController {

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseDateRepository courseDateRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private CourseService courseService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView course(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        prepareView(mvc, user);
        return mvc;
    }

    @RequestMapping(value = "/info/{teacherCourseId}", method = RequestMethod.POST)
    public ModelAndView info(@PathVariable("teacherCourseId") int teacherCourseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().equals(UserType.Student.name())) {
            mvc.setViewName("course/studentCourseDates");
            TeacherCourse teacherCourse = teacherCourseRepository.findOne(teacherCourseId);
            List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
            mvc.addObject("datesList", courseDates);
        } else {
            mvc.setViewName("course/teacherCourseDates");
            TeacherCourse teacherCourse = teacherCourseRepository.findOne(teacherCourseId);
            List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
            mvc.addObject("teacherCourseId", teacherCourseId);
            mvc.addObject("datesList", courseDates);
        }

        return mvc;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newCourse(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        return mvc;
    }

    @RequestMapping(value = "/delete/{dateId}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("dateId") int dateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/teacherCourseDates");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        CourseDate courseDate = courseDateRepository.findOne(dateId);
        courseDateRepository.delete(courseDate);
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseDate.getTeacherCourse().getId());
        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
        mvc.addObject("datesList", courseDates);

        return mvc;
    }

    /*
    @RequestMapping(value = "/unSubscribe/{courseId}", method = RequestMethod.POST)
    public ModelAndView unSubscribe(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        String username = (String) httpSession.getAttribute("username");
        if (!isNotLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userRepository.findByLogin(username);
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        StudentCourse studentCourse = studentCourseRepository.findByStudentAndTeacherCourse(user, teacherCourse);
        studentCourseRepository.delete(studentCourse);

        prepareView(mvc, user);

        mvc.addObject("message", "course.unSubscribed");
        mvc.addObject("messageType", MessageType.WARNING.name());

        return mvc;
    }
    */

    /*
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView newCourse(@ModelAttribute("newCourseForm") NewCourseView newCourseForm, HttpSession session) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        String username = (String) session.getAttribute("username");
        if (!isNotLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userRepository.findByLogin(username);
        saveNewCourse(newCourseForm, user);
        List<Department> departments = (List<Department>) departmentRepository.findAll();
        mvc.addObject("departments", departments);
        mvc.addObject("message", "course.added");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }
    */

    @RequestMapping(value = "/addOne/{teacherCourseId}", method = RequestMethod.POST)
    public ModelAndView newOne(@PathVariable("teacherCourseId") int teacherCourseId, HttpSession session) {
        ModelAndView mvc = new ModelAndView("course/addCourseDate");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        mvc.addObject("teacherCourseId", teacherCourseId);
        return mvc;
    }

    @RequestMapping(value = "/addCourseDate/{teacherCourseId}", method = RequestMethod.POST)
    public ModelAndView newCourseDate(@ModelAttribute("courseDateForm") CourseDateView form, @PathVariable("teacherCourseId") int teacherCourseId, HttpSession session) {
        ModelAndView mvc = new ModelAndView("course/teacherCourseDates");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(teacherCourseId);
        CourseDate courseDate = new CourseDate();
        courseDate.setTeacherCourse(teacherCourse);
        courseDate.setStartTime(form.getStartTime());
        courseDate.setFinishTime(form.getFinishTime());
        courseDate.setDate(form.getDate());
        courseDateRepository.save(courseDate);

        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
        mvc.addObject("datesList", courseDates);
        mvc.addObject("message", "course.courseDateAdded");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }

    private void saveNewCourse(@ModelAttribute("newCourseForm") NewCourseView newCourseForm, User user) {
        Subject subject = new Subject();
        subject.setDescription(newCourseForm.getDescription());
        subject.setName(newCourseForm.getSubjectName());
        subjectRepository.save(subject);

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setDescription(newCourseForm.getDescription());
        teacherCourse.setSubject(subject);
        teacherCourse.setTeacher(user);
        teacherCourseRepository.save(teacherCourse);
    }

    private void prepareView(ModelAndView mvc, User user) {
        if (UserType.Student.name().equals(user.getType())) {
            prepareStudentView(mvc, user);
        } else {
            prepareTeacherView(mvc, user);
        }
    }

    private void prepareStudentView(ModelAndView mvc, User user) {
        List<StudentCourseView> coursesList = courseService.getStudentCoursesList(user);
        mvc.setViewName("course/studentCoursesList");
        mvc.addObject("coursesList", coursesList);
    }

    private void prepareTeacherView(ModelAndView mvc, User user) {
        List<TeacherCourseView> coursesList = courseService.getTeacherCoursesList(user);
        mvc.setViewName("course/teacherCoursesList");
        mvc.addObject("coursesList", coursesList);
    }
}
