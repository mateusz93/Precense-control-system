package neo.dmcs.view.grade;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek on 02.01.2017.
 */
@Data
public class StudentGradeView {

    private int courseId;
    private String name;
    private String teacherName;
}
