package neo.dmcs.view.user;

/**
 * @Author Mateusz Wieczorek on 10/22/16.
 */
public class ProfilePasswordView {

    private String password;
    private String newPassword;
    private String againNewPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAgainNewPassword() {
        return againNewPassword;
    }

    public void setAgainNewPassword(String againNewPassword) {
        this.againNewPassword = againNewPassword;
    }

    @Override
    public String toString() {
        return "ProfilePasswordView{" +
                "password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", againNewPassword='" + againNewPassword + '\'' +
                '}';
    }
}
