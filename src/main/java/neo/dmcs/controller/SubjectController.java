package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.model.Field;
import neo.dmcs.model.Subject;
import neo.dmcs.model.User;
import neo.dmcs.repository.FieldRepository;
import neo.dmcs.repository.SubjectRepository;
import neo.dmcs.view.course.SubjectView;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @Author Mateusz Wieczorek on 14.01.2017.
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final FieldRepository fieldRepository;
    private final MessageSource messageSource;

    @PreAuthorize("hasAuthority('TEACHER') or hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newSubject(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("subject/addSubject");
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            mvc.setViewName("security/login");
            return mvc;
        }

        List<Field> fields = fieldRepository.findAll();
        mvc.addObject("fieldList", fields);

        return mvc;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView newSubject(@ModelAttribute("newSubjectForm") SubjectView subjectView, HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView("subject/addSubject");
        String username = (String) session.getAttribute("username");
        if (isNotLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        Field field = fieldRepository.findByName(subjectView.getField());
        Subject subject = new Subject();
        subject.setName(subjectView.getName());
        subject.setDescription(subjectView.getDescription());
        subject.setField(field);
        subject.setMinQuantity(subjectView.getMinQuantity());
        subject.setQuantity(subjectView.getQuantity());
        subject.setYearOfStudy(subjectView.getYearOfStudy());

        subjectRepository.save(subject);
        List<Field> fields = fieldRepository.findAll();
        mvc.addObject("fieldList", fields);
        mvc.addObject("message", messageSource.getMessage("subject.added", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        return mvc;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/delete/{subjectId}", method = RequestMethod.POST)
    public ModelAndView deleteSubject(@PathVariable("subjectId") String subjectId, HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView("subject/addSubject");
        String username = (String) session.getAttribute("username");
        if (isNotLogged(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }

        Subject subject = subjectRepository.findOne(Integer.valueOf(subjectId));
        subjectRepository.delete(subject);
        mvc.addObject("message", messageSource.getMessage("subject.deleted", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        prepareView(mvc, null);
        return mvc;
    }

    private void prepareView(ModelAndView mvc, User user) {
        List<Subject> subjectList = subjectRepository.findAll();
        mvc.setViewName("subject/subjectList");
        mvc.addObject("subjectList", subjectList);
    }
}
