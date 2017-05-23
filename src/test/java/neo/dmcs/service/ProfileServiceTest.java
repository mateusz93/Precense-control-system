package neo.dmcs.service;

import neo.dmcs.Application;
import neo.dmcs.enums.UserType;
import neo.dmcs.exception.DifferentPasswordsException;
import neo.dmcs.exception.FieldEmptyException;
import neo.dmcs.exception.IncorrectPasswordException;
import neo.dmcs.exception.ValidationException;
import neo.dmcs.util.Encryptor;
import neo.dmcs.view.user.ProfileGeneralView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

/**
 * @author Mateusz Wieczorek, 27.04.16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
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
    public void shouldThrowFieldEmptyExceptionException() throws ValidationException {

        profileGeneralView.setFirstName("  ");
        profileService.update(profileGeneralView);
    }

    @Test(expected=DifferentPasswordsException.class)
    public void shouldThrowDifferentPasswordsException() throws ValidationException {

        profileGeneralView.setPassword("zxcvbnmZ123$");
        profileGeneralView.setConfirmPassword("zxcvbn2mZ123$");

        profileService.update(profileGeneralView);
    }

    @Test(expected=IncorrectPasswordException.class)
    public void shouldThrowIncorrectPasswordException() throws ValidationException {

        profileGeneralView.setPassword("zxcvbnmZ");
        profileGeneralView.setConfirmPassword("zxcvbnmZ");

        profileService.update(profileGeneralView);
    }

}