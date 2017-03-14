package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "subject", schema = "data")
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fieldID")
    private Field field;

    @Column(name = "yearOfStudy")
    private int yearOfStudy;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "minQuantity")
    private int minQuantity;

}
