package neo.dmcs.service;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Mateusz Wieczorek, 14.05.16.
 */
@Service
public class CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private UserDao usersDao;

    @Autowired
    private ContactDao contactDao;
}
