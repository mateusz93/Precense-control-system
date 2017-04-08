package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.User;
import neo.dmcs.service.StatisticService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static neo.dmcs.util.Const.MVC_NOT_LOGGED;
import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @author Mateusz Wieczorek on 11/5/16.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stats")
public class StatisticController {

    private final StatisticService service;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView save(HttpSession httpSession) {
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return service.prepareView(user);
    }

    @RequestMapping(value = "/globalPresenceAverage", method = RequestMethod.GET, produces = "application/json")
    public String globalPresenceAverage(HttpSession session) {
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return "";
        }
        return service.calculateGlobalPresenceAverage(user);
    }

    @RequestMapping(value = "/finalGradesAverage", method = RequestMethod.GET, produces = "application/json")
    public String finalGradesAverage(HttpSession session) {
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return "";
        }
        return service.calculateFinalGradesAverage(user);
    }

    @RequestMapping(value = "/globalGradesAverage", method = RequestMethod.GET, produces = "application/json")
    public String globalGradesAverage(HttpSession session) {
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return "";
        }
        return service.calculateGlobalGradesAverage(user);
    }

    @RequestMapping(value = "/subjectGradesAverage", method = RequestMethod.GET, produces = "application/json")
    public String subjectGradesAverage(HttpSession session) {
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return "";
        }
        return service.calculateSubjectGradesAverage(user);
    }
}
