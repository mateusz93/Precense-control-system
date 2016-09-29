package neo.dmcs.model;

import javax.persistence.*;

/**
 * @Author Mateusz Wieczorek on 9/29/16.
 */

@NamedQueries({
        @NamedQuery(name = SMSTemplate.FIND_BY_NAME, query = "from SMSTemplateDao a where a.name = :name"),
        @NamedQuery(name = SMSTemplate.FIND_ALL, query = "from SMSTemplateDao")
})
@Entity
@Table(name = "SMSTemplateDao", schema = "data")
public class SMSTemplate {


    private int id;
    private String name;
    private String value;

    public static final String FIND_BY_NAME = "SMSTemplateFindByName";
    public static final String FIND_ALL = "SMSTemplateFindAll";

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

        SMSTemplate that = (SMSTemplate) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
