package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "teachercourse", schema = "data")
@Data
public class TeacherCourse {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subjectID")
    private Subject subject;

    @Column(name = "studentGroup")
    private String studentGroup;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherID")
    private User teacher;

    @Column(name = "description")
    private String description;

}
