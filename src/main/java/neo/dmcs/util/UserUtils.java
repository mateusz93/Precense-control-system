package neo.dmcs.util;

import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * @author Mateusz Wieczorek on 11/2/16.
 */
@Component
public class UserUtils {

    private static UserRepository userRepository;

    @Autowired
    public UserUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static boolean isNotLogged(String username) {
        return !StringUtils.isNotBlank(username);
    }

    public static boolean isNotLogged(User user) {
        return !(user != null && StringUtils.isNotBlank(user.getLogin()));
    }

    public static User getUserFromSession(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        if (username == null) {
            return null;
        }
        return userRepository.findByLogin(username);
    }
}
