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

        Contact contact = (Contact) o;

        if (id != contact.id) return false;
        if (!email.equals(contact.email)) return false;
        if (!group.equals(contact.group)) return false;
        if (!phone.equals(contact.phone)) return false;
        if (!street.equals(contact.street)) return false;
        return city.equals(contact.city);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + email.hashCode();
        result = 31 * result + group.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + city.hashCode();
        return result;
    }
}
