package neo.dmcs.service;

import neo.dmcs.enums.UserStatus;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.EmailExistsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.util.Encryptor;
import neo.dmcs.util.PasswordValidator;
import neo.dmcs.view.security.RegisterView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Service
public class RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    @Autowired
    private UserRepository userRepository;

    public void accept(RegisterView form) throws FieldEmptyException, DifferentPasswordsException, IncorrectPasswordException, EmailExistsException {
        validate(form);
        String username = generateUsername(form.getFirstName(), form.getLastName());
        User user = createUser(form, username);
        userRepository.save(user);
    }

    private User createUser(RegisterView form, String username) {
        try {
            User user = new User();
            user.setEmail(form.getEmail());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setType(form.getType());
            user.setLogin(username);
            user.setPassword(Encryptor.encryption(form.getPassword()));
            user.setStatus(UserStatus.INACTIVE.toString());
            return user;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.toString());
        }
        return null;
    }

    private void validate(RegisterView form) throws FieldEmptyException, DifferentPasswordsException, IncorrectPasswordException, EmailExistsException {

        if (!StringUtils.isNotBlank(form.getEmail())) {
            throw new FieldEmptyException("Email address not passed");
        }
        if (!(StringUtils.isNotBlank(form.getConfirmPassword()) && StringUtils.isNotBlank(form.getPassword()))) {
            throw new FieldEmptyException("Password not passed");
        }
        if (!StringUtils.isNotBlank(form.getFirstName())) {
            throw new FieldEmptyException("Firstname not passed");
        }
        if (!StringUtils.isNotBlank(form.getLastName())) {
            throw new FieldEmptyException("Lastname not passed");
        }

        validatePassword(form);
        checkEmail(form.getEmail());
    }

    private void validatePassword(RegisterView form) throws DifferentPasswordsException, IncorrectPasswordException {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new DifferentPasswordsException();
        }

        if (!PasswordValidator.validate(form.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }

    private void checkEmail(String email) throws EmailExistsException {
        try {
            userRepository.findByEmail(email);
        } catch (NoResultException e) {
            return;
        }
        throw new EmailExistsException();
    }

    private String generateUsername(String firstName, String lastName) {
        String username = firstName + lastName;

        for (int i = 0; i < 100; ++i) {
            try {
                userRepository.findByLogin(username);
            } catch (NoResultException e) {
                return username;
            }
            username = username + i;
        }
        return username;
    }

}
