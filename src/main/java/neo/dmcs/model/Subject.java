package neo.dmcs.model;

import javax.persistence.*;

@Entity
@Table(name = "Subject", schema = "data")
public class Subject {

    private int id;
    private String name;
    private String description;
    private Field field;
    private int year;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fieldID")
    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Column(name = "year")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
