package neo.dmcs.util;

/**
 * @Author Mateusz Wieczorek, 27.04.16.
 */
public class PasswordValidator {

    final static String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}";

    public static boolean validate(String password) {
        return password.matches(pattern);
    }

}
