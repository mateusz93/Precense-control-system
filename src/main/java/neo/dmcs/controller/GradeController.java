package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.User;
import neo.dmcs.service.GradeService;
import neo.dmcs.view.grade.TeacherAddGradeView;
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
 * @author Mateusz Wieczorek on 11/6/16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView course(HttpSession session) {
        ModelAndView mvc = new ModelAndView("grade/studentGrades");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return gradeService.prepareView(mvc, user);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/info/{courseId}", method = RequestMethod.GET)
    public ModelAndView gradesInfo(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("grade/studentGradesDetails");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return gradeService.prepareStudentGradesDetails(courseId, mvc);
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public ModelAndView grades(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("grade/studentGradesDetails");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return gradeService.getGradesByCourseId(courseId, mvc, user);
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/new/{courseId}/{studentId}", method = RequestMethod.GET)
    public ModelAndView grades(@PathVariable("courseId") int courseId,
                               @PathVariable("studentId") int studentId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("grade/studentGradesDetails");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return gradeService.newCourseByCourseIdAndStudentId(courseId, studentId, mvc, user);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/save/{courseId}/{studentId}", method = RequestMethod.POST)
    public ModelAndView saveGrade(@PathVariable("courseId") int courseId,
                                  @PathVariable("studentId") int studentId,
                                  @ModelAttribute("addGrade") TeacherAddGradeView teacherAddGradeView,
                                  HttpSession httpSession, Locale locale) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return gradeService.updateCourseGrades(courseId, studentId, teacherAddGradeView, locale, mvc, user);
    }

}
