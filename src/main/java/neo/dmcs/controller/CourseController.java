package neo.dmcs.controller;

import neo.dmcs.dao.*;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.service.CourseService;
import neo.dmcs.view.course.NewCourseView;
import neo.dmcs.view.course.StudentCourseView;
import neo.dmcs.view.course.TeacherCourseView;
import org.apache.commons.lang3.StringUtils;
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

/**
 * Created by mateusz on 25.03.16.
 */
@Controller
@Transactional
@RequestMapping("/courses")
public class CourseController {

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private CustomDao customDao;

    @Autowired
    private CourseDateDao courseDateDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private TeacherCourseDao teacherCourseDao;

    @Autowired
    private CourseService courseService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView course(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userDao.findByUsername(username);
        prepareView(mvc, user);
        return mvc;
    }

    @RequestMapping(value = "/info/{teacherCourseId}", method = RequestMethod.POST)
    public ModelAndView info(@PathVariable("teacherCourseId") int teacherCourseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userDao.findByUsername(username);
        if (user.getType().equals(UserType.Student.name())) {
            mvc.setViewName("course/studentCourseDates");
        } else {
            mvc.setViewName("course/teacherCourseDates");
            TeacherCourse teacherCourse = teacherCourseDao.findById(teacherCourseId);
            List<CourseDate> courseDates = courseDateDao.findByTeacherCourse(teacherCourse);
            mvc.addObject("datesList", courseDates);
        }

        return mvc;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newCourse(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        List<Department> departments = departmentDao.findAll();
        mvc.addObject("departments", departments);
        return mvc;
    }

    @RequestMapping(value = "/delete/{dateId}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("dateId") int dateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/teacherCourseDates");
        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        CourseDate courseDate = courseDateDao.findById(dateId);
        courseDateDao.delete(courseDate);
        TeacherCourse teacherCourse = teacherCourseDao.findById(courseDate.getTeacherCourse().getId());
        List<CourseDate> courseDates = courseDateDao.findByTeacherCourse(teacherCourse);
        mvc.addObject("datesList", courseDates);

        return mvc;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView newCourse(@ModelAttribute("newCourseForm") NewCourseView newCourseForm, HttpSession session) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        String username = (String) session.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userDao.findByUsername(username);
        saveNewCourse(newCourseForm, user);
        List<Department> departments = departmentDao.findAll();
        mvc.addObject("departments", departments);
        mvc.addObject("message", "course.added");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }

    @RequestMapping(value = "/addOne/{courseId}", method = RequestMethod.POST)
    public ModelAndView newCourse(@PathVariable("courseId") int courseId, HttpSession session) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        String username = (String) session.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }


        return mvc;
    }

    private void saveNewCourse(@ModelAttribute("newCourseForm") NewCourseView newCourseForm, User user) {
        Department department = departmentDao.findById(newCourseForm.getDepartmentID());

        Subject subject = new Subject();
        subject.setDepartment(department);
        subject.setDescription(newCourseForm.getDescription());
        subject.setName(newCourseForm.getSubjectName());
        subjectDao.save(subject);

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setDescription(newCourseForm.getDescription());
        teacherCourse.setCoursesQuantity(newCourseForm.getQuantity());
        teacherCourse.setMinPresence(newCourseForm.getMin());
        teacherCourse.setSubject(subject);
        teacherCourse.setTeacher(user);
        teacherCourse.setType(newCourseForm.getType());
        teacherCourseDao.save(teacherCourse);
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

    private boolean isLogged(String username) {
        return StringUtils.isNotBlank(username);
    }
}
