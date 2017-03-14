package neo.dmcs.view.course;

import lombok.Data;

/**
 * @Author Mateusz Wieczorek, 30.03.16.
 */
@Data
public class CourseView {

    private int id;
    private String subjectName;
    private String departmentID;
    private String type;
    private int quantity;
    private int min;
    private String teacherName;
    private String description;
}
