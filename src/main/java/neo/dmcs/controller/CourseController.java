package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import neo.dmcs.model.User;
import neo.dmcs.service.CourseService;
import neo.dmcs.view.course.CourseDateView;
import neo.dmcs.view.course.NewCourseView;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Locale;

import static neo.dmcs.util.Const.MVC_NOT_LOGGED;
import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @author Mateusz Wieczorek on 25.03.16.
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView course(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return courseService.prepareView(mvc, user);
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/info/{teacherCourseId}", method = RequestMethod.GET)
    public ModelAndView info(@PathVariable("teacherCourseId") int teacherCourseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return courseService.prepareViewByCourseId(teacherCourseId, mvc, user);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newCourse(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return courseService.prepareNewView(mvc);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/edit/{dateId}", method = RequestMethod.POST)
    public ModelAndView editCourse(@PathVariable("dateId") int dateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/addCourseDate");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return courseService.prepareEditViewByDateId(dateId, mvc);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{dateId}", method = RequestMethod.POST)
    public ModelAndView deleteCourse(@PathVariable("dateId") int dateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/adminCoursesList");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return courseService.deleteCourseByDateId(dateId, mvc);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/deleteCourse/{id}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("id") int courseId, HttpSession httpSession, Locale locale) {
        ModelAndView mvc = new ModelAndView("course/teacherCourseDates");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return courseService.deleteCourseById(courseId, locale, mvc);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView newCourse(@ModelAttribute("newCourseForm") NewCourseView newCourseForm, HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView("course/addCourse");
        String username = (String) session.getAttribute("username");
        if (isNotLogged(username)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return courseService.saveNewCourse(newCourseForm, locale, mvc);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/addOne/{teacherCourseId}", method = RequestMethod.GET)
    public ModelAndView newOne(@PathVariable("teacherCourseId") int teacherCourseId, HttpSession session) {
        ModelAndView mvc = new ModelAndView("course/addCourseDate");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        mvc.addObject("teacherCourseId", teacherCourseId);
        return mvc;
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/addCourseDate/{teacherCourseId}", method = RequestMethod.POST)
    public ModelAndView newCourseDate(@ModelAttribute("courseDateForm") CourseDateView form, @PathVariable("teacherCourseId") int teacherCourseId,
                                      HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView("course/teacherCourseDates");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return courseService.addCourseDateByCourseId(teacherCourseId, form, locale, mvc);
    }

}
