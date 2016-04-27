package neo.dmcs.service;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.enums.UserStatus;
import neo.dmcs.enums.UserType;
import neo.dmcs.exception.*;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
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
    private UserDao userDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private LoginService loginService;

    private User user;

    @Before
    public void setUp() {
        Contact contact = new Contact();
        contact.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        contactDao.save(contact);

        user = new neo.dmcs.model.User();
        user.setContact(contact);
        user.setStatus(UserStatus.ACTIVE.name());
        try {
            user.setPassword(Encryptor.encryption("zxcvbnmZ123$"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setFirstName("zxcvbnm");
        user.setLastName("fghjkajshdh");
        user.setLogin("zxcvbnmfghjkajshdh123");
        user.setType(UserType.Student.name());
        user.setLastLogin(new Timestamp((new Date()).getTime() - 100000000));

        userDao.save(user);
    }

    @After
    public void delete() {
        userDao.delete(user);
    }


    @Test(expected=IncorrectEmailException.class)
    public void shouldThrowIncorrectEmailException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivedException {
        LoginView loginView = new LoginView();
        loginView.setEmail("q23463utd4hu234hsdt2bt7@wp.plasd");
        loginView.setPassword("o4b2ci7bc4t4ugbjwsadfsdfvdfg");
        loginView.setType(UserType.Student.name());

        loginService.validate(loginView);
    }

    @Test(expected=UserNotActivedException.class)
    public void shouldThrowUserNotActivedException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivedException {
        user.setStatus(UserStatus.INACTIVE.name());
        userDao.update(user);

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.Student.name());

        loginService.validate(loginView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivedException {

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("o4b2ci7bc4t4ugbjwsadfsdfvdfg");
        loginView.setType(UserType.Student.name());

        loginService.validate(loginView);
    }

    @Test(expected=IncorrectUserTypeException.class)
    public void shouldThrowIncorrectUserTypeException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivedException {

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.Teacher.name());

        loginService.validate(loginView);
    }

    @Test(expected=FieldEmptyException.class)
    public void shouldThrowFieldEmptyException() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivedException {

        LoginView loginView = new LoginView();
        loginView.setEmail(" ");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.Teacher.name());

        loginService.validate(loginView);
    }

    @Test
    public void lastLoginTest() throws IncorrectUserTypeException, IncorrectEmailException, IncorrectPasswordException, FieldEmptyException, UserNotActivedException {

        long lastLogin = user.getLastLogin().getTime();

        LoginView loginView = new LoginView();
        loginView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        loginView.setPassword("zxcvbnmZ123$");
        loginView.setType(UserType.Student.name());

        loginService.validate(loginView);

        user = userDao.findById(user.getId());
        long newLastLogin = user.getLastLogin().getTime();

        assertTrue(newLastLogin > lastLogin);
    }

}