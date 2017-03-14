package neo.dmcs.view.user;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
@Data
public class ProfileGeneralView {

    private String firstName;
    private String lastName;
    private String email;
    private String group;
    private String type;
    private String phone;
    private String city;
    private String street;
    private String password;
    private String confirmPassword;
}
