package neo.dmcs.view.precense;

/**
 * @Author Mateusz Wieczorek, 18.05.16.
 */
public class CheckPrecenseView {

    private int ID;
    private String firstName;
    private String lastName;
    private String precenseStatus;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrecenseStatus() {
        return precenseStatus;
    }

    public void setPrecenseStatus(String precenseStatus) {
        this.precenseStatus = precenseStatus;
    }

}
