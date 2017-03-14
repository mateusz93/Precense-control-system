package neo.dmcs.view.grade;

import lombok.Data;
import neo.dmcs.model.Grade;

/**
 * @Author Mateusz Wieczorek on 02.01.2017.
 */
@Data
public class StudentGradeDetailsView {

    private String time;
    private Grade previousGrade;
    private int value;
    private boolean finalGrade;
}
