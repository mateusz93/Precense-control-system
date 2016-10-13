package neo.dmcs.service;

import neo.dmcs.repository.ContactRepository;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import neo.dmcs.util.PasswordValidator;
import neo.dmcs.view.user.ProfileView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Mateusz Wieczorek, 21.04.16.
 */
@Service
public class ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private UserRepository usersDao;

    @Autowired
    private ContactRepository contactRepository;

    public void update(ProfileView form) throws FieldEmptyException, DifferentPasswordsException, IncorrectPasswordException {
        deleteWhiteCharacters(form);

        if (!areFieldsNotEmpty(form)) {
            throw new FieldEmptyException("Field can not be empty");
        }

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new DifferentPasswordsException();
        }

        if (!PasswordValidator.validate(form.getPassword())) {
            throw new IncorrectPasswordException();
        }

        User user = getUpdatedUser(form);
        usersDao.save(user);
    }

    private User getUpdatedUser(ProfileView form) {
        Contact contact = contactRepository.findByEmail(form.getEmail());
        User user = usersDao.findByContact(contact);
        contact.setGroup(form.getGroup());
        contact.setPhone(form.getPhone());
        contact.setCity(form.getCity());
        contact.setStreet(form.getStreet());
        contactRepository.save(contact);
        contact = contactRepository.findByEmail(form.getEmail());
        user.setContact(contact);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        return user;
    }

    private void deleteWhiteCharacters(ProfileView form) {
        form.setFirstName(form.getFirstName().trim());
        form.setLastName(form.getLastName().trim());
    }

    boolean areFieldsNotEmpty(ProfileView form) {
        return StringUtils.isNotBlank(form.getPassword()) && StringUtils.isNotBlank(form.getConfirmPassword())
                && StringUtils.isNotBlank(form.getFirstName()) && StringUtils.isNotBlank(form.getLastName());
    }
}
