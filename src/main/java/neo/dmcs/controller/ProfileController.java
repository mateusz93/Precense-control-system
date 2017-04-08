package neo.dmcs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.User;
import neo.dmcs.service.ProfileService;
import neo.dmcs.view.user.ProfileGeneralView;
import neo.dmcs.view.user.ProfileNotificationView;
import neo.dmcs.view.user.ProfilePasswordView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Locale;

import static neo.dmcs.util.Const.MVC_NOT_LOGGED;
import static neo.dmcs.util.UserUtils.getUserFromSession;
import static neo.dmcs.util.UserUtils.isNotLogged;

/**
 * @author Mateusz Wieczorek
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private static final String MVC_DEFAULT = "user/profile";

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView profile(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView(MVC_DEFAULT);
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return profileService.prepareGeneralProfileView(mvc, user);
    }

    @RequestMapping(value = "/general", method = RequestMethod.POST)
    public ModelAndView profileGeneral(@ModelAttribute("profileForm") ProfileGeneralView form,
                                       HttpSession httpSession, Locale locale) {
        ModelAndView mvc = new ModelAndView(MVC_DEFAULT);
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return profileService.prepareGeneralProfileView(form, mvc, user, locale);
    }

    @RequestMapping(value = "/notification", method = RequestMethod.POST)
    public ModelAndView profileNotification(@ModelAttribute("profileForm") ProfileNotificationView form,
                                            HttpSession httpSession, Locale locale) {
        ModelAndView mvc = new ModelAndView(MVC_DEFAULT);
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return profileService.prepareNotificationProfileView(form, mvc, user, locale);
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ModelAndView profilePassword(@ModelAttribute("profileForm") ProfilePasswordView form,
                                        HttpSession httpSession, Locale locale) {
        ModelAndView mvc = new ModelAndView(MVC_DEFAULT);
        User user = getUserFromSession(httpSession);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return profileService.updatePassword(form, user, mvc, locale);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file,
                                         HttpSession session, Locale locale) {
        ModelAndView mvc = new ModelAndView(MVC_DEFAULT);
        User user = getUserFromSession(session);
        if (isNotLogged(user)) {
            return new ModelAndView(MVC_NOT_LOGGED);
        }
        return profileService.uploadFile(file, user, mvc, locale);
    }
}
