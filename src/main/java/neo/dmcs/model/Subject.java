package neo.dmcs.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(name = Subject.FIND_BY_NAME, query = "from Subject s where s.name = :name"),
        @NamedQuery(name = Subject.FIND_ALL, query = "from Subject")
})
@Entity
@Table(name = "Subject", schema = "data")
public class Subject {

    private int id;
    private String name;
    private String description;
    private int departmentId;

    public static final String FIND_BY_NAME = "SubjectFindByName";
    public static final String FIND_ALL = "SubjectFindAll";

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

    @Column(name = "departmentID")
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (id != subject.id) return false;
        if (departmentId != subject.departmentId) return false;
        if (!name.equals(subject.name)) return false;
        return description.equals(subject.description);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + departmentId;
        return result;
    }

}
