package neo.dmcs.view.security;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
@Data
public class RegisterView {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String type;
}
