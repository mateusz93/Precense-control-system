package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "coursedate", schema = "data")
@Data
public class CourseDate {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherCourseID")
    private TeacherCourse teacherCourse;

    @Column(name = "startTime")
    private Time startTime;

    @Column(name = "finishTime")
    private Time finishTime;

    @Column(name = "date")
    private Date date;

}
