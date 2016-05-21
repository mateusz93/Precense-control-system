package neo.dmcs.controller;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.enums.MessageType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import neo.dmcs.service.ProfileService;
import neo.dmcs.view.user.ProfileView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

/**
 * @Author Mateusz Wieczorek
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView profile(HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("user/profile");
        String username = (String) httpSession.getAttribute("username");
        if (!StringUtils.isNotBlank(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }
        User user = userDao.findByUsername(username);
        prepareProfileView(mvc, user.getContact().getEmail());
        return mvc;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView profile(@ModelAttribute("profileForm") ProfileView form, HttpSession httpSession) {
        ModelAndView mvc = new ModelAndView("user/profile");

        try {
            profileService.update(form);
        } catch (FieldEmptyException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "emptyField");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        } catch (DifferentPasswordsException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "register.differentPasswords");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        } catch (IncorrectPasswordException e) {
            logger.error(e.getMessage());
            mvc.addObject("message", "register.incorrectPassword");
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        }

        logger.debug("Profile updated");
        mvc.addObject("message", "profile.updated");
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        prepareProfileView(mvc, form.getEmail());
        return mvc;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file,
                                         RedirectAttributes redirectAttributes, HttpSession session) {
        ModelAndView mvc = new ModelAndView("user/profile");
        String username = (String) session.getAttribute("username");
        if (!StringUtils.isNotBlank(username)) {
            mvc.setViewName("security/login");
            return mvc;
        }

        if (!file.isEmpty()) {
            try {
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File("src/main/webapp/WEB-INF/resources/images/" + username)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                mvc.addObject("message", "profile.upload.success");
                mvc.addObject("messageType", MessageType.SUCCESS.name());
            } catch (Exception e) {
                mvc.addObject("message", "profile.upload.fail");
                mvc.addObject("messageType", MessageType.DANGER.name());
            }
        } else {
            mvc.addObject("message", "profile.upload.empty");
            mvc.addObject("messageType", MessageType.DANGER.name());
        }

        return mvc;
    }

    private void prepareProfileView(ModelAndView mvc, String email) {
        Contact contact = contactDao.findByEmail(email);
        User user = userDao.findByContact(contact);
        mvc.addObject("firstName", user.getFirstName());
        mvc.addObject("lastName", user.getLastName());
        mvc.addObject("ID", user.getId());
        mvc.addObject("email", user.getContact().getEmail());
        mvc.addObject("type", user.getType());
        mvc.addObject("city", user.getContact().getCity());
        mvc.addObject("group", user.getContact().getGroup());
        mvc.addObject("phone", user.getContact().getPhone());
        mvc.addObject("street", user.getContact().getStreet());
        File f = new File("src/main/webapp/WEB-INF/resources/images/" + user.getLogin());
        if (f.exists() && !f.isDirectory()) {
            mvc.addObject("photoPath", "/resources/images/" + user.getLogin());
        } else {
            mvc.addObject("photoPath", "/resources/images/default.png");
        }
    }
}
