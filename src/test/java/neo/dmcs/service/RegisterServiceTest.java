package neo.dmcs.service;

import neo.dmcs.enums.UserStatus;
import neo.dmcs.enums.UserType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.EmailExistsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.model.User;
import neo.dmcs.repository.UserRepository;
import neo.dmcs.util.Encryptor;
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
    private UserRepository userRepository;
    @Autowired
    private RegisterService registerService;

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
        user.setLogin("zxcvbnmfghjkajshdh");
        user.setType(UserType.STUDENT.name());
        user.setLastLogin(new Timestamp((new Date()).getTime() - 100000000));

        userRepository.save(user);
    }

    @After
    public void delete() {
        userRepository.delete(user);
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
        registerView.setType(UserType.STUDENT.name());

        registerService.accept(registerView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException() throws FieldEmptyException, DifferentPasswordsException, EmailExistsException, IncorrectPasswordException {

        RegisterView registerView = new RegisterView();
        registerView.setFirstName("jhgvhgvh");
        registerView.setLastName("hb");
        registerView.setPassword("zxcv");
        registerView.setConfirmPassword("zxcv");
        registerView.setEmail("jhasgdjas@weq.pl");
        registerView.setType(UserType.STUDENT.name());

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
        registerView.setType(UserType.STUDENT.name());

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
        registerView.setType(UserType.STUDENT.name());

        registerService.accept(registerView);

        User user2 = userRepository.findByEmail(registerView.getEmail());

        assertTrue(user2.getLogin().equals("zxcvbnmfghjkajshdh0"));
        
        userRepository.delete(user2);
    }

}