package neo.dmcs.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Contact.FIND_BY_EMAIL, query = "from Contact c where c.email = :email"),
        @NamedQuery(name = Contact.FIND_BY_GROUP, query = "from Contact c where c.group = :group"),
        @NamedQuery(name = Contact.FIND_BY_PHONE, query = "from Contact c where c.phone = :phone"),
        @NamedQuery(name = Contact.FIND_BY_CITY, query = "from Contact c where c.city = :city"),
        @NamedQuery(name = Contact.FIND_ALL, query = "from Contact")
})
@Entity
@Table(name = "Contact", schema = "data")
public class Contact {
    private int id;
    private String email;
    private String group;
    private String phone;
    private String street;
    private String city;

    public static final String FIND_BY_EMAIL = "ContactFindByEmail";
    public static final String FIND_BY_GROUP = "ContactFindByGroup";
    public static final String FIND_BY_PHONE = "ContactFindByPhone";
    public static final String FIND_BY_CITY = "ContactFindByCity";
    public static final String FIND_ALL = "ContactFindAll";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "`group`")
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact that = (Contact) o;

        if (id != that.id) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
