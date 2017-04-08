package neo.dmcs.service;

import neo.dmcs.enums.UserStatus;
import neo.dmcs.enums.UserType;
import neo.dmcs.exception.*;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.util.Encryptor;
import neo.dmcs.view.security.LoginView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * @Author Mateusz Wieczorek, 19.04.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-test-config.xml"})
public class LoginServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginService loginService;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        user.setStatus(UserStatus.ACTIVE.name());
        try {
            user.setPassword(Encryptor.encryption("zxcvbnmZ123$"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setFirstName("zxcvbnm");
        user.setLastName("fghjkajshdh");
        user.setLogin("zxcvbnmfghjkajshdh123");
        user.setType(UserType.STUDENT.name());
        user.setLastLogin(new Timestamp((new Date()).getTime() - 100000000));

        userRepository.save(user);
    }

    @After
    public void delete() {
        userRepository.delete(user);
    }


    @Test(expected=IncorrectEmailException.class)
    public void shouldThrowIncorrectEmailException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivatedException {
        LoginView loginView = new LoginView();
        loginView.setEmail("q23463utd4hu234hsdt2bt7@wp.plasd");
        loginView.setPassword("o4b2ci7bc4t4ugbjwsadfsdfvdfg");
        loginView.setType(UserType.STUDENT.name());

        loginService.validate(loginView);
    }

    @Test(expected=UserNotActivatedException.class)
    public void shouldThrowUserNotActivedException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivatedException {
        user.setStatus(UserStatus.INACTIVE.name());
        userRepository.save(user);

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.STUDENT.name());

        loginService.validate(loginView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivatedException {

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("o4b2ci7bc4t4ugbjwsadfsdfvdfg");
        loginView.setType(UserType.STUDENT.name());

        loginService.validate(loginView);
    }

    @Test(expected=IncorrectUserTypeException.class)
    public void shouldThrowIncorrectUserTypeException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivatedException {

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.TEACHER.name());

        loginService.validate(loginView);
    }

    @Test(expected=FieldEmptyException.class)
    public void shouldThrowFieldEmptyException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivatedException {

        LoginView loginView = new LoginView();
        loginView.setEmail(" ");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.TEACHER.name());

        loginService.validate(loginView);
    }

    @Test
    public void lastLoginTest() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivatedException {

        long lastLogin = user.getLastLogin().getTime();

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.STUDENT.name());

        loginService.validate(loginView);

        user = userRepository.findOne(user.getId());
        long newLastLogin = user.getLastLogin().getTime();

        assertTrue(newLastLogin > lastLogin);
    }

}