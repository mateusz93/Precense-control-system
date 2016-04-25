package neo.dmcs.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(name = StudentPrecense.FIND_BY_STUDENT_ID, query = "from StudentPrecense s where s.studentId = :id"),
        @NamedQuery(name = StudentPrecense.FIND_BY_COURSE_DATA_ID, query = "from StudentPrecense s where s.courseDateId = :id"),
        @NamedQuery(name = StudentPrecense.FIND_ALL, query = "from StudentPrecense")
})
@Entity
@Table(name = "StudentPrecense", schema = "data")
public class StudentPrecense {

    private int id;
    private String status;
    private int studentId;
    private int courseDateId;

    public static final String FIND_BY_STUDENT_ID = "StudentPrecenseFindByStudentId";
    public static final String FIND_BY_COURSE_DATA_ID = "StudentPrecenseFindByCourseDateId";
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

    @Column(name = "courseDateID")
    public int getCourseDateId() {
        return courseDateId;
    }

    public void setCourseDateId(int courseDateId) {
        this.courseDateId = courseDateId;
    }

    @Column(name = "studentID")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
