package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "AppLog", schema = "data")
public class AppLog {

    private int id;
    private Timestamp time;
    private EventDictionary eventDictionary;
    private User student;
    private String description;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eventDictionaryID")
    public EventDictionary getEventDictionary() {
        return eventDictionary;
    }

    public void setEventDictionary(EventDictionary eventDictionary) {
        this.eventDictionary = eventDictionary;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
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

        AppLog appLog = (AppLog) o;

        if (id != appLog.id) return false;
        if (!time.equals(appLog.time)) return false;
        if (!eventDictionary.equals(appLog.eventDictionary)) return false;
        if (!student.equals(appLog.student)) return false;
        return description.equals(appLog.description);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + time.hashCode();
        result = 31 * result + eventDictionary.hashCode();
        result = 31 * result + student.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
