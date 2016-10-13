package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "StudentCourse", schema = "data")
public class StudentCourse {

    private int id;
    private TeacherCourse teacherCourse;
    private User student;
    private Timestamp saveTime;

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
    @JoinColumn(name = "teacherCourseID")
    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    @Column(name = "saveTime")
    public Timestamp getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Timestamp saveTime) {
        this.saveTime = saveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentCourse that = (StudentCourse) o;

        if (id != that.id) return false;
        if (!teacherCourse.equals(that.teacherCourse)) return false;
        if (!student.equals(that.student)) return false;
        return saveTime.equals(that.saveTime);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + teacherCourse.hashCode();
        result = 31 * result + student.hashCode();
        result = 31 * result + saveTime.hashCode();
        return result;
    }
}
