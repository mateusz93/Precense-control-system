package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.repository.*;
import neo.dmcs.service.CourseService;
import neo.dmcs.service.EmailService;
import neo.dmcs.service.PrecenseService;
import neo.dmcs.service.SMSService;
import neo.dmcs.view.course.CourseDateView;
import neo.dmcs.view.course.TeacherCourseView;
import neo.dmcs.view.precense.CheckPrecenseView;
import neo.dmcs.view.precense.CheckPrecenseViewWrapper;
import neo.dmcs.view.precense.StudentPrecensesView;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek on 25.03.16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/precenses")
public class PresenceController {

    private final UserRepository userRepository;
    private final CourseDateRepository courseDateRepository;
    private final StudentPrecenseRepository studentPrecenseRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final PrecenseService precenseService;
    private final CourseService courseService;
    private final SMSService smsService;
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final MessageSource messageSource;

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView precense(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        prepareView(mvc, user);
        return mvc;
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/info/{courseId}", method = RequestMethod.POST)
    public ModelAndView precenseInfo(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().toString().equals(UserType.Student.name())) {
            prepareStudentPrecenseStatuses(courseId, mvc);
        } else {
            prepareTeacherPrecenseStatuses(courseId, mvc);
        }

        return mvc;
    }

    private void prepareTeacherPrecenseStatuses(int courseId, ModelAndView mvc) {
        mvc.setViewName("precense/checkPrecenses");
        List<CourseDateView> courseDateViews = new ArrayList<>();
        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourseRepository.findOne(courseId));
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

    private void prepareStudentPrecenseStatuses(int courseId, ModelAndView mvc) {
        mvc.setViewName("precense/precensesInfo");
        List<CourseDateView> courseDateViews = new ArrayList<>();
        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourseRepository.findOne(courseId));
        for (CourseDate cd : courseDates) {
            CourseDateView courseDateView = new CourseDateView();
            courseDateView.setDate(cd.getDate());
            courseDateView.setFinishTime(cd.getFinishTime());
            courseDateView.setStartTime(cd.getStartTime());
            courseDateView.setCourseDateID(cd.getId());
            try {
                CourseDate courseDate = courseDateRepository.findOne(cd.getId());
                StudentPrecense studentPrecense = studentPrecenseRepository.findByCourseDate(courseDate);
                if (studentPrecense != null) {
                    courseDateView.setStatus(studentPrecense.getStatus());
                }
            } catch (NoResultException e) {
                log.warn(e.getMessage() + "StudentPrecense with courseDateId = " + courseId + " not found");
            }

            courseDateViews.add(courseDateView);
        }
        mvc.addObject("datesList", courseDateViews);
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/check/{courseDateId}", method = RequestMethod.POST)
    public ModelAndView precensecheck(@PathVariable("courseDateId") int courseDateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("precense/checkPrecense");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        preparePrecensesList(courseDateId, mvc);
        return mvc;
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/courseDates/{courseId}", method = RequestMethod.POST)
    public ModelAndView courseDates(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().toString().equals(UserType.Student.name())) {
            mvc.setViewName("course/studentCourseDates");
            TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
            List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
            mvc.addObject("datesList", courseDates);
        } else {
            mvc.setViewName("precense/teacherCourseDates");
            TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
            List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
            mvc.addObject("teacherCourseId", courseId);
            mvc.addObject("datesList", courseDates);
        }

        return mvc;
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/cancel/{courseDateId}", method = RequestMethod.POST)
    public ModelAndView cancel(@PathVariable("courseDateId") int courseDateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("precense/checkPrecense");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        sendNotifications(courseDateId);

        prepareTeacherView(mvc, user);
        return mvc;
    }

    private void sendNotifications(int courseDateId) {
        CourseDate courseDate = courseDateRepository.findOne(courseDateId);
        TeacherCourse teacherCourse = courseDate.getTeacherCourse();
        List<StudentCourse> studentCourses = studentCourseRepository.findByTeacherCourse(teacherCourse);
        for (StudentCourse studentCourse : studentCourses) {
            Notification notification = notificationRepository.findByUser(studentCourse.getStudent());
            if(notification != null && notification.getCourseCanceled().contains("SMS")) {
                smsService.sendSMS(studentCourse.getStudent().getPhone(), "Odwołano zajęcia "
                        + teacherCourse.getSubject().getName() + " w dniu " + courseDate.getDate());
            }
            if(notification != null && notification.getCourseCanceled().contains("EMAIL")) {
                emailService.sendEmail(studentCourse.getStudent().getEmail(), "Odwołano zajęcia",
                        "Odwołano zajęcia " + teacherCourse.getSubject().getName() + " w dniu " + courseDate.getDate());
            }
        }

    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/update/{courseDateId}", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("studentWrapper") CheckPrecenseView studentWrapper,
                               @PathVariable("courseDateId") int courseDateId, HttpSession httpSession, Locale locale) {
        ModelAndView mvc = new ModelAndView("precense/checkPrecense");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        updatePrecenses(studentWrapper, courseDateId);

        sendNotifications(studentWrapper, courseDateId);

        preparePrecensesList(courseDateId, mvc);
        mvc.addObject("message", messageSource.getMessage("precense.updated", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }

    private void sendNotifications(CheckPrecenseView studentWrapper, int courseDateId) {
        absenceNotifications(studentWrapper, courseDateId);
        criticalPresenceLevelNotifications(courseDateId);
    }

    private void criticalPresenceLevelNotifications(int courseDateId) {
        CourseDate courseDate = courseDateRepository.findOne(courseDateId);
        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(courseDate.getTeacherCourse());
        int maxAbsence = courseDate.getTeacherCourse().getSubject().getQuantity()
                - courseDate.getTeacherCourse().getSubject().getMinQuantity();
        List<StudentCourse> studentCourses = studentCourseRepository.findByTeacherCourse(courseDate.getTeacherCourse());
        int absence;
        for (StudentCourse sc : studentCourses) {
            absence = 0;
            for (CourseDate cd : courseDates) {
                StudentPrecense studentPrecense = studentPrecenseRepository.findByCourseDateAndStudent(cd, sc.getStudent());
                if ("Nieobecny".equalsIgnoreCase(studentPrecense.getStatus())) {
                    absence++;
                }
            }
            if (absence == maxAbsence) {
                Notification notification = notificationRepository.findByUser(sc.getStudent());
                if (notification.getCriticalPresenceLevel().contains("EMAIL")) {
                    emailService.sendEmail(sc.getStudent().getEmail(), "Krytyczny poziom nieobecności",
                            "Twój poziom nieobecności z przedmiotu " + courseDate.getTeacherCourse().getSubject().getName()
                            + " osiągnął poziom krytyczny.");
                }
                if (notification.getCriticalPresenceLevel().contains("SMS")) {
                    smsService.sendSMS(sc.getStudent().getPhone(), "Twój poziom nieobecności z przedmiotu "
                            + courseDate.getTeacherCourse().getSubject().getName() + " osiągnął poziom krytyczny.");
                }
            }
        }
    }

    private void absenceNotifications(CheckPrecenseView studentWrapper, int courseDateId) {
        String[] ids = studentWrapper.getID().split(",");
        String[] statuses = studentWrapper.getPrecenseStatus().split(",");

        for (int i = 0; i < ids.length; ++i) {
            if ("Nieobecny".equalsIgnoreCase(statuses[i])) {
                User user = userRepository.findOne(Integer.valueOf(ids[i]));
                Notification notification = notificationRepository.findByUser(user);
                CourseDate courseDate = courseDateRepository.findOne(courseDateId);
                if (notification.getAbsence().contains("EMAIL")) {
                    sendAbsenceEmail(user, courseDate);
                }
                if (notification.getAbsence().contains("SMS")) {
                    sendAbsenceSMS(user, courseDate);
                }
            }
        }
    }

    private void sendAbsenceSMS(User user, CourseDate courseDate) {
        smsService.sendSMS(user.getPhone(), "Wystawiono nieobecność na zajęciach z przedmiotu: "
                + courseDate.getTeacherCourse().getSubject().getName() + " w dniu: " + courseDate.getDate());
    }

    private void sendAbsenceEmail(User user, CourseDate courseDate) {
        emailService.sendEmail(user.getEmail(), "Nieobecność na zajęciach",
                "Wystawiono nieobecność na zajęciach z przedmiotu: " + courseDate.getTeacherCourse().getSubject().getName()
                + " w dniu: " + courseDate.getDate());
    }


    private void updatePrecenses(CheckPrecenseView studentWrapper, int courseDateId) {
        String[] ids = studentWrapper.getID().split(",");
        String[] firstNames = studentWrapper.getFirstName().split(",");
        String[] lastNames = studentWrapper.getLastName().split(",");
        String[] statuses = studentWrapper.getPrecenseStatus().split(",");

        for (int i = 0; i < ids.length; ++i) {
            User user = userRepository.findOne(Integer.valueOf(ids[i]));
            CourseDate courseDate = courseDateRepository.findOne(courseDateId);
            StudentPrecense studentPrecense = studentPrecenseRepository.findByCourseDateAndStudent(courseDate, user);
            if (studentPrecense != null) {
                studentPrecense.setStatus(statuses[i]);
            } else {
                studentPrecense = new StudentPrecense();
                studentPrecense.setStatus(statuses[i]);
                studentPrecense.setCourseDate(courseDate);
                studentPrecense.setStudent(user);
            }
            studentPrecenseRepository.save(studentPrecense);
        }
    }


    private void preparePrecensesList(int courseDateId, ModelAndView mvc) {
        CourseDate courseDate = courseDateRepository.findOne(courseDateId);
        List<StudentCourse> studentCourses = studentCourseRepository.findByTeacherCourse(courseDate.getTeacherCourse());
        List<CheckPrecenseView> students = new ArrayList<>();

        for (StudentCourse studentCourse : studentCourses) {
            User student = userRepository.findOne(studentCourse.getStudent().getId());
            CheckPrecenseView checkPrecenseView = new CheckPrecenseView();
            checkPrecenseView.setID(String.valueOf(student.getId()));
            checkPrecenseView.setFirstName(student.getFirstName());
            checkPrecenseView.setLastName(student.getLastName());

            StudentPrecense studentPrecense = studentPrecenseRepository.findByCourseDate(courseDate);
            if (studentPrecense != null) {
                checkPrecenseView.setPrecenseStatus(studentPrecense.getStatus());
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
        List<TeacherCourseView> coursesList = courseService.getTeacherCoursesList(user);
        mvc.setViewName("precense/teacherCoursesList");
        mvc.addObject("coursesList", coursesList);
    }

}
