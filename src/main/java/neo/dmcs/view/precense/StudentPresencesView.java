package neo.dmcs.view.precense;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 28.04.16.
 */
@Data
public class StudentPresencesView {

    private int courseId;
    private String subjectName;
    private int quantity;
    private String teacherName;
}
