package neo.dmcs.service;

import neo.dmcs.dao.ContactDao;
import neo.dmcs.dao.UserDao;
import neo.dmcs.enums.UserType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.util.Encryptor;
import neo.dmcs.view.user.ProfileView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.NoSuchAlgorithmException;

/**
 * @Author Mateusz Wieczorek, 27.04.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring-test-config.xml"})
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    private ProfileView profileView;

    @Before
    public void setUp() throws NoSuchAlgorithmException {
        profileView = new ProfileView();
        profileView.setFirstName("Mateusz");
        profileView.setLastName("Wieczorek");
        profileView.setGroup("TI3");
        profileView.setStreet("kanoniczna");
        profileView.setPhone("222333444");
        profileView.setPassword(Encryptor.encryption("zxcvbnmZ123$"));
        profileView.setConfirmPassword(Encryptor.encryption("zxcvbnmZ123$"));
        profileView.setType(UserType.Student.name());
        profileView.setEmail("kasdhnkjan@waea.pl");
        profileView.setCity("Lodz");
    }

    @Test(expected=FieldEmptyException.class)
    public void shouldThrowFieldEmptyExceptionException() throws FieldEmptyException, IncorrectPasswordException, DifferentPasswordsException {

        profileView.setFirstName("  ");
        profileService.update(profileView);
    }

    @Test(expected=DifferentPasswordsException.class)
    public void shouldThrowDifferentPasswordsException() throws FieldEmptyException, IncorrectPasswordException, DifferentPasswordsException, NoSuchAlgorithmException {

        profileView.setPassword("zxcvbnmZ123$");
        profileView.setConfirmPassword("zxcvbn2mZ123$");

        profileService.update(profileView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException() throws FieldEmptyException, IncorrectPasswordException, DifferentPasswordsException, NoSuchAlgorithmException {

        profileView.setPassword("zxcvbnmZ");
        profileView.setConfirmPassword("zxcvbnmZ");

        profileService.update(profileView);
    }

}