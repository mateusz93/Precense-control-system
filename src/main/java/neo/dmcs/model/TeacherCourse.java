package neo.dmcs.model;

import javax.persistence.*;

@Entity
@Table(name = "TeacherCourse", schema = "data")
public class TeacherCourse {

    private int id;
    private Subject subject;
    private User teacher;
    private String type;
    private int coursesQuantity;
    private int minPresence;
    private String description;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherID")
    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
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
        if (coursesQuantity != that.coursesQuantity) return false;
        if (minPresence != that.minPresence) return false;
        if (!subject.equals(that.subject)) return false;
        if (!teacher.equals(that.teacher)) return false;
        if (!type.equals(that.type)) return false;
        return description.equals(that.description);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + subject.hashCode();
        result = 31 * result + teacher.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + coursesQuantity;
        result = 31 * result + minPresence;
        result = 31 * result + description.hashCode();
        return result;
    }
}
