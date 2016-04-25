package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(name = AppLog.FIND_BY_EVENT_DICTIONARY_ID, query = "from AppLog a where a.eventDictionaryId = :id"),
        @NamedQuery(name = AppLog.FIND_BY_TEACHER_ID, query = "from AppLog a where a.teacherId = :id"),
        @NamedQuery(name = AppLog.FIND_BY_STUDENT_ID, query = "from AppLog a where a.studentId = :id"),
        @NamedQuery(name = AppLog.FIND_ALL, query = "from AppLog")
})
@Entity
@Table(name = "AppLog", schema = "data")
public class AppLog {

    private int id;
    private Timestamp time;
    private int eventDictionaryId;
    private int teacherId;
    private int studentId;
    private String description;

    public static final String FIND_BY_EVENT_DICTIONARY_ID = "AppLogFindByEventDictionaryId";
    public static final String FIND_BY_TEACHER_ID = "AppLogFindByTeacherId";
    public static final String FIND_BY_STUDENT_ID = "AppLogFindByStudentId";
    public static final String FIND_ALL = "AppLogFindAll";

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

    @Column(name = "eventDictionaryID")
    public int getEventDictionaryId() {
        return eventDictionaryId;
    }

    public void setEventDictionaryId(int eventDictionaryId) {
        this.eventDictionaryId = eventDictionaryId;
    }

    @Column(name = "teacherID")
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Column(name = "studentID")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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
        if (eventDictionaryId != appLog.eventDictionaryId) return false;
        if (teacherId != appLog.teacherId) return false;
        if (studentId != appLog.studentId) return false;
        if (!time.equals(appLog.time)) return false;
        return description.equals(appLog.description);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + time.hashCode();
        result = 31 * result + eventDictionaryId;
        result = 31 * result + teacherId;
        result = 31 * result + studentId;
        result = 31 * result + description.hashCode();
        return result;
    }
}
