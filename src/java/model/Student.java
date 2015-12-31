package model;

import java.util.Objects;

/**
 *
 * @author Mateusz Wieczorek
 * 
 */
public class Student {

    private int ID;
    private String firstName;
    private String lastName;
    private String precenseStatus;

    public String getPrecenseStatus() {
        return precenseStatus;
    }

    public void setPrecenseStatus(String precenseStatus) {
        this.precenseStatus = precenseStatus;
    }
    
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.ID;
        hash = 83 * hash + Objects.hashCode(this.firstName);
        hash = 83 * hash + Objects.hashCode(this.lastName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student{" + "ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", precenseStatus=" + precenseStatus + '}';
    }
}
