package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.User;
import neo.dmcs.service.SubjectService;
import neo.dmcs.view.course.SubjectView;
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
 * @author Mateusz Wieczorek on 14.01.2017.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView course(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView();
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return subjectService.prepareView(mvc);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newSubject(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("subject/addSubject");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return subjectService.prepareAddNewSubjectView(mvc);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView newSubject(@ModelAttribute("newSubjectForm") SubjectView subjectView, HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView("subject/addSubject");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return subjectService.addNewSubject(subjectView, mvc, locale);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{subjectId}", method = RequestMethod.POST)
    public ModelAndView deleteSubject(@PathVariable("subjectId") String subjectId, HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView("subject/addSubject");
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return subjectService.deleteSubjectById(subjectId, mvc, locale);
    }

}
