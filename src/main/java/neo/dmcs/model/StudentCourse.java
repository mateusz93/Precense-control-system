package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(name = StudentCourse.FIND_BY_TEACHER_COURSE_ID, query = "from StudentCourse s where s.teacherCourseId = :id"),
        @NamedQuery(name = StudentCourse.FIND_BY_STUDENT_ID, query = "from StudentCourse s where s.studentId = :id"),
        @NamedQuery(name = StudentCourse.FIND_ALL, query = "from StudentCourse")
})
@Entity
@Table(name = "StudentCourse", schema = "data")
public class StudentCourse {

    private int id;
    private int teacherCourseId;
    private int studentId;
    private Timestamp saveTime;

    public static final String FIND_BY_TEACHER_COURSE_ID = "StudentCourseFindByTeacherCourseId";
    public static final String FIND_BY_STUDENT_ID = "StudentCourseFindByStudentId";
    public static final String FIND_ALL = "StudentCourseFindAll";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "teacherCourseID")
    public int getTeacherCourseId() {
        return teacherCourseId;
    }

    public void setTeacherCourseId(int teacherCourseId) {
        this.teacherCourseId = teacherCourseId;
    }

    @Column(name = "studentID")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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
        if (teacherCourseId != that.teacherCourseId) return false;
        if (studentId != that.studentId) return false;
        return saveTime.equals(that.saveTime);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + teacherCourseId;
        result = 31 * result + studentId;
        result = 31 * result + saveTime.hashCode();
        return result;
    }
}
