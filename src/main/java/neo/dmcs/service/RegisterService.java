package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.Role;
import neo.dmcs.enums.UserStatus;
import neo.dmcs.enums.UserType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.EmailExistsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.model.Token;
import neo.dmcs.model.User;
import neo.dmcs.repository.TokenRepository;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.util.Encryptor;
import neo.dmcs.util.PasswordValidator;
import neo.dmcs.view.security.RegisterView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private static final String APPLICATION_ADDRESS = "http://localhost:8080";

    public User activateUser(User user) {
        user.setStatus(UserStatus.ACTIVE.name());
        return userRepository.save(user);
    }

    public String generateToken(User user) {
        String tokenAsString = UUID.randomUUID().toString();
        Token token = new Token();
        token.setUser(user);
        token.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(1)));
        token.setToken(tokenAsString);
        tokenRepository.save(token);
        return tokenAsString;
    }

    public String generateActivationLink(String token) {
        return APPLICATION_ADDRESS + "/register/registrationConfirm.html?token=" + token;
    }


    public User accept(RegisterView form) throws FieldEmptyException, DifferentPasswordsException, IncorrectPasswordException, EmailExistsException {
        validate(form);
        String username = generateUsername(form.getFirstName(), form.getLastName());
        User user = createUser(form, username);
        return userRepository.save(user);
    }

    private User createUser(RegisterView form, String username) {
        try {
            User user = new User();
            user.setEmail(form.getEmail());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setType(UserType.Student.toString().equalsIgnoreCase(form.getType()) ? Role.STUDENT : Role.TEACHER);
            user.setLogin(username);
            user.setPassword(Encryptor.encryption(form.getPassword()));
            user.setStatus(UserStatus.INACTIVE.name());
            return user;
        } catch (NoSuchAlgorithmException e) {
            log.error(e.toString());
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
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new EmailExistsException();
        }
    }

    private String generateUsername(String firstName, String lastName) {
        String username = firstName + lastName;

        for (int i = 0; i < 1000; ++i) {
            User user = userRepository.findByLogin(username);
            if (user == null) {
                break;
            }
            username = username + i;
        }
        return username;
    }

}
