package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.repository.*;
import neo.dmcs.service.CourseService;
import neo.dmcs.service.EmailService;
import neo.dmcs.service.GradeService;
import neo.dmcs.service.SMSService;
import neo.dmcs.view.course.TeacherCourseView;
import neo.dmcs.view.grade.StudentGradeDetailsView;
import neo.dmcs.view.grade.StudentGradeView;
import neo.dmcs.view.grade.TeacherAddGradeView;
import neo.dmcs.view.grade.TeacherGradesView;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek on 11/6/16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/grades")
public class GradeController {

    private final UserRepository userRepository;
    private final GradeService gradeService;
    private final CourseService courseService;
    private final GradeRepository gradeRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final SMSService smsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView course(HttpSession session) {
        ModelAndView mvc = new ModelAndView("grade/studentGrades");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().equals(UserType.Student.name())) {
            prepareStudentGrades(user, mvc);
        } else if (user.getType().equals(UserType.Teacher.name())){
            prepareTeacherView(mvc, user);
        }
        return mvc;
    }

    @RequestMapping(value = "/info/{courseId}", method = RequestMethod.GET)
    public ModelAndView gradesInfo(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("grade/studentGradesDetails");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().equals(UserType.Student.name())) {
            prepareStudentGradesDetails(courseId, mvc);
        } else {
            //gradeService.prepareTeacherPrecenseStatuses(courseId, mvc);
        }

        return mvc;
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public ModelAndView grades(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("grade/studentGradesDetails");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().equals(UserType.Student.name())) {
            prepareStudentGradesDetails(courseId, mvc);
        } else if (user.getType().equals(UserType.Teacher.name())){
           prepareTeacherGrades(mvc, courseId, user);
        }

        return mvc;
    }

    @RequestMapping(value = "/new/{courseId}/{studentId}", method = RequestMethod.GET)
    public ModelAndView grades(@PathVariable("courseId") int courseId,
                               @PathVariable("studentId") int studentId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("grade/studentGradesDetails");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().equals(UserType.Student.name())) {
            prepareStudentGradesDetails(courseId, mvc);
        } else if (user.getType().equals(UserType.Teacher.name())){
            User user1 = userRepository.findOne(studentId);
            mvc.addObject("courseId", courseId);
            mvc.addObject("studentId", studentId);
            mvc.addObject("studentName", user1.getFullName());
            mvc.setViewName("grade/teacherAddGrade");
        }

        return mvc;
    }

    @RequestMapping(value = "/save/{courseId}/{studentId}", method = RequestMethod.POST)
    public ModelAndView saveGrade(@PathVariable("courseId") int courseId,
                                  @PathVariable("studentId") int studentId,
                                  @ModelAttribute("addGrade") TeacherAddGradeView teacherAddGradeView,
                                  HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        User user1 = userRepository.findOne(studentId);

        Grade grade = new Grade();
        grade.setTeacherCourse(teacherCourse);
        grade.setTime(new Timestamp(System.currentTimeMillis()));
        grade.setValue(Integer.valueOf(teacherAddGradeView.getValue()));
        if (grade.getValue() == 1) {
            sendGradeNotification(user1, teacherCourse.getSubject().getName());
        }
        grade.setUser(user1);
        if ("Tak".equals(teacherAddGradeView.getIsFinal())) {
            grade.setFinalGrade(true);
        } else {
            grade.setFinalGrade(false);
        }

        gradeRepository.save(grade);
        prepareTeacherGrades(mvc, courseId, user);

        mvc.addObject("message", "grade.add");
        mvc.addObject("messageType", MessageType.SUCCESS.name());

        return mvc;
    }

    private void sendGradeNotification(User user, String subjectName) {
        Notification notification = notificationRepository.findByUser(user);
        if (notification.getBadMark().contains("EMAIL")) {
            emailService.sendEmail(user.getEmail(), "Ocena niedostateczna",
                    "Otrzymałeś ocenę niedostateczną z przedmiotu " + subjectName);
        }
        if (notification.getCriticalPresenceLevel().contains("SMS")) {
            smsService.sendSMS(user.getPhone(), "Otrzymałeś ocenę niedostateczną z przedmiotu " + subjectName);
        }
    }

    private void prepareTeacherGrades(ModelAndView mvc, int courseId, User user) {
        List<TeacherGradesView> gradesViews = new ArrayList<>();
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        List<Grade> grades = gradeRepository.findByTeacherCourse(teacherCourse);
        for (Grade grade : grades) {
            if (hasUser(gradesViews, grade.getUser())) {
                TeacherGradesView teacherGradesView = getTeacherGradeView(gradesViews, grade.getUser());
                teacherGradesView.setGrades(teacherGradesView.getGrades() + ", " + grade.getValue());
            } else {
                TeacherGradesView teacherGradesView = new TeacherGradesView();
                teacherGradesView.setCourseId(String.valueOf(courseId));
                if (grade.isFinalGrade()) {
                    teacherGradesView.setFinalGrade(String.valueOf(grade.getValue()));
                }
                teacherGradesView.setFirstName(grade.getUser().getFirstName());
                teacherGradesView.setLastName(grade.getUser().getLastName());
                teacherGradesView.setGrades(String.valueOf(grade.getValue()));
                teacherGradesView.setStudentId(String.valueOf(grade.getUser().getId()));
                gradesViews.add(teacherGradesView);
            }
        }
        mvc.addObject("students", gradesViews);
        mvc.setViewName("grade/teacherGrades");
    }

    private TeacherGradesView getTeacherGradeView(List<TeacherGradesView> gradesViews, User user) {
        for (TeacherGradesView teacherGradesView : gradesViews) {
            if (teacherGradesView.getStudentId().equals(String.valueOf(user.getId()))) {
                return teacherGradesView;
            }
        }
        return null;
    }

    private boolean hasUser(List<TeacherGradesView> gradesViews, User user) {
        for (TeacherGradesView teacherGradesView : gradesViews) {
            if (teacherGradesView.getStudentId().equals(String.valueOf(user.getId()))) {
                return true;
            }
        }
        return false;
    }

    private void prepareStudentGrades(User user, ModelAndView mvc) {
        List<StudentGradeView> StudentGradeViews = new ArrayList<>();
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudent(user);
        for (StudentCourse studentCourse : studentCourses) {
            StudentGradeView studentGradeView = new StudentGradeView();
            studentGradeView.setCourseId(studentCourse.getTeacherCourse().getSubject().getId());
            studentGradeView.setName(studentCourse.getTeacherCourse().getSubject().getName());
            studentGradeView.setTeacherName(studentCourse.getTeacherCourse().getTeacher().getFullName());

            StudentGradeViews.add(studentGradeView);
        }
        mvc.addObject("gradesList", StudentGradeViews);
    }

    private void prepareStudentGradesDetails(int courseId, ModelAndView mvc) {
        List<StudentGradeDetailsView> StudentGradeDetailsViews = new ArrayList<>();
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        List<Grade> grades = gradeRepository.findByTeacherCourse(teacherCourse);
        for (Grade grade : grades) {
            StudentGradeDetailsView studentGradeDetailsView = new StudentGradeDetailsView();
            studentGradeDetailsView.setFinalGrade(grade.isFinalGrade());
            studentGradeDetailsView.setTime(grade.getTime().toString());
            studentGradeDetailsView.setValue(grade.getValue());
            studentGradeDetailsView.setPreviousGrade(grade.getPreviousGrade());

            StudentGradeDetailsViews.add(studentGradeDetailsView);
        }
        mvc.addObject("gradesDetailsList", StudentGradeDetailsViews);
    }

    private void prepareTeacherView(ModelAndView mvc, User user) {
        List<TeacherCourseView> coursesList = courseService.getTeacherCoursesList(user);
        mvc.setViewName("grade/teacherCoursesList");
        mvc.addObject("coursesList", coursesList);
    }
}
