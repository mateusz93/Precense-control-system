package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.UserType;
import neo.dmcs.model.*;
import neo.dmcs.repository.*;
import neo.dmcs.service.CourseService;
import neo.dmcs.service.EmailService;
import neo.dmcs.service.SMSService;
import neo.dmcs.view.course.CourseDateView;
import neo.dmcs.view.course.NewCourseView;
import neo.dmcs.view.course.StudentCourseView;
import neo.dmcs.view.course.TeacherCourseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek on 25.03.16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/courses")
public class CourseController {

    private final UserRepository userRepository;
    private final CourseDateRepository courseDateRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final CourseService courseService;
    private final NotificationRepository notificationRepository;
    private final SMSService smsService;
    private final EmailService emailService;

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
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

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/info/{teacherCourseId}", method = RequestMethod.POST)
    public ModelAndView info(@PathVariable("teacherCourseId") int teacherCourseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        if (user.getType().toString().equals(UserType.Student.name())) {
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

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newCourse(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        List<Subject> subjects = subjectRepository.findAll();
        mvc.addObject("subjectList", subjects);
        List<User> users = userRepository.findAll();
        List<User> teachers = new ArrayList<>();
        for (User user1 : users) {
            if (UserType.Teacher.toString().equals(user1.getType())) {
                teachers.add(user1);
            }
        }
        mvc.addObject("teacherList", teachers);
        return mvc;
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/edit/{dateId}", method = RequestMethod.POST)
    public ModelAndView editCourse(@PathVariable("dateId") int dateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/addCourseDate");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        CourseDate courseDate = courseDateRepository.findOne(dateId);
        mvc.addObject("date", courseDate.getDate());
        mvc.addObject("startTime", courseDate.getStartTime());
        mvc.addObject("finishTime", courseDate.getFinishTime());
        mvc.addObject("teacherCourseId", courseDate.getTeacherCourse().getId());
        mvc.addObject("dateId", dateId);
        mvc.addObject("isEdited", "True");
        return mvc;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{dateId}", method = RequestMethod.POST)
    public ModelAndView deleteCourse(@PathVariable("dateId") int dateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/adminCoursesList");
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

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/deleteCourse/{id}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("id") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/teacherCourseDates");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(courseId);
        teacherCourseRepository.delete(teacherCourse);
        mvc.addObject("message", "course.deleted");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        prepareAdminView(mvc, user);
        return mvc;
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView newCourse(@ModelAttribute("newCourseForm") NewCourseView newCourseForm, HttpSession session) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        String username = (String) session.getAttribute("username");
        if (isNotLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User teacher = userRepository.findByLogin(newCourseForm.getTeacherLogin());
        Subject subject = subjectRepository.findByName(newCourseForm.getSubjectName());
        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setDescription(newCourseForm.getDescription());
        teacherCourse.setStudentGroup(newCourseForm.getStudentGroup());
        teacherCourse.setSubject(subject);
        teacherCourse.setTeacher(teacher);

        teacherCourseRepository.save(teacherCourse);
        mvc.addObject("message", "course.added");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }

    @PreAuthorize("hasAuthority('STUDENT')")
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

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/addCourseDate/{teacherCourseId}", method = RequestMethod.POST)
    public ModelAndView newCourseDate(@ModelAttribute("courseDateForm") CourseDateView form, @PathVariable("teacherCourseId") int teacherCourseId, HttpSession session) {
        ModelAndView mvc = new ModelAndView("course/teacherCourseDates");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        CourseDate courseDate;
        TeacherCourse teacherCourse = teacherCourseRepository.findOne(teacherCourseId);
        if ("True".equalsIgnoreCase(form.getIsEdited())) {
            courseDate = courseDateRepository.findOne(Integer.valueOf(form.getDateId()));
            sendEditCourseDateNotification(teacherCourse, form, courseDate);
            courseDate.setDate(form.getDate());
            courseDate.setStartTime(form.getStartTime());
            courseDate.setFinishTime(form.getFinishTime());
            courseDateRepository.save(courseDate);
            mvc.addObject("message", "course.courseDateEdited");
            mvc.addObject("messageType", MessageType.SUCCESS.name());
        } else {
            courseDate = new CourseDate();
            courseDate.setTeacherCourse(teacherCourse);
            courseDate.setStartTime(form.getStartTime());
            courseDate.setFinishTime(form.getFinishTime());
            courseDate.setDate(form.getDate());
            courseDateRepository.save(courseDate);
            mvc.addObject("message", "course.courseDateAdded");
            mvc.addObject("messageType", MessageType.SUCCESS.name());
        }

        List<CourseDate> courseDates = courseDateRepository.findByTeacherCourse(teacherCourse);
        mvc.addObject("datesList", courseDates);

        return mvc;
    }

    private void sendEditCourseDateNotification(TeacherCourse teacherCourse, CourseDateView form, CourseDate courseDate) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByTeacherCourse(teacherCourse);
        for(StudentCourse studentCourse : studentCourses) {
            Notification notification = notificationRepository.findByUser(studentCourse.getStudent());
            if (notification.getChangeCourseDate().contains("EMAIL")) {
                emailService.sendEmail(studentCourse.getStudent().getEmail(), "Zmieniono termin zajęć",
                        "Zmieniono termin zajęć przedmiotu: " + studentCourse.getTeacherCourse().getSubject().getName()
                        + ". Poprzedni termin: " + courseDate.getDate() + ". Start: " + courseDate.getStartTime()
                        + ". Koniec: " + courseDate.getFinishTime() + ". Nowy termin: " + form.getDate() + ". Start: "
                        + form.getStartTime() + ". Koniec: " + form.getFinishTime());
            }
            if (notification.getChangeCourseDate().contains("SMS")) {
                smsService.sendSMS(studentCourse.getStudent().getEmail(),
                        "Zmieniono termin zajęć przedmiotu: " + studentCourse.getTeacherCourse().getSubject().getName()
                                + ". Poprzedni termin: " + courseDate.getDate() + ". Start: " + courseDate.getStartTime()
                                + ". Koniec: " + courseDate.getFinishTime() + ". Nowy termin: " + form.getDate() + ". Start: "
                                + form.getStartTime() + ". Koniec: " + form.getFinishTime());
            }
        }
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
        } else if (UserType.Teacher.name().equals(user.getType())) {
            prepareTeacherView(mvc, user);
        } else {
            prepareAdminView(mvc, user);
        }
    }

    private void prepareAdminView(ModelAndView mvc, User user) {
        List<TeacherCourse> coursesList = teacherCourseRepository.findAll();
        mvc.setViewName("course/adminCoursesList");
        mvc.addObject("coursesList", coursesList);
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
