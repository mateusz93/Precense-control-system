package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.repository.*;
import neo.dmcs.view.course.TeacherCourseView;
import neo.dmcs.view.grade.StudentGradeDetailsView;
import neo.dmcs.view.grade.StudentGradeView;
import neo.dmcs.view.grade.TeacherAddGradeView;
import neo.dmcs.view.grade.TeacherGradesView;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static neo.dmcs.util.Const.*;

/**
 * @author Mateusz Wieczorek on 11/6/16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final NotificationRepository notificationRepository;
    private final CourseService courseService;
    private final EmailService emailService;
    private final SMSService smsService;
    private final MessageSource messageSource;

    public ModelAndView prepareView(ModelAndView mvc, User user) {
        if (user.getType().name().equals(UserType.STUDENT.name())) {
            prepareStudentGrades(user, mvc);
        }
        if (user.getType().name().equals(UserType.TEACHER.name())){
            prepareTeacherView(mvc, user);
        }
        if (user.getType().name().equals(UserType.TEACHER.name())){
            prepareTeacherView(mvc, user);
        }
        return new ModelAndView();
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

    private TeacherGradesView getTeacherGradeView(List<TeacherGradesView> gradesViews, User user) {
        for (TeacherGradesView teacherGradesView : gradesViews) {
            if (teacherGradesView.getStudentId().equals(String.valueOf(user.getId()))) {
                return teacherGradesView;
            }
        }
        return new TeacherGradesView();
    }

    private boolean hasUser(List<TeacherGradesView> gradesViews, User user) {
        for (TeacherGradesView teacherGradesView : gradesViews) {
            if (teacherGradesView.getStudentId().equals(String.valueOf(user.getId()))) {
                return true;
            }
        }
        return false;
    }

    public ModelAndView prepareStudentGradesDetails(int courseId, ModelAndView mvc) {
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
        return mvc;
    }

    private void prepareTeacherView(ModelAndView mvc, User user) {
        List<TeacherCourseView> coursesList = courseService.getTeacherCoursesList(user);
        mvc.setViewName("grade/teacherCoursesList");
        mvc.addObject("coursesList", coursesList);
    }

    private void sendGradeNotification(User user, String subjectName) {
        Notification notification = notificationRepository.findByUser(user);
        if (notification.getBadMark().contains("EMAIL")) {
            emailService.sendEmail(user.getEmail(), "Ocena niedostateczna", getGradeNotificationContent(subjectName));
        }
        if (notification.getCriticalPresenceLevel().contains("SMS")) {
            smsService.sendSMS(user.getPhone(), getGradeNotificationContent(subjectName));
        }
    }

    private String getGradeNotificationContent(String subjectName) {
        return "Otrzymałeś ocenę niedostateczną z przedmiotu " + subjectName;
    }

    private ModelAndView prepareTeacherGrades(ModelAndView mvc, int courseId, User user) {
        List<TeacherGradesView> gradesViews = new ArrayList<>();
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        List<Grade> grades = gradeRepository.findByTeacherCourse(teacherCourse);
        for (Grade grade : grades) {
            prepareGradeView(courseId, gradesViews, grade);
        }
        mvc.addObject("students", gradesViews);
        mvc.setViewName("grade/teacherGrades");
        return mvc;
    }

    private void prepareGradeView(int courseId, List<TeacherGradesView> gradesViews, Grade grade) {
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

    public ModelAndView getGradesByCourseId(int courseId, ModelAndView mvc, User user) {
        if (user.getType().toString().equals(UserType.STUDENT.name())) {
            return prepareStudentGradesDetails(courseId, mvc);
        }
        if (user.getType().toString().equals(UserType.TEACHER.name())){
            return prepareTeacherGrades(mvc, courseId, user);
        }
        return new ModelAndView();
    }

    public ModelAndView newCourseByCourseIdAndStudentId(int courseId, int studentId, ModelAndView mvc, User user) {
        if (user.getType().toString().equals(UserType.STUDENT.name())) {
            return prepareStudentGradesDetails(courseId, mvc);
        }
        if (user.getType().toString().equals(UserType.TEACHER.name())){
            User user1 = userRepository.findOne(studentId);
            mvc.addObject("courseId", courseId);
            mvc.addObject("studentId", studentId);
            mvc.addObject("studentName", user1.getFullName());
            mvc.setViewName("grade/teacherAddGrade");
        }
        return mvc;
    }

    public ModelAndView updateCourseGrades(int courseId, int studentId, TeacherAddGradeView teacherAddGradeView, Locale locale, ModelAndView mvc, User user) {
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
        if (YES.equals(teacherAddGradeView.getIsFinal())) {
            grade.setFinalGrade(true);
        } else {
            grade.setFinalGrade(false);
        }

        gradeRepository.save(grade);
        prepareTeacherGrades(mvc, courseId, user);

        mvc.addObject(MESSAGE, messageSource.getMessage("grade.add", null, locale));
        mvc.addObject(MESSAGE_TYPE, MessageType.SUCCESS.name());
        return mvc;
    }
}
