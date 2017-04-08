package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.repository.*;
import neo.dmcs.view.course.CourseDateView;
import neo.dmcs.view.course.NewCourseView;
import neo.dmcs.view.course.StudentCourseView;
import neo.dmcs.view.course.TeacherCourseView;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static neo.dmcs.util.Const.*;

/**
 * @author Mateusz Wieczorek, 14.05.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final CourseDateRepository courseDateRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final EmailService emailService;
    private final SMSService smsService;
    private final NotificationRepository notificationRepository;


    public ModelAndView prepareView(ModelAndView mvc, User user) {
        if (UserType.STUDENT.name().equals(user.getType().name())) {
            return prepareStudentView(mvc, user);
        }
        if (UserType.TEACHER.name().equals(user.getType().name())) {
            return prepareTeacherView(mvc, user);
        }
        if (UserType.ADMIN.name().equals(user.getType().name())) {
            return prepareAdminView(mvc);
        }
        return new ModelAndView();
    }
    private ModelAndView prepareAdminView(ModelAndView mvc) {
        List<TeacherCourse> coursesList = teacherCourseRepository.findAll();
        mvc.setViewName("course/adminCoursesList");
        mvc.addObject("coursesList", coursesList);
        return mvc;
    }

    private ModelAndView prepareStudentView(ModelAndView mvc, User user) {
        List<StudentCourseView> coursesList = getStudentCoursesList(user);
        mvc.setViewName("course/studentCoursesList");
        mvc.addObject("coursesList", coursesList);
        return mvc;
    }

    private ModelAndView prepareTeacherView(ModelAndView mvc, User user) {
        List<TeacherCourseView> coursesList = getTeacherCoursesList(user);
        mvc.setViewName("course/teacherCoursesList");
        mvc.addObject("coursesList", coursesList);
        return mvc;
    }

    public List<TeacherCourseView> getTeacherCoursesList(User user) {
        List<TeacherCourse> courses = teacherCourseRepository.findByTeacher(user);
        return mapTeacherCourses(courses, user);
    }

    public List<StudentCourseView> getStudentCoursesList(User user) {
        List<StudentCourse> courses = studentCourseRepository.findByStudent(user);
        return mapStudentCourses(courses, user);
    }

    private List<TeacherCourseView> mapTeacherCourses(List<TeacherCourse> courses, User user) {
        List<TeacherCourseView> courseViews = new ArrayList<>();
        for (TeacherCourse c : courses) {
            TeacherCourseView courseView = new TeacherCourseView();
            courseView.setCoursesQuantity(c.getSubject().getQuantity());
            courseView.setID(c.getId());
            courseView.setSubjectName(c.getSubject().getName());
            courseViews.add(courseView);
        }
        return courseViews;
    }

    private List<StudentCourseView> mapStudentCourses(List<StudentCourse> courses, User user) {
        List<StudentCourseView> courseViews = new ArrayList<>();
        for (StudentCourse c : courses) {
            StudentCourseView courseView = new StudentCourseView();
            courseView.setCoursesQuantity(c.getTeacherCourse().getSubject().getQuantity());
            courseView.setDescription(c.getTeacherCourse().getSubject().getDescription());
            courseView.setName(c.getTeacherCourse().getSubject().getName());
            courseView.setCourseId(c.getTeacherCourse().getId());
            courseView.setTeacherName(c.getTeacherCourse().getTeacher().getFirstName() + " " +
                    c.getTeacherCourse().getTeacher().getLastName());
            courseViews.add(courseView);
        }
        return courseViews;
    }


    public ModelAndView prepareViewByCourseId(int teacherCourseId, ModelAndView mvc, User user)   {
        if (user.getType().toString().equals(UserType.STUDENT.name())) {
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

    public ModelAndView prepareNewView(ModelAndView mvc) {
        List<Subject> subjects = subjectRepository.findAll();
        mvc.addObject("subjectList", subjects);
        List<User> teachers = userRepository.findByType(UserType.TEACHER.name());
        mvc.addObject("teacherList", teachers);
        return mvc;
    }

    public ModelAndView prepareEditViewByDateId(int dateId, ModelAndView mvc) {
        CourseDate courseDate = courseDateRepository.findOne(dateId);
        mvc.addObject("date", courseDate.getDate());
        mvc.addObject("startTime", courseDate.getStartTime());
        mvc.addObject("finishTime", courseDate.getFinishTime());
        mvc.addObject("teacherCourseId", courseDate.getTeacherCourse().getId());
        mvc.addObject("dateId", dateId);
        mvc.addObject("isEdited", "True");
        return mvc;
    }

    public ModelAndView deleteCourseByDateId(int dateId, ModelAndView mvc) {
        CourseDate courseDate = courseDateRepository.findOne(dateId);
        courseDateRepository.delete(courseDate);
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseDate.getTeacherCourse().getId());
        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
        mvc.addObject("datesList", courseDates);
        return mvc;
    }

    public ModelAndView deleteCourseById(int courseId, Locale locale, ModelAndView mvc) {
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        teacherCourseRepository.delete(teacherCourse);
        mvc.addObject(MESSAGE, messageSource.getMessage("course.deleted", null, locale));
        mvc.addObject(MESSAGE_TYPE, MessageType.SUCCESS.name());
        prepareAdminView(mvc);
        return mvc;
    }

    public ModelAndView saveNewCourse(NewCourseView newCourseForm, Locale locale, ModelAndView mvc) {
        User teacher = userRepository.findByLogin(newCourseForm.getTeacherLogin());
        Subject subject = subjectRepository.findByName(newCourseForm.getSubjectName());
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setDescription(newCourseForm.getDescription());
        teacherCourse.setStudentGroup(newCourseForm.getStudentGroup());
        teacherCourse.setSubject(subject);
        teacherCourse.setTeacher(teacher);

        teacherCourseRepository.save(teacherCourse);
        mvc.addObject(MESSAGE, messageSource.getMessage("course.added", null, locale));
        mvc.addObject(MESSAGE_TYPE, MessageType.SUCCESS.name());
        return mvc;
    }

    public ModelAndView addCourseDateByCourseId(int teacherCourseId, CourseDateView form, Locale locale, ModelAndView mvc) {
        CourseDate courseDate;
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(teacherCourseId);
        if (TRUE.equalsIgnoreCase(form.getIsEdited())) {
            courseDate = courseDateRepository.findOne(Integer.valueOf(form.getDateId()));
            sendEditCourseDateNotification(teacherCourse, form, courseDate);
            courseDate.setDate(form.getDate());
            courseDate.setStartTime(form.getStartTime());
            courseDate.setFinishTime(form.getFinishTime());
            courseDateRepository.save(courseDate);
            mvc.addObject(MESSAGE, messageSource.getMessage("course.courseDateEdited", null, locale));
            mvc.addObject(MESSAGE_TYPE, MessageType.SUCCESS.name());
        } else {
            courseDate = new CourseDate();
            courseDate.setTeacherCourse(teacherCourse);
            courseDate.setStartTime(form.getStartTime());
            courseDate.setFinishTime(form.getFinishTime());
            courseDate.setDate(form.getDate());
            courseDateRepository.save(courseDate);
            mvc.addObject(MESSAGE, messageSource.getMessage("course.courseDateAdded", null, locale));
            mvc.addObject(MESSAGE_TYPE, MessageType.SUCCESS.name());
        }

        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
        mvc.addObject("datesList", courseDates);
        return mvc;
    }

    private void sendEditCourseDateNotification(TeacherCourse teacherCourse, CourseDateView form, CourseDate courseDate) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByTeacherCourse(teacherCourse);
        for(StudentCourse studentCourse : studentCourses) {
            Notification notification = notificationRepository.findByUser(studentCourse.getStudent());
            if (notification.getChangeCourseDate().contains(EMAIL)) {
                emailService.sendEmail(studentCourse.getStudent().getEmail(), "Zmieniono termin zajęć",
                        getEditCourseDateNotificationContent(form, courseDate, studentCourse));
            }
            if (notification.getChangeCourseDate().contains(SMS)) {
                smsService.sendSMS(studentCourse.getStudent().getEmail(),
                        getEditCourseDateNotificationContent(form, courseDate, studentCourse));
            }
        }
    }

    private String getEditCourseDateNotificationContent(CourseDateView form, CourseDate courseDate, StudentCourse studentCourse) {
        return "Zmieniono termin zajęć przedmiotu: " + studentCourse.getTeacherCourse().getSubject().getName()
                + ". Poprzedni termin: " + courseDate.getDate() + ". Start: " + courseDate.getStartTime()
                + ". Koniec: " + courseDate.getFinishTime() + ". Nowy termin: " + form.getDate() + ". Start: "
                + form.getStartTime() + ". Koniec: " + form.getFinishTime();
    }
}
