package neo.dmcs.service;

import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.util.Encryptor;
import neo.dmcs.util.PasswordValidator;
import neo.dmcs.view.user.ProfileGeneralView;
import neo.dmcs.view.user.ProfilePasswordView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Mateusz Wieczorek, 21.04.16.
 */
@Service
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private UserRepository userRepository;

    public void update(ProfileGeneralView form) throws FieldEmptyException, DifferentPasswordsException, IncorrectPasswordException {
        deleteWhiteCharacters(form);

        if (!areFieldsNotEmpty(form)) {
            throw new FieldEmptyException("Field can not be empty");
        }

        User user = getUpdatedUser(form);
        userRepository.save(user);
    }

    private User getUpdatedUser(ProfileGeneralView form) {
        User user = userRepository.findByEmail(form.getEmail());
        user.setGroup(form.getGroup());
        user.setPhone(form.getPhone());
        user.setCity(form.getCity());
        user.setStreet(form.getStreet());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        userRepository.save(user);
        return user;
    }

    private void deleteWhiteCharacters(ProfileGeneralView form) {
        form.setFirstName(form.getFirstName().trim());
        form.setLastName(form.getLastName().trim());
    }

    boolean areFieldsNotEmpty(ProfileGeneralView form) {
        return StringUtils.isNotBlank(form.getFirstName()) && StringUtils.isNotBlank(form.getLastName());
    }

    boolean arePasswordFieldsNotEmpty(ProfilePasswordView form) {
        return StringUtils.isNotBlank(form.getPassword()) && StringUtils.isNotBlank(form.getNewPassword())
                && StringUtils.isNotBlank(form.getAgainNewPassword());
    }

    public void updatePassword(ProfilePasswordView form, HttpSession httpSession) throws DifferentPasswordsException, FieldEmptyException, IncorrectPasswordException, NoSuchAlgorithmException {

        if (!arePasswordFieldsNotEmpty(form)) {
            throw new FieldEmptyException("Field can not be empty");
        }

        if (!form.getNewPassword().equals(form.getAgainNewPassword())) {
            throw new DifferentPasswordsException();
        }

        if (!PasswordValidator.validate(form.getNewPassword())) {
            throw new IncorrectPasswordException();
        }
        String username = (String) httpSession.getAttribute("username");
        User user = userRepository.findByLogin(username);
        user.setPassword(Encryptor.encryption(form.getNewPassword()));
        userRepository.save(user);
    }
}
