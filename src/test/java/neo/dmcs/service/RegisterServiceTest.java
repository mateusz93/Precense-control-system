package neo.dmcs.service;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.enums.UserEnum;
import neo.dmcs.exception.*;
import neo.dmcs.model.Contact;
import neo.dmcs.model.User;
import neo.dmcs.util.Encryptor;
import neo.dmcs.view.security.LoginView;
import neo.dmcs.view.security.RegisterView;
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
 * @Author Mateusz Wieczorek, 20.04.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-test-config.xml"})
public class RegisterServiceTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private RegisterService registerService;

    private User user;

    @Before
    public void setUp() {
        Contact contact = new Contact();
        contact.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        contactDao.save(contact);

        user = new neo.dmcs.model.User();
        user.setContact(contact);
        user.setStatus(UserEnum.Status.ACTIVE.name());
        try {
            user.setPassword(Encryptor.encryption("zxcvbnmZ123$"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setFirstName("zxcvbnm");
        user.setLastName("fghjkajshdh");
        user.setLogin("zxcvbnmfghjkajshdh");
        user.setType(UserEnum.Type.Student.name());
        user.setLastLogin(new Timestamp((new Date()).getTime() - 100000000));

        userDao.save(user);
    }

    @After
    public void delete() {
        userDao.delete(user);
    }

    @Test(expected=FieldEmptyException.class)
    public void shouldThrowFieldEmptyException() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName(" ");

        registerService.accept(registerView);
    }

    @Test(expected=DifferentPasswordsException.class)
    public void shouldThrowDifferentPasswordsException() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("zxcvbnm");
        registerView.setConfirmPassword("fghfgh");
        registerView.setEmail("jhasgdjas@weq.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException01() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("zxcv");
        registerView.setConfirmPassword("zxcv");
        registerView.setEmail("jhasgdjas@weq.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException02() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("zxcvqazqaz");
        registerView.setConfirmPassword("zxcvqazqaz");
        registerView.setEmail("jhasgdjas@weq.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException03() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("Zxcvbnm");
        registerView.setConfirmPassword("Zxcvbnm");
        registerView.setEmail("jhasgdjas@weq.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException04() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("Zxcvbnm123");
        registerView.setConfirmPassword("Zxcvbnm123");
        registerView.setEmail("jhasgdjas@weq.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException05() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("zxcvbnm123");
        registerView.setConfirmPassword("zxcvbnm123");
        registerView.setEmail("jhasgdjas@weq.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException06() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("zxcvbnm123$");
        registerView.setConfirmPassword("zxcvbnm123$");
        registerView.setEmail("jhasgdjas@weq.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);
    }

    @Test(expected=EmailExistsException.class)
    public void shouldThrowEmailExistsException() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("Zxcvbnm123$");
        registerView.setConfirmPassword("Zxcvbnm123$");
        registerView.setEmail("kjasdhahdakjhdkjashdkjashdka@wp.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);
    }

    @Test
    public void generateUsernameTest() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("zxcvbnm");
        registerView.setLastName("fghjkajshdh");
        registerView.setPassword("Zxcvbnm123$");
        registerView.setConfirmPassword("Zxcvbnm123$");
        registerView.setEmail("kjasdhahdakjhdkjashsdfbbbbhdka@wp.pl");
        registerView.setType(UserEnum.Type.Student.name());

        registerService.accept(registerView);

        Contact contact2 = contactDao.findByEmail(registerView.getEmail());
        User user2 = userDao.findByContact(contact2);

        assertTrue(user2.getLogin().equals("zxcvbnmfghjkajshdh0"));
        
        userDao.delete(user2);
    }

}