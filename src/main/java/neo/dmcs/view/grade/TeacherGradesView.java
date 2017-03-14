package neo.dmcs.view.grade;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek on 15.01.2017.
 */
@Data
public class TeacherGradesView {

    private String studentId;
    private String firstName;
    private String lastName;
    private String grades;
    private String finalGrade;
    private String courseId;

}
