package neo.dmcs.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Author Mateusz Wieczorek, 27.04.16.
 */
public class PasswordValidatorTest {

    /*
    Too short, no number, no upperCase letter, no special character
     */
    @Test
    public void validationTest01() {
        assertFalse(PasswordValidator.validate("zxcv"));
    }

    /*
    No number, no upperCase letter, no special character
     */
    @Test
    public void validationTest02() {
        assertFalse(PasswordValidator.validate("zxcvqazqaz"));
    }

    /*
    No number, no special character
     */
    @Test
    public void validationTest03() {
        assertFalse(PasswordValidator.validate("Zxcvbnmm"));
    }

    /*
    No special character
     */
    @Test
    public void validationTest04() {
        assertFalse(PasswordValidator.validate("Zxcvbnm123"));
    }

    /*
    No upperCase letter, no special character
     */
    @Test
    public void validationTest05() {
        assertFalse(PasswordValidator.validate("zxcvbnm123"));
    }

    /*
    No upperCase letter
     */
    @Test
    public void validationTest06() {
        assertFalse(PasswordValidator.validate("zxcvbnm123$"));
    }

    /*
    No upperCase letter, no lowerCase letter
     */
    @Test
    public void validationTest07() {
        assertFalse(PasswordValidator.validate("6239874123$"));
    }

    /*
    No upperCase letter, no lowerCase letter, no number
     */
    @Test
    public void validationTest08() {
        assertFalse(PasswordValidator.validate("$##$#$#$#$#$#$#$"));
    }

    /*
    Too long password
     */
    @Test
    public void validationTest09() {
        assertFalse(PasswordValidator.validate("Xzxcvbnsdf23434m123$s"));
    }

    /*
    All correct
     */
    @Test
    public void correctValidationTest() {
        assertTrue(PasswordValidator.validate("Xzxcvbnm123$"));
    }
}