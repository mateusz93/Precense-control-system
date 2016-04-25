package neo.dmcs.service;

import neo.dmcs.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Mateusz Wieczorek, 02.04.16.
 */
@Service("userService")
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UserDao usersDao;
}
