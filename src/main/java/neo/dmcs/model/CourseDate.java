package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(name = CourseDate.FIND_BY_COURSE_ID, query = "from CourseDate c where c.courseId = :id"),
        @NamedQuery(name = CourseDate.FIND_ALL, query = "from CourseDate")
})
@Entity
@Table(name = "CourseDate", schema = "data")
public class CourseDate {

    private int id;
    private int courseId;
    private Time startTime;
    private Time finishTime;
    private Date date;

    public static final String FIND_BY_COURSE_ID = "CourseDateFindByCourseId";
    public static final String FIND_ALL = "CourseDateFindAll";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "courseID")
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
        if (courseId != that.courseId) return false;
        if (!startTime.equals(that.startTime)) return false;
        if (!finishTime.equals(that.finishTime)) return false;
        return date.equals(that.date);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + courseId;
        result = 31 * result + startTime.hashCode();
        result = 31 * result + finishTime.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
