package neo.dmcs.model;

import lombok.Data;
import neo.dmcs.enums.Role;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "User", schema = "data")
@Data
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Role type;

    @Column(name = "password")
    private String password;

    @Column(name = "lastLogin")
    private Timestamp lastLogin;

    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fieldID")
    private Field field;

    @Column(name = "`group`")
    private String group;

    @Column(name = "yearOfStudy")
    private int yearOfStudy;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
