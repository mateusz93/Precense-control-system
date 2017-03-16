package neo.dmcs.service.security;

import lombok.Getter;
import neo.dmcs.enums.Role;
import neo.dmcs.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * @Author Mateusz Wieczorek on 14.03.2017.
 */
@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getType().toString()));
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getType();
    }

}
