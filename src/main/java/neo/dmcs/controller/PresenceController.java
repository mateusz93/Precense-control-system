package neo.dmcs.controller;

import neo.dmcs.dao.*;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.service.PrecenseService;
import neo.dmcs.view.course.CourseDateView;
import neo.dmcs.view.precense.CheckPrecenseView;
import neo.dmcs.view.precense.StudentPrecensesView;
import neo.dmcs.view.precense.TeacherPrecensesView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    private CourseDateDao courseDateDao;

    @Autowired
    private StudentPrecenseDao studentPrecenseDao;

    @Autowired
    private TeacherCourseDao teacherCourseDao;

    @Autowired
    private StudentCourseDao studentCourseDao;

    @Autowired
    private PrecenseService precenseService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView precense(HttpSession httpSession) {
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

    @RequestMapping(value = "/info/{courseId}", method = RequestMethod.POST)
    public ModelAndView precenseInfo(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userDao.findByUsername(username);
        if (user.getType().equals(UserType.Student.name())) {
            mvc.setViewName("precense/precensesInfo");
            List<CourseDateView> courseDateViews = new ArrayList<CourseDateView>();
            List<CourseDate> courseDates = courseDateDao.findByTeacherCourse(teacherCourseDao.findById(courseId));
            for (CourseDate cd : courseDates) {
                CourseDateView courseDateView = new CourseDateView();
                courseDateView.setDate(cd.getDate());
                courseDateView.setFinishTime(cd.getFinishTime());
                courseDateView.setStartTime(cd.getStartTime());
                courseDateView.setCourseDateID(courseId);
                try {
                    CourseDate courseDate = courseDateDao.findById(courseId);
                    StudentPrecense studentPrecense = studentPrecenseDao.findByCourseDate(courseDate);
                    courseDateView.setStatus(studentPrecense.getStatus());
                } catch (NoResultException e) {
                    if (logger.isWarnEnabled()) {
                        logger.warn(e.getMessage() + "StudentPrecense with courseDateId = " + courseId + " not found");
                    }
                }

                courseDateViews.add(courseDateView);
            }
            mvc.addObject("datesList", courseDateViews);
        } else {
            mvc.setViewName("precense/checkPrecenses");
            List<CourseDateView> courseDateViews = new ArrayList<CourseDateView>();
            List<CourseDate> courseDates = courseDateDao.findByTeacherCourse(teacherCourseDao.findById(courseId));
            for (CourseDate cd : courseDates) {
                CourseDateView courseDateView = new CourseDateView();
                courseDateView.setDate(cd.getDate());
                courseDateView.setFinishTime(cd.getFinishTime());
                courseDateView.setStartTime(cd.getStartTime());
                courseDateView.setCourseDateID(cd.getId());
                courseDateViews.add(courseDateView);
            }
            mvc.addObject("datesList", courseDateViews);

//            List<StudentCourse> studentCourses = studentCourseDao.findByTeacherCourse(courseId);
//            List<User> students = new ArrayList<User>();
//
//            for (StudentCourse studentCourse : studentCourses) {
//                User student = userDao.findById(studentCourse.getStudent());
//                if (student != null) {
//                    students.add(student);
//                }
//            }
//            mvc.addObject("students", students);
        }

        return mvc;
    }

    @RequestMapping(value = "/check/{courseDateId}", method = RequestMethod.POST)
    public ModelAndView precensecheck(@PathVariable("courseDateId") int courseDateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("precense/checkPrecense");
        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }

        CourseDate courseDate = courseDateDao.findById(courseDateId);
        TeacherCourse teacherCourse = courseDate.getTeacherCourse();
        List<StudentCourse> studentCourses = studentCourseDao.findByTeacherCourse(teacherCourse);
        List<CheckPrecenseView> students = new ArrayList<CheckPrecenseView>();

        for (StudentCourse studentCourse : studentCourses) {
            User student = userDao.findById(studentCourse.getStudent().getId());
            CheckPrecenseView checkPrecenseView = new CheckPrecenseView();
            checkPrecenseView.setFirstName(student.getFirstName());
            checkPrecenseView.setLastName(student.getLastName());
            checkPrecenseView.setPrecenseStatus(studentPrecenseDao.findByCourseDate(courseDate).getStatus());
            students.add(checkPrecenseView);
        }
        mvc.addObject("courseDateId", courseDateId);
        mvc.addObject("students", students);

        return mvc;
    }

    private void prepareView(ModelAndView mvc, User user) {
        if (user.getType().equals(UserType.Student.name())) {
            prepareStudentView(mvc, user);
        } else {
            prepareTeacherView(mvc, user);
        }
    }

    private void prepareStudentView(ModelAndView mvc, User user) {
        List<StudentPrecensesView> precensesList = precenseService.getStudentPrecenses(user);
        mvc.setViewName("precense/studentPrecenses");
        mvc.addObject("coursesList", precensesList);
    }

    private void prepareTeacherView(ModelAndView mvc, User user) {
        List<TeacherPrecensesView> precensesList = precenseService.getTeacherPrecenses(user);
        mvc.setViewName("precense/teacherPrecenses");
        mvc.addObject("coursesList", precensesList);
    }

    private boolean isLogged(String username) {
        return StringUtils.isNotBlank(username);
    }

}
