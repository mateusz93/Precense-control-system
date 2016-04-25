package neo.dmcs.view.user;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
public class StudentView {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentView that = (StudentView) o;

        if (ID != that.ID) return false;
        if (!firstName.equals(that.firstName)) return false;
        if (!lastName.equals(that.lastName)) return false;
        return precenseStatus.equals(that.precenseStatus);

    }

    @Override
    public int hashCode() {
        int result = ID;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + precenseStatus.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StudentView{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", precenseStatus='" + precenseStatus + '\'' +
                '}';
    }
}
