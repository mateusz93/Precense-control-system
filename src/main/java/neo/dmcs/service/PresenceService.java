package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.repository.*;
import neo.dmcs.view.course.CourseDateView;
import neo.dmcs.view.course.TeacherCourseView;
import neo.dmcs.view.precense.CheckPresenceView;
import neo.dmcs.view.precense.CheckPresenceViewWrapper;
import neo.dmcs.view.precense.StudentPresencesView;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static neo.dmcs.util.Const.*;

/**
 * @author Mateusz Wieczorek, 28.04.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PresenceService {

    private final UserRepository userRepository;
    private final CourseDateRepository courseDateRepository;
    private final StudentPresenceRepository studentPresenceRepository;
    private final CourseService courseService;
    private final SMSService smsService;
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final MessageSource messageSource;
    private final StudentCourseRepository studentCourseRepository;
    private final TeacherCourseRepository teacherCourseRepository;

    private List<StudentPresencesView> getStudentPresences(User user) {
        List<StudentPresencesView> studentPresencesViews = new ArrayList<>();
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudent(user);
        for (StudentCourse studentCourse : studentCourses) {
            Subject subject = studentCourse.getTeacherCourse().getSubject();
            TeacherCourse teacherCourse = teacherCourseRepository.findBySubjectAndStudentGroup(subject, user.getGroup());
            StudentPresencesView studentPresencesView = getStudentPresencesView(subject, teacherCourse);
            studentPresencesViews.add(studentPresencesView);
        }
        return studentPresencesViews;
    }

    private StudentPresencesView getStudentPresencesView(Subject subject, TeacherCourse teacherCourse) {
        StudentPresencesView studentPresencesView = new StudentPresencesView();
        studentPresencesView.setSubjectName(subject.getName());
        studentPresencesView.setTeacherName(teacherCourse.getTeacher().getFirstName() + " " + teacherCourse.getTeacher().getLastName());
        studentPresencesView.setCourseId(teacherCourse.getId());
        studentPresencesView.setQuantity(subject.getQuantity());
        return studentPresencesView;
    }

    public ModelAndView prepareView(ModelAndView mvc, User user) {
        if (user.getType().name().equalsIgnoreCase(UserType.STUDENT.name())) {
            return prepareStudentView(mvc, user);
        }
        return prepareTeacherView(mvc, user);
    }

    private ModelAndView prepareStudentView(ModelAndView mvc, User user) {
        List<StudentPresencesView> precensesList = getStudentPresences(user);
        mvc.setViewName("precense/studentPrecenses");
        mvc.addObject("coursesList", precensesList);
        return mvc;
    }

    private ModelAndView prepareTeacherView(ModelAndView mvc, User user) {
        List<TeacherCourseView> coursesList = courseService.getTeacherCoursesList(user);
        mvc.setViewName("precense/teacherCoursesList");
        mvc.addObject("coursesList", coursesList);
        return mvc;
    }

    public ModelAndView preparePresenceStatuses(int courseId, ModelAndView mvc, User user) {
        if (user.getType().toString().equals(UserType.STUDENT.name())) {
            return prepareStudentPresenceStatuses(courseId, mvc);
        } else {
            return prepareTeacherPrecenseStatuses(courseId, mvc);
        }
    }

    private ModelAndView prepareTeacherPrecenseStatuses(int courseId, ModelAndView mvc) {
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
        return mvc;
    }

    private ModelAndView prepareStudentPresenceStatuses(int courseId, ModelAndView mvc) {
        mvc.setViewName("precense/precensesInfo");
        List<CourseDateView> courseDateViews = new ArrayList<>();
        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourseRepository.findOne(courseId));
        for (CourseDate cd : courseDates) {
            CourseDateView courseDateView = getCourseDateView(courseId, cd);
            courseDateViews.add(courseDateView);
        }
        mvc.addObject("datesList", courseDateViews);
        return mvc;
    }

    private CourseDateView getCourseDateView(int courseId, CourseDate cd) {
        CourseDateView courseDateView = new CourseDateView();
        courseDateView.setDate(cd.getDate());
        courseDateView.setFinishTime(cd.getFinishTime());
        courseDateView.setStartTime(cd.getStartTime());
        courseDateView.setCourseDateID(cd.getId());
        try {
            CourseDate courseDate = courseDateRepository.findOne(cd.getId());
            StudentPresence studentPresence = studentPresenceRepository.findByCourseDate(courseDate);
            if (studentPresence != null) {
                courseDateView.setStatus(studentPresence.getStatus());
            }
        } catch (NoResultException e) {
            log.warn(e.getMessage() + "StudentPresence with courseDateId = " + courseId + " not found");
        }
        return courseDateView;
    }

    public ModelAndView preparePresencesList(int courseDateId, ModelAndView mvc) {
        CourseDate courseDate = courseDateRepository.findOne(courseDateId);
        List<StudentCourse> studentCourses = studentCourseRepository.findByTeacherCourse(courseDate.getTeacherCourse());
        List<CheckPresenceView> students = new ArrayList<>();

        for (StudentCourse studentCourse : studentCourses) {
            User student = userRepository.findOne(studentCourse.getStudent().getId());
            CheckPresenceView checkPresenceView = getCheckPresenceView(courseDate, student);
            students.add(checkPresenceView);
        }
        CheckPresenceViewWrapper checkPresenceViewWrapper = new CheckPresenceViewWrapper();
        checkPresenceViewWrapper.setStudents(students);
        mvc.addObject("courseDateId", courseDateId);
        mvc.addObject("studentWrapper", checkPresenceViewWrapper);
        return mvc;
    }

    public ModelAndView prepareCourseDates(int courseId, ModelAndView mvc, User user) {
        if (user.getType().toString().equals(UserType.STUDENT.name())) {
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

    private void sendNotifications(CheckPresenceView studentWrapper, int courseDateId) {
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
                StudentPresence studentPresence = studentPresenceRepository.findByCourseDateAndStudent(cd, sc.getStudent());
                if (studentPresence != null && ABSENCE.equalsIgnoreCase(studentPresence.getStatus())) {
                    absence++;
                }
            }
            if (absence == maxAbsence) {
                Notification notification = notificationRepository.findByUser(sc.getStudent());
                if (notification.getCriticalPresenceLevel().contains(EMAIL)) {
                    sendCriticalPresenceLevelEmail(courseDate, sc);
                }
                if (notification.getCriticalPresenceLevel().contains(SMS)) {
                    sendCriticalPresenceLevelSMS(courseDate, sc);
                }
            }
        }
    }

    private void sendCriticalPresenceLevelSMS(CourseDate courseDate, StudentCourse sc) {
        smsService.sendSMS(sc.getStudent().getPhone(), "Twój poziom nieobecności z przedmiotu "
                + courseDate.getTeacherCourse().getSubject().getName() + " osiągnął poziom krytyczny.");
    }

    private void sendCriticalPresenceLevelEmail(CourseDate courseDate, StudentCourse sc) {
        emailService.sendEmail(sc.getStudent().getEmail(), "Krytyczny poziom nieobecności",
                "Twój poziom nieobecności z przedmiotu " + courseDate.getTeacherCourse().getSubject().getName()
                        + " osiągnął poziom krytyczny.");
    }

    private void absenceNotifications(CheckPresenceView studentWrapper, int courseDateId) {
        String[] ids = studentWrapper.getID().split(",");
        String[] statuses = studentWrapper.getPrecenseStatus().split(",");

        for (int i = 0; i < ids.length; ++i) {
            if (ABSENCE.equalsIgnoreCase(statuses[i])) {
                User user = userRepository.findOne(Integer.valueOf(ids[i]));
                Notification notification = notificationRepository.findByUser(user);
                CourseDate courseDate = courseDateRepository.findOne(courseDateId);
                if (notification.getAbsence().contains(EMAIL)) {
                    sendAbsenceEmail(user, courseDate);
                }
                if (notification.getAbsence().contains(SMS)) {
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

    private void sendNotifications(int courseDateId) {
        CourseDate courseDate = courseDateRepository.findOne(courseDateId);
        TeacherCourse teacherCourse = courseDate.getTeacherCourse();
        List<StudentCourse> studentCourses = studentCourseRepository.findByTeacherCourse(teacherCourse);
        for (StudentCourse studentCourse : studentCourses) {
            Notification notification = notificationRepository.findByUser(studentCourse.getStudent());
            if(notification != null && notification.getCourseCanceled().contains(SMS)) {
                sendCourseCanceledSMS(courseDate, teacherCourse, studentCourse);
            }
            if(notification != null && notification.getCourseCanceled().contains(EMAIL)) {
                sendCourseCanceledEmail(courseDate, teacherCourse, studentCourse);
            }
        }
    }

    private void sendCourseCanceledEmail(CourseDate courseDate, TeacherCourse teacherCourse, StudentCourse studentCourse) {
        emailService.sendEmail(studentCourse.getStudent().getEmail(), "Odwołano zajęcia",
                "Odwołano zajęcia " + teacherCourse.getSubject().getName() + " w dniu " + courseDate.getDate());
    }

    private void sendCourseCanceledSMS(CourseDate courseDate, TeacherCourse teacherCourse, StudentCourse studentCourse) {
        smsService.sendSMS(studentCourse.getStudent().getPhone(), "Odwołano zajęcia "
                + teacherCourse.getSubject().getName() + " w dniu " + courseDate.getDate());
    }

    private void updatePresences(CheckPresenceView studentWrapper, int courseDateId) {
        String[] ids = studentWrapper.getID().split(",");
        String[] statuses = studentWrapper.getPrecenseStatus().split(",");

        for (int i = 0; i < ids.length; ++i) {
            User user = userRepository.findOne(Integer.valueOf(ids[i]));
            CourseDate courseDate = courseDateRepository.findOne(courseDateId);
            StudentPresence studentPresence = getStudentPresence(statuses, i, user, courseDate);
            studentPresenceRepository.save(studentPresence);
        }
    }

    private StudentPresence getStudentPresence(String[] statuses, int i, User user, CourseDate courseDate) {
        StudentPresence studentPresence = studentPresenceRepository.findByCourseDateAndStudent(courseDate, user);
        if (studentPresence != null) {
            studentPresence.setStatus(statuses[i]);
        } else {
            studentPresence = new StudentPresence();
            studentPresence.setStatus(statuses[i]);
            studentPresence.setCourseDate(courseDate);
            studentPresence.setStudent(user);
        }
        return studentPresence;
    }

    public ModelAndView cancelCourseDate(int courseDateId, ModelAndView mvc, User user) {
        sendNotifications(courseDateId);
        return prepareTeacherView(mvc, user);
    }

    public ModelAndView updateCourseDate(CheckPresenceView studentWrapper, int courseDateId, ModelAndView mvc, Locale locale) {
        updatePresences(studentWrapper, courseDateId);
        sendNotifications(studentWrapper, courseDateId);
        preparePresencesList(courseDateId, mvc);
        mvc.addObject("message", messageSource.getMessage("precense.updated", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }
    private CheckPresenceView getCheckPresenceView(CourseDate courseDate, User student) {
        CheckPresenceView checkPresenceView = new CheckPresenceView();
        checkPresenceView.setID(String.valueOf(student.getId()));
        checkPresenceView.setFirstName(student.getFirstName());
        checkPresenceView.setLastName(student.getLastName());

        StudentPresence studentPresence = studentPresenceRepository.findByCourseDate(courseDate);
        if (studentPresence != null) {
            checkPresenceView.setPrecenseStatus(studentPresence.getStatus());
        }
        return checkPresenceView;
    }
}
