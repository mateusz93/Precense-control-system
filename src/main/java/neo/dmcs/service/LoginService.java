package neo.dmcs.service;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.enums.UserStatus;
import neo.dmcs.exception.*;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import neo.dmcs.util.Encryptor;
import neo.dmcs.view.security.LoginView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UserDao usersDao;

    @Autowired
    private ContactDao contactDao;


    public void validate(LoginView form) throws IncorrectEmailException, IncorrectPasswordException, IncorrectUserTypeException, UserNotActivedException, FieldEmptyException {

        if (!(StringUtils.isNotBlank(form.getPassword()) && StringUtils.isNotBlank(form.getType()) && StringUtils.isNotBlank(form.getEmail()))) {
            throw new FieldEmptyException("Field can not be empty");
        }

        Contact contact = getContact(form.getEmail());
        User user = usersDao.findByContact(contact);

        try {
            if (!Encryptor.encryption(form.getPassword()).equals(user.getPassword())) {
                throw new IncorrectPasswordException();
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }

        if (!user.getType().equals(form.getType())) {
            throw new IncorrectUserTypeException();
        }

        if (user.getStatus().equals(UserStatus.INACTIVE.name())) {
            throw new UserNotActivedException();
        }

        user.setLastLogin(new Timestamp((new Date()).getTime()));
        usersDao.update(user);
    }

    private Contact getContact(String email) throws IncorrectEmailException {
        try {
            return contactDao.findByEmail(email);
        } catch (NoResultException e) {
            throw new IncorrectEmailException();
        }
    }

}
