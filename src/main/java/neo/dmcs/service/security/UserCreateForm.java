package neo.dmcs.service.security;

import lombok.Data;
import neo.dmcs.enums.Role;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Author Mateusz Wieczorek on 14.03.2017.
 */
@Data
public class UserCreateForm {

    @NotEmpty
    private String email = "";

    @NotEmpty
    private String password = "";

    @NotEmpty
    private String passwordRepeated = "";

    @NotNull
    private Role role = Role.STUDENT;
}
