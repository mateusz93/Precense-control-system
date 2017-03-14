package neo.dmcs.view.security;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
@Data
public class LoginView {
    private String email;
    private String type;
    private String password;
}
