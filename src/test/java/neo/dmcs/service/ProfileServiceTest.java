package neo.dmcs.service;

import neo.dmcs.enums.UserType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.util.Encryptor;
import neo.dmcs.view.user.ProfileGeneralView;
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

    private ProfileGeneralView profileGeneralView;

    @Before
    public void setUp() throws NoSuchAlgorithmException {
        profileGeneralView = new ProfileGeneralView();
        profileGeneralView.setFirstName("Mateusz");
        profileGeneralView.setLastName("Wieczorek");
        profileGeneralView.setGroup("TI3");
        profileGeneralView.setStreet("kanoniczna");
        profileGeneralView.setPhone("222333444");
        profileGeneralView.setPassword(Encryptor.encryption("zxcvbnmZ123$"));
        profileGeneralView.setConfirmPassword(Encryptor.encryption("zxcvbnmZ123$"));
        profileGeneralView.setType(UserType.STUDENT.name());
        profileGeneralView.setEmail("kasdhnkjan@waea.pl");
        profileGeneralView.setCity("Lodz");
    }

    @Test(expected=FieldEmptyException.class)
    public void shouldThrowFieldEmptyExceptionException() throws FieldEmptyException, IncorrectPasswordException, DifferentPasswordsException {

        profileGeneralView.setFirstName("  ");
        profileService.update(profileGeneralView);
    }

    @Test(expected=DifferentPasswordsException.class)
    public void shouldThrowDifferentPasswordsException() throws FieldEmptyException, IncorrectPasswordException, DifferentPasswordsException, NoSuchAlgorithmException {

        profileGeneralView.setPassword("zxcvbnmZ123$");
        profileGeneralView.setConfirmPassword("zxcvbn2mZ123$");

        profileService.update(profileGeneralView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException() throws FieldEmptyException, IncorrectPasswordException, DifferentPasswordsException, NoSuchAlgorithmException {

        profileGeneralView.setPassword("zxcvbnmZ");
        profileGeneralView.setConfirmPassword("zxcvbnmZ");

        profileService.update(profileGeneralView);
    }

}