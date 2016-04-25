package neo.dmcs.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(name = Department.FIND_BY_NAME, query = "from Department d where d.name = :name"),
        @NamedQuery(name = Department.FIND_ALL, query = "from Department")
})
@Entity
@Table(name = "Department", schema = "data")
public class Department {
    private int id;
    private String name;
    private String description;

    public static final String FIND_BY_NAME = "DepartmentFindByName";
    public static final String FIND_ALL = "DepartmentFindAll";

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
