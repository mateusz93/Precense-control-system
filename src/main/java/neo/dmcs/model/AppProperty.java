package neo.dmcs.model;

import javax.persistence.*;

/**
 * @Author Mateusz Wieczorek on 9/28/16.
 */

@NamedQueries({
        @NamedQuery(name = AppProperty.FIND_BY_NAME, query = "from AppProperty a where a.name = :name"),
        @NamedQuery(name = AppProperty.FIND_ALL, query = "from AppProperty")
})
@Entity
@Table(name = "AppProperty", schema = "data")
public class AppProperty {

    private int id;
    private String name;
    private String value;

    public static final String FIND_BY_NAME = "AppPropertyFindByName";
    public static final String FIND_ALL = "AppPropertyFindAll";

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

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppProperty that = (AppProperty) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
