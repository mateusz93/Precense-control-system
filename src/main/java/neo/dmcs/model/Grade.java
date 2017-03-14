package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author Mateusz Wieczorek on 11/6/16.
 */
@Entity
@Table(name = "Grade", schema = "data")
@Data
public class Grade {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    private TeacherCourse teacherCourse;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "previousGradeID")
    private Grade previousGrade;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User user;

    @Column(name = "value")
    private int value;

    @Column(name = "isFinalGrade")
    private boolean isFinalGrade;

    @Column(name = "time")
    private Timestamp time;

}
