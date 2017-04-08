package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.User;
import neo.dmcs.service.PresenceService;
import neo.dmcs.view.precense.CheckPresenceView;
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
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/precenses")
public class PresenceController {

    private final PresenceService presenceService;

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView precense(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return presenceService.prepareView(mvc, user);
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/info/{courseId}", method = RequestMethod.GET)
    public ModelAndView precenseInfo(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return presenceService.preparePresenceStatuses(courseId, mvc, user);
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/check/{courseDateId}", method = RequestMethod.POST)
    public ModelAndView precensecheck(@PathVariable("courseDateId") int courseDateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("precense/checkPrecense");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return presenceService.preparePresencesList(courseDateId, mvc);
    }

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/courseDates/{courseId}", method = RequestMethod.POST)
    public ModelAndView courseDates(@PathVariable("courseId") int courseId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return presenceService.prepareCourseDates(courseId, mvc, user);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/cancel/{courseDateId}", method = RequestMethod.POST)
    public ModelAndView cancel(@PathVariable("courseDateId") int courseDateId, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("precense/checkPrecense");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return presenceService.cancelCourseDate(courseDateId, mvc, user);
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/update/{courseDateId}", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("studentWrapper") CheckPresenceView studentWrapper,
                               @PathVariable("courseDateId") int courseDateId, HttpSession httpSession, Locale locale) {
        ModelAndView mvc = new ModelAndView("precense/checkPrecense");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return presenceService.updateCourseDate(studentWrapper, courseDateId, mvc, locale);
    }

}
