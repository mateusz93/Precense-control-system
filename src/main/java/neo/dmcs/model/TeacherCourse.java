package neo.dmcs.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NamedQueries({
        @NamedQuery(name = TeacherCourse.FIND_BY_SUBJECT_ID, query = "from TeacherCourse t where t.subjectId = :id"),
        @NamedQuery(name = TeacherCourse.FIND_BY_TEACHER_ID, query = "from TeacherCourse t where t.teacherId = :id"),
        @NamedQuery(name = TeacherCourse.FIND_ALL, query = "from TeacherCourse")
})
@Entity
@Table(name = "TeacherCourse", schema = "data")
public class TeacherCourse {

    private int id;
    private int subjectId;
    private int teacherId;
    private String type;
    private int coursesQuantity;
    private int minPresence;
    private String description;

    public static final String FIND_BY_SUBJECT_ID = "TeacherCourseFindBySubjectId";
    public static final String FIND_BY_TEACHER_ID = "TeacherCourseFindByTeacherId";
    public static final String FIND_ALL = "TeacherCourseFindAll";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "subjectID")
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Column(name = "teacherID")
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "coursesQuantity")
    public int getCoursesQuantity() {
        return coursesQuantity;
    }

    public void setCoursesQuantity(int coursesQuantity) {
        this.coursesQuantity = coursesQuantity;
    }

    @Column(name = "minPresence")
    public int getMinPresence() {
        return minPresence;
    }

    public void setMinPresence(int minPresence) {
        this.minPresence = minPresence;
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

        TeacherCourse that = (TeacherCourse) o;

        if (id != that.id) return false;
        if (subjectId != that.subjectId) return false;
        if (teacherId != that.teacherId) return false;
        if (coursesQuantity != that.coursesQuantity) return false;
        if (minPresence != that.minPresence) return false;
        if (!type.equals(that.type)) return false;
        return description.equals(that.description);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + subjectId;
        result = 31 * result + teacherId;
        result = 31 * result + type.hashCode();
        result = 31 * result + coursesQuantity;
        result = 31 * result + minPresence;
        result = 31 * result + description.hashCode();
        return result;
    }
}
