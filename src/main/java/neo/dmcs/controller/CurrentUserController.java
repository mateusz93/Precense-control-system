package neo.dmcs.controller;

import lombok.extern.slf4j.Slf4j;
import neo.dmcs.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Mateusz Wieczorek on 14.03.2017.
 */
@Slf4j
@ControllerAdvice
public class CurrentUserController {

    @ModelAttribute("currentUser")
    public User getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (User) authentication.getPrincipal();
    }

}