package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "CourseDate", schema = "data")
public class CourseDate {

    private int id;
    private Subject subject;
    private Time startTime;
    private Time finishTime;
    private Date date;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjectID")
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setTeacherCourse(Subject subject) {
        this.subject = subject;
    }

    @Column(name = "startTime")
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Column(name = "finishTime")
    public Time getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseDate that = (CourseDate) o;

        if (id != that.id) return false;
        if (!subject.equals(that.subject)) return false;
        if (!startTime.equals(that.startTime)) return false;
        if (!finishTime.equals(that.finishTime)) return false;
        return date.equals(that.date);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + subject.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + finishTime.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
