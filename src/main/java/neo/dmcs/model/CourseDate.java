package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "CourseDate", schema = "data")
public class CourseDate {

    private int id;
    private TeacherCourse teacherCourse;
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
    }
}
