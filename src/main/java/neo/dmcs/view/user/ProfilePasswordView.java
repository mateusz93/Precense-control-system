package neo.dmcs.view.user;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek on 10/22/16.
 */
@Data
public class ProfilePasswordView {

    private String password;
    private String newPassword;
    private String againNewPassword;
}
