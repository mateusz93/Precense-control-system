package neo.dmcs.view.security;

/**
 * @Author Mateusz Wieczorek, 09.04.16.
 */
public class LoginView {
    private String email;
    private String type;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
