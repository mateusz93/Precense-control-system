package neo.dmcs.view.course;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Data
public class SaveView {

    private String subjectName;
    private String teacherName;
    private String description;
    private int coursesQuantity;
    private int id;

}
