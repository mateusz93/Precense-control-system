package neo.dmcs.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(name = EventDictionary.FIND_BY_NAME, query = "from EventDictionary e where e.eventName = :name"),
        @NamedQuery(name = EventDictionary.FIND_ALL, query = "from EventDictionary")
})
@Entity
@Table(name = "EventDictionary", schema = "data")
public class EventDictionary {
    private int id;
    private String eventName;
    private String description;

    public static final String FIND_BY_NAME = "EventDictionaryFindByName";
    public static final String FIND_ALL = "EventDictionaryFindAll";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "eventName")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

        EventDictionary that = (EventDictionary) o;

        if (id != that.id) return false;
        if (eventName != null ? !eventName.equals(that.eventName) : that.eventName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (eventName != null ? eventName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
