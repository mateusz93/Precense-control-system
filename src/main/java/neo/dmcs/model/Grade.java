package neo.dmcs.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Author Mateusz Wieczorek on 11/6/16.
 */
@Entity
@Table(name = "Grade", schema = "data")
public class Grade {

    private int id;
    private Subject subject;
    private Grade previousGrade;
    private User user;
    private int value;
    private boolean isFinalGrade;
    private Timestamp time;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    @JoinColumn(name = "studentID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "previousGradeID")
    public Grade getPreviousGrade() {
        return previousGrade;
    }

    public void setPreviousGrade(Grade previousGrade) {
        this.previousGrade = previousGrade;
    }

    @Column(name = "value")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Column(name = "isFinalGrade")
    public boolean isFinalGrade() {
        return isFinalGrade;
    }

    public void setFinalGrade(boolean finalGrade) {
        isFinalGrade = finalGrade;
    }

    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

}
