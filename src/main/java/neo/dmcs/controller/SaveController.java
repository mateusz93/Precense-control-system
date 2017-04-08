package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.User;
import neo.dmcs.service.SaveService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
 * @author Mateusz Wieczorek, 14.05.16.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/saves")
public class SaveController {

    private final SaveService saveService;

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView save(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("course/studentSaves");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return saveService.prepareView(mvc, user);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping(value = "/{courseId}", method = RequestMethod.POST)
    public ModelAndView signOut(@PathVariable("courseId") int courseId, HttpSession httpSession, Locale locale) {
        ModelAndView mvc = new ModelAndView("course/studentSaves");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return saveService.prepareViewByCourseId(courseId, mvc, user, locale);
    }
}
