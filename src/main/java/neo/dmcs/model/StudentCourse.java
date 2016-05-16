package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Timestamp;

@NamedQueries({
        @NamedQuery(name = StudentCourse.FIND_BY_STUDENT_AND_TEACHER_COURSE, query = "from StudentCourse s where s.teacherCourse = :teacherCourse and s.student = :student"),
        @NamedQuery(name = StudentCourse.FIND_BY_TEACHER_COURSE, query = "from StudentCourse s where s.teacherCourse = :teacherCourse"),
        @NamedQuery(name = StudentCourse.FIND_BY_STUDENT, query = "from StudentCourse s where s.student = :student"),
        @NamedQuery(name = StudentCourse.FIND_ALL, query = "from StudentCourse")
})
@Entity
@Table(name = "StudentCourse", schema = "data")
public class StudentCourse {

    private int id;
    private TeacherCourse teacherCourse;
    private User student;
    private Timestamp saveTime;

    public static final String FIND_BY_STUDENT_AND_TEACHER_COURSE = "StudentCourseFindByStudentAndTeacherCourse";
    public static final String FIND_BY_TEACHER_COURSE = "StudentCourseFindByTeacherCourse";
    public static final String FIND_BY_STUDENT = "StudentCourseFindByStudent";
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
