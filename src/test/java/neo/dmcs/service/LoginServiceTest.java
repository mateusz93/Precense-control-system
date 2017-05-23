package neo.dmcs.service;

import neo.dmcs.Application;
import neo.dmcs.configuration.WebMvcConfig;
import neo.dmcs.configuration.WebSecurityConfig;
import neo.dmcs.enums.Role;
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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * @author Mateusz Wieczorek, 19.04.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
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
        user.setPassword(Encryptor.encryption("zxcvbnmZ123$"));
        user.setFirstName("zxcvbnm");
        user.setLastName("fghjkajshdh");
        user.setLogin("zxcvbnmfghjkajshdh123");
        user.setType(Role.STUDENT);
        user.setLastLogin(new Timestamp((new Date()).getTime() - 100000000));

        userRepository.save(user);
    }

    @After
    public void delete() {
        userRepository.delete(user);
    }


    @Test(expected=IncorrectEmailException.class)
    public void shouldThrowIncorrectEmailException() throws ValidationException {
        LoginView loginView = new LoginView();
        loginView.setEmail("q23463utd4hu234hsdt2bt7@wp.plasd");
        loginView.setPassword("o4b2ci7bc4t4ugbjwsadfsdfvdfg");
        loginView.setType(UserType.STUDENT.name());

        loginService.validate(loginView);
    }

    @Test(expected=UserNotActivatedException.class)
    public void shouldThrowUserNotActivedException() throws ValidationException {
        user.setStatus(UserStatus.INACTIVE.name());
        userRepository.save(user);

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.STUDENT.name());

        loginService.validate(loginView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException() throws ValidationException {

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("o4b2ci7bc4t4ugbjwsadfsdfvdfg");
        loginView.setType(UserType.STUDENT.name());

        loginService.validate(loginView);
    }

    @Test(expected=IncorrectUserTypeException.class)
    public void shouldThrowIncorrectUserTypeException() throws ValidationException {

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.TEACHER.name());

        loginService.validate(loginView);
    }

    @Test(expected=FieldEmptyException.class)
    public void shouldThrowFieldEmptyException() throws ValidationException {

        LoginView loginView = new LoginView();
        loginView.setEmail(" ");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.TEACHER.name());

        loginService.validate(loginView);
    }

    @Test
    public void lastLoginTest() throws ValidationException {

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