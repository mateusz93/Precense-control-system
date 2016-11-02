package neo.dmcs.controller;

import neo.dmcs.repository.*;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.service.PrecenseService;
import neo.dmcs.view.course.CourseDateView;
import neo.dmcs.view.precense.CheckPrecenseView;
import neo.dmcs.view.precense.StudentPrecensesView;
import neo.dmcs.view.precense.TeacherPrecensesView;
import neo.dmcs.view.precense.CheckPrecenseViewWrapper;
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

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mateusz Wieczorek on 25.03.16.
 */
@Controller
@Transactional
@RequestMapping("/precenses")
public class PresenceController {

    private final Logger logger = LoggerFactory.getLogger(PresenceController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomRepository customRepository;

    @Autowired
    private CourseDateRepository courseDateRepository;

    @Autowired
    private StudentPrecenseRepository studentPrecenseRepository;

    @Autowired
    private TeacherCourseRepository teacherCourseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

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
        User user = userRepository.findByLogin(username);
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
        User user = userRepository.findByLogin(username);
        if (user.getType().equals(UserType.Student.name())) {
            mvc.setViewName("precense/precensesInfo");
            List<CourseDateView> courseDateViews = new ArrayList<CourseDateView>();
            List<CourseDate> courseDates = courseDateRepository.findBySubject(teacherCourseRepository.findOne(courseId).getSubject());
            for (CourseDate cd : courseDates) {
                CourseDateView courseDateView = new CourseDateView();
                courseDateView.setDate(cd.getDate());
                courseDateView.setFinishTime(cd.getFinishTime());
                courseDateView.setStartTime(cd.getStartTime());
                courseDateView.setCourseDateID(courseId);
                try {
                    CourseDate courseDate = courseDateRepository.findOne(courseId);
                    StudentPrecense studentPrecense = studentPrecenseRepository.findByCourseDate(courseDate);
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
            List<CourseDate> courseDates = courseDateRepository.findBySubject(teacherCourseRepository.findOne(courseId).getSubject());
            for (CourseDate cd : courseDates) {
                CourseDateView courseDateView = new CourseDateView();
                courseDateView.setDate(cd.getDate());
                courseDateView.setFinishTime(cd.getFinishTime());
                courseDateView.setStartTime(cd.getStartTime());
                courseDateView.setCourseDateID(cd.getId());
                courseDateViews.add(courseDateView);
            }
            mvc.addObject("datesList", courseDateViews);
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
        preparePrecensesList(courseDateId, mvc);
        return mvc;
    }

    @RequestMapping(value = "/update/{courseDateId}", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("studentWrapper") CheckPrecenseViewWrapper studentWrapper,
                               @PathVariable("courseDateId") int courseDateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("precense/checkPrecense");
        String username = (String) httpSession.getAttribute("username");
        if (!isLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }


        preparePrecensesList(courseDateId, mvc);
        mvc.addObject("message", "precense.updated");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }

    private void preparePrecensesList(@PathVariable("courseDateId") int courseDateId, ModelAndView mvc) {
        CourseDate courseDate = courseDateRepository.findOne(courseDateId);
        Subject subject = courseDate.getSubject();
        List<StudentCourse> studentCourses = studentCourseRepository.findBySubject(subject);
        List<CheckPrecenseView> students = new ArrayList<>();

        for (StudentCourse studentCourse : studentCourses) {
            User student = userRepository.findOne(studentCourse.getStudent().getId());
            CheckPrecenseView checkPrecenseView = new CheckPrecenseView();
            checkPrecenseView.setFirstName(student.getFirstName());
            checkPrecenseView.setLastName(student.getLastName());
            try {
                checkPrecenseView.setPrecenseStatus(studentPrecenseRepository.findByCourseDate(courseDate).getStatus());
            } catch (NoResultException e) {
                logger.debug("Student " + student.getFirstName() + " " + student.getLastName() + " nie ma sprawdzonej obecności w tym terminie");
            }
            students.add(checkPrecenseView);
        }
        CheckPrecenseViewWrapper checkPrecenseViewWrapper = new CheckPrecenseViewWrapper();
        checkPrecenseViewWrapper.setStudents(students);
        mvc.addObject("courseDateId", courseDateId);
        mvc.addObject("studentWrapper", checkPrecenseViewWrapper);
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
