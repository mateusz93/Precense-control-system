package neo.dmcs.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = StudentPrecense.FIND_BY_STUDENT, query = "from StudentPrecense s where s.student = :student"),
        @NamedQuery(name = StudentPrecense.FIND_BY_COURSE_DATE, query = "from StudentPrecense s where s.courseDate = :courseDate"),
        @NamedQuery(name = StudentPrecense.FIND_ALL, query = "from StudentPrecense")
})
@Entity
@Table(name = "StudentPrecense", schema = "data")
public class StudentPrecense {

    private int id;
    private String status;
    private User student;
    private CourseDate courseDate;

    public static final String FIND_BY_STUDENT = "StudentPrecenseFindByStudent";
    public static final String FIND_BY_COURSE_DATE = "StudentPrecenseFindByCourseDate";
    public static final String FIND_ALL = "StudentPrecenseFindAll";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "courseDateID")
    public CourseDate getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(CourseDate courseDate) {
        this.courseDate = courseDate;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentPrecense that = (StudentPrecense) o;

        if (id != that.id) return false;
        if (!status.equals(that.status)) return false;
        if (!student.equals(that.student)) return false;
        return courseDate.equals(that.courseDate);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + status.hashCode();
        result = 31 * result + student.hashCode();
        result = 31 * result + courseDate.hashCode();
        return result;
    }
}
