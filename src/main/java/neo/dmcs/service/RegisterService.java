package neo.dmcs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.dmcs.enums.MessageType;
import neo.dmcs.enums.Role;
import neo.dmcs.enums.UserStatus;
import neo.dmcs.enums.UserType;
import neo.dmcs.exception.*;
import neo.dmcs.model.Token;
import neo.dmcs.model.User;
import neo.dmcs.repository.TokenRepository;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.util.Encryptor;
import neo.dmcs.util.PasswordValidator;
import neo.dmcs.view.security.RegisterView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Mateusz Wieczorek, 30.03.16.
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RegisterService {

    private final MessageSource messageSource;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private static final String APPLICATION_ADDRESS = "http://localhost:8080";

    private User activateUser(User user) {
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

    public String generateActivationLink(String token, String processIntanceId) {
        return APPLICATION_ADDRESS + "/register/registrationConfirm.html?token=" + token
                + "&processInstanceId=" + processIntanceId;
    }

    public User accept(RegisterView form) throws ValidationException {
        validate(form);
        String username = generateUsername(form.getFirstName(), form.getLastName());
        User user = createUser(form, username);
        return userRepository.save(user);
    }

    public User createUser(RegisterView form, String username) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setType(UserType.STUDENT.toString().equalsIgnoreCase(form.getType()) ? Role.STUDENT : Role.TEACHER);
        user.setLogin(username);
        user.setPassword(Encryptor.encryption(form.getPassword()));
        user.setStatus(UserStatus.INACTIVE.name());
        return user;
    }

    public void validate(RegisterView form) throws ValidationException {
        validateEmpties(form);
        validatePassword(form);
        checkEmailExists(form.getEmail());
    }

    private void validateEmpties(RegisterView form) throws ValidationException {
        if (!StringUtils.isNotBlank(form.getFirstName())) {
            throw new FieldEmptyException("emptyField", "view.profile.firstname");
        }
        if (!StringUtils.isNotBlank(form.getLastName())) {
            throw new FieldEmptyException("emptyField", "view.profile.lastname");
        }
        if (!StringUtils.isNotBlank(form.getEmail())) {
            throw new FieldEmptyException("emptyField", "view.profile.firstname");
        }
        if (!StringUtils.isNotBlank(form.getPassword())) {
            throw new FieldEmptyException("emptyField", "view.profile.password");
        }
        if (!StringUtils.isNotBlank(form.getConfirmPassword())) {
            throw new FieldEmptyException("emptyField", "view.profile.password.confirme");
        }
    }

    private void validatePassword(RegisterView form) throws ValidationException {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            throw new DifferentPasswordsException("register.differentPasswords");
        }
        if (!PasswordValidator.validate(form.getPassword())) {
            throw new IncorrectPasswordException("register.incorrectPassword");
        }
    }

    private void checkEmailExists(String email) throws ValidationException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new EmailExistsException("register.emailUsed");
        }
    }

    public String generateUsername(String firstName, String lastName) {
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

    public ModelAndView registerUser(RegisterView form, ModelAndView mvc, Locale locale) {
        try {
            User user = accept(form);
            String token = generateToken(user);
            String activationLink = generateActivationLink(token);
            emailService.sendActivationLink(user, activationLink);
        } catch (ValidationException e) {
            mapFields(form, mvc);
            if (e instanceof FieldEmptyException) {
                String fieldName = messageSource.getMessage(((FieldEmptyException) e).getFieldName(), null, locale);
                String message = messageSource.getMessage(e.getMessage(), null, locale);
                message = message.replace("{}", "'" + fieldName + "'");
                mvc.addObject("message", message);
                mvc.addObject("messageType", MessageType.DANGER.name());
                mvc.setViewName("security/register");
                return mvc;
            }
            mvc.addObject("message", messageSource.getMessage(e.getMessage(), null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            mvc.setViewName("security/register");
            return mvc;
        }
        cleanFields(form, mvc);
        mvc.addObject("message", messageSource.getMessage("register.userCreated", null, locale));
        mvc.addObject("messageType", MessageType.WARNING.name());
        return mvc;
    }

    private void mapFields(RegisterView form, ModelAndView mvc) {
        mvc.addObject("newEmail", form.getEmail());
        mvc.addObject("firstName", form.getFirstName());
        mvc.addObject("lastName", form.getLastName());
        mvc.addObject("password", form.getPassword());
        mvc.addObject("confirmPassword", form.getConfirmPassword());
        mvc.addObject("type", form.getType());
    }

    private void cleanFields(RegisterView form, ModelAndView mvc) {
        mvc.addObject("newEmail", "");
        mvc.addObject("firstName", "");
        mvc.addObject("lastName", "");
        mvc.addObject("password", "");
        mvc.addObject("confirmPassword", "");
        mvc.addObject("type", form.getType());
    }

    public ModelAndView confirmRegistration(WebRequest request, String tokenAsString) {
        Locale locale = request.getLocale();
        ModelAndView mvc = new ModelAndView("redirect:/login");
        try {
            Token token = tokenRepository.findByToken(tokenAsString);
            validateToken(token);
            activateUser(token.getUser());
            tokenRepository.delete(token);
        } catch (ValidationException e) {
            mvc.addObject("message", messageSource.getMessage(e.getMessage(), null, locale));
            mvc.addObject("messageType", MessageType.DANGER.name());
            return mvc;
        }
        mvc.addObject("message", messageSource.getMessage("view.register.confirmed", null, locale));
        mvc.addObject("messageType", MessageType.SUCCESS.name());
        mvc.setViewName("security/register");
        return mvc;
    }

    public void validateToken(Token token) throws ValidationException {
        if (token == null) {
            throw new TokenIncorrectException("register.token.incorrect");
        }
//        if (token.getExpiryDate().getTime() < Date.valueOf(LocalDate.now()).getTime()) {
//            throw new TokenExpireException("register.token.expiry");
//        }
    }
}
