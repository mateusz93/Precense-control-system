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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grade grade = (Grade) o;

        if (id != grade.id) return false;
        if (value != grade.value) return false;
        if (isFinalGrade != grade.isFinalGrade) return false;
        if (!subject.equals(grade.subject)) return false;
        if (!previousGrade.equals(grade.previousGrade)) return false;
        return time.equals(grade.time);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + subject.hashCode();
        result = 31 * result + previousGrade.hashCode();
        result = 31 * result + value;
        result = 31 * result + (isFinalGrade ? 1 : 0);
        result = 31 * result + time.hashCode();
        return result;
    }
}
