package neo.dmcs.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "studentprecense", schema = "data")
@Data
public class StudentPresence {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "status")
    private String status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentID")
    private User student;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "courseDateID")
    private CourseDate courseDate;

}
