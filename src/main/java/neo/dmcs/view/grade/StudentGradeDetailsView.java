package neo.dmcs.view.grade;

import neo.dmcs.model.Grade;

/**
 * @Author Mateusz Wieczorek on 02.01.2017.
 */
public class StudentGradeDetailsView {

    private String time;
    private Grade previousGrade;
    private int value;
    private boolean finalGrade;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Grade getPreviousGrade() {
        return previousGrade;
    }

    public void setPreviousGrade(Grade previousGrade) {
        this.previousGrade = previousGrade;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(boolean finalGrade) {
        this.finalGrade = finalGrade;
    }
}
