package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "studentcourse", schema = "data")
@Data
public class StudentCourse {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    private TeacherCourse teacherCourse;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User student;

}
